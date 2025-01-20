package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Negative;
import com.example.demo.entity.Positive;
import com.example.demo.entity.Users;
import com.example.demo.repository.NegativeRepository;
import com.example.demo.repository.PositiveRepository;
import com.example.demo.repository.UserRepository;

@Controller
@RequestMapping("/dateMaster")
public class Homecontroller {

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

	// デート機能一覧画面へ遷移（特定のセッション属性だけ削除）
	@GetMapping("/date")
	public String showDatePage() {
		// 特定のセッション属性を削除（例: "selectedSpots"）
		session.removeAttribute("selectedSpots");

		// date.htmlを返す
		return "date/date";
	}

	// ホーム画面へ遷移
	@GetMapping("/home")
	public ModelAndView showHome(ModelAndView mav) {
		Users user = (Users) session.getAttribute("loginUser"); // セッションからユーザー情報を取得

		// データベースから最新のユーザー情報を取得
		user = userRepository.findById(user.getId()).orElse(user);
		// セッション情報を更新
		session.setAttribute("loginUser", user);

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
	}
}
