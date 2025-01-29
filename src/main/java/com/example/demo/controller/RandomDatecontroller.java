package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.DateShare;
import com.example.demo.entity.DateSpot;
import com.example.demo.entity.Users;
import com.example.demo.repository.RandomRepository;
import com.example.demo.service.RandomDateService;

@Controller
public class RandomDatecontroller {

    @Autowired
    HttpSession session;

    @Autowired
    private RandomDateService randomDateService;

    @Autowired
    private RandomRepository randomRepository;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/randomDate")
    public ModelAndView showRandomDatePage() {
        ModelAndView modelAndView = new ModelAndView("random_date/answer");

        Users user = (Users) session.getAttribute("loginUser");
        
        if (user != null) {
            modelAndView.addObject("user", user);
        }

        modelAndView.addObject("destinations", randomDateService.getDestinationOptions());
        modelAndView.addObject("weathers", randomDateService.getWeatherOptions());
        modelAndView.addObject("planCounts", randomDateService.getPlanCountOptions());
        
        return modelAndView;
    }

    @GetMapping("/dateMaster/back")
    public String goBack() {
        return "date/date";
    }

    @PostMapping("/dateMaster/date_confirm")
    public ModelAndView goToConfirmation(
        @RequestParam("destination") String destination,
        @RequestParam("weather") String weather,
        @RequestParam("planCount") String planCount) {

        ModelAndView modelAndView = new ModelAndView("random_date/confirm_answer");

        modelAndView.addObject("destination", destination);
        modelAndView.addObject("weather", weather);
        modelAndView.addObject("planCount", planCount);
        
        return modelAndView;
    }

    @PostMapping("/date_idea_list")
    public ModelAndView showDateIdeas(
        @RequestParam("destination") String destination,
        @RequestParam("weather") String weather,
        @RequestParam("planCount") String planCount) {

        int destinationId = convertDestinationToId(destination);
        int weatherId = convertWeatherToId(weather);
        int count = convertPlanCountToInt(planCount);

        List<DateSpot> mainSpots = randomRepository.findByDestinationAndWeather(destinationId, weatherId, 1);
        
        if (mainSpots.isEmpty()) {
            ModelAndView modelAndView = new ModelAndView("error");
            modelAndView.addObject("message", "適切なデートスポットが見つかりませんでした。");
            return modelAndView;
        }

        DateSpot mainSpot = mainSpots.get(0);
        List<DateSpot> allSpots = new ArrayList<>();
        allSpots.add(mainSpot);

        if (count > 1) {
            List<DateSpot> subSpots = randomRepository.findNearbySpots(
                mainSpot.getLatitude(),
                mainSpot.getLongitude(),
                count - 1,
                Collections.singletonList(mainSpot.getSpotId())
            );
            allSpots.addAll(subSpots);
        }

        session.setAttribute("currentSpots", allSpots);
        session.setAttribute("planCount", count);

        ModelAndView modelAndView = new ModelAndView("random_date/date_idea_list");
        modelAndView.addObject("spots", allSpots);
        modelAndView.addObject("destination", destination);
        modelAndView.addObject("weather", weather);
        modelAndView.addObject("planCount", count);
        
        return modelAndView;
    }

    @GetMapping("/change_spot")
    public ModelAndView showChangeSpotPage(
        @RequestParam("spotId") Long currentSpotId,
        @RequestParam("spotType") String spotType,
        @RequestParam("destination") String destination,
        @RequestParam("weather") String weather) {
        
        ModelAndView modelAndView = new ModelAndView("random_date/change_spot");
        
        modelAndView.addObject("currentSpotId", currentSpotId);
        modelAndView.addObject("spotType", spotType);
        modelAndView.addObject("destination", destination);
        modelAndView.addObject("weather", weather);
        modelAndView.addObject("destinations", randomDateService.getDestinationOptions());
        
        return modelAndView;
    }

    @PostMapping("/search_spots")
    public ModelAndView searchSpots(
        @RequestParam(value = "spotName", required = false) String spotName,
        @RequestParam(value = "category", required = false) String category,
        @RequestParam("currentSpotId") Long currentSpotId,
        @RequestParam("spotType") String spotType,
        @RequestParam("destination") String destination,
        @RequestParam("weather") String weather) {
        
        List<DateSpot> searchResults;
        
        if (spotName != null && !spotName.isEmpty()) {
            searchResults = randomRepository.findBySpotNameContaining(spotName);
        } else if (category != null && !category.isEmpty()) {
            int categoryId = convertDestinationToId(category);
            searchResults = randomRepository.findByCategory(categoryId);
        } else {
            searchResults = new ArrayList<>();
        }
        
        ModelAndView modelAndView = new ModelAndView("random_date/change_spot");
        modelAndView.addObject("searchResults", searchResults);
        modelAndView.addObject("destinations", randomDateService.getDestinationOptions());
        modelAndView.addObject("currentSpotId", currentSpotId);
        modelAndView.addObject("spotType", spotType);
        modelAndView.addObject("destination", destination);
        modelAndView.addObject("weather", weather);
        
        return modelAndView;
    }

    @PostMapping("/update_spots")
    public ModelAndView updateSpots(
        @RequestParam("selectedSpotId") Long selectedSpotId,
        @RequestParam("currentSpotId") Long currentSpotId,
        @RequestParam("spotType") String spotType,
        @RequestParam("destination") String destination,
        @RequestParam("weather") String weather) {
        
        @SuppressWarnings("unchecked")
        List<DateSpot> currentSpots = (List<DateSpot>) session.getAttribute("currentSpots");
        Integer storedPlanCount = (Integer) session.getAttribute("planCount");
        int planCount = storedPlanCount != null ? storedPlanCount : 1;
        
        if (currentSpots == null) {
            return new ModelAndView("redirect:/randomDate");
        }

        DateSpot selectedSpot = randomRepository.findById(selectedSpotId);
        
        if (selectedSpot == null) {
            return new ModelAndView("redirect:/randomDate");
        }

        if ("メインスポット".equals(spotType)) {
            List<DateSpot> newSpots = new ArrayList<>();
            newSpots.add(selectedSpot);
            
            if (planCount > 1) {
                List<DateSpot> newSubSpots = randomRepository.findNearbySpots(
                    selectedSpot.getLatitude(),
                    selectedSpot.getLongitude(),
                    planCount - 1,
                    Collections.singletonList(selectedSpot.getSpotId())
                );
                newSpots.addAll(newSubSpots);
            }
            
            session.setAttribute("currentSpots", newSpots);
            currentSpots = newSpots;
        } else {
            int index = "サブスポット1".equals(spotType) ? 1 : 2;
            if (index < currentSpots.size()) {
                currentSpots.set(index, selectedSpot);
                session.setAttribute("currentSpots", currentSpots);
            }
        }

        ModelAndView modelAndView = new ModelAndView("random_date/date_idea_list");
        modelAndView.addObject("spots", currentSpots);
        modelAndView.addObject("destination", destination);
        modelAndView.addObject("weather", weather);
        modelAndView.addObject("planCount", planCount);
        
        return modelAndView;
    }

    @GetMapping("/update_additional_spots")
    public ModelAndView updateAdditionalSpots(
        @RequestParam("destination") String destination,
        @RequestParam("weather") String weather) {
        
        int destinationId = convertDestinationToId(destination);
        int weatherId = convertWeatherToId(weather);
        
        Integer storedPlanCount = (Integer) session.getAttribute("planCount");
        int planCount = storedPlanCount != null ? storedPlanCount : 1;

        @SuppressWarnings("unchecked")
        List<DateSpot> currentSpots = (List<DateSpot>) session.getAttribute("currentSpots");
        
        List<Long> excludeSpotIds = new ArrayList<>();
        if (currentSpots != null && !currentSpots.isEmpty()) {
            excludeSpotIds = currentSpots.stream()
                .map(DateSpot::getSpotId)
                .collect(Collectors.toList());
        }

        List<DateSpot> mainSpots = randomRepository.findNewSpotsByDestinationAndWeather(
            destinationId, weatherId, excludeSpotIds);

        if (mainSpots.isEmpty()) {
            ModelAndView errorView = new ModelAndView("error");
            errorView.addObject("message", "適切なデートスポットが見つかりませんでした。");
            return errorView;
        }

        DateSpot mainSpot = mainSpots.get(0);
        List<DateSpot> allSpots = new ArrayList<>();
        allSpots.add(mainSpot);

        if (planCount > 1) {
            List<DateSpot> subSpots = randomRepository.findNearbySpots(
                mainSpot.getLatitude(),
                mainSpot.getLongitude(),
                planCount - 1,
                Collections.singletonList(mainSpot.getSpotId())
            );
            allSpots.addAll(subSpots);
        }

        session.setAttribute("currentSpots", allSpots);

        ModelAndView modelAndView = new ModelAndView("random_date/date_idea_list");
        modelAndView.addObject("spots", allSpots);
        modelAndView.addObject("destination", destination);
        modelAndView.addObject("weather", weather);
        modelAndView.addObject("planCount", planCount);
        
        return modelAndView;
    }

    @PostMapping("/confirm_plan")
    public ModelAndView confirmPlan(
        @RequestParam("destination") String destination,
        @RequestParam("weather") String weather) {
        
        ModelAndView modelAndView = new ModelAndView("random_date/confirm_date_idea");
        
        @SuppressWarnings("unchecked")
        List<DateSpot> currentSpots = (List<DateSpot>) session.getAttribute("currentSpots");
        
        if (currentSpots == null || currentSpots.isEmpty()) {
            return new ModelAndView("redirect:/randomDate");
        }
        
        modelAndView.addObject("spots", currentSpots);
        modelAndView.addObject("destination", destination);
        modelAndView.addObject("weather", weather);
        
        return modelAndView;
    }

    @PostMapping("/plan_decision")
    public ModelAndView showDatePlan(
        @RequestParam("destination") String destination,
        @RequestParam("weather") String weather) {
        
        ModelAndView modelAndView = new ModelAndView("random_date/random_date");
        
        @SuppressWarnings("unchecked")
        List<DateSpot> currentSpots = (List<DateSpot>) session.getAttribute("currentSpots");
        
        if (currentSpots != null && !currentSpots.isEmpty()) {
            List<String> spotNames = new ArrayList<>();
            List<Double> spotLatitudes = new ArrayList<>();
            List<Double> spotLongitudes = new ArrayList<>();
            List<String> spotDescriptions = new ArrayList<>();
            List<String> spotAddresses = new ArrayList<>();
            
            for (DateSpot spot : currentSpots) {
                spotNames.add(spot.getSpotName());
                spotLatitudes.add(spot.getLatitude());
                spotLongitudes.add(spot.getLongitude());
                spotDescriptions.add(spot.getDescription());
                spotAddresses.add(spot.getSpotAddress());
            }
            
            modelAndView.addObject("spotNames", spotNames);
            modelAndView.addObject("spotLatitudes", spotLatitudes);
            modelAndView.addObject("spotLongitudes", spotLongitudes);
            modelAndView.addObject("spotDescriptions", spotDescriptions);
            modelAndView.addObject("spotAddresses", spotAddresses);
            modelAndView.addObject("destination", destination);
            modelAndView.addObject("weather", weather);
        }
        
        return modelAndView;
    }
    
    
    
    @PostMapping("/share_date_plan")
    @ResponseBody
    public ResponseEntity<Map<String, String>> shareDatePlan(HttpSession session) {
        Map<String, String> response = new HashMap<>();
        
        // ログインユーザーの取得
        Users user = (Users) session.getAttribute("loginUser");
        if (user == null) {
            response.put("status", "error");
            response.put("message", "ログインが必要です");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        
        // パートナーの確認
        Long partnerId = randomRepository.findPartnerIdByUserId(user.getId());
        if (partnerId == null) {
            response.put("status", "error");
            response.put("message", "パートナーが登録されていません");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        
        @SuppressWarnings("unchecked")
        List<DateSpot> currentSpots = (List<DateSpot>) session.getAttribute("currentSpots");
        if (currentSpots == null || currentSpots.isEmpty()) {
            response.put("status", "error");
            response.put("message", "共有するプランが見つかりません");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        
        // プランの保存
        try {
            DateShare dateShare = new DateShare();
            dateShare.setSpot1(currentSpots.get(0).getSpotId());
            if (currentSpots.size() > 1) dateShare.setSpot2(currentSpots.get(1).getSpotId());
            if (currentSpots.size() > 2) dateShare.setSpot3(currentSpots.get(2).getSpotId());
            dateShare.setCount(currentSpots.size());
            
            randomRepository.saveDateShare(dateShare, user.getId(), partnerId);
            
            response.put("status", "success");
            response.put("message", "プランを共有しました");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "プランの共有に失敗しました");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    private int convertDestinationToId(String destination) {
        switch(destination) {
            case "カフェ": return 1;
            case "神社系": return 2;
            case "公園": return 3;
            case "歴史系": return 4;
            case "アクティブ系": return 5;
            case "インドア系": return 6;
            case "飲食店": return 7;
            case "動物園": return 8;
            default: return 0;
        }
    }

    private int convertWeatherToId(String weather) {
        switch(weather) {
            case "晴れ": return 1;
            case "曇り": return 2;
            case "雨": return 3;
            default: return 0;
        }
    }

    private int convertPlanCountToInt(String planCount) {
        switch(planCount) {
            case "一個": return 1;
            case "二個": return 2;
            case "三個": return 3;
            default: return 1;
        }
    }
}
