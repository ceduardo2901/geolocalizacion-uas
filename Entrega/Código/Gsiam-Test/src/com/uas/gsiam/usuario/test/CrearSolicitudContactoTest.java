package com.uas.gsiam.usuario.test;

import static org.junit.Assert.*;
import groovyx.net.http.ContentType;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.uas.gsiam.test.dto.SolicitudContactoDTO;
import com.uas.gsiam.test.dto.UsuarioDTO;

public class CrearSolicitudContactoTest {
	
	RestAssured restAssured;
	SolicitudContactoDTO solicitud;

	@Before
	public void setUp() throws Exception {
		restAssured = new RestAssured();
		solicitud = new SolicitudContactoDTO();
	}

	@Test
	public void errorUrlCrearSolicitudContacto() {
		
		Response response = restAssured.given().contentType(ContentType.JSON).body(solicitud).when().post("GsiamWeb2/usuarios/agregarsolicitu");

		Assert.assertEquals(404, response.getStatusCode());
	}
	
	@Test
	public void metodoNoPermitido() {
		
		Response response = restAssured.given().contentType(ContentType.JSON).body(solicitud).when().put("GsiamWeb2/usuarios/agregarsolicitud");

		Assert.assertEquals(405, response.getStatusCode());
	}
	
	@Test
	public void errorCrearSolicitudContacto() {
		
		Response response = restAssured.given().contentType(ContentType.JSON).body(solicitud).when().post("GsiamWeb2/usuarios/agregarsolicitud");

		Assert.assertEquals(500, response.getStatusCode());
	}
	
	@Test
	public void faltaIdAprobador() {
		solicitud.setIdUsuarioSolicitante(69);
		Response response = restAssured.given().contentType(ContentType.JSON).body(solicitud).when().post("GsiamWeb2/usuarios/agregarsolicitud");

		Assert.assertEquals(500, response.getStatusCode());
	}
	
	@Test
	public void crearSolicitud() {
		solicitud.setIdUsuarioSolicitante(5);
		solicitud.setIdUsuarioAprobador(3);
		Response response = restAssured.given().contentType(ContentType.JSON).body(solicitud).when().post("GsiamWeb2/usuarios/agregarsolicitud");

		Assert.assertEquals(200, response.getStatusCode());
	}

}
