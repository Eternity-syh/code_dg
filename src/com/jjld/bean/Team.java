package com.jjld.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

//团队表
@Table("team")
public class Team extends BasePojo implements Comparable<Team> {

	@Id
	private long id;
	@Column
	private String name;
	@Column
	private long leader_id;
	@Column
	private String telephone;
	// 总分
	private double total;
	// 平均分
	private double average;
	@Column
	private String color;
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getLeader_id() {
		return leader_id;
	}

	public void setLeader_id(long leader_id) {
		this.leader_id = leader_id;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getAverage() {
		return average;
	}

	public void setAverage(double average) {
		this.average = average;
	}

	@Override
	public int compareTo(Team o) {
		// TODO Auto-generated method stub
		if (this.getAverage() > o.getAverage()) {
			return -1;
		} else if (this.getAverage() == o.getAverage()) {
			return 0;
		} else {
			return 1;
		}
	}

}
