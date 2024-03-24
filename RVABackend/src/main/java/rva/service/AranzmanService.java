package rva.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rva.model.Aranzman;
import rva.model.Hotel;
import rva.model.TuristickaAgencija;
import rva.repository.AranzmanRepository;

@Service
public class AranzmanService {
	
	@Autowired

	private AranzmanRepository repo;
	
	public List<Aranzman> getAll() {
		return repo.findAll();
	}
	
	public Optional<Aranzman> getById(long id) {
		return repo.findById(id);
	}
	
	public Optional<List<Aranzman>> getByPlacenoTrue() {
		return Optional.of(repo.findByPlacenoIsTrue());
	}
	
	public Optional<List<Aranzman>> getByHotel(Hotel hotel) {
		return Optional.of(repo.findByHotel(hotel));
	}
	
	public Optional<List<Aranzman>> getByAgencija(TuristickaAgencija agencija) {
		return Optional.of(repo.findByTuristickaAgencija(agencija));
	}
	
	public Aranzman save(Aranzman aranzman) {
		return repo.save(aranzman);
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
