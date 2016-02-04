package org.chocolate.shop.dao;

import org.chocolate.shop.entity.Order;

public interface OrderDAO extends BaseDAO<Order> {

	public void insertValuesInOrderProduct(String order_id, String product_id);

	public void deleteFromOrderProduct(String product_id);
}
