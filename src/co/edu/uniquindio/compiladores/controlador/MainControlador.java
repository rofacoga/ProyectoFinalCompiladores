/**
 * 
 */
package co.edu.uniquindio.compiladores.controlador;

import co.edu.uniquindio.compiladores.modelo.Compilador;
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
	
	public static JSONObject compilarCodigofuente( String codFuente ){
		jsonTransport = new JSONObject();
		mainCompilador.compilar(codFuente);
		return jsonTransport;
	}

}
