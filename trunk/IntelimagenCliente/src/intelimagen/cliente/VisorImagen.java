/*
 * VisorImagen.java 
 *
 * Copyright (c) 2014 Jorge Barrera. All Rights Reserved.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */
package intelimagen.cliente;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * El Applet VisorImagen permite interactuar con una imagen MultiTIFF,
 * suministrando herramientas para su visualización, transformación y
 * Segmentación para realizar OCR
 * 
 * @author Jorge Barrera
 * @version 1.0 4/03/2014
 * @updated 29-mar.-2014 1:51:22 p. m.
 */
@SuppressWarnings("serial")
public class VisorImagen extends JApplet {
	private ControlImagen ctrlImg;
	private String imgEjm = "D:/Proyecto Grado/Fuentes/IntelimagenCliente/src/intelimagen/imagenes/000000100195178.tif";
	private JLabel lblConn;
	private JLabel lblPags;
	private JLabel lblRot;
	private JLabel lblTec;
	private JLabel lblZoom;
	private String rutaEjm = "D:/Proyecto Grado/Fuentes/IntelimagenCliente/src/intelimagen/imagenes/";
	private String tecOCR = "TESSERACT";
	private JTextPane txtOCR;
	private JTextField txtRuta;
	private ServicioOCRProxy wsOCR;
	private String wsRuta = "http://localhost:8080/IntelimagenServidor/services/ServicioOCR";

	/**
	 * Constructor de la clase que inicia la ejecución del Applet
	 */
	public VisorImagen() {
		this.getContentPane().setLayout(new BorderLayout(0, 0));
		this.setSize(1024, 768);
		wsOCR = new ServicioOCRProxy(wsRuta);
		ctrlImg = new ControlImagen(this);
		JToolBar mnuEncabezado = new JToolBar();
		mnuEncabezado.setToolTipText("Cuadro de herramientas");
		mnuEncabezado.setRollover(true);
		getContentPane().add(mnuEncabezado, BorderLayout.NORTH);
		txtRuta = new JTextField();
		txtRuta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cargarRutaImagen();
			}
		});
		txtRuta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarRutaImagen();
			}
		});
		txtRuta.setMinimumSize(new Dimension(400, 20));
		txtRuta.setMaximumSize(new Dimension(400, 20));
		txtRuta.setPreferredSize(new Dimension(400, 20));
		txtRuta.setToolTipText("Ruta Im\u00E1gen");
		txtRuta.setText(imgEjm);
		mnuEncabezado.add(txtRuta);
		JButton btnBuscar = new JButton("");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlImg.cargarImagen(txtRuta.getText());
				mostrarEstadoImagen();
			}
		});
		mnuEncabezado.add(btnBuscar);
		btnBuscar.setIcon(new ImageIcon(VisorImagen.class
				.getResource("/intelimagen/imagenes/world_go.png")));
		btnBuscar.setToolTipText("Buscar im\u00E1gen");
		JSeparator sepBuscar = new JSeparator();
		sepBuscar.setForeground(Color.GRAY);
		sepBuscar.setOrientation(SwingConstants.VERTICAL);
		mnuEncabezado.add(sepBuscar);
		JButton btnZoomMax = new JButton("");
		btnZoomMax.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlImg.aplicarZoom(10);
				mostrarEstadoImagen();
			}
		});
		btnZoomMax.setToolTipText("Aumentar Zoom Im\u00E1gen");
		btnZoomMax.setIcon(new ImageIcon(VisorImagen.class
				.getResource("/intelimagen/imagenes/magnifier_zoom_in.png")));
		mnuEncabezado.add(btnZoomMax);
		JButton btnZoomMin = new JButton("");
		btnZoomMin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlImg.aplicarZoom(-10);
				mostrarEstadoImagen();
			}
		});
		btnZoomMin.setToolTipText("Disminuir Zoom  Im\u00E1gen");
		btnZoomMin.setIcon(new ImageIcon(VisorImagen.class
				.getResource("/intelimagen/imagenes/magifier_zoom_out.png")));
		mnuEncabezado.add(btnZoomMin);
		lblZoom = new JLabel(ctrlImg.obtenerZoom() + "%");
		lblZoom.setHorizontalAlignment(SwingConstants.CENTER);
		lblZoom.setMinimumSize(new Dimension(35, 14));
		lblZoom.setMaximumSize(new Dimension(35, 14));
		lblZoom.setPreferredSize(new Dimension(35, 14));
		lblZoom.setToolTipText("Nivel Zoom Im\u00E1gen");
		mnuEncabezado.add(lblZoom);
		JSeparator sepZoom = new JSeparator();
		sepZoom.setForeground(Color.GRAY);
		sepZoom.setOrientation(SwingConstants.VERTICAL);
		mnuEncabezado.add(sepZoom);
		JButton btnRotIzq = new JButton("");
		btnRotIzq.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlImg.aplicarRotacion(-90);
				mostrarEstadoImagen();
			}
		});
		mnuEncabezado.add(btnRotIzq);
		btnRotIzq
				.setIcon(new ImageIcon(
						VisorImagen.class
								.getResource("/intelimagen/imagenes/shape_rotate_anticlockwise.png")));
		btnRotIzq.setToolTipText("Rotar Izquierda Im\u00E1gen");
		JButton btnRotDer = new JButton("");
		btnRotDer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlImg.aplicarRotacion(90);
				mostrarEstadoImagen();
			}
		});
		mnuEncabezado.add(btnRotDer);
		btnRotDer
				.setIcon(new ImageIcon(
						VisorImagen.class
								.getResource("/intelimagen/imagenes/shape_rotate_clockwise.png")));
		btnRotDer.setToolTipText("Rotar Derecha Im\u00E1gen");
		lblRot = new JLabel(ctrlImg.obtenerRotacion() + "\u00B0");
		lblRot.setHorizontalAlignment(SwingConstants.CENTER);
		lblRot.setMaximumSize(new Dimension(35, 14));
		lblRot.setMinimumSize(new Dimension(35, 14));
		lblRot.setPreferredSize(new Dimension(35, 14));
		lblRot.setToolTipText("Grados Rotaci\u00F3n Im\u00E1gen");
		mnuEncabezado.add(lblRot);
		JSeparator sepRot = new JSeparator();
		sepRot.setForeground(Color.GRAY);
		sepRot.setOrientation(SwingConstants.VERTICAL);
		mnuEncabezado.add(sepRot);
		JButton btnPagPrim = new JButton("");
		btnPagPrim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlImg.cambiarPrimeraPagina();
				mostrarEstadoImagen();
			}
		});
		btnPagPrim.setToolTipText("Primera P\u00E1gina");
		btnPagPrim.setIcon(new ImageIcon(VisorImagen.class
				.getResource("/intelimagen/imagenes/resultset_first.png")));
		mnuEncabezado.add(btnPagPrim);
		JButton btnPagAnt = new JButton("");
		btnPagAnt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlImg.cambiarPagina(-1);
				mostrarEstadoImagen();
			}
		});
		btnPagAnt.setIcon(new ImageIcon(VisorImagen.class
				.getResource("/intelimagen/imagenes/resultset_previous.png")));
		btnPagAnt.setToolTipText("P\u00E1gina Anterior");
		mnuEncabezado.add(btnPagAnt);
		JButton btnPagSig = new JButton("");
		btnPagSig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlImg.cambiarPagina(1);
				mostrarEstadoImagen();
			}
		});
		btnPagSig.setIcon(new ImageIcon(VisorImagen.class
				.getResource("/intelimagen/imagenes/resultset_next.png")));
		btnPagSig.setToolTipText("P\u00E1gina Siguiente");
		mnuEncabezado.add(btnPagSig);
		JButton btnPagUlt = new JButton("");
		btnPagUlt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlImg.cambiarUltimaPagina();
				mostrarEstadoImagen();
			}
		});
		btnPagUlt.setToolTipText("\u00DAltima P\u00E1gina");
		btnPagUlt.setIcon(new ImageIcon(VisorImagen.class
				.getResource("/intelimagen/imagenes/resultset_last.png")));
		mnuEncabezado.add(btnPagUlt);
		lblPags = new JLabel(ctrlImg.obtenerPagina() + " de "
				+ ctrlImg.obtenerUltimaPagina());
		lblPags.setHorizontalAlignment(SwingConstants.CENTER);
		lblPags.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblPags.setMinimumSize(new Dimension(60, 14));
		lblPags.setMaximumSize(new Dimension(60, 14));
		lblPags.setPreferredSize(new Dimension(60, 14));
		mnuEncabezado.add(lblPags);
		lblPags.setToolTipText("P\u00E1ginas Im\u00E1gen");
		JSeparator sepPag = new JSeparator();
		sepPag.setForeground(Color.GRAY);
		sepPag.setOrientation(SwingConstants.VERTICAL);
		mnuEncabezado.add(sepPag);
		JButton btnOCR = new JButton("");
		btnOCR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String entrada = "entrada";
				String resultado = "";
				try {
					entrada = new String(ctrlImg.obtenerSeccion());
					resultado = wsOCR.reconocerImagen(entrada, tecOCR);
					byte[] bytes = Base64.decode(resultado);
					resultado = new String(bytes, "UTF-8");
				} catch (Exception ex) {
					resultado = ex.getMessage();
				}
				txtOCR.setText(resultado);
				mostrarEstadoImagen();
			}
		});
		mnuEncabezado.add(btnOCR);
		btnOCR.setIcon(new ImageIcon(VisorImagen.class
				.getResource("/intelimagen/imagenes/wand.png")));
		btnOCR.setToolTipText("OCR Secci\u00F3n Im\u00E1gen");
		JSplitPane splitImg = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitImg.setResizeWeight(0.9);
		splitImg.setOneTouchExpandable(true);
		splitImg.setContinuousLayout(true);
		getContentPane().add(splitImg, BorderLayout.CENTER);
		JScrollPane scrollImg = new JScrollPane(ctrlImg);
		splitImg.setLeftComponent(scrollImg);
		txtOCR = new JTextPane();
		txtOCR.setText("...");
		splitImg.setRightComponent(txtOCR);
		JToolBar mnuPiePagina = new JToolBar();
		getContentPane().add(mnuPiePagina, BorderLayout.SOUTH);
		JSeparator sepTec = new JSeparator();
		mnuPiePagina.add(sepTec);
		sepTec.setOrientation(SwingConstants.VERTICAL);
		JButton btnTec = new JButton("");
		btnTec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object[] optTecOCR = { "TESSERACT", "GOCR" };
				Object selTecOCR = JOptionPane.showInputDialog(null,
						"Seleccione una tecnología OCR", "Tecnología OCR",
						JOptionPane.INFORMATION_MESSAGE, null, optTecOCR,
						optTecOCR[0]);
				tecOCR = selTecOCR.toString();
				mostrarEstadoServicio();
			}
		});
		mnuPiePagina.add(btnTec);
		btnTec.setIcon(new ImageIcon(VisorImagen.class
				.getResource("/intelimagen/imagenes/brick.png")));
		btnTec.setToolTipText("Tecnolog\u00EDa OCR");
		lblTec = new JLabel(tecOCR);
		lblTec.setHorizontalAlignment(SwingConstants.CENTER);
		lblTec.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblTec.setMinimumSize(new Dimension(100, 14));
		lblTec.setMaximumSize(new Dimension(100, 14));
		lblTec.setPreferredSize(new Dimension(100, 14));
		lblTec.setToolTipText("Tecnolog\u00EDa OCR");
		mnuPiePagina.add(lblTec);
		lblTec.setLabelFor(btnTec);
		JSeparator sepConn = new JSeparator();
		sepConn.setOrientation(SwingConstants.VERTICAL);
		mnuPiePagina.add(sepConn);
		JButton btnConn = new JButton("");
		mnuPiePagina.add(btnConn);
		btnConn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String wsRutaAux = wsRuta;
				wsRuta = JOptionPane.showInputDialog(null,
						"Ingrese la ruta del servicio OCR", wsRutaAux);

				if (wsRuta.equalsIgnoreCase("")) {
					wsRuta = wsRutaAux;
				}

				wsOCR = new ServicioOCRProxy(wsRuta);
				try {
					if (wsOCR.test(tecOCR)) {
						JOptionPane.showMessageDialog(null,
								"Conectado satisfactoriamente " + wsRuta);
					} else {
						JOptionPane.showMessageDialog(null,
								"No se puede conectar a " + wsRuta);
						wsRuta = wsRutaAux;
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(
							null,
							"No se puede conectar a " + wsRuta + ", "
									+ ex.getMessage());
					wsRuta = wsRutaAux;
				}
				mostrarEstadoServicio();
			}
		});
		btnConn.setToolTipText("Conexi\u00F3n OCR");
		btnConn.setIcon(new ImageIcon(VisorImagen.class
				.getResource("/intelimagen/imagenes/disconnect.png")));
		lblConn = new JLabel(wsRuta);
		lblConn.setHorizontalAlignment(SwingConstants.CENTER);
		lblConn.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblConn.setMinimumSize(new Dimension(400, 14));
		lblConn.setMaximumSize(new Dimension(400, 14));
		lblConn.setPreferredSize(new Dimension(400, 14));
		lblConn.setToolTipText("Conexi\u00F3n OCR");
		mnuPiePagina.add(lblConn);
		lblConn.setLabelFor(btnConn);
		JSeparator sepAyuda = new JSeparator();
		sepAyuda.setOrientation(SwingConstants.VERTICAL);
		mnuPiePagina.add(sepAyuda);
		JButton btnAyuda = new JButton("");
		mnuPiePagina.add(btnAyuda);
		btnAyuda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,
						"Jorge Barrera\nUniversidad Central\n2014");
			}
		});
		btnAyuda.setIcon(new ImageIcon(VisorImagen.class
				.getResource("/intelimagen/imagenes/help.png")));
		btnAyuda.setToolTipText("Ayuda Im\u00E1gen");
	}

	/**
	 * Permite seleccionar una imagen para cargar en el visor
	 */
	private void cargarRutaImagen() {
		JFileChooser seleccion = new JFileChooser();
		FileNameExtensionFilter filtro = new FileNameExtensionFilter(
				"Imagenes TIF", "tif", "tiff");
		seleccion.setDialogType(JFileChooser.OPEN_DIALOG);
		seleccion.setFileSelectionMode(JFileChooser.FILES_ONLY);
		seleccion.setCurrentDirectory(new java.io.File(rutaEjm));
		seleccion.setFileFilter(filtro);
		seleccion.setToolTipText("Seleccione una imagen TIFF");
		if (seleccion.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			txtRuta.setText(seleccion.getSelectedFile().getAbsolutePath());
		}
	}

	/**
	 * Permite actualizar la descripción del estado de la imagen
	 */
	private void mostrarEstadoImagen() {
		lblZoom.setText(ctrlImg.obtenerZoom() + "%");
		lblRot.setText(ctrlImg.obtenerRotacion() + "\u00B0");
		lblPags.setText(ctrlImg.obtenerPagina() + " de "
				+ ctrlImg.obtenerUltimaPagina());
	}

	/**
	 * Permite actualizar la descripción del estado del servicio
	 */
	private void mostrarEstadoServicio() {
		lblTec.setText(tecOCR);
		lblConn.setText(wsRuta);
		wsOCR = new ServicioOCRProxy(wsRuta);
	}
}
