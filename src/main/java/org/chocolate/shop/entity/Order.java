package org.chocolate.shop.entity;

import java.sql.Timestamp;

public class Order {

	private String uid;
	private String userId;
	private Timestamp orderTime;

	public Order(String uid, String userId, Timestamp orderTime) {
		super();
		this.uid = uid;
		this.userId = userId;
		this.orderTime = orderTime;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Timestamp getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Timestamp orderTime) {
		this.orderTime = orderTime;
	}

	@Override
	public String toString() {
		return "Order [uid=" + uid + ", userId=" + userId + ", orderTime=" + orderTime + "]";
	}

}
