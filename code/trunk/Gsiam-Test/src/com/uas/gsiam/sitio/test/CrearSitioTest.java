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

public class CrearSitioTest {

	RestAssured restAssured;
	SitioDTO sitio;

	@Before
	public void setUp() throws Exception {
		restAssured = new RestAssured();
		sitio = new SitioDTO();
	}

	@Test
	public void errorUrlCrearUsuario() {
		
		Response response = restAssured.given().contentType(ContentType.JSON).body(sitio).when().post("GsiamWeb2/sitios/agrega");

		Assert.assertEquals(404, response.getStatusCode());
	}
	
	@Test
	public void metodoNoPermitido() {
		
		Response response = restAssured.given().contentType(ContentType.JSON).body(sitio).when().put("GsiamWeb2/sitios/agregar");

		Assert.assertEquals(405, response.getStatusCode());
	}
	
	@Test
	public void faltanCamposCrearSitio() {
		CategoriaDTO categoria = new CategoriaDTO();
		categoria.setIdCategoria(1);
		categoria.setIdGrupo(1);
		sitio.setDireccion("Juan Paullier 1013");
		sitio.setIdUsuario(2);
		sitio.setLat(-34.904754);
		sitio.setLon(-56.167708);
		sitio.setCategoria(categoria);
		Response response = restAssured.given().contentType(ContentType.JSON).body(sitio).when().post("GsiamWeb2/sitios/agregar");

		Assert.assertEquals(500, response.getStatusCode());
	}
	
	@Test
	public void crearSitio() {
		CategoriaDTO categoria = new CategoriaDTO();
		categoria.setIdCategoria(1);
		categoria.setIdGrupo(1);
		sitio.setDireccion("Juan Paullier 1013");
		sitio.setIdUsuario(2);
		sitio.setLat(-34.904754);
		sitio.setLon(-56.167708);
		sitio.setCategoria(categoria);
		sitio.setNombre("Don Trigo");
		Response response = restAssured.given().contentType(ContentType.JSON).body(sitio).when().post("GsiamWeb2/sitios/agregar");

		Assert.assertEquals(200, response.getStatusCode());
	}

}
