package com.uas.gsiam.usuario.test;

import static org.junit.Assert.*;
import groovyx.net.http.ContentType;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.uas.gsiam.test.dto.UsuarioDTO;

public class ModificarUsuarioTest {

	RestAssured restAssured;
	UsuarioDTO usuario;

	@Before
	public void setUp() throws Exception {
		restAssured = new RestAssured();
		usuario = new UsuarioDTO();
	}

	@Test
	public void errorUrlModificarUsuario() {
		
		Response response = restAssured.given().contentType(ContentType.JSON).body(usuario).when().post("GsiamWeb2/usuarios/modifica");

		Assert.assertEquals(404, response.getStatusCode());
	}
	
	@Test
	public void metodoNoPermitido() {
		usuario.setEmail("comino@gmail.com");
		Response response = restAssured.given().contentType(ContentType.JSON).body(usuario).when().put("GsiamWeb2/usuarios/modificar");

		Assert.assertEquals(405, response.getStatusCode());
	}
	
	@Test
	public void noExisteUsuario() {
		usuario.setId(1000);
		Response response = restAssured.given().contentType(ContentType.JSON).body(usuario).when().post("GsiamWeb2/usuarios/modificar");
		String msg = response.asString();
		Assert.assertEquals("El email no se encuentra en el sistema", msg);
	}
	
	@Test
	public void errorModificarUsuario() {
		usuario.setEmail("comino@gmail.com");
		usuario.setId(69);
		Response response = restAssured.given().contentType(ContentType.JSON).body(usuario).when().post("GsiamWeb2/usuarios/modificar");
		
		Assert.assertEquals(500, response.getStatusCode());
	}
	
	@Test
	public void modificarUsuario() {
		usuario.setEmail("comino@gmail.com");
		usuario.setId(69);
		usuario.setNombre("Alejandro");
		usuario.setPassword("pass");
		Response response = restAssured.given().contentType(ContentType.JSON).body(usuario).when().post("GsiamWeb2/usuarios/modificar");
		
		Assert.assertEquals(200, response.getStatusCode());
	}

}
