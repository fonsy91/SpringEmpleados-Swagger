package com.gestion.empleados.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestion.empleados.Exception.CuentaNotFoundException;
import com.gestion.empleados.model.Cuenta;
import com.gestion.empleados.repository.CuentaRepository;

@Service
@Transactional
public class CuentaService {
	
	@Autowired
	private CuentaRepository cuentaRepository;
	
	public List<Cuenta> listAll() {
		return cuentaRepository.findAll();
	}
	
	public Cuenta getCuenta(Integer id) {
		return cuentaRepository.findById(id).get();
	}
	
	public Cuenta saveCuenta(Cuenta cuenta) {
		return cuentaRepository.save(cuenta);
	}
	
	public void deleteCuenta(Integer id) throws CuentaNotFoundException {
		if(!cuentaRepository.existsById(id)) {
			throw new CuentaNotFoundException("Cuenta no encontrada con el ID: " + id);
		}
		cuentaRepository.deleteById(id);
	}
	
	public Cuenta depositar(float monto, Integer id) {
		cuentaRepository.actualizarMonto(monto, id);
		return cuentaRepository.findById(id).get();
	}
	
	public Cuenta retirar(float monto, Integer id) {
		cuentaRepository.actualizarMonto(-monto, id);
		return cuentaRepository.findById(id).get();
	}
}
