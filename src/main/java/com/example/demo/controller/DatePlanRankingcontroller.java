package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DatePlanRankingcontroller {

    // デートプランランキングページを表示する
    @GetMapping("/datePlanRanking")
    public ModelAndView showDatePlanRankingPage() {
        // ModelAndViewでビューを返す
        ModelAndView mav = new ModelAndView("/dateplun_ranking/dateplun_ranking"); // date_plan_ranking.htmlを返す
        return mav;
    }
}
