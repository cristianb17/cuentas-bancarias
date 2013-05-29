package edu.tallerweb.cuentas;

/**
 * La más compleja de las cuentas, ésta permite establecer una
 * cantidad de dinero a girar en descubierto. Es por ello que
 * cada vez que se desee extraer dinero, no sólo se considera
 * el que se posee, sino el límite adicional que el banco
 * estará brindando.
 *
 * Por supuesto esto no es gratis, ya que el banco nos cobrará
 * un 5% como comisión sobre todo el monto en descubierto
 * consumido en la operación.
 *
 * Por ejemplo, si tuviéramos $ 100 en la cuenta, y quisiéramos
 * retirar $ 200 (con un descubierto de $ 150), podremos hacerlo.
 * Pasaremos a deberle al banco $ 105 en total: los $ 100 que
 * nos cubrió, más el 5% adicional sobre el descubierto otorgado.
 */
public class CuentaCorriente extends AbstractCuenta {
	private Double descubiertoTotal = 0d;
	private Double descubiertoRestante = 0d;
	private Double deudaDelCincoPorCiento = 0d;
	/**
	 * Toda cuenta corriente se inicia con un límite total
	 * para el descubierto.
	 * @param descubiertoTotal
	 */
	public CuentaCorriente(final Double descubiertoTotal) {
		this.descubiertoTotal = descubiertoTotal;
		this.descubiertoRestante = descubiertoTotal;
	}
	
	/**
	 * Todo depósito deberá cubrir primero el descubierto,
	 * si lo hubiera, y luego contar para el saldo de la
	 * cuenta.
	 * @param monto a depositar
	 */
	public void depositar(final Double monto) {
		Double montoAcargar = monto;
		if(this.descubiertoRestante == this.descubiertoTotal){
			this.montoTotal += monto;
		}else{
			if((this.descubiertoTotal - (this.descubiertoRestante + this.deudaDelCincoPorCiento)) >= monto ){
				montoAcargar = montoAcargar - this.deudaDelCincoPorCiento;
				this.deudaDelCincoPorCiento = 0d;
				this.descubiertoRestante += montoAcargar;
			}else{
				montoAcargar -= this.deudaDelCincoPorCiento;
				this.deudaDelCincoPorCiento = 0d;
				montoAcargar = montoAcargar - (this.descubiertoTotal - this.descubiertoRestante);
				this.descubiertoRestante = 0d;
				this.montoTotal = montoAcargar;
			}
		}
	}

	/**
	 * Se cobrará el 5% de comisión sobre el monto girado
	 * en descubierto.
	 * Por supuesto, no puede extraerse más que el total
	 * de la cuenta, más el descubierto (comisión incluída)
	 * @param monto a extraer
	 */
	public void extraer(final Double monto) {
		if (monto < (this.montoTotal + this.descubiertoRestante)) {
			if (monto < this.montoTotal) {
				this.montoTotal -= monto;
			} else if (this.montoTotal == 0) {
					this.descubiertoRestante -= monto;
				} else {
					this.descubiertoRestante = (this.descubiertoRestante + this.montoTotal) - monto;
					this.montoTotal = 0d;
				}
				this.deudaDelCincoPorCiento = (this.descubiertoTotal - this.descubiertoRestante) * 0.05;
		}else{
			throw new CuentaBancariaException(FONDO_INSUFICIENTE);
		}
	}

	/**
	 * Permite saber el saldo de la cuenta
	 * @return el saldo de la cuenta
	 */
	public Double getSaldo() {
		return this.montoTotal;
	}
	
	/**
	 * Permite saber el saldo en descubierto
	 * @return el descubierto de la cuenta
	 */
	public Double getDescubierto() {
		return this.descubiertoTotal;
	}
	
	public Double getDescubiertoRestante() {
		return this.descubiertoRestante;
	}
	
	public Double getDeudaDelCincoPorCiento() {
		return this.deudaDelCincoPorCiento;
	}


}
