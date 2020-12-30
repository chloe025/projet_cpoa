package test;

import static connexion.Connexion.createConnexion;
import static junit.framework.Assert.assertNotNull;
import static org.apache.log4j.Logger.getLogger;

import org.apache.log4j.Logger;
import org.junit.Test;

public class TestConnexion {

	private static final Logger logger = getLogger(TestConnexion.class);

	@Test
	public void testConnexionOk() {
		logger.info("======================");
		logger.info("testConnexionOk()");
		String url = "jdbc:mysql://devbdd.iutmetz.univ-lorraine.fr:3306/celindan3u_cpoa1?serverTimezone=Europe/Paris";
		String login = "celindan3u_appli";
		String pwd = "31902871";
		assertNotNull(createConnexion(url, login, pwd));
		logger.info("======================");
	}
}
