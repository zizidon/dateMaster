package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DatePlancontroller {

    // デートプラン作成ページを表示する
    @GetMapping("/dateCreate")
    public ModelAndView showDateCreatePage() {
        ModelAndView mav = new ModelAndView("dateplun/date_create"); // date_create.htmlに遷移
        // 必要なデータをモデルに追加する場合は、ここでモデルにデータを追加
        // mav.addObject("key", value);
        return mav;
    }

    

    // デートスポットを削除する処理（削除ボタン）
    @PostMapping("/deleteDateSpot")
    public String deleteDateSpot(@RequestParam("spotId") Long spotId) {
        // デートスポットを削除する処理
        // 例えば、データベースから削除する
        return "redirect:/dateCreate"; // 削除後、同じページにリダイレクト
    }

    // 機能一覧ページに戻る
    @GetMapping("/featureList")
    public String goToFeatureList() {
        return "date/date"; // 機能一覧ページに遷移
    }

    // 検索画面に遷移
    @GetMapping("/search")
    public ModelAndView showSearchPage() {
        ModelAndView mav = new ModelAndView("dateplun/date_add"); // 検索画面 (date_add.html)
        return mav;
    }

    // デートプラン作成ページに遷移
    @GetMapping("/createDatePlan")
    public ModelAndView goToCreateDatePlan() {
        ModelAndView mav = new ModelAndView("createDatePlan"); // createDatePlan.htmlに遷移
        return mav;
    }
}
