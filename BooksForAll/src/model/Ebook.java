package model;

import java.sql.SQLException;

import javax.naming.NamingException;

public class Ebook {

//	int id;
	double price;
	int likesNum;
	String location;
	String title;
	String image;
	String shortDescription;
	boolean liked;
	public Ebook( String title, double price, String image, String shortDescription,String location, int likesNum) throws NamingException, SQLException {
		super();
		System.out.println("in constructor");
		this.title = title;
		this.price = price;
		this.likesNum=likesNum;
		this.image = image;
		this.shortDescription = shortDescription;
		this.location = location;
	}

	public double getPrice() {
		return price;
	}
	public int getLikesNum() {
		return likesNum;
	}
	public String getTitle() {
		return title;
	}
	public String getImage() {
		return image;
	}
	public String getShortDescription() {
		return shortDescription;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	public void setLikesNum(int likesNum) throws NamingException, SQLException {
		this.likesNum = likesNum;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	
	public void setLiked(boolean liked) {
		this.liked = liked;
	}
	public boolean getLiked() {
		return liked;
	}
	
	
}
