package org.chocolate.shop.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.chocolate.shop.connectionmanager.ConnectionManager;
import org.chocolate.shop.dao.UserDAO;
import org.chocolate.shop.entity.User;
import org.chocolate.shop.log.Log;

public class UserDAOImpl implements UserDAO {

	private PreparedStatement ps = null;
	private ResultSet rs;
	private static Logger logger = Log.init("UserDAOImpl");
	private static ConnectionManager connection = null;
	private static UserDAOImpl userDAO = null;

	private UserDAOImpl() {
	}

	public static UserDAOImpl getInstance(final ConnectionManager connect) {
		if (userDAO == null) {
			userDAO = new UserDAOImpl();
			connection = connect;
		}
		return userDAO;
	}

	@Override
	public String create(final User object) {
		final String uuid = UUID.randomUUID().toString();
		try (Connection conn = connection.getConnection()) {
			logger.debug("create user");
			ps = conn.prepareStatement("insert into user_card(user_id,username,email,password) values(?,?,?,?);");
			ps.setString(1, uuid);
			ps.setString(2, object.getUsername());
			ps.setString(3, object.getEmail());
			ps.setString(4, DigestUtils.md5Hex(object.getPassword()));
			ps.execute();
		} catch (final SQLException e) {
			logger.error("create user error", e);
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
	public User readByID(final String id) {
		User user = null;
		try (Connection conn = connection.getConnection()) {
			ps = conn.prepareStatement("select * from user_card where user_id like '?';");
			ps.setString(1, id);
			rs = ps.executeQuery();
			user = resultSet(rs);
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
		return user;
	}

	@Override
	public void update(final User object) {
		try (Connection conn = connection.getConnection()) {
			ps = conn.prepareStatement("update user_card set email=?, password=? where username=?;");
			ps.setString(1, object.getEmail());
			ps.setString(2, DigestUtils.md5Hex(object.getPassword()));
			ps.setString(3, object.getUsername());
			ps.execute();
		} catch (final SQLException e) {
			logger.error("update user error", e);
		} finally {
			try {
				ps.close();
			} catch (final SQLException e) {
				logger.error("problem with closing PS", e);
			}
		}
	}

	public void lock(final String username, final String notLocked) {
		try (Connection conn = connection.getConnection()) {
			ps = conn.prepareStatement("update user_card set notlocked=? where username=?;");
			ps.setBoolean(1, Boolean.parseBoolean(notLocked));
			ps.setString(2, username);
			ps.execute();
		} catch (final SQLException e) {
			logger.error("lock user error", e);
		} finally {
			try {
				ps.close();
			} catch (final SQLException e) {
				logger.error("problem with closing PS", e);
			}
		}
	}

	@Override
	public void delete(final User object) {
		try (Connection conn = connection.getConnection()) {
			logger.debug("delete user");
			ps = conn.prepareStatement("delete from user_card where username=?");
			ps.setString(1, object.getUsername());
			ps.execute();
		} catch (final SQLException e) {
			logger.error("delete user error", e);
		} finally {
			try {
				ps.close();
			} catch (final SQLException e) {
				logger.error("problem with closing PS", e);
			}
		}
	}

	@Override
	public List<User> getAll() {
		final List<User> list = new ArrayList<User>();
		try (Connection conn = connection.getConnection()) {
			ps = conn.prepareStatement("select * from user_card;");
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new User(rs.getString("user_id"), rs.getString("username"), rs.getString("email"), rs.getString("password"),
						rs.getString("role"), rs.getBoolean("notlocked")));
			}
		} catch (final SQLException e) {
			logger.error("get all error", e);
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

	public List<User> getAllNotLocked() {
		final List<User> list = new ArrayList<>();
		try (Connection conn = connection.getConnection()) {
			ps = conn.prepareStatement("select * from user_card where notlocked=true;");
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new User(rs.getString("user_id"), rs.getString("username"), rs.getString("email"), rs.getString("password"),
						rs.getString("role"), rs.getBoolean("notlocked")));
			}
		} catch (final SQLException e) {
			logger.error("get all not locked error", e);
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

	public List<User> getAllLocked() {
		final List<User> list = new ArrayList<>();
		try (Connection conn = connection.getConnection()) {
			ps = conn.prepareStatement("select * from user_card where notlocked=false;");
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new User(rs.getString("user_id"), rs.getString("username"), rs.getString("email"), rs.getString("password"),
						rs.getString("role"), rs.getBoolean("notlocked")));
			}
		} catch (final SQLException e) {
			logger.error("get all locked error", e);
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
	public User findByUsername(final String username) {
		User user = null;
		try (Connection conn = connection.getConnection()) {
			ps = conn.prepareStatement("select * from user_card where username like ?;");
			ps.setString(1, username);
			rs = ps.executeQuery();
			user = resultSet(rs);
		} catch (final SQLException e) {
			logger.error("find by username error", e);
		} finally {
			try {
				ps.close();
				rs.close();
			} catch (final SQLException e) {
				logger.error("problem with closing PS or RS", e);
			}
		}
		return user;
	}

	private static User resultSet(final ResultSet rs) {
		User user = null;
		try {
			while (rs.next()) {
				user = new User();
				user.setUid(rs.getString("user_id"));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setRole(rs.getString("role"));
				user.setBan(rs.getBoolean("notlocked"));
			}
		} catch (final SQLException e) {
			logger.error("ResultSet error", e);
		}
		return user;
	}
}
