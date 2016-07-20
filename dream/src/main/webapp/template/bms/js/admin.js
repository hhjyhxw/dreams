mall = {
	base: "",
	currencySign: "￥",
	currencyUnit: "元",
	priceScale: "2",
	priceRoundType: "roundHalfUp"
};

// 编辑器
if(typeof(KE) != "undefined") {
	KE.show({
		id: "editor",
		height: "400px",
		items: ['source', '|', 'fullscreen', 'undo', 'redo', 'print', 'cut', 'copy', 'paste','plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright','justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript','superscript', '|', 'selectall', '-','title', 'fontname', 'fontsize', '|', 'textcolor', 'bgcolor', 'bold','italic', 'underline', 'strikethrough', 'removeformat', '|', 'image','flash', 'media', 'advtable', 'hr', 'emoticons', 'link', 'unlink'],
		imageUploadJson: mall.base + "/admin/file!ajaxUpload.action",
		fileManagerJson: mall.base + "/admin/file!ajaxBrowser.action",
		allowFileManager: true,
		autoSetDataMode: true
	});
}

// 货币格式化
function currencyFormat(price) {
	price = setScale(price, mall.priceScale, mall.priceRoundType);
	return mall.currencySign + price + mall.currencyUnit;
}

$().ready( function() {

	/* ---------- List ---------- */
	
	var $listForm = $("#listForm");// 列表表单
	if ($listForm.size() > 0) {
		var $searchButton = $("#searchButton");// 查找按钮
		var $allCheck = $("#listTable input.allCheck");// 全选复选框
		var $listTableTr = $("#listTable tr:gt(0)");
		var $idsCheck = $("#listTable input[name='ids']");// ID复选框
		var $deleteButton = $("#deleteButton");// 删除按钮
		var $sendButton = $("#sendButton");// 发送按钮
		var $clearButton = $("#clearButton");//清空按钮
		var $pageNumber = $("#pageNumber");// 当前页码
		var $pageSize = $("#pageSize");// 每页显示数
		var $sort = $("#listTable .sort");// 排序
		var $orderBy = $("#orderBy");// 排序字段
		var $order = $("#order");// 排序方式
		var $arrangeTrade = $("#arrangeTrade");//刷新订单状态，将已经过期的订单状态修改为作废
		// 全选
		$allCheck.click( function() {
			var $this = $(this);
			if ($this.attr("checked")) {
				$idsCheck.attr("checked", true);
				$deleteButton.attr("disabled", false);
				$clearButton.attr("disabled", false);
				$sendButton.attr("disabled", false);
				$listTableTr.addClass("checked");
			} else {
				$idsCheck.attr("checked", false);
				$deleteButton.attr("disabled", true);
				$clearButton.attr("disabled", true);
				$sendButton.attr("disabled", true);
				$listTableTr.removeClass("checked");
			}
		});
		
		// 无复选框被选中时,删除按钮不可用
		$idsCheck.click( function() {
			var $this = $(this);
			if ($this.attr("checked")) {
				$this.parent().parent().addClass("checked");
				$deleteButton.attr("disabled", false);
				$clearButton.attr("disabled", false);
				$sendButton.attr("disabled", false);
			} else {
				$this.parent().parent().removeClass("checked");
				var $idsChecked = $("#listTable input[name='ids']:checked");
				if ($idsChecked.size() > 0) {
					$deleteButton.attr("disabled", false);
					$clearButton.attr("disabled", false);
					$sendButton.attr("disabled", false);
				} else {
					$deleteButton.attr("disabled", true)
					$clearButton.attr("disabled", true)
					$sendButton.attr("disabled", false);
				}
			}
		});
		
		// 批量删除
		$deleteButton.click( function() {
			debugger
			var url = $(this).attr("url");
			var $idsCheckedCheck = $("#listTable input[name='ids']:checked");
			console.log("$idsCheckedCheck.serialize():"+$idsCheckedCheck.serialize())
			$.dialog({type: "warn", content: "您确定要删除吗?", ok: "确 定", cancel: "取 消", modal: true, okCallback: batchDelete});
			function batchDelete() {
				
				$.ajax({
					url: url,
					data: $idsCheckedCheck.serialize(),
					type: "POST",
					dataType: "json",
					cache: false,
					success: function(data) {
						if (data.status == "success") {
							$idsCheckedCheck.parent().parent().remove();
						}
						$deleteButton.attr("disabled", true);
						$allCheck.attr("checked", false);
						$idsCheckedCheck.attr("checked", false);
						$.message({type: data.status, content: data.message});
					},error: function(data){
						alert(data.status+data.message);
					}
				});
			}
		});
		
		// 批量发送会员福利
		$sendButton.click( function() {
			debugger
			var url = $(this).attr("url");
			var $idsCheckedCheck = $("#listTable input[name='ids']:checked");
			$.dialog({type: "warn", content: "您确定要发送选中会员的会员福利吗?", ok: "确 定", cancel: "取 消", modal: true, okCallback: batchDelete});
			function batchDelete() {
				
				$.ajax({
					url: url,
					data: $idsCheckedCheck.serialize(),
					type: "POST",
					dataType: "json",
					cache: false,
					success: function(data) {
						
						$sendButton.attr("disabled", true);
						$allCheck.attr("checked", false);
						$idsCheckedCheck.attr("checked", false);
						$.message({type: data.status, content: data.message});
					},error: function(data){
						alert(data.status+data.message);
					}
				});
			}
		});
		
		//将过期的订单状态修改为废单
		$arrangeTrade.click(function(){
			var url = $(this).attr("url");
			$.dialog({type: "warn", content: "您确定要更新状态吗?", ok: "确 定", cancel: "取 消", modal: true, okCallback: batchArrange});
			function batchArrange(){
				$.ajax({
					url: url,
					type: "POST",
					dataType: "json",
					cache: false,
					success: function(data) {
						//$.message({type: data.status, content: data.message});
						$listForm.submit();
					},error: function(data){
						alert(data.status+data.message);
					}
				});
			}
		});
		
		// 批量清空
		$clearButton.click( function() {
			var url = $(this).attr("url");
			var $idsCheckedCheck = $("#listTable input[name='ids']:checked");
			$.dialog({type: "warn", content: "您确定要清空吗?<br/><span class='red'> 温馨提示：清空数据后，将无法恢复，请慎重！</span>", ok: "确 定", cancel: "取 消", modal: true, okCallback: batchDelete});
			function batchDelete() {
				$.ajax({
					url: url,
					data: $idsCheckedCheck.serialize(),
					type: "POST",
					dataType: "json",
					cache: false,
					success: function(data) {
						if (data.status == "success") {
							$idsCheckedCheck.parent().parent().remove();
						}
						$clearButton.attr("disabled", true);
						$allCheck.attr("checked", false);
						$idsCheckedCheck.attr("checked", false);
						$.message({type: data.status, content: data.message});
					},error: function(data){
						alert(data.status+data.message);
					}
				});
			}
		});
	
		// 查找
		$searchButton.click( function() {
			$pageNumber.val("1");
			$listForm.submit();
		});
	
		// 每页显示数
		$pageSize.change( function() {
			$pageNumber.val("1");
			$listForm.submit();
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
			$listForm.submit();
		});
	
		// 排序图标效果
		if ($orderBy.val() != "") {
			$sort = $("#listTable .sort[name='" + $orderBy.val() + "']");
			if ($order.val() == "asc") {
				$sort.removeClass("desc").addClass("asc");
			} else {
				$sort.removeClass("asc").addClass("desc");
			}
		}
		
		// 页码跳转
		$.gotoPage = function(id) {
			$pageNumber.val(id);
			$listForm.submit();
		}
	}
	
});