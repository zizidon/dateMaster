package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.entity.Users;
import com.example.demo.repository.UserRepository;

public class NameChangeService {

	@Autowired
	UserRepository userRepo;

	//ユーザー情報を取得するメソッド
	public Users getUserId(Long id) {
		return userRepo.findById(id).orElse(null);
	}
	
	//ユーザー情報を更新するメソッド
	public void updateUser(Users user) {
		userRepo.save(user); //DBに保存
	}
}
