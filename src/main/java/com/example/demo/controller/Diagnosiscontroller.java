package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dateMaster")
public class Diagnosiscontroller {
	
	@GetMapping("/diagnosis")
	public String showDiagnosisPage() {
		
		//diagnosis.htmlを返す
		
		return "diagnosis/diagnosis";
	}

}
