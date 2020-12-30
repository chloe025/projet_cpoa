package dao.listememoire;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


import dao.CommandeDAO;
import dao.enumeration.Persistance;
import dao.factory.DAOFactory;
import modele.Client;
import modele.Commande;

public class ListeMemoireCommandeDAO implements CommandeDAO {
	private static ListeMemoireCommandeDAO instance;
	private List<Commande> donnees;
	DateTimeFormatter formatage = DateTimeFormatter.ofPattern("dd/MM/yyyy");


	public static ListeMemoireCommandeDAO getInstance() {
		if (instance == null) {
			instance = new ListeMemoireCommandeDAO();
		}
		return instance;
	}

	private ListeMemoireCommandeDAO() {
		this.donnees = new ArrayList<Commande>();
		String maStringDate = "02/09/2020";
		LocalDate dateDebut = LocalDate.parse(maStringDate, formatage);
		this.donnees.add(new Commande(1,dateDebut , 1));
		maStringDate = "30/08/2020";
		dateDebut = LocalDate.parse(maStringDate, formatage);
		this.donnees.add(new Commande(2, dateDebut, 1));
	}

	@Override
	public boolean create(Commande objet) {
		objet.setNum(3);
		boolean ok;
		boolean idClient = verifIdClient(objet.getClient());
		if (idClient == true){
			while (this.donnees.contains(objet)) {
				objet.setNum(objet.getNum() + 1);
			}
			ok = this.donnees.add(objet);
			}
		else throw new IllegalArgumentException("Aucun client ne possède cet identifiant");
		return ok;
	}

	private boolean verifIdClient(int client) {
		DAOFactory daos = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE);
		List<Client> liste = daos.getClientDAO().getAllClients();
		int i = 0;
		boolean trouve = false;
        while (trouve == false &&  i != liste.size()) {
            if (liste.get(i).getNo() == client)trouve = true;
            else i++;
            }
		return trouve;
	}

	@Override
	public boolean update(Commande objet) {
		int idx = this.donnees.indexOf(objet);
		if (idx == -1) {
			throw new IllegalArgumentException("Tentative de modification d'une commande inexistante");
		} 
		else {
			boolean idClient = verifIdClient(objet.getClient());
			if (idClient == true){
				this.donnees.set(idx, objet);
			}
			else throw new IllegalArgumentException("Aucun client ne possède cet identifiant");
		}
		
		return true;
	}

	@Override
	public boolean delete(Commande objet) {
		Commande supprime;
		
		int idx = this.donnees.indexOf(objet);
		if (idx == -1) {
			throw new IllegalArgumentException("Tentative de suppression d'une commande inexistante");
		} 
		else {
			supprime = this.donnees.remove(idx);
		}
		
		return objet.equals(supprime);
	}

	@Override
	public Commande getById(int id) {
		int idx = this.donnees.indexOf(new Commande(id, LocalDate.parse("01/01/0001", formatage) , 1));
		if (idx == -1) {
			throw new IllegalArgumentException("Aucune commande ne possède cet identifiant");
		} 
		else {
			return this.donnees.get(idx);
		}
	}

	@Override
	public List<Commande> getAllCommandes() {
		return (ArrayList<Commande>) this.donnees;
	}
}