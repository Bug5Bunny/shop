package org.chocolate.shop.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.chocolate.shop.connectionmanager.ConnectionManager;
import org.chocolate.shop.dao.UserDAO;
import org.chocolate.shop.entity.User;
import org.chocolate.shop.log.Log;

public class UserDAOImpl implements UserDAO {
    
    private PreparedStatement ps = null;
    private static Logger logger = Log.init("UserDAOImpl");
    private static ConnectionManager connection = null;
    private static UserDAOImpl userDAO = null;
    
    private UserDAOImpl() {
    }
    
    public static UserDAOImpl getInstance(ConnectionManager connect) {
        if (userDAO == null) {
            userDAO = new UserDAOImpl();
            connection = connect;
        }
        return userDAO;
    }
    
    public String create(User object) {
        String uuid = UUID.randomUUID().toString();
        try (Connection conn = connection.getConnection()) {
                logger.debug("create user");
                ps = conn.prepareStatement("insert into user_card(user_id,username,email,password) values(?,?,?,?);");
                ps.setString(1, uuid);
                ps.setString(2, object.getUsername());
                ps.setString(3, object.getEmail());
                ps.setString(4, object.getPassword());
                ps.execute();
                ps.close();
        } catch (SQLException e) {
            logger.error("create user error", e);
        }
        return uuid;
    }
    
    public User readByID(String id) {
        // TODO Auto-generated method stub
        return null;
    }
    
    public void update(User object) {
        // TODO Auto-generated method stub
        
    }
    
    public void delete(User object) {
        try (Connection conn = connection.getConnection()) {
            logger.debug("delete user");
            ps = conn.prepareStatement("delete * from user_card where username=?");
            ps.setString(1, object.getUsername());
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            logger.error("delete user error", e);
        }
        
    }
    
    public List<User> getAll() {
        // TODO Auto-generated method stub
        return null;
    }
    
    public User findByUsername(String username) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
