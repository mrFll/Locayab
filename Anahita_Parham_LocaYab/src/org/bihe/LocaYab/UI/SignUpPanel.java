package org.bihe.LocaYab.UI;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.bihe.LocaYab.Controller.GUIManager;
import org.bihe.LocaYab.bean.Client;
import org.bihe.LocaYab.jdbc.DBDAO.ClientDAO;

import Resource.Resource;

public class SignUpPanel extends JPanel {

	/**
	 * 
	 * @author @Anahita Swing created
	 * 
	 *         -----------------------------------------------
	 * @author @Parham data base checking and adding user ok 8/2/2017 1:19 PM
	 * 
	 *         -----------------------------------------------
	 * @author @Anahita edited Swing zone 8/3/2017 12:00 AM
	 */

	private static final long serialVersionUID = 1L;
	private JPasswordField passwordField;
	private JPasswordField passwordAgainField;
	private JTextArea addressField;
	private JTextField idNumberField;
	private JTextField numberField;
	private JTextField nameField;
	private JTextField lastNameField;

	// -------------------------------------//Constructor//--------------------------------
	public SignUpPanel() {
		this.setLayout(gridBag());
		GridBagConstraints gbc = new GridBagConstraints(1, 1, 5, 6, 0.0d, 0.0d, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 0, 0);
		this.setBackground(MainFrame.BACKGROUND);

		JLabel locayab = new JLabel();
		locayab.setIcon(new ImageIcon(Resource.getImage("LogoName.png")));
		this.add(locayab, gbc);

		// -------------------------------------//ApplicationLabels//--------------------------------
		// Last name
		gbc = new GridBagConstraints(2, 7, 1, 1, 0.0d, 0.0d, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(1, 1, 1, 1), 0, 0);
		JLabel lastName = new JLabel("*نام خانوادگی", JLabel.RIGHT);
		lastName.setFont(Resource.getFont(23f));
		lastName.setPreferredSize(new Dimension(100, 80));
		this.add(lastName, gbc);
		// ------------------------------------------------------------------------------------------
		// Name
		gbc.gridx = 5;
		JLabel name = new JLabel("*نام", JLabel.RIGHT);
		name.setFont(Resource.getFont(23f));
		name.setPreferredSize(new Dimension(150, 80));
		this.add(name, gbc);
		// ------------------------------------------------------------------------------------------
		// Number
		gbc.gridx = 2;
		gbc.gridy = 8;
		JLabel number = new JLabel("*شماره موبایل", JLabel.RIGHT);
		number.setFont(Resource.getFont(23f));
		number.setPreferredSize(new Dimension(150, 80));
		this.add(number, gbc);
		// ------------------------------------------------------------------------------------------
		// ID
		gbc.gridx = 5;
		JLabel idNumber = new JLabel("*کد ملی", JLabel.RIGHT);
		idNumber.setFont(Resource.getFont(23f));
		idNumber.setPreferredSize(new Dimension(150, 80));
		this.add(idNumber, gbc);
		// ------------------------------------------------------------------------------------------
		// passWord
		gbc.gridx = 2;
		gbc.gridy = 9;
		JLabel password = new JLabel("*رمز عبور", JLabel.RIGHT);
		password.setFont(Resource.getFont(23f));
		password.setPreferredSize(new Dimension(100, 80));
		this.add(password, gbc);
		// ------------------------------------------------------------------------------------------
		// passWord again
		gbc.gridx = 5;
		JLabel passwordAgain = new JLabel("*تکرار رمز عبور", JLabel.RIGHT);
		passwordAgain.setFont(Resource.getFont(23f));
		passwordAgain.setPreferredSize(new Dimension(150, 80));
		this.add(passwordAgain, gbc);

		// ------------------------------------------------------------------------------------------
		// Address
		gbc.gridx = 5;
		gbc.gridy = 10;
		JLabel address = new JLabel("  *آدرس", JLabel.RIGHT);
		address.setFont(Resource.getFont(23f));
		address.setPreferredSize(new Dimension(150, 80));
		this.add(address, gbc);

		// -------------------------------------//ApplicationFields//--------------------------------
		// Last name
		gbc.gridx = 1;
		gbc.gridy = 7;
		gbc.gridheight = 1;
		lastNameField = new JTextField("نام خانوادگی");
		lastNameField.setForeground(Color.GRAY);
		lastNameField.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		lastNameField.setFont(MainFrame.fieldFont(18));
		lastNameField.addFocusListener(textListener("نام خانوادگی", lastNameField));
		this.add(lastNameField, gbc);
		// ------------------------------------------------------------------------------------------
		// Name
		gbc.gridx = 4;
		nameField = new JTextField("نام");
		nameField.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		nameField.setForeground(Color.GRAY);
		nameField.setFont(MainFrame.fieldFont(18));
		nameField.addFocusListener(textListener("نام", nameField));
		this.add(nameField, gbc);
		// ------------------------------------------------------------------------------------------
		// Number
		gbc.gridx = 1;
		gbc.gridy = 8;
		numberField = new JTextField("شماره موبایل");
		numberField.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		numberField.setForeground(Color.GRAY);
		numberField.setFont(MainFrame.fieldFont(18));
		numberField.addFocusListener(textListener("شماره موبایل", numberField));
		this.add(numberField, gbc);
		// ------------------------------------------------------------------------------------------
		// ID
		gbc.gridx = 4;
		idNumberField = new JTextField(" کد ملی");
		idNumberField.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		idNumberField.setForeground(Color.GRAY);
		idNumberField.setFont(MainFrame.fieldFont(18));
		idNumberField.addFocusListener(textListener(" کد ملی", idNumberField));
		this.add(idNumberField, gbc);
		// ------------------------------------------------------------------------------------------
		// password
		gbc.gridx = 1;
		gbc.gridy = 9;
		passwordField = new JPasswordField();
		passwordField.setFont(Resource.getFont(23f));
		this.add(passwordField, gbc);
		// ------------------------------------------------------------------------------------------
		// passWord again
		gbc.gridx = 4;
		passwordAgainField = new JPasswordField();
		passwordAgainField.setFont(Resource.getFont(23f));
		this.add(passwordAgainField, gbc);
		// ------------------------------------------------------------------------------------------
		// Address
		gbc.gridx = 1;
		gbc.gridy = 10;
		gbc.gridheight = 2;
		gbc.gridwidth = 4;
		addressField = new JTextArea();
		addressField.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		addressField.setFont(MainFrame.fieldFont(18));
		this.add(addressField, gbc);
		// ------------------------------------------------------------------------------------------
		// Find Button
		gbc.gridx = 3;
		gbc.gridy = 14;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		JButton find = new JButton("ورود");
		find.setFont(Resource.getFont(23f));
		find.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				signUpCheck();
			}
		});
		this.add(find, gbc);

	}

	// -------------------------------------//Methods//--------------------------------
	/**
	 * check the fields
	 * 
	 * @return boolean
	 */
	@SuppressWarnings("deprecation")
	private boolean signUpCheck() {

		// Initialize panel fields with user data
		String name = nameField.getText().trim();
		String lastname = lastNameField.getText().trim();
		String number = numberField.getText().trim();
		String id = idNumberField.getText().trim();
		String password = passwordField.getText();
		String passwordAgain = passwordAgainField.getText();
		String address = addressField.getText().trim();

		// check data problems
		String error = errorFinder(name, lastname, number, id, password, passwordAgain, address);

		// show massage if we have some problems
		if (error.equals("")) {

			// write to the database
			if (ClientDAO.getInstance()
					.addNewClient(new Client(idNumberField.getText().trim(), nameField.getText().trim(),
							lastNameField.getText().trim(), numberField.getText().trim(),
							password = passwordField.getText(), 0, addressField.getText().trim()))) {
				// if it can write it successfully

				JOptionPane.showMessageDialog(null, "عضویت با موفقیت انجام شد", "", JOptionPane.INFORMATION_MESSAGE);

				GUIManager.addSignInToFrame();

				return true;
			} else {
				// if the method can't write it successfully

				JOptionPane.showMessageDialog(null, "عدم ثبت در پایگاه داده", "خطا", JOptionPane.ERROR_MESSAGE);

				return false;
			}
		} else {

			JOptionPane.showMessageDialog(null, error, "خطا", JOptionPane.ERROR_MESSAGE);

			return false;
		}

	}

	// ------------------------------------------------------------------------------------------
	/**
	 * find the errors
	 * 
	 * @param name
	 * @param lastname
	 * @param number
	 * @param id
	 * @param password
	 * @param passwordAgain
	 * @return String Error
	 * 
	 * @author @Parham improved warnings 8/3/2017 12:40 PM
	 */
	public String errorFinder(String name, String lastname, String number, String id, String password,
			String passwordAgain, String address) {
		String error = "";
		int i = 1;

		// ---------------------- first name check ---------------------------
		if (name.trim().equals("")) {// empty name field
			error += "\r\n" + i + ".نام خود را وارد کنید.";
			i++;
		}

		// ---------------------- address check ---------------------------
		if (address.trim().equals("")) {// empty
										// last name
										// field
			error += "\r\n" + i + "لطفا آدرس خود را به درستی وارد کنید";
			i++;
		}

		// ---------------------- last name check ---------------------------
		if (lastname.trim().equals("")) {// empty last name field
			error += "\r\n" + i + ".نام خانوادگی خود را وارد کنید.";
			i++;
		}

		// ---------------------- number check ---------------------------
		// empty phone number field
		if (number.trim().equals("")) {
			error += "\r\n" + i + ".شماره موبایل خود را وارد کنید.";
			i++;
		} else if (!numberCheck(number.trim()) || number.trim().length() != 11) {
			error += "\r\n" + i + ".لطفا شماره موبایل را صحیح وارد کنید(11رقم).";
			i++;
		}

		// ---------------------- id check ---------------------------
		// empty id field
		if (id.trim().equals("")) {
			error += "\r\n" + i + ".کد ملی خود را وارد کنید.";
			i++;
			// wrong ID
		} else if (id.trim().length() != 10 || !numberCheck(id.trim())) {
			error += "\r\n" + i + ".لطفا کد ملی را صحیح وارد کنید(10رقم).";
			i++;
		}
		// duplicate check
		if (ClientDAO.getInstance().isIDExist(id)) { // duplicate ID
			error += "\r\n" + i + ".کد ملی قبلا در سیستم وارد شده.";
			i++;
		}

		// ---------------------- pass check ---------------------------
		// empty password field
		if (password.trim().equals("")) {
			error += "\r\n" + i + ".رمز عبور را وارد کنید.";
			i++;
		}
		// empty password again field
		if (passwordAgain.trim().equals("")) {
			error += "\r\n" + i + ".مجددا رمز عبور را وارد کنید.";
			i++;
		}
		// passwords do not match
		if (!password.trim().equals(passwordAgain)) {
			error += "\r\n" + i + ".رمز های وارد شده مشابهت ندارند.";
			i++;
		}
		// password characters are less than limit
		if (password.trim().length() < 7) {
			error += "\r\n" + i + ".تعداد حروف رمز عبور باید بیشتر از 7 باشد.";
			i++;
		}

		return error;
	}

	// ------------------------------------------------------------------------------------------
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
				if (field.getText().equals(text)) {
					field.setText("");
					field.setForeground(Color.BLACK);
				}
			}
		};
		return focusListener;
	}

	// ------------------------------------------------------------------------------------------
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

	// ---------------------------------------------------------------------------------------------
	// Layout Handle
	private GridBagLayout gridBag() {
		GridBagLayout gbl = new GridBagLayout();
		gbl.columnWidths = new int[] { 150, 150, 50, 150, 150, 50, 150 };
		gbl.rowHeights = new int[] { 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, };
		//
		gbl.columnWeights = new double[] { 100.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 100.0d };
		gbl.rowWeights = new double[] { 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d,
				10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d };

		return gbl;
	}

	// ---------------------------------------------------------------------------------------------
	// remove the fields
	public void recover() {
		passwordField.setText("");
		passwordField.setForeground(Color.GRAY);
		//
		passwordAgainField.setText("");
		passwordAgainField.setForeground(Color.GRAY);
		//
		addressField.setText("آدرس");
		addressField.setForeground(Color.GRAY);
		//
		idNumberField.setText("کد ملی");
		idNumberField.setForeground(Color.GRAY);
		//
		numberField.setText("شماره موبایل");
		numberField.setForeground(Color.GRAY);
		//
		nameField.setText("نام");
		nameField.setForeground(Color.GRAY);
		//
		lastNameField.setText("نام خانوادگی");
		lastNameField.setForeground(Color.GRAY);
	}
}
