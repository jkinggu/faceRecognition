package com.dx.test.camera;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.dx.Person;
import com.dx.pojo.PersonCard;
import com.dx.test.frameborder.ImagePanel;

/**
 * @author Administrator
 *
 * @Date 2019年6月14日
 *
 * 项目名 FaceRecongnition
 *
 *@version 1.0
 */
public class PersonTest {
	private static Boolean flag=false;
	

	public static void main(String[] args) {
		JFrame frame=new JFrame("身份证阅读器");
		frame.setSize(800, 600);
	    JPanel panel1=new JPanel();
		panel1.setSize(800,600);
		panel1.setBackground(Color.green);
		JLabel label=new JLabel("253164568");
		panel1.add(label);
		panel1.setVisible(false);
	    
	    
		
		
		ImagePanel imagePanel=new ImagePanel(750,1000);
		imagePanel.setVisible(false);
		
		frame.getContentPane().add(panel1);
		frame.getContentPane().add(imagePanel);
		
		if(!flag) {
			panel1.setVisible(false);
			imagePanel.setVisible(true);
		}else {
			imagePanel.setVisible(false);
			panel1.setVisible(true);
			
		}
		
		
		Person  person= new Person();
		person.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName().equals("ischange")) {
					
				    flag=(Boolean) evt.getNewValue();
				    System.out.println(flag);
				   
				   if(flag) {
					   imagePanel.setVisible(false);
						panel1.setVisible(true);
						frame.repaint();
						
					   if(!flag) {
							
							Timer timer=new Timer(true);
							timer.schedule(new TimerTask() {
								@Override
								public void run() {
									panel1.setVisible(false);
									imagePanel.setVisible(true);
									frame.repaint();
									
								}
							}, 2500);
						   
					   }else {
						   
						   
						   
						   
						   
					   }
						
						
					   
					   
				   }
				    
//					if(!flag) {
//						
//						Timer timer=new Timer(true);
//						timer.schedule(new TimerTask() {
//							@Override
//							public void run() {
//								panel1.setVisible(false);
//								imagePanel.setVisible(true);
//								frame.repaint();
//								
//							}
//						}, 2500);
//						
//						try {
//							Thread.sleep(2000);
//						} catch (InterruptedException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
						
//						
//						panel1.setVisible(true);
//						imagePanel.setVisible(false);
//						frame.repaint();
//						try {
//							Thread.currentThread().sleep(3000);
//						} catch (InterruptedException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
						
					
						
			//		}else {
						
//						Timer timer=new Timer(true);
//						timer.schedule(new TimerTask() {
//							
//							@Override
//							public void run() {
//							
//								panel1.setVisible(false);
//								imagePanel.setVisible(true);
//								
//								
//								frame.repaint();
//								
//							}
//					}, 2000);
						
						
					
//						
//						imagePanel.setVisible(false);
//						panel1.setVisible(true);
//						frame.repaint();
//						
						
						
					
						
						
//					}
				    
				    
				    
				    
					PersonCard card = person.getCard1() == null ? person.getCard2() : person.getCard2();
					
					
				}
		  }
		});
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
  
}

