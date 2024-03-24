package rva.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rva.model.TuristickaAgencija;

@Repository
public interface TuristickaAgencijaRepository extends JpaRepository<TuristickaAgencija, Long> {
	
	List<TuristickaAgencija> findByNazivContainingIgnoreCase(String naziv);

}
