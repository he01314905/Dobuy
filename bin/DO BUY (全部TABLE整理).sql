CREATE DATABASE IF NOT EXISTS dobuytest;
USE dobuytest;

CREATE TABLE counter (
    counterNo INT NOT NULL AUTO_INCREMENT, -- 主鍵自增編號
    counterAccount VARCHAR(20) NOT NULL,   -- 櫃位帳號
    counterName VARCHAR(45) NOT NULL,      -- 櫃位管理者姓名
    counterPassword VARCHAR(20) NOT NULL,  -- 櫃位密碼
    counterAddress VARCHAR(255) NOT NULL,  -- 櫃位地址
    counterPhone VARCHAR(10) NOT NULL,     -- 櫃位電話
    counterUid VARCHAR(10) NOT NULL,       -- 管理者身分證字號
    counterEmail VARCHAR(100) NOT NULL,    -- 櫃位電子信箱
    counterUbn VARCHAR(255) NOT NULL,      -- 櫃位統一編號
    counterCName VARCHAR(255) NOT NULL,    -- 櫃位名稱
    counterTypeNo INT NOT NULL,            -- 櫃位類別編號（外來鍵）
    counterInform VARCHAR(255),            -- 櫃位資訊介紹
    counterPic LONGBLOB,                   -- 櫃位商標圖片
    counterStatus TINYINT NOT NULL DEFAULT 1 CHECK (counterStatus IN (0, 1, 2)),        -- 櫃位狀態 (0: 正常, 1: 暫時停權, 2: 永久停權)
    
    PRIMARY KEY (counterNo)                -- 設定主鍵
)
AUTO_INCREMENT = 1;

INSERT INTO counter (counterAccount, counterName, counterPassword, counterAddress, counterPhone, counterUid, counterEmail, counterUbn, counterCName, counterTypeNo, counterInform, counterStatus) VALUES
('account1', 'John Doe', '12345', '台北', '0912345678', 'A123456789', 'john@example.com', '12345678', '老子有錢', 1, '有錢就來花', 0),
('account2', 'Jane Smith', '12345', '新北', '0987654321', 'B987654321', 'jane@example.com', '87654321', '美美醫美', 2, 'This counter offers great products.', 0),
('account3', '3C手機店', '12345', '台北市中山區', '0987654321', 'B234567890', '3cshop@example.com', '87654321', '3C手機專賣', 3, '品質保證，售後服務', 0),
('account4', 'Alice Chen', '12345', '桃園', '0931234567', 'C123456789', 'alice@example.com', '13579246', '山陵家電', 3, 'Your go-to place for electronics.', 0),
('account5', 'Bob Wang', '12345', '新竹', '0923456789', 'D987654321', 'bob@example.com', '24681357', '優你庫', 4, 'Fashion and style at its best!', 0),
('account6', 'Carol Lin', '12345', '桃園', '0919876543', 'E123456789', 'carol@example.com', '12349876', '乃吉', 5, 'Fitness and sports gear available.', 0),
('account7', 'Ninten Do', '12345', '中壢', '0919875678', 'Q123456789', 'niten@example.com', '12349876', '任你玩', 6, '絕對不是那個N牌', 0),
('account8', 'Famil Lily', '12345', '中壢', '0905426543', 'J123456789', 'family@example.com', '13459876', '家家樂', 7, '清潔品、食品、等家用品的櫃位',0);


CREATE TABLE faq (
    faqNo INT NOT NULL ,   -- 常見問題編號
    ques VARCHAR(500) NOT NULL,           -- 常見問題
    ans VARCHAR(500) NOT NULL,            -- 問題解答
    counterNo INT NOT NULL,              -- 櫃位編號，外來鍵
    
    PRIMARY KEY (faqNo)               -- 設定主鍵
   
);
INSERT INTO faq VALUES
(101 , '1. 1+1=?' , '2' , 1),
(102 , '2. 2+2=?' , '4' , 1),
(201 , '1. 1+1=?' , '2' , 2),
(202 , '2. 2+2=?' , '4' , 2),
(301 , '1. 1+1=?' , '2' , 3),
(302 , '2. 2+2=?' , '4' , 3),
(303 , '3. 3+3=?' , '6' , 3),
(401 , '1. 1+1=?' , '2' , 4),
(402 , '2. 2+2=?' , '2' , 4);



CREATE TABLE counterType (
    counterTypeNo INT NOT NULL AUTO_INCREMENT, -- 櫃位類別編號，自增主鍵
    counterTName VARCHAR(100) NOT NULL,         -- 類別名稱
    
    PRIMARY KEY (counterTypeNo)                -- 設定主鍵
)
AUTO_INCREMENT = 1;
INSERT INTO counterType (counterTName) VALUES 
('精品'),
('藥妝'),
('家電'),
('服飾'),
('運動'),
('娛樂'),
('超市');


-- 柏諭
CREATE TABLE Manager (
    managerNo INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    managerName VARCHAR(20) NOT NULL,
    managerAccount VARCHAR(20) NOT NULL,
    managerPassword VARCHAR(20) NOT NULL,
    managerStatus TINYINT(1) DEFAULT 1 NOT NULL
) AUTO_INCREMENT = 1;

INSERT INTO Manager (managerName, managerAccount, managerPassword, managerStatus) 
VALUES
    ('王大明', 'admin1', 'pass123', 1),
    ('李小華', 'admin2', 'pass456', 1),
    ('陳美麗', 'admin3', 'pass789', 0),
    ('林志強', 'admin4', 'pass234', 1),
    ('張小英', 'admin5', 'pass567', 1),
    ('黃建國', 'admin6', 'pass890', 0),
    ('周玉芬', 'admin7', 'pass345', 1),
    ('鄭光明', 'admin8', 'pass678', 1),
    ('吳淑敏', 'admin9', 'pass901', 0),
    ('許英傑', 'admin10', 'pass1234', 1);
    
CREATE TABLE CounterOrder (
    counterOrderNo INT(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    counterNo INT(10) NOT NULL,                    -- FK
    memNo INT(10) NOT NULL,                        -- FK
    orderTotalPriceBefore INT(6) NOT NULL,
    orderTotalPriceAfter INT(6) NOT NULL,
    receiverName VARCHAR(10) NOT NULL,
    receiverAdr VARCHAR(100),
    receiverPhone VARCHAR(10) NOT NULL,
    sellerSatisfaction TINYINT(1) DEFAULT 5,
    sellerCommentContext VARCHAR(500),
    sellerCommentDate Timestamp ,
    orderTime TIMEstamp NOT NULL,
    disNo INT(10),
    orderStatus TINYINT(1) DEFAULT 0 NOT NULL

) AUTO_INCREMENT = 1;

CREATE TABLE CounterOrderDetail (
    counterOrderDetailNo INT(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    counterOrderNo INT(10) NOT NULL,    -- FK
    goodsNo INT(10) NOT NULL, -- FK
    goodsNum INT(5) NOT NULL,
    productPrice INT(5) NOT NULL,
    productDisPrice INT(5) NOT NULL,
    productSpec VARCHAR(10) NOT NULL,
    couponNo INT(10)

) AUTO_INCREMENT = 1;


-- 柏翔
CREATE TABLE Coupon (
    couponNo INT PRIMARY KEY AUTO_INCREMENT,  -- 優惠券編號 (主鍵)
    counterNo INT NOT NULL,  -- 櫃位編號 FK
    couponTitle VARCHAR(255) NOT NULL,  -- 優惠券名稱
    couponContext VARCHAR(255) NOT NULL,  -- 優惠券內容
    couponStart TIMESTAMP NOT NULL,  -- 優惠券起始日
    couponEnd TIMESTAMP NOT NULL,  -- 優惠券到期日
    couponStatus TINYINT(1) NOT NULL CHECK (couponStatus IN (0, 1, 2)),    -- 優惠券狀態 (0:未開放, 1:時效內, 2:過期)   
    usageLimit INT NOT NULL,  -- 領取次數
	checkStatus TINYINT(1) NOT NULL CHECK (checkStatus IN (0, 1))      -- 審核狀態 (0:未審核, 1:已審核)
     
)
AUTO_INCREMENT = 1;

CREATE TABLE CouponDetail (
    couponDetailNo INT PRIMARY KEY AUTO_INCREMENT,  -- 優惠券明細編號 (主鍵)
    couponNo INT NOT NULL,  -- 優惠券編號 FK
    goodsNo INT NOT NULL,  -- 商品編號 FK
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 建立時間
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  -- 更新時間
    counterContext VARCHAR(255) NOT NULL,  -- 優惠券條件
    disRate DECIMAL(3, 2) NOT NULL  -- 折扣率
)
AUTO_INCREMENT = 1;

CREATE TABLE Discount (
    disNo INT PRIMARY KEY AUTO_INCREMENT,  -- 平台優惠編號 (主鍵)
    disTitle VARCHAR(255) NOT NULL,  -- 平台優惠名稱
    disContext VARCHAR(255) NOT NULL,  -- 平台優惠內容
    disRate DECIMAL(3, 2) NOT NULL,  -- 折扣率
    disStatus TINYINT(1) NOT NULL CHECK (disStatus IN (0, 1, 2)),  
    -- 優惠券狀態 (0:尚未開放, 1:使用中, 2:過期)
    descLimit VARCHAR(255) NOT NULL,  -- 使用條件敘述
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 建立時間
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  -- 更新時間
    disStart TIMESTAMP NOT NULL,  -- 優惠起始日
    disEnd TIMESTAMP NOT NULL  -- 優惠到期日
)
AUTO_INCREMENT = 1;

CREATE TABLE MemCoupon (
    memCouponNo INT PRIMARY KEY AUTO_INCREMENT,  -- 會員優惠券編號 (主鍵)
    memNo INT NOT NULL,  -- 會員編號 FK
    couponNo INT NOT NULL  -- 優惠券編號 FK
)
AUTO_INCREMENT = 1;

INSERT INTO Coupon (couponNo, counterNo, couponTitle, couponContext, couponStart, couponEnd, couponStatus, usageLimit, checkStatus)
VALUES
(1, 1, '雙11特價', '全館8折優惠', '2024-11-01 00:00:00', '2024-11-11 23:59:59', 1, 100, 1),
(2, 2, '週年慶', '單筆滿千折百', '2024-10-01 00:00:00', '2024-10-31 23:59:59', 0, 200, 0),
(3, 1, '新品促銷', '新商品9折', '2024-12-01 00:00:00', '2024-12-31 23:59:59', 1, 150, 1),
(4, 3, '限時秒殺', '全場5折', '2024-11-15 00:00:00', '2024-11-15 23:59:59', 1, 50, 1),
(5, 1, '黑五優惠', '全場7折', '2024-11-20 00:00:00', '2024-11-27 23:59:59', 2, 300, 1),
(6, 2, '限量優惠', '限量商品8折', '2024-12-01 00:00:00', '2024-12-31 23:59:59', 1, 120, 0),
(7, 4, 'VIP專屬', 'VIP會員專屬9折', '2024-10-01 00:00:00', '2025-01-01 23:59:59', 0, 500, 0),
(8, 2, '開幕大酬賓', '首日8折', '2024-10-10 00:00:00', '2024-10-10 23:59:59', 1, 200, 1),
(9, 3, '聖誕優惠', '聖誕節全館85折', '2024-12-24 00:00:00', '2024-12-26 23:59:59', 1, 100, 1),
(10, 1, '新年優惠', '新年折扣8折', '2025-01-01 00:00:00', '2025-01-02 23:59:59', 1, 50, 1),
(11, 4, '買一送一', '指定商品買一送一', '2024-11-01 00:00:00', '2024-11-05 23:59:59', 1, 100, 1),
(12, 3, '會員獨享', '會員獨享85折', '2024-10-20 00:00:00', '2024-12-20 23:59:59', 0, 150, 0),
(13, 2, '開幕專屬', '開幕85折', '2024-10-01 00:00:00', '2024-10-05 23:59:59', 1, 80, 1),
(14, 1, '冬季特價', '冬季大折扣9折', '2024-12-01 00:00:00', '2025-02-28 23:59:59', 1, 200, 1),
(15, 2, '新年紅包', '新年滿額折扣', '2025-01-01 00:00:00', '2025-01-05 23:59:59', 2, 100, 1),
(16, 3, '週五限時', '每週五限時8折', '2024-10-01 00:00:00', '2024-12-31 23:59:59', 1, 500, 1),
(17, 4, '返校優惠', '學生專屬8折', '2024-09-01 00:00:00', '2024-09-15 23:59:59', 2, 300, 1),
(18, 2, '感恩節特惠', '感恩節全館7折', '2024-11-28 00:00:00', '2024-11-30 23:59:59', 1, 200, 1),
(19, 1, '夏日折扣', '夏季商品85折', '2025-06-01 00:00:00', '2025-08-31 23:59:59', 0, 100, 0),
(20, 4, '電商狂歡', '雙12全場9折', '2024-12-12 00:00:00', '2024-12-12 23:59:59', 1, 100, 1);

INSERT INTO CouponDetail (couponDetailNo, couponNo, goodsNo, createdAt, updatedAt, counterContext, disRate)
VALUES
(1, 1, 1, '2024-10-01 10:00:00', '2024-10-01 10:30:00', '滿1000折100', 0.10),
(2, 1, 2, '2024-10-02 09:00:00', '2024-10-02 09:15:00', '限時折扣', 0.15),
(3, 2, 3, '2024-10-03 11:30:00', '2024-10-03 11:40:00', '雙11限量優惠', 0.20),
(4, 2, 4, '2024-10-05 08:00:00', '2024-10-05 08:30:00', 'VIP專屬優惠', 0.30),
(5, 3, 5, '2024-10-06 13:00:00', '2024-10-06 13:45:00', '新品9折', 0.10),
(6, 3, 6, '2024-10-07 14:00:00', '2024-10-07 14:15:00', '滿額85折', 0.15),
(7, 4, 7, '2024-10-08 15:00:00', '2024-10-08 15:10:00', '學生專屬8折', 0.20),
(8, 4, 8, '2024-10-09 10:00:00', '2024-10-09 10:20:00', '黑五大促銷', 0.25),
(9, 5, 9, '2024-10-10 12:00:00', '2024-10-10 12:10:00', '限時搶購', 0.30),
(10, 5, 10, '2024-10-11 16:00:00', '2024-10-11 16:15:00', '週年慶85折', 0.15),
(11, 6, 11, '2024-10-12 17:00:00', '2024-10-12 17:30:00', '滿500送100', 0.10),
(12, 6, 12, '2024-10-13 11:00:00', '2024-10-13 11:25:00', '會員專屬優惠', 0.20),
(13, 7, 13, '2024-10-14 14:00:00', '2024-10-14 14:10:00', '新年8折', 0.20),
(14, 7, 14, '2024-10-15 18:00:00', '2024-10-15 18:20:00', '限量商品優惠', 0.25),
(15, 8, 15, '2024-10-16 12:00:00', '2024-10-16 12:30:00', '冬季大促銷', 0.15),
(16, 8, 16, '2024-10-17 13:00:00', '2024-10-17 13:45:00', '開幕專屬優惠', 0.10),
(17, 9, 17, '2024-10-18 08:00:00', '2024-10-18 08:15:00', '返校季85折', 0.15),
(18, 9, 18, '2024-10-19 09:00:00', '2024-10-19 09:10:00', '限時8折', 0.20),
(19, 10, 19, '2024-10-20 10:00:00', '2024-10-20 10:30:00', '夏季折扣', 0.25),
(20, 10, 20, '2024-10-21 11:00:00', '2024-10-21 11:15:00', '週五限時優惠', 0.30);

INSERT INTO Discount (disNo, disTitle, disContext, disRate, disStatus, descLimit, createdAt, updatedAt, disStart, disEnd)
VALUES
(1, '雙11優惠', '全館8折', 0.20, 1, '消費滿1000元適用', '2024-10-01 10:00:00', '2024-10-01 11:00:00', '2024-11-01 00:00:00', '2024-11-11 23:59:59'),
(2, '聖誕節特賣', '全館7折', 0.30, 1, '限時優惠', '2024-12-01 12:00:00', '2024-12-01 12:30:00', '2024-12-24 00:00:00', '2024-12-26 23:59:59'),
(3, 'VIP專屬優惠', 'VIP會員享85折', 0.15, 0, '限VIP會員', '2024-10-01 09:00:00', '2024-10-01 09:15:00', '2024-10-01 00:00:00', '2025-01-01 23:59:59'),
(4, '週年慶特賣', '全館75折', 0.25, 1, '限量優惠', '2024-10-05 14:00:00', '2024-10-05 14:30:00', '2024-10-05 00:00:00', '2024-10-15 23:59:59'),
(5, '黑色星期五', '單品折扣8折', 0.20, 1, '單品優惠適用', '2024-11-20 08:00:00', '2024-11-20 08:30:00', '2024-11-20 00:00:00', '2024-11-27 23:59:59'),
(6, '新年促銷', '全館85折', 0.15, 1, '新年專屬優惠', '2024-12-30 10:00:00', '2024-12-30 10:20:00', '2025-01-01 00:00:00', '2025-01-05 23:59:59'),
(7, '返校季優惠', '限學生85折', 0.15, 2, '需出示學生證', '2024-09-01 07:00:00', '2024-09-01 07:30:00', '2024-09-01 00:00:00', '2024-09-15 23:59:59'),
(8, '感恩節特賣', '滿額75折', 0.25, 1, '消費滿2000元', '2024-11-25 12:00:00', '2024-11-25 12:30:00', '2024-11-28 00:00:00', '2024-11-30 23:59:59'),
(9, '夏季折扣', '夏季商品8折', 0.20, 1, '限指定商品', '2025-06-01 15:00:00', '2025-06-01 15:30:00', '2025-06-01 00:00:00', '2025-08-31 23:59:59'),
(10, '限時優惠', '限時全館7折', 0.30, 0, '限時3小時', '2024-10-10 16:00:00', '2024-10-10 16:20:00', '2024-10-10 00:00:00', '2024-10-10 23:59:59'),
(11, '雙12活動', '全館85折', 0.15, 1, '不限金額', '2024-12-01 10:00:00', '2024-12-01 10:30:00', '2024-12-12 00:00:00', '2024-12-12 23:59:59'),
(12, '首購優惠', '新用戶8折', 0.20, 1, '限新用戶', '2024-10-01 09:00:00', '2024-10-01 09:30:00', '2024-10-01 00:00:00', '2025-01-01 23:59:59'),
(13, '周年VIP專屬', 'VIP會員75折', 0.25, 1, '限VIP會員', '2024-11-01 10:00:00', '2024-11-01 10:30:00', '2024-11-01 00:00:00', '2024-12-01 23:59:59'),
(14, '開幕特賣', '全館8折', 0.20, 1, '限開幕日', '2024-10-01 12:00:00', '2024-10-01 12:30:00', '2024-10-01 00:00:00', '2024-10-01 23:59:59'),
(15, '冬季特賣', '冬季商品7折', 0.30, 2, '限指定商品', '2024-12-01 11:00:00', '2024-12-01 11:30:00', '2024-12-01 00:00:00', '2025-02-28 23:59:59'),
(16, '會員日優惠', '會員85折', 0.15, 1, '限會員', '2024-10-01 13:00:00', '2024-10-01 13:30:00', '2024-10-01 00:00:00', '2024-10-05 23:59:59'),
(17, '結帳再折', '結帳金額再9折', 0.10, 1, '不限金額', '2024-10-10 14:00:00', '2024-10-10 14:30:00', '2024-10-10 00:00:00', '2024-10-15 23:59:59'),
(18, '限量促銷', '限量商品8折', 0.20, 1, '限量100件', '2024-10-20 12:00:00', '2024-10-20 12:30:00', '2024-10-20 00:00:00', '2024-10-31 23:59:59'),
(19, '春季大折扣', '春季商品85折', 0.15, 0, '限指定商品', '2025-03-01 10:00:00', '2025-03-01 10:30:00', '2025-03-01 00:00:00', '2025-05-31 23:59:59'),
(20, '滿千折百', '消費滿1000折100', 0.10, 1, '不限商品', '2024-11-01 15:00:00', '2024-11-01 15:30:00', '2024-11-01 00:00:00', '2024-11-15 23:59:59');

INSERT INTO MemCoupon (memCouponNo, memNo, couponNo)
VALUES
(1, 1, 1),
(2, 2, 2),
(3, 3, 3),
(4, 4, 4),
(5, 5, 5),
(6, 6, 6),
(7, 7, 7),
(8, 8, 8),
(9, 9, 9),
(10, 10, 10),
(11, 1, 1),
(12, 2, 2),
(13, 3, 3),
(14, 4, 4),
(15, 5, 5),
(16, 6, 6),
(17, 7, 7),
(18, 8, 8),
(19, 9, 9),
(20, 10, 10);


-- 定紘

CREATE TABLE Goods (
	goodsNo INT NOT NULL PRIMARY KEY AUTO_INCREMENT, -- 商品編號，自增主鍵
    goodstNo INT NOT NULL,                           -- 商品類別編號 (外鍵)
    counterNo INT NOT NULL,                          -- 櫃位編號 (外鍵)
    goodsName VARCHAR(500) NOT NULL,                -- 商品名稱
    goodsDescription VARCHAR(500) NOT NULL,         -- 商品敘述
    goodsPrice INT NOT NULL,                        -- 商品單價
    goodsAmount INT NOT NULL,                       -- 商品庫存
    gpPhotos1 LONGBLOB,                     		-- 商品主圖(必填)
	gpPhotos2 LONGBLOB,                     		-- 商品副圖1(選填)
	gpPhotos3 LONGBLOB,                     		-- 商品副圖2(選填)
	gpPhotos4 LONGBLOB,                     		-- 商品副圖3(選填)
	gpPhotos5 LONGBLOB,                     		-- 商品副圖4(選填)
    gpPhotos6 LONGBLOB,                     		-- 商品副圖5(選填)
    gpPhotos7 LONGBLOB,                     		-- 商品副圖6(選填)
    gpPhotos8 LONGBLOB,                     		-- 商品副圖7(選填)
    gpPhotos9 LONGBLOB,                     		-- 商品副圖8(選填)
    gpPhotos10 LONGBLOB,                     		-- 商品副圖9(選填)
    goodsStatus TINYINT ,                   		-- 商品狀態 (0：下架、 1：上架)
    checkStatus TINYINT ,                   		-- 審核狀態 (0：審核中、 1：通過、 2：未通過)
    goodsDate DATETIME,                  			-- 商品上架日期
    goodsEnddate DATETIME                  			-- 商品下架日期
);

-- 插入 20 筆假資料
INSERT INTO Goods (
    goodsNo, goodstNo, counterNo, goodsName, goodsDescription, goodsPrice, goodsAmount, gpPhotos1,gpPhotos2,gpPhotos3,gpPhotos4,gpPhotos5,gpPhotos6,gpPhotos7,gpPhotos8,gpPhotos9,gpPhotos10, goodsStatus, checkStatus, goodsDate, goodsEnddate
) VALUES
(1, 1, 1, '路易威登 LOUIS VUITTON Double Zip Pochette Reverse 帆布 多夾層手拿 斜背包 M69203 防塵袋/背帶', '這款Double Zip Pochette完美融合經典Monogram和時尚Monogram Giant Reverse帆布，設計巧妙，擁有可拆式和可調節長度的皮革肩帶，可根據需求靈活變換多種攜帶方式。手袋兩側配有拉鏈口袋，方便隨身物品的分類收納，其中一個口袋還設有三個卡片夾層，中間隔層則方便存放票據等物品。', 32800, 100, NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL, 0, 0,NULL,NULL),
(2, 1, 1, '路易威登 LOUIS VUITTON Cabas Rivington 托特包 N41108 棋盤格托特', '這款 Cabas Rivington 採用經典 Damier 帆布製成，是日常時尚的完美詮釋。內部空間寬敞，可容納 A4 文件，搭配柔美的皮革手柄與亮眼的金色黃銅金屬件，為這款多功能購物袋增添迷人魅力。', 31800, 50, NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL, 0, 0,NULL,NULL),
(3, 2, 2, 'KIMHEKIM｜NEO-MALEVICH V領撞色腰帶洋裝', 'KIMHĒKIM 2022年 OBSESSION Nº4 ‘Hair Chronicles’秋冬系列商品。經典商品NEO-MALEVICH再進化，款式經典的撞色領口及排釦設計搭配中長版剪裁，本季在腰間加入撞色的腰帶拼接凸顯腰部線條，增添穿搭設計感。', 24400, 200, NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL, 0, 0,NULL,NULL),
(4, 2, 2, 'KIMHEKIM｜VENUS 高腰開衩落地喇叭褲', 'KIMHĒKIM 2022年 OBSESSION Nº4 ‘Hair Chronicles’秋冬系列商品。此褲款採用透氣且保暖的羊毛面料，並有著高腰喇叭褲版型，散發復古氛圍。', 17800, 150, NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL, 0, 0,NULL,NULL),
(5, 3, 2, 'KIMHEKIM｜MONROE 蝴蝶結芭蕾平底鞋', '此鞋款靈感來自瑪麗蓮夢露優雅而別緻的美感， 精緻的高級訂製手工蝴蝶結緞帶是鞋子的特別之處。 夢露芭蕾平底鞋款式浪漫而獨特，非常適合約會時著用。', 19040, 75, NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL, 0, 0,NULL,NULL),
(6, 3, 2, 'KIMHEKIM｜LACE-UP 真皮繫帶厚底短靴', 'KIMHĒKIM 2024年秋冬 OBSESSION N°11 ‘Puzzle’系列商品。此款短靴採用100%牛皮製成，皮革表面光滑且富有光澤。鞋頭設計為圓形，前側配有細長繫帶，增加經典時尚感。', 26800, 120, NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL, 0, 0,NULL,NULL),
(7, 4, 1, '路易威登 LOUIS VUITTON Epi 手鍊紅繩 ', '商品如圖實品拍攝🎬 尺碼17cm 紅繩戴起來更加顯色 附：原廠盒、說明書 近全新（戴過兩次而已）', 9800, 30, NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL, 0, 0,NULL,NULL),
(8, 4, 1, '路易威登 LOUIS VUITTON 金頭 老花+黑 雙面用皮帶', 'LV M0353 Circle 金頭 老花+黑 雙面用皮帶(3.5公分寬) 70cm 188 尺寸 (公分) 70 cm(寬3.5公分)', 18800, 20, NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL, 0, 0,NULL,NULL),
(9, 5, 3, 'Valentino Garavani 范倫鐵諾-白色VLTN印花漸層彩虹英文字腰包/胸背包', '品牌編號:YB0719ULP 英國精品網站Farfetch網購 商品尺寸:厚度:4cm、高度:13cm、寬度23cm 面料:牛剖層革小牛皮+金屬 MADE IN ITALY', 15000, 15, NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL, 0, 0, NULL,NULL),
(10, 5, 3,'Valentino VLTN 塗鴉小牛皮 Candystud 包 白色', '這款 Candystud 包以 VLTN 塗鴉設計展現個性，搭配柔軟小牛皮材質和絎縫效果，風格鮮明。可調節肩帶和手柄設計，滿足多種場合需求，內置拉鍊口袋讓收納更有條理。', 53500, 200, NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL, 0, 0,NULL,NULL),
(11, 6, 3,'Valentino 運動衫', '採用拉絨棉質平紋針織布料，連帽配有抽繩、拉鍊、袋鼠口袋、羅紋邊緣，胸前飾有橡膠字母標誌。舒適貼合。模特兒身高 187 厘米，所穿尺寸為 L。', 22949, 90, NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL, 0, 0,NULL,NULL),
(12, 6, 3,'Valentino 圓領運動衫', '採用拉絨棉質平紋針織布料製成，飾有同色系凸起標誌字樣印花。羅紋邊緣，常規版型。模特兒身高 187 厘米，所穿尺寸為 L。', 19575, 120, NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL, 0, 0,NULL,NULL),
(13, 7, 3,'Valentino Garavani 木鞋', '採用光滑和半光皮革製成，側面飾有釕飾面的 VLogo 標誌性細節。絎縫錶帶搭配古董金屬扣環。光滑皮革襯裡，解剖學絨面革鞋墊，鞋跟上飾有徽標嵌件，橡膠鞋底。', 22949, 50, NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL, 0, 0,NULL,NULL),
(14, 7, 3,'Valentino Open For A Change 運動鞋', '* 顏色 : White, White * 質料 : 生物基材質、再生橡膠 * 尺寸/碼 : 41H * 設計師代碼 : YS0830PUD-0BO * Series : OPEN FOR A CHANGE SNEAKER', 16336, 180, NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL, 0, 0,NULL,NULL),
(15, 8, 4,'ARMANI手錶,編號AR00043', '編號AR00043,44mm綠金圓形精鋼錶殼,墨綠色中三針顯示, 運動, 水鬼錶面,金銀相間精鋼錶帶款', 17000, 30, NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL, 0, 0,NULL,NULL),
(16, 8, 4,'ARMANI手錶,編號AR00013', '編號AR00013,42mm墨綠色錶殼,深黑色錶帶款', 13100, 80, NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL, 0, 0,NULL,NULL),
(17, 9, 5, '雅詩蘭黛 Micro Essence 微分子肌底原生露', '雅詩蘭黛微分子肌底原生露，擁有全新保濕因子，打造最強柔嫩肌膚！使用獨創微酵科技，低溫封存99%活性益生菌，秒速吸收直達肌底，並添加光果甘草精萃與2D強效玻尿酸，退紅抗敏，由內而外鎖水保濕。', 8400, 60, NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL, 0, 0,NULL,NULL),
(18, 9, 5, '雅詩蘭黛 Revitalizing Supreme+ Moisturizer 年輕無敵膠原霜', '內含新三大配方激升10倍膠原力*，無敵緊緻Q彈*經科學實驗測試，奇蹟辣木、白芙蓉8大精萃與膠原協同激活科技三種配方相互作用下，相較於未使用之對照組，經96小時後幫助肌膚膠原協同作用達10倍', 4980, 150, NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL, 0, 0,NULL,NULL),
(19, 10, 6, 'Diptyque 聖日爾曼大道34號蠟燭', '這支Sanctuary Road 34號蠟燭，香氣濃郁，非常適合放在室內和室外空間。', 358, 25, NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL, 0, 0,NULL,NULL),
(20, 10, 6, 'Diptyque 青藤玫瑰多用香氛 200ml', '綠蔭玫瑰多用香氛是一款令人迷醉的香氛，帶來了土耳其玫瑰、常春藤、橙皮調、加蓬、天竺葵、麝香、木材和雪松等芬芳。', 99, 130, NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL, 0, 0,NULL,NULL);

-- 商品類別
CREATE TABLE GoodsType (
    goodstNo INT NOT NULL PRIMARY KEY AUTO_INCREMENT,  	-- 商品類別編號，主鍵
    goodstName VARCHAR(255) NOT NULL            		-- 類別名稱
);

-- 插入假資料
INSERT INTO GoodsType (goodstNo, goodstName) 
VALUES 
(1, '女士包包'),
(2, '女士服裝'), 
(3, '女士鞋'),
(4, '女士配件'),
(5, '男士包包'),
(6, '男士服裝'), 
(7, '男士鞋'),
(8, '男士配件'),
(9, '美妝保養'),
(10, '家居與科技');
   

-- 商品照片
CREATE TABLE GoodsPhotos (
    gpNo INT NOT NULL PRIMARY KEY AUTO_INCREMENT,               -- 商品照片編號，主鍵
    goodsNo INT NOT NULL,                        -- 商品編號，外來鍵
    gpPhotos LONGBLOB                           -- 商品照片
   
);

-- 插入 20 筆假資料
INSERT INTO GoodsPhotos (gpNo, goodsNo, gpPhotos) 
VALUES 
(1, 1, NULL),  -- 假資料，實際照片以NULL表示
(2, 1, NULL),
(3, 1, NULL),
(4, 2, NULL),
(5, 2, NULL),
(6, 3, NULL),
(7, 3, NULL),
(8, 4, NULL),
(9, 4, NULL),
(10, 5, NULL),
(11, 5, NULL),
(12, 6, NULL),
(13, 6, NULL),
(14, 7, NULL),
(15, 7, NULL),
(16, 8, NULL),
(17, 8, NULL),
(18, 9, NULL),
(19, 9, NULL),
(20, 10, NULL);

-- 商城輪播資訊
CREATE TABLE StoreCarousel (
    storeCarouselNo INT NOT NULL PRIMARY KEY AUTO_INCREMENT,     -- 輪播資訊編號，主鍵
    counterNo INT NOT NULL,                        -- 櫃位編號，外來鍵
    disNo INT NOT NULL,                           -- 平台優惠編號，外來鍵
    carouselTime DATETIME NOT NULL,               -- 輪播時間
    carouselPic LONGBLOB                          -- 輪播圖片

);

-- 插入 20 筆假資料
INSERT INTO StoreCarousel (storeCarouselNo, counterNo, disNo, carouselTime, carouselPic) 
VALUES 
(1, 1, 1, '2024-01-01 08:00:00', NULL),  -- 假資料，圖片以NULL表示
(2, 1, 2, '2024-01-02 09:00:00', NULL),
(3, 1, 3, '2024-01-03 10:00:00', NULL),
(4, 2, 1, '2024-01-04 11:00:00', NULL),
(5, 2, 2, '2024-01-05 12:00:00', NULL),
(6, 2, 3, '2024-01-06 13:00:00', NULL),
(7, 3, 1, '2024-01-07 14:00:00', NULL),
(8, 3, 2, '2024-01-08 15:00:00', NULL),
(9, 3, 3, '2024-01-09 16:00:00', NULL),
(10, 4, 1, '2024-01-10 17:00:00', NULL),
(11, 4, 2, '2024-01-11 18:00:00', NULL),
(12, 4, 3, '2024-01-12 19:00:00', NULL),
(13, 5, 1, '2024-01-13 20:00:00', NULL),
(14, 5, 2, '2024-01-14 21:00:00', NULL),
(15, 5, 3, '2024-01-15 22:00:00', NULL),
(16, 6, 1, '2024-01-16 23:00:00', NULL),
(17, 6, 2, '2024-01-17 00:00:00', NULL),
(18, 6, 3, '2024-01-18 01:00:00', NULL),
(19, 7, 1, '2024-01-19 02:00:00', NULL),
(20, 7, 2, '2024-01-20 03:00:00', NULL);

-- 抽成月結
CREATE TABLE MonthSettlement (
    monthSettlementNo INT NOT NULL PRIMARY KEY AUTO_INCREMENT,    -- 抽成月結編號，主鍵
    counterNo INT NOT NULL,                         -- 櫃位編號，外來鍵
    month VARCHAR(6) NOT NULL,                     -- 月份 (YYYYMM)
    comm INT NOT NULL                               -- 總金額
   
);

-- 插入假資料
INSERT INTO MonthSettlement (monthSettlementNo, counterNo, month, comm) 
VALUES 
(1, 1, '202401', 50000),  -- 假資料
(2, 2, '202401', 30000),
(3, 3, '202401', 45000),
(4, 4, '202401', 70000),
(5, 5, '202401', 25000),
(6, 6, '202401', 40000),
(7, 7, '202401', 60000),
(8, 1, '202402', 55000),
(9, 2, '202402', 35000),
(10, 3, '202402', 47000),
(11, 4, '202402', 75000),
(12, 5, '202402', 26000),
(13, 6, '202402', 42000),
(14, 7, '202402', 61000),
(15, 1, '202403', 53000),
(16, 2, '202403', 37000),
(17, 3, '202403', 48000),
(18, 4, '202403', 78000),
(19, 5, '202403', 28000),
(20, 6, '202403', 43000);

-- 櫃位訊息通知
CREATE TABLE CounterInform (
    counterInformNo INT NOT NULL PRIMARY KEY AUTO_INCREMENT,       -- 櫃位訊息編號，主鍵
    counterNo INT NOT NULL,                         -- 櫃位編號，外來鍵
    informMsg VARCHAR(500) NOT NULL,               -- 通知訊息
    informDate DATETIME DEFAULT CURRENT_TIMESTAMP,  -- 通知時間
    informRead TINYINT DEFAULT 0                    -- 已讀未讀 (0: 未讀, 1: 已讀)
   
);

-- 插入 20 筆假資料
INSERT INTO CounterInform (counterInformNo, counterNo, informMsg, informDate, informRead) 
VALUES 
(1, 1, '商品已上架', '2024-01-01 08:00:00', 0),
(2, 1, '新的促銷活動開始', '2024-01-02 09:00:00', 0),
(3, 2, '庫存不足，請補貨', '2024-01-03 10:00:00', 0),
(4, 2, '櫃位檢查通過', '2024-01-04 11:00:00', 1),
(5, 3, '本週特價商品', '2024-01-05 12:00:00', 0),
(6, 3, '請注意上架時間', '2024-01-06 13:00:00', 0),
(7, 4, '新商品已進貨', '2024-01-07 14:00:00', 1),
(8, 4, '促銷活動結束提醒', '2024-01-08 15:00:00', 0),
(9, 5, '顧客滿意度調查', '2024-01-09 16:00:00', 0),
(10, 5, '本月銷售報告', '2024-01-10 17:00:00', 1),
(11, 6, '櫃位維護通知', '2024-01-11 18:00:00', 0),
(12, 6, '即將到期的促銷活動', '2024-01-12 19:00:00', 1),
(13, 7, '商品回饋活動', '2024-01-13 20:00:00', 0),
(14, 7, '新年快樂的祝福', '2024-01-14 21:00:00', 0),
(15, 1, '本週營業時間變更', '2024-01-15 22:00:00', 1),
(16, 2, '顧客回饋及評價', '2024-01-16 23:00:00', 0),
(17, 3, '即將到貨商品', '2024-01-17 00:00:00', 0),
(18, 4, '促銷結束通知', '2024-01-18 01:00:00', 1),
(19, 5, '庫存檢查通知', '2024-01-19 02:00:00', 0),
(20, 6, '櫃位改裝計畫', '2024-01-20 03:00:00', 0);


-- 灃晉
CREATE TABLE UsedPic (
    usedPicNo INT NOT NULL AUTO_INCREMENT PRIMARY KEY,  -- 二手商品照片明細編號
    usedNo INT NOT NULL,                                 -- 二手商品編號
    usedPics LONGBLOB                                  -- 二手商品照片
   --  FOREIGN KEY (usedNo) REFERENCES Used(usedNo)       -- 二手商品外來鍵
)AUTO_INCREMENT = 1;


CREATE TABLE GoodsTrack (
    goodsTrackNum INT(10) NOT NULL PRIMARY KEY AUTO_INCREMENT,    -- 商品追蹤清單編號
    memNo INT(10) NOT NULL,                         -- 會員編號
    goodsNo INT(10) NOT NULL                      -- 商品編號
    -- FOREIGN KEY (memNo) REFERENCES Mem(memNo),     -- 會員外來鍵
--     FOREIGN KEY (goodsNo) REFERENCES Goods(goodsNo) -- 商品外來鍵
) AUTO_INCREMENT = 1;


CREATE TABLE Used (
    usedNo INT(10) NOT NULL PRIMARY KEY AUTO_INCREMENT,              -- 二手商品編號
    classNo INT(10) NOT NULL,                         -- 商品類別編號
    sellerNo INT(10) NOT NULL,                        -- 會員編號(賣家)
    usedName VARCHAR(20) NOT NULL,                    -- 二手商品名稱
    usedProDesc VARCHAR(255) NOT NULL,                -- 二手商品描述
    usedNewness TINYINT(1),                           -- 商品新舊程度 (0:近全新, 1:7成新, 2:5成新, 3:3成新)
    usedPrice INT(6) CHECK (usedPrice > 0),           -- 二手商品單價, 必須大於0
    usedStocks INT(5) CHECK (usedStocks > 0),         -- 商品庫存數量, 必須大於0
    usedLaunchedTime DATETIME DEFAULT CURRENT_TIMESTAMP,  -- 二手商品上架時間, 預設為當前時間
    soldTime DATETIME DEFAULT NULL,                   -- 二手商品下架時間, 預設為NULL且允許為空
    usedState TINYINT(2) NOT NULL                    -- 二手商品狀態 (0:未上架, 1:已上架)
    -- FOREIGN KEY (classNo) REFERENCES GoodsType(goodstNo),  -- 商品類別外來鍵
    -- FOREIGN KEY (sellerNo) REFERENCES Member(memNo)        -- 賣家會員編號外來鍵
)AUTO_INCREMENT = 1;

INSERT INTO Used (classNo, sellerNo, usedName, usedProDesc, usedNewness, usedPrice, usedStocks, usedState) 
VALUES 
(1, 1, '筆記型電腦', '高效能筆記型電腦，適合專業用途', 0, 1500, 10, 1),
(2, 2, '智慧型手機', '最新款智慧型手機，搭載高解析螢幕', 0, 800, 25, 1),
(3, 3, '耳機', '降噪耳機，提升音質體驗', 1, 200, 50, 1),
(4, 4, '數位相機', '4K數位相機，拍攝高畫質影片', 0, 1200, 5, 1),
(5, 5, '平板電腦', '10吋平板，輕便攜帶', 0, 300, 15, 1),
(6, 6, '智慧手錶', '防水智慧手錶，支援健康追蹤', 1, 150, 30, 0),
(7, 7, '藍牙音箱', '藍牙連接音箱，小巧音量大', 2, 100, 20, 1),
(8, 8, '顯示器', '27吋4K顯示器，清晰畫質', 0, 500, 8, 1),
(9, 9, '機械鍵盤', 'RGB背光機械鍵盤，手感佳', 2, 70, 60, 1),
(10, 10, '無線滑鼠', '人體工學設計無線滑鼠', 1, 50, 40, 1),
(1, 1, '智慧家庭音箱', '語音控制智慧音箱', 0, 300, 12, 1),
(2, 2, '電動滑板車', '折疊式電動滑板車，適合短程移動', 0, 400, 7, 1),
(3, 3, '電子書閱讀器', '高解析度電子墨水螢幕', 0, 200, 15, 1),
(4, 4, '空氣清淨機', '高效空氣過濾系統', 1, 250, 20, 1),
(5, 5, '咖啡機', '自動研磨咖啡機，輕鬆沖泡', 0, 350, 10, 1),
(6, 6, '電視', '50吋智慧型電視，支援4K解析度', 0, 800, 5, 1),
(7, 7, '健身追蹤器', '運動健康追蹤器', 1, 100, 30, 1),
(8, 8, '攝影無人機', '高畫質攝影無人機', 0, 1200, 3, 1),
(9, 9, '車用吸塵器', '車內專用小型吸塵器', 2, 80, 50, 1),
(10, 10, '電子磅秤', '體重電子磅秤，精確測量', 1, 60, 40, 1);

DELIMITER //

-- 觸發器1: 商品狀態從1變成0時，自動填寫下架時間 Used表格用
CREATE TRIGGER update_sold_time
BEFORE UPDATE ON Used
FOR EACH ROW
BEGIN
    -- 檢查如果商品狀態從已上架(1)變成未上架(0)
    IF NEW.usedState = 0 AND OLD.usedState = 1 THEN
        SET NEW.soldTime = CURRENT_TIMESTAMP;
    END IF;
END //

-- 觸發器2: 商品狀態從0變成1時，自動清除下架時間
CREATE TRIGGER clear_sold_time
BEFORE UPDATE ON Used
FOR EACH ROW
BEGIN
    -- 檢查如果商品狀態從未上架(0)變成已上架(1)
    IF NEW.usedState = 1 AND OLD.usedState = 0 THEN
        SET NEW.soldTime = NULL;
    END IF;
END //

DELIMITER ;


--  羿豪
CREATE TABLE followers (
    followersNo INT(10) NOT NULL,          -- 會員編號 PK、FK
    counterNo INT NOT NULL,                -- 櫃位編號 PK、FK
    PRIMARY KEY (followersNo, counterNo)   -- 複合主鍵
);

-- 插入 Followers 表格的假資料
INSERT INTO followers (followersNo, counterNo) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8),
(9, 8),
(10, 7);

-- 在 followers 表格中新增外來鍵 FK



-- 建立 ShoppingCartList 表格
CREATE TABLE ShoppingCartList (
    shoppingcartListNo INT(10) NOT NULL AUTO_INCREMENT PRIMARY KEY, -- 自增主鍵 PK
    memNo INT(10) NOT NULL,                          -- 會員編號 FK
    goodsNo INT(10) NOT NULL,                        -- 商品編號 FK
    goodsNum INT(5) NOT NULL CHECK (goodsNum > 0),   -- 商品數量 > 0
    goodsPrice INT(5) NOT NULL CHECK (goodsPrice > 0), -- 商品單價 > 0
    goodsName VARCHAR(20) NOT NULL,                  -- 商品名稱
    orderTotalprice INT(6) NOT NULL CHECK (orderTotalprice > 0) -- 訂單總金額 > 0
);

-- 插入 ShoppingCartList 表格的假資料
INSERT INTO shoppingcartList (memNo, goodsNo, goodsNum, goodsPrice, goodsName, orderTotalprice) VALUES
(1, 1, 2, 500, 'Product A', 1000),
(2, 2, 1, 800, 'Product B', 800),
(3, 3, 3, 300, 'Product C', 900),
(4, 4, 2, 150, 'Product D', 300),
(5, 5, 4, 200, 'Product E', 800),
(6, 6, 2, 400, 'Product F', 800),
(7, 7, 1, 1200, 'Product G', 1200),
(8, 8, 5, 100, 'Product H', 500),
(9, 9, 2, 750, 'Product I', 1500),
(10, 10, 1, 2000, 'Product J', 2000);



-- 昱夆

CREATE TABLE Member (
    memNo INT PRIMARY KEY AUTO_INCREMENT,                  -- 會員編號，自增主鍵
    memAccount VARCHAR(50) NOT NULL,                       -- 會員帳號
    memPassword VARCHAR(100) NOT NULL,                     -- 會員密碼
    memName VARCHAR(50) NOT NULL,                          -- 會員姓名
    memAddress VARCHAR(100),                               -- 會員地址
    memPhone VARCHAR(15),                                  -- 會員電話
    memUID VARCHAR(10),                                    -- 會員身分證字號
    memEmail VARCHAR(50),                                  -- 會員電子信箱
    memSex TINYINT,                                        -- 會員性別 (0: 未知, 1: 男, 2: 女)
    memBirth DATETIME,                                     -- 會員生日
    memStatus TINYINT DEFAULT 1,                           -- 會員狀態 (0: 停用, 1: 啟用)
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,          -- 建立時間
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- 更新時間
    memPasswordHint VARCHAR(100),                          -- 密碼提示問題
    memPasswordHintAnswer VARCHAR(100),                    -- 密碼提示答案
    memPasswordChangedAt DATETIME,                         -- 密碼修改時間
    memEmailChecked TINYINT DEFAULT 0                      -- 電子信箱驗證 (0: 未驗證, 1: 已驗證)
) AUTO_INCREMENT=1;

INSERT INTO Member 
    (memAccount, memPassword, memName, memAddress, memPhone, memUID, memEmail, memSex, memBirth, memStatus, createdAt, updatedAt, memPasswordHint, memPasswordHintAnswer, memPasswordChangedAt, memEmailChecked)
VALUES
    ('user1', 'password123', 'Alice', '123 Main St', '0912345678', 'A123456789', 'alice@example.com', 2, '1990-01-01', 1, NOW(), NOW(), 'Favorite color?', 'Blue', NOW(), 1),
    ('user2', 'password456', 'Bob', '456 Elm St', '0923456789', 'B234567890', 'bob@example.com', 1, '1985-05-15', 1, NOW(), NOW(), 'Pet’s name?', 'Buddy', NOW(), 1),
    ('user3', 'password789', 'Charlie', '789 Maple Ave', '0934567890', 'C345678901', 'charlie@example.com', 1, '1992-08-08', 1, NOW(), NOW(), 'Birth city?', 'Springfield', NOW(), 1),
    ('user4', 'password000', 'Diana', '101 Pine Rd', '0945678901', 'D456789012', 'diana@example.com', 2, '1995-02-20', 1, NOW(), NOW(), 'Favorite book?', 'Pride and Prejudice', NOW(), 0),
    ('user5', 'password111', 'Ethan', '102 Oak St', '0956789012', 'E567890123', 'ethan@example.com', 1, '1988-07-13', 1, NOW(), NOW(), 'Best friend’s name?', 'Mike', NOW(), 1),
    ('user6', 'password222', 'Fiona', '103 Birch Ln', '0967890123', 'F678901234', 'fiona@example.com', 2, '1993-09-17', 1, NOW(), NOW(), 'Favorite sport?', 'Soccer', NOW(), 1),
    ('user7', 'password333', 'George', '104 Cedar Dr', '0978901234', 'G789012345', 'george@example.com', 1, '1980-03-03', 1, NOW(), NOW(), 'Mother’s maiden name?', 'Smith', NOW(), 1),
    ('user8', 'password444', 'Hannah', '105 Willow St', '0989012345', 'H890123456', 'hannah@example.com', 2, '1999-12-12', 1, NOW(), NOW(), 'Favorite movie?', 'Inception', NOW(), 0),
    ('user9', 'password555', 'Ian', '106 Aspen Ave', '0910123456', 'I901234567', 'ian@example.com', 1, '1987-11-11', 1, NOW(), NOW(), 'Lucky number?', '7', NOW(), 1),
    ('user10', 'password666', 'Jasmine', '107 Redwood Blvd', '0921234567', 'J012345678', 'jasmine@example.com', 2, '2001-04-25', 1, NOW(), NOW(), 'Favorite food?', 'Pizza', NOW(), 1);


-- 二手商品客訴

CREATE TABLE usedComplaint (
    usedComplaintNo INT PRIMARY KEY AUTO_INCREMENT,		 -- 二手商品客訴編號 (PK)
    memNo INT NOT NULL,							 		 -- 會員編號 (FK: Member(memNo))
    usedOrderNo INT NOT NULL,							 -- 二手商品訂單編號 (FK: UsedOrder(usedOrderNo))
    usedComplaintDate DATETIME NOT NULL,				 -- 客訴日期
    usedComplaintReason VARCHAR(500) NOT NUll, 			 -- 客訴原因
    usedComplaintPhotos LONGBLOB, 						 -- 客訴圖片
    usedComplaintStatus TINYINT DEFAULT 0 , 			 -- 客訴狀態 (0:待處理, 1:處理完成)
    usedComplaintMSG VARCHAR(500) 					 	 -- 客訴回覆
)AUTO_INCREMENT=1;

INSERT INTO usedComplaint (memNo, usedOrderNo, usedComplaintDate, usedComplaintReason, usedComplaintPhotos, usedComplaintStatus, usedComplaintMSG)
VALUES
    (1, 1, '2024-10-01 10:30:00', '產品有瑕疵', NULL, 0, '正在處理'),
    (2, 2, '2024-10-02 11:15:00', '收到與描述不符的商品', NULL, 1, '已完成處理'),
    (3, 3, '2024-10-03 14:45:00', '商品有異味', NULL, 0, '等待回覆'),
    (4, 4, '2024-10-04 09:20:00', '產品損壞', NULL, 1, '已補償'),
    (5, 5, '2024-10-05 16:05:00', '產品顏色錯誤', NULL, 0, '處理中'),
    (6, 6, '2024-10-06 12:00:00', '商品數量不足', NULL, 1, '已寄送補件'),
    (7, 7, '2024-10-07 08:50:00', '商品包裝破損', NULL, 0, '等待處理中'),
    (8, 8, '2024-10-08 17:30:00', '無法開機', NULL, 1, '已完成修理'),
    (9, 9, '2024-10-09 13:40:00', '商品尺寸不合', NULL, 0, '正在確認'),
    (10, 10, '2024-10-10 15:10:00', '產品少配件', NULL, 1, '處理完成');
    
-- 汝鎂
    
    CREATE TABLE MemInform (
    memInformNo INT NOT NULL AUTO_INCREMENT PRIMARY KEY,  -- 會員通知編號
    memNO INT NOT NULL,                                   -- 會員編號
    informMsg Varchar(500) NOT NUll,                      -- 通知訊息
    informDate DateTime NOT NUll,                         -- 通知日期
    informRead Tinyint                                    -- 已讀未讀(0: 未讀)(1: 已讀)
   --  FOREIGN KEY (MemNo) REFERENCES Member(MemNO)       -- 會員通知外來鍵
);

CREATE TABLE Auth (
    authNo INT NOT NULL PRIMARY KEY AUTO_INCREMENT,      -- 權限編號
    authTitle VARCHAR(255) NOT NULL,                     -- 權限名稱
    authContext VARCHAR(255) NOT NULL                    -- 權限內容
);

CREATE TABLE ManagerAuth (
    managerNo INT NOT NULL  ,   -- 管理員編號 FK
    authNo INT NOT NULL  ,    -- 權限編號 FK
    PRIMARY KEY (managerNo, authNo)   -- 複合主鍵
);

CREATE TABLE CounterCarousel (
    counterCarouselNo INT NOT NULL AUTO_INCREMENT PRIMARY KEY,  -- 輪播資訊編號
    counterNo INT NOT NULL,                                     -- 櫃位編號 FK
    carouselTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,  -- 上傳時間，默認為新增時的當前時間
    carouselPic LONGBLOB NULL,                                  -- 輪播圖片
    goodsNo INT NOT NULL                                        -- 商品編號 FK
    -- FOREIGN KEY (counterNo) REFERENCES Counter(counterNo)      -- 櫃位輪播資訊外來鍵
    -- FOREIGN KEY (goodsNo) REFERENCES Goods(goodsNo)           -- 商品外來鍵
);

CREATE TABLE CounterChat (
    chatNo INT NOT NULL AUTO_INCREMENT PRIMARY KEY,              -- 聊天資訊編號
    counterNo INT NOT NULL,                                      -- 櫃位編號 FK
    memNo INT NOT NULL,                                          -- 會員編號 FK
    chatContent VARCHAR(255),                                    -- 訊息內容
    memQuestionPic LongBlob NUll,                                -- 會員問題照片
    chatTime DateTime,                                           -- 聊天時間
    chatDirection TINYINT(1) NOT NULL,                           -- 聊天方向
    chatRead TINYINT(1) NOT NULL                                 -- 已讀未讀
   --  FOREIGN KEY (couterNo) REFERENCES Counter(couterNo)       -- 櫃位聊天紀錄外來鍵
   --  FOREIGN KEY (memNo) REFERENCES Member(memNo)              -- 櫃位聊天紀錄外來鍵
);

CREATE TABLE GoodComplaint (
    counterComplaintNo INT NOT NULL AUTO_INCREMENT PRIMARY KEY,  -- 櫃位客訴編號
    memNo INT NOT NULL,                                          -- 會員編號  FK
    counterOrderNo INT NOT NULL,                                 -- 櫃位訂單編號 FK
    complaintDate DateTime NOT NULL,                             -- 客訴時間
    complaintReason VARCHAR(255) NOT NULL,                       -- 客訴原因
    usedComplaintPhotos LongBlob NUll,                           -- 客訴商品圖片
    usedComplaintStatus TINYINT NOT NULL,                        -- 客訴狀態(0: 待處理)(1: 處理中)(2: 處理完畢)
    usedComplaintMsg VARCHAR(500)                                -- 客訴回覆內容
   --  FOREIGN KEY (CouterNo) REFERENCES Counter(CouterNo)       -- 商品客訴外來鍵
   --  FOREIGN KEY (MemNo) REFERENCES Member(MemNo)              -- 商品客訴外來鍵
);

CREATE TABLE UsedOrder (
    usedOrderNo INT(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,     -- 二手訂單編號
    usedNo INT(10) NOT NULL,                                     -- 二手商品編號 FK
    buyerNo INT(10) NOT NULL,                                    -- 買家編號 FK
    usedOrderTime DATETIME,                                      -- 訂單成立時間
    usedPrice INT(6) NOT NULL CHECK (usedPrice > 0),             -- 二手商品單價，必須大於0
    usedCount INT(5) NOT NULL CHECK (usedCount > 0),             -- 二手商品購買數量，必須大於0
    deliveryStatus TINYINT(5) NOT NULL,                          -- 宅配狀態 (0:未出貨)(1:已出貨)(2:待領件)(3:已領貨)(4:已取消)
    receiverName VARCHAR(100),                                   -- 收件人姓名
    receiverAdr VARCHAR(100),                                    -- 收件人地址
    receiverPhone VARCHAR(100),                                  -- 收件人電話
    sellerSatisfication TINYINT(5),                              -- 滿意度 (1到5顆星)
    sellerCommentContent VARCHAR(500),                           -- 評論內文
    sellerCommentDate DATETIME NOT NULL,                         -- 評論日期
    usedTotalPrice INT(10) NOT NULL CHECK (usedTotalPrice > 0)   -- 訂單總價，必須大於0
    -- FOREIGN KEY (usedNo) REFERENCES Used(usedNo),             -- 二手商品訂單外來鍵
    -- FOREIGN KEY (buyerNo) REFERENCES Member(memNo)            -- 二手商品訂單外來鍵
);

CREATE TABLE UsedChat (
    usedChatNo INT NOT NULL  AUTO_INCREMENT PRIMARY KEY,      -- 二手商品聊天編號
    memNo INT NOT NULL,                                       -- 買家編號
    sellerNo INT NOT NULL,                                    -- 賣家編號
    chatTime DATETIME NOT NULL,                               -- 二手商品聊天時間
    chatContent VARCHAR(500) NOT NULL,                        -- 二手商品聊天內容
    usedQuestionPic LONGBLOB,                                 -- 二手問題照片
    chatDirection TINYINT NOT NULL,                           -- 聊天方向 (0: 買家對賣家)(1: 賣家對買家)
    chatRead TINYINT NOT NULL                                 -- 已讀未讀 (0: 未讀)(1: 已讀)
    -- FOREIGN KEY (memNo) REFERENCES Member(memNo),          -- 二手商品聊天紀錄外來鍵
    -- FOREIGN KEY (sellerNo) REFERENCES Member(memNo)        -- 二手商品聊天紀錄外來鍵
);

INSERT INTO UsedOrder 
    (usedNo, buyerNo, usedOrderTime, usedPrice, usedCount, deliveryStatus, receiverName, receiverAdr, receiverPhone, sellerSatisfication, sellerCommentContent, sellerCommentDate, usedTotalPrice)
VALUES
    (1, 1, '2024-10-01 12:00:00', 500, 2, 1, '王大明', '台北市信義區忠孝東路1號', '0912345678', 5, '非常滿意，商品品質很好', '2024-10-02', 1000),
    (2, 2, '2024-10-02 14:30:00', 300, 1, 0, '李小華', '台北市大安區和平東路2號', '0923456789', 4, '商品符合描述，滿意', '2024-10-03', 300),
    (3, 3, '2024-10-03 10:15:00', 1200, 1, 2, '陳美麗', '台北市松山區南京東路3號', '0934567890', 3, '商品包裝有些瑕疵', '2024-10-04', 1200),
    (4, 4, '2024-10-04 16:45:00', 450, 3, 1, '林志強', '台北市萬華區西寧南路4號', '0945678901', 5, '非常喜歡，會再回購', '2024-10-05', 1350),
    (5, 5, '2024-10-05 09:20:00', 850, 2, 3, '張小英', '新北市板橋區中山路5號', '0956789012', 4, '品質不錯，性價比高', '2024-10-06', 1700),
    (6, 6, '2024-10-06 18:00:00', 700, 1, 1, '黃建國', '新北市中和區中和路6號', '0967890123', 2, '產品外觀有小瑕疵', '2024-10-07', 700),
    (7, 7, '2024-10-07 11:30:00', 600, 2, 4, '周玉芬', '桃園市中壢區新生路7號', '0978901234', 3, '尚可接受，質量一般', '2024-10-08', 1200),
    (8, 8, '2024-10-08 15:45:00', 350, 1, 1, '鄭光明', '桃園市八德區八德路8號', '0989012345', 4, '運送速度快，品質不錯', '2024-10-09', 350),
    (9, 9, '2024-10-09 13:40:00', 500, 1, 1, '吳淑敏', '台中市北屯區文心路9號', '0910123456', 5, '服務很好，推薦購買', '2024-10-10', 500),
    (10, 10, '2024-10-10 20:10:00', 1000, 1, 0, '許英傑', '台南市中西區民生路10號', '0921234567', 1, '不是很滿意，服務需改進', '2024-10-11', 1000);

-- 插入假資料到 MemInform 表格
INSERT INTO MemInform (memInformNo, memNO, informMsg, informDate, informRead) VALUES
(1, 1, '您的訂單已出貨', '2024-11-01 15:00:00', 0),
(2, 2, '您的訂單已取消', '2024-11-02 16:30:00', 1),
(3, 3, '新品上架通知', '2024-11-03 10:15:00', 0),
(4, 4, '限時優惠開始啦', '2024-11-04 11:45:00', 0),
(5, 5, '您的訂單已送達', '2024-11-05 09:20:00', 1),
(6, 6, '會員積分到期提醒', '2024-11-06 17:50:00', 0),
(7, 7, '邀請您參加會員活動', '2024-11-07 13:30:00', 0),
(8, 8, '您的退貨申請已審核', '2024-11-08 14:40:00', 1),
(9, 9, '感謝您的反饋', '2024-11-09 08:10:00', 1),
(10, 10, '系統更新通知', '2024-11-10 12:00:00', 0);


ALTER TABLE counter
ADD CONSTRAINT Counter_counterTypeNo_FK FOREIGN KEY (counterTypeNo) REFERENCES counterType(counterTypeNo);


ALTER TABLE faq
ADD CONSTRAINT faq_counterNo_FK FOREIGN KEY (counterNo) REFERENCES counter(counterNo);


alter TABLE counterorder ADD CONSTRAINT counterorder_counterNo_FK FOREIGN KEY (counterNo) REFERENCES counter(counterNo);
alter TABLE counterorder ADD CONSTRAINT counterorder_memNo_FK FOREIGN KEY (memNo) REFERENCES Member(memNo);
    
alter TABLE counterorderdetail ADD CONSTRAINT counterorderdetail_counterorderno_FK  FOREIGN KEY (counterOrderNo) REFERENCES CounterOrder(counterOrderNo);
alter TABLE counterorderdetail ADD CONSTRAINT counterorderdetail_goodsno_FK FOREIGN KEY (goodsNo) REFERENCES Goods(goodsNo);

ALTER TABLE coupon
ADD CONSTRAINT coupon_counterNo_FK FOREIGN KEY (counterNo) REFERENCES counter(counterNo);

 ALTER TABLE CouponDetail
 ADD CONSTRAINT CouponDetail_couponNo_FK FOREIGN KEY (couponNo) REFERENCES coupon(couponNo);

 ALTER TABLE CouponDetail
 ADD CONSTRAINT CouponDetail_goodsNo_FK FOREIGN KEY (goodsNo) REFERENCES Goods(goodsNo);

 ALTER TABLE MemCoupon
 ADD CONSTRAINT MemCoupon_memNo_FK FOREIGN KEY (memNo) REFERENCES Member(memNo);

 ALTER TABLE MemCoupon
 ADD CONSTRAINT MemCoupon_couponNo_FK FOREIGN KEY (couponNo) REFERENCES coupon(couponNo);
 
  ALTER table Goods ADD CONSTRAINT goods_GoodstNo_FK FOREIGN KEY(goodstNo) REFERENCES  GoodsType(goodstNo);
 ALTER table Goods ADD CONSTRAINT goods_counterNo_FK FOREIGN KEY(counterNo) REFERENCES counter(counterNo);
    
    
ALTER table GoodsPhotos ADD CONSTRAINT gpNo_GoodsNo_FK FOREIGN KEY(goodsNo) REFERENCES Goods(goodsNo) ;
    
    
ALTER table StoreCarousel  ADD CONSTRAINT StoreCarousel_counterNo_FK FOREIGN KEY(counterNo) REFERENCES counter(counterNo);
ALTER table StoreCarousel  ADD CONSTRAINT StoreCarousel_disNo_FK FOREIGN KEY(disNo) REFERENCES Discount(disNo);
    
ALTER table MonthSettlement ADD CONSTRAINT MonthSettlement_counterNo_FK FOREIGN KEY(counterNo) REFERENCES counter(counterNo);

ALTER table CounterInform ADD CONSTRAINT CounterInform_counterNo_FK FOREIGN KEY(counterNo) REFERENCES counter(counterNo);

ALTER TABLE UsedPic
ADD CONSTRAINT UsedPic_usedNo_FK FOREIGN KEY (usedNo) REFERENCES Used(usedNo);


 ALTER TABLE GoodsTrack
 ADD CONSTRAINT GoodsTrack_memNo_FK FOREIGN KEY (memNo) REFERENCES Member(memNo);

 ALTER TABLE GoodsTrack
 ADD CONSTRAINT GoodsTrack_goodsNo_FK FOREIGN KEY (goodsNo) REFERENCES Goods(goodsNo);
 
  ALTER TABLE Used
 ADD CONSTRAINT Used_sellerNo_FK FOREIGN KEY (sellerNo) REFERENCES Member(memNo);
 
 ALTER table Used 
 ADD CONSTRAINT goods_classNo_FK FOREIGN KEY(classNo) REFERENCES  GoodsType(goodstNo);
 
 ALTER TABLE Followers
ADD CONSTRAINT followers_followersNo_FK FOREIGN KEY (followersNo) REFERENCES member(memNo),
ADD CONSTRAINT followers_counterNo_FK FOREIGN KEY (counterNo) REFERENCES counter(counterNo);

ALTER TABLE shoppingcartlist
ADD CONSTRAINT shoppingcartlist_memNo_FK FOREIGN KEY (memNo) REFERENCES member(memNo),
ADD CONSTRAINT shoppingcartlist_goodsNo_FK FOREIGN KEY (goodsNo) REFERENCES goods(goodsNo);

 ALTER TABLE usedComplaint
ADD CONSTRAINT usedComplaint_memNo_FK FOREIGN KEY (memNo) REFERENCES Member(memNo);

 ALTER TABLE usedComplaint
 ADD CONSTRAINT usedComplaint_usedOrderNo_FK FOREIGN KEY (usedOrderNo) REFERENCES UsedOrder(usedOrderNo);
 
 
 ALTER TABLE MemInform
ADD CONSTRAINT MemInform_memNO_FK FOREIGN KEY (memNo) REFERENCES Member(memNo);

ALTER TABLE ManagerAuth
ADD CONSTRAINT ManagerAuth_managerNo_FK FOREIGN KEY (managerNo) REFERENCES Manager(managerNo);

ALTER TABLE ManagerAuth
ADD CONSTRAINT ManagerAuth_authNo_FK FOREIGN KEY (authNo) REFERENCES Auth(authNo);
 
ALTER TABLE CounterCarousel
ADD CONSTRAINT CounterCarousel_counterNo_FK FOREIGN KEY (counterNo) REFERENCES Counter(counterNo);

ALTER TABLE CounterCarousel
ADD CONSTRAINT CounterCarousel_goodsNo_FK FOREIGN KEY (goodsNo) REFERENCES Goods(goodsNo);

ALTER TABLE CounterChat
ADD CONSTRAINT CounterChat_counterNo_FK FOREIGN KEY (counterNo) REFERENCES Counter(counterNo);

ALTER TABLE CounterChat
ADD CONSTRAINT CounterChat_memNo_FK FOREIGN KEY (memNo) REFERENCES Member(memNo);

ALTER TABLE GoodComplaint
ADD CONSTRAINT GoodComplaint_counterOrderNo_FK FOREIGN KEY (counterOrderNo) REFERENCES CounterOrder(counterOrderNo);

ALTER TABLE GoodComplaint
ADD CONSTRAINT GoodComplaint_memNo_FK FOREIGN KEY (memNo) REFERENCES Member(memNo);

ALTER TABLE UsedOrder
ADD CONSTRAINT UsedOrder_usedNo_FK FOREIGN KEY (usedNo) REFERENCES Used(usedNo);

ALTER TABLE UsedOrder
ADD CONSTRAINT UsedOrder_buyerNo_FK FOREIGN KEY (buyerNo) REFERENCES Member(memNo);

ALTER TABLE UsedChat
ADD CONSTRAINT UsedChat_sellerNo_FK FOREIGN KEY (sellerNo) REFERENCES Member(memNo);

ALTER TABLE UsedChat
ADD CONSTRAINT UsedChat_memNo_FK FOREIGN KEY (memNo) REFERENCES Member(memNo);
