package rva.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rva.model.Destinacija;
import rva.repository.DestinacijaRepository;

@Service
public class DestinacijaService {
	
	@Autowired
	private DestinacijaRepository repo;
	
	public List<Destinacija> getAll() {
		return repo.findAll();
	}
	
	public Optional<Destinacija> getById(long id) {
		return repo.findById(id);
	}
	
	public Optional<List<Destinacija>> getByMesto(String mesto) {
		Optional<List<Destinacija>> lista = Optional.of(repo.findByMestoContainingIgnoreCase(mesto));
		return lista;
	}
	
	public Optional<List<Destinacija>> getByDrzava(String drzava) {
		Optional<List<Destinacija>> lista = Optional.of(repo.findByDrzavaContainingIgnoreCase(drzava));
		return lista;
	}
	
	public Destinacija save(Destinacija destinacija) {
		return repo.save(destinacija);
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
