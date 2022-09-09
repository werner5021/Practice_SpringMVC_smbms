<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/jsp/common/head.jsp"%>
<div class="right">
    <div class="location">
        <strong>你現在所在的位置是:</strong>
        <span>使用者管理頁面 >> 使用者訊息查看頁面</span>
    </div>
    <div class="providerView">
        <p><strong>使用者帳號：</strong><span>${user.userCode }</span></p>
        <p><strong>使用者名稱：</strong><span>${user.userName }</span></p>
        <p><strong>性別：</strong>
            <span>
            		<c:if test="${user.gender == 1 }">男</c:if>
					<c:if test="${user.gender == 2 }">女</c:if>
				</span>
        </p>
        <p><strong>生日：</strong><span>${user.birthday }</span></p>
        <p><strong>電話：</strong><span>${user.phone }</span></p>
        <p><strong>地址：</strong><span>${user.address }</span></p>
        <p><strong>職稱：</strong><span>${user.userRoleName}</span></p>
        <div class="providerAddBtn">
            <input type="button" id="back" name="back" value="返回" >
        </div>
    </div>
</div>
</section>
<%@include file="/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/userview.js"></script>