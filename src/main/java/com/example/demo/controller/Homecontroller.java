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
public class Homecontroller {

	@Autowired
	HttpSession session;

	@GetMapping("/date")
	public String showDatePage() {

		//date.htmlを返す

		return "date/date";
	}

	@GetMapping("/home")
	public ModelAndView showHome(ModelAndView mav) {

		Users user = (Users) session.getAttribute("loginUser"); //セッションからユーザー情報を取得
		mav.addObject("user", user);//モデルにユーザー情報を追加
		mav.setViewName("home/home");//ホーム画面に遷移
		//home.htmlを返す

		return mav;
	}
}
