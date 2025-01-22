package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Users;
import com.example.demo.repository.UserRepository;

@Service
public class UserDeleteService {

	@Autowired
	UserRepository userRepo;

	// トランザクション管理を追加
	@Transactional
	public void deleteUser(Long userId) {
		// 1. 削除対象ユーザーを取得
		Users userToDelete = userRepo.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));

		// 2. パートナーのpartnerカラムをNULLに更新
		Long partnerUserId = userToDelete.getPartner();
		if (partnerUserId != null) {
			Users partner = userRepo.findById(partnerUserId)
					.orElseThrow(() -> new RuntimeException("Partner user not found"));
			partner.setPartner(null);
			userRepo.save(partner);
		}

		// 3. このユーザーをapplicantとして持つユーザーのapplicantカラムをNULLに更新
		Iterable<Users> allUsers = userRepo.findAll();
		for (Users user : allUsers) {
			if (userId.equals(user.getApplicant())) {
				user.setApplicant(null);
				userRepo.save(user);
			}
		}

		// 4. 最後にユーザーを削除
		userRepo.deleteById(userId);
	}
}