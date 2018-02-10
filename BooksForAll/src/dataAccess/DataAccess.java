package dataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingException;

import constants.SQLStatements;
import utils.DBUtils;
import model.Ebook;
import model.EbookUser;
import model.Manager;
import model.Purchase;
import model.Review;

public class DataAccess implements DataInterface {

	private Connection c;

	public DataAccess() throws NamingException, SQLException {

		Logger logger = Logger.getLogger(DataAccess.class.getName());
		logger.log(Level.INFO, "DataAccess c'tor: attempting connection...");
		c = DBUtils.getConnection();
		if (c == null) {
			logger.log(Level.SEVERE, "Connection Failed");
		} else {
			logger.log(Level.INFO, "Connection Established");
		}
	}

	@Override
	public EbookUser ebookUserLogin(String username, String password) throws SQLException {

		System.out.println("ebookUserLogin function");

		Logger logger = Logger.getLogger(DataAccess.class.getName());
		if (c == null) {
			logger.log(Level.SEVERE, "Connection Failed");
			return null;
		}

		PreparedStatement stmt = c.prepareStatement(SQLStatements.getUserByUsernameAndPassword);
		stmt.setString(1, username);
		stmt.setString(2, password);

		ResultSet rs = stmt.executeQuery();
		EbookUser user = null;

		if (rs.next()) {
			logger.log(Level.INFO, "User type Mentee");
			user = new EbookUser(rs.getString(DataContract.EbookUserTable.COL_USERNAME),
					rs.getString(DataContract.EbookUserTable.EMAIL),
					rs.getString(DataContract.EbookUserTable.COL_ADDRESS),
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
		if (c == null) {
			logger.log(Level.SEVERE, "Connection Failed");
			return null;
		}

		PreparedStatement stmt = c.prepareStatement(SQLStatements.getAdminUsernameAndPassword);
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
		// PreparedStatement stmt =
		// c.prepareStatement(SQLStatements.selectUserByUsername);
		// stmt.setString(1, user.getUsername());
		// ResultSet rs = stmt.executeQuery();
		// if (rs.next()) // user exists
		// {
		// System.out.println("rs.next(): " + rs.getString(3));
		// System.out.println("BAD, USER ALREADY EXISTS");
		// return -1;
		// }
		// PreparedStatement stmt2 =
		// c.prepareStatement(SQLStatements.addNewUser);
		// stmt2.setString(1, user.getUsername());
		// stmt2.setString(2, user.getEmail());
		// stmt2.setString(3, user.getAddress());
		// stmt2.setString(4, user.getTelephoneNumber());
		// stmt2.setString(5, user.getPassword());
		// stmt2.setString(6, user.getNickname());
		// stmt2.setString(7, user.getShortDescription());
		// stmt2.setString(8, user.getPhoto());
		// stmt2.executeUpdate();
		//
		// stmt = c.prepareStatement(SQLStatements.selectUserByEmail);
		// stmt.setString(1, user.getEmail());
		// rs = stmt.executeQuery();
		// int id = 0;
		// if (rs.next()) // user exists
		// {
		// id = rs.getInt(1);
		// } else {
		// return -1;
		// }
		//
		// if (user.getType() == userType.TSOFEN || user.getType() ==
		// userType.ADMIN)
		// return id;
		//
		// if (user.getType() == userType.MENTOR) {
		// System.out.println("MENTOR DATABASE");
		// PreparedStatement stm3 =
		// c.prepareStatement(SQLStatements.insertMentor);
		// stm3.setInt(1, id);
		// stm3.setString(2, ((Mentor) user).getExperience());
		// stm3.setString(3, ((Mentor) user).getRole());
		// stm3.setInt(4, ((Mentor) user).getCompany());
		// stm3.setString(5, ((Mentor) user).getVolunteering());
		// stm3.setString(6, ((Mentor) user).getWorkHistory());
		//
		// stm3.executeUpdate();
		// return id;
		// }
		//
		// if (user.getType() == userType.MENTEE) {
		// PreparedStatement stm4 =
		// c.prepareStatement(SQLStatements.insertMentee);
		// stm4.setInt(1, id);
		// stm4.setFloat(2, ((Mentee) user).getRemainingSemesters());
		// stm4.setString(3, ((Mentee) user).getGraduationStatus());
		// stm4.setInt(4, ((Mentee) user).getAcademiclnstitution());
		// stm4.setFloat(5, ((Mentee) user).getAverage());
		// stm4.setString(6, ((Mentee) user).getAcademicDicipline());
		// stm4.setString(7, ((Mentee) user).getAcademicDicipline2());
		// stm4.setInt(8, ((Mentee) user).getSignedEULA() ? 1 : 0);
		// stm4.setString(9, ((Mentee) user).getResume());
		// stm4.setString(10, ((Mentee) user).getGradeSheet());
		// stm4.executeUpdate();
		// return id;
		// }
		return -1;
	}

	@Override
	public boolean addEbook(Ebook ebook) throws SQLException {
		PreparedStatement stm = c.prepareStatement(SQLStatements.selectEbookByName);
		stm.setString(1, ebook.getName());
		ResultSet rs = stm.executeQuery();
		if (rs.next()) // parkingLot exists
		{
			System.out.println("rs.next(): " + rs.getString(2));
			System.out.println("BAD, Ebook ALREADY EXISTS");
			return false;
		}

		else {
			PreparedStatement stmt1 = c.prepareStatement(SQLStatements.addNewEbook);
			stmt1.setString(1, ebook.getName());
			stmt1.setInt(2, ebook.getPrice());
			stmt1.setInt(3, ebook.getLikesNum());
			stmt1.setString(4, ebook.getImage());
			stmt1.setString(5, ebook.getShortDescription());
			stmt1.executeUpdate();
			System.out.println("added to database");
		}
		return true;
	}

	// @Override
	// public boolean unlikeEbook(String nameOfEbook) throws SQLException {
	// PreparedStatement stm = c.prepareStatement(SQLStatements.GET_Ebook_STMT);
	// stm.setString(1, nameOfEbook);
	// ResultSet rs = stm.executeQuery();
	// if (!rs.next()) {
	// System.out.println("Parking lot does not exist,try a different parking
	// lot");
	// return false;
	// }
	// PreparedStatement stm1 =
	// c.prepareStatement(SQLStatements.deleteParkingLot);
	// stm1.setString(1, nameOfEbook);
	// stm1.executeUpdate();
	// System.out.println("Parking lot deleted successfully");
	// return true;
	// }

	@Override
	public boolean removeEbookUser(String username) throws SQLException {
		PreparedStatement stm = c.prepareStatement(SQLStatements.selectUserByUsername);
		stm.setString(1, username);
		ResultSet rs = stm.executeQuery();
		if (!rs.next()) {
			System.out.println("user does not exist,try a different user");
			return false;
		}
		PreparedStatement stm1 = c.prepareStatement(SQLStatements.deleteUser);
		stm1.setString(1, username);
		stm1.executeUpdate();
		System.out.println("ebook user deleted successfully");
		return true;
	}

	@Override
	public boolean likeEbook(String nameOfEbook, String username) throws SQLException {
		PreparedStatement stmt = c.prepareStatement(SQLStatements.getNicknameByUsername);
		stmt.setString(1, username);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			PreparedStatement stmt1 = c.prepareStatement(SQLStatements.addNewLike);
			stmt1.setString(1, username);
			stmt1.setString(2, nameOfEbook);
			stmt1.setString(3, rs.getString(DataContract.LikesTable.COL_NICKNAME));
			stmt1.executeUpdate();
			System.out.println("added to database");
			return true;
		}
		return false;
	}

	@Override
	public boolean unlikeEbook(String nameOfEbook, String username) throws SQLException {
		// PreparedStatement stm =
		// c.prepareStatement(SQLStatements.unlikeEbook);
		// stm.setString(1, username);
		// stm.setString(2, nameOfEbook);
		// ResultSet rs = stm.executeQuery();
		// if (!rs.next()) {
		// //System.out.println("like does not exist,try a different user");
		// return false;
		// }
		PreparedStatement stm1 = c.prepareStatement(SQLStatements.unlikeEbook);
		stm1.setString(1, username);
		stm1.setString(2, nameOfEbook);
		stm1.executeUpdate();
		System.out.println("unlike done successfully");
		return true;
	}

	@Override
	public int numOfEbookLikes(String nameOfEbook) throws SQLException {

		int count = 0;
		PreparedStatement stmt = c.prepareStatement(SQLStatements.GET_ebookLikes_STMT);
		stmt.setString(1, nameOfEbook);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			count++;
		}
		return count;
	}

	@Override
	public ArrayList<String> getUsersThatLikedEbook(String nameOfEbook) throws SQLException {

		PreparedStatement stm = c.prepareStatement(SQLStatements.GET_ebookLikes_STMT);
		ArrayList<String> nicknames = new ArrayList<String>();
		ResultSet rs = stm.executeQuery();
		while (rs.next()) {
			nicknames.add(rs.getString(DataContract.LikesTable.COL_NICKNAME));
		}
		return nicknames;
	}

	@Override
	public ArrayList<Purchase> getAllPurchases() throws SQLException {
		PreparedStatement stm = c.prepareStatement(SQLStatements.GET_PURCHASES_STMT);
		ArrayList<Purchase> purchases = new ArrayList<Purchase>();
		Purchase p = null;
		ResultSet rs = stm.executeQuery();
		while (rs.next()) {
			p = new Purchase(rs.getString(DataContract.PurchaseTable.COL_USERNAME),
					rs.getString(DataContract.PurchaseTable.COL_TITLE),
					rs.getString(DataContract.PurchaseTable.COL_CREDITCARDNUMBER),
					rs.getString(DataContract.PurchaseTable.COL_EXPIRY),
					rs.getString(DataContract.PurchaseTable.COL_CVV),
					rs.getString(DataContract.PurchaseTable.COL_FULLNAME),
					rs.getString(DataContract.PurchaseTable.COL_CREDITCARDCOMPANY));
			purchases.add(p);
		}
		return purchases;
	}
////no servlet yet
	@Override
	public ArrayList<Purchase> getAllPurchasesByTitle(String title) throws SQLException {
		PreparedStatement stm = c.prepareStatement(SQLStatements.GET_EBOOKPURCHASES_STMT);
		ArrayList<Purchase> purchases = new ArrayList<Purchase>();
		Purchase p = null;
		stm.setString(1, title);
		ResultSet rs = stm.executeQuery();
		while (rs.next()) {
			p = new Purchase(rs.getString(DataContract.PurchaseTable.COL_USERNAME),
					rs.getString(DataContract.PurchaseTable.COL_TITLE),
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
		PreparedStatement stm = c.prepareStatement(SQLStatements.GET_userPurchases_STMT);
		stm.setString(1, username);
		ResultSet rs = stm.executeQuery();
		ArrayList<Purchase> purchases = new ArrayList<Purchase>();
		Purchase p = null;
		// ResultSet rs = stm.executeQuery();
		while (rs.next()) {
			p = new Purchase(rs.getString(DataContract.PurchaseTable.COL_USERNAME),
					rs.getString(DataContract.PurchaseTable.COL_TITLE),
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
		PreparedStatement stm = c.prepareStatement(SQLStatements.checkIfPurchased);
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
		PreparedStatement stm = c.prepareStatement(SQLStatements.GET_USERS_STMT);
		ArrayList<EbookUser> ebookUsers = new ArrayList<EbookUser>();
		EbookUser user = null;
		ResultSet rs = stm.executeQuery();
		while (rs.next()) {
			user = new EbookUser(rs.getString(DataContract.EbookUserTable.COL_USERNAME),
					rs.getString(DataContract.EbookUserTable.EMAIL),
					rs.getString(DataContract.EbookUserTable.COL_ADDRESS),
					rs.getString(DataContract.EbookUserTable.COL_TELEPHONENUMBER),
					rs.getString(DataContract.EbookUserTable.COL_PASSWORD),
					rs.getString(DataContract.EbookUserTable.COL_NICKNAME),
					rs.getString(DataContract.EbookUserTable.COL_SHORTDESCRIPTION),
					rs.getString(DataContract.EbookUserTable.COL_PHOTO));
			ebookUsers.add(user);
		}
		return ebookUsers;
	}

	@Override
	public ArrayList<Review> getAllUnapprovedReviews() throws SQLException {
		PreparedStatement stm = c.prepareStatement(SQLStatements.GET_unapprovedReviews_STMT);
		ArrayList<Review> reviews = new ArrayList<Review>();
		Review review = null;
		ResultSet rs = stm.executeQuery();
		while (rs.next()) {
			review = new Review(rs.getString(DataContract.ReviewsTable.COL_USERNAME),
					rs.getString(DataContract.ReviewsTable.COL_TITLE),
					rs.getInt(DataContract.ReviewsTable.COL_APPROVED),
					rs.getString(DataContract.ReviewsTable.COL_NICKNAME),
					rs.getString(DataContract.ReviewsTable.COL_REVIEW));
			reviews.add(review);
		}
		return reviews;
	}

	@Override
	public ArrayList<Review> getSingleEbookReviews(String nameOfEbook) throws SQLException {
		PreparedStatement stm = c.prepareStatement(SQLStatements.GET_ebookApprovedReviews_STMT);
		stm.setString(1, nameOfEbook);
		ResultSet rs = stm.executeQuery();
		ArrayList<Review> reviews = new ArrayList<Review>();
		Review review = null;
		// ResultSet rs = stm.executeQuery();
		while (rs.next()) {
			review = new Review(rs.getString(DataContract.ReviewsTable.COL_USERNAME),
					rs.getString(DataContract.ReviewsTable.COL_TITLE),
					rs.getInt(DataContract.ReviewsTable.COL_APPROVED),
					rs.getString(DataContract.ReviewsTable.COL_NICKNAME),
					rs.getString(DataContract.ReviewsTable.COL_REVIEW));
			reviews.add(review);
		}
		return reviews;
	}

	@Override
	public boolean addNewReview(Review review) throws SQLException {

		PreparedStatement stmt1 = c.prepareStatement(SQLStatements.addNewReview);
		stmt1.setString(1, review.getUsername());
		stmt1.setString(2, review.getTitle());
		stmt1.setString(3, review.getNickname());
		stmt1.setString(4, review.getReview());
		stmt1.setInt(5, review.getApproved());
		stmt1.executeUpdate();
		System.out.println("added to database");
		return true;
	}

	@Override
	public String getNicknameByUsername(String username) throws SQLException {
		PreparedStatement stmt = c.prepareStatement(SQLStatements.getNicknameByUsername);
		stmt.setString(1, username);
		ResultSet rs = stmt.executeQuery();

		String nickname = null;
		if (rs.next()) {
			nickname = rs.getString(DataContract.EbookUserTable.COL_NICKNAME);
		}
		return nickname;

	}

	@Override
	public ArrayList<Ebook> getAllEbooks() throws SQLException {
		PreparedStatement stm = c.prepareStatement(SQLStatements.GET_EBOOKS_STMT);
		ArrayList<Ebook> ebooks = new ArrayList<Ebook>();
		Ebook ebook = null;
		ResultSet rs = stm.executeQuery();
		while (rs.next()) {
			ebook = new Ebook(rs.getString(DataContract.EbookTable.COL_NAME),
					rs.getInt(DataContract.EbookTable.COL_PRICE),
					rs.getInt(DataContract.EbookTable.COL_LIKESNUM),
					rs.getString(DataContract.EbookTable.COL_IMAGE),
					rs.getString(DataContract.EbookTable.COL_SHORTDESCRIPTION));
			ebooks.add(ebook);
		}
		return ebooks;

	}

	@Override
	public boolean addNewPurchase(Purchase p) throws SQLException {
		PreparedStatement stmt = c.prepareStatement(SQLStatements.addNewPurchase);
		stmt.setString(1, p.getUsername());
		stmt.setString(2, p.getTitle());
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
		PreparedStatement stmt1 = c.prepareStatement(SQLStatements.approveReview);
		stmt1.setString(1, review.getTitle());
		stmt1.setString(2, review.getUsername());
		stmt1.setString(3, review.getNickname());
		stmt1.setString(4, review.getReview());
		// stmt1.setInt(5, review.getApproved());
		stmt1.executeUpdate();
		System.out.println("approved");
		return true;
	}

	@Override
	public boolean increaseNumOfEbookLikes(String nameOfEbook) throws SQLException {
		PreparedStatement stmt = c.prepareStatement(SQLStatements.getLikesNumByTitle);
		stmt.setString(1, nameOfEbook);
		ResultSet rs = stmt.executeQuery();
		int likesNum = 0;
		if (rs.next()) {
			likesNum = rs.getInt(DataContract.EbookTable.COL_LIKESNUM);
		}
		likesNum = likesNum + 1;
		PreparedStatement stmt1 = c.prepareStatement(SQLStatements.updateLikesNum);
		stmt1.setInt(1, likesNum);
		stmt1.setString(2, nameOfEbook);
		stmt1.executeUpdate();
		System.out.println(" num of likes increased");
		return true;
	}

	@Override
	public boolean decreaseNumOfEbookLikes(String nameOfEbook) throws SQLException {
		PreparedStatement stmt = c.prepareStatement(SQLStatements.getLikesNumByTitle);
		stmt.setString(1, nameOfEbook);
		ResultSet rs = stmt.executeQuery();
		int likesNum = 0;
		if (rs.next()) {
			likesNum = rs.getInt(DataContract.EbookTable.COL_LIKESNUM);
		}
		likesNum = likesNum - 1;
		PreparedStatement stmt1 = c.prepareStatement(SQLStatements.updateLikesNum);
		stmt1.setInt(1, likesNum);
		stmt1.setString(2, nameOfEbook);
		stmt1.executeUpdate();
		System.out.println(" num of likes decreased");
		return true;
	}

}
