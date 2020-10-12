package com.ittalent.application.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ittalent.application.models.Phone;
import com.ittalent.application.models.User;
import com.ittalent.application.repositories.UserRepository;

@RequestMapping("/api")
@RestController
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@RequestMapping(value = "/greeting", method = RequestMethod.GET)
	public String getHelloWorld() {
		String message = "Hello World!";
		return message;
	}
		
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public User addStudent2(@RequestBody User u) {
		
		List<Phone> phones = u.getPhones();
		
		if(!(phones==null) && !phones.isEmpty()) {
			for(Phone phone: phones) {
				phone.setUser(u);
			}
		}
		
		userRepository.save(u);
		
		return u;
	}

}
