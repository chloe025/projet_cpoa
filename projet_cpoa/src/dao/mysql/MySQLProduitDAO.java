package dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connexion.Connexion;
import dao.ProduitDAO;
import modele.Produit;

public class MySQLProduitDAO implements ProduitDAO {
	
	String url = "url";
	String login = "login";
	String pwd = "passeword";
	
	private static MySQLProduitDAO instance;

	public static ProduitDAO getInstance() {
		if (instance == null) {
			instance = new MySQLProduitDAO();
		}
		return instance;
	}

	@Override
	public Produit getById(int id) {
		Produit produit =null;
		try {
			Connection laConnexion = Connexion.createConnexion(url, login, pwd);
			Statement requete = laConnexion.createStatement();
			ResultSet res = requete.executeQuery("select * from	Produit where id_produit ="+id + ";");
			while (res.next()) {
				produit = new Produit(id,res.getString("nom"),res.getString("description"), res.getDouble("tarif"), res.getString("visuel"),res.getInt("id_categorie"));
						
			}
		}
		catch (SQLException sqle) {
			System.out.println("Pb select" + sqle.getMessage());
		}
		return produit;
	}

	@Override
	public boolean create(Produit object) {
		int nbLignes = 0;
		try {
			Connection laConnexion = Connexion.createConnexion(url, login, pwd);
			PreparedStatement res = laConnexion.prepareStatement("insert into `Produit` (`nom`, `description`, `tarif`, `visuel`, `id_categorie`) values (?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
			res.setString(1,object.getNom());
			res.setString(2,object.getDescription());
			res.setDouble(3,object.getTarif());
			res.setString(4,object.getVisuel());
			boolean idClient = verifIdCategorie(object.getCategorie());
	        if (idClient)
	            res.setInt(5, object.getCategorie());
	        else
	        	throw new IllegalArgumentException("Aucune catégorie ne possède cet identifiant");
			nbLignes = res.executeUpdate();
			ResultSet rs = res.getGeneratedKeys();
			if (rs.next()) {
				object.setId(rs.getInt(1));
			}
		} 
		catch (SQLException sqle) {
			System.out.println("Pb create Produit" + sqle.getMessage());
		}
		return nbLignes==1; 
	}
	
	

	@Override
	public boolean update(Produit object) {
		 int nbLignes = 0;
	     try {
	    	 Connection laConnexion = Connexion.createConnexion(url, login, pwd);
	    	 PreparedStatement res = laConnexion.prepareStatement("update `Produit` set `nom`=?, `description`=?, `tarif`=?, `visuel`=?, `id_categorie`=? where `id_produit`=?;", Statement.RETURN_GENERATED_KEYS);
	         res.setString(1,object.getNom());
	         res.setString(2,object.getDescription());
	         res.setDouble(3,object.getTarif());
	         res.setString(4,object.getVisuel());
	         boolean idClient = verifIdCategorie(object.getCategorie());
		        if (idClient)
		            res.setInt(5, object.getCategorie());
		        else
		        	throw new IllegalArgumentException("Aucune catégorie ne possède cet identifiant");
	         res.setInt(6, object.getId());
	         nbLignes = res.executeUpdate();
	         ResultSet rs = res.getGeneratedKeys();
	         if (rs.next()) {
	        	 object.setId(rs.getInt(1));
	         }
	     }
	     catch (SQLException sqle) {
	    	 System.out.println("Pb update Categorie" + sqle.getMessage());
	     }
	     return nbLignes==1; 
	}

	@Override
	public boolean delete(Produit object) {
		int nbLignes = 0;
		try {
			Connection laConnexion = Connexion.createConnexion(url, login, pwd);
			PreparedStatement res = laConnexion.prepareStatement("delete from Produit where `id_produit`=?;", Statement.RETURN_GENERATED_KEYS);
			boolean idProd = verifIdProduit(object.getId());
	        if (idProd)
	            res.setInt(1, object.getId());
	        else
	        	throw new IllegalArgumentException("Aucun produit ne possède cet identifiant");
			nbLignes = res.executeUpdate();
			ResultSet rs = res.getGeneratedKeys();
			if (rs.next()) {
				object.setId(rs.getInt(1));
			}
		} 
		catch (SQLException sqle) {
			System.out.println("Pb delete Produit" + sqle.getMessage());
			}
		return nbLignes==1;
	}

	@Override
	public List<Produit> getAllProduits() {
		ArrayList<Produit> prod = new ArrayList<Produit>();
		try {
			Connection laConnexion = Connexion.createConnexion(url, login, pwd);
			Statement requete = laConnexion.createStatement();
			ResultSet res  = requete.executeQuery("select * from `Produit`");
			while (res.next()) {
				Produit produit = new Produit();
				produit.setId(res.getInt(1));
				produit.setNom(res.getString(2));
				produit.setDescription(res.getString(3));
				produit.setTarif(res.getDouble(4));
				produit.setVisuel(res.getString(5));
				produit.setCategorie(res.getInt(6)); 
				prod.add(produit);
			}
			
		}
		catch (SQLException sqle) {
			System.out.println("Pb select" + sqle.getMessage());
			}
		return prod; 
	}
	
	// Fonction qui vérifie si l'id sélectionné existe dans la bdd
		private boolean verifIdProduit(int produit) {
			boolean existe = false;
	 		
			try {
	            Connection laConnexion = Connexion.createConnexion(url, login, pwd);
	            Statement requete = laConnexion.createStatement();    
	            ResultSet res = requete.executeQuery("select id_produit from `Produit` "); 
	            
	            while (res.next()) {
	            	if (produit == res.getInt(1) ) existe = true;
	            }
	            
	        } catch (SQLException sqle) {
	            System.out.println("Pb select " + sqle.getMessage());
	        }
	        
	        return existe;
		 }
	
	// Fonction qui vérifie si la categorie existe	
		private boolean verifIdCategorie(int categorie) {
			boolean existe = false;
	 		
			try {
	            Connection laConnexion = Connexion.createConnexion(url, login, pwd);
	            Statement requete = laConnexion.createStatement();    
	            ResultSet res = requete.executeQuery("select id_categorie from `Categorie` "); 
	            
	            while (res.next()) {
	            	if (categorie == res.getInt(1) ) existe = true;
	            }
	            
	        } catch (SQLException sqle) {
	            System.out.println("Pb select " + sqle.getMessage());
	        }
	        
	        return existe;
		 }
}
