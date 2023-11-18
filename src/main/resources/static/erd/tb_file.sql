CREATE TABLE tb_file (
  file_no BIGINT NOT NULL AUTO_INCREMENT COMMENT '파일 번호 (PK)',
  ref_no BIGINT NOT NULL COMMENT '참조FK',
  ref_info varchar(30) NOT NULL COMMENT '참조정보',
  file_path varchar(100) NOT NULL COMMENT '파일경로',
  origin_nm varchar(400) NOT NULL COMMENT '원본파일명',
  file_nm varchar(200) NOT NULL COMMENT '파일명', 
  file_ord int NOT NULL DEFAULT 1 COMMENT '순번', 
  file_ext varchar(10) NOT NULL COMMENT '파일 확장자',
  file_size int NOT NULL DEFAULT 0 COMMENT '파일 크기',
  version_id varchar(100) COMMENT '버전',
  del_yn varchar(4) NOT NULL DEFAULT 'N' COMMENT '삭제 여부',
  inpt_id varchar(30) NOT NULL COMMENT '등록자',
  inpt_dt datetime DEFAULT CURRENT_TIMESTAMP() COMMENT '등록일',
  updt_id varchar(30) COMMENT '수정자',
  updt_dt datetime COMMENT '수정일',
  PRIMARY KEY (file_no)
)
comment '첨부파일'
default charset=utf8mb4
engine = InnoDB;
ALTER TABLE tb_file ADD INDEX idx1_tb_file(ref_no, ref_info);
