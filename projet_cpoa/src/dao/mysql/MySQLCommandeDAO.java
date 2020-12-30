package dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import connexion.Connexion;
import dao.CommandeDAO;
import modele.Commande;

public class MySQLCommandeDAO implements CommandeDAO {
	
	private static MySQLCommandeDAO instance;
	String url = "url";
	String login = "login";
	String pwd = "passeword";
	
	public static CommandeDAO getInstance() {
		if (instance == null) {
			instance = new MySQLCommandeDAO();
		}
		return instance;
	}
	

	@Override
	public Commande getById(int id) {
		Commande commande =null;
		try {
			Connection laConnexion = Connexion.createConnexion(url, login, pwd);
			Statement requete = laConnexion.createStatement();
			ResultSet res = requete.executeQuery("select * from	Commande where id_commande ="+id + ";");
			while (res.next()) {
				commande = new Commande(id,res.getDate("date_commande").toLocalDate(),res.getInt("id_client"));
			}
		}
		catch (SQLException sqle) {
			System.out.println("Pb select" + sqle.getMessage());
		}
		return commande; 
	}

	@Override
	public boolean create(Commande object) {
		int nbLignes = 0;
		try {
			Connection laConnexion = Connexion.createConnexion(url, login, pwd);
			PreparedStatement res = laConnexion.prepareStatement("insert into `Commande` (`date_commande`, `id_client`) values (?, ?);", Statement.RETURN_GENERATED_KEYS);
			
	            
			res.setDate(1,java.sql.Date.valueOf(object.getDate()));
			boolean idClient = verifIdClient(object.getClient());
	        if (idClient)
	            res.setInt(2, object.getClient());
	        else
	        	throw new IllegalArgumentException("Aucun client ne possède cet identifiant");
			nbLignes = res.executeUpdate();
			ResultSet rs = res.getGeneratedKeys();
			if (rs.next()) {
				object.setNum(rs.getInt(1));
			}
		} 
		catch (SQLException sqle) {
			System.out.println("Pb create Commande" + sqle.getMessage());
		}
		return nbLignes==1; 
	}


	@Override
	public boolean update(Commande object) {
	        int nbLignes = 0;
	        try {
	            Connection laConnexion = Connexion.createConnexion(url, login, pwd);
	            PreparedStatement res = laConnexion.prepareStatement("update `Commande` set `date_commande`=?, `id_client`=? where `id_commande`=?;", Statement.RETURN_GENERATED_KEYS);
	            res.setDate(1,java.sql.Date.valueOf(object.getDate()));
	            boolean idClient = verifIdClient(object.getClient());
		        if (idClient)
		            res.setInt(2, object.getClient());
		        else
		        	throw new IllegalArgumentException("Aucun client ne possède cet identifiant");
	            res.setInt(3, object.getNum());
	            nbLignes = res.executeUpdate();
	            ResultSet rs = res.getGeneratedKeys();
				if (rs.next()) {
					object.setNum(rs.getInt(1));
				}
	        } 
	        catch (SQLException sqle) {
	            System.out.println("Pb update Commande" + sqle.getMessage());
	        }
	        return nbLignes==1; 
	}

	@Override
	public boolean delete(Commande object) {
		int nbLignes = 0;
		try {
			Connection laConnexion = Connexion.createConnexion(url, login, pwd);
			PreparedStatement res = laConnexion.prepareStatement("delete from Commande where `id_commande`=?;", Statement.RETURN_GENERATED_KEYS);
			boolean idCommande = verifIdCommande(object.getNum());
	        if (idCommande)
	            res.setInt(1, object.getNum());
	        else
	        	throw new IllegalArgumentException("Aucune commande ne possède cet identifiant");
			nbLignes = res.executeUpdate();
			ResultSet rs = res.getGeneratedKeys();
			if (rs.next()) {
				object.setNum(rs.getInt(1));
			}
		} 
		catch (SQLException sqle) {
			System.out.println("Pb delete Commande" + sqle.getMessage());
		}
		return nbLignes==1; 
	}

	@Override
	public List<Commande> getAllCommandes() {
			ArrayList<Commande> cl = new ArrayList<Commande>();
			try {
				Connection laConnexion = Connexion.createConnexion(url, login, pwd);
				Statement requete = laConnexion.createStatement();
				ResultSet res  = requete.executeQuery("select * from `Commande` ORDER BY id_client");
				while (res.next()) {
					Commande cd = new Commande();
					cd.setNum(res.getInt(1));
					cd.setDate(res.getDate(2).toLocalDate());
					cd.setClient(res.getInt(3));
					cl.add(cd);
				}
			}
			catch (SQLException sqle) {
				System.out.println("Pb select" + sqle.getMessage());
				}
			return cl; 
		}

	
	// Fonction qui vérifie si l'id sélectionné existe dans la bdd
		private boolean verifIdCommande(int commande) {
			boolean existe = false;
	 		
			try {
	            Connection laConnexion = Connexion.createConnexion(url, login, pwd);
	            Statement requete = laConnexion.createStatement();    
	            ResultSet res = requete.executeQuery("select id_commande from `Commande` "); 
	            
	            while (res.next()) {
	            	if (commande == res.getInt(1) ) existe = true;
	            }
	            
	        } catch (SQLException sqle) {
	            System.out.println("Pb select " + sqle.getMessage());
	        }
	        
	        return existe;
		 }
	
	// Fonction qui vérifie si le client existe	
		private boolean verifIdClient(int client) {
			boolean existe = false;
	 		
			try {
	            Connection laConnexion = Connexion.createConnexion(url, login, pwd);
	            Statement requete = laConnexion.createStatement();    
	            ResultSet res = requete.executeQuery("select id_client from `Client` "); 
	            
	            while (res.next()) {
	            	if (client == res.getInt(1) ) existe = true;
	            }
	            
	        } catch (SQLException sqle) {
	            System.out.println("Pb select " + sqle.getMessage());
	        }
	        
	        return existe;
		 }
}
