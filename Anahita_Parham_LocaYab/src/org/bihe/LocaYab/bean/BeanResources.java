package org.bihe.LocaYab.bean;

import org.bihe.LocaYab.Controller.GUIManager;

/**
 * 
 * @author @Parham created 8/9/2017 11:06 AM
 *
 */
public class BeanResources {

	// ---------------------- Fields ------------------------------

	// account fields
	private boolean signedInSituation;
	private Client signedInClient;

	// search fields
	private String cityNameSearch;
	private String optionNameSearch;

	// filter fields
	private String orderFilterSearch;

	/**
	 * true --> ASC
	 * 
	 * false --> DESC
	 */
	private boolean ascDESC;

	private static BeanResources instance;

	// -------------------- Constructor ---------------------------

	private BeanResources() {

		// TODO if we add "remember me it should be change
		signedInSituation = false;

		// filter initialize
		ascDESC = true;
		orderFilterSearch = "قیمت";

	}

	// single tone
	public static BeanResources getInstance() {
		if (instance == null) {
			instance = new BeanResources();
		}
		return instance;
	}
	// ---------------------- Getter Setters --------------------------

	public boolean isSignedIn() {
		return signedInSituation;
	}

	public Client getSignedUpClient() {
		return signedInClient;
	}

	public String getOrderFilterSearch() {
		return orderFilterSearch;
	}

	public void setOrderFilterSearch(String orderFilterSearch) {
		this.orderFilterSearch = orderFilterSearch;
	}

	public boolean isAscDESC() {
		return ascDESC;
	}

	public void setAscDESC(boolean ascDESC) {
		this.ascDESC = ascDESC;
	}

	/**
	 * this method handle the taskBar event and add the input value to the
	 * signedUpClient
	 * 
	 * @param signedUpClient
	 */
	private void setSignedUpClient(Client signedUpClient) {

		this.signedInClient = signedUpClient;

		// handle the taskBar show
		if (signedUpClient != null) {

			// signing in

			// the text in the taskBar panel
			GUIManager.getMainFrame().changeUserNameLabel(" خوش آمدی " + signedUpClient.getFirstName() + "  ");
		} else {

			// signing out

			GUIManager.getMainFrame().changeUserNameLabel(" ");

		}

		// repainting the frame to show the user detail
		GUIManager.getMainFrame().repaint();
		GUIManager.getMainFrame().revalidate();

	}

	public String getCityNameSearch() {
		return cityNameSearch;
	}

	public void setCityNameSearch(String cityNameSearch) {
		this.cityNameSearch = cityNameSearch;
	}

	public String getOptionNameSearch() {
		return optionNameSearch;
	}

	public void setOptionNameSearch(String optionNameSearch) {
		this.optionNameSearch = optionNameSearch;
	}

	// ------------------------ Generate sign in ---------------------------

	/**
	 * 1.Us --> user
	 * 
	 * 2.Me --> Member
	 * 
	 * 3.Ad --> Administrator
	 */
	public int generateUsMeAd() {

		if (BeanResources.getInstance().isSignedIn()) {

			// if user signed in
			return 2;

			/**
			 * TODO @Parham
			 * 
			 * change it when you want to add administration pages
			 */

		} else {
			// if user not signed in
			return 1;

		}
	}

	/**
	 * this method handle all the events that happens during signing out and
	 * signing in
	 * 
	 * 
	 * @param for
	 *            sign in (give [Client] to the method)
	 * @param for
	 *            sign out (give [null] to the method)
	 * 
	 * @param cln
	 */
	public void accountManager(Client cln) {

		if (cln == null) {

			// signing out
			signedInSituation = false;
			setSignedUpClient(cln);

		} else {

			// signing in
			signedInSituation = true;
			setSignedUpClient(cln);

		}

	}

}
