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
import org.chocolate.shop.dao.ProductDAO;
import org.chocolate.shop.entity.Product;
import org.chocolate.shop.log.Log;

public class ProductDAOImpl implements ProductDAO {

	private PreparedStatement ps = null;
	private ResultSet rs;
	private static Logger logger = Log.init("ProductDAOImpl");
	private static ConnectionManager connection = null;
	private static ProductDAOImpl productDAO = null;

	private ProductDAOImpl() {
	}

	public static ProductDAOImpl getInstance(final ConnectionManager connect) {
		if (productDAO == null) {
			productDAO = new ProductDAOImpl();
			connection = connect;
		}
		return productDAO;
	}

	@Override
	public String create(final Product object) {
		final String uuid = UUID.randomUUID().toString();
		try (Connection conn = connection.getConnection()) {
			logger.debug("create product");
			ps = conn.prepareStatement("insert into product(uid,name,description,price,photo) values(?,?,?,?,?);");
			ps.setString(1, uuid);
			ps.setString(2, object.getName());
			ps.setString(3, object.getDescription());
			ps.setBigDecimal(4, object.getPrice());
			ps.setString(5, object.getPhoto());
			ps.execute();
		} catch (final SQLException e) {
			logger.error("create product error", e);
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
	public Product readByID(final String uid) {
		Product product = null;
		try (Connection conn = connection.getConnection()) {
			ps = conn.prepareStatement("select * from product where uid like '?';");
			ps.setString(1, uid);
			rs = ps.executeQuery();
			product = resultSet(rs);
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
		return product;
	}

	@Override
	public void update(final Product object) {
		try (Connection conn = connection.getConnection()) {
			ps = conn.prepareStatement("update product set description=?, price=?, photo=? where name=?;");
			ps.setString(1, object.getDescription());
			ps.setBigDecimal(2, object.getPrice());
			ps.setString(3, object.getPhoto());
			ps.setString(4, object.getName());
			ps.execute();
		} catch (final SQLException e) {
			logger.error("update product error", e);
		} finally {
			try {
				ps.close();
			} catch (final SQLException e) {
				logger.error("problem with closing PS", e);
			}
		}
	}

	@Override
	public void delete(final Product object) {
		try (Connection conn = connection.getConnection()) {
			logger.debug("delete product");
			ps = conn.prepareStatement("delete from product where name=?");
			ps.setString(1, object.getName());
			ps.execute();
		} catch (final SQLException e) {
			logger.error("delete product error", e);
		} finally {
			try {
				ps.close();
			} catch (final SQLException e) {
				logger.error("problem with closing PS", e);
			}
		}
	}

	@Override
	public List<Product> getAll() {
		final List<Product> list = new ArrayList<Product>();
		try (Connection conn = connection.getConnection()) {
			ps = conn.prepareStatement("select * from product;");
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new Product(rs.getString("uid"), rs.getString("name"), rs.getString("description"), rs.getBigDecimal("price"),
						rs.getString("photo")));
			}
		} catch (final SQLException e) {
			logger.error("get all product error", e);
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

	@Override
	public Product findByName(final String name) {
		Product product = null;
		try (Connection conn = connection.getConnection()) {
			ps = conn.prepareStatement("select * from product where name like ?;");
			ps.setString(1, name);
			rs = ps.executeQuery();
			product = resultSet(rs);
		} catch (final SQLException e) {
			logger.error("find by product name error", e);
		} finally {
			try {
				ps.close();
				rs.close();
			} catch (final SQLException e) {
				logger.error("problem with closing PS or RS", e);
			}
		}
		return product;
	}

	private static Product resultSet(final ResultSet rs) {
		Product product = null;
		try {
			while (rs.next()) {
				product = new Product();
				product.setUid(rs.getString("uid"));
				product.setName(rs.getString("name"));
				product.setDescription(rs.getString("description"));
				product.setPrice(rs.getBigDecimal("price"));
				product.setPhoto(rs.getString("photo"));
			}
		} catch (final SQLException e) {
			logger.error("ResultSet error", e);
		}
		return product;
	}
}
