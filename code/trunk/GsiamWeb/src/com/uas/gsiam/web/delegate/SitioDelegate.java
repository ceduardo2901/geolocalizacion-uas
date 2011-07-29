package com.uas.gsiam.web.delegate;

import com.uas.gsiam.negocio.servicios.SitioServicio;
import com.uas.gsiam.web.sl.ServiceLocator;

public class SitioDelegate {
	
	private SitioServicio servicioSitio;

    public SitioDelegate() {
        initialLoadBean();
    }

    public void initialLoadBean() {
        try {
            this.servicioSitio =
                    (SitioServicio)ServiceLocator.getBean(SitioServicio.SERVICE_ADDRESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   
    
    
	
}
