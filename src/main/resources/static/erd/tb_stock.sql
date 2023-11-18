create table tb_stock (
	stock_no bigint not null auto_increment comment 'PK',
    reg_dt datetime comment '등록일(수동)',
    stock_type_cd varchar(10) comment '재고 구분',
    store_cd varchar(10) comment '입고매장',
    real_pch_gold_price double comment '실제 구매 순금 시세/g',
    catalog_no bigint not null comment '카다로그번호(FK)',
    model_id varchar(100) comment '모델번호',
    vender_no bigint comment '제조사번호',
    vender_nm varchar(120) comment '제조사명',
    material_cd varchar(20) comment '재질코드',
    color_cd varchar(20) comment '색상코드',
    main_stone_type varchar(60) comment '스톤(메인)',
    sub_stone_type varchar(60) comment '스톤(보조)',
    size varchar(60) comment '사이즈',
    stock_desc varchar(2000) comment '기타설명',
    quantity int default 1 comment '수량',
    per_weight_gram double comment '개당중량(g)_중량',
    per_weight_gold double comment '개당중량(g)_순금',
    per_price_basic double comment '개당구매가(공임)_기본',
    per_price_add int comment '개당구매가(공임)_추가',
    per_price_main int comment '개당구매가(공임)_메인',
    per_price_sub int comment '개당구매가(공임)_보조',
    per_price_gold_real int comment '개당구매가(금값포함)_실질',
    per_price_gold_stdd int comment '개당구매가(금값포함)_기준',
    buying_cnt int comment '매수',
    consumer_price int comment '소비자가(Tag가)',
    origin_type varchar(20) default 'STOCK',
    del_yn varchar(4) default 'N' not null comment '삭제여부',
	inpt_id varchar(30) not null comment '등록자',
	inpt_dt datetime default current_timestamp() comment '등록일',
	updt_id varchar(30) comment '수정자',
	updt_dt datetime comment '수정일',
    primary key (stock_no)
)
comment '재고관리'
default charset=utf8mb4
engine = InnoDB;
ALTER TABLE tb_stock ADD INDEX idx1_tb_stock(catalog_no);
ALTER TABLE tb_stock ADD INDEX idx2_tb_stock(vender_no);

