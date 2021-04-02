package com.santander.ms.app.telefonos.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.santander.ms.app.telefonos.models.Telefono;
import com.santander.ms.app.telefonos.service.ITelefonoService;

@RestController
public class TelefonoController {
	private static Logger log = LoggerFactory.getLogger(TelefonoController.class);

	@Autowired
	private ITelefonoService telefonoService;

	@GetMapping("/listar")
	public List<Telefono> listaTelefonos() {
		return telefonoService.findAllPhones();
	}
	
	@GetMapping("/paginable")
	public ResponseEntity<?> listaTelefonosPagina(Pageable pageable) {
		return ResponseEntity.ok().body(telefonoService.findAllPhonesPagenable(pageable));
	}

	@GetMapping("/ver/{imei}")
	public ResponseEntity<?> getTelefonoByIMEI(@PathVariable String imei) {
		log.info("Ingresando al metodo getTelefonoByIMEI()");
		Telefono telDB = null;
		Map<String, Object> mapError = new HashMap<>();
		try {
			telDB = telefonoService.retriveByIMEI(imei);
			if (telDB == null) {
				mapError.put("mensaje",
						"El equipo telefonico con  imei: ".concat(imei).concat(" no existe en la Base de datos¡"));
				return new ResponseEntity<Map<String, Object>>(mapError, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Telefono>(telDB, HttpStatus.OK);
		} catch (DataAccessException e) {
			mapError.put("mensaje", "Error al realizar la consulta en la Base de datos !");
			mapError.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(mapError, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/")
	public ResponseEntity<?> agregarEquipoTelefonico(@Valid @RequestBody Telefono equipo) {
		Telefono telDB = null;
		Map<String, Object> response = new HashMap<>();
		try {
			telDB = telefonoService.savePhone(equipo);
		} catch (DataAccessException e) {
			;
			response.put("mensaje", "Error al insertar el equipo telefonico !");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Se INSERTO con exito el equipo telefonico.");
		response.put("equipo", telDB);
		log.info("Se INSERTO con exito el equipo telefonico : " + telDB.toString());
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> editarEquipoTelefonico(@Valid @RequestBody Telefono equipo, @PathVariable String id) {

		Map<String, Object> response = new HashMap<>();
		Telefono telDB = telefonoService.findPhoneById(id);
		if (telDB != null) {
			telDB.setEmail(equipo.getEmail());
			telDB.setImei(equipo.getImei());
			telDB.setIos(equipo.isIos());
			telDB.setModelo(equipo.getModelo());
			telDB.setNombre_corto(equipo.getNombre_corto());
			telDB.setNombre_marca(equipo.getNombre_marca());
			telDB.setNumero_celular(equipo.getNumero_celular());
			log.info(String.format("Se actualizara el equipo telefonico con id : %s", id));
			try {
				telDB = telefonoService.savePhone(telDB);
			} catch (DataAccessException e) {
				log.info("Error al actualizar el equipo telefonico con id : " + id);
				response.put("mensaje", "Error al actualizar el equipo telefonico!");
				response.put("error", e.getMessage().concat(":".concat(e.getMostSpecificCause().getMessage())));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			log.info("Se modifico exitosamente, el equipo: " + telDB.toString());
			response.put("mensaje", "Se actualizo correctamente el equipo ¡");
			response.put("equipo", telDB);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		} else {
			String mensaje = "Imposible modificar ya que no existe el equipo telefonico con id: %s";
			log.info(String.format(mensaje, id));
			response.put("mensaje", String.format(mensaje, id));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminarEquipoTelefonico(@PathVariable String id) {
		Map<String, Object> response = new HashMap<String, Object>();
		String mensaje = null;
		Telefono telDB = telefonoService.findPhoneById(id);
		if (telDB != null) {
			try {
				telefonoService.deletePhone(telDB);
			} catch (DataAccessException ex) {
				mensaje = "Error al eliminar el equipo telefonico!";
				response.put("mensaje", mensaje);
				response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			mensaje = "Se elimino correctamente el equipo con Id: %s";
			log.info(String.format(mensaje,id));
			response.put("mensaje", String.format(mensaje,id));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NO_CONTENT);
		}else {
			mensaje = "Imposible eliminar ya que no existe el equipo telefonico con id: %d";
			log.info(String.format(mensaje, id));
			response.put("mensaje", String.format(mensaje, id));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

	}

}
