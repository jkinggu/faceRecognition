package com.dx.test.print;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
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
public class GraphicsTest {
	private Graphics g;
	public static void main(String[] args) {
		 JFrame frame=new JFrame("学生成绩表");
	        frame.setSize(1000,800);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setLayout(new BorderLayout());
	        Container contentPane=frame.getContentPane();
	        
	        
	    
	        JPanel tablePanel=new JPanel();
	        tablePanel.setPreferredSize(new Dimension(frame.getWidth(),frame.getHeight()));
	        tablePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
	      
		
		
		
		
	}
	
	

}
