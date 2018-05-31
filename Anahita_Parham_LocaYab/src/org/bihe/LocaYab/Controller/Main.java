package org.bihe.LocaYab.Controller;

import java.awt.Color;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.ColorUIResource;

public class Main {
	public static void main(String[] args) {

		for (LookAndFeelInfo lafInfo : UIManager.getInstalledLookAndFeels()) {
			if ("Nimbus".equals(lafInfo.getName())) {
				try {
					UIManager.setLookAndFeel(lafInfo.getClassName());
					UIManager.put("control", new ColorUIResource(new Color(211, 248, 240)));
					UIManager.put("text", new ColorUIResource(Color.BLACK));
					UIManager.put("nimbusLightBackground", new ColorUIResource(new Color(156, 255, 175)));
					UIManager.put("info", new ColorUIResource(new Color(211, 248, 240)));
					UIManager.put("nimbusInfoBlue", new ColorUIResource(0x2f5cb4));
					UIManager.put("nimbusBase", new ColorUIResource(Color.GREEN));
					UIManager.put("nimbusBlueGrey", new ColorUIResource(new Color(156, 255, 175)));
					UIManager.put("nimbusFocus", new ColorUIResource(Color.RED));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
				break;
			}
		}
		GUIManager.getResultMap();
		GUIManager.addWelcomeToFrame();
		
	}
}
