/*
 * Copyright 2012-2020 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * @author lzhoumail@126.com/zhouli
 * Git http://git.oschina.net/zhou666/spring-cloud-7simple
 */
package seckill.web.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.text.html.FormSubmitEvent.MethodType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import seckill.common.dto.Exposer;
import seckill.common.dto.SeckillExecution;
import seckill.common.model.Seckill;
import seckill.common.tools.Tools;

@Service
public class WebService {
	 @Autowired	 
	 RestTemplate restTemplate;
	
	 final String SERVICE_NAME="seckill-killed-service";
	 
	@SuppressWarnings("unchecked")
	//@HystrixCommand(fallbackMethod = "fallbackList")
	 public List<Seckill> getSeckillList(){
		    ResponseEntity<List<Seckill>>	list = restTemplate.exchange("http://"+SERVICE_NAME+"/seckill/list", HttpMethod.GET, null,new ParameterizedTypeReference<List<Seckill>>(){});
		    return list.getBody();
	 }
	 public List<Seckill> fallbackList(){
		 List list = new ArrayList();
		 Seckill seckill = new Seckill();
		 seckill.setCreateTime(new Date());
		 seckill.setEndTime(new Date());
		 seckill.setName("this is fallbackdata");
		 seckill.setNumber(-1);
		 seckill.setSeckillId(-1);
		 seckill.setStartTime(new Date());
		 
		 list.add(seckill);
		 
		 return list;
	 }
	 @HystrixCommand
	 public Seckill getById(Long seckillId) {

		 ResponseEntity<Seckill> seckill =restTemplate.exchange("http://"+SERVICE_NAME+"/seckill/{seckillId}",HttpMethod.GET, null,new ParameterizedTypeReference<Seckill>(){},seckillId);
         seckill.getBody();
		 return seckill.getBody();
	 }
	 public Seckill fallbackgetById(Long seckillId) {
		 return new Seckill();
	 }
	 
	 @HystrixCommand
	 public Exposer exportSeckillUrl(Long seckillId) {
			
	        Seckill seckill =	getById(seckillId);
	       
	        if (seckill == null) {
	            return new Exposer(false, seckillId);
	        }
	    	
	        Date startTime = seckill.getStartTime();
	        Date endTime = seckill.getEndTime();

	        Date nowTime = new Date();

	        if (nowTime.getTime() > endTime.getTime() || nowTime.getTime() < startTime.getTime()) {
	            return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
	        }

	        String md5 = Tools.getMD5(seckillId);
	        return new Exposer(true, md5, seckillId);
	 }

	 public SeckillExecution execute(Long seckillId,Long killPhone, String md5) {
		 ResponseEntity<SeckillExecution> ex =  restTemplate.exchange("http://"+SERVICE_NAME+"/seckill/{seckillId}/{killPhone}/{md5}", HttpMethod.POST,null,new ParameterizedTypeReference<SeckillExecution>(){}, seckillId,killPhone,md5);
		return ex.getBody();
	 }
	 
}
