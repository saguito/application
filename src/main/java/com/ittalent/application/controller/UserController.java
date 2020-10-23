package com.ittalent.application.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ittalent.application.model.Phone;
import com.ittalent.application.model.User;
import com.ittalent.application.repository.UserRepository;
import com.ittalent.application.response.UserRegistrationResponse;
import com.ittalent.application.response.UserResponse;
import com.ittalent.application.utils.Error;
import com.ittalent.application.utils.ValidationUtils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RequestMapping("/api")
@RestController
public class UserController {
		
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	private String jwtSecret = "ittalent";

	@Autowired
	UserRepository userRepository;
			
	@RequestMapping(value = "/userRegistration", method = RequestMethod.POST)
	public ResponseEntity<UserRegistrationResponse> registerUser(@RequestBody User u) {
			
		logger.info("Starting /api/userRegistration method call {}", u.toString());
		
		List<Error> errors = validateRequest(u);
		
		UserRegistrationResponse response = new UserRegistrationResponse();

		if(errors.isEmpty()) {

			setUserData(u);
			userRepository.save(u);
			
			UserResponse userData = new UserResponse();
			userData = mappingUserData(u);
			response.setUserData(userData);
			
			return ResponseEntity
		            .status(HttpStatus.CREATED)                 
		            .body(response);
			
		} else {
			
			response.setErrors(errors);
			
			return ResponseEntity
		            .status(errors.get(0).getStatus())
		            .body(response);
		}		
		
		
	}

	private void setUserData(User u) {
		String token = getJWTToken(u.getEmail());

		u.setToken(token);

		List<Phone> phones = u.getPhones();

		if(!(phones==null) && !phones.isEmpty()) {
			for(Phone phone: phones) {
				phone.setUser(u);
			}
		}

		Calendar cal = Calendar.getInstance();
		u.setCreated(cal.getTime());
		u.setModified(cal.getTime());
		u.setLast_login(cal.getTime());

		u.setActive(true);

	}

	private UserResponse mappingUserData(User u) {
		UserResponse userData = new UserResponse();
		userData.setUuid(u.getId());
		userData.setCreated(u.getCreated());
		userData.setModified(u.getModified());
		userData.setLast_login(u.getLast_login());
		userData.setToken(u.getToken());
		userData.setActive(u.isActive());
		return userData;
	}

	private List<Error> validateRequest(User u) {
		
		List<Error> errors = new ArrayList<>();
				
		if(ValidationUtils.anyNullField(u)) {
			logger.info("anyNullField is TRUE");
			errors.add(new Error(HttpStatus.BAD_REQUEST, "Los campos Nombre, Email o Password no pueden ser nulos."));
		} else {
		
			String userName = u.getEmail();		
			
			if(userRepository.checkForExistingEmail(userName)) {
				logger.info("checkForExistingEmail is TRUE");
				errors.add(new Error(HttpStatus.CONFLICT, "El correo ya existe."));
			}
			
			if(!ValidationUtils.isValidName(u.getName())) {
				logger.info("isValidEmail is FALSE");
				errors.add(new Error(HttpStatus.BAD_REQUEST, "El nombre del usuario no puede contener caracteres como '?, *, (, )' o numeros."));
			}
			
			if(!ValidationUtils.isValidEmail(u.getEmail())) {
				logger.info("isValidEmail is FALSE");
				errors.add(new Error(HttpStatus.BAD_REQUEST, "El correo no es correcto. Debe contener tener el formato 'aaaaaaa@dominio.cl'."));
			}
			
			if(!ValidationUtils.isValidPassword(u.getPassword())) {
				logger.info("isValidPassword is FALSE");
				errors.add(new Error(HttpStatus.BAD_REQUEST, "El formato del password no es correcto. Debe contener Una (1) Mayuscula, lestras minusculas y Dos (2) numeros."));
			}

			
		}
						
		return errors;
	}

	private String getJWTToken(String username) {
		String secretKey = jwtSecret;
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER");
		
		String token = Jwts
				.builder()
				.setId("softtekJWT")
				.setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();

		return token;
	}

}
