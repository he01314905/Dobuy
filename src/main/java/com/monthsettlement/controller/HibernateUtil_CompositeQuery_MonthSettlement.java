package com.monthsettlement.controller;

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

import com.monthsettlement.model.MonthSettlementVO;

public class HibernateUtil_CompositeQuery_MonthSettlement {

    public static Predicate get_aPredicate_For_AnyDB(CriteriaBuilder builder, Root<MonthSettlementVO> root, String columnName, String value) {
        Predicate predicate = null;

        if (value == null || value.trim().isEmpty()) {
            // 如果 value 為 null 或空字符串，直接返回 null，不生成條件
            return null;
        }

        // 根據欄位類型創建對應的 Predicate
        if ("monthSettlementNo".equals(columnName) || "counterNo".equals(columnName) || "comm".equals(columnName)) { // 用於 Integer
            try {
                predicate = builder.equal(root.get(columnName), Integer.valueOf(value));
            } catch (NumberFormatException e) {
                System.out.println("Invalid value for " + columnName + ": " + value);
            }
        } else if ("month".equals(columnName)) { // 用於 Varchar
            predicate = builder.equal(root.get(columnName), value);
        }

        return predicate;
    }

    @SuppressWarnings("unchecked")
    public static List<MonthSettlementVO> getAllC(Map<String, String[]> map, Session session) {
        Transaction tx = session.beginTransaction();
        List<MonthSettlementVO> list = null;
        try {
            // 【●創建 CriteriaBuilder】
            CriteriaBuilder builder = session.getCriteriaBuilder();
            // 【●創建 CriteriaQuery】
            CriteriaQuery<MonthSettlementVO> criteriaQuery = builder.createQuery(MonthSettlementVO.class);
            // 【●創建 Root】
            Root<MonthSettlementVO> root = criteriaQuery.from(MonthSettlementVO.class);

            List<Predicate> predicateList = new ArrayList<>();
            
            Set<String> keys = map.keySet();
            int count = 0;
            for (String key : keys) {
                String value = map.get(key)[0];
                if (value != null && value.trim().length() != 0 && !"action".equals(key)) {
                    Predicate predicate = get_aPredicate_For_AnyDB(builder, root, key, value.trim());
                    if (predicate != null) { // 確保 Predicate 不為 null
                        predicateList.add(predicate);
                        count++;
                    }
                }
            }

            System.out.println("有送出查詢資料的欄位數 count = " + count);
            System.out.println("predicateList.size()=" + predicateList.size());
            criteriaQuery.where(predicateList.toArray(new Predicate[0]));
            criteriaQuery.orderBy(builder.asc(root.get("monthSettlementNo")));

            // 【●最後完成創建 javax.persistence.Query】
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

}
