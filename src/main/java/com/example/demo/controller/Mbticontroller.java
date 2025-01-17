package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.MbtiQuestion;
import com.example.demo.entity.Users;
import com.example.demo.service.MbtiService;

@Controller
@RequestMapping("dateMaster")
public class Mbticontroller {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	MbtiService mbtiService;
	
	@GetMapping("questions")
	public ModelAndView showMbtiQuestionsPage() {
		ModelAndView modelAndView = new ModelAndView("mbti_diagonosis/questions");
		
		Users user = (Users) session.getAttribute("loginUser");
		
		if(user != null) {
			modelAndView.addObject("user", user);
		}
		
	    List<MbtiQuestion> questions = mbtiService.getAllQuestions();
	    modelAndView.addObject("questions", questions);
	    
		return modelAndView;
	}
    
	@PostMapping("answers")
	public String submitAnswers(@RequestParam Map<String, String> answers, Model model) {
		model.addAttribute("answers", answers);
		List<MbtiQuestion> questions = (List<MbtiQuestion>) mbtiService.getAllQuestions();
		model.addAttribute("questions", questions);
		return "mbti_diagonosis/answers";
	}
	
	@PostMapping("result")
	public String calculateResult(@RequestParam Map<String, String> answers, Model model) {
		String resultType = mbtiService.calculateResult(answers);
		model.addAttribute("resultType", resultType);
		return "mbti_diagonosis/result";
	}
}
