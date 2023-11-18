create table tb_catalog_stone (
	stone_no bigint not null auto_increment comment '스톤번호(PK)',
    catalog_no bigint not null comment '카탈로그번호(FK)',
    stone_type_cd varchar(20) comment '스톤구분',
    stone_nm varchar(200) comment '스톤명',
    bead_cnt int not null default 0 comment '알 개수',
    purchase_price varchar(20) not null default 0 comment '구매단가',
    stone_desc varchar(2000) comment '스톤설명',
	inpt_id varchar(30) not null comment '등록자',
	inpt_dt datetime default current_timestamp() comment '등록일',
	updt_id varchar(30) comment '수정자',
	updt_dt datetime comment '수정일',
    primary key (stone_no)
)
comment '스톤정보'
default charset=utf8mb4
engine = InnoDB;
ALTER TABLE tb_catalog_stone ADD INDEX idx1_tb_catalog_stone(catalog_no);