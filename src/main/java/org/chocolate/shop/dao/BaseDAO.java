package org.chocolate.shop.dao;

import java.util.List;

public interface BaseDAO<T> {
    
    String create(T object);
    
    T readByID(String id);
    
    void update(T object);
    
    void delete(T object);
    
    List<T> getAll();
}
