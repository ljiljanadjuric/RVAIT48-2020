package rva.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rva.model.TuristickaAgencija;
import rva.repository.TuristickaAgencijaRepository;

@Service
public class TuristickaAgencijaService {
	
	@Autowired
	private TuristickaAgencijaRepository repo;
	
	public List<TuristickaAgencija> getAll() {
		return repo.findAll();
	}
	
	public Optional<TuristickaAgencija> getById(long id) {
		return repo.findById(id);
	}
	
	public Optional<List<TuristickaAgencija>> findByNaziv(String naziv) {
		Optional<List<TuristickaAgencija>> lista = Optional.of(repo.findByNazivContainingIgnoreCase(naziv));
		return lista;
	}
	
	public TuristickaAgencija save(TuristickaAgencija turistickaAgencija) {
		return repo.save(turistickaAgencija);
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
