package rva.controllers;
//upravlja podacima nad bazom

import java.net.URI;
import java.util.List;
import java.util.Optional;

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

import rva.model.Aranzman;
import rva.model.Hotel;
import rva.model.TuristickaAgencija;
import rva.service.AranzmanService;
import rva.service.HotelService;
import rva.service.TuristickaAgencijaService;

@CrossOrigin
@RestController
@RequestMapping("Aranzman")

public class AranzmanController {
	
	@Autowired
	private AranzmanService service;
	
	@Autowired
	private HotelService hotelService;
	
	@Autowired
	private TuristickaAgencijaService agencijaService;
	
	@GetMapping
	public ResponseEntity<List<Aranzman>> getAllAranzman() {
		//za stvaranje http odgovora
		return ResponseEntity.ok(service.getAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getAranzmanById(@PathVariable long id) {
		if(service.getById(id).isPresent())
			return ResponseEntity.ok(service.getById(id).get());
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Resource with requsetsed ID: " + id + " has not been found");
	}
	
	@GetMapping("/Placeno")
	public ResponseEntity<?> getAranzmanByPlacenoTrue() {
		if(service.getByPlacenoTrue().get().isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Resources with requested value have not been found");
		else
			return ResponseEntity.ok(service.getByPlacenoTrue().get());
	}
	
	@GetMapping("/Hotel/{id}")
	public ResponseEntity<?> getAranzmanByHotel(@PathVariable long id) {
		Optional<Hotel> hotel = hotelService.getById(id);
		if(hotel.isPresent()) {
			List<Aranzman> list = service.getByHotel(hotel.get()).get();
			if(!list.isEmpty())
				return ResponseEntity.ok(list);
			else
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("Resources with requested foreign key hotel: " + id + " do not exist");
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Hotel with ID: " + id + " does not exist");
		}
	}
	
	@GetMapping("/TuristickaAgencija/{id}")
	public ResponseEntity<?> getAranzmanByAgencija(@PathVariable long id) {
		Optional<TuristickaAgencija> agencija = agencijaService.getById(id);
		if(agencija.isPresent()) {
			List<Aranzman> list = service.getByAgencija(agencija.get()).get();
			if(!list.isEmpty())
				return ResponseEntity.ok(list);
			else
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("Resources with requested foreign key agencija: " + id + " do not exits");
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Agencija with ID: " + id + " does not exist");
		}
	}
	
	@PostMapping
	public ResponseEntity<Aranzman> createAranzman(@RequestBody Aranzman aranzman) {
		Aranzman savedAranzman;
		if(!service.existsById(aranzman.getId())) {
			savedAranzman = service.save(aranzman);
		}
		else {
			List<Aranzman> list = service.getAll();
			long najvecaVrednost = 1;
			for(int i = 0; i < list.size(); i++) {
				if(najvecaVrednost <= list.get(i).getId()) {
					najvecaVrednost = list.get(i).getId();
				}
				
				if(i == list.size() - 1) {
					najvecaVrednost ++;
				}
			}
			aranzman.setId(najvecaVrednost);
			savedAranzman = service.save(aranzman);
		}
		URI uri = URI.create("/Aranzman/" + savedAranzman.getId());
		return ResponseEntity.created(uri).body(savedAranzman);
	}
	//response entity je povratna vrednost odn odgovorS
	//da li izvreseno 200 ili 404
	@PutMapping("/{id}")
	//kada dobijemo odg smjestamo ga ovde u rrquest body i onda izvrsaavamo
	public ResponseEntity<?> updateAranzman(@RequestBody Aranzman aranzman, @PathVariable long id) {
		if(service.existsById(id)) {
			aranzman.setId(id);
			Aranzman savedAranzman = service.save(aranzman);
			return ResponseEntity.ok(savedAranzman);
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Resource with requested ID: " + id + " has not been found");
		}
	}
	
	@DeleteMapping("/{id}")
	//ovo npr po idu, soapulS
	public ResponseEntity<String> deleteAranzman(@PathVariable long id) {
		//da li postoji sa odredjenim id
		if(service.existsById(id)) {
			service.deleteById(id);
			return ResponseEntity.ok("Resource with ID: " + id + " has been deleted");
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Resource with requsted ID: " + id + " has not been found");
		}
	}

}

