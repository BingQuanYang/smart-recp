package com.smart.recp.service.goods.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.enums.ResultCode;
import com.smart.recp.common.core.result.PageResult;
import com.smart.recp.common.core.util.BeanCopyUtils;
import com.smart.recp.service.goods.dto.*;
import com.smart.recp.service.goods.entity.*;
import com.smart.recp.service.goods.mapper.*;
import com.smart.recp.service.goods.service.GoodsService;
import com.smart.recp.service.goods.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ybq
 */
@Service
@Slf4j
public class GoodsServiceImpl implements GoodsService {


    @Resource
    GoodsMapper goodsMapper;

    @Resource
    GoodsResourceMapper goodsResourceMapper;

    @Resource
    GoodsSpecMapper goodsSpecMapper;

    @Resource
    GoodsSpecPriceMapper goodsSpecPriceMapper;

    @Resource
    GoodsCategoryMapper goodsCategoryMapper;

    @Override
    public PageResult<GoodsVO> list(int page, int size, GoodsDTO goodsDTO) throws BaseException {
        PageResult<GoodsVO> goodsVOPageResult = new PageResult<>();
        try {
            Page<Goods> goodsPage = new Page<>(page, size);
            if (ObjectUtils.isEmpty(goodsDTO)) {
                goodsDTO = new GoodsDTO();
            }
            IPage<Goods> goodsIPage = goodsMapper.selectGoodsList(goodsPage, goodsDTO);
            List<GoodsVO> goodsVOList = BeanCopyUtils.copyListProperties(goodsIPage.getRecords(), GoodsVO::new);
            goodsVOPageResult.setList(goodsVOList);
            goodsVOPageResult.setPage(goodsIPage.getCurrent());
            goodsVOPageResult.setPages(goodsIPage.getPages());
            goodsVOPageResult.setPageSize(goodsIPage.getSize());
            goodsVOPageResult.setTotalCount(goodsIPage.getTotal());
            for (GoodsVO goodsVO : goodsVOList) {
                System.out.println(goodsVO);
            }
            log.info("获取商品列表成功：{}", goodsVOPageResult);
            return goodsVOPageResult;
        } catch (Exception e) {
            log.error("获取商品列表失败：page：{}, size：{},goodsDTO：{},error:{}", page, size, goodsDTO, e.getMessage());
            e.printStackTrace();
            throw new BaseException(ResultCode.ERROR);
        }
    }

    /**
     * 根据商品ID获取商品详情信息
     * 级联
     *
     * @param goodsId 　商品ID
     * @return
     */
    @Override
    public GoodsVO getCascadeByGoodsId(Integer goodsId) throws BaseException {
        try {
            Goods goods = goodsMapper.getCascadeByGoodsId(goodsId);
            if (goods == null || goods.getIsDelete().equals(1)) {
                log.error("根据商品ID获取商品信息失败");
                throw new Exception();
            }
            GoodsVO goodsVO = new GoodsVO();
            BeanUtils.copyProperties(goods, goodsVO);
            List<GoodsResourceVO> goodsResourceVOList = BeanCopyUtils.copyListProperties(goods.getGoodsResourceList(), GoodsResourceVO::new);
            List<GoodsSpecVO> goodsSpecVOList = BeanCopyUtils.copyListProperties(goods.getGoodsSpecList(), GoodsSpecVO::new, (s, t) -> {
                List<GoodsSpecPriceVO> goodsSpecPriceVOList = BeanCopyUtils.copyListProperties(s.getGoodsSpecPriceList(), GoodsSpecPriceVO::new);
                goodsSpecPriceVOList.sort((a, b) -> {
                    if (!a.getType().equals(b.getType())) {
                        return a.getType() - b.getType();
                    }
                    return a.getMin() - b.getMin();
                });
                t.setGoodsSpecPriceVOList(goodsSpecPriceVOList);
            });
            goodsVO.setGoodsResourceVOList(goodsResourceVOList);
            goodsVO.setGoodsSpecVOList(goodsSpecVOList);
            return goodsVO;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据商品ID获取商品信息失败（级联）：goodsId：{}", goodsId);
            throw new BaseException(ResultCode.ERROR.getStatus(), "根据商品ID获取商品信息失败");
        }
    }

    /**
     * 添加商品信息
     *
     * @param goodsDTO
     * @return
     */
    @Override
    @Transactional
    public Boolean add(GoodsDTO goodsDTO) throws BaseException {
        try {

            /**
             * 添加商品
             */
            Goods goods = new Goods();

            BeanUtils.copyProperties(goodsDTO, goods);

            //TODO
            goods.setCategoryName("测试");
            goods.setCreator(1);

            int insert = goodsMapper.insert(goods);
            if (insert < 1) {
                log.error("插入数据库失败 => goods:{}", goods);
                throw new BaseException(ResultCode.ERROR);
            }

            /**
             * 添商品资源
             */
            //默认第一张为主图
            if (goodsDTO.getGoodsResourceDTOList() != null && goodsDTO.getGoodsResourceDTOList().size() > 0) {
                List<GoodsResourceDTO> collect = goodsDTO.getGoodsResourceDTOList().stream().filter(item -> item.getIsMaster() == 1 && item.getType() == 1).collect(Collectors.toList());
                if (collect == null || collect.size() < 1) {
                    for (int i = 0; i < goodsDTO.getGoodsResourceDTOList().size(); i++) {
                        if (goodsDTO.getGoodsResourceDTOList().get(i).getType() == 1) {
                            goodsDTO.getGoodsResourceDTOList().get(i).setIsMaster(1);
                            break;
                        }
                    }
                }
            }
            for (GoodsResourceDTO goodsResourceDTO : goodsDTO.getGoodsResourceDTOList()) {
                GoodsResource goodsResource = new GoodsResource();
                BeanUtils.copyProperties(goodsResourceDTO, goodsResource);
                goodsResource.setGoodsId(goods.getGoodsId());
                insert = goodsResourceMapper.insert(goodsResource);
                if (insert < 1) {
                    log.error("插入数据库失败 => goodsResource:{}", goodsResource);
                    throw new BaseException(ResultCode.ERROR);
                }
            }

            /**
             * 添加商品规格
             */
            for (GoodsSpecDTO goodsSpecDTO : goodsDTO.getGoodsSpecDTOList()) {
                GoodsSpec goodsSpec = new GoodsSpec();
                BeanUtils.copyProperties(goodsSpecDTO, goodsSpec);
                goodsSpec.setGoodsId(goods.getGoodsId());
                insert = goodsSpecMapper.insert(goodsSpec);
                if (insert < 1) {
                    log.error("插入数据库失败 => goodsSpec:{}", goodsSpec);
                    throw new BaseException(ResultCode.ERROR);
                }

                /**
                 * 添加规格价格
                 */
                for (GoodsSpecPriceDTO goodsSpecPriceDTO : goodsSpecDTO.getGoodsSpecPriceDTOList()) {
                    GoodsSpecPrice goodsSpecPrice = new GoodsSpecPrice();
                    BeanUtils.copyProperties(goodsSpecPriceDTO, goodsSpecPrice);
                    goodsSpecPrice.setSpecId(goodsSpec.getSpecId());
                    insert = goodsSpecPriceMapper.insert(goodsSpecPrice);
                    if (insert < 1) {
                        log.error("插入数据库失败 => goodsSpecPrice:{}", goodsSpecPrice);
                        throw new BaseException(ResultCode.ERROR);
                    }
                }
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("添加商品失败 => goodsDTO:{}", goodsDTO);
            throw new BaseException(ResultCode.ERROR);
        }
    }

    /**
     * 修改商品信息
     *
     * @param goodsDTO
     * @return
     */
    @Override
    @Transactional
    public Boolean update(GoodsDTO goodsDTO) throws BaseException {
        try {

            //TODO  删除商品缓存

            /**
             * 修改商品
             */
            Goods goods = new Goods();
            BeanUtils.copyProperties(goodsDTO, goods);
            int update = goodsMapper.updateById(goods);
            if (update < 1) {
                log.error("更新数据库失败 => goods:{}", goods);
                throw new BaseException(ResultCode.ERROR);
            }

            GoodsVO goodsVO = getCascadeByGoodsId(goods.getGoodsId());

            /**
             * 商品资源
             */
            //先全部删除，再重新插入比较方便  --  但是我偏不，我骗要删除修改添加
            List<GoodsResourceDTO> goodsResourceDTOList = goodsDTO.getGoodsResourceDTOList();
            List<GoodsResourceDTO> updateGoodsResourceList = goodsResourceDTOList.stream().filter(item -> ObjectUtils.isNotEmpty(item.getResourceId())).collect(Collectors.toList());
            Stream<GoodsResourceDTO> addGoodsResourceList = goodsResourceDTOList.stream().filter(item -> ObjectUtils.isEmpty(item.getResourceId()));
            List<GoodsResourceVO> goodsResourceVOList = goodsVO.getGoodsResourceVOList();
            //差集goodsResourceVOList - updateGoodsResourceList
            Predicate<GoodsResourceVO> goodsResourceVOPredicate = vo -> updateGoodsResourceList.stream().noneMatch(dto -> vo.getResourceId().equals(dto.getResourceId()));
            List<Integer> deleteGoodsResourceIdList = goodsResourceVOList.stream().filter(goodsResourceVOPredicate).map(item -> item.getResourceId()).collect(Collectors.toList());

            //删除商品资源
            if (ObjectUtils.isNotEmpty(deleteGoodsResourceIdList) && deleteGoodsResourceIdList.size() > 0) {
                int deleteGoodsResourceBatchIds = goodsResourceMapper.deleteBatchIds(deleteGoodsResourceIdList);
                if (deleteGoodsResourceBatchIds < 1) {
                    log.error("失败：删除商品资源数据库失败 => deleteGoodsResourceBatchIds:{}", deleteGoodsResourceBatchIds);
                    throw new BaseException(ResultCode.ERROR);
                }
            }

            for (GoodsResourceDTO goodsResourceDTO : goodsResourceDTOList) {
                GoodsResource goodsResource = new GoodsResource();
                BeanUtils.copyProperties(goodsResourceDTO, goodsResource);
                int result = -1;
                if (ObjectUtils.isNotEmpty(goodsResource.getResourceId())) {
                    //更新商品资源
                    result = goodsResourceMapper.updateById(goodsResource);
                    if (result < 1) {
                        log.error("失败：更新商品资源数据库失败 => goodsResource:{}", goodsResource);
                        throw new BaseException(ResultCode.ERROR);
                    }
                } else {
                    //添加商品资源
                    goodsResource.setGoodsId(goods.getGoodsId());
                    result = goodsResourceMapper.insert(goodsResource);
                    if (result < 1) {
                        log.error("失败：添加商品资源数据库失败 => goodsResource:{}", goodsResource);
                        throw new BaseException(ResultCode.ERROR);
                    }
                }
            }


            /**
             * 商品规格
             */

            List<GoodsSpecDTO> goodsSpecDTOList = goodsDTO.getGoodsSpecDTOList();
            List<GoodsSpecVO> goodsSpecVOList = goodsVO.getGoodsSpecVOList();
            List<GoodsSpecDTO> updateGoodsSpecList = goodsSpecDTOList.stream().filter(item -> ObjectUtils.isNotEmpty(item.getSpecId())).collect(Collectors.toList());
            List<GoodsSpecDTO> addGoodsSpecList = goodsSpecDTOList.stream().filter(item -> ObjectUtils.isEmpty(item.getSpecId())).collect(Collectors.toList());
            Predicate<GoodsSpecVO> goodsSpecVOPredicate = vo -> updateGoodsSpecList.stream().noneMatch(dto -> vo.getSpecId().equals(dto.getSpecId()));

            List<GoodsSpecVO> deleteGoodsSpecList = goodsSpecVOList.stream().filter(goodsSpecVOPredicate).collect(Collectors.toList());
            List<Integer> deleteGoodsSpecIdList = deleteGoodsSpecList.stream().map(item -> item.getSpecId()).collect(Collectors.toList());

            List<Integer> deleteGoodsSpecPriceIdList = new ArrayList<>();
            deleteGoodsSpecList.forEach(goodsSpecVO -> {
                List<Integer> priceIdList = goodsSpecVO.getGoodsSpecPriceVOList().stream().map(item -> item.getPriceId()).collect(Collectors.toList());
                deleteGoodsSpecPriceIdList.addAll(priceIdList);
            });
            //删除需要修改的商品规格的删除的价格
            updateGoodsSpecList.forEach(item1 -> {
                goodsSpecVOList.forEach(item2 -> {
                    if (item1.getSpecId().equals(item2.getSpecId())) {
                        List<GoodsSpecPriceDTO> goodsSpecPriceDTOList = item1.getGoodsSpecPriceDTOList().stream().filter(dto -> ObjectUtils.isNotEmpty(dto.getPriceId())).collect(Collectors.toList());
                        List<GoodsSpecPriceVO> goodsSpecPriceVOList = item2.getGoodsSpecPriceVOList();
                        Predicate<GoodsSpecPriceVO> goodsSpecPriceVOPredicate = vo -> goodsSpecPriceDTOList.stream().noneMatch(dto -> vo.getPriceId().equals(dto.getPriceId()));
                        List<Integer> priceIdList = goodsSpecPriceVOList.stream().filter(goodsSpecPriceVOPredicate).map(vo -> vo.getPriceId()).collect(Collectors.toList());
                        deleteGoodsSpecPriceIdList.addAll(priceIdList);
                    }
                });
            });

            //删除规格价格
            if (ObjectUtils.isNotEmpty(deleteGoodsSpecPriceIdList) && deleteGoodsSpecPriceIdList.size() > 0) {
                int deleteGoodsSpecPriceBatchIds = goodsSpecPriceMapper.deleteBatchIds(deleteGoodsSpecPriceIdList);
                if (deleteGoodsSpecPriceBatchIds < 1) {
                    log.error("失败：删除商品规格价格数据库失败 => deleteGoodsSpecPriceBatchIds:{}", deleteGoodsSpecPriceBatchIds);
                    throw new BaseException(ResultCode.ERROR);
                }
            }
            //删除规格
            if (ObjectUtils.isNotEmpty(deleteGoodsSpecIdList) && deleteGoodsSpecIdList.size() > 0) {
                int deleteGoodsSpecBatchIds = goodsSpecMapper.deleteBatchIds(deleteGoodsSpecIdList);
                if (deleteGoodsSpecBatchIds < 1) {
                    log.error("失败：删除商品规格数据库失败 => deleteGoodsSpecBatchIds:{}", deleteGoodsSpecBatchIds);
                    throw new BaseException(ResultCode.ERROR);
                }
            }


            for (GoodsSpecDTO goodsSpecDTO : goodsSpecDTOList) {
                GoodsSpec goodsSpec = new GoodsSpec();
                BeanUtils.copyProperties(goodsSpecDTO, goodsSpec);
                int specResult = -1;
                if (ObjectUtils.isNotEmpty(goodsSpecDTO.getSpecId())) {
                    //修改规格
                    specResult = goodsSpecMapper.updateById(goodsSpec);
                    if (specResult < 1) {
                        log.error("失败：【商品规格】更新数据库失败 => goodsSpec:{}", goodsSpec);
                        throw new BaseException(ResultCode.ERROR);
                    }
                } else {
                    //添加规格
                    goodsSpec.setGoodsId(goods.getGoodsId());
                    specResult = goodsSpecMapper.insert(goodsSpec);
                    if (specResult < 1) {
                        log.error("失败：【商品规格】添加数据库失败 => goodsSpec:{}", goodsSpec);
                        throw new BaseException(ResultCode.ERROR);
                    }
                }


                /**
                 * 规格价格
                 */
                List<GoodsSpecPriceDTO> goodsSpecPriceDTOList = goodsSpecDTO.getGoodsSpecPriceDTOList();
                for (GoodsSpecPriceDTO goodsSpecPriceDTO : goodsSpecPriceDTOList) {
                    GoodsSpecPrice goodsSpecPrice = new GoodsSpecPrice();
                    BeanUtils.copyProperties(goodsSpecPriceDTO, goodsSpecPrice);
                    int priceResult = -1;
                    if (ObjectUtils.isNotEmpty(goodsSpecPriceDTO.getPriceId())) {
                        //修改商品规格价格
                        priceResult = goodsSpecPriceMapper.updateById(goodsSpecPrice);
                        if (priceResult < 1) {
                            log.error("失败：【商品规格价格】更新数据库失败 => goodsSpecPrice:{}", goodsSpecPrice);
                            throw new BaseException(ResultCode.ERROR);
                        }
                    } else {
                        //添加商品规格价格
                        goodsSpecPrice.setSpecId(goodsSpec.getSpecId());
                        priceResult = goodsSpecPriceMapper.insert(goodsSpecPrice);
                        if (priceResult < 1) {
                            log.error("失败：【商品规格价格】添加数据库失败 => goodsSpecPrice:{}", goodsSpecPrice);
                            throw new BaseException(ResultCode.ERROR);
                        }
                    }

                }
            }

            //TODO  异步延迟删除商品缓存

            log.info("成功：修改商品成功");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("修改商品信息失败 => goodsDTO:{}", goodsDTO);
            throw new BaseException(ResultCode.ERROR);
        }
    }

    /**
     * 删除商品信息
     *
     * @param goodsIdList 商品ID列表
     * @return
     */
    @Override
    public Integer delete(List<Integer> goodsIdList) throws BaseException {
        try {
            Integer delete = goodsMapper.deleteByGoodsIdList(goodsIdList);
            if (delete < 1) {
                log.error("删除商品失败 => goodsIdList:{}", goodsIdList);
                throw new BaseException(ResultCode.ERROR);
            }
            return delete;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除商品信息失败 => goodsIdList:{}", goodsIdList);
            throw new BaseException(ResultCode.ERROR);
        }
    }

    @Override
    public List<GoodsResourceVO> getResourceByGoodsIdListAndIsMaster(List<Integer> goodsIdList, int isMaster) throws BaseException {
        try {
            List<GoodsResource> resourceList = goodsResourceMapper.getResourceByGoodsIdListAndIsMaster(goodsIdList, isMaster);
            if (goodsIdList == null || goodsIdList.size() < 1) {
                log.error("根据商品Id列表和是否为主图获取商品资源信息 => goodsIdList:{},isMaster:{}", goodsIdList, isMaster);
                throw new BaseException(ResultCode.ERROR);
            }
            List<GoodsResourceVO> resourceVOList = BeanCopyUtils.copyListProperties(resourceList, GoodsResourceVO::new);

            log.info("成功：根据商品Id列表和是否为主图获取商品资源信息成功，{}", resourceVOList);
            return resourceVOList;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("失败：根据商品Id列表和是否为主图获取商品资源信息失败 => goodsIdList:{},isMaster:{}", goodsIdList, isMaster);
            throw new BaseException(ResultCode.ERROR);
        }
    }


    /**
     * 获取商品分类信息
     *
     * @return
     */
    @Override
    public List<GoodsCategoryVO> getCategoryCascade() throws BaseException {
        try {
            List<GoodsCategoryVO> goodsCategoryVOList = new ArrayList<>();
            int level = 1;
            List<GoodsCategory> goodsCategoryList = goodsCategoryMapper.getCategoryCascadeByLevel(level);

            if (goodsCategoryList == null || goodsCategoryList.size() < 1) {
                log.error("失败：获取商品分类信息失败");
                throw new BaseException(ResultCode.ERROR);
            }

            goodsCategoryList.forEach(item -> {
                GoodsCategoryVO goodsCategoryVO = new GoodsCategoryVO();
                BeanUtils.copyProperties(item, goodsCategoryVO);
                List<GoodsCategoryVO> voList = BeanCopyUtils.copyListProperties(item.getChildren(), GoodsCategoryVO::new);
                goodsCategoryVO.setChildren(voList);
                goodsCategoryVOList.add(goodsCategoryVO);
            });
            log.info("成功：获取商品分类成功,{}", goodsCategoryVOList);
            return goodsCategoryVOList;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("失败：获取商品分类信息失败");
            throw new BaseException(ResultCode.ERROR.getStatus(), "失败：获取商品分类信息失败");
        }
    }

    @Override
    public Boolean updateById(GoodsDTO goodsDTO) throws BaseException {
        try {
            Goods goods = new Goods();
            BeanUtils.copyProperties(goodsDTO, goods);
            int update = goodsMapper.updateById(goods);
            if (update < 1) {
                throw new BaseException(ResultCode.ERROR.getStatus(), "失败：根据ID修改商品信息失败");
            }
            log.info("成功：【updateById】根据ID修改商品信息成功，{}", goodsDTO);
            return true;
        } catch (Exception e) {
            log.error("失败：【updateById】根据ID修改商品信息失败，{}", goodsDTO);
            e.printStackTrace();
            throw new BaseException(ResultCode.ERROR.getStatus(), "失败：根据ID修改商品信息失败");
        }
    }


    @Override
    public Boolean addCategory(GoodsCategoryDTO goodsCategoryDTO) throws BaseException {
        try {
            GoodsCategory goodsCategory = new GoodsCategory();
            BeanUtils.copyProperties(goodsCategoryDTO, goodsCategory);
            int insert = goodsCategoryMapper.insert(goodsCategory);
            if (insert < 1) {
                log.error("失败：【addCategory】添加商品分类信息失败，{}", goodsCategoryDTO);
                throw new BaseException(ResultCode.ERROR.getStatus(), "失败：添加商品分类信息失败");
            }
            log.info("成功：【addCategory】添加商品分类信息成功，{}", goodsCategoryDTO);
            return true;
        } catch (Exception e) {
            log.error("失败：【addCategory】添加商品分类信息失败，{}", goodsCategoryDTO);
            e.printStackTrace();
            throw new BaseException(ResultCode.ERROR.getStatus(), "失败：添加商品分类信息失败");
        }
    }

    @Override
    public Boolean modifyCategory(GoodsCategoryDTO goodsCategoryDTO) throws BaseException {
        try {
            GoodsCategory goodsCategory = new GoodsCategory();
            BeanUtils.copyProperties(goodsCategoryDTO, goodsCategory);
            int update = goodsCategoryMapper.updateById(goodsCategory);
            if (update < 1) {
                log.error("失败：【modifyCategory】根据ID修改商品分类信息失败，{}", goodsCategoryDTO);
                throw new BaseException(ResultCode.ERROR.getStatus(), "失败：根据ID修改商品分类信息失败");
            }
            log.info("成功：【modifyCategory】根据ID修改商品分类信息成功，{}", goodsCategoryDTO);
            return true;
        } catch (Exception e) {
            log.error("失败：【modifyCategory】根据ID修改商品分类信息失败，{}", goodsCategoryDTO);
            e.printStackTrace();
            throw new BaseException(ResultCode.ERROR.getStatus(), "失败：根据ID修改商品分类信息失败");
        }
    }

    @Override
    public Integer removeCategory(List<Integer> categoryIdList) throws BaseException {
        try {
            int delete = goodsCategoryMapper.deleteBatchIds(categoryIdList);
            if (delete < 1) {
                log.error("失败：【removeCategory】删除商品失败 => categoryIdList:{}", categoryIdList);
                throw new BaseException(ResultCode.ERROR);
            }
            log.info("成功：【removeCategory】删除商品成功 => categoryIdList:{},{}", categoryIdList, delete);
            return delete;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("失败：【removeCategory】删除商品信息失败 => categoryIdList:{}", categoryIdList);
            throw new BaseException(ResultCode.ERROR.getStatus(), "删除商品分类信息失败");
        }
    }

    @Override
    public GoodsCategoryVO getCategoryById(Integer categoryId) throws BaseException {
        try {
            GoodsCategory goodsCategory = goodsCategoryMapper.selectById(categoryId);
            if (ObjectUtils.isEmpty(goodsCategory)) {
                log.error("失败：【getCategoryById】根据ID获取商品分类信息失败 => categoryId:{}", categoryId);
                throw new BaseException(ResultCode.ERROR);
            }
            GoodsCategoryVO goodsCategoryVO = new GoodsCategoryVO();
            BeanUtils.copyProperties(goodsCategory, goodsCategoryVO);
            log.info("成功：【getCategoryById】根据ID获取商品分类信息成功 => categoryId:{}", categoryId);
            return goodsCategoryVO;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("失败：【getCategoryById】根据ID获取商品分类信息失败 => categoryId:{}", categoryId);
            throw new BaseException(ResultCode.ERROR.getStatus(), "根据ID获取商品分类信息失败");
        }
    }
}
