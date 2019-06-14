package com.dx.test.frameborder;

import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;

/**
 * @author Administrator
 *
 * @Date 2019年6月12日
 *
 * 项目名 FaceRecongnition
 *
 *@version 1.0
 */
public class OterTest {
	public static void main(String[] args) {
        JFrame frame=new JFrame("hello");
		frame.setBounds(0, 0, getScreenWidth(),getScreenHeight());
		frame.setVisible(true);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    System.out.println(frame.getHeight()+"--------"+frame.getWidth());
	    frame.addComponentListener(new ComponentListener() {
	
			@Override
			public void componentResized(ComponentEvent e) {
			    System.out.println(frame.getWidth()+"==="+frame.getHeight());
				
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		
		});
	    
           		
	}
	private static int getScreenWidth() {
		return (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();	
	}
    private static int getScreenHeight() {
    	return (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    	
    }
}
