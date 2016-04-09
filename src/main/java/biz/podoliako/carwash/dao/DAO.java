package biz.podoliako.carwash.dao;


import java.util.List;

public interface DAO<T> {

    public T persist(T entity);

    public T find(Long id);

    public List<T> findAll();

    public List<T> findAllIncludingDeleted();

    public T update(T entity);

    public void remove (T entity);








}

