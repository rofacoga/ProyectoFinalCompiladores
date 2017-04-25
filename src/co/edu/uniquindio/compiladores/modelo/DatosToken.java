package co.edu.uniquindio.compiladores.modelo;

/**
 * Clase para el manejo de datos de los tokens que se han 
 * necesarios en el compilador.
 * 
 * @author Cesar Taborda
 * @author Yeison Gomez
 * @author Rogers Cordoba
 */
public class DatosToken {
	
	private String tipoDato;
	private Object valorDato;
	private Object otroValor;

	public DatosToken() {
		// TODO Auto-generated constructor stub
	}

	public DatosToken( String tipoDato, Object valorDato ) {
		// TODO Auto-generated constructor stub
		this.tipoDato 	= tipoDato;
		this.valorDato 	= valorDato;
	}

	public DatosToken( String tipoDato, Object valorDato, Object otroValor ) {
		// TODO Auto-generated constructor stub
		this.tipoDato 	= tipoDato;
		this.valorDato 	= valorDato;
		this.otroValor 	= otroValor;
	}

	// --------------------------------------------------------
	// -- METODOS GET & SET PARA OBTENER Y SI SE LLEGARA A 
	// --- NECESITAR CAMBIAR LOS VALORES DE LA CLASE...
	// --------------------------------------------------------
	public String getTipoDato() {
		return tipoDato;
	}

	public void setTipoDato(String tipoDato) {
		this.tipoDato = tipoDato;
	}

	public Object getValorDato() {
		return valorDato;
	}

	public void setValorDato(Object valorDato) {
		this.valorDato = valorDato;
	}

	public Object getOtroValor() {
		return otroValor;
	}

	public void setOtroValor(Object otroValor) {
		this.otroValor = otroValor;
	}

}
