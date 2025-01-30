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

@ExtendWith(MockitoExtension.class)  // Mockitoを使った単体テストの拡張
class DatePlanServiceTest {
    
    // モックオブジェクトの定義
    @Mock
    private DateSpotRepository dateSpotRepository;
    
    @Mock
    private DateShareRepository dateShareRepository;
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private Model model;

    @Mock
    private HttpSession session;
    
    // テスト対象のコントローラーをインジェクト
    @InjectMocks
    private DatePlancontroller datePlanController;
    
    private List<DateSpot> testSpots;  // テスト用のスポットリスト

    @BeforeEach
    void setUp() {
        // テスト用のDateSpotインスタンスを作成してリストに追加
        testSpots = new ArrayList<>();
        DateSpot spot1 = createTestSpot(1L, "渋谷カフェ", "1", "東京都渋谷区", 35.6812, 139.7671);
        DateSpot spot2 = createTestSpot(2L, "新宿公園", "3", "東京都新宿区", 35.6897, 139.7054);
        DateSpot spot3 = createTestSpot(3L, "上野美術館", "4", "東京都台東区", 35.7147, 139.7967);
        DateSpot spot4 = createTestSpot(4L, "浅草寺", "2", "東京都台東区", 35.7147, 139.7967);
        
        testSpots.add(spot1);
        testSpots.add(spot2);
        testSpots.add(spot3);
        testSpots.add(spot4);
    }

    @Test
    void testAddSpotToPlanWhenMaxLimitReached() {
        // 3つのスポットが選択された状態を作成
        List<DateSpot> selectedSpots = new ArrayList<>(testSpots.subList(0, 3));
        when(model.getAttribute("selectedSpots")).thenReturn(selectedSpots);

        // 4つ目のスポットを追加しようとする
        DateSpot fourthSpot = testSpots.get(3);
        String result = datePlanController.addSpotToPlan(
            fourthSpot.getSpotName(),
            fourthSpot.getDescription(),
            fourthSpot.getSpotAddress(),
            "9:00-18:00", "9:00-18:00", "9:00-18:00", "9:00-18:00", 
            "9:00-18:00", "9:00-18:00", "9:00-18:00",
            fourthSpot.getLatitude(), fourthSpot.getLongitude(), model
        );

        // 結果の検証: スポット数は3のままで、適切にリダイレクトされること
        assertEquals("redirect:/dateCreate", result);
        assertEquals(3, selectedSpots.size());
        verify(model, atLeastOnce()).getAttribute("selectedSpots"); // モックされたモデルのgetAttributeが呼ばれたことを検証
    }

    @Test
    void testAddSpotToPlanWhenUnderLimit() {
        // 2つのスポットが選択された状態を作成
        List<DateSpot> selectedSpots = new ArrayList<>(testSpots.subList(0, 2));
        when(model.getAttribute("selectedSpots")).thenReturn(selectedSpots);

        // 3つ目のスポットを追加
        DateSpot thirdSpot = testSpots.get(2);
        String result = datePlanController.addSpotToPlan(
            thirdSpot.getSpotName(),
            thirdSpot.getDescription(),
            thirdSpot.getSpotAddress(),
            "9:00-18:00", "9:00-18:00", "9:00-18:00", "9:00-18:00", 
            "9:00-18:00", "9:00-18:00", "9:00-18:00",
            thirdSpot.getLatitude(), thirdSpot.getLongitude(), model
        );

        // 結果の検証: 3つ目のスポットが追加され、リダイレクトされること
        assertEquals("redirect:/dateCreate", result);
        verify(model, atLeastOnce()).getAttribute("selectedSpots"); // モックされたモデルのgetAttributeが呼ばれたことを検証
    }

    @Test
    void testSearchSpots() {
        // "カフェ"というキーワードでスポットを検索
        when(dateSpotRepository.findBySpotNameContaining("カフェ"))
            .thenReturn(List.of(testSpots.get(0)));  // モックされたスポットリポジトリから結果を返す
        
        // 検索結果のビュー名を取得
        String viewName = datePlanController.searchSpots("カフェ", model);
        
        // 検索結果の検証: 正しいビュー名が返され、スポットリストがモデルに追加されること
        assertEquals("dateplun/date_add", viewName);
        verify(model).addAttribute(eq("spots"), anyList());  // モックされたモデルにスポットが追加されたことを検証
    }

    @Test
    void testAddSpotToPlan() {
        // 最初のスポットを選択
        DateSpot spot = testSpots.get(0);
        List<DateSpot> selectedSpots = new ArrayList<>();
        when(model.getAttribute("selectedSpots")).thenReturn(selectedSpots);
        
        // そのスポットを計画に追加
        String result = datePlanController.addSpotToPlan(
            spot.getSpotName(),
            spot.getDescription(),
            spot.getSpotAddress(),
            "9:00-18:00", "9:00-18:00", "9:00-18:00", "9:00-18:00", 
            "9:00-18:00", "9:00-18:00", "9:00-18:00",
            spot.getLatitude(), spot.getLongitude(), model
        );
        
        // 結果の検証: 計画が追加され、リダイレクトされること
        assertEquals("redirect:/dateCreate", result);
    }

    @Test
    void testCreateDatePlan() {
        // 2つのスポットが選択された状態を作成
        List<DateSpot> selectedSpots = new ArrayList<>(testSpots.subList(0, 2));
        when(model.getAttribute("selectedSpots")).thenReturn(selectedSpots);
        
        // デートプラン作成のためのビューを取得
        String result = datePlanController.createDatePlan(model);
        
        // 結果の検証: 正しいビュー名が返されること
        assertEquals("dateplun/date_create_check", result);
    }

    @Test
    void testCreateDatePlanWithNoSpots() {
        // スポットが選択されていない場合
        when(model.getAttribute("selectedSpots")).thenReturn(null);
        
        // リダイレクトが行われることを確認
        String result = datePlanController.createDatePlan(model);
        
        // 結果の検証: 適切にリダイレクトされること
        assertEquals("redirect:/dateCreate", result);
    }

    @Test
    void testRemoveSpotFromPlan() {
        // すべてのスポットが選択された状態を作成
        List<DateSpot> selectedSpots = new ArrayList<>(testSpots);
        when(model.getAttribute("selectedSpots")).thenReturn(selectedSpots);
        
        // 最初のスポットをプランから削除
        String result = datePlanController.removeSpotFromPlan(testSpots.get(0).getSpotName(), model);
        
        // 結果の検証: リダイレクトされること
        assertEquals("redirect:/dateCreate", result);
    }

    @Test
    void testViewDatePlanOnMap() {
        // 2つのスポットが選択された状態を作成
        List<DateSpot> selectedSpots = new ArrayList<>(testSpots.subList(0, 2));
        when(model.getAttribute("selectedSpots")).thenReturn(selectedSpots);
        
        // 新しいデートプランを保存
        DateShare newPlan = new DateShare();
        when(dateShareRepository.save(any(DateShare.class))).thenReturn(newPlan);
        
        // デートプランをマップ上で表示
        String result = datePlanController.viewDatePlanOnMap(model);
        
        // 結果の検証: 正しいビュー名が返され、プランが保存されること
        assertEquals("dateplun/date_create_completion", result);
        verify(dateShareRepository).save(any(DateShare.class)); // リポジトリのsaveが呼ばれたことを確認
    }

    // テスト用のDateSpotインスタンスを作成するメソッド
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
