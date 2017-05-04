package co.edu.uniquindio.compiladores.modelo;

public class Excepciones extends Exception {

	private static final long serialVersionUID = 1L;
	private String mensaje;

	public Excepciones() {
		// TODO Auto-generated constructor stub
	}
	
	public Excepciones(String mensaje) {
		super(mensaje);
		this.mensaje = mensaje;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}
