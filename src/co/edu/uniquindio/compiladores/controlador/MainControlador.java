/**
 * 
 */
package co.edu.uniquindio.compiladores.controlador;

import co.edu.uniquindio.compiladores.modelo.Compilador;
import co.edu.uniquindio.compiladores.modelo.ParseException;
import co.edu.uniquindio.compiladores.modelo.Token;
import co.edu.uniquindio.compiladores.vista.MainVista;
import org.json.simple.JSONObject;

/**
 * @author Cesar Taborda
 * @author Yeison Gomez
 * @author Rogers Cordoba
 *
 */
public class MainControlador {
	protected static Compilador mainCompilador;
	protected static MainVista 	mainVista;
	protected static JSONObject jsonTransport;

	/**
	 * This method created one instances from the calculator compiler, 
	 * that allow the window are visible.
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		mainCompilador = new Compilador();
		
		mainVista = new MainVista();
		mainVista.crearVista();
	}
	
	public static String compilarCodigofuente( String[] codFuente ) { 
		String completo = "";
				
		for (int i = 0; i < codFuente.length; i++) {
			/*****************************************************************
			  Primer Manejo de errores, aquí notamos que solo tenemos un tipo
			  de error el error de "Se esperaba otro componente lexico"
			*****************************************************************/
			completo += mainCompilador.compilar( codFuente[i] );
			
//			try{
//				mainCompilador.compilar( codFuente[i] );
//			}catch (ParseException e){
//				System.out.println( e );
//				completo += e;
//				System.out.println("Error 3: \"Se esperaba otro componente lexico\"");
//			}
		}

		return completo;
	}

}
