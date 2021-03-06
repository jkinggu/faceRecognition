package com.dx.inter;

import java.util.List;

import com.dx.pojo.ParamSetup;

public interface ParamSetupInterface {
	//根据时间查是否添加到设置
	Integer getParamSetupBySj(String sj);
	//根据地点查询
	Integer getParamSetupByDd(String sj,String dd);
	//根据考场查询
	Integer getParamSetupByKc(String sj,String dd,String kc);
	String[] getDiShi();
	List<ParamSetup> getShezhiList(String string, String string2, String string3, String string4, String string5,
			String string6,String alltype);
	Integer updateParamRemove(String pids);
	Integer updateParamSetup(String pids);
	String[] getKaoDian(String val);
	String[] getKaoChang(String val);
	boolean initSysData();
	boolean initRenzhengData();
	Integer insertParamset(ParamSetup param);
	
	
}
