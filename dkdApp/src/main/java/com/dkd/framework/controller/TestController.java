package com.dkd.framework.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dkd.framework.entity.NoNoBank;
import com.dkd.framework.service.NonoBankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Scope("prototype")
@RequestMapping("/")
public class TestController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private NonoBankService nonoBankService;

	@RequestMapping(value="/test",method={RequestMethod.GET,RequestMethod.POST})
	public  String baseError(HttpServletRequest request,HttpServletResponse response,Model model){
		Map<String,Object> reqMap = new HashMap<String, Object>();
		reqMap.put("pageIndex", 0);
		reqMap.put("pageSize", 10);
		List<NoNoBank> list  = nonoBankService.select(reqMap);
		for (NoNoBank noNoBank :list){
			System.err.println(noNoBank.getBoExtno());
		}
		model.addAttribute("list",list);
		return "test";
	}

}
