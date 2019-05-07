package com.dx.inter;

import java.util.List;

import com.dx.pojo.FaceLog;
import com.dx.pojo.Zkzdata;
import com.dx.query.PageResult;

public interface FaceLogsInterface {
	public int insertFaceLogs(FaceLog faceLog);

	public FaceLog getLastFaceLog(String upersonnum, String curchangci);
	/**
	 * ��ѯ��֤�����ļ���
	 */
	public String[] getRenzCount();
	
	/**
	 * ����������ѯ��־��Ϣ
	 * @param endtime 
	 * @param startime 
	 * @param rzcount 
	 * @param changci 
	 * @param rzresult 
	 */
	public List<Zkzdata> getFaceLogsByCondition(String rzresult, String changci, String rzcount, String startime, String endtime);
	public PageResult getFaceLogsByPageCondition(String rzresult, String changci, String rzcount, String startime,
			String endtime, Integer currentPage);
	
	public List<FaceLog> getRenZhengList(String cc,String card,String xm,String lx,String startime,String endtime,Integer len, Integer curpage);
	
	public Integer getRenZhengCount(String cc,String card,String xm,String lx,String startime,String endtime);
}