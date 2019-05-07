package com.dx;

import java.awt.Dimension;
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

/**
 * @author fang
 *
 * @Date 2019��4��28��
 *
 *       ��Ŀ�� FaceRecongnition
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
	// private TbUserlist user;
	private JButton button=new JButton();
	private DefaultTableModel dftm;
	private BaseUtil bu = new BaseUtil();
	private Integer allcount = 0;
	private FaceLogsInterface facelog = new FaceLogsImpl();

	// ��ҳ|^
	private JButton firstbutton=new JButton();
	private JButton prebutton=new JButton();
	private JButton nextbutton=new JButton();
	private JButton lastbutton=new JButton();
	private JLabel turnlabel;

	private PageResult pageResult;
	private Integer currentPage = Const.FIRSTPAGE;

	public FaceLogsResultByPage() {
		super();
		try {
			Border titleBorder1 = BorderFactory.createTitledBorder("��֤�����ѯ");
			setBorder(titleBorder1);
			GridBagLayout gridBagLayout = new GridBagLayout();
			gridBagLayout.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0 };
			setLayout(gridBagLayout);
			setBounds(100, 100, 601, 331);

			// ����
			label3 = new JLabel();
			label3.setFont(new Font("", Font.PLAIN, 14));
			label3.setText("��ѡ��   ���Σ�");
			final GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridy = 0;
			gridBagConstraints3.gridx = 0;
			add(label3, gridBagConstraints3);

			// ����ѡ��
			condition3 = new JComboBox();
			String[] kaochang = new String[] { "ְҵ����", "ְҵ����", "��λ����" }; // getKaoChang();//�����ݿ��ȡ���������б�
			condition3.setModel(new DefaultComboBoxModel(kaochang));
			condition3.setFont(new Font("", Font.PLAIN, 14));
			condition3.setPreferredSize(new Dimension(110, 30));
			final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
			gridBagConstraints_3.gridy = 0;
			gridBagConstraints_3.gridx = 1;
			add(condition3, gridBagConstraints_3);

			// ��֤���
			label1 = new JLabel();
			label1.setFont(new Font("", Font.PLAIN, 14));
			label1.setText(" ��֤�����");
			final GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridy = 0;
			gridBagConstraints.gridx = 2;
			add(label1, gridBagConstraints);

			// ��֤���ѡ��
			condition1 = new JComboBox();
			String[] dishistr = new String[] { "��ѡ��", "ͨ��", "��ͨ��", "δ��֤" };// �����ݿ��ȡ�����б�
			condition1.setModel(new DefaultComboBoxModel(dishistr));
			condition1.setFont(new Font("", Font.PLAIN, 14));
			condition1.setPreferredSize(new Dimension(80, 30));
			final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
			gridBagConstraints_1.gridy = 0;
			gridBagConstraints_1.gridx = 3;
			add(condition1, gridBagConstraints_1);

			// ����
			label2 = new JLabel();
			label2.setFont(new Font("", Font.PLAIN, 14));
			label2.setText(" ������");
			final GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.gridx = 4;
			add(label2, gridBagConstraints2);

			// ����ѡ��
			condition2 = new JComboBox();// �����ݿ��ȡ���������б�
			String[] kaodian = facelog.getRenzCount();
			condition2.setModel(new DefaultComboBoxModel(kaodian));
			condition2.setFont(new Font("", Font.PLAIN, 14));
			condition2.setPreferredSize(new Dimension(80, 30));
			final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
			gridBagConstraints_2.gridy = 0;
			gridBagConstraints_2.gridx = 5;
			add(condition2, gridBagConstraints_2);

			// ��֤��ʼʱ��
			label4 = new JLabel();
			label4.setFont(new Font("", Font.PLAIN, 14));
			label4.setText(" ��֤ʱ�䣺");
			final GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridy = 0;
			gridBagConstraints4.gridx = 6;
			add(label4, gridBagConstraints4);

			// ʱ���Ϊ��д����
			startDate = new JTextField();
			final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();
			startDate.setFont(new Font("", Font.PLAIN, 14));
			startDate.setPreferredSize(new Dimension(180, 30));
			gridBagConstraints_4.gridy = 0;
			gridBagConstraints_4.gridx = 7;
			DateChooser dateChooser1 = DateChooser.getInstance("yyyy-MM-dd HH:mm:ss");
			dateChooser1.register(startDate);
			add(startDate, gridBagConstraints_4);

			// ��֤����ʱ��
			label5 = new JLabel();
			label5.setFont(new Font("", Font.PLAIN, 14));
			label5.setText("��");
			final GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.ipadx = 20;
			gridBagConstraints5.gridy = 0;
			gridBagConstraints5.gridx = 8;
			add(label5, gridBagConstraints5);

			// ��֤����ʱ��ѡ��
			endDate = new JTextField();
			final GridBagConstraints gridBagConstraints_5 = new GridBagConstraints();
			endDate.setFont(new Font("", Font.PLAIN, 14));
			endDate.setPreferredSize(new Dimension(180, 30));
			gridBagConstraints_5.gridy = 0;
			gridBagConstraints_5.gridx = 9;
			DateChooser dateChooser2 = DateChooser.getInstance("yyyy-MM-dd HH:mm:ss");
			dateChooser2.register(endDate);
			add(endDate, gridBagConstraints_5);

			// ��ѯ��ť
			button = new JButton();
			final GridBagConstraints gridBagConstraints_7 = new GridBagConstraints();
			gridBagConstraints_7.gridy = 0;
			gridBagConstraints_7.gridx = 10;
			button.setFont(new Font("", Font.PLAIN, 14));
			button.setText("��ѯ");
			add(button, gridBagConstraints_7);

			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// ��֤���
					String rzresult = bu.getStr(condition1.getSelectedItem() + "");
					// ��֤����
					String rzcount = bu.getStr(condition2.getSelectedItem() + "");
					// ����
					String changci = bu.getStr(condition3.getSelectedItem() + "");
					// ��ʼʱ��
					String startime = bu.getStr(startDate.getText());// ��ȡ��ʼʱ��
					// ����ʱ��
					String endtime = bu.getStr(endDate.getText());// ��ȡ����ʱ��
					// System.out.println(dishi+"--"+kaodian+"--"+kaochang+"--"+startime+"--"+endtime);
					// �жϿ�������
					if (changci.equals("")) {
						JOptionPane.showMessageDialog(null, "��ѡ�񳡴�", "���ӿ�������", JOptionPane.WARNING_MESSAGE);
						return;
					} else {
						pageResult = facelog.getFaceLogsByPageCondition(rzresult, changci, rzcount, startime, endtime,
								currentPage);
						// ���¸���jtable�б�
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
						turnlabel.setText("��ǰ��" + pageResult.getCurrentPage() + "ҳ����" + pageResult.getTotalPage() + "ҳ"
								+ "����" + pageResult.getTotalCount() + "��");
					}
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

			// String[] tableHeads = new String[]{"ѡ��","���", "����", "�ص�","����", "����",
			// "��ʼʱ��","����ʱ��"};//,"id����������"
			String[] tableHeads = new String[] { "���", "����", "����֤��", "׼��֤��", "�ص�", "����", "����" };// ,"id����������"
			dftm.setColumnIdentifiers(tableHeads);
			DefaultTableCellRenderer render = new DefaultTableCellRenderer();
			render.setHorizontalAlignment(SwingConstants.CENTER);// ���õ�Ԫ����Ⱦ��ʽ
			
			// �����table�з�����//��ѯ��ʼ����            
			pageResult=facelog.getFaceLogsByPageCondition(null,null, null, null, null, Const.FIRSTPAGE);
			
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
			table.setFont(new Font("", Font.PLAIN, 14));
			table.setRowHeight(30);
			table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getWidth(), 30));
			table.getTableHeader().setFont(new Font("", Font.PLAIN, 14));

			TableColumn column0 = table.getColumn("���");
			column0.setPreferredWidth(10);
			column0.setCellRenderer(render);
			TableColumn column1 = table.getColumn("����");
			column1.setPreferredWidth(50);
			column1.setCellRenderer(render);
			TableColumn column2 = table.getColumn("����֤��");
			column2.setPreferredWidth(250);
			column2.setCellRenderer(render);
			TableColumn column = table.getColumn("׼��֤��");
			column.setPreferredWidth(50);
			column.setCellRenderer(render);
			TableColumn column3 = table.getColumn("�ص�");
			column3.setPreferredWidth(250);
			column3.setCellRenderer(render);
			TableColumn column4 = table.getColumn("����");
			column4.setPreferredWidth(200);
			column4.setCellRenderer(render);
			TableColumn column5 = table.getColumn("����");
			column5.setPreferredWidth(100);
			column5.setCellRenderer(render);
			scrollPane.setViewportView(table);

			// ��ҳ
			firstbutton = new JButton();
			final GridBagConstraints gridBagConstraints_first = new GridBagConstraints();
			gridBagConstraints_first.gridy = 2;
			gridBagConstraints_first.gridx = 4;
			firstbutton.setFont(new Font("", Font.PLAIN, 14));
			firstbutton.setText("��ҳ");
			add(firstbutton, gridBagConstraints_first);
			firstbutton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// ��֤���
					String rzresult = bu.getStr(condition1.getSelectedItem() + "");
					// ��֤����
					String rzcount = bu.getStr(condition2.getSelectedItem() + "");
					// ����
					String changci = bu.getStr(condition3.getSelectedItem() + "");
					// ��ʼʱ��
					String startime = bu.getStr(startDate.getText());// ��ȡ��ʼʱ��
					// ����ʱ��
					String endtime = bu.getStr(endDate.getText());// ��ȡ����ʱ��
					// System.out.println(dishi+"--"+kaodian+"--"+kaochang+"--"+startime+"--"+endtime);
					// �жϿ�������
					if (changci.equals("")) {
						JOptionPane.showMessageDialog(null, "��ѡ�񳡴�", "���ӿ�������", JOptionPane.WARNING_MESSAGE);
						return;
					} else {
						pageResult = facelog.getFaceLogsByPageCondition(rzresult, changci, rzcount, startime, endtime,
								Const.FIRSTPAGE);
						// ���¸���jtable�б�
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
						turnlabel.setText("��ǰ��" + pageResult.getCurrentPage() + "ҳ����" + pageResult.getTotalPage() + "ҳ"
								+ "����" + pageResult.getTotalCount() + "��");
					}
				}
			});
			
			//��һҳ
			final GridBagConstraints gridBagConstraints_pre = new GridBagConstraints();
			gridBagConstraints_pre.gridy = 2;
			gridBagConstraints_pre.gridx = 5;
			prebutton.setFont(new Font("", Font.PLAIN, 14));
			prebutton.setText("��һҳ");
			add(prebutton, gridBagConstraints_pre);
			prebutton.addActionListener(new ActionListener() {				
				@Override
				public void actionPerformed(ActionEvent e) {
					// ��֤���
					String rzresult = bu.getStr(condition1.getSelectedItem() + "");
					// ��֤����
					String rzcount = bu.getStr(condition2.getSelectedItem() + "");
					// ����
					String changci = bu.getStr(condition3.getSelectedItem() + "");
					// ��ʼʱ��
					String startime = bu.getStr(startDate.getText());// ��ȡ��ʼʱ��
					// ����ʱ��
					String endtime = bu.getStr(endDate.getText());// ��ȡ����ʱ��
					// System.out.println(dishi+"--"+kaodian+"--"+kaochang+"--"+startime+"--"+endtime);
					// �жϿ�������
					if (changci.equals("")) {
						JOptionPane.showMessageDialog(null, "��ѡ�񳡴�", "���ӿ�������", JOptionPane.WARNING_MESSAGE);
						return;
					} else {
						pageResult = facelog.getFaceLogsByPageCondition(rzresult, changci, rzcount, startime, endtime,
								pageResult.getPrevious());
						// ���¸���jtable�б�
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
						turnlabel.setText("��ǰ��" + pageResult.getCurrentPage() + "ҳ����" + pageResult.getTotalPage() + "ҳ"
								+ "����" + pageResult.getTotalCount() + "��");
					}
									
				}
			});
			
			//��һҳ
			final GridBagConstraints gridBagConstraints_next = new GridBagConstraints();
			gridBagConstraints_next.gridy = 2;
			gridBagConstraints_next.gridx = 6;
			nextbutton.setFont(new Font("", Font.PLAIN, 14));
			nextbutton.setText("��һҳ");
			add(nextbutton, gridBagConstraints_next);
			nextbutton.addActionListener(new ActionListener() {				
				@Override
				public void actionPerformed(ActionEvent e) {
					// ��֤���
					String rzresult = bu.getStr(condition1.getSelectedItem() + "");
					// ��֤����
					String rzcount = bu.getStr(condition2.getSelectedItem() + "");
					// ����
					String changci = bu.getStr(condition3.getSelectedItem() + "");
					// ��ʼʱ��
					String startime = bu.getStr(startDate.getText());// ��ȡ��ʼʱ��
					// ����ʱ��
					String endtime = bu.getStr(endDate.getText());// ��ȡ����ʱ��
					// System.out.println(dishi+"--"+kaodian+"--"+kaochang+"--"+startime+"--"+endtime);
					// �жϿ�������
					if (changci.equals("")) {
						JOptionPane.showMessageDialog(null, "��ѡ�񳡴�", "���ӿ�������", JOptionPane.WARNING_MESSAGE);
						return;
					} else {
						pageResult = facelog.getFaceLogsByPageCondition(rzresult, changci, rzcount, startime, endtime,
								pageResult.getNext());
						// ���¸���jtable�б�
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
						turnlabel.setText("��ǰ��" + pageResult.getCurrentPage() + "ҳ����" + pageResult.getTotalPage() + "ҳ"
								+ "����" + pageResult.getTotalCount() + "��");
					}														
				}
			});		
			
			
			
			//ĩҳ
			final GridBagConstraints gridBagConstraints_last = new GridBagConstraints();
			gridBagConstraints_last.gridy = 2;
			gridBagConstraints_last.gridx = 7;
			lastbutton.setFont(new Font("", Font.PLAIN, 14));
			lastbutton.setText("ĩҳ");
			add(lastbutton, gridBagConstraints_last);
			lastbutton.addActionListener(new ActionListener() {				
				@Override
				public void actionPerformed(ActionEvent e) {
					// ��֤���
					String rzresult = bu.getStr(condition1.getSelectedItem() + "");
					// ��֤����
					String rzcount = bu.getStr(condition2.getSelectedItem() + "");
					// ����
					String changci = bu.getStr(condition3.getSelectedItem() + "");
					// ��ʼʱ��
					String startime = bu.getStr(startDate.getText());// ��ȡ��ʼʱ��
					// ����ʱ��
					String endtime = bu.getStr(endDate.getText());// ��ȡ����ʱ��
					// System.out.println(dishi+"--"+kaodian+"--"+kaochang+"--"+startime+"--"+endtime);
					// �жϿ�������
					if (changci.equals("")) {
						JOptionPane.showMessageDialog(null, "��ѡ�񳡴�", "���ӿ�������", JOptionPane.WARNING_MESSAGE);
						return;
					} else {
						pageResult = facelog.getFaceLogsByPageCondition(rzresult, changci, rzcount, startime, endtime,
								pageResult.getTotalPage());
						// ���¸���jtable�б�
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
						turnlabel.setText("��ǰ��" + pageResult.getCurrentPage() + "ҳ����" + pageResult.getTotalPage() + "ҳ"
								+ "����" + pageResult.getTotalCount() + "��");
					}																	
				}
			});
			
			//����turnlable
			turnlabel = new JLabel();
			turnlabel.setFont(new Font("", Font.PLAIN, 14));
			turnlabel.setText("��ǰ��" + pageResult.getCurrentPage() + "ҳ����" + pageResult.getTotalPage() + "ҳ"
					+ "����" + pageResult.getTotalCount() + "��");
			final GridBagConstraints gridBagConstraints_turn = new GridBagConstraints();
			gridBagConstraints_turn.gridy = 2;
			gridBagConstraints_turn.gridx = 8;
			add(turnlabel, gridBagConstraints_turn);
			
			
			
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

}