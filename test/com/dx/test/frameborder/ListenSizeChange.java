package com.dx.test.frameborder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import Sense4.Test;

/**
 * @author Administrator
 *
 * @Date 2019年6月11日
 *
 * 项目名 FaceRecongnition
 *
 *@version 1.0
 */
public class ListenSizeChange {
	  private JFrame frame;
	    private JMenuBar menuBar;
	    private int screenWidth;
	    private int screenHeight;
	    private int windowWidth = 800;
	    private int windowHeight = 500;

	    public static void main(String[] args) {
	        new ListenSizeChange();
	    }

	    public ListenSizeChange() {
	    	
	        frame = new JFrame();
	        frame.setBounds(0, 0, 1167, 740);
  
	        //自动将窗口放到屏幕正中间
	        frame.setBounds(getScreenWidth()/2-frame.getWidth()/2,getScreenHeight()/2-frame.getHeight()/2, windowWidth, windowHeight);
	        frame.setVisible(true);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setLayout(new BorderLayout());

	        menuBar = new JMenuBar();
	        menuBar.setBounds(0, 0, windowWidth, 21);
	        frame.getContentPane().add(menuBar,BorderLayout.NORTH);
	        
           
            
            
            JPanel centerPanel=new JPanel();
            centerPanel.setBackground(Color.gray);
            centerPanel.setPreferredSize(new Dimension(500, 400));
            frame.getContentPane().add(centerPanel,BorderLayout.WEST);
            
            centerPanel.addComponentListener(new ComponentAdapter() {
            	@Override
            	public void componentResized(ComponentEvent e) {
            		 System.out.println("尺寸改变了！"+centerPanel.getWidth()+"===="+centerPanel.getHeight());
            		
            		 
            	}
            	
			});
	        //监听窗口尺寸改变事件
	        frame.addComponentListener(new ComponentAdapter() {
	            @Override
	            public void componentResized(ComponentEvent e) {
	           // System.out.println("尺寸改变了！"+frame.getWidth()+"===="+frame.getHeight());
	           menuBar.setSize(frame.getWidth(), 21);
	            if(centerPanel.getWidth()>frame.getWidth()) {
       			    centerPanel.setSize(frame.getWidth(),centerPanel.getHeight());
   
       		 }
	            
	            
	            
	        }
	        });

	    }

	    public int getScreenWidth(){
	        screenWidth = (int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	        return screenWidth;
	    }

	    public int getScreenHeight(){
	        screenHeight = (int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().height; 
	        return screenHeight;
	    }
	
}
