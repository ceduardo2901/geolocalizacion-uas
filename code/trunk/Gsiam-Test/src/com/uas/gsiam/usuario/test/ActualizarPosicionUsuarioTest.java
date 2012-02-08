package com.uas.gsiam.usuario.test;

import static org.junit.Assert.*;
import groovyx.net.http.ContentType;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.uas.gsiam.test.dto.PosicionUsuarioDTO;
import com.uas.gsiam.test.dto.PublicacionDTO;

public class ActualizarPosicionUsuarioTest {

	RestAssured restAssured;
	PosicionUsuarioDTO posicion;
	
	@Before
	public void setUp() throws Exception {
		RestAssured restAssured;
		posicion = new PosicionUsuarioDTO();
	}

	@Test
	public void errorUrlActualizarPosicion() {

		Response response = restAssured.given().contentType(ContentType.JSON)
				.body(posicion).when().post("GsiamWeb2/sitios/actualizarPosicio");

		Assert.assertEquals(404, response.getStatusCode());
	}

	@Test
	public void metodoNoPermitido() {

		Response response = restAssured.given().contentType(ContentType.JSON)
				.body(posicion).when().put("GsiamWeb2/usuarios/actualizarPosicion");

		Assert.assertEquals(405, response.getStatusCode());
	}
	
	@Test
	public void actualizarPosicion() {
		posicion.setLat(-34.907323);
		posicion.setLon(-56.169586);
		posicion.setIdUsuario(4);
		Response response = restAssured.given().contentType(ContentType.JSON)
				.body(posicion).when().post("GsiamWeb2/usuarios/actualizarPosicion");

		Assert.assertEquals(200, response.getStatusCode());
	}

}
