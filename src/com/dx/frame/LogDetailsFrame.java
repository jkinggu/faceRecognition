package com.dx.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.dx.LogDetailsPanel;

public class LogDetailsFrame extends JFrame {
	
	private JPanel panel2 = null;
	
	public LogDetailsFrame() {
//		this.setSize(1610, 668);
//		this.setPreferredSize(new Dimension(1610,668));
//		this.setLocationRelativeTo(null);
		this.setExtendedState( Frame.MAXIMIZED_BOTH );//最大化
		//this.set
		setTitle("认证日志详情");
		
		setLayout(new BorderLayout());	
		panel2 = new LogDetailsPanel();
		getContentPane().add(panel2);
		setVisible(true);
		
		
	}
	

}
