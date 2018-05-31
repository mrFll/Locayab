package org.bihe.LocaYab.bean;

import java.util.ArrayList;

public class Client {

	// ---------------------------- Fields ------------------------------
	private String nationalID;
	private String firstName;
	private String lastName;
	private String tell;
	private String pass;
	private int id;
	private String address;

	private boolean admin;

	private ArrayList<Home> ownHomes;
	private ArrayList<Home> pinnedHome;

	// ------------------------ Constructor -----------------------------

	public Client(String nationalID, String firstName, String lastName, String tell, String pass, int id,
			String address) {

		this.nationalID = nationalID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.tell = tell;
		this.pass = pass;
		this.id = id;
		this.address = address;

		ownHomes = new ArrayList<>();
		pinnedHome = new ArrayList<>();
	}

	// ----------------------- Getter Setters ----------------------------

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNationalID() {
		return nationalID;
	}

	public void setNationalID(String nationalID) {
		this.nationalID = nationalID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTell() {
		return tell;
	}

	public void setTell(String tell) {
		this.tell = tell;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public ArrayList<Home> getOwnHomes() {
		return ownHomes;
	}

	public void setOwnHomes(ArrayList<Home> ownHomes) {
		this.ownHomes = ownHomes;
	}

	public ArrayList<Home> getPinnedHome() {
		return pinnedHome;
	}

	public void setPinnedHome(ArrayList<Home> pinnedHome) {
		this.pinnedHome = pinnedHome;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
