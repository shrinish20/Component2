package com.tweetapp.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.tweetapp.domain.Tweet;
import com.tweetapp.domain.User;
import com.tweetapp.repository.TweetRepository;
import com.tweetapp.repository.UserRepository;

@Service
public class TweetService {

	private TweetRepository tweetRepo;

	public TweetService(TweetRepository tweetRepo) {
		this.tweetRepo = tweetRepo;
	}

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private UserRepository userRepo;

	private static final Logger LOGGER = LoggerFactory.getLogger(TweetService.class);

	public List<Tweet> getAllTweets() {
		LOGGER.info("Entering getAllTweets() service :::: {}");
		List<Tweet> tweets = tweetRepo.findAll();
		LOGGER.info("Exiting getAllTweets() service :::: {}");
		return tweets;
	}

	public String postTweet(Tweet tweet, String loginId) {
		LOGGER.info("Entering postTweet() service :::: {}");
		User userObj = userRepo.findByLoginId(loginId);
		List<Tweet> tweetList = userObj.getTweetList();
		Tweet resObj = tweetRepo.save(tweet);
		if (resObj != null) {
			tweetList.add(tweet);
			Query query = new Query();
			query.addCriteria(Criteria.where("loginId").is(loginId));
			Update update = new Update();
			update.set("tweetList", tweetList);
			mongoTemplate.findAndModify(query, update, User.class);
		}
		LOGGER.info("Exiting postTweet() service :::: {}");
		return (resObj != null) ? "Success" : "Failed";
	}

	public String updateTweet(String id, Tweet tweet, String loginId) {
		LOGGER.info("Entering updateTweet() service :::: {}");
		User userObj = userRepo.findByLoginId(loginId);
		// List<Tweet> tweetList = userObj.getTweetList();
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		Update update = new Update();
		update.set("tweetMessage", tweet.getTweetMessage());
		update.set("postedDate", (new Timestamp(new Date().getTime())).toString());
		Tweet resObj = mongoTemplate.findAndModify(query, update, Tweet.class);
		Query userQuery = new Query();
		userQuery.addCriteria(new Criteria().andOperator(Criteria.where("_id").is(userObj.getId()),
				Criteria.where("tweetList").elemMatch(Criteria.where("_id").is(id))));
		Update userUpdate = new Update();
		userUpdate.set("tweetList.$.tweetMessage", tweet.getTweetMessage());
		userUpdate.set("tweetList.$.postedDate", (new Timestamp(new Date().getTime())).toString());
		mongoTemplate.findAndModify(userQuery, userUpdate, User.class);
		LOGGER.info("Exiting updateTweet() service :::: {}");
		return (resObj != null) ? "Success" : "Failed";
	}

	public void deleteTweet(String id, String loginId) {
		LOGGER.info("Entering deleteTweet() service :::: {}");
		User userObj = userRepo.findByLoginId(loginId);
		List<Tweet> tweetList = userObj.getTweetList();
		tweetRepo.deleteById(id);
		if (tweetList != null) {
			for (Tweet t : tweetList) {
				if (id.equals(t.getId())) {
					this.mongoTemplate.updateFirst(new Query(),
							new Update().pull("tweetList", Query.query(Criteria.where("_id").is(id))), "Tweet");
					LOGGER.info("Exiting deleteTweet() service :::: {}");
					break;
				} else {
					continue;
				}
			}
		}
	}

	public String likeTweet(String id, boolean isLiked) {
		LOGGER.info("Entering likeTweet() service :::: {}");
		Tweet resObj = null;
		Optional<Tweet> tweetList = tweetRepo.findById(id);
		User userObj = userRepo.findByLoginId(tweetList.get().getLoginId());
		int likes = 0;
		if (tweetList.isPresent()) {
			likes = tweetList.get().getLikes();
			likes = (isLiked && likes >= 0) ? (likes + 1) : ((likes > 0) ? (likes - 1) : 0);
			Query query = new Query();
			query.addCriteria(Criteria.where("id").is(id));
			Update update = new Update();
			update.set("likes", likes);
			resObj = mongoTemplate.findAndModify(query, update, Tweet.class);
			Query userQuery = new Query();
			userQuery.addCriteria(new Criteria().andOperator(Criteria.where("_id").is(userObj.getId()),
					Criteria.where("tweetList").elemMatch(Criteria.where("_id").is(id))));
			Update userUpdate = new Update();
			userUpdate.set("tweetList.$.likes", likes);
			mongoTemplate.findAndModify(userQuery, userUpdate, User.class);
		}
		LOGGER.info("Exiting likeTweet() service :::: {}");
		return (resObj != null) ? "Success" : "Failed";
	}

	public List<Tweet> getUserTweets(String userName) {
		LOGGER.info("Entering getUserTweets() service :::: {}");
		List<Tweet> tweetList = tweetRepo.findByLoginId(userName);
		LOGGER.info("Exiting getUserTweets() service :::: {}");
		return tweetList;
	}

}
