package com.uas.gsiam.web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

@ManagedBean(name = "echoBean")
@SessionScoped
public class EchoBean {

	private String name="Tony";
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
