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

	/**
	 * 
	 * @param username 
	 * @param password , the submitted password by the user  
	 * @return user's info 
	 * @throws SQLException
	 * 
	 * this function is called whenever a regular user tries to login,
	 * authenticates user's provided info, if user exists then return his personal data, else returns null and
	 * gives no permission to login
	 */
	EbookUser ebookUserLogin(String username, String password) throws SQLException;
	/**
	 * this function is called upon sign up
	 * @param user an object containing submitted info
	 * @return status of adding a user to the database 
	 * @throws SQLException
	 */
	int addEbookUser(EbookUser user) throws SQLException;
	/**
	 * 
	 * @param username user's name
	 * @return true if user removed successfully, otherwise, false
	 * @throws SQLException
	 * remove a user by the admin
	 */
	boolean removeEbookUser(String username) throws SQLException;
	/**
	 * 
	 * @param username
	 * @param password , password provided by the user
	 * @return manager info in case it's a manager,  
	 * @throws SQLException
	 */
	Manager managerLogin(String username, String password) throws SQLException;
/**
 * 
 * @return
 * @throws SQLException
 */
	ArrayList<Purchase> getAllPurchases() throws SQLException;
	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	ArrayList<EbookUser> getAllEbookUsers() throws SQLException;
	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	ArrayList<Review> getAllUnapprovedReviews() throws SQLException;
	/**
	 * 
	 * @param username
	 * @return
	 * @throws SQLException
	 */
	ArrayList<Purchase> getUserPurchases(String username) throws SQLException;
	/**
	 * 
	 * @param review
	 * @return
	 * @throws SQLException
	 */
	boolean addNewReview(Review review) throws SQLException;
	/**
	 * 
	 * @param p
	 * @return
	 * @throws SQLException
	 */
	boolean addNewPurchase(Purchase p) throws SQLException;
	/**
	 * 
	 * @param username
	 * @return
	 * @throws SQLException
	 */
	String getNicknameByUsername(String username)  throws SQLException;
	/**
	 * 
	 * @param review
	 * @return
	 * @throws SQLException
	 */
	boolean approveReview(Review review) throws SQLException;
	/**
	 * 
	 * @param title
	 * @return
	 * @throws SQLException
	 */
	ArrayList<Purchase> getAllPurchasesByTitle(String title) throws SQLException;
	/**
	 * 
	 * @param is input stream
	 * @return every information of every ebook exists in the app 
	 * @throws SQLException
	 * @throws IOException
	 * this function gets all ebooks info from the json file and populates them into a collection of ebooks
	 */
	Collection<Ebook> getAllEbooks(InputStream is) throws SQLException, IOException;
	/**
	 * 
	 * @throws SQLException
	 *close connection opened in the constructor, after finishing retrieving/updating data 
	 */
	void closeConnection() throws SQLException;
/**
 * @param title
 * @return true if number of likes is decreased successfully, otherwise return flase
 * @throws SQLException
 * decrease number of likes by 1 whenever some user unlikes the book
 */
	boolean decreaseNumOfEbookLikes(String title) throws SQLException;
	/**
	 * 
	 * @param title
	 * @return number of likeds this book has
	 * @throws SQLException
	 * in order to display number of likes for each ebook, this function is called and it retrieves 
	 * the value of the likes number
	 */
	int numOfEbookLikes(String title) throws SQLException;
	/**
	 * 
	 * @param title
	 * @param username
	 * @return true if ebook liked successfully, otherwise false
	 * @throws SQLException
	 * add likes row into the database when a book "title" is liked by the user "username" 
	 */
	boolean likeEbook(String title, String username) throws SQLException;
	/**
	 * 
	 * @param title
	 * @return true if number of likes is updated successfully, otherwise, false
	 * @throws SQLException
	 * this function increases the likes count in the database after the given book is liked by some user
	 */
	boolean increaseNumOfEbookLikes(String title) throws SQLException;
	/**
	 * 
	 * @param username
	 * @param title 
	 * @return true if the given book is purchased by this user given by his "username"
	 * @throws SQLException
	 * this function is triggered in order to check if a user has the authority to 
	 * like/write review or read the book
	 */
	boolean checkIfEbookPurchased(String username, String title) throws SQLException;
	/**
	 * 
	 * @param title ,book''s title
	 * @return arraylist of reviews according to the title ebook
	 * @throws SQLException
	 * this function is triggered  when a user tries to read the reviews of some book
	 */
	ArrayList<Review> getSingleEbookReviews(String title) throws SQLException;
	/**
	 * 
	 * @param title title of ebook
	 * @return an arraylist containing the nicknames of user's that liked this particular book
	 * @throws SQLException
	 * this function is called when hovering over the number of likes tooltip in order to get nicknames
	 */
	ArrayList<String> getUsersThatLikedEbook(String title) throws SQLException;
	/**
	 * 
	 * @param purchase , the submitted purchase
	 * @return true if info provided at submitting a purchase request are with the right format, else return false
	 * this function validates input formats
	 */
	String validatePurchase(Purchase purchase);
	/**
	 * 
	 * @param username the name of the logged in user
	 * @param title book's title
	 * @return true if book provided by title is liked by the user, if not then return false 
	 * @throws SQLException
	 * this function is called in order to check if a book is liked by the user, in order to decide 
	 * according to that whether to respond with positive result
	 */
	boolean checkEbookLikedByUser(String username, String title) throws SQLException;
	/**
	 * 
	 * @param nickname
	 * @return 
	 * @throws SQLException
	 */
	ArrayList<EbookUser> getUserBynickname(String nickname) throws SQLException;
	/**
	 * 
	 * @param review object that includes information of the review being deleted 
	 * @return true if review removed successfully, otherwise false
	 * @throws SQLException
	 * when the admin decides to ignore a review then this function is called, 
	 * searches for the matching review in the database and then 
	 * it's totally removed from the database
	 */
	boolean ignoreReview(Review review) throws SQLException;
	/**
	 * 
	 * @param pos , the value of the current scrollbar position
	 * @param username the name of the user who has currently finished reading
	 * @param title the title of the book being read by the user "username"  
	 * @return true if the scrollbar position was updated successfuly, otherwise; return false
	 * @throws SQLException
	 * this function is called only when the user closes the opened book
	 */
	boolean updateScrollPosition(float pos, String username, String title) throws SQLException;
	/**
	 * 
	 * @param username name of user
	 * @param title name of the book that's already bought by the user
	 * and the user has already started to read it
	 * @return the value of the scrollbar's position where the reader last stopped
	 * @throws SQLException
	 */
	float getScrollPosition(String username, String title) throws SQLException;
	/**
	 * 
	 * @param title the title of the book that's being unliked
	 * @param username the name of the user who is currently trying to unlike the book he chose
	 * @return true if unliking an ebook is done successfully, else return false
	 * @throws SQLException
	 * remove the matching like from the likes table according to the given parameters 
	 */
	boolean unlikeEbook(String title, String username) throws SQLException;
/**
 * 
 * @param user
 * @return
 */
	boolean validateSignUp(EbookUser user);


}
