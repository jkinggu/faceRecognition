package com.dx.test.print;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * @author Administrator
 *
 * @Date 2019年6月13日
 *
 * 项目名 FaceRecongnition
 *
 *@version 1.0
 */
public class JScrollPanelTest {
	
	public static void main(String[] args) {
		
		
		 JFrame frame=new JFrame("学生成绩表");
	        frame.setSize(1000,800);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setLayout(new BorderLayout());
	        Container contentPane=frame.getContentPane();
	        
	        
	    
	        JPanel tablePanel=new JPanel();
	        tablePanel.setPreferredSize(new Dimension(frame.getWidth(),frame.getHeight()));
	        tablePanel.setLayout(new BorderLayout());
	        
	        
	        String[] name={"学号","软件工程","Java","网络","数据结构","数据库","总成绩","平均成绩"};
	        DefaultTableModel dftm=new DefaultTableModel();
	        dftm.setColumnIdentifiers(name);
	        JTable table=new JTable(dftm);
	        //table.setPreferredSize(new Dimension((int)tablePanel.getPreferredSize().getWidth(),(int)tablePanel.getPreferredSize().getHeight())); 
	    	table.setRowHeight(25);
			table.getTableHeader().setVisible(false);
		    table.setShowGrid(false);
	        table.setShowHorizontalLines(false);
	        table.setShowVerticalLines(false);
			table.setFont(new Font("Dialog", Font.PLAIN, 14));	
	        
	        
	        for(int i=0;i<30;i++) {
	        	Object[] data= {100+i,0,0,0,0,0,0,0};
	        	dftm.addRow(data);
	            
	        }
	        
//	        
//	        Object[][] tableDate=new Object[30][8];
//	        for(int i=0;i<30;i++)
//	        {
//	            tableDate[i][0]="1000"+i;
//	            for(int j=1;j<8;j++)
//	            {
//	                tableDate[i][j]=0;
//	            }
//	        }
	        
	 
	       
	        
	        JScrollPane scrollPane=new JScrollPane(table);
	        //scrollPane.setPreferredSize(new Dimension((int)tablePanel.getPreferredSize().getWidth(),(int)tablePanel.getPreferredSize().getHeight()));
	        
	        scrollPane.setBounds(100, 200, 500, 700);
	        scrollPane.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); 
			scrollPane.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
		   
	        tablePanel.add(scrollPane,BorderLayout.CENTER);
	        contentPane.add(tablePanel,BorderLayout.CENTER);
	        
	        
	        
	        
	        frame.setVisible(true);
		    frame.addComponentListener(new ComponentAdapter() {
		    	@Override
		    	public void componentResized(ComponentEvent e) {
		    		tablePanel.setPreferredSize(new Dimension(frame.getWidth(),frame.getHeight()));
		    	    scrollPane.setPreferredSize(new Dimension((int)tablePanel.getPreferredSize().getWidth(),(int)tablePanel.getPreferredSize().getHeight()));
		  	        
		    	}
		    }); 
		
	}
	
	

}
