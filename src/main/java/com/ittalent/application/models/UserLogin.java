package com.ittalent.application.models;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;  

@Entity
@Table(name = "UserLogin")
public class UserLogin {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long uuid;
	private Date created;  
	private Date modified;  
	private Date last_login;
	private String token;
	private boolean isActive;
	@OneToOne
	@JoinColumn(name="user_id", referencedColumnName = "id")
	private User user;
				
	public UserLogin() {
		super();
	}

	
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getUuid() {
		return uuid;
	}

	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public Date getLast_login() {
		return last_login;
	}

	public void setLast_login(Date last_login) {
		this.last_login = last_login;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}


	@Override
	public String toString() {
		return "UserLogin [id=" + id + ", uuid=" + uuid + ", created=" + created + ", modified=" + modified
				+ ", last_login=" + last_login + ", token=" + token + ", isActive=" + isActive + "]";
	}
	
}
