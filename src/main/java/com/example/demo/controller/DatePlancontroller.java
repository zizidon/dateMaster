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

        // 各スポットにカテゴリ名を設定
        for (DateSpot spot : spots) {
            spot.setCategoryName(convertDescriptionToCategory(spot.getDescription()));
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

        // カテゴリ名を設定
        selectedSpot.setCategoryName(convertDescriptionToCategory(spotDescription));

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
    public String createDatePlan(Model model) {
        // セッションから選ばれたスポットリストを取得
        List<DateSpot> selectedSpots = (List<DateSpot>) model.getAttribute("selectedSpots");

        // スポットが選ばれていない場合、エラーメッセージを設定してリダイレクト
        if (selectedSpots == null || selectedSpots.isEmpty()) {
            model.addAttribute("message", "スポットが選ばれていません。");
            return "redirect:/dateCreate";  // プラン作成画面にリダイレクト
        }

        // モデルに選ばれたスポットリストをそのまま渡す
        model.addAttribute("spots", selectedSpots);

        // 確認画面に遷移
        return "dateplun/date_create_check"; // 確認画面
    }

    // description 番号をカテゴリ名に変換するメソッド
    private String convertDescriptionToCategory(String description) {
        switch (description) {
            case "1":
                return "カフェ";
            case "2":
                return "神社系";
            case "3":
                return "公園";
            case "4":
                return "歴史系";
            case "5":
                return "アクティブ系";
            case "6":
                return "インドア系";
            case "7":
                return "飲食店";
            case "8":
                return "動物園";
            default:
                return "未定義";
        }
    }

    // スポットをデートプランから削除
    @PostMapping("/removeSpotFromPlan")
    public String removeSpotFromPlan(@RequestParam("spotName") String spotName, Model model) {
        // セッションから選ばれたスポットリストを取得
        List<DateSpot> selectedSpots = (List<DateSpot>) model.getAttribute("selectedSpots");
        if (selectedSpots == null) {
            selectedSpots = new ArrayList<>();
        }

        // スポット名で削除対象のスポットを検索して削除
        selectedSpots.removeIf(spot -> spot.getSpotName().equals(spotName));

        // セッションに選ばれたスポットを保存
        model.addAttribute("selectedSpots", selectedSpots);

        // デートプラン作成ページにリダイレクト
        return "redirect:/dateCreate";
    }
    
    
    // デートプラン確定後に地図画面に遷移
    @PostMapping("/viewDatePlanOnMap")
    public String viewDatePlanOnMap(Model model) {
        // セッションから選ばれたスポットリストを取得
        List<DateSpot> selectedSpots = (List<DateSpot>) model.getAttribute("selectedSpots");

        // スポットが選ばれていない場合、エラーメッセージを設定してリダイレクト
        if (selectedSpots == null || selectedSpots.isEmpty()) {
            model.addAttribute("message", "スポットが選ばれていません。");
            return "redirect:/dateCreate";  // プラン作成画面にリダイレクト
        }

        // モデルに選ばれたスポットリストをそのまま渡す
        model.addAttribute("spots", selectedSpots);

        // 地図画面に遷移（date_create_completion.html）
        return "dateplun/date_create_completion"; // 地図表示画面
    }



}
