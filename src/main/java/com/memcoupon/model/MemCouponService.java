package com.memcoupon.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.coupon.model.CouponService;
import com.coupon.model.CouponVO;
import com.member.model.MemberService;
import com.member.model.MemberVO;

@Service("memCouponService")
public class MemCouponService {

    @Autowired
    private MemCouponRepository memCouponRepository;
    
    @Autowired
    private CouponService couponService;
    
    @Autowired
    private MemberService memberService; // 添加會員服務
    
    private SessionFactory sessionFactory;  // 添加 SessionFactory

//  前台領取櫃位優惠券後顯示在我的優惠券
    @Transactional
    @CacheEvict(value = "memCouponCache", allEntries = true)
    public MemCouponVO claimCoupon(Integer memberNo, Integer couponNo) {
        // 檢查該會員是否已領取過此優惠券
        long count = memCouponRepository.countByCouponAndMember(couponNo, memberNo);
        if (count > 0) {
            throw new RuntimeException("您已領取過此優惠券");
        }
        // 減少優惠券可領取次數
        couponService.decreaseUsageLimit(couponNo);

        // 獲取優惠券資訊
        CouponVO coupon = couponService.getOneCoupon(couponNo);
        if (coupon == null || coupon.getCheckStatus() != 1) {
            throw new RuntimeException("優惠券不存在或尚未審核通過");
        }

        // 檢查優惠券是否已過期（允許開始日期在未來）
        Date now = new Date();
        if (now.after(coupon.getCouponEnd())) {
            throw new RuntimeException("優惠券已過期，無法領取");
        }

        // 獲取會員資訊 - 修改為使用 findById
        Optional<MemberVO> memberOpt = memberService.findById(memberNo);
        if (!memberOpt.isPresent()) {
            throw new RuntimeException("會員資料不存在");
        }
        MemberVO member = memberOpt.get();

        // 建立會員優惠券
        MemCouponVO memCoupon = new MemCouponVO();
        memCoupon.setCoupon(coupon);
        memCoupon.setMember(member);
        memCoupon.setStatus(0);  // 預設未使用

        return memCouponRepository.save(memCoupon);
    }
    
    
    // 新增會員優惠券
    public void addMemCoupon(MemCouponVO memCouponVO) {
    	memCouponRepository.save(memCouponVO);
    }

    // 更新會員優惠券
    @CacheEvict(value = "memCouponCache", allEntries = true)
    public void updateMemCoupon(MemCouponVO memCouponVO) {
    	memCouponRepository.save(memCouponVO);
    }

    // 刪除會員優惠券
    public void deleteMemCoupon(Integer memCouponNo) {
        if (memCouponRepository.existsById(memCouponNo))
        	memCouponRepository.deleteByMemCouponNo(memCouponNo);
    }

    // 查詢單個會員優惠券
    public MemCouponVO getOneMemCoupon(Integer memCouponNo) {
        Optional<MemCouponVO> optional = memCouponRepository.findById(memCouponNo);
        return optional.orElse(null);
    }

    // 查詢所有會員優惠券
    public List<MemCouponVO> getAll() {
        return memCouponRepository.findAll();
    }

    // 查詢特定會員的所有優惠券
    public List<MemCouponVO> getAllByMemNo(Integer memNo) {
        return memCouponRepository.findByMemNo(memNo);
    }

    // 修改為使用 JPA 的方式查詢
    public List<MemCouponVO> getAll(Map<String, String[]> map) {
        // 這裡可以根據需求改寫查詢邏輯
        return memCouponRepository.findAll();
    }


    public List<MemCouponVO> getAvailableCoupons(Integer memNo) {
        if (memNo == null) {
            return new ArrayList<>();
        }
        
        // 查詢該會員可用的優惠券（未使用且未過期）
        return memCouponRepository.findAvailableCouponsByMemNo(memNo);
    }
	

    
    
    
}
