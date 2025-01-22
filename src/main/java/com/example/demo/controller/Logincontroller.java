package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Negative;
import com.example.demo.entity.Positive;
import com.example.demo.entity.Users;
import com.example.demo.repository.NegativeRepository;
import com.example.demo.repository.PositiveRepository;
import com.example.demo.repository.UserRepository;
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

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PositiveRepository positiveRepository;

	@Autowired
	private NegativeRepository negativeRepository;

	private String getImagePathFromDiagnosis(int diagnosisResult) {
		try {
			// type_idが1-4の場合はPositiveテーブルから取得
			if (diagnosisResult >= 1 && diagnosisResult <= 4) {
				Positive positive = positiveRepository.findById(diagnosisResult).orElse(null);
				if (positive != null) {
					return positive.getImagePath();
				}
			}
			// type_idが5-8の場合はNegativeテーブルから取得
			else if (diagnosisResult >= 5 && diagnosisResult <= 8) {
				Negative negative = negativeRepository.findById(diagnosisResult).orElse(null);
				if (negative != null) {
					return negative.getImagePath();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//ログイン画面に遷移
	@GetMapping("")
	public String loginTop() {
		return "login/login";
	}

	//ログイン処理
	@PostMapping("login")
	public ModelAndView login(@RequestParam String userid, @RequestParam String password, ModelAndView mav) {

		String result = userLoginService.login(userid, password);

		if ("OK".equals(result)) {
			Users user = userRepository.findById(userid); // ユーザー情報をデータベースから取得
	        session.setAttribute("loginUser", user); // セッションにユーザー情報を格納
	        
			// ユーザーの診断結果画像を取得
			String userImagePath = getImagePathFromDiagnosis(user.getDiagnosis());
			mav.addObject("diagnosisImage", userImagePath);

			// パートナー情報の取得
			if (user.getPartner() != null) {
				Users partner = userRepository.findById(user.getPartner()).orElse(null);
				if (partner != null) {
					mav.addObject("partnerName", partner.getName());
					// パートナーの診断結果画像を取得
					String partnerImagePath = getImagePathFromDiagnosis(partner.getDiagnosis());
					mav.addObject("partnerDiagnosisImage", partnerImagePath);
				}
			}

			mav.addObject("user", user);
			mav.setViewName("home/home");
			return mav;

		} else {
			mav.addObject("errorMessage", result); //エラーメッセージを画面に表示
			mav.setViewName("login/login");
		}

		return mav;

	}

	//新規登録画面に遷移
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
	
	//ログアウト処理
	@GetMapping("/logout")
	public String logout() {
		session.invalidate(); //セッションを無効化
		return "login/logout_complete"; //ログイン画面にリダイレクト
	}
}
