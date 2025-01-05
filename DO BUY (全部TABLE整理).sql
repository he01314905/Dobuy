CREATE DATABASE IF NOT EXISTS dobuy;
USE dobuy;

CREATE TABLE counter (
    counterNo INT NOT NULL AUTO_INCREMENT , -- 主鍵自增編號
    counterAccount VARCHAR(20) NOT NULL UNIQUE,   -- 櫃位帳號
    counterName VARCHAR(45) NOT NULL,      -- 櫃位管理者姓名
    counterPassword VARCHAR(20) NOT NULL,  -- 櫃位密碼
    counterAddress VARCHAR(255) NOT NULL,  -- 櫃位地址
    counterPhone VARCHAR(10) NOT NULL,     -- 櫃位電話
    counterUid VARCHAR(10) NOT NULL,       -- 管理者身分證字號
    counterEmail VARCHAR(100) NOT NULL,    -- 櫃位電子信箱
    counterUbn VARCHAR(255) NOT NULL UNIQUE,      -- 櫃位統一編號
    counterCName VARCHAR(255) NOT NULL,    -- 櫃位名稱
    counterTypeNo INT NOT NULL,            -- 櫃位類別編號（外來鍵）
    counterInform VARCHAR(255),            -- 櫃位資訊介紹
    counterPic LONGBLOB,                   -- 櫃位商標圖片
    counterStatus TINYINT NOT NULL DEFAULT 1 CHECK (counterStatus IN (0, 1, 2, 3 )),        -- 櫃位狀態 (0: 停權, 1: 暫時停權, 2: 正常 )
    
    PRIMARY KEY (counterNo)                -- 設定主鍵
)
AUTO_INCREMENT = 1;

INSERT INTO counter 
(counterAccount, counterName, counterPassword, counterAddress, counterPhone, counterUid, counterEmail, counterUbn, counterCName, counterTypeNo, counterInform, counterStatus) 
VALUES
-- 女士精品
('user1', '張淑芬', '12345', '台北市大安區仁愛路123號', '0912345678', 'A123456789', 'ladybag01@example.com', '12345678', '女士精品館', 1, '高品質女士包包與配件櫃位', 3),
-- 時尚女裝
('user2', '林美惠', '12345', '新北市板橋區中山路456號', '0923456789', 'B123456789', 'fashion02@example.com', '23456789', '時尚女裝館', 2, '流行女裝與女鞋專櫃', 3),
-- 男士潮流
('user3', '王建宏', '12345', '台中市西屯區文心路789號', '0934567890', 'C123456789', 'menstyle01@example.com', '34567890', '男士潮流館', 3, '潮流男包與配件櫃位', 3),
-- 型男穿搭
('user4', '陳志明', '12345', '台南市中西區民族路321號', '0945678901', 'D123456789', 'mensfashion02@example.com', '45678901', '型男穿搭館', 4, '專注男裝與男鞋的櫃位', 3),
-- 美妝與保養
('user5', '劉慧君', '12345', '高雄市三民區博愛路654號', '0956789012', 'E123456789', 'beautycare01@example.com', '56789012', '美妝保養館', 5, '化妝品與保養品專賣', 3),
-- 家居科技
('user6', '黃志成', '12345', '桃園市中壢區新生路987號', '0967890123', 'F123456789', 'homeitech01@example.com', '67890123', '家居科技館', 6, '智能家居與科技商品櫃位', 3),
-- 女士精品
('user7', '李佳蓉', '12345', '台中市北屯區中清路159號', '0978901234', 'G123456789', 'ladybag02@example.com', '78901234', '女士精品館二館', 1, '高端女士包包專區', 2),
-- 時尚女裝
('user8', '蔡佩玲', '12345', '新竹市東區東門街753號', '0989012345', 'H123456789', 'fashion03@example.com', '89012345', '時尚女裝二館', 2, '時尚女裝與鞋品新系列', 3);


CREATE TABLE faq (
    faqNo INT NOT NULL AUTO_INCREMENT,    -- 自增主鍵
    ques VARCHAR(500) NOT NULL,           -- 常見問題
    ans VARCHAR(500) NOT NULL,            -- 問題解答
    counterNo INT NOT NULL,               -- 櫃位編號，外來鍵
    PRIMARY KEY (faqNo)                   -- 設定主鍵
) AUTO_INCREMENT = 100;

-- 插入 10 筆中文假資料
INSERT INTO faq (ques, ans, counterNo)
VALUES 
('1. 1+1等於多少？', '2', 1),
('2. 2+2等於多少？', '4', 2),
('3. 水的沸點是多少度？', '100度', 3),
('4. 地球上最大的動物是什麼？', '藍鯨', 4),
('5. 太陽從哪個方向升起？', '東方', 5),
('6. 一年有多少個月？', '12個月', 1),
('7. 一周有幾天？', '7天', 2),
('8. 中國的首都是哪裡？', '北京', 3),
('9. 世界上最大的沙漠是什麼？', '撒哈拉沙漠', 4),
('10. 人體有多少根骨頭？', '206根', 5);




CREATE TABLE counterType (
    counterTypeNo INT NOT NULL AUTO_INCREMENT, -- 櫃位類別編號，自增主鍵
    counterTName VARCHAR(100) NOT NULL,         -- 類別名稱
    
    PRIMARY KEY (counterTypeNo)                -- 設定主鍵
)
AUTO_INCREMENT = 1;
INSERT INTO counterType (counterTName) VALUES 
('精品'),   
('時尚女裝'),
('男士潮流'),
('型男穿搭'),
('美妝與保養'),
('家居科技');



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
    orderTotalBefore INT(6) NOT NULL,
    orderTotalAfter INT(6) NOT NULL,
    receiverName VARCHAR(10) NOT NULL,
    receiverAdr VARCHAR(100),
    receiverPhone VARCHAR(10) NOT NULL,
    orderTime TIMEstamp default now(),
    orderStatus TINYINT(1) DEFAULT 0 NOT NULL,
	memCouponNo INT 
	
) AUTO_INCREMENT = 1;

-- 以下為任國新增的櫃位訂單完成的假資料
INSERT INTO CounterOrder (
    counterNo, memNo, orderTotalBefore, orderTotalAfter, receiverName, receiverAdr, receiverPhone, orderTime, orderStatus, memCouponNo
) VALUES
(1, 5, 18000, 18000, '黃偉德', '台南市東區府前路2段10號', '0956789012', '2024-09-01 13:20:05', 2, NULL),
(1, 6, 16000, 16000, '林家瑋', '基隆市仁愛區中正路88號', '0967890123', '2024-09-05 08:15:50', 2, NULL),
(1, 7, 13000, 13000, '張婷婷', '桃園市中壢區元化路16號', '0978901234', '2024-09-10 10:22:30', 2, NULL),
(1, 8, 15000, 15000, '吳建國', '新竹市東區建設路88號', '0989012345', '2024-09-15 11:15:35', 2, NULL),
(1, 9, 14500, 14500, '劉志偉', '台北市大安區忠孝東路4段30號', '0990123456', '2024-09-18 14:50:20', 2, NULL),
(1, 10, 16500, 16500, '楊家璇', '新北市三重區中正北路12號', '0911122334', '2024-09-22 18:00:25', 2, NULL),
(1, 1, 10500, 10500, '王小明', '台北市信義路5段88號', '0912345678', '2024-09-25 14:22:30', 2, NULL),
(1, 2, 12000, 12000, '李美美', '新北市板橋區文化路一段300號', '0923456789', '2024-09-28 09:10:12', 2, NULL),
(1, 3, 15500, 15500, '張國強', '台中市南區建國路88號', '0934567890', '2024-10-01 11:45:25', 2, NULL),
(1, 4, 14500, 14500, '陳靜怡', '高雄市苓雅區中華路3段50號', '0945678901', '2024-10-05 16:33:12', 2, NULL),
(1, 5, 17000, 17000, '黃偉德', '台南市東區府前路2段10號', '0956789012', '2024-10-10 13:20:05', 2, NULL),
(1, 6, 16000, 16000, '林家瑋', '基隆市仁愛區中正路88號', '0967890123', '2024-10-12 08:15:50', 2, NULL),
(1, 7, 14000, 14000, '張婷婷', '桃園市中壢區元化路16號', '0978901234', '2024-10-15 10:22:30', 2, NULL),
(1, 8, 16000, 16000, '吳建國', '新竹市東區建設路88號', '0989012345', '2024-10-18 11:15:35', 2, NULL),
(1, 9, 13500, 13500, '劉志偉', '台北市大安區忠孝東路4段30號', '0990123456', '2024-10-22 14:50:20', 2, NULL),
(1, 10, 17000, 17000, '楊家璇', '新北市三重區中正北路12號', '0911122334', '2024-10-28 18:00:25', 2, NULL);

-- 以上為任國新增的櫃位訂單完成的假資料
ALTER TABLE CounterOrder
ADD couponNo INT(10) NOT NULL DEFAULT 0;

CREATE TABLE CounterOrderDetail (
    counterOrderDetailNo INT(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    counterOrderNo INT(10) NOT NULL,    -- FK
    goodsNo INT(10) NOT NULL, -- FK
    goodsNum INT(5) NOT NULL,
    productPrice INT(5) NOT NULL,
    productDisPrice INT(5) NOT NULL,
    memCouponNo INT(10)

) AUTO_INCREMENT = 1;

INSERT INTO CounterOrderDetail (counterOrderNo, goodsNo, goodsNum, productPrice, productDisPrice, memCouponNo) VALUES
(1, 1, 1, 18000, 18000, NULL),
(2, 2, 2, 16000, 16000, NULL),
(3, 1, 2, 13000, 13000, NULL),
(4, 2, 1, 15000, 15000, NULL),
(5, 1, 3, 14500, 14500, NULL),
(6, 2, 2, 16500, 16500, NULL),
(7, 1, 1, 10500, 10500, NULL),
(8, 2, 3, 12000, 12000, NULL),
(9, 1, 2, 15500, 15500, NULL),
(10, 2, 1, 14500, 14500, NULL),
(11, 1, 3, 17000, 17000, NULL),
(12, 2, 2, 16000, 16000, NULL),
(13, 1, 1, 14000, 14000, NULL),
(14, 2, 3, 16000, 16000, NULL),
(15, 1, 2, 13500, 13500, NULL),
(16, 2, 1, 17000, 17000, NULL);


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
    couponNo INT NOT NULL,  -- 優惠券編號 FK
    status INT DEFAULT 0  -- 0:未使用, 1:已使用
)
AUTO_INCREMENT = 1;

INSERT INTO Coupon (couponNo, counterNo, couponTitle, couponContext, couponStart, couponEnd, couponStatus, usageLimit, checkStatus)
VALUES
(1, 1, '冬季大特價', '8折優惠', '2024-11-01 00:00:00', '2024-12-30 23:59:59', 1, 100, 1),
(2, 2, '週年慶', '7折優惠', '2024-10-01 00:00:00', '2024-12-31 23:59:59', 1, 200, 1),
(3, 1, '新品促銷', '新商品9折', '2024-12-01 00:00:00', '2024-12-31 23:59:59', 1, 150, 1),
(4, 3, '黑五優惠', '全場5折', '2024-11-15 00:00:00', '2024-12-30 23:59:59', 1, 50, 1),
(5, 5, '大牌特惠', '全館5折', '2024-12-01 00:00:00', '2024-12-31 23:59:59', 1, 100, 1),
(6, 6, '限量專享', '限量商品7折', '2024-12-01 00:00:00', '2024-12-31 23:59:59', 1, 120, 1),
(7, 4, 'VIP專屬', 'VIP會員9折', '2024-10-01 00:00:00', '2025-01-01 23:59:59', 1, 300, 1),
(8, 2, '開幕酬賓', '開幕大優惠8折', '2024-10-10 00:00:00', '2024-12-27 23:59:59', 1, 200, 1),
(9, 3, '聖誕狂歡', '聖誕節全館85折', '2024-12-16 00:00:00', '2024-12-27 23:59:59', 1, 150, 0),
(10, 4, '新年好禮', '新年折扣9折', '2025-01-01 00:00:00', '2025-01-05 23:59:59', 1, 100, 0),
(11, 5, '美妝特賣', '精選面霜85折', '2024-12-10 00:00:00', '2024-12-31 23:59:59', 1, 80, 0),
(12, 6, '歲末大促', '指定商品75折', '2024-12-15 00:00:00', '2024-12-31 23:59:59', 1, 100, 0),
(13, 4, '跨年優惠', '精選商品88折', '2024-12-25 00:00:00', '2025-01-10 23:59:59', 1, 150, 0),
(14, 2, '新春特惠', '新春限定82折', '2025-01-15 00:00:00', '2025-02-15 23:59:59', 1, 200, 0),
(15, 3, '情人節活動', '情人節限定88折', '2025-02-01 00:00:00', '2025-02-14 23:59:59', 1, 100, 0);

INSERT INTO CouponDetail (couponDetailNo, couponNo, goodsNo, createdAt, updatedAt, counterContext, disRate)
VALUES
-- couponNo=1(counterNo=1)
(1, 1, 1, '2024-10-01 10:00:00', '2024-10-01 10:30:00', '1000', 0.80),
(2, 1, 2, '2024-10-02 09:00:00', '2024-10-02 09:15:00', '1000', 0.80),
(3, 1, 7, '2024-10-03 11:30:00', '2024-10-03 11:40:00', '10000', 0.80),
(21, 1, 8, '2024-10-04 08:00:00', '2024-10-04 08:20:00', '2000', 0.80),

-- couponNo=2(counterNo=2)
(4, 2, 3, '2024-10-05 08:00:00', '2024-10-05 08:30:00', '1000', 0.70),
(22, 2, 4, '2024-10-06 09:00:00', '2024-10-06 09:30:00', '2000', 0.70),

-- couponNo=3(counterNo=1)
(5, 3, 1, '2024-10-07 14:00:00', '2024-10-07 14:15:00', '10000', 0.90),
(6, 3, 7, '2024-10-06 13:00:00', '2024-10-06 13:45:00', '2000', 0.90),

-- couponNo=4(counterNo=3)
(7, 4, 11, '2024-11-16 15:00:00', '2024-11-16 15:30:00', '2000', 0.50),
(8, 4, 12, '2024-11-17 10:00:00', '2024-11-17 10:30:00', '2000', 0.50),

-- couponNo=5(counterNo=5)
(9, 5, 17, '2024-12-01 11:00:00', '2024-12-01 11:20:00', '500', 0.50),
(10, 5, 18, '2024-12-02 16:00:00', '2024-12-02 16:20:00', '500', 0.50),
(23, 5, 18, '2024-12-05 10:00:00', '2024-12-05 10:20:00', '500', 0.55),

-- couponNo=6(counterNo=6)
(11, 6, 19, '2024-12-02 10:00:00', '2024-12-02 10:15:00', '10000', 0.70),
(12, 6, 20, '2024-12-02 11:00:00', '2024-12-02 11:15:00', '10000', 0.70),
(25, 6, 19, '2024-12-06 15:00:00', '2024-12-06 15:30:00', '500', 0.80),

-- couponNo=7(counterNo=4)
(13, 7, 15, '2024-10-03 12:00:00', '2024-10-03 12:20:00', '10000', 0.90),
(14, 7, 16, '2024-10-03 13:00:00', '2024-10-03 13:20:00', '500', 0.90),
(24, 7, 15, '2024-10-10 12:00:00', '2024-10-10 12:20:00', '500', 0.85),

-- couponNo=8(counterNo=2)
(15, 8, 3, '2024-10-11 12:30:00', '2024-10-11 12:45:00', '500', 0.80),
(16, 8, 4, '2024-10-12 11:00:00', '2024-10-12 11:15:00', '10000', 0.80),

-- couponNo=9(counterNo=3)
(17, 9, 13, '2024-12-24 09:00:00', '2024-12-24 09:20:00', '500', 0.85),
(18, 9, 14, '2024-12-24 14:00:00', '2024-12-24 14:15:00', '10000', 0.85),

-- couponNo=10(counterNo=4)
(19, 10, 15, '2025-01-01 10:00:00', '2025-01-01 10:15:00', '2000', 0.90),
(20, 10, 16, '2025-01-02 11:00:00', '2025-01-02 11:15:00', '500', 0.90),

-- couponNo=11(counterNo=5)
(26, 11, 17, '2024-12-10 10:00:00', '2024-12-10 10:30:00', '1000', 0.85),
(27, 11, 18, '2024-12-10 11:00:00', '2024-12-10 11:30:00', '1000', 0.85),

-- couponNo=12(counterNo=6)
(28, 12, 19, '2024-12-15 09:00:00', '2024-12-15 09:30:00', '800', 0.75),
(29, 12, 20, '2024-12-15 10:00:00', '2024-12-15 10:30:00', '800', 0.75),

-- couponNo=13(counterNo=4)
(30, 13, 15, '2024-12-25 11:00:00', '2024-12-25 11:30:00', '1500', 0.88),
(31, 13, 16, '2024-12-25 12:00:00', '2024-12-25 12:30:00', '1500', 0.88),

-- couponNo=14(counterNo=2)
(32, 14, 3, '2025-01-15 13:00:00', '2025-01-15 13:30:00', '2000', 0.82),
(33, 14, 4, '2025-01-15 14:00:00', '2025-01-15 14:30:00', '2000', 0.82),

-- couponNo=15(counterNo=3)
(34, 15, 11, '2025-02-01 15:00:00', '2025-02-01 15:30:00', '1200', 0.88),
(35, 15, 12, '2025-02-01 16:00:00', '2025-02-01 16:30:00', '1200', 0.88);

INSERT INTO Discount (disNo, disTitle, disContext, disRate, disStatus, descLimit, createdAt, updatedAt, disStart, disEnd)
VALUES
(1, '雙11優惠', '全館1折', 0.10, 2, '單身貴族優先', '2024-10-01 10:00:00', '2024-10-01 11:00:00', '2024-11-01 00:00:00', '2024-11-11 23:59:59'),
(2, '聖誕節特賣', '全館7折', 0.70, 1, '叮叮噹', '2024-12-01 12:00:00', '2024-12-01 12:30:00', '2024-12-17 00:00:00', '2024-12-26 23:59:59'),
(3, '跨年特賣', '全館7折', 0.89, 0, '放煙火囉', '2024-12-01 12:00:00', '2024-12-01 12:30:00', '2024-12-27 00:00:00', '2024-12-31 23:59:59');


INSERT INTO MemCoupon (memNo, couponNo, status) VALUES
(1, 1, 0),
(1, 2, 0),
(1, 3, 0),
(1, 4, 0),
(2, 2, 0),
(2, 3, 0),
(3, 1, 0),
(3, 2, 0),
(3, 3, 0),
(4, 1, 0),
(4, 2, 0),
(4, 3, 0),
(5, 1, 0),
(5, 2, 0),
(5, 3, 0);


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
    storeCarouselNo INT NOT NULL PRIMARY KEY AUTO_INCREMENT,  -- 輪播資訊編號，主鍵
    counterNo INT NOT NULL,                                    -- 櫃位編號，外來鍵
    disNo INT NOT NULL,                                         -- 平台優惠編號，外來鍵
    carouselTime DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,   -- 輪播時間，自動生成當前時間戳
    carouselPic LONGBLOB                                        -- 輪播圖片
);

-- 插入 20 筆假資料

INSERT INTO CounterCarousel (CounterNo, CarouselTime, CarouselPic, GoodsNo) VALUES
(1, '2024-12-17 10:00:00', NULL, 1),
(1, '2024-12-17 11:00:00', NULL, 2),
(1, '2024-12-17 12:00:00', NULL, 3),
(1, '2024-12-17 13:00:00', NULL, 4),
(1, '2024-12-17 14:00:00', NULL, 5),
(1, '2024-12-17 15:00:00', NULL, 6),
(1, '2024-12-17 16:00:00', NULL, 7);

-- 抽成月結
CREATE TABLE MonthSettlement (
    monthSettlementNo INT NOT NULL PRIMARY KEY AUTO_INCREMENT,    -- 抽成月結編號，主鍵
    counterNo INT NOT NULL,                         -- 櫃位編號，外來鍵
    month VARCHAR(7) NOT NULL,                     -- 月份 (YYYY-MM)
    comm INT NOT NULL                               -- 總金額
   
);

-- 插入假資料
INSERT INTO MonthSettlement (counterNo, month, comm) VALUES
(1, '2024-04', 58000),
(1, '2024-05', 60000),
(1, '2024-06', 59000),
(1, '2024-07', 62000),
(1, '2024-08', 65000),
(2, '2024-04', 57000),
(2, '2024-05', 58000),
(2, '2024-06', 59000),
(2, '2024-07', 60000),
(2, '2024-08', 63000),
(3, '2024-04', 59000),
(3, '2024-05', 60000),
(3, '2024-06', 57000),
(3, '2024-07', 63000),
(3, '2024-08', 67000),
(4, '2024-04', 60000),
(4, '2024-05', 64000),
(4, '2024-06', 65000),
(4, '2024-07', 68000),
(4, '2024-08', 70000);

-- 灃晉
CREATE TABLE UsedPic (
    usedPicNo INT NOT NULL AUTO_INCREMENT PRIMARY KEY,  -- 二手商品照片明細編號
    usedNo INT NOT NULL,                                 -- 二手商品編號
    usedPicName VARCHAR(100),
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
    usedName VARCHAR(200) NOT NULL,                    -- 二手商品名稱
    usedProDesc VARCHAR(700) NOT NULL,                -- 二手商品描述
    usedNewness TINYINT(1),                           -- 商品新舊程度 (0:近全新, 1:7成新, 2:5成新, 3:3成新)
    usedPrice INT(6) CHECK (usedPrice > 0),           -- 二手商品單價, 必須大於0
    usedStocks INT(5) CHECK (usedStocks >= 0),        -- 商品庫存數量, 必須大於0
    usedLaunchedTime DATETIME DEFAULT CURRENT_TIMESTAMP,  -- 二手商品上架時間, 預設為當前時間
    soldTime DATETIME DEFAULT NULL,                   -- 二手商品下架時間, 預設為NULL且允許為空
    usedState TINYINT(2) NOT NULL                    -- 二手商品狀態 (0:未上架, 1:已上架)
    -- FOREIGN KEY (classNo) REFERENCES GoodsType(goodstNo),  -- 商品類別外來鍵
    -- FOREIGN KEY (sellerNo) REFERENCES Member(memNo)        -- 賣家會員編號外來鍵
)AUTO_INCREMENT = 1;

INSERT INTO Used (classNo, sellerNo, usedName, usedProDesc, usedNewness, usedPrice, usedStocks, usedState)
VALUES
-- 女士包包
(1, 1, '香奈兒經典手提包', '近乎全新，黑色香奈兒經典款，附防塵袋', 0, 35000, 1, 1),
(1, 2, 'Gucci手提包', 'Gucci粉色手提包，少量使用，保存良好', 1, 22000, 2, 1),
(1, 3, 'Prada托特包', 'Prada大容量托特包，少量使用痕跡', 1, 18000, 3, 1),
(1, 4, 'Coach手拿包', 'Coach小型手拿包，經典款', 0, 8000, 2, 1),

-- 女士服裝
(2, 5, '夏季雪紡長裙', '輕薄透氣雪紡材質 L號，適合夏季穿著', 1, 800, 5, 1),
(2, 6, 'ZARA連衣裙', 'ZARA春季系列 均碼，略有使用痕跡，7成新', 2, 1500, 3, 0),
(2, 7, '優衣庫襯衫', '白色襯衫  L號，百搭款式', 0, 600, 4, 1),
(2, 8, 'H&M牛仔褲', '經典藍色牛仔褲  M號，8成新', 1, 900, 6, 1),

-- 女士鞋
(3, 9, 'Nike運動鞋', '白色款，舒適耐穿 CM24.5，僅試穿過一次', 0, 3200, 4, 1),
(3, 10, '高跟鞋', '紅色高跟鞋，5成新 CM24，適合宴會穿搭', 2, 700, 1, 1),
(3, 1, 'Adidas慢跑鞋', '黑白配色 CM22，適合戶外運動', 1, 2500, 3, 1),
(3, 2, '涼鞋', '輕便涼鞋 CM24，7成新', 2, 400, 5, 0),

-- 女士配件
(4, 3, '珍珠項鍊', '經典款珍珠項鍊，保存完好', 0, 4500, 2, 1),
(4, 4, '手工耳環', '獨家手工製作，少量使用痕跡', 1, 1200, 6, 0),
(4, 5, '絲巾', '多用途絲巾，經典款式', 0, 900, 5, 1),
(4, 6, '皮手套', '黑色皮手套，保暖實用', 1, 1100, 2, 1),

-- 男士包包
(5, 7, 'LV男士斜背包', '經典款斜背包，近乎全新', 0, 28000, 1, 1),
(5, 8, 'Adidas運動包', '多功能運動包，少量磨損痕跡', 2, 1800, 3, 1),
(5, 9, '黑色公文包', '商務款公文包，輕微使用痕跡', 1, 15000, 1, 0),
(5, 10, '皮背包', '深棕色皮背包，容量大', 0, 12000, 2, 1),

-- 男士服裝
(6, 1, 'Polo衫', '純棉材質，適合夏季穿著 L號，8成新', 1, 600, 10, 1),
(6, 2, '西裝外套', '黑色經典款西裝外套 XL，保存良好', 2, 2500, 2, 0),

-- 男士鞋
(7, 3, '運動鞋', 'Adidas運動鞋 26號半 ，適合跑步或健身', 1, 2800, 5, 1),
(7, 4, '正式皮鞋', '黑色正裝皮鞋 27號半，少量使用痕跡', 2, 3500, 1, 0),

-- 男士配件
(8, 5, '男士錶', '經典款男士腕錶，保存良好', 0, 15000, 1, 1),
(8, 6, '皮帶', '棕色皮帶，輕微磨損痕跡', 2, 1200, 3, 1),

-- 美妝保養
(9, 7, 'La Mer面霜', '高端護膚品，僅使用過幾次', 0, 5200, 2, 1),
(9, 8, '香水套組', 'Dior香水套組，8成新，包含原包裝', 1, 3500, 1, 1),

-- 家居與科技
(10, 9, '藍牙音箱', 'JBL藍牙音箱，音質優良，保存完好', 0, 3200, 2, 1),
(10, 10, '掃地機器人', '自動掃地機器人，運作正常，少量刮痕', 2, 5800, 1, 0);

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

-- 建立 ShoppingCartList 表格
CREATE TABLE ShoppingCartList (
    shoppingcartListNo INT NOT NULL PRIMARY KEY AUTO_INCREMENT, -- 自增主鍵 PK
    memNo INT(10) ,                          -- 會員編號 FK
    goodsNo INT(10) ,                        -- 商品編號 FK
    counterNo INT ,               		 	 -- 櫃位編號
    gpPhotos1 LONGBLOB,                      -- 商品主圖(必填)
    goodsName VARCHAR(100),                  -- 商品名稱
    goodsPrice INT(5)  CHECK (goodsPrice > 0), -- 商品單價 > 0
    goodsNum INT(5)  CHECK (goodsNum > 0),   -- 商品數量 > 0
    orderTotalprice INT(6)  CHECK (orderTotalprice > 0) -- 訂單總金額 > 0
);

-- 插入 ShoppingCartList 表格的假資料
INSERT INTO shoppingcartList (
memNo, goodsNo,counterNo, gpPhotos1, goodsName, goodsPrice, goodsNum, orderTotalprice
) VALUES
-- (1, 1, 1, NULL, '路易威登 LOUIS VUITTON Double Zip Pochette Reverse 帆布 多夾層手拿 斜背包 M69203 防塵袋/背帶', 32800, 1, 32800),
-- (1, 2, 1, NULL, '路易威登 LOUIS VUITTON Cabas Rivington 托特包 N41108 棋盤格托特', 31800, 1, 31800),
(1, 7, 1, NULL, '路易威登 LOUIS VUITTON Epi 手鍊紅繩', 9800, 1, 9800),
-- (1, 8, 1, NULL, '路易威登 LOUIS VUITTON 金頭 老花+黑 雙面用皮帶', 18800, 1, 18800),
 (1, 3, 2, NULL, 'KIMHEKIM｜NEO-MALEVICH V領撞色腰帶洋裝', 24400, 1, 24400);
-- (1, 4, 2, NULL, 'KIMHEKIM｜VENUS 高腰開衩落地喇叭褲', 17800, 1, 17800),
-- (1, 9, 3, NULL, 'Valentino Garavani 范倫鐵諾-白色VLTN印花漸層彩虹英文字腰包/胸背包', 15000, 1, 15000);


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







    


CREATE TABLE Auth (
    authNo INT NOT NULL PRIMARY KEY AUTO_INCREMENT,      -- 權限編號
    authTitle VARCHAR(255) NOT NULL,                     -- 權限名稱
    authContext VARCHAR(255) NOT NULL                    -- 權限內容
);

insert into auth(authTitle,authContext)  values("超級管理員","管理所有人"), ("前台管理員","管理前台"), ("後台管理員","管理後台"), ("櫃位管理員","管理櫃位"), ("客訴管理員","管理客訴") ;

CREATE TABLE ManagerAuth (
    managerNo INT NOT NULL  ,   -- 管理員編號 FK
    authNo INT NOT NULL  ,    -- 權限編號 FK
    PRIMARY KEY (managerNo, authNo)   -- 複合主鍵
  
);

insert into managerAuth(managerNo,authNo) values(1,1),(2,2),(3,3),(4,4),(5,5);
    
CREATE TABLE CounterCarousel (
    counterCarouselNo INT NOT NULL AUTO_INCREMENT PRIMARY KEY,  -- 輪播資訊編號
    counterNo INT NOT NULL,                                     -- 櫃位編號 FK
    carouselTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,  -- 上傳時間，默認為新增時的當前時間
    carouselPic LONGBLOB NULL,                                  -- 輪播圖片
    goodsNo INT NOT NULL                                        -- 商品編號 FK
    -- FOREIGN KEY (counterNo) REFERENCES Counter(counterNo)      -- 櫃位輪播資訊外來鍵
    -- FOREIGN KEY (goodsNo) REFERENCES Goods(goodsNo)           -- 商品外來鍵
);

-- 創建GoodComplaint表
CREATE TABLE GoodComplaint (
    counterComplaintNo INT NOT NULL AUTO_INCREMENT PRIMARY KEY,  -- 櫃位客訴編號
    memNo INT ,                                                  -- 會員編號  FK
    counterNo INT ,                                              -- 櫃位編號  FK
    counterOrderNo INT NOT NULL,                                 -- 櫃位訂單編號 
    complaintDate timestamp,                             -- 客訴時間
    complaintReason VARCHAR(255) NOT NULL,                       -- 客訴原因
	complaintPhotos LongBlob NUll,                           -- 客訴商品圖片
    complaintStatus TINYINT Default 0,                        -- 客訴狀態(0: 待處理)(1: 處理中)(2: 處理完畢)
    complaintMsg VARCHAR(500)                                -- 客訴回覆內容
   --  FOREIGN KEY (CouterNo) REFERENCES Counter(CouterNo)       -- 商品客訴外來鍵
   --  FOREIGN KEY (MemNo) REFERENCES Member(MemNo)              -- 商品客訴外來鍵
);

-- 設置分隔符
DELIMITER //

-- 創建插入觸發器
CREATE TRIGGER before_insert_good_complaint
BEFORE INSERT ON GoodComplaint
FOR EACH ROW
BEGIN
    SET NEW.complaintDate = CURRENT_TIMESTAMP;
    -- 如果沒有指定 informRead，預設為 0
    IF NEW.complaintStatus IS NULL THEN
        SET NEW.complaintStatus = 0;
    END IF;
END//



-- 恢復分隔符
DELIMITER ;


CREATE TABLE UsedOrder (
    usedOrderNo INT(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    usedNo INT(10) NOT NULL,
    buyerNo INT(10) NOT NULL,
    usedOrderTime DATETIME,
    usedPrice INT(6) NOT NULL CHECK (usedPrice > 0),
    usedCount INT(5) NOT NULL CHECK (usedCount > 0),
    deliveryStatus TINYINT(6) NOT NULL DEFAULT 5,
    receiverName VARCHAR(200) NOT NULL,
    receiverAdr VARCHAR(200) NOT NULL,
    receiverPhone VARCHAR(200) NOT NULL,
    sellerSatisfication TINYINT(5),
    sellerCommentContent VARCHAR(500),
    sellerCommentDate DATETIME,  -- 评论日期字段
    usedTotalPrice INT(10) NOT NULL CHECK (usedTotalPrice > 0)
);

DELIMITER $$
CREATE TRIGGER before_usedorder_update
BEFORE UPDATE ON UsedOrder
FOR EACH ROW
BEGIN
    IF (NEW.sellerSatisfication IS NOT NULL AND NEW.sellerCommentContent IS NOT NULL) 
       AND (OLD.sellerCommentDate IS NULL) THEN
        SET NEW.sellerCommentDate = NOW();
    END IF;
END $$
DELIMITER ;



-- 創建Notice表
CREATE TABLE Notice (
    memNoticeNo INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
    memNo INT,
    noticeContent VARCHAR(1000) NOT NULL,                         
    noticeDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    noticeRead TINYINT(1) DEFAULT 0 COMMENT '0:已讀, 1:未讀'
);

-- 設置分隔符
DELIMITER //

-- 創建插入觸發器
CREATE TRIGGER before_insert_notice
BEFORE INSERT ON Notice
FOR EACH ROW
BEGIN
    IF NEW.noticeRead IS NULL THEN
        SET NEW.noticeRead = 0;
    END IF;
END//

-- 恢復分隔符
DELIMITER ;

-- 創建CounterInform表
CREATE TABLE CounterInform (
    counterInformNo INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
    memNo INT,
    counterNo INT,
    informMsg VARCHAR(1000) NOT NULL,                         
    informDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    informRead TINYINT(1) DEFAULT 0 COMMENT '0:已讀, 1:未讀'
);

-- 設置分隔符
DELIMITER //

-- 創建插入觸發器
CREATE TRIGGER before_insert_counter_inform
BEFORE INSERT ON CounterInform
FOR EACH ROW
BEGIN
    SET NEW.informDate = CURRENT_TIMESTAMP;
    IF NEW.informRead IS NULL THEN
        SET NEW.informRead = 0;
    END IF;
END//

-- 創建更新觸發器
CREATE TRIGGER before_update_counter_inform
BEFORE UPDATE ON CounterInform
FOR EACH ROW
BEGIN
    SET NEW.informDate = CURRENT_TIMESTAMP;
END//

-- 恢復分隔符
DELIMITER ;




-- 創建 Followers 表格
CREATE TABLE Followers (
    trackListNo INT(10) AUTO_INCREMENT PRIMARY KEY,   -- 設定 trackListNo 為自動遞增的主鍵
    followersNo INT(10) NOT NULL,                      -- 會員編號
    counterNo INT(10) NOT NULL,                        -- 櫃位編號
    FOREIGN KEY (followersNo) REFERENCES Member (memNo),  -- 設定外來鍵，指向 Member 表的 Mem_No
    FOREIGN KEY (counterNo) REFERENCES Counter(counterNo)  -- 設定外來鍵，指向 Counter 表的 Counter_No
);

-- 插入 10 筆假資料，將 counterNo 改為 1~10 依序排列
INSERT INTO Followers (followersNo, counterNo) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8);


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

 
 
 ALTER TABLE CounterInform
 ADD CONSTRAINT CounterInform_counterNo_FK FOREIGN KEY (counterNo) REFERENCES counter(counterNo);
 
 ALTER TABLE CounterInform
 ADD CONSTRAINT CounterInform_memNo_FK FOREIGN KEY (memNo) REFERENCES member(memNo);
 
 ALTER TABLE Notice
ADD CONSTRAINT Notice_memNO_FK FOREIGN KEY (memNo) REFERENCES Member(memNo);

ALTER TABLE ManagerAuth
ADD CONSTRAINT ManagerAuth_managerNo_FK FOREIGN KEY (managerNo) REFERENCES Manager(managerNo);

ALTER TABLE ManagerAuth
ADD CONSTRAINT ManagerAuth_authNo_FK FOREIGN KEY (authNo) REFERENCES Auth(authNo);
 
ALTER TABLE CounterCarousel
ADD CONSTRAINT CounterCarousel_counterNo_FK FOREIGN KEY (counterNo) REFERENCES Counter(counterNo);

ALTER TABLE CounterCarousel
ADD CONSTRAINT CounterCarousel_goodsNo_FK FOREIGN KEY (goodsNo) REFERENCES Goods(goodsNo);

ALTER TABLE GoodComplaint
ADD CONSTRAINT GoodComplaint_counterNo_FK FOREIGN KEY (counterNo) REFERENCES Counter(counterNo);

ALTER TABLE GoodComplaint
ADD CONSTRAINT GoodComplaint_memNo_FK FOREIGN KEY (memNo) REFERENCES Member(memNo);



ALTER TABLE UsedOrder
ADD CONSTRAINT UsedOrder_usedNo_FK FOREIGN KEY (usedNo) REFERENCES Used(usedNo);

ALTER TABLE UsedOrder
ADD CONSTRAINT UsedOrder_buyerNo_FK FOREIGN KEY (buyerNo) REFERENCES Member(memNo);



-- 以下昱夆新增 練習用
CREATE TABLE NewCouponDetails (
    couponDetailNo INT PRIMARY KEY AUTO_INCREMENT, -- 唯一主键
    couponNo INT NOT NULL,                          -- 优惠券编号
    createdAt DATETIME NOT NULL,                    -- 创建时间
    updatedAt DATETIME NOT NULL,                    -- 更新时间
    thresholdAmount DECIMAL(10, 2) NOT NULL,        -- 满额条件 (如满1000元)
    discountAmount DECIMAL(10, 2) NOT NULL,         -- 折扣金额 (如减100元)
    discountRate DECIMAL(5, 2) NOT NULL             -- 折扣率 
);


CREATE TABLE NewCoupons (
    couponNo INT PRIMARY KEY AUTO_INCREMENT,     -- 优惠券编号
    counterNo INT NOT NULL,                      -- 柜位编号
    couponTitle VARCHAR(255) NOT NULL,           -- 优惠券标题
    couponContext VARCHAR(500) NOT NULL,         -- 优惠券内容描述
    couponStart DATETIME NOT NULL,               -- 优惠券开始时间
    couponEnd DATETIME NOT NULL,                 -- 优惠券结束时间
    couponStatus TINYINT NOT NULL,               -- 优惠券状态 (0: 无效, 1: 可用, 2: 过期)
    usageLimit INT NOT NULL,                     -- 使用限制次数
    checkStatus TINYINT NOT NULL                 -- 检查状态 (0: 未审核, 1: 已审核)
);


INSERT INTO NewCouponDetails (couponNo, createdAt, updatedAt, thresholdAmount, discountAmount, discountRate) VALUES
(1, '2024-12-10 08:00:00', '2024-12-10 09:00:00', 8000, 1000, 0.85), -- 滿8000打85折再減1000
(2, '2024-12-11 10:00:00', '2024-12-11 11:00:00', 12000, 0, 0.80), -- 滿12000打8折
(3, '2024-12-12 12:00:00', '2024-12-12 13:00:00', 15000, 2000, 1.00), -- 滿15000減2000
(4, '2024-12-13 14:00:00', '2024-12-13 15:00:00', 10000, 1500, 0.90), -- 滿10000打9折再減1500
(5, '2024-12-14 16:00:00', '2024-12-14 17:00:00', 18000, 0, 0.85), -- 滿18000打85折
(6, '2024-12-15 09:00:00', '2024-12-15 10:00:00', 25000, 5000, 1.00), -- 滿25000減5000
(7, '2024-12-16 10:00:00', '2024-12-16 11:00:00', 6000, 800, 1.00), -- 滿6000減800
(8, '2024-12-17 11:00:00', '2024-12-17 12:00:00', 15000, 0, 0.90), -- 滿15000打9折
(9, '2024-12-18 12:00:00', '2024-12-18 13:00:00', 22000, 4000, 1.00), -- 滿22000減4000
(10, '2024-12-19 13:00:00', '2024-12-19 14:00:00', 30000, 7000, 1.00), -- 滿30000減7000
(11, '2024-12-20 14:00:00', '2024-12-20 15:00:00', 5000, 500, 0.92), -- 滿5000打92折再減500
(12, '2024-12-21 15:00:00', '2024-12-21 16:00:00', 7000, 1000, 1.00), -- 滿7000減1000
(13, '2024-12-22 16:00:00', '2024-12-22 17:00:00', 9000, 1500, 1.00), -- 滿9000減1500
(14, '2024-12-23 17:00:00', '2024-12-23 18:00:00', 20000, 0, 0.80), -- 滿20000打8折
(15, '2024-12-24 18:00:00', '2024-12-24 19:00:00', 35000, 8000, 1.00), -- 滿35000減8000
(16, '2024-12-25 08:00:00', '2024-12-25 09:00:00', 8000, 1200, 1.00), -- 滿8000減1200
(17, '2024-12-26 10:00:00', '2024-12-26 11:00:00', 10000, 0, 0.88), -- 滿10000打88折
(18, '2024-12-27 12:00:00', '2024-12-27 13:00:00', 12000, 2000, 1.00), -- 滿12000減2000
(19, '2024-12-28 14:00:00', '2024-12-28 15:00:00', 25000, 4500, 1.00), -- 滿25000減4500
(20, '2024-12-29 16:00:00', '2024-12-29 17:00:00', 40000, 0, 0.75); -- 滿40000打75折

INSERT INTO NewCoupons (couponNo, counterNo, couponTitle, couponContext, couponStart, couponEnd, couponStatus, usageLimit, checkStatus) VALUES
(1, 1, '滿8000打85折再減1000', '消費滿8000元享85折優惠，再減1000元', '2024-12-10 00:00:00', '2024-12-31 23:59:59', 1, 200, 1),
(2, 2, '滿12000打8折', '消費滿12000元享8折優惠', '2024-12-11 00:00:00', '2024-12-31 23:59:59', 1, 150, 1),
(3, 3, '滿15000減2000', '消費滿15000元立減2000元', '2024-12-12 00:00:00', '2024-12-31 23:59:59', 1, 100, 1),
(4, 4, '滿10000打9折再減1500', '消費滿10000元享9折優惠，再減1500元', '2024-12-13 00:00:00', '2024-12-31 23:59:59', 1, 200, 1),
(5, 5, '滿18000打85折', '消費滿18000元享85折優惠', '2024-12-14 00:00:00', '2024-12-31 23:59:59', 0, 200, 1),
(6, 6, '滿25000減5000', '消費滿25000元立減5000元', '2024-12-15 00:00:00', '2024-12-31 23:59:59', 1, 150, 1),
(7, 1, '滿6000減800', '消費滿6000元立減800元', '2024-12-16 00:00:00', '2024-12-31 23:59:59', 0, 200, 1),
(8, 2, '滿15000打9折', '消費滿15000元享9折優惠', '2024-12-17 00:00:00', '2024-12-31 23:59:59', 1, 150, 1),
(9, 3, '滿22000減4000', '消費滿22000元立減4000元', '2024-12-18 00:00:00', '2024-12-31 23:59:59', 1, 120, 1),
(10, 4, '滿30000減7000', '消費滿30000元立減7000元', '2024-12-19 00:00:00', '2024-12-31 23:59:59', 1, 100, 1),
(11, 5, '滿5000打92折再減500', '消費滿5000元享92折優惠，再減500元', '2024-12-20 00:00:00', '2024-12-31 23:59:59', 1, 250, 1),
(12, 6, '滿7000減1000', '消費滿7000元立減1000元', '2024-12-21 00:00:00', '2024-12-31 23:59:59', 1, 150, 1),
(13, 1, '滿9000減1500', '消費滿9000元立減1500元', '2024-12-22 00:00:00', '2024-12-31 23:59:59', 1, 150, 1),
(14, 2, '滿20000打8折', '消費滿20000元享8折優惠', '2024-12-23 00:00:00', '2024-12-31 23:59:59', 1, 200, 1),
(15, 3, '滿35000減8000', '消費滿35000元立減8000元', '2024-12-24 00:00:00', '2024-12-31 23:59:59', 1, 100, 1),
(16, 4, '滿8000減1200', '消費滿8000元立減1200元', '2024-12-25 00:00:00', '2024-12-31 23:59:59', 0, 200, 1),
(17, 5, '滿10000打88折', '消費滿10000元享88折優惠', '2024-12-26 00:00:00', '2024-12-31 23:59:59', 1, 150, 1),
(18, 6, '滿12000減2000', '消費滿12000元立減2000元', '2024-12-27 00:00:00', '2024-12-31 23:59:59', 1, 150, 1),
(19, 1, '滿25000減4500', '消費滿25000元立減4500元', '2024-12-28 00:00:00', '2024-12-31 23:59:59', 0, 100, 1),
(20, 2, '滿40000打75折', '消費滿40000元享75折優惠', '2024-12-29 00:00:00', '2024-12-31 23:59:59', 1, 50, 1);


CREATE TABLE emailverification (
    id INT AUTO_INCREMENT PRIMARY KEY,      -- 自增主鍵
    email VARCHAR(255),              		-- 郵箱地址
    verificationCode VARCHAR(255),          -- 驗證碼
    isVerified TINYINT DEFAULT 0,           -- 是否已驗證（0: 未驗證, 1: 已驗證）
    sentTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP -- 發送驗證碼的時間，預設為當前時間
);

CREATE TABLE newcounterorder (
    cOrderNo INT NOT NULL AUTO_INCREMENT,         -- 櫃位訂單編號 (主鍵)
    counterNo INT NOT NULL,                       -- 櫃位編號 (外鍵)
    memNo INT NOT NULL,                           -- 會員編號 (外鍵)
    orderTotalPriceBefore INT NOT NULL CHECK (orderTotalPriceBefore > 0), -- 訂單總金額(折前)
    orderTotalPriceAfter INT NOT NULL CHECK (orderTotalPriceAfter > 0),   -- 訂單總金額(折後)
    receiverName VARCHAR(10) NOT NULL,           -- 收件人姓名
    receiverAdr VARCHAR(100) NOT NULL,           -- 收件人地址
    receiverPhone VARCHAR(10) NOT NULL,          -- 收件人電話
	couponNo INT(10),                   -- 會員優惠券編號 (FK)
    orderTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 訂單成立時間
    orderStatus TINYINT(1) NOT NULL,             -- 訂單狀態 (0:未出貨, 1:已出貨, 2:完成訂單, 3:退貨, 4:作廢),
    PRIMARY KEY (cOrderNo),
    FOREIGN KEY (counterNo) REFERENCES counter(counterNo), -- 外鍵約束
    FOREIGN KEY (memNo) REFERENCES member(memNo)           -- 外鍵約束
);


CREATE TABLE newcounterorderdetail (
    counterOrderDetailNo INT(10) NOT NULL AUTO_INCREMENT, -- 明細主鍵 (PK)
    cOrderNo INT(10) NOT NULL,          -- 櫃位訂單編號
    goodsNo INT(10) NOT NULL,           -- 商品編號 (FK)
    goodsNum INT(5) NOT NULL CHECK (goodsNum > 0),       -- 商品數量 (必須大於0)
    goodsPrice INT(5) NOT NULL CHECK (goodsPrice > 0),   -- 商品單價 (必須大於0)

    PRIMARY KEY (counterOrderDetailNo), -- 主鍵: counterOrderDetailNo
    FOREIGN KEY (cOrderNo) REFERENCES newcounterorder(cOrderNo), -- 外鍵關聯至 Counter_Order
    FOREIGN KEY (goodsNo) REFERENCES goods(goodsNo)           -- 外鍵關聯至 Goods
);


ALTER TABLE counterorder
CHANGE COLUMN couPonNo couponNo INT;

修改counterorder表格欄位
-- 以上昱夆新增 練習用

