var userObj;

function cancleBtn(){
	$('.zhezhao').css('display', 'none');
	$('#removeUse').fadeOut();
}


$(function(){
	//通過jquery的class選擇器(array)
	//對每個class為viewUser的元素動作進行綁定(click) 
	/**
	 * bind、live、delegate
	 * on
	 */
	$(".viewUser").on("click",function(){
		var obj = $(this);
		window.location.href=`${path}/account/${obj.attr("userid")}`;			              
	});
	
	$(".modifyUser").on("click",function(){
		var obj = $(this);
		window.location.href=`${path}/view/userModify/${obj.attr("userid")}`; 
	});

	$('#no').click(function () {
		cancleBtn();
	});
	
	$('#yes').click(function () {
		deleteUser(userObj);
	});

	
	//刪除使用者
	$(".deleteUser").on("click",function(){
		var obj = $(this);
		if(confirm("是否刪除使用者【"+obj.attr("username")+"】?")){
			$.ajax({
				type:"DELETE",
				url:`${path}/account/${obj.attr("userid")}`,
				success:function(data){
					let jsonObj = JSON.parse(data);
					if(jsonObj.message == "true"){//删除成功：移除删除行
						alert("刪除使用者【"+obj.attr("username")+"】成功");
						obj.parents("tr").remove();
					}else if(jsonObj.message == "false"){//删除失败
						alert("刪除使用者【"+obj.attr("username")+"】失敗");
					}else if(jsonObj.message == "notexist"){
						alert("使用者【"+obj.attr("username")+"】不存在，請重新整理頁面");
					}
				},
				error:function(data){
					alert("使用者帳號出現異常，刪除失敗");
				}	
			});
		}
	});
});