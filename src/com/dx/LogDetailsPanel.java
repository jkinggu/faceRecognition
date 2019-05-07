package com.dx;

import java.awt.Color;
import java.awt.Dimension;
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
import com.dx.util.DateChooser;
import com.dx.util.PageUtil;

/**
 * ��־��֤�����ѯ
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
	//private TbUserlist user;
	private JButton button;
	private DefaultTableModel dftm;
	private FaceLogsInterface impl = new FaceLogsImpl();
	private Integer curxuhao  = 0 ;
	private BaseUtil bu = new BaseUtil();
	private PageUtil pageutil = new PageUtil();
	//��ҳ|^
	private JButton firstbutton;
	private JButton prebutton;
	private JButton nextbutton;
	private JButton lastbutton;
	private JLabel turnlabel ;
	
	
	//����ÿҳ����
	Integer len = 20 ;
	//���ص�ǰ�ڼ�ҳ
	Integer curpage = 1 ;
	Integer totalpage = 0 ;
	Integer  allcount = 0 ;
	public LogDetailsPanel() {
		try {
			Border titleBorder1 = BorderFactory.createTitledBorder("��֤��־��ϸ�б�");
	        setBorder(titleBorder1);
			GridBagLayout gridBagLayout = new GridBagLayout();
			gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,1.0};
			setLayout(gridBagLayout);
			setBounds(100, 100, 601, 331);
	
			label1 = new JLabel();
			label1.setFont(new Font("", Font.PLAIN, 14));
			label1.setText(" ��֤���Σ�");
			final GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridy = 0;
			gridBagConstraints.gridx = 0;
			add(label1, gridBagConstraints);
	
			condition1 = new JComboBox();
			String[] changci = {"��ѡ��","ְҵ����","ְҵ����","��λ����"};
			condition1.setModel(new DefaultComboBoxModel(changci));
			condition1.setFont(new Font("", Font.PLAIN, 14));
			condition1.setPreferredSize(new Dimension(100, 30));
			final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
			gridBagConstraints_1.gridy = 0;
			gridBagConstraints_1.gridx = 1;
			add(condition1, gridBagConstraints_1);
			
			label2 = new JLabel();
			label2.setFont(new Font("", Font.PLAIN, 14));
			label2.setText(" ����֤�ţ�");
			final GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.gridx = 2;
			add(label2, gridBagConstraints2);
	
			
			idcard = new JTextField();
			//�����ݿ��ȡ���������б�
			idcard.setFont(new Font("", Font.PLAIN, 14));
			idcard.setPreferredSize(new Dimension(150, 30));
			final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
			gridBagConstraints_2.gridy = 0;
			gridBagConstraints_2.gridx = 3;
			add(idcard, gridBagConstraints_2);
			
			label3 = new JLabel();
			label3.setFont(new Font("", Font.PLAIN, 14));
			label3.setText(" ������");
			final GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridy = 0;
			gridBagConstraints3.gridx = 4;
			add(label3, gridBagConstraints3);
	
			
			xingming = new JTextField();
			//�����ݿ��ȡ���������б�
			String[] kaodian = new String[] {};
			xingming.setFont(new Font("", Font.PLAIN, 14));
			xingming.setPreferredSize(new Dimension(100, 30));
			final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
			gridBagConstraints_3.gridy = 0;
			gridBagConstraints_3.gridx = 5;
			add(xingming, gridBagConstraints_3);
			
			
			label6 = new JLabel();
			label6.setFont(new Font("", Font.PLAIN, 14));
			label6.setText(" ��֤���ͣ�");
			final GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridy = 0;
			gridBagConstraints6.gridx = 6;
			add(label6, gridBagConstraints6);
	
			condition2 = new JComboBox();
			String[] renzhengleixing = {"��ѡ��","�Ǳ�������֤","�ǿ���","����ʱ�䲻��ȷ","���Եص㲻��ȷ","��������ȷ","ͨ��"};//�����ݿ��ȡ�����б�
			condition2.setModel(new DefaultComboBoxModel(renzhengleixing));
			condition2.setFont(new Font("", Font.PLAIN, 14));
			condition2.setPreferredSize(new Dimension(130, 30));
			final GridBagConstraints gridBagConstraints_6 = new GridBagConstraints();
			gridBagConstraints_6.gridy = 0;
			gridBagConstraints_6.gridx = 7;
			add(condition2, gridBagConstraints_6);
			
			
			label4 = new JLabel();
			label4.setFont(new Font("", Font.PLAIN, 14));
			label4.setText(" ʱ�䣺");
			final GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridy = 0;
			gridBagConstraints4.gridx = 8;
			add(label4, gridBagConstraints4);
	
			//ʱ���Ϊ��д����
			startDate = new JTextField();
			final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();
			startDate.setFont(new Font("", Font.PLAIN, 14));
			startDate.setPreferredSize(new Dimension(150, 30));
			gridBagConstraints_4.gridy = 0;
			gridBagConstraints_4.gridx = 9;
			DateChooser dateChooser1 = DateChooser.getInstance("yyyy-MM-dd HH:mm:ss");
			dateChooser1.register(startDate);
			add(startDate, gridBagConstraints_4);
			
			label5 = new JLabel();
			label5.setFont(new Font("", Font.PLAIN, 14));
			label5.setText("��");
			final GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.ipadx = 20;
			gridBagConstraints5.gridy = 0;
			gridBagConstraints5.gridx = 10;
			add(label5, gridBagConstraints5);
	
			endDate = new JTextField();
			final GridBagConstraints gridBagConstraints_5 = new GridBagConstraints();
			endDate.setFont(new Font("", Font.PLAIN, 14));
			endDate.setPreferredSize(new Dimension(150, 30));
			gridBagConstraints_5.gridy = 0;
			gridBagConstraints_5.gridx = 11;
			DateChooser dateChooser2 = DateChooser.getInstance("yyyy-MM-dd HH:mm:ss");
			dateChooser2.register(endDate);
			add(endDate, gridBagConstraints_5);
			
			button = new JButton();
			final GridBagConstraints gridBagConstraints_7 = new GridBagConstraints();
			gridBagConstraints_7.gridy = 0;
			gridBagConstraints_7.gridx = 12;
			button.setFont(new Font("", Font.PLAIN, 14));
			button.setText("��ѯ");
			add(button, gridBagConstraints_7);
			
			
			button.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	//������ѯ����������jtable�µ��б�����
	            	String cc = bu.getStr(condition1.getSelectedItem()+"") ;//����
	            	String card = bu.getStr(idcard.getText());//����֤
	            	String xm = bu.getStr(xingming.getText());//����
	            	String lx = bu.getStr(condition2.getSelectedItem()+"") ;//��֤����
	            	String startime = bu.getStr(startDate.getText());//ʱ����ʼ
	            	String endtime = bu.getStr(endDate.getText()) ; //ʱ�����
	            	if(cc.equals("")&& card.equals("")&&xm.equals("")&&lx.equals("")&&startime.equals("")&&endtime.equals("")) {
	            		JOptionPane.showMessageDialog(null, "ѡ������Ϊ�գ�" , "������ѯ",JOptionPane.WARNING_MESSAGE);
	            		return ;
	            	}else {
	            		//��ȡ������
	        			allcount = impl.getRenZhengCount(cc,card,xm,lx,startime,endtime);
	        			totalpage = pageutil.getTotalPage(allcount, len) ;
	        			Integer startlen = pageutil.getStartindex(allcount, len, 1) ;
	        			Integer endlen = pageutil.getCurrpageSize(allcount, len, 1) ;//��ǰҳ������
	        			curpage = 1 ;
	            		List<FaceLog> loglist = impl.getRenZhengList(cc,card,xm,lx,startime,endtime,startlen,endlen);
	            		//���¸���jtable�б�
	            		Integer c1 =dftm.getRowCount() ; 
						for(int i=0; i<c1 ;i++) {
							dftm.removeRow(0);
						}
						curxuhao = 0 ;
						for(int i=0 ;i<loglist.size() ;i++ ) {
							FaceLog facelog = loglist.get(i) ;
							curxuhao = curxuhao+1 ;
							Object[] row = new Object[] {((curpage-1)*len+curxuhao)+"",facelog.getXingming(),facelog.getSfz(), facelog.getShijian(), facelog.getChangci(),facelog.getShibieleixing(),"��֤��Ƭ�鿴",facelog.getSfzphoto()+";"+facelog.getRenlianphoto()} ;//,param.getPid()+""
							dftm.addRow(row);
						}
						
						turnlabel.setText("��ǰ��"+curpage+"ҳ����"+totalpage+"ҳ");
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
			            
			String[] tableHeads = new String[]{"���", "����", "����֤��","��֤ʱ��", "��֤����", "��֤����","��Ƭ����","��Ƭ��ַ"};//,"id����������"
			dftm.setColumnIdentifiers(tableHeads);
			DefaultTableCellRenderer render = new DefaultTableCellRenderer() ;
			render.setHorizontalAlignment(SwingConstants.CENTER);//���õ�Ԫ����Ⱦ��ʽ
			render.setFont(new Font("Dialog", Font.PLAIN, 16));
			
			
			//��ȡ������
			allcount = impl.getRenZhengCount("","","","","","");
			Integer startlen = pageutil.getStartindex(allcount, len, curpage) ;
			Integer endlen = pageutil.getCurrpageSize(allcount, len, curpage) ;
			totalpage = pageutil.getTotalPage(allcount, len) ;
			
			
			//�����table�з�����//��ѯ��ʼ����
			List<FaceLog> list = impl.getRenZhengList("","","","","","",startlen,endlen);
			for(int i=0 ;i<list.size() ;i++ ) {
				FaceLog facelog = list.get(i) ;
				curxuhao = curxuhao+1 ;
				Object[] row = new Object[] {((curpage-1)*len+curxuhao)+"",facelog.getXingming(),facelog.getSfz(), facelog.getShijian(), facelog.getChangci(),facelog.getShibieleixing(),"��֤��Ƭ�鿴",facelog.getSfzphoto()+";"+facelog.getRenlianphoto()} ;//,param.getPid()+""
				dftm.addRow(row);
			}
			
			
			
			table = new JTable(dftm);
			table.setFont(new Font("", Font.PLAIN, 14));
			table.setRowHeight(30);
			table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getWidth(), 30));
			table.getTableHeader().setFont(new Font("", Font.PLAIN, 14));
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
					//System.out.println("��-------"+row+"     ��------"+column);
					//��Ҫ��ȡ�������е�ֵ
					if(column==6) {
						Object value= table.getValueAt(row, column+1);//��Ƭ��ַ
						Object idcard= table.getValueAt(row, 2);//����֤��
						String info=value.toString();
						//javax.swing.JOptionPane.showMessageDialog(null,info);
						String[] sp = info.split(";");
						String  sfzpurl = sp[0] ;
						String renlurl = sp[1] ;
						//JOptionPane.showInputDialog(null, "����֤��Ƭ", "��Ƭ", JOptionPane.WARNING_MESSAGE, new ImageIcon(sfzpurl), sp, null);
						//JOptionPane.showOptionDialog(null,  "����֤��Ƭ", "��Ƭ", JOptionPane.WARNING_MESSAGE, JOptionPane.WARNING_MESSAGE, new ImageIcon(sfzpurl), sp, null);
						JFrame tupianframe = new JFrame();
						Toolkit toolkit = Toolkit.getDefaultToolkit();
						int x = (int)toolkit.getScreenSize().getWidth();
						int y = (int)toolkit.getScreenSize().getHeight();
						tupianframe.setLocation(x/2-400, y/2-100);
						tupianframe.setSize(400, 200);
						tupianframe.setTitle(idcard+"��֤ͼƬ�鿴");
						tupianframe.setVisible(true);
						JPanel panel = new PhotoPanel(sp);
						tupianframe.getContentPane().add(panel);
						tupianframe.setVisible(true);
					}
				}
			});
			
			
			
			
			
			TableColumn column1 = table.getColumn("���");
			column1.setPreferredWidth(50);
			column1.setCellRenderer(render);
			TableColumn column2 = table.getColumn("����");
			column2.setPreferredWidth(150);
			column2.setCellRenderer(render);
			TableColumn column = table.getColumn("����֤��");
			column.setPreferredWidth(250);
			column.setCellRenderer(render);
			TableColumn column3 = table.getColumn("��֤ʱ��");
			column3.setPreferredWidth(250);
			column3.setCellRenderer(render);
			TableColumn column4 = table.getColumn("��֤����");
			column4.setPreferredWidth(100);
			column4.setCellRenderer(render);
			TableColumn column5 = table.getColumn("��֤����");
			column5.setPreferredWidth(200);
			column5.setCellRenderer(render);
			
			DefaultTableCellRenderer render2 = new DefaultTableCellRenderer() ;
			render2.setHorizontalAlignment(SwingConstants.CENTER);
			render2.setForeground(Color.RED);
			TableColumn column6 = table.getColumn("��Ƭ����");
			column6.setPreferredWidth(100);
			column6.setCellRenderer(render2);
			
			
			TableColumn column7 = table.getColumn("��Ƭ��ַ");
			column7.setPreferredWidth(100);
			column7.setCellRenderer(render);
			column7.setMaxWidth(0);
			column7.setPreferredWidth(0);
			column7.setWidth(0);
			column7.setMinWidth(0);
			table.getTableHeader().getColumnModel().getColumn(7).setMaxWidth(0);
			table.getTableHeader().getColumnModel().getColumn(7).setMinWidth(0);
			
			
			
			
			scrollPane.setViewportView(table);
			
			
			firstbutton = new JButton();//��ҳ
			final GridBagConstraints gridBagConstraints_first = new GridBagConstraints();
			gridBagConstraints_first.gridy = 2;
			gridBagConstraints_first.gridx = 4;
			firstbutton.setFont(new Font("", Font.PLAIN, 14));
			firstbutton.setText("��ҳ");
			add(firstbutton, gridBagConstraints_first);
			firstbutton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					
					String cc = bu.getStr(condition1.getSelectedItem()+"") ;//����
	            	String card = bu.getStr(idcard.getText());//����֤
	            	String xm = bu.getStr(xingming.getText());//����
	            	String lx = bu.getStr(condition2.getSelectedItem()+"") ;//��֤����
	            	String startime = bu.getStr(startDate.getText());//ʱ����ʼ
	            	String endtime = bu.getStr(endDate.getText()) ; //ʱ�����
					//��ȡ������
        			allcount = impl.getRenZhengCount(cc,card,xm,lx,startime,endtime);
        			Integer startlen = pageutil.getStartindex(allcount, len, 1) ;
        			Integer endlen = pageutil.getCurrpageSize(allcount, len, 1) ;//��ǰҳ������
        			totalpage = pageutil.getTotalPage(allcount, len) ;
        			curpage = 1 ;
        			
            		List<FaceLog> loglist = impl.getRenZhengList(cc,card,xm,lx,startime,endtime,startlen,endlen);
					//���¸���jtable�б�
            		Integer c1 =dftm.getRowCount() ; 
					for(int i=0; i<c1 ;i++) {
						dftm.removeRow(0);
					}
					curxuhao = 0 ;
					for(int i=0 ;i<loglist.size() ;i++ ) {
						FaceLog facelog = loglist.get(i) ;
						curxuhao = curxuhao+1 ;
						Object[] row = new Object[] {((curpage-1)*len+curxuhao)+"",facelog.getXingming(),facelog.getSfz(), facelog.getShijian(), facelog.getChangci(),facelog.getShibieleixing(),"��֤��Ƭ�鿴",facelog.getSfzphoto()+";"+facelog.getRenlianphoto()} ;//,param.getPid()+""
						dftm.addRow(row);
					}
					
					turnlabel.setText("��ǰ��"+curpage+"ҳ����"+totalpage+"ҳ");
				}
			});
			
			prebutton = new JButton();//��һҳ
			final GridBagConstraints gridBagConstraints_pre = new GridBagConstraints();
			gridBagConstraints_pre.gridy = 2;
			gridBagConstraints_pre.gridx = 5;
			prebutton.setFont(new Font("", Font.PLAIN, 14));
			prebutton.setText("��һҳ");
			add(prebutton, gridBagConstraints_pre);
			prebutton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					
					String cc = bu.getStr(condition1.getSelectedItem()+"") ;//����
	            	String card = bu.getStr(idcard.getText());//����֤
	            	String xm = bu.getStr(xingming.getText());//����
	            	String lx = bu.getStr(condition2.getSelectedItem()+"") ;//��֤����
	            	String startime = bu.getStr(startDate.getText());//ʱ����ʼ
	            	String endtime = bu.getStr(endDate.getText()) ; //ʱ�����
					//��ȡ������
        			allcount = impl.getRenZhengCount(cc,card,xm,lx,startime,endtime);
        			curpage = curpage==1?curpage : curpage-1 ;
        			Integer startlen = pageutil.getStartindex(allcount, len, curpage) ;
        			Integer endlen = pageutil.getCurrpageSize(allcount, len, curpage) ;//��ǰҳ������
        			totalpage = pageutil.getTotalPage(allcount, len) ;
        			
            		List<FaceLog> loglist = impl.getRenZhengList(cc,card,xm,lx,startime,endtime,startlen,endlen);
					//���¸���jtable�б�
            		Integer c1 =dftm.getRowCount() ; 
					for(int i=0; i<c1 ;i++) {
						dftm.removeRow(0);
					}
					curxuhao = 0 ;
					for(int i=0 ;i<loglist.size() ;i++ ) {
						FaceLog facelog = loglist.get(i) ;
						curxuhao = curxuhao+1 ;
						Object[] row = new Object[] {((curpage-1)*len+curxuhao)+"",facelog.getXingming(),facelog.getSfz(), facelog.getShijian(), facelog.getChangci(),facelog.getShibieleixing(),"��֤��Ƭ�鿴",facelog.getSfzphoto()+";"+facelog.getRenlianphoto()} ;//,param.getPid()+""
						dftm.addRow(row);
					}
					
					turnlabel.setText("��ǰ��"+curpage+"ҳ����"+totalpage+"ҳ");
				}
			});
			
			nextbutton = new JButton();//��һҳ
			final GridBagConstraints gridBagConstraints_next = new GridBagConstraints();
			gridBagConstraints_next.gridy = 2;
			gridBagConstraints_next.gridx = 6;
			nextbutton.setFont(new Font("", Font.PLAIN, 14));
			nextbutton.setText("��һҳ");
			add(nextbutton, gridBagConstraints_next);
			nextbutton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					
					String cc = bu.getStr(condition1.getSelectedItem()+"") ;//����
	            	String card = bu.getStr(idcard.getText());//����֤
	            	String xm = bu.getStr(xingming.getText());//����
	            	String lx = bu.getStr(condition2.getSelectedItem()+"") ;//��֤����
	            	String startime = bu.getStr(startDate.getText());//ʱ����ʼ
	            	String endtime = bu.getStr(endDate.getText()) ; //ʱ�����
					//��ȡ������
        			allcount = impl.getRenZhengCount(cc,card,xm,lx,startime,endtime);
        			totalpage = pageutil.getTotalPage(allcount, len) ;
        			curpage = curpage==totalpage?totalpage : curpage+1 ;
        			Integer startlen = pageutil.getStartindex(allcount, len, curpage) ;
        			Integer endlen = pageutil.getCurrpageSize(allcount, len, curpage) ;//��ǰҳ������
        			
            		List<FaceLog> loglist = impl.getRenZhengList(cc,card,xm,lx,startime,endtime,startlen,endlen);
					//���¸���jtable�б�
            		Integer c1 =dftm.getRowCount() ; 
					for(int i=0; i<c1 ;i++) {
						dftm.removeRow(0);
					}
					curxuhao = 0 ;
					for(int i=0 ;i<loglist.size() ;i++ ) {
						FaceLog facelog = loglist.get(i) ;
						curxuhao = curxuhao+1 ;
						Object[] row = new Object[] {((curpage-1)*len+curxuhao)+"",facelog.getXingming(),facelog.getSfz(), facelog.getShijian(), facelog.getChangci(),facelog.getShibieleixing(),"��֤��Ƭ�鿴",facelog.getSfzphoto()+";"+facelog.getRenlianphoto()} ;//,param.getPid()+""
						dftm.addRow(row);
					}
					
					turnlabel.setText("��ǰ��"+curpage+"ҳ����"+totalpage+"ҳ");
				}
			});
			
			lastbutton = new JButton();//ĩҳ
			final GridBagConstraints gridBagConstraints_last = new GridBagConstraints();
			gridBagConstraints_last.gridy = 2;
			gridBagConstraints_last.gridx = 7;
			lastbutton.setFont(new Font("", Font.PLAIN, 14));
			lastbutton.setText("ĩҳ");
			add(lastbutton, gridBagConstraints_last);
			lastbutton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					
					String cc = bu.getStr(condition1.getSelectedItem()+"") ;//����
	            	String card = bu.getStr(idcard.getText());//����֤
	            	String xm = bu.getStr(xingming.getText());//����
	            	String lx = bu.getStr(condition2.getSelectedItem()+"") ;//��֤����
	            	String startime = bu.getStr(startDate.getText());//ʱ����ʼ
	            	String endtime = bu.getStr(endDate.getText()) ; //ʱ�����
	            	
        			allcount = impl.getRenZhengCount(cc,card,xm,lx,startime,endtime);//������
        			totalpage = pageutil.getTotalPage(allcount, len);//��ҳ��/ĩҳ
        			curpage = totalpage ;
        			Integer startlen = pageutil.getStartindex(allcount, len, curpage) ;//��ʼ��
        			Integer endlen = pageutil.getCurrpageSize(allcount, len, curpage) ;//��ǰҳ������
        			
            		List<FaceLog> loglist = impl.getRenZhengList(cc,card,xm,lx,startime,endtime,startlen,endlen);
					//���¸���jtable�б�
            		Integer c1 =dftm.getRowCount() ; 
					for(int i=0; i<c1 ;i++) {
						dftm.removeRow(0);
					}
					curxuhao = 0 ;
					for(int i=0 ;i<loglist.size() ;i++ ) {
						FaceLog facelog = loglist.get(i) ;
						curxuhao = curxuhao+1 ;
						Object[] row = new Object[] {((curpage-1)*len+curxuhao)+"",facelog.getXingming(),facelog.getSfz(), facelog.getShijian(), facelog.getChangci(),facelog.getShibieleixing(),"��֤��Ƭ�鿴",facelog.getSfzphoto()+";"+facelog.getRenlianphoto()} ;//,param.getPid()+""
						dftm.addRow(row);
					}
					turnlabel.setText("��ǰ��"+curpage+"ҳ����"+totalpage+"ҳ");
				}
			});
			
			turnlabel = new JLabel();
			turnlabel.setFont(new Font("", Font.PLAIN, 14));
			turnlabel.setText("��ǰ��"+curpage+"ҳ����"+totalpage+"ҳ");
			final GridBagConstraints gridBagConstraints_turn = new GridBagConstraints();
			gridBagConstraints_turn.gridy = 2;
			gridBagConstraints_turn.gridx = 8;
			add(turnlabel, gridBagConstraints_turn);
			
			
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
	}
	
	
	class PhotoPanel extends JPanel{
		public PhotoPanel(String[] objurl) {
		//	System.out.println("objurl------"+objurl[0]+"--------"+objurl[1]);
			try {
				
				JLabel labels = new JLabel();//����֤��Ƭ
				add(labels);
				labels.setBounds(0, 0, 100, 100);//��������֤��Ƭ
				
				objurl[0] = objurl[0].replaceAll("\\\\", "//");
				File file = new File(objurl[0]);//����֤ͼ����Ƭ��ַ
				Image image = null;
				try {
					image = ImageIO.read(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
				ImageIcon imageicon=new ImageIcon(image);
				imageicon.setImage(imageicon.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT));//�̶���Ƭ��С
				labels.setIcon(imageicon);
				
				JLabel labelx = new JLabel();//�ֳ�������Ƭ
				add(labelx);
				labelx.setBounds(0, 0, 100,100);
				
				objurl[1] = objurl[1].replaceAll("\\\\", "//");
				ImageIcon imageiconx=new ImageIcon(objurl[1]);
				imageiconx.setImage(imageiconx.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT));//�̶���Ƭ��С
				labelx.setIcon(imageiconx);
				
				
				setVisible(true);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
}