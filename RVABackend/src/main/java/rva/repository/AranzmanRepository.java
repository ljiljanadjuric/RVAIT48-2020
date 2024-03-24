package rva.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rva.model.Aranzman;
import rva.model.Hotel;
import rva.model.TuristickaAgencija;

@Repository
public interface AranzmanRepository extends JpaRepository<Aranzman, Long> {
	
	List<Aranzman> findByPlacenoIsTrue();
	
	
	List<Aranzman> findByHotel(Hotel hotel);
	
	
	List<Aranzman> findByTuristickaAgencija(TuristickaAgencija turistickaAgencija);


}
