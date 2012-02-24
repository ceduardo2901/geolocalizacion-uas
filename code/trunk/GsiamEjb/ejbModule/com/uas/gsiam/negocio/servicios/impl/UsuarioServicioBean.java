package com.uas.gsiam.negocio.servicios.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uas.gsiam.negocio.dto.PosicionUsuarioDTO;
import com.uas.gsiam.negocio.dto.SolicitudContactoDTO;
import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.negocio.excepciones.UsuarioExcepcion;
import com.uas.gsiam.negocio.excepciones.UsuarioNoExisteExcepcion;
import com.uas.gsiam.negocio.servicios.UsuarioServicio;
import com.uas.gsiam.persistencia.AbstractFactory;
import com.uas.gsiam.persistencia.utiles.Constantes;
import com.uas.gsiam.persistencia.utiles.email.EmailSender;
import com.uas.gsiam.persistencia.utiles.email.EmailTemplate;
import com.uas.gsiam.persistencia.utiles.email.EmailTemplateFactory;

/**
 * Session Bean implementation class UsuarioServicio
 */
@Stateless(name = "UsuarioServicio")
public class UsuarioServicioBean implements UsuarioServicio {

	private static final Logger logger = LoggerFactory
			.getLogger(SitioServicioBean.class);

	/**
	 * Default constructor.
	 */
	public UsuarioServicioBean() {
	}

	public UsuarioDTO login(UsuarioDTO usuario)
			throws UsuarioNoExisteExcepcion, UsuarioExcepcion {
		UsuarioDTO user = null;
		logger.info("************* UsuarioDTO login(UsuarioDTO usuario) ****************");
		try {

			user = AbstractFactory.getInstance().getUsuarioDAO().login(usuario);
			if (user == null) {
				logger.warn(Constantes.NO_EXISTE_USUARIO);
				throw new UsuarioNoExisteExcepcion(Constantes.NO_EXISTE_USUARIO);
			}
			logger.debug("usuario logeado: " + user.getEmail());
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_COMUNICACION_BD);
		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_LOGIN);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_LOGIN);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_LOGIN);
		}

		return user;

	}

	public void crearUsuario(UsuarioDTO usuario) throws UsuarioExcepcion {
		logger.info("************* void crearUsuario(UsuarioDTO usuario) ****************");
		try {

			if (AbstractFactory
					.getInstance()
					.getUsuarioDAO()
					.existeUsuario(usuario.getEmail(),
							Constantes.USUARIO_ACTIVO)) {
				logger.warn(Constantes.ERROR_YA_EXISTE_USUARIO_INACTIVO);
				throw new UsuarioExcepcion(Constantes.ERROR_YA_EXISTE_USUARIO);
			} else {

				if (AbstractFactory
						.getInstance()
						.getUsuarioDAO()
						.existeUsuario(usuario.getEmail(),
								Constantes.USUARIO_INACTIVO)) {
					logger.warn(Constantes.ERROR_YA_EXISTE_USUARIO_INACTIVO);
					throw new UsuarioExcepcion(
							Constantes.ERROR_YA_EXISTE_USUARIO_INACTIVO);
				} else {

					AbstractFactory.getInstance().getUsuarioDAO()
							.crearUsuario(usuario);

					try {
						EmailTemplate template = EmailTemplateFactory
								.createEmailTemplate("mailTemplates/bienvenida.vm");
						template.put("p_nombre", usuario.getNombre());

						EmailSender mail = new EmailSender();
						mail.setTemplate(template);

						mail.setEmailDestinatario(usuario.getEmail());

						mail.setSubject(Constantes.EMAIL_SUBJECT_BIENVENIDA
								+ usuario.getNombre());

						new Thread(mail).start();
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						throw new UsuarioExcepcion(e.getMessage());
					}

				}

			}

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_COMUNICACION_BD);

		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_CREAR_USUARIO);

		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_CREAR_USUARIO);

		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_CREAR_USUARIO);

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_CREAR_USUARIO);
		}

	}

	public void modificarUsuario(UsuarioDTO usuario) throws UsuarioExcepcion {
		logger.info("************* void modificarUsuario(UsuarioDTO usuario) ****************");
		try {

			UsuarioDTO userBD = AbstractFactory.getInstance().getUsuarioDAO()
					.getUsuario(usuario.getId());

			if (userBD != null) {

				if (userBD.getEmail().equalsIgnoreCase(usuario.getEmail())) {

					AbstractFactory.getInstance().getUsuarioDAO()
							.modificarUsuario(usuario);
					logger.debug("El usuario " + usuario.getId()
							+ " se modifico correctamente");
				} else {

					if (AbstractFactory
							.getInstance()
							.getUsuarioDAO()
							.existeUsuario(usuario.getEmail(),
									Constantes.USUARIO_ACTIVO)) {
						logger.warn(Constantes.ERROR_YA_EXISTE_USUARIO);
						throw new UsuarioExcepcion(
								Constantes.ERROR_YA_EXISTE_USUARIO);
					} else {
						AbstractFactory.getInstance().getUsuarioDAO()
								.modificarUsuario(usuario);
						logger.debug("El usuario " + usuario.getId()
								+ " se modifico correctamente");
					}
				}

			} else {
				logger.warn(Constantes.NO_EXISTE_EMAIL_USUARIO);
				throw new UsuarioExcepcion(Constantes.NO_EXISTE_EMAIL_USUARIO);
			}

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_COMUNICACION_BD);

		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_MODIFICAR_USUARIO);

		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_MODIFICAR_USUARIO);

		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_MODIFICAR_USUARIO);

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_MODIFICAR_USUARIO);
		}

	}

	public void cerrarCuenta(UsuarioDTO usuario) throws UsuarioExcepcion {
		logger.info("********** void cerrarCuenta(UsuarioDTO usuario) ***********");

		try {
			if (usuario != null) {

				if (AbstractFactory
						.getInstance()
						.getUsuarioDAO()
						.existeUsuario(usuario.getEmail(),
								Constantes.USUARIO_ACTIVO)) {
					AbstractFactory.getInstance().getUsuarioDAO()
							.desactivarUsuario(usuario);

					AbstractFactory.getInstance().getUsuarioDAO()
							.eliminarContactos(usuario);
					logger.debug("Se cerro la cuenta del usuario "
							+ usuario.getId());
				} else {
					logger.warn(Constantes.NO_EXISTE_EMAIL_USUARIO);
					throw new UsuarioExcepcion(
							Constantes.NO_EXISTE_EMAIL_USUARIO);
				}
			} else {
				logger.warn(Constantes.ERROR_CERRAR_CUENTA);
				throw new UsuarioExcepcion(Constantes.ERROR_CERRAR_CUENTA);
			}

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_COMUNICACION_BD);

		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_CERRAR_CUENTA);

		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_CERRAR_CUENTA);

		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_CERRAR_CUENTA);

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_CERRAR_CUENTA);
		}

	}

	public void crearSolicitudContacto(SolicitudContactoDTO solicitud)
			throws UsuarioExcepcion {
		logger.info("********** void crearSolicitudContacto(SolicitudContactoDTO solicitud) ***********");

		try {

			AbstractFactory.getInstance().getUsuarioDAO()
					.crearContacto(solicitud);

			UsuarioDTO uSol = AbstractFactory.getInstance().getUsuarioDAO()
					.getUsuario(solicitud.getIdUsuarioSolicitante());
			UsuarioDTO uApr = AbstractFactory.getInstance().getUsuarioDAO()
					.getUsuario(solicitud.getIdUsuarioAprobador());

			try {
				EmailTemplate template = EmailTemplateFactory
						.createEmailTemplate("mailTemplates/solicitud.vm");
				template.put("p_nombre_aprobador", uApr.getNombre());
				template.put("p_nombre_solicitante", uSol.getNombre());

				EmailSender mail = new EmailSender();
				mail.setTemplate(template);

				mail.setEmailDestinatario(uApr.getEmail());

				mail.setSubject(uSol.getNombre()
						+ Constantes.EMAIL_SUBJECT_SOLICITUD_AMISTAD);

				new Thread(mail).start();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new UsuarioExcepcion(e.getMessage());
			}

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_COMUNICACION_BD);

		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_CREAR_CONTACTO);

		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_CREAR_CONTACTO);

		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_CREAR_CONTACTO);

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_CREAR_CONTACTO);
		}

	}

	public void responderSolicitudContacto(SolicitudContactoDTO solicitud,
			int accion) throws UsuarioExcepcion {
		logger.info("********** void responderSolicitudContacto(SolicitudContactoDTO solicitud,	int accion) ***********");
		try {
			if (solicitud != null) {
				if (solicitud.getIdUsuarioAprobador() != 0
						&& solicitud.getIdUsuarioSolicitante() != 0) {
					if (accion == Constantes.ACEPTAR_SOLICITUD) {
						
						AbstractFactory.getInstance().getUsuarioDAO()
								.aprobarSolicitudContacto(solicitud);
						logger.debug("Se acepta la solicitud");
					} else {
						
						AbstractFactory.getInstance().getUsuarioDAO()
								.eliminarSolicitudContacto(solicitud);
						logger.debug("Se rechaza la solicitud");
					}
				} else {
					logger.warn(Constantes.ERROR_ID_USUARIO_NULL);
					throw new UsuarioExcepcion(Constantes.ERROR_ID_USUARIO_NULL);
				}
			}

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_COMUNICACION_BD);

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_RESPONDER_SOLICITUD);

		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_RESPONDER_SOLICITUD);

		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_RESPONDER_SOLICITUD);

		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_RESPONDER_SOLICITUD);
		}

	}

	public ArrayList<UsuarioDTO> getSolicitudesRecibidasPendientes(int id)
			throws UsuarioExcepcion {
		logger.info("********** ArrayList<UsuarioDTO> getSolicitudesRecibidasPendientes(int id) ***********");
		try {

			return AbstractFactory.getInstance().getUsuarioDAO()
					.getSolicitudesRecibidasPendientes(id);

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_COMUNICACION_BD);

		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_RECUPERAR_SOLICITUDES);

		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_RECUPERAR_SOLICITUDES);

		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_RECUPERAR_SOLICITUDES);

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_RECUPERAR_SOLICITUDES);
		}

	}

	public ArrayList<UsuarioDTO> getSolicitudesEnviadasPendientes(int id)
			throws UsuarioExcepcion {
		logger.info("********** ArrayList<UsuarioDTO> getSolicitudesEnviadasPendientes(int id) ***********");
		try {

			return AbstractFactory.getInstance().getUsuarioDAO()
					.getSolicitudesEnviadasPendientes(id);

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_COMUNICACION_BD);

		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_RECUPERAR_SOLICITUDES);

		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_RECUPERAR_SOLICITUDES);

		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_RECUPERAR_SOLICITUDES);

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_RECUPERAR_SOLICITUDES);
		}

	}

	public ArrayList<UsuarioDTO> getAmigos(int idUsuario)
			throws UsuarioExcepcion {
		logger.info("********** ArrayList<UsuarioDTO> getAmigos(int idUsuario) ***********");
		try {

			return AbstractFactory.getInstance().getUsuarioDAO()
					.getContactos(idUsuario);

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_COMUNICACION_BD);

		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_RECUPERAR_CONTACTOS);

		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_RECUPERAR_CONTACTOS);

		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_RECUPERAR_CONTACTOS);

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_RECUPERAR_CONTACTOS);
		}

	}

	public ArrayList<UsuarioDTO> getUsuarios(int id, String nombre)
			throws UsuarioExcepcion {
		logger.info("********** ArrayList<UsuarioDTO> getUsuarios(int id, String nombre) ***********");
		try {

			return AbstractFactory.getInstance().getUsuarioDAO()
					.getUsuarios(id, nombre);

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_COMUNICACION_BD);

		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_RECUPERAR_USUARIOS);

		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_RECUPERAR_USUARIOS);

		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_RECUPERAR_USUARIOS);

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_RECUPERAR_USUARIOS);
		}
	}

	public void enviarInvitaciones(String direccion, String nombre)
			throws UsuarioExcepcion {
		logger.info("********** void enviarInvitaciones(String direccion, String nombre) ***********");
		try {

			if (AbstractFactory.getInstance().getUsuarioDAO()
					.existeUsuario(direccion, Constantes.USUARIO_ACTIVO)) {
				logger.warn(Constantes.ERROR_YA_EXISTE_USUARIO_INVITACION);
				throw new UsuarioExcepcion(
						Constantes.ERROR_YA_EXISTE_USUARIO_INVITACION);
			} else {
				EmailTemplate template = EmailTemplateFactory
						.createEmailTemplate("mailTemplates/invitacion.vm");
				template.put("p_nombre", nombre);

				EmailSender mail = new EmailSender();
				mail.setTemplate(template);

				mail.setEmailDestinatario(direccion);

				mail.setSubject(nombre + Constantes.EMAIL_SUBJECT_INVITACION);

				new Thread(mail).start();
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(e.getMessage());
		}

	}

	public void actualizarPosicionUsuario(PosicionUsuarioDTO posUsuario)
			throws UsuarioExcepcion {
		logger.info("********** void actualizarPosicionUsuario(PosicionUsuarioDTO posUsuario) ***********");
		try {

			AbstractFactory.getInstance().getUsuarioDAO()
					.actualizarPosicionUsuario(posUsuario);

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_COMUNICACION_BD);

		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_ACTUALIZAR_UBICACION);

		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_ACTUALIZAR_UBICACION);

		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_ACTUALIZAR_UBICACION);

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new UsuarioExcepcion(Constantes.ERROR_ACTUALIZAR_UBICACION);
		}

	}

}
