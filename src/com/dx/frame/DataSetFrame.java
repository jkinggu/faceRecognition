package com.dx.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.dx.DataSetUp;

public class DataSetFrame extends JFrame {

	private JPanel panel2 = null;
	
	public DataSetFrame() {
		this.setSize(1610, 668);
		this.setPreferredSize(new Dimension(1610,668));
		this.setLocationRelativeTo(null);
		setTitle("认证数据设置");
		
		setLayout(new BorderLayout());					
		panel2 = new DataSetUp();
		getContentPane().add(panel2);
		setVisible(true);
		
	}
	
}
