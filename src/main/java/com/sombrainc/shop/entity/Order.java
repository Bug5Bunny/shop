package org.chocolate.shop.entity;

import java.sql.Timestamp;

public class Order {

	private String uid;
	private String userId;
	private Timestamp orderTime;

	public Order() {
		super();
	}

	public Order(final String uid, final String userId, final Timestamp orderTime) {
		super();
		this.uid = uid;
		this.userId = userId;
		this.orderTime = orderTime;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(final String uid) {
		this.uid = uid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(final String userId) {
		this.userId = userId;
	}

	public Timestamp getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(final Timestamp orderTime) {
		this.orderTime = orderTime;
	}

	@Override
	public String toString() {
		return "Order [uid=" + uid + ", userId=" + userId + ", orderTime=" + orderTime + "]";
	}

}
