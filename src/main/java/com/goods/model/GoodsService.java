package com.goods.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goods.controller.HibernateUtil_CompositeQuery_Goods;

@Service("goodsService")
public class GoodsService {
	
	
    @Autowired
    GoodsRepository repository;

    @Autowired
    private SessionFactory sessionFactory;


//	新增商品
    public void addGoods(GoodsVO goodsVO) {
        repository.save(goodsVO);
    }

//  更新商品
    public void updateGoods(GoodsVO goodsVO) {
        repository.save(goodsVO);
    }

//  刪除商品
    public void deleteGoods(Integer goodsNo) {
        if (repository.existsById(goodsNo)) 
            repository.deleteByGoodsno(goodsNo);
    }

//  據商品編號取得單一商品
    public GoodsVO getOneGoods(Integer goodsNo) {
        Optional<GoodsVO> optional = repository.findById(goodsNo);
        return optional.orElse(null); // 如果不存在，返回 null
    }

//  取得所有商品
    public List<GoodsVO> getAll() {
        return repository.findAll();
    }
    public List<GoodsVO> getgoods() {
        return repository.findAllUP();
    }
//  使用條件查詢取得商品
    public List<GoodsVO> getAll(Map<String, String[]> map) {
        return HibernateUtil_CompositeQuery_Goods.getAllC(map, sessionFactory.openSession());
    }
    
    
    // 更新商品審核狀態
    public void updateCheckStatus(Integer goodsNo, Byte checkStatus) {
        // 根據商品編號找到商品
        Optional<GoodsVO> optional = repository.findById(goodsNo);
        
        // 如果商品存在，更新審核狀態
        if (optional.isPresent()) {
            GoodsVO goodsVO = optional.get();
            goodsVO.setCheckStatus(checkStatus); // 設定新的審核狀態
            repository.save(goodsVO); // 儲存更新後的商品資料
        }
    }
    // 更新商品狀態的方法
    public void updateGoodsStatus(String goodsNo, Byte goodsStatus, LocalDateTime now) {
        // 根據商品編號找到商品
        Optional<GoodsVO> optional = repository.findById(Integer.parseInt(goodsNo));

        // 如果商品存在，更新商品狀態
        if (optional.isPresent()) {
            GoodsVO goodsVO = optional.get();
            
            // 設定新的商品狀態
            goodsVO.setGoodsStatus(goodsStatus);

            // 將 LocalDateTime 轉換為 Timestamp
            Timestamp timestamp = Timestamp.valueOf(now);

            // 根據商品的狀態更新時間欄位
            if (goodsStatus == 1) {
                // 上架時，設定 GoodsDate 為當前時間
                goodsVO.setGoodsDate(timestamp);
            } else if (goodsStatus == 0) {
                // 下架時，設定 GoodsEnddate 為當前時間
                goodsVO.setGoodsEnddate(timestamp);
            }
            
            // 儲存更新後的商品資料
            repository.save(goodsVO);
        }
    }
    //  據櫃位編號取得全部商品
    public List<GoodsVO> getOneCounter1(Integer counterNo) {
        return repository.getOneCounter1(counterNo); // 如果不存在，返回 null
    }
    //==================以下昱夆新增=====================//
    //  據櫃位編號取得全部商品
    public List<GoodsVO> getOneCounter35(Integer counterNo) {
        return repository.getOneCounter35(counterNo); // 如果不存在，返回 null
    }
    public GoodsVO getOneCounter(Integer goodsNo) {
        return repository.getOneCounter(goodsNo); // 如果不存在，返回 null
    }
    
    public List<String> getOneGoodsImg(GoodsVO goodsVO) {
    	List<String> photoList = new ArrayList<String>();
    	 if (goodsVO != null) {
    	        // 提取所有的 byte[] 字段並轉換為 Base64
    	        if (goodsVO.getGpPhotos1() != null) {
    	            photoList.add(goodsVO.convertToBase64(goodsVO.getGpPhotos1()));
    	        }
    	        if (goodsVO.getGpPhotos2() != null) {
    	            photoList.add(goodsVO.convertToBase64(goodsVO.getGpPhotos2()));
    	        }
    	        if (goodsVO.getGpPhotos3() != null) {
    	            photoList.add(goodsVO.convertToBase64(goodsVO.getGpPhotos3()));
    	        }
    	        if (goodsVO.getGpPhotos4() != null) {
    	            photoList.add(goodsVO.convertToBase64(goodsVO.getGpPhotos4()));
    	        }
    	        if (goodsVO.getGpPhotos5() != null) {
    	            photoList.add(goodsVO.convertToBase64(goodsVO.getGpPhotos5()));
    	        }
    	        if (goodsVO.getGpPhotos6() != null) {
    	            photoList.add(goodsVO.convertToBase64(goodsVO.getGpPhotos6()));
    	        }
    	        if (goodsVO.getGpPhotos7() != null) {
    	            photoList.add(goodsVO.convertToBase64(goodsVO.getGpPhotos7()));
    	        }
    	        if (goodsVO.getGpPhotos8() != null) {
    	            photoList.add(goodsVO.convertToBase64(goodsVO.getGpPhotos8()));
    	        }
    	        if (goodsVO.getGpPhotos9() != null) {
    	            photoList.add(goodsVO.convertToBase64(goodsVO.getGpPhotos9()));
    	        }
    	        if (goodsVO.getGpPhotos10() != null) {
    	            photoList.add(goodsVO.convertToBase64(goodsVO.getGpPhotos10()));
    	        }
    	    }
    	 return photoList;

    }
    
    @Transactional
    public void updateGoodsAmount(Integer goodsNo,Integer goodsAmount) {
    	 Optional<GoodsVO> optional = repository.findById(goodsNo);
//
//         // 如果商品存在，更新商品狀態
         if (optional.isPresent()) {
             GoodsVO goodsVO = optional.get();
             goodsVO.setGoodsAmount(goodsAmount); // 設定新的商品狀態

             repository.save(goodsVO); // 儲存更新後的商品資料
         }
    	
    }

    
    
    
    //==================以下柏翔新增=====================//

    public List<GoodsVO> findByCounterVO_CounterNo(Integer counterNo) {
        return repository.findByCounterVO_CounterNo(counterNo);
    }
    //==================以上柏翔新增=====================//

  //=============以下gary新增===============//
    //搜尋所有上架商品
    public List<GoodsVO> getAllGoodsStatus1(){
		 	
    	return repository.getAllGoodsStatus1();
    	
    }
    //=============以上GARY新增===============//

    
    
    
}
