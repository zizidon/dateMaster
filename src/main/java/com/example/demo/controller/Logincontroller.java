package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.service.UserLoginService;
import com.example.demo.service.UserRegisterService;

@Controller
@RequestMapping("/dateMaster")
public class Logincontroller {

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	UserRegisterService userRegisterService;

	@GetMapping("")
	public String loginTop() {
		return "login/login";
	}

	@PostMapping("login")
	public ModelAndView login(@RequestParam String userid, @RequestParam String password, ModelAndView mav) {

		if (userLoginService.login(userid, password)) {

			mav.setViewName("home/home");

		} else {
			mav.setViewName("login/login");
		}

		return mav;

	}

	@GetMapping("/register")
	public String registerTop() {
		return "user/register";
	}

	@PostMapping("/register/send")
	public ModelAndView register(@RequestParam String id, @RequestParam String name, @RequestParam String password,
			@RequestParam String confirmPassword, ModelAndView mav) {

		if (userRegisterService.register(name, id, password, confirmPassword)) {

			mav.setViewName("login/login");

		} else {
			mav.setViewName("user/register");
		}

		return mav;
	}
}
