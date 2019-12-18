package com.jjld.module;
import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
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
public class GameModeule {
	@Inject
	private Dao dao;
	@At("/vote")
	public List<Vote> voteTest() {
		List<Vote> vote = dao.query(Vote.class, null);
		List<Game> game = dao.query(Game.class, null);
		for (Game g : game) {
		  for (Vote v : vote) {
			if(v.getGame_id()==g.getId()) {
				v.setGame(g);
			}
		 }
		}
		return vote;
	}
	@SuppressWarnings("null")
	@At("/vote2")
	public NutMap voTest(@Param("wechat") String wechat ,@Param("game1") int game1) {
		NutMap nutMap = new NutMap();
		if(wechat!=null||!wechat.equals("")&&game1>0) {
		List<Game> query = dao.query(Game.class,Cnd.wrap("id="+game1+""));
		Sql sql = Sqls.create("select * from vote where user_id=(select id from player where id=(SELECT id from employee_table where wechat_id=\""+wechat+"\" )) and game_id="+game1+"");
		 sql.setCallback(Sqls.callback.entities());
		 sql.setEntity(dao.getEntity(Vote.class));
		 dao.execute(sql);
		 List<Vote> vote = sql.getList(Vote.class);
		 	ArrayList<Object> list= new ArrayList<>();
		 	for (Vote vote2 : vote) {
		 		long player_id2 = vote2.getPlayer_id();
		 		list.add(player_id2);
		 	}
		 	String sql1="id in(";
		 	String sql2="player_id in(";
		 	for (Object l : list) {
		 		 sql1+=l+",";
		 		 sql2+=l+",";
			}
		 	List<Game_join_player> gp = null ;
		 	String substring = sql1.substring(0, sql1.length()-1)+")";
		 	String substring1 = sql2.substring(0, sql2.length()-1)+")";
		 		List<Player>  player = dao.query(Player.class, Cnd.wrap(substring));
		 			List<Employee> employee = dao.query(Employee.class, null);
		 			List<Team> team = dao.query(Team.class, null);
		 			for (Player p : player){				
		 				 gp = dao.query(Game_join_player.class, Cnd.wrap(substring1+"and game_id="+game1+""));
		 			for (Game_join_player g : gp) {
		 				for (Employee e : employee){
							for (Team t : team) {
								if(g.getPlayer_id()==e.getId()&&g.getTeam_id()==t.getId()) {
									g.setPlayer(e);
									g.setTeam(t);
								}
							}
						}
					}
				}
		 		nutMap.setv("query", query);
		 		nutMap.setv("gp", gp);
		}
		 return nutMap;	 
	}
	
}
