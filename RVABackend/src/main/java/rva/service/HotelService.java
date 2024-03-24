package rva.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rva.model.Destinacija;
import rva.model.Hotel;
import rva.repository.HotelRepository;

@Service
public class HotelService {
	
	@Autowired
	private HotelRepository repo;
	
	public List<Hotel> getAll() {
		return repo.findAll();
	}
	
	public Optional<Hotel> getById(long id) {
		return repo.findById(id);
	}
	
	public Optional<List<Hotel>> getByNaziv(String naziv) {
		return Optional.of(repo.findByNazivContainingIgnoreCase(naziv));
	}
	
	//Pretraga po broju zvezdica
	public Optional<List<Hotel>> getByBrojZvezdica(int brojZvezdica) {
		return Optional.of(repo.findByBrojZvezdicaOrderById(String.valueOf(brojZvezdica)));
	}
	
	//Pretraga po referenci koja predstavlja strani kljuc
	public Optional<List<Hotel>> getByDestinacija(Destinacija destinacija) {
		return Optional.of(repo.findByDestinacija(destinacija));
	}
	
	public Hotel save(Hotel hotel) {
		return repo.save(hotel);
	}
	
	public boolean existsById(long id) {
		if(getById(id).isPresent())
			return true;
		else
			return false;
	}
	
	public void deleteById(long id) {
		repo.deleteById(id);
	}

}
