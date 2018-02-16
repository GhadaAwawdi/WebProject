package model;

import java.sql.SQLException;

import javax.naming.NamingException;

public class Ebook {

	int id;
	double price;
	int likesNum;
	String location;
	String name;
	String image;
	String shortDescription;
	public Ebook(int id, String name, double price, String image, String shortDescription,String location, int likesNum) throws NamingException, SQLException {
		super();
		System.out.println("in constructor");
		this.id = id;
		this.price = price;
		this.likesNum=likesNum;
		this.name = name;
		this.image = image;
		this.shortDescription = shortDescription;
		this.location = location;
	}
	public int getId() {
		return id;
	}
	public double getPrice() {
		return price;
	}
	public int getLikesNum() {
		return likesNum;
	}
	public String getName() {
		return name;
	}
	public String getImage() {
		return image;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public void setLikesNum(int likesNum) throws NamingException, SQLException {
		this.likesNum = likesNum;
	}
	public void setName(String name) {
		this.name = name;
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
	
	
	
}
