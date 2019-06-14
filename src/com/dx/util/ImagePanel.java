package com.dx.util;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
/**
 * @author Administrator
 *
 * @Date 2019年6月14日
 *
 * 项目名 FaceRecongnition
 *
 *@version 1.0
 */
public class ImagePanel extends JPanel{
	private Image image=null;
	private File file=new File("");
	private Integer width;
	private Integer height;
	
	
	public ImagePanel(Integer width,Integer height) {
		this.width=width;
		this.height=height;
	}
	public void paint(Graphics g){
		String filePath=file.getAbsolutePath();
		//System.out.println(filePath);
		try {
			image=ImageIO.read(new File(filePath+"/adv1.jpg"));
			g.drawImage(image,0,0,this.width, this.height,null);
		} catch (Exception e) {
			// TODO: handle exception
			
		}
		

		
		
		
	}
	
	
	

}
