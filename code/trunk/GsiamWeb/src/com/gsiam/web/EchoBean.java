package com.gsiam.web;

import javax.faces.event.ActionEvent;

public class EchoBean {

	private String name;
	private Integer count;
 
	public EchoBean(){
		System.out.println("prueba");
	}
	
	public Integer getCount() {
		return count;
	}
 
	public String getName() {
		return name;
	}
 
	public void setName(String name) {
		this.name = name;
	}
 
	public void countListener (ActionEvent event){
		count = name.length();
		}
}
