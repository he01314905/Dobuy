package com.usedecpay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usedecpay.model.EcpayService;



@RestController
public class EcpayController {
	
	@Autowired
	EcpayService ecpayService;

	@PostMapping("/ecpayCheckout")
	public String ecpayCheckout() {
		String aioCheckOutALLForm = ecpayService.genAioCheckOutOneTime();

		return aioCheckOutALLForm;
	}
}