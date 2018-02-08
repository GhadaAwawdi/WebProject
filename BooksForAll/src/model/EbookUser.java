package model;

public class EbookUser {

	int id;
	String username;
	String email;
	String address;
	String telephoneNumber;
	String password;
	String nickname;
	String shortDescription;
	String photo;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public EbookUser(String username, String email, String address, String telephoneNumber, String password,
			String nickname, String shortDescription, String photo) {
		super();
		this.username = username;
		this.email = email;
		this.address = address;
		this.telephoneNumber = telephoneNumber;
		this.password = password;
		this.nickname = nickname;
		this.shortDescription = shortDescription;
		this.photo = photo;
	}
	
}
