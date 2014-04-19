/*
 * ServicioOCR.java 
 *
 * Copyright (c) 2014 Jorge Barrera. All Rights Reserved.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */
package intelimagen.servidor;

/**
 * Presta los servicios necesarios para realizar el reconocimiento de caracteres
 * en im�genes
 * 
 * @author Jorge Barrera
 * @version 1.0 17/03/2014
 * @updated 29-mar.-2014 6:03:53 p. m.
 */
public class ServicioOCR {
	/**
	 * Implementa el reconocimiento de caracteres de una imagen obteniendo el
	 * texto reconocible
	 * 
	 * @param imagen
	 *            Cadena de caracteres con bytes de la imagen a reconocer
	 * @param serial
	 *            Cadena que contiene la llave para la autorizaci�n al servicio
	 *            936DA01F-9ABD-4d9d-80C7-02AF85C822A8
	 *            F9168C5E-CEB2-4faa-B6BF-329BF39FA1E4
	 * 
	 * @return Texto reconocido
	 */
	public String reconocerImagen(String imagen, String serial) {
		IOCR OCR = null;
		switch (serial) {
		case "936DA01F-9ABD-4d9d-80C7-02AF85C822A8":
			OCR = new TesseractOCR();
			break;
		case "F9168C5E-CEB2-4faa-B6BF-329BF39FA1E4":
			OCR = new GocrOCR();
			break;
		}
		return OCR.reconocerImagen(imagen);
	}

	/**
	 * Permite comprobar que el servicio se encuentre activo
	 * 
	 * @param serial
	 *            Cadena que contiene la llave para la autorizaci�n al servicio
	 * 
	 * @return Servicio en linea
	 */
	public Boolean test(String serial) {
		return true;
	}
}