package com.iam.backendCouture.controller;

import com.iam.backendCouture.entities.Client;
import com.iam.backendCouture.exception.RessourceNotFoundException;
import com.iam.backendCouture.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth/v1/client")
@CrossOrigin(origins = "http://localhost:4200")
public class ClientController {
	@Autowired
	private ClientRepository clientRepository;
	@GetMapping("/clients")
	public List<Client>getAllClients(){
		return clientRepository.findAll();
	}
	@PostMapping("/createclient")
	public ResponseEntity<Client> createClient(@RequestBody Client client) {
		Client c= clientRepository.save(client);
		return ResponseEntity.status(HttpStatus.CREATED).body(c);
	}
	@GetMapping("/{id}")
	public ResponseEntity<Client> getClienById(@PathVariable("id") Long id) {
		Optional<Client> entite = clientRepository.findById(id);
		if (entite.isPresent()) {
			return ResponseEntity.ok(entite.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	@PutMapping("/{id}")
	public ResponseEntity<Client> mettreAJourEntite(@PathVariable("id") Long id, @RequestBody Client client) {
		Optional<Client> entiteExistante = clientRepository.findById(id);
		if (entiteExistante.isPresent()) {
			client.setIdClient(id);
			Client entiteMiseAJour = clientRepository.save(client);
			return ResponseEntity.ok(entiteMiseAJour);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteClientById(@PathVariable("id") Long id) {
		Optional<Client> entite = clientRepository.findById(id);
		if (entite.isPresent()) {
			clientRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
