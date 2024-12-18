package com.example.demo.service;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.entity.Users;
import com.example.demo.repository.UserRepository;

public class UserRegisterService {

	@Autowired
	HttpSession session;

	@Autowired
	UserRepository userRepo;

	public String register(String name, String password, String confirmPassword) {

		//パスワード確認
		if (!password.equals(confirmPassword)) {
			System.out.println("パスワードが一致しません。");
			return "パスワードが一致しません";

		}

		//新規ユーザーを保存
		Users user = new Users();
		user.setName(name);
		user.setPassword(password);

		userRepo.save(user);//idは自動採番される
		System.err.println("登録完了");
		return "登録完了";
	}

}
