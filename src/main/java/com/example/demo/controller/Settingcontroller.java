package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dateMaster")
public class Settingcontroller {

	@GetMapping("/setting")
	public String setting() {
		
		//setting.htmlを返す
		
		return "setting/setting";
	}
}
