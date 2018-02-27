package dataAccess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.NamingException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import constants.SQLStatements;
import utils.DBUtils;
import model.Ebook;
import model.EbookUser;
import model.Manager;
import model.Purchase;
import model.Review;

/**
 * @author Ghada This class handles all the connections with the database
 *         (Updating data and retrieving data)
 */
public class DataAccess implements DataInterface {

	public static Connection conn;

	/**
	 * 
	 * @throws NamingException
	 * @throws SQLException
	 *             creating derby connection through this constructor
	 */
	public DataAccess() throws NamingException, SQLException {
		Logger logger = Logger.getLogger(DataAccess.class.getName());
		logger.log(Level.INFO, "DataAccess c'tor: attempting connection...");
		DBUtils.getConnection();
		if (DBUtils.conn == null) {
			logger.log(Level.SEVERE, "Connection Failed");
		} else {
			logger.log(Level.INFO, "Connection Established");
		}
	}

	/**
	 * close the derby connection
	 */
	@Override
	public void closeConnection() throws SQLException {
		DBUtils.closeConnection();
	}

	@Override
	public EbookUser ebookUserLogin(String username, String password) throws SQLException {

		System.out.println("ebookUserLogin function");

		Logger logger = Logger.getLogger(DataAccess.class.getName());
		if (DBUtils.conn == null) {
			logger.log(Level.SEVERE, "Connection Failed");
			return null;
		}

		PreparedStatement stmt = DBUtils.conn.prepareStatement(SQLStatements.getUserByUsernameAndPassword);
		stmt.setString(1, username);
		stmt.setString(2, password);

		ResultSet rs = stmt.executeQuery();
		EbookUser user = null;

		if (rs.next()) {
			logger.log(Level.INFO, "User type ebook user");
			user = new EbookUser(rs.getString(DataContract.EbookUserTable.COL_USERNAME),
					rs.getString(DataContract.EbookUserTable.EMAIL),
					rs.getString(DataContract.EbookUserTable.COL_STREET),
					rs.getInt(DataContract.EbookUserTable.COL_APARTMENT),
					rs.getString(DataContract.EbookUserTable.COL_CITY),
					rs.getString(DataContract.EbookUserTable.COL_POSTALCODE),
					rs.getString(DataContract.EbookUserTable.COL_TELEPHONENUMBER),
					rs.getString(DataContract.EbookUserTable.COL_PASSWORD),
					rs.getString(DataContract.EbookUserTable.COL_NICKNAME),
					rs.getString(DataContract.EbookUserTable.COL_SHORTDESCRIPTION),
					rs.getString(DataContract.EbookUserTable.COL_PHOTO));
		}
		if (user != null)
			System.out.println("user " + rs.getString(DataContract.EbookUserTable.EMAIL)
					+ rs.getString(DataContract.EbookUserTable.COL_PASSWORD));

		rs.close();
		stmt.close();
		return user;
	}

	@Override
	public Manager managerLogin(String username, String password) throws SQLException {
		System.out.println("manager Login function");
		Logger logger = Logger.getLogger(DataAccess.class.getName());
		if (DBUtils.conn == null) {
			logger.log(Level.SEVERE, "Connection Failed");
			return null;
		}

		PreparedStatement stmt = DBUtils.conn.prepareStatement(SQLStatements.getAdminUsernameAndPassword);
		stmt.setString(1, username);
		stmt.setString(2, password);

		ResultSet rs = stmt.executeQuery();
		Manager manager = null;

		if (rs.next()) {
			manager = new Manager(rs.getString(DataContract.ManagerTable.COL_USERNAME),
					rs.getString(DataContract.ManagerTable.COL_PASSWORD));
			System.out.println("manager " + rs.getString(DataContract.ManagerTable.COL_USERNAME)
					+ rs.getString(DataContract.ManagerTable.COL_PASSWORD));

		}

		rs.close();
		stmt.close();
		return manager;
	}

	@Override
	public int addEbookUser(EbookUser user) throws SQLException {
		PreparedStatement stmt = DBUtils.conn.prepareStatement(SQLStatements.selectUserByUsername);
		stmt.setString(1, user.getUsername());
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) // user exists
		{
			System.out.println("BAD, USERname ALREADY EXISTS");
			return -1;
		}

		PreparedStatement stmt3 = DBUtils.conn.prepareStatement(SQLStatements.selectUserByUserInfo);
		stmt3.setString(1, user.getUsername());
		stmt3.setString(2, user.getPassword());
		stmt3.setString(3, user.getEmail());
		stmt3.setString(4, user.getStreet());
		stmt3.setInt(5, user.getApartment());
		stmt3.setString(6, user.getCity());
		stmt3.setString(7, user.getPostalCode());
		stmt3.setString(8, user.getTelephoneNumber());
		stmt3.setString(9, user.getNickname());
		stmt3.setString(10, user.getShortDescription());
		stmt3.setString(11, user.getPhoto());

		rs = stmt3.executeQuery();
		if (rs.next()) // user exists
		{
			stmt3.close();
			rs.close();
			System.out.println("BAD, USER ALREADY EXISTS");
			return 0;
		}

		PreparedStatement stmt2 = DBUtils.conn.prepareStatement(SQLStatements.addNewUser);
		stmt2.setString(1, user.getUsername());
		stmt2.setString(2, user.getEmail());
		stmt2.setString(3, user.getStreet());
		stmt2.setInt(4, user.getApartment());
		stmt2.setString(5, user.getCity());
		stmt2.setString(6, user.getPostalCode());
		stmt2.setString(7, user.getTelephoneNumber());
		stmt2.setString(8, user.getPassword());
		stmt2.setString(9, user.getNickname());
		stmt2.setString(10, user.getShortDescription());
		stmt2.setString(11, user.getPhoto());
		stmt2.executeUpdate();

		DBUtils.conn.commit();
		stmt2.close();
		return 1;
	}

	@Override
	public boolean removeEbookUser(String username) throws SQLException {
		PreparedStatement stm = DBUtils.conn.prepareStatement(SQLStatements.selectUserByUsername);
		stm.setString(1, username);
		ResultSet rs = stm.executeQuery();
		if (!rs.next()) {
			System.out.println("user does not exist,try a different user");
			return false;
		}
		PreparedStatement stm1 = DBUtils.conn.prepareStatement(SQLStatements.deleteUser);
		stm1.setString(1, username);
		stm1.executeUpdate();
		DBUtils.conn.commit();
		stm1.close();
		System.out.println("ebook user deleted successfully");
		return true;
	}

	@Override
	public boolean likeEbook(String title, String username) throws SQLException {
		PreparedStatement stmt = DBUtils.conn.prepareStatement(SQLStatements.getNicknameByUsername);
		stmt.setString(1, username);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			PreparedStatement stmt1 = DBUtils.conn.prepareStatement(SQLStatements.addNewLike);
			stmt1.setString(1, username);
			stmt1.setString(2, title);
			stmt1.setString(3, rs.getString(DataContract.LikesTable.COL_NICKNAME));
			stmt1.executeUpdate();
			DBUtils.conn.commit();
			stmt1.close();
			System.out.println("added to database");
			return true;
		}
		return false;
	}

	@Override
	public boolean unlikeEbook(String title, String username) throws SQLException {
		// PreparedStatement stm =
		// c.prepareStatement(SQLStatements.unlikeEbook);
		// stm.setString(1, username);
		// stm.setString(2, nameOfEbook);
		// ResultSet rs = stm.executeQuery();
		// if (!rs.next()) {
		// //System.out.println("like does not exist,try a different user");
		// return false;
		// }
		PreparedStatement stm1 = DBUtils.conn.prepareStatement(SQLStatements.unlikeEbook);
		stm1.setString(1, username);
		stm1.setString(2, title);
		stm1.executeUpdate();
		DBUtils.conn.commit();
		stm1.close();
		System.out.println("unlike done successfully");
		return true;
	}

	@Override
	public int numOfEbookLikes(String title) throws SQLException {
		System.out.println(title);
		int count = 0;
		PreparedStatement stmt = DBUtils.conn.prepareStatement(SQLStatements.getLikesNumByTitle);
		stmt.setString(1, title);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			// count++;
			count = rs.getInt("likesNum");

		}
		System.out.println(count);
		return count;
	}

	@Override
	public ArrayList<String> getUsersThatLikedEbook(String title) throws SQLException {

		PreparedStatement stm = DBUtils.conn.prepareStatement(SQLStatements.GET_ebookLikes_STMT);
		stm.setString(1, title);
		ArrayList<String> nicknames = new ArrayList<String>();
		ResultSet rs = stm.executeQuery();
		while (rs.next()) {
			nicknames.add(rs.getString(DataContract.LikesTable.COL_NICKNAME));
		}
		return nicknames;
	}

	@Override
	public ArrayList<Purchase> getAllPurchases() throws SQLException {
		PreparedStatement stm = DBUtils.conn.prepareStatement(SQLStatements.GET_PURCHASES_STMT);
		ArrayList<Purchase> purchases = new ArrayList<Purchase>();
		Purchase p = null;
		ResultSet rs = stm.executeQuery();
		while (rs.next()) {
			p = new Purchase(rs.getString(DataContract.PurchaseTable.COL_USERNAME),
					rs.getString(DataContract.PurchaseTable.COL_EBOOKTITLE),
					rs.getString(DataContract.PurchaseTable.COL_CREDITCARDNUMBER),
					rs.getString(DataContract.PurchaseTable.COL_EXPIRY),
					rs.getString(DataContract.PurchaseTable.COL_CVV),
					rs.getString(DataContract.PurchaseTable.COL_FULLNAME),
					rs.getString(DataContract.PurchaseTable.COL_CREDITCARDCOMPANY));
			Date date = new Date(rs.getTimestamp(DataContract.PurchaseTable.COL_TIME).getTime());
			String formatter = new SimpleDateFormat("yyyy-MM-dd").format(date);
			// SimpleDateFormat formatter = new
			// SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			p.setTime(formatter);
			purchases.add(p);
			System.out.println("time is " + p.getTime() + " " + date);
		}
		return purchases;
	}

	//// no servlet yet
	@Override
	public ArrayList<Purchase> getAllPurchasesByTitle(String title) throws SQLException {
		PreparedStatement stm = DBUtils.conn.prepareStatement(SQLStatements.GET_EBOOKPURCHASES_STMT);
		ArrayList<Purchase> purchases = new ArrayList<Purchase>();
		Purchase p = null;
		stm.setString(1, title);
		ResultSet rs = stm.executeQuery();
		while (rs.next()) {
			p = new Purchase(rs.getString(DataContract.PurchaseTable.COL_USERNAME),
					rs.getString(DataContract.PurchaseTable.COL_EBOOKTITLE),
					rs.getString(DataContract.PurchaseTable.COL_CREDITCARDNUMBER),
					rs.getString(DataContract.PurchaseTable.COL_EXPIRY),
					rs.getString(DataContract.PurchaseTable.COL_CVV),
					rs.getString(DataContract.PurchaseTable.COL_FULLNAME),
					rs.getString(DataContract.PurchaseTable.COL_CREDITCARDCOMPANY));
			purchases.add(p);
		}
		return purchases;
	}

	@Override
	public ArrayList<Purchase> getUserPurchases(String username) throws SQLException {
		PreparedStatement stm = DBUtils.conn.prepareStatement(SQLStatements.GET_userPurchases_STMT);
		stm.setString(1, username);
		ResultSet rs = stm.executeQuery();
		ArrayList<Purchase> purchases = new ArrayList<Purchase>();
		Purchase p = null;
		// ResultSet rs = stm.executeQuery();
		while (rs.next()) {
			p = new Purchase(rs.getString(DataContract.PurchaseTable.COL_USERNAME),
					rs.getString(DataContract.PurchaseTable.COL_EBOOKTITLE),
					rs.getString(DataContract.PurchaseTable.COL_CREDITCARDNUMBER),
					rs.getString(DataContract.PurchaseTable.COL_EXPIRY),
					rs.getString(DataContract.PurchaseTable.COL_CVV),
					rs.getString(DataContract.PurchaseTable.COL_FULLNAME),
					rs.getString(DataContract.PurchaseTable.COL_CREDITCARDCOMPANY));
			purchases.add(p);
		}
		return purchases;
	}

	@Override
	public boolean checkIfEbookPurchased(String username, String title) throws SQLException {
		PreparedStatement stm = DBUtils.conn.prepareStatement(SQLStatements.checkIfPurchased);
		stm.setString(1, username);
		stm.setString(2, title);
		ResultSet rs = stm.executeQuery();
		if (rs.next()) {
			return true;
		}
		return false;
	}

	@Override
	public ArrayList<EbookUser> getAllEbookUsers() throws SQLException {
		PreparedStatement stm = DBUtils.conn.prepareStatement(SQLStatements.GET_USERS_STMT);
		ArrayList<EbookUser> ebookUsers = new ArrayList<EbookUser>();
		EbookUser user = null;
		ResultSet rs = stm.executeQuery();
		while (rs.next()) {
			user = new EbookUser(rs.getString(DataContract.EbookUserTable.COL_USERNAME),
					rs.getString(DataContract.EbookUserTable.EMAIL),
					rs.getString(DataContract.EbookUserTable.COL_STREET),
					rs.getInt(DataContract.EbookUserTable.COL_APARTMENT),
					rs.getString(DataContract.EbookUserTable.COL_CITY),
					rs.getString(DataContract.EbookUserTable.COL_POSTALCODE),
					rs.getString(DataContract.EbookUserTable.COL_TELEPHONENUMBER),
					rs.getString(DataContract.EbookUserTable.COL_PASSWORD),
					rs.getString(DataContract.EbookUserTable.COL_NICKNAME),
					rs.getString(DataContract.EbookUserTable.COL_SHORTDESCRIPTION),
					rs.getString(DataContract.EbookUserTable.COL_PHOTO));
			ebookUsers.add(user);
		}
		DBUtils.conn.commit();
		stm.close();
		rs.close();

		return ebookUsers;
	}

	@Override
	public ArrayList<Review> getAllUnapprovedReviews() throws SQLException {
		PreparedStatement stm = DBUtils.conn.prepareStatement(SQLStatements.GET_unapprovedReviews_STMT);
		ArrayList<Review> reviews = new ArrayList<Review>();
		Review review = null;
		ResultSet rs = stm.executeQuery();
		while (rs.next()) {
			review = new Review(rs.getString(DataContract.ReviewsTable.COL_USERNAME),
					rs.getString(DataContract.ReviewsTable.COL_EBOOKTITLE),
					rs.getInt(DataContract.ReviewsTable.COL_APPROVED),
					rs.getString(DataContract.ReviewsTable.COL_NICKNAME),
					rs.getString(DataContract.ReviewsTable.COL_REVIEW));
			reviews.add(review);
		}
		return reviews;
	}

	@Override
	public ArrayList<Review> getSingleEbookReviews(String title) throws SQLException {
		PreparedStatement stm = DBUtils.conn.prepareStatement(SQLStatements.GET_ebookApprovedReviews_STMT);
		stm.setString(1, title);
		ResultSet rs = stm.executeQuery();
		DBUtils.conn.commit();
		System.out.println("is of book " + title);
		ArrayList<Review> reviews = new ArrayList<Review>();
		Review review = null;
		// ResultSet rs = stm.executeQuery();
		while (rs.next()) {
			review = new Review(rs.getString(DataContract.ReviewsTable.COL_USERNAME),
					rs.getString(DataContract.ReviewsTable.COL_EBOOKTITLE),
					rs.getInt(DataContract.ReviewsTable.COL_APPROVED),
					rs.getString(DataContract.ReviewsTable.COL_NICKNAME),
					rs.getString(DataContract.ReviewsTable.COL_REVIEW));
			reviews.add(review);
		}
		return reviews;
	}

	@Override
	public boolean addNewReview(Review review) throws SQLException {

		PreparedStatement stmt1 = DBUtils.conn.prepareStatement(SQLStatements.addNewReview);
		stmt1.setString(1, review.getTitle());
		stmt1.setString(2, review.getUsername());
		stmt1.setString(3, review.getNickname());
		stmt1.setString(4, review.getReview());
		stmt1.setInt(5, review.getApproved());
		stmt1.executeUpdate();
		DBUtils.conn.commit();
		stmt1.close();
		System.out.println("added to database");
		return true;
	}

	@Override
	public String getNicknameByUsername(String username) throws SQLException {
		PreparedStatement stmt = DBUtils.conn.prepareStatement(SQLStatements.getNicknameByUsername);
		stmt.setString(1, username);
		ResultSet rs = stmt.executeQuery();

		String nickname = null;
		if (rs.next()) {
			nickname = rs.getString(DataContract.EbookUserTable.COL_NICKNAME);
		}
		return nickname;

	}

	@Override
	public Collection<Ebook> getAllEbooks(InputStream is) throws SQLException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuilder jsonFileContent = new StringBuilder();
		String nextLine = null;
		while ((nextLine = br.readLine()) != null) {
			jsonFileContent.append(nextLine);
		}
		Gson gson = new Gson();
		Type type = new TypeToken<Collection<Ebook>>() {
		}.getType();
		Collection<Ebook> tempEbooks = gson.fromJson(jsonFileContent.toString(), type);

		// close
		br.close();
		return tempEbooks;

	}

	// @Override
	// public Collection<Integer> getLikesNumOrderedById() throws SQLException {
	// Collection<Integer> c = new Collection<Integer>() {
	//
	// @Override
	// public <T> T[] toArray(T[] a) {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// public Object[] toArray() {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// public int size() {
	// // TODO Auto-generated method stub
	// return 0;
	// }
	//
	// @Override
	// public boolean retainAll(Collection<?> c) {
	// // TODO Auto-generated method stub
	// return false;
	// }
	//
	// @Override
	// public boolean removeAll(Collection<?> c) {
	// // TODO Auto-generated method stub
	// return false;
	// }
	//
	// @Override
	// public boolean remove(Object o) {
	// // TODO Auto-generated method stub
	// return false;
	// }
	//
	// @Override
	// public Iterator<Integer> iterator() {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// public boolean isEmpty() {
	// // TODO Auto-generated method stub
	// return false;
	// }
	//
	// @Override
	// public boolean containsAll(Collection<?> c) {
	// // TODO Auto-generated method stub
	// return false;
	// }
	//
	// @Override
	// public boolean contains(Object o) {
	// // TODO Auto-generated method stub
	// return false;
	// }
	//
	// @Override
	// public void clear() {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public boolean addAll(Collection<? extends Integer> c) {
	// // TODO Auto-generated method stub
	// return false;
	// }
	//
	// @Override
	// public boolean add(Integer e) {
	// // TODO Auto-generated method stub
	// return false;
	// }
	// };
	// PreparedStatement stmt =
	// DBUtils.conn.prepareStatement(SQLStatements.getLikesOrderedById);
	// ResultSet rs = stmt.executeQuery();
	// while (rs.next()) {
	// c.add(rs.getInt(DataContract.EbookTable.COL_LIKESNUM));
	// }
	// return c;
	//
	// }

	@Override
	public boolean addNewPurchase(Purchase p) throws SQLException {
		PreparedStatement stmt = DBUtils.conn.prepareStatement(SQLStatements.addNewPurchase);
		stmt.setString(1, p.getUsername());
		stmt.setString(2, p.getTitle());
		stmt.setString(3, p.getCreditCardNumber());
		stmt.setString(4, p.getExpiry());
		stmt.setString(5, p.getCvv());
		stmt.setString(6, p.getFullName());
		stmt.setString(7, p.getCreditCardCompany());

		stmt.executeUpdate();
		DBUtils.conn.commit();
		stmt.close();
		System.out.println("added to database");
		return true;
	}

	@Override
	public boolean approveReview(Review review) throws SQLException {
		PreparedStatement stmt1 = DBUtils.conn.prepareStatement(SQLStatements.approveReview);
		stmt1.setString(1, review.getTitle());
		stmt1.setString(2, review.getUsername());
		stmt1.setString(3, review.getNickname());
		stmt1.setString(4, review.getReview());
		// stmt1.setInt(5, review.getApproved());
		stmt1.executeUpdate();
		DBUtils.conn.commit();
		stmt1.close();
		System.out.println("approved");
		return true;
	}

	@Override
	public boolean increaseNumOfEbookLikes(String title) throws SQLException {
		PreparedStatement stmt = DBUtils.conn.prepareStatement(SQLStatements.getLikesNumByTitle);
		stmt.setString(1, title);
		ResultSet rs = stmt.executeQuery();
		int likesNum = 0;
		if (rs.next()) {
			likesNum = rs.getInt(DataContract.EbookTable.COL_LIKESNUM);
		}
		likesNum = likesNum + 1;
		PreparedStatement stmt1 = DBUtils.conn.prepareStatement(SQLStatements.updateLikesNum);
		stmt1.setInt(1, likesNum);
		stmt1.setString(2, title);
		stmt1.executeUpdate();
		DBUtils.conn.commit();
		stmt1.close();
		System.out.println(" num of likes increased");
		return true;
	}

	@Override
	public boolean decreaseNumOfEbookLikes(String title) throws SQLException {
		PreparedStatement stmt = DBUtils.conn.prepareStatement(SQLStatements.getLikesNumByTitle);
		stmt.setString(1, title);
		ResultSet rs = stmt.executeQuery();
		int likesNum = 0;
		if (rs.next()) {
			likesNum = rs.getInt(DataContract.EbookTable.COL_LIKESNUM);
		}
		likesNum = likesNum - 1;
		PreparedStatement stmt1 = DBUtils.conn.prepareStatement(SQLStatements.updateLikesNum);
		stmt1.setInt(1, likesNum);
		stmt1.setString(2, title);
		stmt1.executeUpdate();
		DBUtils.conn.commit();
		stmt1.close();
		return true;
	}

	@Override
	public String validatePurchase(Purchase purchase) {
		if (purchase.getUsername() == null || purchase.getTitle() == null) {
			return null;
		}

		if (purchase.getFullName() == null || purchase.getFullName().isEmpty()) {
			return null;
		}

		if (purchase.getCreditCardCompany() == null) {
			return null;
		}
		if (!purchase.getCreditCardCompany().equals("amex") && !purchase.getCreditCardCompany().equals("visa")) {
			return null;
		}
		
		if((purchase.getCreditCardCompany().equals("amex") && !(purchase.getCreditCardNumber().length() == 15))){
			return "Credit card number should be of length 15";
		}
		if((purchase.getCreditCardCompany().equals("visa") && !(purchase.getCreditCardNumber().length() == 16))){
			return "Credit card number should be of length 16";
		}
		if((purchase.getCreditCardCompany().equals("amex") && !(purchase.getCreditCardNumber().startsWith("34")))){
			return "Card number number should start with 34";
		}
		if((purchase.getCreditCardCompany().equals("visa") && !(purchase.getCreditCardNumber().startsWith("4")))){
			return "Card number number should start with 4";
		}
		if((purchase.getCreditCardCompany().equals("amex") && !(purchase.getCvv().length() == 4))){
			return "CVV should be of length 4";
		}
		if((purchase.getCreditCardCompany().equals("visa") && !(purchase.getCvv().length() == 3))){
			return "CVV should be of length 3";
		}

		if (purchase.getExpiry() == null || purchase.getExpiry().isEmpty()) {
			return null;
		}
		if (!purchase.getExpiry().matches("\\d{2}-\\d{2}")) {
			return "Format of data in the expiry field is not correct";
		}

		return "true";

	}

	@Override
	public boolean checkEbookLikedByUser(String username, String title) throws SQLException {
		PreparedStatement stm = DBUtils.conn.prepareStatement(SQLStatements.checkIfLiked);
		stm.setString(1, username);
		stm.setString(2, title);
		ResultSet rs = stm.executeQuery();
		if (rs.next()) {
			return true;
		}
		return false;
	}
	

	@Override
	public ArrayList<EbookUser> getUserBynickname(String nickname) throws SQLException {
		PreparedStatement stm = DBUtils.conn.prepareStatement(SQLStatements.Get_UserByNickname);
		stm.setString(1, nickname);
		ResultSet rs = stm.executeQuery();
		DBUtils.conn.commit();
		System.out.println("user: " + nickname);
		ArrayList<EbookUser> User = new ArrayList<EbookUser>();
		EbookUser user = null;
		// ResultSet rs = stm.executeQuery();
		while (rs.next()) {
			user = new EbookUser(rs.getString(DataContract.EbookUserTable.COL_USERNAME),
					rs.getString(DataContract.EbookUserTable.EMAIL),
					rs.getString(DataContract.EbookUserTable.COL_STREET),
					rs.getInt(DataContract.EbookUserTable.COL_APARTMENT),
					rs.getString(DataContract.EbookUserTable.COL_CITY),
					rs.getString(DataContract.EbookUserTable.COL_POSTALCODE),
					rs.getString(DataContract.EbookUserTable.COL_TELEPHONENUMBER),
					rs.getString(DataContract.EbookUserTable.COL_PASSWORD),
					rs.getString(DataContract.EbookUserTable.COL_NICKNAME),
					rs.getString(DataContract.EbookUserTable.COL_SHORTDESCRIPTION),
					rs.getString(DataContract.EbookUserTable.COL_PHOTO));
			User.add(user);
		}
		return User;
	}

	@Override
	public boolean ignoreReview(Review review) throws SQLException {
		PreparedStatement stmt1 = DBUtils.conn.prepareStatement(SQLStatements.ignoreReview);
		stmt1.setString(1, review.getTitle());
		stmt1.setString(2, review.getUsername());
		stmt1.setString(3, review.getNickname());
		stmt1.setString(4, review.getReview());
		stmt1.executeUpdate();
		DBUtils.conn.commit();
		stmt1.close();
		System.out.println("ignored");
		return true;
	}

	@Override
	public boolean updateScrollPosition(float pos, String username, String title) throws SQLException {
		PreparedStatement stmt1 = DBUtils.conn.prepareStatement(SQLStatements.updateScrollPosition);
		stmt1.setFloat(1, pos);
		stmt1.setString(2, title);
		stmt1.setString(3, username);
		stmt1.executeUpdate();
		DBUtils.conn.commit();
		stmt1.close();
		System.out.println("updated");
		return true;
	}

	@Override
	public float getScrollPosition(String username, String title) throws SQLException {
		float pos = 0;
		PreparedStatement stmt1 = DBUtils.conn.prepareStatement(SQLStatements.getScrollPosition);
		stmt1.setString(1, title);
		stmt1.setString(2, username);
		ResultSet rs = stmt1.executeQuery();
		if (rs.next()) {
			pos = rs.getFloat("scrollPos");
		}
		DBUtils.conn.commit();
		stmt1.close();
		System.out.println("updated");
		return pos;
	}

	@Override
	public boolean validateSignUp(EbookUser user) {
		if (user.getUsername().isEmpty() || user.getUsername().length() > 10) {
			return false;
		}
		if (user.getNickname().isEmpty() || user.getNickname().length() > 20) {
			
			return false;
		}
		if (user.getPassword().isEmpty() || user.getPassword().length() > 8) {
			return false;
		}

		if ((user.getCity().length() <= 3)) {
			return false;
		}
		if (user.getStreet().length() <= 3) {
			return false;
		}
		if (user.getPostalCode() == null || user.getPostalCode().isEmpty()
				|| (!user.getPostalCode().matches("^\\d{7}"))) {
			return false;
		}
		if (user.getApartment() <= 0) {
			return false;
		}
		if(!(user.getTelephoneNumber().startsWith("05")||user.getTelephoneNumber().startsWith("03")
			||user.getTelephoneNumber().startsWith("04")||user.getTelephoneNumber().startsWith("02")
			||user.getTelephoneNumber().startsWith("09")||user.getTelephoneNumber().startsWith("09"))) {
			return false;
		}
		
		
		if((user.getTelephoneNumber().startsWith("05") && !(user.getTelephoneNumber().matches("^\\d{10}")))
		||((user.getTelephoneNumber().startsWith("02")||user.getTelephoneNumber().startsWith("03")
		||user.getTelephoneNumber().startsWith("04") ||user.getTelephoneNumber().startsWith("08") 
		||user.getTelephoneNumber().startsWith("09"))&&(!(user.getTelephoneNumber().matches("^\\d{9}"))))) {
			
			return false;
		}
		
		if (user.getEmail() == null || user.getEmail().isEmpty()) {
			
			return false;
		}
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		Pattern p = java.util.regex.Pattern.compile(ePattern);
		Matcher m = p.matcher(user.getEmail());
		if (!m.matches()) {
			return false;
		}

		return true;

	}
	
	@Override
	public void decreaselikesNumByusername(String username) throws SQLException {
		PreparedStatement stm = DBUtils.conn.prepareStatement(SQLStatements.GET_EBOOKS_STMT);
		ResultSet rs = stm.executeQuery();
		while (rs.next()) {
			decreaseNumOfEbookLikes(rs.getString("title"));
		}
		rs.close();
	}

}
