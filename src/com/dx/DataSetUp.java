package com.dx;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import com.dx.inter.ParamSetupInterface;
import com.dx.pojo.ParamSetup;
import com.dx.service.ParamSetupImpl;
import com.dx.util.BaseUtil;
import com.dx.util.Const;
import com.dx.util.DateChooser;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

/**
 * 认证数据设置,设置地市，考点，考场，时间（默认当天可选）
 * */
public class DataSetUp extends JPanel{

	private JTextField endDate;
	private JTextField startDate;
	private JTable table;
	private JLabel label1;
	private JComboBox condition1;
	private JLabel label2;
	private JComboBox condition2;
	private JLabel label3;
	private JComboBox condition3;
	private JLabel label4;
	private JLabel label5;
	private JPanel barPanel;//把条件查询放入这个界面
	//private TbUserlist user;
	private JButton button;
	private JButton button1;
	private DefaultTableModel dftm;
	private BaseUtil bu = new BaseUtil();
	private Integer allcount = 0 ;

	private JCheckBox[] hobby  ;
	private ParamSetupInterface paramface = new ParamSetupImpl();
	public DataSetUp() {
		super();
		try {
			Border titleBorder1 = BorderFactory.createTitledBorder("认证数据设置");
	        setBorder(titleBorder1);
			GridBagLayout gridBagLayout = new GridBagLayout();
			gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0};
			setLayout(gridBagLayout);
			setBounds(100, 100, 601, 331);
	        
			
			//把条件放入此panel
            barPanel=new JPanel();
            barPanel.setLayout(new FlowLayout(FlowLayout.LEADING,10,10));
            GridBagConstraints barPanelContrains=new GridBagConstraints();
            barPanelContrains.gridy=0;
            barPanelContrains.gridx=0;
            barPanelContrains.gridwidth=12;   	
            barPanelContrains.anchor = GridBagConstraints.NORTH;
            barPanelContrains.insets = new Insets(0, 10,0, 10);
            barPanelContrains.fill = GridBagConstraints.BOTH;
            add(barPanel,barPanelContrains);
			
		
			
			label1 = new JLabel();
			label1.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			label1.setText(" 请选择    地市:");
//			final GridBagConstraints gridBagConstraints = new GridBagConstraints();
//			gridBagConstraints.gridy = 0;
//			gridBagConstraints.gridx = 0;
		    barPanel.add(label1);
	
			condition1 = new JComboBox();
			String[] dishistr = paramface.getDiShi();//从数据库获取地市列表，添加登陆之后查看是哪个地市登陆的，某个地市登陆之后，只选择该地市所在的地市
			condition1.setModel(new DefaultComboBoxModel(dishistr));
			condition1.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			condition1.setPreferredSize(new Dimension(100, 30));
//			final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
//			gridBagConstraints_1.gridy = 0;
//			gridBagConstraints_1.gridx = 1;
			barPanel.add(condition1);
			
			label2 = new JLabel();
			label2.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			label2.setText(" 考点:");
//			final GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
//			gridBagConstraints2.gridy = 0;
//			gridBagConstraints2.gridx = 2;
			barPanel.add(label2);
	
			
			condition2 = new JComboBox();
			//从数据库获取考点下拉列表
			String[] kaodian = new String[] {};
			condition2.setModel(new DefaultComboBoxModel(kaodian));
			condition2.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			condition2.setPreferredSize(new Dimension(150, 30));
//			final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
//			gridBagConstraints_2.gridy = 0;
//			gridBagConstraints_2.gridx = 3;
			barPanel.add(condition2);
			
			
			label3 = new JLabel();
			label3.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			label3.setText(" 考场:");
//			final GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
//			gridBagConstraints2.gridy = 0;
//			gridBagConstraints2.gridx = 4;
			barPanel.add(label3);
	
			condition3 = new JComboBox();
			String[] kaochang = new String[] {}; //getKaoChang();//从数据库获取考场下拉列表
			condition3.setModel(new DefaultComboBoxModel(kaochang));
			condition3.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			condition3.setPreferredSize(new Dimension(200, 30));
//			final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
//			gridBagConstraints_2.gridy = 0;
//			gridBagConstraints_2.gridx = 5;
			barPanel.add(condition3);
			
			label4 = new JLabel();
			label4.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			label4.setText(" 时间:");
//			final GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
//			gridBagConstraints4.gridy = 0;
//			gridBagConstraints4.gridx = 6;
			barPanel.add(label4);
	
			//时间框为填写区域
			startDate = new JTextField();
		//	final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();
			startDate.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			startDate.setPreferredSize(new Dimension(200, 30));
//			gridBagConstraints_4.gridy = 0;
//			gridBagConstraints_4.gridx = 7;
			DateChooser dateChooser1 = DateChooser.getInstance("yyyy-MM-dd");
			dateChooser1.register(startDate);
			barPanel.add(startDate);
			
			label5 = new JLabel();
			label5.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			label5.setText("至");
			final GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
//			gridBagConstraints5.ipadx = 20;
//			gridBagConstraints5.gridy = 0;
//			gridBagConstraints5.gridx = 8;
			barPanel.add(label5);
	
			endDate = new JTextField();
			//final GridBagConstraints gridBagConstraints_5 = new GridBagConstraints();
			endDate.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			endDate.setPreferredSize(new Dimension(200, 30));
			//gridBagConstraints_5.gridy = 0;
			//gridBagConstraints_5.gridx = 9;
			DateChooser dateChooser2 = DateChooser.getInstance("yyyy-MM-dd");
			dateChooser2.register(endDate);
			barPanel.add(endDate);
			
			button = new JButton();
//			final GridBagConstraints gridBagConstraints_7 = new GridBagConstraints();
//			gridBagConstraints_7.gridy = 0;
//			gridBagConstraints_7.gridx = 10;
			button.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			button.setText("添加");
			barPanel.add(button);
			
			button1 = new JButton();
//			final GridBagConstraints gridBagConstraints_6 = new GridBagConstraints();
//			gridBagConstraints_6.gridy = 0;
//			gridBagConstraints_6.gridx = 11;
			button1.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			button1.setText("移除");
			barPanel.add(button1);
			
			
			condition1.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					if(e.getStateChange() == ItemEvent.SELECTED) {
						String val = e.getItem()+"" ;//e.paramString() ;
						condition2.removeAllItems();
						condition2.setModel(new DefaultComboBoxModel(paramface.getKaoDian(val)));
						condition3.removeAllItems();
					}
				}
			});
			condition2.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					if(e.getStateChange() == ItemEvent.SELECTED) {
						String val = e.getItem()+"" ;
						condition3.removeAllItems();
						condition3.setModel(new DefaultComboBoxModel(paramface.getKaoChang(val)));
					}
				}
			});
			/**
			 * 点击添加到数据库以及列表下方
			 * 1只添加地市， 1.1地市，开始时间，1.2地市，结束时间，1.3地市，开始，结束时间                                                           其下所有的考场据添加到数据库以及页面列表
			 * 2地市，考点，2.1地市，考点，开始时间，2.2地市，考点，结束时间，2.3，地市，考点，开始，结束时间
			 * 3地市，考点，考场3.1地市，考点，考场，开始时间，3.2地市，考点，考场结束时间，3.3地市，考点，考场，开始，结束时间
			 * 4地市，考点，考场，开始时间
			 * 5地市，考场，考点，结束时间
			 * 6地市，考点，考场，开始时间，结束时间
			 * 如果1没有选择，2选择重复均需要判断
			 * */
			button.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	String dishi = bu.getStr(condition1.getSelectedItem()+"") ;
	            	String kaodian = bu.getStr(condition2.getSelectedItem()+"") ;
	            	String kaochang = bu.getStr(condition3.getSelectedItem() +"") ;
	            	String startime = bu.getStr(startDate.getText());//获取开始时间
	            	String endtime = bu.getStr(endDate.getText()) ;//获取借宿时间
	            	//System.out.println(dishi+"--"+kaodian+"--"+kaochang+"--"+startime+"--"+endtime);
	            	//判断考场内容
	            	if(dishi.equals("") && kaodian.equals("") && kaochang.equals("") && startime.equals("") && endtime.equals("")) {
	            		JOptionPane.showMessageDialog(null, "选择内容为空！" , "添加考场设置",JOptionPane.WARNING_MESSAGE);
	            		return ;
	            	}else {
	            		List<ParamSetup> list = paramface.getShezhiList(dishi,kaodian,kaochang,startime,endtime,"0",null);//查询可添加的所有剩余内容
	            		String pids = "" ;
	            		JCheckBox checkbox ;
	            		Integer hobbylen =  hobby.length ;
	            		Integer counthhlen = hobbylen+list.size() ;
	            	//	System.out.println("hobbylen----"+hobbylen+"----list.size()----"+list.size());
	            		JCheckBox[] newcbox = new JCheckBox[counthhlen] ;
	            		for(int i=0; i<hobbylen ; i++) {
	            			newcbox[i] = hobby[i] ;
	            		}
	            		for(int i=0 ;i<list.size() ;i++ ) {
	        				ParamSetup param = list.get(i) ;
	        				allcount = allcount + 1 ;
	        				//创建复选框
	        				checkbox = new JCheckBox(param.getPid()+"");
	        				//获取当前checkbox的总长度
	        				newcbox[hobbylen+i] = checkbox ;//获取checkbox序列
	        				dftm.addRow(new String[] {"",allcount+"",param.getDishiname(),param.getKaodianname(),param.getDidian(),param.getKaochang(),param.getStarttime(),param.getEndtime()});//,param.getPid()+""
	        				pids = pids + param.getPid()+"," ;
	        				
	        			}
	            		hobby = newcbox ;
	        			pids = pids.substring(0, pids.length()-1) ;
	        			Integer sise = paramface.updateParamSetup(pids);
	            	}
	            	
	            	
	            }
	        });
			button1.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	int rows=table.getRowCount();
	            	int ckxCol=0;// 假设第一列是checkbox列 
	            	String pids = "" ;
	            	for(int r=0;r<rows;r++){// 循环所有行
	            	     Object v = table.getValueAt(r,ckxCol)+""; 
	            	     if(v!=null && v.equals("true") ){//选中
	            	    	 pids = pids + hobby[r].getText().toString()+",";
	            	     }
	            	}
	            	if(pids.equals("")) {
	            		JOptionPane.showMessageDialog(null, "请选择需要移除的行！" , "移除考场设置",JOptionPane.WARNING_MESSAGE);
	            		return ;
	            	}
	            	pids = pids.substring(0,pids.length()-1) ;
	            	//处理数据库的对应数据
	            	Integer sise = paramface.updateParamRemove(pids);
	            	//System.out.println("pids----"+pids);
	            	FaceMainFrame frame = (FaceMainFrame) getParent().getParent().getParent().getParent() ;
	            	frame.setStr2(pids);
	            	//System.out.println("daozhelima !!!!"+getParent().getParent().getParent().getParent());//Frame窗体
	            }
	        });
			final JScrollPane scrollPane = new JScrollPane();
			final GridBagConstraints gridBagConstraints_8 = new GridBagConstraints();
			gridBagConstraints_8.weighty = 1.0;
			gridBagConstraints_8.anchor = GridBagConstraints.NORTH;
			gridBagConstraints_8.insets = new Insets(0, 10, 0, 10);
			gridBagConstraints_8.fill = GridBagConstraints.BOTH;
			gridBagConstraints_8.gridwidth = 12;
			gridBagConstraints_8.gridy = 1;
			gridBagConstraints_8.gridx = 0;
			add(scrollPane, gridBagConstraints_8);
	
			dftm = new DefaultTableModel(){
	            @Override
	            public boolean isCellEditable(int row,int column){
	            	if(column==0) {
	            		return true ;
	            	}else {
	            		return false;
	            	}
	            }
			};
			            
			String[] tableHeads = new String[]{"选择","序号", "地市", "地点","考点", "考场", "开始时间","结束时间"};//,"id可以隐藏吗"
			dftm.setColumnIdentifiers(tableHeads);
			DefaultTableCellRenderer render = new DefaultTableCellRenderer() ;
			render.setHorizontalAlignment(SwingConstants.CENTER);//设置单元格渲染方式
			render.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			//如何向table中放内容//查询初始数据
			List<ParamSetup> list = paramface.getShezhiList("","","","","","1",null);
			
			hobby = new JCheckBox[list.size()];
			JCheckBox checkbox ;
			for(int i=0 ;i<list.size() ;i++ ) {
				ParamSetup param = list.get(i) ;
				allcount = allcount+1 ;
				checkbox = new JCheckBox(param.getPid()+"");
				hobby[i] = checkbox ;
				//获取checkbox序列
				//dftm.addRow(new String[] {"",allcount+"",param.getDishiname(),param.getKaodianname(),param.getDidian(),param.getKaochang(),param.getStarttime(),param.getEndtime()});
				Object[] row = new Object[] {"",allcount+"",param.getDishiname(),param.getKaodianname(),param.getDidian(),param.getKaochang(),param.getStarttime(),param.getEndtime()} ;//,param.getPid()+""
				//Object[] row = new Object[] {param.getPid()+"",allcount+"",param.getDishiname(),param.getKaodianname(),param.getDidian(),param.getKaochang(),param.getStarttime(),param.getEndtime(),param.getPid()+""} ;
				dftm.addRow(row);
			}
			
			
			
			table = new JTable(dftm);
			table.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			table.setRowHeight(30);
			table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getWidth(), 30));
			table.getTableHeader().setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			//table.setEnabled(false);//table不可以操作
			//下面的作用是为了可以选择多行，如果不需要选择多行，可注释掉，但在此处必须要，其它地方视情况而定
			table.setSelectionModel(new DefaultListSelectionModel() {
				@Override
				public void setSelectionInterval(int index0, int index1) {
					if (super.isSelectedIndex(index0)) {
						super.removeSelectionInterval(index0, index1);
					} else {
						super.addSelectionInterval(index0, index1);
					}
				}
			});
			
			TableColumn column0 = table.getColumn("选择");
			column0.setCellEditor(new DefaultCellEditor(new JCheckBox()));
			column0.setPreferredWidth(50);
//			column0.setCellRenderer(render);
			column0.setCellRenderer(new TableCellRenderer() {
				@Override
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
						boolean hasFocus, int row, int column) {
					// 创建用于返回的渲染组件
					JCheckBox ck = new JCheckBox();
					 // 使具有焦点的行对应的复选框选中         
					if(column==0) {
						ck.setSelected(isSelected);
					}
					//设置背景颜色  这里是设置jcheckbox的背景颜色   直接设置为透明，我这里是用了一种明暗交替的颜色转换，所以背景颜色设置了一下                  
					ck.setOpaque(false);
					// 设置单选box.setSelected(hasFocus);
					// 使复选框在单元格内居中显示
					ck.setHorizontalAlignment((int) 0.5f);
					return ck;
				}
			});
			
			TableColumn column1 = table.getColumn("序号");
			column1.setPreferredWidth(50);
			column1.setCellRenderer(render);
			
			TableColumn column2 = table.getColumn("地市");
			column2.setPreferredWidth(50);
			column2.setCellRenderer(render);
			TableColumn column = table.getColumn("地点");
			column.setPreferredWidth(250);
			column.setCellRenderer(render);
			TableColumn column3 = table.getColumn("考点");
			column3.setPreferredWidth(250);
			column3.setCellRenderer(render);
			TableColumn column4 = table.getColumn("考场");
			column4.setPreferredWidth(100);
			column4.setCellRenderer(render);
			TableColumn column5 = table.getColumn("开始时间");
			column5.setPreferredWidth(200);
			column5.setCellRenderer(render);
			TableColumn column6 = table.getColumn("结束时间");
			column6.setPreferredWidth(200);
			column6.setCellRenderer(render);
//			TableColumn column7 = table.getColumn("id可以隐藏吗");                             
			//table.removeColumn(column7); //隐藏id列
			//column7.setPreferredWidth(0);
			scrollPane.setViewportView(table);
			
			
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		
		
	}
	
}
