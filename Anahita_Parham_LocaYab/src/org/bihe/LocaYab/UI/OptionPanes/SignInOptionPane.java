package org.bihe.LocaYab.UI.OptionPanes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.bihe.LocaYab.Controller.GUIManager;
import org.bihe.LocaYab.UI.MainFrame;
import org.bihe.LocaYab.bean.BeanResources;
import org.bihe.LocaYab.jdbc.DBDAO.ClientDAO;

import Resource.Resource;

/**
 * 
 * @author @Parham created 8/11/2017 6:37 PM
 *
 */
public class SignInOptionPane implements KeyListener {

	/**
	 * 
	 */
	private JPasswordField passwordField;
	private JTextField idNumberField;
	private JFrame frame;
	private JPanel panel;

	// ------------------------- Constructor -----------------------------
	public SignInOptionPane() {

		// make new frame
		setFrameSettings();

		// add panel with some settings
		addPanelAndDetails();

		frame.setVisible(true);

	}

	private void setFrameSettings() {

		frame = new JFrame();

		frame.setBackground(MainFrame.BACKGROUND);
		frame.setSize(new Dimension(400, 550));
		frame.setLocationByPlatform(true);

		frame.setAlwaysOnTop(true);
		frame.setUndecorated(true);
		frame.setResizable(false);
	}

	private void addPanelAndDetails() {

		panel = new JPanel(gridBag());
		panel.setBackground(MainFrame.BACKGROUND);
		panel.setBorder(BorderFactory.createEtchedBorder(Color.GRAY, Color.DARK_GRAY));
		GridBagConstraints gbc = new GridBagConstraints(1, 0, 2, 2, 0.0d, 0.0d, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);

		addIcon(gbc, 5, 2, 3, 3);

		// ------------ user name --------------
		addUserNameField(gbc, 1, 7, 6, 1);
		addUserNameLabel(gbc, 7, 7, 4, 1);

		// ------------- Password --------------
		addPasswordField(gbc, 1, 8, 6, 1);
		addPasswordLabel(gbc, 7, 8, 4, 1);

		// ------------- sign in ------------------
		addSignInBtn(gbc, 1, 9, 6, 2);

		// ------------- sign up ------------------
		addSignUpBtn(gbc, 1, 13, 9, 2);

		// -------------- close button -----------------
		addCloseBtn(gbc, 10, 0, 1, 1);

		// --------------- add icon --------------------

		// add panel to frame
		frame.add(panel);

	}

	private void addUserNameLabel(GridBagConstraints gbc, int a, int b, int c, int d) {

		JLabel idNumber = new JLabel("کد ملی", JLabel.CENTER);
		idNumber.setFont(Resource.getFont(25f));

		gbc.gridx = a;
		gbc.gridy = b;
		gbc.gridwidth = c;
		gbc.gridheight = d;

		panel.add(idNumber, gbc);

	}

	private void addPasswordLabel(GridBagConstraints gbc, int a, int b, int c, int d) {

		JLabel idNumber = new JLabel("رمز عبور", JLabel.CENTER);
		idNumber.setFont(Resource.getFont(25f));

		gbc.gridx = a;
		gbc.gridy = b;
		gbc.gridwidth = c;
		gbc.gridheight = d;

		panel.add(idNumber, gbc);

	}

	private void addIcon(GridBagConstraints gbc, int a, int b, int c, int d) {

		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		p.setBackground(MainFrame.BACKGROUND);

		JLabel locayab = new JLabel();
		locayab.setIcon(new ImageIcon(Resource.getImage("Small.png")));

		p.add(locayab, BorderLayout.CENTER);

		gbc.gridx = a;
		gbc.gridy = b;
		gbc.gridwidth = c;
		gbc.gridheight = d;

		panel.add(p, gbc);

	}

	/**
	 * this method make grid bag layout for panel
	 * 
	 * @return
	 */
	private GridBagLayout gridBag() {
		GridBagLayout gbl = new GridBagLayout();
		gbl.columnWidths = new int[] { 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20 };
		gbl.rowHeights = new int[] { 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20 };

		gbl.columnWeights = new double[] { 5.0d, 5.0d, 5.0d, 5.0d, 5.0d, 5.0d, 5.0d, 5.0d, 5.0d, 5.0d, 5.0d };
		gbl.rowWeights = new double[] { 5.0d, 5.0d, 5.0d, 5.0d, 5.0d, 5.0d, 5.0d, 5.0d, 5.0d, 5.0d, 5.0d, 5.0d, 5.0d, };

		return gbl;
	}

	/**
	 * this method make sign up button for panel
	 * 
	 * @param gbc
	 */
	private void addSignUpBtn(GridBagConstraints gbc, int a, int b, int c, int d) {

		JButton signUp = new JButton("تازه به لوکایاب پیوسته اید؟ پس عضو شوید!");
		signUp.setFont(Resource.getFont(19f));

		// add sign up panel
		signUp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				frame.dispose();
				GUIManager.addSignUpToFrame();

			}
		});

		gbc.gridx = a;
		gbc.gridy = b;
		gbc.gridwidth = c;
		gbc.gridheight = d;

		panel.add(signUp, gbc);

	}

	/**
	 * this method make close label for panel
	 * 
	 * @param gbc
	 */
	private void addCloseBtn(GridBagConstraints gbc, int a, int b, int c, int d) {

		JLabel close = new JLabel();
		close.setIcon(new ImageIcon(Resource.getImage("close.png")));

		close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				frame.setVisible(false);

			}
		});

		gbc.gridx = a;
		gbc.gridy = b;
		gbc.gridwidth = c;
		gbc.gridheight = d;

		panel.add(close, gbc);

	}

	/**
	 * this method make sign in button for panel
	 * 
	 * @param gbc
	 */
	private void addSignInBtn(GridBagConstraints gbc, int a, int b, int c, int d) {

		JButton signIn = new JButton("ورود");
		signIn.setFont(Resource.getFont(28f));
		signIn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				signInChecker();

			}
		});

		gbc.gridx = a;
		gbc.gridy = b;
		gbc.gridwidth = c;
		gbc.gridheight = d;

		panel.add(signIn, gbc);

	}

	/**
	 * this method add user name field to panel
	 * 
	 * @param gbc
	 */
	private void addUserNameField(GridBagConstraints gbc, int a, int b, int c, int d) {

		idNumberField = new JTextField();
		idNumberField.setFont(MainFrame.fieldFont(18));
		idNumberField.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

		gbc.gridx = a;
		gbc.gridy = b;
		gbc.gridwidth = c;
		gbc.gridheight = d;

		panel.add(idNumberField, gbc);

	}

	/**
	 * this method add password field to panel
	 * 
	 * @param gbc
	 */
	private void addPasswordField(GridBagConstraints gbc, int a, int b, int c, int d) {

		passwordField = new JPasswordField();

		passwordField.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		passwordField.setFont(MainFrame.fieldFont(18));
		passwordField.addKeyListener(this);

		gbc.gridx = a;
		gbc.gridy = b;
		gbc.gridwidth = c;
		gbc.gridheight = d;

		panel.add(passwordField, gbc);

	}

	/**
	 * this method set the user to bean resource
	 */
	private void signInChecker() {

		if (signedUpCheck()) {

			// add signed in client to program
			BeanResources.getInstance().accountManager(ClientDAO.getInstance().getClient(idNumberField.getText()));

			GUIManager.recoverSearchResultPanelPins();
			// user signed in
			frame.dispose();

		}
	}

	/**
	 * check the fields
	 * 
	 * @return
	 */
	private boolean signedUpCheck() {

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

		if (id.trim().equals("") || id.trim().equals("کدملی")) {// empty id
																// field
			error += "\r\n" + i + ".کد ملی خود را وارد کنید.";
			i++;
		}
		if (password.trim().equals("") || password.trim().equals("رمزعبور")) {// empty
																				// password
																				// field
			error += "\r\n" + i + ".رمز عبور را وارد کنید.";
			i++;
		}
		return error;
	}

	public void show() {

		this.idNumberField.setText("");
		this.passwordField.setText("");
		this.frame.setVisible(true);

	}

	// -------------------------------------------//KeyListener//-------------------------------------

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (KeyEvent.VK_ENTER == e.getKeyCode()) {

			signInChecker();
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

}
