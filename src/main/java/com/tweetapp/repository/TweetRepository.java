package com.tweetapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tweetapp.domain.Tweet;

import java.lang.String;
import java.util.List;

@Repository
public interface TweetRepository extends MongoRepository<Tweet, String> {

	List<Tweet> findByLoginId(String loginId);

}
