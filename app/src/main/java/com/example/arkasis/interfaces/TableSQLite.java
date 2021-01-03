package com.example.arkasis.interfaces;

import java.util.List;

public interface TableSQLite {
    public void insertar(Object o);
    public void insertarBatch(List<Object> objectList);
    public int getCount();
    public void truncate();
    public List<Object> findAll(String buscar, Integer limit);
}
