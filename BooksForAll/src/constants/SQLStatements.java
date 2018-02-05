package constants;

public interface SQLStatements {

	public final String CREATE_USER_TABLE = "CREATE TABLE ebookUser\r\n" + 
			"    (id  INT PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1) ,\r\n" + 
			"    username VARCHAR(10) NOT NULL UNIQUE,\r\n" + 
			"    email VARCHAR(30) NOT NULL,\r\n" + 
			"    address VARCHAR(20) NOT NULL,\r\n" + 
			"    telephoneNumber VARCHAR(10) NOT NULL,\r\n" + 
			"    password VARCHAR(8) NOT NULL,\r\n" + 
			"    nickname VARCHAR(20) NOT NULL,\r\n" + 
			"    shortDescription VARCHAR(50),\r\n" + 
			"    photo VARCHAR(45)\r\n" + 
			"    );";
	
	
	public final String CREATE_MANAGER_TABLE = "CREATE TABLE manager" + 
			"(id INT PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1)," + 
			"username VARCHAR(10) NOT NULL," + 
			"password VARCHAR(8) NOT NULL" + 
			")";
	
	
	public final String CREATE_EBOOK_TABLE = "CREATE TABLE ebook\r\n" + 
			"	(id  INT PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1) ,\r\n" + 
			"  	name VARCHAR(20) NOT NULL,\r\n" + 
			"  	image VARCHAR(20),\r\n" + 
			"  	price INT DOUBLE(3,2) NULL,\r\n" + 
			"   shortDescription VARCHAR(50),\r\n" + 
			"	likesNum INT NOT NULL,\r\n" + 
			"	review VARCHAR(50) NOT NULL\r\n" + 
			" 	); ";
	
	
	public final String CREATE_LIKES_TABLE = "CREATE TABLE likes\r\n" + 
			" 	(userId  INT NOT NULL,\r\n" + 
			" 	 bookId  INT NOT NULL ,\r\n" + 
			" 	 nickname VARCHAR(20) NOT NULL,\r\n" + 
			" 	 FOREIGN KEY (bookId) REFERENCES ebook(id)\r\n" + 
			" 	);\r\n" + 
			"";
	
	public final String CREATE_REVIEWS_TABLE = "CREATE TABLE reviews\r\n" + 
			" 	(bookId INT NOT NULL,\r\n" + 
			" 	 userId INT NOT NULL,\r\n" + 
			" 	 nickname VARCHAR(20) NOT NULL,\r\n" + 
			" 	 review VARCHAR(50) NOT NULL,\r\n" + 
			" 	 FOREIGN KEY (bookId) REFERENCES ebook(id)\r\n" + 
			" 	);";
	
	public final String CREATE_PURCHASE_TABLE = "CREATE TABLE purchase\r\n" + 
			" 	(userId  INT NOT NULL,\r\n" + 
			" 	 bookId  INT NOT NULL,\r\n" + 
			"	 creditCardNumber INT(16) NOT NULL,\r\n" +
			"	 expiry DATE NOT NULL,\r\n" +
			"	 CVV INT(4) NOT NULL,\r\n" +
			"	 fullName VARCHAR(20) NOT NULL,\r\n" +
			"	 creditCardCompany VARCHAR(10) NOT NULL,\r\n" +
			" 	 FOREIGN KEY (userId) REFERENCES ebookUser(id)\r\n" + 
			" 	);";
			
	public final String INSERT_MANAGER_STMT = "INSERT INTO MANAGER (username,password) VALUES ('admin','Passw0rd');";

	public final String GET_MANAGER_STMT = " SELECT * FROM MANAGER "; 
	
}
