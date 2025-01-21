package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.DateShare;
import com.example.demo.entity.DateSpot;
import com.example.demo.repository.DateShareRepository;
import com.example.demo.repository.DateSpotRepository;

@Controller
public class DatePlanRankingcontroller {

	@Autowired
	private DateShareRepository dateShareRepository;

	@Autowired
	private DateSpotRepository dateSpotRepository;

	@GetMapping("/datePlanRanking")
	public ModelAndView showDatePlanRanking() {
		ModelAndView mav = new ModelAndView("/dateplun_ranking/dateplun_ranking");

		// デバッグ用：topPlansの取得を確認
		List<DateShare> topPlans = dateShareRepository.findTop5ByOrderByCountDesc();

		List<RankingData> rankingList = new ArrayList<>();

		for (DateShare plan : topPlans) {
			RankingData rankingData = new RankingData();

			// spot1は必須とする
			DateSpot spot1 = dateSpotRepository.findById(plan.getSpot1());
			if (spot1 == null) {

				continue; // spot1が見つからない場合はスキップ
			}

			// spot2とspot3はNULLの可能性がある
			DateSpot spot2 = null;
			DateSpot spot3 = null;

			if (plan.getSpot2() != null) {
				spot2 = dateSpotRepository.findById(plan.getSpot2());

			}

			if (plan.getSpot3() != null) {
				spot3 = dateSpotRepository.findById(plan.getSpot3());

			}

			// データを設定
			rankingData.setSpot1Name(spot1.getSpotName());
			rankingData.setSpot2Name(spot2 != null ? spot2.getSpotName() : "未設定");
			rankingData.setSpot3Name(spot3 != null ? spot3.getSpotName() : "未設定");
			rankingData.setCount(plan.getCount());

			rankingList.add(rankingData);
		}

		mav.addObject("rankings", rankingList);
		return mav;
	}

	// ランキング表示用の内部クラス
	private static class RankingData {
		private String spot1Name;
		private String spot2Name;
		private String spot3Name;
		private Integer count;

		// getter/setter
		public String getSpot1Name() {
			return spot1Name;
		}

		public void setSpot1Name(String spot1Name) {
			this.spot1Name = spot1Name;
		}

		public String getSpot2Name() {
			return spot2Name;
		}

		public void setSpot2Name(String spot2Name) {
			this.spot2Name = spot2Name;
		}

		public String getSpot3Name() {
			return spot3Name;
		}

		public void setSpot3Name(String spot3Name) {
			this.spot3Name = spot3Name;
		}

		public Integer getCount() {
			return count;
		}

		public void setCount(Integer count) {
			this.count = count;
		}
	}
}