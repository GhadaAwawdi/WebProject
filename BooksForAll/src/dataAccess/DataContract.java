package dataAccess;

/**
 * 
 * @author Ghada
 *the actual name of each table column in the database is saved as a string in order to make it easy
 *for us to retrieve and update data from the dataabase
 */
public final class DataContract {

	/**
	 * 
	 * admin's table
	 *
	 */
	public static class ManagerTable{
		public static final String TABLE_NAME = "admin";
		public static final String COL_ID = "id";
		public static final String COL_USERNAME = "username";
		public static final String COL_PASSWORD = "password";
	}
	
	/**
	 * 
	 * regular user table columns
	 *
	 */
	public static class EbookUserTable{
		public static final String TABLE_NAME = "ebookUser";
		public static final String COL_ID = "id";
		public static final String COL_USERNAME = "username";
		public static final String EMAIL = "email";
		public static final String COL_STREET = "street";
		public static final String COL_APARTMENT = "apartment";
		public static final String COL_CITY = "city";
		public static final String COL_POSTALCODE = "postalCode";
		public static final String COL_TELEPHONENUMBER = "telephoneNumber";
		public static final String COL_PASSWORD = "password";
		public static final String COL_NICKNAME = "nickname";
		public static final String COL_SHORTDESCRIPTION = "shortDescription";
		public static final String COL_PHOTO = "photo";
	}
	
	/**
	 * 
	 * ebook table
	 *
	 */

	public static class EbookTable{
		public static final String TABLE_NAME = "ebook";
		public static final String COL_EBOOKTITLE = "title";
		public static final String COL_LIKESNUM = "likesNum";
	}
	/**
	 * 
	 * @author Ghada
	 *likes table columns names
	 */
	public static class LikesTable{
		public static final String TABLE_NAME = "likes";
		public static final String COL_USERNAME = "username";
		public static final String COL_EBOOKTITLE = "title";
		public static final String COL_NICKNAME = "nickname";
	}
	
	/**
	 * 
	 * reviews table columns
	 *
	 */
	public static class ReviewsTable{
		public static final String TABLE_NAME = "reviews";
		public static final String COL_USERNAME = "username";
		public static final String COL_EBOOKTITLE = "title";
		public static final String COL_USERID = "userId";
		public static final String COL_NICKNAME = "nickname";
		public static final String COL_REVIEW = "review";
		public static final String COL_APPROVED = "approved";
	}
	/**
	 * 
	 * purchases table columns
	 *
	 */
	public static class PurchaseTable{
		public static final String TABLE_NAME = "purchase";
		public static final String COL_USERID = "userId";
		public static final String COL_USERNAME = "username";
		public static final String COL_EBOOKTITLE = "title";
		public static final String COL_CREDITCARDNUMBER = "creditCardNumber";
		public static final String COL_EXPIRY = "expiry";
		public static final String COL_CVV = "cvv";
		public static final String COL_FULLNAME = "fullName";
		public static final String COL_CREDITCARDCOMPANY = "creditCardCompany";
		public static final String COL_TIME = "currentTimestamp";
	}
}
