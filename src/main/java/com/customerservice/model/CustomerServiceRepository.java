package com.customerservice.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.faq.model.FaqVO;
import com.msg.model.MsgVO;
import com.notice.model.NoticeVO;

public interface CustomerServiceRepository extends JpaRepository<CustomerServiceVO, Integer> {
	
	
	List<NoticeVO> findByMemNo(Integer memNo);

	

	//抓客訴回覆
    @Query(value = "SELECT * FROM goodcomplaint WHERE counterNo = ?1 ", nativeQuery = true)
	List<CustomerServiceVO> findByCounterNo(Integer counterNo);
    
    
    //測試任國
    @Query("SELECT COUNT(p) FROM CustomerServiceVO p WHERE p.counterNo = :counterNo AND p.complaintStatus = :complaintStatus")
    int counterPlaintReader(@Param("counterNo") Integer counterNo, @Param("complaintStatus") Byte complaintStatus);
    
   
}

