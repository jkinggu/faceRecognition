package com.dx.test.print;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.math.BigDecimal;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 * @author Administrator
 *
 * @Date 2019��5��27��
 *
 *       ��Ŀ�� FaceRecongnition
 *
 * @version 1.0
 */
public class PrintPanel extends JFrame implements  Printable {
	private Object[][] data = { { "100", "0.8", "0.1", "90" } };
	// Jtable��ͷ
	private Object[] head = { "����", "�ۿ�", "˰", "������۸�" };
	// ������ݵ�JTable
	JTable table = new JTable(data, head);
	private JScrollPane scrollPane = new JScrollPane(table);
	private JButton prtBtn=new JButton("��ӡ");
   
	
	private static final int LEFT = 0;   
    private static final int RIGHT = 1;   
    private static final int CENTER = 2;   
    private static final int AUTO = 3;   
	
	private static final double paper_offset_x = 20;   
    private static final double paper_offset_y = 20;   
    private static final double title_time_margin = 10;   
    private static final double time_body_margin = 2;   
    private static final double cell_padding_y = 3;   
    private static final double cell_padding_x = 2;   
    private static final double body_btm_margin = 20;   
    private static final double body_cell_height = 20;   
    private static final Font title_font = new Font("����", Font.PLAIN, 18);   
    private static final Font time_font = new Font("Dialog", Font.PLAIN, 10);   
    private static final Font body_font = new Font("Dialog", Font.PLAIN, 10);   
	
	
	
	public PrintPanel() {
		setSize(800, 600);
		 add(prtBtn,BorderLayout.NORTH);
		 add(scrollPane,BorderLayout.CENTER);
		 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 setVisible(true);
		 
		 prtBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PrinterJob job=PrinterJob.getPrinterJob();
				job.setPrintable(PrintPanel.this);
				if(job.printDialog()) {					
					try {
						job.print();
					} catch (Exception e2) {
						// TODO: handle exception
						e2.printStackTrace();
					}
					
				}	
			}
		});		 
	}
	@Override
	public int print(Graphics grp, PageFormat pf, int pageIndex) throws PrinterException {

		Graphics2D g = (Graphics2D) grp;
		g.setColor(Color.black);
		//ֽ�ų���
		Double pageWidth=pf.getImageableWidth();
		Double pageHeight=pf.getImageableHeight();
		// ��ӡ�������
		double x = pf.getImageableX();
		double y = pf.getImageableY();
		System.out.println(x+"  "+y+"  "+pageWidth+"  "+ pageHeight+"  "+pageIndex);
		
		//��ͷ��
		double tableHeadH=0;
		//��Ԫ���
		double cellH=0;
		
		//��ͷ�߶Ⱥ͵�Ԫ��߶�
		g.setFont(body_font); 
	    FontMetrics cellFm=g.getFontMetrics();
		cellH=cellFm.getHeight()+cell_padding_y*2+1;
	    tableHeadH=cellH*2;
	    
	    //����titleλ��
	    
	    //��׺ͱ�ͷ��������
	    g.setFont(time_font);
	    FontMetrics btmFm=g.getFontMetrics();
	    FontMetrics timeFm=g.getFontMetrics();
	    
	    //�������Margin������
	    
	    double tableTopMargin=paper_offset_y+title_time_margin+timeFm.getHeight()+time_body_margin;
	    
	    //���ÿ�������
	    
	    double[] cellColMaxWidths=caculateTableCellWidth(table.getModel(), cellFm);
	    
	    
	    //��ǰpage���������߶�
	    
	    double currentPageContainerHeight=pageHeight-tableTopMargin-tableHeadH-btmFm.getHeight()-body_btm_margin-1;
	    
	    //��ǰPage��������
	    
	    int currentPageBodyRows=(int)(currentPageContainerHeight/cellH);
	    
	    
	    //y�����ҳ����
	    
	    int pagesY=0;
	    if(table.getModel().getRowCount()%currentPageBodyRows==0) {
	    	pagesY=(int)(table.getModel().getRowCount()/currentPageBodyRows);
	    }else {
	    	pagesY=(int)(table.getModel().getRowCount()/currentPageBodyRows)+1;
		}
	    //��ǰҳ��������ҳ������ӡ
	    
	    if(pageIndex+1>pagesY) {	    	
	    	return NO_SUCH_PAGE;
	    }
		//����title
	    
	    
	   //���������ƶ����µģ�0,0����
	    
	    
	   //���Ƶ�һ�ű�
	   //���Ʊ�ͷ
	    //���Ƶ�һ��ͷ
	    
	    //��������
	    int currentX = 0, currentY = 0;   
	    currentY=(int)tableHeadH;
	    //��ǰPage��������
	    int rightCellX=0;
	    int yIndex=pageIndex;
	    
	    int startRow=currentPageBodyRows*yIndex;
	    int endRow=(currentPageBodyRows*(yIndex+1))>table.getModel().getRowCount()?
	    		table.getModel().getRowCount():(currentPageBodyRows*(yIndex+1));
	    for(int row=startRow;row<endRow;row++) {
	    	//���Ƶ����ͷ��������
	    	for(int i=0;i<table.getModel().getColumnCount();i++) {
	    		double width=cellColMaxWidths[i];
	    		double height=body_cell_height;
	    		Object value=table.getModel().getValueAt(row,i);
	    		drawCell(g, value, currentX,currentY,(int)width,(int)height,AUTO);
	    		currentX+=width;
	    		rightCellX=currentX;
	    	}
	    	currentX=0;
	    	currentY+=cellH;
	    }
	    
	    return PAGE_EXISTS;
	    

	}
	
	private static void drawCell(Graphics g, Object value, int x, int y,  int width,int height, int locate) {
		System.out.println("================");
		FontMetrics fm=g.getFontMetrics();
		if(value==null) {
			value="";
		}
		switch (locate) {
		case 0://����
			g.drawString(value.toString(),(int)(x+cell_padding_x), y+(height-fm.getHeight())/2+fm.getAscent());
		case 1://����
			   g.drawString(value.toString(),   
                       (int) (x +   
                       (width - fm.stringWidth(value.toString()) + width -   
                       fm.stringWidth(value.toString()) - cell_padding_x) /   
                       2), y +   
                       (height - fm.getHeight()) / 2 + fm.getAscent());    
		case 2://����
			  g.drawString(value.toString(), x + (width - fm.stringWidth(   
                      value.toString())) / 2, y + (height -   
                      fm.getHeight()) / 2 + fm.getAscent());
	    		  
		 case 3:   
             //�Զ��ж�   
             NumberFormat formatter = NumberFormat.getNumberInstance();   
             formatter.setMinimumFractionDigits(2);   
             formatter.setMaximumFractionDigits(2); 	  
             //����������������뻹���Ҷ�����ƻ��Ǿ��ж���   
             if (value instanceof BigDecimal) {   
                 //����   
                 value = ((BigDecimal) value).doubleValue();   
                 value = formatter.format(value);   
                 g.drawString(value.toString(),   
                         (int) (x +   
                         (width - fm.stringWidth(value.toString()) + width -   
                         fm.stringWidth(value.toString()) - cell_padding_x) /   
                         2), y +   
                         (height - fm.getHeight()) / 2 + fm.getAscent());   
             } else if (value instanceof Integer || value instanceof Long ||   
                     value instanceof Double) {   
                 //����   
                 g.drawString(value.toString(),   
                         (int) (x +   
                         (width - fm.stringWidth(value.toString()) + width -   
                         fm.stringWidth(value.toString()) - cell_padding_x) /   
                         2), y +   
                         (height - fm.getHeight()) / 2 + fm.getAscent());   
             } else {   
                 //����   
                 g.drawString(value.toString(), x + (width - fm.stringWidth(   
                         value.toString())) / 2, y + (height -   
                         fm.getHeight()) / 2 + fm.getAscent());   
             }    
		}
		
		
	}
	private static double[]  caculateTableCellWidth(TableModel model,FontMetrics cellFm) {
		//������ÿ�������
		double[] cellColMaxWidths=new double[model.getColumnCount()];
		
		//�����ͷÿ�������
		double[] headerColMaxWidths=new double[model.getColumnCount()];
		
		for(int i=0;i<model.getColumnCount();i++) {
			String name=model.getColumnName(i);
			headerColMaxWidths[i]=cellFm.stringWidth(name)+cell_padding_x*2+1;
		}
		
		//û������ʱ���ͷÿ������Ⱦ��Ǳ��ÿ�������
		
		cellColMaxWidths=headerColMaxWidths;
		
		//��������ÿ�е�����Ⱥͱ�ͷÿ������ȶԱ�
		
		for(int j=0;j<model.getRowCount();j++) {
			for(int i=0;i<model.getColumnCount();i++) {
				Object value=model.getValueAt(j, i);
				if(value instanceof BigDecimal) {
					value=((BigDecimal)value).doubleValue();
				}
				String text="";
				if(value!=null) {
					text=value.toString();
				}
				
				double temp=cellFm.stringWidth(text)+cell_padding_x*2+1;
				
				if(cellColMaxWidths[i]<temp) {
					cellColMaxWidths[i]=temp;
					
				}
				
			}						
			
		}
		
		
		
		return cellColMaxWidths;
	}

	public static void main(String[] args) {
			
			JFrame frame=new PrintPanel();
			
			
	}

}
