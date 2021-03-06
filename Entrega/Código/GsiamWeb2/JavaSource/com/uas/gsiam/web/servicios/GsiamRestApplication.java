package com.uas.gsiam.web.servicios;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class GsiamRestApplication extends Application{

	private Set<Object> singletons = new HashSet<Object>();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Set<Class<?>> empty = new HashSet();

	public GsiamRestApplication() {
		this.singletons.add(new UsuarioServicios());
		this.singletons.add(new SitioServicios());
	}

	public Set<Class<?>> getClasses()
	{
		return this.empty;
	}

	public Set<Object> getSingletons()
	{
		return this.singletons;
	}

}
