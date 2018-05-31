package org.bihe.LocaYab.Controller;

import javax.swing.JOptionPane;

import org.bihe.LocaYab.UI.AddNewHome;
import org.bihe.LocaYab.UI.BackgroundMovement;
import org.bihe.LocaYab.UI.HomeDetailPanel;
import org.bihe.LocaYab.UI.MainFrame;
import org.bihe.LocaYab.UI.MapPanel;
import org.bihe.LocaYab.UI.PersonalInfoPanel;
import org.bihe.LocaYab.UI.SignInPanel;
import org.bihe.LocaYab.UI.SignUpPanel;
import org.bihe.LocaYab.UI.WelcomePanel;
import org.bihe.LocaYab.UI.OptionPanes.SignInOptionPane;
import org.bihe.LocaYab.bean.BeanResources;
import org.bihe.LocaYab.bean.Home;
import org.bihe.LocaYab.jdbc.DBDAO.ClientDAO;
import org.bihe.LocaYab.UI.SearchResultPanel;

public class GUIManager {
	private static MainFrame mainFrame;
	private static WelcomePanel welcomePanel;
	private static SignUpPanel signUp;
	private static SignInPanel signIn;
	private static SearchResultPanel searchResult;
	private static AddNewHome addHome;
	private static HomeDetailPanel homeDetails;
	private static MapPanel detailMap;
	private static MapPanel resultMap;
	private static MapPanel newHomeMap;
	private static MapPanel directionMap;
	private static BackgroundMovement backgroundMovement;
	private static SignInOptionPane signInOP;
	private static PersonalInfoPanel personInfo;

	//

	// -------------------------------------//PanelGetters//--------------------------------
	/**
	 * 
	 * @return
	 */
	public static MainFrame getMainFrame() {
		if (mainFrame == null) {
			mainFrame = new MainFrame();
		}
		return mainFrame;
	}

	private static SignInOptionPane getSignInOptionPane() {

		if (signInOP == null) {
			signInOP = new SignInOptionPane();
		}
		return signInOP;
	}

	/**
	 * 
	 * @return
	 */
	public static WelcomePanel getWelcomePanel() {
		if (welcomePanel == null) {
			welcomePanel = new WelcomePanel();
		}
		return welcomePanel;
	}

	public static PersonalInfoPanel getPersonInfoPanel() {
		if (personInfo == null) {
			personInfo = new PersonalInfoPanel();
		}
		getDetailMap();
		return personInfo;
	}

	public static BackgroundMovement getBackgroundMovement() {
		if (backgroundMovement == null) {
			backgroundMovement = new BackgroundMovement();
		}
		return backgroundMovement;
	}

	/**
	 * 
	 * @return
	 */
	private static SignUpPanel getSignUpPanel() {
		if (signUp == null) {
			signUp = new SignUpPanel();
		}
		return signUp;
	}

	/**
	 * 
	 * @return
	 */
	private static SignInPanel getSignInPanel() {
		if (signIn == null) {
			signIn = new SignInPanel();
		}
		getNewHomeMap();
		return signIn;
	}

	private static SearchResultPanel getsearchResult() {
		if (searchResult == null) {
			searchResult = new SearchResultPanel();
		}
		getDetailMap();
		return searchResult;
	}

	private static AddNewHome getAddNewHome() {
		if (addHome == null) {
			addHome = new AddNewHome();
		}
		return addHome;
	}

	private static HomeDetailPanel getHomeDetails() {
		if (homeDetails == null) {
			homeDetails = new HomeDetailPanel();
		}
		getDirectionMap();
		return homeDetails;
	}

	// -------------------------------------//adToFrame//--------------------------------

	/**
	 * 
	 */
	public static void addWelcomeToFrame() {

		remove();
		getMainFrame().panel = 1;
		getMainFrame().addComponent(getWelcomePanel());

		// repaint the info in the taskBar in every changes
		recoverUserDetailInTaskBar();

	}

	public static void addPersonalInfoToFrame() {

		remove();
		getPersonInfoPanel().recover();
		getMainFrame().addComponent(getPersonInfoPanel());
		getMainFrame().panel = 7;

		// repaint the info in the taskBar in every changes
		recoverUserDetailInTaskBar();

	}

	public static void showSignInOptionPane() {

		getSignInOptionPane().show();

	}

	public static void addSignUpToFrame() {
		remove();
		getMainFrame().panel = 2;
		getMainFrame().addComponent(getSignUpPanel());

		// repaint the info in the taskBar in every changes
		recoverUserDetailInTaskBar();

	}

	public static void addSignInToFrame() {
		remove();
		getMainFrame().panel = 3;
		getMainFrame().addComponent(getSignInPanel());

		// repaint the info in the taskBar in every changes
		recoverUserDetailInTaskBar();

	}

	public static void addsearchResultToFrame() {

		// recover the last panel
		remove();

		// Initialize and put details in the panel
		getsearchResult().recover();

		getMainFrame().panel = 4;
		getMainFrame().addComponent(getsearchResult());

		// repaint the info in the taskBar in every changes
		recoverUserDetailInTaskBar();

		if (getsearchResult().getSearchedHouses().isEmpty()) {
			JOptionPane.showMessageDialog(null, "خانه ای در شهر مورد نظر ثبت نشده! ", "",
					JOptionPane.INFORMATION_MESSAGE);
		}

	}

	public static void addAddHomeToFrame() {
		remove();
		getMainFrame().panel = 5;
		getMainFrame().addComponent(getAddNewHome());
		// repaint the info in the taskBar in every changes
		recoverUserDetailInTaskBar();

	}

	public static void addHomeDetailsToFrame(Home home, int previousPanel) {
		remove();
		getMainFrame().panel = 6;
		HomeDetailPanel.home = home;
		getHomeDetails().previousPanel = previousPanel;
		getHomeDetails().reNew();
		getMainFrame().addComponent(getHomeDetails());

		// repaint the info in the taskBar in every changes
		recoverUserDetailInTaskBar();

	}

	public static MapPanel getResultMap() {
		if (resultMap == null) {
			resultMap = new MapPanel();
		}
		return resultMap;
	}

	public static MapPanel getDetailMap() {
		if (detailMap == null) {
			detailMap = new MapPanel();
		}
		return detailMap;
	}

	public static MapPanel getNewHomeMap() {
		if (newHomeMap == null) {
			newHomeMap = new MapPanel();
		}
		newHomeMap.setPinUsage(true);
		return newHomeMap;
	}

	public static MapPanel getDirectionMap() {
		if (directionMap == null) {
			directionMap = new MapPanel();
		} else {
			directionMap.PathRemover();
			directionMap.setPinUsage(true);
		}
		return directionMap;
	}

	// -------------------------------------//remover//--------------------------------
	/**
	 * 1--> welcomePanel
	 * 
	 * 2-->signUpPanel
	 * 
	 * 3-->SignInPanel
	 * 
	 * 4-->SearchResult
	 * 
	 * 5-->AddNewHome
	 * 
	 * 6-->homeDetail
	 * 
	 * 7-->PersonalInfoPanel
	 * 
	 */
	private static void remove() {

		if (BeanResources.getInstance().isSignedIn()) {

			BeanResources.getInstance().accountManager(
					ClientDAO.getInstance().getClient(BeanResources.getInstance().getSignedUpClient().getNationalID()));

		}

		if (getMainFrame().panel == 3) {

			getSignInPanel().recover();

		} else if (getMainFrame().panel == 2) {

			getSignUpPanel().recover();

		} else if (getMainFrame().panel == 1) {

			getWelcomePanel().recover();

		} else if (getMainFrame().panel == 5) {

			getAddNewHome().recover();
			getNewHomeMap().mapRemover(true);

		} else if (getMainFrame().panel == 6) {
			getHomeDetails().recover();
			getDetailMap().mapRemover(false);
		}

	}

	/**
	 * this method repaint the user detail in the taskBar
	 */
	public static void recoverUserDetailInTaskBar() {

		if (BeanResources.getInstance().isSignedIn()) {

			GUIManager.getMainFrame().changeUserNameLabel(
					" خوش آمدی " + BeanResources.getInstance().getSignedUpClient().getFirstName() + "  ");

		} else {

			GUIManager.getMainFrame().changeUserNameLabel(" ");
		}
	}

	public static void recoverSearchResultPanelPins() {
		getsearchResult().doSearch();
	}

}
