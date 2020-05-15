package com.example.demo.controller;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@Controller
public class UserController {

	@Autowired
	UserService uesrService;
	
	@PostConstruct
	public void init() {
		
		User user1 = new User();
		user1.setName("山田　太郎");
		user1.setAge(30);
		user1.setJob("会社員");
		
		User user2 = new User();
		user2.setName("田中　一郎");
		user2.setAge(20);
		user2.setJob("学生");

		User user3 = new User();
		user3.setName("斎藤　花子");
		user3.setAge(40);
		user3.setJob("公務員");
		
		User user4 = new User();
		user4.setName("伊藤　博文");
		user4.setAge(68);
		user4.setJob("政治家");
		
		User user5 = new User();
		user5.setName("織田　信長");
		user5.setAge(47);
		user5.setJob("武士");

		User user6 = new User();
		user6.setName("卑弥呼");
		user6.setAge(78);
		user6.setJob("王");

		uesrService.saveAndFlush(user1);
		uesrService.saveAndFlush(user2);
		uesrService.saveAndFlush(user3);
		uesrService.saveAndFlush(user4);
		uesrService.saveAndFlush(user5);
		uesrService.saveAndFlush(user6);
		
	}
	
	
	@GetMapping("/user")
	public ModelAndView index(@ModelAttribute("formModel")User user,
			@PageableDefault(page = 0, size = 2)Pageable pageable,
			ModelAndView mav) {
		
		mav.setViewName("userIndex");
		Page<User> list = uesrService.findAll(pageable);
		
		mav.addObject("page", list);
		mav.addObject("datalist", list.getContent());
		return mav;
	}
	
	@PostMapping("/user")
	@Transactional(readOnly=false)
	public ModelAndView form(@ModelAttribute("formModel")
			@Validated User user,
			BindingResult result,
			ModelAndView mav) {
		
		ModelAndView res = null;
		
		if(!result.hasErrors()) {
			uesrService.saveAndFlush(user);
			res = new ModelAndView("redirect:/user");
		} else {
			mav.setViewName("userIndex");
			Iterable<User> list = uesrService.findAll();
			mav.addObject("datalist", list);
			mav.addObject("msg", "エラーが発生しています。");
			res = mav;
		}
		
		return res;
	}
	
	@PostMapping("/user/search")
	public ModelAndView search(@ModelAttribute("formModel")User user,
			HttpServletRequest reqest,
			ModelAndView mav) {
		
		ModelAndView res = null;
		String param = reqest.getParameter("fstr");
		
		if (param == "") {
			res = new ModelAndView("redirect:/user");			
		} else {
			mav.setViewName("userIndex");
			Iterable<User> list = uesrService.findAll();
			mav.addObject("datalist", list);

			mav.addObject("msg", "検索条件は、ID＝" + param + "です");			
			Optional<User> data = uesrService.findById(Long.parseLong(param));
			
			if(!data.isEmpty()) {
				mav.addObject("formModel",  data.get());
			}
			res = mav;
		}
		return res;
		
	}	
	
	@PostMapping("/user/{id}")
	@Transactional(readOnly = false)
	public ModelAndView remove(@PathVariable Long id, ModelAndView mav) {
		uesrService.deleteById(id);
		return new ModelAndView("redirect:/user");
	}
	
}
