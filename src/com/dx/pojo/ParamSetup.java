package com.dx.pojo;

/**
 * �������ã�ѡ�����ӿ��������㣬ʱ��ȿ��Աر���Ϣ��
 * ���û�����ã���Ĭ�ϵ������ݿ��ڵ����п���
 * 
 * */
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
	
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getDishi() {
		return dishi;
	}
	public void setDishi(String dishi) {
		this.dishi = dishi;
	}
	public String getDishiname() {
		return dishiname;
	}
	public void setDishiname(String dishiname) {
		this.dishiname = dishiname;
	}
	public String getKaodianname() {
		return kaodianname;
	}
	public void setKaodianname(String kaodianname) {
		this.kaodianname = kaodianname;
	}
	public String getKaochang() {
		return kaochang;
	}
	public void setKaochang(String kaochang) {
		this.kaochang = kaochang;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getAdminname() {
		return adminname;
	}

	public void setAdminname(String adminname) {
		this.adminname = adminname;
	}

	public Integer getAdminid() {
		return adminid;
	}

	public void setAdminid(Integer adminid) {
		this.adminid = adminid;
	}

	public String getDidian() {
		return didian;
	}

	public void setDidian(String didian) {
		this.didian = didian;
	}

	public String getUstatu() {
		return ustatu;
	}

	public void setUstatu(String ustatu) {
		this.ustatu = ustatu;
	}
	
	
	
	
	
	
	
	
}