package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dateMaster")
public class UserManagementcontroller {

	
	//ユーザー管理画面に移動する
	@GetMapping("/userManagement")
	public String userManagement() {
		
		//user_management.htmlを返す
		
		return "user_management/user_management";
	}
}
