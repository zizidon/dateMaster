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
    private Long plansId;
    private Long spot1;
    private Long spot2;
    private Long spot3;
    private int count;

    // ゲッターとセッター
    public Long getPlansId() {
        return plansId;
    }

    public void setPlansId(Long plansId) {
        this.plansId = plansId;
    }

    public Long getSpot1() {
        return spot1;
    }

    public void setSpot1(Long spot1) {
        this.spot1 = spot1;
    }

    public Long getSpot2() {
        return spot2;
    }

    public void setSpot2(Long spot2) {
        this.spot2 = spot2;
    }

    public Long getSpot3() {
        return spot3;
    }

    public void setSpot3(Long spot3) {
        this.spot3 = spot3;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}