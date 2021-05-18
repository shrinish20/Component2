package com.tweetapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tweetapp.domain.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
	boolean existsByEmailId(String emailId);
	boolean existsByLoginId(String loginId);
	User findByLoginId(String loginId);
}
