package com.dx.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.dx.util.DruidUtil;
/**
 * ���ݿ�����
 * */
public class DBHelper {  
//    public static final String url = "jdbc:mysql://192.168.1.140:3306/facerenzheng";  
//    public static final String name = "com.mysql.jdbc.Driver";  
//    public static final String user = "root";  
//    public static final String password = "123";  
  
    public static Connection conn = null;  
    public static Statement pst = null;  
  
    public ResultSet getResult(String sql) {  
    	ResultSet rs = null ;
        try {  
           // Class.forName(name);//ָ����������  
            conn = DruidUtil.getConnection(); //DriverManager.getConnection(url, user, password);//��ȡ����  
            pst = conn.createStatement();//׼��ִ�����  
            rs=pst.executeQuery(sql);
        } catch (Exception e) {  
            e.printStackTrace();  
        }
        return rs ;
    }  
    public ResultSet getResults(Connection conn,String sql) {  
    	ResultSet rs = null ;
        try {  
        	pst = conn.createStatement();//׼��ִ�����  
            rs=pst.executeQuery(sql);
        } catch (Exception e) {  
            e.printStackTrace();  
        }
        return rs ;
    } 
    public Integer updateResults(Connection conn,String sql) {  
    	Integer i = 0 ;
        try {  
        	pst = conn.createStatement();//׼��ִ�����  
            i=pst.executeUpdate(sql);
        } catch (Exception e) {  
            e.printStackTrace();  
        }
        return i ;
    } 
    public static  void close(Connection conn,Statement pst) {  
        try {  
        	if(conn != null)
        		conn.close();  
        	if(pst != null)
        		pst.close();  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    }  
    
    public static Connection getConn() {
    	try {
    		//Class.forName(name);//ָ����������  
        	conn = DruidUtil.getConnection(); // DriverManager.getConnection(url, user, password);//��ȡ����
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return conn ;
    }
    public void close(Connection conn ) {
    	try {
    		if(conn != null)
    			conn.close();
    		if(pst != null)
        		pst.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    public static void main(String[] args) throws SQLException {
    	DBHelper dbh = new DBHelper();
    	Connection conn = dbh.getConn() ;
    	System.out.println(conn);
    	dbh.close(conn);
	}
    
    
    
}