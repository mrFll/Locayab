package org.bihe.LocaYab.UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import Resource.Resource;

public class BackgroundMovement extends JPanel implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image backGround = Resource.getImage("NewBack.jpg");
	private Image city = Resource.getImage("Picture1.jpg");
	private Image pin = Resource.getImage("Pin.png");
	private Image leftcoluds = Resource.getImage("LeftCloud.png");
	private Image rightcoluds = Resource.getImage("RightClouds.png");
	int yCity = getHeight() + 480;
	int yPin = -125;
	int xLeftClouds =-497;
	int xRightClouds = getWidth();
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(backGround, 0, 0, getWidth(), getHeight(), this);

		g.drawImage(city, getWidth() / 2 - 300, yCity, 600, 600, this);

		g.drawImage(pin, getWidth() / 2 - 46, yPin, 82, 110, this);

		g.drawImage(leftcoluds, xLeftClouds, getHeight() - 480, 497, 229, this);
		
		g.drawImage(rightcoluds, xRightClouds, getHeight() - 480, 525, 229, this);
	}

	public BackgroundMovement() {
		//
		this.setBackground(new Color(209, 252, 243));
	}

	public void run() {
		while (true) {
			if (yCity != getHeight() - 480) {
				if (yCity > getHeight() - 480) {
					yPin = -125;
					xLeftClouds = -497;
					xRightClouds = getWidth();
					yCity--;
				} else {
					yCity = getHeight() - 480;
					yPin = getHeight() - 500;
					xLeftClouds = getWidth() / 2-497;
					xRightClouds=getWidth() / 2  ;
				}
			} else if (yPin != getHeight() - 480) {
				yPin++;
			} else if (xLeftClouds != getWidth() / 2-497) {
				xLeftClouds++;
				xRightClouds--;
			}
			this.repaint();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
