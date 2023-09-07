package com.gestion.empleados.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestion.empleados.model.Cuenta;
import com.gestion.empleados.model.Monto;
import com.gestion.empleados.service.CuentaService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class CuentaController {

	@Autowired
	private CuentaService cuentaService;
	
	// Ruta para acceder al swagger
	//http://localhost:8080/api/swagger-ui/index.html#/
	
	@Operation(summary = "Lista de todas las cuentas.")
	@GetMapping
	public ResponseEntity<List<Cuenta>> listarCuentas() {
		List<Cuenta> cuentas = cuentaService.listAll();
		
		if(cuentas.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		// Mostramos los links de este metodo en la salida
		for(Cuenta cuenta:cuentas) {
			cuenta.add(linkTo(methodOn(CuentaController.class).listarCuenta(cuenta.getId())).withSelfRel());
			cuenta.add(linkTo(methodOn(CuentaController.class).listarCuentas()).withRel(IanaLinkRelations.COLLECTION));
		}
		CollectionModel<Cuenta> modelo = CollectionModel.of(cuentas);
		modelo.add(linkTo(methodOn(CuentaController.class).listarCuentas()).withSelfRel());
		// Fin de mostrar links
		
		
		log.info("Cuentas de los usuarios: " + cuentas);
		// Si todo va bien devuelve cuentas y OK
		return new ResponseEntity<>(cuentas, HttpStatus.OK);
	}
	
	@Operation(summary = "Solicitar informacion de una cuenta especifica (pasar idCuenta)")
	@GetMapping("/{id}")
	public ResponseEntity<Cuenta> listarCuenta(@PathVariable Integer id) {
		try {
			Cuenta cuenta = cuentaService.getCuenta(id);
			//Con esto mostraremos links tanto de la clase como del metodo listar cuenta
			cuenta.add(linkTo(methodOn(CuentaController.class).listarCuenta(cuenta.getId())).withSelfRel());
			cuenta.add(linkTo(methodOn(CuentaController.class).listarCuentas()).withRel(IanaLinkRelations.COLLECTION));
			return new ResponseEntity<>(cuenta, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error al listar cuentas, listarCuenta");
			return ResponseEntity.notFound().build();
		}
	}

	@Operation(summary = "Guardar una cuenta nueva")
	@PostMapping
	public ResponseEntity<Cuenta> guardarCuenta(@RequestBody Cuenta cuenta) {
		Cuenta cuentaBBDD = cuentaService.saveCuenta(cuenta);
		return new ResponseEntity<>(cuentaBBDD, HttpStatus.CREATED);
	}
	
	@Operation(summary = "Editar una cuenta")
	@PutMapping
	public ResponseEntity<Cuenta> editarCuenta(@RequestBody Cuenta cuenta) {
		Cuenta cuentaBBDD = cuentaService.saveCuenta(cuenta);
		return new ResponseEntity<>(cuentaBBDD, HttpStatus.OK);
	}
	
	@Operation(summary = "Eliminar una cuenta")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminarCuenta(@PathVariable Integer id) {
		try {
			cuentaService.deleteCuenta(id);
			return ResponseEntity.noContent().build();
			
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@Operation(summary = "Ingresar dinero en cuenta")
	//Hace lo mismo que PutMapping pero este se utiliza para cuando actualizas un solo campo
	@PatchMapping("/{id}/deposito")
	public ResponseEntity<Cuenta> depositarDinero(@PathVariable Integer id, @RequestBody Monto monto) {
		Cuenta cuentaBBDD = cuentaService.depositar(monto.getMonto(), id);
		return new ResponseEntity<>(cuentaBBDD, HttpStatus.OK);
	}
	
	@Operation(summary = "Retirar dinero de cuenta")
	@PatchMapping("/{id}/retiro")
	public ResponseEntity<Cuenta> retirarDinero(@PathVariable Integer id, @RequestBody Monto monto) {
		Cuenta cuentaBBDD = cuentaService.retirar(monto.getMonto(), id);
		return new ResponseEntity<>(cuentaBBDD, HttpStatus.OK);
	}
	
}
