package com.dx.test.voice;

import org.junit.Test;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * @author Administrator
 *
 * @Date 2019年5月31日
 *
 * 项目名 FaceRecongnition
 *
 *@version 1.0
 */
public class SpeckVoice {
    @Test
    public void testVoice() {
    	ActiveXComponent sap=new ActiveXComponent("Sapi.SpVoice");
    	try {
    		 // 音量 0-100  
            sap.setProperty("Volume", new Variant(100));  
            // 语音朗读速度 -10 到 +10  
            sap.setProperty("Rate", new Variant(-2));  
            // 获取执行对象  
            Dispatch sapo = sap.getObject();  
            // 执行朗读  
            Dispatch.call(sapo, "Speak", new Variant("你好，很高兴见到你。"));  
            // 关闭执行对象  
            sapo.safeRelease();  
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			sap.safeRelease();
		}
    	
    	
    }
	
	
}
