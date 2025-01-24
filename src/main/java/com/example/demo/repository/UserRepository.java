package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.entity.Users;

public interface UserRepository extends CrudRepository<Users, Long> {

	// ユーザー名で検索するメソッドを追加
	Optional<Users> findByName(String name);

	Users findById(String userid);
	
    // date_shareを更新するためのメソッド
    Users save(Users user);
    
    // Find partner by current user's ID
    Optional<Users> findByPartner(Long userId);
    
 

    // Find user by ID (corrected return type)
    Optional<Users> findById(Long userid);
}