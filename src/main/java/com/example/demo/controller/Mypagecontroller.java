package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Users;

@Controller
@RequestMapping("/dateMaster")
public class Mypagecontroller {

	@Autowired
	HttpSession session;

	@GetMapping("/mypage")
	public ModelAndView showMypage(ModelAndView mav) {
		// セッションからログインユーザーを取得
		Users user = (Users) session.getAttribute("loginUser");

		//ユーザー情報をモデルに追加
		if (user != null) {
			mav.addObject("userName", user.getName());
			mav.addObject("userId", user.getId());
		}

		mav.setViewName("mypage/mypage");

		return mav;
	}
}
