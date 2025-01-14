package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.DateSpot;
import com.example.demo.repository.DateSpotRepository;

@Controller
public class DatePlancontroller {

    @Autowired
    private DateSpotRepository dateSpotRepository;

    // デートプラン作成ページを表示するメソッド
    @GetMapping("/dateCreate")
    public String showDateCreatePage(Model model) {
        return "dateplun/date_create";  // date_create.htmlに遷移
    }

    // スポット検索ページを表示するメソッド
    @GetMapping("/searchSpots")
    public String searchSpots(@RequestParam(value = "query", required = false) String query, Model model) {
        List<DateSpot> spots;
        boolean noResults = false;

        if (query != null && !query.isEmpty()) {
            // 部分一致検索を実行
            spots = dateSpotRepository.findBySpotNameContaining(query);
            
            // 検索結果が空の場合、エラーメッセージを表示するフラグを設定
            if (spots.isEmpty()) {
                noResults = true;  // 検索結果がなければフラグを立てる
            }
        } else {
            // クエリが空の場合、全スポットを返す
            spots = dateSpotRepository.findAll();
        }

        // 検索結果をモデルに渡す
        model.addAttribute("spots", spots);
        model.addAttribute("noResults", noResults);  // エラーフラグを渡す
        model.addAttribute("query", query);  // 現在の検索キーワードを保持

        return "dateplun/date_add";  // date_add.htmlを表示
    }

    // デートプランを作成するメソッド
    @PostMapping("/createDatePlan")
    public String goToConfirmPage(@RequestParam("planName") String planName,  // フォームから送られたプラン名
                                  @RequestParam("spotNames[]") List<String> spotNames,  // フォームから送られたスポット名
                                  @RequestParam("spotDescriptions[]") List<String> spotDescriptions,  // スポットの説明
                                  @RequestParam("spotAddresses[]") List<String> spotAddresses,  // スポットの住所
                                  @RequestParam("spotOpenings[]") List<String> spotOpenings,  // 営業時間
                                  Model model) {

        // 入力されたスポット情報を元にDateSpotオブジェクトを作成し、リストに格納
        List<DateSpot> selectedSpots = new ArrayList<>();
        for (int i = 0; i < spotNames.size(); i++) {
            DateSpot spot = new DateSpot();
            spot.setSpotName(spotNames.get(i));
            spot.setDescription(spotDescriptions.get(i));
            spot.setSpotAddress(spotAddresses.get(i));
            spot.setOpeningMonday(spotOpenings.get(i));

            selectedSpots.add(spot);
        }

        // プラン名と選択されたスポット情報をモデルに追加
        model.addAttribute("planName", planName);
        model.addAttribute("spots", selectedSpots);

        return "date_confirm";  // date_confirm.htmlに遷移
    }
}
