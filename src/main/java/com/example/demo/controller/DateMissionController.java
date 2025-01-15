package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.DateMission_high;
import com.example.demo.entity.DateMission_mid;
import com.example.demo.entity.DateMission_row;
import com.example.demo.repository.DateMission_high_Repository;
import com.example.demo.repository.DateMission_mid_Repository;
import com.example.demo.repository.DateMission_row_Repository;

@Controller
public class DateMissionController {

    @Autowired
    private DateMission_row_Repository dateMission_row_Repository;

    @Autowired
    private DateMission_mid_Repository dateMission_mid_Repository;
    
    @Autowired
    private DateMission_high_Repository dateMission_high_Repository;


    // デートミッション共通
    @GetMapping("/dateRank")
    public String showRankSelectionPage() {
        return "date_mission/rank_selection"; // rank_selection.htmlに遷移
    }

    @GetMapping("/date")
    public String showHomePage() {
        return "date/date"; // date.htmlに遷移
    }

    @GetMapping("/rankSelection")
    public String rankSelection() {
        return "date_mission/rank_selection"; // rank_selection.htmlに遷移
    }

    @GetMapping("/start")
    public String missionStart() {
        return "date_mission/mission_start"; // mission_start.htmlに遷移
    }

    // 初級ボタンを押下
    @GetMapping("/rank_row")
    public String showRankRow(Model model) {
        List<DateMission_row> allMissions = (List<DateMission_row>) dateMission_row_Repository.findAll();
        List<DateMission_row> randomMissions = getRandomMissions(allMissions, 3);
        model.addAttribute("missions", randomMissions);
        return "date_mission/rank_row"; // rank_row.htmlに遷移
    }

    // 中級ボタンを押下
    @GetMapping("/rank_mid")
    public String showRankMid(Model model) {
        List<DateMission_mid> allMissions = (List<DateMission_mid>) dateMission_mid_Repository.findAll();
        List<DateMission_mid> randomMissions = getRandomMissions(allMissions, 3);
        model.addAttribute("missions", randomMissions);
        return "date_mission/rank_mid"; // rank_mid.htmlに遷移
    }

    // 上級ボタンを押下
    @GetMapping("/rank_high")
    public String showRankHigh(Model model) {
        // 上級ミッションが実装された場合のために仮実装（現在は中級と同様の動作）
        List<DateMission_high> allMissions = (List<DateMission_high>) dateMission_high_Repository.findAll();
        List<DateMission_high> randomMissions = getRandomMissions(allMissions, 3);
        model.addAttribute("missions", randomMissions);
        return "date_mission/rank_high"; // rank_high.htmlに遷移
    }

    // 初級ミッションを更新
    @GetMapping("/updateMissions")
    public String updateMissions(Model model) {
        List<DateMission_row> allMissions = (List<DateMission_row>) dateMission_row_Repository.findAll();
        List<DateMission_row> randomMissions = getRandomMissions(allMissions, 3);
        model.addAttribute("missions", randomMissions);
        return "date_mission/rank_row"; // ミッション更新後、rank_row.htmlに遷移
    }
    
 // 中級ミッションを更新
    @GetMapping("/updateMissions2")
    public String updateMissions2(Model model) {
        List<DateMission_mid> allMissions = (List<DateMission_mid>) dateMission_mid_Repository.findAll();
        List<DateMission_mid> randomMissions = getRandomMissions(allMissions, 3);
        model.addAttribute("missions", randomMissions);
        return "date_mission/rank_mid"; // ミッション更新後、rank_row.htmlに遷移
    }
    
 // 上級ミッションを更新
    @GetMapping("/updateMissions3")
    public String updateMissions3(Model model) {
		List<DateMission_high> allMissions = (List<DateMission_high>) dateMission_high_Repository.findAll();
        List<DateMission_high> randomMissions = getRandomMissions(allMissions, 3);
        model.addAttribute("missions", randomMissions);
        return "date_mission/rank_high"; // ミッション更新後、rank_row.htmlに遷移
    }

    // ランダムなミッションを取得するヘルパーメソッド
    private <T> List<T> getRandomMissions(List<T> missions, int count) {
        if (missions.isEmpty()) {
            return Collections.emptyList();
        }
        List<T> shuffled = new ArrayList<>(missions);
        Collections.shuffle(shuffled);
        return shuffled.subList(0, Math.min(count, shuffled.size()));
    }
}
