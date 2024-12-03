package com.goods.model;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.goods.model.GoodsRepository;
import com.goods.model.GoodsVO;

@SpringBootApplication
public class Test_Application_CommandLineRunner implements CommandLineRunner {

    @Autowired
    GoodsRepository repository;

    @Autowired
    private SessionFactory sessionFactory;

    public static void main(String[] args) {
        SpringApplication.run(Test_Application_CommandLineRunner.class);
    }

    @Override
    public void run(String... args) throws Exception {

        // ● 新增
//        GoodsVO goodsVO1 = new GoodsVO();
////        goodsVO1.setGoodstNo(3);
////        goodsVO1.setCounterNo(4);
//        goodsVO1.setGoodsName("經典手錶");
//        goodsVO1.setGoodsDescription("精緻的手錶，適合各種場合佩戴，永不過時的設計");
//        goodsVO1.setGoodsPrice(4500);
//        goodsVO1.setGoodsAmount(100);
//        goodsVO1.setGoodsStatus((byte) 0);  // 商品狀態：0 - 下架
//        goodsVO1.setCheckStatus((byte) 0);  // 審核狀態：0 - 審核中
//        goodsVO1.setGoodsDate(java.sql.Timestamp.valueOf("2024-11-01 10:00:00"));
//        goodsVO1.setGoodsEnddate(java.sql.Timestamp.valueOf("2024-11-22 10:00:00"));
//        // 這裡可以設置圖片（gpPhotos1, gpPhotos2 等）為 null 或有效的 LONGBLOB
//        repository.save(goodsVO1);
//
////        // ● 修改
//        GoodsVO goodsVO2 = new GoodsVO();
//        goodsVO2.setGoodsNo(21); // 設定要修改的商品編號
////        goodsVO2.setGoodstNo(3);
////        goodsVO2.setCounterNo(4);
//        goodsVO2.setGoodsName("更新版經典手錶");
//        goodsVO2.setGoodsDescription("全新設計，更多精緻的細節");
//        goodsVO2.setGoodsPrice(4600); // 修改價格
//        goodsVO2.setGoodsAmount(90);  // 修改庫存數量
//        goodsVO2.setGoodsStatus((byte) 1);  // 商品狀態：1 - 已上架
//        goodsVO2.setCheckStatus((byte) 1);  // 審核狀態：1 - 通過
//        goodsVO2.setGoodsDate(java.sql.Timestamp.valueOf("2024-11-01 10:00:00"));
//        goodsVO2.setGoodsEnddate(java.sql.Timestamp.valueOf("2024-11-22 10:00:00"));
//        repository.save(goodsVO2);
//
//        // ● 刪除
//        // 刪除編號為 21 的商品
////        repository.deleteByGoodsno(21);
//
//        // ● 查詢 - 根據商品編號查詢
//        Optional<GoodsVO> optional = repository.findById(2);
//        if (optional.isPresent()) {
//            GoodsVO goodsVO3 = optional.get();
//            System.out.println("商品編號: " + goodsVO3.getGoodsNo());
////            System.out.println("商品類別編號: " + goodsVO3.getGoodstNo());
////            System.out.println("櫃位編號: " + goodsVO3.getCounterNo());
//            System.out.println("商品名稱: " + goodsVO3.getGoodsName());	
//            System.out.println("商品敘述: " + goodsVO3.getGoodsDescription());
//            System.out.println("商品單價: " + goodsVO3.getGoodsPrice());
//            System.out.println("商品庫存: " + goodsVO3.getGoodsAmount());
//            System.out.println("商品狀態: " + goodsVO3.getGoodsStatus());
//            System.out.println("審核狀態: " + goodsVO3.getCheckStatus());
//            System.out.println("上架日期: " + goodsVO3.getGoodsDate());
//            System.out.println("下架日期: " + goodsVO3.getGoodsEnddate());
//        }
//
//        // ● 查詢 - 查詢所有商品
//        List<GoodsVO> list = repository.findAll();
//        for (GoodsVO aGoods : list) {
//            System.out.print(aGoods.getGoodsNo() + ", ");
////            System.out.print(aGoods.getGoodstNo() + ", ");
////            System.out.print(aGoods.getCounterNo() + ", ");
//            System.out.print(aGoods.getGoodsName() + ", ");
//            System.out.print(aGoods.getGoodsDescription() + ", ");
//            System.out.print(aGoods.getGoodsPrice() + ", ");
//            System.out.print(aGoods.getGoodsAmount() + ", ");
//            System.out.print(aGoods.getGoodsStatus() + ", ");
//            System.out.print(aGoods.getCheckStatus() + ", ");
//            System.out.print(aGoods.getGoodsDate() + ", ");
//            System.out.print(aGoods.getGoodsEnddate());
//            System.out.println();
//        }
//
//        // ● 查詢 - 根據條件查詢
//        List<GoodsVO> list2 = repository.findByOthers(2, "真皮皮包", 2999);
//        for (GoodsVO aGoods : list2) {
//            System.out.print(aGoods.getGoodsNo() + ", ");
////            System.out.print(aGoods.getGoodstNo() + ", ");
////            System.out.print(aGoods.getCounterNo() + ", ");
//            System.out.print(aGoods.getGoodsName() + ", ");
//            System.out.print(aGoods.getGoodsDescription() + ", ");
//            System.out.print(aGoods.getGoodsPrice() + ", ");
//            System.out.print(aGoods.getGoodsAmount() + ", ");
//            System.out.print(aGoods.getGoodsStatus() + ", ");
//            System.out.print(aGoods.getCheckStatus() + ", ");
//            System.out.print(aGoods.getGoodsDate() + ", ");
//            System.out.print(aGoods.getGoodsEnddate());
//            System.out.println();
//        }
    }
}
