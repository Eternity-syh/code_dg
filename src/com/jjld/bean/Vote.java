package com.jjld.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

//投票详情表，记录投票信息
@Table("vote")
public class Vote extends BasePojo implements Comparable<Vote> {

	@Id
	private long id;
	@Column
	private long user_id; // 投票人
	@Column
	private long player_id; // 参赛者
	@Column
	private long game_id;
	@Column
	private double score;
	@Column("class")
	private long classs;
	// 投票者
	private Employee user;
	// 被投票者,即参加本场比赛的人
	private Employee player;
	private Game game;
	

	public long getClasss() {
		return classs;
	}

	public void setClasss(long classs) {
		this.classs = classs;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public long getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(long player_id) {
		this.player_id = player_id;
	}

	public long getGame_id() {
		return game_id;
	}

	public void setGame_id(long game_id) {
		this.game_id = game_id;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public Employee getUser() {
		return user;
	}

	public void setUser(Employee user) {
		this.user = user;
	}

	public Employee getPlayer() {
		return player;
	}

	public void setPlayer(Employee player) {
		this.player = player;
	}

	@Override
	public int compareTo(Vote o) {
		// TODO Auto-generated method stub
		if (this.getScore() > o.getScore()) {
			return -1;
		} else if (this.getScore() == o.getScore()) {
			return 0;
		} else {
			return 1;
		}
	}

}
