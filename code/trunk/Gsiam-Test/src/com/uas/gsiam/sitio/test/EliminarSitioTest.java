package com.uas.gsiam.sitio.test;

import static org.junit.Assert.*;
import groovyx.net.http.ContentType;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

public class EliminarSitioTest {

	RestAssured restAssured;

	@Before
	public void setUp() throws Exception {
		restAssured = new RestAssured();

	}

	@Test
	public void errorUrlEliminarSitio() {

		Response response = restAssured.expect().delete(
				"GsiamWeb2/sitios/elimina/{usuario}/{sitio}", 2, 7);

		Assert.assertEquals(404, response.getStatusCode());
	}

	@Test
	public void usuarioNoPermitidoEliminarSitio() {

		Response response = restAssured.expect().delete(
				"GsiamWeb2/sitios/eliminar/{usuario}/{sitio}", 2, 7);
		String msg = response.asString();
		Assert.assertEquals(
				"No se puede modificar o borrar un sitio que lo fue creado por usted",
				msg);
	}

	@Test
	public void eliminarSitio() {

		Response response = restAssured.expect().delete(
				"GsiamWeb2/sitios/eliminar/{usuario}/{sitio}", 1, 54);
		Assert.assertEquals(200, response.getStatusCode());
	}

}
