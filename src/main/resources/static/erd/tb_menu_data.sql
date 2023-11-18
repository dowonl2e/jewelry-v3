INSERT INTO tb_menu VALUES ('catalog', '카다로그', null, 1, '00', 'Y', 'admin', NOW(), null, null, 1);
INSERT INTO tb_menu VALUES ('customer', '고객관리', null, 2, '00', 'Y', 'admin', NOW(), null, null, 1);
INSERT INTO tb_menu VALUES ('order', '주문관리', null, 3, '00', 'Y', 'admin', NOW(), null, null, 1);
INSERT INTO tb_menu values ('order/schedule', '주문예정', '/order/schedule/list', 1, 'order', 'Y', 'admin', NOW(), NULL, NULL, 2);
INSERT INTO tb_menu values ('order/stocked', '입고예정', '/order/stocked/list', 2, 'order', 'Y', 'admin', NOW(), NULL, NULL, 2);
        
INSERT INTO tb_menu VALUES ('repair', '수리관리', null, 4, '00', 'Y', 'admin', NOW(), null, null, 1);
INSERT INTO tb_menu VALUES ('stock', '재고관리', null, 5, '00', 'Y', 'admin', NOW(), null, null, 1);
INSERT INTO tb_menu VALUES ('sale', '판매관리', null, 6, '00', 'Y', 'admin', NOW(), null, null, 1);
INSERT INTO tb_menu VALUES ('vender', '거래처', null, 7, '00', 'Y', 'admin', NOW(), null, null, 1);
INSERT INTO tb_menu VALUES ('cash', '금/현금', null, 8, '00', 'Y', 'admin', NOW(), null, null, 1);
INSERT INTO tb_menu VALUES ('code', '코드관리', null, 9, '00', 'Y', 'admin', NOW(), null, null, 1);