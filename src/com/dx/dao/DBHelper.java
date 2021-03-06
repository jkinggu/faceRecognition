package com.dx.dao;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.dx.pojo.ParamSetup;
import com.dx.query.IResultHandler;
/**
 * 数据库链接
 * */
public class DBHelper  {  
    public static Connection conn = null;  
    public static Statement pst = null;  
    private static DataSource source;
	private static Properties properties=new Properties();
	static {	    
	     try {
			InputStream is = new BufferedInputStream(new FileInputStream("dbsource/db.properties"));
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
//	public  void getConnection() {
//		
//	}
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
    public ResultSet getResult(String sql) {  
    	ResultSet rs = null ;
        try {  
           // Class.forName(name);//指定连接类型  
            conn = DBHelper.getConnection(); //DriverManager.getConnection(url, user, password);//获取连接  
            pst = conn.createStatement();//准备执行语句  
            rs=pst.executeQuery(sql);
        } catch (Exception e) {  
            e.printStackTrace();  
        }
        return rs ;
    }  
    public ResultSet getResults(Connection conn,String sql) {  
    	ResultSet rs = null ;
        try {  
        	pst = conn.createStatement();//准备执行语句  
            rs=pst.executeQuery(sql);
        } catch (Exception e) {  
            e.printStackTrace();  
        }
        return rs ;
    } 
    public Integer updateResults(Connection conn,String sql) {  
    	Integer i = 0 ;
        try {  
        	pst = conn.createStatement();//准备执行语句  
            i=pst.executeUpdate(sql);
        } catch (Exception e) {  
            e.printStackTrace();  
        }
        return i ;
    } 
    public boolean executeSql(Connection conn,String sql) {  
    	boolean i = false ;
        try {  
        	pst = conn.createStatement();//准备执行语句  
            i=pst.execute(sql);
            conn.setAutoCommit(true);
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
    		//Class.forName(name);//指定连接类型  
        	conn = DBHelper.getConnection(); // DriverManager.getConnection(url, user, password);//获取连接
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
    public static Integer update(String sql, Object... objs) {
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBHelper.getConnection();
			pst = conn.prepareStatement(sql);
			if (objs != null) {
				for (int i = 0; i < objs.length; i++) {
					pst.setObject(i + 1, objs[i]);
				}
			}
			return pst.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DBHelper.close(conn, pst, null);
		}
		return 0;
	}

	public static <T> T getResult(String sql, IResultHandler<T> irs, Object... objs) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DBHelper.getConnection();
			pst = conn.prepareStatement(sql);
			if (objs != null) {				
				for (int i = 0; i < objs.length; i++) {
					pst.setObject(i + 1, objs[i]);
				}
			}			
			rs = pst.executeQuery();			
			return irs.handler(rs);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DBHelper.close(conn, pst, rs);
		}
		throw new RuntimeException("查询过程异常!");
	}
    
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");//指定连接类型  
    	Connection  	conn = DriverManager.getConnection("jdbc:sqlite:dbsource/data/facerenzheng.db");//获取连接
    	Statement sta = conn.createStatement();
    	System.out.println(conn);
    	
    	String sql1 = "select count(*) as a from facelog";
    	ResultSet rs = sta.executeQuery(sql1);
    	System.out.println(rs);
    	while(rs.next()) {
			System.out.println(rs.getString("a"));
		}	
    	
    	conn.setAutoCommit(true);
    	String sql2 = "DELETE  from 'facelog' ;update sqlite_sequence SET seq = 0 where name ='facelog' ;commit; " ;//where shibieleixing='gsfhrt'
    	boolean cc = sta.execute(sql2);
    	//sta.execute("update sqlite_sequence SET seq = 0 where name ='facelog'");
    	//update sqlite_sequence SET seq = 0 where name ='TableName';
    	System.out.println("删除结果     "+cc);
    	
    	conn.close();
	}
    
}
