package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
        
        // 必要に応じて、モデルデータを追加
        // modelAndView.addObject("someData", someValue);
        
        return modelAndView;
    }

    // 戻るボタンの処理
    @GetMapping("/dateMaster/back")
    public String goBack() {
        // 戻るボタンを押したときの処理（例: 他のページに遷移する）
        return "date/date"; // 必要に応じて適切なURLを指定
    }

    // 次へ進むボタンの処理
    @GetMapping("/dateMaster/confirm")
    public ModelAndView goToConfirmation() {
        // 次へ進むボタンを押したときの処理
        ModelAndView modelAndView = new ModelAndView("confirmationPage"); // confirmationPageは確認画面のビュー名
        return modelAndView;
    }
}
