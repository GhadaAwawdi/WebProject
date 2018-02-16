package model;

public class Purchase {

	String username;
	int id;
	String creditCardNumber;
	String expiry;
	String cvv;
	String fullName;
	String creditCardCompany;
	
	public Purchase(String username, int id, String creditCardNumber, String expiry, String cVV, String fullName,
			String creditCardCompany) {
		super();
		this.username = username;
		this.id = id;
		this.creditCardNumber = creditCardNumber;
		this.expiry = expiry;
		this.cvv = cVV;
		this.fullName = fullName;
		this.creditCardCompany = creditCardCompany;
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
