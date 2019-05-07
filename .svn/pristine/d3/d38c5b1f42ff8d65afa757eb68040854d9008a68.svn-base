package com.dx.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dx.dao.DBHelper;
import com.dx.inter.FaceadminInteraface;
import com.dx.pojo.Faceadmin;

public class FaceadminImpl implements FaceadminInteraface{

	@Override
	public Faceadmin getFaceadmin() {
		Connection conn = DBHelper.getConn();
	    String sql = "select * from faceadmin where curmana='1' ";
	    PreparedStatement pstmt = null;
	    Faceadmin admin = new  Faceadmin();
	    try {
	        pstmt = conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();
	        while(rs.next()) { 
	        	admin.setFid(Integer.parseInt(rs.getString(1)));
	        	admin.setBmname(rs.getString(2));
	        	admin.setBmpass(rs.getString(3));
	        	admin.setRemarks(rs.getString(4));
	        	admin.setCurmana(rs.getString(5));
	        	admin.setCurchangci(rs.getString(6));
	        }
	        if(admin.getFid()==null) {
	        	admin = null ;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }finally {
	    	DBHelper.close(conn, pstmt);
		}
	    return admin ;
	}

	@Override
	public Faceadmin loginAdmin(String username, String password, String cc) {
		Connection conn = DBHelper.getConn();
	    String sql = "select * from faceadmin where  bmname = ? and bmpass = ?";
	    PreparedStatement pstmt = null;
	    Faceadmin admin = new  Faceadmin();
	    try {
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, username);
	        pstmt.setString(2, password);
	        ResultSet rs = pstmt.executeQuery();
	        while(rs.next()) { 
	        	admin.setFid(Integer.parseInt(rs.getString(1)));
	        	admin.setBmname(rs.getString(2));
	        	admin.setBmpass(rs.getString(3));
	        	admin.setRemarks(rs.getString(4));
	        	admin.setCurmana(rs.getString(5));
	        	admin.setCurchangci(rs.getString(6));
	        }
	        if(admin.getFid()==null) {
	        	return new Faceadmin();
	        }else {
	        	Faceadmin oldAdmin = getFaceadmin();
	        	sql = "update faceadmin set curmana = ? ,curchangci = ? where fid= ?";
	        	if(oldAdmin != null) {
	        		pstmt = conn.prepareStatement(sql);
	    	        pstmt.setString(1, "");//把之前的当前值置空
	    	        pstmt.setString(2, oldAdmin.getCurchangci());
	    	        pstmt.setInt(3, oldAdmin.getFid());
	    	        pstmt.execute();
	        	}
	        	pstmt = conn.prepareStatement(sql);
	        	pstmt.setString(1, "1");//把之前的当前值置空
	        	pstmt.setString(2, cc);
	        	pstmt.setInt(3, admin.getFid());
	        	pstmt.execute();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }finally {
	    	DBHelper.close(conn, pstmt);
		}
	    return admin ;
	}
	
	

}
