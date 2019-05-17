package com.dx.test.camera;



import javax.swing.JFrame;

/**
 * @author fang
 *
 * @Date 2019年5月16日
 *
 * 项目名 FaceRecongnition
 *
 *@version 1.0
 */
public class CameraFrame {
     public static void main(String[] args) {
        //ThreadProgram panel=new ThreadProgram(); 
       CoreCamera panel1=new CoreCamera();
       JFrame frame= CameraUtil.getJFrame(panel1);
       if(!frame.isShowing()) {
    	   System.out.println("+++++++++++++++++");
    	   panel1.getMainThread().interrupt();
    	   
       }
         
	}
}
