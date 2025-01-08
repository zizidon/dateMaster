package com.example.demo.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Users;

public interface UserRepository extends CrudRepository<Users, Long> {

	@Transactional
	@Modifying
	@Query("UPDATE Users u SET u.icon_Path = :iconPath WHERE u.id = :id")
	void updateIconPath(Long id, String iconPath);

}
