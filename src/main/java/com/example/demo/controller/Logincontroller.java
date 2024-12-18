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

		String result = userLoginService.login(userid, password);
		
		if ("OK".equals(result)) {

			mav.setViewName("home/home"); // ログイン成功後、ホーム画面に遷移

		} else {
			mav.addObject("errorMessage", result); //エラーメッセージを画面に表示
			mav.setViewName("login/login");
		}

		return mav;

	}

	@GetMapping("/register")
	public String registerTop() {
		return "user/register";
	}

	@PostMapping("/register/send")
	public ModelAndView register(@RequestParam String name, @RequestParam String password,
			@RequestParam String confirmPassword, ModelAndView mav) {

		String result = userRegisterService.register(name, password, confirmPassword);

		if (result.equals("登録完了")) {

			mav.setViewName("login/login");//登録成功後、ログイン画面に遷移

		} else {
			mav.addObject("errorMessage", result); //エラーメッセージを画面に表示
			mav.setViewName("user/register");
		}

		return mav;
	}
}
