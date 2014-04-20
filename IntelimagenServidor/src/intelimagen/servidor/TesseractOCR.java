/*
 * TesseractOCR.java 
 *
 * Copyright (c) 2014 Jorge Barrera. All Rights Reserved.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */
package intelimagen.servidor;

import java.io.File;
import java.util.UUID;

/**
 * Implementa la forma de realizar el reconocimiento optico de caracteres por
 * Tesseract http://tesseract-ocr.googlecode.com/
 * 
 * @author Jorge Barrera
 * @version 1.0 17/03/2014
 * @updated 29-mar.-2014 6:03:53 p. m.
 */
public class TesseractOCR implements IOCR {
	private final String ejecutable = "C:\\Program Files (x86)\\Tesseract-OCR\\tesseract.exe {0} {1} -l spa";

	/*
	 * (non-Javadoc)
	 * 
	 * @see intelimagen.servidor.IOCR#reconocerImagen(java.lang.String)
	 */
	@Override
	public String reconocerImagen(String imagen) {
		String ocrResult = "";
		UUID tmp = UUID.randomUUID();
		try {
			File imgTemp = File.createTempFile(tmp.toString(), ".tiff");
			if (Utilidades.guardarImagenTIFF(imagen, imgTemp)) {
				if (Utilidades.ejecutarProceso(String.format(ejecutable,
						imgTemp.getAbsolutePath(), imgTemp.getAbsolutePath()))) {
					File txtTemp = new File(imgTemp + ".txt");
					ocrResult = Utilidades.leerArchivoPlano(txtTemp);
					if (ocrResult.length() > 0) {
						Utilidades.eliminarArchivo(txtTemp);
						Utilidades.eliminarArchivo(imgTemp);
					} else {
						ocrResult = "Servidor: No hay resultados del proceso "
								+ ejecutable;
					}
				} else {
					ocrResult = "Servidor: No se puede ejecutar proceso "
							+ ejecutable;
				}
			} else {
				ocrResult = "Servidor: No se puede almacenar imagenes de forma temporal";
			}
		} catch (Exception ex) {
			ocrResult = ex.getMessage();
		}
		return ocrResult;
	}
}
