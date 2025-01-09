package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Users;
import com.example.demo.repository.UserRepository;

@Controller
@RequestMapping("/dateMaster")
public class Partnercontroller {

	@Autowired
	HttpSession session;

	@Autowired
	UserRepository userRepo;

	//パートナー画面へ遷移
	@GetMapping("/partner")
	public ModelAndView showPartnerPage(ModelAndView mav) {
		//現在ログイン中のユーザー情報を取得
		Users user = (Users) session.getAttribute("loginUser");

		if (user != null && user.getPartner() != null) {

			//パートナーの情報をデータベースから取得
			Users partner = userRepo.findById(user.getPartner()).orElse(null);
			mav.addObject("partner", partner);
		} else {
			mav.addObject("partner", null);
		}
		mav.setViewName("partner/partner");
		return mav;
	}

	//パートナー削除画面へ遷移
	@GetMapping("/partnerDelete")
	public ModelAndView showPartnerDeletePage(ModelAndView mav) {
		Users user = (Users) session.getAttribute("loginUser");

		if (user != null && user.getPartner() != null) {
			Users partner = userRepo.findById(user.getPartner()).orElse(null);
			mav.addObject("partner", partner);
		} else {
			mav.addObject("partner", null);
		}
		mav.setViewName("partnerDelete/partner_delete");
		return mav;
	}

	//パートナー削除確認画面へ遷移
	@GetMapping("/partnerDeleteCheck")
	public ModelAndView showPartnerDeleteCheckPage(ModelAndView mav) {
		mav.setViewName("partnerDelete/partner_delete_check");
		return mav;
	}

	//パートナー削除処理
	@PostMapping("/partnerDeleteComplete")
	public ModelAndView deletePartner(ModelAndView mav) {
		Users user = (Users) session.getAttribute("loginUser");

		if (user != null && user.getPartner() != null) {
			//ユーザーのパートナーIDを取得
			Long partnerId = user.getPartner();
			
			//ユーザーのパートナー情報を削除
			user.setPartner(null);
			userRepo.save(user);
			
			//相手(パートナー)のパートナー情報を削除
			Users partner = userRepo.findById(partnerId).orElse(null);
			if (partner != null) {
				partner.setPartner(null);
				userRepo.save(partner);
			}
		}

		mav.setViewName("partnerDelete/partner_delete_complete");

		return mav;
	}

	//パートナー了承画面へ遷移
	@GetMapping("/partnerAccept")
	public ModelAndView showPartnerAcceptPage(ModelAndView mav) {
		Users user = (Users) session.getAttribute("loginUser");

		if (user != null) {
			// 浮気チェック
			if (user.getPartner() != null) {
				mav.setViewName("partnerAccept/partner_already");
				return mav;
			}

			// applicant 情報を取得
			Users applicant = null;
			if (user.getApplicant() != null) {
				applicant = userRepo.findById(user.getApplicant()).orElse(null);
			}
			mav.addObject("applicant", applicant);
		}

		mav.setViewName("partnerAccept/partner_accept");
		return mav;
	}

	// applicant を拒否
	@PostMapping("/partnerReject")
	public String rejectApplicant() {
		Users user = (Users) session.getAttribute("loginUser");

		if (user != null) {
			user.setApplicant(null);
			userRepo.save(user);
		}

		return "redirect:/dateMaster/partner";
	}

	// applicant を承認
	@PostMapping("/partnerApprove")
	public String approveApplicant() {
		Users user = (Users) session.getAttribute("loginUser");

		if (user != null && user.getApplicant() != null) {
			user.setPartner(user.getApplicant());
			user.setApplicant(null);
			userRepo.save(user);
		}

		return "redirect:/dateMaster/partner";
	}
}
