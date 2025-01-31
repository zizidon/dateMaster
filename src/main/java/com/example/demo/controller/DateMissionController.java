package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.DateMission_high;
import com.example.demo.entity.DateMission_mid;
import com.example.demo.entity.DateMission_row;
import com.example.demo.repository.DateMission_high_Repository;
import com.example.demo.repository.DateMission_mid_Repository;
import com.example.demo.repository.DateMission_row_Repository;

@Controller
public class DateMissionController {

	@Autowired
    private HttpSession session;
	
    @Autowired
    private DateMission_row_Repository dateMission_row_Repository;

    @Autowired
    private DateMission_mid_Repository dateMission_mid_Repository;
    
    @Autowired
    private DateMission_high_Repository dateMission_high_Repository;

     
   

    // デート機能一覧からデートミッションランク選択画面に遷移
    @GetMapping("/dateRank")
    public String showRankSelectionPage() {
        return "date_mission/rank_selection"; // rank_selection.htmlに遷移
    }
    
    
    //デートミッションランク選択画面からデート機能一覧に戻る
    @GetMapping("/date")
    public String showHomePage() {
        return "date/date"; // date.htmlに遷移
    }
    
    //それぞれのミッション検討画面からミッション難易度一覧に遷移
    @GetMapping("/rankSelection")
    public String rankSelection() {
        return "date_mission/rank_selection"; // rank_selection.htmlに遷移
    }
    
   
    
    // ミッション達成状態を処理
    @PostMapping("/missionAchieve")
    public String missionAchieve(@RequestParam Map<String, String> missionResults) {
        int totalCount = 0;
        
        // ラジオボタンの選択結果を集計
        for (String value : missionResults.values()) {
            if ("1".equals(value)) {  // 達成の場合
                totalCount++;
            }
        }
        
        // 合計値をセッションに保存
        session.setAttribute("missionCount", totalCount);
        
        return "redirect:/missionEvaluation";
    }


    
    // 評価画面の表示
    @GetMapping("/missionEvaluation")
    public String missionEvaluation(Model model) {
        Integer missionCount = (Integer) session.getAttribute("missionCount");
        if (missionCount == null) {
            missionCount = 0;
        }

        // カウント値に応じて評価を決定
        String evaluation;
        switch (missionCount) {
            case 3:
                evaluation = "デートマスター";
                break;
            case 2:
                evaluation = "デートエキスパート";
                break;
            case 1:
                evaluation = "デートビギナー";
                break;
            default:
                evaluation = "未評価";
        }

        model.addAttribute("evaluation", evaluation);
        
        
        // 評価後にカウントを初期化
        session.setAttribute("missionCount", 0);
        
        return "date_mission/mission_evaluation";
    }
  
    // 初級ボタンを押下
    @GetMapping("/rank_row")
    public String showRankRow(Model model) {
    	
        List<DateMission_row> allMissions = (List<DateMission_row>) dateMission_row_Repository.findAll();
        List<DateMission_row> randomMissions = getRandomMissions(allMissions, 3);
        
        session.setAttribute("currentMissions", randomMissions); // セッションに保存
        model.addAttribute("missions", randomMissions);
		return "date_mission/rank_row";
    }
    
 
    // 中級ボタンを押下
    @GetMapping("/rank_mid")
    public String showRankMid(Model model) {
        List<DateMission_mid> allMissions = (List<DateMission_mid>) dateMission_mid_Repository.findAll();
        List<DateMission_mid> randomMissions = getRandomMissions(allMissions, 3);
        
        session.setAttribute("currentMissions", randomMissions); // セッションに保存
        model.addAttribute("missions", randomMissions);
        return "date_mission/rank_mid"; // rank_mid.htmlに遷移
    }

    // 上級ボタンを押下
    @GetMapping("/rank_high")
    public String showRankHigh(Model model) {
        // 上級ミッションが実装された場合のために仮実装（現在は中級と同様の動作）
        List<DateMission_high> allMissions = (List<DateMission_high>) dateMission_high_Repository.findAll();
        List<DateMission_high> randomMissions = getRandomMissions(allMissions, 3);
        
        session.setAttribute("currentMissions", randomMissions); // セッションに保存
        model.addAttribute("missions", randomMissions);
        return "date_mission/rank_high"; // rank_high.htmlに遷移
    }

    // 初級ミッションを更新
    @GetMapping("/updateMissions")
    public String updateMissions(Model model) {
        List<DateMission_row> allMissions = (List<DateMission_row>) dateMission_row_Repository.findAll();
        List<DateMission_row> randomMissions = getRandomMissions(allMissions, 3);
        
        session.setAttribute("currentMissions", randomMissions); // セッションを更新
		model.addAttribute("missions", randomMissions);
		return "date_mission/rank_row"; // ミッション更新後、rank_row.htmlに遷移
    
        
    }
    

 // 中級ミッションを更新
    @GetMapping("/updateMissions2")
    public String updateMissions2(Model model) {
        List<DateMission_mid> allMissions = (List<DateMission_mid>) dateMission_mid_Repository.findAll();
        List<DateMission_mid> randomMissions = getRandomMissions(allMissions, 3);
   
        
        session.setAttribute("currentMissions", randomMissions); // セッションを更新
		model.addAttribute("missions", randomMissions);
		return "date_mission/rank_mid"; // ミッション更新後、rank_row.htmlに遷移
    }
    
 // 上級ミッションを更新
    @GetMapping("/updateMissions3")
    public String updateMissions3(Model model) {
		List<DateMission_high> allMissions = (List<DateMission_high>) dateMission_high_Repository.findAll();
        List<DateMission_high> randomMissions = getRandomMissions(allMissions, 3);
       
        
        session.setAttribute("currentMissions", randomMissions); // セッションを更新
     	model.addAttribute("missions", randomMissions);
     	return "date_mission/rank_high"; // ミッション更新後、rank_row.htmlに遷移
    }
    
    // 初級スタートボタン押下時にミッションを表示
    @GetMapping("/start")
    public String missionStart( Model model) {
    	
    	// カウントを初期化
        session.setAttribute("missionCount", 0);
       
    	List<DateMission_row> missions = (List<DateMission_row>) session.getAttribute("currentMissions");
        if (missions == null || missions.isEmpty()) {
            // ミッションが存在しない場合はデフォルトのミッションを取得
            missions = getRandomMissions((List<DateMission_row>) dateMission_row_Repository.findAll(), 3);
        }
        model.addAttribute("missions", missions);
     
        return "date_mission/row_start"; // mission_start.htmlに遷移
    }
    
 
    // 中級スタートボタン押下時にミッションを表示
       @GetMapping("/start2")
       public String missionStart2( Model model) {
          
       	List<DateMission_row> missions = (List<DateMission_row>) session.getAttribute("currentMissions");
           if (missions == null || missions.isEmpty()) {
               // ミッションが存在しない場合はデフォルトのミッションを取得
               missions = getRandomMissions((List<DateMission_row>) dateMission_row_Repository.findAll(), 3);
           }
           model.addAttribute("missions", missions);
        
           return "date_mission/mid_start"; // mission_start.htmlに遷移
       }
       
    // 上級スタートボタン押下時にミッションを表示
       @GetMapping("/start3")
       public String missionStart3( Model model) {
          
       	List<DateMission_row> missions = (List<DateMission_row>) session.getAttribute("currentMissions");
           if (missions == null || missions.isEmpty()) {
               // ミッションが存在しない場合はデフォルトのミッションを取得
               missions = getRandomMissions((List<DateMission_row>) dateMission_row_Repository.findAll(), 3);
           }
           model.addAttribute("missions", missions);
        
           return "date_mission/high_start"; // mission_start.htmlに遷移
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
