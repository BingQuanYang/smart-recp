package com.smart.recp.service.goods.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.enums.ResultCode;
import com.smart.recp.common.core.result.PageResult;
import com.smart.recp.common.core.util.BeanCopyUtils;
import com.smart.recp.service.goods.dto.GoodsDTO;
import com.smart.recp.service.goods.dto.GoodsResourceDTO;
import com.smart.recp.service.goods.dto.GoodsSpecDTO;
import com.smart.recp.service.goods.dto.GoodsSpecPriceDTO;
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
            log.info("???????????????????????????{}", goodsVOPageResult);
            return goodsVOPageResult;
        } catch (Exception e) {
            log.error("???????????????????????????page???{}, size???{},goodsDTO???{},error:{}", page, size, goodsDTO, e.getMessage());
            e.printStackTrace();
            throw new BaseException(ResultCode.ERROR);
        }
    }

    /**
     * ????????????ID????????????????????????
     * ??????
     *
     * @param goodsId ?????????ID
     * @return
     */
    @Override
    public GoodsVO getCascadeByGoodsId(Integer goodsId) throws BaseException {
        try {
            Goods goods = goodsMapper.getCascadeByGoodsId(goodsId);
            if (goods == null || goods.getIsDelete().equals(1)) {
                log.error("????????????ID????????????????????????");
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
            log.error("????????????ID???????????????????????????????????????goodsId???{}", goodsId);
            throw new BaseException(ResultCode.ERROR.getStatus(), "????????????ID????????????????????????");
        }
    }

    /**
     * ??????????????????
     *
     * @param goodsDTO
     * @return
     */
    @Override
    @Transactional
    public Boolean add(GoodsDTO goodsDTO) throws BaseException {
        try {

            /**
             * ????????????
             */
            Goods goods = new Goods();

            BeanUtils.copyProperties(goodsDTO, goods);

            //TODO
            goods.setCategoryName("??????");
            goods.setCreator(1);

            int insert = goodsMapper.insert(goods);
            if (insert < 1) {
                log.error("????????????????????? => goods:{}", goods);
                throw new BaseException(ResultCode.ERROR);
            }

            /**
             * ???????????????
             */
            //????????????????????????
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
                    log.error("????????????????????? => goodsResource:{}", goodsResource);
                    throw new BaseException(ResultCode.ERROR);
                }
            }

            /**
             * ??????????????????
             */
            for (GoodsSpecDTO goodsSpecDTO : goodsDTO.getGoodsSpecDTOList()) {
                GoodsSpec goodsSpec = new GoodsSpec();
                BeanUtils.copyProperties(goodsSpecDTO, goodsSpec);
                goodsSpec.setGoodsId(goods.getGoodsId());
                insert = goodsSpecMapper.insert(goodsSpec);
                if (insert < 1) {
                    log.error("????????????????????? => goodsSpec:{}", goodsSpec);
                    throw new BaseException(ResultCode.ERROR);
                }

                /**
                 * ??????????????????
                 */
                for (GoodsSpecPriceDTO goodsSpecPriceDTO : goodsSpecDTO.getGoodsSpecPriceDTOList()) {
                    GoodsSpecPrice goodsSpecPrice = new GoodsSpecPrice();
                    BeanUtils.copyProperties(goodsSpecPriceDTO, goodsSpecPrice);
                    goodsSpecPrice.setSpecId(goodsSpec.getSpecId());
                    insert = goodsSpecPriceMapper.insert(goodsSpecPrice);
                    if (insert < 1) {
                        log.error("????????????????????? => goodsSpecPrice:{}", goodsSpecPrice);
                        throw new BaseException(ResultCode.ERROR);
                    }
                }
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("?????????????????? => goodsDTO:{}", goodsDTO);
            throw new BaseException(ResultCode.ERROR);
        }
    }

    /**
     * ??????????????????
     *
     * @param goodsDTO
     * @return
     */
    @Override
    @Transactional
    public Boolean update(GoodsDTO goodsDTO) throws BaseException {
        try {

            //TODO  ??????????????????

            /**
             * ????????????
             */
            Goods goods = new Goods();
            BeanUtils.copyProperties(goodsDTO, goods);
            int update = goodsMapper.updateById(goods);
            if (update < 1) {
                log.error("????????????????????? => goods:{}", goods);
                throw new BaseException(ResultCode.ERROR);
            }

            GoodsVO goodsVO = getCascadeByGoodsId(goods.getGoodsId());

            /**
             * ????????????
             */
            //?????????????????????????????????????????????  --  ?????????????????????????????????????????????
            List<GoodsResourceDTO> goodsResourceDTOList = goodsDTO.getGoodsResourceDTOList();
            List<GoodsResourceDTO> updateGoodsResourceList = goodsResourceDTOList.stream().filter(item -> ObjectUtils.isNotEmpty(item.getResourceId())).collect(Collectors.toList());
            Stream<GoodsResourceDTO> addGoodsResourceList = goodsResourceDTOList.stream().filter(item -> ObjectUtils.isEmpty(item.getResourceId()));
            List<GoodsResourceVO> goodsResourceVOList = goodsVO.getGoodsResourceVOList();
            //??????goodsResourceVOList - updateGoodsResourceList
            Predicate<GoodsResourceVO> goodsResourceVOPredicate = vo -> updateGoodsResourceList.stream().noneMatch(dto -> vo.getResourceId().equals(dto.getResourceId()));
            List<Integer> deleteGoodsResourceIdList = goodsResourceVOList.stream().filter(goodsResourceVOPredicate).map(item -> item.getResourceId()).collect(Collectors.toList());

            //??????????????????
            if (ObjectUtils.isNotEmpty(deleteGoodsResourceIdList) && deleteGoodsResourceIdList.size() > 0) {
                int deleteGoodsResourceBatchIds = goodsResourceMapper.deleteBatchIds(deleteGoodsResourceIdList);
                if (deleteGoodsResourceBatchIds < 1) {
                    log.error("?????????????????????????????????????????? => deleteGoodsResourceBatchIds:{}", deleteGoodsResourceBatchIds);
                    throw new BaseException(ResultCode.ERROR);
                }
            }

            for (GoodsResourceDTO goodsResourceDTO : goodsResourceDTOList) {
                GoodsResource goodsResource = new GoodsResource();
                BeanUtils.copyProperties(goodsResourceDTO, goodsResource);
                int result = -1;
                if (ObjectUtils.isNotEmpty(goodsResource.getResourceId())) {
                    //??????????????????
                    result = goodsResourceMapper.updateById(goodsResource);
                    if (result < 1) {
                        log.error("?????????????????????????????????????????? => goodsResource:{}", goodsResource);
                        throw new BaseException(ResultCode.ERROR);
                    }
                } else {
                    //??????????????????
                    goodsResource.setGoodsId(goods.getGoodsId());
                    result = goodsResourceMapper.insert(goodsResource);
                    if (result < 1) {
                        log.error("?????????????????????????????????????????? => goodsResource:{}", goodsResource);
                        throw new BaseException(ResultCode.ERROR);
                    }
                }
            }


            /**
             * ????????????
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
            //???????????????????????????????????????????????????
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

            //??????????????????
            if (ObjectUtils.isNotEmpty(deleteGoodsSpecPriceIdList) && deleteGoodsSpecPriceIdList.size() > 0) {
                int deleteGoodsSpecPriceBatchIds = goodsSpecPriceMapper.deleteBatchIds(deleteGoodsSpecPriceIdList);
                if (deleteGoodsSpecPriceBatchIds < 1) {
                    log.error("???????????????????????????????????????????????? => deleteGoodsSpecPriceBatchIds:{}", deleteGoodsSpecPriceBatchIds);
                    throw new BaseException(ResultCode.ERROR);
                }
            }
            //????????????
            if (ObjectUtils.isNotEmpty(deleteGoodsSpecIdList) && deleteGoodsSpecIdList.size() > 0) {
                int deleteGoodsSpecBatchIds = goodsSpecMapper.deleteBatchIds(deleteGoodsSpecIdList);
                if (deleteGoodsSpecBatchIds < 1) {
                    log.error("?????????????????????????????????????????? => deleteGoodsSpecBatchIds:{}", deleteGoodsSpecBatchIds);
                    throw new BaseException(ResultCode.ERROR);
                }
            }


            for (GoodsSpecDTO goodsSpecDTO : goodsSpecDTOList) {
                GoodsSpec goodsSpec = new GoodsSpec();
                BeanUtils.copyProperties(goodsSpecDTO, goodsSpec);
                int specResult = -1;
                if (ObjectUtils.isNotEmpty(goodsSpecDTO.getSpecId())) {
                    //????????????
                    specResult = goodsSpecMapper.updateById(goodsSpec);
                    if (specResult < 1) {
                        log.error("???????????????????????????????????????????????? => goodsSpec:{}", goodsSpec);
                        throw new BaseException(ResultCode.ERROR);
                    }
                } else {
                    //????????????
                    goodsSpec.setGoodsId(goods.getGoodsId());
                    specResult = goodsSpecMapper.insert(goodsSpec);
                    if (specResult < 1) {
                        log.error("???????????????????????????????????????????????? => goodsSpec:{}", goodsSpec);
                        throw new BaseException(ResultCode.ERROR);
                    }
                }


                /**
                 * ????????????
                 */
                List<GoodsSpecPriceDTO> goodsSpecPriceDTOList = goodsSpecDTO.getGoodsSpecPriceDTOList();
                for (GoodsSpecPriceDTO goodsSpecPriceDTO : goodsSpecPriceDTOList) {
                    GoodsSpecPrice goodsSpecPrice = new GoodsSpecPrice();
                    BeanUtils.copyProperties(goodsSpecPriceDTO, goodsSpecPrice);
                    int priceResult = -1;
                    if (ObjectUtils.isNotEmpty(goodsSpecPriceDTO.getPriceId())) {
                        //????????????????????????
                        priceResult = goodsSpecPriceMapper.updateById(goodsSpecPrice);
                        if (priceResult < 1) {
                            log.error("?????????????????????????????????????????????????????? => goodsSpecPrice:{}", goodsSpecPrice);
                            throw new BaseException(ResultCode.ERROR);
                        }
                    } else {
                        //????????????????????????
                        goodsSpecPrice.setSpecId(goodsSpec.getSpecId());
                        priceResult = goodsSpecPriceMapper.insert(goodsSpecPrice);
                        if (priceResult < 1) {
                            log.error("?????????????????????????????????????????????????????? => goodsSpecPrice:{}", goodsSpecPrice);
                            throw new BaseException(ResultCode.ERROR);
                        }
                    }

                }
            }

            //TODO  ??????????????????????????????

            log.info("???????????????????????????");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("???????????????????????? => goodsDTO:{}", goodsDTO);
            throw new BaseException(ResultCode.ERROR);
        }
    }

    /**
     * ??????????????????
     *
     * @param goodsIdList ??????ID??????
     * @return
     */
    @Override
    public Integer delete(List<Integer> goodsIdList) throws BaseException {
        try {
            Integer delete = goodsMapper.deleteByGoodsIdList(goodsIdList);
            if (delete < 1) {
                log.error("?????????????????? => goodsIdList:{}", goodsIdList);
                throw new BaseException(ResultCode.ERROR);
            }
            return delete;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("???????????????????????? => goodsIdList:{}", goodsIdList);
            throw new BaseException(ResultCode.ERROR);
        }
    }

    @Override
    public List<GoodsResourceVO> getResourceByGoodsIdListAndIsMaster(List<Integer> goodsIdList, int isMaster) throws BaseException {
        try {
            List<GoodsResource> resourceList = goodsResourceMapper.getResourceByGoodsIdListAndIsMaster(goodsIdList, isMaster);
            if (goodsIdList == null || goodsIdList.size() < 1) {
                log.error("????????????Id???????????????????????????????????????????????? => goodsIdList:{},isMaster:{}", goodsIdList, isMaster);
                throw new BaseException(ResultCode.ERROR);
            }
            List<GoodsResourceVO> resourceVOList = BeanCopyUtils.copyListProperties(resourceList, GoodsResourceVO::new);

            log.info("?????????????????????Id?????????????????????????????????????????????????????????{}", resourceVOList);
            return resourceVOList;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("?????????????????????Id?????????????????????????????????????????????????????? => goodsIdList:{},isMaster:{}", goodsIdList, isMaster);
            throw new BaseException(ResultCode.ERROR);
        }
    }


    /**
     * ????????????????????????
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
                log.error("???????????????????????????????????????");
                throw new BaseException(ResultCode.ERROR);
            }

            goodsCategoryList.forEach(item -> {
                GoodsCategoryVO goodsCategoryVO = new GoodsCategoryVO();
                BeanUtils.copyProperties(item, goodsCategoryVO);
                List<GoodsCategoryVO> voList = BeanCopyUtils.copyListProperties(item.getChildren(), GoodsCategoryVO::new);
                goodsCategoryVO.setChildren(voList);
                goodsCategoryVOList.add(goodsCategoryVO);
            });
            log.info("?????????????????????????????????,{}", goodsCategoryVOList);
            return goodsCategoryVOList;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("???????????????????????????????????????");
            throw new BaseException(ResultCode.ERROR.getStatus(), "???????????????????????????????????????");
        }
    }

    @Override
    public Boolean updateById(GoodsDTO goodsDTO) throws BaseException {
        try {
            Goods goods = new Goods();
            BeanUtils.copyProperties(goodsDTO, goods);
            int update = goodsMapper.updateById(goods);
            if (update < 1) {
                throw new BaseException(ResultCode.ERROR.getStatus(), "???????????????ID????????????????????????");
            }
            log.info("????????????updateById?????????ID???????????????????????????{}", goodsDTO);
            return true;
        } catch (Exception e) {
            log.error("????????????updateById?????????ID???????????????????????????{}", goodsDTO);
            e.printStackTrace();
            throw new BaseException(ResultCode.ERROR.getStatus(), "???????????????ID????????????????????????");
        }
    }
}
