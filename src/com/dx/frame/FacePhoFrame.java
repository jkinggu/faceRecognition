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
		this.setExtendedState( Frame.MAXIMIZED_BOTH);//ˮƽ�������
		setTitle("�����ɼ���֤");
		setLayout(new FlowLayout(FlowLayout.CENTER,10,42));
		
		
		
		//�����Ǵ�����ͷ�ʹ����֤�Ķ���
		facepanel =  new FacePhoPanel();
		panel = facepanel.getFacePhoPanel();				
		facepanel.getPerson();			
		getContentPane().add(panel);
		setVisible(true);
		
		
		//�رմ����¼�
//		addWindowListener(new WindowAdapter() {
//			public void windowClosing(WindowEvent e) {
//				try {
//					if(panel != null) {
//						//getContentPane().remove(panel);//������������,������ӿ����ٶȺ���
//						panel.updateUI();//�����÷�				 
//						repaint();
//						if(facepanel != null && facepanel.getPanel1() != null && facepanel.getPanel1().getMainThread() != null) {
//							Thread mainThread = facepanel.getPanel1().getMainThread() ;					
//							facepanel.getPanel1().getCamera().release();
//							facepanel.getPerson().closePerson();
//							facepanel.getPanel1().setMat(new Mat());
//						    facepanel.getPanel1().setImage();
//							mainThread.interrupt();//����ͷ�߳���ֹ
//						}
//					}
//				} catch (Exception ion) {
//					System.out.println("����ͷ�����߳���ֹ����");
//				}
//				//����ֹͣ����ͷ���������ٵ�ǰ����
//				
//				
//				
//			}
//		});
		
		
		
	}

}
