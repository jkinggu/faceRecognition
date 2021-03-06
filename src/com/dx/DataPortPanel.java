package com.dx;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTable;

import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

import javax.swing.border.Border;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.common.util.ZipUtils;
import com.dx.dao.DBHelper;
import com.dx.frame.JProgressBarDemo;
import com.dx.inter.BelongforInterface;
import com.dx.inter.FaceLogsInterface;
import com.dx.inter.ParamSetupInterface;
import com.dx.inter.ZkzInterface;
import com.dx.pojo.Belongfor;
import com.dx.pojo.FaceLog;
import com.dx.pojo.ParamSetup;
import com.dx.pojo.Zkzdata;
import com.dx.service.BelongforImpl;
import com.dx.service.ExportFileService;
import com.dx.service.FaceLogsImpl;
import com.dx.service.ParamSetupImpl;
import com.dx.service.ZkzInterImpl;
import com.dx.util.Const;

/**
 * 数据导入导出
 * 初始数据导入，本机数据导出，分级数据汇总，
 * 数据流动方式，均以xml文件格式来操作
 * 
 */
public class DataPortPanel extends JPanel{
	
	private JButton button;
	private JButton button1;
	private JButton button2;
	private JButton button3;
	private JButton button4;
	private JButton button5;
	private JTable table;
	private DefaultTableModel dftm;
	private FaceLogsInterface impl = new FaceLogsImpl();
	private ZkzInterface zkzimpl = new ZkzInterImpl();
	private BelongforInterface befimpl = new BelongforImpl();
	private ParamSetupInterface paseimpl = new ParamSetupImpl();
	//JProgressBarDemo example=new JProgressBarDemo(0);//添加文件大小，设置进度条显示时间长短 ;
	//private long filesize ;
	private JProgressBar progressBar = null ;
	public DataPortPanel() {
		try {
			Border titleBorder1 = BorderFactory.createTitledBorder("数据导入导出");
			setBorder(titleBorder1);
//				GridBagLayout gridBagLayout = new GridBagLayout();
//				gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
//				setLayout(gridBagLayout);
				//setBounds(100, 0, 301, 331);
			
			button = new JButton();
			final GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridy = 0;
			gridBagConstraints.gridx = 0;
			button.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			button.setText("初始数据导入(报名)");
			add(button, gridBagConstraints);
			
			button1 = new JButton();
			final GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridy = 2;
			gridBagConstraints1.gridx = 0;
			button1.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			button1.setText("本机数据导出");
			add(button1, gridBagConstraints1);
			
			button2 = new JButton();
			final GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridy = 4;
			gridBagConstraints2.gridx = 0;
			button2.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			button2.setText("分机数据汇总");
			add(button2, gridBagConstraints2);
			
			button3 = new JButton();
			final GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridy = 6;
			gridBagConstraints3.gridx = 0;
			button3.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			button3.setText("总数据备份");
			add(button3, gridBagConstraints3);
			
			button4 = new JButton();
			final GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridy = 8;
			gridBagConstraints4.gridx = 0;
			button4.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			button4.setText("总数据恢复");
			add(button4, gridBagConstraints4);
			
			button5 = new JButton();
			final GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridy = 8;
			gridBagConstraints5.gridx = 0;
			button5.setFont(new Font(Const.FONT_TYPE, Font.PLAIN, Const.FONT_SIZE));
			button5.setText("初始数据导入(考务)");
			add(button5, gridBagConstraints5);
			
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JFileChooser jfc=new JFileChooser();  
					jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );  
					jfc.showDialog(new JLabel(), "选择");  
					File file=jfc.getSelectedFile();  
					if(file.isDirectory()){  
						//System.out.println("文件夹:"+file.getAbsolutePath());  
						JOptionPane.showMessageDialog(null, "请选择对应文件！" , "文件选择",JOptionPane.WARNING_MESSAGE);
	            		return ;
					}else if(file.isFile()){  
						System.out.println("文件:"+file.getAbsolutePath()); 
						String urlpath = file.getAbsolutePath() ;//文件的绝对路径
						if(urlpath.endsWith(".xml")) {
							//文件的导入，初始数据文件来源于工勤报名系统，各地市的数据自己导出接入，暂时搁置，未写
							SAXReader reader = new SAXReader();  
							Document document = null;
							try {
								document = reader.read(file);
							} catch (DocumentException e1) {
								e1.printStackTrace();
							} 
							Element root = document.getRootElement();
							List<Element> list = root.elements();	
							//文件导入
							String msg = importInitXml(list);
							if(msg.equals("error")) {
								JOptionPane.showMessageDialog(null, "文件格式不正确，请选择正确的xml文件" , "文件导入",JOptionPane.WARNING_MESSAGE);
							}else {
								JOptionPane.showMessageDialog(null, "导入成功，共导入了"+list.size()+"条数据, 其中导入考生"+msg+"人" , "文件导入",JOptionPane.INFORMATION_MESSAGE);
							}				
							
						}else {
							JOptionPane.showMessageDialog(null, "文件格式不正确，请选择正确的xml文件" , "文件选择",JOptionPane.WARNING_MESSAGE);
						}
//						if(urlpath.endsWith(".zip")) {
//							//图片
//							File f = new File("");
//							String realName = getDateFormat("yyyyMMddHHmmssSS")+ ".zip";
//							String cpath=f.getAbsolutePath()+"/zkzpho/";//解压路径，获取当前运行程序下的zkzpho的路径
//							String phoName =  cpath+ realName;
//							File myfile = new File(urlpath) ;
//							copy(myfile, new File(phoName),	Integer.parseInt(myfile.length() + ""));
//							
//							ZipUtils zu = new ZipUtils();
//							String folder = phoName.substring(0, phoName.length() - 4);
//							zu.makeZipFile(phoName, folder);
//							copyFolder(new File(folder+"/1/"),new File(cpath));
//							
//							
//							//xml内容
//							File xmlfile=new File(folder+"/1.xml");
//							getXmlList(xmlfile);
//							
//							
//						}else {
//							JOptionPane.showMessageDialog(null, "文件格式不正确，请选择正确的xml文件" , "文件选择",JOptionPane.WARNING_MESSAGE);
//						}
					}
					
				}
			});
			button1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//导出xml文件
					List<FaceLog> list = impl.getRenZhengList("","","","","","",null,null);
					export(list);
				}
			});
			button2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						JFileChooser jfc=new JFileChooser();  
						jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );  
						jfc.showDialog(new JLabel(), "选择");  
						File file=jfc.getSelectedFile();  
						if(file.isDirectory()){  
							//System.out.println("文件夹:"+file.getAbsolutePath());  
							JOptionPane.showMessageDialog(null, "请选择对应文件！" , "文件导入",JOptionPane.WARNING_MESSAGE);
							return ;
						}else if(file.isFile()){  
							String urlpath = file.getAbsolutePath() ;//文件的绝对路径
							//System.out.println("文件:"+file.getAbsolutePath()+"-------------"+urlpath.endsWith(".xml")); 
							if(urlpath.endsWith(".xml")) {
								//分机数据的导出的xml文件，再接入到同一个数据库
								//读取文件 转换成Document  
								new Thread() {
									public void run() { // 你的事情都放在这里处理
										JProgressBarDemo example1=new JProgressBarDemo(100);
										example1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

										SAXReader reader = new SAXReader();  
										Document document = null;
										try {
											document = reader.read(file);
										} catch (DocumentException e1) {
											e1.printStackTrace();
										} 
										Element root = document.getRootElement();
										List<Element> list = root.elements();	
										//文件导入
										String msg = importXml(list);
										if(msg.equals("error")) {
											JOptionPane.showMessageDialog(null, "文件格式不正确，请选择正确的xml文件" , "文件导入",JOptionPane.WARNING_MESSAGE);
										}else {
											JOptionPane.showMessageDialog(null, "导入成功，共导入了"+list.size()+"条数据" , "文件导入",JOptionPane.INFORMATION_MESSAGE);
										}
										
									}
								}.start() ; 
								
								
							}else {
								JOptionPane.showMessageDialog(null, "文件格式不正确，请选择正确的xml文件" , "文件导入",JOptionPane.WARNING_MESSAGE);
							}
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					
				}

				
			});
			button3.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//汇总数据备份导出
					List<Belongfor>  beflist = befimpl.getBelongforList();
					List<FaceLog> loglist = impl.getRenZhengList("","","","","","",null,null);
					List<ParamSetup> parsetlist = paseimpl.getShezhiList("", "", "", "", "", "","all");
					List<Zkzdata> zkzlist = zkzimpl.getZkzList();
					exportAllData(beflist,loglist, parsetlist, zkzlist);
					
				}
			});
			button4.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//汇总数据恢复导入
					JFileChooser jfc=new JFileChooser();  
					jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );  
					jfc.showDialog(new JLabel(), "选择");  
					File file=jfc.getSelectedFile();  
					if(file.isDirectory()){  
						//System.out.println("文件夹:"+file.getAbsolutePath());  
						JOptionPane.showMessageDialog(null, "请选择对应文件！" , "文件选择",JOptionPane.WARNING_MESSAGE);
	            		return ;
					}else if(file.isFile()){  
						System.out.println("文件:"+file.getAbsolutePath()); 
						String urlpath = file.getAbsolutePath() ;//文件的绝对路径
						if(urlpath.endsWith(".xml")) {
							
							new Thread() {
								public void run() { // 你的事情都放在这里处理
									JProgressBarDemo example1=new JProgressBarDemo(100);
									example1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

									SAXReader reader = new SAXReader();  
									Document document = null;
									try {
										document = reader.read(file);
									} catch (DocumentException e1) {
										e1.printStackTrace();
									} 
									Element root = document.getRootElement();
									List<Element> list = root.elements();	
									//文件导入
									String msg = importAllData(list);
									if(msg.equals("error")) {
										JOptionPane.showMessageDialog(null, "文件格式不正确，请选择正确的xml文件" , "文件导入",JOptionPane.WARNING_MESSAGE);
									}else {
										JOptionPane.showMessageDialog(null, "导入成功，共导入了"+list.size()+"条数据" , "文件导入",JOptionPane.INFORMATION_MESSAGE);
									}
									
								}
							}.start() ; 
							
							
							
							
						}else {
							JOptionPane.showMessageDialog(null, "文件格式不正确，请选择正确的xml文件" , "文件选择",JOptionPane.WARNING_MESSAGE);
						}
					}
					
				}
			});
			//这个初始数据导入来源于工考考务软件，导入格式为zip，有照片和文件
			button5.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//汇总数据恢复导入
					JFileChooser jfc=new JFileChooser();  
					jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );  
					jfc.showDialog(new JLabel(), "选择");  
					File file=jfc.getSelectedFile();  
					if(file.isDirectory()){  
						//System.out.println("文件夹:"+file.getAbsolutePath());  
						JOptionPane.showMessageDialog(null, "请选择对应文件！" , "文件选择",JOptionPane.WARNING_MESSAGE);
	            		return ;
					}else if(file.isFile()){  
						//System.out.println("文件:"+file.getAbsolutePath()); 
						String urlpath = file.getAbsolutePath() ;//文件的绝对路径
						if(urlpath.endsWith(".zip")) {
							new Thread() {
								public void run() { // 你的事情都放在这里处理 
									//图片
									File f = new File("");
									String realName = getDateFormat("yyyyMMddHHmmssSS")+ ".zip";
									String cpath=f.getAbsolutePath()+"/zkzpho/";//解压路径，获取当前运行程序下的zkzpho的路径
									String phoName =  cpath+ realName;
									File myfile = new File(urlpath) ;
									JProgressBarDemo example1=new JProgressBarDemo(15000);//时间两分半
									example1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
									
									copy(myfile, new File(phoName),	Integer.parseInt(myfile.length() + ""));
									
									ZipUtils zu = new ZipUtils();
									String folder = phoName.substring(0, phoName.length() - 4);
									
									//添加进度条
//							new ProgressBarRealized().execute();
									
									
									zu.makeZipFile(phoName, folder);
									File phofile = new File(folder+"/1/") ;//图片
									File xmlfile=new File(folder+"/1.xml");//xml文件
									
//									example1.setVisible(false);
//									
//									JProgressBarDemo example2=new JProgressBarDemo(phofile.length()+xmlfile.length());//添加文件大小，设置进度条显示时间长短
									//example.setFilesize(phofile.length()+xmlfile.length());
									
									
									System.out.println("---------------");
									//图片复制
									copyFolder(phofile,new File(cpath));
									//xml内容
									getXmlList(xmlfile);
									
								}
							}.start() ; 
							
							
							//example = null ;
							
						}else {
							JOptionPane.showMessageDialog(null, "文件格式不正确，请选择正确的xml文件" , "文件选择",JOptionPane.WARNING_MESSAGE);
						}
					}
					
				}
			});
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

//		JProgressBar progressBar=new JProgressBar();
//    	progressBar.setStringPainted(true);
//    	new Thread(){
//    		public void run(){
//    			for(int i=0;i<=100;i++){
//    				try{
//    					Thread.sleep(100);
//    				}catch(InterruptedException e){
//    					e.printStackTrace();
//    				}
//    			      progressBar.setValue(i);
//    			}
//    			progressBar.setString("导入完毕！");
//    		}
//    		
//    		
//    	}.start();
//    	add(progressBar);
//    	setVisible(true);
	}
//	class ProgressBarRealized extends SwingWorker<Void, Integer> {
//		@Override
//		//后台任务在此方法中实现
//		protected Void doInBackground() throws Exception {
//		// 模拟有一百项任务，每次睡1s
//		for (int i = 0; i < 100; i++) {
//		    Thread.sleep(1000);
//		     publish(i);//将当前进度信息加入chunks中
//		}
//		   return null;
//		}
//		 @Override
//		//每次更新进度条的信息
//		protected void process(List<Integer> chunks) {
//		     progressBar.setValue(chunks.get(chunks.size() - 1));
//		}
//		@Override
//		//任务完成后返回一个信息
//		protected void done() {
//		    JOptionPane.showMessageDialog(null, "导入完毕！");
//		}
//	}
	
	protected void getXmlList(File xmlfile) {
//		JProgressBarDemo example=new JProgressBarDemo();
		
		SAXReader reader = new SAXReader();  
		Document document = null;
		try {
			document = reader.read(xmlfile);
		} catch (DocumentException e1) {
			e1.printStackTrace();
		} 
		Element root = document.getRootElement();
		List<Element> list = root.elements();	
		//文件导入
		String msg = importInitXmlkaowu(list);
		if(msg.equals("error")) {
			JOptionPane.showMessageDialog(null, "文件格式不正确，请选择正确的xml文件" , "文件导入",JOptionPane.WARNING_MESSAGE);
		}else {
			JOptionPane.showMessageDialog(null, "导入成功，共导入了"+list.size()+"条数据，其中导入考生"+msg+"人" , "文件导入",JOptionPane.INFORMATION_MESSAGE);
		}	
		
		
		
	}
	/**
	 * 复制一个目录及其子目录、文件到另外一个目录
	 * @param src
	 * @param dest
	 * @throws IOException
	 */
	public void copyFolder(File src, File dest)  {
		try {
			
			if (src.isDirectory()) {
				if (!dest.exists()) {
					dest.mkdir();
				}
				String files[] = src.list();
				for (String file : files) {
					File srcFile = new File(src, file);
					File destFile = new File(dest, file);
					// 递归复制
					copyFolder(srcFile, destFile);
				}
			} else {
				InputStream in = new FileInputStream(src);
				OutputStream out = new FileOutputStream(dest);
				
				byte[] buffer = new byte[1024];
				
				int length;
				
				while ((length = in.read(buffer)) > 0) {
					out.write(buffer, 0, length);
				}
				in.close();
				out.close();
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String getDateFormat(String fm)
	  {
	    SimpleDateFormat sdf = new SimpleDateFormat(fm);
	    return sdf.format(new Date());
	}
	public void copy(File src, File dst, int SIZE)
	  {
	    try {
	      InputStream in = null;
	      OutputStream out = null;
	      try {
	        in = new BufferedInputStream(new FileInputStream(src), 
	          SIZE);
	        out = new BufferedOutputStream(new FileOutputStream(dst), 
	          SIZE);
	        byte[] buffer = new byte[SIZE];
	        while (in.read(buffer) > 0)
	          out.write(buffer);
	      }
	      finally {
	        if (in != null) {
	          in.close();
	        }
	        if (out != null)
	          out.close();
	      }
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	  }
	
	
	
	/**这个里面的数据是备份出来的数据*/
	protected String importAllData(List<Element> list) {
		String msg = "" ;
		Connection conn = DBHelper.getConn();
		try {
			conn.setAutoCommit(false);
			for(int i=0; i<list.size() ;i++) {
				Element node0 = list.get(i);
				if(node0.getName().equalsIgnoreCase("Zkzdata")) {
					List<Element> zkze = node0.elements() ;
					if(zkze.size() == 29) {//这个长度才是完整的我们需要的数据
						Zkzdata zkzdata = new Zkzdata();
						String id = zkze.get(0).getText() ;
						String xingming = zkze.get(1).getText() ;
						String xingbie = zkze.get(2).getText() ;
						String nianlin = zkze.get(3).getText() ;
						String zkznum = zkze.get(4).getText() ;
						String upersonnum = zkze.get(5).getText() ;
						String danweiid = zkze.get(6).getText() ;
						String danweiname = zkze.get(7).getText() ;
						String baokaocode = zkze.get(8).getText() ;
						String baokaoname = zkze.get(9).getText() ;
						String jbcode = zkze.get(10).getText() ;
						String jbname = zkze.get(11).getText() ;
						String kd1 = zkze.get(12).getText() ;
						String kc1 = zkze.get(13).getText() ;
						String zh1 = zkze.get(14).getText() ;
						String sj1 = zkze.get(15).getText() ;
						String dd1 = zkze.get(16).getText() ;
						String kd2 = zkze.get(17).getText() ;
						String kc2 = zkze.get(18).getText() ;
						String zh2 = zkze.get(19).getText() ;
						String sj2 = zkze.get(20).getText() ;
						String dd2 = zkze.get(21).getText() ;
						String kd3 = zkze.get(22).getText() ;
						String kc3 = zkze.get(23).getText() ;
						String zh3 = zkze.get(24).getText() ;
						String sj3 = zkze.get(25).getText() ;
						String dd3 = zkze.get(26).getText() ;
						String zmarks = zkze.get(27).getText() ;
						String dishiname = zkze.get(28).getText() ;
						
						zkzdata.setXingming(xingming);
						zkzdata.setXingbie(xingbie);
						zkzdata.setNianlin(nianlin);
						zkzdata.setZkznum(zkznum);
						zkzdata.setUpersonnum(upersonnum);
						zkzdata.setDanweiid(danweiid);
						zkzdata.setDanweiname(danweiname);
						zkzdata.setBaokaocode(baokaocode);
						zkzdata.setBaokaoname(baokaoname);
						zkzdata.setJbcode(jbcode);
						zkzdata.setJbname(jbname);
						zkzdata.setKd1(kd1);
						zkzdata.setKc1(kc1);
						zkzdata.setZh1(zh1);
						zkzdata.setSj1(sj1);
						zkzdata.setDd1(dd1);
						zkzdata.setKd2(kd2);
						zkzdata.setKc2(kc2);
						zkzdata.setZh2(zh2);
						zkzdata.setSj2(sj2);
						zkzdata.setDd2(dd2);
						zkzdata.setKd3(kd3);
						zkzdata.setKc3(kc3);
						zkzdata.setZh3(zh3);
						zkzdata.setSj3(sj3);
						zkzdata.setDd3(dd3);
						zkzdata.setZmarks(zmarks);
						zkzdata.setDishiname(dishiname);
						
						Integer num = zkzimpl.insertZkz(zkzdata,conn);
					}else {
						msg = "error" ;
						break ;
					}
					
				}else if(node0.getName().equalsIgnoreCase("Belongfor")) {
					//保存单位信息
					List<Element> belongfor = node0.elements() ;
					if(belongfor.size() == 11) {//这个长度为单位的数据
						Belongfor bef = new Belongfor();
						String allcode = belongfor.get(0).getText() ;
						String no1code =  belongfor.get(1).getText() ;
						String no1 =  belongfor.get(2).getText() ;
						String no2code =  belongfor.get(3).getText() ;
						String no2 =  belongfor.get(4).getText() ;
						String no3code =  belongfor.get(5).getText() ;
						String no3 =  belongfor.get(6).getText() ;
						String no4code =  belongfor.get(7).getText() ;
						String no4 =  belongfor.get(8).getText() ;
						String id = belongfor.get(9).getText() ;
						String leibie =  belongfor.get(10).getText() ;
						bef.setAllcode(allcode);
						bef.setNo1code(no1code);
						bef.setNo1name(no1);
						bef.setNo2code(no2code);
						bef.setNo2name(no2);
						bef.setNo3code(no3code);
						bef.setNo3name(no3);
						bef.setNo4code(no4code);
						bef.setNo4name(no4);
						bef.setLeibie(leibie);
						
						Integer num = zkzimpl.insertBelf(bef,conn);
						
					}
				}else if(node0.getName().equalsIgnoreCase("ParamSetup")) {
					//认证参数
					List<Element> paramset = node0.elements() ;
					if(paramset.size()==12) {
						ParamSetup param = new ParamSetup();
						String pid = paramset.get(0).getText();
						String dishi = paramset.get(1).getText();
						String dishiname = paramset.get(2).getText() ;
						String kaodianname = paramset.get(3).getText() ;
						String kaochang = paramset.get(4).getText() ;
						String starttime = paramset.get(5).getText() ;
						String endtime  = paramset.get(6).getText() ;
						String remaks = paramset.get(7).getText();
						if(remaks.equals("null")) {
							remaks = "" ;
						}
						String adminname = paramset.get(8).getText() ;
						String adminid  = paramset.get(9).getText() ;
						String didian = paramset.get(10).getText() ;
						String ustatu = paramset.get(11).getText();
						param.setDishi(dishi);
						param.setDishiname(dishiname);
						param.setKaodianname(kaodianname);
						param.setKaochang(kaochang);
						param.setStarttime(starttime);
						param.setEndtime(endtime);
						param.setRemarks(remaks);
						param.setAdminname(adminname);
						param.setAdminid(Integer.parseInt(adminid));
						param.setDidian(didian);
						param.setUstatu(ustatu);
						
						Integer num = paseimpl.insertParamset(param);
					}
					
					
				}else if(node0.getName().equalsIgnoreCase("FaceLog")) {
					//认证记录表
					List<Element> logs = node0.elements() ;
					if(logs.size() == 13) {
						FaceLog faceLog  = new FaceLog();
						String id = logs.get(0).getText() ;
						String sfz = logs.get(1).getText() ;
						String xingming = logs.get(2).getText() ;
						String xingbie = logs.get(3).getText() ;
						String shibieleixing = logs.get(4).getText() ;
						String shibieleixingint = logs.get(5).getText() ;
						String shijian = logs.get(6).getText() ;
						String renlianphoto = logs.get(7).getText() ;
						String remarks = logs.get(8).getText() ;
						String sfzphoto = logs.get(9).getText() ;
						String changci = logs.get(10).getText() ;
						String denglumana = logs.get(11).getText() ;
						String renzcount = logs.get(12).getText() ;
						
						faceLog.setSfz(sfz);
						faceLog.setXingming(xingming);
						faceLog.setXingbie(xingbie);
						faceLog.setShibieleixing(shibieleixing);
						faceLog.setShibieleixingint(shibieleixingint);
						faceLog.setShijian(shijian);
						faceLog.setRenlianphoto(renlianphoto);
						faceLog.setRemarks(remarks);
						faceLog.setSfzphoto(sfzphoto);
						faceLog.setChangci(changci);
						faceLog.setDenglumana(denglumana);
						if(!"null".equals(renzcount))
							faceLog.setRenzcount(renzcount);
						impl.insertFaceLogs(faceLog);
					}
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				conn.commit();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return msg ;
	}
	private String importInitXml(List<Element> list) {
		String msg = "" ;
		Integer kscount = 0 ;
		//开启事务
		Connection conn = DBHelper.getConn();
		try {
			conn.setAutoCommit(false);
			for(int i=0; i<list.size() ;i++) {
				Element node0 = list.get(i);
				//System.out.println("nodename      "+node0.getName());
				if(node0.getName().equalsIgnoreCase("Zkzdata")) {
					List<Element> zkze = node0.elements() ;
					if(zkze.size() == 28) {//这个长度才是完整的我们需要的数据
						kscount = kscount +1 ;
						Zkzdata zkzdata = new Zkzdata();
						//String id = zkze.get(0).getText() ;
						String xingming = zkze.get(1).getText() ;
						String xingbie = zkze.get(2).getText() ;
						String nianlin = zkze.get(3).getText() ;
						String zkznum = zkze.get(4).getText() ;
						String upersonnum = zkze.get(5).getText() ;
						String danweiid = zkze.get(6).getText() ;
						String danweiname = zkze.get(7).getText() ;
						String baokaocode = zkze.get(8).getText() ;
						String baokaoname = zkze.get(9).getText() ;
						String jbcode = zkze.get(10).getText() ;
						String jbname = zkze.get(11).getText() ;
						String kd1 = zkze.get(12).getText() ;
						String kc1 = zkze.get(13).getText() ;
						String zh1 = zkze.get(14).getText() ;
						String sj1 = zkze.get(15).getText() ;
						String dd1 = zkze.get(16).getText() ;
						String kd2 = zkze.get(17).getText() ;
						String kc2 = zkze.get(18).getText() ;
						String zh2 = zkze.get(19).getText() ;
						String sj2 = zkze.get(20).getText() ;
						String dd2 = zkze.get(21).getText() ;
						String kd3 = zkze.get(22).getText() ;
						String kc3 = zkze.get(23).getText() ;
						String zh3 = zkze.get(24).getText() ;
						String sj3 = zkze.get(25).getText() ;
						String dd3 = zkze.get(26).getText() ;
//						String zmarks = zkze.get(27).getText() ;
//						String zkzpho = zkze.get(28).getText() ;
//						String sfzpho = zkze.get(29).getText() ;
//						String dishiname = zkze.get(30).getText() ;
						
						zkzdata.setXingming(xingming);
						zkzdata.setXingbie(xingbie);
						zkzdata.setNianlin(nianlin);
						zkzdata.setZkznum(zkznum);
						zkzdata.setUpersonnum(upersonnum);
						zkzdata.setDanweiid(danweiid);
						zkzdata.setDanweiname(danweiname);
						zkzdata.setBaokaocode(baokaocode);
						zkzdata.setBaokaoname(baokaoname);
						zkzdata.setJbcode(jbcode);
						zkzdata.setJbname(jbname);
						zkzdata.setKd1(kd1);
						zkzdata.setKc1(kc1);
						zkzdata.setZh1(zh1);
						zkzdata.setSj1(sj1);
						zkzdata.setDd1(dd1);
						zkzdata.setKd2(kd2);
						zkzdata.setKc2(kc2);
						zkzdata.setZh2(zh2);
						zkzdata.setSj2(sj2);
						zkzdata.setDd2(dd2);
						zkzdata.setKd3(kd3);
						zkzdata.setKc3(kc3);
						zkzdata.setZh3(zh3);
						zkzdata.setSj3(sj3);
						zkzdata.setDd3(dd3);
//						zkzdata.setZmarks(zmarks);
//						zkzdata.setZkzpho(zkzpho);
//						zkzdata.setSfzpho(sfzpho);
//						zkzdata.setDishiname(dishiname);
						
						Integer num = zkzimpl.insertZkz(zkzdata,conn);
					}else {
						msg = "error" ;
						break ;
					}
					
				}else if(node0.getName().equalsIgnoreCase("Belongfor")) {
					//保存单位信息
					List<Element> belongfor = node0.elements() ;
					if(belongfor.size() == 10) {//这个长度为单位的数据
						Belongfor bef = new Belongfor();
						String allcode = belongfor.get(0).getText() ;
						String no1code =  belongfor.get(1).getText() ;
						String no1 =  belongfor.get(2).getText() ;
						String no2code =  belongfor.get(3).getText() ;
						String no2 =  belongfor.get(4).getText() ;
						String no3code =  belongfor.get(5).getText() ;
						String no3 =  belongfor.get(6).getText() ;
						String no4code =  belongfor.get(7).getText() ;
						String no4 =  belongfor.get(8).getText() ;
						String leibie =  belongfor.get(9).getText() ;
						bef.setAllcode(allcode);
						bef.setNo1code(no1code);
						bef.setNo1name(no1);
						bef.setNo2code(no2code);
						bef.setNo2name(no2);
						bef.setNo3code(no3code);
						bef.setNo3name(no3);
						bef.setNo4code(no4code);
						bef.setNo4name(no4);
						bef.setLeibie(leibie);
						
						Integer num = zkzimpl.insertBelf(bef,conn);
						
					}
					
					
					
					
				}
				msg = kscount+"" ;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				conn.commit();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return msg ;
	}
	/**分机数据导入汇总*/
	private String importXml(List<Element> list) {
		String msg = "" ;
		for(int i=0; i<list.size() ;i++) {
			FaceLog faceLog  = new FaceLog();
			Element node0 = list.get(i);
			List<Element> logs = node0.elements() ;
			if(logs.size() == 13) {
				String id = logs.get(0).getText() ;
				String sfz = logs.get(1).getText() ;
				String xingming = logs.get(2).getText() ;
				String xingbie = logs.get(3).getText() ;
				String shibieleixing = logs.get(4).getText() ;
				String shibieleixingint = logs.get(5).getText() ;
				String shijian = logs.get(6).getText() ;
				String renlianphoto = logs.get(7).getText() ;
				String remarks = logs.get(8).getText() ;
				String sfzphoto = logs.get(9).getText() ;
				String changci = logs.get(10).getText() ;
				String denglumana = logs.get(11).getText() ;
				String renzcount = logs.get(12).getText() ;
				
				faceLog.setSfz(sfz);
				faceLog.setXingming(xingming);
				faceLog.setXingbie(xingbie);
				faceLog.setShibieleixing(shibieleixing);
				faceLog.setShibieleixingint(shibieleixingint);
				faceLog.setShijian(shijian);
				faceLog.setRenlianphoto(renlianphoto);
				faceLog.setRemarks(remarks);
				faceLog.setSfzphoto(sfzphoto);
				faceLog.setChangci(changci);
				faceLog.setDenglumana(denglumana);
				if(!"null".equals(renzcount))
					faceLog.setRenzcount(renzcount);
				impl.insertFaceLogs(faceLog);
			}else {
				msg = "error" ;
				break ;
			}
			
		}
		return msg ;
	}
	private void export(List list) {  
	    MyFileFilterXML xmlFilter = new MyFileFilterXML();  
	    List<FileFilter> filters = new ArrayList<FileFilter>();  
	    filters.add(xmlFilter);  
	    boolean createFileRs = false;  
	    String[] pathAndTypeStr = ExportFileService.exportFile(filters);  
	    if(pathAndTypeStr==null){  
	        return ;  
	    }  
	    pathAndTypeStr[0] = pathAndTypeStr[0] + ".xml";  
	    createFileRs = ExportFileService.createXML(pathAndTypeStr[0], list);  
	    JOptionPane.showMessageDialog(null, "文件导出"  
	            + (createFileRs ? "成功" : "失败"), "提示",  
	            JOptionPane.INFORMATION_MESSAGE);  
	} 
	/**总数居备份导出
	 * @param zkzlist 
	 * @param parsetlist 
	 * @param loglist 
	 * @param beflist */
	protected void exportAllData(List<Belongfor> beflist, List<FaceLog> loglist, List<ParamSetup> parsetlist, List<Zkzdata> zkzlist) {
		MyFileFilterXML xmlFilter = new MyFileFilterXML();  
	    List<FileFilter> filters = new ArrayList<FileFilter>();  
	    filters.add(xmlFilter);  
	    boolean createFileRs = false;  
	    String[] pathAndTypeStr = ExportFileService.exportFile(filters);  
	    if(pathAndTypeStr==null){  
	        return ;  
	    }  
	    pathAndTypeStr[0] = pathAndTypeStr[0] + ".xml";  
	    createFileRs = ExportFileService.createXMLAllData(pathAndTypeStr[0], beflist,loglist,parsetlist,zkzlist);  
	    JOptionPane.showMessageDialog(null, "文件导出"  
	            + (createFileRs ? "成功" : "失败"), "提示",  
	            JOptionPane.INFORMATION_MESSAGE);
		
	}
	
	
	class MyFileFilterXML extends javax.swing.filechooser.FileFilter {  
	      
		private List<String> ext=new ArrayList<String>();  
		      
		    public MyFileFilterXML(){  
		        ext.add(".XML");  
		        ext.add(".xml");  
		    }  
		    public List<String> getExt() {  
		        return ext;  
		    }  
		    public boolean accept(java.io.File pathname) {  
		        String fn = pathname.getAbsolutePath().toString();  
		        if (fn.length() > 4)  
		            fn = fn.substring(fn.length() - 4, fn.length());  
		        if (ext.contains(fn)|| pathname.isDirectory())  
		            return true;  
		        return false;  
		    }  
		  
		    public String getDescription() {  
		        return "XML文件(*.xml)";  
		    }  
		}

	private String importInitXmlkaowu(List<Element> list) {
		String msg = "" ;
		Integer stucount = 0 ;
		//开启事务
		Connection conn = DBHelper.getConn();
		try {
			conn.setAutoCommit(false);
			
			for(int i=0; i<list.size() ;i++) {
				Element node0 = list.get(i);
				//System.out.println("nodename      "+node0.getName());
				if(node0.getName().equalsIgnoreCase("Zkzdata")) {
					List<Element> zkze = node0.elements() ;
					if(zkze.size() == 26) {//这个长度才是完整的我们需要的数据
						stucount = stucount+1 ;
						Zkzdata zkzdata = new Zkzdata();
						//String id = zkze.get(0).getText() ;
						String xingming = zkze.get(0).getText() ;
						String xingbie = zkze.get(1).getText() ;
						String nianlin = zkze.get(2).getText() ;
						String zkznum = zkze.get(3).getText() ;
						String upersonnum = zkze.get(4).getText() ;
						String danweiid = zkze.get(5).getText() ;
						String danweiname = zkze.get(6).getText() ;
						String baokaocode = zkze.get(7).getText() ;
						String baokaoname = zkze.get(8).getText() ;
						String jbcode = zkze.get(9).getText() ;
						String jbname = zkze.get(10).getText() ;
						String kd1 = zkze.get(11).getText() ;
						String kc1 = zkze.get(12).getText() ;
						String zh1 = zkze.get(13).getText() ;
						String sj1 = zkze.get(14).getText() ;
						String dd1 = zkze.get(15).getText().trim() ;
						String kd2 = zkze.get(16).getText() ;
						String kc2 = zkze.get(17).getText() ;
						String zh2 = zkze.get(18).getText() ;
						String sj2 = zkze.get(19).getText() ;
						String dd2 = zkze.get(20).getText().trim() ;
						String kd3 = zkze.get(21).getText() ;
						String kc3 = zkze.get(22).getText() ;
						String zh3 = zkze.get(23).getText() ;
						String sj3 = zkze.get(24).getText() ;
						String dd3 = zkze.get(25).getText().trim() ;
//						String zmarks = zkze.get(27).getText() ;
//						String zkzpho = zkze.get(28).getText() ;
//						String sfzpho = zkze.get(29).getText() ;
//						String dishiname = zkze.get(30).getText() ;
						
						zkzdata.setXingming(xingming);
						zkzdata.setXingbie(xingbie);
						zkzdata.setNianlin(nianlin);
						zkzdata.setZkznum(zkznum);
						zkzdata.setUpersonnum(upersonnum);
						zkzdata.setDanweiid(danweiid);
						zkzdata.setDanweiname(danweiname);
						zkzdata.setBaokaocode(baokaocode);
						zkzdata.setBaokaoname(baokaoname);
						zkzdata.setJbcode(jbcode);
						zkzdata.setJbname(jbname);
						zkzdata.setKd1(kd1);
						zkzdata.setKc1(kc1);
						zkzdata.setZh1(zh1);
						zkzdata.setSj1(sj1);
						zkzdata.setDd1(dd1);
						zkzdata.setKd2(kd2);
						zkzdata.setKc2(kc2);
						zkzdata.setZh2(zh2);
						zkzdata.setSj2(sj2);
						zkzdata.setDd2(dd2);
						zkzdata.setKd3(kd3);
						zkzdata.setKc3(kc3);
						zkzdata.setZh3(zh3);
						zkzdata.setSj3(sj3);
						zkzdata.setDd3(dd3);
						
						Integer num = zkzimpl.insertZkz(zkzdata,conn);
					}else {
						msg = "error" ;
						break ;
					}
					
				}else if(node0.getName().equalsIgnoreCase("Belongfor")) {
					//保存单位信息
					List<Element> belongfor = node0.elements() ;
					if(belongfor.size() == 9) {//这个长度为单位的数据
						Belongfor bef = new Belongfor();
						String allcode = belongfor.get(0).getText() ;
						String no1code =  belongfor.get(1).getText() ;
						String no1 =  belongfor.get(2).getText() ;
						String no2code =  belongfor.get(3).getText() ;
						String no2 =  belongfor.get(4).getText() ;
						String no3code =  belongfor.get(5).getText() ;
						String no3 =  belongfor.get(6).getText() ;
						String no4code =  belongfor.get(7).getText() ;
						String no4 =  belongfor.get(8).getText() ;
						//String leibie =  belongfor.get(9).getText() ;
						bef.setAllcode(allcode);
						bef.setNo1code(no1code);
						bef.setNo1name(no1);
						bef.setNo2code(no2code);
						bef.setNo2name(no2);
						bef.setNo3code(no3code);
						bef.setNo3name(no3);
						bef.setNo4code(no4code);
						bef.setNo4name(no4);
						//bef.setLeibie(leibie);
						
						Integer num = zkzimpl.insertBelf(bef,conn);
						
					}
					
					
					
					
				}
				msg = stucount+"" ;//导入的考生数量
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				conn.commit();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return msg ;
	}
	
	
}
