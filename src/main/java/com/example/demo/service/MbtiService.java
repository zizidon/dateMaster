package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.MbtiQuestion;
import com.example.demo.entity.MbtiResult;
import com.example.demo.entity.MbtiType;
import com.example.demo.repository.MbtiQuestionRepository;
import com.example.demo.repository.MbtiResultRepository;
import com.example.demo.repository.MbtiTypeRepository;

@Service
public class MbtiService {

@Autowired
private MbtiQuestionRepository mbtiQuestionRepository;

@Autowired
private MbtiResultRepository mbtiResultRepository;

@Autowired
private MbtiTypeRepository mbtiTypeRepository;

public List<MbtiQuestion> getAllQuestions() {
return (List<MbtiQuestion>) mbtiQuestionRepository.findAll();
}

public String calculateResult(Map<String, String> answers, Long userId) {
	Map<String, Integer> scores = new HashMap<>();
	scores.put("E", 0);
	scores.put("I", 0);
	scores.put("S", 0);
	scores.put("N", 0);
	scores.put("T", 0);
	scores.put("F", 0);
	scores.put("J", 0);
	scores.put("P", 0);

	List<MbtiQuestion> questions = (List<MbtiQuestion>) mbtiQuestionRepository.findAll();
		for (MbtiQuestion question : questions) {
			String answer = answers.get("answer_" + question.getId());
			if ("yes".equals(answer)) {
				String dimension = question.getDimension();
				String positiveTrait = dimension.substring(0, 1);
				scores.put(positiveTrait, scores.getOrDefault(positiveTrait, 0) + 1);
			} else if ("no".equalsIgnoreCase(answer)) {
				String dimension = question.getDimension();
				String negativeTrait = dimension.substring(1, 2);
				scores.put(negativeTrait, scores.getOrDefault(negativeTrait, 0) + 1);
			}
		}

		String resultType = buildResultType(scores);

		// resultTypeに対応するMbtiTypeのdescriptionを取得
	    MbtiType mbtiType = mbtiTypeRepository.findByType(resultType).orElse(null);
	    String description = mbtiType != null ? mbtiType.getDescription() : "Description not available";
		//結果をデータベースに保存
		MbtiResult mbtiResult = new MbtiResult(resultType,userId,description);
		mbtiResultRepository.save(mbtiResult);

		return resultType;
	}

	public List<MbtiType> getMbtiDetailsByUserId(Long userId) {
		Optional<MbtiResult> results = mbtiResultRepository.findById(userId);
		return results.stream()
            .map(result -> mbtiTypeRepository.findByType(result.getResultType()))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
}

	private String buildResultType(Map<String, Integer> scores) {
		StringBuilder resultType = new StringBuilder();
		resultType.append(scores.get("E") >= scores.get("I") ? "E" : "I");
		resultType.append(scores.get("S") >= scores.get("N") ? "S" : "N");
		resultType.append(scores.get("T") >= scores.get("F") ? "T" : "F");
		resultType.append(scores.get("J") >= scores.get("P") ? "J" : "P");
		return resultType.toString();
	}

	private String tieBreak(String trait1, String trait2, Map<String, Integer> scores) {
		int score1 = scores.get(trait1);
		int score2 = scores.get(trait2);

		if (score1 > score2) {
			return trait1;
		} else if (score2 > score1) {
			return trait2;
		} else {
			// 同点の場合ランダムに選択
			return new Random().nextBoolean() ? trait1 : trait2;
		}
	}
}