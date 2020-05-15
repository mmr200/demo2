package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SampleController2 {

	@GetMapping(value = "/sample")
	private ModelAndView index(ModelAndView mav) {
		mav.setViewName("sample");
		mav.addObject("msg", "Sample Thymeleaf!");
		return mav;
	}

	@PostMapping(value="/sample") 
	private ModelAndView send(@RequestParam("text1")String str, 
			@RequestParam(value="check1", required=false)boolean chk,
			@RequestParam(value="radio1", required=false)String rdo,
			@RequestParam(value="select1", required=false)String sel,
			ModelAndView mav) {
		mav.setViewName("sample");
		
		String res = "";
		res = "text:" + str + " check:" + chk + " radio:" + rdo + " select:" + sel; 
		mav.addObject("msg", "送信内容は、" + res + " です。");
		
		mav.addObject("valtxt", str);
		mav.addObject("valchk", chk);
		
		if (rdo != null) {
			mav.addObject(rdo.equals("male")? "valrdoA" : "valrdoB", rdo);
		}
		
		if (sel != null) {
			mav.addObject("valsel" + sel.substring(3), sel);
		}
		
		return mav;
	}
}