package test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.apache.log4j.Logger.getLogger;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static junit.framework.Assert.assertNotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import dao.CategorieDAO;
import dao.CommandeDAO;
import dao.enumeration.Persistance;
import dao.factory.DAOFactory;
import dao.mysql.MySQLCommandeDAO;
import modele.Categorie;
import modele.Client;
import modele.Commande;
import modele.Produit;

public class TestMySQLCommande {

	private CommandeDAO dao;
	Commande commande;
	
	@Before
	public void setUp(){
		String strDate = "01/01/2001";
		DateTimeFormatter formatage = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate date = LocalDate.parse(strDate, formatage);
		commande =  new Commande(0, date, 1);
		dao = DAOFactory.getDAOFactory(Persistance.MYSQL).getCommandeDAO();
		assertNotNull(dao);
		assertNotNull(dao.getAllCommandes());
	}
	
	/*
	 * **********************************
	 * 		  Tests fonctionnels		*
	 * **********************************
	 */
	
	// Test du getById
	
	@Test
	public void testGetById() {
		// 1. Creation de la commande dans la bdd
		assertTrue(dao.create(commande));
		assertNotNull(dao.getById(commande.getNum()));
	}
	
	// Test de l'ajout d'une commande
	
	@Test
	public void testCreate() {
		// Nombre de commandes avant insertion
		int size = dao.getAllCommandes().size();
		assertTrue(dao.create(commande));
		// Verification que nous avons une commande en plus dans la liste : 
		assertEquals(size + 1, dao.getAllCommandes().size());
	}
	
	// Test de la modification d'une commande
	
	@Test
	public void testUpdate() {
		// 1. Creation de la commande dans la bdd
		assertTrue(dao.create(commande));
		// 2. Verification que la commande existe bien dans la bdd
		Commande commandeRead = dao.getById(commande.getNum());
		assertEquals(commande, commandeRead);
		// 3. Modification de la commande
		String strDate = "31/12/2001";
		DateTimeFormatter formatage = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate date = LocalDate.parse(strDate, formatage);
		commandeRead.setDate(date);
		assertTrue(dao.update(commandeRead));
		// 4. Verification en bdd
		assertEquals(commandeRead, dao.getById(commande.getNum()));
	}
	
	// Test de la suppression d'une commande
	
	@Test
	public void testDelete() {
		// 1. Creation de la commande
		assertTrue(dao.create(commande));
		// 2. Lecture de la commande
		commande = dao.getById(commande.getNum());
		// 3. Suppression de la commande dans la bdd
		dao.delete(commande);
		// 4. Verification que la commande n'existe plus dans la bdd
		assertNull(dao.getById(commande.getNum()));
	}
	
	// Test de la liste des commandes
	
	@Test
    public void testFindAll() {
            dao.create(commande);
            List<Commande> listeCommande = dao.getAllCommandes();
            assertTrue(!listeCommande.isEmpty());
    }
	
	
	
	/*
	 * **********************************
	 * 		 	 Tests fail 			*
	 * **********************************
	 */
	
	// Test de l'erreur lors de la creation d'une commande
	
	@Test
	public void testCreateFail() {
			try{
			commande.setClient(99);	//On associe la commande à un client inexistant
			assertTrue(dao.create(commande));
			fail("Exception non lancée !");
			}
			catch (Exception e){
			}
		
	}
	
	// Test de l'erreur lors de la modification d'une commande
	
		@Test
		public void testUpdateFail() {
			try {
				// 1. Creation de la commande dans la bdd
				assertTrue(dao.create(commande));
				// 2. Verification que la commande existe bien dans la bdd
				Commande commandeRead = dao.getById(commande.getNum());
				assertEquals(commande, commandeRead);
				// 3. Modification de la commande
				commandeRead.setClient(99); // On modifie le client associé la commande en un client inexistant
				assertTrue(dao.update(commandeRead));
				fail("Exception non lancée !");
			}
			catch (Exception e){
			}
		}
	
	// Test de l'erreur lors de la suppression d'une commande
	
			@Test
			public void testDeleteFail() {
				try {
				assertTrue(dao.delete(commande)); // On supprime la categorie alors qu'elle n'existe pas encore dans la bdd
				fail("Exception non lancée !");
				}
				catch (Exception e) {
				}
				
			}
}
