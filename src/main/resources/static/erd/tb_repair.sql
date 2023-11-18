create table tb_repair (
	repair_no bigint not null auto_increment comment 'PK',
    repair_nm varchar(90) not null comment '수리 제품명',
    repair_req_dt datetime comment '수리요청일',
    repair_res_dt datetime comment '수리종료일',
    repair_desc varchar(3000) not null comment '비고',
    del_yn varchar(4) default 'N' comment '삭제 여부',
	inpt_id varchar(30) not null comment '등록자',
	inpt_dt datetime default current_timestamp() comment '등록일',
	updt_id varchar(30) comment '수정자',
	updt_dt datetime comment '수정일',
    primary key (repair_no)
)
comment '수리관리'
default charset=utf8mb4
engine = InnoDB;


alter table tb_repair add customer_no bigint comment '고객번호';
alter table tb_repair add customer_nm varchar(80) comment '고객명';
alter table tb_repair add customer_cel varchar(90) comment '연락처 ';

alter table tb_repair add index idx1_tb_repair(customer_no);

