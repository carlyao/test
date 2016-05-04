package com.upchina.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.ImageIcon;

public class ImageUtils {
	public static byte[] resize(BufferedImage srcBufferedImage, int targetWidth, String formatName)
			throws IOException {
		return resize(srcBufferedImage, targetWidth, 1f, false,formatName);
	}

	public static byte[] resize(BufferedImage srcBufferedImage,
			int targetWidth, boolean square) throws IOException {
		return resize(srcBufferedImage, targetWidth, square);
	}

	public static byte[] resize(BufferedImage srcBufferedImage,
			int targetWidth, float quality, boolean square, String formatName) throws IOException {
		if (quality > 1) {
			throw new IllegalArgumentException(
					"Quality has to be between 0 and 1");
		}
		if (square) {
			// 正方形，需要提前进行裁剪
			int width = srcBufferedImage.getWidth();
			int height = srcBufferedImage.getHeight();
			if (width > height) {
				int x = (width - height) / 2;
				srcBufferedImage = srcBufferedImage.getSubimage(x, 0, height,
						height);
			} else if (width < height) {
				int y = (height - width) / 2;
				srcBufferedImage = srcBufferedImage.getSubimage(0, y, width,
						width);
			}
		}

		Image resizedImage = null;
		int iWidth = srcBufferedImage.getWidth();
		int iHeight = srcBufferedImage.getHeight();

		if (iWidth > iHeight) {
			if(iWidth<=targetWidth){
				targetWidth=iWidth;
			}
			resizedImage = srcBufferedImage.getScaledInstance(targetWidth,
					(targetWidth * iHeight) / iWidth, Image.SCALE_SMOOTH);
		} else {
			if(iHeight<=targetWidth){
				targetWidth=iHeight;
			}
			resizedImage = srcBufferedImage.getScaledInstance(
					(targetWidth * iWidth) / iHeight, targetWidth,
					Image.SCALE_SMOOTH);
		}

		// This code ensures that all the pixels in the image are loaded.
		Image temp = new ImageIcon(resizedImage).getImage();

		// Create the buffered image.
		BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null),
				temp.getHeight(null), BufferedImage.TYPE_INT_RGB);

		// Copy image to buffered image.
		Graphics g = bufferedImage.createGraphics();

		// Clear background and paint the image.
		g.setColor(Color.white);
		g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));
		g.drawImage(temp, 0, 0, null);
		g.dispose();

		// Soften.
		float softenFactor = 0.05f;
		float[] softenArray = { 0, softenFactor, 0, softenFactor,
				1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0 };
		Kernel kernel = new Kernel(3, 3, softenArray);
		ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
		bufferedImage = cOp.filter(bufferedImage, null);

		// Write the jpeg to a file.

		// Encodes image as a JPEG data stream
		// JPEGImageEncoder encoder = JPEGCodeca
		// .createJPEGEncoder(byteArrayOutputStream);
		//
		// JPEGEncodeParam param = encoder
		// .getDefaultJPEGEncodeParam(bufferedImage);
		//
		// param.setQuality(quality, true);
		//
		// encoder.setJPEGEncodeParam(param);
		// encoder.encode(bufferedImage);

		ImageWriter writer = ImageIO.getImageWritersByFormatName(formatName).next();
		ImageWriteParam param = writer.getDefaultWriteParam();
//		param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
//		param.setCompressionQuality(1.0F); // Highest quality
		// Write the JPEG to our ByteArray stream
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ImageOutputStream imageOutputStream = ImageIO
				.createImageOutputStream(byteArrayOutputStream);
		writer.setOutput(imageOutputStream);
		writer.write(null, new IIOImage(bufferedImage, null, null), param);
		return byteArrayOutputStream.toByteArray();
	}

	public static byte[] resizeByArea(BufferedImage srcBufferedImage,
			int targetArea, String formatName) throws IOException {
		Image resizedImage = null;
		int iWidth = srcBufferedImage.getWidth();
		int iHeight = srcBufferedImage.getHeight();
		
		int iArea=iWidth*iHeight;
		if (iArea <= targetArea) {
			resizedImage = srcBufferedImage.getScaledInstance(
					iWidth, iHeight,Image.SCALE_SMOOTH);
		} else {
			double scale = Math.sqrt(iArea*1.0/targetArea);
			
			if (iWidth > iHeight) {
				int targetWidth = (int)(iWidth/scale);
				resizedImage = srcBufferedImage.getScaledInstance(targetWidth,
						(targetWidth * iHeight) / iWidth, Image.SCALE_SMOOTH);
			} else {
				int targetWidth = (int)(iHeight/scale);
				resizedImage = srcBufferedImage.getScaledInstance(
						(targetWidth * iWidth) / iHeight, targetWidth,
						Image.SCALE_SMOOTH);
			}
		}
		
		// This code ensures that all the pixels in the image are loaded.
		Image temp = new ImageIcon(resizedImage).getImage();
		
		// Create the buffered image.
		BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null),
				temp.getHeight(null), BufferedImage.TYPE_INT_RGB);
		
		// Copy image to buffered image.
		Graphics g = bufferedImage.createGraphics();
		
		// Clear background and paint the image.
		g.setColor(Color.white);
		g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));
		g.drawImage(temp, 0, 0, null);
		g.dispose();
		
		// Soften.
		float softenFactor = 0.05f;
		float[] softenArray = { 0, softenFactor, 0, softenFactor,
				1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0 };
		Kernel kernel = new Kernel(3, 3, softenArray);
		ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
		bufferedImage = cOp.filter(bufferedImage, null);
		
		// Write the jpeg to a file.
		
		// Encodes image as a JPEG data stream
		// JPEGImageEncoder encoder = JPEGCodeca
		// .createJPEGEncoder(byteArrayOutputStream);
		//
		// JPEGEncodeParam param = encoder
		// .getDefaultJPEGEncodeParam(bufferedImage);
		//
		// param.setQuality(quality, true);
		//
		// encoder.setJPEGEncodeParam(param);
		// encoder.encode(bufferedImage);
		
		ImageWriter writer = ImageIO.getImageWritersByFormatName(formatName).next();
		ImageWriteParam param = writer.getDefaultWriteParam();
//		param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
//		param.setCompressionQuality(1.0F); // Highest quality
		// Write the JPEG to our ByteArray stream
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ImageOutputStream imageOutputStream = ImageIO
				.createImageOutputStream(byteArrayOutputStream);
		writer.setOutput(imageOutputStream);
		writer.write(null, new IIOImage(bufferedImage, null, null), param);
		return byteArrayOutputStream.toByteArray();
	}
	
	
	// Example usage
	public static void main(String[] args) throws IOException {

//		File originalImage = new File("F:/tmp/scene.jpg");
//		byte[] bytes = resize(ImageIO.read(originalImage), 100);
//		FileOutputStream out = new FileOutputStream(new File("F:/tmp/scene_thumb.jpg"));
//		out.write(bytes);
//		out.close();
//		System.out.println("Ok");
		
//		String readFormats[] = ImageIO.getReaderFormatNames();
//		String writeFormats[] = ImageIO.getWriterFormatNames();
//		System.out.println("Readers: " + Arrays.asList(readFormats));
//		System.out.println("Writers: " + Arrays.asList(writeFormats));
	}

	public static void createThumbnailByWidth(File saveDirectory, String fileName,
			String fileExtension) throws IOException {
		BufferedImage srcBufferedImage=ImageIO.read(new File(saveDirectory, fileName));
		Integer width=300;
		String widthProp = ImagePathUtil.getHost("THUMBNAIL_WIDTH");
		if(widthProp!=null){
			width=Integer.parseInt(widthProp);
		}
		byte[] bytes=resize(srcBufferedImage, width, fileExtension);
		FileOutputStream out = new FileOutputStream(new File(saveDirectory,fileName.replace(".", "-thumbnail.")));
		out.write(bytes);
		out.close();
		System.out.println(fileName+" create thumbnail done");
	}
	
	public static void createThumbnailByArea(File saveDirectory, String fileName,
			String fileExtension) throws IOException {
		BufferedImage srcBufferedImage=ImageIO.read(new File(saveDirectory, fileName));
		Integer area=250000;
		String areaProp = ImagePathUtil.getHost("THUMBNAIL_AREA");
		if(areaProp!=null){
			area=Integer.parseInt(areaProp);
		}
		byte[] bytes=resizeByArea(srcBufferedImage, area, fileExtension);
		FileOutputStream out = new FileOutputStream(new File(saveDirectory,fileName.replace(".", "-thumbnail.")));
		out.write(bytes);
		out.close();
		System.out.println(fileName+" create thumbnail done");
	}
	
	public static void createThumbnail(File saveDirectory, String fileName,
			String fileExtension) throws IOException {
		String modeProp = ImagePathUtil.getHost("THUMBNAIL_MODE");
		if(modeProp==null||modeProp.equals("area")){
			createThumbnailByArea(saveDirectory,fileName,fileExtension);
		}else{
			createThumbnailByWidth(saveDirectory,fileName,fileExtension);
		}
	}
}