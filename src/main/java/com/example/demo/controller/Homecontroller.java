package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dateMaster")
public class Homecontroller {

	
	@GetMapping("/date")
	public String showDatePage() {
		
		//date.htmlを返す
		
		return "date/date";
	}
}
