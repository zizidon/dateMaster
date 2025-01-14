package com.example.demo.controller;

import java.util.Optional;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Coaching;
import com.example.demo.entity.Users;
import com.example.demo.repository.CoachingRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.PartnerRequestService;

@Controller
@RequestMapping("dateMaster")
public class Partnercontroller {

	@Autowired
	HttpSession session;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
    PartnerRequestService partnerRequestService;

	UserRepository userRepo;

	@Autowired
	CoachingRepository coachingRepo;

	//パートナー画面へ遷移
	@GetMapping("partner")
	public ModelAndView showPartnerPage(ModelAndView mav) {
		//現在ログイン中のユーザー情報を取得
		Users user = (Users) session.getAttribute("loginUser");

		if (user != null && user.getPartner() != null) {

			//パートナーの情報をデータベースから取得
			Users partner = userRepo.findById(user.getPartner()).orElse(null);
			mav.addObject("partner", partner);
		} else {
			mav.addObject("partner", null);
		}
		mav.setViewName("partner/partner");
		return mav;
	}
	
	//パートナー申請画面を表示
	@GetMapping("partner_request")
	public ModelAndView showPartnerRequestPage() {
		ModelAndView modelAndView = new ModelAndView("partner_request/partner_request");
		
		Users user = (Users) session.getAttribute("loginUser");
		
		if(user != null) {
			modelAndView.addObject("user",user);
		}
		return modelAndView;
	}
	
	//パートナーID検索をする
    @GetMapping("user/search")
    public String search(@RequestParam Long id, Model model) {
        Optional<Users> userOpt = partnerRequestService.getUserById(id);

        if (userOpt.isPresent()) {
            model.addAttribute("searchedUser", userOpt.get());
            model.addAttribute("message", null);
        } else {
            model.addAttribute("searchedUser", null);
            model.addAttribute("message", "指定されたIDのユーザーが見つかりませんでした。");
        }

        return "partner_request/partner_request";
    }

    // パートナー申請を実行
    @PostMapping("partner_request")
    public String requestPartner(@RequestParam("id") Long partnerId, Model model) {
        // セッションからログイン中のユーザー情報を取得
        Users loggedInUser = (Users) session.getAttribute("loginUser");

        if (loggedInUser == null) {
            model.addAttribute("message", "ログインが必要です。");
            return "partner_request/partner_request";
        }

        // パートナー申請処理
        Optional<Users> partnerOpt = userRepository.findById(partnerId);

        if (partnerOpt.isPresent()) {
            Users partner = partnerOpt.get();

            // パートナー申請を実行
            boolean success = partnerRequestService.requestPartner(loggedInUser, partner);

            if (success) {
                model.addAttribute("message", "パートナー申請が成功しました。");
                model.addAttribute("updatedPartner", partner);
            } else {
                // すでに申請済みまたはパートナーが設定されている場合のエラー
                model.addAttribute("message", "このユーザーにはすでに申請済み、または既にパートナーです。");
            }
        } else {
            model.addAttribute("message", "指定されたIDのユーザーが見つかりませんでした。");
        }

        return "partner_request/partner_request";
    }

	//パートナー削除画面へ遷移
	@GetMapping("/partnerDelete")
	public ModelAndView showPartnerDeletePage(ModelAndView mav) {
		Users user = (Users) session.getAttribute("loginUser");

		if (user != null && user.getPartner() != null) {
			Users partner = userRepo.findById(user.getPartner()).orElse(null);
			mav.addObject("partner", partner);
		} else {
			mav.addObject("partner", null);
		}
		mav.setViewName("partnerDelete/partner_delete");
		return mav;
	}

	//パートナー削除確認画面へ遷移
	@GetMapping("/partnerDeleteCheck")
	public ModelAndView showPartnerDeleteCheckPage(ModelAndView mav) {
		mav.setViewName("partnerDelete/partner_delete_check");
		return mav;
	}

	//パートナー削除処理
	@PostMapping("/partnerDeleteComplete")
	public ModelAndView deletePartner(ModelAndView mav) {
		Users user = (Users) session.getAttribute("loginUser");

		if (user != null && user.getPartner() != null) {
			//ユーザーのパートナーIDを取得
			Long partnerId = user.getPartner();

			//ユーザーのパートナー情報を削除
			user.setPartner(null);
			userRepo.save(user);

			//相手(パートナー)のパートナー情報を削除
			Users partner = userRepo.findById(partnerId).orElse(null);
			if (partner != null) {
				partner.setPartner(null);
				userRepo.save(partner);
			}
		}

		mav.setViewName("partnerDelete/partner_delete_complete");

		return mav;
	}

	//パートナー了承画面へ遷移
	@GetMapping("/partnerAccept")
	public ModelAndView showPartnerAcceptPage(ModelAndView mav) {
		Users user = (Users) session.getAttribute("loginUser");

		if (user != null) {
			// 浮気チェック
			if (user.getPartner() != null) {
				mav.setViewName("partnerAccept/partner_already");
				return mav;
			}

			// applicant 情報を取得
			Users applicant = null;
			if (user.getApplicant() != null) {
				applicant = userRepo.findById(user.getApplicant()).orElse(null);
			}
			mav.addObject("applicant", applicant);
		}

		mav.setViewName("partnerAccept/partner_accept");
		return mav;
	}

	// applicant を拒否
	@PostMapping("/partnerReject")
	public String rejectApplicant() {
		Users user = (Users) session.getAttribute("loginUser");

		if (user != null) {
			user.setApplicant(null);
			userRepo.save(user);
		}

		return "redirect:/dateMaster/partner";
	}

	// applicant を承認
	@PostMapping("/partnerApprove")
	public String approveApplicant() {
		Users user = (Users) session.getAttribute("loginUser");

		if (user != null && user.getApplicant() != null) {
			//自分のpartnerにapplicantのIDを設定
			Long applicantId = user.getApplicant();
			user.setPartner(user.getApplicant());
			user.setApplicant(null);
			userRepo.save(user);

			//applicantのpartnerに自分のIDを設定
			Users applicant = userRepo.findById(applicantId).orElse(null);
			if (applicant != null) {
				applicant.setPartner(user.getId());
				userRepo.save(applicant);
			}
		}

		return "redirect:/dateMaster/partner";
	}

	//パートナーコーチング画面へ遷移
	@GetMapping("/partnerCoaching")
	public ModelAndView showPartnerCoachingPage(ModelAndView mav) {
		mav.setViewName("partner_coach/partner_coach");
		return mav;
	}

	//パートナー問題一覧へ遷移
	@GetMapping("/partnerCoachingList")
	public ModelAndView showPartnerCoachingListPage(ModelAndView mav) {
		//現在ログイン中のユーザー情報を取得
		Users user = (Users) session.getAttribute("loginUser");

		//ユーザーが存在し、関連するCoaching情報を取得
		if (user != null) {
			// CoachingテーブルからユーザーIDと一致する問題を取得
			Iterable<Coaching> coachingList = coachingRepo.findByUserId(user.getId());
			mav.addObject("coachingList", coachingList);
		}
		mav.setViewName("partner_coach_list/partner_coach_list");
		return mav;
	}

	//問題編集画面に遷移
	@GetMapping("/editProblem")
	public ModelAndView showQuestionEditPage(ModelAndView mav) {
		Users user = (Users) session.getAttribute("loginUser");

		//ユーザーが存在し、関連するCoaching情報を取得
		if (user != null) {
			// CoachingテーブルからユーザーIDと一致する問題を取得
			Iterable<Coaching> coachingList = coachingRepo.findByUserId(user.getId());
			mav.addObject("coachingList", coachingList);
		}
		mav.setViewName("question_edit/question_edit");
		return mav;
	}

	// 問題を削除
	@PostMapping("/deleteProblem")
	public String deleteProblem(Long coachingId) {
		coachingRepo.deleteById(coachingId); // 指定されたIDの問題を削除
		return "redirect:/dateMaster/editProblem"; // 問題編集画面にリダイレクト
	}

	// 問題作成画面に遷移
	@GetMapping("/questionCreate")
	public ModelAndView showQuestionCreatePage(ModelAndView mav) {
		mav.setViewName("question_create/question_create");
		return mav;
	}

	// 問題を作成
	@PostMapping("/createProblem")
	public String createProblem(Coaching coaching) {
		Users user = (Users) session.getAttribute("loginUser");

		if (user != null) {
			coaching.setUserId(user.getId()); // ユーザーIDをセット
			coachingRepo.save(coaching); // 問題を保存
		}

		return "redirect:/dateMaster/editProblem"; // 問題編集画面にリダイレクト
	}

	//共有画面へ遷移
	@PostMapping("/shareProblem")
	public ModelAndView showShareProblemPage(Long coachingId, ModelAndView mav) {
		Coaching coaching = coachingRepo.findById(coachingId).orElse(null);

		if (coaching != null) {
			mav.addObject("coaching", coaching); //Coachingデータを画面に渡す
		}

		mav.setViewName("question_share/question_share");
		return mav;
	}

	//問題共有を確定
	@PostMapping("/shareProblemComplete")
	public String completeShareProblem(Long coachingId) {
		Users user = (Users) session.getAttribute("loginUser");

		if (user != null && user.getPartner() != null) {
			// CoachingテーブルのpartnerIdにログインユーザーのパートナー情報を保存
			Coaching coaching = coachingRepo.findById(coachingId).orElse(null);
			if (coaching != null) {
				coaching.setPartnerId(user.getPartner());
				coachingRepo.save(coaching);
			}
		}

		return "redirect:/dateMaster/shareComplete"; //完了画面にリダイレクト
	}

	//共有完了画面編遷移
	@GetMapping("/shareComplete")
	public ModelAndView showShareCompletePage(ModelAndView mav) {
		mav.setViewName("question_share/question_share_complete");
		return mav;
	}

	// 共有問題一覧画面へ遷移
	@GetMapping("/questionAnswerShare")
	public ModelAndView showQuestionAnswerSharePage(ModelAndView mav) {
		Users user = (Users) session.getAttribute("loginUser");

		// ユーザーが存在し、partnerIdが設定されている場合、共有された問題を取得
		if (user != null && user.getPartner() != null) {
			// partnerIdが一致する問題をCoachingテーブルから取得
			Iterable<Coaching> sharedCoachingList = coachingRepo.findByPartnerId(user.getId());
			mav.addObject("sharedCoachingList", sharedCoachingList);
		}

		mav.setViewName("question_answer/question_answer_share");
		return mav;
	}

	//問題選択画面へ遷移
	@GetMapping("/questionAnswer")
	public ModelAndView showQuestionAnswerPage(Long coachingId, ModelAndView mav) {
		Coaching coaching = coachingRepo.findById(coachingId).orElse(null);
		mav.addObject("coaching", coaching); // 問題を渡す
		mav.setViewName("question_answer/question_answer");
		return mav;
	}

	//選択肢を保存する
	@PostMapping("/selectOption")
	public String selectOption(Long coachingId, int selectedOption) {
		session.setAttribute("selectedOption", selectedOption); // セッションに選択肢を保存
		return "redirect:/dateMaster/questionAnswer?coachingId=" + coachingId;
	}

	@PostMapping("/questionAnswerCheck")
	public ModelAndView checkAnswer(@RequestParam Long coachingId, @RequestParam String selectedOption,
			ModelAndView mav) {
		// 入力チェック
		if (selectedOption == null || selectedOption.isEmpty()) {
			mav.addObject("errorMessage", "選択肢を選んでください。");
			mav.setViewName("question_answer/question_answer");
			return mav;
		}

		// Coachingデータ取得
		Coaching coaching = coachingRepo.findById(coachingId).orElse(null);

		if (coaching != null) {
			// selectedOptionを整数に変換
			int selectedOptionInt = Integer.parseInt(selectedOption);
			String selectedAnswer = "";
			switch (selectedOptionInt) {
			case 1:
				selectedAnswer = coaching.getOption1();
				break;
			case 2:
				selectedAnswer = coaching.getOption2();
				break;
			case 3:
				selectedAnswer = coaching.getOption3();
				break;
			case 4:
				selectedAnswer = coaching.getOption4();
				break;
			}

			mav.addObject("coaching", coaching); // coachingをビューに渡す
			mav.addObject("question", coaching.getQuestion());
			mav.addObject("selectedAnswer", selectedAnswer);
			mav.addObject("selectedOption", selectedOptionInt); // 数値として渡す
		} else {
			mav.addObject("errorMessage", "問題が見つかりませんでした。");
		}

		mav.setViewName("question_answer/question_answer_check");
		return mav;
	}

	@GetMapping("/questionAnswerResult")
	public ModelAndView showQuestionAnswerResultPage(Long coachingId, ModelAndView mav) {
		Coaching coaching = coachingRepo.findById(coachingId).orElse(null);
		Integer selectedOption = (Integer) session.getAttribute("selectedOption");

		boolean isCorrect = selectedOption != null && coaching != null && selectedOption == coaching.getCorrectOption();
		mav.addObject("coaching", coaching);
		mav.addObject("selectedOption", selectedOption);
		mav.addObject("isCorrect", isCorrect);
		mav.setViewName("question_answer/question_answer_result");
		return mav;
	}
}
