package com.dx.util;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * @author Administrator
 *
 * @Date 2019年6月6日
 *
 * 项目名 FaceRecongnition
 *
 *@version 1.0
 */
public class JBoxAndJTableRender  extends JCheckBox implements TableCellRenderer{

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		// TODO Auto-generated method stub		
		Boolean b = (Boolean) value;
		this.setSelected(b.booleanValue());
		return this;
	}
	
	
	

}
