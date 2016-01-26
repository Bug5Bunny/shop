package org.chocolate.shop.dao;

import org.chocolate.shop.entity.User;

public interface UserDAO extends BaseDAO<User> {

	User findByUsername(String username);

}
