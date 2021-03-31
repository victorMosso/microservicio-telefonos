package com.santander.ms.app.telefonos.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "telefonos")
public class Telefono {
	@Id
	private String id;	
	@NonNull
	private String nombre_marca;
	@NonNull
	private String modelo;
	@NonNull
	private String nombre_corto;
	@NonNull
	private String fecha_creacion;
	@NonNull
	private String imei;
	
	private String numero_celular;

	private String email;
	
	private boolean ios;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre_marca() {
		return nombre_marca;
	}

	public void setNombre_marca(String nombre_marca) {
		this.nombre_marca = nombre_marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getNombre_corto() {
		return nombre_corto;
	}

	public void setNombre_corto(String nombre_corto) {
		this.nombre_corto = nombre_corto;
	}

	public String getFecha_creacion() {
		return fecha_creacion;
	}

	public void setFecha_creacion(String fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getNumero_celular() {
		return numero_celular;
	}

	public void setNumero_celular(String numero_celular) {
		this.numero_celular = numero_celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isIos() {
		return ios;
	}

	public void setIos(boolean ios) {
		this.ios = ios;
	}

	@Override
	public String toString() {
		return "id : "+getId()+" marca : "+getNombre_marca()+" modelo : "+getModelo()
				+ " nombreCorto : "+getNombre_corto() + " fecha_ creacion : "+getFecha_creacion()
				+ " IMEI : "+getImei()+" numero_celular" + getNumero_celular()
				+" email : "+getEmail()+" IOS : "+isIos();
	}
	
	
}
