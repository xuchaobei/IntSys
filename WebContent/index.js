$(document).ready(function(){	
	$("#readBasicData").click(function(){	
		$("#fuzzy").show();		
		$.post(
		    "ReadBasicData!readManually.action?&ts="+new Date().getTime(),
		    {nosense:"nosense"},
		    function(rdata){
		    	if(rdata=="1")
		    	   alert("读取完成！");
		    	else
		    		alert("读取失败！");	
		    	$("#fuzzy").hide();
		    },
		    "json");
	});
	$("#read").click(function(){
		labApplyNo=$("#readDeclData").val();
		$.post(
			    "ReadTestReport!updateItemLabData.action?&ts="+new Date().getTime(),
			    {labApplyNo:labApplyNo},
			    function(rdata){
			    	if(rdata=="1")
			    	   alert("读取完成！");
			    	else
			    		alert("读取失败！");				    
			    },
			    "json");
	});
	$("#send").click(function(){	
		declNo=$("#sendDeclData").val();	
		$.post(
	        "SendDeclData!getLabApply.action?&ts="+new Date().getTime(),
	        {declNo:declNo},
	        function(rdata){	        	
	        	if(rdata=="0")
	    	        alert("读取完成！");
	    	    else {
	    	    	if(rdata=="1")
	    	    		alert("没有报验数据！");
	    	    	else if(rdata=="2")
	    	    		alert("报验数据已发送过！");
	    	    	else
	    	    		alert("读取失败！");
	    	    }
	    		   			    
	    },
	    "json");
	});
});