package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dateMaster")
public class Diagnosiscontroller {

	//自己診断一覧画面へ遷移
	@GetMapping("/diagonosis")
	public String showDiagnosisPage() {

		//diagnosis.htmlを返す

		return "diagonosis/diagonosis";
	}

	//自己診断回答画面へ遷移
	@GetMapping("/self_diagonosis")
	public String showSelfDiagnosisPage() {
		// self_diagnosis.htmlを返す
		return "self_diagonosis/self_diagonosis";
	}
}
