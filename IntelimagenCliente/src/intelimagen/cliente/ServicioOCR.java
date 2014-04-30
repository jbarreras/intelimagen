/**
 * ServicioOCR.java
 *
 * Copyright (c) 2014 Jorge Barrera. All Rights Reserved.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */

package intelimagen.cliente;

/**
 * Interfaz que define los mecanismos para invocar de manera remota el
 * reconocimiento de caracteres en im�genes
 * 
 * @author Jorge Barrera
 * @version 1.0 24/03/2014
 * @updated 29-mar.-2014 8:03:15 p. m.
 */
public interface ServicioOCR extends java.rmi.Remote {

	/**
	 * Implementa el reconocimiento de caracteres de una imagen obteniendo el
	 * texto reconocible
	 * 
	 * @param imagen
	 *            Cadena de caracteres con bytes de la imagen a reconocer
	 * @param serial
	 *            Cadena que contiene la llave para la autorizaci�n al servicio
	 * 
	 * @return Texto reconocido
	 */
	public java.lang.String reconocerImagen(java.lang.String imagen,
			java.lang.String serial) throws java.rmi.RemoteException;

	/**
	 * Permite comprobar que el servicio se encuentre activo
	 * 
	 * @param serial
	 *            Cadena que contiene la llave para la autorizaci�n al servicio
	 * 
	 * @return Servicio en linea
	 */
	public boolean test(java.lang.String serial)
			throws java.rmi.RemoteException;
}
