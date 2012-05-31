package com.uas.gsiam.persistencia;

import java.io.IOException;


import com.uas.gsiam.persistencia.dao.IPublicacionDAO;
import com.uas.gsiam.persistencia.dao.ISitioDAO;
import com.uas.gsiam.persistencia.dao.IUsuarioDAO;

/**
 * Factory de DAOs
 */
public abstract class AbstractFactory {
	
	
	
	/**
	 * Metodo estatico que retorna la instancia
	 * @return AbstractFactory
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public static AbstractFactory getInstance() throws IOException,InstantiationException, IllegalAccessException,ClassNotFoundException {
		return (AbstractFactory) Class.forName("com.uas.gsiam.persistencia.postgresql.PostgreSqlFactory").newInstance();
	}
	

	/**
	 * Metodo abstracto que retorna la interfaz
	 * @return Interfaz del UsuarioDAO
	 */
	public abstract IUsuarioDAO getUsuarioDAO();
	
	
	/**
	 * Metodo abstracto que retorna la interfaz
	 * @return Interfaz del SitioDAO
	 */
	public abstract ISitioDAO getSitioDAO();
	
	
	/**
	 * Metodo abstracto que retorna la interfaz
	 * @return Interfaz del PublicacionDAO
	 */
	public abstract IPublicacionDAO getPublicacionDAO();
	
}
