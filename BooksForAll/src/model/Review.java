package model;

public class Review {
	
	int userId;
	int bookId;
	int approved;
	String nickname;
	String review;
	
	public Review(int userId, int bookId, int approved, String nickname, String review) {
		super();
		this.userId = userId;
		this.bookId = bookId;
		this.approved = approved;
		this.nickname = nickname;
		this.review = review;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
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
