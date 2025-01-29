package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import jakarta.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.Users;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserLoginService;

@SpringBootTest
class LoginServiceTest {
    
    @Mock
    private UserRepository userRepo;
    
    @Mock
    private HttpSession session;
    
    @InjectMocks
    private UserLoginService userLoginService;
    
    private Users validUser;
    
    @BeforeEach
    void setUp() {
        // テストデータの準備
        validUser = new Users();
        validUser.setId(1L);
        validUser.setPassword("correctPassword");
        
        // モックの基本設定
        when(userRepo.findById(1L)).thenReturn(Optional.of(validUser));
        when(userRepo.findById(999L)).thenReturn(Optional.empty());
    }
    
    @Test
    void testLoginSuccess() {
        // 正常なログインケース
        String result = userLoginService.login("1", "correctPassword");
        
        // 検証
        assertEquals("OK", result, "正常なログインは'OK'を返すべき");
        verify(session).setAttribute("loginUser", validUser);
    }
    
    @Test
    void testLoginWithInvalidPassword() {
        // パスワードが間違っているケース
        String result = userLoginService.login("1", "wrongPassword");
        
        // 検証
        assertEquals("パスワードが一致しません。", result, "不正なパスワードの場合エラーメッセージを返すべき");
        verify(session, never()).setAttribute(anyString(), any());
    }
    
    @Test
    void testLoginWithNonexistentUser() {
        // 存在しないユーザーIDでのログイン試行
        String result = userLoginService.login("999", "anyPassword");
        
        // 検証
        assertEquals("ユーザーIDが存在しません。", result, "存在しないユーザーIDの場合エラーメッセージを返すべき");
        verify(session, never()).setAttribute(anyString(), any());
    }
    
    @Test
    void testLoginWithInvalidUserId() {
        // 数値に変換できないユーザーIDでのログイン試行
        String result = userLoginService.login("abc", "password");
        
        // NumberFormatExceptionが発生することを想定
        assertEquals("ユーザーIDが存在しません。", result, "不正なユーザーIDの場合エラーメッセージを返すべき");
        verify(session, never()).setAttribute(anyString(), any());
    }
    
    @Test
    void testLoginWithCompleteUserData() {
        // 全データが設定されているユーザーでのテスト
        Users completeUser = new Users();
        completeUser.setId(1L);
        completeUser.setName("TestUser");
        completeUser.setPassword("correctPassword");
        completeUser.setPartner(2L);
        completeUser.setDiagnosis(1);
        completeUser.setQuestion("1");
        completeUser.setAnswer("answer");
        
        when(userRepo.findById(1L)).thenReturn(Optional.of(completeUser));
        
        String result = userLoginService.login("1", "correctPassword");
        
        // 検証
        assertEquals("OK", result, "完全なユーザーデータでも正常にログインできるべき");
        verify(session).setAttribute("loginUser", completeUser);
    }
    
    
    @Test
    void testLoginWithEmptyPassword() {
        // 空のパスワードでのテスト
        String result = userLoginService.login("1", "");
        
        // 検証
        assertEquals("パスワードが一致しません。", result, "空パスワードの場合エラーメッセージを返すべき");
        verify(session, never()).setAttribute(anyString(), any());
    }
}