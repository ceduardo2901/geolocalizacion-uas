package com.uas.gsiam.web.sl;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;



/**
 * Clase que representa el patron Service Locator
 * 
 */
public class ServiceLocator {

	/*
	private static ServiceLocator instancia;
	private UsuarioServicio usuarioServicio;
	private String jndi;
*/
	
	private ServiceLocator() throws NamingException{
	}
	
	
	 private static Context getInitialContext() throws NamingException {
		 /* 
		 Hashtable env = new Hashtable();
	       String ctxFactory =
	            ConfigManager.getString("services.locator.context.initial_context_factory");
	        String providerUrl =
	            ConfigManager.getString("services.locator.context.provider_url");

	        env.put(Context.INITIAL_CONTEXT_FACTORY, ctxFactory);
	        env.put(Context.PROVIDER_URL, providerUrl);

	       return new InitialContext(env);
		 
		 */
	        return new InitialContext();
	    }
	
	 public static Object getBean(String beanAddress) throws NamingException {
        final Context context;
        Object obj = new Object();
        try {
            context = getInitialContext();
            obj = context.lookup(beanAddress);
        } catch (NamingException ne) {
            throw ne;
        }
        return obj;

    }
	
	
}
