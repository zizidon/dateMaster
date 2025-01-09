package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
	@GetMapping("/partner/delete")
	public ModelAndView showPartnerDeletePage(ModelAndView mav) {
		Users user = (Users) session.getAttribute("loginUser");

		if (user != null && user.getPartner() != null) {
			Users partner = userRepo.findById(user.getPartner()).orElse(null);
			mav.addObject("partner", partner);
		} else {
			mav.addObject("partner", null);
		}
		mav.setViewName("partner_delete/partner_delete");
		return mav;
	}

	//パートナー削除確認画面へ遷移
	public ModelAndView showPartnerDeleteCheckPage(ModelAndView mav) {
		mav.setViewName("partner_delete/partner_delete_check");
		return mav;
	}

	//パートナー削除処理
	public ModelAndView deletePartner(ModelAndView mav) {
		Users user = (Users) session.getAttribute("loginUser");

		if (user != null && user.getPartner() != null) {
			user.setPartner(null);
			userRepo.save(user);
		}

		mav.setViewName("partner_delete/partner_complete");

		return mav;
	}
}
