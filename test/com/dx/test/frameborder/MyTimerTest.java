package com.dx.test.frameborder;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Administrator
 *
 * @Date 2019��6��14��
 *
 * ��Ŀ�� FaceRecongnition
 *
 *@version 1.0
 */
public class MyTimerTest {
  public static void main(String[] args) {
	Timer timer=new Timer();
	
	timer.schedule(new TimerTask() {
		
		@Override
		public void run() {
			System.out.println("timer=========");
		}
	}, 2000,1000);
	  
}
}
