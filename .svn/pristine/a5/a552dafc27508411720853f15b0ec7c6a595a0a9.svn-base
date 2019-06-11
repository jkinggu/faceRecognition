package com.dx.test.print;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JDialog;
import javax.swing.JOptionPane;


/**
 * @author Administrator
 *
 * @Date 2019年6月4日
 *
 * 项目名 FaceRecongnition
 *
 *@version 1.0
 */
public class AutoTimerTest {
	
	
	
	 public static void main(String[] args) {  
		  
	        JOptionPane op = new JOptionPane("本对话框将在3秒后关闭",JOptionPane.INFORMATION_MESSAGE);  
	        JDialog dialog = op.createDialog("服务器自检异常");  
	          
	        // 创建一个新计时器  
	        Timer timer = new Timer(true);  
	        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);  
	        dialog.setAlwaysOnTop(true);  
	        dialog.setModal(false);  
	        dialog.setVisible(true);  
	        
	        
	        // 3秒 后执行该任务  
	        timer.schedule(new TimerTask() {  
	            public void run() {  
	                dialog.setVisible(false);  
	                dialog.dispose();  
	            }  
	        }, 3000);  
	  
	      
	       // timer.cancel();
	    }  
	    
	
	
	

}
