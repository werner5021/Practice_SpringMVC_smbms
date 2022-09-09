package com.werner.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseBean<T> {
	
	public static final String OK = "200"; //成功
	public static final String CREATED = "201"; //資源新增成功
	public static final String ACCEPTED = "202"; //請求已被接受，但仍在處理中
	public static final String NO_CONTENT = "204"; //請求成功，但不返回內容
	
	public static final String BAD_REQUEST = "400"; //缺少必須欄位、資料錯誤
	public static final String UNAUTHORIZED = "401"; //用戶需要認證
	public static final String FORBIDDEN = "403"; //已認證，無權限
	public static final String NOT_FOUND = "404"; //查無資源
	public static final String GONE = "410"; //資源已過期
	
	public static final String INTERNAL_SERVER_ERROR = "500"; //伺服器錯誤
	public static final String BAD_GATEWAY = "502"; //伺服器某個服務掛掉
	public static final String GATEWAY_TIMEOUT = "504"; //無反應
	
	private String responseStatus;
	private List<T> dataList;	
	private String message;
	
	
}
