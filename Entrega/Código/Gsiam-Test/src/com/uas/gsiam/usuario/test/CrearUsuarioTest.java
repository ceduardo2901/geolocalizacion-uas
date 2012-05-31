package com.uas.gsiam.usuario.test;

import static org.junit.Assert.*;
import groovyx.net.http.ContentType;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.uas.gsiam.test.dto.UsuarioDTO;

public class CrearUsuarioTest {

	RestAssured restAssured;
	UsuarioDTO usuario;

	@Before
	public void setUp() throws Exception {
		restAssured = new RestAssured();
		usuario = new UsuarioDTO();
	}

	@Test
	public void errorUrlCrearUsuario() {
		
		Response response = restAssured.given().contentType(ContentType.JSON).body(usuario).when().post("GsiamWeb2/usuarios/agrega");

		Assert.assertEquals(404, response.getStatusCode());
	}
	
	@Test
	public void existeUsuario() {
		usuario.setEmail("pepe@gmail.com");
		
		Response response = restAssured.given().contentType(ContentType.JSON).body(usuario).when().post("GsiamWeb2/usuarios/agregar");
		String msg = response.asString();
		Assert.assertEquals("Ya existe una cuenta con ese mail, ingrese otro", msg);
	}

	
	@Test
	public void errorCrearUsuario() {
		usuario.setEmail("pablo@gmail.com");
		usuario.setNombre("Pablo");
		
		Response response = restAssured.given().contentType(ContentType.JSON).body(usuario).when().post("GsiamWeb2/usuarios/agregar");

		Assert.assertEquals(500, response.getStatusCode());
	}
	
	@Test
	public void crearUsuario() {
		usuario.setEmail("pablo@gmail.com");
		usuario.setNombre("Pablo");
		usuario.setPassword("pass");
		Response response = restAssured.given().contentType(ContentType.JSON).body(usuario).when().post("GsiamWeb2/usuarios/agregar");

		Assert.assertEquals(200, response.getStatusCode());
	}
}
