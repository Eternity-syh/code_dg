package com.jjld.module;
import java.util.Collections;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;

import com.jjld.bean.Employee;
import com.jjld.bean.Game_join_player;
import com.jjld.bean.Team;
@IocBean
public class Modeulez {
	@Inject
	private Dao dao;
	@At("/mzong")
	public List<Game_join_player> mzongtest (@Param("class") int classs) {
     List<Game_join_player> query = dao.query(Game_join_player.class, Cnd.where("class","=",classs).orderBy("score", "DESC"));
     for (Game_join_player g : query) {
    	 long player_id = g.getPlayer_id();
    	 long team_id = g.getTeam_id();
    	 	g.setEmployee(dao.fetch(Employee.class,Cnd.wrap("id="+player_id+"")));
    	 	g.setTeam(dao.fetch(Team.class,Cnd.wrap("id="+team_id+"")));
	}
     return query;
	}
	@At("/mzong1")
	public List<Team> zongTest(@Param("class") int class1) {
		List<Team> team = null;
		if(class1>0) {
		 team = dao.query(Team.class, Cnd.wrap("where id!=6"));
		List<Game_join_player> gjp = dao.query(Game_join_player.class,null);
		for (Team t : team) {	
			for (Game_join_player g : gjp) {
				if(t.getId()==g.getTeam_id()){
					 double func2 = (double)dao.func2(Game_join_player.class, "sum", "score",Cnd.wrap("team_id="+g.getTeam_id()+" and class="+class1+""));
				     double count = dao.count(Game_join_player.class,Cnd.wrap("team_id="+g.getTeam_id()+" and class="+class1+""));
					 double average=func2/count;
					 int fu = dao.count(Game_join_player.class,Cnd.wrap(" class=2 AND team_id="+g.getTeam_id()+""));	
					 int ban = dao.count(Game_join_player.class,Cnd.wrap(" class=3 AND team_id="+g.getTeam_id()+""));				
					 int jue = dao.count(Game_join_player.class,Cnd.wrap(" class=4 AND team_id="+g.getTeam_id()+""));					
					 double he =(average+fu*5+ban*10+jue*20)*0.6+0*0.4;
					 String format =String.format("%.2f", average);
					 double parseDouble = Double.parseDouble(format);
					 String format2 = String.format("%.2f", he);
					 double parseDouble2 = Double.parseDouble(format2);
					 t.setAverage(parseDouble);
					 t.setTotal(parseDouble2);
				}
			}
		}
		Collections.sort(team);
		}
		return team;
	}
}
