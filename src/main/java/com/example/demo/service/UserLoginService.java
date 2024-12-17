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

	public boolean login(String userid, String password) {
		session.setAttribute("userid", userid);
		session.setAttribute("password", password);

		boolean isExists;

		isExists = userRepo.existsById(userid);

		if (isExists) {

			Optional<Users> user = userRepo.findById(userid);

			if (password.equals(user.get().getPassword())) {
				System.out.println("一致");

				return true;

			} else {
				System.out.println("不一致");
				return false;
			}

		} else {

			return false;

		}

	}
}
