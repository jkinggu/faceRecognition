package com.dx.test.camera;



import javax.swing.JFrame;

/**
 * @author fang
 *
 * @Date 2019��5��16��
 *
 * ��Ŀ�� FaceRecongnition
 *
 *@version 1.0
 */
public class CameraFrame {
     public static void main(String[] args) {
        //ThreadProgram panel=new ThreadProgram(); 
       CoreCamer panel1=new CoreCamer();
       JFrame frame= CamperUtil.getJFrame(panel1);
       if(!frame.isShowing()) {
    	   System.out.println("+++++++++++++++++");
    	   panel1.getMainThread().interrupt();
    	   
       }
         
	}
}
