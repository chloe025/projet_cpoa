package modele;

import java.util.HashMap;
import java.util.Map;

public class LigneCommande {
	private int idCommande;
	private int idProd;
	private int quantite;
	private double tarif;
	
	private Map<Produit, Integer> produits;	// Ligne de commande : liste des produits.Une Commande contient '1 ou n' produits.

	/* @return le montant total du panier (ligne de commande). */
	public Double getMontantTotal() {
		// Parcours de la table des produits ...ligne de commande.
		Double resultat = 0d;
		for (Map.Entry<Produit, Integer> entry : produits.entrySet()) {
			// Pour chaque ligne de commande je recupere la cle(produit) et la valeur
			// (quantite).
			Produit produit = entry.getKey();
			Integer quantite = entry.getValue();
			resultat += (produit.getTarif() * quantite);
		}
		
		return resultat;
	}
	
	public LigneCommande(int idCommande, int idProd, int quantite, double tarif) {
		super();
		this.idCommande = idCommande;
		this.idProd = idProd;
		this.quantite = quantite;
		this.tarif = tarif;
	}

	public LigneCommande() {
	}

	public LigneCommande(int idCommande, int idProd) {
		this.idCommande = idCommande;
		this.idProd = idProd;
	}

	/* Methode qui permet d'ajouter un produit a la ligne de commande. */
	public void addProduit(Produit produit, Integer quantite) {
		if (produits == null) {
			produits = new HashMap<>();
		}
		produits.put(produit, quantite);
	}

	public void delProduit(Produit produit) {
		produits.remove(produit);
	}

	public int getIdCommande() {
		return idCommande;
	}

	public void setIdCommande(int idCommande) {
		this.idCommande = idCommande;
	}

	public int getIdProd() {
		return idProd;
	}

	public void setIdProd(int idProd) {
		this.idProd = idProd;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public double getTarif() {
		return tarif;
	}

	public void setTarif(double tarif) {
		this.tarif = tarif;
	}

	public Map<Produit, Integer> getProduits() {
		return produits;
	}

	public void setProduits(Map<Produit, Integer> produits) {
		this.produits = produits;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idCommande;
		result = prime * result + idProd;
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
		LigneCommande other = (LigneCommande) obj;
		if (idCommande != other.idCommande)
			return false;
		if (idProd != other.idProd)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Commande " + idCommande + " produit " + idProd;
	}
	public String toString2() {
		return "\nLigne Commande : id de la commande = " + idCommande + ", id produit = " + idProd + ", quantite commandee = " + quantite + " tarif unitaire = " + tarif;
	}
}
