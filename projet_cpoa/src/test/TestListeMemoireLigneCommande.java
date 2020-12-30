package test;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

import dao.CommandeDAO;
import dao.LigneCommandeDAO;
import dao.enumeration.Persistance;
import dao.factory.DAOFactory;
import modele.Commande;
import modele.LigneCommande;

public class TestListeMemoireLigneCommande {

	private LigneCommandeDAO dao;
	LigneCommande lignecommande;
	
	@Before
	public void setUp(){
		lignecommande =  new LigneCommande(1, 2, 10, 15);
		dao = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getLigneCommandeDAO();
		assertNotNull(dao);
		assertNotNull(dao.getAllLigneCommande());
	}
	
	// Test du getById
	
	/*@Test
	public void testGetById() {
		// 1. Creation de la ligne commande 
		assertTrue(dao.create(lignecommande));
		assertNotNull(dao.getById(lignecommande.getIdCommande()));
	}*/
	
	// Test de l'ajout d'une ligne de commande
	
	/*@Test
	public void testCreate() {
		// Nombre de ligne commandes avant insertion
		int size = dao.getAllLigneCommande().size();
		//Creation de la liste de produit
		HashMap<Produit, Integer> LC = new HashMap<Produit, Integer>();
		Produit prod = new Produit(12,"Dall", "Joyeux No�l avec nos petits lutins dansants !", 35., "bonnet1.png", 2);
		int quantite = 30;
		LC.put(prod, quantite);
		
		assertTrue(dao.create(lignecommande);
		// Verification que nous avons une ligne commande en plus dans la liste : 
		assertEquals(size + 1, dao.getAllLigneCommande().size());
	}*/
	
	// Test de la modification d'une ligne de commande
	
	/*@Test
	public void testUpdate() {
		// 1. Creation de la ligne commande 
		//Cr�ation de la liste de produit
		HashMap<Produit, Integer> LC = new HashMap<Produit, Integer>();
		Produit prod = new Produit(12,"Dall", "Joyeux No�l avec nos petits lutins dansants !", 35., "bonnet1.png", 2);
		int quantite = 30;
		LC.put(prod, quantite);
		
		assertTrue(dao.create(lignecommande);
		// 2. Verification que la ligne commande existe 
		LigneCommande lignecommandeRead = dao.getById(lignecommande.getIdCommande());
		assertEquals(lignecommande, lignecommandeRead);
		// 3. Modification de la ligne commande
		lignecommandeRead.setQuantite(5);
		assertTrue(dao.update(lignecommandeRead));
	}*/
	
	// Test de la suppression d'une ligne de commande
	
	/*@Test
	public void testDelete() {
		// 1. Creation de la ligne commande
		//Cr�ation de la liste de produit
		HashMap<Produit, Integer> LC = new HashMap<Produit, Integer>();
		Produit prod = new Produit(12,"Dall", "Joyeux No�l avec nos petits lutins dansants !", 35., "bonnet1.png", 2);
		int quantite = 30;
		LC.put(prod, quantite);
		
		assertTrue(dao.create(lignecommande);
		// 2. Lecture de la ligne commande
		lignecommande = dao.getById(lignecommande.getIdCommande());
		// 3. Suppression de la ligne commande
		dao.delete(lignecommande);
	}*/
	
	/*
	 * **********************************
	 * 		 	 Tests fail 			*
	 * **********************************
	 */
	
	// Test de l'erreur lors de l'ajout d'une ligne de commande
	
//		@Test
//		public void testCreateFail() {
//			try {
//				lignecommande.setIdCommande(99); //On associe la ligne de commande � une commande inexistante
//				assertTrue(dao.create(lignecommande));
//				fail("Exception non lanc�e !");
//			}
//			catch (Exception e){
//			}
//		}
	
	// Test de la modification d'une ligne de commande
	
		/*@Test
		public void testUpdateFail() {
			try {
				// 1. Creation de la ligne commande 
				assertTrue(dao.create(lignecommande));
				// 2. Verification que la ligne commande existe
				LigneCommande lignecommandeRead = dao.getById(lignecommande.getIdCommande(), lignecommande.getIdProd());
				assertEquals(lignecommande, lignecommandeRead);
				// 3. Modification de la ligne commande
				lignecommandeRead.setIdCommande(99); // On donne a la ligne de commande un id de commande inexistant
				assertTrue(dao.update(lignecommandeRead));
				fail("Exception non lanc�e !");
			}
			catch (Exception e){
			}
		}*/
	
	// Test de l'erreur lors de la suppression d'une ligne de commande
	
			/*@Test
			public void testDeleteFail() {
				try {
				assertTrue(dao.delete(lignecommande)); // On supprime la categorie alors qu'elle n'existe pas encore dans la bdd
				fail("Exception non lanc�e !");
				}
				catch (Exception e) {
				}
				
			}*/
}
