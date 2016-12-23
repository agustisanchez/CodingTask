package com.prestamosprima.codingtask.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseDAO<T> extends JpaRepository<T, Long> {

}
