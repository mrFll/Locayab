package org.bihe.LocaYab.UI;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.bihe.LocaYab.Controller.GUIManager;
import org.bihe.LocaYab.IO.IO;
import org.bihe.LocaYab.bean.BeanResources;
import org.bihe.LocaYab.bean.Home;
import org.bihe.LocaYab.jdbc.DBDAO.ClientDAO;
import org.bihe.LocaYab.jdbc.DBDAO.HomeDAO;

import Resource.Resource;

public class PersonalInfoPanel extends JPanel {

	// ------------------------- Fields -----------------------------
	private static final long serialVersionUID = 1L;

	// for editing
	private JTextField nameTF;
	private JTextField lastNameTF;
	private JTextField nationalIdTF;
	private JTextField tellTF;
	private JTextArea addressTA;

	// for showing details
	private JLabel nameL;
	private JLabel lastNameL;
	private JLabel nationalIdL;
	private JLabel tellL;
	private JLabel addressL;

	private JLabel houseNumbLabel;
	private JPanel owningHouseList;

	/**
	 * editStatus:
	 * 
	 * 
	 * true--> editing situation
	 * 
	 * false-> not editing situation
	 */
	private boolean editStatus;

	/**
	 * ownPin:
	 * 
	 * 
	 * true--> show own house
	 * 
	 * false--> show pin house
	 */
	private boolean ownPin;

	private JButton showPinBtn;

	// ---------------------- Constructor -----------------------------
	public PersonalInfoPanel() {

		// set panel settings
		this.setLayout(createGridBagLayout());
		this.setBackground(MainFrame.BACKGROUND);

		//
		editStatus = false;
		ownPin = true;

		makeRightPart();

		makeLeftPart();
	}

	// ------------------------ UI Methods --------------------------------
	private GridBagLayout createGridBagLayout() {

		// 11 X 9 all resize with panel
		GridBagLayout gbl = new GridBagLayout();

		gbl.columnWidths = new int[] { 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30 };
		gbl.rowHeights = new int[] { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 };
		gbl.columnWeights = new double[] { 100d, 100d, 100d, 100d, 100d, 100d, 100d, 100d, 100d, 100d, 100d, 100d, 100d,
				100d, 100d, 100d, 100d };
		gbl.rowWeights = new double[] { 100d, 100d, 100d, 100d, 100d, 100d, 100d, 100d, 100d, 100d, 100d, 100d, 100d,
				100d, 100d, 100d };

		return gbl;

	}

	/**
	 * this method make the right part of the panel
	 */
	private void makeRightPart() {

		GridBagConstraints gbc = new GridBagConstraints(0, 1, 10, 15, 0.0d, 0.0d, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(4, 4, 4, 4), 0, 0);

		// ------------------- Name zone ----------------
		addFirstNameZone(gbc);

		// ------------------- LastName zone ----------------
		addLastNameZone(gbc);

		// ------------------- nationalID zone ----------------
		addNAtionalIdZone(gbc);

		// ------------------- tell zone ----------------
		addTellZone(gbc);

		// ------------------- address zone ----------------
		addAddressZone(gbc);

		// ------------------- change pass button ----------------
		addChangePassBtn(gbc);

		// ------------------- edit enter button ----------------
		addChangeDetailBtn(gbc);

		// ------------------- show pins button ----------------
		addOwnPinsBtn(gbc);

		// add delete button
		addDeleteBtn(gbc);

	}

	private void addDeleteBtn(GridBagConstraints gbc) {

		JButton showPinBtn = new JButton("حذف حساب");
		showPinBtn.setFont(Resource.getFont(24f));

		showPinBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int exit = JOptionPane.showOptionDialog(null, "آیا میخواهید حساب خود را حذف کنید؟", "حذف ملک", 0,
						JOptionPane.INFORMATION_MESSAGE, null, new String[] { "بلی", "خیر" }, null);

				if (exit == JOptionPane.YES_OPTION) {

					if (ClientDAO.getInstance().deleteClient(BeanResources.getInstance().getSignedUpClient().getId(),
							BeanResources.getInstance().getSignedUpClient().getNationalID())) {

						// remove the client from bean resource
						BeanResources.getInstance().accountManager(null);

						// change panel
						GUIManager.addWelcomeToFrame();
					} else {

						JOptionPane.showMessageDialog(null, "خطا در حذف اطلاعات از پایگاه داده ", "خطا",
								JOptionPane.ERROR_MESSAGE);
					}

				}

			}
		});

		gbc.gridx = 15;
		gbc.gridy = 15;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		this.add(showPinBtn, gbc);

	}

	/**
	 * this method add first name label - textField to the panel
	 * 
	 * @param gbc
	 */
	private void addFirstNameZone(GridBagConstraints gbc) {

		this.nameL = new JLabel(BeanResources.getInstance().getSignedUpClient().getFirstName(), JLabel.RIGHT);
		this.nameL.setFont(Resource.getFont(22f));
		this.nameL.setVisible(true);

		gbc.gridx = 14;
		gbc.gridy = 8;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;

		this.add(nameL, gbc);

		// --------------------------

		this.nameTF = new JTextField(BeanResources.getInstance().getSignedUpClient().getFirstName(), JTextField.RIGHT);
		this.nameTF.setVisible(false);
		this.nameTF.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		this.nameTF.setFont(MainFrame.fieldFont(20));
		this.nameTF.setBorder(BorderFactory.createEtchedBorder(Color.GRAY, Color.DARK_GRAY));

		this.add(nameTF, gbc);

		// --------------------------

		JLabel NameText = new JLabel("نام :", JLabel.LEFT);
		NameText.setFont(Resource.getFont(24f));

		gbc.gridx = 16;
		gbc.gridy = 8;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		this.add(NameText, gbc);

	}

	/**
	 * this method add last name zone to the panel
	 * 
	 * @param gbc
	 */
	private void addLastNameZone(GridBagConstraints gbc) {
		this.lastNameL = new JLabel(BeanResources.getInstance().getSignedUpClient().getLastName(), JLabel.RIGHT);
		this.lastNameL.setFont(Resource.getFont(22f));
		this.lastNameL.setVisible(true);

		gbc.gridx = 11;
		gbc.gridy = 8;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		this.add(lastNameL, gbc);

		// --------------------------

		this.lastNameTF = new JTextField(BeanResources.getInstance().getSignedUpClient().getLastName(),
				JTextField.RIGHT);
		this.lastNameTF.setVisible(false);
		this.lastNameTF.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		this.lastNameTF.setFont(MainFrame.fieldFont(20));
		this.lastNameTF.setBorder(BorderFactory.createEtchedBorder(Color.GRAY, Color.DARK_GRAY));

		this.add(lastNameTF, gbc);

		// --------------------------

		JLabel lastNameText = new JLabel("نام خانوادگی :", JLabel.LEFT);
		lastNameText.setFont(Resource.getFont(24f));

		gbc.gridx = 13;
		gbc.gridy = 8;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		this.add(lastNameText, gbc);

	}

	/**
	 * this method add national id label - textField to panel
	 * 
	 * @param gbc
	 */
	private void addNAtionalIdZone(GridBagConstraints gbc) {

		this.nationalIdL = new JLabel(BeanResources.getInstance().getSignedUpClient().getNationalID(), JLabel.RIGHT);
		this.nationalIdL.setFont(Resource.getFont(24f));
		this.nationalIdL.setVisible(true);

		gbc.gridx = 14;
		gbc.gridy = 9;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		this.add(nationalIdL, gbc);

		// --------------------------

		this.nationalIdTF = new JTextField(BeanResources.getInstance().getSignedUpClient().getNationalID(),
				JTextField.RIGHT);
		this.nationalIdTF.setVisible(false);
		this.nationalIdTF.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		this.nationalIdTF.setFont(MainFrame.fieldFont(20));
		this.nationalIdTF.setBorder(BorderFactory.createEtchedBorder(Color.GRAY, Color.DARK_GRAY));

		this.add(nationalIdTF, gbc);

		// --------------------------

		JLabel nationalIDText = new JLabel("کدملی :", JLabel.LEFT);
		nationalIDText.setFont(Resource.getFont(24f));

		gbc.gridx = 16;
		gbc.gridy = 9;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		this.add(nationalIDText, gbc);
	}

	/**
	 * this method add tell label - textField to panel
	 * 
	 * @param gbc
	 */
	private void addTellZone(GridBagConstraints gbc) {

		this.tellL = new JLabel(BeanResources.getInstance().getSignedUpClient().getTell(), JLabel.RIGHT);
		this.tellL.setFont(Resource.getFont(24f));
		this.tellL.setVisible(true);

		gbc.gridx = 11;
		gbc.gridy = 9;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		this.add(tellL, gbc);

		// --------------------------

		this.tellTF = new JTextField(BeanResources.getInstance().getSignedUpClient().getTell(), JTextField.RIGHT);
		this.tellTF.setVisible(false);
		this.tellTF.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		this.tellTF.setFont(MainFrame.fieldFont(20));
		this.tellTF.setBorder(BorderFactory.createEtchedBorder(Color.GRAY, Color.DARK_GRAY));

		this.add(tellTF, gbc);

		// --------------------------

		JLabel tellText = new JLabel("شماره موبایل :", JLabel.LEFT);
		tellText.setFont(Resource.getFont(24f));

		gbc.gridx = 13;
		gbc.gridy = 9;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		this.add(tellText, gbc);

	}

	/**
	 * this method add address label - textField to panel
	 * 
	 * @param gbc
	 */
	private void addAddressZone(GridBagConstraints gbc) {

		this.addressL = new JLabel(BeanResources.getInstance().getSignedUpClient().getAddress(), JLabel.RIGHT);
		this.addressL.setFont(Resource.getFont(24f));
		this.addressL.setBorder(BorderFactory.createEtchedBorder(Color.GRAY, Color.DARK_GRAY));
		this.addressL.setVisible(true);

		gbc.gridx = 11;
		gbc.gridy = 10;
		gbc.gridwidth = 5;
		gbc.gridheight = 4;
		this.add(addressL, gbc);

		// --------------------------

		this.addressTA = new JTextArea(BeanResources.getInstance().getSignedUpClient().getAddress());
		this.addressTA.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		this.addressTA.setBorder(BorderFactory.createEtchedBorder(Color.GRAY, Color.DARK_GRAY));
		this.addressTA.setVisible(false);
		this.addressTA.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		this.addressTA.setFont(MainFrame.fieldFont(20));

		this.add(addressTA, gbc);

		// ----------------------------
		JLabel addressText = new JLabel("آدرس :", JLabel.LEFT);
		addressText.setFont(Resource.getFont(24f));

		gbc.gridx = 16;
		gbc.gridy = 10;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		this.add(addressText, gbc);

	}

	/**
	 * this method add change password button to panel
	 * 
	 * @param gbc
	 */
	private void addChangePassBtn(GridBagConstraints gbc) {

		JButton changePassBtn = new JButton("تغییر رمز عبور");
		changePassBtn.setFont(Resource.getFont(24f));

		changePassBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JTextField currentPass = new JTextField("رمز عبور فعلی");
				JTextField newPass = new JTextField("رمز عبور جدید");
				JTextField reNewPass = new JTextField("تکرار رمز عبور جدید");

				currentPass.addFocusListener(textListener("رمز عبور فعلی", currentPass));
				newPass.addFocusListener(textListener("رمز عبور جدید", newPass));
				reNewPass.addFocusListener(textListener("تکرار رمز عبور جدید", reNewPass));

				JPanel panel = new JPanel();
				panel.setLayout(new GridLayout(3, 1));
				panel.add(currentPass);
				panel.add(newPass);
				panel.add(reNewPass);

				int resOptionPane = JOptionPane.showConfirmDialog(null, panel, "تغییر رمز عبور",
						JOptionPane.OK_CANCEL_OPTION);

				if (resOptionPane == JOptionPane.OK_OPTION) {

					if (BeanResources.getInstance().getSignedUpClient().getPass().equals(currentPass.getText())) {

						if (!newPass.getText().trim().equals("رمز عبور جدید")
								|| !reNewPass.getText().trim().equals("تکرار رمز عبور جدید")) {

							if (newPass.getText().trim().equals(reNewPass.getText().trim())) {

								if (ClientDAO.getInstance().updateClientPass(
										BeanResources.getInstance().getSignedUpClient().getId(),
										newPass.getText().trim())) {

									JOptionPane.showMessageDialog(null, "رمز عبور تغییر پیدا کرد", "",
											JOptionPane.INFORMATION_MESSAGE);

								} else {
									JOptionPane.showMessageDialog(null, "عدم تغییر در پایگاه داده", "خطا",
											JOptionPane.ERROR_MESSAGE);
								}

							} else {
								JOptionPane.showMessageDialog(null, "رمز جدید و تکرار آن متفاوت هستند", "خطا",
										JOptionPane.ERROR_MESSAGE);
							}

						} else {
							JOptionPane.showMessageDialog(null, "لطفا جاهای خالی را پر کنید", "خطا",
									JOptionPane.ERROR_MESSAGE);

						}

					} else {

						JOptionPane.showMessageDialog(null, "رمز کنونی اشتباه وارد شده ", "خطا",
								JOptionPane.ERROR_MESSAGE);
					}
				}

			}
		});

		gbc.gridx = 13;
		gbc.gridy = 15;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		this.add(changePassBtn, gbc);

	}

	/**
	 * this method add show pins or own house to the panel
	 */
	private void addOwnPinsBtn(GridBagConstraints gbc) {

		showPinBtn = new JButton("نمایش نشان شده ها");
		showPinBtn.setFont(Resource.getFont(24f));

		showPinBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (ownPin) {

					ownPin = false;
					showPinBtn.setText("نمایش املاک");
					showOwningHouse();

				} else {

					ownPin = true;
					showPinBtn.setText("نمایش نشان شده ها");
					showOwningHouse();

				}

			}
		});

		gbc.gridx = 6;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		this.add(showPinBtn, gbc);

	}

	/**
	 * this method add change detail button
	 * 
	 * @param gbc
	 */
	private void addChangeDetailBtn(GridBagConstraints gbc) {

		JButton editBtn = new JButton("تغییر اطلاعات");
		editBtn.setFont(Resource.getFont(24f));
		editBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (editStatus) {

					if (update()) {
						editStatus = false;
						editVisibalingManager();
						editBtn.setText("تغییر اطلاعات");
						GUIManager.addPersonalInfoToFrame();
					}

				} else {

					editStatus = true;
					editVisibalingManager();
					editBtn.setText("ثبت اطلاعات");

				}

			}
		});

		gbc.gridx = 11;
		gbc.gridy = 15;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		this.add(editBtn, gbc);
	}

	/**
	 * this method make the left part of the panel
	 */
	private void makeLeftPart() {

		// add house number label
		GridBagConstraints gbc = new GridBagConstraints(0, 0, 6, 1, 0.0d, 0.0d, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(4, 4, 4, 4), 0, 0);

		houseNumbLabel = new JLabel("", JLabel.CENTER);
		houseNumbLabel.setFont(Resource.getFont(24f));

		this.add(houseNumbLabel, gbc);

		// add and handle scroll pane
		owningHouseList = new JPanel();
		owningHouseList.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		owningHouseList.setBackground(MainFrame.BACKGROUND);
		owningHouseList.setLayout(new BoxLayout(owningHouseList, BoxLayout.Y_AXIS));

		JScrollPane scPane = new JScrollPane();
		scPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scPane.setViewportView(owningHouseList);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 11;
		gbc.gridheight = 15;

		this.add(scPane, gbc);

		showOwningHouse();

	}

	/**
	 * this method add houses to the panel
	 */
	private void showOwningHouse() {

		BeanResources.getInstance().accountManager(null);
		BeanResources.getInstance().accountManager(ClientDAO.getInstance().getClient(nationalIdL.getText().trim()));

		this.owningHouseList.removeAll();

		if (this.ownPin) {

			// show user owning houses
			this.houseNumbLabel.setText(
					"تعداد املاک :" + "  " + BeanResources.getInstance().getSignedUpClient().getOwnHomes().size());

			for (Home home : BeanResources.getInstance().getSignedUpClient().getOwnHomes()) {

				if (home != null) {

					this.owningHouseList.add(makeLittleHomeDetailPanel(home));

				}

			}

		} else {

			// show user pin houses
			this.houseNumbLabel.setText("تعداد نشان شده ها :" + "  "
					+ BeanResources.getInstance().getSignedUpClient().getPinnedHome().size());

			for (Home home : BeanResources.getInstance().getSignedUpClient().getPinnedHome()) {
				if (home != null) {

					this.owningHouseList.add(makeLittleHomeDetailPanel(home));

				}

			}

		}
		this.owningHouseList.repaint();
		this.owningHouseList.revalidate();

	}

	/**
	 * make the house detail panel
	 * 
	 * @param home
	 * @return
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

	/**
	 * make another gridBag
	 * 
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
	 * make the detail and object of the panel
	 * 
	 * @param homePresent
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

		// ----------------- delete ----------------------

		JLabel delete = new JLabel();
		delete.setIcon(new ImageIcon(Resource.getImage("remove.png").getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
		delete.setCursor(new Cursor(Cursor.HAND_CURSOR));
		delete.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				if (ownPin) {
					// if own houses shown

					int exit = JOptionPane.showOptionDialog(null, "آیا میخواهید ملک را حذف کنید؟", "حذف ملک", 0,
							JOptionPane.INFORMATION_MESSAGE, null, new String[] { "بلی", "خیر" }, null);

					if (exit == JOptionPane.YES_OPTION) {

						if (HomeDAO.getInstance().deleteHouse(home.getHomeID())) {

							BeanResources.getInstance().getSignedUpClient().getOwnHomes().remove(home);

						} else {

							JOptionPane.showMessageDialog(null, "عدم ثبت در پایگاه داده ", "خطا",
									JOptionPane.ERROR_MESSAGE);

						}
					}

				} else {

					// if pin houses shown

					int exit = JOptionPane.showOptionDialog(null, "آیا میخواهید نشان را حذف کنید؟", "حذف نشان", 0,
							JOptionPane.INFORMATION_MESSAGE, null, new String[] { "بلی", "خیر" }, null);

					if (exit == JOptionPane.YES_OPTION) {

						if (ClientDAO.getInstance().deletePin(BeanResources.getInstance().getSignedUpClient().getId(),
								home.getHomeID())) {

							BeanResources.getInstance().getSignedUpClient().getPinnedHome().remove(home);

						} else {

							JOptionPane.showMessageDialog(null, "عدم ثبت در پایگاه داده ", "خطا",
									JOptionPane.ERROR_MESSAGE);
						}

					}

				}

				// show them again
				showOwningHouse();

			}
		});
		gbc.gridx = 19;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 2;
		homePresent.add(delete, gbc);

		// ---------------- panel action listener --------------------

		homePresent.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				GUIManager.addHomeDetailsToFrame(home, 7);

			}

		});

	}

	/**
	 * this method control and set the visibility of the label and text fields
	 */
	private void editVisibalingManager() {

		if (editStatus) {

			nameTF.setVisible(true);
			lastNameTF.setVisible(true);
			nationalIdTF.setVisible(true);
			tellTF.setVisible(true);
			addressTA.setVisible(true);

			// for showing details
			nameL.setVisible(false);
			lastNameL.setVisible(false);
			nationalIdL.setVisible(false);
			tellL.setVisible(false);
			addressL.setVisible(false);

		} else {

			nameTF.setVisible(false);
			lastNameTF.setVisible(false);
			nationalIdTF.setVisible(false);
			tellTF.setVisible(false);
			addressTA.setVisible(false);

			// for showing details
			nameL.setVisible(true);
			lastNameL.setVisible(true);
			nationalIdL.setVisible(true);
			tellL.setVisible(true);
			addressL.setVisible(true);

		}

	}

	/**
	 * this method handle the error of client field
	 * 
	 * @return
	 */
	private String errorFinder() {

		String error = "";
		int i = 1;

		// ---------------------- first name check ---------------------------
		if (nameTF.getText().trim().equals("")) {// empty name field
			error += "\r\n" + i + ".نام خود را وارد کنید.";
			i++;
		}

		// ---------------------- address check ---------------------------
		if (addressTA.getText().trim().equals("")) {// empty address field
			error += "\r\n" + i + "لطفا آدرس خود را به درستی وارد کنید";
			i++;
		}

		// ---------------------- last name check ---------------------------
		if (lastNameTF.getText().trim().equals("")) {// empty last name field
			error += "\r\n" + i + ".نام خانوادگی خود را وارد کنید.";
			i++;
		}

		// ---------------------- number check ---------------------------
		// empty phone number field
		if (tellTF.getText().trim().equals("")) {
			error += "\r\n" + i + ".شماره موبایل خود را وارد کنید.";
			i++;
		} else if (!numberCheck(tellTF.getText()) || tellTF.getText().length() != 11) {
			error += "\r\n" + i + ".لطفا شماره موبایل را صحیح وارد کنید(11رقم).";
			i++;
		}

		// ---------------------- id check ---------------------------
		// empty id field
		if (nationalIdTF.getText().trim().equals("")) {
			error += "\r\n" + i + ".کد ملی خود را وارد کنید.";
			i++;
			// wrong ID
		} else if (nationalIdTF.getText().trim().length() != 10 || !numberCheck(nationalIdTF.getText().trim())) {
			error += "\r\n" + i + ".لطفا کد ملی را صحیح وارد کنید(10رقم).";
			i++;
		}

		// -------------------- existing national id --------------------------
		if (!(nationalIdTF.getText().trim()
				.equals(BeanResources.getInstance().getSignedUpClient().getNationalID().trim()))) {

			if (ClientDAO.getInstance().isIDExist(nationalIdTF.getText().trim())) {

				error += "\r\n" + i + "کدملی در سیستم موجود است";
				i++;
			}

		}

		return error;

	}

	/**
	 * all characters are number
	 * 
	 * @param number
	 * @return
	 */
	private boolean numberCheck(String number) {
		try {
			Long.parseLong(number);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * this method update the client
	 * 
	 * @return
	 */
	private boolean update() {

		if (nameL.getText().trim().equals(nameTF.getText().trim())
				&& lastNameL.getText().trim().equals(lastNameTF.getText().trim())
				&& nationalIdL.getText().trim().equals(nationalIdTF.getText().trim())
				&& tellL.getText().trim().equals(tellTF.getText().trim())
				&& addressL.getText().trim().equals(addressTA.getText().trim())) {

			// if user don't change the data
			return true;

		} else {

			String error = errorFinder();

			if (error.equals("")) {

				// change bean client
				BeanResources.getInstance().getSignedUpClient().setFirstName(nameTF.getText().trim());
				BeanResources.getInstance().getSignedUpClient().setLastName(lastNameTF.getText().trim());
				BeanResources.getInstance().getSignedUpClient().setNationalID(nationalIdTF.getText().trim());
				BeanResources.getInstance().getSignedUpClient().setTell(tellTF.getText().trim());
				BeanResources.getInstance().getSignedUpClient().setAddress(addressTA.getText().trim());

				if (ClientDAO.getInstance().updateClient(BeanResources.getInstance().getSignedUpClient())) {

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
				if (field.getText().equals(text)) {
					field.setText("");
					field.setForeground(Color.BLACK);
				}
			}
		};
		return focusListener;
	}

	public void recover() {

		nameTF.setText(BeanResources.getInstance().getSignedUpClient().getFirstName());
		lastNameTF.setText(BeanResources.getInstance().getSignedUpClient().getLastName());
		nationalIdTF.setText(BeanResources.getInstance().getSignedUpClient().getNationalID());
		tellTF.setText(BeanResources.getInstance().getSignedUpClient().getTell());
		addressTA.setText(BeanResources.getInstance().getSignedUpClient().getAddress());

		nameL.setText(BeanResources.getInstance().getSignedUpClient().getFirstName());
		lastNameL.setText(BeanResources.getInstance().getSignedUpClient().getLastName());
		nationalIdL.setText(BeanResources.getInstance().getSignedUpClient().getNationalID());
		tellL.setText(BeanResources.getInstance().getSignedUpClient().getTell());
		addressL.setText(BeanResources.getInstance().getSignedUpClient().getAddress());

		this.showPinBtn.setText("نمایش نشان شده ها");
		editStatus = false;
		ownPin = true;

		showOwningHouse();
	}
}
