$(function(){
	var pathName=window.document.location.pathname; 
	var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1); 
 setInterval(function(){  
	 var ul03=$("#ul03")
	 var ul06=$("#ul06")
            zong()
    		sheng()  	
    },5000);
 setInterval(function(){    
	 var ul=$("#ul01")
    	z()
 },5000);
  	var thisURL = document.URL;
    var getval =thisURL.split('?')[1];
    var showvals= getval.split("&")[0];
    var showval= showvals.split("=")[1];
    
    
    var thisURL6 = document.URL;
    var getval6 =thisURL6.split('?')[1];
    var showvals6= getval6.split("&")[1];
    var showval6= showvals6.split("=")[1];
    function z(){
	var ul=$("#ul01")
	ul.html("")
	$.ajax({
		url: "/code_dg/weblook",
		type:"post",
		data:{
			"game":showval
		},
		success:function(response){
			$.each(response,function(i,v){		
				++i;
				ul.append("<li class='list'  >" +
						"<div class='list_wrap'>" +
						" <div class='theChars_index'>"+i+"</div>" +
						 " <div class='theChars_picture'>"+
						  "  <img src='' alt=''>" +
						 " </div>" +
						" <div class='theChars_name'  style='color:"+v.team.color+";' '>"+v.player.name+"</div> " +
						 " <div class='theChars_department'>" +
						" <div class='department_de' style='color:"+v.team.color+";' >"+v.team.name+"</div>" +
						 "  </div>" +
						" <div class='theChars_source'><span class='source' style='color:"+v.team.color+";'> 总分："+v.score+"</span></div>" +
						" <i class='icon' onclick='on("+v.player.id+")'><img src='img/hide.png' alt='' ></i>" +
						 " </div>" +
						"  </li>")
			})
		}
	})
    }
    var ul=$("#ul01")
	ul.html("")
	$.ajax({
		url: "/code_dg/weblook",
		type:"post",
		data:{
			"game":showval
		},
		success:function(response){
			$.each(response,function(i,v){		
				++i;
				ul.append("<li class='list'  >" +
						"<div class='list_wrap'>" +
						" <div class='theChars_index'>"+i+"</div>" +
						 " <div class='theChars_picture'>"+
						  "  <img src='' alt=''>" +
						 " </div>" +
						" <div class='theChars_name'  style='color:"+v.team.color+";' '>"+v.player.name+"</div> " +
						 " <div class='theChars_department'>" +
						" <div class='department_de' style='color:"+v.team.color+";' >"+v.team.name+"</div>" +
						 "  </div>" +
						" <div class='theChars_source'><span class='source' style='color:"+v.team.color+";'> 总分："+v.score+"</span></div>" +
						" <i class='icon' onclick='on("+v.player.id+")'><img src='img/hide.png' alt='' ></i>" +
						 " </div>" +
						"  </li>")
			})
		}
	})
	function zong(){

	var ul03=$("#ul03")
		ul03.html("")
	$.ajax({
		url: "/code_dg/zong",
		type:"post",
		data:{
			"class":showval6
		},
		success:function(response){
			$.each(response,function(i,v){
				console.log(v)
				++i;
				ul03.append("<li class='list' style='background:"+v.color+";' >"+
						"<div class='list-outWrap'>"+
						"<div class='list_wrap'>"+
						"<div class='theCharsTeam_department'>"+
						"<div class='department_de'>"+v.name+"</div>"+
						"</div>"+
						" <div class='heng'>"+
						" <div class='theCharsTeam_source'>"+
						"<div class='source_text'>总分：</div>"+
						 "<div class='source_num'>"+v.total+"</div>"+
						 "</div>"+
						"<div class='theCharsTeam_pj'>"+
						"<div class='source_text'>平均分：</div>"+
						"<div class='source_num'>"+v.average+"</div>"+
						" </div>"+
						" </div>"+
						 "</div>"+
						 "<div class='theCharsTeam_index'>"+i+"</div>"+
						"</div>"+
						"</li>")
			})
		}
	})
	
	}
	
	

	
	
		function sheng() {
	
		var ul06=$("#ul06")
		ul06.html("")
		$.ajax({
		url:"/code_dg/sheng",
		type:"post",
		data:{
			"game":showval,
			"class":showval6
		},
		success:function(response){
			$.each(response,function(i,v){
					ul06.append("<li style='background:"+v.team.color+";'>"+
							" <div class='surplus_li'>"+
							  "<div class='surplus_name'>"+v.employee.name+"</div>"+
							  "<div class='surplus_remain'>"+
							" <div class='surplus_num'>"+v.count+"</div>"+
							"  <div class='surplus_zhang'>张</div>"+
							 "</div>"+
							 "</div>"+
							 " </li>")
			})
			
		}
	})
	
	
		}

	
})