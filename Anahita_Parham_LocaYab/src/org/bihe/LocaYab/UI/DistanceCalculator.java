package org.bihe.LocaYab.UI;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.TimeUnit;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.bihe.LocaYab.Controller.GUIManager;

import com.teamdev.jxmaps.LatLng;
import com.teamdev.jxmaps.TravelMode;

import Resource.Resource;

public class DistanceCalculator extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 700;
	private static final int HEIGHT = 500;
	private MapPanel map;
	private TravelMode travelmode;
	private static JLabel distance;
	private JPanel content;

	public DistanceCalculator(LatLng location) {
		Dimension dimen = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLayout(new FlowLayout());
		int y = (dimen.height - WIDTH) / 2;
		int x = (dimen.width - HEIGHT) / 2;
		setBounds(x, y, HEIGHT, WIDTH);
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		this.setTitle("LOCAYAB");
		this.setIconImage(Resource.getImage("LOGO.png"));

		//
		this.setContentPane(contenPanel(location));

	}

	// ----------------------------------//contenPanel//-------------------------------------
	/**
	 * 
	 * @param location
	 * @return
	 */
	private JPanel contenPanel(LatLng location) {
		content = new JPanel();
		GridBagLayout gbl = new GridBagLayout();
		gbl.rowHeights = new int[] { 10, 50, 50, 50, 60, 100, 100, 100, 100, 100, 20 };
		gbl.rowWeights = new double[] { 100d, 100d, 100d, 100d, 100d, 100d, 100d, 100d, 100d, 100d, 100d };

		gbl.columnWidths = new int[] { 20, 100, 100, 100, 100, 100, 20 };
		gbl.columnWeights = new double[] { 100d, 100d, 100d, 100d, 100d, 100d, 100d };
		content.setLayout(gbl);
		content.setBackground(MainFrame.BACKGROUND);
		// ........................................//Map//.............................................
		GridBagConstraints gbc = new GridBagConstraints(1, 5, 5, 5, 0.0d, 0.0d, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(4, 4, 4, 4), 0, 0);
		map = GUIManager.getDirectionMap();
		map.PathRemover();
		map.finder(location, true);
		map.setInDetailLocation(map.markerCreator(location));
		map.setPinUsage(true);
		content.add(map, gbc);
		// ........................................//InfoJLable//...................................
		gbc = new GridBagConstraints(1, 1, 6, 1, 0.0d, 0.0d, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(4, 4, 4, 4), 0, 0);
		distance = new JLabel("محل مورد نظر خود را روی نقشه علامت زده و بر روی دکمه تایید کلیک کنید!", JLabel.CENTER);
		distance.setFont(Resource.getFont(20f));
		content.add(distance, gbc);
		// ........................................//TravelMode//...................................
		gbc = new GridBagConstraints(5, 2, 1, 1, 0.0d, 0.0d, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(4, 4, 4, 4), 0, 0);
		travelmode = TravelMode.WALKING;
		ButtonGroup bg = new ButtonGroup();
		JRadioButton walking = new JRadioButton("پیاده");
		walking.setFont(Resource.getFont(20f));
		walking.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				travelmode = TravelMode.WALKING;
			}
		});
		content.add(walking, gbc);
		bg.add(walking);

		//
		gbc.gridx = 6;
		gbc.gridx = 2;
		JRadioButton car = new JRadioButton("ماشین");
		car.setFont(Resource.getFont(20f));
		car.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				travelmode = TravelMode.DRIVING;
			}
		});
		content.add(car, gbc);
		bg.add(car);
		// ........................................//searchField//...................................
		// Map Finder text Field
		gbc = new GridBagConstraints(3, 4, 3, 1, 5.0d, 5.0d, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(4, 4, 4, 4), 0, 0);
		JTextField mapSearch = new JTextField("شهر، خیابان");
		mapSearch.setForeground(Color.GRAY);
		mapSearch.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		mapSearch.setFont(Resource.getFont(18f));
		mapSearch.addFocusListener(textListener("شهر، خیابان", mapSearch));
		mapSearch.addKeyListener(start(location));
		content.add(mapSearch, gbc);
		// ........................................//FindButton//...................................
		// Find Button
		gbc = new GridBagConstraints(2, 4, 1, 1, 5.0d, 5.0d, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(4, 4, 4, 4), 0, 0);
		JButton find = new JButton("جستجو");
		find.setFont(Resource.getFont(18f));
		find.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				map.finder(mapSearch.getText());
			}
		});
		content.add(find, gbc);
		// .......................................//confirmButton//...................................
		// Confirm Button
		gbc = new GridBagConstraints(1, 4, 1, 1, 5.0d, 5.0d, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(4, 4, 4, 4), 0, 0);
		JButton confirm = new JButton("تأیید");
		confirm.setFont(Resource.getFont(18f));
		confirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				directionCalculate(location);
			}
		});
		content.add(confirm, gbc);
		return content;
	}

	// ----------------------------------------//DistanceToString//------------------------------------------
	/**
	 * if it is more than 1000m--> convert it to kilometer
	 * 
	 * @return
	 */
	private String distance() {
		if (map.getDistance() < 1000) {
			return map.getDistance() + "متر ";
		} else {
			double distance = map.getDistance() / 1000;
			return distance + "کیلومتر ";
		}

	}

	// ------------------------------------//directionCalculate//----------------------------------------
	private void directionCalculate(LatLng location) {
		if (map.getPinLocation() == null) {
			JOptionPane.showMessageDialog(null, "ابتدا محل مورد نظر را روی نقشه تعیین کنید!");
		} else {
			map.distanceCalculator(location, map.getPinLocation().getPosition(), travelmode, true);
			for (int i = 0; i < 15; i++) {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
				}
				if (map.getDistance() != 0.0) {
					break;
				}
			}
			if (map.getDistance() != 0.0) {
				distance.setText("با محل مورد نظر شما" + "حدودا " + distance() + "فاصله دارد ");
				distance.repaint();
			} else if (map.getDistance() == 0.0) {
				distance.setText("اتصال شما قطع شده یا سرعت اینترنت پایین است!");
			}
			map.markersCleaner(map.getPinLocation());
			map.setSet(false);
		}
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

	// -------------------------------------------//KeyListener//-------------------------------------
	public KeyListener start(LatLng location) {
		KeyListener keyListener = new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (KeyEvent.VK_ENTER == e.getKeyCode()) {
					directionCalculate(location);
				}
			}
		};
		return keyListener;
	}

	// -----------------------------------------------------//remover//------------------------------
	public void remover() {
		distance.setText("محل مورد نظر خود را روی نقشه علامت زده و بر روی دکمه تایید کلیک کنید!");
	}
}
