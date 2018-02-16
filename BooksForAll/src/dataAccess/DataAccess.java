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
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

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

public class DataAccess implements DataInterface {

	public static Connection conn;

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
			System.out.println("rs.next(): " + rs.getString(3));
			System.out.println("BAD, USER ALREADY EXISTS");
			return -1;
		}
		PreparedStatement stmt2 = DBUtils.conn.prepareStatement(SQLStatements.addNewUser);
		stmt2.setString(1, user.getUsername());
		stmt2.setString(2, user.getEmail());
		stmt2.setString(3, user.getStreet());
		stmt2.setInt(4, user.getApartment());
		stmt2.setString(5, user.getCity());
		stmt2.setString(6, user.getPostalCode());
		stmt2.setString(4, user.getTelephoneNumber());
		stmt2.setString(5, user.getPassword());
		stmt2.setString(6, user.getNickname());
		stmt2.setString(7, user.getShortDescription());
		stmt2.setString(8, user.getPhoto());
		stmt2.executeUpdate();

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
		System.out.println("ebook user deleted successfully");
		return true;
	}

	@Override
	public boolean likeEbook(int idOfEbook, String username) throws SQLException {
		PreparedStatement stmt = DBUtils.conn.prepareStatement(SQLStatements.getNicknameByUsername);
		stmt.setString(1, username);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			PreparedStatement stmt1 = DBUtils.conn.prepareStatement(SQLStatements.addNewLike);
			stmt1.setString(1, username);
			stmt1.setInt(2, idOfEbook);
			stmt1.setString(3, rs.getString(DataContract.LikesTable.COL_NICKNAME));
			stmt1.executeUpdate();
			System.out.println("added to database");
			return true;
		}
		return false;
	}

	@Override
	public boolean unlikeEbook(int idOfEbook, String username) throws SQLException {
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
		stm1.setInt(2, idOfEbook);
		stm1.executeUpdate();
		System.out.println("unlike done successfully");
		return true;
	}

	@Override
	public int numOfEbookLikes(int idOfEbook) throws SQLException {

		int count = 0;
		PreparedStatement stmt = DBUtils.conn.prepareStatement(SQLStatements.GET_ebookLikes_STMT);
		stmt.setInt(1, idOfEbook);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			count++;
		}
		return count;
	}

	@Override
	public ArrayList<String> getUsersThatLikedEbook(String nameOfEbook) throws SQLException {

		PreparedStatement stm = DBUtils.conn.prepareStatement(SQLStatements.GET_ebookLikes_STMT);
		stm.setString(1, nameOfEbook);
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
					rs.getInt(DataContract.PurchaseTable.COL_EBOOKID),
					rs.getString(DataContract.PurchaseTable.COL_CREDITCARDNUMBER),
					rs.getString(DataContract.PurchaseTable.COL_EXPIRY),
					rs.getString(DataContract.PurchaseTable.COL_CVV),
					rs.getString(DataContract.PurchaseTable.COL_FULLNAME),
					rs.getString(DataContract.PurchaseTable.COL_CREDITCARDCOMPANY));
			purchases.add(p);
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
					rs.getInt(DataContract.PurchaseTable.COL_EBOOKID),
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
					rs.getInt(DataContract.PurchaseTable.COL_EBOOKID),
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
	public boolean checkIfEbookPurchased(String username, int id) throws SQLException {
		PreparedStatement stm = DBUtils.conn.prepareStatement(SQLStatements.checkIfPurchased);
		stm.setString(1, username);
		stm.setInt(2, id);
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
					rs.getInt(DataContract.ReviewsTable.COL_EBOOKID), rs.getInt(DataContract.ReviewsTable.COL_APPROVED),
					rs.getString(DataContract.ReviewsTable.COL_NICKNAME),
					rs.getString(DataContract.ReviewsTable.COL_REVIEW));
			reviews.add(review);
		}
		return reviews;
	}

	@Override
	public ArrayList<Review> getSingleEbookReviews(int idOfEbook) throws SQLException {
		PreparedStatement stm = DBUtils.conn.prepareStatement(SQLStatements.GET_ebookApprovedReviews_STMT);
		stm.setInt(1, idOfEbook);
		ResultSet rs = stm.executeQuery();
		DBUtils.conn.commit();
		System.out.println("is of book " + idOfEbook);
		ArrayList<Review> reviews = new ArrayList<Review>();
		Review review = null;
		// ResultSet rs = stm.executeQuery();
		while (rs.next()) {
			review = new Review(rs.getString(DataContract.ReviewsTable.COL_USERNAME),
					rs.getInt(DataContract.ReviewsTable.COL_EBOOKID), rs.getInt(DataContract.ReviewsTable.COL_APPROVED),
					rs.getString(DataContract.ReviewsTable.COL_NICKNAME),
					rs.getString(DataContract.ReviewsTable.COL_REVIEW));
			reviews.add(review);
		}
		return reviews;
	}

	@Override
	public boolean addNewReview(Review review) throws SQLException {

		PreparedStatement stmt1 = DBUtils.conn.prepareStatement(SQLStatements.addNewReview);
		stmt1.setString(1, review.getUsername());
		stmt1.setInt(2, review.getId());
		stmt1.setString(3, review.getNickname());
		stmt1.setString(4, review.getReview());
		stmt1.setInt(5, review.getApproved());
		stmt1.executeUpdate();
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

		// Collection<Ebook> ebooks =tempEbooks;
		// Iterator<Ebook> iter = tempEbooks.iterator();
		// while (iter.hasNext()) {
		// System.out.println("iteeeer");
		// Ebook ebook = iter.next();
		// PreparedStatement stmt =
		// DBUtils.conn.prepareStatement(SQLStatements.getLikesNumByTitle);
		// stmt.setInt(1, ebook.getId());
		// ResultSet rs = stmt.executeQuery();
		// //
		// System.out.println(iter.next().getId()+iter.next().getImage()+iter.next().getLocation());
		// if (rs.next()) {
		// System.out.println("rs.next");
		// ebook.setLikesNum(rs.getInt(DataContract.EbookTable.COL_LIKESNUM));
		// ebooks.add(ebook);
		// }
		// iter.remove();
		//
		// }
		// close
		br.close();
		return tempEbooks;

	}

//	@Override
//	public Collection<Integer> getLikesNumOrderedById() throws SQLException {
//		Collection<Integer> c = new Collection<Integer>() {
//
//			@Override
//			public <T> T[] toArray(T[] a) {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override
//			public Object[] toArray() {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override
//			public int size() {
//				// TODO Auto-generated method stub
//				return 0;
//			}
//
//			@Override
//			public boolean retainAll(Collection<?> c) {
//				// TODO Auto-generated method stub
//				return false;
//			}
//
//			@Override
//			public boolean removeAll(Collection<?> c) {
//				// TODO Auto-generated method stub
//				return false;
//			}
//
//			@Override
//			public boolean remove(Object o) {
//				// TODO Auto-generated method stub
//				return false;
//			}
//
//			@Override
//			public Iterator<Integer> iterator() {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override
//			public boolean isEmpty() {
//				// TODO Auto-generated method stub
//				return false;
//			}
//
//			@Override
//			public boolean containsAll(Collection<?> c) {
//				// TODO Auto-generated method stub
//				return false;
//			}
//
//			@Override
//			public boolean contains(Object o) {
//				// TODO Auto-generated method stub
//				return false;
//			}
//
//			@Override
//			public void clear() {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public boolean addAll(Collection<? extends Integer> c) {
//				// TODO Auto-generated method stub
//				return false;
//			}
//
//			@Override
//			public boolean add(Integer e) {
//				// TODO Auto-generated method stub
//				return false;
//			}
//		};
//		PreparedStatement stmt = DBUtils.conn.prepareStatement(SQLStatements.getLikesOrderedById);
//		ResultSet rs = stmt.executeQuery();
//		while (rs.next()) {
//			c.add(rs.getInt(DataContract.EbookTable.COL_LIKESNUM));
//		}
//		return c;
//
//	}

	@Override
	public boolean addNewPurchase(Purchase p) throws SQLException {
		PreparedStatement stmt = DBUtils.conn.prepareStatement(SQLStatements.addNewPurchase);
		stmt.setString(1, p.getUsername());
		stmt.setInt(2, p.getId());
		stmt.setString(3, p.getCreditCardNumber());
		stmt.setString(4, p.getExpiry());
		stmt.setString(5, p.getCvv());
		stmt.setString(6, p.getFullName());
		stmt.setString(7, p.getCreditCardCompany());

		stmt.executeUpdate();
		System.out.println("added to database");
		return true;
	}

	@Override
	public boolean approveReview(Review review) throws SQLException {
		PreparedStatement stmt1 = DBUtils.conn.prepareStatement(SQLStatements.approveReview);
		stmt1.setInt(1, review.getId());
		stmt1.setString(2, review.getUsername());
		stmt1.setString(3, review.getNickname());
		stmt1.setString(4, review.getReview());
		// stmt1.setInt(5, review.getApproved());
		stmt1.executeUpdate();
		System.out.println("approved");
		return true;
	}

	@Override
	public boolean increaseNumOfEbookLikes(int idOfEbook) throws SQLException {
		PreparedStatement stmt = DBUtils.conn.prepareStatement(SQLStatements.getLikesNumByTitle);
		stmt.setInt(1, idOfEbook);
		ResultSet rs = stmt.executeQuery();
		int likesNum = 0;
		if (rs.next()) {
			likesNum = rs.getInt(DataContract.EbookTable.COL_LIKESNUM);
		}
		likesNum = likesNum + 1;
		PreparedStatement stmt1 = DBUtils.conn.prepareStatement(SQLStatements.updateLikesNum);
		stmt1.setInt(1, likesNum);
		stmt1.setInt(2, idOfEbook);
		stmt1.executeUpdate();
		System.out.println(" num of likes increased");
		return true;
	}

	@Override
	public boolean decreaseNumOfEbookLikes(int idOfEbook) throws SQLException {
		PreparedStatement stmt = DBUtils.conn.prepareStatement(SQLStatements.getLikesNumByTitle);
		stmt.setInt(1, idOfEbook);
		ResultSet rs = stmt.executeQuery();
		int likesNum = 0;
		if (rs.next()) {
			likesNum = rs.getInt(DataContract.EbookTable.COL_LIKESNUM);
		}
		likesNum = likesNum - 1;
		PreparedStatement stmt1 = DBUtils.conn.prepareStatement(SQLStatements.updateLikesNum);
		stmt1.setInt(1, likesNum);
		stmt1.setInt(2, idOfEbook);
		stmt1.executeUpdate();
		System.out.println(" num of likes decreased");
		return true;
	}

}
