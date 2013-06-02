package edu.tallerweb.cuentas;

/**
 * Esta clase representa todas las excepciones en tiempo de ejecución de
 * negocio que puedan darse en el proyecto
 */
public class CuentaBancariaException extends RuntimeException {

	private static final long serialVersionUID = -2869788375032434768L;

	public CuentaBancariaException(final String message) {
		super(message);
	}

}
