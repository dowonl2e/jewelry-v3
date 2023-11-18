
CREATE TABLE tb_vender (
  vender_no bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
  vender_nm varchar(120) not null comment '거래처명',
  business_nm varchar(120) comment '사업자명',
  agent_cel varchar(50) comment '대표자 연락처',
  vat_cd varchar(20) comment '부가세적용률',
  melt_cd varchar(20) comment '매입해리',
  vender_fax varchar(50) comment '매입처 팩스번호',
  vender_cel1 varchar(50) comment '전화번호1',
  vender_cel2 varchar(50) comment '전화번호2',
  manager_nm varchar(120) comment '담당자 이름',
  manager_cel varchar(50) comment '담당자 전화번호',
  manager_email varchar(80) comment '이메일',
  etc varchar(2000) comment '비고', 
  commerce varchar(300) comment '통상처',
  del_yn varchar(4) default 'N' comment '삭제여부',
  inpt_id varchar(30) not null comment '등록자',
  inpt_dt datetime default current_timestamp() comment '등록자',
  updt_id varchar(30) comment '수정자',
  updt_dt datetime comment '등록자'
  
)
comment '거래처'
default charset=utf8mb4
engine = InnoDB;