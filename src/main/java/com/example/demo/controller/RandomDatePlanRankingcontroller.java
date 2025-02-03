package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.DateSpot;
import com.example.demo.entity.RandomRanking;
import com.example.demo.repository.DateSpotRepository;
import com.example.demo.repository.RandomDateShareRepository;

@Controller
public class RandomDatePlanRankingcontroller {

	@Autowired
	private RandomDateShareRepository randomDateShareRepository;

	@Autowired
	private DateSpotRepository dateSpotRepository;

	@GetMapping("/randomDatePlanRanking")
	public ModelAndView showRandomDatePlanRanking() {
		ModelAndView mav = new ModelAndView("/randomdateplun_ranking/randomdateplun_ranking");

		// 全てのランダムプランを取得し、count順でソート
		Iterable<RandomRanking> allPlans = randomDateShareRepository.findAll();
		List<RandomRanking> topPlans = new ArrayList<>();
		allPlans.forEach(topPlans::add);
		topPlans.sort((a, b) -> Integer.compare(b.getCount(), a.getCount()));

		// 上位5件のみを取得
		List<RandomRanking> top5Plans = topPlans.subList(0, Math.min(topPlans.size(), 5));

		List<RankingData> rankingList = new ArrayList<>();

		for (RandomRanking plan : top5Plans) {
			RankingData rankingData = new RankingData();

			// spot1は必須とする
			DateSpot spot1 = dateSpotRepository.findById(Long.valueOf(plan.getSpot1()));
			if (spot1 == null) {
				continue;
			}

			// spot2とspot3はNULLの可能性がある
			DateSpot spot2 = null;
			DateSpot spot3 = null;

			if (plan.getSpot2() != null) {
				spot2 = dateSpotRepository.findById(Long.valueOf(plan.getSpot2()));
			}

			if (plan.getSpot3() != null) {
				spot3 = dateSpotRepository.findById(Long.valueOf(plan.getSpot3()));
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