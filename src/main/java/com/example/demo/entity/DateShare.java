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
    private Long plans_id;  // 自動採番
    private Long spot1;  // スポット1のID
    private Long spot2;  // スポット2のID
    private Long spot3;  // スポット3のID
    private Integer count;  // プランの使用回数
}