package com.storecarousel.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("storeCarouselService")
public class storeCarouselService {
	@Autowired
	storecarouselRepository storecarouselRepository;
	
	
	public List<storeCarouselVO> getPic(Integer counterNo){
		
		return storecarouselRepository.findNewest3(counterNo);
		
	}
}
