package modele;

public class Client {
	protected int no;
	protected String nom;
	protected String prenom;
	protected String ville;

	public Client(int no, String nom,String prenom,String ville) {
		this.setNo(no);
		this.setNom(nom);
		this.setPrenom(prenom);
		this.setVille(ville);
		
	}
	public Client() {
		
	}

	public int getNo() {
		return this.no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		if (nom==null || nom.trim().length()==0) {
			throw new IllegalArgumentException("Nom de la personne vide !");
		}
		this.nom = nom;
	}

	public String getPrenom() {
		return this.prenom;
	}

	public void setPrenom(String prenom) {
		if (prenom==null || prenom.trim().length()==0) {
			throw new IllegalArgumentException("Prenom de la personne vide !");
		}
		this.prenom = prenom;
	}
	
	public String getVille() {
		return this.ville;
	}
	
	public void setVille(String ville) {
		if (ville==null || ville.trim().length()==0) {
			throw new IllegalArgumentException("Ville de la personne vide !");
		}
		this.ville = ville;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + no;
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
		Client other = (Client) obj;
		if (no != other.no)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return this.nom + " " + this.prenom;
	}
	public String toString2() {
		return "\nClient : no_id = " + this.no + " nom = " + this.nom + ", prenom = " + this.prenom + " ville = " + this.ville;
	}
}