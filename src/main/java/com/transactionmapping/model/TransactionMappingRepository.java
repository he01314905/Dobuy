package com.transactionmapping.model;

import com.transactionmapping.model.TransactionMappingVO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionMappingRepository extends JpaRepository<TransactionMappingVO, Integer> {
    
    // 根據綠界交易號碼查詢對應的 CounterOrderNo
    List<TransactionMappingVO> findByMerchantTradeNo(String merchantTradeNo);
}
