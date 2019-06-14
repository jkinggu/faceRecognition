package com.dx.test.print;

/**
 * @author Administrator
 *
 * @Date 2019年6月13日
 *
 * 项目名 FaceRecongnition
 *
 *@version 1.0
 */

	import java.awt.Dimension;
	import java.awt.EventQueue;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.io.IOException;
	import javax.swing.JButton;
	import javax.swing.JFrame;
	 
	public class RestartTest {
	    public static void main(final String[] args) {
	        java.awt.EventQueue.invokeLater(new Runnable(){
	                public void run(){
	                    final JFrame frame = new JFrame("Main");
	                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	                    frame.setPreferredSize(new Dimension(400,300));
	                    final JButton restart = new JButton("Restart");
	                    restart.addActionListener(new ActionListener(){
	                            @Override public void actionPerformed(ActionEvent e){
	                                Runtime.getRuntime().addShutdownHook(new Thread(){
	                                        @Override public void run(){
	                                            try {
	                                                new ProcessBuilder("java","Main").start();
	                                            } catch (IOException e) {
	                                            }
	                                        }
	                                    });
	                                System.exit(0);
	                            }
	                        });
	                    frame.getContentPane().add(restart,"South");
	                    frame.pack();
	                    frame.setVisible(true);
	                }
	            });
	    }
	}
	


