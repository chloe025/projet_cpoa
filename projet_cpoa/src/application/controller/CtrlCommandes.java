package application.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import dao.factory.DAOFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import modele.Client;
import modele.Commande;
import modele.LigneCommande;
import modele.Produit;

public class CtrlCommandes implements Initializable{

    @FXML
    private TableView<Commande> tblCde;
    @FXML
    private TableView<LigneCommande> tblLigneCde;
    @FXML
    private TableColumn<Commande, String> colClient;
    @FXML
    private TextField txtQte;
    @FXML
    private Label lblCde;
    @FXML
    private Label lblLigneCde;
    @FXML
    private Label lblTarif;
    @FXML
    private Label lblErreurSuppr;
    @FXML
    private DatePicker dateCde;
    @FXML
    private ComboBox<Client> cbxClient;
    @FXML
    private ComboBox<Produit> cbxProd;
    @FXML
    private ComboBox<String> cbxTri;
    @FXML
    private ComboBox<Client> cbxFiltreClient;
    @FXML
    private ComboBox<Produit> cbxFiltreProd;
    @FXML
    private Button btnCreer;
    @FXML
    private Button btnModifier;
    @FXML
    private Button btnSupprimer;
    @FXML
    private Button btnCreerLigne;
    @FXML
    private Button btnSupprimerLigne;
    @FXML
    private Button btnModifierLigne;
    
    DAOFactory dao = Controller.getDaos();
    boolean bool = CtrlClients.bool;
    int idSelectedClient = CtrlClients.idClient;
    private final ObservableList<Commande> data = FXCollections.observableArrayList();
    
    public CtrlCommandes() {
    	data.addAll(dao.getCommandeDAO().getAllCommandes());
    }
    
	@SuppressWarnings("unchecked")
	@Override
    public void initialize(URL location, ResourceBundle resources) {
		// remplissage des combos 
		this.cbxClient.setItems(FXCollections.observableArrayList(dao.getClientDAO().getAllClients()));
		this.cbxFiltreClient.setItems(FXCollections.observableArrayList(dao.getClientDAO().getAllClients()));
		this.cbxFiltreProd.setItems(FXCollections.observableArrayList(dao.getProduitDAO().getAllProduits()));
		this.cbxProd.setItems(FXCollections.observableArrayList(dao.getProduitDAO().getAllProduits()));
		this.cbxTri.getItems().addAll("Produit", "Client");
		
		// création de la table des commandes
		TableColumn<Commande, String> colDate = new TableColumn<>("Date");
		colDate.setCellValueFactory(new PropertyValueFactory<Commande, String>("date"));
		colClient = new TableColumn<>("Client");
		colClient.setCellValueFactory(new PropertyValueFactory<Commande, String>("client"));
		this.tblCde.getColumns().setAll(colDate, colClient);
		
		// création de la table des lignes de commande
		TableColumn<LigneCommande, Integer> colIdCde = new TableColumn<>("Commande");
		colIdCde.setCellValueFactory(new PropertyValueFactory<LigneCommande, Integer>("idCommande"));
		TableColumn<LigneCommande, Integer> colIdProd = new TableColumn<>("Produit");
		colIdProd.setCellValueFactory(new PropertyValueFactory<LigneCommande, Integer>("idProd"));
		TableColumn<LigneCommande, Integer> colQte = new TableColumn<>("Quantité");
		colQte.setCellValueFactory(new PropertyValueFactory<LigneCommande, Integer>("quantite"));
		TableColumn<LigneCommande, Double> colTarif = new TableColumn<>("Tarif Unitaire");
		colTarif.setCellValueFactory(new PropertyValueFactory<LigneCommande, Double>("tarif"));
		this.tblLigneCde.getColumns().setAll(colIdCde, colIdProd, colQte, colTarif);
		
		// on affiche toutes les commandes
		if (bool == false) { 
			this.tblCde.getItems().addAll(data);
		}
		// on affiche les commandes du client si il est sélectionné dans la fenêtre des clients
		else {  
			List<Commande> listeCde = data;
			int id=0;
			for (int i=0; i<listeCde.size(); i++) {
				id = listeCde.get(i).getClient();
				if (id == idSelectedClient)
					this.tblCde.getItems().add(listeCde.get(i));
			}
		}
		
		// si on sélectionne un objet de la table commande
		this.tblCde.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			this.btnModifier.setDisable(newValue == null);
			this.btnSupprimer.setDisable(newValue == null);
			try {
				tblLigneCde.setVisible(true);
				tblLigneCde.getItems().clear();
				List<LigneCommande> listeLCde = dao.getLigneCommandeDAO().getAllLigneCommande();
				int idLCde = 0;
				int idCde = 0;
				for (int i=0; i<listeLCde.size(); i++) {
					idLCde = listeLCde.get(i).getIdCommande();
					idCde = tblCde.getSelectionModel().getSelectedItem().getNum();
					if (idLCde == idCde)
							this.tblLigneCde.getItems().add(listeLCde.get(i));
				}
			}
			catch (Exception e) {
			}
		 });
		
		// si on sélectionne un objet de la table ligne commande
		this.tblLigneCde.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			this.btnSupprimerLigne.setDisable(newValue == null);
			this.btnModifierLigne.setDisable(newValue == null);
		});
    }
    
    public void creerModele() {
    	String textErreur = "";
    	Commande cde = new Commande();
    	if (dateCde.getValue()==null)
    		textErreur =  textErreur + ("Date non renseignee \n");
    	else
    		cde.setDate(dateCde.getValue());
    	
    	int index = cbxClient.getSelectionModel().getSelectedIndex()+1;
    	if (index >= 1)
    		cde.setClient(index);
    	else
    		textErreur =  textErreur + ("Pas de client selectionne \n");
    	
    	textErreur = ExistanceCommande(index, textErreur); 
           
        if (textErreur == "") { // si il n'y a pas d'erreur
        	this.lblCde.setTextFill(Color.BLACK);
            this.lblCde.setText(cde.toString2());
            dao.getCommandeDAO().create(cde);
            this.data.add(cde);
            this.tblCde.getItems().clear();
            this.tblCde.getItems().addAll(data);
            dateCde.setValue(null);
            cbxClient.setValue(null);
            cbxClient.setPromptText("");
        }
        else {
        	this.lblCde.setTextFill(Color.RED);
            this.lblCde.setText(textErreur);
        }
    }
    
    public void modifierModele() {
        String textErreur = "";
        int index = cbxClient.getSelectionModel().getSelectedIndex()+1;
        textErreur = ExistanceCommande(index, textErreur); 
        if (textErreur =="") {
        	Commande cde = this.tblCde.getSelectionModel().getSelectedItem();
            if (dateCde.getValue()==null)
            	textErreur =  textErreur + ("Date non renseignee \n");
            else
            	cde.setDate(dateCde.getValue());
            
            if (index >= 1)
            	cde.setClient(index);
            else
            	textErreur =  textErreur + ("Pas de client selectionne \n");
            
            if (textErreur == "") { // si il n'y a pas d'erreur
            	this.lblCde.setTextFill(Color.BLACK);
                this.lblCde.setText(cde.toString2());
                dao.getCommandeDAO().update(cde);
                this.tblCde.getItems().clear();
                this.tblCde.getItems().addAll(data);
                dateCde.setValue(null);
                cbxClient.setValue(null);
                cbxClient.setPromptText("");
            }
            else {
            	this.lblCde.setTextFill(Color.RED);
                this.lblCde.setText(textErreur);
            }
        }
        else {
        	this.lblCde.setTextFill(Color.RED);
            this.lblCde.setText(textErreur);
        }
    }
    
	public void supprimerModele(){
    	Commande cde = this.tblCde.getSelectionModel().getSelectedItem();
    	List<LigneCommande> listcde = dao.getLigneCommandeDAO().getAllLigneCommande();
        boolean bool = false;
        int id_cde= 0;
        for (int i=0; i<listcde.size() ; i++) {
            id_cde = listcde.get(i).getIdCommande();
            if (id_cde == cde.getNum()) {
                bool = true;
                lblErreurSuppr.setVisible(true);
                break;
            }
        }
        if (bool == false) {
            lblErreurSuppr.setVisible(false);
            dao.getCommandeDAO().delete(cde);
            this.data.remove(cde);
            this.tblCde.getItems().clear();
            this.tblCde.getItems().addAll(data);
        }
    }
	
	private String ExistanceCommande(int index, String textErreur) {
        List<Commande> listCde = dao.getCommandeDAO().getAllCommandes();
           for (int i=0; i<listCde.size() ; i++) {
               LocalDate LDDate = dateCde.getValue();
               if (listCde.get(i).getClient() == index && listCde.get(i).getDate().isEqual(LDDate)) 
                   textErreur = textErreur + ("Cette commande existe déjà");
           }
       return textErreur;
    }
	
	public void creerLigneCde() {
		String textErreur = "";
        LigneCommande lcde = new LigneCommande();
        if (tblCde.getSelectionModel().isEmpty())
        	textErreur =  textErreur + ("Aucune commande sélectionnée \n");
        else
        	lcde.setIdCommande(tblCde.getSelectionModel().getSelectedItem().getNum());
        
        int index = cbxProd.getSelectionModel().getSelectedIndex()+1;
        if (index >= 1) {
            lcde.setIdProd(cbxProd.getSelectionModel().getSelectedItem().getId());
            lcde.setTarif(Double.parseDouble(lblTarif.getText()));
         }
        else
        	textErreur =  textErreur + ("Pas de produit sélectionné \n");
        	
        if (Pattern.matches("[a-zA-Z]+" , txtQte.getText())) // cela indique qu'uniquement des lettres sont attendues
        	textErreur =  textErreur + ("Que des chiffres sont attendus pour la quantité \n");
        else if (txtQte.getText()== null || txtQte.getText().trim().length() == 0)
        	textErreur = textErreur + ("Une quantité est attendu \n");
        else {
        	String qte = txtQte.getText().trim();
        	lcde.setQuantite(Integer.parseInt(qte));
        }
        
        if (textErreur == "") { // si il n'y a pas d'erreur
        	this.lblLigneCde.setTextFill(Color.BLACK);
        	this.lblLigneCde.setText(lcde.toString2());
        	dao.getLigneCommandeDAO().create(lcde);
        	this.tblLigneCde.getItems().add(lcde);
        	cbxProd.setValue(null);
        	txtQte.setText("");
        	lblTarif.setText("");
        }
        else {
        	this.lblLigneCde.setTextFill(Color.RED);
        	this.lblLigneCde.setText(textErreur);
        }
	}
	
	public void modifierLigneCde() {
		String textErreur = "";
        LigneCommande lcde = this.tblLigneCde.getSelectionModel().getSelectedItem();
        if (tblCde.getSelectionModel().isEmpty())
        	textErreur =  textErreur + ("Aucune commande sélectionnée \n");
        else
        	lcde.setIdCommande(tblCde.getSelectionModel().getSelectedItem().getNum());
        
        int index = cbxProd.getSelectionModel().getSelectedIndex()+1;
        if (index >= 1) {
            lcde.setIdProd(cbxProd.getSelectionModel().getSelectedItem().getId());
            lcde.setTarif(Double.parseDouble(lblTarif.getText()));
        }
        else
        	textErreur =  textErreur + ("Pas de produit sélectionné \n");
        
        if (Pattern.matches("[a-zA-Z]+" , txtQte.getText())) // cela indique qu'uniquement des lettres sont attendues
        	textErreur =  textErreur + ("Que des chiffres sont attendus pour la quantité \n");
        else if (txtQte.getText()== null || txtQte.getText().trim().length() == 0)
        	textErreur = textErreur + ("Une quantité est attendu \n");
        else {
        	String qte = txtQte.getText().trim();
        	lcde.setQuantite(Integer.parseInt(qte));
        }
        
        if (textErreur == "") { // si il n'y a pas d'erreur
        	this.lblLigneCde.setTextFill(Color.BLACK);
        	this.lblLigneCde.setText(lcde.toString2());
        	dao.getLigneCommandeDAO().update(lcde);
        	this.tblLigneCde.refresh();
        	cbxProd.setValue(null);
        	txtQte.setText("");
        	lblTarif.setText("");
        }
        else {
        	this.lblLigneCde.setTextFill(Color.RED);
        	this.lblLigneCde.setText(textErreur);
        }
	}
	
	public void supprimerLigneCde() {
		LigneCommande lcde = this.tblLigneCde.getSelectionModel().getSelectedItem();
        dao.getLigneCommandeDAO().delete(lcde);
        this.tblLigneCde.getItems().remove(lcde);
	}
	
	public void choixProd() {
		try {
            lblTarif.setText(String.valueOf(cbxProd.getSelectionModel().getSelectedItem().getTarif()));
        }
        catch (Exception e) {    
        }
    }
	
	public void choixCl() {
		try {
        cbxClient.setPromptText(cbxClient.getSelectionModel().getSelectedItem().toString());
		}
		catch (Exception e) {
		}
    }

	public void choixTri() {
		int index = cbxTri.getSelectionModel().getSelectedIndex();
	    if (index == 0) {
	    	// faire le tri en fonction du produit se trouvant dans les lignes de commande
	    }
	    else {
	    	this.tblCde.getSortOrder().add(colClient);
	    }
	 }
	
	public void filtreClient() {
		cbxFiltreClient.setPromptText(cbxFiltreClient.getSelectionModel().getSelectedItem().toString());
		FilteredList<Commande> flCde = new FilteredList<>(data, p -> true); // Envoyer les données dans une liste filtree
    	flCde.setPredicate(cde -> {			
    	// Comparaisons
    	int id = cbxFiltreClient.getSelectionModel().getSelectedItem().getNo();
    	if (cde.getClient() == id)
    		return true;
    	else
    		return false; 
    		});
		
    	SortedList<Commande> sortedData = new SortedList<>(flCde);
    	sortedData.comparatorProperty().bind(tblCde.comparatorProperty());
    	tblCde.getItems().clear();
    	tblCde.getItems().addAll(sortedData);
	}
	
	public void filtreProd() {
		cbxFiltreProd.setPromptText(cbxFiltreProd.getSelectionModel().getSelectedItem().toString());
		FilteredList<Commande> flCde = new FilteredList<>(data, p -> true); // Envoyer les données dans une liste filtree
    	flCde.setPredicate(cde -> {    					
    		// Comparaisons
    		boolean test = false;
    		int id = cbxFiltreProd.getSelectionModel().getSelectedItem().getId();
    		int id2 = 0;
    		List<LigneCommande> listeLCde = dao.getLigneCommandeDAO().getAllLigneCommande();
    		for (int i=0; i<listeLCde.size(); i++) {
    			id2 = listeLCde.get(i).getIdProd();
    			if (id == id2) 
    				test = true;
    		}
    		return test;
    	});
		
    	SortedList<Commande> sortedData = new SortedList<>(flCde);
    	sortedData.comparatorProperty().bind(tblCde.comparatorProperty());
    	tblCde.getItems().clear();
    	tblCde.getItems().addAll(sortedData);
	}
}