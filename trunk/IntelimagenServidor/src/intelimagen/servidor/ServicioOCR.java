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
 * en imágenes
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
	 *            Cadena que contiene la llave para la autorización al servicio
	 *            TESSERACT GOCR
	 * 
	 * @return Texto reconocido
	 */
	public String reconocerImagen(String imagen, String serial) {

		CreadorOCR creador = null;

		switch (serial) {
		case "TESSERACT":
			creador = new CreadorTesseractOCR();
			break;
		case "GOCR":
			creador = new CreadorGocrOCR();
			break;
		}

		OCR OCR = creador.fabrica(serial);
		return Utilidades.cadenaBase64(OCR.reconocerImagen(imagen));
	}

	/**
	 * Permite comprobar que el servicio se encuentre activo
	 * 
	 * @param serial
	 *            Cadena que contiene la llave para la autorización al servicio
	 * 
	 * @return Servicio en linea
	 */
	public boolean test(String serial) {
		boolean result = ((serial.equals("TESSERACT")) || (serial
				.equals("GOCR")));
		return result;
	}
}
