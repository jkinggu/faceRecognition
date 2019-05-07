package com.dx.inter;

import java.util.List;

import com.dx.pojo.Belongfor;
import com.dx.pojo.Zkzdata;
import com.dx.query.PageResult;

public interface ZkzInterface {
	//按身份证查找准考证信息
	public Zkzdata findByPersonnum(String Upersonnum);

//	新增准考证信息
	public Integer insertZkz(Zkzdata zkzdata);

//	查询统计
	public List<String> getCountByCondition(String kaodian, String changci, String startime);
	public PageResult  getCountByPageCondition(String kaodian, String changci, String startime,Integer currentPage);

	public Integer insertBelf(Belongfor bef);

	public List<Zkzdata> getZkzList();
}
