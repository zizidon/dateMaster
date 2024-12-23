package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.repository.UserRepository;

public class UserDeleteService {

	
	@Autowired
	UserRepository userRepo;
	
	//ユーザー削除処理
	public void deleteUser(Long id) {
		userRepo.deleteById(id);
	}
}
