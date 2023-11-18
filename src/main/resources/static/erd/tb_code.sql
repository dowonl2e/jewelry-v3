CREATE TABLE tb_code (
  cd_id varchar(20) not null comment '코드ID',
  cd_nm varchar(100) not null comment '코드명',
  cd_ord int not null default 1 comment '순번',
  up_cd_id varchar(20) not null comment '상위코드ID',
  cd_depth int not null default 1 comment '코드뎁스',
  use_yn varchar(4) not null default 'Y' comment '사용여부(Y,N)',
  inpt_id varchar(30) not null comment '등록자',
  inpt_dt datetime default current_timestamp() comment '등록일',
  updt_id varchar(30) comment '수정자',
  updt_dt datetime comment '수정일',
  primary key (cd_id)
)
comment '코드'
default charset=utf8mb4
engine = InnoDB;
ALTER TABLE tb_code ADD INDEX idx1_tb_code(up_cd_id);

