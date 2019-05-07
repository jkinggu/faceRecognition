package com.dx.util;

import java.io.File;
import java.io.IOException;

public class FileUtil {
	//创建不存在的文件夹路径
	public static void createFile(String filepath) {
		File file = new File(filepath);
		if(!file.exists()){  
			file.mkdirs();  
		}  
	}

}
