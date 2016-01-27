package org.chocolate.shop.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.chocolate.shop.connectionmanager.ConnectionManager;
import org.chocolate.shop.dao.OrderDAO;
import org.chocolate.shop.entity.Order;
import org.chocolate.shop.log.Log;

public class OrderDAOImpl implements OrderDAO {

	private PreparedStatement ps = null;
	private ResultSet rs;
	private static Logger logger = Log.init("OrderDAOImpl");
	private static ConnectionManager connection = null;
	private static OrderDAOImpl orderDAO = null;

	private OrderDAOImpl() {
	}

	public static OrderDAOImpl getInstance(final ConnectionManager connect) {
		if (orderDAO == null) {
			orderDAO = new OrderDAOImpl();
			connection = connect;
		}
		return orderDAO;
	}

	@Override
	public String create(final Order object) {
		final String uuid = UUID.randomUUID().toString();
		try (Connection conn = connection.getConnection()) {
			logger.debug("create order_list");
			ps = conn.prepareStatement("insert into order_list(order_id,user_id) values(?,?);");
			ps.setString(1, uuid);
			ps.setString(2, object.getUserId());
			ps.execute();
		} catch (final SQLException e) {
			logger.error("create order_list error", e);
		} finally {
			try {
				ps.close();
			} catch (final SQLException e) {
				logger.error("problem with closing PS", e);
			}
		}
		return uuid;
	}

	@Override
	public Order readByID(final String id) {
		Order order = null;
		try (Connection conn = connection.getConnection()) {
			ps = conn.prepareStatement("select * from order_list where order_id like '?';");
			ps.setString(1, id);
			rs = ps.executeQuery();
			order = resultSet(rs);
		} catch (final SQLException e) {
			logger.error("read by ID error", e);
		} finally {
			try {
				ps.close();
				rs.close();
			} catch (final SQLException e) {
				logger.error("problem with closing PS or RS", e);
			}
		}
		return order;
	}

	@Override
	public void update(final Order object) {
		try (Connection conn = connection.getConnection()) {
			ps = conn.prepareStatement("update order_list set user_id=?, order_time=? where order_id=?;");
			ps.setString(1, object.getUserId());
			ps.setTimestamp(2, object.getOrderTime());
			ps.setString(3, object.getUid());
			ps.execute();
		} catch (final SQLException e) {
			logger.error("update order_list error", e);
		} finally {
			try {
				ps.close();
			} catch (final SQLException e) {
				logger.error("problem with closing PS", e);
			}
		}
	}

	@Override
	public void delete(final Order object) {
		try (Connection conn = connection.getConnection()) {
			logger.debug("delete order_list");
			ps = conn.prepareStatement("delete from order_list where order_id=?");
			ps.setString(1, object.getUid());
			ps.execute();
		} catch (final SQLException e) {
			logger.error("delete order_list error", e);
		} finally {
			try {
				ps.close();
			} catch (final SQLException e) {
				logger.error("problem with closing PS", e);
			}
		}
	}

	@Override
	public List<Order> getAll() {
		final List<Order> list = new ArrayList<>();
		try (Connection conn = connection.getConnection()) {
			ps = conn.prepareStatement("select * from order_list;");
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new Order(rs.getString("order_id"), rs.getString("user_id"), rs.getTimestamp("order_time")));
			}
		} catch (final SQLException e) {
			logger.error("get all order_list error", e);
		} finally {
			try {
				ps.close();
				rs.close();
			} catch (final SQLException e) {
				logger.error("problem with closing PS or RS", e);
			}
		}
		return list;
	}

	private static Order resultSet(final ResultSet rs) {
		Order order = null;
		try {
			while (rs.next()) {
				order = new Order();
				order.setUid(rs.getString("order_id"));
				order.setUserId(rs.getString("user_id"));
				order.setOrderTime(rs.getTimestamp("order_time"));
			}
		} catch (final SQLException e) {
			logger.error("ResultSet error", e);
		}
		return order;
	}

	@Override
	public void insertValuesInOrderProduct(final String order_id, final String product_id) {
		final String uuid = UUID.randomUUID().toString();
		final String query = "insert into order_product values (?,?,?);";
		try (Connection conn = connection.getConnection()) {
			ps = conn.prepareStatement(query);
			ps.setString(1, uuid);
			ps.setString(2, order_id);
			ps.setString(3, product_id);
			ps.executeQuery();
		} catch (final SQLException e) {
			logger.error("insert into order product error", e);
		} finally {
			try {
				ps.close();
			} catch (final SQLException e) {
				logger.error("problem with closing PS", e);
			}
		}

	}

	@Override
	public void deleteFromOrderProduct(final String product_id) {
		try (Connection conn = connection.getConnection()) {
			logger.debug("delete order_list");
			ps = conn.prepareStatement("delete from order_product where product_id = ?;");
			ps.setString(1, product_id);
			ps.execute();
		} catch (final SQLException e) {
			logger.error("delete order_record error", e);
		} finally {
			try {
				ps.close();
			} catch (final SQLException e) {
				logger.error("problem with closing PS", e);
			}
		}
	}
}
