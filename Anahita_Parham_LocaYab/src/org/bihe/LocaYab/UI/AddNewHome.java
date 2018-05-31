package org.bihe.LocaYab.UI;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.util.UUID;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.bihe.LocaYab.Controller.GUIManager;
import org.bihe.LocaYab.IO.IO;
import org.bihe.LocaYab.bean.BeanResources;
import org.bihe.LocaYab.jdbc.DBDAO.HomeDAO;

import com.teamdev.jxmaps.LatLng;

import Resource.Resource;

public class AddNewHome extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MapPanel map;
	private JTextField mapSearch;

	private JTextArea adressField;
	private JTextField priceField;
	private JTextField areaField;
	private JComboBox<Integer> roomNums;
	private JTextField cityField;
	private JComboBox<String> regionField;
	private JComboBox<String> houseOption;

	private JPanel photoPanel;

	/**
	 * @author @Anahita 8/1/2017 1:28AM
	 */
	public AddNewHome() {
		this.setLayout(gridBag());
		this.setBackground(MainFrame.BACKGROUND);
		GridBagConstraints gbc = new GridBagConstraints(7, 0, 2, 1, 5.0d, 5.0d, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 0, 0);

		// Logo
		JLabel locayab = new JLabel();
		locayab.setIcon(new ImageIcon(Resource.getImage("Pin.png")));
		this.add(locayab, gbc);
		// ---------------------------------/Photo//---------------------------------------

		// photo showing panel
		gbc = new GridBagConstraints(6, 6, 6, 5, 5.0d, 5.0d, GridBagConstraints.LINE_END, GridBagConstraints.BOTH,
				new Insets(1, 1, 1, 1), 0, 0);
		photoPanel = new JPanel();
		photoPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		photoPanel.setLayout(new FlowLayout());
		photoPanel.setBackground(MainFrame.FIELD);
		photoPrinter(photoPanel);
		this.add(photoPanel, gbc);
		// .................................//EnterPhoto//.......................................
		// enter new photo
		gbc = new GridBagConstraints(11, 11, 1, 1, 5.0d, 5.0d, GridBagConstraints.LINE_END,
				GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 0, 0);
		JButton enterPhoto = new JButton("ثبت عکس");
		enterPhoto.setFont(Resource.getFont(18f));
		enterPhoto.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				File sourceFile = null;
				JFrame frame = new JFrame();
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				fileChooser.showOpenDialog(frame);
				sourceFile = fileChooser.getSelectedFile();
				org.bihe.LocaYab.IO.IO.copyPhoto(sourceFile);
				photoPanel.removeAll();
				photoPrinter(photoPanel);
			}
		});
		this.add(enterPhoto, gbc);
		// .................................//Info//.......................................
		// how to choose photo (i)
		gbc.gridx = 10;
		gbc.fill = GridBagConstraints.NONE;
		this.add(
				info("بر روی دکمه ی ثبت عکس کلیک کنید و عکس دلخواهتان را انتخاب کنید!\r\n 1.فرمت عکس شما تنها باید png یا jpg باشد\r\n2.شما تنها مجاز به ثبت 3 عکس هستید\r\n(برای پاک کردن عکس روی آن کلیک کنید)"),
				gbc);

		// ---------------------------------//Region//---------------------------------------
		// Region
		gbc = new GridBagConstraints(12, 2, 1, 1, 5.0d, 5.0d, GridBagConstraints.LINE_END,
				GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 0, 0);
		JLabel region = new JLabel("استان*");
		region.setFont(Resource.getFont(18f));
		this.add(region, gbc);
		// region ComboBox
		gbc = new GridBagConstraints(11, 2, 1, 1, 5.0d, 5.0d, GridBagConstraints.LINE_END,
				GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 0, 0);
		String cities[] = { "آذربایجان شرقی", "آذربایجان غربی", "اردبیل", "اصفهان", "البرز", "ایلام", "بوشهر", "تهران",
				"چهارمحال بختیاری", "خراسان جنوبی", "خراسان رضوی", "خراسان شمالی", "خوزستان", "زنجان", "سمنان",
				"سیستان و بلوچستان", "فارس", "قزوین", "قم", "کردستان", "کرمان", "کرمانشاه", "کهگیلویه و بویراحمد",
				"گلستان", "گیلان", "مازندران", "مرکزی", "هرمزگان", "همدان", "یزد" };
		regionField = new JComboBox<>(cities);
		regionField.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		DefaultListCellRenderer dlcr = new DefaultListCellRenderer();
		dlcr.setHorizontalAlignment(4);
		regionField.setRenderer(dlcr);
		regionField.setFont(Resource.getFont(18f));
		regionField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {// change the
															// position of map
															// according to the
															// region
				map.ZOOM = 12.0;
				map.finder(regionField.getSelectedItem().toString());

			}
		});
		this.add(regionField, gbc);

		// ---------------------------------//City//---------------------------------------
		// city
		gbc.gridwidth = 1;
		gbc.gridx = 10;
		JLabel city = new JLabel("شهر*");
		city.setFont(Resource.getFont(18f));
		this.add(city, gbc);
		// city Field
		gbc = new GridBagConstraints(8, 2, 2, 1, 5.0d, 5.0d, GridBagConstraints.LINE_END, GridBagConstraints.HORIZONTAL,
				new Insets(1, 1, 1, 1), 0, 0);
		cityField = new JTextField("تهران");
		cityField.setForeground(Color.GRAY);
		cityField.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		cityField.setFont(MainFrame.fieldFont(18));
		cityField.addFocusListener(textListener("تهران", cityField));
		this.add(cityField, gbc);

		// --------------------------------//option//-------------------------------------
		// option label
		gbc = new GridBagConstraints(7, 2, 1, 1, 5.0d, 5.0d, GridBagConstraints.LINE_END, GridBagConstraints.HORIZONTAL,
				new Insets(1, 1, 1, 1), 0, 0);
		JLabel optionName = new JLabel("نوع معامله*");
		optionName.setFont(Resource.getFont(18f));
		this.add(optionName, gbc);

		// option ComboBox
		gbc.gridx = 6;
		String[] options = { "رهن", "اجاره", "خرید" };
		houseOption = new JComboBox<>(options);
		houseOption.setFont(Resource.getFont(18f));
		this.add(houseOption, gbc);

		// ---------------------------------//Area//---------------------------------------
		// Area
		gbc = new GridBagConstraints(12, 3, 1, 1, 5.0d, 5.0d, GridBagConstraints.LINE_END,
				GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 0, 0);
		JLabel area = new JLabel("متراژ*");
		area.setFont(Resource.getFont(18f));
		this.add(area, gbc);
		// Area Field
		gbc.gridx = 11;
		areaField = new JTextField("500");
		areaField.setForeground(Color.GRAY);
		areaField.setFont(MainFrame.fieldFont(18));
		areaField.addFocusListener(textListener("500", areaField));
		this.add(areaField, gbc);

		// ---------------------------------//RoomNumbers//---------------------------------------
		// Room Number
		gbc.gridx = 10;
		JLabel roomNum = new JLabel("تعداد خواب*");
		roomNum.setFont(Resource.getFont(18f));
		this.add(roomNum, gbc);
		// Room Number ComboBox
		gbc.gridx = 9;
		Integer nums[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24,
				25 };
		roomNums = new JComboBox<>(nums);
		roomNums.setFont(Resource.getFont(18f));
		this.add(roomNums, gbc);

		// ---------------------------------//Price//---------------------------------------
		// price
		gbc.gridx = 8;
		JLabel price = new JLabel("قیمت(تومان)*");
		price.setFont(Resource.getFont(18f));
		this.add(price, gbc);
		// price Field
		gbc = new GridBagConstraints(6, 3, 2, 1, 5.0d, 5.0d, GridBagConstraints.LINE_END, GridBagConstraints.HORIZONTAL,
				new Insets(1, 1, 1, 1), 0, 0);
		priceField = new JTextField("500000000");
		priceField.setForeground(Color.GRAY);
		priceField.setFont(MainFrame.fieldFont(18));
		priceField.addFocusListener(textListener("500000000", priceField));
		this.add(priceField, gbc);

		// ---------------------------------//Address//---------------------------------------
		// Address
		gbc = new GridBagConstraints(12, 4, 1, 1, 5.0d, 5.0d, GridBagConstraints.LINE_END,
				GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 0, 0);
		JLabel number = new JLabel("آدرس* ");
		number.setFont(Resource.getFont(18f));
		this.add(number, gbc);
		// address Field
		gbc = new GridBagConstraints(6, 4, 6, 2, 5.0d, 5.0d, GridBagConstraints.LINE_END, GridBagConstraints.BOTH,
				new Insets(1, 1, 1, 1), 0, 0);
		adressField = new JTextArea("");
		adressField = new JTextArea("خیابان، کوچه، پلاک");
		adressField.setForeground(Color.GRAY);
		adressField.setFont(MainFrame.fieldFont(18));
		adressField.addFocusListener(textListener("خیابان، کوچه، پلاک", adressField));
		adressField.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		adressField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		this.add(adressField, gbc);

		// ---------------------------------//Map//---------------------------------------
		// Map Finder text Field
		gbc = new GridBagConstraints(3, 2, 3, 1, 5.0d, 5.0d, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(4, 4, 4, 4), 0, 0);
		mapSearch = new JTextField("شهر، خیابان");
		mapSearch.setForeground(Color.GRAY);
		mapSearch.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		mapSearch.setFont(Resource.getFont(18f));
		mapSearch.addFocusListener(textListener("شهر، خیابان", mapSearch));
		this.add(mapSearch, gbc);

		// edit pinLocation
		gbc = new GridBagConstraints(1, 11, 2, 1, 5.0d, 5.0d, GridBagConstraints.LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 0, 0);
		JButton edit = new JButton("ویرایش مکان نما");
		edit.setFont(Resource.getFont(18f));
		edit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				map.setSet(false);
				if (map.getPinLocation() != null) {
					map.getPinLocation().remove();
					map.setPinLocation(null);
				}
			}
		});
		this.add(edit, gbc);
		// Find Button
		gbc = new GridBagConstraints(2, 2, 1, 1, 5.0d, 5.0d, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(4, 4, 4, 4), 0, 0);
		JButton find = new JButton("جستجو");
		find.setFont(Resource.getFont(18f));
		find.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				map.finder(mapSearch.getText());
			}
		});
		this.add(find, gbc);

		// how to use map
		gbc.gridx = 1;
		gbc.fill = GridBagConstraints.NONE;
		this.add(
				info("برای راحتی در یافتن مکان مورد نظر میتوانید نام خیابان را جستجو کنید!\r\n 1.برای پین کردن مکان خانه روی محل مورد نظر کلیک کنید\r\n2.برای اصلاح محل پین روی آن کلیک کرد و مجددا مکان مورد نظر را انتخاب کنید)"),
				gbc);

		// Main Map
		gbc = new GridBagConstraints(1, 3, 5, 8, 5.0d, 5.0d, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(4, 4, 4, 4), 0, 0);

		map = GUIManager.getNewHomeMap();
		this.add(map, gbc);

		// ---------------------------------//AddButton//---------------------------------------
		JButton add = new JButton("ثبت");
		add.setFont(Resource.getFont(26f));
		gbc = new GridBagConstraints(7, 12, 2, 1, 5.0d, 5.0d, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(4, 4, 4, 4), 0, 0);
		add.setPreferredSize(new Dimension(250, 70));
		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				// ***____Add new house to data base____***
				addingNewHome();
			}
		});
		this.add(add, gbc);

	}

	// ---------------------------------//GridBagLayout//---------------------------------------
	// GridBagLayout Handle
	private GridBagLayout gridBag() {
		GridBagLayout gbl = new GridBagLayout();
		gbl.columnWidths = new int[] { 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50 };
		gbl.rowHeights = new int[] { 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50 };
		//
		gbl.columnWeights = new double[] { 5.0d, 5.0d, 5.0d, 5.0d, 5.0d, 5.0d, 5.0d, 5.0d, 5.0d, 5.0d, 5.0d, 5.0d, 5.0d,
				5.0d };
		gbl.rowWeights = new double[] { 5.0d, 5.0d, 5.0d, 5.0d, 5.0d, 5.0d, 5.0d, 5.0d, 5.0d, 5.0d, 5.0d, 5.0d, 5.0d,
				5.0d };

		return gbl;
	}

	// ----------------------------------//textFocusListener//-------------------------------------
	/**
	 * JTextFields focusListener :set the text
	 * 
	 * @param number
	 * @return
	 */
	private FocusListener textListener(String text, JTextField field) {
		FocusListener focusListener = new FocusListener() {
			public void focusLost(FocusEvent arg0) {// set the main text if
													// there is not anything in
													// it
				if (field.getText().trim().equals("")) {
					field.setText(text);
					field.setForeground(Color.GRAY);
				}
			}

			@Override
			public void focusGained(FocusEvent arg0) {// erase the default text
				if (field.getText().trim().equals(text)) {
					field.setText("");
					field.setForeground(Color.BLACK);
				}
			}
		};
		return focusListener;
	}

	/**
	 * JTextFields focusListener :set the text
	 * 
	 * @param number
	 * @return
	 */
	private FocusListener textListener(String text, JTextArea field) {
		FocusListener focusListener = new FocusListener() {
			public void focusLost(FocusEvent arg0) {// set the main text if
													// there is not anything in
													// it
				if (field.getText().trim().equals("")) {
					field.setText(text);
					field.setForeground(Color.GRAY);
				}
			}

			@Override
			public void focusGained(FocusEvent arg0) {// erase the default text
				if (field.getText().trim().equals(text)) {
					field.setText("");
					field.setForeground(Color.BLACK);
				}
			}
		};
		return focusListener;
	}

	// -----------------------------------------//photoPrinter//------------------------------------------------
	/**
	 * print the photos in the panel
	 * 
	 * @param photoPanel
	 */
	private void photoPrinter(JPanel photoPanel) {
		File[] photos = IO.photoFiles();
		if (photos.length != 0) {
			JButton photosBtn[] = new JButton[photos.length];
			for (int i = 0; i < photos.length; i++) {
				photosBtn[i] = new JButton();
				photosBtn[i].setPreferredSize(new Dimension(200, 200));
				ImageIcon img = new ImageIcon(new ImageIcon(photos[i].getAbsolutePath()).getImage()
						.getScaledInstance(200, 200, Image.SCALE_DEFAULT));
				photosBtn[i].setIcon(img);
				photosBtn[i].addActionListener(photoEditor(photosBtn[i], i, photoPanel));
				photoPanel.add(photosBtn[i]);
				photoPanel.revalidate();
				photoPanel.repaint();
			}
		}
	}

	// --------------------------------------//photoEditor//------------------------------------------
	/**
	 * edits the available photos and delete them if it is needed
	 * 
	 * @param photo
	 * @param photoNum
	 * @param photoPanel
	 * @return
	 */
	private ActionListener photoEditor(JButton photo, int photoNum, JPanel photoPanel) {
		ActionListener photoEdit = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int exit = JOptionPane.showOptionDialog(null, "آیا میخواهید عکس را حذف کنید؟", "حذف عکس", 0,
						JOptionPane.INFORMATION_MESSAGE, null, new String[] { "بلی", "خیر" }, null);
				if (exit == JOptionPane.YES_OPTION) {
					File[] files = IO.photoFiles();
					IO.erasePhoto(files[photoNum]);
					photoPanel.remove(photo);
					photoPanel.removeAll();
					photoPrinter(photoPanel);
					photoPanel.revalidate();
					photoPanel.repaint();
				}
			}
		};
		return photoEdit;
	}

	// ------------------------------------------------------------------------------------------
	/**
	 * 
	 */
	private static JButton info(String tipText) {
		JButton info = new JButton("i");
		info.setPreferredSize(new Dimension(35, 35));
		info.setBackground(Color.WHITE);
		info.setFont(new Font("Arial", Font.ITALIC, 15));
		info.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, tipText);
			}
		});
		return info;
	}

	// ------------------------------------------------------------------------------------------
	/**
	 * 
	 */
	public void recover() {

		// recover the JFields and combo boxes
		mapSearch.setText("شهر، خیابان");
		mapSearch.setForeground(Color.GRAY);

		adressField.setText("خیابان، کوچه، پلاک");
		adressField.setForeground(Color.GRAY);

		priceField.setText("500000000");
		priceField.setForeground(Color.GRAY);

		areaField.setText("500");
		areaField.setForeground(Color.GRAY);

		cityField.setForeground(Color.GRAY);
		cityField.setText("تهران");

		roomNums.setSelectedIndex(0);
		regionField.setSelectedIndex(0);
		houseOption.setSelectedIndex(0);

		// recover the map
		map.mapRemover(true);

		// recover the photo panel and delete all photos from temp
		photoPanel.removeAll();
		photoPanel.revalidate();
		photoPanel.repaint();
		IO.erasePhotos();
	}
	// ------------------------------------------------------------------------------------------

	/**
	 * this method check the fields , handle errors and save the home
	 * 
	 * @author @Parham 8/22/2017
	 */
	private void addingNewHome() {

		String photoID = UUID.randomUUID().toString();

		String errors = errorFinder();
		// if every thing is correct
		if (errors == "") {

			// get the position of the location
			LatLng pinLocation = map.getPinLocation().getPosition();

			// adding and check the result
			if (HomeDAO.getInstance().addNewHome(BeanResources.getInstance().getSignedUpClient().getId(),
					Integer.parseInt(priceField.getText()), Integer.parseInt(roomNums.getSelectedItem().toString()),
					Integer.parseInt(areaField.getText()), cityField.getText(), adressField.getText(),
					houseOption.getSelectedItem().toString(), pinLocation + "", photoID)) {

				IO.movePhotosFromTemp(photoID);
				// if the house added successfully to database

				JOptionPane.showMessageDialog(null, "خانه ی شما ثبت شد! \r\n باتشکر", "",
						JOptionPane.INFORMATION_MESSAGE);
				GUIManager.addWelcomeToFrame();

			} else {
				// if the house not added to database
				JOptionPane.showMessageDialog(null, "عدم ثبت در پایگاه داده ", "خطا", JOptionPane.ERROR_MESSAGE);
			}
		} else {

			JOptionPane.showMessageDialog(null, errors, "خطا", JOptionPane.ERROR_MESSAGE);
		}

	}

	public String errorFinder() {
		String error = "";
		int i = 1;

		// ---------- error handling -------------
		// if user not signed in

		if (!BeanResources.getInstance().isSignedIn()) {

			error += "\r\n" + i + "لطفا قبل از ثبت خانه ، وارد سیستم شوید";
			i++;
		}

		// if city name is empty
		if (cityField.getText().equals("") || cityField.getForeground().equals(Color.GRAY)) {

			error += "\r\n" + i + "لطفا شهر محل خانه را وارد کنید";
			i++;
		}

		// if house meter is empty
		if (areaField.getText().equals("") || areaField.getForeground().equals(Color.GRAY)) {

			error += "\r\n" + i + "لطفا متراژ خانه را وارد کنید";
			i++;
		}

		// if price is empty
		if (priceField.getText().equals("") || priceField.getForeground().equals(Color.GRAY)) {

			error += "\r\n" + i + "لطفا قیمت خانه را وارد کنید";
			i++;
		}

		// if address is empty
		if (adressField.getText().equals("") || adressField.getForeground().equals(Color.GRAY)) {

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

}
