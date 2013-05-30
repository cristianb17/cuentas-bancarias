package edu.tallerweb.cuentas;

import org.junit.Assert;
import org.junit.Test;

public class CuentaTests {
	//Cuenta Sueldo Tests
	@Test
	public void queVerifiqueLaConsigna() {
		CuentaSueldo cuenta = new CuentaSueldo();
		cuenta.depositar(4000.0);

		Assert.assertEquals(
				"al depositar $ 4000.0 en una cuenta vacía, tiene $ 4000.0",
				4000.0, cuenta.getSaldo(), 0.0);

		cuenta.extraer(500.0);
	
		Assert.assertEquals(
				"al extraer $ 500.0 de una cuenta con $ 4000.0 se obtienen $ 3500.0",
				3500.0, cuenta.getSaldo(), 0.0);
	}

	@Test(expected=CuentaBancariaException.class)
	public void queVerifiqueLaConsignaException() {
		CuentaSueldo cuenta = new CuentaSueldo();
		cuenta.depositar(3500.0);

		cuenta.extraer(4000.0);
	}
	
	//Cuenta Ahorros Tests
	
	@Test
	public void queVerifiqueLaConsignaCajaDeAhorro() {
		CajaAhorros cuenta = new CajaAhorros();
		cuenta.depositar(4000.0);

		Assert.assertEquals(
				"al depositar $ 4000.0 en una cuenta vacía, tiene $ 4000.0",
				4000.0, cuenta.getSaldo(), 0.0);

		cuenta.extraer(500.0);
	
		Assert.assertEquals(
				"al extraer $ 500.0 de una cuenta con $ 4000.0 se obtienen $ 3500.0",
				3500.0, cuenta.getSaldo(), 0.0);
	}
	
	
	@Test
	public void extraerSeisVecesCajaDeAhorro() {
		CajaAhorros cuenta = new CajaAhorros();
		cuenta.depositar(1000.0);
		cuenta.extraer(100.0);
		cuenta.extraer(100.0);
		cuenta.extraer(100.0);
		cuenta.extraer(100.0);
		cuenta.extraer(100.0);
		cuenta.extraer(100.0);
	
		Assert.assertEquals(
				"al extraer $ 100.0 seis veces de una cuenta con $ 1000.0 de una caja de ahorro se obtienen $ 394.0",
				394.0, cuenta.getSaldo(), 0.0);
	}

	@Test(expected=CuentaBancariaException.class)
	public void extraccionExcedida() {
		CajaAhorros cuenta = new CajaAhorros();
		cuenta.depositar(200.0);

		Assert.assertEquals(
				"al depositar $ 200 en una cuenta vacía, tiene $ 200",
				200.0, cuenta.getSaldo(), 0.0);
		cuenta.extraer(500.0);
	}
	
	
	//Cuenta Corriente Tests
	
	@Test(expected=CuentaBancariaException.class)
	public void extraccionExcedidaCuentaCorriente() {
		CuentaCorriente cuenta = new CuentaCorriente(200.0);
		cuenta.depositar(200.0);

		Assert.assertEquals(
				"al depositar $ 200 en una cuenta vacía, tiene $ 200",
				200.0, cuenta.getSaldo(), 0.0);
		cuenta.extraer(500.0);
	}
	
	@Test
	public void queVerifiqueLaConsignaCajaDeCuentaCorriente() {
		CuentaCorriente cuenta = new CuentaCorriente(200d);
		cuenta.depositar(4000.0);

		Assert.assertEquals(
				"al depositar $ 4000.0 en una cuenta vacía, tiene $ 4000.0",
				4000.0, cuenta.getSaldo(), 0.0);

		cuenta.extraer(500.0);
	
		Assert.assertEquals(
				"al extraer $ 500.0 de una cuenta con $ 4000.0 se obtienen $ 3500.0",
				3500.0, cuenta.getSaldo(), 0.0);
	}
	
	@Test
	public void queLaExtraccionGasteParteDelDescubierto() {
		CuentaCorriente cuenta = new CuentaCorriente(200d);
		cuenta.depositar(4000.0);

		Assert.assertEquals(
				"al depositar $ 4000.0 en una cuenta vacía, tiene $ 4000.0",
				4000.0, cuenta.getSaldo(), 0.0);

		cuenta.extraer(4150.0);
	
		Assert.assertEquals(
				"al extraer $ 4000 de una cuenta con $ 4000.0 se obtienen $ 0.0",
				0.0, cuenta.getSaldo(), 0.0);
		
		Assert.assertEquals(
				"al extraer $ 150 del descubierto con $ 200 de descubierto se obtienen $ 42.50",
				42.5, cuenta.getDescubierto(), 0.0);
		
		cuenta.depositar(300.0);
		
		Assert.assertEquals(
				"al depositar $ 300 en una cuenta donde tengo una deuda con el banco del 7.5% y tengo que completa el descubierto de 200, tiene $ 142.5",
				142.5, cuenta.getSaldo(), 0.0);
	}
	
	@Test
	public void extraccionCuentaCorriente() {
		CuentaCorriente cuenta = new CuentaCorriente(500.0);
		cuenta.depositar(1000.0);
		cuenta.extraer(1100.0);
		
		Assert.assertEquals(
				"al depositar $ 1000.0 en una cuenta, con $500.0 de descubierto y extraer 1100$ deberian quedar $395.0 descubierto",
				395.0, cuenta.getDescubierto(), 0.0);

	}
	
	@Test
	public void extraccionCuentaCorriente2() {
		CuentaCorriente cuenta = new CuentaCorriente(500.0);
		cuenta.depositar(1000.0);
		cuenta.extraer(1476.0);
		
		Assert.assertEquals(
				"al depositar $ 1000.0 en una cuenta, con $500.0 de descubierto y extraer 1476.0$ deberian quedar $0.2 descubierto",
				0.2, cuenta.getDescubierto(), 0.0);

	}
	
	@Test
	public void queLuegoDeExtraerEnDescubiertoAlDepositarRestauraDescubierto() {
		CuentaCorriente cuenta = new CuentaCorriente(500.0);
		cuenta.extraer(100.0);
		cuenta.depositar(500.0);
		Assert.assertEquals(
				"al depositar $ 500 en una cuenta, con $395 de descubierto y $ deberian quedar $ 500 descubierto",
				500.0, cuenta.getDescubierto(), 0.0);

	}
	
	@Test
	public void queLuegoDeExtraerEnDescubiertoAlDepositarMenosDelDescubiertoRestauraDescubiertoParcialmente() {
		CuentaCorriente cuenta = new CuentaCorriente(500.0);
		cuenta.extraer(100.0);
		cuenta.depositar(50.0);
		Assert.assertEquals(
				"al depositar $ 500 en una cuenta, con $395 de descubierto y $ deberian quedar $ 500 descubierto",
				445.0, cuenta.getDescubierto(), 0.0);

	}

}
