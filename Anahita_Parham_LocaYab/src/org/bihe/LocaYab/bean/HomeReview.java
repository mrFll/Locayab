package org.bihe.LocaYab.bean;

public class HomeReview {

	// --------------------------- Fields -------------------------
	private int ID;
	private String clientName;
	private String text;

	// ------------------------- Constructor -----------------------
	public HomeReview(String clientName, String text) {

		this.clientName = clientName;
		this.text = text;
	}

	public HomeReview(int id, String clientName, String text) {

		this.clientName = clientName;
		this.text = text;
		this.ID = id;
	}

	// ------------------------ Getter Setter ----------------------

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

}
