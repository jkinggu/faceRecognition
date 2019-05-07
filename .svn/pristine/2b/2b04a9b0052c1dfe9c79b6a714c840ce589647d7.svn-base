package com.dx.util;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;


/**图片相似度*/
public class BMPLoader {
	// 改变成二进制码
	public static String[][] getPX(String args) {
		int[] rgb = new int[3];
 
		File file = new File(args);
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
 
		int width = bi.getWidth();
		int height = bi.getHeight();
		int minx = bi.getMinX();
		int miny = bi.getMinY();
		String[][] list = new String[width][height];
		for (int i = minx; i < width; i++) {
			for (int j = miny; j < height; j++) {
				int pixel = bi.getRGB(i, j);
				rgb[0] = (pixel & 0xff0000) >> 16;
				rgb[1] = (pixel & 0xff00) >> 8;
				rgb[2] = (pixel & 0xff);
				list[i][j] = rgb[0] + "," + rgb[1] + "," + rgb[2];
 
			}
		}
		return list;
 
	}
	
	public static void compareImage(String imgPath1, String imgPath2){
		String[] images = {imgPath1, imgPath2};
		if (images.length == 0) {
			System.out.println("Usage >java BMPLoader ImageFile.bmp");
			System.exit(0);
		}
 
		// 分析图片相似度 begin
		String[][] list1 = getPX(images[0]);
		String[][] list2 = getPX(images[1]);
		int xiangsi = 0;
		int busi = 0;
		int i = 0, j = 0;
		for (String[] strings : list1) {
			if ((i + 1) == list1.length) {
				continue;
			}
			for (int m=0; m<strings.length; m++) {
				try {
					String[] value1 = list1[i][j].toString().split(",");
					String[] value2 = list2[i][j].toString().split(",");
					int k = 0;
					for (int n=0; n<value2.length; n++) {
						if (Math.abs(Integer.parseInt(value1[k]) - Integer.parseInt(value2[k])) < 5) {
							xiangsi++;
						} else {
							busi++;
						}
					}
				} catch (RuntimeException e) {
					continue;
				}
				j++;
			}
			i++;
		}
 
		list1 = getPX(images[1]);
		list2 = getPX(images[0]);
		i = 0;
		j = 0;
		for (String[] strings : list1) {
			if ((i + 1) == list1.length) {
				continue;
			}
			for (int m=0; m<strings.length; m++) {
				try {
					String[] value1 = list1[i][j].toString().split(",");
					String[] value2 = list2[i][j].toString().split(",");
					int k = 0;
					for (int n=0; n<value2.length; n++) {
						if (Math.abs(Integer.parseInt(value1[k]) - Integer.parseInt(value2[k])) < 5) {
							xiangsi++;
						} else {
							busi++;
						}
					}
				} catch (RuntimeException e) {
					continue;
				}
				j++;
			}
			i++;
		}
		String baifen = "";
		try {
			baifen = ((Double.parseDouble(xiangsi + "") / Double.parseDouble((busi + xiangsi) + "")) + "");
			baifen = baifen.substring(baifen.indexOf(".") + 1, baifen.indexOf(".") + 3);
		} catch (Exception e) {
			baifen = "0";
		}
		if (baifen.length() <= 0) {
			baifen = "0";
		}
		if(busi == 0){
			baifen="100";
		}
 
		System.out.println("相似像素数量：" + xiangsi + " 不相似像素数量：" + busi + " 相似率：" + Integer.parseInt(baifen) + "%");
 
	}
 
	public static void main(String[] args){
		BMPLoader.compareImage("C:\\Users\\admin\\Desktop\\del/bjwj/lm1.png", "C:\\Users\\admin\\Desktop\\del/bjwj/gkc.png");
	}
	
	//图片切割
	public BufferedImage SplitImage(BufferedImage img, int x, int y, 
			int width, int height) {
		if(x+width >= img.getWidth() || y+height >= img.getHeight()) {
			return null;
		}else {
			BufferedImage newImg = new BufferedImage(width, height, 
					BufferedImage.TYPE_INT_ARGB);
			for(int i=x;i<x+width;i++) {
				for(int j=y;j<y+height;j++) {
					newImg.setRGB(i-x, j-y, img.getRGB(i, j));
				}
			}
			return newImg;
		}
	}
	/**
     * Mat转换成BufferedImage
     *
     * @param matrix
     *            要转换的Mat
     * @param fileExtension
     *            格式为 ".jpg", ".png", etc
     * @return
     */
    public static BufferedImage Mat2BufImg (Mat matrix, String fileExtension) {
        MatOfByte mob = new MatOfByte();
        Imgcodecs.imencode(fileExtension, matrix, mob);
        byte[] byteArray = mob.toArray();
        BufferedImage bufImage = null;
        try {
            InputStream in = new ByteArrayInputStream(byteArray);
            bufImage = ImageIO.read(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bufImage;
    }
 
    /**
     * BufferedImage转换成Mat
     *
     * @param original
     *            要转换的BufferedImage
     * @param imgType
     *            bufferedImage的类型 如 BufferedImage.TYPE_3BYTE_BGR
     * @param matType
     *            转换成mat的type 如 CvType.CV_8UC3
     */
    public static Mat BufImg2Mat (BufferedImage original, int imgType, int matType) {
        if (original == null) {
            throw new IllegalArgumentException("original == null");
        }
        System.out.println(original.getType()+"~~~~~~~~~"+imgType);
        if (original.getType() != imgType) {
 
            // Create a buffered image
            BufferedImage image = new BufferedImage(original.getWidth(), original.getHeight(), imgType);
 
            // Draw the image onto the new buffer
            Graphics2D g = image.createGraphics();
            try {
                g.setComposite(AlphaComposite.Src);
                g.drawImage(original, 0, 0, null);
            } finally {
                g.dispose();
            }
        }
        
//        byte[] pixels = ((DataBufferByte) original.getRaster().getDataBuffer()).getData();
//        Mat mat = Mat.eye(original.getHeight(), original.getWidth(), matType);
//        mat.put(0, 0, pixels);
        System.out.println("class--"+original.getRaster().getDataBuffer().getClass());
        DataBuffer dbi = original.getRaster().getDataBuffer();
        byte[] pixels = ((DataBufferByte)dbi).getData();
        
        Mat mat = Mat.eye(original.getHeight(), original.getWidth(), matType);
        mat.put(0, 0, pixels);
        return mat;
    }
}

