package com.example.demo.controller;

import java.util.Collections;
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
import com.example.demo.entity.MbtiResult;
import com.example.demo.entity.MbtiType;
import com.example.demo.entity.Users;
import com.example.demo.repository.MbtiResultRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.MbtiService;

@Controller
@RequestMapping("dateMaster")
public class Mbticontroller {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	MbtiService mbtiService;
	
	@Autowired
	MbtiResultRepository mbtiResultRepository;
	
	@Autowired
	UserRepository userRepository;
	
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
	    // セッションからユーザー情報を取得
	    Users user = (Users) session.getAttribute("loginUser");

	    // ユーザーがログインしている場合、診断結果を計算
	    String resultType = mbtiService.calculateResult(answers, user.getId());
	    model.addAttribute("resultType", resultType);

	    // ユーザーの診断結果に基づいてMbtiTypeを取得
	    List<MbtiType> mbtiDetails = mbtiService.getMbtiDetailsByUserId(user.getId());

	    // 診断結果に関連する説明を取得
	    if (!mbtiDetails.isEmpty()) {
	        MbtiType mbtiType = mbtiDetails.get(0);  // 最新の結果のタイプを取得
	        model.addAttribute("description", mbtiType.getDescription());
	    }

	    return "mbti_diagonosis/result"; // 結果を表示するHTMLに遷移
	}
	
	@GetMapping("history")
	public String showHistory(Model model) {
	    // セッションからログインユーザー情報を取得
	    Users user = (Users) session.getAttribute("loginUser");

	    // ユーザーがログインしている場合
	    if (user != null) {
	        // ユーザーの診断結果をデータベースから取得
	        List<MbtiResult> results = mbtiResultRepository.findByUserId(user.getId());
	        
	        // 最後の5件のみ取得し、逆順に並び替える
	        if (results.size() > 1) {
	            results = results.subList(results.size() - 1, results.size()); // 最後の5件
	        }
	        Collections.reverse(results); // 逆順に並び替え
	        
	        model.addAttribute("results", results);  // モデルに診断結果をセット
	    }
	    // 診断結果履歴を表示するためのHTMLページを返す
	    return "mbti_diagonosis/history";
	}


}