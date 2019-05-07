package com.dx.pojo;


public class Belongfor implements java.io.Serializable{
	private String id ;
	private String allcode;
	private String no1code;
	private String no2code;
	private String no3code;
	private String no4code;
	private String no1name; 
	private String no2name;
	private String no3name ;
	private String no4name ;
	private String leibie ;
//	private String bubaotype ;
	public Belongfor() {}
	public Belongfor ( String id ,String allcode, String no1code, String no2code,String no3code, String no4code,
			String no1name, String no2name, String no3name, String no4name,String leibie){//,String bubaotype
		this.id = id ;
		this.allcode = allcode ; 
		this.no1code = no1code ;
		this.no2code = no2code ;
		this.no3code = no3code ;
		this.no4code = no4code ;
		this.no1name = no1name ;
		this.no2name = no2name ;
		this.no3name = no3name ;
		this.no4name = no4name ;
		this.leibie = leibie ;
		//this.bubaotype = bubaotype ;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAllcode() {
		return allcode;
	}

	public void setAllcode(String allcode) {
		this.allcode = allcode;
	}
	public String getNo1code() {
		return no1code;
	}
	public void setNo1code(String no1code) {
		this.no1code = no1code;
	}
	public String getNo2code() {
		return no2code;
	}
	public void setNo2code(String no2code) {
		this.no2code = no2code;
	}
	public String getNo3code() {
		return no3code;
	}
	public void setNo3code(String no3code) {
		this.no3code = no3code;
	}
	public String getNo4code() {
		return no4code;
	}
	public void setNo4code(String no4code) {
		this.no4code = no4code;
	}
	public String getNo1name() {
		return no1name;
	}
	public void setNo1name(String no1name) {
		this.no1name = no1name;
	}
	public String getNo2name() {
		return no2name;
	}
	public void setNo2name(String no2name) {
		this.no2name = no2name;
	}
	public String getNo3name() {
		return no3name;
	}
	public void setNo3name(String no3name) {
		this.no3name = no3name;
	}
	public String getNo4name() {
		return no4name;
	}
	public void setNo4name(String no4name) {
		this.no4name = no4name;
	}
	public String getLeibie() {
		return leibie;
	}
	public void setLeibie(String leibie) {
		this.leibie = leibie;
	}
//	public String getBubaotype() {
//		return bubaotype;
//	}
//	public void setBubaotype(String bubaotype) {
//		this.bubaotype = bubaotype;
//	}
//	
}
