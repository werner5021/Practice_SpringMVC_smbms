var path = $("#path").val();
var imgYes = "<img width='15px' src='"+path+"/statics/images/y.png' />";
var imgNo = "<img width='15px' src='"+path+"/statics/images/n.png' />";
/**
 * 提示訊息
 * element: 顯示提示的訊息（font）
 * css：提示的式樣
 * tipString: 提示內容
 * status：true/false 驗證狀態
 */
function validateTip(element,css,tipString,status){
	element.css(css);
	element.html(tipString);
	
	element.prev().attr("validateStatus",status);
}
var referer = $("#referer").val();