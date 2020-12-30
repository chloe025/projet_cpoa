package dao.listememoire;

import java.util.ArrayList;
import java.util.List;

import dao.ProduitDAO;
import modele.Produit;

public class ListeMemoireProduitDAO implements ProduitDAO {
	private static ListeMemoireProduitDAO instance;
	private List<Produit> donnees;

		
	//creation instance singleton
	public static ListeMemoireProduitDAO getInstance() {
		if (instance == null) {
			instance = new ListeMemoireProduitDAO();
		}
		return instance;
	}

	private ListeMemoireProduitDAO() {
		this.donnees = new ArrayList<Produit>();
		this.donnees.add(new Produit(2, "Sonic", "Inspiré par la saga Séga (c''est plus fort que toi !), un pull 100% gamer qui te permettra de faire baver d''envie tes petits camarades de jeu.", 41.5, "pull1.png", 1));
		this.donnees.add(new Produit(6, "La chaleur des rennes", "Classique mais efficace, un bonnet dont l''élégance n''est pas à souligner, il vous grattera comme il faut !", 15., "bonnet0.png", 2));	
	}


	@Override
	public boolean create(Produit objet) {
		objet.setId(6);
		while (this.donnees.contains(objet)) {
			objet.setId(objet.getId() + 1);
		}
		boolean ok = this.donnees.add(objet);
		
		return ok;
	}

	@Override
	public boolean update(Produit objet) {

		int idx = this.donnees.indexOf(objet);
		if (idx == -1) {
			throw new IllegalArgumentException("Tentative de modification d'un produit inexistant");
		} 
		else {	
			this.donnees.set(idx, objet);
		}
		
		return true;
	}

	@Override
	public boolean delete(Produit objet) {
		Produit supprime;
		
		int idx = this.donnees.indexOf(objet);
		if (idx == -1) {
			throw new IllegalArgumentException("Tentative de suppression d'un produit inexistant");
		} 
		else {
			supprime = this.donnees.remove(idx);
		}
		
		return objet.equals(supprime);
	}

	@Override
	public Produit getById(int id) {
		int idx = this.donnees.indexOf(new Produit(id, "test", "test", 1., "test", 1));
		if (idx == -1) {
			throw new IllegalArgumentException("Aucune categorie ne possede cet identifiant");
		} 
		else {
			return this.donnees.get(idx);
		}
	}

	@Override
	public List<Produit> getAllProduits() {
		return (ArrayList<Produit>) this.donnees;
	}

}

