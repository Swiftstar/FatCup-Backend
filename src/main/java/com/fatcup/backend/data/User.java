package com.fatcup.backend.data;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {

	@Id
	private String uid;
	
	@Column
	private String logintype;
	
	@Column
	private String name;
	
	@Column
	private String phone;
	
	@Column
	private String birthday;
	
	@Column
	private String gender;
	
	@Column
	private LocalDateTime createTime;
	
	public String getLogintype() {
		return logintype;
	}
	public void setLogintype(String logintype) {
		this.logintype = logintype;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getUid() {
		return this.uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public LocalDateTime getCreateTime() {
		return this.createTime;
	}
	public void setCreateTime(LocalDateTime time) {
		this.createTime = time;
	}
}
