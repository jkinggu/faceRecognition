package com.dx.test.db;

import java.util.List;

import org.junit.Test;

import com.dx.inter.FaceLogsInterface;
import com.dx.service.FaceLogsImpl;

/**
 * @author Administrator
 *
 * @Date 2019��5��27��
 *
 * ��Ŀ�� FaceRecongnition
 *
 *@version 1.0
 */


public class DataBaseTest {
	
	private FaceLogsInterface facelog = new FaceLogsImpl();
     @Test
     public  void testDataSource() {
    
    	 
     }
     
     @Test
     public void testKd() {
    	 
    	 List<String> kds=facelog.getKd();
    	 String [] kds1=kds.toArray(new String[kds.size()]);
    	 System.out.println(kds1[0]);
    	 String[] kaochang = new String[] { "ְҵ����", "ְҵ����", "��λ����" }; 
    	 System.out.println(kaochang);
     }
     
}
