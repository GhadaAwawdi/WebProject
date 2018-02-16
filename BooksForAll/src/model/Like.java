package model;

public class Like {

	String username;
	int id;
	String nickname;
	
	public Like(String username, int id, String nickname) {
		super();
		this.username = username;
		this.id = id;
		this.nickname = nickname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	
}
