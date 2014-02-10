package chat.client.gui;

public class Contexte {
	
	private String longitude;
	private String latitude;
	private String adresse;
	private String langue;
	private String date;
	private String typeReseau;
	private String typeDispositif;
	private String tailleEcran;
	private int niveau;
	
	public Contexte(String longitude, String latitude, String adresse, String langue,
			String date, String typeReseau, String typeDispositif,
			String tailleEcran, int niveau) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
		this.adresse = adresse;
		this.langue = langue;
		this.date = date;
		this.typeReseau = typeReseau;
		this.typeDispositif = typeDispositif;
		this.tailleEcran = tailleEcran;
		this.niveau = niveau;
	}
	
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLangue() {
		return langue;
	}
	public void setLangue(String langue) {
		this.langue = langue;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTypeReseau() {
		return typeReseau;
	}
	public void setTypeReseau(String typeReseau) {
		this.typeReseau = typeReseau;
	}
	public String getTypeDispositif() {
		return typeDispositif;
	}
	public void setTypeDispositif(String typeDispositif) {
		this.typeDispositif = typeDispositif;
	}
	public String getTailleEcran() {
		return tailleEcran;
	}
	public void setTailleEcran(String tailleEcran) {
		this.tailleEcran = tailleEcran;
	}
	public int getNiveau() {
		return niveau;
	}
	public void setNiveau(int niveau) {
		this.niveau = niveau;
	}
	
	@Override
	public String toString() {
		return " longitude=" + longitude + "\n latitude=" + latitude
				+ "\n adresse=" + adresse + "\n langue=" + langue + "\n date="
				+ date + "\n typeReseau=" + typeReseau + "\n typeDispositif="
				+ typeDispositif + "\n tailleEcran=" + tailleEcran + "\n niveau="
				+ niveau ;
	}
}
