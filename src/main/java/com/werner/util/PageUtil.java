package com.werner.util;

import org.springframework.beans.factory.annotation.Value;

import lombok.Data;

@Data
public class PageUtil {

	@Value("1")
	private int currentPageNo;  //當前頁碼，來自於用戶輸入
	@Value("0")
	private int totalUserCount;  //總使用者數量
	@Value("0")
	private int perPageSize;  //每頁最大顯示數量
	@Value("0")
	private int totalPageCount;  //總頁數
	
	public void setCurrentPage(int currentPage) {
		if(currentPage>0) {
			this.currentPageNo = currentPage;
		}
	}
	
	public void setTotalUserCount(int totalUserCount) {
		if(totalUserCount>0) {
			this.totalUserCount = totalUserCount;
			this.setTotalPageCountByRs();
		}
	}
	
	public void setPerPageSize(int perPageSize) {
		if(perPageSize > 0) {
			this.perPageSize = perPageSize;
		}	
	}
	
	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}
	
	public void setTotalPageCountByRs() {
		int remainder = this.totalUserCount % this.perPageSize ;
		if(remainder == 0) {
			this.totalPageCount = this.totalUserCount / this.perPageSize;
		}
		if(remainder > 0) {
			this.totalPageCount = this.totalUserCount / this.perPageSize + 1;
		}
		if(remainder < 0) {
			this.totalPageCount = 0;
		}
	}
	
	
}
