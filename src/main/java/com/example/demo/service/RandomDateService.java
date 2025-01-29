package com.example.demo.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class RandomDateService {
    public List<String> getDestinationOptions() {
        return Arrays.asList("カフェ", "神社系", "公園", "歴史系", "アクティブ系", "インドア系", "飲食店", "動物園");
    }

    public List<String> getWeatherOptions() {
        return Arrays.asList("晴れ", "曇り", "雨");
    }

    public List<String> getPlanCountOptions() {
        return Arrays.asList("一個", "二個", "三個");
    }
}


