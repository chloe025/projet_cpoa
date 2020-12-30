package dao;

import java.util.List;

import modele.Produit;

public interface ProduitDAO extends DAO<Produit>{
	
	List<Produit> getAllProduits();
}

