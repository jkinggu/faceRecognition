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

import com.dx.dao.DBHelper;
import com.dx.inter.ParamSetupInterface;
import com.dx.pojo.ParamSetup;
import com.dx.service.ParamSetupImpl;
import com.dx.util.BaseUtil;
import com.dx.util.DateChooser;

import java.awt.Component;
import java.awt.Dimension;
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
 * ��֤��������,���õ��У����㣬������ʱ�䣨Ĭ�ϵ����ѡ��
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
			Border titleBorder1 = BorderFactory.createTitledBorder("��֤��������");
	        setBorder(titleBorder1);
			GridBagLayout gridBagLayout = new GridBagLayout();
			gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0};
			setLayout(gridBagLayout);
			setBounds(100, 100, 601, 331);
	
			label1 = new JLabel();
			label1.setFont(new Font("", Font.PLAIN, 14));
			label1.setText(" ��ѡ��    ���У�");
			final GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridy = 0;
			gridBagConstraints.gridx = 0;
			add(label1, gridBagConstraints);
	
			condition1 = new JComboBox();
			String[] dishistr = paramface.getDiShi();//�����ݿ��ȡ�����б������ӵ�½֮��鿴���ĸ����е�½�ģ�ĳ�����е�½֮��ֻѡ��õ������ڵĵ���
			condition1.setModel(new DefaultComboBoxModel(dishistr));
			condition1.setFont(new Font("", Font.PLAIN, 14));
			condition1.setPreferredSize(new Dimension(130, 30));
			final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
			gridBagConstraints_1.gridy = 0;
			gridBagConstraints_1.gridx = 1;
			add(condition1, gridBagConstraints_1);
			
			label2 = new JLabel();
			label2.setFont(new Font("", Font.PLAIN, 14));
			label2.setText(" ���㣺");
			final GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.gridx = 2;
			add(label2, gridBagConstraints2);
	
			
			condition2 = new JComboBox();
			//�����ݿ��ȡ���������б�
			String[] kaodian = new String[] {};
			condition2.setModel(new DefaultComboBoxModel(kaodian));
			condition2.setFont(new Font("", Font.PLAIN, 14));
			condition2.setPreferredSize(new Dimension(200, 30));
			final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
			gridBagConstraints_2.gridy = 0;
			gridBagConstraints_2.gridx = 3;
			add(condition2, gridBagConstraints_2);
			
			
			label3 = new JLabel();
			label3.setFont(new Font("", Font.PLAIN, 14));
			label3.setText(" ������");
			final GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.gridx = 4;
			add(label3, gridBagConstraints3);
	
			condition3 = new JComboBox();
			String[] kaochang = new String[] {}; //getKaoChang();//�����ݿ��ȡ���������б�
			condition3.setModel(new DefaultComboBoxModel(kaochang));
			condition3.setFont(new Font("", Font.PLAIN, 14));
			condition3.setPreferredSize(new Dimension(200, 30));
			final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
			gridBagConstraints_2.gridy = 0;
			gridBagConstraints_2.gridx = 5;
			add(condition3, gridBagConstraints_3);
			
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
			startDate.setPreferredSize(new Dimension(200, 30));
			gridBagConstraints_4.gridy = 0;
			gridBagConstraints_4.gridx = 7;
			DateChooser dateChooser1 = DateChooser.getInstance("yyyy-MM-dd");
			dateChooser1.register(startDate);
			add(startDate, gridBagConstraints_4);
			
			label5 = new JLabel();
			label5.setFont(new Font("", Font.PLAIN, 14));
			label5.setText("��");
			final GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.ipadx = 20;
			gridBagConstraints5.gridy = 0;
			gridBagConstraints5.gridx = 8;
			add(label5, gridBagConstraints5);
	
			endDate = new JTextField();
			final GridBagConstraints gridBagConstraints_5 = new GridBagConstraints();
			endDate.setFont(new Font("", Font.PLAIN, 14));
			endDate.setPreferredSize(new Dimension(200, 30));
			gridBagConstraints_5.gridy = 0;
			gridBagConstraints_5.gridx = 9;
			DateChooser dateChooser2 = DateChooser.getInstance("yyyy-MM-dd");
			dateChooser2.register(endDate);
			add(endDate, gridBagConstraints_5);
			
			button = new JButton();
			final GridBagConstraints gridBagConstraints_7 = new GridBagConstraints();
			gridBagConstraints_7.gridy = 0;
			gridBagConstraints_7.gridx = 10;
			button.setFont(new Font("", Font.PLAIN, 14));
			button.setText("����");
			add(button, gridBagConstraints_7);
			
			button1 = new JButton();
			final GridBagConstraints gridBagConstraints_6 = new GridBagConstraints();
			gridBagConstraints_6.gridy = 0;
			gridBagConstraints_6.gridx = 11;
			button1.setFont(new Font("", Font.PLAIN, 14));
			button1.setText("�Ƴ�");
			add(button1, gridBagConstraints_6);
			
			
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
			 * ������ӵ����ݿ��Լ��б��·�
			 * 1ֻ���ӵ��У� 1.1���У���ʼʱ�䣬1.2���У�����ʱ�䣬1.3���У���ʼ������ʱ��                                                           �������еĿ��������ӵ����ݿ��Լ�ҳ���б�
			 * 2���У����㣬2.1���У����㣬��ʼʱ�䣬2.2���У����㣬����ʱ�䣬2.3�����У����㣬��ʼ������ʱ��
			 * 3���У����㣬����3.1���У����㣬��������ʼʱ�䣬3.2���У����㣬��������ʱ�䣬3.3���У����㣬��������ʼ������ʱ��
			 * 4���У����㣬��������ʼʱ��
			 * 5���У����������㣬����ʱ��
			 * 6���У����㣬��������ʼʱ�䣬����ʱ��
			 * ���1û��ѡ��2ѡ���ظ�����Ҫ�ж�
			 * */
			button.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	String dishi = bu.getStr(condition1.getSelectedItem()+"") ;
	            	String kaodian = bu.getStr(condition2.getSelectedItem()+"") ;
	            	String kaochang = bu.getStr(condition3.getSelectedItem() +"") ;
	            	String startime = bu.getStr(startDate.getText());//��ȡ��ʼʱ��
	            	String endtime = bu.getStr(endDate.getText()) ;//��ȡ����ʱ��
	            	//System.out.println(dishi+"--"+kaodian+"--"+kaochang+"--"+startime+"--"+endtime);
	            	//�жϿ�������
	            	if(dishi.equals("") && kaodian.equals("") && kaochang.equals("") && startime.equals("") && endtime.equals("")) {
	            		JOptionPane.showMessageDialog(null, "ѡ������Ϊ�գ�" , "���ӿ�������",JOptionPane.WARNING_MESSAGE);
	            		return ;
	            	}else {
	            		List<ParamSetup> list = paramface.getShezhiList(dishi,kaodian,kaochang,startime,endtime,"0",null);//��ѯ�����ӵ�����ʣ������
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
	        				//������ѡ��
	        				checkbox = new JCheckBox(param.getPid()+"");
	        				//��ȡ��ǰcheckbox���ܳ���
	        				newcbox[hobbylen+i] = checkbox ;//��ȡcheckbox����
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
	            	int ckxCol=0;// �����һ����checkbox�� 
	            	String pids = "" ;
	            	for(int r=0;r<rows;r++){// ѭ��������
	            	     Object v = table.getValueAt(r,ckxCol)+""; 
	            	     if(v!=null && v.equals("true") ){//ѡ��
	            	    	 pids = pids + hobby[r].getText().toString()+",";
	            	     }
	            	}
	            	if(pids.equals("")) {
	            		JOptionPane.showMessageDialog(null, "��ѡ����Ҫ�Ƴ����У�" , "�Ƴ���������",JOptionPane.WARNING_MESSAGE);
	            		return ;
	            	}
	            	pids = pids.substring(0,pids.length()-1) ;
	            	//�������ݿ�Ķ�Ӧ����
	            	Integer sise = paramface.updateParamRemove(pids);
	            	//System.out.println("pids----"+pids);
	            	FaceMainFrame frame = (FaceMainFrame) getParent().getParent().getParent().getParent() ;
	            	frame.setStr2(pids);
	            	//System.out.println("daozhelima !!!!"+getParent().getParent().getParent().getParent());//Frame����
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
			            
			String[] tableHeads = new String[]{"ѡ��","���", "����", "�ص�","����", "����", "��ʼʱ��","����ʱ��"};//,"id����������"
			dftm.setColumnIdentifiers(tableHeads);
			DefaultTableCellRenderer render = new DefaultTableCellRenderer() ;
			render.setHorizontalAlignment(SwingConstants.CENTER);//���õ�Ԫ����Ⱦ��ʽ
			//�����table�з�����//��ѯ��ʼ����
			List<ParamSetup> list = paramface.getShezhiList("","","","","","1",null);
			hobby = new JCheckBox[list.size()];
			JCheckBox checkbox ;
			for(int i=0 ;i<list.size() ;i++ ) {
				ParamSetup param = list.get(i) ;
				allcount = allcount+1 ;
				checkbox = new JCheckBox(param.getPid()+"");
				hobby[i] = checkbox ;//��ȡcheckbox����
				//dftm.addRow(new String[] {"",allcount+"",param.getDishiname(),param.getKaodianname(),param.getDidian(),param.getKaochang(),param.getStarttime(),param.getEndtime()});
				Object[] row = new Object[] {"",allcount+"",param.getDishiname(),param.getKaodianname(),param.getDidian(),param.getKaochang(),param.getStarttime(),param.getEndtime()} ;//,param.getPid()+""
				//Object[] row = new Object[] {param.getPid()+"",allcount+"",param.getDishiname(),param.getKaodianname(),param.getDidian(),param.getKaochang(),param.getStarttime(),param.getEndtime(),param.getPid()+""} ;
				dftm.addRow(row);
			}
			
			
			
			table = new JTable(dftm);
			table.setFont(new Font("", Font.PLAIN, 14));
			table.setRowHeight(30);
			table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getWidth(), 30));
			table.getTableHeader().setFont(new Font("", Font.PLAIN, 14));
			//table.setEnabled(false);//table�����Բ���
			//�����������Ϊ�˿���ѡ����У��������Ҫѡ����У���ע�͵������ڴ˴�����Ҫ�������ط����������
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
			
			TableColumn column0 = table.getColumn("ѡ��");
			column0.setCellEditor(new DefaultCellEditor(new JCheckBox()));
			column0.setPreferredWidth(50);
//			column0.setCellRenderer(render);
			column0.setCellRenderer(new TableCellRenderer() {
				@Override
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
						boolean hasFocus, int row, int column) {
					// �������ڷ��ص���Ⱦ���
					JCheckBox ck = new JCheckBox();
					 // ʹ���н�����ж�Ӧ�ĸ�ѡ��ѡ��         
					if(column==0) {
						ck.setSelected(isSelected);
					}
					//���ñ�����ɫ  ����������jcheckbox�ı�����ɫ   ֱ������Ϊ͸����������������һ�������������ɫת�������Ա�����ɫ������һ��                  
					ck.setOpaque(false);
					// ���õ�ѡbox.setSelected(hasFocus);
					// ʹ��ѡ���ڵ�Ԫ���ھ�����ʾ
					ck.setHorizontalAlignment((int) 0.5f);
					return ck;
				}
			});
			
			TableColumn column1 = table.getColumn("���");
			column1.setPreferredWidth(50);
			column1.setCellRenderer(render);
			TableColumn column2 = table.getColumn("����");
			column2.setPreferredWidth(50);
			column2.setCellRenderer(render);
			TableColumn column = table.getColumn("�ص�");
			column.setPreferredWidth(250);
			column.setCellRenderer(render);
			TableColumn column3 = table.getColumn("����");
			column3.setPreferredWidth(250);
			column3.setCellRenderer(render);
			TableColumn column4 = table.getColumn("����");
			column4.setPreferredWidth(100);
			column4.setCellRenderer(render);
			TableColumn column5 = table.getColumn("��ʼʱ��");
			column5.setPreferredWidth(200);
			column5.setCellRenderer(render);
			TableColumn column6 = table.getColumn("����ʱ��");
			column6.setPreferredWidth(200);
			column6.setCellRenderer(render);
//			TableColumn column7 = table.getColumn("id����������");                             
			//table.removeColumn(column7); //����id��
			//column7.setPreferredWidth(0);
			scrollPane.setViewportView(table);
			
			
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		
		
	}
	
}