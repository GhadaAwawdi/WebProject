package model;

public class Ebook {

	int id;
	int price;
	int likesNum;
	String name;
	String image;
	String shortDescription;
	public Ebook( String name, int price, int likesNum, String image, String shortDescription) {
		super();
		this.price = price;
		this.likesNum = likesNum;
		this.name = name;
		this.image = image;
		this.shortDescription = shortDescription;
	}
	public int getId() {
		return id;
	}
	public int getPrice() {
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
	public void setPrice(int price) {
		this.price = price;
	}
	public void setLikesNum(int likesNum) {
		this.likesNum = likesNum;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	
	
	
}
