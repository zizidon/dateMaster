package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dateMaster")
public class Partnercontroller {

	@GetMapping("/partner")
	public String showPartnerPage() {

		//partner.htmlを返す

		return "partner/partner";
	}
}
