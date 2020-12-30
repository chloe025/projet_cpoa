package test;


import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dao.CategorieDAO;
import dao.enumeration.Persistance;
import dao.factory.DAOFactory;
import dao.mysql.MySQLCategorieDAO;
import modele.Categorie;


public class TestListeMemoireCategorie {

	private CategorieDAO dao;
	Categorie categorie;
	
	@Before
	public void setUp(){
		categorie =  new Categorie(0, "TestCategorie", "TestCategorie.png");
		dao = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getCategorieDAO();
		assertNotNull(dao);
		assertNotNull(dao.getAllCategories());
	}
	
	/*
	 * **********************************
	 * 		  Tests fonctionnels		*
	 * **********************************
	 */
	
	// Test du getById
	
	@Test
	public void testGetById() {
		// 1. Création de la catégorie 
		assertTrue(dao.create(categorie));
		assertNotNull(dao.getById(categorie.getId()));
	}
	
	// Test de l'ajout d'une catégorie
	
	@Test
	public void testCreate() {
		// Nombre de categories avant insertion
		int size = dao.getAllCategories().size();
		categorie.setId(0);	
		assertTrue(dao.create(categorie));
		// Vérification que nous avons une catégeorie en plus dans la liste : 
		assertEquals(size + 1, dao.getAllCategories().size());
	}
	
	// Test de la modification d'une catégorie
	
	@Test
	public void testUpdate() {
		// 1. Création de la catégorie 
		assertTrue(dao.create(categorie));
		// 2. Vérification que la catégorie existe
		Categorie categorieRead = dao.getById(categorie.getId());
		assertEquals(categorie, categorieRead);
		// 3. Modification de la catégorie
		categorieRead.setTitre("Titre");
		assertTrue(dao.update(categorieRead));
	}
	
	// Test de la suppression d'une catégorie
	
	@Test
	public void testDelete() {
		// 1. Création de la catégorie
		assertTrue(dao.create(categorie));
		// 2. Lecture de la catégorie
		categorie = dao.getById(categorie.getId());
		// 3. Suppression de la catégorie
		dao.delete(categorie);
	}
	
	// Test de l'affichage de la liste de produits
	
	@Test
    public void testFindAll() {
            dao.create(categorie);
            List<Categorie> listeCategorie = dao.getAllCategories();
            assertTrue(!listeCategorie.isEmpty());
    }
	
	/*
	 * **********************************
	 * 		 	 Tests fail 			*
	 * **********************************
	 */
	
	// Test de l'erreur lors de l'ajout d'une categorie
	
		@Test
		public void testFail() {
			try {
			categorie.setTitre(""); // On donne a la categorie un titre vide
			assertTrue(dao.create(categorie));
			fail("Exception non lanc�e !");
			}
			catch (Exception e) {
			}
		}
	
	// Test de l'erreur lors de la modification d'une categorie
	
		@Test
		public void testUpdateFail() {
			try {
				// 1. Creation de la categorie
				assertTrue(dao.create(categorie));
				// 2. Verification que la categorie existe
				Categorie categorieRead = dao.getById(categorie.getId());
				assertEquals(categorie, categorieRead);
				// 3. Modification de la categorie
				categorieRead.setTitre(""); // On modifie le titre en un titre vide
				assertTrue(dao.update(categorieRead));
				fail("Exception non lanc�e !");
			}
			catch (Exception e){
			}
		}
	
	// Test de l'erreur lors de la suppression d'une categorie
	
		@Test
		public void testDeleteFail() {
			try {
			assertTrue(dao.delete(categorie)); // On supprime la categorie alors qu'elle n'existe pas encore dans la bdd
			fail("Exception non lanc�e !");
			}
			catch (Exception e) {
			}
			
		}
	
}
