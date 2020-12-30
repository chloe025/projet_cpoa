package dao.listememoire;

import java.util.ArrayList;
import java.util.List;

import dao.CategorieDAO;
import modele.Categorie;

public class ListeMemoireCategorieDAO implements CategorieDAO {
	private static ListeMemoireCategorieDAO instance;
	private List<Categorie> donnees;

	public static CategorieDAO getInstance() {
		if (instance == null) {
			instance = new ListeMemoireCategorieDAO();
		}
		return instance;
	}

	private ListeMemoireCategorieDAO() {
		this.donnees = new ArrayList<Categorie>();
		this.donnees.add(new Categorie(1, "Pulls", "pulls.png"));
		this.donnees.add(new Categorie(2, "Bonnets", "bonnets.png"));
	}

	@Override
	public boolean create(Categorie objet) {
		objet.setId(3);
		while (this.donnees.contains(objet)) {
			objet.setId(objet.getId() + 1);
		}
		boolean ok = this.donnees.add(objet);
		return ok;
	}

	@Override
	public boolean update(Categorie objet) {
		int idx = this.donnees.indexOf(objet);
		if (idx == -1) {
			throw new IllegalArgumentException("Tentative de modification d'une categorie inexistante");
		} else {
			this.donnees.set(idx, objet);
		}
		return true;
	}

	@Override
	public boolean delete(Categorie objet) {
		Categorie supprime;
		int idx = this.donnees.indexOf(objet);
		if (idx == -1) {
			throw new IllegalArgumentException("Tentative de suppression d'une categorie inexistante");
		} else {
			supprime = this.donnees.remove(idx);
		}
		return objet.equals(supprime);
	}

	@Override
	public Categorie getById(int id) {
		int idx = this.donnees.indexOf(new Categorie(id, "test", "test.png"));
		if (idx == -1) {
			throw new IllegalArgumentException("Aucune categorie ne poss�de cet identifiant");
		} else {
			return this.donnees.get(idx);
		}
	}

	@Override
	public List<Categorie> getAllCategories() {
		return (ArrayList<Categorie>) this.donnees;
	}


}

