package com.uas.gsiam.utils;

import com.uas.gsiam.negocio.dto.UsuarioDTO;

import android.app.Application;

public class ApplicationController extends Application {
	
	
	private UsuarioDTO userLogin;

	@Override
	public void onCreate() {
	    super.onCreate();
	    //Do Application initialization over here
    }
    
	public UsuarioDTO getUserLogin() {
		return userLogin;
	}


	public void setUserLogin(UsuarioDTO userLogin) {
		this.userLogin = userLogin;
	}

	
	

}
