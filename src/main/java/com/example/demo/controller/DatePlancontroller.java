package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

        List<DateSpot> selectedSpots = (List<DateSpot>) model.getAttribute("selectedSpots");
        if (selectedSpots == null) {
            selectedSpots = new ArrayList<>();
        }

        for (DateSpot spot : spots) {
            spot.setCategoryName(spot.getDescription());
        }

        model.addAttribute("spots", spots);
        model.addAttribute("selectedSpots", selectedSpots);
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
        selectedSpot.setCategoryName(spotDescription);

        List<DateSpot> selectedSpots = (List<DateSpot>) model.getAttribute("selectedSpots");
        if (selectedSpots == null) {
            selectedSpots = new ArrayList<>();
        }

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
    @PostMapping("/createDatePlan")
    public String createDatePlan(Model model) {
        List<DateSpot> selectedSpots = (List<DateSpot>) model.getAttribute("selectedSpots");
        if (selectedSpots == null || selectedSpots.isEmpty()) {
            model.addAttribute("message", "スポットが選ばれていません。");
            return "redirect:/dateCreate";
        }
        model.addAttribute("spots", selectedSpots);
        return "dateplun/date_create_check"; // 確認画面
    }

    // スポットをデートプランから削除
    @PostMapping("/removeSpotFromPlan")
    public String removeSpotFromPlan(@RequestParam("spotName") String spotName, Model model) {
        List<DateSpot> selectedSpots = (List<DateSpot>) model.getAttribute("selectedSpots");
        if (selectedSpots == null) {
            selectedSpots = new ArrayList<>();
        }

        selectedSpots.removeIf(spot -> spot.getSpotName().equals(spotName));
        model.addAttribute("selectedSpots", selectedSpots);
        return "redirect:/dateCreate";
    }

    // デートプラン確定後に地図画面に遷移
    @PostMapping("/viewDatePlanOnMap")
    public String viewDatePlanOnMap(Model model) {
        List<DateSpot> selectedSpots = (List<DateSpot>) model.getAttribute("selectedSpots");
        if (selectedSpots == null || selectedSpots.isEmpty()) {
            model.addAttribute("message", "スポットが選ばれていません。");
            return "redirect:/dateCreate";
        }

        // スポット情報に緯度と経度を追加
        List<String> spotNames = selectedSpots.stream()
                                              .map(DateSpot::getSpotName)
                                              .collect(Collectors.toList());
        List<DateSpot> spotsFromDb = dateSpotRepository.findBySpotNames(spotNames);

        for (DateSpot spot : selectedSpots) {
            DateSpot dbSpot = spotsFromDb.stream()
                                        .filter(s -> s.getSpotName().equals(spot.getSpotName()))
                                        .findFirst()
                                        .orElse(null);
            if (dbSpot != null) {
                spot.setLatitude(dbSpot.getLatitude());
                spot.setLongitude(dbSpot.getLongitude());
            }
        }

        model.addAttribute("spots", selectedSpots);
        return "dateplun/date_create_completion"; // 地図表示画面
    }
}
