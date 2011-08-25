package com.uas.gsiam.web.servicios;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;


import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.web.delegate.SitioDelegate;

@Path("/sitios")
public class SitioServicios {

	private SitioDelegate servicio;

	public SitioServicios() {
		servicio = new SitioDelegate();
	}

	@GET
	@Path("/{lat}/{lon}")
	@Produces("application/json")
	public List<ServicioSitioDTO> getSitios(@PathParam ("lat") String lat, @PathParam ("lon") String lon){
		List<ServicioSitioDTO> listaSitios = new ArrayList<ServicioSitioDTO>();
		List<SitioDTO> sitios = servicio.getSitios(lat, lon);
		for(SitioDTO sitio : sitios){
			listaSitios.add(new ServicioSitioDTO(sitio));
		}
		
		System.out.println(sitios.size());
		
		return listaSitios;
	}
}

