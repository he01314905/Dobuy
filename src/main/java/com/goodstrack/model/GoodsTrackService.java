package com.goodstrack.model;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodsTrackService {
	
	@Autowired
	GoodsTrackRepository goodsTrackRepository;
	
	// 新增追蹤
	@Transactional
    public GoodsTrackVO addGoodsTrack(Integer memNo, Integer goodsNo) {
        GoodsTrackVO goodsTrack = new GoodsTrackVO();
        goodsTrack.setMemNo(memNo);
        goodsTrack.setGoodsNo(goodsNo);
        return goodsTrackRepository.save(goodsTrack);
    }

    // 移除追蹤
	@Transactional
    public void removeGoodsTrack(Integer memNo, Integer goodsNo) {
        goodsTrackRepository.deleteByMemNoAndGoodsNo(memNo, goodsNo);
    }

    // 獲取會員的追蹤清單
    public List<GoodsTrackVO> getGoodsTracksByMember(Integer memNo) {
        return goodsTrackRepository.findByMemNo(memNo);
    }

    // 檢查是否已追蹤某商品
    public boolean isGoodsTracked(Integer memNo, Integer goodsNo) {
        return goodsTrackRepository.findByMemNoAndGoodsNo(memNo, goodsNo) != null;
    }
}
