package com.example.demo.service;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.repository.UserRepository;

public class UserRegisterService {

	@Autowired
	HttpSession session;

	@Autowired
	UserRepository userRepo;

	public boolean register(String id, String name, String password, String confirmPassword) {

		//パスワード確認
		if (password.equals(confirmPassword)) {
			System.out.println("一致");
			return true;
		} else {
			System.out.println("不一致");
			return false;
		}
	}

}
