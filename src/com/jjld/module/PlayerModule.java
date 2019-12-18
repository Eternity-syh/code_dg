package com.jjld.module;
import java.util.Date;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.jjld.bean.Employee;
import com.jjld.bean.Game;
import com.jjld.bean.Game_join_player;
import com.jjld.bean.Player;
import com.jjld.bean.Team;

@IocBean()
@At("/user")
public class PlayerModule {
	@Inject
	Dao dao;
	//添加
	 @At("/addperson")
	 @Ok("json:full")
	 public Object addPerson() {
	  NutMap map = new NutMap();
	  Cnd cnd = Cnd.where("identity", "=", 0);
	  List<Player> players = dao.query(Player.class, cnd);
	  for (Player player : players) {
	   player.setEmployee(dao.fetch(Employee.class, player.getId()));
	   player.setTeam( dao.fetch(Team.class,player.getTeam_id()));
	  }
	  map.setv("data", players);
	  map.setv("ok", true);
	  return map;
	 }
	 
	 
	 //查询
	 @At("/selectperson")
	 @Ok("json:full")
	  public Object selectPerson(@Param("game_id") int game_id) {
	  System.out.println("game_id::"+game_id);
	  NutMap rt = new NutMap();
	  rt.setv("ok", false);
	  if(game_id!=0) {
	   List<Game_join_player> gjp = dao.query(Game_join_player.class, Cnd.where("game_id","=",game_id));
	   if(gjp !=null && gjp.size()>0) {
	    for(Game_join_player g : gjp) {
	     Employee emp = dao.fetch(Employee.class,Cnd.where("id","=",g.getPlayer_id()));
	     Team team = dao.fetch(Team.class,Cnd.where("id","=",g.getTeam_id()));
	     if(emp!=null && team!=null) {
	      g.setEmployee(emp);
	      g.setTeam(team);
	     }
	    }
	    rt.setv("ok", true);
	    rt.setv("player",gjp);
	   }
	  }
	    return rt;
	  }
//删除
	@At("/delete")
	public Object deletePerson(@Param("id")int id) {
		NutMap map=new NutMap();
		if(id!=0) {
			dao.delete(Game.class,id);
			map.setv("ok", true);
		}
		return map;
	}
	//删除
		@At("/delete1")
		public Object delete1Person(@Param("id")int id) {
			NutMap map=new NutMap();
			if(id!=0) {
				Game_join_player fetch = dao.fetch(Game_join_player.class,Cnd.where("player_id","=",id));
				dao.delete(Game_join_player.class,fetch.getId());
				map.setv("ok", true);
			}
			return map;
		}
	
	
	
	
	 @At("/addPlayersByGameId")
	 @Ok("json:full")
	 @Fail("http:500")
	 public Object addPlayersByGameId(@Param("game_id") long game_id,@Param("player_ids") long[] player_ids) {
	  NutMap nutMap = new NutMap();
	  Player p=new Player();
	  Game_join_player gameJoinPlayer=new Game_join_player();
	  gameJoinPlayer.setGame_id(game_id);
	  for(int i=0;i<player_ids.length;i++) {
		  Cnd cnd = Cnd.where("game_id", "=", game_id).and("player_id", "=", player_ids[i]);
		  Game_join_player game_join_player = dao.fetch(Game_join_player.class, cnd);
		  if (game_join_player!=null) {
			game_join_player.setUpdateTime(new Date());
			dao.update(game_join_player);
		}else {
			Game fetch = dao.fetch(Game.class,Cnd.wrap("id="+game_id+""));
			 gameJoinPlayer.setPlayer_id(player_ids[i]);
			 p=dao.fetch(Player.class,Cnd.where("id","=",player_ids[i]));
			 gameJoinPlayer.setTeam_id(p.getTeam_id());
			 gameJoinPlayer.setCreateTime(new Date());
			 gameJoinPlayer.setClass1(fetch.getClasss());
			 gameJoinPlayer.setUpdateTime(new Date());
			 dao.insert(gameJoinPlayer);
		}
	   
	  }
	  
	  return nutMap;
	 }
//	@At("/selectperson")
//	 public Object selectPerson() {
//		 NutMap map=new NutMap();
//	   List<Player> player = dao.query(Player.class, null);
//	   List<Employee> employee = dao.query(Employee.class, null);
//	   List<Team> team = dao.query(Team.class, null);
//	   for (Player p : player) {
//	    for (Employee e : employee) {
//	     for (Team t : team) {
//	      if(p.getId()==e.getId()&&p.getTeam_id()==t.getId()) {
//	       p.setEmployee(e); 
//	       p.setTeam(t);
//	      }
//	   }
//	   }
//	   }
//	   if(player!=null) {
//		   map.setv("code", player);
//	   }else {
//		   map.setv("code", -1);
//	   }
//	   return map;
//	 }
}
