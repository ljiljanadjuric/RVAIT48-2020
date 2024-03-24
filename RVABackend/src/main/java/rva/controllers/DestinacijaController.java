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

import rva.model.Destinacija;
import rva.service.DestinacijaService;

@CrossOrigin
@RestController
@RequestMapping("Destinacija")
public class DestinacijaController {
	
	@Autowired
	private DestinacijaService service;
	
	@GetMapping
	public ResponseEntity<List<Destinacija>> getAllDestinations() {
		return ResponseEntity.ok(service.getAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getDestinacijaById(@PathVariable long id) {
		if(service.existsById(id))
			return ResponseEntity.ok(service.getById(id));
		else 
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Resource with requested ID: " + id + " has not been found");
	}
	
	@GetMapping("/Mesto/{mesto}")
	public ResponseEntity<?> getDestinacijaByMesto(@PathVariable String mesto) {
		if(service.getByMesto(mesto).get().isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Resources with requested Mesto: " + mesto + " have not been found");
		else 
			return ResponseEntity.ok(service.getByMesto(mesto).get());
	}
	
	@GetMapping("/Drzava/{drzava}")
	public ResponseEntity<?> getDestinacijaByDrzava(@PathVariable String drzava) {
		if(service.getByDrzava(drzava).get().isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Resources with requested Drzava: " + drzava + " have not been found");
		else 
			return ResponseEntity.ok(service.getByDrzava(drzava).get());
	}
	
	@PostMapping
	public ResponseEntity<Destinacija> createDestinacija(@RequestBody Destinacija destinacija) {
		Destinacija savedDestinacija;
		if(!service.existsById(destinacija.getId())) {
			savedDestinacija = service.save(destinacija);
		}
		else {
			List<Destinacija> list = service.getAll();
			long najvecaVrednost = 1;
			for(int i = 0; i < list.size(); i++) {
				if(najvecaVrednost <= list.get(i).getId()) {
					najvecaVrednost = list.get(i).getId();
				}
				
				if(i == list.size() - 1) {
					najvecaVrednost ++;
				}
			}
			destinacija.setId(najvecaVrednost);
			savedDestinacija = service.save(destinacija);
		}
		URI uri = URI.create("/Destinacija/" + savedDestinacija.getId());
		return ResponseEntity.created(uri).body(savedDestinacija);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateDestinacija(@RequestBody Destinacija destinacija, @PathVariable long id) {
		if(service.existsById(id)) {
			destinacija.setId(id);
			Destinacija savedDestinacija = service.save(destinacija);
			return ResponseEntity.ok(savedDestinacija);
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Resource with requested ID: " + id + " has not been found");
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteDestinacija(@PathVariable long id) {
		if(service.existsById(id)) {
			service.deleteById(id);
			return ResponseEntity.ok("Resource with requested ID: " + id + " has been deleted");
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Resource with requested ID: " + id + " has not been found");
		}
	}

}
