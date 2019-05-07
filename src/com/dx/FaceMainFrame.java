package com.dx;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.dx.inter.ParamSetupInterface;
import com.dx.login.Login;
import com.dx.service.ParamSetupImpl;

public class FaceMainFrame extends JFrame {
	private JPanel panel = null;
	private FacePhoPanel facepanel = null ;
	private JPanel panel2 = null;
	private String str2 = "" ;
	private ParamSetupInterface paramface = new ParamSetupImpl();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Login();
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FaceMainFrame() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		int x = (int)toolkit.getScreenSize().getWidth();
		int y = (int)toolkit.getScreenSize().getHeight();
		this.setLocation(x/2-700, y/2-384);
		this.setSize(1400, 768);
		this.setVisible(true);
		setBackground(Color.MAGENTA);
		setTitle("�����ɼ�ϵͳ");
		ImageIcon icon=new ImageIcon("img/face.png");  
		this.setIconImage(icon.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menubar = new JMenuBar();
		setJMenuBar(menubar);
		JMenu menu1 = new JMenu("ͼ��ɼ�");
		menubar.add(menu1);
		JMenuItem item1_1 = new JMenuItem("��Ƭ�ɼ���֤");
		menu1.add(item1_1);
		
		JMenu menu2 = new JMenu("�ɼ���֤��ѯͳ��");
		menubar.add(menu2);
		JMenuItem item2_1 = new JMenuItem("��֤�����ѯ");
		menu2.add(item2_1);
		JMenuItem item2_2 = new JMenuItem("��֤���ͳ��");
		menu2.add(item2_2);
		JMenuItem item2_3 = new JMenuItem("��֤��־����");
		menu2.add(item2_3);
		
		JMenu menu3 = new JMenu("���ݹ���");
		menubar.add(menu3);
		JMenuItem item3_1 = new JMenuItem("ϵͳ���ݳ�ʼ��");
		menu3.add(item3_1);
		JMenuItem item3_2 = new JMenuItem("���ݵ��뵼��");
		menu3.add(item3_2);
		JMenuItem item3_3 = new JMenuItem("��֤��������");
		menu3.add(item3_3);
		JMenuItem item3_4 = new JMenuItem("��֤���ݳ�ʼ��");
		menu3.add(item3_4);
		
		/**����ð�ť,�ﵽ��controlp��幦��*/
		item1_1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				removePanel();
				facepanel =  new FacePhoPanel() ;
				panel = facepanel.getFacePhoPanel();
				facepanel.getPerson();
				getContentPane().add(panel);
				setVisible(true);
			}

		});
	
		item2_1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				removePanel();
				panel2 = new FaceLogsResultByPage();
				getContentPane().add(panel2);
				setVisible(true);
			}
		});
		item2_2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				removePanel();
				//panel2 = new ResultTongji();
				panel2=new StatisticsResultByPage();
				getContentPane().add(panel2);
				setVisible(true);
			}
		});
		item2_3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				removePanel();
				panel2 = new LogDetailsPanel();
				getContentPane().add(panel2);
				setVisible(true);
			}
		});
		item3_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//ϵͳ���ݳ�ʼ������ճ��û���֮������б�
				int isDelete = JOptionPane.showConfirmDialog(null, "ȷ��Ҫ��ʼ��ϵͳ���ݿ��𣬸ò���������","��ʾ",JOptionPane.YES_NO_CANCEL_OPTION);
				if(isDelete == JOptionPane.YES_OPTION) {
					boolean inittype = paramface.initSysData();
					JOptionPane.showMessageDialog(null, "ϵͳ��ʼ���ɹ�");
				}
			}
		});
		item3_2.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent evt){
				removePanel();
				panel2 = new DataPortPanel();
				getContentPane().add(panel2);
				setVisible(true);
//				Thread.currentThread().getThreadGroup().list();
			}
		});
		item3_3.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent evt){
				removePanel();
				panel2 = new DataSetUp();
				getContentPane().add(panel2);
				setVisible(true);
//				Thread.currentThread().getThreadGroup().list();
			}
		});
		item3_4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//ϵͳ���ݳ�ʼ������ճ��û���֮������б�
				int isDelete = JOptionPane.showConfirmDialog(null, "ȷ��Ҫ��ʼ����֤�����𣬳�ʼ�������������� ��ǰ��֤����","��ʾ",JOptionPane.YES_NO_CANCEL_OPTION);
				if(isDelete == JOptionPane.YES_OPTION) {
					boolean inittype = paramface.initRenzhengData();
					JOptionPane.showMessageDialog(null, "��֤���ݳ�ʼ���ɹ�");
				}
			}
		});
		addPropertyChangeListener(new PropertyChangeListener(){  
			public void propertyChange(PropertyChangeEvent evt) {  
			//	System.out.println("1Old:"+evt.getOldValue()+"----New:"+evt.getNewValue()+"----"+evt.getPropertyName());
				if(evt.getPropertyName().equals("pidstr2")) {
					//�Ƴ����¼������panel
					removePanel();
					panel2 = new DataSetUp();
					getContentPane().add(panel2);
					setVisible(true);
				}
					
		}});
	}
	
	/**
	 * ���getContentPane�е�panel
	 * */
	public void removePanel() {
		//���getContentPane�е�panel
//		Thread.currentThread().getThreadGroup().list();
		try {
			if(panel != null) {
				getContentPane().remove(panel);//������������,��������
				repaint();
				if(facepanel != null && facepanel.getPanel1() != null && facepanel.getPanel1().getMainThread() != null) {
					Thread mainThread = facepanel.getPanel1().getMainThread() ;
					facepanel.getPanel1().getCamera().release();
					facepanel.getPerson().closePerson();
					mainThread.interrupt();//����ͷ�߳���ֹ
				}
			}
		} catch (Exception e) {
			System.out.println("����ͷ�����߳���ֹ����");
		}
//		try {
//			if(person != null && person.getPthread() != null) {
////				person.closeThread();
//				person.closePerson();
//				System.out.println(person.getPthread().isDaemon());
////				person.getPthread().interrupt();//�̹߳ر�
////				person.closePerson();//�رն˿�
////				person.getThread().interrupt();//����֤�Ķ������߳���ֹ
//			}
//		} catch (Exception e) {
//			System.out.println("�Ķ����߳���ֹ����");
//		}
		
		//������� ��panel
		getContentPane().removeAll();
	}

	public String getStr2() {
		return str2;
	}

	public void setStr2(String str2) {
		String oldstr2 = this.str2 ;
		this.str2 = str2;
		firePropertyChange("pidstr2", oldstr2, str2);
	}

	
}