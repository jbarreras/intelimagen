/*
 * Utilidades.java 
 *
 * Copyright (c) 2014 Jorge Barrera. All Rights Reserved.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */
package intelimagen.servidor;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.TIFFDecodeParam;
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

	public static boolean eliminarArchivo(File tmp) {
		boolean result = false;
		if (tmp.exists()) {
			result = tmp.delete();
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
	public static boolean guardarImagenPBM(String imagen, File imgTemp) {
		boolean result = false;
		try {
			InputStream entrada = new ByteArrayInputStream(
					Base64.decode(imagen));
			TIFFDecodeParam param = new TIFFDecodeParam();
			ImageDecoder decTiff = ImageCodec.createImageDecoder("TIFF",
					entrada, param);
			entrada.close();
			Raster imgInfo = decTiff.decodeAsRaster();
			OutputStream salida = new FileOutputStream(imgTemp);
			salida.write("P1\n".getBytes());
			salida.write((imgInfo.getWidth() + " " + imgInfo.getHeight() + "\n")
					.getBytes());
			for (int height = 0; height < imgInfo.getHeight(); height++) {
				for (int width = 0; width < imgInfo.getWidth(); width++) {
					int[] pixel = null;
					pixel = imgInfo.getPixel(width, height, pixel);
					salida.write(((((int) pixel[0]) == 1 ? 1 : 0) + " ")
							.getBytes());
				}
				salida.write('\n');
			}
			salida.close();
			result = imgTemp.exists();
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
	 * @return
	 */
	public static boolean guardarImagenTIFF(String imagen, File imgTemp) {
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
}
