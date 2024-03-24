package rva.model;
import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Hotel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "HOTEL_ID_GENERATOR", sequenceName = "HOTEL_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HOTEL_ID_GENERATOR")
	private long id;
	private String naziv;
	private String brojZvezdica;
	private String opis;
	
	
	@ManyToOne
	@JoinColumn(name="destinacija")
	private Destinacija destinacija; 
	
	@OneToMany(mappedBy = "hotel")
	private List<Aranzman> aranzmani;
	
	
	
	

	public Hotel() {
		
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getBrojZvezdica() {
		return brojZvezdica;
	}

	public void setBrojZvezdica(String brojZvezdica) {
		this.brojZvezdica = brojZvezdica;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	
	}
