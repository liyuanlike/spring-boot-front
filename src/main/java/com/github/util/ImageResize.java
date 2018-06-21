package com.github.util;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

public class ImageResize {
	
	public static void resize(File sourceFile, String targetFile, int width, int hight) {
		resize(sourceFile, new File(targetFile), width, hight);
	}
	public static void resize(String sourceFile, String targetFile, int width, int hight) {
		resize(new File(sourceFile), new File(targetFile), width, hight);
	}
	public static void resize(File sourceFile, File targetFile, int width, int hight) {
		
		String fileName = sourceFile.getName();
		String formatName = fileName.substring(fileName.indexOf(".") + 1);
		
		try {
			BufferedImage sourceBufferedImage = ImageIO.read(sourceFile);
			
			if (sourceBufferedImage == null) {
				return;
			}
			
			double sx = (double) width / sourceBufferedImage.getWidth();
			double sy = (double) hight / sourceBufferedImage.getHeight();
			// 这里想实现在targetW，targetH范围内实现等比缩放。如果不需要等比缩放
			// 则将下面的if else语句注释即可
			if (sx > sy) {
				sx = sy;
				width = (int) (sx * sourceBufferedImage.getWidth());
			} else {
				sy = sx;
				hight = (int) (sy * sourceBufferedImage.getHeight());
			}
			
			int imageType = sourceBufferedImage.getType();
			BufferedImage target = null;
			if (imageType == BufferedImage.TYPE_CUSTOM) { // handmade
				ColorModel cm = sourceBufferedImage.getColorModel();
				WritableRaster raster = cm.createCompatibleWritableRaster(width, hight);
				target = new BufferedImage(cm, raster, cm.isAlphaPremultiplied(), null);
			} else {
				target = new BufferedImage(width, hight, imageType);
			}
			Graphics2D graphics2d = target.createGraphics();
			graphics2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			graphics2d.drawRenderedImage(sourceBufferedImage, AffineTransform.getScaleInstance(sx, sy));
			graphics2d.dispose();
			
			ImageIO.write(target, formatName, targetFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void resizeDirectory(File file) {
		System.err.println("file: " + file);
		if (file.isDirectory()) {
			for (File subFile : file.listFiles()) {
				resizeDirectory(subFile);
			}
		} else {
			String filePath = file.getAbsolutePath();
			String fileName = file.getName();
			String newFileName = fileName.substring(0, fileName.indexOf(".")) + "_thumb" + fileName.substring(fileName.indexOf("."));
			filePath = filePath.replace(fileName, newFileName);
			resize(file, new File(filePath), 232, 156);
		}
	}

	public static void main(String args[]) {
		String filePath = "D:/1t9twXUl.jpg";
		File file = new File(filePath);
		System.err.println(file.exists());
		System.err.println(file.length());
		resize(filePath, filePath.replace("9tw", "9tw2222"), 400, 400);
	}
}