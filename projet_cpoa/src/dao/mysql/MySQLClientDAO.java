package dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connexion.Connexion;
import dao.ClientDAO;
import modele.Client;

public class MySQLClientDAO implements ClientDAO {

	String url = "url";
	String login = "login";
	String pwd = "passeword";
	
	private static MySQLClientDAO instance;
	
	public static MySQLClientDAO getInstance() {
		if (instance==null) {
			instance = new MySQLClientDAO();
		}
		return instance;
	}
	
	
	@Override
	public Client getById(int id) {
		Client client =null;
		try {
			Connection laConnexion = Connexion.createConnexion(url, login, pwd);
			Statement requete = laConnexion.createStatement();
			ResultSet res = requete.executeQuery("select * from	Client where id_client ="+id + ";");
			while (res.next()) {
				client = new Client(id, res.getString("nom"), res.getString("prenom"), res.getString("adr_ville"));
			}
		}
		catch (SQLException sqle) {
			System.out.println("Pb select" + sqle.getMessage());
		}
		return client;
	}

	@Override
	public boolean create(Client object) {
		int nbLignes = 0;
		try {
			Connection laConnexion = Connexion.createConnexion(url, login, pwd);
			PreparedStatement res = laConnexion.prepareStatement("insert into `Client` (`nom`, `prenom`, `identifiant`, `mot_de_passe`, `adr_numero`, `adr_voie`, `adr_code_postal`, `adr_ville`, `adr_pays`) values (?, ?, '', '', '', '', '', ?, '');", Statement.RETURN_GENERATED_KEYS);
			res.setString(1,object.getNom());
			res.setString(2,object.getPrenom());
			res.setString(3, object.getVille());
			nbLignes = res.executeUpdate();
			ResultSet rs = res.getGeneratedKeys();
			if (rs.next()) {
				object.setNo(rs.getInt(1));
			}
		} 
		catch (SQLException sqle) {
			System.out.println("Pb create Client" + sqle.getMessage());
		}
		return nbLignes==1; 
	}

	@Override
	public boolean update(Client object) {
		 int nbLignes = 0;
	     try {
	    	 Connection laConnexion = Connexion.createConnexion(url, login, pwd);
	         PreparedStatement res = laConnexion.prepareStatement("update `Client` set `nom`=?, `prenom`=?, `ville`=? where `id_client`=?;", Statement.RETURN_GENERATED_KEYS);
	         res.setString(1,object.getNom());
	         res.setString(2,object.getPrenom());
	         res.setString(3, object.getVille());
	         res.setInt(4, object.getNo());
	         nbLignes = res.executeUpdate();
	         ResultSet rs = res.getGeneratedKeys();
	         	if (rs.next()) {
	         		object.setNo(rs.getInt(1));
				}
	     } 
	     catch (SQLException sqle) {
	    	 System.out.println("Pb update Categorie" + sqle.getMessage());
	     }
	     return nbLignes==1; 
	}

	@Override
	public boolean delete(Client object) {
		int nbLignes = 0;
		try {
			Connection laConnexion = Connexion.createConnexion(url, login, pwd);
			PreparedStatement res = laConnexion.prepareStatement("delete from Client where `id_client`=?;", Statement.RETURN_GENERATED_KEYS);
			boolean idClient = verifIdClient(object.getNo());
	        if (idClient)
	            res.setInt(1, object.getNo());
	        else
	        	throw new IllegalArgumentException("Aucun client ne possède cet identifiant");
			nbLignes = res.executeUpdate();
			ResultSet rs = res.getGeneratedKeys();
			if (rs.next()) {
				object.setNo(rs.getInt(1));
			}
		} 
		catch (SQLException sqle) {
			System.out.println("Pb delete Client" + sqle.getMessage());
		}
		return nbLignes==1;
	}

	
	@Override
	public List<Client> getAllClients() {
		ArrayList<Client> cl = new ArrayList<Client>();
		try {
			Connection laConnexion = Connexion.createConnexion(url, login, pwd);
			Statement requete = laConnexion.createStatement();
			ResultSet res  = requete.executeQuery("select * from `Client`");
			while (res.next()) {
				Client client = new Client();
				client.setNo(res.getInt(1));
				client.setNom(res.getString(2));
				client.setPrenom(res.getString(3));
				client.setVille(res.getString(4));
				cl.add(client);
			}
			
		}
		catch (SQLException sqle) {
			System.out.println("Pb select" + sqle.getMessage());
			}
		return cl; 
	}
	
	// Fonction qui vérifie si l'id sélectionné existe dans la bdd
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