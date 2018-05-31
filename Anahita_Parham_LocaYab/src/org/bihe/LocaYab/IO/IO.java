package org.bihe.LocaYab.IO;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class IO {
	private final static String DESTINATION_PATH = "Property photos\\temp";
	private final static String FOLDER = "Property photos\\";

	public static void copyPhoto(File source) {
		destinFolderCreator(DESTINATION_PATH);
		if (source != null) {
			if (!source.isDirectory() && (source.getName().toLowerCase().endsWith("png")
					|| source.getName().toLowerCase().endsWith("jpg"))) {
				if (numberOfFiles(source)) {
					fileCopyIO(source, DESTINATION_PATH);
				}
			} else {
				JOptionPane.showMessageDialog(new JFrame(), "فرمت عکس شما باید png یا jpg باشد!");
			}
		}
	}

	// --------------------------------------------------------------------------------------------------------------------

	public static void movePhotosFromTemp(String dest) {
		File file = new File(DESTINATION_PATH);
		File[] photos = file.listFiles();
		destinFolderCreator(FOLDER + dest);
		for (int i = 0; i < photos.length; i++) {
			fileCopyIO(photos[i], FOLDER + dest);
		}
	}

	// --------------------------------------------------------------------------------------------------------------------

	public static File[] getPhotos(String homeID) {
		File file = new File(FOLDER + homeID);
		// if accidently the folder is deleted do not error
		destinFolderCreator(FOLDER + homeID);
		File[] photos = file.listFiles();
		return photos;
	}

	// --------------------------------------------------------------------------------------------------------------------
	/**
	 * copy from source to temp
	 * 
	 * @param sourcePath
	 * @param destinationPath
	 */
	private static void fileCopyIO(File source, String dest) {

		//
		File copiedFile = new File(dest + "\\" + source.getName());

		//
		if (!copiedFile.exists()) {
			BufferedInputStream bufInStream = null;
			BufferedOutputStream bufOutStream = null;
			//
			int b;
			//
			try {

				//
				bufInStream = new BufferedInputStream(new FileInputStream(source.getAbsolutePath()));
				bufOutStream = new BufferedOutputStream(new FileOutputStream(copiedFile.getAbsolutePath()));

				while ((b = bufInStream.read()) != -1) {
					bufOutStream.write(b);
				}
				bufOutStream.flush();

			}
			//
			catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			//
			finally {// bufInStream close
				try {
					if (bufInStream != null) {
						bufInStream.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {// bufOutStream close
					{
						if (bufOutStream != null) {
							bufOutStream.close();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "فایل با این نام موجود است!");
		}
	}

	// ---------------------------------------------------------------------------------------------------------------------------
	/**
	 * give all the files in folder
	 * 
	 * @return
	 */
	public static File[] photoFiles() {
		destinFolderCreator(DESTINATION_PATH);
		File file = new File(DESTINATION_PATH);
		File[] insideFiles = file.listFiles();
		return insideFiles;
	}

	// ---------------------------------------------------------------------------------------------------------------------------
	/**
	 * erase the photo
	 * 
	 * @param photo
	 */
	public static void erasePhoto(File photo) {
		destinFolderCreator(DESTINATION_PATH);
		photo.delete();
	}

	public static void eraseFolder(String fileName) {
		File file = new File(FOLDER + fileName);
		destinFolderCreator(fileName);
		file.mkdirs();
		File photos[] = file.listFiles();
		for (int i = 0; i < photos.length; i++) {
			photos[i].delete();
		}
		file.delete();
	}

	// ---------------------------------------------------------------------------------------------------------------------------
	/**
	 * erase photos in temp
	 * 
	 * @param photo
	 */
	public static void erasePhotos() {
		destinFolderCreator(DESTINATION_PATH);
		File[] photos = photoFiles();
		for (int i = 0; i < photos.length; i++) {
			photos[i].delete();
		}

	}

	// ---------------------------------------------------------------------------------------------------------------------------
	/**
	 * create the temp folder
	 */
	public static void destinFolderCreator(String destinationPath) {
		File destin = new File(destinationPath);
		if (!destin.exists()) {
			File[] listFile = destin.listFiles();
			destin.mkdirs();
			if (listFile != null) {
				for (int i = 0; i < listFile.length; i++) {
					if (listFile[i].isDirectory()) {
						destinFolderCreator(destinationPath);
					}
				}
			}
		}
	}

	// ---------------------------------------------------------------------------------------------------------------------------
	/**
	 * 
	 */
	public static void possibilityCheck() {
		File destin = new File(DESTINATION_PATH);
		if (!destin.exists()) {
			File[] listFile = destin.listFiles();
			destin.mkdirs();
			if (listFile != null) {
				for (int i = 0; i < listFile.length; i++) {
					if (listFile[i].isDirectory()) {
						destinFolderCreator(DESTINATION_PATH);
					}
				}
			}
		}
	}

	// ---------------------------------------------------------------------------------------------------------------------------
	/**
	 * check the number of photos in temp it should not be more than 3
	 * 
	 * @param source
	 * @return
	 */
	public static boolean numberOfFiles(File source) {
		File[] photos = photoFiles();
		long size = source.length();
		if (photos.length == 3) {
			JOptionPane.showMessageDialog(null, "شما تنها مجاز به ثبت 3 تصویر هستید!");
			return false;
		}
		if (size > 1000000.0) {
			JOptionPane.showMessageDialog(null, "حجم تصویر باید کمتر از 1مگابایت باشد!");
			return false;
		}
		return true;
	}
}
