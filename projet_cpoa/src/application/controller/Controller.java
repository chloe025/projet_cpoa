package application.controller;

import static dao.enumeration.Persistance.LISTE_MEMOIRE;
import static dao.enumeration.Persistance.MYSQL;
import static dao.factory.DAOFactory.getDAOFactory;
import java.net.URL;
import java.util.ResourceBundle;
import dao.factory.DAOFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Controller implements Initializable {
	
	@FXML
	private ComboBox<String> cbxMode;
	@FXML
	private Label lblMode;
	@FXML
	private Label lbl_prod;
	@FXML
	private Label lbl_categ;
	@FXML
	private Label lbl_cl;
	@FXML
	private Label lbl_cde;
	@FXML
	private Button btn_prod;
	@FXML
	private Button btn_categ;
	@FXML
	private Button btn_client;
	@FXML
	private Button btn_commandes;

	private static DAOFactory daos;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cbxMode.getItems().addAll("MySQL", "Liste Memoire");
	}

	public void choixMode() {
		daos = changeDaoMode();
		lbl_prod.setVisible(true);
		lbl_categ.setVisible(true);
		lbl_cl.setVisible(true);
		lbl_cde.setVisible(true);
		btn_prod.setVisible(true);
		btn_categ.setVisible(true);
		btn_client.setVisible(true);
		btn_commandes.setVisible(true);
		btn_prod.setDisable(false);
		btn_categ.setDisable(false);
		btn_client.setDisable(false);
		btn_commandes.setDisable(false);
	}

	private DAOFactory changeDaoMode() {
		DAOFactory df = null;
		int index = cbxMode.getSelectionModel().getSelectedIndex();
		if (index == 0) {
			df = getDAOFactory(MYSQL);
			lblMode.setText("Vous êtes actuellement en mode "+ cbxMode.getSelectionModel().getSelectedItem());
			cbxMode.setPromptText(cbxMode.getSelectionModel().getSelectedItem());
		} else {
			df = getDAOFactory(LISTE_MEMOIRE);
			lblMode.setText("Vous êtes actuellement en mode "+ cbxMode.getSelectionModel().getSelectedItem());
			cbxMode.setPromptText(cbxMode.getSelectionModel().getSelectedItem());
		}
		return df;
	}

	public static DAOFactory getDaos() {
		return daos;
	}

	public void setDaos(DAOFactory daos) {
		Controller.daos = daos;
	}

	public void affich_prod() {
		try {
			URL fxmlURL=getClass().getResource("../appliprod.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			Node root = fxmlLoader.load();
		    Scene scene = new Scene((Pane) root, 750, 700);
		    Stage stage = new Stage();
		    stage.setScene(scene);
			stage.setTitle("Gestion des produits");
			stage.getIcons().add(new Image("file:lib/noel.png"));
			stage.show();			
			}
			catch (Exception e) {
				e.printStackTrace(); 
			}
	}
	
	public void affich_categ() {
		try {
			URL fxmlURL=getClass().getResource("../applicateg.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			Node root = fxmlLoader.load();
		    Scene scene = new Scene((Pane) root, 750, 600);
		    Stage stage = new Stage();
		    stage.setScene(scene);
			stage.setTitle("Gestion des catégories");
			stage.getIcons().add(new Image("file:lib/noel.png"));
			stage.show();			
			}
			catch (Exception e) {
				e.printStackTrace(); 
			}
	}
	
	public void affich_cl() {
		try {
			URL fxmlURL=getClass().getResource("../appliclient.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			Node root = fxmlLoader.load();
		    Scene scene = new Scene((Pane) root, 750, 600);
		    Stage stage = new Stage();
		    stage.setScene(scene);
			stage.setTitle("Gestion des clients");
			stage.getIcons().add(new Image("file:lib/noel.png"));
			stage.show();			
			}
			catch (Exception e) {
				e.printStackTrace(); 
			}
	}
	
	public void affich_cde() {
		CtrlClients.bool = false; // booleen qui permet d'afficher toutes les commandes dans la fenetre des commandes
		try {
			URL fxmlURL=getClass().getResource("../applicommande.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			Node root = fxmlLoader.load();
		    Scene scene = new Scene((Pane) root, 750, 700);
		    Stage stage = new Stage();
		    stage.setScene(scene);
			stage.setTitle("Gestion des commandes");
			stage.getIcons().add(new Image("file:lib/noel.png"));
			stage.show();			
			}
			catch (Exception e) {
				e.printStackTrace(); 
			}
	}

	public Button getBtn_commandes() {
		return btn_commandes;
	}

	public void setBtn_commandes(Button btn_commandes) {
		this.btn_commandes = btn_commandes;
	}
}