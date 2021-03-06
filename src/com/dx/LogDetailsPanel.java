package com.dx;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
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
import com.dx.pojo.FaceLog;
import com.dx.service.FaceLogsImpl;
import com.dx.util.BaseUtil;
import com.dx.util.Const;
import com.dx.util.DateChooser;
import com.dx.util.PageUtil;

/**
 * 日志认证详情查询
 * */
public class LogDetailsPanel extends JPanel{
	private JTextField endDate;
	private JTextField startDate;
	private JTable table;
	private JLabel label1;
	private JComboBox condition1;
	private JComboBox condition2;
	private JLabel label2;
	private JTextField idcard ;
	private JLabel label3;
	private JTextField xingming ;
	private JLabel label4;
	private JLabel label5;
	private JLabel label6;
	
	private JPanel barPanel;//把条件查询放入这个界面
	private JPanel pagePanel;//把分页信息放在找个界面
	
	//private TbUserlist user;
	private JButton button;
	private DefaultTableModel dftm;
	private FaceLogsInterface impl = new FaceLogsImpl();
	private Integer curxuhao  = 0 ;
	private BaseUtil bu = new BaseUtil();
	private PageUtil pageutil = new PageUtil();
	//分页|^
	private JButton firstbutton;
	private JButton prebutton;
	private JButton nextbutton;
	private JButton lastbutton;
	private JLabel turnlabel ;
	
	
	//设置每页条数
	Integer len = 20 ;
	//返回当前第几页
	Integer curpage = 1 ;
	Integer totalpage = 0 ;
	Integer  allcount = 0 ;
	public LogDetailsPanel() {
		try {
			Border titleBorder1 = BorderFactory.createTitledBorder("认证日志详细列表");
	        setBorder(titleBorder1);
			GridBagLayout gridBagLayout = new GridBagLayout();
			gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,1.0};
			setLayout(gridBagLayout);
			//setBounds(100, 100, 601, 331);
	
			
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
            
            //分页信息放入找个界面
            
            pagePanel=new JPanel();
            pagePanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
            GridBagConstraints pageContrains=new GridBagConstraints();
            pageContrains.gridy=2;
            pageContrains.gridx=0;
            pageContrains.gridwidth=12;   	
            pageContrains.anchor = GridBagConstraints.NORTH;
            pageContrains.insets = new Insets(0, 10, 0, 10);
            pageContrains.fill = GridBagConstraints.BOTH;
            add(pagePanel,pageContrains);
            
			
			
			label1 = new JLabel();
			label1.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			label1.setText(" 认证场次:");
//			final GridBagConstraints gridBagConstraints = new GridBagConstraints();
//			gridBagConstraints.gridy = 0;
//			gridBagConstraints.gridx = 0;
			barPanel.add(label1);
	
			condition1 = new JComboBox();
			String[] changci = {"请选择","职业道德","职业能力","岗位技能"};
			condition1.setModel(new DefaultComboBoxModel(changci));
			condition1.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			condition1.setPreferredSize(new Dimension(100, 30));
//			final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
//			gridBagConstraints_1.gridy = 0;
//			gridBagConstraints_1.gridx = 1;
			barPanel.add(condition1);
			
			
			
			label2 = new JLabel();
			label2.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			label2.setText(" 身份证号:");
//			final GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
//			gridBagConstraints2.gridy = 0;
//			gridBagConstraints2.gridx = 2;
			barPanel.add(label2);
	
			
			idcard = new JTextField();
			//从数据库获取考点下拉列表
			idcard.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			idcard.setPreferredSize(new Dimension(150, 30));
//			final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
//			gridBagConstraints_2.gridy = 0;
//			gridBagConstraints_2.gridx = 3;
			barPanel.add(idcard);
			
			label3 = new JLabel();
			label3.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			label3.setText(" 姓名:");
//			final GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
//			gridBagConstraints3.gridy = 0;
//			gridBagConstraints3.gridx = 4;
			barPanel.add(label3);
	
			
			xingming = new JTextField();
			//从数据库获取考点下拉列表
			String[] kaodian = new String[] {};
			xingming.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			xingming.setPreferredSize(new Dimension(100, 30));
//			final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
//			gridBagConstraints_3.gridy = 0;			
//			gridBagConstraints_3.gridx = 5;
			
			barPanel.add(xingming);
			
			
			label6 = new JLabel();
			label6.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			label6.setText(" 认证类型:");
//			final GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
//			gridBagConstraints6.gridy = 0;
//			gridBagConstraints6.gridx = 6;
			barPanel.add(label6);
	
			condition2 = new JComboBox();
			String[] renzhengleixing = {"请选择","非本人身份证","非考生","考试时间不正确","考试地点不正确","考场不正确","通过"};//从数据库获取地市列表
			condition2.setModel(new DefaultComboBoxModel(renzhengleixing));
			condition2.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			condition2.setPreferredSize(new Dimension(130, 30));
//			final GridBagConstraints gridBagConstraints_6 = new GridBagConstraints();
//			gridBagConstraints_6.gridy = 0;
//			gridBagConstraints_6.gridx = 7;
			barPanel.add(condition2);
			
			
			label4 = new JLabel();
			label4.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			label4.setText(" 时间:");
//			final GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
//			gridBagConstraints4.gridy = 0;
//			gridBagConstraints4.gridx = 8;
			barPanel.add(label4);
	
			//时间框为填写区域
			startDate = new JTextField();
//			final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();
			startDate.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			startDate.setPreferredSize(new Dimension(150, 30));
//			gridBagConstraints_4.gridy = 0;
//			gridBagConstraints_4.gridx = 9;
			DateChooser dateChooser1 = DateChooser.getInstance("yyyy-MM-dd HH:mm:ss");
			dateChooser1.register(startDate);
			barPanel.add(startDate);
			
			label5 = new JLabel();
			label5.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			label5.setText("至");
//			final GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
//			gridBagConstraints5.ipadx = 20;
//			gridBagConstraints5.gridy = 0;
//			gridBagConstraints5.gridx = 10;
			barPanel.add(label5);
	
			
			endDate = new JTextField();
		//	final GridBagConstraints gridBagConstraints_5 = new GridBagConstraints();
			endDate.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			endDate.setPreferredSize(new Dimension(150, 30));
//			gridBagConstraints_5.gridy = 0;
//			gridBagConstraints_5.gridx = 11;
			DateChooser dateChooser2 = DateChooser.getInstance("yyyy-MM-dd HH:mm:ss");
			dateChooser2.register(endDate);
			barPanel.add(endDate);
			
			button = new JButton();
//			final GridBagConstraints gridBagConstraints_7 = new GridBagConstraints();
//			gridBagConstraints_7.gridy = 0;
//			gridBagConstraints_7.gridx = 12;
			button.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			button.setText("查询");
			barPanel.add(button);
			
			
			button.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	//条件查询，重新添加jtable下的列表数据
	            	String cc = bu.getStr(condition1.getSelectedItem()+"");//场次
	            	String card = bu.getStr(idcard.getText());//身份证
	            	String xm = bu.getStr(xingming.getText());//姓名
	            	String lx = bu.getStr(condition2.getSelectedItem()+"");//认证类型
	            	String startime = bu.getStr(startDate.getText());//时间起始
	            	String endtime = bu.getStr(endDate.getText()) ; //时间结束
	            	if(cc.equals("")&& card.equals("")&&xm.equals("")&&lx.equals("")&&startime.equals("")&&endtime.equals("")) {
	            		JOptionPane.showMessageDialog(null, "选择内容为空！" , "条件查询",JOptionPane.WARNING_MESSAGE);
	            		return ;
	            	}else {
	            		//获取总条数
	        			allcount = impl.getRenZhengCount(cc,card,xm,lx,startime,endtime);
	        			totalpage = pageutil.getTotalPage(allcount, len) ;
	        			Integer startlen = pageutil.getStartindex(allcount, len, 1) ;
	        			Integer endlen = pageutil.getCurrpageSize(allcount, len, 1) ;//当前页总条数
	        			curpage = 1 ;
	            		List<FaceLog> loglist = impl.getRenZhengList(cc,card,xm,lx,startime,endtime,startlen,endlen);
	            		//重新更新jtable列表
	            		Integer c1 =dftm.getRowCount() ; 
						for(int i=0; i<c1 ;i++) {
							dftm.removeRow(0);
						}
						curxuhao = 0 ;
						for(int i=0 ;i<loglist.size() ;i++ ) {
							FaceLog facelog = loglist.get(i) ;
							curxuhao = curxuhao+1 ;
							Object[] row = new Object[] {((curpage-1)*len+curxuhao)+"",facelog.getXingming(),facelog.getSfz(), facelog.getShijian(), facelog.getChangci(),facelog.getShibieleixing(),"认证照片查看",facelog.getSfzphoto()+";"+facelog.getRenlianphoto()} ;//,param.getPid()+""
							dftm.addRow(row);
						}
						
						turnlabel.setText("当前第"+curpage+"页，共"+totalpage+"页");
	            	}
	            }

				
	        });
			
			
			final JScrollPane scrollPane = new JScrollPane();
			final GridBagConstraints gridBagConstraints_8 = new GridBagConstraints();
			gridBagConstraints_8.weighty = 1.0;
			gridBagConstraints_8.anchor = GridBagConstraints.NORTH;
			gridBagConstraints_8.insets = new Insets(0, 10, 0, 10);
			gridBagConstraints_8.fill = GridBagConstraints.BOTH;
			gridBagConstraints_8.gridwidth = 13;
			gridBagConstraints_8.gridy = 1;
			gridBagConstraints_8.gridx = 0;
			add(scrollPane, gridBagConstraints_8);
	
			dftm = new DefaultTableModel(){
	            @Override
	            public boolean isCellEditable(int row,int column){
	            	return false;
	            }
			};
			            
			String[] tableHeads = new String[]{"序号", "姓名", "身份证号","认证时间", "认证场次", "认证类型","照片详情","照片地址"};//,"id可以隐藏吗"
			dftm.setColumnIdentifiers(tableHeads);
			DefaultTableCellRenderer render = new DefaultTableCellRenderer() ;
			render.setHorizontalAlignment(SwingConstants.CENTER);//设置单元格渲染方式
			render.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			
			
			//获取总条数
			allcount = impl.getRenZhengCount("","","","","","");
			Integer startlen = pageutil.getStartindex(allcount, len, curpage) ;
			Integer endlen = pageutil.getCurrpageSize(allcount, len, curpage) ;
			totalpage = pageutil.getTotalPage(allcount, len) ;
			
			
			//如何向table中放内容//查询初始数据
			List<FaceLog> list = impl.getRenZhengList("","","","","","",startlen,endlen);
			for(int i=0 ;i<list.size() ;i++ ) {
				FaceLog facelog = list.get(i) ;
				curxuhao = curxuhao+1 ;
				Object[] row = new Object[] {((curpage-1)*len+curxuhao)+"",facelog.getXingming(),facelog.getSfz(), facelog.getShijian(), facelog.getChangci(),facelog.getShibieleixing(),"认证照片查看",facelog.getSfzphoto()+";"+facelog.getRenlianphoto()} ;//,param.getPid()+""
				dftm.addRow(row);
			}
			
			
			
			table = new JTable(dftm);
			table.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			table.setRowHeight(30);
			table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getWidth(), 30));
			table.getTableHeader().setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			//table.setEnabled(false);
//			table.setSelectionModel(new DefaultListSelectionModel() {
//				@Override
//				public void setSelectionInterval(int index0, int index1) {
//					if (super.isSelectedIndex(index0)) {
//						super.removeSelectionInterval(index0, index1);
//					} else {
//						super.addSelectionInterval(index0, index1);
//					}
//				}
//			});
			table.addMouseListener(new  MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					int row = table.getSelectedRow();
					int column = table.getSelectedColumn();
					//System.out.println("行-------"+row+"     列------"+column);
					//需要获取隐藏列中的值
					if(column==6) {
						Object value= table.getValueAt(row, column+1);//照片地址
						Object idcard= table.getValueAt(row, 2);//身份证号
						String info=value.toString();
						//javax.swing.JOptionPane.showMessageDialog(null,info);
						String[] sp = info.split(";");
						String  sfzpurl = sp[0] ;
						String renlurl = sp[1] ;
						//JOptionPane.showInputDialog(null, "身份证照片", "照片", JOptionPane.WARNING_MESSAGE, new ImageIcon(sfzpurl), sp, null);
						//JOptionPane.showOptionDialog(null,  "身份证照片", "照片", JOptionPane.WARNING_MESSAGE, JOptionPane.WARNING_MESSAGE, new ImageIcon(sfzpurl), sp, null);
						JFrame tupianframe = new JFrame();
						Toolkit toolkit = Toolkit.getDefaultToolkit();
						int x = (int)toolkit.getScreenSize().getWidth();
						int y = (int)toolkit.getScreenSize().getHeight();
						tupianframe.setLocation(x/2-400, y/2-100);
						tupianframe.setSize(400, 200);
						tupianframe.setTitle(idcard+"认证图片查看");
						tupianframe.setVisible(true);
						JPanel panel = new PhotoPanel(sp);
						tupianframe.getContentPane().add(panel);
						tupianframe.setVisible(true);
					}
				}
			});
			
			
			
			
			
			TableColumn column1 = table.getColumn("序号");
			column1.setPreferredWidth(50);
			column1.setCellRenderer(render);
			TableColumn column2 = table.getColumn("姓名");
			column2.setPreferredWidth(150);
			column2.setCellRenderer(render);
			TableColumn column = table.getColumn("身份证号");
			column.setPreferredWidth(250);
			column.setCellRenderer(render);
			TableColumn column3 = table.getColumn("认证时间");
			column3.setPreferredWidth(250);
			column3.setCellRenderer(render);
			TableColumn column4 = table.getColumn("认证场次");
			column4.setPreferredWidth(100);
			column4.setCellRenderer(render);
			TableColumn column5 = table.getColumn("认证类型");
			column5.setPreferredWidth(200);
			column5.setCellRenderer(render);
			
			DefaultTableCellRenderer render2 = new DefaultTableCellRenderer() ;
			render2.setHorizontalAlignment(SwingConstants.CENTER);
			render2.setForeground(Color.RED);
			TableColumn column6 = table.getColumn("照片详情");
			column6.setPreferredWidth(100);
			column6.setCellRenderer(render2);
			
			
			TableColumn column7 = table.getColumn("照片地址");
			column7.setPreferredWidth(100);
			column7.setCellRenderer(render);
			column7.setMaxWidth(0);
			column7.setPreferredWidth(0);
			column7.setWidth(0);
			column7.setMinWidth(0);
			table.getTableHeader().getColumnModel().getColumn(7).setMaxWidth(0);
			table.getTableHeader().getColumnModel().getColumn(7).setMinWidth(0);
			
			
			
			
			scrollPane.setViewportView(table);
			
			
			firstbutton = new JButton();//首页
//			final GridBagConstraints gridBagConstraints_first = new GridBagConstraints();
//			gridBagConstraints_first.gridy = 2;
//			gridBagConstraints_first.gridx = 4;
			firstbutton.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			firstbutton.setText("首页");
			pagePanel.add(firstbutton);
			firstbutton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					
					String cc = bu.getStr(condition1.getSelectedItem()+"") ;//场次
	            	String card = bu.getStr(idcard.getText());//身份证
	            	String xm = bu.getStr(xingming.getText());//姓名
	            	String lx = bu.getStr(condition2.getSelectedItem()+"") ;//认证类型
	            	String startime = bu.getStr(startDate.getText());//时间起始
	            	String endtime = bu.getStr(endDate.getText()) ; //时间结束
					//获取总条数
        			allcount = impl.getRenZhengCount(cc,card,xm,lx,startime,endtime);
        			Integer startlen = pageutil.getStartindex(allcount, len, 1) ;
        			Integer endlen = pageutil.getCurrpageSize(allcount, len, 1) ;//当前页总条数
        			totalpage = pageutil.getTotalPage(allcount, len) ;
        			curpage = 1 ;
        			
            		List<FaceLog> loglist = impl.getRenZhengList(cc,card,xm,lx,startime,endtime,startlen,endlen);
					//重新更新jtable列表
            		Integer c1 =dftm.getRowCount() ; 
					for(int i=0; i<c1 ;i++) {
						dftm.removeRow(0);
					}
					curxuhao = 0 ;
					for(int i=0 ;i<loglist.size() ;i++ ) {
						FaceLog facelog = loglist.get(i) ;
						curxuhao = curxuhao+1 ;
						Object[] row = new Object[] {((curpage-1)*len+curxuhao)+"",facelog.getXingming(),facelog.getSfz(), facelog.getShijian(), facelog.getChangci(),facelog.getShibieleixing(),"认证照片查看",facelog.getSfzphoto()+";"+facelog.getRenlianphoto()} ;//,param.getPid()+""
						dftm.addRow(row);
					}
					
					turnlabel.setText("当前第"+curpage+"页，共"+totalpage+"页");
				}
			});
			
			prebutton = new JButton();//上一页
//			final GridBagConstraints gridBagConstraints_pre = new GridBagConstraints();
//			gridBagConstraints_pre.gridy = 2;
//			gridBagConstraints_pre.gridx = 5;
			prebutton.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			prebutton.setText("上一页");
			pagePanel.add(prebutton);
			prebutton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					
					String cc = bu.getStr(condition1.getSelectedItem()+"") ;//场次
	            	String card = bu.getStr(idcard.getText());//身份证
	            	String xm = bu.getStr(xingming.getText());//姓名
	            	String lx = bu.getStr(condition2.getSelectedItem()+"") ;//认证类型
	            	String startime = bu.getStr(startDate.getText());//时间起始
	            	String endtime = bu.getStr(endDate.getText()) ; //时间结束
					//获取总条数
        			allcount = impl.getRenZhengCount(cc,card,xm,lx,startime,endtime);
        			curpage = curpage==1?curpage : curpage-1 ;
        			Integer startlen = pageutil.getStartindex(allcount, len, curpage) ;
        			Integer endlen = pageutil.getCurrpageSize(allcount, len, curpage) ;//当前页总条数
        			totalpage = pageutil.getTotalPage(allcount, len) ;
        			
            		List<FaceLog> loglist = impl.getRenZhengList(cc,card,xm,lx,startime,endtime,startlen,endlen);
					//重新更新jtable列表
            		Integer c1 =dftm.getRowCount() ; 
					for(int i=0; i<c1 ;i++) {
						dftm.removeRow(0);
					}
					curxuhao = 0 ;
					for(int i=0 ;i<loglist.size() ;i++ ) {
						FaceLog facelog = loglist.get(i) ;
						curxuhao = curxuhao+1 ;
						Object[] row = new Object[] {((curpage-1)*len+curxuhao)+"",facelog.getXingming(),facelog.getSfz(), facelog.getShijian(), facelog.getChangci(),facelog.getShibieleixing(),"认证照片查看",facelog.getSfzphoto()+";"+facelog.getRenlianphoto()} ;//,param.getPid()+""
						dftm.addRow(row);
					}
					
					turnlabel.setText("当前第"+curpage+"页，共"+totalpage+"页");
				}
			});
			
			nextbutton = new JButton();//下一页
//			final GridBagConstraints gridBagConstraints_next = new GridBagConstraints();
//			gridBagConstraints_next.gridy = 2;
//			gridBagConstraints_next.gridx = 6;
			nextbutton.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			nextbutton.setText("下一页");
			pagePanel.add(nextbutton);
			nextbutton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					
					String cc = bu.getStr(condition1.getSelectedItem()+"") ;//场次
	            	String card = bu.getStr(idcard.getText());//身份证
	            	String xm = bu.getStr(xingming.getText());//姓名
	            	String lx = bu.getStr(condition2.getSelectedItem()+"") ;//认证类型
	            	String startime = bu.getStr(startDate.getText());//时间起始
	            	String endtime = bu.getStr(endDate.getText()) ; //时间结束
					//获取总条数
        			allcount = impl.getRenZhengCount(cc,card,xm,lx,startime,endtime);
        			totalpage = pageutil.getTotalPage(allcount, len) ;
        			curpage = curpage==totalpage?totalpage : curpage+1 ;
        			Integer startlen = pageutil.getStartindex(allcount, len, curpage) ;
        			Integer endlen = pageutil.getCurrpageSize(allcount, len, curpage) ;//当前页总条数
        			
            		List<FaceLog> loglist = impl.getRenZhengList(cc,card,xm,lx,startime,endtime,startlen,endlen);
					//重新更新jtable列表
            		Integer c1 =dftm.getRowCount() ; 
					for(int i=0; i<c1 ;i++) {
						dftm.removeRow(0);
					}
					curxuhao = 0 ;
					for(int i=0 ;i<loglist.size() ;i++ ) {
						FaceLog facelog = loglist.get(i) ;
						curxuhao = curxuhao+1 ;
						Object[] row = new Object[] {((curpage-1)*len+curxuhao)+"",facelog.getXingming(),facelog.getSfz(), facelog.getShijian(), facelog.getChangci(),facelog.getShibieleixing(),"认证照片查看",facelog.getSfzphoto()+";"+facelog.getRenlianphoto()} ;//,param.getPid()+""
						dftm.addRow(row);
					}
					
					turnlabel.setText("当前第"+curpage+"页，共"+totalpage+"页");
				}
			});
			
			lastbutton = new JButton();//末页
//			final GridBagConstraints gridBagConstraints_last = new GridBagConstraints();
//			gridBagConstraints_last.gridy = 2;
//			gridBagConstraints_last.gridx = 7;
			lastbutton.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			lastbutton.setText("末页");
			pagePanel.add(lastbutton);
			lastbutton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					
					String cc = bu.getStr(condition1.getSelectedItem()+"") ;//场次
	            	String card = bu.getStr(idcard.getText());//身份证
	            	String xm = bu.getStr(xingming.getText());//姓名
	            	String lx = bu.getStr(condition2.getSelectedItem()+"") ;//认证类型
	            	String startime = bu.getStr(startDate.getText());//时间起始
	            	String endtime = bu.getStr(endDate.getText()) ; //时间结束
	            	
        			allcount = impl.getRenZhengCount(cc,card,xm,lx,startime,endtime);//总条数
        			totalpage = pageutil.getTotalPage(allcount, len);//总页数/末页
        			curpage = totalpage ;
        			Integer startlen = pageutil.getStartindex(allcount, len, curpage) ;//起始行
        			Integer endlen = pageutil.getCurrpageSize(allcount, len, curpage) ;//当前页总条数
        			
            		List<FaceLog> loglist = impl.getRenZhengList(cc,card,xm,lx,startime,endtime,startlen,endlen);
					//重新更新jtable列表
            		Integer c1 =dftm.getRowCount() ; 
					for(int i=0; i<c1 ;i++) {
						dftm.removeRow(0);
					}
					curxuhao = 0 ;
					for(int i=0 ;i<loglist.size() ;i++ ) {
						FaceLog facelog = loglist.get(i) ;
						curxuhao = curxuhao+1 ;
						Object[] row = new Object[] {((curpage-1)*len+curxuhao)+"",facelog.getXingming(),facelog.getSfz(), facelog.getShijian(), facelog.getChangci(),facelog.getShibieleixing(),"认证照片查看",facelog.getSfzphoto()+";"+facelog.getRenlianphoto()} ;//,param.getPid()+""
						dftm.addRow(row);
					}
					turnlabel.setText("当前第"+curpage+"页，共"+totalpage+"页");
				}
			});
			
			turnlabel = new JLabel();
			turnlabel.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			turnlabel.setText("当前第"+curpage+"页，共"+totalpage+"页");
//			final GridBagConstraints gridBagConstraints_turn = new GridBagConstraints();
//			gridBagConstraints_turn.gridy = 2;
//			gridBagConstraints_turn.gridx = 8;
			pagePanel.add(turnlabel);
			
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
	}
	
	
	class PhotoPanel extends JPanel{
		public PhotoPanel(String[] objurl) {
		//	System.out.println("objurl------"+objurl[0]+"--------"+objurl[1]);
			try {
				
				JLabel labels = new JLabel();//身份证照片
				add(labels);
				labels.setBounds(0, 0, 100, 100);//放置身份证照片
				
				objurl[0] = objurl[0].replaceAll("\\\\", "//");
				File file = new File(objurl[0]);//身份证图像照片地址
				Image image = null;
				try {
					image = ImageIO.read(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
				ImageIcon imageicon=new ImageIcon(image);
				imageicon.setImage(imageicon.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT));//固定照片大小
				labels.setIcon(imageicon);
				
				JLabel labelx = new JLabel();//现场人脸照片
				add(labelx);
				labelx.setBounds(0, 0, 100,100);				
				objurl[1] = objurl[1].replaceAll("\\\\", "//");
				ImageIcon imageiconx=new ImageIcon(objurl[1]);
				imageiconx.setImage(imageiconx.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT));//固定照片大小
				labelx.setIcon(imageiconx);			
				setVisible(true);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
}
