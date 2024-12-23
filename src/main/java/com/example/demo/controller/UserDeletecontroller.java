package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Users;
import com.example.demo.service.UserDeleteService;

@Controller
@RequestMapping("/dateMaster")
public class UserDeletecontroller {

	
	@Autowired
	HttpSession session;
	
	@Autowired
	UserDeleteService userDeleteService;
	
	//アカウント削除画面からのパスワード確認処理
	@PostMapping("/userDelete")
	public ModelAndView confirmDelete(@ModelAttribute("password") String password, ModelAndView mav) {
		
		Users user = (Users) session.getAttribute("loginUser");
		
		//入力されたパスワードが正しいか確認
		if (!user.getPassword().equals(password)) {
			mav.addObject("error", "パスワードが一致しません。");
			mav.setViewName("delete_user/account_delete");
			return mav;
		}
		
		//確認画面へ遷移
		mav.setViewName("delete_user/accountDelete_confirm");
		return mav;
		
	}
	
	//アカウント削除確認画面から削除完了への処理
	@PostMapping("/completeDelete")
	public ModelAndView completeDelete(ModelAndView mav) {
		
		Users user = (Users) session.getAttribute("loginUser");
		
		//ユーザーを削除
		userDeleteService.deleteUser(user.getId());
		session.invalidate(); //セッションを無効化
		
		//完了画面へ遷移
		mav.setViewName("delete_user/accountDelete_complete");
		return mav;
		
	}
	
}
