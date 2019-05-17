package com.dx.test.camera;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import com.dx.CameraCapture;

import lombok.NoArgsConstructor;

/**
 * @author fang
 *
 * @Date 2019年5月16日
 *
 *       项目名 FaceRecongnition
 *
 * @version 1.0
 */
@NoArgsConstructor
public class CamerPanel extends JPanel {
	private CoreCamer panel1=null;
	public JPanel getCamerPanel() {
		JPanel controlp = new JPanel();
		BoxLayout layout=new BoxLayout(controlp,BoxLayout.X_AXIS);
		
	    controlp.setLayout(layout);
		panel1 = new CoreCamer();
		
    	//panel1.setBounds(0, 0, 700, 768);
		// panel1.setSize(new Dimension(800, 800));
		// panel1.setSize(800, 600);
		panel1.setPreferredSize(new Dimension(800,800));
		controlp.add(panel1, "panel1");
		return controlp;
		
	}
	
	/*
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	private BufferedImage mImg;
	
	public void paintComponent(Graphics g) {
		if (mImg != null) {
			g.drawImage(mImg, 0, 0, mImg.getWidth(), mImg.getHeight(), this);
			
		}
	}
	public static void main(String[] args) {
		try {
			Mat capMat = new Mat();
			Mat temp = new Mat();
			VideoCapture capture = CamperUtil.getVideoCapture();
			JFrame frame = new JFrame("Camer");
			frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			int x = (int) toolkit.getScreenSize().getWidth();
			int y = (int) toolkit.getScreenSize().getHeight();
			frame.setLocation(x / 2 - 700, y / 2 - 384);
			frame.setSize(1400, 768);
			frame.setVisible(true);
			CamerPanel panel = new CamerPanel();
			frame.setContentPane(panel);
			
			while (frame.isShowing()) {
				capture.read(capMat);
				Imgproc.cvtColor(capMat, temp, Imgproc.COLOR_RGB2BGR);
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}*/
 
}
