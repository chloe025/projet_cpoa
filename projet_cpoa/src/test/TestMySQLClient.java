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

import dao.ClientDAO;
import dao.ProduitDAO;
import dao.enumeration.Persistance;
import dao.factory.DAOFactory;
import dao.mysql.MySQLClientDAO;
import modele.Client;
import modele.Produit;


public class TestMySQLClient {

	private ClientDAO dao;
	Client client;
	
	@Before
	public void setUp(){
		client =  new Client(0, "TestClientNom", "TestClientPrenom", "TestClientVille");
		dao = DAOFactory.getDAOFactory(Persistance.MYSQL).getClientDAO();
		assertNotNull(dao);
		assertNotNull(dao.getAllClients());
	}
	
	/*
	 * **********************************
	 * 		  Tests fonctionnels		*
	 * **********************************
	 */
	
	// Test du getById
	
	@Test
	public void testGetById() {
		// 1. Creation du client dans la bdd
		assertTrue(dao.create(client));
		assertNotNull(dao.getById(client.getNo()));
	}
	
	// Test de l'ajout d'un client
	
	@Test
	public void testCreate() {
		// Nombre de clients avant insertion
		int size = dao.getAllClients().size();
		assertTrue(dao.create(client));
		// Verification que nous avons un client en plus dans la liste : 
		assertEquals(size + 1, dao.getAllClients().size());
	}
	
	// Test de la modification d'un client
	
	@Test
	public void testUpdate() {
		// 1. Creation du client dans la bdd
		assertTrue(dao.create(client));
		// 2. Verification que le client existe bien dans la bdd
		Client clientRead = dao.getById(client.getNo());
		assertEquals(client, clientRead);
		// 3. Modification du client
		clientRead.setNom("Nom");
		assertTrue(dao.update(clientRead));
		// 4. Verification en bdd
		assertEquals(clientRead, dao.getById(client.getNo()));
	}
	
	// Test de la suppression d'un client
	
	@Test
	public void testDelete() {
		// 1. Creation du client
		assertTrue(dao.create(client));
		// 2. Lecture du client
		client = dao.getById(client.getNo());
		// 3. Suppression du client dans la bdd
		dao.delete(client);
		// 4. Verification que le client n'exclientiste plus dans la bdd
		assertNull(dao.getById(client.getNo()));
	}
	
	// Test de la liste des clients
	
	@Test
    public void testFindAll() {
       dao.create(client);
       List<Client> listeClient = dao.getAllClients();
       assertTrue(!listeClient.isEmpty());
    }
	
	
	/*
	 * **********************************
	 * 		 	 Tests fail 			*
	 * **********************************
	 */
	
	// Test de l'erreur lors de l'ajout d'un client
	
		@Test
		public void testCreateFail() {
			try {
				client.setNom(""); // On donne au client un nom vide
				assertTrue(dao.create(client));
				fail("Exception non lancée !");
			}
			catch (Exception e){
			}
		}
	
	// Test de l'erreur lors de la modification d'un client
	
		@Test
		public void testUpdateFail() {
			try {
				// 1. Creation du client dans la bdd
				assertTrue(dao.create(client));
				// 2. Verification que le client existe bien dans la bdd
				Client clientRead = dao.getById(client.getNo());
				assertEquals(client, clientRead);
				// 3. Modification du client
				clientRead.setNom(""); // On modifie le nom en un nom vide
				assertTrue(dao.update(clientRead));
				fail("Exception non lancée !");
			}
			catch (Exception e){
			}
		}
	
	// Test de l'erreur lors de la suppression d'un client	
			@Test
			public void testDeleteFail() {
				try {
				assertTrue(dao.delete(client)); // On supprime le client alors qu'il n'existe pas encore dans la bdd
				fail("Exception non lancée !");
				}
				catch (Exception e) {
				}
			}
}