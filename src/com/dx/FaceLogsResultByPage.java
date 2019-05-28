package com.dx;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.dx.inter.FaceLogsInterface;
import com.dx.pojo.Zkzdata;
import com.dx.query.PageResult;
import com.dx.service.FaceLogsImpl;
import com.dx.util.BaseUtil;
import com.dx.util.Const;
import com.dx.util.DateChooser;
import com.dx.util.PrinterTableUtil;

/**
 * @author fang
 *
 * @Date 2019年4月28日
 *
 *       项目名 FaceRecongnition
 *
 * @version 1.0
 */
public class FaceLogsResultByPage extends JPanel {
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

	private JLabel label6;// 考dian
	private JComboBox condition6;//
	private JLabel label7;// 考ch
	private JComboBox condition7;//

	private JPanel barPanel;// 把条件查询放入这个界面
	private JPanel pagePanel;// 把分页信息放在找个界面
	// private TbUserlist user;
	private JButton button = new JButton();
	private JButton prtBtn=new JButton();
	private DefaultTableModel dftm;
	private BaseUtil bu = new BaseUtil();
	private Integer allcount = 0;
	private FaceLogsInterface facelog = new FaceLogsImpl();

	// 分页|^
	private JButton firstbutton = new JButton();
	private JButton prebutton = new JButton();
	private JButton nextbutton = new JButton();
	private JButton lastbutton = new JButton();
	private JLabel turnlabel;

	private PageResult pageResult;
	private Integer currentPage = Const.FIRSTPAGE;
	private FaceLogsInterface faceService = new FaceLogsImpl();

	public FaceLogsResultByPage() {
		super();
		try {
			Border titleBorder1 = BorderFactory.createTitledBorder("认证结果查询");
			setBorder(titleBorder1);
			GridBagLayout gridBagLayout = new GridBagLayout();
			gridBagLayout.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 3.0 };
			setLayout(gridBagLayout);

			// 把条件放入此panel
			barPanel = new JPanel();
			barPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 10, 10));
			GridBagConstraints barPanelContrains = new GridBagConstraints();
			barPanelContrains.gridy = 0;
			barPanelContrains.gridx = 0;
			barPanelContrains.gridwidth = 12;
			barPanelContrains.anchor = GridBagConstraints.NORTH;
			barPanelContrains.insets = new Insets(0, 10, 0, 10);
			barPanelContrains.fill = GridBagConstraints.BOTH;
			add(barPanel, barPanelContrains);

			// 分页信息放入找个界面

			pagePanel = new JPanel();
			pagePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
			GridBagConstraints pageContrains = new GridBagConstraints();
			pageContrains.gridy = 2;
			pageContrains.gridx = 0;
			pageContrains.gridwidth = 12;
			pageContrains.anchor = GridBagConstraints.NORTH;
			pageContrains.insets = new Insets(0, 10, 0, 10);
			pageContrains.fill = GridBagConstraints.BOTH;
			add(pagePanel, pageContrains);

			// 场次放入barPanel下同
			label3 = new JLabel();
			label3.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, 13));
			label3.setText("请选择   场次:");
			barPanel.add(label3);

			// 场次选项
			condition3 = new JComboBox();
			String[] kaochang = new String[] { "职业道德", "职业能力", "岗位技能" }; // getKaoChang();//从数据库获取考场下拉列表
			condition3.setModel(new DefaultComboBoxModel(kaochang));
			condition3.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			condition3.setPreferredSize(new Dimension(90, 30));
			barPanel.add(condition3);

			// 考点
			label6 = new JLabel();
			label6.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, 13));
			label6.setText(" 考点:");
			barPanel.add(label6);
			condition6 = new JComboBox();

			List<String> kds = facelog.getKd();
			String[] kds1 = kds.toArray(new String[kds.size()]);
			condition6.setModel(new DefaultComboBoxModel(kds1));
			condition6.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			condition6.setPreferredSize(new Dimension(200, 30));
			barPanel.add(condition6);

			// 考场

			label7 = new JLabel();
			label7.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, 13));
			label7.setText(" 考场:");
			barPanel.add(label7);

			condition7= new JComboBox();
			List<String> kcs = facelog.getKc();
			String[] kcs1 = kcs.toArray(new String[kcs.size()]);
			condition7.setModel(new DefaultComboBoxModel(kcs1));
			condition7.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			condition7.setPreferredSize(new Dimension(90, 30));
			barPanel.add(condition7);

			// 认证结果
			label1 = new JLabel();
			label1.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			label1.setText(" 认证结果:");
			barPanel.add(label1);

			// 认证结果选项
			condition1 = new JComboBox();
			String[] dishistr = new String[] { "请选择", "通过", "不通过", "未认证" };// 从数据库获取地市列表
			condition1.setModel(new DefaultComboBoxModel(dishistr));
			condition1.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			condition1.setPreferredSize(new Dimension(80, 30));
			// final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
			// gridBagConstraints_1.gridy = 0;
			// gridBagConstraints_1.gridx = 3;
			barPanel.add(condition1);

			// 次数
			label2 = new JLabel();
			label2.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			label2.setText(" 次数:");
			// final GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			// gridBagConstraints2.gridy = 0;
			// gridBagConstraints2.gridx = 4;
			barPanel.add(label2);

			// 次数选项
			condition2 = new JComboBox();// 从数据库获取考点下拉列表
			String[] kaodian = facelog.getRenzCount();
			condition2.setModel(new DefaultComboBoxModel(kaodian));
			condition2.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			condition2.setPreferredSize(new Dimension(80, 30));
			// final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
			// gridBagConstraints_2.gridy = 0;
			// gridBagConstraints_2.gridx = 5;
			barPanel.add(condition2);

			// 认证开始时间
			label4 = new JLabel();
			label4.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			label4.setText(" 认证时间:");
			// final GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			// gridBagConstraints4.gridy = 0;
			// gridBagConstraints4.gridx = 6;
			barPanel.add(label4);

			// 时间框为填写区域
			startDate = new JTextField();

			startDate.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			startDate.setPreferredSize(new Dimension(180, 30));
			// final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();
			// gridBagConstraints_4.gridy = 0;
			// gridBagConstraints_4.gridx = 7;
			DateChooser dateChooser1 = DateChooser.getInstance("yyyy-MM-dd HH:mm:ss");
			dateChooser1.register(startDate);
			barPanel.add(startDate);

			// 认证结束时间
			label5 = new JLabel();
			label5.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			label5.setText("至");
			// final GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			// gridBagConstraints5.ipadx = 20;
			// gridBagConstraints5.gridy = 0;
			// gridBagConstraints5.gridx = 8;
			barPanel.add(label5);

			// 认证结束时间选项
			endDate = new JTextField();
			endDate.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			endDate.setPreferredSize(new Dimension(180, 30));
			// final GridBagConstraints gridBagConstraints_5 = new GridBagConstraints();
			// gridBagConstraints_5.gridy = 0;
			// gridBagConstraints_5.gridx = 9;
			DateChooser dateChooser2 = DateChooser.getInstance("yyyy-MM-dd HH:mm:ss");
			dateChooser2.register(endDate);
			barPanel.add(endDate);

			// 查询按钮
			button = new JButton();
			// final GridBagConstraints gridBagConstraints_7 = new GridBagConstraints();
			// gridBagConstraints_7.gridy = 0;
			// gridBagConstraints_7.gridx = 10;
			button.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			button.setText("查询");
			barPanel.add(button);

			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// 认证结果
					String rzresult = bu.getStr(condition1.getSelectedItem() + "");
					
					//考点信息
					String rzKaodian=bu.getStr(condition6.getSelectedItem()+"");
					//考场信息
					String rzKaochang=bu.getStr(condition7.getSelectedItem()+"");
					
					// 认证次数					
					String rzcount = bu.getStr(condition2.getSelectedItem() + "");
					// 场次
					String changci = bu.getStr(condition3.getSelectedItem() + "");
					// 开始时间
					String startime = bu.getStr(startDate.getText());// 获取开始时间
					// 结束时间
					String endtime = bu.getStr(endDate.getText());// 获取结束时间
					// System.out.println(dishi+"--"+kaodian+"--"+kaochang+"--"+startime+"--"+endtime);
					// 判断考场内容
					if (changci.equals("")) {
						JOptionPane.showMessageDialog(null, "请选择场次", "添加考场设置", JOptionPane.WARNING_MESSAGE);
						return;
					} else {
						pageResult = facelog.getFaceLogsByPageCondition(rzresult, changci,rzKaodian,rzKaochang, rzcount, startime, endtime,
								currentPage);
						// 重新更新jtable列表
						Integer c1 = dftm.getRowCount();
						for (int i = 0; i < c1; i++) {
							dftm.removeRow(0);
						}
						allcount = 0;
						List<Zkzdata> list = pageResult.getResult();
						for (int i = 0; i < list.size(); i++) {
							Zkzdata zkzdata = list.get(i);
							allcount = allcount + 1;
							Object[] row = new Object[] { allcount + "", zkzdata.getXingming(), zkzdata.getUpersonnum(),
									zkzdata.getZkznum(), zkzdata.getDd1(), zkzdata.getKd1(), zkzdata.getKc1() };// ,param.getPid()+""
							dftm.addRow(row);
						}
						turnlabel.setText("当前第" + pageResult.getCurrentPage() + "页，共" + pageResult.getTotalPage() + "页"
								+ "，共" + pageResult.getTotalCount() + "条");
					}
				}
			});
			
			
			//打印按钮		
			prtBtn=new JButton();
			prtBtn.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			prtBtn.setText("打印");
			barPanel.add(prtBtn);			
			prtBtn.addActionListener(new ActionListener() {				
				@Override
				public void actionPerformed(ActionEvent e) {
					 new PrinterTableUtil().printTable(dftm);   	
					
				}
			});
			
			
			
			final JScrollPane scrollPane = new JScrollPane();
			final GridBagConstraints gridBagConstraints_8 = new GridBagConstraints();
			gridBagConstraints_8.weighty = 1.0;
			gridBagConstraints_8.anchor = GridBagConstraints.NORTH;
			gridBagConstraints_8.insets = new Insets(10, 10, 10, 10);
			gridBagConstraints_8.fill = GridBagConstraints.BOTH;
			gridBagConstraints_8.gridwidth = 12;
			gridBagConstraints_8.gridy = 1;
			gridBagConstraints_8.gridx = 0;
			add(scrollPane, gridBagConstraints_8);

			dftm = new DefaultTableModel() {
				@Override
				public boolean isCellEditable(int row, int column) {
					if (column == 0) {
						return true;
					} else {
						return false;
					}
				}
			};

			// String[] tableHeads = new String[]{"选择","序号", "地市", "地点","考点", "考场",
			// "开始时间","结束时间"};//,"id可以隐藏吗"
			String[] tableHeads = new String[] { "序号", "姓名", "身份证号", "准考证号", "地点", "考点", "考场" };// ,"id可以隐藏吗"
			dftm.setColumnIdentifiers(tableHeads);
			DefaultTableCellRenderer render = new DefaultTableCellRenderer();
			render.setHorizontalAlignment(SwingConstants.CENTER);// 设置单元格渲染方式

			// 如何向table中放内容//查询初始数据
			pageResult = facelog.getFaceLogsByPageCondition(null, null,null,null,null, null, null, Const.FIRSTPAGE);

			allcount = 0;
			List<Zkzdata> list = pageResult.getResult();
			for (int i = 0; i < list.size(); i++) {
				Zkzdata zkzdata = list.get(i);
				allcount = allcount + 1;
				Object[] row = new Object[] { allcount + "", zkzdata.getXingming(), zkzdata.getUpersonnum(),
						zkzdata.getZkznum(), zkzdata.getDd1(), zkzdata.getKd1(), zkzdata.getKc1() };// ,param.getPid()+""
				dftm.addRow(row);
			}

			table = new JTable(dftm);
			// table.setEnabled(false);
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
			table.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			table.setRowHeight(30);
			table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getWidth(), 30));
			table.getTableHeader().setFont(new Font("", Font.PLAIN, 14));

			TableColumn column0 = table.getColumn("序号");
			column0.setPreferredWidth(10);
			column0.setCellRenderer(render);
			TableColumn column1 = table.getColumn("姓名");
			column1.setPreferredWidth(50);
			column1.setCellRenderer(render);
			TableColumn column2 = table.getColumn("身份证号");
			column2.setPreferredWidth(250);
			column2.setCellRenderer(render);
			TableColumn column = table.getColumn("准考证号");
			column.setPreferredWidth(50);
			column.setCellRenderer(render);
			TableColumn column3 = table.getColumn("地点");
			column3.setPreferredWidth(250);
			column3.setCellRenderer(render);
			TableColumn column4 = table.getColumn("考点");
			column4.setPreferredWidth(200);
			column4.setCellRenderer(render);
			TableColumn column5 = table.getColumn("考场");
			column5.setPreferredWidth(100);
			column5.setCellRenderer(render);
			scrollPane.setViewportView(table);

			// 首页
			firstbutton = new JButton();
			// final GridBagConstraints gridBagConstraints_first = new GridBagConstraints();
			// gridBagConstraints_first.gridy = 2;
			// gridBagConstraints_first.gridx = 4;
			firstbutton.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			firstbutton.setText("首页");
			pagePanel.add(firstbutton);
			firstbutton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// 认证结果
					String rzresult = bu.getStr(condition1.getSelectedItem() + "");
					// 认证次数
					String rzcount = bu.getStr(condition2.getSelectedItem() + "");
					
					//考点信息
					String rzKaodian=bu.getStr(condition6.getSelectedItem()+"");
					//考场信息
					String rzKaochang=bu.getStr(condition7.getSelectedItem()+"");
					
					// 场次
					String changci = bu.getStr(condition3.getSelectedItem() + "");
					// 开始时间
					String startime = bu.getStr(startDate.getText());// 获取开始时间
					// 结束时间
					String endtime = bu.getStr(endDate.getText());// 获取结束时间
					// System.out.println(dishi+"--"+kaodian+"--"+kaochang+"--"+startime+"--"+endtime);
					// 判断考场内容
					if (changci.equals("")) {
						JOptionPane.showMessageDialog(null, "请选择场次", "添加考场设置", JOptionPane.WARNING_MESSAGE);
						return;
					} else {
						pageResult = facelog.getFaceLogsByPageCondition(rzresult, changci,rzKaodian,rzKaochang, rzcount, startime, endtime,
								Const.FIRSTPAGE);
						// 重新更新jtable列表
						Integer c1 = dftm.getRowCount();
						for (int i = 0; i < c1; i++) {
							dftm.removeRow(0);
						}
						allcount = 0;
						List<Zkzdata> list = pageResult.getResult();
						for (int i = 0; i < list.size(); i++) {
							Zkzdata zkzdata = list.get(i);
							allcount = allcount + 1;
							Object[] row = new Object[] { allcount + "", zkzdata.getXingming(), zkzdata.getUpersonnum(),
									zkzdata.getZkznum(), zkzdata.getDd1(), zkzdata.getKd1(), zkzdata.getKc1() };// ,param.getPid()+""
							dftm.addRow(row);
						}
						turnlabel.setText("当前第" + pageResult.getCurrentPage() + "页，共" + pageResult.getTotalPage() + "页"
								+ "，共" + pageResult.getTotalCount() + "条");
					}
				}
			});

			// 上一页
			// final GridBagConstraints gridBagConstraints_pre = new GridBagConstraints();
			// gridBagConstraints_pre.gridy = 2;
			// gridBagConstraints_pre.gridx = 5;
			prebutton.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			prebutton.setText("上一页");
			pagePanel.add(prebutton);
			prebutton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// 认证结果
					String rzresult = bu.getStr(condition1.getSelectedItem() + "");
					// 认证次数
					String rzcount = bu.getStr(condition2.getSelectedItem() + "");
					
					//考点信息
					String rzKaodian=bu.getStr(condition6.getSelectedItem()+"");
					//考场信息
					String rzKaochang=bu.getStr(condition7.getSelectedItem()+"");
					
					// 场次
					String changci = bu.getStr(condition3.getSelectedItem() + "");
					// 开始时间
					String startime = bu.getStr(startDate.getText());// 获取开始时间
					// 结束时间
					String endtime = bu.getStr(endDate.getText());// 获取结束时间
					// System.out.println(dishi+"--"+kaodian+"--"+kaochang+"--"+startime+"--"+endtime);
					// 判断考场内容
					if (changci.equals("")) {
						JOptionPane.showMessageDialog(null, "请选择场次", "添加考场设置", JOptionPane.WARNING_MESSAGE);
						return;
					} else {
						
						pageResult = facelog.getFaceLogsByPageCondition(rzresult, changci,rzKaodian,rzKaochang, rzcount, startime, endtime,
								pageResult.getPrevious());
						
						// 重新更新jtable列表
						Integer c1 = dftm.getRowCount();
						for (int i = 0; i < c1; i++) {
							dftm.removeRow(0);
						}
						allcount = 0;
						List<Zkzdata> list = pageResult.getResult();
						for (int i = 0; i < list.size(); i++) {
							Zkzdata zkzdata = list.get(i);
							allcount = allcount + 1;
							Object[] row = new Object[] { allcount + "", zkzdata.getXingming(), zkzdata.getUpersonnum(),
									zkzdata.getZkznum(), zkzdata.getDd1(), zkzdata.getKd1(), zkzdata.getKc1() };// ,param.getPid()+""
							dftm.addRow(row);
						}
						turnlabel.setText("当前第" + pageResult.getCurrentPage() + "页，共" + pageResult.getTotalPage() + "页"
								+ "，共" + pageResult.getTotalCount() + "条");
					}

				}
			});

			// 下一页
			// final GridBagConstraints gridBagConstraints_next = new GridBagConstraints();
			// gridBagConstraints_next.gridy = 2;
			// gridBagConstraints_next.gridx = 6;
			nextbutton.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			nextbutton.setText("下一页");
			pagePanel.add(nextbutton);
			nextbutton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// 认证结果
					String rzresult = bu.getStr(condition1.getSelectedItem() + "");
					// 认证次数
					String rzcount = bu.getStr(condition2.getSelectedItem() + "");
					
					//考点信息
					String rzKaodian=bu.getStr(condition6.getSelectedItem()+"");
					//考场信息
					String rzKaochang=bu.getStr(condition7.getSelectedItem()+"");
					
									
					// 场次
					String changci = bu.getStr(condition3.getSelectedItem() + "");
					// 开始时间
					String startime = bu.getStr(startDate.getText());// 获取开始时间
					// 结束时间
					String endtime = bu.getStr(endDate.getText());// 获取结束时间
					// System.out.println(dishi+"--"+kaodian+"--"+kaochang+"--"+startime+"--"+endtime);
					// 判断考场内容
					if (changci.equals("")) {
						JOptionPane.showMessageDialog(null, "请选择场次", "添加考场设置", JOptionPane.WARNING_MESSAGE);
						return;
					} else {
						
						pageResult = facelog.getFaceLogsByPageCondition(rzresult, changci,rzKaodian,rzKaochang, rzcount, startime, endtime,
								pageResult.getNext());
											
						// 重新更新jtable列表
						Integer c1 = dftm.getRowCount();
						for (int i = 0; i < c1; i++) {
							dftm.removeRow(0);
						}
						allcount = 0;
						List<Zkzdata> list = pageResult.getResult();
						for (int i = 0; i < list.size(); i++) {
							Zkzdata zkzdata = list.get(i);
							allcount = allcount + 1;
							Object[] row = new Object[] { allcount + "", zkzdata.getXingming(), zkzdata.getUpersonnum(),
									zkzdata.getZkznum(), zkzdata.getDd1(), zkzdata.getKd1(), zkzdata.getKc1() };// ,param.getPid()+""
							dftm.addRow(row);
						}
						turnlabel.setText("当前第" + pageResult.getCurrentPage() + "页，共" + pageResult.getTotalPage() + "页"
								+ "，共" + pageResult.getTotalCount() + "条");
					}
				}
			});

			// 末页
			// final GridBagConstraints gridBagConstraints_last = new GridBagConstraints();
			// gridBagConstraints_last.gridy = 2;
			// gridBagConstraints_last.gridx = 7;
			lastbutton.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			lastbutton.setText("末页");
			pagePanel.add(lastbutton);
			lastbutton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// 认证结果
					String rzresult = bu.getStr(condition1.getSelectedItem() + "");
					// 认证次数
					String rzcount = bu.getStr(condition2.getSelectedItem() + "");
					
					//考点信息
					String rzKaodian=bu.getStr(condition6.getSelectedItem()+"");
					//考场信息
					String rzKaochang=bu.getStr(condition7.getSelectedItem()+"");
					
					
					// 场次
					String changci = bu.getStr(condition3.getSelectedItem() + "");
					// 开始时间
					String startime = bu.getStr(startDate.getText());// 获取开始时间
					// 结束时间
					String endtime = bu.getStr(endDate.getText());// 获取结束时间
					// System.out.println(dishi+"--"+kaodian+"--"+kaochang+"--"+startime+"--"+endtime);
					// 判断考场内容
					if (changci.equals("")) {
						JOptionPane.showMessageDialog(null, "请选择场次", "添加考场设置", JOptionPane.WARNING_MESSAGE);
						return;
					} else {
						
						pageResult = facelog.getFaceLogsByPageCondition(rzresult, changci,rzKaodian,rzKaochang, rzcount, startime, endtime,
								pageResult.getTotalPage());
						
				
						// 重新更新jtable列表
						Integer c1 = dftm.getRowCount();
						for (int i = 0; i < c1; i++) {
							dftm.removeRow(0);
						}
						allcount = 0;
						List<Zkzdata> list = pageResult.getResult();
						for (int i = 0; i < list.size(); i++) {
							Zkzdata zkzdata = list.get(i);
							allcount = allcount + 1;
							Object[] row = new Object[] { allcount + "", zkzdata.getXingming(), zkzdata.getUpersonnum(),
									zkzdata.getZkznum(), zkzdata.getDd1(), zkzdata.getKd1(), zkzdata.getKc1() };// ,param.getPid()+""
							dftm.addRow(row);
						}
						turnlabel.setText("当前第" + pageResult.getCurrentPage() + "页，共" + pageResult.getTotalPage() + "页"
								+ "，共" + pageResult.getTotalCount() + "条");
					}
				}
			});

			// 设置turnlable
			turnlabel = new JLabel();
			turnlabel.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			turnlabel.setText("当前第" + pageResult.getCurrentPage() + "页，共" + pageResult.getTotalPage() + "页" + "，共"
					+ pageResult.getTotalCount() + "条");
			// final GridBagConstraints gridBagConstraints_turn = new GridBagConstraints();
			// gridBagConstraints_turn.gridy = 2;
			// gridBagConstraints_turn.gridx = 8;
			pagePanel.add(turnlabel);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

}
