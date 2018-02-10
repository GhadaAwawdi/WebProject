package dataAccess;

public final class DataContract {

	public static class ManagerTable{
		public static final String TABLE_NAME = "manager";
		public static final String COL_ID = "id";
		public static final String COL_USERNAME = "username";
		public static final String COL_PASSWORD = "password";
	}
	
	public static class EbookUserTable{
		public static final String TABLE_NAME = "ebookUser";
		public static final String COL_ID = "id";
		public static final String COL_USERNAME = "username";
		public static final String EMAIL = "email";
		public static final String COL_ADDRESS = "address";
		public static final String COL_TELEPHONENUMBER = "telephoneNumber";
		public static final String COL_PASSWORD = "password";
		public static final String COL_NICKNAME = "nickname";
		public static final String COL_SHORTDESCRIPTION = "shortDescription";
		public static final String COL_PHOTO = "photo";
	}

	public static class EbookTable{
		public static final String TABLE_NAME = "ebook";
		public static final String COL_ID = "id";
		public static final String COL_NAME = "title";
		public static final String COL_IMAGE = "image";
		public static final String COL_PRICE = "price";
		public static final String COL_SHORTDESCRIPTION = "shortDescription";
		public static final String COL_LIKESNUM = "likesNum";
	}
	
	public static class LikesTable{
		public static final String TABLE_NAME = "likes";
		public static final String COL_USERNAME = "username";
		public static final String COL_TITLE = "title";
		public static final String COL_NICKNAME = "nickname";
	}
	
	public static class ReviewsTable{
		public static final String TABLE_NAME = "reviews";
		public static final String COL_USERNAME = "username";
		public static final String COL_TITLE = "title";
		public static final String COL_USERID = "userId";
		public static final String COL_NICKNAME = "nickname";
		public static final String COL_REVIEW = "review";
		public static final String COL_APPROVED = "approved";
	}
	
	public static class PurchaseTable{
		public static final String TABLE_NAME = "purchase";
		public static final String COL_USERID = "userId";
		public static final String COL_USERNAME = "username";
		public static final String COL_TITLE = "title";
		public static final String COL_CREDITCARDNUMBER = "creditCardNumber";
		public static final String COL_EXPIRY = "expiry";
		public static final String COL_CVV = "cvv";
		public static final String COL_FULLNAME = "fullName";
		public static final String COL_CREDITCARDCOMPANY = "creditCardCompany";
	}
}
