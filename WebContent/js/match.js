var pathName=window.document.location.pathname; 
 var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
var id_id = [];
layui.use('table', function(){
var table = layui.table;
table.render({
  elem: '#test'
  ,url: projectName+'/select'
    ,defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
      title: '提示'
      ,layEvent: 'LAYTABLE_TIPS'
      ,icon: 'layui-icon-tips'
    }]
    ,title: '用户数据表'
    ,parseData:function(res){
    	$("#inp").val(res[0].id)
    	     return{
    	      "code": 0,
    	      "msg": "",
    	      "count":"1000",
    	      "data": res
    	    }  
    	    }
    ,cols: [[
      {type: 'checkbox', fixed: 'left', width:120}
      ,{field:'id', title:'赛事ID', width:120, fixed: 'left'}
      ,{field:'name', title:'赛事名称', width:120}
      ,{field:'backimg', src:'img' ,title:'赛事规则图片', width:120,event:'image',templet:function(res){
          return '<img width="120" height="50" src='+ res.backimg + '>'
      }}
      ,{field:'poll', title:'个人票数', width:120}
      ,{field:'starTime', title:'赛事时间', width:120,sort: true}
      ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:280}
    ]]
  });
  //头工具栏事件
  table.on('toolbar(test)', function(obj){
	 
    var checkStatus = table.checkStatus(obj.config.id);
    switch(obj.event){
      case 'getCheckData':
        var data = checkStatus.data;
        layer.alert(JSON.stringify(data));
      break;
      case 'getCheckLength':
        var data = checkStatus.data;
        layer.msg('选中了：'+ data.length + ' 个');
      break;
      case 'isAll':
        layer.msg(checkStatus.isAll ? '全选': '未全选');
      break;
      //自定义头工具栏右侧图标 - 提示
      case 'LAYTABLE_TIPS':
        layer.alert('这是工具栏右侧自定义的一个图标按钮');
      break;
    };
  });

  //监听行工具事件
  table.on('tool(test)', function(obj){
    var data = obj.data;
   
    // 删除触发的事件
    if(obj.event === 'del'){
        $.ajax({
            url : projectName+"/user/delete",
            data : {"id":data.id},
            dataType : "json",
            success : function (data) {
                if (data.ok) {
                	window.location.reload();   
                } else {
                    alert(data.msg);
                }
            }
        });  
      // 大屏出现图片
    }else if(obj.event === 'image'){
      layer.open({
        title:'图片',
        area:['400px','400px'],
        content : '<img width="100%" height="200px" src='+ data.backimg + '>'
      })
      // 赛事名称大图
    }else if(obj.event === 'image1'){
      layer.open({
        title:'图片',
        area:['400px','400px'],
        content : '<img width="100%" height="200px" src=https://ghwgyx.oss-cn-beijing.aliyuncs.com/newImage/'+ obj.backimg + '>'
      })
      // 添加赛事人员
    }else if(obj.event === 'add'){
    	var game_id=data.id;
    	
      layer.open({
        content:"<div class = 'test'></div>"
        ,area: ['550px',"400px"]
        ,btn: ['确定', '取消']
        // 请求数据库中的参赛人员
        ,success:function(){
        	var txt = "";
            $.ajax({
              url:projectName+"/user/addperson",
              data:"json",
              success:function(data){
                var list = data.data;
                //console.log(list);
                var dataNum = list.length;
               //console.log(dataNum);
                txt += "<form class='layui-form'><table class='layui-table'><tr><td>用户ID</td><td>用户名</td><td>战队</td><td>参赛</td></tr>";   
                for(var i = 0;i< dataNum;i++){	
                 // console.log(i);
                  txt += "<tr>";
                  txt += "<td>"+ list[i].id+ "</td>";
                  //console.log(list[i].employee.name);
                  //console.log(list[i].team.name);
                  txt += "<td>"+list[i].employee.name+ "</td>";
                  txt += "<td>"+list[i].team.name+ "</td>";
                  console.log(list[i].status);
                  // 传递值  如果为true则继续确定这个已经是本场赛事
                  if(list[i].status==1){
                    txt += "<td>"+"<div class='layui-form'><input type='checkbox'  in_dex='"+i+"' lay-skin='switch'  lay-filter='cz' lay-text='提交|取消' value='"+list[i].id+"'>"+"</div></td>";
                  }else{
                    txt += "<td>"+"<div class='layui-form'><input type='checkbox' in_dex='"+i+"' lay-skin='switch' lay-filter='cz' lay-text='提交|取消' value='"+list[i].id+"'>"+"</div></td>";
                  }
                  txt += "</tr>";
                }
                txt += "</table></form>";
              $(".test").append(txt);
              // 传递的开关值
              layui.use('form', function(){
                var form = layui.form;
                form.on('switch(cz)', function(data,index){
                	//console.log(data.value);
                  if(data.elem.checked ==1){
                    console.log(data.elem.checked); //开关是否开启，true或者false
                    id_id.push(data.value);
                  }else{
                    id_id.splice(data.elem.attributes['in_dex'].nodeValue,1);
                    console.log(data.elem.checked); //开关是否开启，true或者false
                  }
                   //得到美化后的DOM对象
                });
                form.render();
              });
            },  
            error:function(){
              //alert("请求失败")
            }
          })
        }
        // 点击确定触发的js  点击确定触发的
      ,yes: function(index,layero){
          if(id_id != '' && id_id){  
            var player_ids = id_id;
          //  console.log(player_ids)
              $.ajax({
                  url:projectName+'/user/addPlayersByGameId',
                  data:{
                	  game_id:game_id,
                	  player_ids:player_ids
                	  },
                  traditional:true,
                  success:function(rs){
                	  player_ids.splice(0,player_ids.length);
                      layer.close(index); // 关闭弹窗
//                    if(rs.ok){
//                     layer.msg(rs.msg,{icon: 6,time:1000});
//                    }else{
//                     layer.msg(rs.msg,{icon: 2,time:1000});
//                    }
                  }
             });
          }
        }
      })
      //查询
    }else if(obj.event === 'edit'){
      layer.open({
    	 content: "<div class='compe'></div>",
    	 area: ['550px',"400px"]
        ,btn: ['确定', '取消']
        ,success:function(){
          $.ajax({
            url:projectName+"/user/selectperson",
            type:"post",
        	data:{
        		game_id:data.id
        	},
            success:function(data){         
            	if(data.ok){
            		var player = data.player;           		
            		var compeTxt = "";
                    compeTxt += "<table class='layui-table'><tr><td>用户ID</td><td>用户名</td><td>战队</td><td>操作</td></tr>";
                    for(var i=0;i<player.length;i++ ){
                       compeTxt += "<tr>";
                       compeTxt += "<td>"+player[i].employee.id+"</td>";
                       compeTxt += "<td>"+player[i].employee.name+"</td>";
                       compeTxt += "<td>"+player[i].team.name+ "</td>";
                       compeTxt += "<td><button class='layui-btn layui-btn-xs layui-btn-danger' value="+player[i].employee.id+" onclick='delete1(this)' >删除</button></td>";
                     // compeTxt += "<td>"+player[i].player.status+ "</td>";
                       compeTxt += "</tr>";
                   }
                     compeTxt += "</table>";
                     $(".compe")[0].innerHTML = compeTxt;
            	}
              }
          })
        }
        ,yes: function(index, layero){
        }
        ,btn2: function(index, layero){
          //按钮【按钮二】的回调
          //return false 开启该代码可禁止点击该按钮关闭
        }
      })
    }else if(obj.event === 'huo') {
    	
    	   layer.open({
    	        title:'地址',
    	        area:['400px','300px'],
    	        content : '手机端：http://192.168.88.243:8080/code_dg/demo.html?wechat=BWSNWHMDAV&game1='+data.id+'<br/> web端：http://192.168.88.243:8080/code_dg/result.html?game1='+data.id+'&class='+data.classs+' <br/> web端总排行：http://192.168.88.243:8080/code_dg/show.html?class='+data.classs+''
    	      })
    	
    }
  });
});
function delete1(ob){
	
	$.ajax({
		url:projectName+"/user/delete1",
		type:"post",
		data:{
			"id":$(ob).val()
		},
		 success:function(data){  
			 layer.msg('删除');
			   
		 }
	})
}

