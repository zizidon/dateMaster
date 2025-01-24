package com.example.demo.entity;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateShare {
    @Id
    private Long plansId; // カラム名をスネークケースからキャメルケースに変更
    private Long spot1;
    private Long spot2;
    private Long spot3;
    private Integer count;
}
