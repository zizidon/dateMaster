package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Users;
import com.example.demo.service.UserLoginService;
import com.example.demo.service.UserRegisterService;

@Controller
@RequestMapping("/dateMaster")
public class Logincontroller {

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	UserRegisterService userRegisterService;

	@Autowired
	HttpSession session;

	//ログイン画面に移動する
	@GetMapping("")
	public String loginTop() {
		return "login/login";
	}

	//ログイン処理
	@PostMapping("login")
	public ModelAndView login(@RequestParam String userid, @RequestParam String password, ModelAndView mav) {

		String result = userLoginService.login(userid, password);

		if ("OK".equals(result)) {
			Users user = (Users) session.getAttribute("loginUser"); //セッションからユーザー情報を取得
			mav.addObject("user", user); //モデルにユーザー情報を追加
			mav.setViewName("home/home"); // ログイン成功後、ホーム画面に遷移

		} else {
			mav.addObject("errorMessage", result); //エラーメッセージを画面に表示
			mav.setViewName("login/login");
		}

		return mav;

	}

	//新規登録画面に移動する
	@GetMapping("/register")
	public String registerTop() {
		return "user/register";
	}

	//新規登録処理
	@PostMapping("/register/send")
	public ModelAndView register(@RequestParam String name, @RequestParam String password,
			@RequestParam String confirmPassword, ModelAndView mav) {

		try {
			Long userId = userRegisterService.register(name, password, confirmPassword);
			mav.addObject("userId", userId);
			mav.setViewName("user/register_complete");
		} catch (IllegalArgumentException e) {
			mav.addObject("errorMessage", e.getMessage());
			mav.setViewName("user/register");
		}

		return mav;
	}
}
