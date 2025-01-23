package com.example.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.DateShare;
import com.example.demo.entity.Users;
import com.example.demo.repository.DateShareRepository;
import com.example.demo.repository.UserRepository;

@Service
public class DatePlanShareService {
    @Autowired
    private DateShareRepository dateShareRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void shareDatePlanWithPartner(Users currentUser) {
        // Find partner with the opposite partner ID
        Users partner = userRepository.findById(currentUser.getPartner())
            .orElseThrow(() -> new RuntimeException("Partner not found"));
        
        // Retrieve DateShare for current user
        DateShare datePlan = dateShareRepository.findById(currentUser.getDate_share())
            .orElseThrow(() -> new RuntimeException("Date plan not found"));
        
        // Update partner's shared_date_plan
        partner.setShared_date_plan(datePlan.getPlansId());
        userRepository.save(partner);
    }
}