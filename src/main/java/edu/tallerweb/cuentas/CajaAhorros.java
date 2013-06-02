package edu.tallerweb.cuentas;

/**
 * Similar a la CuentaSueldo, pero se pide que luego de la quinta extracción de
 * dinero se cobre un costo adicional por extracción de $ 6
 */
public class CajaAhorros extends AbstractCuenta {
	private Integer cantidadDeExtracciones = 1;
	private Integer aplicarAdicional = 6;
	private static final Integer CantidadMaximaExtracciones = 6;

	/**
	 * No hay reglas adicionales para el depósito
	 * 
	 * @param monto
	 *            a depositar
	 */
	public void depositar(final Double monto) {
		if (monto > 0) {
			this.setMontoTotal(this.getMontoTotal() + monto);
		} else {
			throw new CuentaBancariaException(FondoInvalido);
		}
	}

	/**
	 * Se cobran $6 adicionales por cada extracción luego de la quinta. Las
	 * extracciones por saldo insuficiente no seran contabilizadas
	 * 
	 * @param monto
	 *            a extraer
	 */
	public void extraer(final Double monto) {
		if (monto < this.getMontoTotal() && monto > 0) {
			if (this.cantidadDeExtracciones >= CantidadMaximaExtracciones) {
				this.setMontoTotal(this.getMontoTotal()
						- (monto + this.aplicarAdicional));
			} else {
				this.setMontoTotal(this.getMontoTotal() - monto);
			}
		} else if (monto > 0) {
			throw new CuentaBancariaException(FondoInsuficiente);
		} else {
			throw new CuentaBancariaException(FondoInvalido);
		}
		this.cantidadDeExtracciones++;
	}

	/**
	 * Permite saber el saldo de la cuenta
	 * 
	 * @return el saldo de la cuenta
	 */
	public Double getSaldo() {
		return this.getMontoTotal();
	}
}
