package com.gsiam.web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "user")
@SessionScoped
public class User {

	private String name = "";

	public String getName() {

		return name;

	}

	public void setName(String name) {

		this.name = name;

	}
}
