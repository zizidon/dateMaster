package com.example.demo.controller;

import java.util.Optional;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Users;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.PartnerRequestService;

@Controller
@RequestMapping("dateMaster")
public class Partnercontroller {

	@Autowired
	HttpSession session;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
    PartnerRequestService partnerRequestService;

	//パートナー画面へ遷移
	@GetMapping("partner")
	public ModelAndView showPartnerPage(ModelAndView mav) {
		//現在ログイン中のユーザー情報を取得
		Users user = (Users) session.getAttribute("loginUser");

		if (user != null && user.getPartner() != null) {

			//パートナーの情報をデータベースから取得
			Users partner = userRepository.findById(user.getPartner()).orElse(null);
			mav.addObject("partner", partner);
		} else {
			mav.addObject("partner", null);
		}
		mav.setViewName("partner/partner");
		return mav;
	}
	
	//パートナー申請画面を表示
	@GetMapping("partner_request")
	public ModelAndView showPartnerRequestPage() {
		ModelAndView modelAndView = new ModelAndView("partner_request/partner_request");
		
		Users user = (Users) session.getAttribute("loginUser");
		
		if(user != null) {
			modelAndView.addObject("user",user);
		}
		return modelAndView;
	}
	
	//パートナーID検索をする
    @GetMapping("user/search")
    public String search(@RequestParam Long id, Model model) {
        Optional<Users> userOpt = partnerRequestService.getUserById(id);

        if (userOpt.isPresent()) {
            model.addAttribute("searchedUser", userOpt.get());
            model.addAttribute("message", null);
        } else {
            model.addAttribute("searchedUser", null);
            model.addAttribute("message", "指定されたIDのユーザーが見つかりませんでした。");
        }

        return "partner_request/partner_request";
    }

    // パートナー申請を実行
    @PostMapping("partner_request")
    public String requestPartner(@RequestParam("id") Long partnerId, Model model) {
        // セッションからログイン中のユーザー情報を取得
        Users loggedInUser = (Users) session.getAttribute("loginUser");

        if (loggedInUser == null) {
            model.addAttribute("message", "ログインが必要です。");
            return "partner_request/partner_request";
        }

        // パートナー申請処理
        Optional<Users> partnerOpt = userRepository.findById(partnerId);

        if (partnerOpt.isPresent()) {
            Users partner = partnerOpt.get();

            // パートナー申請を実行
            boolean success = partnerRequestService.requestPartner(loggedInUser, partner);

            if (success) {
                model.addAttribute("message", "パートナー申請が成功しました。");
                model.addAttribute("updatedPartner", partner);
            } else {
                // すでに申請済みまたはパートナーが設定されている場合のエラー
                model.addAttribute("message", "このユーザーにはすでに申請済み、または既にパートナーです。");
            }
        } else {
            model.addAttribute("message", "指定されたIDのユーザーが見つかりませんでした。");
        }

        return "partner_request/partner_request";
    }
}
