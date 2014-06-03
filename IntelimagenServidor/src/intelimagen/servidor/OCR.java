/*
 * IOCR.java 
 *
 * Copyright (c) 2014 Jorge Barrera. All Rights Reserved.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */

package intelimagen.servidor;

/**
 * Clase abstracta que define la forma de realizar el reconocimiento optico de
 * caracteres
 * 
 * @author Jorge Barrera
 * @version 1.0 17/03/2014
 * @updated 29-mar.-2014 6:03:53 p. m.
 */
public abstract class OCR {

	/**
	 * Implementa el reconocimiento de caracteres de una imagen obteniendo el
	 * texto reconocible
	 * 
	 * @param imagen
	 *            Cadena de caracteres con bytes de la imagen a reconocer
	 * 
	 * @return Texto reconocido
	 */
	public abstract String reconocerImagen(String imagen);

}
