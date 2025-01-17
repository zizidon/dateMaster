package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.entity.Users;

public interface UserRepository extends CrudRepository<Users, Long> {

	// ユーザー名で検索するメソッドを追加
	Optional<Users> findByName(String name);
}
