package com.dx.test.print;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * @author Administrator
 *
 * @Date 2019年5月27日
 *
 *       项目名 FaceRecongnition
 *
 * @version 1.0
 */
public class TablePrintTest extends JFrame implements Printable, ActionListener {
	private Object[][] data = { { "100", "0.8", "0.1", "90" } };
	// Jtable表头
	private Object[] head = { "单价", "折扣", "税", "调整后价格" };
	// 存放数据的JTable
	JTable table = new JTable(data, head);
	// 打印按钮
	private JButton printBtn = new JButton("打印");
	private JScrollPane scrollPane = new JScrollPane(table);
    
	
	
	public TablePrintTest() {
		setSize(800, 600);
		add(printBtn, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
		printBtn.addActionListener(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public int print(Graphics grps, PageFormat format, int pageIndex) throws PrinterException {
       Graphics2D g=(Graphics2D)grps;
       int x=(int)format.getImageableX();
       int y=(int)format.getImageableY();
       switch (pageIndex) {
	   case 0:
		  g.setColor(Color.RED);
		  g.drawString("单价"+table.getValueAt(0, 0),x+100,y+10);
		  g.drawString("折扣"+table.getValueAt(0,1),x+100,y+30);
		  g.drawString("税率"+table.getValueAt(0,2),x+100,y+50);
		  g.drawString("单价"+table.getValueAt(0,3),x+100,y+70);
		 return 0;
	  default:
		return 0;
	  }
	  }
	
	@Override
	public void actionPerformed(ActionEvent e) {
	   //获取打印服务对象
	   PrinterJob job=PrinterJob.getPrinterJob();
	   job.setPrintable(TablePrintTest.this);
	   try {
		job.print();
	} catch (Exception e2) {
		// TODO: handle exception
		e2.printStackTrace();
	}
	   JOptionPane.showMessageDialog(this, "导出打印pdf文件成功");
		
	}
	
	public static void main(String[] args) {
		JFrame frame=new TablePrintTest();
	}

}
