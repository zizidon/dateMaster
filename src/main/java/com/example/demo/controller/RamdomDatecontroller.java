package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Users;

@Controller
public class RamdomDatecontroller {

    @Autowired
    HttpSession session;

    // ランダムデートプランページを表示するメソッド
    @GetMapping("/randomDate")
    public ModelAndView showRandomDatePage() {
        ModelAndView modelAndView = new ModelAndView("random_date/answer");

        // セッションからユーザー情報を取得
        Users user = (Users) session.getAttribute("loginUser");
        
        // ユーザー情報があれば、モデルに追加
        if (user != null) {
            modelAndView.addObject("user", user);
        }
        
        return modelAndView;
    }

    // 戻るボタンの処理
    @GetMapping("/dateMaster/back")
    public String goBack() {
        return "date/date"; // 戻るページに遷移
    }

    // 次へ進むボタンの処理（フォーム送信で選択された内容を受け取る）
    @PostMapping("/dateMaster/date_confirm")
    public ModelAndView goToConfirmation(
        @RequestParam("mood") String mood,
        @RequestParam("weather") String weather,
        @RequestParam("planCount") String planCount) {

        ModelAndView modelAndView = new ModelAndView("random_date/confirm_answer"); // 確認ページに遷移

        // フォームから送信されたデータをモデルに追加
        modelAndView.addObject("mood", mood);
        modelAndView.addObject("weather", weather);
        modelAndView.addObject("planCount", planCount);
        
        return modelAndView;
    }
}
