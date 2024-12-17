package com.example.demo.service;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

public class UserRegisterService {

	@Autowired
	HttpSession session;

	public boolean register(String password, String confirmPassword) {

		session.setAttribute("password", password);
		session.setAttribute("confirmPassword", confirmPassword);

		//パスワード確認
		if (password.equals(confirmPassword)) {
			return true;
		} else {
			return false;
		}
	}

}
