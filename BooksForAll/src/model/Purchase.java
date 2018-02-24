package model;

import java.util.Date;

public class Purchase {

	String username;
	String title;
	String creditCardNumber;
	String expiry;
	String cvv;
	String fullName;
	String creditCardCompany;
	String time;
	
	public Purchase(String username, String title, String creditCardNumber, String expiry, String cVV, String fullName,
			String creditCardCompany) {
		super();
		this.username = username;
		this.title = title;
		this.creditCardNumber = creditCardNumber;
		this.expiry = expiry;
		this.cvv = cVV;
		this.fullName = fullName;
		this.creditCardCompany = creditCardCompany;
	}
	
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time =  time;
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
	public String getCreditCardNumber() {
		return creditCardNumber;
	}
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}
	public String getExpiry() {
		return expiry;
	}
	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}
	public String getCvv() {
		return cvv;
	}
	public void setCvv(String cVV) {
		this.cvv = cVV;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getCreditCardCompany() {
		return creditCardCompany;
	}
	public void setCreditCardCompany(String creditCardCompany) {
		this.creditCardCompany = creditCardCompany;
	}
	
}
