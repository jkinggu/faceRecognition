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
 * @Date 2019年5月27日
 *
 *       项目名 FaceRecongnition
 *
 * @version 1.0
 */
public class PrintPanel extends JFrame implements  Printable {
	private Object[][] data = { { "100", "0.8", "0.1", "90" } };
	// Jtable表头
	private Object[] head = { "单价", "折扣", "税", "调整后价格" };
	// 存放数据的JTable
	JTable table = new JTable(data, head);
	private JScrollPane scrollPane = new JScrollPane(table);
	private JButton prtBtn=new JButton("打印");
   
	
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
    private static final Font title_font = new Font("黑体", Font.PLAIN, 18);   
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
		//纸张长宽
		Double pageWidth=pf.getImageableWidth();
		Double pageHeight=pf.getImageableHeight();
		// 打印起点坐标
		double x = pf.getImageableX();
		double y = pf.getImageableY();
		System.out.println(x+"  "+y+"  "+pageWidth+"  "+ pageHeight+"  "+pageIndex);
		
		//表头高
		double tableHeadH=0;
		//单元格高
		double cellH=0;
		
		//表头高度和单元格高度
		g.setFont(body_font); 
	    FontMetrics cellFm=g.getFontMetrics();
		cellH=cellFm.getHeight()+cell_padding_y*2+1;
	    tableHeadH=cellH*2;
	    
	    //计算title位置
	    
	    //表底和表头文字属性
	    g.setFont(time_font);
	    FontMetrics btmFm=g.getFontMetrics();
	    FontMetrics timeFm=g.getFontMetrics();
	    
	    //表格以上Margin待测试
	    
	    double tableTopMargin=paper_offset_y+title_time_margin+timeFm.getHeight()+time_body_margin;
	    
	    //表格每列最大宽度
	    
	    double[] cellColMaxWidths=caculateTableCellWidth(table.getModel(), cellFm);
	    
	    
	    //当前page数据容量高度
	    
	    double currentPageContainerHeight=pageHeight-tableTopMargin-tableHeadH-btmFm.getHeight()-body_btm_margin-1;
	    
	    //当前Page数据容量
	    
	    int currentPageBodyRows=(int)(currentPageContainerHeight/cellH);
	    
	    
	    //y方向分页数量
	    
	    int pagesY=0;
	    if(table.getModel().getRowCount()%currentPageBodyRows==0) {
	    	pagesY=(int)(table.getModel().getRowCount()/currentPageBodyRows);
	    }else {
	    	pagesY=(int)(table.getModel().getRowCount()/currentPageBodyRows)+1;
		}
	    //当前页数大于总页数不打印
	    
	    if(pageIndex+1>pagesY) {	    	
	    	return NO_SUCH_PAGE;
	    }
		//绘制title
	    
	    
	   //绘制区域移动到新的（0,0）点
	    
	    
	   //绘制第一张表
	   //绘制表头
	    //绘制单一表头
	    
	    //绘制数据
	    int currentX = 0, currentY = 0;   
	    currentY=(int)tableHeadH;
	    //当前Page数据容量
	    int rightCellX=0;
	    int yIndex=pageIndex;
	    
	    int startRow=currentPageBodyRows*yIndex;
	    int endRow=(currentPageBodyRows*(yIndex+1))>table.getModel().getRowCount()?
	    		table.getModel().getRowCount():(currentPageBodyRows*(yIndex+1));
	    for(int row=startRow;row<endRow;row++) {
	    	//绘制单项表头下面数据
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
		case 0://居左
			g.drawString(value.toString(),(int)(x+cell_padding_x), y+(height-fm.getHeight())/2+fm.getAscent());
		case 1://居右
			   g.drawString(value.toString(),   
                       (int) (x +   
                       (width - fm.stringWidth(value.toString()) + width -   
                       fm.stringWidth(value.toString()) - cell_padding_x) /   
                       2), y +   
                       (height - fm.getHeight()) / 2 + fm.getAscent());    
		case 2://居中
			  g.drawString(value.toString(), x + (width - fm.stringWidth(   
                      value.toString())) / 2, y + (height -   
                      fm.getHeight()) / 2 + fm.getAscent());
	    		  
		 case 3:   
             //自动判断   
             NumberFormat formatter = NumberFormat.getNumberInstance();   
             formatter.setMinimumFractionDigits(2);   
             formatter.setMaximumFractionDigits(2); 	  
             //根据数据类型左对齐还是右对齐绘制还是居中对齐   
             if (value instanceof BigDecimal) {   
                 //居右   
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
                 //居右   
                 g.drawString(value.toString(),   
                         (int) (x +   
                         (width - fm.stringWidth(value.toString()) + width -   
                         fm.stringWidth(value.toString()) - cell_padding_x) /   
                         2), y +   
                         (height - fm.getHeight()) / 2 + fm.getAscent());   
             } else {   
                 //居中   
                 g.drawString(value.toString(), x + (width - fm.stringWidth(   
                         value.toString())) / 2, y + (height -   
                         fm.getHeight()) / 2 + fm.getAscent());   
             }    
		}
		
		
	}
	private static double[]  caculateTableCellWidth(TableModel model,FontMetrics cellFm) {
		//计算表格每列最大宽度
		double[] cellColMaxWidths=new double[model.getColumnCount()];
		
		//计算表头每列最大宽度
		double[] headerColMaxWidths=new double[model.getColumnCount()];
		
		for(int i=0;i<model.getColumnCount();i++) {
			String name=model.getColumnName(i);
			headerColMaxWidths[i]=cellFm.stringWidth(name)+cell_padding_x*2+1;
		}
		
		//没有数据时候表头每列最大宽度就是表格每列最大宽度
		
		cellColMaxWidths=headerColMaxWidths;
		
		//计算数据每列的最大宽度和表头每列最大宽度对比
		
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
