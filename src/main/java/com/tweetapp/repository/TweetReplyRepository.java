package com.tweetapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tweetapp.domain.TweetReply;

@Repository
public interface TweetReplyRepository extends MongoRepository<TweetReply, String> {

}
