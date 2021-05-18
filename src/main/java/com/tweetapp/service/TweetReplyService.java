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
import com.tweetapp.domain.TweetReply;
import com.tweetapp.domain.User;
import com.tweetapp.repository.TweetReplyRepository;
import com.tweetapp.repository.TweetRepository;
import com.tweetapp.repository.UserRepository;

@Service
public class TweetReplyService {

	private TweetReplyRepository tweetReplyRepo;

	public TweetReplyService(TweetReplyRepository tweetReplyRepo) {
		this.tweetReplyRepo = tweetReplyRepo;
	}

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private TweetRepository tweetRepo;

	@Autowired
	private UserRepository userRepo;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TweetReplyService.class);

	public String replyTweet(String id, String loginId, TweetReply tweetReply) {		
		LOGGER.info("Entering replyTweet() service :::: {}");
		Tweet tweetObj = new Tweet();
		TweetReply resObj = null;
		tweetReply.setTweetId(id);
		tweetReply.setLoginId(loginId);
		tweetReply.setPostedDate(new Timestamp(new Date().getTime()).toString());
		resObj = tweetReplyRepo.save(tweetReply);
		Optional<Tweet> tweetList = tweetRepo.findById(id);
		if (tweetList.isPresent()) {
			tweetObj = tweetList.get();
			List<TweetReply> replyLists = tweetObj.getReply();
			replyLists.add(tweetReply);
			Query query = new Query();
			query.addCriteria(Criteria.where("id").is(id));
			Update update = new Update();
			update.set("reply", replyLists);
			mongoTemplate.findAndModify(query, update, Tweet.class);
			Query userQuery = new Query();
			userQuery.addCriteria(
					new Criteria().andOperator(Criteria.where("_id").is(userRepo.findByLoginId(tweetObj.getLoginId()).getId()),
							Criteria.where("tweetList").elemMatch(Criteria.where("_id").is(id))));
			Update userUpdate = new Update();
			userUpdate.set("tweetList.$.reply", replyLists);
			mongoTemplate.findAndModify(userQuery, userUpdate, User.class);
		}
		LOGGER.info("Exiting replyTweet() service :::: {}");
		return (resObj != null) ? "Success" : "Failed";
	}

}
