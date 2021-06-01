package com.tweetapp.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tweetapp.domain.User;
import com.tweetapp.exception.EntityNotFoundException;
import com.tweetapp.repository.UserRepository;

@Service
public class UserService implements UserDetailsService{

	private final UserRepository userRepo;

	public UserService(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Autowired
	private MongoTemplate mongoTemplate;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TweetService.class);
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User userObj = userRepo.findByLoginId(username);
		if(userObj == null) {
			throw new EntityNotFoundException(username + " - Username Not Found");
		}
		return new org.springframework.security.core.userdetails.User(userObj.getLoginId(), userObj.getUserPassword(), new ArrayList<>());
	}

	public List<User> getUserDetails() {
		LOGGER.info("Entering getUserDetails() service :::: {}");
		List<User> userList = userRepo.findAll();
		LOGGER.info("Exiting getUserDetails() service :::: {}");
		return userList;
	}

	public String registerUser(User user) {
		LOGGER.info("Entering registerUser() service :::: {}");
		String result = "";
		boolean isLoginId = userRepo.existsByLoginId(user.getLoginId());
		boolean isEmailId = userRepo.existsByEmailId(user.getEmailId()); 		
		if(!(isLoginId || isEmailId)) {
			User resObj = userRepo.save(user);
			result = (resObj != null) ? "Success" : "Failed";
		} else {
			result = "User Already Exist for the ID - " + (isLoginId) != null?user.getLoginId():user.getEmailId();
		}
		LOGGER.info("Exiting registerUser() service :::: {}");
		return result;
	}

	public User searchByUsername(String loginId) {
		LOGGER.info("Entering searchByUsername() service :::: {}");
		User userObj = userRepo.findByLoginId(loginId);
		LOGGER.info("Exiting searchByUsername() service :::: {}");
		return userObj;
	}

	public String resetPassword(User user) {
		LOGGER.info("Entering resetPassword() service :::: {}");
		Query query = new Query();
		query.addCriteria(Criteria.where("loginId").is(user.getLoginId()));
		Update update = new Update();
		update.set("userPassword", user.getUserPassword());
		User resObj = mongoTemplate.findAndModify(query, update, User.class);		
		LOGGER.info("Exiting resetPassword() service :::: {}");
		return (resObj != null) ? "Success" : "Failed";
	}

}
