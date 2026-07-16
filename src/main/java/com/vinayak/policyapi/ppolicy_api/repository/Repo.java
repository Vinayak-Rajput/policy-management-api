package com.vinayak.policyapi.ppolicy_api.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public abstract class Repo<T> {

    protected final ConcurrentMap<Integer, T> storage = new ConcurrentHashMap<>();
    
    private int idCounter = 1;

    private synchronized int generateNextId() {
        return idCounter++;
    }

    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    public Optional<T> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    protected abstract void setId(T entity, int id);
    protected abstract int getId(T entity);

    public T save(T entity) {
        int currentId = getId(entity);
        
        if (currentId == 0) {
            currentId = generateNextId();
            setId(entity, currentId);
        }
        
        storage.put(currentId, entity);
        return entity;
    }
}