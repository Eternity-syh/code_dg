package com.jjld.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;



@Table("game_join_player")
public class Game_join_player extends BasePojo {

	@Id
	private long id;
	@Column
	private long game_id;
	@Column
	private long player_id;
	@Column
	private long team_id;
	@Column
	private double score; // 当前参赛人员的本场比赛分数，在每次投票时，更新此值
	@Column("class")
	private long class1;
	private Employee player;// 参赛人详细信息
	private Team team;
	private Employee employee;



	public long getClass1() {
		return class1;
	}

	public void setClass1(long class1) {
		this.class1 = class1;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getGame_id() {
		return game_id;
	}

	public void setGame_id(long game_id) {
		this.game_id = game_id;
	}

	public long getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(long player_id) {
		this.player_id = player_id;
	}

	public long getTeam_id() {
		return team_id;
	}

	public void setTeam_id(long team_id) {
		this.team_id = team_id;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public Employee getPlayer() {
		return player;
	}

	public void setPlayer(Employee player) {
		this.player = player;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

}
