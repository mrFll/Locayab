package org.bihe.LocaYab.UI;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.bihe.LocaYab.Controller.GUIManager;
import org.bihe.LocaYab.bean.BeanResources;

import Resource.Resource;

public class ToolsPopUp extends JPopupMenu {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenuItem home;
	private JMenuItem enter;
	private JMenuItem memberApplication;
	private JMenuItem myProfile;
	private JMenuItem report;
	private JMenuItem addHome;
	private JMenuItem exit;

	public ToolsPopUp() {
		this.setBackground(new Color(39, 66, 135));
		// 1.Us--> user 2.Me-->Member 3.Ad-->Admin

		home = new JMenuItem("خانه");
		home.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		home.setFont(Resource.getFont(30f));
		home.setActionCommand("خانه");
		home.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				GUIManager.addWelcomeToFrame();
			}
		});

		enter = new JMenuItem("ورود");
		enter.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		enter.setFont(Resource.getFont(30f));
		enter.setActionCommand("ورود");
		enter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				GUIManager.addSignInToFrame();

			}
		});

		memberApplication = new JMenuItem("عضویت");
		memberApplication.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		memberApplication.setFont(Resource.getFont(30f));
		memberApplication.setActionCommand("عضویت");
		memberApplication.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				GUIManager.addSignUpToFrame();
				;
			}
		});

		myProfile = new JMenuItem("پروفایل شخصی");
		myProfile.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		myProfile.setFont(Resource.getFont(30f));
		myProfile.setActionCommand("پروفایل شخصی");
		myProfile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				GUIManager.addPersonalInfoToFrame();

			}
		});

		addHome = new JMenuItem("افزودن خانه");
		addHome.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		addHome.setFont(Resource.getFont(30f));
		addHome.setActionCommand("افزودن خانه");
		addHome.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				/**
				 * TODO initialize later
				 */
				GUIManager.addAddHomeToFrame();
			}
		});
		exit = new JMenuItem("خروج");
		exit.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		exit.setFont(Resource.getFont(30f));
		exit.setActionCommand("خروج");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				// remove the client from bean resource
				BeanResources.getInstance().accountManager(null);

				// change panel
				GUIManager.addWelcomeToFrame();
			}
		});

	}

	public void recoverPopUpIndex() {

		if (BeanResources.getInstance().generateUsMeAd() == 1) {
			this.removeAll();
			this.add(home);
			this.add(memberApplication);
			this.add(enter);
		} else if (BeanResources.getInstance().generateUsMeAd() == 2) {
			this.removeAll();
			this.add(home);
			this.add(addHome);
			this.add(myProfile);
			this.add(exit);
		} else if (BeanResources.getInstance().generateUsMeAd() == 3) {
			this.removeAll();
			this.add(home);
			this.add(report);
			this.add(myProfile);
			this.add(exit);
		}
	}
}
