package com.dx.util;

import java.io.File;
import java.io.IOException;

public class FileUtil {
	//���������ڵ��ļ���·��
	public static void createFile(String filepath) {
		File file = new File(filepath);
		if(!file.exists()){  
			file.mkdirs();  
		}  
	}

}