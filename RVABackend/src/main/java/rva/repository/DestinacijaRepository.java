package rva.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rva.model.Destinacija;

@Repository
public interface DestinacijaRepository extends JpaRepository<Destinacija, Long> {
	
	List<Destinacija> findByMestoContainingIgnoreCase(String mesto);
	
	List<Destinacija> findByDrzavaContainingIgnoreCase(String drzava);

}
