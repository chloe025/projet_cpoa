package dao.listememoire;

import java.util.ArrayList;
import java.util.List;

import dao.LigneCommandeDAO;
import dao.enumeration.Persistance;
import dao.factory.DAOFactory;
import modele.Commande;
import modele.LigneCommande;
import modele.Produit;

public class ListeMemoireLigneCommandeDAO implements LigneCommandeDAO{
	private static ListeMemoireLigneCommandeDAO instance;
	private List<LigneCommande> donnees;


	public static ListeMemoireLigneCommandeDAO getInstance() {
		if (instance == null) {
			instance = new ListeMemoireLigneCommandeDAO();
		}
		return instance;
	}

	private ListeMemoireLigneCommandeDAO() {
		this.donnees = new ArrayList<LigneCommande>();
		this.donnees.add(new LigneCommande(1, 2, 2, 41.5));
		this.donnees.add(new LigneCommande(1, 6, 1, 15));
		this.donnees.add(new LigneCommande(2, 12, 4, 35));
	}


	@Override
	public boolean create(LigneCommande objet) {
		boolean idCde = verifIdCde(objet.getIdCommande());
		boolean idProd = verifIdProd(objet.getIdProd());
		boolean ok;
		
		if (idCde == true && idProd == true) {
			objet.setIdCommande(objet.getIdCommande()); 
			objet.setIdProd(objet.getIdProd());
			ok = this.donnees.add(objet);
		}
		else if (idCde == false) throw new IllegalArgumentException("Aucune commande ne possède cet identifiant");
		else throw new IllegalArgumentException("Aucun produit ne possède cet identifiant");
		return ok;
	}

	private boolean verifIdProd(int idProd) {
		DAOFactory daos = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE);
		List<Produit> liste = daos.getProduitDAO().getAllProduits();
		int i = 0;
		boolean trouve = false;
        while (trouve == false &&  i != liste.size()) {
            if (liste.get(i).getId() == idProd)trouve = true;
            else i++;
            }
		return trouve;
	}

	private boolean verifIdCde(int idCommande) {
		DAOFactory daos = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE);
		List<Commande> liste = daos.getCommandeDAO().getAllCommandes();
		int i = 0;
		boolean trouve = false;
        while (trouve == false &&  i != liste.size()) {
            if (liste.get(i).getNum() == idCommande)trouve = true;
            else i++;
            }
		return trouve;
	}

	public boolean update(LigneCommande objet) {
		
		int idx = this.donnees.indexOf(objet);
		if (idx == -1) {
			throw new IllegalArgumentException("Tentative de modification d'une commande inexistante");
		}
		else {
			this.donnees.set(idx, objet);
		}
		return true;
	}

	public boolean delete(LigneCommande objet) {
		LigneCommande supprime;
		
		int idx = this.donnees.indexOf(objet);
		if (idx == -1) {
			throw new IllegalArgumentException("Tentative de suppression d'une commande inexistante");
		} 
		else {
			supprime = this.donnees.remove(idx);
		}
		
		return objet.equals(supprime);
	}
	
	public LigneCommande getById(int id, int id2) {
		int idx = this.donnees.indexOf(new LigneCommande(id, id2, 1, 1));
		if (idx == -1) {
			throw new IllegalArgumentException("Aucune commande ne possède cet identifiant");
		} 
		else {
			return this.donnees.get(idx);
		}
	}

	@Override
	public List<LigneCommande> getAllLigneCommande() {
		return (ArrayList<LigneCommande>) this.donnees;
	}

	


	
}
