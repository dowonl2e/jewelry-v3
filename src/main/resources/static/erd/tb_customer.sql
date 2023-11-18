CREATE TABLE tb_customer (
  customer_no BIGINT NOT NULL AUTO_INCREMENT COMMENT '고객번호(PK)',
  store_cd varchar(30) COMMENT '매장코드',
  contract_cd varchar(30) COMMENT '계약코드',
  zipcode varchar(30) NOT NULL COMMENT '우편번호',
  address1 varchar(400) COMMENT '주소1',
  address2 varchar(400) NOT NULL COMMENT '주소2',
  etc varchar(2000) NOT NULL COMMENT '비고',
  contractor_nm varchar(80) NOT NULL COMMENT '이름',
  contractor_gen varchar(30) COMMENT '성별',
  contractor_cel varchar(90) COMMENT '연락처',
  contractor_birth varchar(60) COMMENT '생일', 
  contractor_lunar varchar(10) NOT NULL DEFAULT 'N' COMMENT '음력여부', 
  contractor_email varchar(30) COMMENT '이메일',
  del_yn varchar(4) NOT NULL DEFAULT 'N' COMMENT '삭제 여부',
  reg_dt datetime NOT NULL DEFAULT CURRENT_TIMESTAMP() COMMENT '등록일(수동)',
  inpt_id varchar(30) NOT NULL COMMENT '등록자',
  inpt_dt datetime DEFAULT CURRENT_TIMESTAMP() COMMENT '등록일',
  updt_id varchar(30) COMMENT '수정자',
  updt_dt datetime COMMENT '수정일',
  PRIMARY KEY (customer_no)
)
comment '고객'
default charset=utf8mb4
engine = InnoDB;
