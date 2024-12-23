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
public class UserManagementcontroller {

	@Autowired
	HttpSession session;

	//ユーザー管理画面に移動する
	@GetMapping("/userManagement")
	public String userManagement() {

		//user_management.htmlを返す

		return "user_management/user_management";
	}

	//名前変更画面へ移動する
	@GetMapping("/name")
	public ModelAndView showNamePage(ModelAndView mav) {

		//セッションからユーザー情報を取得
		Users user = (Users) session.getAttribute("loginUser");

		mav.addObject("user", user); //モデルにユーザー情報を追加
		mav.setViewName("name/name_change"); //名前変更画面へ遷移

		return mav;
	}

	//パスワード変更画面表示
	@GetMapping("/password")
	public ModelAndView passwordChange(ModelAndView mav) {

		//セッションからユーザー情報を取得
		Users user = (Users) session.getAttribute("loginUser");

		if (user != null) {
			mav.addObject("user", user);
			mav.setViewName("password_change/password_change");
		}

		return mav;
	}
	
	//アカウント削除画面表示
	@GetMapping("/userDelete")
	public String deleteAccount() {
		
		//account_delete.htmlを返す
		return "delete_user/account_delete";
	}
}
