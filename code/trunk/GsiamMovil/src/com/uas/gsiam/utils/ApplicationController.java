package com.uas.gsiam.utils;

import greendroid.app.GDApplication;
import android.content.Intent;

import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.ui.MainActivity;

public class ApplicationController extends GDApplication {

	private UsuarioDTO userLogin;

	public Class<?> getHomeActivityClass() {
		return MainActivity.class;
	}

	@Override
	public Intent getMainApplicationIntent() {
		return new Intent(Intent.ACTION_DEFAULT);
	}

	public UsuarioDTO getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(UsuarioDTO userLogin) {
		this.userLogin = userLogin;
	}

}
