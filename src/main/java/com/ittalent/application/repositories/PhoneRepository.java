package com.ittalent.application.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ittalent.application.models.Phone;

@Repository
public interface PhoneRepository extends CrudRepository<Phone, Long>{

}
