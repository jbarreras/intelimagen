/*
 * TipoOCR.java 
 *
 * Copyright (c) 2014 Jorge Barrera. All Rights Reserved.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */

package intelimagen.servidor;

/**
 * Tipos de herramientas OCR soportadas por el servicio
 * 
 * @author Jorge Barrera
 * @version 1.0 17/03/2014
 * @updated 29-mar.-2014 6:03:53 p. m.
 */
public enum TipoOCR {

	/**
	 * https://tesseract-ocr.googlecode.com/files/tesseract-ocr-setup-3.02.02.
	 * exe
	 */
	Tesseract,
	/**
	 * http://www-e.uni-magdeburg.de/jschulen/ocr/gocr049.exe
	 */
	Gocr
}
