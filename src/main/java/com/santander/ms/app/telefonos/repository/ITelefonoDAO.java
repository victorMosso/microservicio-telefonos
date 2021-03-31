package com.santander.ms.app.telefonos.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.santander.ms.app.telefonos.models.Telefono;

public interface ITelefonoDAO extends MongoRepository<Telefono, String> {
	
	public Telefono findByImei(String imei);
}
