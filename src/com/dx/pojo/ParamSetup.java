package com.dx.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 参数设置，选择添加考场，考点，时间等考试必备信息，
 * 如果没有设置，则默认当天数据库内的所有考生
 * 
 * */
@Getter@Setter @ToString
public class ParamSetup implements java.io.Serializable {

	private Integer pid ;
	private String  dishi; 
	private String dishiname ;
	private String kaodianname ;
	private String kaochang ;
	private String starttime ;
	private String endtime ;
	private String remarks ;
	private String adminname ;
	private Integer adminid ;
	private String didian ;
	private String ustatu ;
	public ParamSetup() {}
	
	public ParamSetup( Integer pid,String  dishi, String dishiname,String kaodianname ,String kaochang,
			String starttime,String endtime,String remarks,String adminname,Integer adminid, String didian, String ustatu) {
		this.pid = pid ;
		this.dishi = dishi ;
		this.dishiname = dishiname ;
		this.kaodianname = kaodianname ;
		this.kaochang = kaochang ;
		this.starttime = starttime ;
		this.endtime = endtime ;
		this.remarks = remarks ;
		this.adminname = adminname ;
		this.adminid = adminid ;
		this.didian = didian ;
		this.ustatu = ustatu ;
	}
	

	
}
