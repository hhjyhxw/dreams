mall = {
	base: "",
	currencySign: "￥",
	currencyUnit: "元",
	priceScale: "2",
	priceRoundType: "roundHalfUp"
};

//货币格式化 88.88
function priceFormat(price) {
	price = setScale(price, mall.priceScale, mall.priceRoundType);
	return price;
}
//货币格式化 88.88 元
function unitFormat(price) {
	price = setScale(price, mall.priceScale, mall.priceRoundType);
	return price + mall.currencyUnit;
}

//货币格式化￥ 88.88
function signFormat(price) {
	price = setScale(price, mall.priceScale, mall.priceRoundType);
	return mall.currencySign +price;
}
//货币格式化 ￥88.88 元
function currencyFormat(price) {
	price = setScale(price, mall.priceScale, mall.priceRoundType);
	return mall.currencySign + price + mall.currencyUnit;
}

$().ready( function() {
	
    //返回顶部
    $().add_gettop();

	/* ---------- List begin(部分页面需要查询)---------- */
	
	var $listForm = $("#listForm");// 列表表单
	if ($listForm.size() > 0) {
		var $searchButton = $("#searchButton");// 查找按钮
		var $pageNumber = $("#pageNumber");// 当前页码
		
		// 查找
		$searchButton.click( function() {
			$pageNumber.val("1");
			$listForm.submit();
		});
		
	}
	/* ---------- List end---------- */
	
	
	/*--------------购物导航start---------------*/
	var $navigationShop = $("#navigationShop");
	if($navigationShop.size() > 0 ){
		$(".sidelist").mousemove(function(){
			$(this).find(".i-list").show();
			$(this).find('h3').addClass("hover");
		});
		$(".sidelist").mouseleave(function(){
			$(this).find(".i-list").hide();
			$(this).find('h3').removeClass("hover");
		});
	   var $allGoodsCategoryDiv = $("#allGoodsCategoryDiv");
	   var $allGoodsCategoryContent = $("#allGoodsCategoryContent");
	   $allGoodsCategoryDiv.click(function(){
	       if($allGoodsCategoryContent.hasClass("ico_down")){
	    	   $allGoodsCategoryContent.removeClass("ico_down");
	    	   $allGoodsCategoryContent.addClass("ico_up");
	    	   $("#godosCategoryTree").show();
	       }else{
	    	   $allGoodsCategoryContent.removeClass("ico_up");
	    	   $allGoodsCategoryContent.addClass("ico_down");
	    	   $("#godosCategoryTree").hide();
	       }
	   });
	}
	/*--------------购物导航end---------------*/
	
	//登录页面赋值：是否记住用户名
	var $userName=$("#userName");
	if($userName.size()>0){
		var cookVal=getCookie("isRemeberUserName");
		if(cookVal != null){
			$userName.val(cookVal);
		}
	}
	
	//头部JS处理
	var $header = $("#header");
	if ($header.size() > 0) {
		
		//静态页通过cookie值来判断用户是否登录
		var $headerShowLoginWindow = $("#headerShowLoginWindow");
		var $headerShowRegisterWindow = $("#headerShowRegisterWindow");
		var $headerLoginMemberUsername = $("#headerLoginMemberUsername");
		var $headerMemberCenter = $("#headerMemberCenter");
		var $headerTradeCenter = $("#headerTradeCenter");
		var $headerLogout = $("#headerLogout");
		var $headerCarItemQuantity=$("#headerCarItemQuantity");
		
		$.flushHeaderInfo = function () {
			$headerCarItemQuantity.text(getHeaderCarItemCount());
			if(getCookie("memberUsername") != null && getCookie("memberUsername") != ''  && getCookie("memberUsername") != "") {
				$headerLoginMemberUsername.text(getCookie("memberUsername"));
				$headerMemberCenter.show();
				$headerLogout.show();
				$headerTradeCenter.show();
				$headerShowLoginWindow.hide();
				$headerShowRegisterWindow.hide();
			} else { 
				$headerLoginMemberUsername.text(null);
				$headerShowLoginWindow.show();
				$headerShowRegisterWindow.show();
				$headerMemberCenter.hide();
				$headerLogout.hide();
				$headerTradeCenter.hide();
			}
		}
		$.flushHeaderInfo();
	}
	
	//商品详细页 加入购物车处理
	var $cartClose = $("#cartClose");
	var $cartDiv = $("#gwcar");
	$cartClose.click(function(){
		$cartDiv.hide();
	});
	$.addCartItem = function(id, quantity) {
		if ($.trim(id) == "") {
			$.dialog({type: "warn", content: "请选择购买商品!", modal: true, autoCloseTime: 3000});
			return false;
		}
		if (!/^[0-9]*[1-9][0-9]*$/.test($.trim(quantity))) {
			$.dialog({type: "warn", content: "商品品数量必须为正整数!", modal: true, autoCloseTime: 3000});
			return false;
		}
		$.ajax({
			url: mall.base + "/shop/carItem!ajaxAdd.action",
			data: {"pId": id, "quantity": quantity},
			type: "POST",
			dataType: "json",
			cache: false,
			success: function(data) {
				if (data.status == "success") {
					$("#cartInfo").html('您的购物车中有<b>'+data.totalQuantity+'</b>件商品，金额共计<b>'+priceFormat(data.totalPrice)+'</b>元。');
					$("#headerCarItemQuantity").text(data.totalQuantity);
					$cartDiv.show();
				} else {
					$.dialog({type: data.status, content: data.message, modal: true, autoCloseTime: 3000});
				}
			}
		});
	 }
	
	//订单管理 - 再次购买
	$.againAddCartItem = function(id, quantity, callBackFunction) {
		$.ajax({
			url: mall.base + "/shop/carItem!ajaxAdd.action",
			data: {"pId": id, "quantity": quantity},
			type: "POST",
			dataType: "json",
			cache: false,
			success: callBackFunction
		});
	 }
	
	
	//列表页添加购物车
	$.quickAddCartItem = function(pSn,quantity) {
		if ($.trim(pSn) == "") {
			$.dialog({type: "warn", content: "请填写货号!", modal: true, autoCloseTime: 1500});
			return false;
		}
		if (!/^[0-9]*[1-9][0-9]*$/.test($.trim(quantity))) {
			$.dialog({type: "warn", content: "商品品数量必须为正整数!", modal: true, autoCloseTime: 1500});
			return false;
		}
		$.ajax({
			url: mall.base + "/shop/carItem!ajaxAdd.action",
			data: {"pSn": pSn,"quantity": quantity},
			type: "POST",
			dataType: "json",
			cache: false,
			success: function(data) {
				if (data.status == "success") {
					cartHtml = '您的购物车中有<b>'+data.totalQuantity+'</b>件商品，金额共计<b>'+priceFormat(data.totalPrice)+'</b>元。';
					$("#headerCarItemQuantity").text(data.totalQuantity);
				    $.dialog({type: "success", content: cartHtml, width: 420, modal: true});
				} else {
					$.dialog({type: data.status, content: data.message, modal: true, autoCloseTime: 3000});
				}
			}
		});
	}
	
	
	
	//列表页添加购物车
	$.listAddCartItem = function(pId,quantity) {
		if ($.trim(pId) == "") {
			$.dialog({type: "warn", content: "请选择购买商品!", modal: true, autoCloseTime: 3000});
			return false;
		}
		if (!/^[0-9]*[1-9][0-9]*$/.test($.trim(quantity))) {
			$.dialog({type: "warn", content: "商品品数量必须为正整数!", modal: true, autoCloseTime: 3000});
			return false;
		}
		$.ajax({
			url: mall.base + "/shop/carItem!ajaxAdd.action",
			data: {"pId": pId,"quantity": quantity},
			type: "POST",
			dataType: "json",
			cache: false,
			success: function(data) {
				if (data.status == "success") {
					cartHtml = '您的购物车中有<b>'+data.totalQuantity+'</b>件商品，金额共计<b>'+priceFormat(data.totalPrice)+'</b>元。';
					$("#headerCarItemQuantity").text(data.totalQuantity);
				    $.dialog({type: "success", content: cartHtml, width: 420, modal: true});
				} else {
					$.dialog({type: data.status, content: data.message, modal: true, autoCloseTime: 3000});
				}
			}
		});
	}
	
	//列表页申请分销
	$.listApplySale = function() {
		$.ajax({
			url: mall.base + "/shop/mCenter!ajaxApplySale.action",
			type: "POST",
			dataType: "json",
			cache: false,
			success: function(data) {
				if (data.status == "success") {
					cartHtml = '<div class="tc_cons">申请分销成功，等待审核</div>';
				    $.dialog({type: "info", content: cartHtml, width: 500, modal: true});
				} else {
					$.dialog({type: data.status, content: data.message, modal: true, autoCloseTime: 3000});
				}
			}
		});
	}

	
	//收藏商品
	$.addFavorite = function(gId,proStatus) {
		if ($.trim(gId) == "") {
			$.dialog({type: "warn", content: "请选择收藏商品!", modal: true, autoCloseTime: 3000});
			return false;
		}
		if(proStatus!='1'){
			proStatus = '0';
		}
		$.ajax({
			url: mall.base + "/member/favorite!ajaxAdd.action",
			data: {"id": gId,"proStatus":proStatus},
			type: "POST",
			dataType: "json",
			cache: false,
			success: function(data) {
				if (data.status == "success") {
					cartHtml = '<div class="tc_cons">收藏商品成功！</div>';
				    $.dialog({type: "info", content: cartHtml, width: 500, modal: true, autoCloseTime: 3000});
				} else {
					cartHtml = '<div class="tc_cons">'+data.message+'！</div>';
				    $.dialog({type: data.status, content: cartHtml, width: 500, modal: true, autoCloseTime: 3000});
				}
			},error: function(data){
				cartHtml = '<div class="tc_cons">'+data.message+'！</div>';
			    $.dialog({type: data.status, content: cartHtml, width: 500, modal: true, autoCloseTime: 3000});
			}
		});
	}
	
	
	
    /* ---------- GoodsList ---------- */
	
	var $goodsListForm = $("#goodsListForm");
	if ($goodsListForm.size() > 0) {
		var $brandId = $("#brandId");
		var $brand = $(".brand");
		var $goodsAttributeOption = $(".goodsAttributeOption");
		var $sort = $("#paixu .sort");// 排序
		var $orderBy = $("#orderBy");// 排序字段
		var $order = $("#order");// 排序方式
		var $pageNumber = $("#pageNumber");
		var $pageSize = $("#pageSize");
		var $apageSize = $(".pageSize");
		
		//每页显示数量
		$apageSize.click(function(){
			var $apageSize = $(this).attr("pageSize");
			$pageSize.val($apageSize);
			$pageNumber.val(1);
			$goodsListForm.submit();
		});
		
		// 排序
		$sort.click( function() {
			var $currentOrderBy = $(this).attr("name");
			if ($orderBy.val() == $currentOrderBy) {
				if ($order.val() == "") {
					$order.val("asc")
				} else if ($order.val() == "desc") {
					$order.val("asc");
				} else if ($order.val() == "asc") {
					$order.val("desc");
				}
			} else {
				$orderBy.val($currentOrderBy);
				$order.val("asc");
			}
			$pageNumber.val("1");
			$goodsListForm.submit();
		});
	
		// 排序图标效果
		if ($orderBy.val() != "") {
			$sort = $("#paixu .sort[name='" + $orderBy.val() + "']");
			if ($order.val() == "asc") {
				$sort.find("em").removeClass("red_top").addClass("red_bottom");
			} else {
				$sort.find("em").removeClass("red_bottom").addClass("red_top");
			}
		}
		
		//商品品牌选择查询
		$brand.click( function() {
			var $this = $(this);
			if ($this.hasClass("all")) {
				$brandId.val("");
			} else {
				$brandId.val($this.attr("brandId"));
			}
			$goodsListForm.submit();
			return false;
		});
		
		//商品属性选择查询
		$goodsAttributeOption.click( function() {
			var $this = $(this);
			$this.parent().parent().find(":hidden").attr("disabled", true);
			if (!$this.hasClass("all")) {
				$this.prev(":hidden").attr("disabled", false);
			}
			$goodsListForm.submit();
			return false;
		});
	}
	/*-----------------GoodsList------------------*/
	
	
    /* ---------- GoodsHistory ---------- */
	var $goodsHistory = $("#goodsHistory");
	if ($goodsHistory.size() > 0) {
		var $goodsHistoryListDetail = $("#goodsHistoryListDetail");
		var maxGoodsHistoryListCount = 5;
		$.addGoodsHistory = function(name, htmlPath,price,profitRate,imagePath) {
			var goodsHistory = {
				name: name,
				htmlPath: htmlPath,
				price: price,
				profitRate: profitRate,
				imagePath: imagePath
			};
			var goodsHistoryArray = new Array();
			var goodsHistoryListCookie = getCookie("goodsHistoryList");
			if(goodsHistoryListCookie) {
				goodsHistoryArray = eval(goodsHistoryListCookie);
			}
			var goodsHistoryListHtml = "";
			for (var i in goodsHistoryArray) {
				
				goodsHistoryListHtml +=
					'<dl>'+
		        	'<dt><a target="_bank" href="'+goodsHistoryArray[i].htmlPath+'"><img src="'+goodsHistoryArray[i].imagePath+'" alt="" /></a></dt>'+
		            '<dd class="tc juzis">'+goodsHistoryArray[i].name+'</dd>'+
		            '<dd>'+
		            	'<ul class="fencai">'+
		                	'<li>建议市场价：<em>'+goodsHistoryArray[i].price+'</em></li>'+
		                    '<li>利润率：<em>'+goodsHistoryArray[i].profitRate+'%</em></li>'+
		                '</ul>'+
		            '</dd>'+
		           '</dl>';
			}
			for (var i in goodsHistoryArray) {
				if(goodsHistoryArray[i].htmlPath == htmlPath) {
					return;
				}
			}
			if(goodsHistoryArray.length >= maxGoodsHistoryListCount) {
				goodsHistoryArray.shift();
			}
			goodsHistoryArray.push(goodsHistory);
			var newGoodsHistoryCookieString = "";
			for (var i in goodsHistoryArray) {
				newGoodsHistoryCookieString += ',{name: "' + goodsHistoryArray[i].name
				+ '", htmlPath: "' + goodsHistoryArray[i].htmlPath 
				+ '", price: "'+ goodsHistoryArray[i].price
				+ '", imagePath: "'+ goodsHistoryArray[i].imagePath
				+ '", profitRate: "' + goodsHistoryArray[i].profitRate
				+ '"}'
			}
			newGoodsHistoryCookieString = "[" + newGoodsHistoryCookieString.substring(1, newGoodsHistoryCookieString.length) + "]";
			setCookie("goodsHistoryList", newGoodsHistoryCookieString);
			//$.cookie("goodsHistoryList", newGoodsHistoryCookieString, {path: "/",expires:7});
		}
		
		var goodsHistoryArray = new Array();
		var goodsHistoryListCookie = getCookie("goodsHistoryList");
		if(goodsHistoryListCookie) {
			goodsHistoryArray = eval(goodsHistoryListCookie);
		}
		var goodsHistoryListHtml = "";
		for (var i in goodsHistoryArray) {
			goodsHistoryListHtml +=
			'<dl>'+
        	'<dt><a target="_bank" href="'+goodsHistoryArray[i].htmlPath+'"><img src="'+goodsHistoryArray[i].imagePath+'" alt="" /></a></dt>'+
            '<dd class="tc juzis">'+goodsHistoryArray[i].name+'</dd>'+
            '<dd>'+
            	'<ul class="fencai">'+
                	'<li>建议市场价：<em>'+goodsHistoryArray[i].price+'</em></li>'+
                    '<li>利润率：<em>'+goodsHistoryArray[i].profitRate+'%</em></li>'+
                '</ul>'+
            '</dd>'+
           '</dl>';
			//goodsHistoryListHtml += '<li><span class="icon">&nbsp;</span><a href="' + goodsHistoryArray[i].htmlPath + '">' + goodsHistoryArray[i].name + '</a></li>';
		}
		$goodsHistoryListDetail.html(goodsHistoryListHtml);
	}
	
	
	/*-------------------------------- header 公共js   --------------------------------*/	
	$('#gcar').hover(
			function() {
				reqCarItem();
				document.getElementById('carItemList').style.visibility = 'visible'
				$('#carItemList').stop(true, true);
				$('#carItemList').fadeIn();
			},
			function() {
				$('#carItemList').fadeOut();
			}
		)
	
	$('#carItemList').hover(
		function() {
			$('#carItemList').stop(true, true);
			$('#carItemList').show();
		},
		function() {
			$('#carItemList').fadeOut();
		}
	)
	/*-------------------------------- header 公共js   over--------------------------------*/	

	/*-------------------------------- 登录页 记住我的实现   --------------------------------*/
	var userNameCookie=getCookie("isRemeberUserName");
	if(userNameCookie !=null && userNameCookie!=''){
		$('#userName').val(userNameCookie);
		$('#remeber_username').attr('checked','checked');
	}
});

function getHeaderCarItemCount(){
	var quantity=getCookie("carItemCookieQuantityMark");
	if(quantity==null || quantity==''){
		quantity=0;
	}
	return quantity;
}
	//请求最新的购物车下拉
	function reqCarItem(){
		$.ajax({
			url:mall.base+'/shop/carItem!getShowListHtml.action',
  			data:{},
  			method:'POST',
  			dataType:'json',
  			success:function(data){
	            $('#indexCarItemHtml').html(data.showListHtml);
	            $('#totalQuantity').html(data.totalQuantity);
  			}
		});
	}

	//购物车跳转到购物车列表
	function toPayMent(){
		window.location.href=mall.base+"/shop/carItem!showList.action";
	}


/* ---------- AddCartItem ---------- */
	$addCartItem = function(pId, quantity) {
//		if ($.trim(id) == "") {
//			//$.dialog({type: "warn", content: "请选择购买商品!", modal: true, autoCloseTime: 3000});
//			alert("请选择购买商品!");
//			return false;
//		}
////		if (!/^[0-9]*[1-9][0-9]*$/.test($.trim(quantity))) {
////			//$.dialog({type: "warn", content: "商品品数量必须为正整数!", modal: true, autoCloseTime: 3000});
////			alert("商品品数量必须为正整数!");
////			return false;
////		}
		$.ajax({
			url: mall.base+ "/shop/carItem!ajaxAdd.action",
			data: {"pId": pId, "quantity": quantity},
			type: "POST",
			dataType: "json",
			cache: false,
			success: function(data) {
				$.dialog({type: data.status, content: data.message, modal: true, autoCloseTime: 3000});
//				if (data.status == "success") {
//					$.dialog({type: "success", content: "<span class=\"red\">" + data.message + "</span><br />商品总计: " + data.totalProductQuantity + " 件, 总金额: " + currencyFormat(data.totalProductPrice), width: 360, modal: true, autoCloseTime: 3000});
//				} else {
//					$.dialog({type: data.status, content: data.message, modal: true, autoCloseTime: 3000});
//				}
			}
		});
	}
	//更新购物车
	function updateCarItem(pId,quantity,callBackFunction){
		//更新cookie，更新数据库对应信息
		$.ajax({
  			url:mall.base+'/shop/carItem!ajaxUpdateCarItem.action',
  			data:{"pIds":pId,"quantity":quantity},
  			method:'POST',
  			dataType:'json',
  			success:function(data){
  				callBackFunction(data);
			}
		});
	}
	
	//header上的购物车删除
	function deleteHeaderCarItem(pId){
		updateCarItem(pId,'-1',function(data){
			if(data.status=='success'){
				$('#indexCarItemHtml').html(data.showListHtml);
				$("#headerCarItemQuantity").text(data.totalQuantity);
			}
		});
	}
	
	//确认收货-修改订单状态
	function sureReceiver(tid,callBackFunction){
		if(tid!=""){
			$.ajax({
	  			url:mall.base+"/member/trade!ajaxSureReceiver.action",
	  			data:{"id":tid},
	  			method:'POST',
	  			dataType:'json',
	  			success:callBackFunction
			});
		}else{
			$.dialog({type: "error", content: "收货异常，请确认订单信息是否正确！", modal: true, autoCloseTime: 3000});
		}
	}
	
	
	//根据 产品对应的规则集合，产品Id，数量，更新对应价格(在回调函数里面做)
	$priceChangeBycount = function(porductSaleRuleDatas,selectedProductId,quantity,successCallBack){
		   if(selectedProductId!=null && selectedProductId!=""){
			   $.each(porductSaleRuleDatas[selectedProductId],function(i){
				   var obj=eval(porductSaleRuleDatas[selectedProductId]);
					var minInterval = obj[i].minInterval;
					var maxInterval = obj[i].maxInterval;
					if(minInterval != "" && maxInterval != ""){
						if( quantity>= parseInt(minInterval) && quantity<=parseInt(maxInterval)){
							successCallBack(obj[i].price);
							return false;
						}else if(quantity<parseInt(minInterval)){
							successCallBack(obj[i].price);
							return false;
						}
					}else if(minInterval != "" && maxInterval == ""){
					    if(quantity>=parseInt(minInterval)){
					    	successCallBack(obj[i].price);
							return false;
						}
					}
				});
		   }
	}
	

  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
  ga('create', 'UA-4560422-4', 'mall.com');
  ga('send', 'pageview');
