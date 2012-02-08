package com.uas.gsiam.usuario.test;

import static org.junit.Assert.*;
import groovyx.net.http.ContentType;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

public class EnviarInvitacioneTest {

	RestAssured restAssured;

	
	@Before
	public void setUp() throws Exception {
		 restAssured = new RestAssured();

	}

	@Test
	public void servicioNoEcontrado() {

		Response response = restAssured.expect().get(
				"/Gsiameb2/usuarios/invitar/{direccion}/{nombre}", "pepe@gmail.com", "nob");

		Assert.assertEquals(404, response.getStatusCode());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void enviarInvitacion() {

		Response response = restAssured.expect().get(
				"/GsiamWeb2/usuarios/invitar/{direccion}/{nombre}", "porcelli.antonio@gmail.com");

		Assert.assertEquals("ok", response.asString());
	}
	
	
	@Test
	public void enviarInvitacionOk() {

		Response response = restAssured.expect().get(
				"/GsiamWeb2/usuarios/invitar/{direccion}/{nombre}", "mloure@gmail.com", "Tomasito");

		Assert.assertEquals("ok", response.asString());
	}

}
