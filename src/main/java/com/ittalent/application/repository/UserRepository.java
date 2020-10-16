package com.ittalent.application.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ittalent.application.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>, UserRepositoryCustom {

}
