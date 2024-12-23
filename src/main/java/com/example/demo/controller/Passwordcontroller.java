package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Users;
import com.example.demo.service.PasswordChangeService;

@Controller
@RequestMapping("/dateMaster")
public class Passwordcontroller {

	@Autowired
	HttpSession session;

	@Autowired
	PasswordChangeService passwordChangeService;

	//パスワード変更確認・更新処理
	@PostMapping("/confirmPass")
	public ModelAndView confirmPassChange(@ModelAttribute("currentPassword") String currentPassword,
			@ModelAttribute("newPassword") String newPassword,
			@ModelAttribute("confirmPassword") String confirmPassword,
			ModelAndView mav) {

		//セッションからユーザー情報を取得
		Users user = (Users) session.getAttribute("loginUser");

		//現在のパスワードが正しいかを確認
		if (!user.getPassword().equals(currentPassword)) {
			mav.addObject("error", "パスワードが一致しません。");
			mav.setViewName("password_change/password_change");

			return mav;
		}

		//新しいパスワードと確認パスワードを比較
		if (!newPassword.equals(confirmPassword)) {
			mav.addObject("error", "パスワードが一致しません。");
			mav.setViewName("password_change/password_change");

			return mav;
		}

		//パスワード更新処理
		passwordChangeService.updatePassword(user.getId(), newPassword);

		//完了画面への遷移
		mav.addObject("massage", "パスワードを変更しました");
		mav.setViewName("password_change/password_change_complete");

		return mav;

	}

}
