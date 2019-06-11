package com.dx.frame;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.opencv.core.Mat;

import com.dx.FacePhoPanel;

public class FacePhoFrame extends JFrame{
	
	private JPanel panel = null;
	private FacePhoPanel facepanel = null ;
	
	
	
	public FacePhoFrame() {
		
		
//		this.setSize(1610, 668);
//		this.setPreferredSize(new Dimension(1610,668));
//		this.setLocationRelativeTo();
//		this.setSize(Frame.MAXIMIZED_HORIZ, 768);
//		this.setLocation(0,100);
		this.setExtendedState( Frame.MAXIMIZED_BOTH);//水平方向最大化
		setTitle("人脸采集认证");
		setLayout(new FlowLayout(FlowLayout.CENTER,10,42));
		
		
		
		//下面是打开摄像头和打开身份证阅读器
		facepanel =  new FacePhoPanel();
		panel = facepanel.getFacePhoPanel();				
		facepanel.getPerson();			
		getContentPane().add(panel);
		setVisible(true);
		
		
		//关闭窗口事件
//		addWindowListener(new WindowAdapter() {
//			public void windowClosing(WindowEvent e) {
//				try {
//					if(panel != null) {
//						//getContentPane().remove(panel);//清空里面的内容,重新添加可能速度很慢
//						panel.updateUI();//建议用法				 
//						repaint();
//						if(facepanel != null && facepanel.getPanel1() != null && facepanel.getPanel1().getMainThread() != null) {
//							Thread mainThread = facepanel.getPanel1().getMainThread() ;					
//							facepanel.getPanel1().getCamera().release();
//							facepanel.getPerson().closePerson();
//							facepanel.getPanel1().setMat(new Mat());
//						    facepanel.getPanel1().setImage();
//							mainThread.interrupt();//摄像头线程中止
//						}
//					}
//				} catch (Exception ion) {
//					System.out.println("摄像头或其线程中止报错");
//				}
//				//上面停止摄像头，下面销毁当前对象
//				
//				
//				
//			}
//		});
		
		
		
	}

}
