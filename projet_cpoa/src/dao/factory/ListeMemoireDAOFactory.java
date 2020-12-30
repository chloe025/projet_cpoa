package dao.factory;

import dao.CategorieDAO;
import dao.ClientDAO;
import dao.CommandeDAO;
import dao.LigneCommandeDAO;
import dao.ProduitDAO;
import dao.listememoire.ListeMemoireCategorieDAO;
import dao.listememoire.ListeMemoireClientDAO;
import dao.listememoire.ListeMemoireCommandeDAO;
import dao.listememoire.ListeMemoireLigneCommandeDAO;
import dao.listememoire.ListeMemoireProduitDAO;

public class ListeMemoireDAOFactory extends DAOFactory {

	@Override
	public CategorieDAO getCategorieDAO() {
		return ListeMemoireCategorieDAO.getInstance();
	}

	@Override
	public ProduitDAO getProduitDAO() {
		return ListeMemoireProduitDAO.getInstance();
	}

	@Override
	public ClientDAO getClientDAO() {
		return ListeMemoireClientDAO.getInstance();
	}

	@Override
	public CommandeDAO getCommandeDAO() {
		return ListeMemoireCommandeDAO.getInstance();
	}

	@Override
	public LigneCommandeDAO getLigneCommandeDAO() {
		return ListeMemoireLigneCommandeDAO.getInstance();
	}

}
