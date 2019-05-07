package com.dx.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;

/**
 * @author fang
 *
 * @Date 2019年4月26日
 *
 * 项目名 FaceRecongnition
 *
 *@version 1.0
 */
public class DruidUtil {
	private static DataSource source;
	private static Properties properties=new Properties();
	static {	    
	     try {
	    	//InputStream is=Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties");
			InputStream is = new BufferedInputStream(new FileInputStream("D:\\faceimages\\dbsource/db.properties"));
			properties.load(is);
		} catch (Exception e) {
			 throw new RuntimeException("获取配置文件失败!");
		}
	    try {
	    	source=DruidDataSourceFactory.createDataSource(properties);
	    }catch (Exception e) {
			// TODO: handle exception
	    	throw new RuntimeException("Druid加载失败!");
		}
	    
	}
	public static Connection getConnection() {
		try {
			return source.getConnection();
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException("获取数据库连接失败!");
		}
	}
	public static void close(Connection conn,Statement st,ResultSet rs) {
		try {
			if(st!=null) {
				st.close();
			}
			if(rs!=null) {
				rs.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			if(conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
