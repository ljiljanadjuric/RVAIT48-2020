package rva.controllers;

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

import rva.model.Destinacija;
import rva.model.Hotel;
import rva.service.DestinacijaService;
import rva.service.HotelService;

@CrossOrigin
@RestController
@RequestMapping("Hotel")
public class HotelController {
	
	@Autowired
	private HotelService service;
	
	@Autowired
	private DestinacijaService destinacijaService;
	
	@GetMapping
	public ResponseEntity<List<Hotel>> getAllHotels() {
		return ResponseEntity.ok(service.getAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getHotelById(@PathVariable long id) {
		if(service.getById(id).isPresent())
			return ResponseEntity.ok(service.getById(id).get());
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Resource with requested ID: " + id + "has not been found");
	}
	
	@GetMapping("/Naziv/{naziv}")
	public ResponseEntity<?> getHotelByNaziv(@PathVariable String naziv) {
		if(service.getByNaziv(naziv).get().isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Resources with requested Naziv: " + naziv + " have not been found");
		else
			return ResponseEntity.ok(service.getByNaziv(naziv).get());
	}
	
	@GetMapping("/BrojZvezdica/{brojZvezdica}")
	public ResponseEntity<?> getHotelByBrojZvezdica(@PathVariable int brojZvezdica) {
		Optional<List<Hotel>> list = service.getByBrojZvezdica(brojZvezdica);
		if(!list.get().isEmpty())
				return ResponseEntity.ok(list.get());
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Resources with broj zvezdica equal to: " + brojZvezdica + " have not been found");
	}
	
	@GetMapping("/Destinacija/{id}")
	public ResponseEntity<?> getHotelByDestinacija(@PathVariable long id) {
		Optional<Destinacija> destinacija = destinacijaService.getById(id);
		if(destinacija.isPresent()) {
			List<Hotel> list = service.getByDestinacija(destinacija.get()).get();
			if(!list.isEmpty())
				return ResponseEntity.ok(list);
			else
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("Resources with requested foreign key destinacija: " + id + " do not exist");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Destinacija with ID: " + id + " does not exist");
		}
	}
	
	@PostMapping
	public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) {
		Hotel savedHotel;
		if(!service.existsById(hotel.getId())) {
			savedHotel = service.save(hotel);
		} else {
			List<Hotel> list = service.getAll();
			long najvecaVrednost = 1;
			for(int i = 0; i < list.size(); i++) {
				if(najvecaVrednost <= list.get(i).getId()) {
					najvecaVrednost = list.get(i).getId();
				}
				
				if(i == list.size() - 1) {
					najvecaVrednost ++;
				}
			}
			hotel.setId(najvecaVrednost);
			savedHotel = service.save(hotel);
		}
		URI uri = URI.create("/Hotel/" + savedHotel.getId());
		return ResponseEntity.created(uri).body(savedHotel);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateHotel(@RequestBody Hotel hotel, @PathVariable long id) {
		if(service.existsById(id)) {
			hotel.setId(id);
			Hotel savedHotel = service.save(hotel);
			return ResponseEntity.ok(savedHotel);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Resource with requested ID: " + id + " has not been found");
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteHotel(@PathVariable long id) {
		if(service.existsById(id)) {
			service.deleteById(id);
			return ResponseEntity.ok("Resource with ID: " + id + " has been deleted");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Resource with ID: " + id + " has not been found");
		}
	}

}