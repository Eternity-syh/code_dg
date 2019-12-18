package com.jjld.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * 
 * @author guzhanfang
 * @Email guzhanfangyi@163.com
 * @introduce 参赛人员信息表，保存所有参赛人员你的每场比赛得分情况，剩余票数
 */
@Table("player")
public class Player extends BasePojo implements Comparable<Player> {

	@Id(auto = false)
	private long id;
	@Column
	private long pid;
	@Column
	private long team_id;
	@Column
	private int identity;
	@Column
	private int status; // 是否参赛，1表示参赛
	private double score; // 获取的分数
	private Employee employee;
	private Team team;
	private int count;
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPid() {
		return pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

	public long getTeam_id() {
		return team_id;
	}

	public void setTeam_id(long team_id) {
		this.team_id = team_id;
	}

	public int getIdentity() {
		return identity;
	}

	public void setIdentity(int identity) {
		this.identity = identity;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
	public int compareTo(Player p) {
		if(this.getCount()>p.getCount()) {
			return -1;
		}else if(this.getCount()==p.getCount()){
			return 0;
		}else {
			return 1;
		}
		
	}

}
