package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DatePlanSharecontroller {

    @GetMapping("/datePlanShare")
    //プラン共有画面表示
    public ModelAndView showDatePlanSharePage() {
        ModelAndView mav = new ModelAndView("/date_reference/date_reference_list");

        

        return mav;
    }
}
