package com.jjld.module;

import java.util.Collections;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import com.jjld.bean.Employee;
import com.jjld.bean.Game;
import com.jjld.bean.Game_join_player;
import com.jjld.bean.Player;
import com.jjld.bean.Team;
import com.jjld.bean.Vote;

@IocBean
public class WebModeule {
	@Inject
	private Dao dao;
	@At("/weblook")
	public  List<Game_join_player> lookTest(@Param("game") int game) {
		List<Game_join_player> gjp = null;
		Game fetch = dao.fetch(Game.class, Cnd.wrap("id="+game+""));
		if(game>0) {
		 gjp = dao.query(Game_join_player.class, Cnd.wrap("game_id="+game+" and class="+fetch.getClasss()+"  ORDER BY score DESC"));
		List<Team> team = dao.query(Team.class, null);
		List<Employee> mployee = dao.query(Employee.class, null);
			for (Game_join_player g : gjp) {
				for (Team t : team) {
					for (Employee p : mployee) {
					if(g.getTeam_id()==t.getId()&&g.getPlayer_id()==p.getId()) {
						g.setTeam(t);
						g.setPlayer(p);
					}
				}
			}
		}
		}
		return gjp;
	}
	@At("/shiming")
	public NutMap shimingTest(@Param("id") int id,@Param("class") int class1,@Param("game") int game) {
		NutMap nutMap = new NutMap();
		if(id>0) {
		int identity=0;
		List<Vote> vote = dao.query(Vote.class, Cnd.wrap("player_id=(select id from player where id="+id+") and class="+class1+" and game_id="+game+" "));
		for (Vote v : vote) {
			long a=v.getUser_id();
			List<Player> player1 = dao.query(Player.class, Cnd.where("id", "=", a));
			for (Player p1 : player1) {
				if(p1.getIdentity()==1) {
					identity=5;
				}else {
					identity=1;
				}
			}
			v.setPlayer(dao.fetch(Employee.class,Cnd.wrap("id="+a+"")));
		}
		nutMap.setv("identity", identity);
		nutMap.setv("vote", vote);
		}
		return nutMap;
	}
	
	@At("/zong")
	public List<Team> zongTest(@Param("class") int class1) {
		List<Team> team = null;
		if(class1>0) {
		 team = dao.query(Team.class, Cnd.wrap("where id!=6"));
		List<Game_join_player> gjp = dao.query(Game_join_player.class, Cnd.wrap(" class="+class1+" "));
		for (Team t : team) {	
			for (Game_join_player g : gjp) {
				if(t.getId()==g.getTeam_id()){
					double func2 = (double)dao.func2(Game_join_player.class, "sum", "score",Cnd.wrap("team_id="+g.getTeam_id()+" and class="+class1+""));
					System.out.println("``````````````"+func2);
					t.setTotal(func2);
					double count = dao.count(Game_join_player.class,Cnd.wrap("team_id="+g.getTeam_id()+" and class="+class1+""));
					 double average=func2/count;
					 String format =String.format("%.2f", average);
					 double parseDouble = Double.parseDouble(format);
					t.setAverage(parseDouble);
				}
			}
		}
		Collections.sort(team);
		}
		return team;
	}
	public List<Game> imgTest(@Param("game1") int game1) {
		List<Game> game = dao.query(Game.class, Cnd.where("status","=",1).and("id", "=", game1));
		return game;
	}
	@At("/sheng")
	public List<Player> shengTest(@Param("game") int game,@Param("class") int class1) {
		List<Player> player = null;
		if(game>0&&class1>0) {
		int  num=0;
		 player = dao.query(Player.class,null);
		List<Game> game1 = dao.query(Game.class, Cnd.where("status","=",1).and("class", "=", class1));
		List<Team> Team = dao.query(Team.class, null);
		for (Game g : game1) {
			for (Player p : player) {
				for (Team t : Team) {
					if(p.getTeam_id()==t.getId()) {
						p.setTeam(t);
					}
				}
			int poll = g.getPoll();
			
			p.setEmployee(dao.fetch(Employee.class,Cnd.wrap("id="+p.getId()+"")));
			long id2 = p.getId();
			int count = dao.count(Vote.class,Cnd.wrap("user_id ="+id2+" and game_id="+game+""));			
			  num= poll-count;
			  p.setCount(num);
			} 
		}
		Collections.sort(player);
		}
		return player;
	}
}
