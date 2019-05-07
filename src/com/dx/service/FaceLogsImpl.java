package com.dx.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dx.dao.DBHelper;
import com.dx.dao.DBTemplate;
import com.dx.inter.FaceLogsInterface;
import com.dx.pojo.FaceLog;
import com.dx.pojo.Zkzdata;
import com.dx.query.IResultHandler;
import com.dx.query.PageResult;
import com.dx.util.Const;

public class FaceLogsImpl implements FaceLogsInterface {
	private DBHelper dbdao = new DBHelper();
	private Connection conn = null;;
	private ResultSet res = null;

	@Override
	public int insertFaceLogs(FaceLog faceLog) {
		Connection conn = DBHelper.getConn();
		int num = 0;
		String sql = "insert into facelog(sfz,xingming,xingbie,shibieleixing,"
				+ "shibieleixingint,shijian,renlianphoto,sfzphoto,remarks,changci,denglumana,renzcount) values("
				+ "?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = null;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setString(1, faceLog.getSfz());
			pstmt.setString(2, faceLog.getXingming());
			pstmt.setString(3, faceLog.getXingbie());
			pstmt.setString(4, faceLog.getShibieleixing());
			pstmt.setString(5, faceLog.getShibieleixingint());
			pstmt.setString(6, faceLog.getShijian());
			pstmt.setString(7, faceLog.getRenlianphoto());
			pstmt.setString(8, faceLog.getSfzphoto());
			pstmt.setString(9, faceLog.getRemarks());
			pstmt.setString(10, faceLog.getChangci());
			pstmt.setString(11, faceLog.getDenglumana());
			pstmt.setString(12, faceLog.getRenzcount());
			num = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBHelper.close(conn, pstmt);
		}
		return num;
	}

	public static void main(String[] args) {
		FaceLog faceLog = new FaceLog();
		faceLog.setSfz("411502199009184511");
		faceLog.setXingming("葛开存");
		faceLog.setXingbie("男");
		faceLog.setShibieleixing("非考生");
		faceLog.setShibieleixingint("1");
		faceLog.setShijian("2018-11-13 10:35:25");
		faceLog.setRenlianphoto("D:\\faceimages\\faceimage");
		faceLog.setSfzphoto("D:\\faceimages/faceimage/20181222521.jpg");
		FaceLogsInterface impl = new FaceLogsImpl();
		System.out.println(impl.insertFaceLogs(faceLog));
	}

	@Override
	public FaceLog getLastFaceLog(String upersonnum, String curchangci) {
		Connection conn = DBHelper.getConn();
		String sql = "select * from facelog where sfz='" + upersonnum + "' and changci='" + curchangci
				+ "'  ORDER BY id DESC LIMIT 1";
		PreparedStatement pstmt = null;
		FaceLog facelog = new FaceLog();
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				facelog.setId(Integer.parseInt(rs.getString(1)));
				facelog.setSfz(rs.getString(2));
				facelog.setXingming(rs.getString(3));
				facelog.setXingbie(rs.getString(4));
				facelog.setShibieleixing(rs.getString(5));
				facelog.setShibieleixingint(rs.getString(6));
				facelog.setShijian(rs.getString(7));
				facelog.setRenlianphoto(rs.getString(8));
				facelog.setRemarks(rs.getString(9));
				facelog.setSfzphoto(rs.getString(10));
				facelog.setChangci(rs.getString(11));
				facelog.setDenglumana(rs.getString(12));
				facelog.setRenzcount(rs.getString(13));
			}
			if (facelog.getId() == null) {
				facelog = null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBHelper.close(conn, pstmt);
		}
		return facelog;
	}

	@Override
	public String[] getRenzCount() {
		String[] renzcounts = new String[] {};
		try {
			conn = dbdao.getConn();
			String sql = "select distinct count(*) rzcount from facelog where renzcount is not null group by sfz order by rzcount";
			res = dbdao.getResults(conn, sql);
			List<String> list = new ArrayList<>();
			list.add("请选择");
			while (res.next()) {
				String kc = res.getString(1);
				list.add(kc);
			}
			renzcounts = list.toArray(new String[list.size()]);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbdao.close(conn);
		}
		return renzcounts;
	}
	
	@Override
	public PageResult getFaceLogsByPageCondition(String rzresult, String changci, String rzcount, String startime,
			String endtime, Integer currentPage) {
		//String tiaojian = "select zkzdata.xingming,zkzdata.upersonnum,zkzdata.zkznum,zkzdata.kd1,zkzdata.kc1,zkzdata.dd1 "
		//		+ "from zkzdata left join facelog on zkzdata.upersonnum = facelog.sfz " + " where facelog.changci='"
		//		+ changci + "' ";	
		String tiaojian="select zkzdata.xingming,zkzdata.upersonnum,zkzdata.zkznum,zkzdata.kd1,zkzdata.kc1,zkzdata.dd1 "
				+ "from zkzdata left join facelog on zkzdata.upersonnum = facelog.sfz ";
		//String tiaojian2="SELECT COUNT(zkzdata.id) FROM zkzdata left join facelog on zkzdata.upersonnum = facelog.sfz"+
		//		" where facelog.changci='"+ changci + "' ";
		String tiaojian2="SELECT COUNT(zkzdata.id) FROM zkzdata  join facelog on zkzdata.upersonnum = facelog.sfz";
		String baseSql = "";
		String countSql="";
		
		// 认证结果为空，则显示所有对应场次的信息
		if (rzresult == null || "".equals(rzresult)) {
			baseSql = tiaojian;
			countSql=tiaojian2;
		} else if ("未认证".equals(rzresult)) {// 未认证怎么办
			if (startime == null || "".equals(startime)) {
				String datetime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
				//baseSql = tiaojian.substring(0, tiaojian.length() - 24) + " facelog.sfz is null and zkzdata.sj1 like '"
				//		+ datetime + "%' ";
				  baseSql=tiaojian+" and facelog.sfz is null and zkzdata.sj1 like '%"+ datetime + "%' ";
				
				//countSql=tiaojian2.substring(0, tiaojian2.length() - 24) + " facelog.sfz is null and zkzdata.sj1 like '"
				//		+ datetime + "%' ";
				  
				  countSql=tiaojian2+" and facelog.sfz is null and zkzdata.sj1 like  '%"+ datetime + "%' ";
			} else {
				//baseSql = tiaojian.substring(0, tiaojian.length() - 24) + " facelog.sfz is null and zkzdata.sj1 like '"
				//		+ startime.substring(0, 10) + "%' ";
				baseSql = tiaojian + " and facelog.sfz is null and zkzdata.sj1 like '%"+ startime.substring(0, 10) + "%' ";
				
				//countSql=tiaojian2.substring(0, tiaojian2.length() - 24) + " facelog.sfz is null and zkzdata.sj1 like '"
				//		+ startime.substring(0, 10) + "%' ";	
				countSql=tiaojian2+ " and facelog.sfz is null and zkzdata.sj1 like '%"
						+ startime.substring(0, 10) + "%' ";	
			}
		} else {
			if ("通过".equals(rzresult)) {// 通过的
				baseSql = tiaojian + " and facelog.shibieleixingint=7 and facelog.renzcount is not null ";
				countSql=tiaojian2 + " and facelog.shibieleixingint=7 and facelog.renzcount is not null ";
			} else if ("不通过".equals(rzresult)) {// 不通过的
				baseSql = tiaojian + " and facelog.sfz not in (select distinct sfz from facelog where shibieleixingint = 7)";
				countSql = tiaojian2 + " and facelog.sfz not in (select distinct sfz from facelog where shibieleixingint = 7)";
			}

			if (rzcount != null && !"".equals(rzcount)) {
				baseSql += " and facelog.renzcount=" + rzcount + " ";
				countSql += " and facelog.renzcount=" + rzcount + " ";
			}

			if (startime != null && !"".equals(startime)) {
				baseSql += " and facelog.shijian >'" + startime + "' ";
				countSql += " and facelog.shijian >'" + startime + "' ";
			}

			if (endtime != null && !"".equals(endtime)) {
				baseSql += " and facelog.shijian <'" + endtime + "' ";
				countSql += " and facelog.shijian <'" + endtime + "' ";
			}
			
		}
		if(changci!=null&& !"".equals(changci)) {
			baseSql+=" where facelog.changci='"+ changci + "' ";
			countSql+=" where facelog.changci='"+ changci + "' ";
		}
		if(currentPage>0) {
			baseSql+="LIMIT ? , ? ";
		}
		System.out.println(baseSql);
		System.out.println(countSql);
		List<Zkzdata> list=DBTemplate.getResult(baseSql,new IResultHandler<List<Zkzdata>>() {
			@Override
			public List<Zkzdata> handler(ResultSet res) throws Exception {
				List<Zkzdata> list=new ArrayList<>();
				ResultSetMetaData resmate = res.getMetaData();
				int cols = resmate.getColumnCount();// 查询返回的行数
				while (res.next()) {
					Zkzdata zkzdata = new Zkzdata();
					for (int i = 0; i < cols; i++) {
						int g = i + 1;
						if (i == 0) {
							zkzdata.setXingming(res.getString(g));
						}
						if (i == 1) {
							zkzdata.setUpersonnum(res.getString(g));
						}
						if (i == 2) {
							zkzdata.setZkznum(res.getString(g));
						}
						if (i == 3) {
							zkzdata.setKd1(res.getString(g));
						}
						if (i == 4) {
							zkzdata.setKc1(res.getString(g));
						}
						if (i == 5) {
							zkzdata.setDd1(res.getString(g));
						}
					}
					list.add(zkzdata);
				}
				return list;
			}
		}, (currentPage-1)*Const.PAGESIZE,Const.PAGESIZE);
		
		
		Integer totalCount=DBTemplate.getResult(countSql, new IResultHandler<Integer>() {
			@Override
			public Integer handler(ResultSet rs) throws Exception {
				if(rs.next()) {
					return rs.getInt(1);
				}
				return 0;
			}
		});
		return new PageResult(list, totalCount, currentPage);
	}
	
	@Override
	public List<Zkzdata> getFaceLogsByCondition(String rzresult, String changci, String rzcount, String startime,
			String endtime) {
		List<Zkzdata> list = new ArrayList<>();
		try {
			String tiaojian = "select zkzdata.xingming,zkzdata.upersonnum,zkzdata.zkznum,zkzdata.kd1,zkzdata.kc1,zkzdata.dd1 "
					+ "from zkzdata left join facelog on zkzdata.upersonnum = facelog.sfz " + "where facelog.changci='"
					+ changci + "' ";
			String sql = "";
			// 认证结果为空，则显示所有对应场次的信息
			if (rzresult == null || "".equals(rzresult)) {
				sql = tiaojian;
			} else if ("未认证".equals(rzresult)) {// 未认证怎么办
				if (startime == null || "".equals(startime)) {
					String datetime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					sql = tiaojian.substring(0, tiaojian.length() - 24) + " facelog.sfz is null and zkzdata.sj1 like '"
							+ datetime + "%' ";
				} else {
					sql = tiaojian.substring(0, tiaojian.length() - 24) + " facelog.sfz is null and zkzdata.sj1 like '"
							+ startime.substring(0, 10) + "%' ";
				}
			} else {
				if ("通过".equals(rzresult)) {// 通过的
					sql = tiaojian + " and facelog.shibieleixingint=7 and facelog.renzcount is not null ";
				} else if ("不通过".equals(rzresult)) {// 不通过的
					sql = tiaojian
							+ "and facelog.sfz not in (select distinct sfz from facelog where shibieleixingint = 7)";
				}

				if (rzcount != null && !"".equals(rzcount)) {
					sql += " and facelog.renzcount=" + rzcount + " ";
				}

				if (startime != null && !"".equals(startime)) {
					sql += " and facelog.shijian >'" + startime + "' ";
				}

				if (endtime != null && !"".equals(endtime)) {
					sql += " and facelog.shijian <'" + endtime + "' ";
				}
			}
			System.out.println(sql);

			conn = dbdao.getConn();
			res = dbdao.getResults(conn, sql);
			ResultSetMetaData resmate = res.getMetaData();
			int cols = resmate.getColumnCount();// 查询返回的行数
			while (res.next()) {
				Zkzdata zkzdata = new Zkzdata();
				for (int i = 0; i < cols; i++) {
					int g = i + 1;
					if (i == 0) {
						zkzdata.setXingming(res.getString(g));
					}
					if (i == 1) {
						zkzdata.setUpersonnum(res.getString(g));
					}
					if (i == 2) {
						zkzdata.setZkznum(res.getString(g));
					}
					if (i == 3) {
						zkzdata.setKd1(res.getString(g));
					}
					if (i == 4) {
						zkzdata.setKc1(res.getString(g));
					}
					if (i == 5) {
						zkzdata.setDd1(res.getString(g));
					}
				}
				list.add(zkzdata);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbdao.close(conn);
		}
		return list;
	}

	@Override
	public List<FaceLog> getRenZhengList(String cc, String card, String xm, String lx, String startime, String endtime,
			Integer start, Integer end) {
		Connection conn = DBHelper.getConn();
		List<FaceLog> loglist = new ArrayList<FaceLog>();
		PreparedStatement pstmt = null;
		try {
			String tiaojian = "";
			Integer m = 0;
			if (!"".equals(cc)) {
				if (m == 1) {
					tiaojian = tiaojian + " and  changci='" + cc + "' ";
				} else {
					tiaojian = tiaojian + " where  changci='" + cc + "' ";
					m = 1;
				}
			}
			if (!"".equals(card)) {
				if (m == 1) {
					tiaojian = tiaojian + " and  sfz='" + card + "' ";
				} else {
					tiaojian = tiaojian + " where  sfz='" + card + "' ";
					m = 1;
				}
			}
			if (!"".equals(xm)) {
				if (m == 1) {
					tiaojian = tiaojian + " and  xingming='" + xm + "' ";
				} else {
					tiaojian = tiaojian + " where  xingming='" + xm + "' ";
					m = 1;
				}
			}
			if (!"".equals(lx)) {
				if (m == 1) {
					tiaojian = tiaojian + " and  shibieleixing='" + lx + "' ";
				} else {
					tiaojian = tiaojian + " where  shibieleixing='" + lx + "' ";
					m = 1;
				}
			}
			if (!"".equals(startime)) {
				if (m == 1) {
					tiaojian = tiaojian + " and shijian>'" + startime + "' ";
				} else {
					tiaojian = tiaojian + " where shijian>'" + startime + "' ";
					m = 1;
				}
			}
			if (!"".equals(endtime)) {
				if (m == 1) {
					tiaojian = tiaojian + " and shijian<'" + endtime + "' ";
				} else {
					tiaojian = tiaojian + " where shijian<'" + endtime + "' ";
					m = 1;
				}
			}

			String sql = "select * from facelog ";

			if (!"".equals(tiaojian)) {
				sql = sql + tiaojian;
			}
			// 获取数据是从第n行到第m行，sql语句中用limit n-1, m-n(索引，长度)
			// System.out.println(beginindex +" "+end);
			if (start != null && end != null) {
				Integer beginindex = start - 1;
				sql = sql + " limit " + beginindex + " , " + end + " ";
			}

			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			ResultSet res = pstmt.executeQuery();
			ResultSetMetaData resmate = res.getMetaData();
			int cols = resmate.getColumnCount();// 查询返回的列数
			while (res.next()) {
				FaceLog facelog = new FaceLog();
				for (int i = 0; i < cols; i++) {
					int g = i + 1;
					facelog.setId(Integer.parseInt(res.getString(1)));
					facelog.setSfz(res.getString(2));
					facelog.setXingming(res.getString(3));
					facelog.setXingbie(res.getString(4));
					facelog.setShibieleixing(res.getString(5));
					facelog.setShibieleixingint(res.getString(6));
					facelog.setShijian(res.getString(7));
					facelog.setRenlianphoto(res.getString(8));
					facelog.setRemarks(res.getString(9));
					facelog.setSfzphoto(res.getString(10));
					facelog.setChangci(res.getString(11));
					facelog.setDenglumana(res.getString(12));
					facelog.setRenzcount(res.getString(13));
				}
				loglist.add(facelog);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBHelper.close(conn, pstmt);
		}
		return loglist;
	}

	@Override
	public Integer getRenZhengCount(String cc, String card, String xm, String lx, String startime, String endtime) {
		Connection conn = DBHelper.getConn();
		List<FaceLog> loglist = new ArrayList<FaceLog>();
		PreparedStatement pstmt = null;
		Integer count = 0;
		try {
			String tiaojian = "";
			Integer m = 0;
			if (!"".equals(cc)) {
				if (m == 1) {
					tiaojian = tiaojian + " and  changci='" + cc + "' ";
				} else {
					tiaojian = tiaojian + " where  changci='" + cc + "' ";
					m = 1;
				}
			}
			if (!"".equals(card)) {
				if (m == 1) {
					tiaojian = tiaojian + " and  sfz='" + card + "' ";
				} else {
					tiaojian = tiaojian + " where  sfz='" + card + "' ";
					m = 1;
				}
			}
			if (!"".equals(xm)) {
				if (m == 1) {
					tiaojian = tiaojian + " and  xingming='" + xm + "' ";
				} else {
					tiaojian = tiaojian + " where  xingming='" + xm + "' ";
					m = 1;
				}
			}
			if (!"".equals(lx)) {
				if (m == 1) {
					tiaojian = tiaojian + " and  shibieleixing='" + lx + "' ";
				} else {
					tiaojian = tiaojian + " where  shibieleixing='" + lx + "' ";
					m = 1;
				}
			}
			if (!"".equals(startime)) {
				if (m == 1) {
					tiaojian = tiaojian + " and shijian>'" + startime + "' ";
				} else {
					tiaojian = tiaojian + " where shijian>'" + startime + "' ";
					m = 1;
				}
			}
			if (!"".equals(endtime)) {
				if (m == 1) {
					tiaojian = tiaojian + " and shijian<'" + endtime + "' ";
				} else {
					tiaojian = tiaojian + " where shijian<'" + endtime + "' ";
					m = 1;
				}
			}

			String sql = "select count(*) from facelog ";

			if (!"".equals(tiaojian)) {
				sql = sql + tiaojian;
			}

			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			ResultSet res = pstmt.executeQuery();
			while (res.next()) {
				count = Integer.parseInt(res.getString(1));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBHelper.close(conn, pstmt);
		}
		return count;
	}

}