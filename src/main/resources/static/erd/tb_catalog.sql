create table tb_catalog (
	catalog_no BIGINT NOT NULL AUTO_INCREMENT comment 'PK',
    vender_no BIGINT comment '제조사FK',
    model_id varchar(100) not null comment '모델번호',
    model_nm varchar(200) comment '품명',
    stdd_material_cd varchar(20) comment '표준재질',
    stdd_weight_cd varchar(20) comment '표준중량',
    stdd_color_cd varchar(20) comment '표준색상',
    stdd_size varchar(100) comment '표준사이즈',
    odr_notice varchar(2000) comment '주문시 유의사항',
    reg_dt datetime not null default current_timestamp() comment '등록일(수동)',
	del_yn varchar(4) not null default 'N' comment '삭제여부',
	inpt_id varchar(30) not null comment '등록자',
	inpt_dt datetime default current_timestamp() comment '등록일',
	updt_id varchar(30) comment '수정자',
	updt_dt datetime comment '수정일',
    primary key (catalog_no)
)
comment '카다로그'
default charset=utf8mb4
engine = InnoDB;
ALTER TABLE tb_catalog ADD INDEX idx1_tb_catalog(vender_id);
