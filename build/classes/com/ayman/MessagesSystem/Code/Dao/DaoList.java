package com.ayman.MessagesSystem.Code.Dao;

import java.sql.SQLException;
import java.util.List;

public interface DaoList<T> {
    
    public boolean insert(T t);
    
    public T update(T t, int id);
    
    public boolean delete(int t) throws SQLException;
    
    public List<T> loadAll();
}
