package org.bihe.LocaYab.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.bihe.LocaYab.Controller.GUIManager;

import Resource.Resource;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 1200;
	public static final int HEIGHT = 750;
	public static final Color FIELD = new Color(156, 255, 175);
	public static final Color SURROUND = new Color(176, 239, 165);
	public static final Color BACKGROUND = new Color(211, 248, 240);
	public static JPanel contentPanel;
	int showTool = 0;
	public int panel = 1;
	private JPanel toolsPanel;
	private JLabel userNameDetail;

	// -------------------------------------//Constructor//--------------------------------
	public MainFrame() {

		Dimension dimen = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLayout(new FlowLayout());
		int y = (dimen.height - HEIGHT) / 2;
		int x = (dimen.width - WIDTH) / 2;
		setBounds(x, y, WIDTH, HEIGHT);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		this.setTitle("LOCAYAB");
		this.setIconImage(Resource.getImage("LOGO.png"));

		//
		if (panel == 1) {

			contentPanel = GUIManager.getBackgroundMovement();
			Thread t1 = new Thread(GUIManager.getBackgroundMovement());
			t1.start();

		} else {

			contentPanel = new JPanel();
			contentPanel.setBackground(new Color(163, 204, 255));
		}
		contentPanel.setLayout(new BorderLayout());
		this.setContentPane(contentPanel);
		setVisible(true);
	}

	// -------------------------------------//addComponentToContetPanel//--------------------------------
	public void addComponent(JPanel p) {
		p.setBorder(BorderFactory.createTitledBorder(""));
		MainFrame.contentPanel.removeAll();
		if (panel != 1) {
			MainFrame.contentPanel.add(surroundedPanel(), BorderLayout.EAST);
			MainFrame.contentPanel.add(surroundedPanel(), BorderLayout.WEST);
			MainFrame.contentPanel.add(surroundedPanel(), BorderLayout.SOUTH);
		}
		contentPanel.add(tools(), BorderLayout.NORTH);
		MainFrame.contentPanel.add(p, BorderLayout.CENTER);
		this.repaint();
		this.revalidate();
	}

	// -------------------------------------//Tools//--------------------------------
	private JPanel tools() {

		toolsPanel = new JPanel();
		toolsPanel.setLayout(new BorderLayout());
		toolsPanel.setPreferredSize(new Dimension(500, 50));
		toolsPanel.setBackground(MainFrame.SURROUND);
		//
		JLabel locayab = new JLabel();
		locayab.setIcon(new ImageIcon(Resource.getImage("SmalLogo.png")));
		toolsPanel.add(locayab, BorderLayout.WEST);
		//

		JPanel leftPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(400, 50));
		leftPanel.setLayout(new BorderLayout());
		leftPanel.setBackground(MainFrame.SURROUND);

		JButton toolsShow = toolsShow();
		leftPanel.add(toolsShow, BorderLayout.EAST);

		userNameDetail = new JLabel("", JLabel.RIGHT);
		userNameDetail.setFont(Resource.getFont(20f));

		leftPanel.add(userNameDetail, BorderLayout.CENTER);

		toolsPanel.add(leftPanel, BorderLayout.EAST);

		//

		return toolsPanel;
	}

	// -------------------------------------//JButton//--------------------------------
	private JButton toolsShow() {

		JButton toolsShow = new JButton();
		toolsShow.setPreferredSize(new Dimension(50, 50));
		toolsShow.setBorderPainted(false);
		toolsShow.setIcon(new ImageIcon(Resource.getImage("more.png")));
		ToolsPopUp toolsList = new ToolsPopUp();

		toolsShow.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				// recover the pop up index
				toolsList.recoverPopUpIndex();

				toolsList.show(contentPanel, contentPanel.getWidth()-110, 50);
			}

			public void mouseExited(MouseEvent e) {
				toolsShow.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			public void mouseEntered(MouseEvent e) {
				toolsShow.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		});
		return toolsShow;
	}

	// -------------------------------------//SuroundingPanel//--------------------------------
	private JPanel surroundedPanel() {

		JPanel panel = new JPanel();
		panel.setBackground(MainFrame.SURROUND);
		return panel;
	}

	// -------------------------------------//getFont//--------------------------------
	public static Font fieldFont(int size) {

		return new Font("Times New Roman", Font.BOLD, size);
	}

	public void changeUserNameLabel(String name) {

		if (this.userNameDetail == null) {
			this.userNameDetail = new JLabel("");
		}

		this.userNameDetail.setText(name);

	}

}
