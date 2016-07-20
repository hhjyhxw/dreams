
//判断非空
function isNullValue(val) {
	if (val == '') {return true;}
	var regu = "^[ ]+$|^[ ]+$";
	var re = new RegExp(regu);
	if (re.test(val) == true) {return true;}
	return false;
}

function del(deliveryId){
	var delivery=$("#"+deliveryId+"");
	$.ajax({
		url:'delivery_address!delete.action',
		data:{"id":deliveryId},
		method:'POST',
		dataType:'json',
		success:function(data){
			if(data.status=='success'){
				delivery.parent().empty();
				
			}else{
				alert(data.message);
			}
		}
	});
}
function addDelivery(){
	var provinceId= $("#provinceIdDelivery").val();
	var provinceName = document.getElementById("provinceIdDelivery").options[document.getElementById("provinceIdDelivery").selectedIndex].text;
	var cityId=$("#cityIdDelivery").val();
	var cityName = document.getElementById("cityIdDelivery").options[document.getElementById("cityIdDelivery").selectedIndex].text;
	var areaId=$("#areaIdDelivery").val();
	var areaName = document.getElementById("areaIdDelivery").options[document.getElementById("areaIdDelivery").selectedIndex].text;
	var detailAddress=$("#detailAddress").val();
	var latitude=$("#latitudeDelivery").val();
	var longitude=$("#longitudeDelivery").val();
	var flag=true;
	if(isNullValue(provinceId)&&flag){
		alert('请选择省份！');
		flag=false;
	}
	if(isNullValue(cityId)&&flag){
		alert('请选择市区！');
		flag=false;
	}
	if(isNullValue(areaId)&&flag){
		alert('请选择县区！');
		flag=false;
	}
	if(isNullValue(detailAddress)&&flag){
		alert('请填写详细地址！');
		flag=false;
	}
	if(isNullValue(latitude)&&flag){
		alert('请填写纬度！');
		flag=false;
	}
	if(isNullValue(longitude)&&flag){
		alert('请填写经度！');
		flag=false;
	}
	if(flag){
		$("#pop").hide();
	 	$("#exposeMask").hide();
		$.ajax({
			url:'delivery_address!save.action',
			data:{"provinceId":provinceId,
				"provinceName":provinceName,
				"cityId":cityId,
				"cityName":cityName,
				"areaId":areaId,
				"areaName":areaName,
				"detailAddress":detailAddress,
				"latitude":latitude,
				"longitude":longitude},
			method:'POST',
			dataType:'json',
			success:function(data){
				if(data.status=='success'){
				 	$("#addaddress").append(data.message);
				}
			}
		});
	}

}

$.fn.ready( function() {
	$('#provinceId').change(function(){
		var provinceVal=$(this).val();
		var provinceName=$(this).find('option:selected').html();
		$.ajax({
			url:'supplier_store!ajaxProvinceChange.action',
  			data:{"provinceId":provinceVal},
  			method:'POST',
  			dataType:'json',
  			success:function(data){
  				if(data.status == 'success'){
  					$("#cityId option").each(function() {                            
                        $(this).remove();   //移除原有项                        
                    }); 
                    $("<option value=''>请选择</option>").appendTo("#cityId");
                    $(data.message).appendTo("#cityId");        //将返回来的项添加到下拉菜单中
                    
                    
                    $("#areaId option").each(function() {                            
                        $(this).remove();   //移除原有项                        
                    }); 
                    $("<option value=''>请选择</option>").appendTo("#areaId");
  				}
  			}
		});
		
		$('#provinceName').val(provinceName);
		$('#cityName').val("");
		$('#areaName').val("");
	});
	
	//市的改变事件
	$('#cityId').change(function(){
		var cityVal=$(this).val();
		var cityName=$(this).find('option:selected').html();
		$.ajax({
			url:'supplier_store!ajaxCityChange.action',
  			data:{"cityId":cityVal},
  			method:'POST',
  			dataType:'json',
  			success:function(data){
  				if(data.status == 'success'){
  					$("#areaId option").each(function() {                            
                        $(this).remove();   //移除原有项                        
                    }); 
                    $("<option value=''>请选择</option>").appendTo("#areaId");
                    $(data.message).appendTo("#areaId");        //将返回来的项添加到下拉菜单中
  				}
  			}
		});
		
		$('#cityName').val(cityName);
		$('#areaName').val("");
	});

	
	//区的改变
	$('#areaId').change(function(){
		var areaName=$(this).find('option:selected').html();
		$('#areaName').val(areaName);
	});
	
	
	
});
