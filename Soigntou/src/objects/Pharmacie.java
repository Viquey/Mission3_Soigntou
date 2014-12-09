package objects;

import java.io.Serializable;

public class Pharmacie implements Serializable {
	
	private String commune;
	private String voie;
	private int nofinesset;
	private String rs;
	private String rslongue;
	private String compldistrib;
	private String typvoie;
	private int telephone;
	private int nofinessej;
	private int telecopie;
	private int numvoie;
	private Double lat;
	private Double lng;
	private int cp;
	
	public Pharmacie() {
		super();
	}

	public Pharmacie(String commune, String voie, int nofinesset, String rs,
			String rslongue, String compldistrib, String typvoie,
			int telephone, int nofinessej, int telecopie, int numvoie,
			Double lat, Double lng, int cp) {
		super();
		this.commune = commune;
		this.voie = voie;
		this.nofinesset = nofinesset;
		this.rs = rs;
		this.rslongue = rslongue;
		this.compldistrib = compldistrib;
		this.typvoie = typvoie;
		this.telephone = telephone;
		this.nofinessej = nofinessej;
		this.telecopie = telecopie;
		this.numvoie = numvoie;
		this.lat = lat;
		this.lng = lng;
		this.cp = cp;
	}

	public String getCommune() {
		return commune;
	}

	public void setCommune(String commune) {
		this.commune = commune;
	}

	public String getVoie() {
		return voie;
	}

	public void setVoie(String voie) {
		this.voie = voie;
	}

	public int getNofinesset() {
		return nofinesset;
	}

	public void setNofinesset(int nofinesset) {
		this.nofinesset = nofinesset;
	}

	public String getRs() {
		return rs;
	}

	public void setRs(String rs) {
		this.rs = rs;
	}

	public String getRslongue() {
		return rslongue;
	}

	public void setRslongue(String rslongue) {
		this.rslongue = rslongue;
	}

	public String getCompldistrib() {
		return compldistrib;
	}

	public void setCompldistrib(String compldistrib) {
		this.compldistrib = compldistrib;
	}

	public String getTypvoie() {
		return typvoie;
	}

	public void setTypvoie(String typvoie) {
		this.typvoie = typvoie;
	}

	public int getTelephone() {
		return telephone;
	}

	public void setTelephone(int telephone) {
		this.telephone = telephone;
	}

	public int getNofinessej() {
		return nofinessej;
	}

	public void setNofinessej(int nofinessej) {
		this.nofinessej = nofinessej;
	}

	public int getTelecopie() {
		return telecopie;
	}

	public void setTelecopie(int telecopie) {
		this.telecopie = telecopie;
	}

	public int getNumvoie() {
		return numvoie;
	}

	public void setNumvoie(int numvoie) {
		this.numvoie = numvoie;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public int getCp() {
		return cp;
	}

	public void setCp(int cp) {
		this.cp = cp;
	}

	@Override
	public String toString() {
		return rslongue;
	}
	
	
}
