package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DateMissioncontroller {

    // ランク選択画面を表示
    @GetMapping("/dateMission")
    public String showRankSelectionPage() {
        return "date_mission/rank_selection";  // HTMLファイルのパス
    }

    
}
