package com.example.demo.service;

import java.util.Optional;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.entity.Users;
import com.example.demo.repository.UserRepository;

public class UserLoginService {

	@Autowired
	HttpSession session;

	@Autowired
	UserRepository userRepo;

	public String login(String userid, String password) {

		//ユーザーが存在するか確認
		Optional<Users> user = userRepo.findById(Long.valueOf(userid));

		//boolean isExists = userRepo.existsById(Long.valueOf(userid));

		if (user.isEmpty()) {

			return "ユーザーIDが存在しません。";
		}

		//パスワードが一致しているかの確認
		if (!password.equals(user.get().getPassword())) {

			return "パスワードが一致しません。";

		}

		//ログイン成功 - セッションにユーザー情報を保存
		session.setAttribute("loginUser", user.get()); // Usersオブジェクトを保存
		System.out.println("ログイン成功");
		return "OK";

	}

}
