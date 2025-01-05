package com.msg.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.faq.model.FaqVO;
import com.notice.model.NoticeVO;

public interface MsgRepository extends JpaRepository<MsgVO, Integer> {
	
	
	List<NoticeVO> findByMemNo(Integer memNo);

	

    @Transactional
    @Modifying
    @Query(value = "delete from counterinform where counterInformNo = ?1", nativeQuery = true)
    void deleteByCounterInformNo(int counterInformNo);
    
    
    
    //抓櫃位通知
    @Query(value = "SELECT * FROM counterInform WHERE counterNo = ?1 ", nativeQuery = true)
	List<MsgVO> findByCounterNo(Integer counterNo);
    //任國確認已讀未讀
    @Query("SELECT COUNT(m) FROM MsgVO m WHERE m.counterNo = :counterNo AND m.informRead = :informRead")
    int counterReader(@Param("counterNo") Integer counterNo, @Param("informRead") Byte informRead);
}



