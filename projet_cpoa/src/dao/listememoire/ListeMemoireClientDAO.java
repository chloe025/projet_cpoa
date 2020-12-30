package dao.listememoire;

import java.util.ArrayList;
import java.util.List;

import dao.ClientDAO;
import modele.Client;

public class ListeMemoireClientDAO implements ClientDAO {
	private static ListeMemoireClientDAO instance;
	private List<Client> donnees;


	public static ListeMemoireClientDAO getInstance() {
		if (instance == null) {
			instance = new ListeMemoireClientDAO();
		}
		return instance;
	}

	private ListeMemoireClientDAO() {
		this.donnees = new ArrayList<Client>();
		this.donnees.add(new Client(1, "LAROCHE", "Pierre","Metz"));
	}


	@Override
	public boolean create(Client objet) {
		objet.setNo(2);
		while (this.donnees.contains(objet)) {
			objet.setNo(objet.getNo() + 1);
		}
		boolean ok = this.donnees.add(objet);
		return ok;
	}

	@Override
	public boolean update(Client objet) {
		int idx = this.donnees.indexOf(objet);
		if (idx == -1) {
			throw new IllegalArgumentException("Tentative de modification d'un client inexistant");
		} 
		else {
			this.donnees.set(idx, objet);
		}
		
		return true;
	}

	@Override
	public boolean delete(Client objet) {
		Client supprime;
		
		int idx = this.donnees.indexOf(objet);
		if (idx == -1) {
			throw new IllegalArgumentException("Tentative de suppression d'une categorie inexistante");
		} 
		else {
			supprime = this.donnees.remove(idx);
		}
		
		return objet.equals(supprime);
	}

	@Override
	public Client getById(int id) {
		int idx = this.donnees.indexOf(new Client(id, "test", "test","test"));
		if (idx == -1) {
			throw new IllegalArgumentException("Aucune categorie ne possede cet identifiant");
		}
		else {
			return this.donnees.get(idx);
		}
	}

	@Override
	public List<Client> getAllClients() {
		return (ArrayList<Client>) this.donnees;
	}

}