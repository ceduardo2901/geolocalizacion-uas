package com.uas.gsiam.web.delegate;

import java.util.List;

import com.uas.gsiam.negocio.dto.PublicacionDTO;
import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.negocio.excepciones.PublicacionExcepcion;
import com.uas.gsiam.negocio.excepciones.SitioExcepcion;
import com.uas.gsiam.negocio.excepciones.SitioYaExisteExcepcion;
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

   public List<SitioDTO> getSitios(String lat, String lon){
	   SitioDTO sitio = new SitioDTO();
	   sitio.setLat(new Double(lat));
	   sitio.setLon(new Double(lon));
	   return servicioSitio.obtenerSitios(sitio);
   }
   
   public void crearSitio(SitioDTO sitio) throws SitioYaExisteExcepcion, SitioExcepcion {
	   
	    servicioSitio.crearSitio(sitio);
   }
   
   public void crearPublicacion(PublicacionDTO publicacion) throws PublicacionExcepcion {
	   
	    servicioSitio.crearPublicacion(publicacion);
  }
    
   
    
	
}
