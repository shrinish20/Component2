package com.tweetapp.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Class Tweet
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "Tweet")
public class Tweet {

	@Id
	private String id;
	private String firstName;
	private String lastName;
	@Indexed
	private String loginId;
	private String tweetMessage;
	private String postedDate;
	private List<TweetReply> reply;
	private int likes;

	/**
	 * Constructor
	 */
	public Tweet() {
		super();
	}

	/**
	 * @param loginId
	 * @param tweetMessage
	 * @param postedDate
	 * @param reply
	 */
	public Tweet(String loginId, String tweetMessage, String postedDate, List<TweetReply> reply) {
		super();
		this.loginId = loginId;
		this.tweetMessage = tweetMessage;
		this.postedDate = postedDate;
		this.reply = reply;
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
	 * @return the tweetMessage
	 */
	public String getTweetMessage() {
		return tweetMessage;
	}

	/**
	 * @param tweetMessage the tweetMessage to set
	 */
	public void setTweetMessage(String tweetMessage) {
		this.tweetMessage = tweetMessage;
	}

	/**
	 * @return the postedDate
	 */
	public String getPostedDate() {
		return postedDate;
	}

	/**
	 * @param postedDate the postedDate to set
	 */
	public void setPostedDate(String postedDate) {
		this.postedDate = postedDate;
	}

	/**
	 * @return the reply
	 */
	public List<TweetReply> getReply() {
		return reply;
	}

	/**
	 * @param reply the reply to set
	 */
	public void setReply(List<TweetReply> reply) {
		this.reply = reply;
	}

	/**
	 * @return the likes
	 */
	public int getLikes() {
		return likes;
	}

	/**
	 * @param likes the likes to set
	 */
	public void setLikes(int likes) {
		this.likes = likes;
	}

	@Override
	public String toString() {
		return "Tweet [id=" + id + ", loginId=" + loginId + ", tweetMessage=" + tweetMessage + ", postedDate="
				+ postedDate + ", reply=" + reply + ", likes=" + likes + "]";
	}
}
