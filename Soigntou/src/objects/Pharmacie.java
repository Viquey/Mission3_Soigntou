package objects;

import java.io.Serializable;

import android.location.Location;

public class Pharmacie implements Serializable, Comparable {

	private String commune;
	private String voie;
	private String rs;
	private String rslongue;
	private String typvoie;
	private int telephone;
	private int telecopie;
	private int numvoie;
	private Double lat;
	private Double lng;
	private int cp;
	private String adresse;
	private double distanceFromMyPosition;

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
		this.rs = rs;
		this.rslongue = rslongue;
		this.typvoie = typvoie;
		this.telephone = telephone;
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
		double distance = distanceFromMyPosition;
		String extDistance = "m";
		String pharmaDistance = "";
		if (distance > 1000) {
			distance = distance / 1000;
			distance = (double) Math.round(distance * 10)/10;
			extDistance = "km";
			pharmaDistance = distance+" "+extDistance;
		}
		else {
			int distance2 =  (int) Math.round(distance);
			pharmaDistance = distance2+" "+extDistance;
		}
		return rslongue+" - "+pharmaDistance;
	}

	public String getAdresse() {
		return adresse;
	}

	public void makeAdresse(){
		String adresseMade = "";

		if (this.numvoie != 0 ) {
			adresseMade = this.numvoie+"";
		}
		if (!this.typvoie.equals("Non renseigné")) {
			adresseMade = adresseMade +" "+ this.typvoie;
		}
		if (!this.voie.equals("Non renseigné")) {
			adresseMade = adresseMade +" "+ this.voie;
		}

		if (adresseMade.equals("")) {
			adresseMade = "Non renseigné";
		}

		this.adresse = adresseMade;

	}

	public double getDistanceFromMyPosition() {
		return distanceFromMyPosition;
	}

	public void makeDistance(Location maPosition) {

		Location locPharm = new Location("pharmacie");
		locPharm.setLatitude(this.lat);

		locPharm.setLongitude(this.lng);
		double distanceCalculated = locPharm.distanceTo(maPosition);

		this.distanceFromMyPosition = distanceCalculated;
	}

	@Override
	public int compareTo(Object o) {

		Pharmacie f = (Pharmacie)o;

		if (distanceFromMyPosition > f.distanceFromMyPosition) {
			return 1;
		}
		else if (distanceFromMyPosition <  f.distanceFromMyPosition) {
			return -1;
		}
		else {
			return 0;
		}

	}

}
