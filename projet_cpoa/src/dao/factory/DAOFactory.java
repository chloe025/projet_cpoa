package dao.factory;

import dao.CategorieDAO;
import dao.ClientDAO;
import dao.CommandeDAO;
import dao.LigneCommandeDAO;
import dao.ProduitDAO;
import dao.enumeration.Persistance;

public abstract class DAOFactory {
	public static DAOFactory getDAOFactory(Persistance cible) {
		DAOFactory daoF = null;
		switch (cible) {
		case MYSQL:
			daoF = new MySQLDAOFactory();
			break;
		case LISTE_MEMOIRE:
			daoF = new ListeMemoireDAOFactory();
			break;
			}
		return daoF;
		}
	public abstract CategorieDAO getCategorieDAO();
	public abstract ProduitDAO getProduitDAO();
	public abstract ClientDAO getClientDAO();
	public abstract CommandeDAO getCommandeDAO();
	public abstract LigneCommandeDAO getLigneCommandeDAO();
	}

