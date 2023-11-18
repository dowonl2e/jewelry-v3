create table tb_order (
	order_no bigint not null auto_increment comment '주문번호(PK)',
	order_type varchar(10) not null comment '주문구분(CUSTOMER:고객주문 / READMADE:기성주문)',
    store_cd varchar(20) comment '매장코드',
    receipt_dt datetime default CURRENT_TIMESTAMP() comment '접수일',
    expected_ord_dt datetime comment '주문예상일',
    customer_no BIGINT default 0 comment '고객번호(FK)',
    customer_nm varchar(80) comment '고객명',
    customer_cel varchar(50) comment '연락처',
    order_step varchar(4) default 'A' comment '주문단계',
    serial_id varchar(60) comment '시리얼ID',
    catalog_no bigint comment '카다로그번호',
    model_id varchar(100) comment '모델번호',
    vender_no bigint comment '제조사번호',
    vender_nm varchar(120) comment '제조사명',
    material_cd varchar(20) comment '재질코드',
    color_cd varchar(20) comment '색상코드',
    quantity int default 1 comment '수량',
    main_stone_type varchar(60) comment '스톤(메인)',
    sub_stone_type varchar(60) comment '스톤(보조)',
    size varchar(60) comment '사이즈',
    order_desc varchar(2000) comment '주문설명',
    del_yn varchar(4) default 'N' not null comment '삭제여부',
	inpt_id varchar(30) not null comment '등록자',
	inpt_dt datetime default current_timestamp() comment '등록일',
	updt_id varchar(30) comment '수정자',
	updt_dt datetime comment '수정일',
    primary key (order_no)
)
comment '주문관리'
default charset=utf8mb4
engine = InnoDB;
ALTER TABLE tb_order ADD INDEX idx1_tb_order(customer_no);

