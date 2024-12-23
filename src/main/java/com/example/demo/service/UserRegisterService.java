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

	public Long register(String name, String password, String confirmPassword) {

		//パスワード確認
		if (!password.equals(confirmPassword)) {
			throw new IllegalArgumentException("パスワードが一致しません。");

		}

		//新規ユーザーを保存
		Users user = new Users();
		user.setName(name);
		user.setPassword(password);

		userRepo.save(user);//idは自動採番される
		return user.getId(); //新規ユーザーのIDを返す
	}

}
