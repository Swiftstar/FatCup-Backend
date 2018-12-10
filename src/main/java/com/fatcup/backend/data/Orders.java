package com.fatcup.backend.data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Orders {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	@OneToOne
	@JoinColumn(name="customerId")
	Customer customer;
	
	@OneToMany
	@JoinColumn(name="ordersId")
	Set<OrderDetail> details = new HashSet<OrderDetail>();
	
	@Enumerated(EnumType.ORDINAL)
	OrdersStatus status;

	@Column
	LocalDateTime orderDateTime;
	
	@Column
	double customerLat;
	
	@Column
	double customerLong;
	
	@Column
	double teamLat;
	
	@Column
	double teamLong;
	
	@Column
	String remark;
	
	@OneToOne
	@JoinColumn(name="teamId")
	Team team;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public OrdersStatus getStatus() {
		return status;
	}

	public void setStatus(OrdersStatus status) {
		this.status = status;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Set<OrderDetail> getDetails() {
		return details;
	}

	public void setDetails(Set<OrderDetail> details) {
		this.details = details;
	}
	
	public double getCustomerLat() {
		return customerLat;
	}

	public void setCustomerLat(double latitude) {
		this.customerLat = latitude;
	}

	public double getCustomerLong() {
		return customerLong;
	}

	public double getTeamLat() {
		return teamLat;
	}

	public void setTeamLat(double teamLat) {
		this.teamLat = teamLat;
	}

	public double getTeamLong() {
		return teamLong;
	}

	public void setTeamLong(double teamLong) {
		this.teamLong = teamLong;
	}

	public void setCustomerLong(double longitude) {
		this.customerLong = longitude;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public LocalDateTime getOrderDateTime() {
		return orderDateTime;
	}

	public void setOrderDateTime(LocalDateTime orderDateTime) {
		this.orderDateTime = orderDateTime;
	}
}
