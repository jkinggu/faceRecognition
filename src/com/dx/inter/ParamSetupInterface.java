package com.dx.inter;

import java.util.List;

import com.dx.pojo.ParamSetup;

public interface ParamSetupInterface {
	//����ʱ����Ƿ����ӵ�����
	Integer getParamSetupBySj(String sj);
	//���ݵص��ѯ
	Integer getParamSetupByDd(String sj,String dd);
	//���ݿ�����ѯ
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