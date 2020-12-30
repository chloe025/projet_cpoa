package test;


import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dao.CategorieDAO;
import dao.enumeration.Persistance;
import dao.factory.DAOFactory;
import dao.mysql.MySQLCategorieDAO;
import modele.Categorie;


public class TestMySQLCategorie {

	private CategorieDAO dao;
	Categorie categorie;
	
	@Before
	public void setUp(){
		categorie =  new Categorie(0, "TestCategorie", "TestCategorie.png");
		dao = DAOFactory.getDAOFactory(Persistance.MYSQL).getCategorieDAO();
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
		// 1. Creation de la categorie dans la bdd
		assertTrue(dao.create(categorie));
		assertNotNull(dao.getById(categorie.getId()));
	}
	
	// Test de l'ajout d'une categorie
	
	@Test
	public void testCreate() {
		// Nombre de categories avant insertion
		int size = dao.getAllCategories().size();
		assertTrue(dao.create(categorie));
		// Verification que nous avons une categeorie en plus dans la liste : 
		assertEquals(size + 1, dao.getAllCategories().size());
	}
	
	// Test de la modification d'une categorie
	
	@Test
	public void testUpdate() {
		// 1. Creation de la categorie dans la bdd
		assertTrue(dao.create(categorie));
		// 2. Verification que la categorie existe bien dans la bdd
		Categorie categorieRead = dao.getById(categorie.getId());
		assertEquals(categorie, categorieRead);
		// 3. Modification de la categorie
		categorieRead.setTitre("Titre");
		assertTrue(dao.update(categorieRead));
		// 4. Verification en bdd
		assertEquals(categorieRead, dao.getById(categorie.getId()));
	}
	
	// Test de la suppression d'une categorie
	
	@Test
	public void testDelete() {
		// 1. Creation de la categorie dans la bdd
		assertTrue(dao.create(categorie));
		// 2. Lecture de la categorie
		categorie = dao.getById(categorie.getId());
		// 3. Suppression de la categorie dans la bdd
		dao.delete(categorie);
		// 4. Verification que la categorie n'existe plus dans la bdd
		assertNull(dao.getById(categorie.getId()));
	}
	
	// Test de l'affichage de la liste des categories
	
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
		public void testCreateFail() {
			try {
			categorie.setTitre(""); // On donne a la categorie un titre vide
			assertTrue(dao.create(categorie));
			fail("Exception non lancée !");
			}
			catch (Exception e) {
			}
		}
	
	// Test de l'erreur lors de la modification d'une categorie
	
		@Test
		public void testUpdateFail() {
			try {
				// 1. Creation de la categorie dans la bdd
				assertTrue(dao.create(categorie));
				// 2. Verification que la categorie existe bien dans la bdd
				Categorie categorieRead = dao.getById(categorie.getId());
				assertEquals(categorie, categorieRead);
				// 3. Modification de la categorie
				categorieRead.setTitre(""); // On modifie le titre en un titre vide
				assertTrue(dao.update(categorieRead));
				fail("Exception non lancée !");
			}
			catch (Exception e){
			}
		}
	
	// Test de l'erreur lors de la suppression d'une categorie
	
		@Test
		public void testDeleteFail() {
			try {
			assertTrue(dao.delete(categorie)); // On supprime la categorie alors qu'elle n'existe pas encore dans la bdd
			fail("Exception non lancée !");
			}
			catch (Exception e) {
			}
			
		}
}
