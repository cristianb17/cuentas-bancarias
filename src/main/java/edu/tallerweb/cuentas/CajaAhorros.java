package edu.tallerweb.cuentas;

/**
 * Similar a la CuentaSueldo, pero se pide que luego de la
 * quinta extracción de dinero se cobre un costo adicional
 * por extracción de $ 6
 */
public class CajaAhorros extends AbstractCuenta {

	private Integer cantidadDeExtracciones = 1;
	private Integer aplicarAdicional = 6;

	/**
	 * No hay reglas adicionales para el depósito
	 * @param monto a depositar
	 */
	public void depositar(final Double monto) {
		if(monto > 0){
			this.montoTotal += monto;
		}else{
			throw new CuentaBancariaException(FONDO_INVALIDO);
		}
	}

	/**
	 * Se cobran $6 adicionales por cada extracción luego de
	 * la quinta.
	 * Las extracciones por saldo insuficiente no seran contabilizadas
	 * @param monto a extraer
	 */
	public void extraer(final Double monto) {
		if(monto < this.montoTotal && monto > 0){
			if(this.cantidadDeExtracciones >= 6){
				this.montoTotal -= (monto + this.aplicarAdicional); 
			}else{
				this.montoTotal -= monto;
			}
		}else if(monto > 0){
			throw new CuentaBancariaException(FONDO_INSUFICIENTE);
		}else {
			throw new CuentaBancariaException(FONDO_INVALIDO);
		}
		this.cantidadDeExtracciones++;
	}

	/**
	 * Permite saber el saldo de la cuenta
	 * @return el saldo de la cuenta
	 */
	public Double getSaldo() {
		return this.montoTotal;
	}

}
