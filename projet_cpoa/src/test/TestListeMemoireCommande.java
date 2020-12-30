package test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dao.CommandeDAO;
import dao.enumeration.Persistance;
import dao.factory.DAOFactory;
import modele.Commande;

public class TestListeMemoireCommande {

	private CommandeDAO dao;
	Commande commande;
	
	@Before
	public void setUp(){
		String strDate = "01/01/2001";
		DateTimeFormatter formatage = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate date = LocalDate.parse(strDate, formatage);
		commande =  new Commande(0, date, 2);
		dao = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getCommandeDAO();
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
		assertTrue(dao.create(commande));
		System.out.println(commande);
		System.out.println("Num�ro de la commande " + commande.getNum());
		assertNotNull(dao.getById(commande.getNum()));
	}
	
	// Test de l'ajout d'une commande
	@Test
	 public void testCreate() {
    	int size = dao.getAllCommandes().size();
	    System.out.println("Nombre de commande avant l'ajout : "+size);
		commande.setNum(0);	
		assertTrue(dao.create(commande)); 
		assertEquals(size + 1, dao.getAllCommandes().size());
		System.out.println("Nombre de commande apres l'ajout : "+dao.getAllCommandes().size());
	}

	// Test de la modification d'une commande	
	@Test
	public void testUpdate() {
		// 1. Creation de la commande dans la liste M�moire
		assertTrue(dao.create(commande));
		// 2. Verification que la commande existe bien
		Commande commandeRead = dao.getById(commande.getNum());
		assertEquals(commande, commandeRead);
		// 3. Modification de la commande
		commandeRead.setClient(5);
		assertTrue(dao.update(commandeRead));
	}
	
	// Test de la suppression d'une commande
	
	@Test
	public void testDelete() {
		// 1. Creation de la commande
		assertTrue(dao.create(commande));
		// 2. Lecture de la commande
		commande = dao.getById(commande.getNum());
		// 3. Suppression de la commande 
		dao.delete(commande);
	}
	
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
			commande.setClient(99);	//On associe la commande � un client inexistant
			assertTrue(dao.create(commande));
			fail("Exception non lanc�e !");
			}
			catch (Exception e){
			}
		
	}
	
	// Test de l'erreur lors de la modification d'une commande
	
		@Test
		public void testUpdateFail() {
			try {
				assertTrue(dao.create(commande));
				Commande commandeRead = dao.getById(commande.getNum());
				assertEquals(commande, commandeRead);
				commandeRead.setClient(99);
				assertTrue(dao.update(commandeRead));
				fail("Exception non lanc�e !");
			}
			catch (Exception e){
			}
		}
	
	// Test de l'erreur lors de la suppression d'une commande
		
	@Test
	public void testDeleteFail() {
		try {
			assertTrue(dao.delete(commande)); // On supprime la commande alors qu'elle n'existe pas encore dans la bdd
			fail("Exception non lanc�e !");
		}
		catch (Exception e) {
		}		
	}		
}
