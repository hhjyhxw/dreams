$.fn.ready( function() {
	localStorage.btnclick = 1  
	//防止按钮重复点击 页面加载时候给初始值
})	
	/**
	 * 后台根据订单在订单下显示对应的商品信息
	 * */
	function itemList(dom,id){ 
	     var url = getRootPath()+"/admin/trade!ajaxGetOrderItem.action";
	     debugger;
		 if(localStorage.btnclick && 0<localStorage.btnclick){
			 localStorage.btnclick = -1;
			 var tradeRow = $(dom).parent().parent().parent()
 			 var orderItem= $('.orderItem'+id);
			 if(orderItem && 0 < orderItem.length){
				 	orderItem.toggle();
				 	localStorage.btnclick = 1;
			 }else{
				 $.ajax({
						url:url,
						data:{'id':id,'tdLength':tradeRow.find("td").length},
			  			method:'POST',
			  			dataType:'json',
			  			success:function(data){
			  				localStorage.btnclick = 1;
			  				tradeRow.after(data.html);  
			  			}
				 }); 
			 }
		 }
	 } 