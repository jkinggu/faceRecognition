package com.dx.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.table.TableModel;

import com.dx.pojo.CheckoutNeeded;
import com.fr.report.core.A.g;

/**
 * @author Administrator
 *
 * @Date 2019年6月10日
 *
 * 项目名 FaceRecongnition
 *
 *@version 1.0
 */
public class CheckoutPrint implements Printable{
	private TableModel model = null;
	private List<CheckoutNeeded> printList;
	private String info;
	private int totalRow = 0;
	private static final int LEFT = 0;
	private static final int RIGHT = 1;
	private static final int CENTER = 2;
	private static final int AUTO = 3;

	public void DataPrint(List<CheckoutNeeded> printList,String info) {
		this.printList= printList;
		this.info =info;
		//totalRow = printList.size();
		PrinterJob printJob = PrinterJob.getPrinterJob();
		printJob.setPrintable(this);
		if (printJob.printDialog()) {
			try {
				printJob.print();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
    
	private static final double paper_offset_x = 20;
	private static final double paper_offset_y = 20;
	private static final double paper_image_margin = 20;
	private static final double text_body_margin = 18;
	private static final double cell_padding_x = 2;
	private static final double cell_padding_y = 2;
	private static final double body_btm_margin = 20;
	private static final double body_cell_height = 20;
	private static final Font title_font = new Font("黑体", Font.PLAIN, 20);
	private static final Font time_font = new Font("Dialog", Font.PLAIN, 10);
	private static final Font body_font = new Font("Dialog", Font.PLAIN, 12);

	public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
		
		// 纸张宽
		double pageWidth = pf.getImageableWidth();
		// 纸张高
		double pageHeight = pf.getImageableHeight();
		// 打印的内容起始X
		double pageStartX = 72;
		// 打印的内容起始Y
		double pageStartY = 72;
        // System.out.println(pageWidth+" "+pageHeight+" "+pageStartX+"  "+pageStartY+ " "+pf.getWidth()+" "+pf.getHeight());
		// Cell高
		double cellH = 0;
       
	   

		// 计算Title以及其位置
		String title = info;
		g.setFont(title_font);
		FontMetrics titleFm = g.getFontMetrics();
		int titleW = titleFm.stringWidth(title);

		
		
	   //y方向分页数量，判断退出打印条件
		if(pageIndex+1>printList.size()) {
			return NO_SUCH_PAGE;
			
		}
	
		// 绘制Title
		g.setFont(title_font);
		g.drawString(title, (int) ((pageWidth - titleW) / 2),
				(int) (paper_offset_y + titleFm.getAscent()));
		
		
		// 表格以上的Margin
		double topMargin =paper_offset_y*2 + titleFm.getHeight() +titleFm.getAscent();
		
		//获取需要打印的元数据
		CheckoutNeeded ckn=printList.get(pageIndex);
		
		//具体打印元数据
	
		// 绘制图片 暂无信息
		g.translate((int)paper_offset_x,(int) (topMargin));
		int currentX=0;
		int currentY=0;
		//存储最大图片高度
		//double  preWidth=(pageWidth-2*paper_offset_y)/3;
		//规定图片高度
		double imgHeight=130;
		try {
	
			//准考图片打印
			 File f=new File("");
			 String filepath=f.getAbsolutePath();	
			//System.out.println(filepath);402  610
    	     Image image1 = ImageIO.read(new FileInputStream(new File(filepath+ckn.getZkzPho())));
  			 double imgWidth1=((double)image1.getWidth(null)/image1.getHeight(null))*imgHeight;  
  				
  			//身份证照片打印		 					
 		    Image image2= ImageIO.read(new FileInputStream(new File(ckn.getSfzPhoto())));
 			double imgWidth2=((double)image2.getWidth(null)/image2.getHeight(null))*imgHeight;
 			
           //现场照片打印
			Image image3= ImageIO.read(new FileInputStream(new File(ckn.getFacePhoto())));
				double imgWidth3=((double)image3.getWidth(null)/image3.getHeight(null))*imgHeight;
 			
 		    double preWidth=(pageWidth-2*paper_offset_x-imgWidth1-imgWidth2-imgWidth3-2*paper_image_margin)/2;
 		    
 		    currentX+=preWidth;
			g.drawImage(image1,currentX,currentY, (int)imgWidth1,
				(int)imgHeight, null);
		    
			currentX+=paper_image_margin+imgWidth1;
			g.drawImage(image2,currentX,currentY, (int)imgWidth2,
					(int)imgHeight, null);
			
		
			currentX+=paper_image_margin+imgWidth2;	
		    g.drawImage(image3,currentX,currentY, (int)imgWidth3,
						(int)imgHeight, null);	
	
		    
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
         
		//绘制具体数据
		g.setFont(body_font);
		g.translate((int)pageStartX,(int)(imgHeight+2*paper_offset_y));
		currentX=0;
		currentY=0;
		g.drawString("姓名:"+ckn.getName(),currentX,currentY);
		
		currentY+=text_body_margin;
		g.drawString("性别:"+ckn.getSex(),currentX,currentY);
		
		currentY+=text_body_margin;
		g.drawString("年龄:"+ckn.getAge(),currentX,currentY);
		
		currentY+=text_body_margin;
		g.drawString("准考证号:"+ckn.getZkzNum(),currentX,currentY);
		
		currentY+=text_body_margin;
		g.drawString("身份证号:"+ckn.getSfzNum(),currentX,currentY);
		
		currentY+=text_body_margin;
		g.drawString("工作单位:"+ckn.getDanweiName(),currentX,currentY);
		
		currentY+=text_body_margin;
		g.drawString("申报工种:"+ckn.getBaokaoName(),currentX,currentY);
		
		currentY+=text_body_margin;
		g.drawString("申报级别:"+ckn.getJbName(),currentX,currentY);
		
		//绘制Map
	    List<String> cc1=(List<String>) ckn.getTestInfo().get("职业道德"); 	
	    int mapX=currentX;
	    int mapy=currentY;
	    mapy+=text_body_margin;
	    int col2x=(int)(pageWidth-pageStartX*2);
	    int col2y=mapy;
	    int col1x=mapX;
		g.drawLine(mapX,mapy,(int)(pageWidth-pageStartX*2),mapy);
		g.drawLine(mapX,mapy,mapX,(int)(mapy+g.getFontMetrics().getHeight()*4)+1);
		int col1tx=(int) (mapX+cell_padding_x);
        mapX+=((int)(g.getFontMetrics().stringWidth("职业道德"))+2*cell_padding_x);
        int col1x1=mapX;
		g.drawLine(mapX,mapy,mapX,(int)(mapy+g.getFontMetrics().getHeight()*4)+1); 
      	mapX+=cell_padding_x;
      	mapy+=(cell_padding_y*2+text_body_margin);
      	int col2tx=mapX;
      	g.drawString("考场:"+cc1.get(0),mapX,mapy);
        g.drawString("座号:"+cc1.get(1),mapX+120,mapy);
		mapy+=text_body_margin;
		g.setColor(new Color(255, 0, 0));
		g.drawString("考试时间:"+cc1.get(2),mapX,mapy);
		g.setColor(new Color(0, 0, 0));
		g.drawString("职业道德",col1tx, mapy);
		mapy+=text_body_margin;		
	    g.drawString("考试地点:"+cc1.get(3),mapX,mapy);
		mapy+=text_body_margin;
		g.drawLine((col1tx-(int)cell_padding_x),mapy-2, (int)(pageWidth-pageStartX*2),mapy-2);
      	g.drawLine(col2x,col2y,col2x,mapy-2);
		
      	List<String> cc2=(List<String>) ckn.getTestInfo().get("职业能力");
      	mapy-=2;
      	int col2y2=mapy;
      	g.drawLine(col1x,mapy,col1x,(int)(mapy+g.getFontMetrics().getHeight()*4)+1);
      	g.drawLine(col1x1,mapy,col1x1,(int)(mapy+g.getFontMetrics().getHeight()*4)+1);
      	mapy+=(cell_padding_y*2+text_body_margin);
    	g.drawString("考场:"+cc2.get(0),col2tx,mapy);
        g.drawString("座号:"+cc2.get(1),col2tx+120,mapy);
		mapy+=text_body_margin;
		g.setColor(new Color(255, 0, 0));
		g.drawString("考试时间:"+cc2.get(2),col2tx,mapy);
		g.setColor(new Color(0, 0, 0));
		g.drawString("职业能力",col1tx, mapy);
		mapy+=text_body_margin;		
	    g.drawString("考试地点:"+cc2.get(3),col2tx,mapy);
		mapy+=(text_body_margin-2);
		g.drawLine(col1x,mapy, (int)(pageWidth-pageStartX*2),mapy);
      	g.drawLine(col2x,col2y2,col2x,mapy);
		
      	List<String> cc3=(List<String>) ckn.getTestInfo().get("岗位技能");
      	
      	int col2y3=mapy;
      	g.drawLine(col1x,mapy,col1x,(int)(mapy+g.getFontMetrics().getHeight()*4)+1);
      	g.drawLine(col1x1,mapy,col1x1,(int)(mapy+g.getFontMetrics().getHeight()*4)+1);
      	mapy+=(cell_padding_y*2+text_body_margin);
    	g.drawString("考场:"+cc3.get(0),col2tx,mapy);
        g.drawString("座号:"+cc3.get(1),col2tx+120,mapy);
		mapy+=text_body_margin;
		g.setColor(new Color(255, 0, 0));
		g.drawString("考试时间:"+cc3.get(2),col2tx,mapy);
		g.setColor(new Color(0, 0, 0));
		g.drawString("岗位技能",col1tx, mapy);
		mapy+=text_body_margin;		
	    g.drawString("考试地点:"+cc3.get(3),col2tx,mapy);
		mapy+=(text_body_margin-2);
		g.drawLine(col1x,mapy, (int)(pageWidth-pageStartX*2),mapy);
      	g.drawLine(col2x,col2y2,col2x,mapy);
      	
      	//表格绘制结束
      	
      	//最后三行
        currentY=(int) (mapy+paper_offset_y*2);
        g.drawString("本人签名:",currentX,currentY);
        g.drawLine(currentX+(int)g.getFontMetrics().stringWidth("本人签名:")+(int)cell_padding_x,currentY, currentX+(int)g.getFontMetrics().stringWidth("本人签名:")+(int)cell_padding_x+120, currentY);
		
		currentY+=paper_offset_y*2;
		g.drawString("核查结果:"+ckn.getRzResult(),currentX,currentY);
		
		currentY+=paper_offset_y*2;
		g.drawString("打印日期:"+ckn.getPrintTime(),currentX,currentY);  
	
		return PAGE_EXISTS;
	}
}
