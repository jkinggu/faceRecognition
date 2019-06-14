package com.dx.frame;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.dx.DataPortPanel;

public class DataPortFrame  extends JFrame{

	private JPanel panel2 = null;
	
	public DataPortFrame() {
		
		this.setSize(1610, 668);
		this.setPreferredSize(new Dimension(1610,668));
		this.setLocationRelativeTo(null);
		setTitle("数据导入导出");
		
		panel2 = new DataPortPanel();
		getContentPane().add(panel2);
		setVisible(true);
		
		
	}
	
}
