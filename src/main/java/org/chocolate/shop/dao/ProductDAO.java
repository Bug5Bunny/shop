package org.chocolate.shop.dao;

import org.chocolate.shop.entity.Product;

public interface ProductDAO extends BaseDAO<Product> {

    Product findByName(String name);
    
}
