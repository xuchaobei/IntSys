$(document).ready(function(){	
	$("#readBasicData").click(function(){	
		$("#fuzzy").show();		
		$.post(
		    "ReadBasicData!readManually.action?&ts="+new Date().getTime(),
		    {nosense:"nosense"},
		    function(rdata){
		    	if(rdata=="1")
		    	   alert("��ȡ��ɣ�");
		    	else
		    		alert("��ȡʧ�ܣ�");	
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
			    	   alert("��ȡ��ɣ�");
			    	else
			    		alert("��ȡʧ�ܣ�");				    
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
	    	        alert("��ȡ��ɣ�");
	    	    else {
	    	    	if(rdata=="1")
	    	    		alert("û�б������ݣ�");
	    	    	else if(rdata=="2")
	    	    		alert("���������ѷ��͹���");
	    	    	else
	    	    		alert("��ȡʧ�ܣ�");
	    	    }
	    		   			    
	    },
	    "json");
	});
});