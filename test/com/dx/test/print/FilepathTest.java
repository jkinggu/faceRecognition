package com.dx.test.print;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.fr.process.pdl.io.SystemIOUtils;

/**
 * @author Administrator
 *
 * @Date 2019年6月10日
 *
 * 项目名 FaceRecongnition
 *
 *@version 1.0
 */
public class FilepathTest {
	
	
   public static void main(String[] args) {
		File f=new File("");
		String filepath=f.getAbsolutePath();
		//System.out.println(filepath);
		try {
			Image image = ImageIO.read(new FileInputStream(new File(filepath+"\\zkzpho\\411403199006107984.JPG")));
			System.out.println(image.getWidth(null));
			System.out.println(image.getHeight(null));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  
	  
	  
	  
	  
}     
   
   
}
