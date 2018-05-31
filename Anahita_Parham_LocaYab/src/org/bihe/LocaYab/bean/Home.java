package org.bihe.LocaYab.bean;

import java.util.ArrayList;
import org.bihe.LocaYab.bean.HomeReview;

import com.teamdev.jxmaps.LatLng;

public class Home {

	// ---------------------------- Fields ------------------------------
	private int homeID;
	private int ownerID;
	private int cost;
	private int meter;
	private String city;
	private String address;
	private int roomNumb;
	private String option;
	private LatLng location;
	private String photoID;

	private ArrayList<HomeReview> reviews;

	// ------------------------- Constructor -----------------------------

	public Home(int homeID, int cost, int meter, String city, String address, String option, int roomNumb, int ownerID,
			LatLng location, String photoID) {

		this.homeID = homeID;
		this.cost = cost;
		this.meter = meter;
		this.city = city;
		this.roomNumb = roomNumb;
		this.address = address;
		this.option = option;
		this.ownerID = ownerID;
		this.location = location;
		this.photoID = photoID;

		this.reviews = new ArrayList<>();
	}
	// --------------------------- Methods --------------------------------

	// ------------------------ Getter Setter -----------------------------
	public int getHomeID() {
		return homeID;
	}

	public void setHomeID(int homeID) {
		this.homeID = homeID;
	}

	public int getOwnerID() {
		return ownerID;
	}

	public int getCost() {
		return cost;
	}

	public String getPhotoID() {
		return photoID;
	}

	public void setPhotoID(String photoID) {
		this.photoID = photoID;
	}

	public void setOwnerID(int ownerID) {
		this.ownerID = ownerID;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getMeter() {
		return meter;
	}

	public void setMeter(int meter) {
		this.meter = meter;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public LatLng getLocation() {
		return location;
	}

	public void setLocation(LatLng location) {
		this.location = location;
	}

	public ArrayList<HomeReview> getReviews() {
		return reviews;
	}

	public void setReviews(ArrayList<HomeReview> reviews) {
		this.reviews = reviews;
	}

	public void addReview(HomeReview review) {
		this.reviews.add(review);
	}

	public int getRoomNumb() {
		return roomNumb;
	}

	public void setRoomNumb(int roomNumb) {
		this.roomNumb = roomNumb;
	}

}
