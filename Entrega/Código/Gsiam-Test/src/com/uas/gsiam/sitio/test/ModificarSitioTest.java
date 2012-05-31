package com.uas.gsiam.sitio.test;

import static org.junit.Assert.*;
import groovyx.net.http.ContentType;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.uas.gsiam.test.dto.CategoriaDTO;
import com.uas.gsiam.test.dto.SitioDTO;

public class ModificarSitioTest {

	RestAssured restAssured;
	SitioDTO sitio;

	@Before
	public void setUp() throws Exception {
		restAssured = new RestAssured();
		sitio = new SitioDTO();
	}

	@Test
	public void errorUrlModificarSitio() {

		Response response = restAssured.given().contentType(ContentType.JSON)
				.body(sitio).when().put("GsiamWeb2/sitios/modifica");

		Assert.assertEquals(404, response.getStatusCode());
	}

	@Test
	public void metodoNoPermitido() {

		Response response = restAssured.given().contentType(ContentType.JSON)
				.body(sitio).when().post("GsiamWeb2/sitios/modificar");

		Assert.assertEquals(405, response.getStatusCode());
	}

	@Test
	public void usuarioNoPermitidoParaModoficar() {
		sitio.setIdUsuario(2);
		sitio.setIdSitio(1);
		CategoriaDTO categoria = new CategoriaDTO();
		categoria.setIdGrupo(1);
		categoria.setIdCategoria(1);
		sitio.setNombre("Venesia");
		sitio.setCategoria(categoria);
		
		sitio.setDireccion("Juan Paullier 1040");
		Response response = restAssured.given().contentType(ContentType.JSON)
				.body(sitio).when().put("GsiamWeb2/sitios/modificar");
		String msg = response.asString();
		Assert.assertEquals(
				"No se puede modificar o borrar un sitio que lo fue creado por usted",
				msg);
	}
	
	@Test
	public void modificarSitio() {
		sitio.setIdUsuario(1);
		sitio.setIdSitio(7);
		CategoriaDTO categoria = new CategoriaDTO();
		categoria.setIdGrupo(1);
		categoria.setIdCategoria(1);
		sitio.setNombre("Venesia");
		sitio.setCategoria(categoria);
		sitio.setDireccion("Juan Paullier 1040");
		sitio.setLat(-34.904754);
		sitio.setLon(-56.167708);
		Response response = restAssured.given().contentType(ContentType.JSON)
				.body(sitio).when().put("GsiamWeb2/sitios/modificar");
		
		Assert.assertEquals(200, response.getStatusCode());
	}

}
