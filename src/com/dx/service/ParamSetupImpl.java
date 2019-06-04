package com.dx.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dx.dao.DBHelper;
import com.dx.dao.DBTemplate;
import com.dx.inter.ParamSetupInterface;
import com.dx.pojo.ParamSetup;
import com.dx.query.IResultHandler;
import com.dx.util.BaseUtil;
import com.fr.third.com.lowagie.text.pdf.ArabicLigaturizer;

public class ParamSetupImpl implements ParamSetupInterface{
	private DBHelper dbdao = new DBHelper() ;
	private Connection conn = null ;;
	private ResultSet  res = null ;
	private BaseUtil bu = new BaseUtil();
	@Override
	public Integer getParamSetupBySj(String sj) {
		Integer rowCount = 0 ;
		conn = DBHelper.getConn();
	    String sql = "select * from paramsetup where starttime like '"+sj+"%' and ustatu='1'";
	    PreparedStatement pstmt = null;
	    try {
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        res = pstmt.executeQuery();
	        //Integer cou = rs.getMetaData().getColumnCount();//总列数
	        while(res.next()) { 
	          rowCount++; 
	        }
	        //rowCount = rs.getRow(); //总行数
	       // System.out.println("rowCount---------"+rowCount);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }finally {
	    	try {
				res.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	DBHelper.close(conn, pstmt);
		}
	    return rowCount;
	}

	@Override
	public Integer getParamSetupByDd(String sj,String dd) {
		
		Integer rowCount = 0 ;
		Connection conn = DBHelper.getConn();
	    String sql = "select * from paramsetup where starttime like '"+sj+"%' and didian='"+dd+"' and ustatu='1'";
	    PreparedStatement pstmt = null;
	    try {
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        res = pstmt.executeQuery();
	        while(res.next()) { 
		        rowCount++; 
	        }
//	        rowCount = rs.getRow(); //总行数
	       // System.out.println("rowCount---------"+rowCount);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }finally {
	    	try {
				res.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	DBHelper.close(conn, pstmt);
		}
	    return rowCount;
	}

	@Override
	public Integer getParamSetupByKc(String sj,String dd,String kc) {
		Integer rowCount = 0 ;
		Connection conn = DBHelper.getConn();
	    String sql = "select * from paramsetup where starttime like '"+sj+"%' and didian='"+dd+"' and kaochang='"+kc+"' and ustatu='1'";
	    PreparedStatement pstmt = null;
	    try {
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        res = pstmt.executeQuery();
	        while(res.next()) { 
		        rowCount++; 
	        }
//	        rowCount = rs.getRow(); //总行数
	      //  System.out.println("rowCount---------"+rowCount);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }finally {
	    	try {
				res.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	DBHelper.close(conn, pstmt);
		}
	    return rowCount;
	}
	//跟新查询出来的结果集
	@Override
	public Integer updateParamSetup(String pids) {
		Integer sise = 0 ;
		try {
			conn = dbdao.getConn() ;
			String sql = "update paramsetup set ustatu='1' where pid in("+pids+") " ;
			sise = dbdao.updateResults(conn, sql);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				res.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dbdao.close(conn);
		}
		return sise ;
	}
	//跟新需要移除的数据
	@Override
	public Integer updateParamRemove(String pids) {
		Integer sise = 0 ;
		try {
			conn = dbdao.getConn() ;
			String sql = "update paramsetup set ustatu='0' where pid in("+pids+") " ;
			sise = dbdao.updateResults(conn, sql);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				res.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dbdao.close(conn);
		}
		return sise ;
	}
	@Override
	public List<ParamSetup> getShezhiList(String dishi,String kaodian,String kaochang,String startime,String endtime,String ustatu,String alltype) {
		String  tiaojian = "" ;
		Integer m=0 ;
		if(!"".equals(dishi)) {
			if(m==1) {
				tiaojian  = tiaojian+ " and  dishiname='"+dishi+"' " ;
			}else {
				tiaojian  = tiaojian+ " where  dishiname='"+dishi+"' " ;
				m=1 ;
			}
		}
		if(!"".equals(kaodian)  ) {
			if(m==1) {
				tiaojian  = tiaojian + " and kaodianname='"+kaodian+"' " ;
			}else {
				tiaojian  = tiaojian + " where kaodianname='"+kaodian+"' " ;
				m=1 ;
			}
		}
		if(!"".equals(kaochang)) {
			if(m==1) {
				tiaojian  = tiaojian + " and kaochang='"+kaochang+"' " ;
			}else {
				tiaojian  = tiaojian + " where kaochang='"+kaochang+"' " ;
				m=1 ;
			}
		}
		if(!"".equals(startime)) {
			if(m==1) {
				tiaojian  = tiaojian + " and starttime>'"+startime+" 00:00:00' " ;
			}else {
				tiaojian  = tiaojian + " where starttime>'"+startime+" 00:00:00' " ;
				m=1 ;
			}
		}
		if(!"".equals(endtime)) {
			if(m==1) {
				tiaojian  = tiaojian + " and endtime<'"+endtime+" 23:59:59' " ;
			}else {
				tiaojian  = tiaojian + " where endtime<'"+endtime+" 23:59:59' " ;
				m=1;
			}
		}
		String sql = "select pid,dishi,dishiname,kaodianname,kaochang,starttime,endtime,remarks,adminname,adminid,didian,ustatu from paramsetup " ;
		if(!"".equals(tiaojian)) {
			sql = sql + tiaojian ; 
		}
		if(alltype != null && alltype.equals("all")) {
		}else {
			if(m==1) {
				sql = sql + " and ustatu = '"+ustatu+"' " ;
			}else {
				sql = sql + " where ustatu = '"+ustatu+"' " ;
			}
		}	
		
		List<ParamSetup> list = new ArrayList<ParamSetup>();
	    list=DBTemplate.getResult(sql, new IResultHandler<List<ParamSetup>>() {
			@Override
			public List<ParamSetup> handler(ResultSet rs) throws Exception {
				List<ParamSetup> pList=new ArrayList<>();		
				while(rs.next()) {
					ParamSetup parame=new ParamSetup();
					parame.setPid(rs.getInt("pid"));
					parame.setDishi(rs.getString("dishi"));
					parame.setDishiname(rs.getString("dishiname"));
					parame.setKaodianname(rs.getString("kaodianname"));
					parame.setKaochang(rs.getString("kaochang"));
					parame.setStarttime(rs.getString("starttime")); 
					parame.setEndtime(rs.getString("endtime")); 
					parame.setRemarks(rs.getString("remarks")); 
					parame.setAdminname(rs.getString("adminname")); 
					parame.setAdminid(Integer.parseInt(rs.getString("adminid")));
					parame.setDidian(rs.getString("didian")); 
					parame.setUstatu(rs.getString("ustatu")); 
					pList.add(parame);		
				}	
		       return pList;
			}
		});
	    
	
		return list;
	}
	
	/*
	@Override
	public List<ParamSetup> getShezhiList(String dishi,String kaodian,String kaochang,String startime,String endtime,String ustatu,String alltype) {
		List<ParamSetup> list = new ArrayList<ParamSetup>();
		try {
			conn = dbdao.getConn() ;
			String  tiaojian = "" ;
			Integer m=0 ;
			if(!"".equals(dishi)) {
				if(m==1) {
					tiaojian  = tiaojian+ " and  dishiname='"+dishi+"' " ;
				}else {
					tiaojian  = tiaojian+ " where  dishiname='"+dishi+"' " ;
					m=1 ;
				}
			}
			if(!"".equals(kaodian)  ) {
				if(m==1) {
					tiaojian  = tiaojian + " and kaodianname='"+kaodian+"' " ;
				}else {
					tiaojian  = tiaojian + " where kaodianname='"+kaodian+"' " ;
					m=1 ;
				}
			}
			if(!"".equals(kaochang)) {
				if(m==1) {
					tiaojian  = tiaojian + " and kaochang='"+kaochang+"' " ;
				}else {
					tiaojian  = tiaojian + " where kaochang='"+kaochang+"' " ;
					m=1 ;
				}
			}
			if(!"".equals(startime)) {
				if(m==1) {
					tiaojian  = tiaojian + " and starttime>'"+startime+" 00:00:00' " ;
				}else {
					tiaojian  = tiaojian + " where starttime>'"+startime+" 00:00:00' " ;
					m=1 ;
				}
			}
			if(!"".equals(endtime)) {
				if(m==1) {
					tiaojian  = tiaojian + " and endtime<'"+endtime+" 23:59:59' " ;
				}else {
					tiaojian  = tiaojian + " where endtime<'"+endtime+" 23:59:59' " ;
					m=1;
				}
			}
			String sql = "select * from paramsetup " ;
			if(!"".equals(tiaojian)) {
				sql = sql + tiaojian ; 
			}
			if(alltype != null && alltype.equals("all")) {
			}else {
				if(m==1) {
					sql = sql + " and ustatu='"+ustatu+"' " ;
				}else {
					sql = sql + " where ustatu='"+ustatu+"' " ;
				}
			}
			System.out.println(sql+"   ===================");
			res = dbdao.getResults(conn, sql);
			ResultSetMetaData resmate = res.getMetaData() ;
			int cols = resmate.getColumnCount() ;//查询返回的行数
			while(res.next()) {
				ParamSetup parame = new ParamSetup();
				for(int i= 0; i<cols; i++) {
					int g = i+1 ;
					if(i==0) {parame.setPid(Integer.parseInt(res.getString(g))); }
					if(i==1) {parame.setDishi(res.getString(g)); }
					if(i==2) {parame.setDishiname(res.getString(g)); }
					if(i==3) {parame.setKaodianname(res.getString(g)); }
					if(i==4) {parame.setKaochang(res.getString(g)); }
					if(i==5) {parame.setStarttime(res.getString(g)); }
					if(i==6) {parame.setEndtime(res.getString(g)); }
					if(i==7) {parame.setRemarks(res.getString(g)); }
					if(i==8) {parame.setAdminname(res.getString(g)); }
					if(i==9) {parame.setAdminid(Integer.parseInt(res.getString(g))); }
					if(i==10) {parame.setDidian(res.getString(g)); }
					if(i==11) {parame.setUstatu(res.getString(g)); }
				}
				list.add(parame);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				res.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dbdao.close(conn);
		}
		return list ;
	}*/
	@Override
	public String[] getDiShi() {
		String dishistr[] = null;
		try {
			conn = dbdao.getConn() ;
			String sql = "SELECT b.no1 FROM belongfor b,faceadmin f WHERE ((b.no1code=f.bmname AND f.bmname != 'admin' ) OR (f.bmname = 'admin')) AND  b.no1code>0 AND f.curmana='1' GROUP BY no1" ;
			res = dbdao.getResults(conn, sql);
			//int i=0 ;
			List<String> list = new ArrayList<String>();
			list.add("请选择");
			while(res.next()) {
				String no1name = res.getString(1) ;
				list.add(no1name);
			}
			dishistr = list.toArray(new String[list.size()]);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				res.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dbdao.close(conn);
		}
		return dishistr ;
	}
	
	/*
	@Override
	public String[] getKaoDian(String dishi) {
		String kaodian[] = null;
		try {
			conn = dbdao.getConn() ;
			String sql = "select dd1,kd1 from zkzdata where  dishiname='"+dishi+"' and jbcode !='5' group by concat(dd1,',',kd1)" ;
			res = dbdao.getResults(conn, sql);
			//int i=0 ;
			List<String> list = new ArrayList<String>();
			list.add("请选择");
			while(res.next()) {
				String kd1 = res.getString(2) ;
				list.add(kd1);
			}
			kaodian = list.toArray(new String[list.size()]);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				res.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dbdao.close(conn);
		}
		return kaodian ;
	}*/
	

	@Override
	public String[] getKaoDian(String dishi) {
		String kaodian[] = null;
		String sql = "select dd1,kd1 from zkzdata where  dishiname=? and jbcode !='5' group by   dd1+','+kd1 " ;
		kaodian=DBTemplate.getResult(sql, new IResultHandler<String[]>() {
			@Override
			public String[] handler(ResultSet rs) throws Exception {
				String[] kd=new String[] {};
				List<String> list = new ArrayList<String>();
				list.add("请选择");
				while(rs.next()) {
					list.add(rs.getString(2));					
				}
				kd=list.toArray(new String[list.size()]);
				return kd;
			}
		},dishi);
		return kaodian;
	}
	
	@Override
	public String[] getKaoChang(String kaodian) {
		String kaochang[] = null;
		try {
			conn = dbdao.getConn() ;
			String sql = "select kc1 from zkzdata where  kd1='"+kaodian+"' and jbcode !='5' group by kc1" ;
			res = dbdao.getResults(conn, sql);
			//int i=0 ;
			List<String> list = new ArrayList<String>();
			list.add("请选择");
			while(res.next()) {
				String kc = res.getString(1) ;
				list.add(kc);
			}
			kaochang = list.toArray(new String[list.size()]);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				res.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dbdao.close(conn);
		}
		return kaochang ;
	}
	/**初始化系统数据。除用户表以外的所有表，初始化后，需重新导入  初始数据（来自于网上报名系统）*/
	@Override
	public boolean initSysData() {
		boolean result = true ;
		Integer initcount = 0 ;
		try {
			conn = dbdao.getConn() ;
			String sql = "truncate facelog " ;
			initcount = dbdao.updateResults(conn, sql);
			sql = "truncate belongfor " ;
			initcount = dbdao.updateResults(conn, sql);
			sql = "truncate paramsetup " ;
			initcount = dbdao.updateResults(conn, sql);
			sql = "truncate zkzdata " ;
			initcount = dbdao.updateResults(conn, sql);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbdao.close(conn);
		}
		return result;
	}
	/**初始化认证数据和当前认证参数设置*/
	@Override
	public boolean initRenzhengData() {		
		String sql = "truncate facelog " ;
		DBTemplate.update(sql);
		sql = "update paramsetup set ustatu='0' " ;
		DBTemplate.update(sql);
		return true;
		
		/*boolean result = true ;
		try {
			conn = dbdao.getConn() ;
			String sql = "truncate facelog " ;
			Integer j = dbdao.updateResults(conn, sql);
			sql = "update paramsetup set ustatu='0' " ;
			Integer i = dbdao.updateResults(conn, sql);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbdao.close(conn);
		}
		return result;20190515注释*/
		
	}

	@Override
	public Integer insertParamset(ParamSetup param) {
		Integer num = 0;
		String sql = "insert into paramsetup(dishi,dishiname, kaodianname, kaochang, starttime, endtime, remarks,"
				+ "adminname , adminid,didian,ustatu  ) values(?,?,?,?,?,?,?,?,?,?,?)";
		num=DBTemplate.update(sql,param.getDishi(),param.getDishiname(),param.getKaodianname(),param.getKaochang(),param.getStarttime(),
				param.getEndtime(), param.getRemarks(),param.getAdminname(),param.getAdminid()+"",param.getDidian(),param.getUstatu());
		return num;
		
		/*Connection conn = DBHelper.getConn();	
		PreparedStatement pstmt = null;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setString(1, param.getDishi());
			pstmt.setString(2, param.getDishiname());
			pstmt.setString(3, param.getKaodianname());
			pstmt.setString(4, param.getKaochang());
			pstmt.setString(5, param.getStarttime());
			pstmt.setString(6, param.getEndtime());
			pstmt.setString(7, param.getRemarks());
			pstmt.setString(8, param.getAdminname());
			pstmt.setString(9, param.getAdminid()+"");
			pstmt.setString(10, param.getDidian());
			pstmt.setString(11, param.getUstatu());
			num = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBHelper.close(conn, pstmt);
		}20190515注释*/
		
	}
}
