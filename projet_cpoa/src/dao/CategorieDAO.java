package dao;


import java.util.List;

import javafx.util.Callback;
import modele.Categorie;

public interface CategorieDAO extends DAO<Categorie>{
	
	List<Categorie> getAllCategories();
	
}
