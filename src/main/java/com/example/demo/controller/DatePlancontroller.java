package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.entity.DateSpot;
import com.example.demo.repository.DateSpotRepository;

@Controller
@SessionAttributes("selectedSpots")  // ここでセッションに「selectedSpots」を保持
public class DatePlancontroller {

    @Autowired
    private DateSpotRepository dateSpotRepository;

    // デートプラン作成画面
    @GetMapping("/dateCreate")
    public String showDateCreatePage(Model model) {
        // 初期の選択されたスポットリストをセッションから取得
        List<DateSpot> selectedSpots = (List<DateSpot>) model.getAttribute("selectedSpots");
        if (selectedSpots == null) {
            selectedSpots = new ArrayList<>(); // 初めての場合は空のリスト
        }
        model.addAttribute("selectedSpots", selectedSpots);  // セッションから取得したスポットリストを渡す
        return "dateplun/date_create";  // date_create.htmlに遷移
    }

    // スポット検索画面
    @GetMapping("/searchSpots")
    public String searchSpots(@RequestParam(value = "query", required = false) String query, Model model) {
        List<DateSpot> spots = new ArrayList<>();
        boolean noResults = false;

        if (query != null && !query.isEmpty()) {
            if ("すべて".equals(query)) {
                spots = dateSpotRepository.findAll();
            } else {
                spots = dateSpotRepository.findBySpotNameContaining(query);
            }

            if (spots.isEmpty()) {
                noResults = true;
            }
        }

        // リダイレクト後に選択されたスポットリストを取得（セッションから）
        List<DateSpot> selectedSpots = (List<DateSpot>) model.getAttribute("selectedSpots");
        if (selectedSpots == null) {
            selectedSpots = new ArrayList<>();
        }

        model.addAttribute("spots", spots);
        model.addAttribute("selectedSpots", selectedSpots);  // スポットリストを渡す
        model.addAttribute("noResults", noResults);
        model.addAttribute("query", query);

        return "dateplun/date_add";  // 検索結果画面
    }

   
 // スポットをデートプランに追加
    @PostMapping("/addSpotToPlan")
    public String addSpotToPlan(@RequestParam("spotName") String spotName,
                                @RequestParam("spotDescription") String spotDescription,
                                @RequestParam("spotAddress") String spotAddress,
                                @RequestParam("spotOpening") String spotOpening,
                                Model model,
                                @RequestParam(value = "query", required = false) String query) {

        // 新しいスポットを作成
        DateSpot selectedSpot = new DateSpot();
        selectedSpot.setSpotName(spotName);
        selectedSpot.setDescription(spotDescription);
        selectedSpot.setSpotAddress(spotAddress);
        selectedSpot.setOpeningMonday(spotOpening);

        // セッションから現在の選択スポットリストを取得
        List<DateSpot> selectedSpots = (List<DateSpot>) model.getAttribute("selectedSpots");
        if (selectedSpots == null) {
            selectedSpots = new ArrayList<>();
        }

        // 重複チェック：スポット名がすでに選択リストに含まれていないか確認
        boolean isDuplicate = selectedSpots.stream()
                                           .anyMatch(spot -> spot.getSpotName().equals(selectedSpot.getSpotName()));

        if (!isDuplicate) {
            // スポットを追加
            selectedSpots.add(selectedSpot);
        } else {
            // 重複している場合は追加しない
            model.addAttribute("message", "このスポットはすでに追加されています。");
        }

        // セッションに選ばれたスポットを保存
        model.addAttribute("selectedSpots", selectedSpots);

        // 検索クエリも保持
        model.addAttribute("query", query);

        // 最後にデートプラン作成画面にリダイレクト
        return "redirect:/dateCreate";  // リダイレクト先を修正
    }

    // デートプラン確認
    @PostMapping("/createDatePlan")
    public String createDatePlan(@RequestParam("spotNames") String spotNames,
                                 @RequestParam("spotDescriptions") String spotDescriptions,
                                 @RequestParam("spotAddresses") String spotAddresses,
                                 @RequestParam("spotOpenings") String spotOpenings,
                                 Model model) {

        // 送信されたデータを使ってスポットオブジェクトを作成
        List<DateSpot> selectedSpots = new ArrayList<>();

        String[] spotNameArray = spotNames.split(",");
        String[] spotDescriptionArray = spotDescriptions.split(",");
        String[] spotAddressArray = spotAddresses.split(",");
        String[] spotOpeningArray = spotOpenings.split(",");

        for (int i = 0; i < spotNameArray.length; i++) {
            DateSpot spot = new DateSpot();
            spot.setSpotName(spotNameArray[i]);
            spot.setDescription(spotDescriptionArray[i]);
            spot.setSpotAddress(spotAddressArray[i]);
            spot.setOpeningMonday(spotOpeningArray[i]);

            selectedSpots.add(spot);
        }

        // モデルに選ばれたスポットを渡す
        model.addAttribute("spots", selectedSpots);

        // 最終的にdate_create.htmlに遷移
        return "dateplun/date_create_check";
    }
}