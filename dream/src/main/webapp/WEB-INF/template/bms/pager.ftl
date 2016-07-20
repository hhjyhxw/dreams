<script type="text/javascript">
$().ready( function() {
	
	var $pager = $("#pager");
	
	$pager.pager({
		pagenumber: ${pager.pageNo},
		pagecount: ${pager.totalPages},
		buttonClickCallback: $.gotoPage
	});

})
</script>
<span id="pager"></span>
<input type="hidden" name="pager.pageNo" id="pageNumber" value="${pager.pageNo}" />
<input type="hidden" name="pager.orderBy" id="orderBy" value="${pager.orderBy}" />
<input type="hidden" name="pager.order" id="order" value="${pager.order}" />