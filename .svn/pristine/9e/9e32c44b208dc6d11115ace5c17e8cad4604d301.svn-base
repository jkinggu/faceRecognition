package com.dx.test.camera;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import lombok.Getter;
import lombok.Setter;

/**
 * @author fang
 *
 * @Date 2019年5月16日
 *
 *       项目名 FaceRecongnition
 *
 * @version 1.0
 */
@Getter @Setter
public class CoreCamera extends JPanel {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	private static final long serialVersionUID = 1L;
	private  BufferedImage mImg;
	private Thread mainThread = null;
    Mat mat = new Mat();
	private VideoCapture camera = null;

	
	public CoreCamera() {	
		camera=CameraUtil.getVideoCapture();		
		try {
			  mainThread=new Thread(new Runnable() {			
				@Override
				public void run() {
				       Mat temp=new Mat();
				       while(true) {
				    	    camera.read(mat);
							Imgproc.cvtColor(mat, temp,Imgproc.COLOR_RGB2BGR);
							mImg=CameraUtil.mat2BI(CameraUtil.detectFace(mat));
							repaint();//p
					   }
				   				
				}
			});
			 mainThread.setDaemon(true);
			 mainThread.setName("cameraThread");
			 mainThread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			
		}
	}
	
	public  void paintComponent(Graphics g) {
		if (mImg != null) {
			g.drawImage(mImg, 0, 0, mImg.getWidth(), mImg.getHeight(), this);
			
		}
	}
	

}
