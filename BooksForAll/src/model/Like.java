package model;

public class Like {

	int userId;
	int bookId;
	String nickname;
	
	public Like(int userId, int bookId, String nickname) {
		super();
		this.userId = userId;
		this.bookId = bookId;
		this.nickname = nickname;
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
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	
}
