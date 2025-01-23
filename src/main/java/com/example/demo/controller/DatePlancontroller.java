
package com.example.demo.controller; 

 

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.entity.DateShare;
import com.example.demo.entity.DateSpot;
import com.example.demo.entity.Users;
import com.example.demo.repository.DateShareRepository;
import com.example.demo.repository.DateSpotRepository;
import com.example.demo.repository.UserRepository; 


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
                               @RequestParam("spotLatitude") double spotLatitude,
                               @RequestParam("spotLongitude") double spotLongitude,
                               Model model) {
        // データベースから該当するスポットを検索
        List<DateSpot> existingSpots = dateSpotRepository.findBySpotNameContaining(spotName);
        DateSpot selectedSpot;
        
        if (!existingSpots.isEmpty()) {
            // データベースに存在する場合は、そのスポットを使用
            selectedSpot = existingSpots.get(0);
        } else {
            // 新しいスポットオブジェクトを作成して、フォームから送られたデータをセット
            selectedSpot = new DateSpot();
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
            selectedSpot.setLatitude(spotLatitude);
            selectedSpot.setLongitude(spotLongitude);
            selectedSpot.setCategoryName(convertDescriptionToCategory(spotDescription));
        }

        // セッションから選ばれたスポットリストを取得
        List<DateSpot> selectedSpots = (List<DateSpot>) model.getAttribute("selectedSpots");
        if (selectedSpots == null) {
            selectedSpots = new ArrayList<>();
        }

        // 重複チェック
        boolean isDuplicate = selectedSpots.stream()
                .anyMatch(spot -> spot.getSpotName().equals(selectedSpot.getSpotName()));
        if (!isDuplicate) {
            selectedSpots.add(selectedSpot);
        } else {
            model.addAttribute("message", "このスポットはすでに追加されています。");
        }

        model.addAttribute("selectedSpots", selectedSpots);
        return "redirect:/dateCreate";
    }
    // デートプラン確認
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

        // 緯度経度情報をまとめて渡す
        List<Double> latitudes = new ArrayList<>();
        List<Double> longitudes = new ArrayList<>();
        for (DateSpot spot : selectedSpots) {
            latitudes.add(spot.getLatitude());
            longitudes.add(spot.getLongitude());
        }
        model.addAttribute("spotLatitudes", latitudes);
        model.addAttribute("spotLongitudes", longitudes);

        // 確認画面に遷移
        return "dateplun/date_create_check"; // 確認画面
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
    @Autowired
    private UserRepository userRepository; // Add this autowired field

    @Autowired
    private HttpSession session; // Add HttpSession autowiring

    @Autowired
    private DateShareRepository dateShareRepository;  // リポジトリ名変更

    @PostMapping("/viewDatePlanOnMap")
    public String viewDatePlanOnMap(Model model) {
        List<DateSpot> selectedSpots = (List<DateSpot>) model.getAttribute("selectedSpots");

        if (selectedSpots == null || selectedSpots.isEmpty()) {
            model.addAttribute("message", "スポットが選ばれていません。");
            return "redirect:/dateCreate";
        }

        try {
            // 選択されたスポットのIDを取得（最大3つまで）
            Long spot1Id = selectedSpots.size() >= 1 ? selectedSpots.get(0).getSpotId() : null;
            Long spot2Id = selectedSpots.size() >= 2 ? selectedSpots.get(1).getSpotId() : null;
            Long spot3Id = selectedSpots.size() >= 3 ? selectedSpots.get(2).getSpotId() : null;

            // 既存のプランを検索
            DateShare existingPlan = dateShareRepository.findBySpot1AndSpot2AndSpot3(spot1Id, spot2Id, spot3Id);

            if (existingPlan != null) {
                // 既存のプランが見つかった場合はcountをインクリメント
                existingPlan.setCount(existingPlan.getCount() + 1);
                DateShare savedPlan = dateShareRepository.save(existingPlan);

                // セッションからログインユーザーを取得し、date_shareを更新
                Users currentUser = (Users) session.getAttribute("loginUser");
                if (currentUser != null) {
                    currentUser.setDate_share(savedPlan.getPlansId());
                    userRepository.save(currentUser);
                }
            } else {
                // 新しいプランを作成
                DateShare newPlan = new DateShare();
                newPlan.setSpot1(spot1Id);
                newPlan.setSpot2(spot2Id);
                newPlan.setSpot3(spot3Id);
                newPlan.setCount(1);
                DateShare savedPlan = dateShareRepository.save(newPlan);

                // セッションからログインユーザーを取得し、date_shareを更新
                Users currentUser = (Users) session.getAttribute("loginUser");
                if (currentUser != null) {
                    currentUser.setDate_share(savedPlan.getPlansId());
                    userRepository.save(currentUser);
                }
            }
        } catch (Exception e) {
            System.err.println("プランの保存中にエラーが発生しました: " + e.getMessage());
        }

        // マップ表示用のデータ設定（この部分は変更なし）
        List<String> spotNames = new ArrayList<>();
        List<Double> spotLatitudes = new ArrayList<>();
        List<Double> spotLongitudes = new ArrayList<>();
        List<String> spotDescriptions = new ArrayList<>();
        List<String> spotAddresses = new ArrayList<>();

        for (DateSpot spot : selectedSpots) {
            spotNames.add(spot.getSpotName());
            spotLatitudes.add(spot.getLatitude());
            spotLongitudes.add(spot.getLongitude());
            spotDescriptions.add(spot.getDescription());
            spotAddresses.add(spot.getSpotAddress());
        }
        // モデルに各情報を追加
        model.addAttribute("spotNames", spotNames);
        model.addAttribute("spotLatitudes", spotLatitudes);
        model.addAttribute("spotLongitudes", spotLongitudes);
        model.addAttribute("spotDescriptions", spotDescriptions);
        model.addAttribute("spotAddresses", spotAddresses);
        // 地図画面に遷移（date_create_completion.html）
        return "dateplun/date_create_completion";
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

} 