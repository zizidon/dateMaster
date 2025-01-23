package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.DateShare;
import com.example.demo.entity.DateSpot;
import com.example.demo.entity.Users;
import com.example.demo.repository.DateShareRepository;
import com.example.demo.repository.DateSpotRepository;
import com.example.demo.service.DatePlanShareService;

@Controller
public class DatePlanSharecontroller {

    @Autowired
    private DateShareRepository dateShareRepository;

    @Autowired
    private DateSpotRepository dateSpotRepository;

    @Autowired
    private DatePlanShareService datePlanShareService;

    @GetMapping("/myDatePlans")
    public String myDatePlans(Model model, HttpSession session) {
        Users currentUser = (Users) session.getAttribute("loginUser");
        
        if (currentUser == null) {
            return "redirect:/login";
        }
        
        List<DateSpot> ownPlanSpots = new ArrayList<>();
        List<DateSpot> sharedPlanSpots = new ArrayList<>();
        
        // Fetch own date plan
        if (currentUser.getDate_share() != null) {
            Optional<DateShare> datePlanOptional = dateShareRepository.findById(currentUser.getDate_share());
            
            if (datePlanOptional.isPresent()) {
                DateShare plan = datePlanOptional.get();
                addSpotIfExists(plan.getSpot1(), ownPlanSpots);
                addSpotIfExists(plan.getSpot2(), ownPlanSpots);
                addSpotIfExists(plan.getSpot3(), ownPlanSpots);
                
                model.addAttribute("ownPlanCount", plan.getCount());
            }
        }
        
        // Fetch shared date plan
        if (currentUser.getShared_date_plan() != null) {
            Optional<DateShare> sharedPlanOptional = dateShareRepository.findById(currentUser.getShared_date_plan());
            
            if (sharedPlanOptional.isPresent()) {
                DateShare sharedPlan = sharedPlanOptional.get();
                addSpotIfExists(sharedPlan.getSpot1(), sharedPlanSpots);
                addSpotIfExists(sharedPlan.getSpot2(), sharedPlanSpots);
                addSpotIfExists(sharedPlan.getSpot3(), sharedPlanSpots);
                
                model.addAttribute("sharedPlanCount", sharedPlan.getCount());
            }
        }
        
        model.addAttribute("ownPlanSpots", ownPlanSpots);
        model.addAttribute("sharedPlanSpots", sharedPlanSpots);
        
        return "date_reference/date_reference_list";
    }

    @PostMapping("/shareDatePlan")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> shareDatePlan(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        Users currentUser = (Users) session.getAttribute("loginUser");

        // Validate current user exists
        if (currentUser == null) {
            response.put("success", false);
            response.put("message", "パートナーが見つかりません");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            datePlanShareService.shareDatePlanWithPartner(currentUser);
            response.put("success", true);
            response.put("message", "デートプランを共有しました");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "パートナーがいません");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    private void addSpotIfExists(Long spotId, List<DateSpot> planSpots) {
        if (spotId != null) {
            DateSpot spot = dateSpotRepository.findById(spotId);
            if (spot != null) {
                planSpots.add(spot);
            }
        }
    }
}