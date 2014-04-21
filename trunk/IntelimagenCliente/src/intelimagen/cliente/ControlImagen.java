/*
 * ControlImagen.java 
 *
 * Copyright (c) 2014 Jorge Barrera. All Rights Reserved.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */
package intelimagen.cliente;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.media.jai.InterpolationBilinear;
import javax.media.jai.InterpolationNearest;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;
import javax.swing.JOptionPane;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.widget.DisplayJAI;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * Contiene el control grafico que permite cargar una imagen y visualizarla,
 * además suministra la funcionalidad para realizar transformaciones y obtener
 * segmentos de la imagen
 * 
 * @author Jorge Barrera
 * @version 1.0 4/03/2014
 * @updated 29-mar.-2014 2:13:28 p. m.
 */
@SuppressWarnings("serial")
public class ControlImagen extends DisplayJAI {
	private int alto = 0;
	private int ancho = 0;
	private boolean imagen = false;
	private ImageDecoder imgDecoder = null;
	private Component padre = null;
	private int pagina = 1;
	private int rotacion = 0;
	private boolean seleccion = true;
	private int ultimaPagina = 0;
	private int x = 0;
	private int x1 = 0;
	private int x2 = 0;
	private int y = 0;
	private int y1 = 0;
	private int y2 = 0;
	private int zoom = 100;

	/**
	 * Constructor de la clase que inicializa el visor
	 * 
	 * @param padre
	 *            Control de jerarquía superior que utiliza la visualización de
	 *            imágenes
	 * @wbp.parser.constructor
	 */
	public ControlImagen(Component padre) {
		inicializar(padre);
	}

	/**
	 * Constructor de la clase que inicializa el visor precargando una imagen
	 * 
	 * @param padre
	 *            Control de jerarquía superior que utiliza la visualización de
	 *            imágenes
	 * @param url
	 *            Ruta de la imagen a la que se requiere hacer tratamiento
	 */
	public ControlImagen(Component padre, String url) {
		inicializar(padre);
		cargarImagen(url);
	}

	/**
	 * Permite rotar una imagen cargada
	 * 
	 * @param angulo
	 *            Angulo de rotación que se quiere aplicar a la imagen
	 */
	public void aplicarRotacion(int angulo) {
		try {
			if (imagen) {
				this.rotacion += (angulo);
				ParameterBlock params = new ParameterBlock();
				params.addSource(imgDecoder.decodeAsRenderedImage(pagina - 1));
				params.add(imgDecoder.decodeAsRenderedImage(pagina - 1)
						.getWidth() / 2f);
				params.add(imgDecoder.decodeAsRenderedImage(pagina - 1)
						.getHeight() / 2f);
				params.add((float) Math.toRadians(rotacion));
				params.add(new InterpolationBilinear());
				set(JAI.create("rotate", params));
				repaint();
			} else {
				mostrarMensaje("No hay imagenes cargadas");
			}
		} catch (IOException ex) {
			mostrarMensaje("Se presentó un problema al aplicar rotación");
		}
	}

	/**
	 * Permite aumentar o disminuir de tamaño de la imagen cargada
	 * 
	 * @param escala
	 *            Nivel de escala que se requiere aplicar a la imagen
	 */
	public void aplicarZoom(int escala) {
		try {
			if (imagen) {
				if ((this.zoom + (escala)) > 0) {
					this.zoom += (escala);
					ParameterBlock params = new ParameterBlock();
					params.addSource(imgDecoder
							.decodeAsRenderedImage(pagina - 1));
					params.add((float) ((float) zoom / 100.0));
					params.add((float) ((float) zoom / 100.0));
					params.add(0.0F);
					params.add(0.0F);
					params.add(new InterpolationNearest());
					set(JAI.create("scale", params));
					repaint();
				}
			} else {
				mostrarMensaje("No hay imagenes cargadas");
			}
		} catch (IOException ex) {
			mostrarMensaje("Se presentó un problema al aplicar zoom");
		}
	}

	/**
	 * Permite explorar las páginas de una imagen MultiTIFF
	 * 
	 * @param pagina
	 *            Página del archivo multitiff que quiere cargarse en el visor
	 */
	public void cambiarPagina(int pagina) {
		try {
			if (imagen) {
				if ((this.pagina + pagina) <= ultimaPagina
						&& (this.pagina + pagina) > 0) {
					this.pagina += pagina;
					set(imgDecoder.decodeAsRenderedImage(this.pagina - 1));
					repaint();
				}
			} else {
				mostrarMensaje("No hay imagenes cargadas");
			}
		} catch (IOException ex) {
			mostrarMensaje("Se presentó un problema al explorar");
		}
	}

	/**
	 * Permite ir a la primera página de una imagen MultiTIFF
	 */
	public void cambiarPrimeraPagina() {
		try {
			if (imagen) {
				this.pagina = 1;
				set(imgDecoder.decodeAsRenderedImage(this.pagina - 1));
				repaint();
			} else {
				mostrarMensaje("No hay imagenes cargadas");
			}
		} catch (IOException ex) {
			mostrarMensaje("Se presentó un problema al explorar");
		}
	}

	/**
	 * Permite ir a la última página de una imagen MultiTIFF
	 */
	public void cambiarUltimaPagina() {
		try {
			if (imagen) {
				this.pagina = this.ultimaPagina;
				set(imgDecoder.decodeAsRenderedImage(this.pagina - 1));
				repaint();
			} else {
				mostrarMensaje("No hay imagenes cargadas");
			}
		} catch (IOException ex) {
			mostrarMensaje("Se presentó un problema al explorar");
		}
	}

	/**
	 * Permite cargar una imagen al control de visualización
	 * 
	 * @param url
	 *            Ruta de la imagen a la que se requiere hacer tratamiento
	 */
	public void cargarImagen(String url) {
		try {
			this.setToolTipText(url);
			InputStream stream = null;
			try {
				URL imgUrl = new URL(url);
				stream = imgUrl.openStream();
			} catch (Exception ex1) {
				try {
					stream = new FileInputStream(url);
				} catch (FileNotFoundException ex2) {
					mostrarMensaje("Se presentó un problema cargando la imagen");
				}
			}
			this.seleccion = true;
			this.imagen = true;
			imgDecoder = ImageCodec.createImageDecoder("tiff", stream, null);
			ultimaPagina = imgDecoder.getNumPages();
			set(imgDecoder.decodeAsRenderedImage(pagina - 1));
			repaint();
		} catch (IOException ex3) {
			mostrarMensaje("Se presentó un problema cargando la imagen");
		}
	}

	/**
	 * Permite obtener la página vizualizada
	 */
	public int obtenerPagina() {
		return this.pagina;
	}

	/**
	 * Permite obtener el atributo rotación
	 */
	public int obtenerRotacion() {
		return this.rotacion;
	}

	/**
	 * Permite obtener la información de un segmento de imagen seleccionado
	 */
	public String obtenerSeccion() {
		String cadenaImagen = "";
		try {
			if (imagen) {
				BufferedImage bufferedImage = null;
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				ParameterBlock paramRecorte = new ParameterBlock();
				paramRecorte.addSource(super.source);
				paramRecorte.add((float) x);
				paramRecorte.add((float) y);
				paramRecorte.add((float) ancho);
				paramRecorte.add((float) alto);
				RenderedOp cropImage = JAI.create("crop", paramRecorte);
				bufferedImage = cropImage.getAsBufferedImage();
				ImageIO.write(bufferedImage, "TIFF", output);
				output.flush();
				cadenaImagen = Base64.encode(output.toByteArray());
				output.close();
			} else {
				mostrarMensaje("No hay imagenes cargadas");
			}
		} catch (Exception ex) {
			mostrarMensaje("Se presentó un problema al obtener sección");
		}
		return cadenaImagen;
	}

	/**
	 * Permite obtener la información de un segmento de imagen seleccionado
	 * 
	 * @param ruta
	 *            Ruta donde se requiere registrar el segmento de la imagen
	 */
	public void obtenerSeccion(String ruta) {
		try {
			if (imagen) {
				BufferedImage bufferedImage = null;
				BufferedOutputStream output = null;
				ParameterBlock paramRecorte = new ParameterBlock();
				paramRecorte.addSource(super.source);
				paramRecorte.add((float) x);
				paramRecorte.add((float) y);
				paramRecorte.add((float) ancho);
				paramRecorte.add((float) alto);
				RenderedOp cropImage = JAI.create("crop", paramRecorte);
				File file = new File(ruta);
				if (file.exists())
					file.delete();
				bufferedImage = cropImage.getAsBufferedImage();
				output = new BufferedOutputStream(new FileOutputStream(file));
				ImageIO.write(bufferedImage, "TIFF", output);
				output.flush();
				output.close();
			} else {
				mostrarMensaje("No hay imagenes cargadas");
			}
		} catch (Exception ex) {
			mostrarMensaje("Se presentó un problema al obtener sección");
		}
	}

	/**
	 * Permite obtener las página que se pueden vizualizar
	 */
	public int obtenerUltimaPagina() {
		return this.ultimaPagina;
	}

	/**
	 * Permite obtener el atributo zoom
	 */
	public int obtenerZoom() {
		return this.zoom;
	}

	/**
	 * Renderiza o muestra el control en pantalla
	 * 
	 * @param g
	 *            Referencias que se requieres para renderizar el control
	 */
	public void paint(Graphics g) {
		super.paint(g);
		if (!seleccion) {
			if (imagen) {
				int w1 = this.x1 - this.x2;
				int h1 = this.y1 - this.y2;
				ancho = Math.abs(w1);
				alto = Math.abs(h1);
				x = w1 < 0 ? this.x1 : this.x2;
				y = h1 < 0 ? this.y1 : this.y2;
				Graphics2D g2d = (Graphics2D) g;
				Color bdColor = new Color(0, 0, 255);
				g2d.setColor(bdColor);
				// g2d.drawString(this.getUrl(), x2, y2);
				g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_SQUARE,
						BasicStroke.JOIN_BEVEL, 0, new float[] { 5 }, 0));
				g2d.drawRect(x, y, ancho, alto);
				Color bgColor = new Color(0, 0, 255, 25); // Red
				g2d.setPaint(bgColor);
				g2d.fillRect(x, y, ancho, alto);
			}
		}
	}

	/**
	 * Carga la información basica del control para su inicialización
	 * 
	 * @param padre
	 *            Control de jerarquía superior que utiliza la visualización de
	 *            imágenes
	 */
	private void inicializar(Component padre) {
		this.x1 = 0;
		this.y1 = 0;
		this.x2 = 0;
		this.y2 = 0;
		this.seleccion = true;
		this.ancho = 0;
		this.alto = 0;
		this.x = 0;
		this.y = 0;
		this.imgDecoder = null;
		this.zoom = 100;
		this.rotacion = 0;
		this.pagina = 1;
		this.imagen = false;
		this.padre = padre;
		this.setBackground(Color.LIGHT_GRAY);
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				x1 = e.getX();
				y1 = e.getY();
				repaint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// seleccion = true;
				// repaint();
			}
		});
		this.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
				x2 = e.getX();
				y2 = e.getY();
				seleccion = false;
				repaint();
			}

			public void mouseMoved(MouseEvent e) {
			}
		});
	}

	/**
	 * Muestra un mensaje en la pantalla
	 * 
	 * @param string
	 *            Mensaje que se quiere mostrar al usuario
	 */
	private void mostrarMensaje(String string) {
		JOptionPane.showMessageDialog(padre, string);
	}
}