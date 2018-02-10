package model;

public class Like {

	String username;
	String title;
	String nickname;
	
	public Like(String username, String title, String nickname) {
		super();
		this.username = username;
		this.title = title;
		this.nickname = nickname;
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
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	
}
