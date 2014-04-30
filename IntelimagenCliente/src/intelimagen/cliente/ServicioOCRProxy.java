/*
 * ServicioOCRProxy.java 
 *
 * Copyright (c) 2014 Jorge Barrera. All Rights Reserved.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */

package intelimagen.cliente;

/**
 * Clase que permite consumumir la funcionalidad que presta el servicio de OCR
 * 
 * @author Jorge
 * @version 1.0 24/03/2014
 * @updated 29-mar.-2014 6:34:33 p. m.
 */
public class ServicioOCRProxy implements intelimagen.cliente.ServicioOCR {
	private String _endpoint = null;
	private intelimagen.cliente.ServicioOCR servicioOCR = null;

	/**
	 * Constructor de la clase
	 */
	public ServicioOCRProxy() {
		_initServicioOCRProxy();
	}

	/**
	 * Contructor de la clase
	 * 
	 * @param endpoint
	 *            Ruta del servicio web (wsdl)
	 */
	public ServicioOCRProxy(String endpoint) {
		_endpoint = endpoint;
		_initServicioOCRProxy();
	}

	/**
	 * Obteniene la ruta de acceso al servicio de OCR
	 * 
	 * @return Ruta del servicio web (wsdl)
	 */
	public String getEndpoint() {
		return _endpoint;
	}

	/**
	 * Obtiene una instancia del servicio para realizar OCR
	 * 
	 * @return Servicio OCR
	 */
	public intelimagen.cliente.ServicioOCR getServicioOCR() {
		if (servicioOCR == null)
			_initServicioOCRProxy();
		return servicioOCR;
	}

	/**
	 * Implementa el reconocimiento de caracteres de una imagen obteniendo el
	 * texto reconocible
	 * 
	 * @param imagen
	 *            Cadena de caracteres con bytes de la imagen a reconocer
	 * @param serial
	 *            Cadena que contiene la llave para la autorización al servicio
	 * 
	 * @return Texto reconocido
	 */
	public java.lang.String reconocerImagen(java.lang.String imagen,
			java.lang.String serial) throws java.rmi.RemoteException {
		if (servicioOCR == null)
			_initServicioOCRProxy();
		return servicioOCR.reconocerImagen(imagen, serial);
	}

	/**
	 * Coloca la ruta de acceso al servicio de OCR
	 * 
	 * @param endpoint
	 *            Ruta del servicio web (wsdl)
	 */
	public void setEndpoint(String endpoint) {
		_endpoint = endpoint;
		if (servicioOCR != null)
			((javax.xml.rpc.Stub) servicioOCR)._setProperty(
					"javax.xml.rpc.service.endpoint.address", _endpoint);

	}

	/**
	 * Permite comprobar que el servicio se encuentre activo
	 * 
	 * @param serial
	 *            Cadena que contiene la llave para la autorización al servicio
	 * 
	 * @return Servicio en linea
	 */
	public boolean test(java.lang.String serial)
			throws java.rmi.RemoteException {
		if (servicioOCR == null)
			_initServicioOCRProxy();
		return servicioOCR.test(serial);
	}

	/**
	 * Inicializa la el cliente del servicio de OCR
	 */
	private void _initServicioOCRProxy() {
		try {
			servicioOCR = (new intelimagen.cliente.ServicioOCRServiceLocator())
					.getServicioOCR();
			if (servicioOCR != null) {
				if (_endpoint != null)
					((javax.xml.rpc.Stub) servicioOCR)
							._setProperty(
									"javax.xml.rpc.service.endpoint.address",
									_endpoint);
				else
					_endpoint = (String) ((javax.xml.rpc.Stub) servicioOCR)
							._getProperty("javax.xml.rpc.service.endpoint.address");
			}

		} catch (javax.xml.rpc.ServiceException serviceException) {
		}
	}

}