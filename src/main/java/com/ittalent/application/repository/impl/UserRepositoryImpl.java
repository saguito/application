package com.ittalent.application.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ittalent.application.model.User;
import com.ittalent.application.repository.UserRepositoryCustom;

public class UserRepositoryImpl implements UserRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public boolean checkForExistingEmail(String email) {
		Query query = entityManager.createNativeQuery("SELECT us.* FROM USER as us " +
				"WHERE us.EMAIL = :email", User.class);
		query.setParameter("email", email);
	
		int num = query.getResultList().size();
		
		System.out.println("num: "+num);
		
		return num==0?false:true;
	}

}
