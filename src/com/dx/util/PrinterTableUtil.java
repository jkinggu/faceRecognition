package com.dx.util;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.math.BigDecimal;
import java.text.NumberFormat;

import javax.swing.table.TableModel;

/**
 * @author Administrator
 *
 * @Date 2019年5月28日
 *
 * 项目名 FaceRecongnition
 *
 *@version 1.0
 */
public class PrinterTableUtil implements Printable{


	private TableModel model = null;   
   
    private int totalRow = 0;   
    private static final int LEFT = 0;   
    private static final int RIGHT = 1;   
    private static final int CENTER = 2;   
    private static final int AUTO = 3;   
    
    private static final double paper_offset_x = 20;   
    private static final double paper_offset_y = 20;   
    private static final double cell_padding_y = 3;   
    private static final double cell_padding_x = 2;   
    private static final double body_cell_height = 20;    
    private static final Font body_font = new Font("Dialog", Font.PLAIN, 10);   
    
    
    public void printTable(TableModel model) {   
        this.model = model;        
        this.totalRow = model.getRowCount();   
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
 
    
        
    public int print(Graphics g, PageFormat pf, int pageIndex) throws  
            PrinterException {   
        //纸张宽   
        double pageWidth = pf.getImageableWidth();   
        //纸张高   
        double pageHeight = pf.getImageableHeight();   
        //打印起始X   
        double pageStartX = pf.getImageableX();   
        //打印起始Y   
        double pageStartY = pf.getImageableY();   
        //表头高   
        double tableHeadH = 0;   
        //Cell高   
        double cellH = 0;   
    
        //计算表头高度和单元格高度   
        g.setFont(body_font);   
        FontMetrics cellFm = g.getFontMetrics();
        cellH = cellFm.getHeight() + cell_padding_y * 2 + 1;     
        tableHeadH = cellH * 1.5;     
        //表格以上的Margin   
       // double tableTopMargin = paper_offset_y + titleFm.getHeight() +    title_time_margin + timeFm.getHeight() + time_body_margin;   
        double tableTopMargin = paper_offset_y;   
        
        //表格每列的最大宽度   
        double[] cellColMaxWidths = caculateTableCellWidth(model, cellFm);   
        double colWidth=0;
        for(int j=0;j<cellColMaxWidths.length;j++) {
        	colWidth+=cellColMaxWidths[j];     	
        }
        
    
        //当前Page的数据容量高度  
        //double currentPageDataCapacityHeight = pageHeight - tableTopMargin -  tableHeadH - btmFm.getHeight() - body_btm_margin - 1;   
        double currentPageDataCapacityHeight = pageHeight - tableTopMargin - 1;  
        
        //当前Page的数据容量   
        int currentPageBodyCapacityRows = (int) (currentPageDataCapacityHeight / cellH);   
    
        //Y方向计算总分页   
        int pagesY = 0;   
        if (model.getRowCount() % currentPageBodyCapacityRows == 0) {   
            pagesY = (int) (model.getRowCount()/currentPageBodyCapacityRows);   
        } else {   
            pagesY = (int) (model.getRowCount()/currentPageBodyCapacityRows) +1;   
        }   
    
        //当前页数大于总页数时不打印  pageIndex从0开始的
        if (pageIndex + 1 > pagesY) {   
            return NO_SUCH_PAGE;   
        }   
          
         
        //绘制区域移动到新的(0,0)点   
       // g.translate((int) (paper_offset_x + pageStartX), (int) (tableTopMargin + pageStartY));
        g.translate((int) ((pageWidth-colWidth-2*pageStartX)/2+pageStartX), (int) (tableTopMargin + pageStartY));      
        int currentX = (int)pageStartX;     
        int currentY = 0;   
        currentY += 5;   
        //绘制单一表头   
        for (int i = 0; i < model.getColumnCount(); i++) {   
            double width = cellColMaxWidths[i];   
            double height = tableHeadH;   
            String name = model.getColumnName(i);   
            if(name.equals("序号")||name.equals("地点")) {
            	 continue;
            }           
            drawCell(g, name, currentX, currentY, (int) width,(int) height, CENTER);   
            currentX += width;   
        }   
        
        //绘制数据   
        currentX =(int)pageStartX;  
        currentY = (int) tableHeadH;   
        currentY += 5;   
        //当前Page的数据容量   
        int rightCellX = 0;   
        int yIndex = pageIndex;   
        int startRow = currentPageBodyCapacityRows * yIndex;   
        int endRow = (currentPageBodyCapacityRows * (yIndex + 1)) >totalRow ? totalRow : (currentPageBodyCapacityRows * (yIndex + 1));   
        for (int row = startRow; row < endRow; row++) {   
            //绘制单项表头下面的数据   
            for (int i = 0; i < model.getColumnCount(); i++) {   
                double width = cellColMaxWidths[i];   
                double height = body_cell_height;   
                String name = model.getColumnName(i);   
                if(name.equals("序号")||name.equals("地点")) {
               	 continue;
               }
                Object value = model.getValueAt(row, i);   
                drawCell(g, value, currentX, currentY, (int) width,(int) height, AUTO);   
                currentX += width;   
                rightCellX = currentX;   
          }   
            currentX = (int)pageStartX;   
            currentY += cellH;   
        }   
    
        //绘制闭合线，下面和右侧两条   
        g.drawLine(currentX, currentY, rightCellX+1, currentY);   
        g.drawLine(rightCellX+1, 5, rightCellX+1, currentY);   
    
        return PAGE_EXISTS;   
    }   
    
    
    
    /**  
     * 计算最大列宽      
     */  
    private double[] caculateTableCellWidth(TableModel model,FontMetrics cellFm) {   
    	
        //表格每列的最大宽度   
        double[] cellColMaxWidths = new double[model.getColumnCount()];   
        
        //计算表头每列最大宽度   
        double[] headerColMaxWidths = new double[model.getColumnCount()]; 
        
        for (int i = 0; i < model.getColumnCount(); i++) {   
            String name = model.getColumnName(i);   
            headerColMaxWidths[i] = cellFm.stringWidth(name) + cell_padding_x*2 + 1;   
        }   
        
        //没有数据时，表头每列的最大宽度就是表格每列的最大宽度   
        cellColMaxWidths = headerColMaxWidths;   
    
        //算数据每列的数据最大宽度和表头每列最大宽度对比取出最大宽度
        for (int j = 0; j < model.getRowCount(); j++) {   
            for (int i = 0; i < model.getColumnCount(); i++) {    
            	
                //当列为序号和地点时候不做判断
            	String name=model.getColumnName(i);
            	if(name.equals("序号")||name.equals("地点")) {
            		continue;           		
            	}
            	
                //数据类型的判断,数据长度判断取较长的。   
                Object value = model.getValueAt(j, i);   
                if (value instanceof BigDecimal) {   
                    value = ((BigDecimal) value).doubleValue();   
                }   
                String text = "";   
                if (value != null) {   
                    text = value.toString();   
                }   
                double temp = cellFm.stringWidth(text) + cell_padding_x * 2 + 1;   
                if (cellColMaxWidths[i] < temp) {   
                    cellColMaxWidths[i] = temp;   
                }   
            }   
        }   
        return cellColMaxWidths;   
    }   
    
    /**  
     * 绘制单元格及里面的文字  
     */  
    private static void drawCell(Graphics g, Object value, int x, int y,int width,int height, int locate) {   
    	
        g.drawLine(x, y, x + width+1, y);   
        g.drawLine(x, y, x, y + height+1);   
        FontMetrics fm = g.getFontMetrics();   
        if (value == null) {   
            value = "";   
        }   
        switch (locate) {   
            case 0:   
                //居左   
                g.drawString(value.toString(), (int) (x + cell_padding_x), y +  (height - fm.getHeight()) / 2 + fm.getAscent());   
            case 1:   
                //居右   
                g.drawString(value.toString(),   
                   (int) (x +(width - fm.stringWidth(value.toString()) + width - fm.stringWidth(value.toString()) - cell_padding_x)/2),
                   y +(height - fm.getHeight()) / 2 + fm.getAscent());  
            case 2:   
                //居中   
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
	
	

}
