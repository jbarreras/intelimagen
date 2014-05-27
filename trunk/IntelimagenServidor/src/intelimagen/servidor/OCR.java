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

	/**
	 * En diseño de software, el patrón de diseño Factory Method consiste en
	 * utilizar una clase constructora (al estilo del Abstract Factory)
	 * abstracta con unos cuantos métodos definidos y otro(s) abstracto(s): el
	 * dedicado a la construcción de objetos de un subtipo de un tipo
	 * determinado. Es una simplificación del Abstract Factory, en la que la
	 * clase abstracta tiene métodos concretos que usan algunos de los
	 * abstractos; según usemos una u otra hija de esta clase abstracta,
	 * tendremos uno u otro comportamiento.
	 * 
	 * @param serial
	 * @return Objeto OCR
	 */
	public static OCR fabrica(String serial) {

		switch (serial) {
		case "TESSERACT":
			return new TesseractOCR();
		case "GOCR":
			return new GocrOCR();
		default:
			return null;
		}
	}
}
