package com.example.demo.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.config.OpenfeignConfig;

@FeignClient(url = "https://api.coindesk.com/v1/bpi", name="outerService", configuration = OpenfeignConfig.class)
public interface OuterService {
	
	@RequestMapping(value="/currentprice.json", method= RequestMethod.GET, consumes = "application/json")
	String getData();
}
