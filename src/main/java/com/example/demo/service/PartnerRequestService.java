package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Users;
import com.example.demo.repository.UserRepository;

@Service
public class PartnerRequestService {
	
	@Autowired
	UserRepository userRepository;
	
	//パートナーIDを検索するメソッド
	public Optional<Users> getUserById(long id) {
		return userRepository.findById(id);
	}
	
	//パートナー申請を実行
	public boolean requestPartner(Users applicant, Users partner) {
	    if (partner != null) {
	    	
	        // すでに申請中またはパートナーが設定されている場合はエラー
	        if (partner.getApplicant() != null || partner.getPartner() != null) {
	            return false; // 申請できない
	        }
	        
	        // ターゲットユーザーの applicant フィールドに申請者の ID をセット
	        partner.setApplicant(applicant.getId());

	        // 必要なら申請者情報も更新
	        applicant.setPartner(partner.getId());

	        // データベースに保存
	        userRepository.save(applicant);
	        userRepository.save(partner);

	        return true;
	    }
	    return false;
	}
}