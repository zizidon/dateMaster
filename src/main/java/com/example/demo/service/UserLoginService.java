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
		
		// ユーザーIDが数字かどうかを確認
		if (!isNumeric(userid)) {
			return "ユーザーIDは数字でなければなりません。";
		}

		// ユーザーIDで検索
		Optional<Users> user = userRepo.findById(Long.valueOf(userid));

		if (user.isEmpty()) {
			return "ユーザーIDが存在しません。";
		}

		// パスワードが一致しているかの確認
		if (!password.equals(user.get().getPassword())) {
			return "パスワードが一致しません。";
		}

		// ログイン成功 - セッションにユーザー情報を保存
		session.setAttribute("loginUser", user.get()); // Usersオブジェクトを保存
		System.out.println("ログイン成功");
		return "OK";
	}
	
	// 入力値が数値かどうかを判定するメソッド
	private boolean isNumeric(String str) {
		if (str == null || str.isEmpty()) {
			// 空の場合は数値ではない
			return false;
		}
		try {
			Long.parseLong(str); // 数値に変換できればtrue
			return true;
		} catch (NumberFormatException e) {
			// 数値に変換できなければfalse
			return false;
		}
	}
}