package connexion;

import static java.sql.DriverManager.getConnection;
import static org.apache.log4j.Logger.getLogger;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/*
 * Classe permettant d'obtenir une connection vers la base de données distance.
 */
public class Connexion {

	private static final Logger logger = getLogger(Connexion.class);

	private static Connection instance;

	/* @return une instance unique de connection (en singleton). */
	public static Connection createConnexion(String url, String login, String password) {
		if (instance == null) {
			try {
				logger.info("- Demande de connection en cours ...");
				instance = getConnection(url, login, password);
				logger.info("- Demande de connection établie ..." + url);
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("- Erreur lors de la tentative de connexion : " + e.getMessage());
			}
		}
		return instance;
	}
}
