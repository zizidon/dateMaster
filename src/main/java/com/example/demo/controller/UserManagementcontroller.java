package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dateMaster")
public class UserManagementcontroller {

	@GetMapping("/userManagement")
	public String userManagement() {
		
		//userManagement.htmlを返す
		
		return "user_management/user_management";
	}
}
