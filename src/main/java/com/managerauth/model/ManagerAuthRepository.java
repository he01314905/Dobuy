package com.managerauth.model;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface ManagerAuthRepository extends JpaRepository<ManagerAuthVO, ManagerAuthNo>{
		
	
		@Query(value="select * from managerauth where managerNo =?1", nativeQuery = true)
		List<ManagerAuthVO> findOneManager(Integer managerNo);
		
		@Transactional
		@Modifying
		@Query(value="delete  from managerauth where managerNo =?1 AND authNo = ?2", nativeQuery = true)
		void deleteAuth(Integer manager ,Integer authNo);
		
		
	}	





