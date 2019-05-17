package com.dx.test.camera;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.junit.Test;

import com.dx.CameraCapture;

/**
 * @author fang
 *
 * @Date 2019年5月15日
 *
 * 项目名 FaceRecongnition
 *
 *@version 1.0
 */

public class CameraCaptureTest {

	@Test
	public void testCam() {
		JFrame frame = new JFrame("camera");
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		int x = (int)toolkit.getScreenSize().getWidth();
		int y = (int)toolkit.getScreenSize().getHeight();
		frame.setLocation(x/2-700, y/2-384);
		frame.setSize(1400, 768);
		frame.setVisible(true);
		
		//FacePhoPanel facepanel =  new FacePhoPanel() ;
	    JPanel panel=new CameraCapture();
	    
	    frame.setContentPane(panel);
	    
	    frame.dispose();
		
	}
	
}
