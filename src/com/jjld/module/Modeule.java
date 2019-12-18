package com.jjld.module;
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
public class Modeule {
	@Inject
	private Dao dao;
	//显示手机端参赛人员信息
	@At("/look")
	public  NutMap lookTest(@Param("game") int game,@Param("wechat") String wechat) {
		NutMap nutMap = new NutMap();
		if(game>0&&wechat!=null||!wechat.equals("")) {
		Employee fetch = dao.fetch(Employee.class,Cnd.wrap("wechat_id=\""+wechat+"\""));
		List<Game_join_player> gjp = dao.query(Game_join_player.class, Cnd.wrap("game_id="+game+" ORDER BY score DESC"));
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
			nutMap.setv("gjp", gjp);
			nutMap.setv("fetch", fetch);
		}
		return nutMap;
		
	}
	//显示标题  img
	@At("/img")
	public List<Game> imgTest(@Param("game1") int game1) {
		List<Game> game = dao.query(Game.class, Cnd.where("status","=",1).and("id", "=", game1));
		return game;
	}
	//显示 谁的 剩余票数  判读是否是执委会
	@SuppressWarnings("null")
	@At("/num")
	public NutMap numTest(String wechat,@Param("game1") int id1) {
		NutMap nutMap = new NutMap();
		if(wechat!=null||!wechat.equals("")&&id1>0) {
		int num1=0;
		String mag=null;
		List<Player> player = dao.query(Player.class, Cnd.wrap("id=(select  id from employee_table where wechat_id=\""+wechat+"\")"));
		Employee fetch = dao.fetch(Employee.class, Cnd.wrap("wechat_id=\""+wechat+"\""));
		for (Player player2 : player) {
		int identity = player2.getIdentity();
		List<Game> game = imgTest(id1);
		for (Game g : game) {
				int poll = g.getPoll();
				int count = dao.count(Vote.class, Cnd.where("game_id","=",id1).and("user_id","=", fetch.getId()));
				if(count==10) {
					 mag="你剩余票数以为0！";
				}else {
					num1=poll-count;
				}
		}
		nutMap.setv("num1", num1);
		nutMap.setv("identity", identity);
		nutMap.setv("mag", mag);
		nutMap.setv("img", game);
		}
		}
		return nutMap;
	}
	//加分方法
	@At("/jiafen")
	public NutMap jfTest(@Param("id") int[] id,@Param("zhi") int zhi,@Param("game1") int game1 ,@Param("wechat") String wechat1) {
		NutMap nutMap = new NutMap();
		if(id!=null&&zhi>0&&game1>0&&wechat1!=null||!wechat1.equals("")) {
		String msg=null;
		int count2 = 0 ;
		//id
		Employee fetch = dao.fetch(Employee.class, Cnd.wrap("wechat_id=\""+wechat1+"\""));	
		Game fetch2 = dao.fetch(Game.class,Cnd.wrap("id="+game1+""));
		for(int i1 :id) { 		
					List<Game_join_player> gp = dao.query(Game_join_player.class, Cnd.wrap("player_id="+i1+"  and game_id="+game1+" and class="+fetch2.getClasss()+""));
				for (Game_join_player g1 : gp) {
					long player_id2 = g1.getPlayer_id();
					List<Player> query = dao.query(Player.class, Cnd.wrap("id="+player_id2+""));	
					int count = dao.count(Vote.class, Cnd.wrap("user_id="+fetch.getId()+" and player_id="+i1+" and game_id="+game1+" and class="+fetch2.getClasss()+""));
					int count3 = dao.count(Vote.class,Cnd.wrap("user_id="+fetch.getId()+" and game_id="+game1+" and class="+fetch2.getClasss()+""));	
					for (Player p : query) {
					if(fetch.getId()==p.getPid()){
						 msg="请勿给本您的战队投票";
					}else if(fetch.getId()==i1){
						msg="请勿给自己投票";
					}else if(count==1){
						msg="请勿重复投票";
					}else if(count3<=fetch2.getPoll()){
						Vote vote = new Vote();
						vote.setUser_id(fetch.getId());
						vote.setPlayer_id(i1);
						vote.setGame_id(game1);
						vote.setClasss(fetch2.getClasss());
						dao.insert(vote);
						double score = g1.getScore();
						if(zhi==1) {//执委会
							g1.setScore(score+5);
							dao.updateIgnoreNull(gp);
						}else {//普通员工
							g1.setScore(score+1);
							dao.updateIgnoreNull(gp);
					}
				}
				}
				
		 nutMap.setv("count2", count2);
		 nutMap.setv("msg", msg);
				}
				
		}
		}
			return nutMap;
	
	}
	/*@At("/ban")
	public int banTest(@Param("user_id") int user_id,@Param("player_id") int player_id) {
		int count = dao.count(Vote.class,Cnd.wrap(" player_id=1 and user_id=1"));
		return count;
	}*/
}
