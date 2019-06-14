package com.dx.test.camera;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.dx.CameraCore;

/**
 * @author Administrator
 *
 * @Date 2019年6月13日
 *
 * 项目名 FaceRecongnition
 *
 *@version 1.0
 */
public class testFullScreen  {
	public static void main(String[] args) {
		JFrame frame=new JFrame("满屏");
		//frame.setLocation(-1000, 789);
		frame.setSize(getScreenWidth(),getScreenHeight());
		
		JPanel leftPanel=new JPanel();
		leftPanel.setPreferredSize(new Dimension((int) (frame.getWidth()*0.6),(int)frame.getHeight()));		 
		leftPanel.setBackground(Color.gray);
		leftPanel.setLayout(new BorderLayout());
		//CameraCore cameraCore=new CameraCore((int)leftPanel.getPreferredSize().getWidth(),(int)leftPanel.getPreferredSize().getHeight());
		CameraCore cameraCore=new CameraCore((int) (leftPanel.getPreferredSize().getHeight()*1.5*1.33),(int) (leftPanel.getPreferredSize().getHeight()));
		
		
        cameraCore.setPreferredSize(new Dimension((int)leftPanel.getPreferredSize().getWidth(),(int)leftPanel.getPreferredSize().getHeight()));
		
        leftPanel.add(cameraCore,BorderLayout.SOUTH);
        
        
		
		frame.getContentPane().add(leftPanel,BorderLayout.WEST);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		
		
	}
	
	private static int getScreenWidth() {
		return (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	}

	private static int getScreenHeight() {
		return (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();

	}


}
