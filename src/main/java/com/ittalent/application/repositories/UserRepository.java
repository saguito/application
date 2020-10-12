package com.ittalent.application.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ittalent.application.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{

}
