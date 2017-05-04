/**
 * 
 */
package co.edu.uniquindio.compiladores.vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import co.edu.uniquindio.compiladores.controlador.MainControlador;
import co.edu.uniquindio.compiladores.modelo.ParseException;
import co.edu.uniquindio.compiladores.modelo.TokenMgrError;

/**
 * 
 * 
 * @author Cesar Taborda
 * @author Yeison Gomez
 * @author Rogers Cordoba
 */
public class MainVista {
	
	private MainControlador mainControlador;
	
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
	final static String tituloErrores 		= "Backlog";
	
	final static String compiler 	 = "Compilador - ";
	final static String sinCambios 	 = "< No se han guardado los Cambios >";
	final static String strDocumento = "Documento ";
	
	private JFrame 			frame;
	private JFileChooser 	jFileChooser;
	private JTextArea 		codFuenteTxtArea;			
	private JTextArea 		erroresTxtArea;
	private JTable 			tablaSimbolos;

	private JTabbedPane 	panelesTabPane;
	
	private String 			nombreArchivo;
	private int 			numeroArchivo;

	/*
	 * Parametros requeridos para crear un nuevo panel en el jTabbedPane
	 */
	private int 		numTabs;
	private KeyAdapter 	k;

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
		this.numeroArchivo = 1;
		this.nombreArchivo = strDocumento+numeroArchivo;

		this.numTabs = 0;
		
		this.mainControlador = new MainControlador();
		
		
		this.k = new KeyAdapter() {
			@Override
			public void keyReleased( KeyEvent evt ) {
				texareaEntradaKeyReleased(evt);
			}
		};
		
		initialize();

		this.tablaSimbolos.setModel(new javax.swing.table.DefaultTableModel(new Object[][] { { null, null } },
				new String[] { "#", "Token", "Descripción" }));

		/*root = new DefaultMutableTreeNode("Raiz");
		treeModel = new DefaultTreeModel(root);
		tree.setShowsRootHandles(true);
		tree = new JTree(treeModel);
		jScrollPane3.setViewportView(tree);*/

		crearJTabbedPane();
	}
	
	/**
	 * This method is created to callback set visible the main window in this project
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
		frame.setTitle( compiler+strDocumento+numeroArchivo );
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
					NuevoArchivo( );
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
			panelCodigoFuente.setLayout(null);
			panelPrincipal.add(panelCodigoFuente);
			
			/**
			 * Manejo para el jTabbedPane, Creamos el conjunto de pestañas
			 */
	        panelesTabPane = new JTabbedPane();
	        panelesTabPane.setBounds( 0, 0, (widthPanelCodigo-(padding*2)), (heightPanelCodigo-(padding)) );
	        panelCodigoFuente.add(panelesTabPane);

	        /**
	         * 
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds( padding, padding, (widthPanelCodigo-(padding*4)), (heightPanelCodigo-(padding*3)) );
			panelCodigoFuente.add(scrollPane);

			codFuenteTxtArea = new JTextArea();
			codFuenteTxtArea.setFont(new Font("Consolas", Font.PLAIN, 13));
			TextLineNumber tlnCodFuente = new TextLineNumber(codFuenteTxtArea);
			scrollPane.setViewportView(codFuenteTxtArea);
			scrollPane.setRowHeaderView(tlnCodFuente);
	         */

			/**
			 * Label para mostrar si existen cambios en el codigo fuente
			 * 
			Label lblcodfuente = new Label();
			lblcodfuente.setAlignment(Label.CENTER);
			lblcodfuente.setText(sinCambios);
			lblcodfuente.setBounds( padding, (heightPanelCodigo*5/6), (widthPanelCodigo/3), heightLabel);
			panelCodigoFuente.add(lblcodfuente);
			 */

			/**
			 * Boton para Compilar...
			 * 
			JButton btnCompilar = new JButton("COMPILAR");
			btnCompilar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CompilarCodFuente();
				}
			});
			btnCompilar.setBounds( (widthPanelCodigo*2/3), ((heightPanelCodigo*3/4)+(padding*2)), 
										((widthPanelCodigo/4)-padding), heightLabel);
			panelCodigoFuente.add(btnCompilar);
			 */

			/**
			 * Panel de Tabla de Simbolos
			 */
			JPanel panelTablaSimb = new JPanel();
			panelTablaSimb.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
			panelTablaSimb.setBounds( widthPanelCodigo, padding, 
											(widthPanelesDerecho-padding), ((heightPanelesDerecho*2)-padding) );
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
			scrollPaneTablaSimb.setBounds( padding, (padding+heightLabel), widthScrollDerecho, (heightScrollDerecho*2) );
			panelTablaSimb.add(scrollPaneTablaSimb);
			
			tablaSimbolos = new JTable();
			scrollPaneTablaSimb.setViewportView(tablaSimbolos);

			/**
			 * Panel de Arbol de Derivacion
			 * 
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
			 */

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

			erroresTxtArea = new JTextArea();
			erroresTxtArea.setEditable( false );
			erroresTxtArea.setForeground( Color.red );

			TextLineNumber tlnCodFuente = new TextLineNumber(erroresTxtArea);
			
			JScrollPane scrollPaneErrores = new JScrollPane();
			scrollPaneErrores.setBounds(padding, (padding+heightLabel), (widthPanelErrores-(padding*2)), (heightPanelErrores-((padding*3)+heightLabel)) );
			scrollPaneErrores.setViewportView(erroresTxtArea);
			scrollPaneErrores.setRowHeaderView(tlnCodFuente);
			panelErrores.add(scrollPaneErrores);

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
	public void NuevoArchivo( ) {
		
		if (! codFuenteTxtArea.getText().equals("") ) { 
			boolean guardado = false;
			
			if ( nombreArchivo == (strDocumento+numeroArchivo) ) {
				
				guardado = GuardarComoArchivo();
				
				if( guardado ) { 
					numeroArchivo ++;
					frame.setTitle(compiler+strDocumento+numeroArchivo);
					codFuenteTxtArea.setText("");
				} else { 
					
				}
				
			} else {

			}
			
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
			jFileChooser.setFileFilter( new FileNameExtensionFilter( "Files Extension CRY", "cry" ) );
			jFileChooser.setFileFilter( new FileNameExtensionFilter( "Files Extension TXT", "txt" ) );
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
			
			frame.setTitle(compiler + nombreArchivo);
			
			codFuenteTxtArea.setText(texto);
			
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
	public void GuardarArchivo( ) {

	}

	/**
	 * @return 
	 * 
	 */
	public boolean GuardarComoArchivo( ) {
		return true;
	}

	/**
	 * 
	 */
	public void Salir( ) {
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
	@SuppressWarnings("static-access")
	public void CompilarCodFuente(String codFuenteTxtArea) {
		codFuenteTxtArea = codFuenteTxtArea.trim();
		
		if ( ! codFuenteTxtArea.isEmpty() ) {
			
			String errores 		= "";
			String resultados 	= "";
			
			mainControlador = new MainControlador();
			
			try {
				mainControlador.compilarCodigofuente( codFuenteTxtArea );
				errores 	= mainControlador.getErrores();
				resultados 	= mainControlador.getResultado();
//				errores = mainControlador.compilarCodigofuente( parts );
			} catch (ParseException | TokenMgrError e) {
				// TODO Auto-generated catch block
				System.out.println("Errores!!!");
				errores += e.toString(); 
				e.printStackTrace();
			}

			int indexPanel = panelesTabPane.getSelectedIndex()+1;
			erroresTxtArea.setText("");
			erroresTxtArea.setText( "Panel "+indexPanel+": "+errores );
			
		} else {
			JOptionPane.showMessageDialog(frame, "No hay codigo para analizar... D=");
		}
	}
	
	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private void LlenarTablaArbol(ArrayList<String> lista){
		
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.addColumn("Variable");
		modelo.addColumn("Lexema");
		
		for (String obj : lista) {
			String[] elementos = obj.split(";");
			
			modelo.addRow(elementos);
		}
		
		tablaSimbolos.getModel();
	}

	/**
	 * Metdo para crear 2 pestañas nuevas en el panelesTabPane (JTabbedPane)
	 * 'Panel 1' y '+' 
	 */
	private void crearJTabbedPane() {
		int panelActual = numTabs+1;
		/* add first tab */
		panelesTabPane.add( createJPanel(), "Panel " + String.valueOf(panelActual), numTabs++ );
		panelesTabPane.setTabComponentAt( 0, new CustomTab(this) );

		/* add tab to add new tab when click */
		JPanel panel = new JPanel();
		panelesTabPane.add(panel, "+", numTabs++);

		panelesTabPane.addChangeListener(changeListener);
	}

	/**
	 * Crear un nuevo JPanel para contener todos 
	 * las areas introducir el texto para analizar 
	 * los distintos codigos fuentes
	 */
	private JPanel createJPanel() {
		// System.out.println("creando panel: "+numTabs);
		JPanel panel = new JPanel();
		panel.setBounds( padding, padding, (widthPanelCodigo-(padding*3)), (heightPanelCodigo-(padding*2)) );
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds( 0, 0, (widthPanelCodigo-((padding*3)+(padding*1/2))), (heightPanelCodigo-((padding*3)+(padding*1/2))) );

		codFuenteTxtArea = new JTextArea();
		codFuenteTxtArea.setFont(new Font("Consolas", Font.PLAIN, 13));
		TextLineNumber tlnCodFuente = new TextLineNumber(codFuenteTxtArea);
		scrollPane.setViewportView(codFuenteTxtArea);
		scrollPane.setRowHeaderView(tlnCodFuente);

		panel.add(scrollPane);

		Font font = codFuenteTxtArea.getFont();
		float size = font.getSize() + 4f;
		codFuenteTxtArea.setFont(font.deriveFont(size));

		codFuenteTxtArea.addKeyListener(k);

		return panel;
	}

	private void addNewTab() {
		int index = numTabs - 1;
		
		/* if click new tab */
		if (panelesTabPane.getSelectedIndex() == index) { 
			/* add new tab */
			panelesTabPane.add(createJPanel(), "Panel " + String.valueOf(index+1), index);
			/* set tab is createJPanelcustom tab */
			panelesTabPane.setTabComponentAt(index, new CustomTab(this));
			panelesTabPane.removeChangeListener(changeListener);
			panelesTabPane.setSelectedIndex(index);
			panelesTabPane.addChangeListener(changeListener);
			numTabs++;
		}
	}

	/**
	 * Metodo para eliminar el panel de posicion index creado en el tabbedPane
	 * 
	 * @param index
	 */
	public void removeTab(int index) {
		panelesTabPane.remove(index);
		numTabs--;

		if (index == numTabs - 1 && index > 0) {
			panelesTabPane.setSelectedIndex(numTabs - 2);
		} else {
			panelesTabPane.setSelectedIndex(index);
		}

		if (numTabs == 1) {
			addNewTab();
		}
	}

	/**
	 * Metodo para detectar cuando se da click en '+' para agregar una nueva pestaña...
	 */
	ChangeListener changeListener = (ChangeEvent e) -> {
		addNewTab();
	};
	
//	/**
//	 * Permite saber cuando se presiona click en una de las pestañas 
//	 * @param evt
//	 */
//	private void jTabbedPane1MouseClicked( MouseEvent evt ) { 
//		// jTabbedPane1.getSelectedIndex() esto te entrega el indice del tab que seleccionaste
//		System.out.println( panelesTabPane.getSelectedIndex() );
//	}

//	@SuppressWarnings({ "static-access", "static-access", "static-access", "static-access" })
//	private void compilar(String texarea) throws UnsupportedEncodingException {
//
//		jLterminal.setText(" ");
//		// Se reinicia el arreglo que alameca los tokens de la tab
//		Calculadora.arreglo = new Object[100][2];
//		Calculadora.i = 0;
//
//		for (int i = 0; i < texarea.split("\n").length; i++) {
//			try {
//
//				condador = i;
//				String cad = texarea.split("\n")[i];
//				cad = cad.isEmpty() ? " " : cad + "\n";
//				// convert String into InputStream
//				InputStream s = new ByteArrayInputStream(cad.getBytes("UTF-8"));
//
//				if (parser == null) {
//					parser = new Calculadora(s);
//				} else {
//					Calculadora.ReInit(s);
//				}
//				eliminar(i);
//
//				parser.setTabla(jTable1);// se envia la instancia del
//				parser.setError(error);;
//				parser.setIndice(i);// se envia la instancia del
//				// componente tabla a la clase
//				// calculadora
//				Calculadora.setjLterminal(jLterminal);
//				// online() es un metodo creado para analizar linea por
//				// linea
//
//				switch (Calculadora.one_line()) {
//				case 1:
//					// System.exit(0);
//				default:
//					break;
//				}
//
//			} catch (Error e) {
//				mostrarError(e.getMessage());
//			} catch (NumberFormatException e) {
//				mostrarError(e.getMessage());
//				JOptionPane.showMessageDialog(null, "Numero fuera de Rango", "Importante", JOptionPane.ERROR_MESSAGE);
//			} catch (ParseException e) {
//				mostrarError(e.getMessage());
//			} catch (UnsupportedEncodingException e) {
//				mostrarError(e.getMessage());
//			}
//		}
//
//		String salida = "<html>";
//		for (int i = 0; i < texarea.split("\n").length; i++)
//
//		{
//			if (existe(i)) {
//				salida += "<img src=\"" + Ventana.class.getResource("/imagenes/ok.png") + "\">" + "<FONT COLOR=Green>"
//						+ (i + 1) + "</FONT>" + "<br>";
//
//			} else {
//				salida += "<img src=\"" + Ventana.class.getResource("/imagenes/error.png") + "\">" + "<FONT COLOR=red>"
//						+ (i + 1) + "</FONT>" + "<br>";
//			}
//
//		}
//
//		salida += "</html>";
//
//		JScrollPane scrPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
//				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//		JPanel c = (JPanel) tabbedPane.getSelectedComponent();
//
//		Component[] components = c.getComponents();
//		for (Component jLsintax : components)
//
//		{
//			if (jLsintax instanceof JLabel) {
//				((JLabel) (jLsintax)).add(scrPane);
//				((JLabel) (jLsintax)).setText(salida);
//			}
//		}
//		c.revalidate();
//		c.repaint();
//
//	}


	private void texareaEntradaKeyReleased(KeyEvent evt) {
		String codFuente = ((JTextArea) evt.getSource()).getText().trim();
		
		if ( codFuente.length() > 1 ) {
			
			CompilarCodFuente( codFuente );
			
			// try {
			// System.out.println( codFuente );
			// int indexPanel = panelesTabPane.getSelectedIndex()+1;
			// erroresTxtArea.setText( "Panel "+indexPanel+": "+codFuente );
			//	compilar( ((JTextArea) evt.getSource()).getText() );
			// 	if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
			//		compilar( ((JTextArea) evt.getSource()).getText() );
			// 	}
			// } catch (UnsupportedEncodingException e) {
			// 	e.printStackTrace();
			// }
		} else {
			erroresTxtArea.setText( "" );
		}
	}

	public JTabbedPane getTabbedPane() {
		return panelesTabPane;
	}

	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.panelesTabPane = tabbedPane;
	}
}
