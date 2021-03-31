package com.santander.ms.app.telefonos.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.santander.ms.app.telefonos.models.Telefono;

public interface ITelefonoService {

	public Telefono retriveByIMEI(String IMEI);
	
	public Telefono findPhoneById(String id);
	
	public List<Telefono> findAllPhones();
	
	public Page<Telefono> findAllPhonesPagenable(Pageable pageable);

	public Telefono savePhone(Telefono equipo); 

	public void deletePhone(Telefono equipo);	
}