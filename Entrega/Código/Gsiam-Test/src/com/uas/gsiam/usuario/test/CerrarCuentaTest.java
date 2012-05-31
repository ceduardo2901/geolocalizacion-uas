package com.uas.gsiam.usuario.test;

import static org.junit.Assert.*;
import groovyx.net.http.ContentType;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.uas.gsiam.test.dto.UsuarioDTO;

public class CerrarCuentaTest {

	RestAssured restAssured;
	UsuarioDTO usuario;

	@Before
	public void setUp() throws Exception {
		restAssured = new RestAssured();
		usuario = new UsuarioDTO();
	}

	@Test
	public void errorUrlCerrarCuenta() {
		
		Response response = restAssured.given().contentType(ContentType.JSON).body(usuario).when().post("GsiamWeb2/usuarios/cerra");

		Assert.assertEquals(404, response.getStatusCode());
	}
	
	@Test
	public void metodoNoPermitido() {
		
		Response response = restAssured.given().contentType(ContentType.JSON).body(usuario).when().put("GsiamWeb2/usuarios/cerrar");

		Assert.assertEquals(405, response.getStatusCode());
	}
	
	@Test
	public void errorAlCerrarCuenta() {
		
		Response response = restAssured.given().contentType(ContentType.JSON).body(usuario).when().post("GsiamWeb2/usuarios/cerrar");

		Assert.assertEquals(500, response.getStatusCode());
	}
	
	@Test
	public void errorAlCerrarCuentaNoExisteUsuario() {
		usuario.setEmail("pe@gmail.com");
		Response response = restAssured.given().contentType(ContentType.JSON).body(usuario).when().post("GsiamWeb2/usuarios/cerrar");
		String msg = response.asString();
		Assert.assertEquals("El email no se encuentra en el sistema", msg);
	}
	
	@Test
	public void cerrarCuenta() {
		usuario.setEmail("comino@gmail.com");
		usuario.setId(69);
		Response response = restAssured.given().contentType(ContentType.JSON).body(usuario).when().post("GsiamWeb2/usuarios/cerrar");
		
		Assert.assertEquals(200, response.getStatusCode());
	}

}
