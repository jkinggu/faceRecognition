package com.dx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.dx.query.IResultHandler;
import com.dx.util.DruidUtil;

public class DBTemplate {
	/**
	 * DML操作模板
	 * 
	 */
	public static Integer update(String sql, Object... objs) {
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DruidUtil.getConnection();
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
			DruidUtil.close(conn, pst, null);
		}
		return 0;
	}

	public static <T> T getResult(String sql, IResultHandler<T> irs, Object... objs) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = DruidUtil.getConnection();
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
			DruidUtil.close(conn, pst, rs);
		}
		throw new RuntimeException("查询过程异常!");
	}

}
