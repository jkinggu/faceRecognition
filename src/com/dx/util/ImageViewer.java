package com.dx.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImageViewer extends JPanel {
	private static final long serialVersionUID = 7581047317531797244L;
	private static Image srcImage;
	private static int dstwidth = 0;
	private static int dstheight = 0;
	public ImageViewer(String ImageFileName) {
		srcImage =Toolkit.getDefaultToolkit().getImage(this.getClass().getResource( ImageFileName));
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int srcwidth = srcImage.getWidth(this);
		int srcheight = srcImage.getHeight(this);
		int maxsize = 100;
		dstwidth = srcwidth;
		dstheight = srcheight;
		float k = 0;
		if (srcwidth > maxsize) {
			dstwidth = maxsize;
			k = (float) srcwidth / (float) maxsize;
			dstheight = Math.round((float) srcheight / k);
		}
		srcheight = dstheight;
		if (srcheight > maxsize) {
			dstheight = maxsize;
			k = (float) srcheight / (float) maxsize;
			dstwidth = Math.round((float) dstwidth / k);
		}
		if (srcImage != null) {
			g.drawImage(srcImage,0,0,dstwidth, dstheight,null);
		}
		
	}
	
}

