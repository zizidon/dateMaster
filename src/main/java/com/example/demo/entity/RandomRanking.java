package com.example.demo.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RandomRanking {

	@Id
	private Long id;

	private String spotPattern;

	private Integer spot1;

	private Integer spot2;

	private Integer spot3;

	private int count;

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	// ゲッターとセッター
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSpotPattern() {
		return spotPattern;
	}

	public void setSpotPattern(String spotPattern) {
		this.spotPattern = spotPattern;
	}

	public Integer getSpot1() {
		return spot1;
	}

	public void setSpot1(Integer spot1) {
		this.spot1 = spot1;
	}

	public Integer getSpot2() {
		return spot2;
	}

	public void setSpot2(Integer spot2) {
		this.spot2 = spot2;
	}

	public Integer getSpot3() {
		return spot3;
	}

	public void setSpot3(Integer spot3) {
		this.spot3 = spot3;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

}
