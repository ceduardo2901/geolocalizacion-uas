package com.uas.gsiam.web.delegate;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uas.gsiam.negocio.dto.PosicionUsuarioDTO;
import com.uas.gsiam.negocio.dto.SolicitudContactoDTO;
import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.negocio.excepciones.UsuarioExcepcion;
import com.uas.gsiam.negocio.excepciones.UsuarioNoExisteExcepcion;
import com.uas.gsiam.negocio.servicios.UsuarioServicio;
import com.uas.gsiam.negocio.servicios.impl.SitioServicioBean;
import com.uas.gsiam.persistencia.utiles.Constantes;
import com.uas.gsiam.web.sl.ServiceLocator;

/**
 * 
 * Esta clase es la encargada de llamar a los servicios publicados en el ejb
 * UsuarioServicioBean.
 * 
 * @author Martín
 * 
 */
public class UsuarioDelegate {

	private static final Logger logger = LoggerFactory
			.getLogger(SitioServicioBean.class);
	
	private UsuarioServicio servicioUsuario;

	public UsuarioDelegate() {
		initialLoadBean();

	}

	public void initialLoadBean() {
		try {
			this.servicioUsuario = (UsuarioServicio) ServiceLocator
					.getBean(UsuarioServicio.SERVICE_ADDRESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * Metodo que identifica al usuario en el sistema
	 * 
	 * @param usuario
	 *            Se debe ingresar el email y el password del usuario para
	 *            loguearlo al sistema
	 * @return El ususario correspondiente a los datos ingresados
	 * @throws UsuarioNoExisteExcepcion
	 * @throws UsuarioExcepcion
	 */
	public UsuarioDTO login(UsuarioDTO usuario)
			throws UsuarioNoExisteExcepcion, UsuarioExcepcion {

		usuario = servicioUsuario.login(usuario);

		return usuario;
	}

	/**
	 * Crea un usuario en el sistema
	 * 
	 * @param usuario
	 *            Datos del usuario a crear
	 * @throws UsuarioExcepcion
	 */
	public void crearUsuario(UsuarioDTO usuario) throws UsuarioExcepcion {

		servicioUsuario.crearUsuario(usuario);

	}

	/**
	 * Modifica un usuario en el sistema
	 * 
	 * @param usuario
	 *            Datos del usuario a modificar
	 * @throws UsuarioExcepcion
	 */
	public void modificarUsuario(UsuarioDTO usuario) throws UsuarioExcepcion {

		servicioUsuario.modificarUsuario(usuario);

	}

	/**
	 * Cierra la cuenta del usuario en el sistema
	 * 
	 * @param usuario
	 *            Id de usuario el cual sera eliminado del sistema
	 * @throws UsuarioExcepcion
	 */
	public void cerrarCuenta(UsuarioDTO usuario) throws UsuarioExcepcion {

		servicioUsuario.cerrarCuenta(usuario);

	}

	/**
	 * Retorna la lista de amigos del usuario
	 * 
	 * @param idUsuario
	 *            Identificador del usuario
	 * @return La lista de amigos del usuario ingresado
	 * @throws UsuarioExcepcion
	 */
	public List<UsuarioDTO> getAmigos(int idUsuario) throws UsuarioExcepcion {

		return servicioUsuario.getAmigos(idUsuario);
	}

	/**
	 * Retorna todos los usuarios cuyo nombre contiene el nombre ingresado por
	 * parametro que no sean amigos del id ingresado.
	 * 
	 * @param id
	 * @param nombre
	 * @return
	 * @throws UsuarioExcepcion
	 */
	public List<UsuarioDTO> getUsuarios(int id, String nombre)
			throws UsuarioExcepcion {

		return servicioUsuario.getUsuarios(id, nombre);
	}

	/**
	 * Crea una solicitud de amistad entre usuarios del sistema
	 * 
	 * @param solicitud
	 *            Solicitud a crear en el sistema
	 * @throws UsuarioExcepcion
	 */
	public void crearSolicitudContacto(SolicitudContactoDTO solicitud)
			throws UsuarioExcepcion {

		servicioUsuario.crearSolicitudContacto(solicitud);
	}

	/**
	 * Metodo para aceptar una solicitud de amistad
	 * 
	 * @param solicitud Solicitud de amistad
	 * @param accion Accion de aceptar solicitud {@link Constantes}
	 * @throws UsuarioExcepcion
	 */
	public void responderSolicitudContacto(SolicitudContactoDTO solicitud,
			int accion) throws UsuarioExcepcion {

		servicioUsuario.responderSolicitudContacto(solicitud, accion);
	}

	/**
	 * Obtener todos los usuario con solicitudes enviadas pendientes de procesar
	 * con un identificador dado
	 * 
	 * @param id
	 *            Identificador de usuario
	 * @return Retorna la lista de usuario correspondiente
	 * @throws UsuarioExcepcion
	 */
	public List<UsuarioDTO> getSolicitudesEnviadasPendientes(int idUsuario)
			throws UsuarioExcepcion {

		return servicioUsuario.getSolicitudesEnviadasPendientes(idUsuario);
	}

	/**
	 * Obtener todos los usuario con solicitudes recibidas pendientes de
	 * procesar con un identificador dado
	 * 
	 * @param id
	 *            Identificador de usuario
	 * @return Retorna la lista de usuario correspondiente
	 * @throws UsuarioExcepcion
	 */
	public List<UsuarioDTO> getSolicitudesRecibidasPendientes(int idUsuario)
			throws UsuarioExcepcion {

		return servicioUsuario.getSolicitudesRecibidasPendientes(idUsuario);
	}

	/**
	 * Envia una invitación para unirse a la red de amigos a la dirección de
	 * mail ingresada por parametro
	 * 
	 * @param direccion
	 *            Email al que se le enviara la invitación
	 * @param nombre
	 *            Nombre del usuario
	 * @throws UsuarioExcepcion
	 */
	public void enviarInvitaciones(String direccion, String nombre)
			throws UsuarioExcepcion {

		servicioUsuario.enviarInvitaciones(direccion, nombre);
	}

	/**
	 * Actualiza la posicion geografica del usuario en la base de datos del
	 * sistema
	 * 
	 * @param posUsuario
	 *            Posicion geografica del usuario
	 * @throws UsuarioExcepcion
	 */
	public void actualizarPosicionUsuario(PosicionUsuarioDTO posUsuario)
			throws UsuarioExcepcion {

		servicioUsuario.actualizarPosicionUsuario(posUsuario);

	}

}
