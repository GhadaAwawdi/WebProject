package dataAccess;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import model.Ebook;
import model.EbookUser;
import model.Manager;
import model.Purchase;
import model.Review;

public interface DataInterface {

	EbookUser ebookUserLogin(String username, String password) throws SQLException;
	int addEbookUser(EbookUser user) throws SQLException;
//	boolean addEbook(Ebook ebook) throws SQLException;
	boolean removeEbookUser(String username) throws SQLException;
	Manager managerLogin(String username, String password) throws SQLException;
	/**
	 * 
	 * @param nameOfEbook
	 * @return number of users that liked the ebook 
	 * @throws SQLException
	 */
	ArrayList<Purchase> getAllPurchases() throws SQLException;
	ArrayList<EbookUser> getAllEbookUsers() throws SQLException;
	ArrayList<Review> getAllUnapprovedReviews() throws SQLException;
	ArrayList<Purchase> getUserPurchases(String username) throws SQLException;
	boolean addNewReview(Review review) throws SQLException;
	boolean addNewPurchase(Purchase p) throws SQLException;
	String getNicknameByUsername(String username)  throws SQLException;
	boolean approveReview(Review review) throws SQLException;
	ArrayList<Purchase> getAllPurchasesByTitle(String title) throws SQLException;
	Collection<Ebook> getAllEbooks(InputStream is) throws SQLException, IOException;
	void closeConnection() throws SQLException;
	//Collection<Integer> getLikesNumOrderedById() throws SQLException;
	boolean unlikeEbook(String title, String username) throws SQLException;
	boolean decreaseNumOfEbookLikes(String title) throws SQLException;
	int numOfEbookLikes(String title) throws SQLException;
	boolean likeEbook(String title, String username) throws SQLException;
	boolean increaseNumOfEbookLikes(String title) throws SQLException;
	boolean checkIfEbookPurchased(String username, String title) throws SQLException;
	ArrayList<Review> getSingleEbookReviews(String title) throws SQLException;
	ArrayList<String> getUsersThatLikedEbook(String title) throws SQLException;


}
