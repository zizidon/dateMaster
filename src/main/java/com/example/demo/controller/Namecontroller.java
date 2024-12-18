package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dateMaster")
public class Namecontroller {

	@GetMapping("/name")
	public String showNamePage() {
		
		//name_change.htmlを返す
		
		
		return "name/name_change";
	}
}
