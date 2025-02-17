package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.DateShare;
import com.example.demo.entity.DateSpot;
import com.example.demo.entity.RandomShare;
import com.example.demo.entity.Users;
import com.example.demo.repository.DateShareRepository;
import com.example.demo.repository.DateSpotRepository;
import com.example.demo.repository.RandomShareRepository;
import com.example.demo.service.DatePlanShareService;

@Controller
@RequestMapping("/dateMaster")
public class DatePlanSharecontroller {

	@Autowired
	private DateShareRepository dateShareRepository;

	@Autowired
	private DateSpotRepository dateSpotRepository;

	@Autowired
	private DatePlanShareService datePlanShareService;

	@Autowired
	private RandomShareRepository randomShareRepository;

	@GetMapping("/shareRandomPlan")
	public String randomPlanShare(Model model, HttpSession session) {
		Users currentUser = (Users) session.getAttribute("loginUser");

		if (currentUser == null) {
			return "redirect:/login";
		}

		// パートナーとユーザーIDを使用して最新の共有プランを取得
		Optional<RandomShare> sharedPlan = randomShareRepository.findByPartnerIdAndUserId(
				currentUser.getId(),
				currentUser.getPartner());

		if (sharedPlan.isPresent()) {
			RandomShare plan = sharedPlan.get();
			List<DateSpot> spots = new ArrayList<>();

			// spot1, spot2, spot3のIDを使用してDateSpotの情報を取得
			if (plan.getSpot1() != null) {
				DateSpot spot1 = dateSpotRepository.findById(plan.getSpot1());
				if (spot1 != null) {
					spots.add(spot1);
				}
			}

			if (plan.getSpot2() != null) {
				DateSpot spot2 = dateSpotRepository.findById(plan.getSpot2());
				if (spot2 != null) {
					spots.add(spot2);
				}
			}

			if (plan.getSpot3() != null) {
				DateSpot spot3 = dateSpotRepository.findById(plan.getSpot3());
				if (spot3 != null) {
					spots.add(spot3);
				}
			}

			model.addAttribute("sharedPlan", plan);
			model.addAttribute("spots", spots);
		}

		return "date_reference/random_plan_share";
	}

	private void getRandomSpotDetails(Long spotId, List<DateSpot> spots) {
		if (spotId != null) {
			DateSpot spot = dateSpotRepository.findById(spotId);
			if (spot != null) {
				spots.add(spot);
			}
		}
	}

	@GetMapping("/myDatePlans")
	public String myDatePlans(Model model, HttpSession session) {
		Users currentUser = (Users) session.getAttribute("loginUser");

		if (currentUser == null) {
			return "redirect:/login";
		}

		List<DateSpot> ownPlanSpots = new ArrayList<>();
		List<DateSpot> sharedPlanSpots = new ArrayList<>();

		if (currentUser.getDate_share() != null) {
			Optional<DateShare> datePlanOptional = dateShareRepository.findById(currentUser.getDate_share());

			if (datePlanOptional.isPresent()) {
				DateShare plan = datePlanOptional.get();
				addSpotIfExists(plan.getSpot1(), ownPlanSpots);
				addSpotIfExists(plan.getSpot2(), ownPlanSpots);
				addSpotIfExists(plan.getSpot3(), ownPlanSpots);

				model.addAttribute("ownPlanCount", plan.getCount());
			}
		}

		if (currentUser.getShared_date_plan() != null) {
			Optional<DateShare> sharedPlanOptional = dateShareRepository.findById(currentUser.getShared_date_plan());

			if (sharedPlanOptional.isPresent()) {
				DateShare sharedPlan = sharedPlanOptional.get();
				addSpotIfExists(sharedPlan.getSpot1(), sharedPlanSpots);
				addSpotIfExists(sharedPlan.getSpot2(), sharedPlanSpots);
				addSpotIfExists(sharedPlan.getSpot3(), sharedPlanSpots);

				model.addAttribute("sharedPlanCount", sharedPlan.getCount());
			}
		}

		model.addAttribute("ownPlanSpots", ownPlanSpots);
		model.addAttribute("sharedPlanSpots", sharedPlanSpots);

		return "date_reference/date_reference_list";
	}

	@GetMapping("/createRandomPlan") // URLを変更
	public String shareRandomPlan(Model model, HttpSession session) {
		Users currentUser = (Users) session.getAttribute("loginUser");

		if (currentUser == null) {
			return "redirect:/login";
		}

		return "date_reference/random_plan_share";
	}

	@PostMapping("/shareDatePlan")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> shareDatePlan(HttpSession session) {
		Map<String, Object> response = new HashMap<>();
		Users currentUser = (Users) session.getAttribute("loginUser");

		if (currentUser == null) {
			response.put("success", false);
			response.put("message", "パートナーが見つかりません");
			return ResponseEntity.badRequest().body(response);
		}

		try {
			datePlanShareService.shareDatePlanWithPartner(currentUser);
			response.put("success", true);
			response.put("message", "デートプランを共有しました");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "パートナーがいません");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	private void addSpotIfExists(Long spotId, List<DateSpot> spots) {
		if (spotId != null) {
			DateSpot spot = dateSpotRepository.findById(spotId);
			if (spot != null) {
				spots.add(spot);
			}
		}
	}

	@GetMapping("/viewOwnDatePlanMap")
	public String viewOwnDatePlanMap(Model model, HttpSession session) {
		Users currentUser = (Users) session.getAttribute("loginUser");

		if (currentUser == null) {
			return "redirect:/login";
		}

		List<DateSpot> planSpots = new ArrayList<>();
		List<String> spotNames = new ArrayList<>();
		List<Double> spotLatitudes = new ArrayList<>();
		List<Double> spotLongitudes = new ArrayList<>();
		List<String> spotDescriptions = new ArrayList<>();
		List<String> spotAddresses = new ArrayList<>();

		// 自分のプランのみを表示
		if (currentUser.getDate_share() != null) {
			Optional<DateShare> datePlanOptional = dateShareRepository.findById(currentUser.getDate_share());

			if (datePlanOptional.isPresent()) {
				DateShare plan = datePlanOptional.get();
				addSpotDetailsIfExists(plan.getSpot1(), planSpots, spotNames, spotLatitudes, spotLongitudes,
						spotDescriptions, spotAddresses);
				addSpotDetailsIfExists(plan.getSpot2(), planSpots, spotNames, spotLatitudes, spotLongitudes,
						spotDescriptions, spotAddresses);
				addSpotDetailsIfExists(plan.getSpot3(), planSpots, spotNames, spotLatitudes, spotLongitudes,
						spotDescriptions, spotAddresses);
			}
		}

		// モデルに各情報を追加
		model.addAttribute("spotNames", spotNames);
		model.addAttribute("spotLatitudes", spotLatitudes);
		model.addAttribute("spotLongitudes", spotLongitudes);
		model.addAttribute("spotDescriptions", spotDescriptions);
		model.addAttribute("spotAddresses", spotAddresses);

		return "date_reference/date_plun_reference";
	}

	@GetMapping("/sharedRandomPlanMap")
	public String sharedRandomPlanMap(Model model, HttpSession session) {
		Users currentUser = (Users) session.getAttribute("loginUser");

		if (currentUser == null) {
			return "redirect:/login";
		}

		Optional<RandomShare> sharedPlan = randomShareRepository.findByPartnerIdAndUserId(
				currentUser.getId(),
				currentUser.getPartner());

		if (sharedPlan.isPresent()) {
			RandomShare plan = sharedPlan.get();
			List<DateSpot> spots = new ArrayList<>();

			if (plan.getSpot1() != null) {
				DateSpot spot1 = dateSpotRepository.findById(plan.getSpot1());
				if (spot1 != null) {
					spots.add(spot1);
				}
			}

			if (plan.getSpot2() != null) {
				DateSpot spot2 = dateSpotRepository.findById(plan.getSpot2());
				if (spot2 != null) {
					spots.add(spot2);
				}
			}

			if (plan.getSpot3() != null) {
				DateSpot spot3 = dateSpotRepository.findById(plan.getSpot3());
				if (spot3 != null) {
					spots.add(spot3);
				}
			}

			model.addAttribute("spots", spots);
		}

		return "date_reference/shared_random_plan_map";
	}

	@GetMapping("/viewSharedDatePlanMap")
	public String viewSharedDatePlanMap(Model model, HttpSession session) {
		Users currentUser = (Users) session.getAttribute("loginUser");

		if (currentUser == null) {
			return "redirect:/login";
		}

		List<DateSpot> planSpots = new ArrayList<>();
		List<String> spotNames = new ArrayList<>();
		List<Double> spotLatitudes = new ArrayList<>();
		List<Double> spotLongitudes = new ArrayList<>();
		List<String> spotDescriptions = new ArrayList<>();
		List<String> spotAddresses = new ArrayList<>();

		if (currentUser.getShared_date_plan() != null) {
			Optional<DateShare> sharedPlanOptional = dateShareRepository.findById(currentUser.getShared_date_plan());

			if (sharedPlanOptional.isPresent()) {
				DateShare sharedPlan = sharedPlanOptional.get();
				addSpotDetailsIfExists(sharedPlan.getSpot1(), planSpots, spotNames, spotLatitudes, spotLongitudes,
						spotDescriptions, spotAddresses);
				addSpotDetailsIfExists(sharedPlan.getSpot2(), planSpots, spotNames, spotLatitudes, spotLongitudes,
						spotDescriptions, spotAddresses);
				addSpotDetailsIfExists(sharedPlan.getSpot3(), planSpots, spotNames, spotLatitudes, spotLongitudes,
						spotDescriptions, spotAddresses);
			}
		}

		model.addAttribute("spotNames", spotNames);
		model.addAttribute("spotLatitudes", spotLatitudes);
		model.addAttribute("spotLongitudes", spotLongitudes);
		model.addAttribute("spotDescriptions", spotDescriptions);
		model.addAttribute("spotAddresses", spotAddresses);

		return "date_reference/date_plun_reference";
	}

	private void addSpotDetailsIfExists(Long spotId,
			List<DateSpot> planSpots,
			List<String> spotNames,
			List<Double> spotLatitudes,
			List<Double> spotLongitudes,
			List<String> spotDescriptions,
			List<String> spotAddresses) {
		if (spotId != null) {
			DateSpot spot = dateSpotRepository.findById(spotId);
			if (spot != null) {
				planSpots.add(spot);
				spotNames.add(spot.getSpotName());
				spotLatitudes.add(spot.getLatitude());
				spotLongitudes.add(spot.getLongitude());
				spotDescriptions.add(spot.getDescription());
				spotAddresses.add(spot.getSpotAddress());
			}
		}
	}
}
