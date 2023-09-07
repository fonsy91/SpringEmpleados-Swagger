package com.gestion.empleados.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gestion.empleados.model.Cuenta;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Integer>{

	
	// Lo utilizamos para hacer consultas personalizadas 
	@Query("UPDATE Cuenta c SET c.monto=c.monto + ?1 WHERE c.id=?2")
	// Indicamos que se va a realizar una modificacion en la tabla de la BD
	@Modifying
	void actualizarMonto(float monto, Integer id);
	
}

/*
 * La SQL modifica el valor de una cuenta, se le pasan dos parametros el saldo
 * a ingresar y el numero de cuenta a la que quieres ingresarlo.
 * */
