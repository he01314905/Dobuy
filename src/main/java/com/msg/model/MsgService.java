package com.msg.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faq.model.FaqVO;

@Service("msgService")
public class MsgService {

    @Autowired
    MsgRepository repository;

    public void addMsg(MsgVO msgVO) {
        repository.save(msgVO);
    }

    public void updateMsg(MsgVO msgVO) {
        repository.save(msgVO);
    }
    
    public MsgVO getMsgById(Integer counterInformNo) {
        return repository.findById(counterInformNo).orElse(null);
    }

    public void deleteMsg(Integer counterInformNo) {
        if (repository.existsById(counterInformNo))
            repository.deleteByCounterInformNo(counterInformNo);
    }

    public MsgVO getOneMsg(Integer counterInformNo) {
        Optional<MsgVO> optional = repository.findById(counterInformNo);
        return optional.orElse(null);
    }

    public List<MsgVO> getAll() {
        return repository.findAll();
    }
    
    public void save(MsgVO msgVO) {
        repository.save(msgVO);
    }
    
  //任國抓櫃位通知
    public List<MsgVO> getOneCounterMsg(Integer counterNo) {
        return repository.findByCounterNo(counterNo); // 如果不存在，返回 null
    }
}




    


   


   
  

