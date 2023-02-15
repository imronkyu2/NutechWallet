package com.example.nutechwallet.model.register;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponse {

	@JsonProperty("last_name")
	private String lastName;

	@JsonProperty("first_name")
	private String firstName;

	@JsonProperty("email")
	private String email;

	public UserResponse(){

	}

	public String getLastName(){
		return lastName;
	}

	public String getFirstName(){
		return firstName;
	}

	public String getEmail(){
		return email;
	}
}