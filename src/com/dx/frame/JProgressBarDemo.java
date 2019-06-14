package com.dx.frame;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;


public class JProgressBarDemo extends JFrame{
	
	private JProgressBar progressBar = null ;
	public JProgressBarDemo(long size ){
		
	
		
    	setTitle("请等待");
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setBounds(100, 100, 250, 100);
    	
    	//setSize(250, 100);
		//setPreferredSize(new Dimension(width,height));
		setLocationRelativeTo(null);
    	
    	JPanel contentPane=new JPanel();
    	contentPane.setBorder(new EmptyBorder(5,5,5,5));
    	setContentPane(contentPane);
    	contentPane.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
    	progressBar=new JProgressBar();
    	progressBar.setStringPainted(true);
    	contentPane.add(progressBar);
    	setVisible(true);

    	new Thread(){
    		public void run(){
    			for(long i=0l;i<=size;i++){
    				try{
    					if(size==100) {
    						Thread.sleep(10);
    					}else {
    						Thread.sleep(1100);
    					}
    				}catch(InterruptedException e){
    					e.printStackTrace();
    				}
    			      progressBar.setValue((int)i);
    			}
    			//progressBar.setString("导入完毕");
    		}
    	}.start();


    }
	
    public static void main(String[]args){
    	JProgressBarDemo example=new JProgressBarDemo(100);
 //   	example.setFilesize(1024);
    	
    	
//    	JProgressBar progressBar=new JProgressBar();
//    	progressBar.setStringPainted(true);
//    	new Thread(){
//    		public void run(){
//    			for(long i=0l;i<=1024l;i++){
//    				try{
//    					Thread.sleep(100);
//    				}catch(InterruptedException e){
//    					e.printStackTrace();
//    				}
//    			      progressBar.setValue((int)i);
//    			}
//    			//progressBar.setString("升级完成！");
//    		}
//    	}.start();
//    	//xample.add(progressBar);
//    	example.setContentPane(progressBar);
//    	example.setVisible(true);
//    	contentPane.setVisible(true);
    	//progressBar.setVisible(true);
    }

	
}
