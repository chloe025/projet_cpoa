package modele;
/*
 * Classe Metier POJO Categorie
*/


public class Categorie {
	
	private int id;
	private String titre;
	private String visuel;

	public Categorie(int id, String titre, String visuel) {
		this.setId(id);
		this.setTitre(titre);
		this.setVisuel(visuel);
	}
	
	

	public Categorie() {
		
	}



	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setTitre(String titre) {
		if (titre==null || titre.trim().length()==0) {
			throw new IllegalArgumentException("Titre de la categorie vide !");
		}
		this.titre = titre;
	}
	
	public String getTitre() {
		return this.titre;
	}
	
	public void setVisuel(String visuel) {
		if (visuel==null || visuel.trim().length()==0) {
			throw new IllegalArgumentException("Visuel vide !");
		}
		this.visuel = visuel;
	}
	
	public String getVisuel() {
		return this.visuel;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categorie other = (Categorie) obj;
		if (id != other.id)
			return false;
		return true;
	}



	@Override
	public String toString() {
		return titre;
	}
	public String toString2() {
		return "\nCategorie : id = " + id + ", titre = " + titre + ", visuel = " + visuel;
	}
}
