<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/1/28
  Time: 19:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <script type="text/javascript">

    </script>
</head>
<body>
<div class="page-bar">
    <ul class="page-num-ul clearfix">
        <li>共${param.totalUserCount}筆資料&nbsp;&nbsp; ${param.currentPageNo }/${param.totalPageCount}頁</li>
        <c:if test="${param.currentPageNo > 1}">
            <a href="javascript:page_nav('userlist',1);">第一頁</a>
            <a href="javascript:page_nav('userlist',${param.currentPageNo-1});">上一頁</a>
        </c:if>
        <c:if test="${param.currentPageNo < param.totalPageCount }">
            <a href="javascript:page_nav('userlist',${param.currentPageNo+1 });">下一頁</a>
            <a href="javascript:page_nav('userlist',${param.totalPageCount });">最末頁</a>
        </c:if>
        &nbsp;&nbsp;
    </ul>
    <span class="page-go-form"><label>移動到</label>
	     <input type="text" name="inputPage" id="inputPage" class="page-key" />頁
	     <button type="button" class="page-btn" onClick='jump_to("userlist", document.getElementById("inputPage").value)'>GO</button>
		</span>
</div>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/rollpage.js"></script>
</html>