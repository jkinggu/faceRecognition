package com.dx.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import com.dx.dao.DBHelper;
import com.dx.inter.BelongforInterface;
import com.dx.pojo.Belongfor;
import com.dx.pojo.FaceLog;

public class BelongforImpl implements BelongforInterface{

	/**所有的单位列表*/
	@Override
	public List<Belongfor> getBelongforList() {
		Connection conn = DBHelper.getConn();
		List<Belongfor> beflist = new ArrayList<Belongfor>();
		PreparedStatement pstmt = null;
		try {
			String sql = "select * from belongfor " ;
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			ResultSet res = pstmt.executeQuery();
			ResultSetMetaData resmate = res.getMetaData();
			int cols = resmate.getColumnCount();// 查询返回的列数
			while (res.next()) {
				Belongfor bef = new Belongfor();
				for (int i = 0; i < cols; i++) {
					int g = i + 1;
					bef.setAllcode(res.getString(1));
					bef.setNo1code(res.getString(2));
					bef.setNo1name(res.getString(3));
					bef.setNo2code(res.getString(4));
					bef.setNo2name(res.getString(5));
					bef.setNo3code(res.getString(6));
					bef.setNo3name(res.getString(7));
					bef.setNo4code(res.getString(8));
					bef.setNo4name(res.getString(9));
					bef.setId(res.getString(10));
					bef.setLeibie(res.getString(11));
				}
				beflist.add(bef);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBHelper.close(conn, pstmt);
		}
		
		
		
		
		return beflist;
	}

}
