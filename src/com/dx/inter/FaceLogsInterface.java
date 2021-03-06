package com.dx.inter;

import java.util.List;
import java.util.Map;

import com.dx.pojo.FaceLog;
import com.dx.pojo.Zkzdata;
import com.dx.query.PageResult;

public interface FaceLogsInterface {
	public int insertFaceLogs(FaceLog faceLog);
	
	//根据身份证号查询考试信息
	public Zkzdata getZkzdataBysfzNum(String sfzNum);
	
	//获取验证次数
    public Integer getLastSucessFaceLogRzcount(String upersonnum, String curchangci);
    //获取验证次数和是否通过
    public Map<String, Object> getLastSucessFaceLogRzcountAndFlag(String upersonnum, String curchangci);
    
	public FaceLog getLastFaceLog(String upersonnum, String curchangci);
	/**
	 * 查询认证次数的集合
	 */
	public String[] getRenzCount();
	
	/**
	 * 根据条件查询日志信息
	 * @param endtime 
	 * @param startime 
	 * @param rzcount 
	 * @param changci 
	 * @param rzresult 
	 */
	
	public List<Zkzdata> getFaceLogsByCondition(String rzresult, String changci, String rzcount, String startime, String endtime);
	public PageResult getFaceLogsByPageCondition(String rzresult, String changci, String kaodian,String kaochang,String startime,
			 Integer currentPage);
	
	public List<FaceLog> getRenZhengList(String cc,String card,String xm,String lx,String startime,String endtime,Integer len, Integer curpage);
	
	public Integer getRenZhengCount(String cc,String card,String xm,String lx,String startime,String endtime);
	
	public List<String> getKd();
	public List<String> getKc();


	/**
	 * @param upersonnum
	 * @return
	 */
	public Boolean getIsExitORnot(String upersonnum);
}
