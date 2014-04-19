/*
 * Utilidades.java 
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
import javax.imageio.ImageIO;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * Conjunto de utilidades utilizadas por los procesos de OCR
 * 
 * @author Jorge Barrera
 * @version 1.0 17/03/2014
 * @updated 29-mar.-2014 6:03:53 p. m.
 */
public class Utilidades {
	/**
	 * Permite ejecutar un proceso del sistema
	 * 
	 * @param comando
	 *            Linea de comando a ejecutar
	 * @return Ejecución satisfactoria
	 */
	public static boolean ejecutarProceso(String comando) {
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
	 * Almacena una el contenido de una imagen en disco
	 * 
	 * @param imagen
	 *            Contenido de la imagen
	 * @param imgTemp
	 *            Archivo donde se almacena la imagen
	 * @param formato
	 *            Tipo de formato de la imagen
	 * @return
	 */
	public static boolean guardarImagen(String imagen, File imgTemp,
			String formato) {
		boolean result = false;
		try {
			byte[] bytearray = Base64.decode(imagen);
			BufferedImage img = ImageIO
					.read(new ByteArrayInputStream(bytearray));
			result = ImageIO.write(img, formato, imgTemp);
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
	public static String leerArchivoPlano(File txtTemp) {
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

	public static boolean eliminarArchivo(File tmp) {
		boolean result = false;
		if (tmp.exists()) {
			result = tmp.delete();
		}
		return result;
	}
}
