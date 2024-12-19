package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Users;
import com.example.demo.service.NameChangeService;

@Controller
@RequestMapping("/dateMaster")
public class Namecontroller {

	@Autowired
	NameChangeService nameChangeService;

	@Autowired
	HttpSession session;

	//名前変更画面へ移動する
	@GetMapping("/name")
	public ModelAndView showNamePage(ModelAndView mav) {

		//セッションからユーザー情報を取得
		Users user = (Users) session.getAttribute("loginUser");

		mav.addObject("user", user); //モデルにユーザー情報を追加
		mav.setViewName("name/name_change"); //名前変更画面へ遷移

		return mav;
	}

	//名前変更確認画面へ移動する
	@PostMapping("/confirm")
	public ModelAndView confirmNameChange(String newName, ModelAndView mav) {
		//セッションからユーザー情報を取得
		Users user = (Users) session.getAttribute("loginUser");

		mav.addObject("currentName", user.getName()); //現在の名前
		mav.addObject("newName", newName); //変更後の名前
		session.setAttribute("newName", newName); //変更後の名前をセッションに一時保存
		mav.setViewName("name/name_change_confirm"); //名前変更確認画面へ遷移
		return mav;
	}

	//名前変更完了画面へ移動する
	@PostMapping("/complete")
	public ModelAndView completeNameChange(ModelAndView mav) {
		//セッションからユーザー情報を取得
		Users user = (Users) session.getAttribute("loginUser"); //現在のユーザー情報

		String newName = (String) session.getAttribute("newName"); //セッションから新しい名前を取得
		
		//名前を変更
		user.setName(newName);
		
		//DB保存処理
		nameChangeService.updateUser(user);
		
		//セッション情報を更新
		session.setAttribute("loginUser", user);
		
		mav.addObject("message", "名前を変更しました");
		mav.setViewName("name/name_change_complete");
		return mav;
	}
}
