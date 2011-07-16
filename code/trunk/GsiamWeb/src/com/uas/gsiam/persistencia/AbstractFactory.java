package com.uas.gsiam.persistencia;

import java.io.IOException;
import java.util.ResourceBundle;


import com.uas.gsiam.persistencia.dao.ISitioDAO;
import com.uas.gsiam.persistencia.dao.IUsuarioDAO;

/**
 * Factory de DAOs
 */
public abstract class AbstractFactory {
	
	//TODO tener una lcase Constantes???
	private static final String archivo = "com.uas.obligatorio.negocio.utiles.configuracion";
	private static ResourceBundle rb = ResourceBundle.getBundle(archivo);
	
	
	
	/**
	 * Metodo estatico que retorna la instancia
	 * @return AbstractFactory
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public static AbstractFactory getInstance() throws IOException,InstantiationException, IllegalAccessException,ClassNotFoundException {
		return (AbstractFactory) Class.forName(rb.getString("postgresql")).newInstance();
	}
	

	/**
	 * Metodo abstracto que retorna la interfaz
	 * @return Interfaz del UsuarioDAO
	 */
	public abstract IUsuarioDAO getUsuarioDAO();
	
	public abstract ISitioDAO getSitioDAO();
	
}
