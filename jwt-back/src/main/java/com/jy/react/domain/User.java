package com.jy.react.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "uid", length = 100, nullable = false, unique = true)
    private String uid;

    private String password;
    
    private String roles = "ROLE_USER";

    
    public static User createUser(String uid, String password) {
    	User user = new User();
    	user.uid = uid;
    	user.password = password;
    	return user;
    }

}
