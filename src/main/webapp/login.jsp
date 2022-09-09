<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<title>系統登入-超市訂單管理系統</title>
		<link type="text/css" rel="stylesheet"
		href="${pageContext.request.contextPath}/statics/css/style.css">
	</head>
	<script type="text/javascript">
	</script>
<body class="login_bg">
	<section class="loginBox">
		<header class="loginHeader">
			<h1>超市訂單管理系統</h1>
		</header>
		<section class="loginCont">
			<form class="loginForm" action="${pageContext.request.contextPath}/login" name="actionForm" id="actionForm" method="post">
				<div class="info">${error}</div>
				<div class="inputbox">
					<label>用戶名:</label>
					<input type="text" class="input-text" id="userCode" name="userCode" placeholder="請輸入用戶名" required/>
				</div>
				<div class="inputcox">
					<label>密碼名:</label>
					<input type="password" id="userPassword" name="userPassword" placeholder="請輸入密碼" required/>
				</div>
				<div class="subBtn">
					<input type="submit" value="登入"/>
					<input type="reset" value="重置"/>
				</div>
			</form>
		</section>
	</section>
</body>
</html>