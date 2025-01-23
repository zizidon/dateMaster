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
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserLoginService;
import com.example.demo.service.UserRegisterService;

@Controller
@RequestMapping("/dateMaster")
public class Resetcontroller {

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	UserRegisterService userRegisterService;

	@Autowired
	HttpSession session;

	@Autowired
	private UserRepository userRepository;

	// パスワードリセット用の本人確認画面表示
	@GetMapping("/password/reset")
	public String showPasswordResetCheck() {
		return "password_reset/password_reset_answercheck";
	}

	// 本人確認の検証
	@PostMapping("/password/check")
	public ModelAndView checkSecurityQuestion(
			@RequestParam String userId,
			@RequestParam String question,
			@RequestParam String answer,
			ModelAndView mav) {

		try {
			Long id = Long.parseLong(userId);
			Users user = userRepository.findById(id).orElse(null);

			if (user != null &&
					user.getQuestion().equals(question) &&
					user.getAnswer().equals(answer)) {

				// 本人確認成功
				mav.addObject("userId", userId);
				mav.setViewName("password_reset/password_reset");
			} else {
				// 本人確認失敗
				mav.addObject("errorMessage", "ユーザーIDまたは回答が正しくありません。");
				mav.setViewName("password_reset/password_reset_answercheck");
			}
		} catch (NumberFormatException e) {
			mav.addObject("errorMessage", "無効なユーザーIDです。");
			mav.setViewName("password_reset/password_reset_answercheck");
		}

		return mav;
	}

	// パスワードリセットの実行
	@PostMapping("/password/reset")
	public ModelAndView resetPassword(
			@RequestParam String userId,
			@RequestParam String newPassword,
			@RequestParam String confirmPassword,
			ModelAndView mav) {

		try {
			if (!newPassword.equals(confirmPassword)) {
				mav.addObject("errorMessage", "パスワードが一致しません。");
				mav.addObject("userId", userId);
				mav.setViewName("password_reset/password_reset");
				return mav;
			}

			Long id = Long.parseLong(userId);
			Users user = userRepository.findById(id).orElse(null);

			if (user != null) {
				user.setPassword(newPassword);
				userRepository.save(user);
				mav.setViewName("password_reset/password_reset_complete");
			} else {
				mav.addObject("errorMessage", "ユーザーが見つかりません。");
				mav.setViewName("password_reset/password_reset");
			}
		} catch (NumberFormatException e) {
			mav.addObject("errorMessage", "無効なユーザーIDです。");
			mav.setViewName("password_reset/password_reset");
		}

		return mav;
	}
}
