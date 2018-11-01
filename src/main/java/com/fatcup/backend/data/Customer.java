package com.fatcup.backend.data;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="customer")
public class Customer {

	@Id
	private String uid;
	
	@Column
	private String logintype;
	
	@Column
	private String name;
	
	@Column
	private String phone;
	
	@Column
	private LocalDate birthday;
	
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition="CHAR(1)")
	private Gender gender;
	
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
	public LocalDate getBirthday() {
		return birthday;
	}
	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
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
	
	public enum Gender {
		M, F
	}
}
