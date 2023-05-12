package com.study.home.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query(value = "select count(*) from user where user_id = :userId", nativeQuery = true)
	int findByUserId(@Param(value = "userId") String userId);

}
