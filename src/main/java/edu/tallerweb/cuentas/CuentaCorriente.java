package edu.tallerweb.cuentas;

/**
 * La más compleja de las cuentas, ésta permite establecer una cantidad de
 * dinero a girar en descubierto. Es por ello que cada vez que se desee extraer
 * dinero, no sólo se considera el que se posee, sino el límite adicional que
 * el banco estará brindando.
 * Por supuesto esto no es gratis, ya que el banco nos cobrará un 5% como
 * comisión sobre todo el monto en descubierto consumido en la operación.
 * Por ejemplo, si tuviéramos $ 100 en la cuenta, y quisiéramos retirar $ 200
 * (con un descubierto de $ 150), podremos hacerlo. Pasaremos a deberle al banco
 * $ 105 en total: los $ 100 que nos cubrió, más el 5% adicional sobre el
 * descubierto otorgado.
 */
public class CuentaCorriente extends AbstractCuenta {
	private Double descubiertoTotal = 0.0;
	private Double descubiertoOriginal = 0.0;

	/**
	 * Toda cuenta corriente se inicia con un límite total para el descubierto.
	 * @param descubiertoTotal
	 */
	public CuentaCorriente(final Double descubiertoTotal) {
		this.descubiertoTotal = descubiertoTotal;
		this.descubiertoOriginal = descubiertoTotal;
	}

	/**
	 * Todo depósito deberá cubrir primero el descubierto, si lo hubiera, y
	 * luego contar para el saldo de la cuenta.
	 * @param monto a depositar
	 */
	public void depositar(final Double monto) {
		if (monto > 0) {
			Double montoAcargar = monto;
			if (this.descubiertoOriginal == this.descubiertoTotal) {
				this.setMontoTotal(this.getMontoTotal() + monto);
			} else {
				if ((this.descubiertoOriginal - this.descubiertoTotal) >= monto) {
					this.descubiertoTotal += montoAcargar;
				} else {
					montoAcargar = montoAcargar
							- (this.descubiertoOriginal - this.descubiertoTotal);
					this.descubiertoTotal = this.descubiertoOriginal;
					this.setMontoTotal(montoAcargar);
				}
			}
		} else {
			throw new CuentaBancariaException(FONDO_INVALIDO);
		}
	}

	/**
	 * Se cobrará el 5% de comisión sobre el monto girado en descubierto. Por
	 * supuesto, no puede extraerse más que el total de la cuenta, más el
	 * descubierto (comisión incluída)
	 * @param monto a extraer
	 */
	public void extraer(final Double monto) {
		Double deudaDelCincoPorCiento = 0.0;
		if (monto <= (this.getMontoTotal() + this.descubiertoTotal)
				&& monto > 0) {
			if (monto < this.getMontoTotal()) {
				this.setMontoTotal(this.getMontoTotal() - monto);
			} else if (this.getMontoTotal() == 0) {
				this.descubiertoTotal -= monto;
			} else {
				this.descubiertoTotal = (this.descubiertoTotal + this
						.getMontoTotal()) - monto;
				this.setMontoTotal(0.0);
			}
			deudaDelCincoPorCiento = ((this.descubiertoOriginal - this.descubiertoTotal) * 5) / 100;
			float valor = (float) (this.descubiertoTotal - deudaDelCincoPorCiento);
			if (valor < 0) {
				throw new CuentaBancariaException(FONDO_INVALIDO);
			}
			this.descubiertoTotal = (double) valor;
		} else if (monto > 0) {
			throw new CuentaBancariaException(FONDO_INSUFICIENTE);
		} else {
			throw new CuentaBancariaException(FONDO_INVALIDO);
		}
	}

	/**
	 * Permite saber el saldo de la cuenta
	 * @return el saldo de la cuenta
	 */
	public Double getSaldo() {
		return this.getMontoTotal();
	}

	/**
	 * Permite saber el saldo en descubierto
	 * @return el descubierto de la cuenta
	 */
	public Double getDescubiertoRestante() {
		return this.descubiertoOriginal;
	}

	public Double getDescubierto() {
		int ix = (int) (this.descubiertoTotal * 100);
		double dbl2 = ((double) ix) / 100.0;
		return dbl2;
	}
}
