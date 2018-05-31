package org.bihe.LocaYab.UI;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import org.bihe.LocaYab.Controller.GUIManager;
import org.bihe.LocaYab.bean.BeanResources;
import org.bihe.LocaYab.jdbc.DBDAO.ClientDAO;
import Resource.Resource;

/**
 * 
 * @author @Anahita created(7/31/2017 9:24PM )
 * 
 * @author @Parham add database checking validation user/pass 8/3/2017 1:09 PM
 *
 */
public class SignInPanel extends JPanel implements KeyListener {
	/**
	 * 
	 */
	// ----------------------------------------//Fields//--------------------------------------------
	private static final long serialVersionUID = 1L;
	private JPasswordField passwordField;
	private JTextField idNumberField;

	// ---------------------------------------//Constructor//-----------------------------------------
	public SignInPanel() {
		this.setLayout(gridBag());
		GridBagConstraints gbc = new GridBagConstraints(1, 0, 2, 2, 0.0d, 0.0d, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 0, 0);
		this.setBackground(MainFrame.BACKGROUND);

		JLabel locayab = new JLabel();
		locayab.setIcon(new ImageIcon(Resource.getImage("LogoName.png")));
		this.add(locayab, gbc);

		// -------------------------------------//ApplicationLabels//-------------------------------
		// ID
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.gridx = 1;
		gbc.gridy = 3;
		JLabel idNumber = new JLabel("کد ملی", JLabel.CENTER);
		idNumber.setFont(Resource.getFont(25f));
		idNumber.setPreferredSize(new Dimension(100, 80));
		this.add(idNumber, gbc);
		// .........................................................................................
		// passWord
		gbc.gridy = 4;
		JLabel password = new JLabel("رمز عبور", JLabel.CENTER);
		password.setFont(Resource.getFont(25f));
		password.setPreferredSize(new Dimension(100, 80));
		this.add(password, gbc);

		// -------------------------------------//ApplicationFields//-------------------------------
		// ID
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 2;
		gbc.gridy = 3;
		idNumberField = new JTextField();
		idNumberField.setFont(MainFrame.fieldFont(18));
		idNumberField.setPreferredSize(new Dimension(200, 40));
		this.add(idNumberField, gbc);

		// ..........................................................................................
		// password
		gbc.gridy = 4;
		passwordField = new JPasswordField();
		passwordField.setFont(MainFrame.fieldFont(18));
		passwordField.setPreferredSize(new Dimension(200, 40));
		passwordField.addKeyListener(this);
		this.add(passwordField, gbc);
		// -----------------------------------//PasswordRemember//--------------------------------------
		// // remember my passsword
		// gbc.gridy = 5;
		// gbc.fill = GridBagConstraints.BOTH;
		// JRadioButton remember = new JRadioButton("مرا به خاطر بسپار");
		// remember.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		// remember.setFont(Resource.getFont(22f));
		// this.add(remember, gbc);
		// //
		// -----------------------------------//ForgetMyPassWord//--------------------------------------
		// // send my password
		// gbc.gridy = 6;
		// JRadioButton forget = new JRadioButton("رمز عبور یا نام کاربری را
		// فراموش کرده ام!");
		// forget.setFont(Resource.getFont(22f));
		// forget.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		// this.add(forget, gbc);

		// ---------------------------------------//EnterButton//----------------------------------------
		// Enter Button
		gbc = new GridBagConstraints(1, 7, 2, 1, 0.0d, 0.0d, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				new Insets(1, 1, 1, 1), 0, 0);
		JButton enter = new JButton("ورود");
		enter.setPreferredSize(new Dimension(100, 40));
		enter.setFont(Resource.getFont(25f));
		enter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				signInChecker();

			}
		});
		this.add(enter, gbc);
	}

	// ------------------------------------------//Methods//--------------------------------------

	/**
	 * check the fields
	 * 
	 * @return
	 */
	private boolean signUpCheck() {

		String id = idNumberField.getText();
		@SuppressWarnings("deprecation")
		String password = passwordField.getText();
		String error = errorFinder(id, password);

		// checking errors
		if (error.equals("")) {

			if (ClientDAO.getInstance().signInCheck(id, password)) {
				// if the user is valid (correct user/pass)

				// don't need to show massage when user sign in
				return true;

			} else {
				// if the user is not valid ( incorrect user/pass)
				JOptionPane.showMessageDialog(null, "کد ملی یا رمز عبور اشتباه وارد شده.", "خطا",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}

		} else {
			// if user have another errors like put the text field empty
			JOptionPane.showMessageDialog(null, error, "خطا", JOptionPane.ERROR_MESSAGE);
			return false;
		}

	}

	/**
	 * find the errors
	 * 
	 * @param id
	 * @param password
	 * @return
	 */
	public String errorFinder(String id, String password) {
		String error = "";
		int i = 1;

		if (id.trim().equals("")) {// empty id field
			error += "\r\n" + i + ".کد ملی خود را وارد کنید.";
			i++;
		}
		if (password.trim().equals("")) {// empty password field
			error += "\r\n" + i + ".رمز عبور را وارد کنید.";
			i++;
		}
		return error;
	}

	/**
	 * Layout Handle
	 * 
	 * @return
	 */
	private GridBagLayout gridBag() {
		GridBagLayout gbl = new GridBagLayout();
		gbl.columnWidths = new int[] { 200, 200, 200, 200 };
		gbl.rowHeights = new int[] { 50, 50, 50, 50, 50, 50, 50, 50, 50 };
		//
		gbl.columnWeights = new double[] { 100.0d, 10.0d, 10.0d, 100.0d };
		gbl.rowWeights = new double[] { 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d };

		return gbl;
	}

	/**
	 * remove the fields
	 */
	public void recover() {
		passwordField.setText("");
		idNumberField.setText("");
	}

	private void signInChecker() {

		if (signUpCheck()) {

			// add signed in client to program
			BeanResources.getInstance().accountManager(ClientDAO.getInstance().getClient(idNumberField.getText()));

			// change panel and administration
			GUIManager.addWelcomeToFrame();
		}
	}

	// -------------------------------------------//KeyListener//-------------------------------------
	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (KeyEvent.VK_ENTER == e.getKeyCode()) {

			signInChecker();
		}

	}

}
