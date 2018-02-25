package constants;

public interface SQLStatements {

	public final String CREATE_USER_TABLE = "CREATE TABLE ebookUser" + 
			"(id  INT PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1)," + 
			"username VARCHAR(10) NOT NULL UNIQUE," + 
			"email VARCHAR(50) NOT NULL," + 
			"street VARCHAR(20) NOT NULL," + 
			"apartment INT NOT NULL," + 
			"city VARCHAR(20) NOT NULL," + 
			"postalCode VARCHAR(7) NOT NULL," + 
			"telephoneNumber VARCHAR(10) NOT NULL," + 
			"password VARCHAR(8) NOT NULL," + 
			"nickname VARCHAR(20) NOT NULL," + 
			"shortDescription VARCHAR(50)," + 
			"photo VARCHAR(100)" + 
			")";
	
	public final String CREATE_Admin_TABLE = "CREATE TABLE admin" + 
			"(id INT PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1)," + 
			"username VARCHAR(10) NOT NULL," + 
			"password VARCHAR(8) NOT NULL" + 
			")";
	
	public final String CREATE_EBOOK_TABLE = "CREATE TABLE ebook" + 
			"(title VARCHAR(45) PRIMARY KEY NOT NULL," + 
			"likesNum INT NOT NULL" + 
			")";
	
	public final String CREATE_LIKES_TABLE = "CREATE TABLE likes" + 
			"(username VARCHAR(10) NOT NULL ," + 
			"title VARCHAR(45) NOT NULL," + 
			"nickname VARCHAR(20) NOT NULL," + 
			"FOREIGN KEY (title) REFERENCES ebook(title) ON DELETE CASCADE," + 
			"FOREIGN KEY (username) REFERENCES ebookUser(username)"+
			"ON DELETE CASCADE"+
			")";
	
	public final String CREATE_REVIEWS_TABLE = "CREATE TABLE reviews" + 
			"(title VARCHAR(45) NOT NULL," + 
			"username VARCHAR(10) NOT NULL ," + 
			"nickname VARCHAR(20) NOT NULL," + 
			"review VARCHAR(1000) NOT NULL," + 
			"approved INT NOT NULL," +
			"FOREIGN KEY (title) REFERENCES ebook(title) ON DELETE CASCADE," + 
			"FOREIGN KEY (username) REFERENCES ebookUser(username)" +
			"ON DELETE CASCADE"+
			")";
	
	public final String CREATE_PURCHASE_TABLE = "CREATE TABLE purchase" + 
			"(username  VARCHAR(10) NOT NULL ," + 
			"title VARCHAR(45) NOT NULL," + 
			"creditCardNumber VARCHAR(16) NOT NULL," +
			"expiry VARCHAR(14) NOT NULL," +
			"cvv VARCHAR(4) NOT NULL," +
			"fullName VARCHAR(20) NOT NULL," +
			"creditCardCompany VARCHAR(10) NOT NULL," +
			"currentTimestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP," +
			"FOREIGN KEY (username) REFERENCES ebookUser(username) ON DELETE CASCADE," + 
			"FOREIGN KEY (title) REFERENCES ebook(title)" + 
			"ON DELETE CASCADE"+
			")";
	
	
//	public final static String dropEbookUser = "DROP TABLE ebookUser";

	public final static String INSERT_Admin_STMT = "INSERT INTO admin (username,password) VALUES ('admin','Passw0rd')";

	public final static String addNewUser = "INSERT INTO ebookUser (username, email,street,apartment,city,postalCode,TelephoneNumber,password,nickname,shortDescription,photo) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
	public final static String addNewEbook = "INSERT INTO ebook (title,likesNum) VALUES (?,?)";
	public final static String addNewLike = "INSERT INTO likes (username, title, nickname) VALUES (?,?,?)";
//	public final static String addNewLike = "INSERT INTO likes (username, title) VALUES (?,?)";
	public final static String addNewReview = "INSERT INTO reviews (title, username, nickname, review,approved) VALUES (?,?,?,?,?)";
	public final static String addNewPurchase = "INSERT INTO purchase (username, title, creditCardNumber, expiry, cvv, fullName, creditCardCompany) VALUES (?,?,?,?,?,?,?)";

	public final String GET_Admin_STMT = "SELECT* FROM admin"; 
	public final String GET_USERS_STMT = "SELECT* FROM ebookUser";
	public final String GET_EBOOKS_STMT = "SELECT* FROM ebook";
	public final String GET_LIKES_STMT = "SELECT* FROM likes";
	public final String GET_nicknamesOfebookLikes_STMT = "SELECT nickname FROM likes WHERE title=? ";
//	public final String GET_ebookLikesNum_STMT = "SELECT count(*) FROM likes WHERE title=? ";
	public final String GET_ebookLikes_STMT = "SELECT* FROM likes WHERE title=? ";
	
	public final String GET_ebookApprovedReviews_STMT = "SELECT* FROM reviews WHERE title=? and approved=1";
	public final String GET_unapprovedReviews_STMT = "SELECT* FROM reviews WHERE approved=0";
	public final String GET_PURCHASES_STMT = "SELECT* FROM purchase";
	public final String GET_EBOOKPURCHASES_STMT = "SELECT* FROM purchase WHERE title=?";

	public final String GET_userPurchases_STMT = "SELECT* FROM purchase WHERE username=? ";
	public final String GET_Ebook_STMT = "SELECT* FROM ebooks WHERE title=? ";
		
	public final String getNicknameByUsername = "SELECT nickname FROM ebookUser WHERE username=? ";
	public final String selectUserByUserInfo = "SELECT * FROM ebookUser WHERE username=? and password=? and email=? and street=? and apartment=? and city=? and postalCode=? and telephoneNumber=? and nickname=? and shortDescription=? and photo=?";

	public final String getUserByUsernameAndPassword = "SELECT * FROM ebookUser WHERE username=? and password=? ";
	public final String getAdminUsernameAndPassword = "SELECT * FROM admin WHERE username=? and password=? ";
	
	public final String selectUserByUsername = "SELECT * FROM ebookUser WHERE username=?";
	public final String selectEbookByName = "Select * From ebook WHERE title=? ";
	
	public final static String deleteUser = "DELETE FROM ebookUser WHERE username=?";
	public final static String deleteEbook = "DELETE FROM ebook WHERE title=?";

	public final static String unlikeEbook = "DELETE FROM likes WHERE username=? and title=?";
	public final static String updateLikesNum = "UPDATE ebook SET likesNum=? WHERE title=?";
	public final String getLikesNumByTitle = "SELECT* FROM ebook WHERE title=? ";
	public final String getLikesOrderedById = "SELECT * FROM ebook Order By title ";

	
	public final static String approveReview = "UPDATE reviews SET approved=1 WHERE title=? and username=? and nickname=? and review=?";

	public final String checkIfPurchased = "SELECT* FROM purchase WHERE username=? and title=?";
	public final String checkIfLiked = "SELECT* FROM likes WHERE username=? and title=?";
	public final String Get_UserByNickname = "SELECT* FROM ebookUser WHERE nickname=?";

}
