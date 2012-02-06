package com.uas.gsiam.sitio.test;

import static org.junit.Assert.*;

import java.util.Date;

import groovyx.net.http.ContentType;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.uas.gsiam.test.dto.CategoriaDTO;
import com.uas.gsiam.test.dto.PublicacionDTO;
import com.uas.gsiam.test.dto.SitioDTO;

public class PublicarTest {

	RestAssured restAssured;
	PublicacionDTO publicacion;

	@Before
	public void setUp() throws Exception {
		restAssured = new RestAssured();
		publicacion = new PublicacionDTO();
	}

	@Test
	public void errorUrlPublicacion() {

		Response response = restAssured.given().contentType(ContentType.JSON)
				.body(publicacion).when().post("GsiamWeb2/sitios/publicacio");

		Assert.assertEquals(404, response.getStatusCode());
	}

	@Test
	public void metodoNoPermitido() {

		Response response = restAssured.given().contentType(ContentType.JSON)
				.body(publicacion).when().put("GsiamWeb2/sitios/publicar");

		Assert.assertEquals(405, response.getStatusCode());
	}

	@Test
	public void faltanCamposCrearPublicacion() {

		publicacion.setComentario("comentario");
		publicacion.setIdUsuario(1);
		publicacion.setPuntaje(new Float(4));
		Response response = restAssured.given().contentType(ContentType.JSON)
				.body(publicacion).when().post("GsiamWeb2/sitios/publicar");

		Assert.assertEquals(500, response.getStatusCode());
	}

	@Test
	public void crearSitio() {
		publicacion.setComentario("comentario");
		publicacion.setIdUsuario(1);
		publicacion.setPuntaje(new Float(4));
		publicacion.setIdSitio(60);
		publicacion.setFecha(new Date());
		Response response = restAssured.given().contentType(ContentType.JSON)
				.body(publicacion).when().post("GsiamWeb2/sitios/publicar");

		Assert.assertEquals(200, response.getStatusCode());
	}

}
