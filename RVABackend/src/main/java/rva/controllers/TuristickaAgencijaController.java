package rva.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rva.model.TuristickaAgencija;
import rva.service.TuristickaAgencijaService;

@CrossOrigin
@RestController
@RequestMapping("TuristickaAgencija")
public class TuristickaAgencijaController {
	
	@Autowired
	private TuristickaAgencijaService service;
	
	@GetMapping("/hello")
	public String sayHello() {
		return "Hello There!";
	}
	
	@GetMapping
	public ResponseEntity<List<TuristickaAgencija>> getAllTuristickaAgencija() {
		return ResponseEntity.ok(service.getAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getTuristickaAgencijaById(@PathVariable long id) {
		if(service.existsById(id))
			return ResponseEntity.ok(service.getById(id));
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource with requested ID: " + id + " does not exist");
	}
	
	@GetMapping("/Naziv/{naziv}")
	public ResponseEntity<?> getTuristickaAgencijaByNaziv(@PathVariable String naziv) {
		if(service.findByNaziv(naziv).get().isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource with requested Naziv: " + naziv + " does not exist");
		else
			return ResponseEntity.ok(service.findByNaziv(naziv).get());
	}
	
	@PostMapping
	public ResponseEntity<?> createTuristickaAgencija(@RequestBody TuristickaAgencija agencija) {
		TuristickaAgencija savedAgencija;
		if(!service.existsById(agencija.getId())) {
			savedAgencija = service.save(agencija);
		}
		else {
			List<TuristickaAgencija> list = service.getAll();
			long najvecaVrednost = 1;
			for(int i = 0; i < list.size(); i++) {
				if(najvecaVrednost <= list.get(i).getId()) {
					najvecaVrednost = list.get(i).getId();
				}
				
				if(i == list.size() - 1) {
					najvecaVrednost ++;
				}
			}
			agencija.setId(najvecaVrednost);
			savedAgencija = service.save(agencija);
		}
		URI uri = URI.create("/TuristickaAdencija/" + savedAgencija.getId());
		return ResponseEntity.created(uri).body(savedAgencija);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateTuristickaAgencija(@RequestBody TuristickaAgencija agencija, @PathVariable long id) {
		if(service.existsById(id)) {
			agencija.setId(id);
			TuristickaAgencija savedAgencija = service.save(agencija);
			return ResponseEntity.ok(savedAgencija);
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Resource with requested ID: " + id + " has not been found");
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteTuristickaAgencija(@PathVariable long id) {
		if(service.existsById(id)) {
			service.deleteById(id);
			return ResponseEntity.ok("Resource with ID: " + id + " has been deleted");
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Resource with ID: " + id + " has not been found");
		}
	}

}
