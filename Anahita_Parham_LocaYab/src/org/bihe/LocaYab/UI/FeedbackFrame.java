package org.bihe.LocaYab.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.bihe.LocaYab.bean.HomeReview;
import org.bihe.LocaYab.jdbc.DBDAO.HomeDAO;

import Resource.Resource;

public class FeedbackFrame extends JFrame implements KeyListener {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	private ArrayList<HomeReview> feedbacks;
	private static final int HEIGHT = 700;
	private static final int WIDTH = 700;
	private int line;
	private JPanel content;
	private JPanel reviews;
	private JTextField name;
	private JTextArea writeHere;
	private int houseID;

	public FeedbackFrame(ArrayList<HomeReview> feedbacks, int houseID) {
		this.feedbacks = feedbacks;

		Dimension dimen = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLayout(new FlowLayout());
		int y = (dimen.height - HEIGHT) / 2;
		int x = (dimen.width - WIDTH) / 2;
		setBounds(x, y, WIDTH, HEIGHT);
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		this.setTitle("LOCAYAB");
		this.setIconImage(Resource.getImage("LOGO.png"));
		this.houseID = houseID;
		//

		this.setContentPane(contentPanel());
	}

	private JPanel contentPanel() {
		content = new JPanel();
		content.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		content.setLayout(new BorderLayout());
		content.setBackground(MainFrame.BACKGROUND);
		//
		content.add(myReview(), BorderLayout.SOUTH);
		content.add(reviews(), BorderLayout.CENTER);
		return content;
	}

	private JPanel myReview() {
		JPanel myReview = new JPanel();
		myReview.setBackground(MainFrame.FIELD);
		myReview.setPreferredSize(new Dimension(WIDTH, 400));
		myReview.setLayout(new BorderLayout());
		name = new JTextField("نام و نام خانوادگی");
		name.setForeground(Color.GRAY);
		myReview.setPreferredSize(new Dimension(WIDTH, 200));
		name.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		name.setFont(MainFrame.fieldFont(18));
		name.addFocusListener(textListener("نام و نام خانوادگی", name));
		name.addKeyListener(this);
		myReview.add(name, BorderLayout.NORTH);

		writeHere = new JTextArea("نظر من");
		writeHere.setForeground(Color.GRAY);
		writeHere.setPreferredSize(new Dimension(WIDTH, 800));
		writeHere.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		writeHere.setFont(MainFrame.fieldFont(18));
		writeHere.addFocusListener(textListener("نظر من", writeHere));
		writeHere.addKeyListener(this);
		myReview.add(writeHere, BorderLayout.CENTER);

		JButton confirm = new JButton("ثبت");
		confirm.setFont(Resource.getFont(22f));
		confirm.setPreferredSize(new Dimension(80, 50));
		confirm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				confirm();
			}
		});
		myReview.add(confirm, BorderLayout.EAST);
		return myReview;
	}

	private void confirm() {
		if (name.getText().trim().equals("") || name.getForeground().equals(Color.GRAY)) {
			JOptionPane.showMessageDialog(null, "لطفا نام خود را وارد کنید!");
			name.setText("نام و نام خانوادگی");
			name.setForeground(Color.GRAY);
		} else if (writeHere.getText().trim().equals("") || writeHere.getForeground().equals(Color.GRAY)) {
			JOptionPane.showMessageDialog(null, "لطفا نظر خود را بنویسید!");
			writeHere.setText("نظر من");
			writeHere.setForeground(Color.GRAY);
		} else {

			if (HomeDAO.getInstance().addNewReview(this.houseID, name.getText().trim(), writeHere.getText().trim())) {

				HomeReview review = new HomeReview(name.getText().trim(), writeHere.getText().trim());
				writeHere.setText("نظر من");
				writeHere.setForeground(Color.GRAY);
				name.setText("نام و نام خانوادگی");
				name.setForeground(Color.GRAY);
				feedbacks.add(review);
				reviewPrinter();
				reviews.removeAll();
				reviewPrinter();
			}

		}
	}

	private JPanel reviews() {
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		reviews = new JPanel();
		reviews.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		reviews.setBackground(MainFrame.BACKGROUND);
		reviews.setLayout(new BoxLayout(reviews, BoxLayout.Y_AXIS));
		// add and handle scroll pane
		JScrollPane scPane = new JScrollPane();
		scPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scPane.setViewportView(reviews);
		reviewPrinter();

		//
		container.add(scPane, BorderLayout.CENTER);
		return container;
	}

	private void reviewPrinter() {
		reviews.removeAll();
		for (int i = 0; i < feedbacks.size(); i++) {
			JTextArea review = new JTextArea(text(feedbacks.get(i), 75));
			review.setEditable(false);
			review.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			review.setFont(MainFrame.fieldFont(18));
			review.setPreferredSize(new Dimension(WIDTH - 120, 40 * line));
			review.setBackground(MainFrame.BACKGROUND);
			review.setBorder(BorderFactory.createLineBorder(MainFrame.FIELD));
			reviews.add(review);
		}
		reviews.revalidate();
		reviews.repaint();
	}

	private String text(HomeReview review, int num) {
		line = 1;
		String text = review.getText();
		int alpha = text.toCharArray().length;
		if (alpha > num) {
			line = alpha / num + 1;
			String editedText = "  " + review.getClientName() + ": \r\n";
			for (int i = 0; i < alpha / num; i++) {
				editedText += "\r\n" + text.substring(num * i, num * (i + 1));
			}
			return editedText += "\r\n" + text.substring(num * (alpha / num), text.length());
		} else {
			return review.getClientName() + ": \r\n" + text;
		}
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

	/**
	 * JTextArea focusListener :set the text
	 * 
	 * @param text
	 * @param field
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
			confirm();
		}

	}
}
