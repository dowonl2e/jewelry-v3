CREATE TABLE tb_menu (
  menu_id varchar(20) not null comment '메뉴ID',
  menu_nm varchar(100) not null comment '메뉴명',
  menu_link varchar(200) comment '메뉴링크',
  menu_ord int not null default 1 comment '순번',
  up_menu_id varchar(20) not null default '00' comment '상위메뉴ID',
  use_yn varchar(4) not null default 'N' comment '사용여부(Y,N)',
  inpt_id varchar(30) not null comment '등록자',
  inpt_dt datetime default current_timestamp() comment '등록일',
  updt_id varchar(30) comment '수정자',
  updt_dt datetime comment '수정일',
  primary key (menu_id)
)
comment '메뉴'
default charset=utf8mb4
engine = InnoDB;
ALTER TABLE tb_menu ADD INDEX idx1_tb_menu(up_menu_id);

