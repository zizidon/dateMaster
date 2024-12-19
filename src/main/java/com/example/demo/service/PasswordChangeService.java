package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.entity.Users;
import com.example.demo.repository.UserRepository;

public class PasswordChangeService {

	@Autowired
	UserRepository userRepo;

	//パスワードを更新するメソッド
	public void updatePassword(Long userId, String newPassword) {
		Users user = userRepo.findById(userId).orElse(null);
		if (user != null) {
			user.setPassword(newPassword);
			userRepo.save(user); //パスワード更新
		}
	}
}
