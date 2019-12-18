package com.jjld.module;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.TempFile;
import org.nutz.mvc.upload.UploadAdaptor;

import com.jjld.bean.Game;


@IocBean
public class GameModuleo {
	// @Ok("jetx:humanResIndex.html")
	@Inject
	Dao dao;

	@At("/count")
	public Object count() {
		return dao.count(Game.class);
	}

	// 查询
	@At("/select")
	public List<Game> selectTest() {
		List<Game> query = dao.query(Game.class, null);
		return query;
	}

	// 添加
	@SuppressWarnings("null")
	@At("/addGameView")
	@Ok("json:full")
	@Fail("http:500")
	public Object addGameView(@Param("name") String name, @Param("poll") int poll,@Param("backimg") String backimg,@Param("starTime") Date starTime) throws Exception {
		String substring = name.substring(name.length()-1);
		Integer decode = Integer.parseInt(substring);
		String substring1 = name.substring(0,name.length()-1);
		NutMap nutMap = new NutMap();
		if(name!=null||name.equals("")&&poll!=0) {
			Game game = new Game();
			game.setName(substring1);
			game.setPoll(poll);
			game.setBackimg(backimg);
			game.setClasss(decode);
			game.setStatus(1);
			game.setStarTime(starTime);
		    Game game2=dao.insert(game);
		    nutMap.setv("game", game2);
		}
		return nutMap;
	}
	 @At("/index")
	@AdaptBy(type = UploadAdaptor.class, args = { "${app.root}/WEB-INF/tmp/user_avatar", "8192", "utf-8", "20000",
	   "10240000" }) // 临时文件夹路径,缓存环的大小,编码,临时文件夹的文件数,上传文件的最大大小
	 public Object index(@Param("file") TempFile tempFile) throws IOException, CloneNotSupportedException {
		
		NutMap nutMap = new NutMap();
	  nutMap.setv("ok", false);
	  if (tempFile != null) {
		  AlbbModule.albbOSS(tempFile.getFile(),"newImage/"+tempFile.getSubmittedFileName());
	   nutMap.setv("ok", true);
	  }
	  String url = "https://ghwgyx.oss-cn-beijing.aliyuncs.com/newImage/"+tempFile.getSubmittedFileName();
	 
	  nutMap.setv("url", url);
	  return nutMap;

	 }
	// 修改
	@At("/updateGameView")
	@Ok("json:full")
	@Fail("http:500")
	public Object updateGameView(@Param("id") String id, @Param("name") String name, @Param("backing") String backing,
			@Param("votes") int votes, @Param("status") int status) {
		NutMap map = new NutMap();
		int count = dao.update(Game.class,
				Chain.make("name", name).add("backing", backing).add("votes", votes).add("status", status),
				Cnd.where("id", "=", id));
		if (count == 1) {
			map.setv("code", 200);
		} else {
			map.setv("失败", "shibai");
		}
		return map;
	}

	// 删除
	@At("/deleteGameView")
	@Ok("json:full")
	@Fail("http:500")
	public Object updateGameView(@Param("id") String id) {
		NutMap map = new NutMap();
		int count = dao.clear(Game.class, Cnd.where("id", "=", id));
		if (count == 1) {
			map.setv("msg", "删除成功！");
			map.setv("code", 200);
		} else {
			map.setv("msg", "删除失败！");
			map.setv("code", 500);
		}
		return map;
	}
}