package com.jjld.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * 
 * @author guzhanfang
 * @Email guzhanfangyi@163.com
 * @introduce 雇员表，保持原系统表格结构不变
 */
@Table("employee_table")
public class Employee extends BasePojo {
	@Id
	private long id;

	@Column("department_id")
	private long department_id;
	@Column("name")
	private String name;
	@Column
	private String sex;
	@Column
	private String education;

	@Column
	private String age;

	@Column
	private String phone;

	@Column
	private String wechat_id;

	@Column
	private String open_id;

	@Column
	private String email;

	@Column
	private String identity_card;

	@Column
	private String station;

	@Column
	private String job_num;

	@Column
	private String hiredate;

	@Column
	private String correction_time;

	@Column
	private String departure_time;

	@Column
	private int employee_state;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getDepartment_id() {
		return department_id;
	}

	public void setDepartment_id(long department_id) {
		this.department_id = department_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWechat_id() {
		return wechat_id;
	}

	public void setWechat_id(String wechat_id) {
		this.wechat_id = wechat_id;
	}

	public String getOpen_id() {
		return open_id;
	}

	public void setOpen_id(String open_id) {
		this.open_id = open_id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdentity_card() {
		return identity_card;
	}

	public void setIdentity_card(String identity_card) {
		this.identity_card = identity_card;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getJob_num() {
		return job_num;
	}

	public void setJob_num(String job_num) {
		this.job_num = job_num;
	}

	public String getHiredate() {
		return hiredate;
	}

	public void setHiredate(String hiredate) {
		this.hiredate = hiredate;
	}

	public String getCorrection_time() {
		return correction_time;
	}

	public void setCorrection_time(String correction_time) {
		this.correction_time = correction_time;
	}

	public String getDeparture_time() {
		return departure_time;
	}

	public void setDeparture_time(String departure_time) {
		this.departure_time = departure_time;
	}

	public int getEmployee_state() {
		return employee_state;
	}

	public void setEmployee_state(int employee_state) {
		this.employee_state = employee_state;
	}

}
