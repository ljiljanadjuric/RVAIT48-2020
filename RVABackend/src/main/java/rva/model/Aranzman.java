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
public class Aranzman implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "ARANZMAN_ID_GENERATOR", sequenceName = "ARANZMAN_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ARANZMAN_ID_GENERATOR")
	
	private long id;
	private double  ukupnaCena;
	private boolean placeno;
	private java.util.Date  datumRezervacije;
	//veza sa drugim entitetima: 
	@ManyToOne
	@JoinColumn(name="hotel")
	
	private Hotel hotel; 
	
	@ManyToOne
	@JoinColumn(name="turistickaAgencija")
	private TuristickaAgencija turistickaAgencija; 
	
	
	public Aranzman() {
		
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}

	public double getUkupnaCena() {
		return ukupnaCena;
	}

	public void setUkupnaCena(double ukupnaCena) {
		this.ukupnaCena = ukupnaCena;
	}

	public boolean isPlaceno() {
		return placeno;
	}

	public void setPlaceno(boolean placeno) {
		this.placeno = placeno;
	}

	public java.util.Date getDatumRezervacije() {
		return datumRezervacije;
	}

	public void setDatumRezervacije(java.util.Date datumRezervacije) {
		this.datumRezervacije = datumRezervacije;
	}

}

