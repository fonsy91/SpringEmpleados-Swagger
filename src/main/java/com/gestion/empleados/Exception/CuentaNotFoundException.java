package com.gestion.empleados.Exception;

@SuppressWarnings("serial")
public class CuentaNotFoundException extends Exception{

	public CuentaNotFoundException(String mensaje) {
		super(mensaje);
	}
}
