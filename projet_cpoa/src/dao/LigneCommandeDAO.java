package dao;

import java.util.List;

import modele.LigneCommande;

public interface LigneCommandeDAO{
	
	public abstract boolean create(LigneCommande object); // Create
	public abstract LigneCommande getById(int idCde, int idProd); // Read
	public abstract boolean update(LigneCommande object); // Update
	public abstract boolean delete(LigneCommande object); // Delete
    List<LigneCommande> getAllLigneCommande();
	
}
