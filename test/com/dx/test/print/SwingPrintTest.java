package com.dx.test.print;

import java.io.File;
import java.io.FileInputStream;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import org.junit.Test;


/**
 * @author Administrator
 *
 * @Date 2019年5月27日
 *
 * 项目名 FaceRecongnition
 *
 *@version 1.0
 */
public class SwingPrintTest {
   
	@Test
	public void testPrintDemo() {
		JFileChooser fileChooser=new JFileChooser();//文件选择框
		int state=fileChooser.showOpenDialog(null);//展示文件选择框
		if(state==fileChooser.APPROVE_OPTION) {
			//获取选择的文件
			File file=fileChooser.getSelectedFile();
			//构建打印请求
			HashPrintRequestAttributeSet pras=new HashPrintRequestAttributeSet();
	    	//设置打印格式，未确定类型，选择auto		
			DocFlavor flavor=DocFlavor.INPUT_STREAM.AUTOSENSE;
			
			//查找所有可用的打印服务
			PrintService printService[]=PrintServiceLookup.lookupPrintServices(flavor,pras);
			
			//定位默认打印服务
		    	
			PrintService defaultService=PrintServiceLookup.lookupDefaultPrintService();
			//显示打印对话框
			PrintService service=ServiceUI.printDialog(null,200,200,printService,defaultService, flavor,pras);
			if(service!=null) {
				try {
					DocPrintJob job=service.createPrintJob();//创建打印作业
					FileInputStream fis=new FileInputStream(file);//构造待打印文件
					DocAttributeSet das=new HashDocAttributeSet();//构建待打印文档属性集合
					
					Doc doc=new SimpleDoc(file, flavor, das);//创建可打印文档
					
					job.print(doc,pras);//打印（处理后的文件，打印机指令集
				 
					
				}catch(Exception e) {
					e.printStackTrace();
				}
				
			}
			
		}
	}
	
	@Test
	public void testTablePrint() {
	   JFrame frame=new TablePrintTest();
	
		
	}
	
	
}
