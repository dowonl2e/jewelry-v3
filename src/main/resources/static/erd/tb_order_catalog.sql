create table tb_order_catalog (
	order_cat_no bigint not null auto_increment comment '주문카다로그번호(PK)',
    order_no bigint default 0 not null comment '주문번호(FK)',
    catalog_no bigint default 0 not null comment '카다로그번호',
    vender_no bigint default 0 not null comment '제조사번호',
    serial_id varchar(60) not null comment '시리얼ID',
    mateial_cd varchar(20) comment '재질코드',
    color_cd varchar(20) comment '색상코드',
    quantity int not null default 1 comment '수량',
    main_stone_type varchar(30) comment '스톤(메인)',
    sub_stone_type varchar(30) comment '스톤(보조)',
    size varchar(60) comment '사이즈',
    order_desc varchar(2000) comment '주문설명',
	inpt_id varchar(30) not null comment '등록자',
	inpt_dt datetime default current_timestamp() comment '등록일',
	updt_id varchar(30) comment '수정자',
	updt_dt datetime comment '수정일',
    primary key (order_cat_no)
)
comment '주문카다로그관리'
default charset=utf8mb4
engine = InnoDB;
ALTER TABLE tb_order_catalog ADD INDEX idx1_tb_order_catalog(order_no);
