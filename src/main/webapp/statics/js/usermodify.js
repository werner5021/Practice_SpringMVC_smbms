var userName = null;
var birthday = null;
var phone = null;
var userRole = null;
var saveBtn = null;
var backBtn = null;

$(function(){
	userName = $("#userName");
	birthday = $("#birthday");
	phone = $("#phone");
	userRole = $("#userRole");
	saveBtn = $("#save");
	backBtn = $("#back");
	
	userName.next().html("*");
	birthday.next().html("*");
	phone.next().html("*");
	userRole.next().html("*");	
	
	//獲取職稱(角色)清單
	$.ajax({
		type:"GET",
		url:`${path}/role`,
		dataType:"json",
		success:function(data){
			if(data != null){
				var rid = $("#rid").val();
				userRole.html("");
				var options = "<option value=\"0\">請選擇</option>";
				for(var i = 0; i < data.length; i++){
					if(rid != null && rid != undefined && data[i].id == rid ){
						options += "<option selected=\"selected\" value=\""+data[i].id+"\" >"+data[i].roleName+"</option>";
					}else{
						options += "<option value=\""+data[i].id+"\" >"+data[i].roleName+"</option>";
					}					
				}
				userRole.html(options);
			}
		},
		error:function(data){  //404、500
			validateTip(userRole.next(),{"color":"red"},imgNo+" 獲取職稱列表發生異常",false);
		}
	});
	
	//使用者名稱提示
	userName.bind("focus",function(){
		validateTip(userName.next(),{"color":"#666666"},"* 該欄位須介於1~10個字元之間",false);
	}).bind("blur",function(){
		if(userName.val() != null && userName.val().length > 1
				&& userName.val().length < 10){
			validateTip(userName.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(userName.next(),{"color":"red"},imgNo+" 輸入格式不合規範，請重新輸入",false);
		}
		
	});
	
	//生日提示
	birthday.bind("focus",function(){
		validateTip(birthday.next(),{"color":"#666666"},"* 選擇以輸入日期",false);
	}).bind("blur",function(){
		if(birthday.val() != null && birthday.val() != ""){
			validateTip(birthday.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(birthday.next(),{"color":"red"},imgNo + " 日期不可為空，請重新輸入",false);
		}
	});
	
	//手機提示
	phone.bind("focus",function(){
		validateTip(phone.next(),{"color":"#666666"},"* 請輸入手機號碼",false);
	}).bind("blur",function(){
		var patrn=/^09\d{8}$/;
		if(phone.val().match(patrn)){
			validateTip(phone.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(phone.next(),{"color":"red"},imgNo + " 輸入格式不合規範，請重新輸入",false);
		}
	});
	
	//職稱(角色)提示
	userRole.bind("focus",function(){
		validateTip(userRole.next(),{"color":"#666666"},"* 請選擇職稱",false);
	}).bind("blur",function(){
		if(userRole.val() != null && userRole.val() > 0){
			validateTip(userRole.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(userRole.next(),{"color":"red"},imgNo + " 請重新選擇職稱",false);
		}
	});
	
	//儲存按鈕觸發
	saveBtn.on("click",function(){
		userName.blur();
		phone.blur();
		birthday.blur();
		userRole.blur();
		if(userName.attr("validateStatus") == "true" 
			&& phone.attr("validateStatus") == "true"
			&& birthday.attr("validateStatus") == "true"
			&& userRole.attr("validateStatus") == "true"){
			if(confirm("是否確認修改資訊？")){
				$("#userForm").submit();
			}
		}
	});
	
	//返回按鈕觸發
	backBtn.on("click",function(){
		//alert("modify: "+referer);
		if(referer != undefined 
			&& null != referer 
			&& "" != referer
			&& "null" != referer
			&& referer.length > 4){
		 window.location.href = referer;
		}else{
			history.back(-1);
		}
	});
});