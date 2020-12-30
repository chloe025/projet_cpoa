package dao.mysql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import connexion.Connexion;
import dao.CategorieDAO;
import modele.Categorie;

public class MySQLCategorieDAO implements CategorieDAO {
	
	String url = "url";
	String login = "login";
	String pwd = "passeword";
	
	private static MySQLCategorieDAO instance;

	public static CategorieDAO getInstance() {
		if (instance == null) {
			instance = new MySQLCategorieDAO();
		}
		return instance;
	}
	
	@Override
	public Categorie getById(int id) {
		Categorie categorie =null;
		try {
			Connection laConnexion = Connexion.createConnexion(url, login, pwd);
			Statement requete = laConnexion.createStatement();
			ResultSet res = requete.executeQuery("select * from	Categorie where id_categorie ="+id + ";");
			
			while (res.next()) {
				categorie = new Categorie(id,res.getString("titre"),res.getString("visuel"));
			}
		}
		catch (SQLException sqle) {
			System.out.println("Pb select" + sqle.getMessage());
		}
		return categorie; 
	}

	@Override
	public boolean create(Categorie object) {
		int nbLignes = 0;
		try {
			Connection laConnexion = Connexion.createConnexion(url, login, pwd);
			PreparedStatement res = laConnexion.prepareStatement("insert into `Categorie` (`titre`, `visuel`) values (?, ?);", Statement.RETURN_GENERATED_KEYS);
			res.setString(1,object.getTitre());
			res.setString(2,object.getVisuel());
			nbLignes = res.executeUpdate();
			ResultSet rs = res.getGeneratedKeys();
			if (rs.next()) {
				object.setId(rs.getInt(1));
			}
		} 
		catch (SQLException sqle) {
			System.out.println("Pb create Categorie" + sqle.getMessage());
		}
		return nbLignes==1; 
		
	}
	
	@Override
	public boolean update(Categorie object) {
        int nbLignes = 0;
        try {
            Connection laConnexion = Connexion.createConnexion(url, login, pwd);
            PreparedStatement res = laConnexion.prepareStatement("update `Categorie` set `titre`=?, `visuel`=? where `id_categorie`=?;", Statement.RETURN_GENERATED_KEYS);
            res.setString(1,object.getTitre());;
            res.setString(2, object.getVisuel());
            res.setInt(3, object.getId());
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
	public boolean delete(Categorie object) {
		int nbLignes = 0;
		try {
			Connection laConnexion = Connexion.createConnexion(url, login, pwd);
			PreparedStatement res = laConnexion.prepareStatement("delete from Categorie where `id_categorie`=?;", Statement.RETURN_GENERATED_KEYS);
			boolean idCategorie = verifIdCategorie(object.getId());
	        if (idCategorie)
	            res.setInt(1, object.getId());
	        else
	        	throw new IllegalArgumentException("Aucune catégorie ne possède cet identifiant");
			nbLignes = res.executeUpdate();
			ResultSet rs = res.getGeneratedKeys();
			if (rs.next()) {
				object.setId(rs.getInt(1));
			}
		} 
		catch (SQLException sqle) {
			System.out.println("Pb delete Categorie" + sqle.getMessage());
		}
		return nbLignes==1; 
	}

	@Override
	public List<Categorie> getAllCategories() {
		ArrayList<Categorie> cat = new ArrayList<Categorie>();
		try {
			Connection laConnexion = Connexion.createConnexion(url, login, pwd);			
			Statement requete = laConnexion.createStatement();
			ResultSet res = requete.executeQuery("select * from `Categorie`");
			
			while (res.next()) {
				cat.add(new Categorie(res.getInt(1),res.getString(2), res.getString(3)));
			}
			
		}
		catch (SQLException sqle) {
			System.out.println("Pb select" + sqle.getMessage());
			}
		return cat; 
	}
	
	
	// Fonction qui vérifie si l'id sélectionné existe dans la bdd
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
