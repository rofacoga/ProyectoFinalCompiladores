/**
 * 
 */
package co.edu.uniquindio.compiladores.controlador;

import co.edu.uniquindio.compiladores.modelo.Compilador;
import co.edu.uniquindio.compiladores.modelo.ParseException;
import co.edu.uniquindio.compiladores.modelo.Token;
import co.edu.uniquindio.compiladores.modelo.TokenMgrError;
import co.edu.uniquindio.compiladores.vista.MainVista;
import org.json.simple.JSONObject;

/**
 * 
 * @author Cesar Taborda
 * @author Yeison Gomez
 * @author Rogers Cordoba
 */
public class MainControlador {
	protected static Compilador mainCompilador;
	protected static MainVista 	mainVista;
	protected static JSONObject jsonTransport;
	private static String resultado;
	private static String errores;

	/**
	 * This method created one instances from the calculator compiler, 
	 * that allow the window are visible.
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		mainCompilador = new Compilador();
		errores 	= "";
		resultado 	= "";
		
		mainVista = new MainVista();
		mainVista.crearVista();
	}
	
	public static String compilarCodigofuente( String codFuente ) throws ParseException, TokenMgrError { 
		
		mainCompilador = new Compilador();
		/*****************************************************************
		  Primer Manejo de errores, aqu� notamos que solo tenemos un tipo
		  de error el error de "Se esperaba otro componente lexico"
		*****************************************************************/
		// System.err.println( codFuente );
		mainCompilador.compilar( codFuente );
		errores 	+= mainCompilador.getErrores();
		resultado 	+= mainCompilador.getResultado();
		
//		for (int i = 0; i < codFuente.length; i++) {
//			/*****************************************************************
//			  Primer Manejo de errores, aqu� notamos que solo tenemos un tipo
//			  de error el error de "Se esperaba otro componente lexico"
//			*****************************************************************/
//			System.out.println( codFuente[i] );
//			mainCompilador.compilar( codFuente[i] );
			
// 			completo += mainCompilador.compilar( codFuente[i] );
//			try{
//				mainCompilador.compilar( codFuente[i] );
//			}catch (ParseException e){
//				System.out.println( e );
//				completo += e;
//				System.out.println("Error 3: \"Se esperaba otro componente lexico\"");
//			}
//		}

		return errores;
	}

	public static String getResultado() {
		return resultado;
	}

	public static String getErrores() {
		return errores;
	}
}
