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

	public Long register(String name, String password, String confirmPassword, String question, String answer) {

		//パスワード確認
		if (!password.equals(confirmPassword)) {
			throw new IllegalArgumentException("パスワードが一致しません。");

		}

		//新規ユーザーを保存
		Users user = new Users();
		user.setName(name);
		user.setPassword(password);
		user.setQuestion(question); // 秘密の質問を設定
		user.setAnswer(answer); // 秘密の質問の回答を設定

		userRepo.save(user);//idは自動採番される
		return user.getId(); //新規ユーザーのIDを返す
	}

}
