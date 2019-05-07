package com.dx.login;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.dx.FaceMainFrame;
import com.dx.pojo.Faceadmin;
import com.dx.service.FaceadminImpl;
public class Login extends JFrame {
	private JLabel userLabel;
	private JLabel passLabel;
	private JLabel ccLable;
	private JButton exit;
	private JButton login;
	private static Faceadmin fadmin;
	public Login() {
		setTitle("登录人证合一系统");
		ImageIcon icon=new ImageIcon("D:\\faceimages\\opencv\\img/face.png");  //img/face.png图片不能被压缩到jar中（即使打成jarbao也不管用），单独用绝对地址隔离出来
		this.setIconImage(icon.getImage());
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		int x = (int)toolkit.getScreenSize().getWidth();
		int y = (int)toolkit.getScreenSize().getHeight();
		

		final JPanel panel = new LoginPanel();
		panel.setLayout(null);
		getContentPane().add(panel);
		setBounds(300, 200, panel.getWidth(), panel.getHeight());
		this.setLocation(x/2-panel.getWidth()/2, y*2/5-panel.getHeight()/2);
		//System.out.println(panel.getWidth()+"     +    "+panel.getHeight());
		
		userLabel = new JLabel();
		userLabel.setText("用户名：");
		userLabel.setBounds(170, 195, 200, 25);
		userLabel.setFont(new Font("", Font.PLAIN, 14));
		panel.add(userLabel);
		
		
		final JTextField userName = new JTextField();
		userName.setBounds(220, 195, 200, 25);
		userName.setFont(new Font("", Font.PLAIN, 14));
//		userName.setPreferredSize(new Dimension(200, 30));
		panel.add(userName);
		
		
		passLabel = new JLabel();
		passLabel.setText("密  码：");
		passLabel.setBounds(170, 225, 200, 25);
		passLabel.setFont(new Font("", Font.PLAIN, 14));
		panel.add(passLabel);
		

		final JPasswordField userPassword = new JPasswordField();
		userPassword.addKeyListener(new KeyAdapter() {
			public void keyPressed(final KeyEvent e) {
				if (e.getKeyCode() == 10)
					login.doClick();
			}
		});
		userPassword.setBounds(220, 225, 200, 25);
		userPassword.setFont(new Font("", Font.PLAIN, 14));
//		userPassword.setPreferredSize(new Dimension(200, 30));
		panel.add(userPassword);
		
		ccLable = new JLabel();
		ccLable.setText("场  次：");
		ccLable.setBounds(170, 255, 200, 25);
		ccLable.setFont(new Font("", Font.PLAIN, 14));
		panel.add(ccLable);
		
		
		JComboBox ccBox = new JComboBox();
		String[] cc = new String[] {"职业道德","职业能力","岗位技能"};
		ccBox.setModel(new DefaultComboBoxModel(cc));
		ccBox.setFont(new Font("", Font.PLAIN, 14));
		ccBox.setBounds(220, 255, 200, 25);
		panel.add(ccBox);
		
		
		login = new JButton();
		login.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				fadmin = new FaceadminImpl().loginAdmin(userName.getText(), new String(userPassword.getPassword()),
						ccBox.getSelectedItem()+"");
				if (fadmin.getBmname() == null || fadmin.getBmpass() == null) {
					userName.setText(null);
					userPassword.setText(null);
					JOptionPane.showMessageDialog(null, "用户名或密码错误", "提示消息",JOptionPane.WARNING_MESSAGE);  
					return;
				}
				setVisible(false);
				new FaceMainFrame();
			}
		});
		login.setText("登录");
		login.setBounds(220, 305, 60, 25);
		panel.add(login);
		exit = new JButton();
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				System.exit(0);
			}
		});
		exit.setText("退出");
		exit.setBounds(320, 305, 60, 25);
		panel.add(exit);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	}
	public static Faceadmin getFadmin() {
		return fadmin;
	}
	public static void setFadmin(Faceadmin fadmin) {
		Login.fadmin = fadmin;
	}
	
	public static void main(String[] args) {
		int[] all = new int[33];
		int num = 1;
		for (int i : all) {
			all[i] = num++;
		}
		
		
	}
}
