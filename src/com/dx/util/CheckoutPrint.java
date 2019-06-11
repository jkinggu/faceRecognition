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
 * @Date 2019��6��10��
 *
 * ��Ŀ�� FaceRecongnition
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
	private static final Font title_font = new Font("����", Font.PLAIN, 20);
	private static final Font time_font = new Font("Dialog", Font.PLAIN, 10);
	private static final Font body_font = new Font("Dialog", Font.PLAIN, 12);

	public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
		
		// ֽ�ſ�
		double pageWidth = pf.getImageableWidth();
		// ֽ�Ÿ�
		double pageHeight = pf.getImageableHeight();
		// ��ӡ��������ʼX
		double pageStartX = 72;
		// ��ӡ��������ʼY
		double pageStartY = 72;
        // System.out.println(pageWidth+" "+pageHeight+" "+pageStartX+"  "+pageStartY+ " "+pf.getWidth()+" "+pf.getHeight());
		// Cell��
		double cellH = 0;
       
	   

		// ����Title�Լ���λ��
		String title = info;
		g.setFont(title_font);
		FontMetrics titleFm = g.getFontMetrics();
		int titleW = titleFm.stringWidth(title);

		
		
	   //y�����ҳ�������ж��˳���ӡ����
		if(pageIndex+1>printList.size()) {
			return NO_SUCH_PAGE;
			
		}
	
		// ����Title
		g.setFont(title_font);
		g.drawString(title, (int) ((pageWidth - titleW) / 2),
				(int) (paper_offset_y + titleFm.getAscent()));
		
		
		// ������ϵ�Margin
		double topMargin =paper_offset_y*2 + titleFm.getHeight() +titleFm.getAscent();
		
		//��ȡ��Ҫ��ӡ��Ԫ����
		CheckoutNeeded ckn=printList.get(pageIndex);
		
		//�����ӡԪ����
	
		// ����ͼƬ ������Ϣ
		g.translate((int)paper_offset_x,(int) (topMargin));
		int currentX=0;
		int currentY=0;
		//�洢���ͼƬ�߶�
		//double  preWidth=(pageWidth-2*paper_offset_y)/3;
		//�涨ͼƬ�߶�
		double imgHeight=130;
		try {
	
			//׼��ͼƬ��ӡ
			 File f=new File("");
			 String filepath=f.getAbsolutePath();	
			//System.out.println(filepath);402  610
    	     Image image1 = ImageIO.read(new FileInputStream(new File(filepath+ckn.getZkzPho())));
  			 double imgWidth1=((double)image1.getWidth(null)/image1.getHeight(null))*imgHeight;  
  				
  			//���֤��Ƭ��ӡ		 					
 		    Image image2= ImageIO.read(new FileInputStream(new File(ckn.getSfzPhoto())));
 			double imgWidth2=((double)image2.getWidth(null)/image2.getHeight(null))*imgHeight;
 			
           //�ֳ���Ƭ��ӡ
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
         
		//���ƾ�������
		g.setFont(body_font);
		g.translate((int)pageStartX,(int)(imgHeight+2*paper_offset_y));
		currentX=0;
		currentY=0;
		g.drawString("����:"+ckn.getName(),currentX,currentY);
		
		currentY+=text_body_margin;
		g.drawString("�Ա�:"+ckn.getSex(),currentX,currentY);
		
		currentY+=text_body_margin;
		g.drawString("����:"+ckn.getAge(),currentX,currentY);
		
		currentY+=text_body_margin;
		g.drawString("׼��֤��:"+ckn.getZkzNum(),currentX,currentY);
		
		currentY+=text_body_margin;
		g.drawString("���֤��:"+ckn.getSfzNum(),currentX,currentY);
		
		currentY+=text_body_margin;
		g.drawString("������λ:"+ckn.getDanweiName(),currentX,currentY);
		
		currentY+=text_body_margin;
		g.drawString("�걨����:"+ckn.getBaokaoName(),currentX,currentY);
		
		currentY+=text_body_margin;
		g.drawString("�걨����:"+ckn.getJbName(),currentX,currentY);
		
		//����Map
	    List<String> cc1=(List<String>) ckn.getTestInfo().get("ְҵ����"); 	
	    int mapX=currentX;
	    int mapy=currentY;
	    mapy+=text_body_margin;
	    int col2x=(int)(pageWidth-pageStartX*2);
	    int col2y=mapy;
	    int col1x=mapX;
		g.drawLine(mapX,mapy,(int)(pageWidth-pageStartX*2),mapy);
		g.drawLine(mapX,mapy,mapX,(int)(mapy+g.getFontMetrics().getHeight()*4)+1);
		int col1tx=(int) (mapX+cell_padding_x);
        mapX+=((int)(g.getFontMetrics().stringWidth("ְҵ����"))+2*cell_padding_x);
        int col1x1=mapX;
		g.drawLine(mapX,mapy,mapX,(int)(mapy+g.getFontMetrics().getHeight()*4)+1); 
      	mapX+=cell_padding_x;
      	mapy+=(cell_padding_y*2+text_body_margin);
      	int col2tx=mapX;
      	g.drawString("����:"+cc1.get(0),mapX,mapy);
        g.drawString("����:"+cc1.get(1),mapX+120,mapy);
		mapy+=text_body_margin;
		g.setColor(new Color(255, 0, 0));
		g.drawString("����ʱ��:"+cc1.get(2),mapX,mapy);
		g.setColor(new Color(0, 0, 0));
		g.drawString("ְҵ����",col1tx, mapy);
		mapy+=text_body_margin;		
	    g.drawString("���Եص�:"+cc1.get(3),mapX,mapy);
		mapy+=text_body_margin;
		g.drawLine((col1tx-(int)cell_padding_x),mapy-2, (int)(pageWidth-pageStartX*2),mapy-2);
      	g.drawLine(col2x,col2y,col2x,mapy-2);
		
      	List<String> cc2=(List<String>) ckn.getTestInfo().get("ְҵ����");
      	mapy-=2;
      	int col2y2=mapy;
      	g.drawLine(col1x,mapy,col1x,(int)(mapy+g.getFontMetrics().getHeight()*4)+1);
      	g.drawLine(col1x1,mapy,col1x1,(int)(mapy+g.getFontMetrics().getHeight()*4)+1);
      	mapy+=(cell_padding_y*2+text_body_margin);
    	g.drawString("����:"+cc2.get(0),col2tx,mapy);
        g.drawString("����:"+cc2.get(1),col2tx+120,mapy);
		mapy+=text_body_margin;
		g.setColor(new Color(255, 0, 0));
		g.drawString("����ʱ��:"+cc2.get(2),col2tx,mapy);
		g.setColor(new Color(0, 0, 0));
		g.drawString("ְҵ����",col1tx, mapy);
		mapy+=text_body_margin;		
	    g.drawString("���Եص�:"+cc2.get(3),col2tx,mapy);
		mapy+=(text_body_margin-2);
		g.drawLine(col1x,mapy, (int)(pageWidth-pageStartX*2),mapy);
      	g.drawLine(col2x,col2y2,col2x,mapy);
		
      	List<String> cc3=(List<String>) ckn.getTestInfo().get("��λ����");
      	
      	int col2y3=mapy;
      	g.drawLine(col1x,mapy,col1x,(int)(mapy+g.getFontMetrics().getHeight()*4)+1);
      	g.drawLine(col1x1,mapy,col1x1,(int)(mapy+g.getFontMetrics().getHeight()*4)+1);
      	mapy+=(cell_padding_y*2+text_body_margin);
    	g.drawString("����:"+cc3.get(0),col2tx,mapy);
        g.drawString("����:"+cc3.get(1),col2tx+120,mapy);
		mapy+=text_body_margin;
		g.setColor(new Color(255, 0, 0));
		g.drawString("����ʱ��:"+cc3.get(2),col2tx,mapy);
		g.setColor(new Color(0, 0, 0));
		g.drawString("��λ����",col1tx, mapy);
		mapy+=text_body_margin;		
	    g.drawString("���Եص�:"+cc3.get(3),col2tx,mapy);
		mapy+=(text_body_margin-2);
		g.drawLine(col1x,mapy, (int)(pageWidth-pageStartX*2),mapy);
      	g.drawLine(col2x,col2y2,col2x,mapy);
      	
      	//�����ƽ���
      	
      	//�������
        currentY=(int) (mapy+paper_offset_y*2);
        g.drawString("����ǩ��:",currentX,currentY);
        g.drawLine(currentX+(int)g.getFontMetrics().stringWidth("����ǩ��:")+(int)cell_padding_x,currentY, currentX+(int)g.getFontMetrics().stringWidth("����ǩ��:")+(int)cell_padding_x+120, currentY);
		
		currentY+=paper_offset_y*2;
		g.drawString("�˲���:"+ckn.getRzResult(),currentX,currentY);
		
		currentY+=paper_offset_y*2;
		g.drawString("��ӡ����:"+ckn.getPrintTime(),currentX,currentY);  
	
		return PAGE_EXISTS;
	}
}
