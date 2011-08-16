package com.uas.gsiam.sitios.utils;

import com.uas.gsiam.sitios.ui.SitioMovilDTO;

public class ListaSitios  {

	private SitioMovilDTO[] sitios;

	ListaSitios() {

	}

	public SitioMovilDTO[] getAircraftTypes() {
		return sitios;
	}

	public void setAircraftTypes(SitioMovilDTO[] sitios) {
		this.sitios = sitios;
	}

	
}
