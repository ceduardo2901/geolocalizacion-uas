package com.uas.gsiam.web.servicios;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class GsiamRestApplication extends Application{

	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> empty = new HashSet();

	public GsiamRestApplication() {
		this.singletons.add(new Login());
		this.singletons.add(new Sitio());
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
