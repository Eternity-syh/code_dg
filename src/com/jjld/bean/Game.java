package com.jjld.bean;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

//赛事表，设置活动名及上方背景图信息
@Table("game")
public class Game extends BasePojo {
	@Id
	private long id;
	@Column
	private String name;
	@Column
	private String backimg;
	@Column
	private int poll;
	@Column
	private int status;
	@Column("class")
	private int classs;
	@Column
    private Date starTime;
	

	public Date getStarTime() {
		return starTime;
	}

	public void setStarTime(Date starTime) {
		this.starTime = starTime;
	}

	public int getClasss() {
		return classs;
	}

	public void setClasss(int classs) {
		this.classs = classs;
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

	public String getBackimg() {
		return backimg;
	}

	public void setBackimg(String backimg) {
		this.backimg = backimg;
	}

	public int getPoll() {
		return poll;
	}

	public void setPoll(int poll) {
		this.poll = poll;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
