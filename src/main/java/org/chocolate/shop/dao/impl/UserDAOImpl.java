package org.chocolate.shop.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.chocolate.shop.dao.UserDAO;
import org.chocolate.shop.entity.User;

import org.chocolate.shop.connectionmanager.ConnectionManager;
import org.chocolate.shop.dao.impl.UserDAOImpl;
import org.chocolate.shop.log.Log;

public class UserDAOImpl implements UserDAO {
    
    private PreparedStatement ps = null;
    private ResultSet rs;
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
        // TODO Auto-generated method stub
        
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
