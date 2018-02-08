package dataAccess;

import java.sql.SQLException;

import model.Ebook;
import model.EbookUser;
import model.Manager;

public interface DataInterface {

	EbookUser ebookUserLogin(String username, String password) throws SQLException;
	int addEbookUser(EbookUser user) throws SQLException;
	boolean addEbook(Ebook ebook) throws SQLException;
	boolean unlikeEbook(String nameOfEbook) throws SQLException;
	boolean deleteEbookUser(String username) throws SQLException;
	Manager managerLogin(String username, String password) throws SQLException;


}
