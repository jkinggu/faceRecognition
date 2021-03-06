package com.dx;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.dx.inter.ParamSetupInterface;
import com.dx.inter.ZkzInterface;
import com.dx.pojo.StatisticsResult;
import com.dx.query.PageResult;
import com.dx.service.ParamSetupImpl;
import com.dx.service.ZkzInterImpl;
import com.dx.util.BaseUtil;
import com.dx.util.Const;
import com.dx.util.DateChooser;
import com.dx.util.MutilHeadTable;

/**
 * @author fang
 *
 * @Date 2019年4月29日
 *
 * 项目名 FaceRecongnition
 *
 *@version 1.0
 */
public class StatisticsResultByPage extends JPanel{
	
	private JTable table;
	private JLabel label1;
	private JComboBox condition1;
	private JLabel label2;
	private JComboBox condition2;
	private JLabel label3;
	private JComboBox condition3;
	private JLabel label4;
	private JLabel label5;
	private JPanel pagePanel;//放置查询条件
	private JPanel barPanel;//放置分页条件
	//private TbUserlist user;
	private JButton button;
	private JTextField startDate;
	private DefaultTableModel dftm;
	private BaseUtil bu = new BaseUtil();
	private ZkzInterface zkzInterface = new ZkzInterImpl();
	private ParamSetupInterface paramface = new ParamSetupImpl();
	
	//分页
	// 分页|^
		private JButton firstbutton=new JButton();
		private JButton prebutton=new JButton();
		private JButton nextbutton=new JButton();
		private JButton lastbutton=new JButton();
		private JLabel turnlabel=new JLabel();

		private PageResult pageResult;
		private Integer currentPage = Const.FIRSTPAGE;

	public StatisticsResultByPage() {
		super();
		try {
			Border titleBorder1 = BorderFactory.createTitledBorder("认证结果统计");
	        setBorder(titleBorder1);
			GridBagLayout gridBagLayout = new GridBagLayout();
			//gridBagLayout.columnWidths = new int[]{136, 97, 59, 0, 0, 0, 0, 0, 51, 53, 60, 56};
			gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0,1.0, 1.0, 1.0, 1.0, 1.0};
			setLayout(gridBagLayout);
			//setBounds(100, 100, 699, 331);
			
			
			//把分页条件放入此panel
            barPanel=new JPanel();
            barPanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
            GridBagConstraints barPanelContrains=new GridBagConstraints();
            barPanelContrains.gridy=2;
            barPanelContrains.gridx=0;
            barPanelContrains.gridwidth=12;   	
            barPanelContrains.anchor = GridBagConstraints.NORTH;
            barPanelContrains.insets = new Insets(0, 10,0, 10);
            barPanelContrains.fill = GridBagConstraints.BOTH;
            add(barPanel,barPanelContrains);
			
            //查询信息放入找个界面
            pagePanel=new JPanel();
            pagePanel.setLayout(new FlowLayout(FlowLayout.LEADING,10,10));
            GridBagConstraints pageContrains=new GridBagConstraints();
            pageContrains.gridy=0;
            pageContrains.gridx=0;
            pageContrains.gridwidth=12;   	
            pageContrains.anchor = GridBagConstraints.NORTH;
            pageContrains.insets = new Insets(0, 10, 0, 10);
            pageContrains.fill = GridBagConstraints.BOTH;
            add(pagePanel,pageContrains);
			
			
			
			
//			地市
			label1 = new JLabel();
			label1.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			label1.setText(" 请选择    地市:");
//			final GridBagConstraints gridBagConstraints = new GridBagConstraints();
//			gridBagConstraints.gridy = 0;
//			gridBagConstraints.gridx = 0;
			pagePanel.add(label1);
			
//			从数据库获取地市列表
			condition1 = new JComboBox();
			String[] dishistr = paramface.getDiShi();//从数据库获取地市列表
			condition1.setModel(new DefaultComboBoxModel(dishistr));
			condition1.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			condition1.setPreferredSize(new Dimension(90, 30));
//			final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
//			gridBagConstraints_1.gridy = 0;
//			gridBagConstraints_1.gridx = 1;
			pagePanel.add(condition1);
			
//			考点
			label2 = new JLabel();
			label2.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			label2.setText(" 考点:");
//			final GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
//			gridBagConstraints2.gridy = 0;
//			gridBagConstraints2.gridx = 2;
			pagePanel.add(label2);
	
			
			//从数据库获取考点下拉列表
			condition2 = new JComboBox();
			String[] kaodian = new String[] {};
			condition2.setModel(new DefaultComboBoxModel(kaodian));
			condition2.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			condition2.setPreferredSize(new Dimension(150, 30));
//			final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
//			gridBagConstraints_2.gridy = 0;
//			gridBagConstraints_2.gridx = 3;
			pagePanel.add(condition2);
			
//			考场
			label3 = new JLabel();
			label3.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			label3.setText(" 考场:");
//			final GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
//			gridBagConstraints3.gridy = 0;
//			gridBagConstraints3.gridx = 4;
			pagePanel.add(label3);
			
			condition3 = new JComboBox();
			String[] kaochang = new String[] {}; //getKaoChang();//从数据库获取考场下拉列表
			condition3.setModel(new DefaultComboBoxModel(kaochang));
			condition3.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			condition3.setPreferredSize(new Dimension(150, 30));
//			final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
//			gridBagConstraints_3.gridy = 0;
//			gridBagConstraints_3.gridx = 5;
			pagePanel.add(condition3);
			
			//认证开始时间
			label4 = new JLabel();
			label4.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			label4.setText(" 时间:");
//			final GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
//			gridBagConstraints4.gridy = 0;
//			gridBagConstraints4.gridx = 6;
			pagePanel.add(label4);
	
			//时间框为填写区域
			startDate = new JTextField();
			//final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();
			startDate.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			startDate.setPreferredSize(new Dimension(180, 30));
//			gridBagConstraints_4.gridy = 0;
//			gridBagConstraints_4.gridx = 7;
			DateChooser dateChooser1 = DateChooser.getInstance("yyyy-MM-dd");
			dateChooser1.register(startDate);
			pagePanel.add(startDate);
			
			//查询按钮
			button = new JButton();
//			final GridBagConstraints gridBagConstraints_5 = new GridBagConstraints();
//			gridBagConstraints_5.gridy = 0;
//			gridBagConstraints_5.gridx = 8;
			button.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			button.setText("查询");
			pagePanel.add(button);
			
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
			
			
			button.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
//	            	地市
//	            	String shi = bu.getStr(condition1.getSelectedItem()+"") ;
//	            	考点
	            	String kaodian = bu.getStr(condition2.getSelectedItem()+"") ;
//	            	考场
	            	String changci = bu.getStr(condition3.getSelectedItem() +"") ;
//	            	日期
	            	String startime = bu.getStr(startDate.getText());//选择日期
	            	
//	            	判定地市不能为空
	               //List<String> list = zkzInterface.getCountByCondition( kaodian, changci, startime);
	                 pageResult=zkzInterface.getCountByPageCondition(kaodian, changci, startime, Const.FIRSTPAGE);
	            	//重新更新jtable列表
            		Integer c1 =dftm.getRowCount() ; 
					for(int i=0; i<c1 ;i++) {
						dftm.removeRow(0);
					}
					List<StatisticsResult> list=pageResult.getResult();
					for(StatisticsResult sResult:list) {
						String[] row=new String[] {sResult.getStatisticsCon().getDate(),sResult.getStatisticsCon().getLocation(),sResult.getStatisticsCon().getPlace(),
								         sResult.getMoralPass()+"",sResult.getMoralFail()+"",sResult.getMoralUnauthorized()+"",sResult.getAbilityPass()+"",sResult.getAbilityFail()+"",
								         sResult.getAbilityUnauthorized()+"",sResult.getSkillsPass()+"",sResult.getSkillsFail()+"",sResult.getSkillsUnauthorized()+""};
						dftm.addRow(row);
					}		
					turnlabel.setText("当前第" + pageResult.getCurrentPage() + "页，共" + pageResult.getTotalPage() + "页"
							+ "，共" + pageResult.getTotalCount() + "条");										
	            }
	        });
			
			
			final JScrollPane scrollPane = new JScrollPane();
			final GridBagConstraints gridBagConstraints_8 = new GridBagConstraints();
			gridBagConstraints_8.weighty = 1.0;
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
			            
			String[] tableHeads = new String[]{"日期","考点","考场"," /通过","职业道德/不通过", " /未认证"," /通过", "职业能力/不通过"," /未认证", " /通过","岗位技能/不通过"," /未认证"};//,"id可以隐藏吗"
//			String[] tableHeads = new String[]{"","","","","职业道德", "","", "职业能力","", "","职业道德",""};//,"id可以隐藏吗"
//			String[] tableHeads2 = new String[]{"日期","考点","考场","通过","不通过", "未认证","通过", "不通过","未认证", "通过","不通过","未认证"};
			dftm.setColumnIdentifiers(tableHeads);			
//			dftm.setColumnIdentifiers(tableHeads2);
			DefaultTableCellRenderer render = new DefaultTableCellRenderer() ;
			render.setHorizontalAlignment(SwingConstants.CENTER);//设置单元格渲染方式
			render.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
//			table.setDefaultRenderer(Object.class, render);
			//如何向table中放内容//查询初始数据
			
			pageResult=zkzInterface.getCountByPageCondition("","","",Const.FIRSTPAGE);
			List<StatisticsResult> list=pageResult.getResult();
			for(StatisticsResult sResult:list) {
				String[] row=new String[] {sResult.getStatisticsCon().getDate(),sResult.getStatisticsCon().getLocation(),sResult.getStatisticsCon().getPlace(),
						         sResult.getMoralPass()+"",sResult.getMoralFail()+"",sResult.getMoralUnauthorized()+"",sResult.getAbilityPass()+"",sResult.getAbilityFail()+"",
						         sResult.getAbilityUnauthorized()+"",sResult.getSkillsPass()+"",sResult.getSkillsFail()+"",sResult.getSkillsUnauthorized()+""};
				dftm.addRow(row);
			}		
			
			table = new MutilHeadTable(dftm);// new MutilHeadTable(dftm);//这里是提供单元格合并的  //new JTable(dftm);
			//table.setEnabled(false);
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
//			table.getTableHeader().setPreferredSize(new Dimension(table.getWidth(),150));
			
			table.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			table.setRowHeight(30);
			table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getWidth(), 40));
			table.getTableHeader().setFont(new Font("", Font.PLAIN, 14));
//			table.getTableHeader().setBounds(r);
			
			
			table.getColumnModel().getColumn(0).setPreferredWidth(180);
			table.getColumnModel().getColumn(1).setPreferredWidth(270);
			table.getColumnModel().getColumn(2).setPreferredWidth(120);
			table.getColumnModel().getColumn(3).setPreferredWidth(80);
			table.getColumnModel().getColumn(4).setPreferredWidth(80);
			table.getColumnModel().getColumn(5).setPreferredWidth(80);
			table.getColumnModel().getColumn(6).setPreferredWidth(80);
			table.getColumnModel().getColumn(7).setPreferredWidth(80);
			table.getColumnModel().getColumn(8).setPreferredWidth(80);
			table.getColumnModel().getColumn(9).setPreferredWidth(80);
			table.getColumnModel().getColumn(10).setPreferredWidth(80);
			table.getColumnModel().getColumn(11).setPreferredWidth(80);
//			TableColumn column0 = table.getColumn("通过");
//			column0.setPreferredWidth(80);
//			column0.setCellRenderer(render);
//			TableColumn column2 = table.getColumn("未认证");
//			column2.setPreferredWidth(80);
//			column2.setCellRenderer(render);
//			TableColumn column1 = table.getColumn("职业道德/不通过");
//			column1.setPreferredWidth(80);
//			column1.setCellRenderer(render);
//			TableColumn column = table.getColumn("职业能力/不通过");
//			column.setPreferredWidth(80);
//			column.setCellRenderer(render);
//			TableColumn column3 = table.getColumn("岗位技能/不通过");
//			column3.setPreferredWidth(80);
//			column3.setCellRenderer(render);
//			TableColumn column4 = table.getColumn("日期");
//			column4.setPreferredWidth(180);
//			column4.setCellRenderer(render);
//			TableColumn column5 = table.getColumn("考点");
//			column5.setPreferredWidth(250);
//			column5.setCellRenderer(render);
//			TableColumn column6 = table.getColumn("考场");
//			column6.setPreferredWidth(100);
//			column6.setCellRenderer(render);
			
			scrollPane.setViewportView(table);
			// 首页
//			final GridBagConstraints gridBagConstraints_first = new GridBagConstraints();
//			gridBagConstraints_first.gridy =2;
//			gridBagConstraints_first.gridx =2;
			firstbutton.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			firstbutton.setText("首页");
			barPanel.add(firstbutton);
			firstbutton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
//	            	地市
//	            	String shi = bu.getStr(condition1.getSelectedItem()+"") ;
//	            	考点
	            	String kaodian = bu.getStr(condition2.getSelectedItem()+"") ;
//	            	考场
	            	String changci = bu.getStr(condition3.getSelectedItem() +"") ;
//	            	日期
	            	String startime = bu.getStr(startDate.getText());//选择日期
	            	
//	            	判定地市不能为空
	               //List<String> list = zkzInterface.getCountByCondition( kaodian, changci, startime);
	                 pageResult=zkzInterface.getCountByPageCondition(kaodian, changci, startime, Const.FIRSTPAGE);
	            	//重新更新jtable列表
            		Integer c1 =dftm.getRowCount() ; 
					for(int i=0; i<c1 ;i++) {
						dftm.removeRow(0);
					}
					List<StatisticsResult> list=pageResult.getResult();
					for(StatisticsResult sResult:list) {
						String[] row=new String[] {sResult.getStatisticsCon().getDate(),sResult.getStatisticsCon().getLocation(),sResult.getStatisticsCon().getPlace(),
								         sResult.getMoralPass()+"",sResult.getMoralFail()+"",sResult.getMoralUnauthorized()+"",sResult.getAbilityPass()+"",sResult.getAbilityFail()+"",
								         sResult.getAbilityUnauthorized()+"",sResult.getSkillsPass()+"",sResult.getSkillsFail()+"",sResult.getSkillsUnauthorized()+""};
						dftm.addRow(row);
					}		
					turnlabel.setText("当前第" + pageResult.getCurrentPage() + "页，共" + pageResult.getTotalPage() + "页"
							+ "，共" + pageResult.getTotalCount() + "条");										
	            }
	        });
			
			//上一页
//			final GridBagConstraints gridBagConstraints_previous = new GridBagConstraints();
//			gridBagConstraints_previous.gridy =2;
//			gridBagConstraints_previous.gridx =3;
		    prebutton.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
		    prebutton.setText("上一页");
			barPanel.add(prebutton);
			
			prebutton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
//	            	地市
//	            	String shi = bu.getStr(condition1.getSelectedItem()+"") ;
//	            	考点
	            	String kaodian = bu.getStr(condition2.getSelectedItem()+"") ;
//	            	考场
	            	String changci = bu.getStr(condition3.getSelectedItem() +"") ;
//	            	日期
	            	String startime = bu.getStr(startDate.getText());//选择日期
	            	
//	            	判定地市不能为空
	               //List<String> list = zkzInterface.getCountByCondition( kaodian, changci, startime);
	                pageResult=zkzInterface.getCountByPageCondition(kaodian, changci, startime, pageResult.getPrevious());
	              //重新更新jtable列表
            		Integer c1 =dftm.getRowCount(); 
					for(int i=0; i<c1 ;i++) {
						dftm.removeRow(0);
					}
					List<StatisticsResult> list=pageResult.getResult();
					for(StatisticsResult sResult:list) {
						String[] row=new String[] {sResult.getStatisticsCon().getDate(),sResult.getStatisticsCon().getLocation(),sResult.getStatisticsCon().getPlace(),
								         sResult.getMoralPass()+"",sResult.getMoralFail()+"",sResult.getMoralUnauthorized()+"",sResult.getAbilityPass()+"",sResult.getAbilityFail()+"",
								         sResult.getAbilityUnauthorized()+"",sResult.getSkillsPass()+"",sResult.getSkillsFail()+"",sResult.getSkillsUnauthorized()+""};
						dftm.addRow(row);
					}		
					turnlabel.setText("当前第" + pageResult.getCurrentPage() + "页，共" + pageResult.getTotalPage() + "页"
							+ "，共" + pageResult.getTotalCount() + "条");										
	            }
	        });
			
			//下一页
//			final GridBagConstraints gridBagConstraints_next = new GridBagConstraints();
//			gridBagConstraints_next.gridy =2;
//			gridBagConstraints_next.gridx =4;
		    nextbutton.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
		    nextbutton.setText("下一页");
			barPanel.add( nextbutton);
			nextbutton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
//	            	地市
//	            	String shi = bu.getStr(condition1.getSelectedItem()+"") ;
//	            	考点
	            	String kaodian = bu.getStr(condition2.getSelectedItem()+"") ;
//	            	考场
	            	String changci = bu.getStr(condition3.getSelectedItem() +"") ;
//	            	日期
	            	String startime = bu.getStr(startDate.getText());//选择日期
	            	
//	            	判定地市不能为空
	               //List<String> list = zkzInterface.getCountByCondition( kaodian, changci, startime);
	                 pageResult=zkzInterface.getCountByPageCondition(kaodian, changci, startime, pageResult.getNext());
	            	//重新更新jtable列表
            		Integer c1 =dftm.getRowCount() ; 
					for(int i=0; i<c1 ;i++) {
						dftm.removeRow(0);
					}
					List<StatisticsResult> list=pageResult.getResult();
					for(StatisticsResult sResult:list) {
						String[] row=new String[] {sResult.getStatisticsCon().getDate(),sResult.getStatisticsCon().getLocation(),sResult.getStatisticsCon().getPlace(),
								         sResult.getMoralPass()+"",sResult.getMoralFail()+"",sResult.getMoralUnauthorized()+"",sResult.getAbilityPass()+"",sResult.getAbilityFail()+"",
								         sResult.getAbilityUnauthorized()+"",sResult.getSkillsPass()+"",sResult.getSkillsFail()+"",sResult.getSkillsUnauthorized()+""};
						dftm.addRow(row);
					}		
					turnlabel.setText("当前第" + pageResult.getCurrentPage() + "页，共" + pageResult.getTotalPage() + "页"
							+ "，共" + pageResult.getTotalCount() + "条");										
	            }
	        });
			
			//末页
//			final GridBagConstraints gridBagConstraints_total = new GridBagConstraints();
//			gridBagConstraints_total.gridy =2;
//			gridBagConstraints_total.gridx =5;
		    lastbutton.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
		    lastbutton.setText("末页");
			barPanel.add(lastbutton);
			lastbutton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
//	            	地市
//	            	String shi = bu.getStr(condition1.getSelectedItem()+"") ;
//	            	考点
	            	String kaodian = bu.getStr(condition2.getSelectedItem()+"") ;
//	            	考场
	            	String changci = bu.getStr(condition3.getSelectedItem() +"") ;
//	            	日期
	            	String startime = bu.getStr(startDate.getText());//选择日期
	            	
//	            	判定地市不能为空
	               //List<String> list = zkzInterface.getCountByCondition( kaodian, changci, startime);
	                 pageResult=zkzInterface.getCountByPageCondition(kaodian, changci, startime, pageResult.getTotalPage());
	            	//重新更新jtable列表
            		Integer c1 =dftm.getRowCount() ; 
					for(int i=0; i<c1 ;i++) {
						dftm.removeRow(0);
					}
					List<StatisticsResult> list=pageResult.getResult();
					for(StatisticsResult sResult:list) {
						String[] row=new String[] {sResult.getStatisticsCon().getDate(),sResult.getStatisticsCon().getLocation(),sResult.getStatisticsCon().getPlace(),
								         sResult.getMoralPass()+"",sResult.getMoralFail()+"",sResult.getMoralUnauthorized()+"",sResult.getAbilityPass()+"",sResult.getAbilityFail()+"",
								         sResult.getAbilityUnauthorized()+"",sResult.getSkillsPass()+"",sResult.getSkillsFail()+"",sResult.getSkillsUnauthorized()+""};
						dftm.addRow(row);
					}		
					turnlabel.setText("当前第" + pageResult.getCurrentPage() + "页，共" + pageResult.getTotalPage() + "页"
							+ "，共" + pageResult.getTotalCount() + "条");										
	            }
	        });
			
			//设置turnlable
			turnlabel = new JLabel();
			turnlabel.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			turnlabel.setText("当前第" + pageResult.getCurrentPage() + "页，共" + pageResult.getTotalPage() + "页"
					+ "，共" + pageResult.getTotalCount() + "条");
//			final GridBagConstraints gridBagConstraints_turn = new GridBagConstraints();
//			gridBagConstraints_turn.gridy = 2;
//			gridBagConstraints_turn.gridx = 6;
		    barPanel.add(turnlabel);			
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	
	
	
	

}
