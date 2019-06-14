package com.dx.test.camera;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneLayout;
import javax.swing.table.DefaultTableModel;

import com.dx.CameraCore;
import com.dx.Person;
import com.dx.pojo.PersonCard;
import com.dx.pojo.Zkzdata;
import com.dx.util.Const;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class CamerPanel extends JFrame {

	private JPanel rightPanel = new JPanel();
	private JPanel leftPanel = new JPanel();
	private JMenuBar bar = new JMenuBar();

	private String shibieleixing = "";// ʶ�𷵻���Ϣ
	private Integer rzCount = 0;// ʶ�����
	private Person person = null;
	private JDialog dialog = null;// ��ʾ��Ϣ��
	private CameraCore camerPanel;
	private JPanel photoPanel;
	private JPanel infoPanel;
	private JLabel sfzLabel;
	private JLabel faceLabel;
	private JLabel zkzLabel;
	private JLabel text;
	private JScrollPane scrollPane;//
	private Boolean zkzFlag = false;
	private BoxLayout boxLayout;

	public static void main(String[] args) {
		new CamerPanel();
	}

	public CamerPanel() {

		setBounds(0, 0, getScreenWidth(), getScreenHeight());
		setTitle("�����ɼ���֤");
		setLayout(new BorderLayout());

	
		// leftPanel��������ͷ
		leftPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension((int) (getWidth() * Const.CAMER_PERCENT), (int) getHeight()));
		leftPanel.setLayout(new BorderLayout());
		camerPanel = new CameraCore((int) (leftPanel.getPreferredSize().getHeight() * Const.HEIGHT_WIDTH_PERCENT
				* Const.CV_HEIGHT_WIDTH_PERCENT), (int) (leftPanel.getPreferredSize().getHeight()));
		camerPanel.setPreferredSize(new Dimension((int) leftPanel.getPreferredSize().getWidth() - Const.CV_MARGIN,
				(int) leftPanel.getPreferredSize().getHeight()));
		camerPanel.setVisible(true);
		leftPanel.add(camerPanel, BorderLayout.WEST);
		getContentPane().add(leftPanel, BorderLayout.WEST);
		// -------------����ͷ��ʼ������-------------//

		
		
		rightPanel = new JPanel();
		rightPanel.setPreferredSize(new Dimension((int) (getWidth() * Const.INFO_PERCENT), (int) getHeight()));
		boxLayout = new BoxLayout(rightPanel, BoxLayout.Y_AXIS);
		rightPanel.setLayout(new BorderLayout());
		// ����rightpanel���
		// ͼƬ���
		photoPanel = new JPanel();
		photoPanel.setPreferredSize(new Dimension((int) rightPanel.getPreferredSize().getWidth(),
				(int) (rightPanel.getPreferredSize().getHeight() * Const.PHOTO_PERCENT)));
		//photoPanel.setAlignmentX(CENTER_ALIGNMENT);
		photoPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		// ����ͼƬ��������
		sfzLabel = new JLabel("�������֤..");
		sfzLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		sfzLabel.setForeground(Color.red);
		sfzLabel.setPreferredSize(new Dimension(Const.PHOTO_SIZE, Const.PHOTO_SIZE));
		sfzLabel.setVisible(true);

		zkzLabel = new JLabel();
		zkzLabel.setPreferredSize(new Dimension(Const.PHOTO_SIZE, Const.PHOTO_SIZE));
		zkzLabel.setVisible(true);

		faceLabel = new JLabel();
		faceLabel.setPreferredSize(new Dimension(Const.PHOTO_SIZE, Const.PHOTO_SIZE));
		faceLabel.setVisible(true);

		text = new JLabel();
		text.setFont(new Font("Dialog", Font.BOLD, 16));
		text.setForeground(Color.red);

		photoPanel.add(sfzLabel);
		photoPanel.add(faceLabel);
		photoPanel.add(zkzLabel);
		photoPanel.add(text);
		photoPanel.setVisible(true);
		rightPanel.add(photoPanel,BorderLayout.NORTH);

		// ��Ϣ���������׼��֤��Ϣ
		infoPanel = new JPanel();
		infoPanel.setPreferredSize(new Dimension((int) rightPanel.getPreferredSize().getWidth(),
				(int) (rightPanel.getPreferredSize().getHeight() * Const.PHOTO_INFO_PERCENT)));
	//	infoPanel.setAlignmentX(CENTER_ALIGNMENT);
		infoPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		// infoPanel.setBackground(Color.GRAY);
		// ����infoPanel�����
		// ������ʾ׼��֤��JTabelģʽ
		DefaultTableModel dftm = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		String[] tableHeads = new String[] { "ռλ" };
		dftm.setColumnIdentifiers(tableHeads);
		// ׼��֤�����ʾ
		JTable table = new JTable(dftm);
		table.setRowHeight(25);
		table.getTableHeader().setVisible(false);
		table.setShowGrid(false);
		table.setShowHorizontalLines(false);
		table.setShowVerticalLines(false);
		table.setFont(new Font("Dialog", Font.PLAIN, 14));

		// table.setPreferredSize(new
		// Dimension((int)infoPanel.getPreferredSize().getWidth()-Const.PREFERSIZE_MARGIN,(int)
		// (infoPanel.getPreferredSize().getHeight())));
		// TableColumn column = table.getColumn(table.getColumnName(0));
		// column.setPreferredWidth(650);
		scrollPane = new JScrollPane(table);

		// scrollPane.setLayout(new ScrollPaneLayout());
		scrollPane.setSize(new Dimension((int) infoPanel.getPreferredSize().getWidth() - Const.PREFERSIZE_MARGIN,
				(int) (infoPanel.getPreferredSize().getHeight() * Const.SCROLL_HEIGHT_PERCENT)));
		// scrollPane.setPreferredSize(new
		// Dimension((int)infoPanel.getPreferredSize().getWidth()-Const.PREFERSIZE_MARGIN,(int)
		// (infoPanel.getPreferredSize().getHeight()*Const.SCROLL_HEIGHT_PERCENT)));

		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		scrollPane.setVisible(false);
		infoPanel.add(scrollPane);

		rightPanel.add(infoPanel,BorderLayout.SOUTH);

		getContentPane().add(rightPanel, BorderLayout.EAST);

		// �������֤�Ķ���
		person = new Person();
		person.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName().equals("ischange") && person.isIschange() == true) {
					sfzLabel.setIcon(null);
					zkzLabel.setIcon(null);
					faceLabel.setIcon(null);
					text.setText("");
					camerPanel.flag = true;
					//���֤����
					PersonCard card = person.getCard1() == null ? person.getCard2() : person.getCard2();
					camerPanel.card = card ;
					//���֤��
//					String upersonnum = person.getCard1() == null ? person.getCard2().getPnum() :
//						person.getCard1().getPnum();
					String upersonnum = card.getPnum();
					camerPanel.upersonnum = upersonnum ;
					camerPanel.upersonname=card.getPname();
					//�����֤��Ƭ��ʾ��ҳ����
					//File file = new File("D:\\eclipse\\workspace\\FaceRecongnition\\img/facezp.bmp");//���֤ͼ����Ƭ��ַ
					File f = new File("");
					File file = new File(f.getAbsolutePath()+ "\\faceimage\\"+upersonnum+"/"+upersonnum+"zp.bmp");//���֤ͼ����Ƭ��ַ
					Image image = null;
					try {
						image = ImageIO.read(file);
					} catch (IOException e) {
						e.printStackTrace();
					}
					ImageIcon imageicon=new ImageIcon(image);
					imageicon.setImage(imageicon.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT));//�̶���Ƭ��С
					sfzLabel.setIcon(imageicon);
					//����������֤�����ı䣬���ҳ�����������
				//	text.setText("");					
					
				}
		  }
		});

		// cameraPanel����ֵ����
		//����ֵ������
		 camerPanel.addPropertyChangeListener(new PropertyChangeListener(){  
					public void propertyChange(PropertyChangeEvent evt) {  
						//System.out.println("2Old:"+evt.getOldValue()+"----New:"+evt.getNewValue()+"----"+evt.getPropertyName());  
						if(evt.getPropertyName().equals("filepath")) {
							String imgpath = evt.getNewValue() +"";
							//��new	ͼƬ��ӵ�panel2����ʾ
							imgpath = imgpath.replaceAll("\\\\", "//");
							ImageIcon imageicon=new ImageIcon(imgpath);
							imageicon.setImage(imageicon.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT));//�̶���Ƭ��С
							faceLabel.setIcon(imageicon);
							//text.setText("");
						}else if(evt.getPropertyName().equals("types")) {
							text.setText("");
							Integer c1 =dftm.getRowCount() ; 
							for(int i=0; i<c1 ;i++) {
								dftm.removeRow(0);
							}
						}else if(evt.getPropertyName().equals("rzMap")) {
							Map<String,Object> rzMap=(Map<String, Object>) evt.getNewValue();
							shibieleixing = (String) rzMap.get("type");//��֤������Ϣ
							rzCount=(Integer)rzMap.get("rzCount");//�Ƿ�ɹ���
							Boolean rzFlag=(Boolean)rzMap.get("rzFlag");
							//��ȡ����Ľ������ʾ��ҳ����
							if("�ǿ���".equals(shibieleixing)) {						
								text.setText(shibieleixing);				   		    					
							}else if(("���˹����".equals(shibieleixing))){					
								text.setText("���˹����");//shibieleixing												
							}else if (!"������һ��".equals(shibieleixing)) {
								text.setText(shibieleixing);				
							}else if((!"ͨ��".equals(shibieleixing))) {
								text.setText(shibieleixing);//shibieleixing						
								//����һ��׼��֤��ʾ���������
		  			            for(int i=0;i<dftm.getRowCount();i++) {
									dftm.removeRow(0);
								}						
							}else if("ͨ��".equals(shibieleixing)){
								text.setText("ͨ��");//shibieleixing					
							} 
							
							//showSheng(shibieleixing);//�����������
								
						}else if(evt.getPropertyName().equals("zkzdata")) {
							Integer c1 =dftm.getRowCount() ; 
							for(int i=0; i<c1 ;i++) {
								dftm.removeRow(0);
							}
							
							Zkzdata zkz = (Zkzdata) evt.getNewValue() ;
							File f = new File("");
							String imgpath = f.getAbsolutePath()+ "\\zkzpho\\"+zkz.getUpersonnum()+".JPG";
							//��new	ͼƬ��ӵ�panel2����ʾ
							imgpath = imgpath.replaceAll("\\\\", "//");
							ImageIcon imageicon=new ImageIcon(imgpath);
							imageicon.setImage(imageicon.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT));//�̶���Ƭ��С
							zkzLabel.setIcon(imageicon);
							
							
							String[] row1 = new String[] {" ������"+zkz.getXingming()+"                            �Ա�"+zkz.getXingbie()+"                           ���䣺"+zkz.getNianlin()} ;
							dftm.addRow(row1);
						
							String[] row2 = new String[] {" ׼��֤�ţ�"+zkz.getZkznum()} ;
							dftm.addRow(row2);
							
							String[] row3 = new String[] {" ���֤�ţ�"+zkz.getUpersonnum()} ;
							dftm.addRow(row3);
						
							String[] row4 = new String[] {" ������λ��"+zkz.getDanweiname()} ;
							dftm.addRow(row4);
						
							String[] row5 = new String[] {" �걨���֣�"+zkz.getBaokaoname()} ;
							dftm.addRow(row5);
						
							String[] row6 = new String[] {" �걨�ȼ���"+zkz.getJbname()} ;
							dftm.addRow(row6);
						
							
							if(!"".equals(zkz.getKc1())) {
								String[] row7 = new String[] {"                 ������"+zkz.getKd1()+""+zkz.getKc1()+"                    ���ţ�"+zkz.getZh1()} ;
								dftm.addRow(row7);
								String[] row8 = new String[] {" ְҵ����: ����ʱ�䣺"+zkz.getSj1()} ;
								dftm.addRow(row8);
								String[] row9 = new String[] {"                 ���Եص㣺"+zkz.getDd1()} ;
								dftm.addRow(row9);
								
							}
							if(!"".equals(zkz.getKc2())) {
								String[] row7 = new String[] {"                 ������"+zkz.getKd2()+""+zkz.getKc2()+"                    ���ţ�"+zkz.getZh2()} ;
								dftm.addRow(row7);
								String[] row8 = new String[] {" ְҵ����: ����ʱ�䣺"+zkz.getSj2()} ;
								dftm.addRow(row8);
								String[] row9 = new String[] {"                 ���Եص㣺"+zkz.getDd2()} ;
								dftm.addRow(row9);
								
							}
							if(!"".equals(zkz.getKc3())) {
								String[] row7 = new String[] {"                 ������"+zkz.getKd3()+""+zkz.getKc3()+"                    ���ţ�"+zkz.getZh3()} ;
								dftm.addRow(row7);
								String[] row8 = new String[] {" ��λ����: ����ʱ�䣺"+zkz.getSj3()} ;
								dftm.addRow(row8);
								String[] row9 = new String[] {"                 ���Եص㣺"+zkz.getDd3()} ;
								dftm.addRow(row9);
							
							}					
						}
				}}); 

		// ����frame����Ҫ��������
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		
		// ����frame�ߴ�仯
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
			   //	System.out.println("frame�仯��" + getWidth() + "---" + getHeight());
				bar.setPreferredSize(new Dimension(getWidth(), Const.BAR_HEIGHT));
				rightPanel.setPreferredSize(new Dimension((int) (getWidth() * Const.INFO_PERCENT), getHeight()));
				leftPanel.setPreferredSize(new Dimension((int) (getWidth() * Const.CAMER_PERCENT), getHeight()));
				photoPanel.setPreferredSize(new Dimension((int) rightPanel.getPreferredSize().getWidth(),
						(int) (rightPanel.getPreferredSize().getHeight() * Const.PHOTO_PERCENT)));
				infoPanel.setPreferredSize(new Dimension((int) rightPanel.getPreferredSize().getWidth(),
						(int) (rightPanel.getPreferredSize().getHeight() * Const.PHOTO_INFO_PERCENT)));
				scrollPane.setPreferredSize(
						new Dimension((int) infoPanel.getPreferredSize().getWidth() - Const.PREFERSIZE_MARGIN,
								(int) (infoPanel.getPreferredSize().getHeight() * Const.SCROLL_HEIGHT_PERCENT)));

				camerPanel.setPreferredSize(
						new Dimension((int) leftPanel.getPreferredSize().getWidth() - Const.PREFERSIZE_MARGIN,
								(int) leftPanel.getPreferredSize().getHeight()));

				scrollPane.setVisible(true);
				repaint();
			}

			// ����϶������¼�
			@Override
			public void componentMoved(ComponentEvent e) {
				bar.setPreferredSize(new Dimension(getWidth(), Const.BAR_HEIGHT));
				rightPanel.setPreferredSize(new Dimension((int) (getWidth() * Const.INFO_PERCENT), getHeight()));
				leftPanel.setPreferredSize(new Dimension((int) (getWidth() * Const.CAMER_PERCENT), getHeight()));
				photoPanel.setPreferredSize(new Dimension((int) rightPanel.getPreferredSize().getWidth(),
						(int) (rightPanel.getPreferredSize().getHeight() * Const.PHOTO_PERCENT)));
				infoPanel.setPreferredSize(new Dimension((int) rightPanel.getPreferredSize().getWidth(),
						(int) (rightPanel.getPreferredSize().getHeight() * Const.PHOTO_INFO_PERCENT)));
				scrollPane.setPreferredSize(
						new Dimension((int) infoPanel.getPreferredSize().getWidth() - Const.PREFERSIZE_MARGIN,
								(int) (infoPanel.getPreferredSize().getHeight() * Const.SCROLL_HEIGHT_PERCENT)));
				camerPanel.setPreferredSize(
						new Dimension((int) leftPanel.getPreferredSize().getWidth() - Const.PREFERSIZE_MARGIN,
								(int) leftPanel.getPreferredSize().getHeight()));
				table.setVisible(true);
				scrollPane.setVisible(true);
				repaint();
			}

			// ��������⻹ԭ�¼�
			@Override
			public void componentShown(ComponentEvent e) {
				bar.setPreferredSize(new Dimension(getWidth(), Const.BAR_HEIGHT));
				rightPanel.setPreferredSize(new Dimension((int) (getWidth() * Const.INFO_PERCENT), getHeight()));
				leftPanel.setPreferredSize(new Dimension((int) (getWidth() * Const.CAMER_PERCENT), getHeight()));
				photoPanel.setPreferredSize(new Dimension((int) rightPanel.getPreferredSize().getWidth(),
						(int) (rightPanel.getPreferredSize().getHeight() * Const.PHOTO_PERCENT)));
				infoPanel.setPreferredSize(new Dimension((int) rightPanel.getPreferredSize().getWidth(),
						(int) (rightPanel.getPreferredSize().getHeight() * Const.PHOTO_INFO_PERCENT)));
				scrollPane.setPreferredSize(
						new Dimension((int) infoPanel.getPreferredSize().getWidth() - Const.PREFERSIZE_MARGIN,
								(int) (infoPanel.getPreferredSize().getHeight() * Const.SCROLL_HEIGHT_PERCENT)));
				camerPanel.setPreferredSize(
						new Dimension((int) leftPanel.getPreferredSize().getWidth() - Const.PREFERSIZE_MARGIN,
								(int) leftPanel.getPreferredSize().getHeight()));
				scrollPane.setVisible(true);
				repaint();
			}

		});

		// ��������״̬
		addWindowStateListener(new WindowStateListener() {

			@Override
			public void windowStateChanged(WindowEvent e) {
				if (e.getNewState() == 6) {// �������
					bar.setPreferredSize(new Dimension(getWidth(), Const.BAR_HEIGHT));
					rightPanel.setPreferredSize(new Dimension((int) (getWidth() * Const.INFO_PERCENT), getHeight()));
					leftPanel.setPreferredSize(new Dimension((int) (getWidth() * Const.CAMER_PERCENT), getHeight()));
					photoPanel.setPreferredSize(new Dimension((int) rightPanel.getPreferredSize().getWidth(),
							(int) (rightPanel.getPreferredSize().getHeight() * Const.PHOTO_PERCENT)));
					infoPanel.setPreferredSize(new Dimension((int) rightPanel.getPreferredSize().getWidth(),
							(int) (rightPanel.getPreferredSize().getHeight() * Const.PHOTO_INFO_PERCENT)));
					scrollPane.setPreferredSize(
							new Dimension((int) infoPanel.getPreferredSize().getWidth() - Const.PREFERSIZE_MARGIN,
									(int) (infoPanel.getPreferredSize().getHeight() * Const.SCROLL_HEIGHT_PERCENT)));
					camerPanel.setPreferredSize(
							new Dimension((int) leftPanel.getPreferredSize().getWidth() - Const.PREFERSIZE_MARGIN,
									(int) leftPanel.getPreferredSize().getHeight()));
					scrollPane.setVisible(true);
					repaint();

				} else if (e.getNewState() == 0) {// ���ڻ�ԭ
					bar.setPreferredSize(new Dimension(getWidth(), Const.BAR_HEIGHT));
					rightPanel.setPreferredSize(new Dimension((int) (getWidth() * Const.INFO_PERCENT), getHeight()));
					leftPanel.setPreferredSize(new Dimension((int) (getWidth() * Const.CAMER_PERCENT), getHeight()));
					photoPanel.setPreferredSize(new Dimension((int) rightPanel.getPreferredSize().getWidth(),
							(int) (rightPanel.getPreferredSize().getHeight() * Const.PHOTO_PERCENT)));
					infoPanel.setPreferredSize(new Dimension((int) rightPanel.getPreferredSize().getWidth(),
							(int) (rightPanel.getPreferredSize().getHeight() * Const.PHOTO_INFO_PERCENT)));
					scrollPane.setPreferredSize(
							new Dimension((int) infoPanel.getPreferredSize().getWidth() - Const.PREFERSIZE_MARGIN,
									(int) (infoPanel.getPreferredSize().getHeight() * Const.SCROLL_HEIGHT_PERCENT)));
					camerPanel.setPreferredSize(
							new Dimension((int) leftPanel.getPreferredSize().getWidth() - Const.PREFERSIZE_MARGIN,
									(int) leftPanel.getPreferredSize().getHeight()));
					scrollPane.setVisible(true);
					repaint();
				} else if (e.getNewState() == 1 || e.getNewState() == 7) {// ������С��

				}

			}
		});

	
	}

	private static int getScreenWidth() {
		return (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	}

	private static int getScreenHeight() {
		return (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();

	}

	// ��������
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
			Dispatch.call(sapo, "Speak", new Variant(str));// "��С��С���ѣ����Ϻ�"

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sapo.safeRelease();
			sap.safeRelease();
		}
	}

	

}
