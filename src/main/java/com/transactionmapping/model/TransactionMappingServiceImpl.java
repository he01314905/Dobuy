package com.transactionmapping.model;


import com.transactionmapping.model.TransactionMappingVO;
import com.transactionmapping.model.TransactionMappingRepository;
import com.transactionmapping.model.TransactionMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionMappingServiceImpl implements TransactionMappingService {

    @Autowired
    private TransactionMappingRepository repository;

    @Override
    public void saveTransactionMapping(String merchantTradeNo, Integer counterOrderNo) {
        TransactionMappingVO transaction = new TransactionMappingVO(merchantTradeNo, counterOrderNo);
        repository.save(transaction);
    }

    @Override
    public List<Integer> findCounterOrderNosByMerchantTradeNo(String merchantTradeNo) {
        List<TransactionMappingVO> mappings = repository.findByMerchantTradeNo(merchantTradeNo);
        return mappings.stream()
                       .map(TransactionMappingVO::getCounterOrderNo)
                       .collect(Collectors.toList());
    }
}
