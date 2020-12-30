package application.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import dao.factory.DAOFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import modele.Client;
import modele.Commande;

public class CtrlClients implements Initializable, ChangeListener<Client> {
	
    @FXML
    private TableView<Client> tblClient;
    @FXML
	TableColumn<Client, String> colNom;
    @FXML
	TableColumn<Client, String> colVille;
    @FXML
    private Label lblClient;
    @FXML
    private Label lblErreurSuppr;
    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtPrenom;
    @FXML
    private TextField txtVille;
    @FXML
    private TextField txtRech;
    @FXML
    private Button btnCreer;
    @FXML
    private Button btnModifier;
    @FXML
    private Button btnSupprimer;
    @FXML
    private Button btnCde;

    DAOFactory dao = Controller.getDaos();
    public static boolean bool;
    public static int idClient;
	private final ObservableList<Client> data = FXCollections.observableArrayList();
    
    public CtrlClients() {
    	data.addAll(dao.getClientDAO().getAllClients());
    }
    
	@SuppressWarnings("unchecked")
	@Override
    public void initialize(URL location, ResourceBundle resources) {
		colNom = new TableColumn<>("Nom");
        colNom.setCellValueFactory(new PropertyValueFactory<Client, String>("nom"));
        TableColumn<Client, String> colPrenom = new TableColumn<>("Prenom");
        colPrenom.setCellValueFactory(new PropertyValueFactory<Client, String>("prenom"));
        colVille = new TableColumn<>("Ville");
        colVille.setCellValueFactory(new PropertyValueFactory<Client, String>("ville"));
        this.tblClient.getColumns().setAll(colNom, colPrenom,colVille);
		tblClient.getItems().addAll(data);
		tblClient.getSortOrder().add(colNom);
		tblClient.getSortOrder().add(colVille);
        this.tblClient.getSelectionModel().selectedItemProperty().addListener(this);
    }

    public void creerModele() {
    	String textErreur = "";
        Client client = new Client();
        if (txtNom.getText()==null || txtNom.getText().trim().length()==0)
        	textErreur =  textErreur + ("Nom non rempli \n");
        else
        	client.setNom(txtNom.getText().trim());
        
        if (txtPrenom.getText()==null || txtPrenom.getText().trim().length()==0)
        	textErreur =  textErreur + ("Prenom non rempli \n");
        else
        	client.setPrenom(txtPrenom.getText().trim());
        
        if(txtVille.getText()==null || txtVille.getText().trim().length()==0)
        	textErreur = textErreur + ("Ville non remplie \n");
        else 
        	client.setVille(txtVille.getText().trim());
        
        textErreur = ExistanceClient(textErreur);
        
        // si il n'y a pas d'erreur dans la variable d'erreur
        if (textErreur == "") {
        	this.lblClient.setTextFill(Color.BLACK);
            this.lblClient.setText(client.toString2());
            dao.getClientDAO().create(client);
            this.data.add(client);
            this.tblClient.getItems().clear();
            this.tblClient.getItems().addAll(data);
    		tblClient.getSortOrder().add(colNom);
    		tblClient.getSortOrder().add(colVille);
            txtNom.setText("");
            txtPrenom.setText("");
            txtVille.setText("");
        }
        else {
        	this.lblClient.setTextFill(Color.RED);
            this.lblClient.setText(textErreur);
        }
    }
    
    public void modifierModele() {
    	String textErreur = "";
    	textErreur = ExistanceClient(textErreur);
    	if (textErreur == "") {
    		Client client = this.tblClient.getSelectionModel().getSelectedItem();
        	if (txtNom.getText()==null || txtNom.getText().trim().length()==0)
        		textErreur =  textErreur + ("Nom non rempli \n");
        	else
        		client.setNom(txtNom.getText().trim());
        	
        	if (txtPrenom.getText()==null || txtPrenom.getText().trim().length()==0)
        		textErreur =  textErreur + ("Prenom non rempli \n");
        	else
        		client.setPrenom(txtPrenom.getText().trim());
        	
        	if(txtVille.getText()==null || txtVille.getText().trim().length()==0)
        		textErreur = textErreur + ("Ville non remplie \n");
        	else
        		client.setVille(txtVille.getText().trim());
        	
        	if (textErreur == "") {
            	this.lblClient.setTextFill(Color.BLACK);
                this.lblClient.setText(client.toString2());
                dao.getClientDAO().update(client);
                this.tblClient.getItems().clear();
                this.tblClient.getItems().addAll(data);
        		tblClient.getSortOrder().add(colNom);
        		tblClient.getSortOrder().add(colVille);
                txtNom.setText("");
                txtPrenom.setText("");
                txtVille.setText("");
            }
        	else {
            	this.lblClient.setTextFill(Color.RED);
                this.lblClient.setText(textErreur);
            }
    	}
        else {
        	this.lblClient.setTextFill(Color.RED);
            this.lblClient.setText(textErreur);
        }
    }
    
    public void supprimerModele(){
        Client client = this.tblClient.getSelectionModel().getSelectedItem();
        List<Commande> listCde = dao.getCommandeDAO().getAllCommandes();
        boolean bool = false;
        int cl= 0;
        for (int i=0; i<listCde.size() ; i++) {
            cl = listCde.get(i).getClient();
            if (cl == client.getNo()) {
                bool = true;
                lblErreurSuppr.setVisible(true);
                break;
            }
        }
        if (bool == false) {
            lblErreurSuppr.setVisible(false);
            dao.getClientDAO().delete(client);
            this.data.remove(client);
            this.tblClient.getItems().clear();
            this.tblClient.getItems().addAll(data);
    		tblClient.getSortOrder().add(colNom);
    		tblClient.getSortOrder().add(colVille);
        }
    }
    
    private String ExistanceClient(String textErreur) {
        List<Client> listCat = dao.getClientDAO().getAllClients();
        for (int i=0; i<listCat.size() ; i++) {
            if (txtNom.getText().trim().toLowerCase().equals(listCat.get(i).getNom().trim().toLowerCase()) && 
            		(txtPrenom.getText().trim().toLowerCase().equals(listCat.get(i).getPrenom().trim().toLowerCase())) &&
            		(txtVille.getText().trim().toLowerCase().equals(listCat.get(i).getVille().trim().toLowerCase()))) 
                return textErreur = textErreur + ("Ce client existe déjà");
        }
        return textErreur;
    }
	
	public void rechercheClient() {
		FilteredList<Client> flClient = new FilteredList<>(data, p -> true); // Envoyer les données dans une liste filtree
		flClient.setPredicate(client -> {
			// Comparaisons
			String lowerCaseFilter = txtRech.getText();	
			if (client.getNom().toLowerCase().contains(lowerCaseFilter))
				return true; 
			else if (client.getPrenom().toLowerCase().contains(lowerCaseFilter))
				return true; 
			else if (client.getNom().toLowerCase().concat(" ".concat(client.getPrenom().toLowerCase())).contains(lowerCaseFilter))
					return true;
			return false; 
			});
				
		SortedList<Client> sortedData = new SortedList<>(flClient);
		sortedData.comparatorProperty().bind(tblClient.comparatorProperty());
		tblClient.getItems().clear();
		tblClient.getItems().addAll(sortedData);
	}
	
	@Override
	public void changed(ObservableValue<? extends Client> observable, Client oldValue, Client newValue) {
		this.btnModifier.setDisable(newValue == null);
		this.btnSupprimer.setDisable(newValue == null);
		this.btnCde.setDisable(newValue == null);
		try {
			txtNom.setText(tblClient.getSelectionModel().getSelectedItem().getNom());
			txtPrenom.setText(tblClient.getSelectionModel().getSelectedItem().getPrenom());
			txtVille.setText(tblClient.getSelectionModel().getSelectedItem().getVille());
		}
		catch (Exception e) {
		}
	}
	
	// Fenetre qui contient uniquement les commandes du client sélectionné
	public void afficherCde() { 
		bool = true;
		idClient = tblClient.getSelectionModel().getSelectedItem().getNo();
		try {
			URL fxmlURL=getClass().getResource("../applicommande.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			Node root = fxmlLoader.load();
			Scene scene = new Scene((Pane) root, 750, 700);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Gestion des commandes");
			stage.show();
		}
		catch (Exception e) {
			e.printStackTrace(); 
		}
	}		
}