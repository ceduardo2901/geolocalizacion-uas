package com.uas.gsiam.web.servicios;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.web.delegate.SitioDelegate;

@Path("/sitios")
public class Sitio {

	private SitioDelegate servicio;

	public Sitio() {
		servicio = new SitioDelegate();
	}

	@GET
	@Path("/{lat}/{lon}")
	@Produces("application/json")
	public List<SitioDTO> getSitios(@PathParam ("lat") String lat, @PathParam ("lon") String lon){
		List<SitioDTO> sitios = servicio.getSitios(lat, lon);
		System.out.println(sitios.size());
		return null;
	}
}

