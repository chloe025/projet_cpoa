package dao;

import java.util.List;

import modele.Commande;

public interface CommandeDAO extends DAO<Commande> {

	List<Commande> getAllCommandes();
}
