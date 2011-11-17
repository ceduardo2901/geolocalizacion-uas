package com.uas.gsiam.utils;

import java.util.ArrayList;
import java.util.HashMap;

import greendroid.app.GDApplication;
import android.content.Intent;

import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.ui.MainActivity;

public class ApplicationController extends GDApplication {
	
	private UsuarioDTO userLogin;
	private ArrayList<HashMap<String, Object>> gruposCategorias = new ArrayList<HashMap<String, Object>>();
	private ArrayList<ArrayList<HashMap<String, Object>>> subCategorias = new ArrayList<ArrayList<HashMap<String, Object>>>();
	

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

	public ArrayList<HashMap<String, Object>> getGruposCategorias() {
		return gruposCategorias;
	}

	public void setGruposCategorias(
			ArrayList<HashMap<String, Object>> gruposCategorias) {
		this.gruposCategorias = gruposCategorias;
	}

	public ArrayList<ArrayList<HashMap<String, Object>>> getSubCategorias() {
		return subCategorias;
	}

	public void setSubCategorias(
			ArrayList<ArrayList<HashMap<String, Object>>> subCategorias) {
		this.subCategorias = subCategorias;
	}

	
	

}
