package com.yourorg.sampleapp.core;

import java.util.Set;

import lombok.Data;

@Data
public class User {
	private String username;
	private String password;
	private String fName;
	private String mName;
	private String lName;
	private String name;
	private String delFlag;
	private Set<Role> roles;
	private String token;
}
