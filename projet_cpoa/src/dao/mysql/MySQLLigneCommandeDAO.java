package dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connexion.Connexion;
import dao.LigneCommandeDAO;
import modele.LigneCommande;

public class MySQLLigneCommandeDAO implements LigneCommandeDAO{
	private static MySQLLigneCommandeDAO instance;
	String url = "url";
	String login = "login";
	String pwd = "passeword";
	
	public static LigneCommandeDAO getInstance() {
		if (instance == null) {
			instance = new MySQLLigneCommandeDAO();
		}
		return instance;
	}

	
	@Override
	public LigneCommande getById(int id, int id2) {
		LigneCommande lignecommande =null;
		try {
			Connection laConnexion = Connexion.createConnexion(url, login, pwd);
			Statement requete = laConnexion.createStatement();
			ResultSet res = requete.executeQuery("select * from	Ligne_commande where `id_commande` ="+id + ";");
			while (res.next()) {
				lignecommande = new LigneCommande(id,id2,res.getInt("quantite"), res.getDouble("tarif_unitaire"));
				System.out.println(lignecommande);			
			}
		}
		catch (SQLException sqle) {
			System.out.println("Pb select" + sqle.getMessage());
		}
		return lignecommande; 
	}

	@Override
	public boolean create(LigneCommande object) {
		int nbLignes = 0;
		try {
			Connection laConnexion = Connexion.createConnexion(url, login, pwd);
			PreparedStatement getAll = laConnexion.prepareStatement("select * from `Ligne_commande` ");
	        ResultSet getAllres = getAll.executeQuery();
	        
	        while (getAllres.next()) {
	            LigneCommande lignecde1 = new LigneCommande(object.getIdCommande(), object.getIdProd());
	            LigneCommande lignecde2= new LigneCommande(getAllres.getInt(1), getAllres.getInt(2));
	            
	            if (lignecde1.equals(lignecde2)) 
	                throw new IllegalArgumentException("Ligne de commande deja existante !");
	        }
	        
			PreparedStatement res = laConnexion.prepareStatement("insert into `Ligne_commande` (`id_commande`, id_produit, `quantite`, `tarif_unitaire`) values (?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
			boolean idCom = verifIdCom(object.getIdCommande());
	        if (idCom)
	            res.setInt(1, object.getIdCommande());
	        else
	            throw new IllegalArgumentException("Aucune commande ne possede cet identifiant");
	        
	        
	        boolean idProd = verifIdProd(object.getIdProd());
	        if (idProd)
	            res.setInt(2, object.getIdProd());
	        else
	            throw new IllegalArgumentException("Aucun produit ne possede cet identifiant");
			res.setInt(3,object.getQuantite());
			res.setDouble(4, object.getTarif());
			
			nbLignes = res.executeUpdate();
			ResultSet rs = res.getGeneratedKeys();
			if (rs.next()) {
				object.setIdCommande(rs.getInt(1));
			}
		} 
		catch (SQLException sqle) {
			System.out.println("Pb create LigneCommande" + sqle.getMessage());
		}
		return nbLignes==1; 
	}
	
	

	@Override
	public boolean update(LigneCommande object) {
	        int nbLignes = 0;
	        try {
	            Connection laConnexion = Connexion.createConnexion(url, login, pwd);
	            PreparedStatement res = laConnexion.prepareStatement("update `Ligne_commande` set `quantite`=?, `tarif_unitaire`=? where (`id_commande`=? and `id_produit`=?);", Statement.RETURN_GENERATED_KEYS);  
				res.setInt(1,object.getQuantite());
				res.setDouble(2, object.getTarif());
				boolean idCom = verifIdCom(object.getIdCommande());
		        if (idCom)
		            res.setInt(3, object.getIdCommande());
		        else
		            throw new IllegalArgumentException("Aucune commande ne possede cet identifiant");
		        boolean idProd = verifIdProd(object.getIdProd());
		        if (idProd)
		            res.setInt(4, object.getIdProd());
		        else
		            throw new IllegalArgumentException("Aucun produit ne possede cet identifiant"); 
	            nbLignes = res.executeUpdate();
	            ResultSet rs = res.getGeneratedKeys();
				if (rs.next()) {
					object.setIdCommande(rs.getInt(1));
				}
	        } 
	        catch (SQLException sqle) {
	            System.out.println("Pb update LigneCommande" + sqle.getMessage());
	        }
	        return nbLignes==1; 
	}

	@Override
	public boolean delete(LigneCommande object) {
		int nbLignes = 0;
		try {
			Connection laConnexion = Connexion.createConnexion(url, login, pwd);
			PreparedStatement res = laConnexion.prepareStatement("delete from Ligne_commande where (`id_commande`=? and `id_produit`=?);", Statement.RETURN_GENERATED_KEYS);
			boolean idCom = verifIdCom(object.getIdCommande());
	        if (idCom)
	            res.setInt(1, object.getIdCommande());
	        else
	            throw new IllegalArgumentException("Aucune commande ne possede cet identifiant");
	        
	        
	        boolean idProd = verifIdProd(object.getIdProd());
	        if (idProd)
	            res.setInt(2, object.getIdProd());
	        else
	            throw new IllegalArgumentException("Aucun produit ne possede cet identifiant");
			nbLignes = res.executeUpdate();
			ResultSet rs = res.getGeneratedKeys();
			if (rs.next()) {
				object.setIdCommande(rs.getInt(1));
			}
		} 
		catch (SQLException sqle) {
			System.out.println("Pb delete LigneCommande" + sqle.getMessage());
		}
		return nbLignes==1; 
	}

	@Override
	public List<LigneCommande> getAllLigneCommande() {
		ArrayList<LigneCommande> cl = new ArrayList<LigneCommande>();
		try {
			Connection laConnexion = Connexion.createConnexion(url, login, pwd);
			Statement requete = laConnexion.createStatement();
			ResultSet res  = requete.executeQuery("select * from `Ligne_commande` ORDER BY id_commande");
			while (res.next()) {
				LigneCommande cd = new LigneCommande();
				cd.setIdCommande(res.getInt(1));
				cd.setIdProd(res.getInt(2));
				cd.setQuantite(res.getInt(3));
				cd.setTarif(res.getDouble(4));
				cl.add(cd);
			}
		}
		catch (SQLException sqle) {
			System.out.println("Pb select" + sqle.getMessage());
			}
		return cl; 
	}

	// Fonction qui vérifie si l'id de commande existe
	private boolean verifIdCom(int commande) {
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
	
	// Fonction qui vérifie si l'id de produit existe
	private boolean verifIdProd(int produit) {
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
}
