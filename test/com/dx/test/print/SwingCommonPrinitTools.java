package com.dx.test.print;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.math.BigDecimal;
import java.text.NumberFormat;

import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 * @author Administrator
 *
 * @Date 2019��5��28��
 *
 * ��Ŀ�� FaceRecongnition
 *
 *@version 1.0
 */
public class SwingCommonPrinitTools implements Printable{
	
	private TableModel model = null;   
    private String info;   
    private int totalRow = 0;   
    private static final int LEFT = 0;   
    private static final int RIGHT = 1;   
    private static final int CENTER = 2;   
    private static final int AUTO = 3;   
    
    public void printTable(TableModel model,   
            String info) {   
        this.model = model;   
        this.info = info;   
        totalRow = model.getRowCount();   
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
    private static final double title_time_margin = 10;   
    private static final double time_body_margin = 2;   
    private static final double cell_padding_y = 3;   
    private static final double cell_padding_x = 2;   
    private static final double body_btm_margin = 20;   
    private static final double body_cell_height = 20;   
    private static final Font title_font = new Font("����", Font.PLAIN, 18);   
    private static final Font time_font = new Font("Dialog", Font.PLAIN, 10);   
    private static final Font body_font = new Font("Dialog", Font.PLAIN, 10);   
    
        
    public int print(Graphics g, PageFormat pf, int pageIndex) throws  
            PrinterException {   
        //ֽ�ſ�   
        double pageWidth = pf.getImageableWidth();   
        //ֽ�Ÿ�   
        double pageHeight = pf.getImageableHeight();   
        //��ӡ��������ʼX   
        double pageStartX = pf.getImageableX();   
        //��ӡ��������ʼY   
        double pageStartY = pf.getImageableY();   
    
        //��ͷ��   
        double tableHeadH = 0;   
        //Cell��   
        double cellH = 0;   
    
        //�����ͷ�߶Ⱥ͵�Ԫ��߶�   
        g.setFont(body_font);   
        FontMetrics cellFm = g.getFontMetrics();   
        cellH = cellFm.getHeight() + cell_padding_y * 2 + 1;   
        tableHeadH = cellH * 2;   
    
        //����Title�Լ���λ��   
        String title = info;   
        g.setFont(title_font);   
        FontMetrics titleFm = g.getFontMetrics();   
        int titleW = titleFm.stringWidth(title);   
    
        //��׺ͱ�ͷ��������   
        g.setFont(time_font);   
        FontMetrics btmFm = g.getFontMetrics();   
        FontMetrics timeFm = g.getFontMetrics();   
    
        //������ϵ�Margin   
        double tableTopMargin = paper_offset_y + titleFm.getHeight() +   
                title_time_margin + timeFm.getHeight() + time_body_margin;   
    
        //���ÿ�е������   
        double[] cellColMaxWidths = caculateTableCellWidth(model, cellFm);   
    
        //��ǰPage�����������߶�-��������ͷ�ͱ�β   
        double currentPageDataCapacityHeight = pageHeight - tableTopMargin -   
                tableHeadH - btmFm.getHeight() - body_btm_margin - 1;   
    
        //��ǰPage����������   
        int currentPageBodyCapacityRows = (int) (currentPageDataCapacityHeight /   
                cellH);   
    
        //Y����ķ�ҳ����   
        int pagesY = 0;   
        if (model.getRowCount() % currentPageBodyCapacityRows == 0) {   
            pagesY = (int) (model.getRowCount() /   
                    currentPageBodyCapacityRows);   
        } else {   
            pagesY = (int) (model.getRowCount() /   
                    currentPageBodyCapacityRows) +   
                    1;   
        }   
    
        //��ǰҳ��������ҳ��ʱ����ӡ   
        if (pageIndex + 1 > pagesY) {   
            return NO_SUCH_PAGE;   
        }   
    
        //����Title   
        g.setFont(title_font);   
        g.drawString(title, (int) (pageStartX +   
                (pageWidth - titleW) / 2), (int) (pageStartY +   
                paper_offset_y +   
                titleFm.getAscent()));   
    
        //���������ƶ����µ�(0,0)��   
        g.translate((int) (paper_offset_x + pageStartX), (int) (tableTopMargin +   
                pageStartY));   
        int currentX = 0, currentY = 0;   
    
        //���Ƶ�һ�ű�   
    
        //���Ʊ�ͷ   
        g.setFont(time_font);   
        String time = "��ͷ: " + info;   
        g.drawString(time, currentX, currentY);   
        currentY += 5;   
        //���Ƶ�һ��ͷ   
        for (int i = 0; i < model.getColumnCount(); i++) {   
            double width = cellColMaxWidths[i];   
            double height = tableHeadH;   
            String name = model.getColumnName(i);   
            drawCell(g, name, currentX, currentY, (int) width,   
                    (int) height, CENTER);   
            currentX += width;   
        }   
    
        //��������   
        currentX = 0;   
        currentY = (int) tableHeadH;   
        //��ǰPage����������   
        int rightCellX = 0;   
        int yIndex = pageIndex;   
        int startRow = currentPageBodyCapacityRows * yIndex;   
        int endRow = (currentPageBodyCapacityRows * (yIndex + 1)) >  //aa   
                totalRow   
                ? totalRow   
                : (currentPageBodyCapacityRows * (yIndex + 1));   
        for (int row = startRow; row < endRow; row++) {   
            //���Ƶ����ͷ���������   
            for (int i = 0; i < model.getColumnCount(); i++) {   
                double width = cellColMaxWidths[i];   
                double height = body_cell_height;   
                Object value = model.getValueAt(row, i);   
                drawCell(g, value, currentX, currentY, (int) width,   
                        (int) height, AUTO);   
                currentX += width;   
                rightCellX = currentX;   
            }   
            currentX = 0;   
            currentY += cellH;   
        }   
    
        //���Ʊպ��ߣ�������Ҳ�����   
        g.drawLine(currentX, currentY, rightCellX, currentY);   
        g.drawLine(rightCellX, 5, rightCellX, currentY);   
    
        drawBottomInfo(pageIndex, pagesY, currentY, g, (int) pageWidth);   
        return PAGE_EXISTS;   
    }   
    
    private void drawBottomInfo(int pageIndex, int pagesY,   
            int currentY, Graphics g, int pageWidth) {   
        if (pageIndex + 1 == pagesY) {   
            //���Ƶײ���Ϣ   
            int btmX = 0;   
            int btmY = currentY + 20;   
            g.drawString("������:", btmX, btmY);   
            g.drawString("�Ʊ�:", pageWidth / 3, btmY);   
            FontMetrics fm = g.getFontMetrics();   
            int dataWidth = fm.stringWidth("����: 2019/05/28");   
            g.drawString("����:", pageWidth - dataWidth, btmY);   
        }   
    }   
    
    /**  
     * ��������п�  
     * @param cellFm  
     * @return  
     */  
    private double[] caculateTableCellWidth(   
            TableModel model,   
            FontMetrics cellFm) {   
        //���ÿ�е������   
        double[] cellColMaxWidths = new double[model.getColumnCount()];   
    
        //�����ͷÿ�������   
        double[] headerColMaxWidths = new double[model.getColumnCount()];   
    
        for (int i = 0; i < model.getColumnCount(); i++) {   
            String name = model.getColumnName(i);   
            headerColMaxWidths[i] = cellFm.stringWidth(name) + cell_padding_x *   
                    2 + 1;   
        }   
        //û������ʱ����ͷÿ�е�����Ⱦ��Ǳ��ÿ�е������   
        cellColMaxWidths = headerColMaxWidths;   
    
        //������ÿ�е�����Ⱥͱ�ͷÿ������ȶԱ�   
        for (int j = 0; j < model.getRowCount(); j++) {   
            for (int i = 0; i < model.getColumnCount(); i++) {   
                //��Щ�������͵��ж�   
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
     * ���Ƶ�Ԫ�����������  
     * @param g  
     * @param value  
     * @param x  
     * @param y  
     * @param width  
     * @param height  
     */  
    private static void drawCell(Graphics g, Object value, int x, int y,   
            int width,   
            int height, int locate) {   
    
        g.drawLine(x, y, x + width - 1, y);   
        g.drawLine(x, y, x, y + height - 1);   
        FontMetrics fm = g.getFontMetrics();   
        if (value == null) {   
            value = "";   
        }   
        switch (locate) {   
            case 0:   
                //����   
                g.drawString(value.toString(), (int) (x + cell_padding_x), y +   
                        (height - fm.getHeight()) / 2 + fm.getAscent());   
            case 1:   
                //����   
                g.drawString(value.toString(),   
                        (int) (x +   
                        (width - fm.stringWidth(value.toString()) + width -   
                        fm.stringWidth(value.toString()) - cell_padding_x) /   
                        2), y +   
                        (height - fm.getHeight()) / 2 + fm.getAscent());   
            case 2:   
                //����   
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
    
    public static void main(String[] args) {   
        new SwingCommonPrinitTools().printTable(testData(), "����");   
    }   
	
    private static TableModel testData() {   
        final Object rows[][] = {   
            {"one", "ichi - \u4E00", "Test1", "Test2", "Test3"},   
            {"two", "ni - \u4E8C", "Test1", "Test2", "Test3"},   
            {"three", "san - \u4E09", "Test1", "Test2", "Test3"},   
            {"four", "shi - \u56DB", "Test1", "Test2", "Test3"},   
            {"five", "go - \u4E94", "Test1", "Test2", "Test3"},   
            {"six", "roku - \u516D", "Test1", "Test2", "Test3"},   
            {"seven", "shichi - \u4E03", "Test1", "Test2", "Test3"},   
            {"eight", "hachi - \u516B", "Test1", "Test2", "Test3"},   
            {"nine", "kyu - \u4E5D", "Test1", "Test2", "Test3"},   
            {"ten", "ju - \u5341", "Test1", "Test2", "Test3"},   
            {"one", "ichi - \u4E00", "Test1", "Test2", "Test3"},   
            {"two", "ni - \u4E8C", "Test1", "Test2", "Test3"},   
            {"three", "san - \u4E09", "Test1", "Test2", "Test3"},   
            {"four", "shi - \u56DB", "Test1", "Test2", "Test3"},   
            {"five", "go - \u4E94", "Test1", "Test2", "Test3"},   
            {"six", "roku - \u516D", "Test1", "Test2", "Test3"},   
            {"seven", "shichi - \u4E03", "Test1", "Test2", "Test3"},   
            {"eight", "hachi - \u516B", "Test1", "Test2", "Test3"},   
            {"nine", "kyu - \u4E5D", "Test1", "Test2", "Test3"},   
            {"ten", "ju - \u5341", "Test1", "Test2", "Test3"},   
            {"one", "ichi - \u4E00", "Test1", "Test2", "Test3"},   
            {"two", "ni - \u4E8C", "Test1", "Test2", "Test3"},   
            {"three", "san - \u4E09", "Test1", "Test2", "Test3"},   
            {"four", "shi - \u56DB", "Test1", "Test2", "Test3"},   
            {"five", "go - \u4E94", "Test1", "Test2", "Test3"},   
            {"six", "roku - \u516D", "Test1", "Test2", "Test3"},   
            {"seven", "shichi - \u4E03", "Test1", "Test2", "Test3"},   
            {"eight", "hachi - \u516B", "Test1", "Test2", "Test3"},   
            {"nine", "kyu - \u4E5D", "Test1", "Test2", "Test3"},   
            {"ten", "ju - \u5341", "Test1", "Test2", "Test3"},   
            {"one", "ichi - \u4E00", "Test1", "Test2", "Test3"},   
            {"two", "ni - \u4E8C", "Test1", "Test2", "Test3"},   
            {"three", "san - \u4E09", "Test1", "Test2", "Test3"},   
            {"four", "shi - \u56DB", "Test1", "Test2", "Test3"},   
            {"five", "go - \u4E94", "Test1", "Test2", "Test3"},   
            {"six", "roku - \u516D", "Test1", "Test2", "Test3"},   
            {"seven", "shichi - \u4E03", "Test1", "Test2", "Test3"},   
            {"eight", "hachi - \u516B", "Test1", "Tes12121t2", "Test3"},   
            {"nine", "kyu - \u4E5D", "Test1", "Test2", "Test3"},   
            {"ten", "ju - \u5341", "Test1", "Test2", "Test3"},   
            {"one", "ichi - \u4E00", "Test1", "Test2", "Test3"},   
            {"two", "ni - \u4E8C", "Test1", "Test2", "Test3"},   
            {"three", "san - \u4E09", "Test1", "Test2", "Test3"},   
            {"four", "shi - \u56DB", 12, "Test2", "Test3"},   
            {"five", "go - \u4E94", 121212, "Test2", "Test3"},   
            {"six", "roku - \u516D", 1212121212, "Test2", "Test3"},   
            {"seven", "shichi - \u4E03", 12.01, "Test2", "Test3"},   
            {"eight", "hachi - \u516B", 135.12, "Test2", "Test3"},   
            {"nine", "kyu - \u4E5D", 93828.34, "Test2", "Test3"},   
            {"ten", "ju - \u5341", "Test1", "Test2", "Test3"},   
            {"one", "ichi - \u4E00", "Test1", "Test2", "Test3"},   
            {"two", "ni - \u4E8C", "Test1", "Test2", "Test3"},   
            {"three", "san - \u4E09", "Test1", "Test2", "Test3"},   
            {"four", "shi - \u56DB", "Test1", "Test2", "Test3"},   
            {"five", "go - \u4E94", "Test1", "Test2", "Test3"},   
            {"six", "roku - \u516D", "Test1", "Test2", "Test3"},   
            {"seven", "shichi - \u4E03", "Test1", "Test2", "Test3"},   
            {"eight", "hachi - \u516B", "Test1", "Test2", "T1212121212est3"},   
            {"nine", "kyu - \u4E5D", "Test1", "Test2", "Test3"},   
            {"ten", "ju - \u5341", "Test1", "Test2", "Test3"},};   
        final Object headers[] = {"English", "Japanese", "Column1", "Column2",   
            "Column3"};   
        JTable table = new JTable(rows, headers);   
        return table.getModel();   
    }   

}
