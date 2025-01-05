package com.notice.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msg.model.MsgVO;

import java.util.List;

@Service("noticeService")
public class NoticeService {

    @Autowired
    NoticeRepository repository;

    public List<NoticeVO> getAll() {
        return repository.findAll();
    }

    public void save(NoticeVO noticeVO) {
        repository.save(noticeVO);
    }

    public NoticeVO getNoticeById(Integer noticeId) {
        return repository.findById(noticeId).orElse(null);
    }

    public void updateNotice(NoticeVO noticeVO) {
        repository.save(noticeVO);
    }
    
    public List<NoticeVO> getOneMemberNotice(Integer memNo) {
        return repository.findByMemNo(memNo);
    }

    public List<NoticeVO> getNoticesByMemNo(Integer memNo) {
        return repository.findByMemNo(memNo);
    }
    
    public void deleteNoticesByMemNo(Integer memNo) {
        List<NoticeVO> notices = repository.findByMemNo(memNo);
        repository.deleteAll(notices);
    }
    
    public void markNoticesAsReadByMemNo(Integer memNo) {
        List<NoticeVO> notices = repository.findByMemNo(memNo);
        for (NoticeVO notice : notices) {
            notice.setNoticeRead((byte) 1); // 假設 noticeRead 是 byte 類型，1 代表已讀
        }
        repository.saveAll(notices);
    }

    public List<NoticeVO> findAllByCounterInformNo(Integer counterInformNo) {
        return repository.findAllByCounterInformNo(counterInformNo);
    }

    public NoticeVO getNoticeByMsgIdAndMemNo(Integer counterInformNo, Integer memNo) {
        return repository.findByCounterInformNoAndMemNo(counterInformNo, memNo);
    }
}



   


 

   
   