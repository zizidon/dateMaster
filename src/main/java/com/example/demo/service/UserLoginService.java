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
		session.setAttribute("userid", userid);
		session.setAttribute("password", password);

		//ユーザーが存在するか確認
		boolean isExists = userRepo.existsById(Long.valueOf(userid));

		if (!isExists) {

			return "ユーザーIDが存在しません。";
		}

		//ユーザー情報を取得
		Optional<Users> user = userRepo.findById(Long.valueOf(userid));

		//パスワードが一致しているかの確認
		if (!password.equals(user.get().getPassword())) {
			System.out.println("一致");

			return "パスワードが一致しません。";

		}
		
		//ログイン成功
		System.out.println("ログイン成功");
		return "OK";

	}

}
