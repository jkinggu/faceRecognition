package com.dx.util;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * */
public class PageUtil {
	/**根据总条数，每页条数，计算总页数*/
	public Integer getTotalPage(Integer allcount, Integer len) {
		Integer totalpage = 0 ;
		totalpage = allcount / len ;
		if(allcount % len != 0) {
			totalpage = totalpage + 1 ;
		}
		return totalpage ;
	}
	/**
	 * 根据当前页，返回开始的行数
	 * */
	public Integer getStartindex(Integer allcount, Integer len, Integer curpage) {
		Integer startindex = 0 ;
		startindex = (curpage-1)*len+1 ;
		return startindex ;
	}
	
	/**
	 * 根据当前页，返回结束的行数
	 * */
	public Integer getEndindex(Integer allcount, Integer len, Integer curpage) {
		Integer endindex = 0 ;
		Integer totalpage = this.getTotalPage(allcount, len);
		if(curpage==totalpage) {
			endindex = (totalpage-1)*len+allcount % len ;
		}else {
			endindex = curpage * len ;
		}
		return endindex ;
	}
	/**当前页的总条数*/
	public Integer getCurrpageSize(Integer allcount, Integer len, Integer curpage) {
		Integer curpagesize = 0 ;
		Integer totalpage = this.getTotalPage(allcount, len);
		if(curpage==totalpage) {
			curpagesize = allcount % len ;
		}else {
			curpagesize = len ;
		}
		return curpagesize ;
	}
}
