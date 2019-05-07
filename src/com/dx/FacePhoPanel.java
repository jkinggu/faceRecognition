package com.dx;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.TableView.TableRow;

import org.freehep.graphicsio.swf.SetBackgroundColor;

import com.dx.pojo.PersonCard;
import com.dx.pojo.Zkzdata;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class FacePhoPanel extends JPanel {
	public FacePhoPanel() {
	}
	private String shibieleixing = "" ;
	private CameraCapture panel1 = null ;
	private Person person = null ;
	public JPanel getFacePhoPanel() {
		JPanel controlp = new JPanel();
		BoxLayout layout=new BoxLayout(controlp, BoxLayout.X_AXIS);
		controlp.setLayout(layout);
		
		panel1 = new CameraCapture();
		controlp.add(panel1, "panel1");
//		panel1.setBounds(0, 0, 700, 768);
		panel1.setPreferredSize(new Dimension(700,768));
		JPanel panel2 = new JPanel();
		controlp.add(panel2, "panel2");
//		panel2.setBounds(700, 0, 700, 768);
		panel2.setPreferredSize(new Dimension(700,768));
//		getContentPane().add(controlp);
		
		
		JPanel panel2zuo = new JPanel();
		BoxLayout layout2=new BoxLayout(panel2,BoxLayout.Y_AXIS);
		panel2.setLayout(layout2);
		panel2.add(panel2zuo, "panel2zuo");//��������е���Ƭ
		//panel2zuo.setLayout(null);
		JPanel panel2you = new JPanel();
		panel2.add(panel2you, "panel2you");//�����׼��֤
		//panel2you.setLayout(null);
		
		
		//��������֤�Ķ�������
		JLabel label2 = new JLabel();//����֤��Ƭ
		panel2zuo.add(label2);
		label2.setBounds(200, 0, 100, 100);//��������֤��Ƭ
		
		JLabel label = new JLabel();//�ֳ�������Ƭ
		panel2zuo.add(label);
		label.setBounds(300, 0, 100,100);//�̶�lable��λ��
		
		JLabel label3 = new JLabel();//׼��֤��Ƭ
		panel2zuo.add(label3);
		label3.setBounds(300, 0, 100,100);//�̶�lable��λ��
		
		JPanel scrollPane = new JPanel();
		GridBagConstraints gridBagConstraints_8 = new GridBagConstraints();
		gridBagConstraints_8.weighty = 1.0;
		gridBagConstraints_8.gridwidth = 1;
		gridBagConstraints_8.gridy = 2;
		gridBagConstraints_8.gridx = 1;
		panel2you.add(scrollPane, gridBagConstraints_8);
		
		DefaultTableModel dftm = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row,int column){
            	return false;
            }
           
		};
		
		String[] tableHeads = new String[]{""};
		dftm.setColumnIdentifiers(tableHeads);
		//׼��֤�����ʾ
		JTable table = new JTable(dftm);
		table.setRowHeight(35);
		table.getTableHeader().setVisible(false);
		table.setShowGrid(false);
		table.setFont(new Font("Dialog", Font.PLAIN, 14));
		
		TableColumn column = table.getColumn(table.getColumnName(0));
		column.setPreferredWidth(650);
		
		List<String[]> positioins = new ArrayList<String[]>() ;
		
		JLabel text = new JLabel();
		text.setFont(new Font("Dialog", Font.BOLD, 16));
		text.setForeground(Color.red);
		panel2zuo.add(text);
		
		person = new Person();
		person.addPropertyChangeListener(new PropertyChangeListener(){  
			public void propertyChange(PropertyChangeEvent evt) {  
			//	System.out.println("1Old:"+evt.getOldValue()+"----New:"+evt.getNewValue()+"----"+evt.getPropertyName());  
				if(evt.getPropertyName().equals("ischange") && person.isIschange() == true) {
					label2.setIcon(null);
					label.setIcon(null);
					label3.setIcon(null);
					panel1.flag = true ;
					//����֤����
					PersonCard card = person.getCard1() == null ? person.getCard2() : person.getCard2();
					panel1.card = card ;
					//����֤��
//					String upersonnum = person.getCard1() == null ? person.getCard2().getPnum() :
//						person.getCard1().getPnum();
					String upersonnum = card.getPnum();
					panel1.upersonnum = upersonnum ;
					//������֤��Ƭ��ʾ��ҳ����
//					File file = new File("D:\\eclipse\\workspace\\FaceRecongnition\\img/facezp.bmp");//����֤ͼ����Ƭ��ַ
					File file = new File("D:\\faceimages\\faceimage\\"+upersonnum+"/"+upersonnum+"zp.bmp");//����֤ͼ����Ƭ��ַ
					Image image = null;
					try {
						image = ImageIO.read(file);
					} catch (IOException e) {
						e.printStackTrace();
					}
					ImageIcon imageicon=new ImageIcon(image);
					imageicon.setImage(imageicon.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT));//�̶���Ƭ��С
					label2.setIcon(imageicon);
					//�����������֤�����ı䣬���ҳ�����������
				//	text.setText("");
					
					
				}
				
		}});
		
		//����ֵ������
		panel1.addPropertyChangeListener(new PropertyChangeListener(){  
			public void propertyChange(PropertyChangeEvent evt) {  
				//System.out.println("2Old:"+evt.getOldValue()+"----New:"+evt.getNewValue()+"----"+evt.getPropertyName());  
				if(evt.getPropertyName().equals("filepath")) {
					String imgpath = evt.getNewValue() +"";
					//��new	ͼƬ���ӵ�panel2����ʾ
					imgpath = imgpath.replaceAll("\\\\", "//");
					ImageIcon imageicon=new ImageIcon(imgpath);
					imageicon.setImage(imageicon.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT));//�̶���Ƭ��С
					label.setIcon(imageicon);
					//text.setText("");
				}else if(evt.getPropertyName().equals("types")) {
					text.setText("");
					Integer c1 =dftm.getRowCount() ; 
					for(int i=0; i<c1 ;i++) {
						dftm.removeRow(0);
					}
				}else if(evt.getPropertyName().equals("shibieleixing")) {
					shibieleixing = evt.getNewValue() +"";
					//��ȡ����Ľ������ʾ��ҳ����
					text.setText(shibieleixing);//shibieleixing
					//showSheng(shibieleixing);//�����������
					//����һ��׼��֤��ʾ���������
					if(!"ͨ��".equals(shibieleixing)) {
						Integer c1 =dftm.getRowCount() ; 
						for(int i=0; i<c1 ;i++) {
							dftm.removeRow(0);
						}
					}
				}else if(evt.getPropertyName().equals("zkzdata")) {
					Integer c1 =dftm.getRowCount() ; 
					for(int i=0; i<c1 ;i++) {
						dftm.removeRow(0);
					}
					
					Zkzdata zkz = (Zkzdata) evt.getNewValue() ;
					
					String imgpath = "D:\\faceimages\\zkzpho\\"+zkz.getUpersonnum()+".JPG";
					//��new	ͼƬ���ӵ�panel2����ʾ
					imgpath = imgpath.replaceAll("\\\\", "//");
					ImageIcon imageicon=new ImageIcon(imgpath);
					imageicon.setImage(imageicon.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT));//�̶���Ƭ��С
					label3.setIcon(imageicon);
					
					
					String[] row1 = new String[] {" ������"+zkz.getXingming()+"                            �Ա�"+zkz.getXingbie()+"                           ���䣺"+zkz.getNianlin()} ;
					dftm.addRow(row1);
					positioins.add(row1);
					String[] row2 = new String[] {" ׼��֤�ţ�"+zkz.getZkznum()} ;
					dftm.addRow(row2);
					positioins.add(row2);
					String[] row3 = new String[] {" ����֤�ţ�"+zkz.getUpersonnum()} ;
					dftm.addRow(row3);
					positioins.add(row3);
					String[] row4 = new String[] {" ������λ��"+zkz.getDanweiname()} ;
					dftm.addRow(row4);
					positioins.add(row4);
					String[] row5 = new String[] {" �걨���֣�"+zkz.getBaokaoname()} ;
					dftm.addRow(row5);
					positioins.add(row5);
					String[] row6 = new String[] {" �걨�ȼ���"+zkz.getJbname()} ;
					dftm.addRow(row6);
					positioins.add(row6);
					
					if(!"".equals(zkz.getKc1())) {
						String[] row7 = new String[] {"                 ������"+zkz.getKd1()+""+zkz.getKc1()+"                    ���ţ�"+zkz.getZh1()} ;
						dftm.addRow(row7);
						String[] row8 = new String[] {" ְҵ����: ����ʱ�䣺"+zkz.getSj1()} ;
						dftm.addRow(row8);
						String[] row9 = new String[] {"                 ���Եص㣺"+zkz.getDd1()} ;
						dftm.addRow(row9);
						positioins.add(row7);
						positioins.add(row8);
						positioins.add(row9);
					}
					if(!"".equals(zkz.getKc2())) {
						String[] row7 = new String[] {"                 ������"+zkz.getKd2()+""+zkz.getKc2()+"                    ���ţ�"+zkz.getZh2()} ;
						dftm.addRow(row7);
						String[] row8 = new String[] {" ְҵ����: ����ʱ�䣺"+zkz.getSj2()} ;
						dftm.addRow(row8);
						String[] row9 = new String[] {"                 ���Եص㣺"+zkz.getDd2()} ;
						dftm.addRow(row9);
						positioins.add(row7);
						positioins.add(row8);
						positioins.add(row9);
					}
					if(!"".equals(zkz.getKc3())) {
						String[] row7 = new String[] {"                 ������"+zkz.getKd3()+""+zkz.getKc3()+"                    ���ţ�"+zkz.getZh3()} ;
						dftm.addRow(row7);
						String[] row8 = new String[] {" ��λ����: ����ʱ�䣺"+zkz.getSj3()} ;
						dftm.addRow(row8);
						String[] row9 = new String[] {"                 ���Եص㣺"+zkz.getDd3()} ;
						dftm.addRow(row9);
						positioins.add(row7);
						positioins.add(row8);
						positioins.add(row9);
					}
					
//					for(int i=0; i<table.getRowCount(); i++){
//						if (6 == i){
//							table.setBackground(Color.YELLOW);
//						}
//					}
//					
					//EvenOddRenderer cellrender = new EvenOddRenderer(positioins);
					//table = cellrender.getTableCellRendererComponent(table,  6, 0);
				}
		}}); 
		
		
		
		scrollPane.add(table);
		
		//��ʾ��
		text.setVisible(true);
		
		return controlp ;
	}
	class EvenOddRenderer extends DefaultTableCellRenderer {
		
		private static final long serialVersionUID = 1L;
		private List<String[]> positioins;
		
		public EvenOddRenderer(List<String[]> _positioins){
			this.positioins = _positioins;
		}
		
		public Component getTableCellRendererComponent(JTable table,int row, int col) {
			for(int i=0; i<table.getRowCount(); i++){
				if (row == i){
					this.setBackground(Color.yellow);
				}
			}
			for(String[] rowAndCol : this.positioins){
//				System.out.println(rowAndCol+"--------"+rowAndCol[0]+"--------"+rowAndCol[1]);
//				System.out.println(this.positioins);
				int _row= Integer.valueOf(rowAndCol[0]);
				int _col= Integer.valueOf(rowAndCol[1]);
				
				if( _row == row && _col == col) {
					this.setBackground(Color.yellow);
				}
			}
			return this;
		}
	}
	
	public CameraCapture getPanel1() {
		return panel1;
	}

	public void setPanel1(CameraCapture panel1) {
		this.panel1 = panel1;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	//��������
	public static void showSheng(String str) {
		ActiveXComponent sap = new ActiveXComponent("Sapi.SpVoice");
		// Dispatch����ʲô�ģ�
		Dispatch sapo = sap.getObject();
		try {
			
			// ���� 0-100
			sap.setProperty("Volume", new Variant(100));
			// �����ʶ��ٶ� -10 �� +10
			sap.setProperty("Rate", new Variant(0));
			
			Variant defalutVoice = sap.getProperty("Voice");
			
			Dispatch dispdefaultVoice = defalutVoice.toDispatch();
			Variant allVoices = Dispatch.call(sapo, "GetVoices");
			Dispatch dispVoices = allVoices.toDispatch();
			
			Dispatch setvoice = Dispatch.call(dispVoices, "Item", new Variant(1)).toDispatch();
			ActiveXComponent voiceActivex = new ActiveXComponent(dispdefaultVoice);
			ActiveXComponent setvoiceActivex = new ActiveXComponent(setvoice);
			
			Variant item = Dispatch.call(setvoiceActivex, "GetDescription");
			// ִ���ʶ�
			Dispatch.call(sapo, "Speak", new Variant(str));//"��С��С���ѣ����Ϻ�"
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sapo.safeRelease();
			sap.safeRelease();
		}
	}
	
}