package com.dx.pojo;

public class PersonCard {
	//姓名
	private String pname;
	//性别
	private String psex;
	//名族
	private String pmin;
	//出生年月
	private String pbirth;
	//住址
	private String padd;
	//身份证号
	private String pnum;
	//起始日期
	private String pstart;
	//结束日期
	private String pend;
	//签发机关
	private String pdept;
	
	public PersonCard() {
		
	}
                                                                     
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((padd == null) ? 0 : padd.hashCode());
		result = prime * result + ((pbirth == null) ? 0 : pbirth.hashCode());
		result = prime * result + ((pdept == null) ? 0 : pdept.hashCode());
		result = prime * result + ((pend == null) ? 0 : pend.hashCode());
		result = prime * result + ((pmin == null) ? 0 : pmin.hashCode());
		result = prime * result + ((pname == null) ? 0 : pname.hashCode());
		result = prime * result + ((pnum == null) ? 0 : pnum.hashCode());
		result = prime * result + ((psex == null) ? 0 : psex.hashCode());
		result = prime * result + ((pstart == null) ? 0 : pstart.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PersonCard other = (PersonCard) obj;
		if (padd == null) {
			if (other.padd != null)
				return false;
		} else if (!padd.equals(other.padd))
			return false;
		if (pbirth == null) {
			if (other.pbirth != null)
				return false;
		} else if (!pbirth.equals(other.pbirth))
			return false;
		if (pdept == null) {
			if (other.pdept != null)
				return false;
		} else if (!pdept.equals(other.pdept))
			return false;
		if (pend == null) {
			if (other.pend != null)
				return false;
		} else if (!pend.equals(other.pend))
			return false;
		if (pmin == null) {
			if (other.pmin != null)
				return false;
		} else if (!pmin.equals(other.pmin))
			return false;
		if (pname == null) {
			if (other.pname != null)
				return false;
		} else if (!pname.equals(other.pname))
			return false;
		if (pnum == null) {
			if (other.pnum != null)
				return false;
		} else if (!pnum.equals(other.pnum))
			return false;
		if (psex == null) {
			if (other.psex != null)
				return false;
		} else if (!psex.equals(other.psex))
			return false;
		if (pstart == null) {
			if (other.pstart != null)
				return false;
		} else if (!pstart.equals(other.pstart))
			return false;
		return true;
	}

	public PersonCard(String pname, String psex, String pmin, String pbirth, String padd, String pnum, String pstart,
			String pend, String pdept) {
		super();
		this.pname = pname;
		this.psex = psex;
		this.pmin = pmin;
		this.pbirth = pbirth;
		this.padd = padd;
		this.pnum = pnum;
		this.pstart = pstart;
		this.pend = pend;
		this.pdept = pdept;
	}

	public String getPname() {
		return pname;
	}

	public String getPsex() {
		return psex;
	}

	public String getPmin() {
		return pmin;
	}

	public String getPbirth() {
		return pbirth;
	}

	public String getPadd() {
		return padd;
	}

	public String getPnum() {
		return pnum;
	}

	public String getPstart() {
		return pstart;
	}

	public String getPend() {
		return pend;
	}

	public String getPdept() {
		return pdept;
	}
	
	public void setPname(String pname) {
		this.pname = pname;
	}

	public void setPsex(String psex) {
		this.psex = psex;
	}

	public void setPmin(String pmin) {
		this.pmin = pmin;
	}

	public void setPbirth(String pbirth) {
		this.pbirth = pbirth;
	}

	public void setPadd(String padd) {
		this.padd = padd;
	}

	public void setPnum(String pnum) {
		this.pnum = pnum;
	}

	public void setPstart(String pstart) {
		this.pstart = pstart;
	}

	public void setPend(String pend) {
		this.pend = pend;
	}

	public void setPdept(String pdept) {
		this.pdept = pdept;
	}

	@Override
	public String toString() {
		return "PersonCard [pname=" + pname + ", psex=" + psex + ", pmin=" + pmin + ", pbirth=" + pbirth + ", padd="
				+ padd + ", pnum=" + pnum + ", pstart=" + pstart + ", pend=" + pend + ", pdept=" + pdept + "]";
	}
	
	
}
