// 获取Cookie
function getCookie(name) {
	var value = document.cookie.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
	if (value != null) {
		return decodeURIComponent(value[2]);
    } else {
		return null;
	}
}
var memberUsernameId ="0";
var user_cookie="0";
if(null != getCookie("memberUsernameId") ){
	memberUsernameId=getCookie("memberUsernameId");
	user_cookie=getCookie("memberUsernameId");
}

/* 百分*/
window["_BFD"] = window["_BFD"] || {};
_BFD.client_id = "Cgxzy_hyzy";
_BFD.BFD_USER = {	
		"user_id" : memberUsernameId, //网站当前用户id，如果未登录就为0或空字符串		
		"user_cookie" : user_cookie //网站当前用户的cookie，不管是否登录都需要传		
		//"user_cookie" : "xxxxxxxxx" //网站当前用户的cookie，不管是否登录都需要传		
};
_BFD.script = document.createElement("script");
_BFD.script.type = "text/javascript";
_BFD.script.async = true;
_BFD.script.charset = "utf-8";
_BFD.script.src = (('https:' == document.location.protocol?'https://ssl-static1':'http://static1')+'.bfdcdn.com/service/haiyunzhiyou/haiyunzhiyou.js');
document.getElementsByTagName("head")[0].appendChild(_BFD.script);
//alert("baifendian 统计代码加载完成"+memberUsernameId);