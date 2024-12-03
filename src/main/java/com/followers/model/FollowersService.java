package com.followers.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.followers.controller.HibernateUtil_CompositeQuery_Followers;


@Service("followersService")
public class FollowersService {

    @Autowired
    FollowersRepository repository;

    @Autowired
    private SessionFactory sessionFactory;

    // 新增櫃位追蹤清單
    public void addFollowers(FollowersVO followersVO) {
        repository.save(followersVO);
    }

    // 更新櫃位追蹤清單
    public void updateFollowers(FollowersVO followersVO) {
        repository.save(followersVO);
    }

    // 刪除櫃位追蹤清單
    public void deleteFollowers(Integer trackListNo) {
        if (repository.existsById(trackListNo))
            repository.deleteByTrackListNo(trackListNo);
    }

    // 取得單筆櫃位追蹤清單
    public FollowersVO getOneFollowers(Integer trackListNo) {
        Optional<FollowersVO> optional = repository.findById(trackListNo);
        return optional.orElse(null); // public T orElse(T other): 如果值存在就回傳其值，否則回傳other的值
    }

    // 取得所有櫃位追蹤清單
    public List<FollowersVO> getAll() {
        return repository.findAll();
    }

    // 動態查詢取得所有櫃位追蹤清單
    public List<FollowersVO> getAll(Map<Integer, Integer[]> map) {
        return HibernateUtil_CompositeQuery_Followers.getAllC(map, sessionFactory.openSession());
    }
}
