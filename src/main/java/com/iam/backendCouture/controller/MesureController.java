package com.iam.backendCouture.controller;

import com.iam.backendCouture.entities.Client;
import com.iam.backendCouture.entities.Mesure;
import com.iam.backendCouture.repository.ClientRepository;
import com.iam.backendCouture.repository.MesureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth/v1/mesure")
@CrossOrigin(origins = "http://localhost:4200")
public class MesureController {
	@Autowired
	private MesureRepository mesureRepository ;
	@Autowired
	private ClientRepository clientRepository;
	@GetMapping("/mesures")
	public List<Mesure> getAllMesures(){
		return mesureRepository.findAll();
	}
//	@PostMapping("/createmeasure")
//	public ResponseEntity<Mesure> createClient(@RequestBody Mesure mesure) {
//		Client client = mesure.getClient();
//		Long clientId = client.getIdClient();
//		Client existingClient = clientRepository.findById(clientId).orElse(null);
//
//		if (existingClient != null) {
//			mesure.setClient(existingClient);
//			Mesure createdMesure = mesureRepository.save(mesure);
//			return ResponseEntity.status(HttpStatus.CREATED).body(createdMesure);
//		} else {
//			Mesure m= mesureRepository.save(mesure);
//			return ResponseEntity.status(HttpStatus.CREATED).body(m);
//		}
//
//	}
	@PostMapping("/createmeasure")
	public ResponseEntity<Mesure> createClient(@RequestBody Mesure mesure) {
		Mesure createdMesure = mesureRepository.save(mesure);

		Client client = mesure.getClient();
		if (client != null) {
			client.setMesure(createdMesure);
			clientRepository.save(client);
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(createdMesure);
	}

	//	@GetMapping("/{id}")
//	public ResponseEntity<Mesure> getMesureById(@PathVariable("id") Long id) {
//		Optional<Mesure> entite = mesureRepository.findById(id);
//		if (entite.isPresent()) {
//			return ResponseEntity.ok(entite.get());
//		} else {
//			return ResponseEntity.notFound().build();
//		}
//	}
@GetMapping("/{id}")
public ResponseEntity<Map<String, Object>> getClientWithMesureById(@PathVariable("id") Long id) {
	Optional<Client> clientEntite = clientRepository.findById(id);

	if (clientEntite.isPresent()) {
		Client client = clientEntite.get();
		Mesure mesure = client.getMesure();

		Map<String, Object> response = new HashMap<>();
		response.put("client", client);
		response.put("mesure", mesure);

		return ResponseEntity.ok(response);
	} else {
		return ResponseEntity.notFound().build();
	}
}


	@PutMapping("/{id}")
	public ResponseEntity<Mesure> updateMesure(@PathVariable("id") Long id, @RequestBody Mesure mesure) {
		Optional<Mesure> entiteExistante = mesureRepository.findById(id);
		if (entiteExistante.isPresent()) {
			mesure.setIdMesure(id);
			Mesure entiteMiseAJour = mesureRepository.save(mesure);
			return ResponseEntity.ok(entiteMiseAJour);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMesureById(@PathVariable("id") Long id) {
		Optional<Mesure> entite = mesureRepository.findById(id);
		if (entite.isPresent()) {
			mesureRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
