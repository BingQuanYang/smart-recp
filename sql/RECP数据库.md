# 商品

## 商品表

```sql
create table recp_goods
(
    goods_id          int          not null primary key auto_increment comment '商品ID',
    category_id       int          not null comment '分类ID',
    category_name     varchar(64)  not null comment '分类名称',
    seller_id         int          not null comment '卖家ID',
    goods_name        varchar(256) not null comment '商品名称',
    sub_title         varchar(256) not null comment '副标题',
    description       varchar(256) comment '商品描述',
    tags              varchar(256) comment '商品标签',
    detail            text comment '商品详情',
    sale_total        int          not null default 0 comment '销售总量',
    generated_address varchar(256) comment '产地',
    goods_unit        varchar(32) comment '计算单位(斤、公斤、个)',
    status            int(2)       not null default 0 comment '状态: 0->未上架;1->上架中;2->审核中;3->审核未通过;4->冻结',
    new_status        int(1)       not null default 0 comment '新品状态: 0->不是新品;1->新品',
    recommend_status  int(1)       not null default 0 comment '推荐状态: 0->不推荐;1->推荐',
    creator           int          not null comment '创建者',
    create_time       datetime     not null default now() comment '创建时间',
    modified_by       int comment '创建者',
    modified_time     datetime comment '创建时间',
    is_delete         int(1)       not null default 0 comment '软删除: 0->未删除,1->已删除'
) comment '商品表';
```



## 商品资源表

```sql
create table recp_goods_resource
(
    resource_id int          not null primary key auto_increment comment '资源ID',
    goods_id          int          not null comment '商品ID',
    type              int(1)       not null default 1 comment '类型: 1->图片;2->视频',
    link              varchar(256) not null comment '资源地址',
    sort              int comment '排序',
    is_master         int(1)       not null default 0 comment '是否主图: 0->否;1->是'
) comment '商品资源表';
```



## 商品规格表

```sql
create table recp_goods_spec
(
    spec_id int          not null primary key auto_increment comment '规格ID',
    goods_id      int          not null comment '商品ID',
    spec_name     varchar(128) not null comment '规格名称',
    image         varchar(256) comment '图片',
    stock         int          not null default 0 comment '库存',
    sale          int          not null default 0 comment '销量',
    weight        int comment '重量',
    status        int(1)       not null default 1 comment '状态:0->禁用;1->启用'
) comment '商品规格表';
```



## 商品规格价格表

```sql
create table recp_goods_spec_price
(
    price_id int            not null primary key auto_increment comment '价格ID',
    spec_id  int            not null comment '规格ID',
    price    decimal(10, 2) not null comment '价格',
    type     int(1)         not null comment '类型:1->零售,2->批发,3->VIP',
    min      int comment '区间最小值',
    max      int comment '区间最大值'
) comment '商品规格价格表';
```



## 商品分类表

```sql
create table recp_goods_category
(
    category_id   int auto_increment primary key comment '主键',
    parent_id     int comment '上级分类的编号',
    category_name varchar(64) comment '分类名称',
    level         int(1) comment '分类级别：1->1级；2->2级',
    icon          varchar(255) comment '图标',
    image         varchar(1024) comment '图片',
    status        int(1)            default 1 comment '显示状态：0->不显示；1->显示',
    sort          int               default 1 comment '排序',
    description   text comment '描述',
    creator       int comment '创建者',
    create_time   datetime not null default now() comment '创建时间',
    modified_by   int comment '创建者',
    modified_time datetime comment '创建时间'
) comment '商品分类表';
```





# 用户

## 用户表

```sql
create table recp_user
(
    user_id             int          not null primary key auto_increment comment '用户ID',
    account             varchar(128) not null unique comment '账号',
    password            varchar(128) comment '密码',
    real_name           varchar(32) comment '真实姓名',
    identification_card varchar(32) comment '身份证',
    mobile              varchar(32) comment '手机号',
    type                int          not null comment '类型：1->买家,2->卖家,0->平台管理员',
    cert_status         int comment '认证状态：0->未认证,1->已认证,2->认证中',
    last_login_time     datetime comment '最后一次登录时间',
    creator             int comment '创建者',
    create_time         datetime     not null default now() comment '创建时间',
    modified_by         int comment '修改者',
    modified_time       datetime comment '修改时间',
    is_delete           int(1)       not null default 0 comment '软删除: 0->未删除,1->已删除'
) comment '用户表';
```



## 卖家表

```sql
create table recp_seller
(
    seller_id      int          not null primary key comment '卖家ID,来源于userId',
    seller_account varchar(128) not null unique comment '卖家账号,来源于account',
    seller_name    varchar(128) not null unique comment '店铺名称',
    seller_alias   varchar(64) comment '店铺别名,简称',
    seller_logo    varchar(256) comment '店铺Logo',
    seller_licence varchar(256) comment '营业执照',
    seller_rank    int                   default 0 comment '店铺等级',
    seller_grade   decimal(4, 1) comment '店铺评分',
    seller_address varchar(256) comment '店铺地址',
    seller_keyword varchar(256) comment '关键字',
    seller_tel     varchar(20) comment '店铺联系方式',
    balance_money  decimal(12, 2)        default '0.00' comment '余额',
    remark         varchar(255) comment '备注',
    seller_status  int(2)                default 1 comment '状态：1->正常,0->冻结',
    create_time    datetime     not null default now() comment '创建时间',
    modified_by    int comment '修改者',
    modified_time  datetime comment '修改时间',
    is_delete      int(1)       not null default 0 comment '软删除: 0->未删除,1->已删除'
) comment '卖家表';
```



## 买家表

```sql
create table recp_buyer
(
    buyer_id      int          not null primary key comment '卖家ID,来源于userId',
    buyer_account varchar(128) not null unique comment '卖家账号,来源于account',
    nickname      varchar(128) comment '昵称',
    email         varchar(125) comment '邮箱',
    gender        varchar(4) comment '性别',
    introduction  varchar(256) comment '个人简介',
    birthday      varchar(64) comment '生日',
    head_portrait varchar(256) comment '头像',
    buyer_status  int(2)                default 1 comment '状态:1->正常，0->禁用',
    balance_money decimal(12, 2)        default '0.00' comment '余额',
    buyer_level   int(4)                default 0 comment '等级',
    create_time   datetime     not null default now() comment '创建时间',
    modified_by   int comment '修改者',
    modified_time datetime comment '修改时间',
    is_delete     int(1)       not null default 0 comment '软删除: 0->未删除,1->已删除'
) comment '买家表';
```

