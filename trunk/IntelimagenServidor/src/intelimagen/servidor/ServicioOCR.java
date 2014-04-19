/*
 * ServicioOCR.java 
 *
 * Copyright (c) 2014 Jorge Barrera. All Rights Reserved.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */

package intelimagen.servidor;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

import javax.imageio.ImageIO;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

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
	 * 
	 * @return Texto reconocido
	 */
	public String reconocerImagen(String imagen, String serial) {

		String ocrResult = "";
		UUID tmp = UUID.randomUUID();

		try {

			File imgTemp = File.createTempFile(tmp.toString(), ".tiff");
			File txtTemp = null;

			if (guardarImagen(imagen, imgTemp)) {

				txtTemp = ejecutarOCR(imgTemp.getAbsolutePath());
				ocrResult = leerArchivoPlano(txtTemp);

				if (ocrResult == "") {
					ocrResult = "Servidor: No hay resultados del proceso de OCR";
				}

			} else {
				ocrResult = "Servidor: No se puede almacenar imagenes de forma temporal";
			}

		} catch (Exception ex) {
			imagen = ex.getMessage();
		}

		return ocrResult;
	}

	/**
	 * Permite comprobar que el servicio se encuentre activo
	 * 
	 * @param serial
	 *            Cadena que contiene la llave para la autorización al servicio
	 * 
	 * @return Servicio en linea
	 */
	public Boolean test(String serial) {
		return true;
	}

	/**
	 * Permite ejecutar un proceso del sistema
	 * 
	 * @param comando
	 *            Linea de comando a ejecutar
	 * @return Ejecución satisfactoria
	 */
	private boolean ejecutarProceso(String comando) {

		boolean result = false;

		try {
			StringBuffer ejecucion = new StringBuffer();
			Process proceso = Runtime.getRuntime().exec(comando);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					proceso.getInputStream()));

			String linea = "";
			while ((linea = reader.readLine()) != null) {
				ejecucion.append(linea + "\n");
			}

			result = (proceso.waitFor() == 0);

		} catch (Exception ex) {
			result = false;
		}

		return result;
	}

	/**
	 * Realiza la ejecución del proceso de OCR de tesseract
	 * 
	 * @param imgTemp
	 *            Imagen a reconocer por OCR
	 * @return Resultado de OCR
	 */
	private File ejecutarOCR(String imgTemp) {
		File result = null;

		if (ejecutarProceso("C:\\Program Files (x86)\\Tesseract-OCR\\tesseract.exe"
				+ " " + imgTemp + " " + imgTemp + " -l spa ")) {

			File txt = new File(imgTemp + ".txt");
			if (txt.exists()) {
				result = txt;
			}
		}

		return result;
	}

	/**
	 * Almacena una el contenido de una imagen en disco
	 * 
	 * @param imagen
	 *            Contenido de la imagen
	 * @param imgTemp
	 *            Archivo donde se almacena la imagen
	 * @return
	 */
	private boolean guardarImagen(String imagen, File imgTemp) {

		boolean result = false;
		try {
			byte[] bytearray = Base64.decode(imagen);
			BufferedImage img;
			img = ImageIO.read(new ByteArrayInputStream(bytearray));
			result = ImageIO.write(img, "TIFF", imgTemp);
		} catch (Exception ex) {
			result = false;
		}

		return result;
	}

	/**
	 * Permite realizar la lectura de un archivo plano
	 * 
	 * @param txtTemp
	 *            Archivo plano a leer
	 * @return Información del archivo
	 */
	private String leerArchivoPlano(File txtTemp) {
		String result = "";
		try {
			FileReader reader = new FileReader(txtTemp);
			char[] chars = new char[(int) txtTemp.length()];
			reader.read(chars);
			result = new String(chars);
			reader.close();
		} catch (IOException e) {
			result = "";
		}
		return result;
	}

}
