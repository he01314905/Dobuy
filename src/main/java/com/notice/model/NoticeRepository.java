package com.notice.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NoticeRepository extends JpaRepository<NoticeVO, Integer> {

	@Query("SELECT COUNT(n) FROM NoticeVO n WHERE n.noticeRead = 0")
	long countUnreadNotices();

	// 抓會員通知
	@Query(value = "SELECT * FROM notice WHERE memNo = ?1 ", nativeQuery = true)
	List<NoticeVO> findByMemNo(Integer memNo);

	List<NoticeVO> findAllByCounterInformNo(Integer counterInformNo);

	NoticeVO findByCounterInformNoAndMemNo(Integer counterInformNo, Integer memNo);

//	柏翔新增====================================================================
	boolean existsByMemNoAndNoticeContent(Integer memNo, String noticeContent);

//	柏翔新增====================================================================	
	@Query("SELECT n.memNo FROM NoticeVO n WHERE n.noticeContent = :noticeContent AND n.memNo IN :memNos")
	List<Integer> findExistingMemNosByContent(@Param("noticeContent") String noticeContent,
			@Param("memNos") List<Integer> memNos);

}
