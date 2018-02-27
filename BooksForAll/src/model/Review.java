package model;
/**
 * Revies class
 *this class is for adding and getting a review
 */
public class Review {
	
	String username;
	String title;
	int approved;
	String nickname;
	String review;
	/**
	 * 
	 * @param username of a user
	 * @param title ebook title
	 * @param approved , 0 when adding review , 1 after approving
	 * @param nickname 
	 * @param review , the review text 
	 */
	public Review(String username,String title, int approved, String nickname, String review) {
		super();
		this.username = username;
		this.title = title;
		this.approved = approved;
		this.nickname = nickname;
		this.review = review;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getApproved() {
		return approved;
	}
	public void setApproved(int approved) {
		this.approved = approved;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	
	

}
