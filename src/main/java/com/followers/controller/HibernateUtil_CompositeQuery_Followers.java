package com.followers.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.followers.model.FollowersVO;

public class HibernateUtil_CompositeQuery_Followers {

    public static Predicate get_aPredicate_For_AnyDB(CriteriaBuilder builder, Root<FollowersVO> root, String columnName, String value) {
        Predicate predicate = null;

        if (value == null || value.trim().isEmpty()) {
            // 如果 value 為 null 或空字符串，直接返回 null，不生成條件
            return null;
        }

        // 根據欄位類型創建對應的 Predicate
        if ("trackListNo".equals(columnName) || "followersNo".equals(columnName) || "counterNo".equals(columnName)) { // 用於 Integer
            try {
                predicate = builder.equal(root.get(columnName), Integer.valueOf(value));
            } catch (NumberFormatException e) {
                System.out.println("Invalid value for " + columnName + ": " + value);
            }
        }

        return predicate;
    }

//    @SuppressWarnings("unchecked")
//    public static List<FollowersVO> getAllC(Map<Integer, Integer[]> map, Session session) {
//        Transaction tx = session.beginTransaction();
//        List<FollowersVO> list = null;
//        try {
//            // 【●創建 CriteriaBuilder】
//            CriteriaBuilder builder = session.getCriteriaBuilder();
//            // 【●創建 CriteriaQuery】
//            CriteriaQuery<FollowersVO> criteriaQuery = builder.createQuery(FollowersVO.class);
//            // 【●創建 Root】
//            Root<FollowersVO> root = criteriaQuery.from(FollowersVO.class);
//
//            List<Predicate> predicateList = new ArrayList<>();
//            
//            Set<Integer> keys = map.keySet();
//            int count = 0;
//            for (Integer key : keys) {
//            	Integer value = map.get(key)[0];
//                if (value != null && value.trim().length() != 0 && !"action".equals(key)) {
//                    Predicate predicate = get_aPredicate_For_AnyDB(builder, root, key, value.trim());
//                    if (predicate != null) { // 確保 Predicate 不為 null
//                        predicateList.add(predicate);
//                        count++;
//                    }
//                }
//            }
    
    @SuppressWarnings("unchecked")
    public static List<FollowersVO> getAllC(Map<Integer, Integer[]> map, Session session) {
        Transaction tx = session.beginTransaction();
        List<FollowersVO> list = null;
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<FollowersVO> criteriaQuery = builder.createQuery(FollowersVO.class);
            Root<FollowersVO> root = criteriaQuery.from(FollowersVO.class);

            List<Predicate> predicateList = new ArrayList<>();

            // 修改這裡的型別處理
            Set<Integer> keys = map.keySet();  // 使用 Integer 而不是 String
            int count = 0;
            for (Integer key : keys) {  // 使用 Integer 作為迭代型別
                Integer[] values = map.get(key);  // 獲取 Integer 陣列
                if (values != null && values.length > 0 && values[0] != null) {
                    // 將 Integer 轉換為 String 再傳入
                    String columnName = getColumnNameFromKey(key);  // 需要新增這個幫助方法
                    String value = String.valueOf(values[0]);
                    
                    Predicate predicate = get_aPredicate_For_AnyDB(builder, root, columnName, value);
                    if (predicate != null) {
                        predicateList.add(predicate);
                        count++;
                    }
                }
            }

            System.out.println("有送出查詢資料的欄位數 count = " + count);
            System.out.println("predicateList.size()=" + predicateList.size());
            
            if (!predicateList.isEmpty()) {
                criteriaQuery.where(predicateList.toArray(new Predicate[0]));
            }
            criteriaQuery.orderBy(builder.asc(root.get("trackListNo")));

            Query query = session.createQuery(criteriaQuery);
            list = query.getResultList();

            tx.commit();
        } catch (RuntimeException ex) {
            if (tx != null) tx.rollback();
            throw ex;
        } finally {
            session.close();
        }

        return list;
    }

    // 新增一個幫助方法來將 Integer key 轉換為對應的欄位名稱
    private static String getColumnNameFromKey(Integer key) {
        // 根據你的業務邏輯來定義對應關係
        switch (key) {
            case 1: return "trackListNo";
            case 2: return "followersNo";
            case 3: return "counterNo";
            default: return null;
        }
    }

//            System.out.println("有送出查詢資料的欄位數 count = " + count);
//            System.out.println("predicateList.size()=" + predicateList.size());
//            criteriaQuery.where(predicateList.toArray(new Predicate[0]));
//            criteriaQuery.orderBy(builder.asc(root.get("trackListNo")));

            // 【●最後完成創建 javax.persistence.Query】
//            Query query = session.createQuery(criteriaQuery);	
//            list = query.getResultList();

//            tx.commit();
//        } catch (RuntimeException ex) {
//            if (tx != null) tx.rollback();
//            throw ex;
//        } finally {
//            session.close();
//        }
//
//        return list;
//    }

}
