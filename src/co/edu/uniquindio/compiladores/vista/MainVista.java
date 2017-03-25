/**
 * 
 */
package co.edu.uniquindio.compiladores.vista;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 * @author Cesar Taborda
 * @author Yeison Gomez
 * @author Rogers Cordoba
 *
 */
public class MainVista {
	/**
	 * Variable t que ayudara a obtener el tamaño de la pantalla
	 */
	private Dimension screenSize 	= Toolkit.getDefaultToolkit().getScreenSize();
	private final int heightScreen 	= screenSize.height;
	private final int widthScreen 	= screenSize.width;
	private final int heightWindow 	= heightScreen*7/8;
	private final int widthWindow 	= widthScreen*3/4;
	private final int positionX 	= (widthScreen-widthWindow)/2;
	private final int positionY 	= (heightScreen-heightWindow)/2;
	private final int heightMenuBar = heightWindow/25;
	private final int widthMenuBar 	= widthWindow;
	
	/**
	 * 
	 */
	private final int padding 		= heightWindow/43;
	private final int heightLabel 	= 30;
	
	/**
	 * 
	 */
	private final int heightPanelPpal 		= (heightWindow-heightMenuBar);
	private final int widthPanelPpal 		= widthWindow;
	private final int heightPanelCodigo 	= (heightPanelPpal*2/3);
	private final int widthPanelCodigo 		= (widthPanelPpal*2/3);
	private final int heightPanelesDerecho 	= (heightPanelPpal/3);
	private final int widthPanelesDerecho 	= (widthPanelPpal/3);
	private final int heightPanelErrores 	= (heightPanelPpal/3)-(padding*3);
	private final int widthPanelErrores 	= widthPanelPpal-(padding*2);
	
	private final int heightScrollDerecho 	= heightPanelesDerecho-(padding+(heightLabel*2));
	private final int widthScrollDerecho 	= widthPanelesDerecho-(padding*3);

	/**
	 * 
	 */
	final static String tituloCodigoFuente 	= "Compilador";
	final static String tituloTablaSimbolos = "Tabla de Simbolos";
	final static String tituloArbolDerivac 	= "Árbol de Derivación";
	final static String tituloErrores 		= "Errores Generados";
	
	final static String compiler 	= "Compilador";
	final static String sinCambios 	= "< No se han guardado los Cambios >";
	
	private JFrame 			frame;
	private JFileChooser 	jFileChooser;
	private JTextArea 		textArea1;
	private String 			nombreArchivo;
	private JTable 			tableSimbolos;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					MainVista window = new MainVista();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public MainVista() {
		
		initialize();
	}
	
	/**
	 * 
	 */
	public void crearVista() {
		// TODO Auto-generated method stub
		
		try {
			MainVista window = new MainVista();
			window.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds( positionX, positionY, widthWindow, heightWindow ); // frame.setBounds(50, 50, 900, 650);
		frame.setTitle("Compilador");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		/**
		 * Parte del menu
		 */
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, widthMenuBar, heightMenuBar);
		frame.getContentPane().add(menuBar);

		{
			JMenu menu1 = new JMenu();
			menu1.setText("Archivo");
			menuBar.add(menu1);

			// MenuItem para llamar el metodo de crear un nuevo archivo
			JMenuItem menuItem11 = new JMenuItem();
			menuItem11.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			menuItem11.setText("Nuevo Archivo");
			menu1.add(menuItem11);

			// MenuItem para llamar el metodo de abrir y cargar archivos
			JMenuItem menuItem12 = new JMenuItem();
			menuItem12.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CargarArchivo();
				}
			});
			menuItem12.setText("Abrir Archivo");
			menu1.add(menuItem12);

			// MenuItem para guardar los avances de un codigo creado
			JMenuItem menuItem13 = new JMenuItem();
			menuItem13.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			menuItem13.setText("Guardar");
			menu1.add(menuItem13);

			// MenuItem para guardar un archivo con otro nombre o en otro lugar
			JMenuItem menuItem14 = new JMenuItem();
			menuItem14.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			menuItem14.setText("Guardar como...");
			menu1.add(menuItem14);

			// MenuItem para salir del software
			JMenuItem menuItem15 = new JMenuItem();
			menuItem15.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Salir();
				}
			});
			menuItem15.setText("Salir");
			menu1.add(menuItem15);

			JMenu menu2 = new JMenu();
			menu2.setText("Mas");
			menuBar.add(menu2);

			// MenuItem para mostrar informacion de los creadores del software
			JMenuItem menuItem21 = new JMenuItem();
			menuItem21.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Acerca();
				}
			});
			menuItem21.setText("Acerca de...");
			menu2.add(menuItem21);
		}

		/**
		 * Parte del panel principal
		 */
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setBounds( 0, heightMenuBar, widthPanelPpal, heightPanelPpal );
		frame.getContentPane().add(panelPrincipal);
		panelPrincipal.setLayout(null);

		{
			/**
			 * Panel de Codigo Fuente
			 */
			JPanel panelCodigoFuente = new JPanel();
			panelCodigoFuente.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
			panelCodigoFuente.setBounds( padding, padding, (widthPanelCodigo-(padding*2)), (heightPanelCodigo-padding) );
			panelPrincipal.add(panelCodigoFuente);
			panelCodigoFuente.setLayout(null);

			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds( padding, padding, (widthPanelCodigo-(padding*4)), (heightPanelCodigo*3/4) );
			panelCodigoFuente.add(scrollPane);

			textArea1 = new JTextArea();
			textArea1.setFont(new Font("Consolas", Font.PLAIN, 13));
			TextLineNumber tlnCodFuente = new TextLineNumber(textArea1);
			scrollPane.setViewportView(textArea1);
			scrollPane.setRowHeaderView(tlnCodFuente);

			Label lblcodfuente = new Label();
			lblcodfuente.setAlignment(Label.CENTER);
			lblcodfuente.setText(sinCambios);
			lblcodfuente.setBounds( padding, (heightPanelCodigo*5/6), (widthPanelCodigo/3), heightLabel);
			panelCodigoFuente.add(lblcodfuente);

			/**
			 * Boton para Compilar...
			 */
			JButton btnCompilar = new JButton("COMPILAR");
			btnCompilar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CompilarCodFuente();
				}
			});
			btnCompilar.setBounds( (widthPanelCodigo*2/3), ((heightPanelCodigo*3/4)+(padding*2)), ((widthPanelCodigo/4)-padding), heightLabel);
			panelCodigoFuente.add(btnCompilar);

			/**
			 * Panel de Tabla de Simbolos
			 */
			JPanel panelTablaSimb = new JPanel();
			panelTablaSimb.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
			panelTablaSimb.setBounds( widthPanelCodigo, padding, (widthPanelesDerecho-padding), (heightPanelesDerecho-padding) );
			panelPrincipal.add(panelTablaSimb);
			panelTablaSimb.setLayout(null);

			Label lblTablaSimb = new Label();
			lblTablaSimb.setFont(new Font("Tahoma", Font.BOLD, 20));
			lblTablaSimb.setAlignment(Label.CENTER);
			lblTablaSimb.setSize( (widthPanelesDerecho-(padding*4)), heightLabel);
			lblTablaSimb.setLocation(padding, padding);
			lblTablaSimb.setText( tituloTablaSimbolos );
			panelTablaSimb.add(lblTablaSimb);

			JScrollPane scrollPaneTablaSimb = new JScrollPane();
			scrollPaneTablaSimb.setBounds( padding, (padding+heightLabel), widthScrollDerecho, heightScrollDerecho );
			panelTablaSimb.add(scrollPaneTablaSimb);
			
			tableSimbolos = new JTable();
			scrollPaneTablaSimb.setViewportView(tableSimbolos);

			/**
			 * Panel de Arbol de Derivacion
			 */
			JPanel panelArbolDer = new JPanel();
			panelArbolDer.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
			panelArbolDer.setBounds( widthPanelCodigo, (heightPanelesDerecho+padding), (widthPanelesDerecho-padding), (heightPanelesDerecho-padding) );
			panelPrincipal.add(panelArbolDer);
			panelArbolDer.setLayout(null);

			Label lblArbolDer = new Label();
			lblArbolDer.setFont(new Font("Tahoma", Font.BOLD, 20));
			lblArbolDer.setAlignment(Label.CENTER);
			lblArbolDer.setSize( (widthPanelesDerecho-(padding*4)), heightLabel);
			lblArbolDer.setLocation(padding, padding);
			lblArbolDer.setText( tituloArbolDerivac );
			panelArbolDer.add(lblArbolDer);

			JScrollPane scrollPaneArbolDer = new JScrollPane();
			scrollPaneArbolDer.setBounds( padding, (padding+heightLabel), widthScrollDerecho, heightScrollDerecho );
			panelArbolDer.add(scrollPaneArbolDer);
			
			JTextArea textArea3 = new JTextArea();
			scrollPaneArbolDer.setViewportView(textArea3);

			/**
			 * Panel de Errores
			 */
			JPanel panelErrores = new JPanel();
			panelErrores.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
			panelErrores.setBounds( padding, (heightPanelCodigo+padding), widthPanelErrores, (heightPanelErrores-padding) );
			panelPrincipal.add(panelErrores);
			panelErrores.setLayout(null);

			Label lblErrores = new Label();
			lblErrores.setFont(new Font("Tahoma", Font.BOLD, 20));
			lblErrores.setSize( (widthPanelErrores-(padding*2)), heightLabel);
			lblErrores.setLocation( padding, padding );
			lblErrores.setText( tituloErrores );
			panelErrores.add(lblErrores);

			JScrollPane scrollPaneErrores = new JScrollPane();
			scrollPaneErrores.setBounds(padding, (padding+heightLabel), (widthPanelErrores-(padding*2)), (heightPanelErrores-((padding*3)+heightLabel)) );
			panelErrores.add(scrollPaneErrores);

			JTextArea textArea4 = new JTextArea();
			scrollPaneErrores.setViewportView(textArea4);

		}

	}

	/**
	 * **************************************************************
	 * METODOS LLAMADOS DESDE LOS ACTIONPERFORMED
	 * **************************************************************
	 */

	/**
	 * 
	 */
	public void NuevoArchivo() 
	{
		
		if (textArea1.getText().equals("")) {
			frame.setTitle(compiler);
			textArea1.setText("");
		} else {
			
		}
	}

	/**
	 * 
	 */
	public void CargarArchivo() {

		try {
			/** llamamos el metodo que permite cargar la ventana */
			jFileChooser = new JFileChooser();
			FileNameExtensionFilter filtro = new FileNameExtensionFilter("SHTML", "shtml");
			jFileChooser.setFileFilter(filtro);
			jFileChooser.showOpenDialog(null);
			/** abrimos el archivo seleccionado */
			File abre = jFileChooser.getSelectedFile();
			String texto = "";
			String aux = "";
			/**
			 * recorremos el archivo, lo leemos para plasmarlo en el area de
			 * texto
			 */
			if (abre != null) {
				nombreArchivo = "";
				nombreArchivo = abre.getName();
				FileReader archivos;

				archivos = new FileReader(abre);

				BufferedReader lee = new BufferedReader(archivos);
				while ((aux = lee.readLine()) != null) {
					texto += aux + "\u005cn";
				}
				lee.close();
			}
			aux ="";
			
			frame.setTitle(compiler + " - " + nombreArchivo);
			
			textArea1.setText(texto);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(jFileChooser, 
					"ERROR! - Archivo No Encontrado o No Existente!");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(jFileChooser, 
					"ERROR! - No se puede Leer el Archivo!");
			e.printStackTrace();
		} 

	}

	/**
	 * 
	 */
	public void GuardarArchivo() {

	}

	/**
	 * 
	 */
	public void GuardarComoArchivo() {

	}

	/**
	 * 
	 */
	public void Salir() {
		System.exit(0);
	}

	/**
	 * 
	 */
	public void Acerca() {
		String[] lista = { "Software creado por:", " ", 
				"* Cesar Taborda", 
				"* Yison Gomez",
				"* Roger Cordoba" };
		JOptionPane.showMessageDialog(frame, lista);
	}

	/**
	 * 
	 */
	public void CompilarCodFuente() {
		if (textArea1.getText().isEmpty()) {
			JOptionPane.showMessageDialog(frame, "No hay codigo para analizar... D=");
		} else {
			JOptionPane.showMessageDialog(frame, "Compilando...");

			InputStream stream = new ByteArrayInputStream(textArea1.getText().getBytes(StandardCharsets.UTF_8));

			System.out.println(
					"---------- INICIANDO AN\u00c1LISIS L\u00c9XICO PARA EL ARCHIVO " + nombreArchivo + " ----------");
			System.out.println("Ingrese el c\u00f3digo a analizar:");

//			@SuppressWarnings("unused")
//			Compilador parser = new Compilador(stream);
//			// parser.TokenList();
//			try {
//				Compilador.Programa();
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}

			System.out.println("Analisis terminado:");
			System.out.println("no se han hallado errores l\u00e9xicos");

			// LlenarTablaArbol( compilador.getListaSimbolos() );
		}
	}
	
	/**
	 * 
	 */
	private void LlenarTablaArbol(ArrayList<String> lista){
		
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.addColumn("Variable");
		modelo.addColumn("Lexema");
		
		for (String obj : lista) {
			String[] elementos = obj.split(";");
			
			modelo.addRow(elementos);
		}
		
		tableSimbolos.getModel();
	}

}
