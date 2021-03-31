package com.santander.ms.app.telefonos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.santander.ms.app.telefonos.models.Telefono;
import com.santander.ms.app.telefonos.repository.ITelefonoDAO;

@Service
public class TelefonoService implements ITelefonoService {
	
	@Autowired
	private ITelefonoDAO telefonoDAO;
	
	@Override
	public Telefono findPhoneById(String id) {
		return telefonoDAO.findById(id).orElse(null);		
	}

	@Override
	public Telefono retriveByIMEI(String IMEI) {
		return telefonoDAO.findByImei(IMEI);
	}

	@Override
	public List<Telefono> findAllPhones() {
		
		return telefonoDAO.findAll();
	}

	@Override
	public Page<Telefono> findAllPhonesPagenable(Pageable pageable) {

		return telefonoDAO.findAll(pageable);
	}

	@Override
	public Telefono savePhone(Telefono equipo) {
		return telefonoDAO.save(equipo);
	}

	@Override
	public void deletePhone(Telefono equipo) {
		telefonoDAO.delete(equipo);
	}

	

}
