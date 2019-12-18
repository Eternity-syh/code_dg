$(function(){
	

	var pathName=window.document.location.pathname; 
	var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1); 
	
	var thisURL = document.URL;
    var getval =thisURL.split('?')[1];
    var showvals= getval.split("&")[0];
    var showval= showvals.split("=")[1];
    
    var thisURL6 = document.URL;
    var getval6 =thisURL6.split('?')[1];
    var showvals6= getval6.split("&")[1];
    var showval6= showvals6.split("=")[1];
	var ul=$("#ulul")
	$.ajax({
		url:projectName+"/look",
		type:"post",
		data:{
			"game":showval6,
			"wechat":showval
		},
		success:function(response){
			$.each(response.gjp,function(i,v){
				if(response.fetch.id==v.player.id){
					document.write("您是本场的参赛人员")
					 $("input[type='checkbox']:not(:checked)").attr("disabled",true)
					return;
				}else{
					ul.append("<li class='lib' style='background:"+v.team.color+";'>"+
							"<div class='person'>"+
							 "<div class='photo'><img src='images/1.jpg' alt=''></div>"+
							 "<div class='name'><span>"+v.player.name+"</span><br/><span>"+v.team.name+"</span></div>"+
							 "<p class='score'>当前分数："+v.score+"</p>"+
							"<div class=' tou select' div='divs'><input type='checkbox' name=''  class='ch' value='"+v.player_id+"' onclick='num()' id='chec'  ></div>"+
							"</div>"+
							"</li>")
				}
			
			})
		}
	})
	var thisURL = document.URL; 
    var getval =thisURL.split('?')[1]; 
    var showvals= getval.split("&")[1];
    var showval= showvals.split("=")[1];
    
    var thisURL1 = document.URL;
    var getval1 =thisURL1.split('?')[1];
    var showvals1= getval1.split("&")[0];
    var showval1= showvals1.split("=")[1];
		$.ajax({
		url:projectName+"/num",
		type:"post",
		data:{
			"game1":showval,
			"wechat":showval1
		},
		success:function(response){
			$.each(response.img,function(i,v){
				$("title").append(v.name)
				$("#image").append("<img src='"+v.backimg+"'>")
			})
		}
	})
	
  
    

})

