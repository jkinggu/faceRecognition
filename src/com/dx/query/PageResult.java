package com.dx.query;

import java.util.List;

import com.dx.util.Const;

import lombok.Getter;

@Getter
public class PageResult {
	private List result;
	private Integer totalCount;

	private Integer currentPage=Const.FIRSTPAGE;
	private Integer pageSize=Const.PAGESIZE;

	private Integer firstPage =Const.FIRSTPAGE;
	private Integer next;
	private Integer previous;
	private Integer totalPage;// ��ҳ��
	public PageResult(List result, Integer totalCount, Integer currentPage) {
		super();
		this.result = result;
		this.totalCount = totalCount;
		this.currentPage = currentPage;
		
		this.totalPage=this.totalCount%this.pageSize==0?this.totalCount/this.pageSize:
			           this.totalCount/this.pageSize+1;
		this.next=this.currentPage<this.totalPage?this.currentPage+1:this.totalPage;
		this.previous=this.currentPage>1?this.currentPage-1:Const.FIRSTPAGE;
	}
}
