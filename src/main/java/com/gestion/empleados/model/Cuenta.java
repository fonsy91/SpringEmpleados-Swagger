package com.gestion.empleados.model;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name="cuentas")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class Cuenta extends RepresentationModel<Cuenta>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	// Con unique decimos que solo puede ser unico el numero de cuenta
	@Column(length = 20, nullable = false, unique = true)
	private String numeroDeCuenta;
	
	private Float monto;

	// Constructor especifico
	public Cuenta(Integer id, String numeroDeCuenta) {
		super();
		this.id = id;
		this.numeroDeCuenta = numeroDeCuenta;
	}

	
	
	
}

/*
 * @Entity: indica que la clase representa una tabla en la base de datos 
 * y cada atributo de la clase es una columna de la misma ademas puedes 
 * administrar la tabla lo que significa que puedes crear, leer, actualizar
 * y eliminar registros
 * 
 * @Id: con esta etiqueta marcas el atributo como una clave primaria 
 * 
 * @GeneratedValue: se utiliza para definir la estrategia de generacion de 
 * valores de una clave prmaria como es este caso las estrategias son:
 * 
 * 	-GenerationType.IDENTITY: generara automaticamente un valor clave cada vez 
 * 	que se inserte una fila (autoincremento)
 * 	-GenerationType.SEQUENCE: mediante una secuencia de valores 
 * 	-GenerationType.TABLE.
 * 
 * @NoArgsConstructor: se utiliza para generar un constructor vacio sin argumentos
 * 
 * */