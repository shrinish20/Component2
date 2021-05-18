package com.tweetapp.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mongodb.lang.NonNull;

/**
 * Class User
 *
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Document(collection = "User")
public class User {

	@Id
	private String id;
	@Indexed(direction = IndexDirection.ASCENDING)
	private String firstName;
	private String lastName;
	@NonNull
	@Indexed(unique = true, direction = IndexDirection.ASCENDING)
	private String emailId;
	@NonNull
	@Indexed(unique = true, direction = IndexDirection.ASCENDING)
	private String loginId;
	private String userPassword;
	private String contactNumber;
	private List<Tweet> tweetList;

	/**
	 * Constructor
	 */
	public User() {
		super();
	}

	/**
	 * @param firstName
	 * @param lastName
	 * @param emailId
	 * @param loginId
	 * @param userPassword
	 * @param contactNumber
	 */
	public User(String firstName, String lastName, String emailId, String loginId, String userPassword,
			String contactNumber) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.loginId = loginId;
		this.userPassword = userPassword;
		this.contactNumber = contactNumber;
	}
	
	/**
	 * @param firstName
	 * @param lastName
	 * @param emailId
	 * @param loginId
	 * @param userPassword
	 * @param contactNumber
	 * @param tweetList
	 */
	public User(String firstName, String lastName, String emailId, String loginId, String userPassword,
			String contactNumber, List<Tweet> tweetList) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.loginId = loginId;
		this.userPassword = userPassword;
		this.contactNumber = contactNumber;
		this.tweetList = tweetList;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * @return the loginId
	 */
	public String getLoginId() {
		return loginId;
	}

	/**
	 * @param loginId the loginId to set
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	/**
	 * @return the userPassword
	 */
	public String getUserPassword() {
		return userPassword;
	}

	/**
	 * @param userPassword the userPassword to set
	 */
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	/**
	 * @return the contactNumber
	 */
	public String getContactNumber() {
		return contactNumber;
	}

	/**
	 * @param contactNumber the contactNumber to set
	 */
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	/**
	 * @return the tweetList
	 */
	public List<Tweet> getTweetList() {
		return tweetList;
	}

	/**
	 * @param tweetList the tweetList to set
	 */
	public void setTweetList(List<Tweet> tweetList) {
		this.tweetList = tweetList;
	}		
}
