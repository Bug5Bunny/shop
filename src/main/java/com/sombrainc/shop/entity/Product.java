package org.chocolate.shop.entity;

import java.math.BigDecimal;

public class Product {

	private String uid;
	private String name;
	private String description;
	private BigDecimal price;
	private String photo;

	public Product() {
		super();
	}

	public Product(final String uid, final String name, final String description, final BigDecimal money, final String photo) {
		super();
		this.uid = uid;
		this.name = name;
		this.description = description;
		this.price = money;
		this.photo = photo;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(final String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(final BigDecimal price) {
		this.price = price;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(final String photo) {
		this.photo = photo;
	}

	@Override
	public String toString() {
		return "Product [uid=" + uid + ", name=" + name + ", description=" + description + ", money=" + price + ", photo=" + photo + "]";
	}

}
