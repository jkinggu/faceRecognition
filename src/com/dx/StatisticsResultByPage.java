package com.dx;

import java.awt.Dimension;
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
 * @Date 2019��4��29��
 *
 * ��Ŀ�� FaceRecongnition
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
	//private TbUserlist user;
	private JButton button;
	private JTextField startDate;
	private DefaultTableModel dftm;
	private BaseUtil bu = new BaseUtil();
	private ZkzInterface zkzInterface = new ZkzInterImpl();
	private ParamSetupInterface paramface = new ParamSetupImpl();
	
	//��ҳ
	// ��ҳ|^
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
			Border titleBorder1 = BorderFactory.createTitledBorder("��֤���ͳ��");
	        setBorder(titleBorder1);
			GridBagLayout gridBagLayout = new GridBagLayout();
			//gridBagLayout.columnWidths = new int[]{136, 97, 59, 0, 0, 0, 0, 0, 51, 53, 60, 56};
			gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0,1.0, 1.0, 1.0, 1.0, 1.0};
			setLayout(gridBagLayout);
			setBounds(100, 100, 699, 331);

//			����
			label1 = new JLabel();
			label1.setFont(new Font("", Font.PLAIN, 14));
			label1.setText(" ��ѡ��    ���У�");
			final GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridy = 0;
			gridBagConstraints.gridx = 0;
			add(label1, gridBagConstraints);
			
//			�����ݿ��ȡ�����б�
			condition1 = new JComboBox();
			String[] dishistr = paramface.getDiShi();//�����ݿ��ȡ�����б�
			condition1.setModel(new DefaultComboBoxModel(dishistr));
			condition1.setFont(new Font("", Font.PLAIN, 14));
			condition1.setPreferredSize(new Dimension(130, 30));
			final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
			gridBagConstraints_1.gridy = 0;
			gridBagConstraints_1.gridx = 1;
			add(condition1, gridBagConstraints_1);
			
//			����
			label2 = new JLabel();
			label2.setFont(new Font("", Font.PLAIN, 14));
			label2.setText(" ���㣺");
			final GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.gridx = 2;
			add(label2, gridBagConstraints2);
	
			
			//�����ݿ��ȡ���������б�
			condition2 = new JComboBox();
			String[] kaodian = new String[] {};
			condition2.setModel(new DefaultComboBoxModel(kaodian));
			condition2.setFont(new Font("", Font.PLAIN, 14));
			condition2.setPreferredSize(new Dimension(200, 30));
			final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
			gridBagConstraints_2.gridy = 0;
			gridBagConstraints_2.gridx = 3;
			add(condition2, gridBagConstraints_2);
			
//			����
			label3 = new JLabel();
			label3.setFont(new Font("", Font.PLAIN, 14));
			label3.setText(" ������");
			final GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridy = 0;
			gridBagConstraints3.gridx = 4;
			add(label3, gridBagConstraints3);
			
			condition3 = new JComboBox();
			String[] kaochang = new String[] {}; //getKaoChang();//�����ݿ��ȡ���������б�
			condition3.setModel(new DefaultComboBoxModel(kaochang));
			condition3.setFont(new Font("", Font.PLAIN, 14));
			condition3.setPreferredSize(new Dimension(200, 30));
			final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
			gridBagConstraints_3.gridy = 0;
			gridBagConstraints_3.gridx = 5;
			add(condition3, gridBagConstraints_3);
			
			//��֤��ʼʱ��
			label4 = new JLabel();
			label4.setFont(new Font("", Font.PLAIN, 14));
			label4.setText(" ʱ�䣺");
			final GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridy = 0;
			gridBagConstraints4.gridx = 6;
			add(label4, gridBagConstraints4);
	
			//ʱ���Ϊ��д����
			startDate = new JTextField();
			final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();
			startDate.setFont(new Font("", Font.PLAIN, 14));
			startDate.setPreferredSize(new Dimension(180, 30));
			gridBagConstraints_4.gridy = 0;
			gridBagConstraints_4.gridx = 7;
			DateChooser dateChooser1 = DateChooser.getInstance("yyyy-MM-dd");
			dateChooser1.register(startDate);
			add(startDate, gridBagConstraints_4);
			
			//��ѯ��ť
			button = new JButton();
			final GridBagConstraints gridBagConstraints_5 = new GridBagConstraints();
			gridBagConstraints_5.gridy = 0;
			gridBagConstraints_5.gridx = 8;
			button.setFont(new Font("", Font.PLAIN, 14));
			button.setText("��ѯ");
			add(button, gridBagConstraints_5);
			
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
//	            	����
//	            	String shi = bu.getStr(condition1.getSelectedItem()+"") ;
//	            	����
	            	String kaodian = bu.getStr(condition2.getSelectedItem()+"") ;
//	            	����
	            	String changci = bu.getStr(condition3.getSelectedItem() +"") ;
//	            	����
	            	String startime = bu.getStr(startDate.getText());//ѡ������
	            	
//	            	�ж����в���Ϊ��
	               //List<String> list = zkzInterface.getCountByCondition( kaodian, changci, startime);
	                 pageResult=zkzInterface.getCountByPageCondition(kaodian, changci, startime, Const.FIRSTPAGE);
	            	//���¸���jtable�б�
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
					turnlabel.setText("��ǰ��" + pageResult.getCurrentPage() + "ҳ����" + pageResult.getTotalPage() + "ҳ"
							+ "����" + pageResult.getTotalCount() + "��");										
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
			            
			String[] tableHeads = new String[]{"����","����","����"," /ͨ��","ְҵ����/��ͨ��", " /δ��֤"," /ͨ��", "ְҵ����/��ͨ��"," /δ��֤", " /ͨ��","��λ����/��ͨ��"," /δ��֤"};//,"id����������"
//			String[] tableHeads = new String[]{"","","","","ְҵ����", "","", "ְҵ����","", "","ְҵ����",""};//,"id����������"
//			String[] tableHeads2 = new String[]{"����","����","����","ͨ��","��ͨ��", "δ��֤","ͨ��", "��ͨ��","δ��֤", "ͨ��","��ͨ��","δ��֤"};
			dftm.setColumnIdentifiers(tableHeads);
//			dftm.setColumnIdentifiers(tableHeads2);
			DefaultTableCellRenderer render = new DefaultTableCellRenderer() ;
			render.setHorizontalAlignment(SwingConstants.CENTER);//���õ�Ԫ����Ⱦ��ʽ
			render.setFont(new Font("Dialog", Font.BOLD, 14));
//			table.setDefaultRenderer(Object.class, render);
			//�����table�з�����//��ѯ��ʼ����
			
			pageResult=zkzInterface.getCountByPageCondition("","","",Const.FIRSTPAGE);
			List<StatisticsResult> list=pageResult.getResult();
			for(StatisticsResult sResult:list) {
				String[] row=new String[] {sResult.getStatisticsCon().getDate(),sResult.getStatisticsCon().getLocation(),sResult.getStatisticsCon().getPlace(),
						         sResult.getMoralPass()+"",sResult.getMoralFail()+"",sResult.getMoralUnauthorized()+"",sResult.getAbilityPass()+"",sResult.getAbilityFail()+"",
						         sResult.getAbilityUnauthorized()+"",sResult.getSkillsPass()+"",sResult.getSkillsFail()+"",sResult.getSkillsUnauthorized()+""};
				dftm.addRow(row);
			}		
			
			table = new MutilHeadTable(dftm);// new MutilHeadTable(dftm);//�������ṩ��Ԫ��ϲ���  //new JTable(dftm);
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
			
			table.setFont(new Font("", Font.PLAIN, 14));
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
//			TableColumn column0 = table.getColumn("ͨ��");
//			column0.setPreferredWidth(80);
//			column0.setCellRenderer(render);
//			TableColumn column2 = table.getColumn("δ��֤");
//			column2.setPreferredWidth(80);
//			column2.setCellRenderer(render);
//			TableColumn column1 = table.getColumn("ְҵ����/��ͨ��");
//			column1.setPreferredWidth(80);
//			column1.setCellRenderer(render);
//			TableColumn column = table.getColumn("ְҵ����/��ͨ��");
//			column.setPreferredWidth(80);
//			column.setCellRenderer(render);
//			TableColumn column3 = table.getColumn("��λ����/��ͨ��");
//			column3.setPreferredWidth(80);
//			column3.setCellRenderer(render);
//			TableColumn column4 = table.getColumn("����");
//			column4.setPreferredWidth(180);
//			column4.setCellRenderer(render);
//			TableColumn column5 = table.getColumn("����");
//			column5.setPreferredWidth(250);
//			column5.setCellRenderer(render);
//			TableColumn column6 = table.getColumn("����");
//			column6.setPreferredWidth(100);
//			column6.setCellRenderer(render);
			
			scrollPane.setViewportView(table);
			// ��ҳ
			final GridBagConstraints gridBagConstraints_first = new GridBagConstraints();
			gridBagConstraints_first.gridy =2;
			gridBagConstraints_first.gridx =2;
			firstbutton.setFont(new Font("", Font.PLAIN, 14));
			firstbutton.setText("��ҳ");
			add(firstbutton, gridBagConstraints_first);
			firstbutton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
//	            	����
//	            	String shi = bu.getStr(condition1.getSelectedItem()+"") ;
//	            	����
	            	String kaodian = bu.getStr(condition2.getSelectedItem()+"") ;
//	            	����
	            	String changci = bu.getStr(condition3.getSelectedItem() +"") ;
//	            	����
	            	String startime = bu.getStr(startDate.getText());//ѡ������
	            	
//	            	�ж����в���Ϊ��
	               //List<String> list = zkzInterface.getCountByCondition( kaodian, changci, startime);
	                 pageResult=zkzInterface.getCountByPageCondition(kaodian, changci, startime, Const.FIRSTPAGE);
	            	//���¸���jtable�б�
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
					turnlabel.setText("��ǰ��" + pageResult.getCurrentPage() + "ҳ����" + pageResult.getTotalPage() + "ҳ"
							+ "����" + pageResult.getTotalCount() + "��");										
	            }
	        });
			
			//��һҳ
			final GridBagConstraints gridBagConstraints_previous = new GridBagConstraints();
			gridBagConstraints_previous.gridy =2;
			gridBagConstraints_previous.gridx =3;
		    prebutton.setFont(new Font("", Font.PLAIN, 14));
		    prebutton.setText("��һҳ");
			add(prebutton, gridBagConstraints_previous);
			
			prebutton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
//	            	����
//	            	String shi = bu.getStr(condition1.getSelectedItem()+"") ;
//	            	����
	            	String kaodian = bu.getStr(condition2.getSelectedItem()+"") ;
//	            	����
	            	String changci = bu.getStr(condition3.getSelectedItem() +"") ;
//	            	����
	            	String startime = bu.getStr(startDate.getText());//ѡ������
	            	
//	            	�ж����в���Ϊ��
	               //List<String> list = zkzInterface.getCountByCondition( kaodian, changci, startime);
	                 pageResult=zkzInterface.getCountByPageCondition(kaodian, changci, startime, pageResult.getPrevious());
	            	//���¸���jtable�б�
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
					turnlabel.setText("��ǰ��" + pageResult.getCurrentPage() + "ҳ����" + pageResult.getTotalPage() + "ҳ"
							+ "����" + pageResult.getTotalCount() + "��");										
	            }
	        });
			
			//��һҳ
			final GridBagConstraints gridBagConstraints_next = new GridBagConstraints();
			gridBagConstraints_next.gridy =2;
			gridBagConstraints_next.gridx =4;
		    nextbutton.setFont(new Font("", Font.PLAIN, 14));
		    nextbutton.setText("��һҳ");
			add( nextbutton, gridBagConstraints_next);
			nextbutton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
//	            	����
//	            	String shi = bu.getStr(condition1.getSelectedItem()+"") ;
//	            	����
	            	String kaodian = bu.getStr(condition2.getSelectedItem()+"") ;
//	            	����
	            	String changci = bu.getStr(condition3.getSelectedItem() +"") ;
//	            	����
	            	String startime = bu.getStr(startDate.getText());//ѡ������
	            	
//	            	�ж����в���Ϊ��
	               //List<String> list = zkzInterface.getCountByCondition( kaodian, changci, startime);
	                 pageResult=zkzInterface.getCountByPageCondition(kaodian, changci, startime, pageResult.getNext());
	            	//���¸���jtable�б�
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
					turnlabel.setText("��ǰ��" + pageResult.getCurrentPage() + "ҳ����" + pageResult.getTotalPage() + "ҳ"
							+ "����" + pageResult.getTotalCount() + "��");										
	            }
	        });
			
			//ĩҳ
			final GridBagConstraints gridBagConstraints_total = new GridBagConstraints();
			gridBagConstraints_total.gridy =2;
			gridBagConstraints_total.gridx =5;
		    lastbutton.setFont(new Font("", Font.PLAIN, 14));
		    lastbutton.setText("ĩҳ");
			add(lastbutton, gridBagConstraints_total);
			lastbutton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
//	            	����
//	            	String shi = bu.getStr(condition1.getSelectedItem()+"") ;
//	            	����
	            	String kaodian = bu.getStr(condition2.getSelectedItem()+"") ;
//	            	����
	            	String changci = bu.getStr(condition3.getSelectedItem() +"") ;
//	            	����
	            	String startime = bu.getStr(startDate.getText());//ѡ������
	            	
//	            	�ж����в���Ϊ��
	               //List<String> list = zkzInterface.getCountByCondition( kaodian, changci, startime);
	                 pageResult=zkzInterface.getCountByPageCondition(kaodian, changci, startime, pageResult.getTotalPage());
	            	//���¸���jtable�б�
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
					turnlabel.setText("��ǰ��" + pageResult.getCurrentPage() + "ҳ����" + pageResult.getTotalPage() + "ҳ"
							+ "����" + pageResult.getTotalCount() + "��");										
	            }
	        });
			
			//����turnlable
			turnlabel = new JLabel();
			turnlabel.setFont(new Font("", Font.PLAIN, 14));
			turnlabel.setText("��ǰ��" + pageResult.getCurrentPage() + "ҳ����" + pageResult.getTotalPage() + "ҳ"
					+ "����" + pageResult.getTotalCount() + "��");
			final GridBagConstraints gridBagConstraints_turn = new GridBagConstraints();
			gridBagConstraints_turn.gridy = 2;
			gridBagConstraints_turn.gridx = 6;
			add(turnlabel, gridBagConstraints_turn);
			
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	
	
	
	

}