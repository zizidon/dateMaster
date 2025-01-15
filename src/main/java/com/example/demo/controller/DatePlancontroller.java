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
@SessionAttributes("selectedSpots")
public class DatePlancontroller {

    @Autowired
    private DateSpotRepository dateSpotRepository;

    // デートプラン作成画面
    @GetMapping("/dateCreate")
    public String showDateCreatePage(Model model) {
        // 初期状態として空のリストを設定
        List<DateSpot> selectedSpots = (List<DateSpot>) model.getAttribute("selectedSpots");
        if (selectedSpots == null) {
            selectedSpots = new ArrayList<>();
        }
        model.addAttribute("selectedSpots", selectedSpots);

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
                                 @RequestParam("spotOpeningMonday") String spotOpeningMonday,
                                 @RequestParam("spotOpeningTuesday") String spotOpeningTuesday,
                                 @RequestParam("spotOpeningWednesday") String spotOpeningWednesday,
                                 @RequestParam("spotOpeningThursday") String spotOpeningThursday,
                                 @RequestParam("spotOpeningFriday") String spotOpeningFriday,
                                 @RequestParam("spotOpeningSaturday") String spotOpeningSaturday,
                                 @RequestParam("spotOpeningSunday") String spotOpeningSunday,
                                 Model model) {
        // 新しいスポットオブジェクトを作成して、フォームから送られたデータをセット
        DateSpot selectedSpot = new DateSpot();
        selectedSpot.setSpotName(spotName);
        selectedSpot.setDescription(spotDescription);
        selectedSpot.setSpotAddress(spotAddress);
        selectedSpot.setMonday(spotOpeningMonday);
        selectedSpot.setTuesday(spotOpeningTuesday);
        selectedSpot.setWednesday(spotOpeningWednesday);
        selectedSpot.setThursday(spotOpeningThursday);
        selectedSpot.setFriday(spotOpeningFriday);
        selectedSpot.setSaturday(spotOpeningSaturday);
        selectedSpot.setSunday(spotOpeningSunday);

        // セッションから選ばれたスポットリストを取得
        List<DateSpot> selectedSpots = (List<DateSpot>) model.getAttribute("selectedSpots");
        if (selectedSpots == null) {
            selectedSpots = new ArrayList<>();
        }

        // 重複チェック：スポット名がすでに選ばれていないか確認
        boolean isDuplicate = selectedSpots.stream()
                                           .anyMatch(spot -> spot.getSpotName().equals(selectedSpot.getSpotName()));
        if (!isDuplicate) {
            selectedSpots.add(selectedSpot); // スポットを追加
        } else {
            model.addAttribute("message", "このスポットはすでに追加されています。");
        }

        // セッションに選ばれたスポットを保存
        model.addAttribute("selectedSpots", selectedSpots);

        // デートプラン作成ページにリダイレクト
        return "redirect:/dateCreate";
    }

    // デートプラン確認
    @PostMapping("/createDatePlan")
    public String createDatePlan(@RequestParam("spotNames") String spotNames,
                                 @RequestParam("spotDescriptions") String spotDescriptions,
                                 @RequestParam("spotAddresses") String spotAddresses,
                                 @RequestParam("spotOpenings") String spotOpenings,
                                 Model model) {

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
            spot.setMonday(spotOpeningArray[i]);

            selectedSpots.add(spot);
        }

        model.addAttribute("spots", selectedSpots);

        return "dateplun/date_create_check"; // 確認画面
    }

    
}
