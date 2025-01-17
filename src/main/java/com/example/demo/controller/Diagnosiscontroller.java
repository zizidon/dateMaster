package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Negative;
import com.example.demo.entity.Positive;
import com.example.demo.entity.Users;
import com.example.demo.repository.NegativeRepository;
import com.example.demo.repository.PositiveRepository;
import com.example.demo.repository.UserRepository;

@Controller
@RequestMapping("/dateMaster")
public class Diagnosiscontroller {

	@Autowired
	private PositiveRepository positiveRepository;

	@Autowired
	private NegativeRepository negativeRepository;

	@Autowired
	private UserRepository usersRepository;

	//自己診断一覧画面へ遷移
	@GetMapping("/diagonosis")
	public String showDiagnosisPage() {

		//diagnosis.htmlを返す

		return "diagonosis/diagonosis";
	}

	//自己診断回答画面へ遷移
	@GetMapping("/self_diagonosis")
	public String showSelfDiagnosisPage() {
		// self_diagnosis.htmlを返す
		return "self_diagonosis/self_diagonosis";
	}

	// ポジティブ/ネガティブ選択後の処理
	@PostMapping("/self_diagonosis_step2")
	public String handlePositiveNegativeSelection(@RequestParam("choice") String choice) {
		if ("positive".equals(choice)) {
			// ポジティブ画面へ遷移
			return "self_diagonosis/self_diagonosis_positive";
		} else {
			// ネガティブ画面 (仮) へ遷移
			return "self_diagonosis/self_diagonosis_negative";
		}
	}

	// ポジティブの回答確認画面へ遷移
	@PostMapping("/self_diagonosis_check_positive")
	public String showDiagnosisCheckPositive(
			@RequestParam("q1") String q1,
			@RequestParam("q2") String q2,
			@RequestParam("q3") String q3,
			@RequestParam("q4") String q4,
			@RequestParam("q5") String q5,
			Model model) {

		// スコア計算
		int score1 = 0, score2 = 0, score3 = 0, score4 = 0;

		if ("はい".equals(q1)) {
			score1 += 4;
			score2 += 1;
			score3 += 2;
			score4 += 1;
		}
		if ("はい".equals(q2)) {
			score1 += 1;
			score2 += 4;
			score3 += 1;
			score4 += 2;
		}
		if ("はい".equals(q3)) {
			score1 += 1;
			score2 += 1;
			score3 += 4;
			score4 += 1;
		}
		if ("はい".equals(q4)) {
			score1 += 1;
			score2 += 2;
			score3 += 1;
			score4 += 4;
		}
		if ("はい".equals(q5)) {
			score1 += 3;
			score2 += 2;
			score3 += 1;
			score4 += 2;
		}

		// 最大スコアのタイプ
		int maxScore = Math.max(Math.max(score1, score2), Math.max(score3, score4));
		String resultType = "";
		if (maxScore == score1)
			resultType = "タイプ1";
		else if (maxScore == score2)
			resultType = "タイプ2";
		else if (maxScore == score3)
			resultType = "タイプ3";
		else if (maxScore == score4)
			resultType = "タイプ4";

		// モデルにデータを渡す
		model.addAttribute("q1", q1);
		model.addAttribute("q2", q2);
		model.addAttribute("q3", q3);
		model.addAttribute("q4", q4);
		model.addAttribute("q5", q5);
		model.addAttribute("resultType", resultType);

		return "self_diagonosis/self_diagonosis_check_positive";
	}

	// ネガティブの回答確認画面へ遷移
	@PostMapping("/self_diagonosis_check_negative")
	public String showDiagnosisCheckNegative(
			@RequestParam("q1") String q1,
			@RequestParam("q2") String q2,
			@RequestParam("q3") String q3,
			@RequestParam("q4") String q4,
			@RequestParam("q5") String q5,
			Model model) {

		// スコア計算
		int score1 = 0, score2 = 0, score3 = 0, score4 = 0;

		if ("はい".equals(q1)) {
			score1 += 4;
			score2 += 1;
			score3 += 2;
			score4 += 1;
		}
		if ("はい".equals(q2)) {
			score1 += 1;
			score2 += 4;
			score3 += 1;
			score4 += 2;
		}
		if ("はい".equals(q3)) {
			score1 += 1;
			score2 += 1;
			score3 += 4;
			score4 += 1;
		}
		if ("はい".equals(q4)) {
			score1 += 1;
			score2 += 2;
			score3 += 1;
			score4 += 4;
		}
		if ("はい".equals(q5)) {
			score1 += 3;
			score2 += 2;
			score3 += 1;
			score4 += 2;
		}

		// 最大スコアのタイプ
		int maxScore = Math.max(Math.max(score1, score2), Math.max(score3, score4));
		String resultType = "";
		if (maxScore == score1)
			resultType = "タイプ5";
		else if (maxScore == score2)
			resultType = "タイプ6";
		else if (maxScore == score3)
			resultType = "タイプ7";
		else if (maxScore == score4)
			resultType = "タイプ8";

		// モデルにデータを渡す
		model.addAttribute("q1", q1);
		model.addAttribute("q2", q2);
		model.addAttribute("q3", q3);
		model.addAttribute("q4", q4);
		model.addAttribute("q5", q5);
		model.addAttribute("resultType", resultType);

		return "self_diagonosis/self_diagonosis_check_negative";
	}

	// 診断結果画面に遷移
	@PostMapping("/self_diagonosis_result_positive")
	public String showDiagnosisResultPositive(
			@RequestParam("resultType") String resultType,
			Model model) {

		// タイプIDを取得
		int typeId = 0;
		switch (resultType) {
		case "タイプ1":
			typeId = 1;
			break;
		case "タイプ2":
			typeId = 2;
			break;
		case "タイプ3":
			typeId = 3;
			break;
		case "タイプ4":
			typeId = 4;
			break;
		}

		// Positiveテーブルから診断結果を取得
		Positive positiveResult = positiveRepository.findById(typeId).orElse(null);
		if (positiveResult != null) {
			model.addAttribute("type", positiveResult.getType());
			model.addAttribute("text", positiveResult.getText());
			model.addAttribute("imagePath", positiveResult.getImagePath()); // 画像パスを追加
		} else {
			model.addAttribute("type", "結果が見つかりませんでした");
			model.addAttribute("text", "");
			model.addAttribute("imagePath", ""); // 画像がない場合のデフォルト値
		}

		// ユーザーの診断結果を保存
		Long userId = getLoggedInUserId(); // ログイン中のユーザーIDを取得するメソッドを実装
		if (userId != null) {
			Users user = usersRepository.findById(userId).orElse(null);
			if (user != null) {
				user.setDiagnosis(typeId);
				usersRepository.save(user);
			}
		}

		return "self_diagonosis/self_diagonosis_completion_positive";
	}

	// 診断結果画面に遷移
	@PostMapping("/self_diagonosis_result_negative")
	public String showDiagnosisResultNegative(
			@RequestParam("resultType") String resultType,
			Model model) {

		// タイプIDを取得
		int typeId = 0;
		switch (resultType) {
		case "タイプ5":
			typeId = 5;
			break;
		case "タイプ6":
			typeId = 6;
			break;
		case "タイプ7":
			typeId = 7;
			break;
		case "タイプ8":
			typeId = 8;
			break;
		}

		// Positiveテーブルから診断結果を取得
		Negative negativeResult = negativeRepository.findById(typeId).orElse(null);
		if (negativeResult != null) {
			model.addAttribute("type", negativeResult.getType());
			model.addAttribute("text", negativeResult.getText());
			model.addAttribute("imagePath", negativeResult.getImagePath()); // 画像パスを追加
		} else {
			model.addAttribute("type", "結果が見つかりませんでした");
			model.addAttribute("text", "");
			model.addAttribute("imagePath", ""); // 画像がない場合のデフォルト値
		}

		// ユーザーの診断結果を保存
		Long userId = getLoggedInUserId(); // ログイン中のユーザーIDを取得するメソッドを実装
		if (userId != null) {
			Users user = usersRepository.findById(userId).orElse(null);
			if (user != null) {
				user.setDiagnosis(typeId);
				usersRepository.save(user);
			}
		}

		return "self_diagonosis/self_diagonosis_completion_negative";
	}

	private Long getLoggedInUserId() {
		// ログインユーザーのIDを取得するロジックを実装（仮の例）
		return 1L; // ログインしているユーザーID（実際は認証情報から取得）
	}

}
