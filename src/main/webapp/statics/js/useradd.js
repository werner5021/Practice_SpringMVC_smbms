var userCode = null;
var userName = null;
var userPassword = null;
var ruserPassword = null;
var phone = null;
var birthday = null;
var userRole = null;
var addBtn = null;
var backBtn = null;


$(function(){
	userCode = $("#userCode");
	userName = $("#userName");
	userPassword = $("#userPassword");
	ruserPassword = $("#ruserPassword");
	phone = $("#phone");
	birthday = $("#birthday");
	userRole = $("#userRole");
	addBtn = $("#add");
	backBtn = $("#back");
	//初始化的时候，要把所有的提示信息变为：* 以提示必填项，更灵活，不要写在页面上
	userCode.next().html("*");
	userName.next().html("*");
	userPassword.next().html("*");
	ruserPassword.next().html("*");
	phone.next().html("*");
	birthday.next().html("*");
	userRole.next().html("*");
		
	//獲取職稱(角色)清單
	$.ajax({
		type:"GET", 
		url:path+"/role", 
		dataType:"json",  //ajax
		success:function(data){  //data:回傳值(json字串)
			if(data != null){
				userRole.html("");
				var options = "<option value=\"0\">請選擇</option>";
				for(var i = 0; i < data.length; i++){
					options += "<option value=\""+data[i].id+"\">"+data[i].roleName+"</option>";
				}
				userRole.html(options);
			}
		},
		error:function(data){  //404、500
			validateTip(userRole.next(),{"color":"red"},imgNo+" 獲取職稱列表發生異常",false);
		}
	});
	
	//ajax後台驗證，是否已存在userCode
	userCode.bind("blur",function(){		
		$.ajax({
			type:"Get", 
			url:`${path}/account/checkUserName/${userCode.val()}`,	
			success:function(data){ //data:回傳值(json字串)			
				let jsonObj = JSON.parse(data);  //轉成json物件
				if(jsonObj.message == "exist"){  //帳號已存在，錯誤提示
					validateTip(userCode.next(),{"color":"red"},imgNo+ " 該帳號已被使用",false);
				}else{  //帳號不存在，可以使用
					validateTip(userCode.next(),{"color":"green"},imgYes+" 該帳號可使用",true);
				}
			},
			error:function(data){ //404、500
				validateTip(userCode.next(),{"color":"red"},imgNo+" 請輸入使用者帳號",false);
			}
		});		
	
	//使用者帳號提示	
	}).bind("focus",function(){  		
		validateTip(userCode.next(),{"color":"#666666"},"* 使用者帳號為登入系統的帳號",false);
	}).focus();
	
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
	
	//密碼提示
	userPassword.bind("focus",function(){   
		validateTip(userPassword.next(),{"color":"#666666"},"* 密碼長度須介於6-20個字元之間",false);
	}).bind("blur",function(){
		if(userPassword.val() != null && userPassword.val().length >= 6
				&& userPassword.val().length <= 20 ){
			validateTip(userPassword.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(userPassword.next(),{"color":"red"},imgNo + " 輸入格式不合規範，請重新輸入",false);
		}
	});
	
	//再次確認密碼提示
	ruserPassword.bind("focus",function(){  
		validateTip(ruserPassword.next(),{"color":"#666666"},"* 請輸入與上一欄位相同的密碼",false);
	}).bind("blur",function(){
		if(ruserPassword.val() != null && ruserPassword.val().length >= 6
				&& ruserPassword.val().length <= 20 && userPassword.val() == ruserPassword.val()){
			validateTip(ruserPassword.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(ruserPassword.next(),{"color":"red"},imgNo + " 兩次密碼輸入不一致，請重新輸入",false);
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
	
	//確認按鈕觸發
	addBtn.bind("click",function(){
		if(userCode.attr("validateStatus") != "true"){
			userCode.blur();
		}else if(userName.attr("validateStatus") != "true"){
			userName.blur();
		}else if(userPassword.attr("validateStatus") != "true"){
			userPassword.blur();
		}else if(ruserPassword.attr("validateStatus") != "true"){
			ruserPassword.blur();
		}else if(birthday.attr("validateStatus") != "true"){
			birthday.blur();
		}else if(phone.attr("validateStatus") != "true"){
			phone.blur();
		}else if(userRole.attr("validateStatus") != "true"){
			userRole.blur();
		}else{
			if(confirm("是否送出表單?")){
				$("#userForm").submit();
			}
		}
	});
	
	//返回按鈕觸發
	backBtn.on("click",function(){
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