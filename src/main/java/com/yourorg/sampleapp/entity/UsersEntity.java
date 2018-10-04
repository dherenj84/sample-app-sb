package com.yourorg.sampleapp.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "Users")
@Data
public class UsersEntity {
	@Id
	private String userId;
	private String name;
	private String email;
	private String hashPass;
	private String salt;
	private Date createdDate;
	private String createdBy;
	private Date updatedDate;
	private String updatedBy;
	private String delFlag;
	private String sendEmail;
	private String phoneNumber;
	private String sendText;
	private String changePassword;
}
