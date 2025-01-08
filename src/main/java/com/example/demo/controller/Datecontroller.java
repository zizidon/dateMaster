package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Datecontroller {

    // デートプラン作成ページを表示する
    @GetMapping("/dateCreate")
    public ModelAndView showDateCreatePage() {
        ModelAndView mav = new ModelAndView("/dateplun/date_create"); // ここで、date_create.html を返す
        // 必要に応じてデータをモデルに追加
        // mav.addObject("someData", someValue);
        return mav;
    }

    // デートスポットを追加する処理（＋ボタン）
    @PostMapping("/addDateSpot")
    public String addDateSpot(@RequestParam("spotDetails") String spotDetails) {
        // 新しいデートスポットを追加する処理
        // spotDetailsを使って処理を実行
        return "redirect:/dateCreate"; // 追加後、dateCreateページにリダイレクト
    }

    // デートスポットを削除する処理（削除ボタン）
    @PostMapping("/deleteDateSpot")
    public String deleteDateSpot(@RequestParam("spotId") Long spotId) {
        // デートスポットを削除する処理
        return "redirect:/dateCreate"; // 削除後、同じページにリダイレクト
    }

    // 機能一覧ページに戻る
    @GetMapping("/featureList")
    public String goToFeatureList() {
        return "redirect:/dateMaster"; // 必要に応じてURLを変更
    }

    // デートプラン作成ページに遷移
    @GetMapping("/createDatePlan")
    public ModelAndView goToCreateDatePlan() {
        ModelAndView mav = new ModelAndView("createDatePlan");
        // 必要に応じてデータをモデルに追加
        return mav;
    }
}
