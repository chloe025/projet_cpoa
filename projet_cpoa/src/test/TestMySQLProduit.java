package test;


import static junit.framework.Assert.assertTrue;
import static org.apache.log4j.Logger.getLogger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import dao.ProduitDAO;
import dao.enumeration.Persistance;
import dao.factory.DAOFactory;
import dao.mysql.MySQLProduitDAO;
import junit.framework.Assert;
import modele.Categorie;
import modele.Produit;


public class TestMySQLProduit {
	
	private ProduitDAO dao;
	Produit produit;
	
	@Before
	public void setUp(){
		produit =  new Produit(0, "TestProduit", "TestProduiDesc", 10., "TestProd.png", 4);
		dao = DAOFactory.getDAOFactory(Persistance.MYSQL).getProduitDAO();
		assertNotNull(dao);
		assertNotNull(dao.getAllProduits());
	}
	
	/*
	 * **********************************
	 * 		  Tests fonctionnels		*
	 * **********************************
	 */
	
	// Test du getById
	
	@Test
	public void testGetById() {
		// 1. Creation du produit dans la bdd
		assertTrue(dao.create(produit));
		assertNotNull(dao.getById(produit.getId()));
	}
	
	// Test de l'ajout d'un produit
	
	@Test
	public void testCreate() {
		// Nombre de produits avant insertion
		int size = dao.getAllProduits().size();
		assertTrue(dao.create(produit));
		// Verification que nous avons un produit en plus dans la liste : 
		assertEquals(size + 1, dao.getAllProduits().size());
	}
	
	// Test de la modification d'un produit
	
	@Test
	public void testUpdate() {
		// 1. Creation du produit dans la bdd
		assertTrue(dao.create(produit));
		// 2. Verification que le produit existe bien dans la bdd
		Produit produitRead = dao.getById(produit.getId());
		assertEquals(produit, produitRead);
		// 3. Modification du produit
		produitRead.setDescription("Description");
		assertTrue(dao.update(produitRead));
		// 4. Verification en bdd
		assertEquals(produitRead, dao.getById(produit.getId()));
	}
	
	// Test de la suppression d'un produit
	
	@Test
	public void testDelete() {
		// 1. Creation du produit
		assertTrue(dao.create(produit));
		// 2. Lecture du produit
		produit = dao.getById(produit.getId());
		// 3. Suppression du produit dans la bdd
		dao.delete(produit);
		// 4. Verification que le produit n'existe plus dans la bdd
		assertNull(dao.getById(produit.getId()));
	}
	
	// Test de la liste de produits
	
	@Test
    public void testFindAll() {
            dao.create(produit);
            List<Produit> listeProduit = dao.getAllProduits();
            assertTrue(!listeProduit.isEmpty());
    }
	
	
	/*
	 * **********************************
	 * 		 	 Tests fail 			*
	 * **********************************
	 */
	
	// Test de l'erreur lors de l'ajout d'un produit
	
		@Test
		public void testCreateFail() {
			try {
				produit.setCategorie(99); //On associe le produit à une catégorie inexistante
				assertTrue(dao.create(produit));
				fail("Exception non lancée !");
			}
			catch (Exception e){
			}
		}
	
	// Test de l'erreur lors de la modification d'un produit
	
		@Test
		public void testUpdateFail() {
			try {
				// 1. Creation du produit dans la bdd
				assertTrue(dao.create(produit));
				// 2. Verification que le produit existe bien dans la bdd
				Produit produitRead = dao.getById(produit.getId());
				assertEquals(produit, produitRead);
				// 3. Modification du produit
				produitRead.setDescription(""); // On modifie la description en une descrption vide
				assertTrue(dao.update(produitRead));
				fail("Exception non lancée !");
			}
			catch (Exception e){
			}
		}
	
	// Test de l'erreur lors de la suppression d'un produit
	
			@Test
			public void testDeleteFail() {
				try {
				assertTrue(dao.delete(produit)); // On supprime la categorie alors qu'elle n'existe pas encore dans la bdd
				fail("Exception non lancée !");
				}
				catch (Exception e) {
				}
				
			}
}

