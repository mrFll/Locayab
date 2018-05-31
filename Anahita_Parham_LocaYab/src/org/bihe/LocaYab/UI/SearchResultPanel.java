package org.bihe.LocaYab.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.bihe.LocaYab.Controller.GUIManager;
import org.bihe.LocaYab.IO.IO;
import org.bihe.LocaYab.bean.BeanResources;
import org.bihe.LocaYab.bean.Home;
import org.bihe.LocaYab.jdbc.DBDAO.ClientDAO;
import org.bihe.LocaYab.jdbc.DBDAO.HomeDAO;

import com.teamdev.jxmaps.LatLng;

import Resource.Resource;

/**
 * 
 * @author @Parham 8/1/2017 4:21PM
 * 
 * @author @Anahita
 *
 */
public class SearchResultPanel extends JPanel {

	// -------------------------------------//Fields//-------------------------------------
	private static final long serialVersionUID = 1L;
	public static LatLng location;
	public static JTextField cityName;
	public static JComboBox<String> sellingType;
	private JPanel housesPhoto;
	private ArrayList<Home> searchedHouses;
	private MapPanel map;

	// ----------------------------------//Constructor//-----------------------------------
	public SearchResultPanel() {

		searchedHouses = new ArrayList<>();
		// add setting to main panel
		mainPanelSetting();
		//
		JSplitPane split = new JSplitPane();
		split.setContinuousLayout(true);
		split.setDividerLocation(800);
		split.setResizeWeight(1);

		split.setLeftComponent(leftPanel());
		split.setRightComponent(rightPanel());

		this.add(split);
	}

	// -------------------------------------//MainPanelSetting//--------------------------------------

	/**
	 * @author @Parham 8/1/2017 4:21PM
	 */
	private void mainPanelSetting() {

		this.setLayout(new BorderLayout());
		this.setBackground(MainFrame.BACKGROUND);

	}

	// -------------------------------------//addLeftPanel//--------------------------------------
	/**
	 * @author @Parham 8/1/2017 4:21PM
	 */
	private JPanel leftPanel() {

		JPanel leftPanel = new JPanel();
		leftPanel.setBackground(MainFrame.BACKGROUND);
		leftPanel.setMinimumSize(new Dimension(770, MainFrame.HEIGHT));
		leftPanel.setLayout(new GridBagLayout());
		GridBagLayout gbl = new GridBagLayout();
		leftPanel.setLayout(gbl);

		// 7 X 12 all resize with panel
		gbl.columnWidths = new int[] { 60, 60, 60, 60, 60, 60, 60 };
		gbl.rowHeights = new int[] { 20, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30 };
		gbl.columnWeights = new double[] { 100d, 100d, 100d, 100d, 100d, 100d, 100d };
		gbl.rowWeights = new double[] { 10.0d, 100d, 100d, 100d, 100d, 100d, 100d, 100d, 100d, 100d, 100d, 100d };

		// panel setting
		this.housesPhoto = new JPanel();
		this.housesPhoto.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		this.housesPhoto.setBackground(MainFrame.BACKGROUND);
		this.housesPhoto.setLayout(new BoxLayout(housesPhoto, BoxLayout.Y_AXIS));

		// add and handle scroll pane
		JScrollPane scPane = new JScrollPane();
		scPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scPane.setViewportView(housesPhoto);

		// make position for panel in main panel
		GridBagConstraints gbc = new GridBagConstraints(0, 1, 7, 12, 0.0d, 0.0d, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(4, 4, 4, 4), 0, 0);
		leftPanel.add(scPane, gbc);

		// adding search again panel to top of panel
		gbc.gridy = 0;
		gbc.gridheight = 1;
		leftPanel.add(SearchSettingPanel(), gbc);

		// get data from data base and show search result on the panel
		doSearch();
		return leftPanel;
	}

	// -------------------------------------//addrightPanel//--------------------------------------
	/**
	 * this method add map to main panel
	 * 
	 * @author @Parham 8/1/2017 4:21PM
	 * 
	 */
	private JPanel rightPanel() {
		JPanel rightPanel = new JPanel();
		rightPanel.setBackground(MainFrame.BACKGROUND);
		rightPanel.setMaximumSize(new Dimension(200, MainFrame.HEIGHT));
		rightPanel.setLayout(new GridBagLayout());
		GridBagLayout gbl = new GridBagLayout();
		rightPanel.setLayout(gbl);
		// 7 X 12 all resize with panel
		gbl.columnWidths = new int[] { 50, 50, 50, 50, 50, 50, 50 };
		gbl.rowHeights = new int[] { 20, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30 };
		gbl.columnWeights = new double[] { 100d, 100d, 100d, 100d, 100d, 100d, 100d };
		gbl.rowWeights = new double[] { 10.0d, 100d, 100d, 100d, 100d, 100d, 100d, 100d, 100d, 100d, 100d, 100d };

		// adding Map
		GridBagConstraints gbc = new GridBagConstraints(0, 4, 7, 8, 0.0d, 0.0d, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(4, 4, 4, 4), 0, 0);
		map = GUIManager.getResultMap();
		mapHandler();
		rightPanel.add(map, gbc);

		// adding Filter Panel

		gbc.gridy = 0;
		gbc.gridheight = 4;
		rightPanel.add(filter(), gbc);

		return rightPanel;

	}

	// -------------------------------------//MapHandler//--------------------------------------
	/**
	 * 
	 */
	private void mapHandler() {
		map.mapRemover(false);
		map = GUIManager.getResultMap();
		if (cityName.getText().equals("ایران")) {
			map.ZOOM = 5.0;
		} else {
			map.ZOOM = 10.0;
		}

		map.setLocation(BeanResources.getInstance().getCityNameSearch().trim());
		// get the locations
		LatLng[] locations = new LatLng[searchedHouses.size()];
		for (int i = 0; i < searchedHouses.size(); i++) {
			Home home = searchedHouses.get(i);
			locations[i] = home.getLocation();
		}
		map.markersCleaner(map.propertiesPin);
		map.markerCreator(locations);
		map.markerShow(map.propertiesPin);
		map.finder(BeanResources.getInstance().getCityNameSearch().trim());
	}

	// -------------------------------------//addSerachPanel//--------------------------------------
	/**
	 * this method add search setting panel to main panel
	 * 
	 * @author @Parham 8/1/2017 4:21PM
	 */
	private JPanel SearchSettingPanel() {

		JPanel searchPanel = new JPanel();
		searchPanel.setBackground(MainFrame.BACKGROUND);
		searchPanel.setLayout(new FlowLayout());

		// add buttons, text fields , ...
		addSerachPanelOptions(searchPanel);

		return searchPanel;
	}

	// -------------------------------------//addOptionsToSearchPanel//--------------------------------------
	/**
	 * this method add things to search panel like buttons or text fields ...
	 * 
	 * @author @Parham created 8/4/2017 9:50 AM
	 */
	private void addSerachPanelOptions(JPanel searchPanel) {
		cityName = new JTextField("شهر را وارد کنید", SwingConstants.CENTER);
		cityName.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		cityName.setPreferredSize(new Dimension(500, 40));
		cityName.setToolTipText("به زبان فارسی وارد کنید!");
		cityName.setBackground(MainFrame.FIELD);
		cityName.setFont(Resource.getFont(20f));
		searchPanel.add(cityName);

		// selling type filter in search
		String sellingTypeOptions[] = { "رهن", "اجاره", "خرید" };

		sellingType = new JComboBox<>(sellingTypeOptions);
		sellingType.setPreferredSize(new Dimension(100, 40));
		sellingType.setFont(Resource.getFont(22f));
		DefaultListCellRenderer dlcr = new DefaultListCellRenderer();
		dlcr.setHorizontalAlignment(4);
		sellingType.setRenderer(dlcr);
		sellingType.setToolTipText("نوع معامله");
		searchPanel.add(sellingType);

		// search button in search panel
		JButton searchBtn = new JButton("جستجو");
		searchBtn.setPreferredSize(new Dimension(100, 40));
		searchBtn.setFont(Resource.getFont(22f));

		// when the user search again in this panel
		searchBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				BeanResources.getInstance().setCityNameSearch(cityName.getText());
				BeanResources.getInstance().setOptionNameSearch(sellingType.getSelectedItem().toString());

				GUIManager.addsearchResultToFrame();
			}
		});

		searchPanel.add(searchBtn);

	}

	// -------------------------------------//CityNameListener//--------------------------------------
	public void jlabelListener() {
		cityName.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent arg0) {
				if (cityName.getText().equals("")) {
					cityName.setText("شهر را وارد کنید");
				}

			}

			@Override
			public void focusGained(FocusEvent arg0) {
				if (cityName.getText().equals("شهر را وارد کنید")) {
					cityName.setText("");
				}

			}
		});
	}

	// -------------------------------------//addFilterPanel//--------------------------------------
	/**
	 * 
	 * @author @Anahita 8/1/2017 11:28PM
	 * 
	 */
	private static JPanel filter() {
		JPanel filter = new JPanel();
		GridBagLayout gbl = new GridBagLayout();
		gbl.rowHeights = new int[] { 10, 10, 10, 10, 10, 10, 10, 10 };
		gbl.rowWeights = new double[] { 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d };
		//
		gbl.columnWidths = new int[] { 20, 20, 20, 20, 20, 20, 20, 20, 20, 20 };
		gbl.columnWeights = new double[] { 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d };

		filter.setLayout(gbl);
		filter.setBackground(MainFrame.BACKGROUND);

		GridBagConstraints gbc = new GridBagConstraints(0, 1, 4, 1, 0.0d, 0.0d, GridBagConstraints.LINE_END,
				GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0);

		// add order by column combo box
		String orderingOption[] = { "قیمت", "متراژ", "تعداد خواب" };

		JComboBox<String> orderFilter = new JComboBox<>(orderingOption);
		orderFilter.setFont(Resource.getFont(19f));
		DefaultListCellRenderer dlcr = new DefaultListCellRenderer();
		dlcr.setHorizontalAlignment(4);
		orderFilter.setRenderer(dlcr);

		gbc.gridx = 2;
		gbc.gridy = 4;
		gbc.gridwidth = 3;
		gbc.gridheight = 2;

		filter.add(orderFilter, gbc);

		// add DESC
		JLabel descLabel = new JLabel();
		descLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		// add ASC
		JLabel ascLabel = new JLabel();
		ascLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		// ASC detail
		ascLabel.setToolTipText("صعودی");
		if (BeanResources.getInstance().isAscDESC()) {
			ascLabel.setIcon(
					new ImageIcon(Resource.getImage("ascFill.png").getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
		} else {
			ascLabel.setIcon(new ImageIcon(Resource.getImage("asc.png").getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
		}

		ascLabel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				ascLabel.setIcon(
						new ImageIcon(Resource.getImage("ascFill.png").getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
				descLabel.setIcon(
						new ImageIcon(Resource.getImage("desc.png").getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
				BeanResources.getInstance().setAscDESC(true);
			}
		});

		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		gbc.gridheight = 2;

		filter.add(ascLabel, gbc);

		// DESC details
		descLabel.setToolTipText("نزولی");
		if (BeanResources.getInstance().isAscDESC()) {
			descLabel.setIcon(
					new ImageIcon(Resource.getImage("desc.png").getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
		} else {
			descLabel.setIcon(
					new ImageIcon(Resource.getImage("descFill.png").getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
		}

		descLabel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				ascLabel.setIcon(
						new ImageIcon(Resource.getImage("asc.png").getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
				descLabel.setIcon(
						new ImageIcon(Resource.getImage("descFill.png").getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
				BeanResources.getInstance().setAscDESC(false);
			}
		});

		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		gbc.gridheight = 2;

		filter.add(descLabel, gbc);

		// add label
		JLabel byorderString = new JLabel("ترتیب :", JLabel.LEFT);
		byorderString.setFont(Resource.getFont(19f));

		gbc.gridx = 5;
		gbc.gridy = 4;
		gbc.gridwidth = 5;
		gbc.gridheight = 2;

		filter.add(byorderString, gbc);

		// // add Max and Min combo box
		// String minMaxString[] = { "حداکثر", "حداقل" };
		//
		// JComboBox<String> minMaxCom = new JComboBox<>(minMaxString);
		// minMaxCom.setFont(Resource.getFont(19f));
		// minMaxCom.setRenderer(dlcr);
		//
		// gbc.gridx = 7;
		// gbc.gridy = 1;
		// gbc.gridwidth = 3;
		// gbc.gridheight = 2;
		//
		// filter.add(minMaxCom, gbc);
		//
		// // add order by column for max and min combo box
		//
		// JComboBox<String> orderFilterMxMi = new JComboBox<>(orderingOption);
		// orderFilterMxMi.setFont(Resource.getFont(19f));
		// orderFilterMxMi.setRenderer(dlcr);
		//
		// gbc.gridx = 4;
		// gbc.gridy = 1;
		// gbc.gridwidth = 3;
		// gbc.gridheight = 2;
		//
		// filter.add(orderFilterMxMi, gbc);
		//
		// // add price label
		// JLabel price = new JLabel("", JLabel.LEFT);
		// price.setFont(Resource.getFont(19f));
		//
		// gbc.gridx = 0;
		// gbc.gridy = 1;
		// gbc.gridwidth = 4;
		// gbc.gridheight = 2;
		//
		// filter.add(price, gbc);
		//
		// // add price filter
		//
		// gbc.gridx = 0;
		// gbc.gridy = 3;
		// gbc.gridwidth = 10;
		// gbc.gridheight = 1;
		// filter.add(priceFilter(filter, price), gbc);

		// add filter icon
		JLabel doFilters = new JLabel();
		doFilters.setToolTipText("اعمال فیلتر ");
		doFilters.setCursor(new Cursor(Cursor.HAND_CURSOR));
		doFilters.setIcon(new ImageIcon(Resource.getImage("filter.png").getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
		doFilters.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				doFilters.setIcon(
						new ImageIcon(Resource.getImage("filter.png").getScaledInstance(40, 40, Image.SCALE_SMOOTH)));

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				doFilters.setIcon(new ImageIcon(
						Resource.getImage("filterFill.png").getScaledInstance(40, 40, Image.SCALE_SMOOTH)));

			}

			@Override
			public void mouseClicked(MouseEvent e) {

				BeanResources.getInstance().setOrderFilterSearch(orderFilter.getSelectedItem().toString());

				GUIManager.addsearchResultToFrame();
			}
		});

		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.gridwidth = 2;
		gbc.gridheight = 2;

		filter.add(doFilters, gbc);

		return filter;
	}

	// -------------------------------------//HomeDetailManagement//--------------------------------------
	/**
	 * this method make little homes detail to add in house photo panel
	 * 
	 * @author @Parham 8/4/2017 3:03 PM
	 * @param home
	 * @return panel contain homes detail
	 */

	private JPanel makeLittleHomeDetailPanel(Home home) {

		JPanel homePresent = new JPanel();

		// add panel setting
		homePresent.setLayout(createLittleHomeGridBagLayout());
		homePresent.setBackground(MainFrame.BACKGROUND);
		homePresent.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		homePresent.setPreferredSize(new Dimension(350, 250));

		// make position

		homeDetailInfo(homePresent, home);

		return homePresent;

	}

	// -------------------------------------//HomeDetaiInfo//--------------------------------------
	/**
	 * little Info about home
	 * 
	 * @param homePresent
	 * @param gbc
	 * @param home
	 */
	private void homeDetailInfo(JPanel homePresent, Home home) {

		GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 0.0d, 0.0d, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(4, 4, 4, 4), 0, 0);

		// --------------- picture ----------------------
		gbc.gridx = 1;
		gbc.gridy = 0;
		File[] photos = IO.getPhotos(home.getPhotoID() + "");
		if (photos.length != 0) {
			gbc.gridwidth = 6;
			gbc.gridheight = 5;
			for (int i = 0; i < photos.length; i++) {
				JLabel pictures = new JLabel("", JLabel.CENTER);
				ImageIcon img = new ImageIcon(new ImageIcon(photos[i].getAbsolutePath()).getImage()
						.getScaledInstance(150, 150, Image.SCALE_DEFAULT));
				pictures.setIcon(img);
				homePresent.add(pictures, gbc);
				gbc.gridx += 6;
			}
		} else {
			gbc.gridwidth = 18;
			gbc.gridheight = 9;
			JLabel pictures = new JLabel("عکسی برای این ملک موجود نیست!", JLabel.CENTER);
			pictures.setFont(Resource.getFont(32f));
			homePresent.add(pictures, gbc);
		}

		// ------------------ price ----------------------
		JLabel price = new JLabel(home.getCost() + " تومان ", SwingConstants.CENTER);
		price.setFont(Resource.getFont(20f));
		gbc.gridx = 1;
		gbc.gridy = 8;
		gbc.gridwidth = 5;
		gbc.gridheight = 2;
		homePresent.add(price, gbc);

		// ------------------ room ----------------------
		JLabel roomNumb = new JLabel(home.getRoomNumb() + "خوابه ", SwingConstants.CENTER);
		roomNumb.setFont(Resource.getFont(20f));
		gbc.gridx = 9;
		gbc.gridy = 8;
		gbc.gridwidth = 3;
		gbc.gridheight = 2;
		homePresent.add(roomNumb, gbc);

		// ------------------ meter ----------------------
		JLabel metter = new JLabel(home.getMeter() + "متری", SwingConstants.CENTER);
		metter.setFont(Resource.getFont(20f));
		gbc.gridx = 12;
		gbc.gridy = 8;
		gbc.gridwidth = 3;
		gbc.gridheight = 2;
		homePresent.add(metter, gbc);

		// ------------------ Address ----------------------
		JLabel address = new JLabel(home.getAddress(), SwingConstants.RIGHT);
		address.setFont(Resource.getFont(18f));
		gbc.gridx = 1;
		gbc.gridy = 10;
		gbc.gridwidth = 18;
		gbc.gridheight = 4;
		homePresent.add(address, gbc);

		// ------------------ Option ----------------------
		JLabel houseOption = new JLabel(home.getOption(), SwingConstants.CENTER);
		houseOption.setFont(Resource.getFont(20f));
		gbc.gridx = 6;
		gbc.gridy = 8;
		gbc.gridwidth = 3;
		gbc.gridheight = 2;
		homePresent.add(houseOption, gbc);

		// ------------------ City ----------------------
		JLabel houseCity = new JLabel(home.getCity(), SwingConstants.CENTER);
		houseCity.setFont(Resource.getFont(20f));
		gbc.gridx = 15;
		gbc.gridy = 8;
		gbc.gridwidth = 4;
		gbc.gridheight = 2;
		homePresent.add(houseCity, gbc);

		// ----------------- pin ----------------------

		JLabel pinned = new JLabel();

		// checking signed in user house and show pins about it
		checkUserPinnedHouse(pinned, home.getHomeID());

		pinned.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				pinEventHandle(pinned, home);
			}
		});
		gbc.gridx = 19;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 2;
		homePresent.add(pinned, gbc);

		// ---------------- panel action listener --------------------

		homePresent.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				GUIManager.addHomeDetailsToFrame(home, 4);

			}

		});

		// -------------------//AdminDelete//------------------------

		JLabel delete = new JLabel();
		delete.setIcon(new ImageIcon(Resource.getImage("remove.png").getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
		delete.setCursor(new Cursor(Cursor.HAND_CURSOR));
		if (BeanResources.getInstance().generateUsMeAd() != 3) {
			delete.setVisible(false);
		}
		delete.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				int exit = JOptionPane.showOptionDialog(null, "آیا میخواهید ملک را حذف کنید؟", "حذف ملک", 0,
						JOptionPane.INFORMATION_MESSAGE, null, new String[] { "بلی", "خیر" }, null);

				if (exit == JOptionPane.YES_OPTION) {

					if (HomeDAO.getInstance().deleteHouse(home.getHomeID())) {

						BeanResources.getInstance().getSignedUpClient().getOwnHomes().remove(home);
						doSearch();
					} else {

						JOptionPane.showMessageDialog(null, "عدم ثبت در پایگاه داده ", "خطا",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		gbc.gridx = 19;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 2;
		homePresent.add(delete, gbc);

	}

	/**
	 * this method handle the
	 * 
	 * @param pin
	 */
	private void pinEventHandle(JLabel pin, Home home) {

		if (BeanResources.getInstance().isSignedIn()) {
			// if user signed in to the system

			if (pin.getName().equals("1")) {
				// if it pinned before

				if (ClientDAO.getInstance().deletePin(BeanResources.getInstance().getSignedUpClient().getId(),
						home.getHomeID())) {

					BeanResources.getInstance().getSignedUpClient().getPinnedHome().remove(home);

					pin.setIcon(new ImageIcon(
							Resource.getImage("upinned.png").getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
					pin.setName("0");

				} else {

					JOptionPane.showMessageDialog(null, "عدم ثبت در پایگاه داده", "خطا", JOptionPane.ERROR_MESSAGE);

				}

			} else if (pin.getName().equals("0")) {
				// if it not pinned before

				if (ClientDAO.getInstance().addNewPin(BeanResources.getInstance().getSignedUpClient().getId(),
						home.getHomeID())) {

					BeanResources.getInstance().getSignedUpClient().getPinnedHome().add(home);

					pin.setIcon(new ImageIcon(
							Resource.getImage("pinned.png").getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
					pin.setName("1");

				} else {

					JOptionPane.showMessageDialog(null, "عدم ثبت در پایگاه داده", "خطا", JOptionPane.ERROR_MESSAGE);

				}

			}

		} else {

			// if user didn't signed in to the system
			GUIManager.showSignInOptionPane();
		}

	}

	// -------------------------------------//recover//--------------------------------------

	/**
	 * when we come back to panel this method recover the data in the panel
	 * 
	 * @author @Parham
	 */
	public void recover() {

		// search again
		housesPhoto.removeAll();
		housesPhoto.repaint();
		doSearch();

		if (map.propertiesPin != null) {
			map.markerShow(map.propertiesPin);
		}

		// fill search city name with String that user searches
		cityName.setText(BeanResources.getInstance().getCityNameSearch().trim());

		// set house selling type with index that user searches
		String temp = BeanResources.getInstance().getOptionNameSearch().trim();

		if (temp.trim().equals("رهن") || temp.trim().equals("")) {

			sellingType.setSelectedIndex(0);

		} else if (temp.trim().equals("اجاره")) {

			sellingType.setSelectedIndex(1);

		} else if (temp.trim().equals("خرید")) {

			sellingType.setSelectedIndex(2);

		}

		mapHandler();
		if (map.propertiesPin != null) {
			map.markerShow(map.propertiesPin);
		}

	}
	// -------------------------------------//HomeDetailLayout//--------------------------------------

	/**
	 * this method make the little home detail panel layout
	 * 
	 * @author @Parham 8/4/2017 6:55 PM
	 * @return
	 */
	private GridBagLayout createLittleHomeGridBagLayout() {

		// 11 X 9 all resize with panel
		GridBagLayout gbl = new GridBagLayout();

		gbl.columnWidths = new int[] { 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30 };
		gbl.rowHeights = new int[] { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 };
		gbl.columnWeights = new double[] { 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d,
				10d, 10d, 10d, 10d, 10d };
		gbl.rowWeights = new double[] { 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d, 10d };

		return gbl;

	}

	/**
	 * this method get data from data base & process them and add them to panel
	 * 
	 * @author @Parham 8/11/2017 10:49 PM
	 */
	public void doSearch() {

		if (BeanResources.getInstance().getOptionNameSearch() == "") {

			searchedHouses = HomeDAO.getInstance()
					.getHousesFilterByCity(BeanResources.getInstance().getCityNameSearch());

		} else {

			searchedHouses = HomeDAO.getInstance().getHousesFilterByOptionAndCity(
					BeanResources.getInstance().getOptionNameSearch(), BeanResources.getInstance().getCityNameSearch());
		}

		housesPhoto.removeAll();
		// show houses in the housePhoto panel
		for (Home home : searchedHouses) {
			this.housesPhoto.add(makeLittleHomeDetailPanel(home));
		}

	}

	/**
	 * this method check the signed in user house that he pinned before
	 * 
	 * @author @Parham
	 * @param label
	 */
	public void checkUserPinnedHouse(JLabel label, int houseId) {

		if (BeanResources.getInstance().isSignedIn()) {
			// if user signed in

			// default is he not pinned the house
			label.setIcon(
					new ImageIcon(Resource.getImage("upinned.png").getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
			label.setName("0");

			for (Home home : BeanResources.getInstance().getSignedUpClient().getPinnedHome()) {

				if (home != null && home.getHomeID() == houseId) {
					// if the user pinned the house before

					label.setIcon(new ImageIcon(
							Resource.getImage("pinned.png").getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
					label.setName("1");
				}

			}

		} else {

			// if user not signed in
			label.setIcon(
					new ImageIcon(Resource.getImage("upinned.png").getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
			label.setName("0");

		}
	}

	public ArrayList<Home> getSearchedHouses() {
		return searchedHouses;
	}

}
