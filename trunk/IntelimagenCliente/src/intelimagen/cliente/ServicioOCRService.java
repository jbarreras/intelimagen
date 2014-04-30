/**
 * ServicioOCRService.java
 *
 * Copyright (c) 2014 Jorge Barrera. All Rights Reserved.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */

package intelimagen.cliente;

/**
 * Interfaz que define la funcionalidad de los servicios necesarios para
 * realizar el reconocimiento de caracteres en im�genes
 * 
 * @author Jorge Barrera
 * @version 1.0 24/03/2014
 * @updated 29-mar.-2014 8:26:43 p. m.
 */
public interface ServicioOCRService extends javax.xml.rpc.Service {

	/**
	 * Obtiene una instancia del servicio para realizar OCR
	 * 
	 * @return Servicio OCR
	 * @throws javax.xml.rpc.ServiceException
	 */
	public intelimagen.cliente.ServicioOCR getServicioOCR()
			throws javax.xml.rpc.ServiceException;

	/**
	 * Obtiene una instancia del servicio para realizar OCR
	 * 
	 * @param portAddress
	 *            Ruta del servicio OCR
	 * @return Servicio OCR
	 * @throws javax.xml.rpc.ServiceException
	 */
	public intelimagen.cliente.ServicioOCR getServicioOCR(
			java.net.URL portAddress) throws javax.xml.rpc.ServiceException;

	/**
	 * Obtiene la ruta del servicio para realizar OCR
	 * 
	 * @return Ruta del servicio OCR
	 */
	public java.lang.String getServicioOCRAddress();
}
