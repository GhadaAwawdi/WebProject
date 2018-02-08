package model;

public class Purchase {

	int userId;
	int bookId;
	String creditCardNumber;
	String expiry;
	String cvv;
	String fullName;
	String creditCardCompany;
	
	public Purchase(int userId, int bookId, String creditCardNumber, String expiry, String cVV, String fullName,
			String creditCardCompany) {
		super();
		this.userId = userId;
		this.bookId = bookId;
		this.creditCardNumber = creditCardNumber;
		this.expiry = expiry;
		this.cvv = cVV;
		this.fullName = fullName;
		this.creditCardCompany = creditCardCompany;
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
