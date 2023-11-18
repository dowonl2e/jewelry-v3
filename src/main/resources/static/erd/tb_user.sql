CREATE TABLE tb_user (
  user_id varchar(30) not null comment 'ID',
  user_pwd varchar(500) not null comment '비밀번호',
  user_name varchar(100) not null comment '이름',
  user_role varchar(10) not null comment '권한',
  email varchar(30) comment '이메일',
  celnum varchar(13) comment '핸드폰번호',
  use_yn varchar(1) not null default 'Y' comment '사용여부',
  inpt_id varchar(30) not null comment '등록자',
  inpt_dt datetime default CURRENT_TIMESTAMP() comment '등록일',
  updt_id varchar(30) comment '수정자',
  updt_dt datetime comment '수정일',
  primary key (user_id)
)
comment '사용자'
default charset=utf8mb4
engine = InnoDB;