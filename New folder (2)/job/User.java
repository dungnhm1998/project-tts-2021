package com.app.tts.server.job;

import com.opencsv.bean.CsvBindByName;

import lombok.Data;

@Data
public class User {
//	@CsvBindByPosition(position = 0)
	@CsvBindByName(column = "EMAIL")
	String email;

//	@CsvBindByPosition(position = 1)
	@CsvBindByName(column = "PASSWORD")
	String password;

//	@CsvBindByPosition(position = 2)
	@CsvBindByName(column = "PHONE")
	String phone;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "User [email=" + email + ", password=" + password + ", phone=" + phone + "]";
	}
	
	
}