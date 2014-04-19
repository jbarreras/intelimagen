/*
 * VisorImagen.java 
 *
 * Copyright (c) 2014 Jorge Barrera. All Rights Reserved.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */

package intelimagen.cliente;

import intelimagen.servidor.ServicioOCRProxy;

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
	private JLabel lblPags;
	private JLabel lblRot;
	private JLabel lblZoom;
	private JTextPane txtOCR;
	private JTextField txtRuta;
	private ServicioOCRProxy wsOCR;

	/**
	 * Constructor de la clase que inicia la ejecución del Applet
	 */
	public VisorImagen() {

		this.getContentPane().setLayout(new BorderLayout(0, 0));
		this.setSize(800, 600);

		wsOCR = new ServicioOCRProxy(
				"http://localhost:8080/IntelimagenServidor/services/ServicioOCR");

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
		txtRuta.setText("D:/Proyecto Grado/Fuentes/IntelimagenCliente/src/intelimagen/imagenes/000000100195178.tif");
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
					resultado = wsOCR.reconocerImagen(entrada, "test");
				} catch (Exception ex) {
					resultado = ex.getMessage();
				}

				txtOCR.setText(resultado);
				// JOptionPane.showMessageDialog(null, resultado);
				mostrarEstadoImagen();
			}
		});
		mnuEncabezado.add(btnOCR);
		btnOCR.setIcon(new ImageIcon(VisorImagen.class
				.getResource("/intelimagen/imagenes/wand.png")));
		btnOCR.setToolTipText("OCR Secci\u00F3n Im\u00E1gen");

		JButton btnAyuda = new JButton("");
		btnAyuda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,
						"Jorge Barrera\nUniversidad Central\n2014");
			}
		});

		JButton btnConn = new JButton("");
		mnuEncabezado.add(btnConn);
		btnConn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		btnConn.setToolTipText("Conexi\u00F3n OCR");
		btnConn.setIcon(new ImageIcon(VisorImagen.class
				.getResource("/intelimagen/imagenes/disconnect.png")));
		mnuEncabezado.add(btnAyuda);
		btnAyuda.setIcon(new ImageIcon(VisorImagen.class
				.getResource("/intelimagen/imagenes/help.png")));
		btnAyuda.setToolTipText("Ayuda Im\u00E1gen");

		JSplitPane splitImg = new JSplitPane();
		splitImg.setDividerLocation((int) this.getWidth());
		getContentPane().add(splitImg, BorderLayout.CENTER);

		JScrollPane scrollImg = new JScrollPane(ctrlImg);
		splitImg.setLeftComponent(scrollImg);

		txtOCR = new JTextPane();
		txtOCR.setText("...");
		splitImg.setRightComponent(txtOCR);

		JToolBar mnuPiePagina = new JToolBar();
		getContentPane().add(mnuPiePagina, BorderLayout.SOUTH);

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
		seleccion
				.setCurrentDirectory(new java.io.File(
						"D:/Proyecto Grado/Fuentes/IntelimagenCliente/src/intelimagen/imagenes/"));
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
}
