package modele;



public class Produit {
	private int id;
	private String nom;
	private String description;
	private Double tarif;
	private String visuel;
	// un prod est lie a une categorie et une seule
	private Integer categorie;

public Produit() {		
	}

public Produit(int id, String nom, String description, Double tarif, String visuel, Integer categorie) {
	super();
	this.id = id;
	this.nom = nom;
	this.description = description;
	this.tarif = tarif;
	this.visuel = visuel;
	this.categorie = categorie;
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public String getNom() {
	return nom;
}

public void setNom(String nom) {
	if (nom==null || nom.trim().length()==0) {
		throw new IllegalArgumentException("Nom de produit vide !");
	}
	this.nom = nom;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	if (description==null || description.trim().length()==0) {
		throw new IllegalArgumentException("Description du produit vide !");
	}
	this.description = description;
}

public double getTarif() {
	return tarif;
}

public void setTarif(double tarif) {
	if (tarif <= 0) {
		throw new IllegalArgumentException("Tarif nul ou inferieur a� 0 !");
	}
	this.tarif = tarif;
}

public String getVisuel() {
	return visuel;
}

public void setVisuel(String visuel) {
	if (visuel==null || visuel.trim().length()==0) {
		throw new IllegalArgumentException("Visuel du produit vide !");
	}
	this.visuel = visuel;
}

public int getCategorie() {
	return categorie;
}



/**
 * Ajouter une categorie.
 * 
 * @param categorie : un objet de type Categorie.
 */
public void setCategorie(int categorie) {
	this.categorie = categorie;
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
	Produit other = (Produit) obj;
	if (id != other.id)
		return false;
	return true;
}

public String toString() {
	return this.nom + " "+ this.tarif + " categorie : " + this.categorie;
}
public String toString2() {
	return "\nProduit = " + this.id + " nom : " +this.nom + ", description : " + this.description  + " tarif : " + this.tarif + ", visuel : " + this.visuel + " categorie : " + this.categorie;
}
}
