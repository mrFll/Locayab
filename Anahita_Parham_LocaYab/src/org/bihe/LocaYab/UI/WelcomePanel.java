package org.bihe.LocaYab.UI;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.bihe.LocaYab.Controller.GUIManager;
import org.bihe.LocaYab.bean.BeanResources;

import Resource.Resource;

public class WelcomePanel extends JPanel implements KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String city = "ایران";
	private JTextField cityField;
	private JComboBox<String> rBM;

	public WelcomePanel() {
		setOpaque(false);
		this.setLayout(new FlowLayout());

		// set the components in proper location
		JPanel empty = new JPanel();
		empty.setOpaque(false);
		empty.setPreferredSize(new Dimension(30000, 30));
		this.add(empty);

		// -----------------------------------------------------------------------------------------------
		//
		JLabel choose = new JLabel("تامین آسایش شما در یافتن خانه ای نورا،  این است رسالت ما!", JLabel.CENTER);
		choose.setFont(Resource.getFont(48f));
		choose.setForeground(Color.DARK_GRAY);
		choose.setPreferredSize(new Dimension(30000, 80));
		this.add(choose);
		cityField = new JTextField("شهر را وارد کنید");
		cityField.setFont(Resource.getFont(20f));
		cityField.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		cityField.setPreferredSize(new Dimension(400, 40));
		cityField.setBackground(MainFrame.FIELD);
		if (BeanResources.getInstance().generateUsMeAd() == 3) {
			cityField.addFocusListener(textListener("ایران", cityField));
		} else {
			cityField.addFocusListener(textListener("شهر را وارد کنید", cityField));
		}

		cityField.addKeyListener(this);
		this.add(cityField);
		// -----------------------------------------------------------------------------------------------
		// r--> rent b-->buy m-->mortgage
		String reBuMo[] = { "رهن", "اجاره", "خرید" };
		rBM = new JComboBox<>(reBuMo);
		rBM.setPreferredSize(new Dimension(180, 40));
		rBM.setFont(Resource.getFont(25f));
		DefaultListCellRenderer dlcr = new DefaultListCellRenderer();
		dlcr.setHorizontalAlignment(4);
		dlcr.setBackground(new Color(131, 230, 113));
		dlcr.setForeground(MainFrame.FIELD);
		rBM.setRenderer(dlcr);
		rBM.addKeyListener(this);
		this.add(rBM);
		// Find Button
		JButton find = new JButton("جستجو");
		find.setFont(Resource.getFont(25f));
		find.setPreferredSize(new Dimension(80, 40));
		find.setBackground(MainFrame.FIELD);
		find.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				showResults();
				
			}
		});
		find.addKeyListener(this);
		this.add(find);

	}

	// -----------------------------------------------------------------------------------------------
	// remove the fields
	public void recover() {
		if (BeanResources.getInstance().generateUsMeAd() == 3) {
			cityField.setText("ایران");
		} else {
			cityField.setText("شهر را وارد کنید");
		}
	}

	// -------------------------------------------//ShowResults//--------------------------------------
	private void showResults() {
		// TODO show all houses by "ایران"
		// set search panel fields with the data got from user
		BeanResources.getInstance().setCityNameSearch(cityField.getText());
		BeanResources.getInstance().setOptionNameSearch((String) rBM.getSelectedItem());

		// change the panel
		GUIManager.addsearchResultToFrame();
		//
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
			showResults();
		}

	}

}