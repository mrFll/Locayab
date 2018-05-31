package org.bihe.LocaYab.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.concurrent.TimeUnit;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.bihe.LocaYab.Controller.GUIManager;
import org.bihe.LocaYab.IO.IO;
import org.bihe.LocaYab.bean.BeanResources;
import org.bihe.LocaYab.bean.Client;
import org.bihe.LocaYab.bean.Home;
import org.bihe.LocaYab.jdbc.DBDAO.ClientDAO;
import org.bihe.LocaYab.jdbc.DBDAO.HomeDAO;

import com.teamdev.jxmaps.LatLng;

import Resource.Resource;

public class HomeDetailPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MapPanel map;
	private LatLng location;
	private int largePhoto = 0;
	public static Home home;
	public int previousPanel;

	private JLabel ownerPhone;
	private JLabel ownerName;
	private JLabel roomNumbL;
	private JLabel meterL;
	private JLabel priceL;
	private JLabel addressL;
	private JLabel optionL;
	private JLabel cityL;

	private JComboBox<Integer> roomNumberCB;
	private JTextField meterTF;
	private JTextField priceTF;
	private JTextArea addressTF;
	private JComboBox<String> optionCB;
	private JTextField cityTF;

	private boolean editStatus;
	private JButton editBtn;
	private JLabel marketsNum;
	private JPanel moreInfo;
	private JLabel nearestBank;
	private JLabel bankNum;
	private JLabel nearestMarket;
	private JPanel details;
	private JPanel info;
	private JPanel photosPnl;
	private JButton[] smallPhotos;
	private JLabel largePhotoLabel;
	private File[] photos;
	private JButton changePin;
	private JButton homeDetail;

	public HomeDetailPanel() {

		location = home.getLocation();
		//
		this.setLayout(new BorderLayout());
		this.setBackground(MainFrame.SURROUND);

		JSplitPane split = new JSplitPane();
		split.setContinuousLayout(true);
		split.setDividerLocation(800);
		split.setResizeWeight(1);

		split.setLeftComponent(leftPanel());
		split.setRightComponent(rightPanel());

		this.add(split);
		this.editStatus = false;

	}

	// ----------------------------------------//LeftPanel//------------------------------------------
	/**
	 * left Panel that includes home detail information
	 * 
	 * @return
	 */
	private JPanel leftPanel() {

		JPanel leftPanel = new JPanel();
		leftPanel.setBackground(MainFrame.BACKGROUND);
		leftPanel.setMinimumSize(new Dimension(700, MainFrame.HEIGHT));
		leftPanel.setLayout(new BorderLayout());
		//
		leftPanel.add(detailPanel(), BorderLayout.CENTER);

		return leftPanel;
	}

	// ----------------------------------------//DistanceToString//------------------------------------------
	private String distance() {
		if (map.getDistance() < 1000) {
			return map.getDistance() + "متر ";
		} else {
			double distance = map.getDistance() / 1000;
			return distance + "کیلومتر ";
		}
	}

	// ----------------------------------------//DetailPanel//------------------------------------------
	private JPanel detailPanel() {

		// scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		details = new JPanel();
		details.setBackground(MainFrame.BACKGROUND);
		GridBagLayout gbl = new GridBagLayout();
		details.setLayout(gbl);
		// 10 X 12 all resize with panel
		gbl.columnWidths = new int[] { 100, 100, 100, 100, 100 };
		gbl.columnWeights = new double[] { 10.0d, 10.0d, 10.0d, 10.0d, 10.0 };
		gbl.rowHeights = new int[] { 100, 100, 100, 50, 50, 50, 50 };
		gbl.rowWeights = new double[] { 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d };
		// -------------------------------------------//Back//-------------------------------------------------
		GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 0.0d, 0.0d, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0);
		JButton back = new JButton("");
		back.setSize(new Dimension(10, 10));
		back.setIcon(new ImageIcon(Resource.getImage("back.png")));
		back.addActionListener(new ActionListener() {
			/**
			 * 4-->SearchResult 7-->PersonalInfoPanel
			 */
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (previousPanel == 4) {
					GUIManager.addsearchResultToFrame();
				} else if (previousPanel == 7) {
					GUIManager.addPersonalInfoToFrame();
				}
			}
		});
		details.add(back, gbc);
		// -------------------------------------------//photos//-------------------------------------------------
		gbc = new GridBagConstraints(1, 0, 3, 3, 0.0d, 0.0d, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(4, 4, 4, 4), 0, 0);

		String homeID = home.getHomeID() + "";
		details.add(photos(homeID), gbc);
		// -------------------------------------------//INFO//-------------------------------------------------
		// other info about the house
		gbc = new GridBagConstraints(0, 3, 5, 4, 0.0d, 0.0d, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(4, 4, 4, 4), 0, 0);
		details.add(infoPanel(), gbc);
		details.add(MoreInfoPanel(), gbc);
		moreInfo.setVisible(false);
		return details;
	}

	// -------------------------------------------//infoPanel//-------------------------------------------------
	/**
	 * show the ordinary info about property
	 * 
	 * @return
	 */
	private JPanel infoPanel() {

		info = new JPanel();
		info.setBackground(MainFrame.BACKGROUND);
		GridBagLayout gbl = new GridBagLayout();
		info.setLayout(gbl);

		// 10 X 12 all resize with panel
		gbl.columnWidths = new int[] { 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30 };
		gbl.columnWeights = new double[] { 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d,
				10.0d, 10.0d, 10.0d };

		gbl.rowHeights = new int[] { 30, 30, 30, 30, 30, 30, 30, 30, 30 };
		gbl.rowWeights = new double[] { 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d };
		//
		// owner detail
		Client tempClient = ClientDAO.getInstance().getClientWithOutdetails(home.getOwnerID());

		// ----------------------- Client Info -----------------------

		// ----------- name -----------------
		GridBagConstraints gbc = new GridBagConstraints(6, 0, 8, 2, 0.0d, 0.0d, GridBagConstraints.LINE_END,
				GridBagConstraints.BOTH, new Insets(4, 4, 4, 4), 0, 0);
		ownerName = new JLabel(
				"   نام و نام خانوادگی مالک:" + tempClient.getFirstName() + " " + tempClient.getLastName(),
				JLabel.RIGHT);
		ownerName.setFont(Resource.getFont(20f));
		info.add(ownerName, gbc);

		// ----------- tell -----------------
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 6;
		gbc.gridheight = 2;

		ownerPhone = new JLabel(" شماره تماس: " + tempClient.getTell(), JLabel.RIGHT);
		ownerPhone.setFont(Resource.getFont(20f));
		info.add(ownerPhone, gbc);

		// ------------------- Room numb --------------------------

		roomNumbL = new JLabel(home.getRoomNumb() + "", JLabel.RIGHT);
		roomNumbL.setFont(Resource.getFont(20f));
		roomNumbL.setVisible(true);

		gbc.gridx = 11;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.gridheight = 2;

		info.add(roomNumbL, gbc);

		// ---------------------------
		Integer nums[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24,
				25 };
		roomNumberCB = new JComboBox<>(nums);
		roomNumberCB.setFont(Resource.getFont(18f));
		roomNumberCB.setSelectedIndex(home.getRoomNumb());
		roomNumberCB.setVisible(false);

		info.add(roomNumberCB, gbc);

		// ----------------------------
		JLabel roomNumbText = new JLabel("  تعداد اتاق: ", JLabel.LEFT);
		roomNumbText.setFont(Resource.getFont(20f));

		gbc.gridx = 13;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.gridheight = 2;

		info.add(roomNumbText, gbc);

		// ------------------- meter --------------------------
		meterL = new JLabel(home.getMeter() + "", JLabel.RIGHT);
		meterL.setFont(Resource.getFont(20f));
		meterL.setVisible(true);

		gbc.gridx = 6;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.gridheight = 2;

		info.add(meterL, gbc);

		// ---------------------------
		meterTF = new JTextField(home.getMeter() + "");
		meterTF.setVisible(false);
		meterTF.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		meterTF.setFont(MainFrame.fieldFont(20));
		meterTF.setBorder(BorderFactory.createEtchedBorder(Color.GRAY, Color.DARK_GRAY));

		info.add(meterTF, gbc);

		// ----------------------------
		JLabel meterText = new JLabel(" متراژ: ", JLabel.LEFT);
		meterText.setFont(Resource.getFont(20f));

		gbc.gridx = 8;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.gridheight = 2;

		info.add(meterText, gbc);

		// ------------------- option --------------------------
		optionL = new JLabel(home.getOption(), JLabel.RIGHT);
		optionL.setFont(Resource.getFont(20f));
		optionL.setVisible(true);

		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.gridheight = 2;

		info.add(optionL, gbc);

		// ---------------------------
		String[] options = { "رهن", "اجاره", "خرید" };
		optionCB = new JComboBox<>(options);
		optionCB.setFont(Resource.getFont(18f));

		if (home.getOption().equals("رهن")) {
			optionCB.setSelectedIndex(0);

		} else if (home.getOption().equals("اجاره")) {

			optionCB.setSelectedIndex(1);

		} else if (home.getOption().equals("خرید")) {

			optionCB.setSelectedIndex(2);

		}
		optionCB.setVisible(false);

		info.add(optionCB, gbc);

		// ----------------------------
		JLabel optionText = new JLabel(" نوع معامله: ", JLabel.LEFT);
		optionText.setFont(Resource.getFont(20f));

		gbc.gridx = 3;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.gridheight = 2;

		info.add(optionText, gbc);

		// ------------------- price --------------------------
		priceL = new JLabel(home.getCost() + "", JLabel.RIGHT);
		priceL.setFont(Resource.getFont(20f));
		priceL.setVisible(true);

		gbc.gridx = 2;
		gbc.gridy = 6;
		gbc.gridwidth = 2;
		gbc.gridheight = 2;

		info.add(priceL, gbc);

		// ---------------------------
		priceTF = new JTextField(home.getCost() + "");
		priceTF.setVisible(false);
		priceTF.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		priceTF.setFont(MainFrame.fieldFont(20));
		priceTF.setBorder(BorderFactory.createEtchedBorder(Color.GRAY, Color.DARK_GRAY));

		info.add(priceTF, gbc);

		// ----------------------------
		JLabel priceTextCount = new JLabel(" تومان ", JLabel.RIGHT);
		priceTextCount.setFont(Resource.getFont(20f));
		priceTextCount.setVisible(true);

		gbc.gridx = 1;
		gbc.gridy = 6;
		gbc.gridwidth = 1;
		gbc.gridheight = 2;

		info.add(priceTextCount, gbc);

		// ----------------------------
		JLabel priceText = new JLabel("قیمت  : ", JLabel.LEFT);
		priceText.setFont(Resource.getFont(20f));

		gbc.gridx = 4;
		gbc.gridy = 6;
		gbc.gridwidth = 1;
		gbc.gridheight = 2;

		info.add(priceText, gbc);

		// ------------------- address --------------------------

		addressL = new JLabel(home.getAddress(), JLabel.RIGHT);
		addressL.setFont(Resource.getFont(20f));
		addressL.setVisible(true);

		gbc.gridx = 5;
		gbc.gridy = 6;
		gbc.gridwidth = 8;
		gbc.gridheight = 4;

		info.add(addressL, gbc);

		// ---------------------------
		addressTF = new JTextArea(home.getAddress());
		addressTF.setVisible(false);
		addressTF.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		addressTF.setFont(MainFrame.fieldFont(20));
		addressTF.setBorder(BorderFactory.createEtchedBorder(Color.GRAY, Color.DARK_GRAY));

		info.add(addressTF, gbc);

		// ----------------------------
		JLabel addressText = new JLabel("  آدرس: ", JLabel.LEFT);
		addressText.setFont(Resource.getFont(20f));

		gbc.gridx = 13;
		gbc.gridy = 7;
		gbc.gridwidth = 2;
		gbc.gridheight = 2;

		info.add(addressText, gbc);

		// ---------------------- city field ----------------------------

		cityL = new JLabel(home.getCity() + "", JLabel.RIGHT);
		cityL.setFont(Resource.getFont(20f));
		cityL.setVisible(true);

		gbc.gridx = 11;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		gbc.gridheight = 2;

		info.add(cityL, gbc);

		// ---------------------------
		cityTF = new JTextField(home.getCity() + "");
		cityTF.setVisible(false);
		cityTF.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		cityTF.setFont(MainFrame.fieldFont(20));
		cityTF.setBorder(BorderFactory.createEtchedBorder(Color.GRAY, Color.DARK_GRAY));

		info.add(cityTF, gbc);

		// ----------------------------
		JLabel cityText = new JLabel(" شهر: ", JLabel.LEFT);
		cityText.setFont(Resource.getFont(20f));

		gbc.gridx = 13;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		gbc.gridheight = 2;

		info.add(cityText, gbc);

		// ------------------- edit button --------------------------
		editBtn = new JButton("ویرایش");
		editBtn.setFont(Resource.getFont(24f));

		if (BeanResources.getInstance().isSignedIn()) {

			if (BeanResources.getInstance().getSignedUpClient().getId() == home.getHomeID()) {

				editBtn.setVisible(true);
			} else {

				editBtn.setVisible(false);

			}

		}

		if (BeanResources.getInstance().generateUsMeAd() == 3) {

			editBtn.setVisible(true);

		}

		editBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (editStatus) {

					if (update()) {
						editStatus = false;
						editVisibalingManager();
						editBtn.setText("ویرایش");
						// System.out.println(map.pinUsage);
						// System.out.println(map.getSet());

						if (previousPanel == 7) {

							GUIManager.addPersonalInfoToFrame();

						} else {

							repaint();
							reNew();
						}

					}

				} else {

					editStatus = true;
					editVisibalingManager();
					editBtn.setText("ثبت");

				}

			}
		});

		gbc.gridx = 2;
		gbc.gridy = 9;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		info.add(editBtn, gbc);

		// ------------------- more info --------------------------
		gbc.gridx = 0;
		gbc.gridy = 9;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.BOTH;
		homeDetail = new JButton("جزییات بیشتر");
		homeDetail.setFont(Resource.getFont(20f));
		homeDetail.setVisible(false);
		homeDetail.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				info.setVisible(false);
				labelText("store", "market.png", "مغازه", nearestMarket, marketsNum);
				labelText("bank", "bank.png", "بانک", nearestBank, bankNum);
				moreInfo.revalidate();
				moreInfo.repaint();
				moreInfo.setVisible(true);
				revalidate();
				repaint();
			}
		});

		changePin = new JButton("تغییر مکان نما");
		changePin.setFont(Resource.getFont(20f));
		changePin.setVisible(false);
		changePin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "مکان جدید را روی نقشه مشخص کنید");
				map.setPinUsage(true);
				map.setSet(false);
				map = GUIManager.getDetailMap();
				map.markersCleaner(map.inDetailLocation);
				map.repaint();
				revalidate();
				repaint();
			}
		});

		info.add(homeDetail, gbc);
		info.add(changePin, gbc);
		return info;

	}

	private void labelText(String placeID, String icon, String name, JLabel nearest, JLabel num) {
		String[] distance = distance(placeID, icon, name);
		nearest.setText(distance[0]);
		if (!distance[0].equals("در شعاع یک کیلومتری موردی یافت نشد")) {
			num.setText(distance[1]);
		} else {
			num.setText("");
		}

	}

	// -------------------------------------//MoreInfoPanel//------------------------------
	/**
	 * shows extra info about home
	 * 
	 * @return
	 */
	private JPanel MoreInfoPanel() {
		moreInfo = new JPanel();
		moreInfo.setVisible(false);
		moreInfo.setBackground(MainFrame.BACKGROUND);
		GridBagLayout gbl = new GridBagLayout();
		moreInfo.setLayout(gbl);
		// 10 X 12 all resize with panel
		gbl.columnWidths = new int[] { 100, 100, 100, 100 };
		gbl.columnWeights = new double[] { 10.0d, 10.0d, 10.0d };
		gbl.rowHeights = new int[] { 100, 100, 100, 100 };
		gbl.rowWeights = new double[] { 10.0d, 10.0d, 10.0d, 10.0d };
		//

		GridBagConstraints gbc = new GridBagConstraints(0, 0, 3, 1, 0.0d, 0.0d, GridBagConstraints.LINE_END,
				GridBagConstraints.BOTH, new Insets(4, 4, 4, 4), 0, 0);
		nearestMarket = new JLabel("فاصله", JLabel.RIGHT);
		nearestMarket.setFont(Resource.getFont(20f));
		moreInfo.add(nearestMarket, gbc);

		gbc.gridy = 1;
		marketsNum = new JLabel("تعداد", JLabel.RIGHT);
		marketsNum.setFont(Resource.getFont(20f));
		moreInfo.add(marketsNum, gbc);

		gbc.gridy = 2;
		nearestBank = new JLabel("", JLabel.RIGHT);
		nearestBank.setFont(Resource.getFont(20f));
		moreInfo.add(nearestBank, gbc);

		gbc.gridy = 3;
		bankNum = new JLabel("", JLabel.RIGHT);
		bankNum.setFont(Resource.getFont(20f));
		moreInfo.add(bankNum, gbc);

		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridy = 3;
		gbc.gridx = 0;
		gbc.gridwidth = 1;
		JButton homeDetail = new JButton("بازگشت");
		homeDetail.setFont(Resource.getFont(20f));
		homeDetail.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				moreInfo.setVisible(false);
				info.setVisible(true);
				revalidate();
				repaint();
			}

		});
		moreInfo.add(homeDetail, gbc);
		gbc = new GridBagConstraints(0, 3, 5, 4, 0.0d, 0.0d, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(4, 4, 4, 4), 0, 0);
		details.add(moreInfo, gbc);
		return moreInfo;
	}

	// -------------------------------------//photosPanel//------------------------------
	/**
	 * the panel that consist of photos
	 * 
	 * @param homeID
	 * @return
	 */
	private JPanel photos(String homeID) {
		photosPnl = new JPanel();
		photosPnl.setBackground(MainFrame.BACKGROUND);
		GridBagLayout gbl = new GridBagLayout();
		photosPnl.setLayout(gbl);
		// 10 X 12 all resize with panel
		gbl.columnWidths = new int[] { 100, 100, 100, 100 };
		gbl.columnWeights = new double[] { 0.0d, 0.0d, 0.0d, 0.0d };
		gbl.rowHeights = new int[] { 100, 100, 100 };
		gbl.rowWeights = new double[] { 0.0d, 0.0d, 0.0d };

		GridBagConstraints gbc = new GridBagConstraints(0, 0, 3, 3, 0.0d, 0.0d, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(4, 4, 4, 4), 0, 0);
		File[] photos = IO.getPhotos(homeID);
		largePhotoLabel = new JLabel("", JLabel.CENTER);
		photosPnl.add(largePhotoLabel, gbc);
		// show them if there are any picture
		int gridy = 0;
		gbc = new GridBagConstraints(3, gridy, 1, 1, 0.0d, 0.0d, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				new Insets(4, 4, 4, 4), 0, 0);

		if (photos.length == 0) {
			largePhotoLabel.setText("عکسی برای این ملک موجود نیست!      ");
			largePhotoLabel.setFont(Resource.getFont(32f));
		} else {

			ImageIcon img = new ImageIcon(new ImageIcon(photos[largePhoto].getAbsolutePath()).getImage()
					.getScaledInstance(400, 300, Image.SCALE_DEFAULT));
			largePhotoLabel.setIcon(img);
		}

		smallPhotos = new JButton[3];
		for (int i = 0; i < 3; i++) {
			smallPhotos[i] = new JButton("");
			smallPhotos[i].setOpaque(false);
			smallPhotos[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
			smallPhotos[i].setBackground(MainFrame.FIELD);
			gbc.gridy = gridy++;
			photosPnl.add(smallPhotos[i], gbc);
		}
		return photosPnl;
	}

	// -------------------------------------//photosLabel//------------------------------
	/**
	 * handle the photos in top of page
	 * 
	 * @param homeID
	 */
	public void photosLabel(String photoID) {

		for (int i = 0; i < 3; i++) {
			smallPhotos[i].setVisible(false);
		}
		largePhotoLabel.setIcon(null);
		largePhotoLabel.setText("");
		photos = IO.getPhotos(photoID);
		if (photos.length == 0) {
			largePhotoLabel.setIcon(null);
			largePhotoLabel.setText("عکسی برای این ملک موجود نیست!");
			largePhotoLabel.setFont(Resource.getFont(32f));
		} else {
			ImageIcon img = new ImageIcon(new ImageIcon(photos[largePhoto].getAbsolutePath()).getImage()
					.getScaledInstance(400, 300, Image.SCALE_DEFAULT));
			largePhotoLabel.setIcon(img);
			for (int i = 0; i < photos.length; i++) {
				smallPhotos[i].setVisible(true);
				smallPhotos[i].setFocusable(false);
				smallPhotos[i].removeActionListener(smallPhotos(i, largePhotoLabel));
				smallPhotos[i].addActionListener(smallPhotos(i, largePhotoLabel));

			}
		}
		photosPnl.repaint();
		repaint();
	}

	// -------------------------------------//PhotosActionListener//------------------------------
	/**
	 * change the main photo
	 * 
	 * @param i
	 * @param photo
	 * @param largePhotoLabel
	 * @return
	 */
	private ActionListener smallPhotos(int i, JLabel largePhotoLabel) {
		ActionListener smallPhotos = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				largePhotoLabel.removeAll();
				ImageIcon img = new ImageIcon(new ImageIcon(photos[i].getAbsolutePath()).getImage()
						.getScaledInstance(400, 300, Image.SCALE_DEFAULT));
				largePhotoLabel.setIcon(img);
				largePhoto = i;
				largePhotoLabel.revalidate();
				largePhotoLabel.repaint();
			}
		};
		return smallPhotos;
	}

	// -------------------------------------//distanceGetter//------------------------------
	/**
	 * get the measure of distance from map
	 * 
	 * @param place
	 * @param icon
	 * @param placeName
	 * @return
	 */
	private String[] distance(String place, String icon, String placeName) {
		String[] placeInfo = new String[2];
		map.nearbyFinder(place, Resource.getIcon(icon), false);
		for (int i = 0; i < 15; i++) {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
			}
			if (map.getDistance() != 0.0) {
				break;
			}
		}
		if (map.getDistance() != 0.0 && map.getDistance() != -1) {
			placeInfo[0] = ("با اولین " + placeName + " کم تر از  " + distance() + "فاصله دارد ");
			placeInfo[1] = ("تعداد " + placeName + " موجود در شعاع یک کیلومتری بیش از:"
					+ map.getRequestedNearby().length + " است.");
			repaint();
			return placeInfo;
		} else if (map.getDistance() == 0.0) {
			JOptionPane.showMessageDialog(null, "اتصال شما قطع شده یا سرعت اینترنت پایین است!");
		} else if (map.getDistance() == -1) {
			placeInfo[0] = "در شعاع یک کیلومتری موردی یافت نشد";
		}
		return placeInfo;
	}

	// ----------------------------------------//RightPanel//------------------------------------------
	/**
	 * right panel includes map in bottom and JRadioButton Panel in top
	 * 
	 * @return
	 */
	private JPanel rightPanel() {
		JPanel rightPanel = new JPanel();
		rightPanel.setBackground(MainFrame.BACKGROUND);
		rightPanel.setMaximumSize(new Dimension(200, MainFrame.HEIGHT));
		GridBagLayout gbl = new GridBagLayout();
		rightPanel.setLayout(gbl);
		// 7 X 12 all resize with panel
		gbl.columnWidths = new int[] { 50, 50, 50, 50, 50, 50, 50 };
		gbl.rowHeights = new int[] { 20, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30 };
		gbl.columnWeights = new double[] { 100d, 100d, 100d, 100d, 100d, 100d, 100d };
		gbl.rowWeights = new double[] { 10.0d, 100d, 100d, 100d, 100d, 100d, 100d, 100d, 100d, 100d, 100d, 100d };

		// ...........................................//Map//................................................
		GridBagConstraints gbc = new GridBagConstraints(0, 4, 7, 8, 0.0d, 0.0d, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(4, 4, 4, 4), 0, 0);

		map = GUIManager.getDetailMap();
		map.finder(location, true);
		rightPanel.add(map, gbc);

		// ...........................................//upperPanel//................................................
		gbc.gridy = 0;
		gbc.gridheight = 4;
		rightPanel.add(upRightPanel(), gbc);

		return rightPanel;
	}

	// -------------------------------------------//PlacesList//-----------------------------------------------
	/**
	 * Up right panel that includes JRadioButtons
	 * 
	 * @return
	 */
	private JPanel upRightPanel() {
		JPanel markerShow = new JPanel();
		GridBagLayout gbl = new GridBagLayout();
		gbl.rowHeights = new int[] { 40, 40, 40, 40 };
		gbl.columnWidths = new int[] { 75, 75, 75, 75 };
		gbl.columnWeights = new double[] { 100d, 100d, 100d, 100d };
		gbl.rowWeights = new double[] { 100d, 100d, 100d, 100d };
		markerShow.setLayout(gbl);
		markerShow.setBackground(MainFrame.BACKGROUND);
		GridBagConstraints gbc = new GridBagConstraints(2, 0, 2, 1, 0.0d, 0.0d, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(4, 4, 4, 4), 0, 0);
		JRadioButton place[] = places();
		ButtonGroup allInOne = new ButtonGroup();
		for (int i = 0; i < place.length; i++) {
			markerShow.add(place[i], gbc);
			gbc.gridy++;
			allInOne.add(place[i]);
		}
		// .......................................//FindMyPlace//.................................................
		// Finder button
		gbc = new GridBagConstraints(0, 2, 2, 1, 0.0d, 0.0d, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(4, 4, 4, 4), 0, 0);
		JButton finder = new JButton("فاصله تا مکان مورد نظر");
		finder.setCursor(new Cursor(Cursor.HAND_CURSOR));
		finder.setFont(Resource.getFont(16f));
		finder.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				DistanceCalculator disAndDur = new DistanceCalculator(location);
				disAndDur.remover();
				GUIManager.getDirectionMap().traffic(true);
				disAndDur.setVisible(true);
			}
		});
		markerShow.add(finder, gbc);
		// .......................................//Feedbacks//.................................................
		// Feedbacks
		gbc.gridy = 1;
		JButton feedbacks = new JButton("نظرات");
		feedbacks.setCursor(new Cursor(Cursor.HAND_CURSOR));
		feedbacks.setFont(Resource.getFont(16f));
		feedbacks.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				FeedbackFrame feedbackFrame = new FeedbackFrame(HomeDAO.getInstance().getHouseReviews(home.getHomeID()),
						home.getHomeID());
				feedbackFrame.setVisible(true);
			}
		});
		markerShow.add(feedbacks, gbc);
		return markerShow;
	}

	// -------------------------------------------//PlacesList//-----------------------------------------------
	/**
	 * places JRadio button
	 * 
	 * @return
	 */
	private JRadioButton[] places() {
		JRadioButton[] places = new JRadioButton[5];
		places[0] = new JRadioButton("مغازه ها");
		places[0].setFont(Resource.getFont(18f));
		places[0].setCursor(new Cursor(Cursor.HAND_CURSOR));
		places[0].setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		places[0].addMouseListener(showMarkers("store", "market.png"));

		places[1] = new JRadioButton("بانک ها");
		places[1].setFont(Resource.getFont(18f));
		places[1].setCursor(new Cursor(Cursor.HAND_CURSOR));
		places[1].setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		places[1].addMouseListener(showMarkers("bank", "bank.png"));

		places[2] = new JRadioButton("پارک ها");
		places[2].setFont(Resource.getFont(18f));
		places[2].setCursor(new Cursor(Cursor.HAND_CURSOR));
		places[2].setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		places[2].addMouseListener(showMarkers("park", "park.png"));

		places[3] = new JRadioButton("ایستگاه های اتوبوس");
		places[3].setFont(Resource.getFont(18f));
		places[3].setCursor(new Cursor(Cursor.HAND_CURSOR));
		places[3].setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		places[3].addMouseListener(showMarkers("bus_station", "bus.png"));

		places[4] = new JRadioButton("ایستگاه های مترو");
		places[4].setFont(Resource.getFont(18f));
		places[4].setCursor(new Cursor(Cursor.HAND_CURSOR));
		places[4].setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		places[4].addMouseListener(showMarkers("subway_station", "subway.png"));

		return places;
	}

	// -------------------------------------------//ShowPlacesMarker//-----------------------------------------------
	/**
	 * show the marker by click by get the requested placeID
	 * 
	 * @param placeID
	 * @param iconName
	 * @return
	 */
	private MouseAdapter showMarkers(String placeID, String iconName) {
		MouseAdapter show = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				map.nearbyFinder(placeID, Resource.getIcon(iconName), true);
			}
		};
		return show;
	}

	// -------------------------------------------//Recover//-----------------------------------------------
	/**
	 * recover the map
	 */
	public void recover() {

		map.markersCleaner(map.getInDetailLocation());
		map.traffic(false);
		map.pinUsage = false;
		editStatus = false;
		largePhoto = 0;

	}

	// -------------------------------------------//reNew//-----------------------------------------------
	public void mapHandler() {
		location = home.getLocation();
		map = GUIManager.getDetailMap();
		map.ZOOM = 12.0;
		map.setInDetailLocation(map.markerCreator(location));
		map.finder(location, true);
	}

	// -------------------------------------------//reNew//-----------------------------------------------
	/**
	 * recover the map
	 */
	public void reNew() {

		editStatus = false;
		mapHandler();
		Client tempClient = ClientDAO.getInstance().getClientWithOutdetails(home.getOwnerID());

		// set labels
		ownerName.setText("   نام و نام خانوادگی مالک:" + tempClient.getFirstName() + " " + tempClient.getLastName());
		ownerPhone.setText(" شماره تماس: " + tempClient.getTell());

		this.roomNumbL.setText(home.getRoomNumb() + "");
		this.meterL.setText(home.getMeter() + "");
		this.optionL.setText(home.getOption());
		this.priceL.setText(home.getCost() + "");
		this.addressL.setText(home.getAddress());

		roomNumberCB.setSelectedIndex(home.getRoomNumb());
		this.meterTF.setText(home.getMeter() + "");

		if (home.getOption().equals("رهن")) {
			optionCB.setSelectedIndex(0);

		} else if (home.getOption().equals("اجاره")) {

			optionCB.setSelectedIndex(1);

		} else if (home.getOption().equals("خرید")) {

			optionCB.setSelectedIndex(2);

		}

		this.priceTF.setText(home.getCost() + "");
		this.addressTF.setText(home.getAddress());

		nearestMarket.setText("");
		marketsNum.setText("");
		nearestBank.setText("");
		bankNum.setText("");
		// set photo
		String photoID = home.getPhotoID() + "";
		photosLabel(photoID);
		//
		info.setVisible(true);
		moreInfo.setVisible(false);
		editVisibalingManager();
		map.setSet(false);
		revalidate();
		repaint();
		// set the large photo
		largePhoto = 0;

		if (BeanResources.getInstance().isSignedIn()) {

			if (BeanResources.getInstance().getSignedUpClient().getId() == home.getHomeID()) {

				editBtn.setVisible(true);
			} else {

				editBtn.setVisible(false);

			}

		} else {

			editBtn.setVisible(false);

		}

		if (BeanResources.getInstance().generateUsMeAd() == 3) {

			editBtn.setVisible(true);

		}

	}

	private boolean update() {

		if (roomNumbL.getText().trim().equals(roomNumberCB.getSelectedItem().toString().trim())
				&& meterL.getText().trim().equals(meterTF.getText().trim())
				&& optionL.getText().trim().equals(optionCB.getSelectedItem().toString().trim())
				&& priceL.getText().trim().equals(priceTF.getText().trim())
				&& addressL.getText().trim().equals(addressTF.getText().trim())) {

			return true;

		} else {

			String error = errorFinder();
			if (error.equals("")) {

				home.setAddress(addressTF.getText().trim());
				home.setRoomNumb(roomNumberCB.getSelectedIndex());
				home.setMeter(Integer.parseInt(meterTF.getText().trim()));
				home.setAddress(addressTF.getText().trim());
				home.setCost(Integer.parseInt(priceTF.getText().trim()));
				home.setCity(cityTF.getText().trim());
				if (map.pinUsage && map.getSet()) {
					home.setLocation(map.getPinLocation().getPosition());
				}

				// update data base

				if (HomeDAO.getInstance().updateHouseInformation(home)) {

					JOptionPane.showMessageDialog(null, "تغییرات با موفقیت انجام شد!", "",
							JOptionPane.INFORMATION_MESSAGE);
					return true;

				} else {

					JOptionPane.showMessageDialog(null, "عدم ثبت در پایگاه داده", "خطا", JOptionPane.ERROR_MESSAGE);
					return false;

				}

			} else {

				JOptionPane.showMessageDialog(null, error, "خطا", JOptionPane.ERROR_MESSAGE);
				return false;

			}

		}

	}

	private String errorFinder() {
		String error = "";
		int i = 1;

		// ---------- error handling -------------
		// if user not signed in

		// if city name is empty
		if (cityTF.getText().equals("")) {

			error += "\r\n" + i + "لطفا شهر محل خانه را وارد کنید";
			i++;
		}

		// if house meter is empty
		if (meterTF.getText().equals("")) {

			error += "\r\n" + i + "لطفا متراژ خانه را وارد کنید";
			i++;
		}

		// if price is empty
		if (priceTF.getText().equals("")) {

			error += "\r\n" + i + "لطفا قیمت خانه را وارد کنید";
			i++;
		}

		// if address is empty
		if (addressTF.getText().equals("")) {

			error += "\r\n" + i + "لطفا ادرس خانه را وارد کنید";
			i++;
		}

		// if user doesn't set pin on map
		if (map.getSet() == false) {

			error += "\r\n" + i + "لطفا مکان خانه را بر روی نقشه مشخص کنید";
			i++;
		}

		return error;
	}

	private void editVisibalingManager() {

		if (editStatus) {

			roomNumbL.setVisible(false);
			meterL.setVisible(false);
			priceL.setVisible(false);
			addressL.setVisible(false);
			optionL.setVisible(false);
			homeDetail.setVisible(false);
			cityL.setVisible(false);

			roomNumberCB.setVisible(true);
			meterTF.setVisible(true);
			priceTF.setVisible(true);
			addressTF.setVisible(true);
			optionCB.setVisible(true);
			changePin.setVisible(true);
			cityTF.setVisible(true);

		} else {

			roomNumbL.setVisible(true);
			meterL.setVisible(true);
			priceL.setVisible(true);
			addressL.setVisible(true);
			optionL.setVisible(true);
			homeDetail.setVisible(true);
			cityL.setVisible(true);

			roomNumberCB.setVisible(false);
			meterTF.setVisible(false);
			priceTF.setVisible(false);
			addressTF.setVisible(false);
			optionCB.setVisible(false);
			changePin.setVisible(false);
			cityTF.setVisible(false);

		}

		// if (previousPanel == 7) {
		//
		// editBtn.setVisible(true);
		// editBtn.setText("ویرایش");
		//
		// } else {
		//
		// editBtn.setVisible(false);
		//
		// }
	}

}
