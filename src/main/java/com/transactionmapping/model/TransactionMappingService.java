package com.transactionmapping.model;

import com.transactionmapping.model.TransactionMappingVO;

import java.util.List;

public interface TransactionMappingService {
    void saveTransactionMapping(String merchantTradeNo, Integer counterOrderNo);
    List<Integer> findCounterOrderNosByMerchantTradeNo(String merchantTradeNo);
}