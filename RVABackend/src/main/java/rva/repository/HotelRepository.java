package rva.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rva.model.Destinacija;
import rva.model.Hotel;

@Repository
//Anotacija @Repository: Ova anotacija označava da je AranzmanRepository 
//Springov komponentni repozitorij. Spring će automatski otkriti ovu anotaciju
//i stvoriti instancu ovog repozitorija kao Spring Bean.
public interface HotelRepository extends JpaRepository<Hotel, Long> {
	//Nasljeđivanje JpaRepository<Aranzman, Long>: AranzmanRepository nasljeđuje sučelje
	//JpaRepository, što pruža osnovne CRUD (create, read, update, delete) operacije za entitet Aranzman
	//. Također, definira se da će ključni atribut biti tipa Long.
	
	List<Hotel> findByNazivContainingIgnoreCase(String naziv);
	
	List<Hotel> findByBrojZvezdicaOrderById(String brojZvezdica);
	
	List<Hotel> findByDestinacija(Destinacija destinacija);

}