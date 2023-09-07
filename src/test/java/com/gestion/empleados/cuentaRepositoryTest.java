package com.gestion.empleados;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.gestion.empleados.model.Cuenta;
import com.gestion.empleados.repository.CuentaRepository;

@DataJpaTest
@Rollback(value=true)
public class cuentaRepositoryTest {

	// Inyeccion de dependencias 
	@Autowired
	private CuentaRepository cuentaRepository;
	
	@Test
	void testAgregarCuenta() {
		Cuenta cuenta = new Cuenta(10, "1234567");
		Cuenta cuentaGuardada = cuentaRepository.save(cuenta);
		
		Assertions.assertThat(cuentaGuardada).isNotNull();
		Assertions.assertThat(cuentaGuardada.getId()).isGreaterThan(0);
	}
	
}

// Esta clase esta hecha para hacer pruebas de la aplicacion 