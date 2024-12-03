package com.managerauth.model;

	import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.auth.model.AuthVO;
import com.manager.model.ManagerVO;

	@Service("managerAuthService")
	public class ManagerAuthService {
		
	
		
		@Autowired
	    ManagerAuthRepository repository;
 
	    public List<ManagerAuthVO> getAll() {
	    	List<ManagerAuthVO> malist = repository.findAll();
	    	return malist;
	    }
	 
	    public void updateAuth(ManagerAuthVO managerAuthVO) {
	    	repository.save(managerAuthVO);
	    }
	   
	    public void deleteAuthByManager(Integer manager,Integer authNo) {
	    	repository.deleteAuth(manager,authNo); 
	    }
	    public List<ManagerAuthVO> getOne(Integer managerNo){
	return repository.findOneManager(managerNo);
	    	
	    }
	   
	    
	}

