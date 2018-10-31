package com.fatcup.backend.data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;

@Entity
public class Orders {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	@OneToOne
	User user;
	
	@OneToMany
	@JoinColumn(name="items")
	Set<OrderDetail> details = new HashSet<OrderDetail>();
	
	public Set<OrderDetail> getDetails() {
		return details;
	}

	public void setDetails(Set<OrderDetail> details) {
		this.details = details;
	}
	
//	@ManyToMany
//	@JoinTable(name="orders_drink",
//	joinColumns= {@JoinColumn(name="orders_fk")},
//	inverseJoinColumns= {@JoinColumn(name="drinks_fk")})
//	Set<Drink> drinks;
//
//	public Set<Drink> getDrinks() {
//		return drinks;
//	}
//
//	public void setDrinks(Set<Drink> drinks) {
//		this.drinks = drinks;
//	}

	@Column
	LocalDateTime orderDateTime;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDateTime getOrderDateTime() {
		return orderDateTime;
	}

	public void setOrderDateTime(LocalDateTime orderDateTime) {
		this.orderDateTime = orderDateTime;
	}
}
