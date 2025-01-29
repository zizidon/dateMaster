package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import com.example.demo.controller.DatePlancontroller;
import com.example.demo.entity.DateShare;
import com.example.demo.entity.DateSpot;
import com.example.demo.repository.DateShareRepository;
import com.example.demo.repository.DateSpotRepository;
import com.example.demo.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class DatePlanServiceTest {
    
    @Mock
    private DateSpotRepository dateSpotRepository;
    
    @Mock
    private DateShareRepository dateShareRepository;
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private Model model;

    @Mock
    private HttpSession session;  // HttpSession をモック
    
    @InjectMocks
    private DatePlancontroller datePlanController;
    
    private List<DateSpot> testSpots;
    
    @BeforeEach
    void setUp() {
        testSpots = new ArrayList<>();
        // テスト用のスポットデータを作成
        DateSpot spot1 = createTestSpot(1L, "渋谷カフェ", "1", "東京都渋谷区", 35.6812, 139.7671);
        DateSpot spot2 = createTestSpot(2L, "新宿公園", "3", "東京都新宿区", 35.6897, 139.7054);
        DateSpot spot3 = createTestSpot(3L, "上野美術館", "4", "東京都台東区", 35.7147, 139.7967);
        
        testSpots.add(spot1);
        testSpots.add(spot2);
        testSpots.add(spot3);
    }

    @Test
    void testSearchSpots() {
        // スポット検索のテスト
        when(dateSpotRepository.findBySpotNameContaining("カフェ"))
            .thenReturn(List.of(testSpots.get(0)));
        
        String viewName = datePlanController.searchSpots("カフェ", model);
        
        assertEquals("dateplun/date_add", viewName);
        verify(model).addAttribute(eq("spots"), anyList());
    }

    @Test
    void testAddSpotToPlan() {
        // スポット追加のテスト
        DateSpot spot = testSpots.get(0);
        
        // セッションにデータを設定
        
        String result = datePlanController.addSpotToPlan(
            spot.getSpotName(),
            spot.getDescription(),
            spot.getSpotAddress(),
            "9:00-18:00", // Monday
            "9:00-18:00", // Tuesday
            "9:00-18:00", // Wednesday
            "9:00-18:00", // Thursday
            "9:00-18:00", // Friday
            "9:00-18:00", // Saturday
            "9:00-18:00", // Sunday
            spot.getLatitude(),
            spot.getLongitude(),
            model
        );
        
        assertEquals("redirect:/dateCreate", result);
    }

    @Test
    void testCreateDatePlan() {
        // プラン作成のテスト（スポットが選択されている場合）
        List<DateSpot> selectedSpots = new ArrayList<>(testSpots.subList(0, 2));
        
        // モックしたセッションからスポットを取得
        when(session.getAttribute("selectedSpots")).thenReturn(selectedSpots);
        
        String result = datePlanController.createDatePlan(model);
        
        assertEquals("dateplun/date_create_check", result);
    }

    @Test
    void testCreateDatePlanWithNoSpots() {
        // スポットが選択されていない場合のテスト
        
        String result = datePlanController.createDatePlan(model);
        
        assertEquals("redirect:/dateCreate", result);
    }
    

    @Test
    void testRemoveSpotFromPlan() {
        // スポット削除のテスト
        List<DateSpot> selectedSpots = new ArrayList<>(testSpots);
        
        
        String result = datePlanController.removeSpotFromPlan(testSpots.get(0).getSpotName(), model);
        
        assertEquals("redirect:/dateCreate", result);
    }

    @Test
    void testViewDatePlanOnMap() {
        // プラン保存と地図表示のテスト
        List<DateSpot> selectedSpots = new ArrayList<>(testSpots.subList(0, 2));
        
        when(session.getAttribute("selectedSpots")).thenReturn(selectedSpots);
        
        DateShare newPlan = new DateShare();
        when(dateShareRepository.save(any(DateShare.class))).thenReturn(newPlan);
        
        String result = datePlanController.viewDatePlanOnMap(model);
        
        assertEquals("dateplun/date_create_completion", result);
        verify(dateShareRepository).save(any(DateShare.class));
    }

    // テスト用のスポットを作成するヘルパーメソッド
    private DateSpot createTestSpot(Long id, String name, String description, String address, double lat, double lng) {
        DateSpot spot = new DateSpot();
        spot.setSpotId(id);
        spot.setSpotName(name);
        spot.setDescription(description);
        spot.setSpotAddress(address);
        spot.setLatitude(lat);
        spot.setLongitude(lng);
        return spot;
    }
}
