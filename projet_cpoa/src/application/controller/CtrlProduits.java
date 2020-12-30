package application.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import dao.factory.DAOFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import modele.Categorie;
import modele.LigneCommande;
import modele.Produit;

public class CtrlProduits implements Initializable, ChangeListener<Produit> {
	@FXML
    private ComboBox<Categorie> cbxCateg;
	@FXML
    private ComboBox<Categorie> cbxFiltre;
	@FXML
	TableColumn<Produit, Integer> colCateg;
    @FXML
    private Label lblProd;
    @FXML
    private Label lblQuantite;
    @FXML
    private Label lblErreurSuppr;
    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtDesc;
    @FXML
    private TextField txtTarif;
    @FXML
    private TextField txtVisuel;
    @FXML
    private TextField txtRech;
    @FXML
    private TableView<Produit> tblProd;
    @FXML
    private Button btnCreer;
    @FXML
    private Button btnModifier;
    @FXML
    private Button btnSupprimer;
    @FXML
    private Button btnTri;
    
    DAOFactory dao = Controller.getDaos();
    
    private final ObservableList<Produit> data = FXCollections.observableArrayList();
    
    public CtrlProduits() {
    	data.addAll(dao.getProduitDAO().getAllProduits());
    }
    
	@SuppressWarnings("unchecked")
	@Override
    public void initialize(URL location, ResourceBundle resources) {
		// utiliser un getAllCategories2 pour afficher que les noms
    	this.cbxCateg.setItems(FXCollections.observableArrayList(dao.getCategorieDAO().getAllCategories()));
    	this.cbxFiltre.setItems(FXCollections.observableArrayList(dao.getCategorieDAO().getAllCategories()));
    	TableColumn<Produit, String> colNom = new TableColumn<>("Nom");
    	colNom.setCellValueFactory(new PropertyValueFactory<Produit, String>("nom"));
        TableColumn<Produit, String> colDesc = new TableColumn<>("Description");
    	colDesc.setCellValueFactory(new PropertyValueFactory<Produit, String>("description"));
    	TableColumn<Produit, Double> colTarif = new TableColumn<>("Tarif");
    	colTarif.setCellValueFactory(new PropertyValueFactory<Produit, Double>("tarif"));
    	TableColumn<Produit, String> colVisuel = new TableColumn<>("Visuel");
    	colVisuel.setCellValueFactory(new PropertyValueFactory<Produit, String>("visuel"));
    	colCateg = new TableColumn<>("Categorie");
    	colCateg.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("categorie"));
    	this.tblProd.getColumns().setAll(colNom, colDesc, colTarif, colVisuel, colCateg);
    	this.tblProd.getItems().addAll(data);
    	this.tblProd.getSelectionModel().selectedItemProperty().addListener(this);
    }

	public void creerModele() {	
        String textErreur = "";
        Produit prod = new Produit();
        if (txtNom.getText()==null || txtNom.getText().trim().length()==0)
        	textErreur =  textErreur + ("Nom non rempli \n");
        else
        	prod.setNom(txtNom.getText().trim());
        
        if (txtDesc.getText()==null || txtDesc.getText().trim().length()==0)
        	textErreur =  textErreur + ("Description non remplie \n");
        else
        	prod.setDescription(txtDesc.getText().trim());
        
        if (Pattern.matches("[a-zA-Z]+" , txtTarif.getText())) // cela indique qu'uniquement des lettres sont attendues
        	textErreur =  textErreur + ("Que des chiffres sont attendus pour le tarif \n");
        else if (txtTarif.getText()== null || txtTarif.getText().trim().length() == 0)
        	textErreur = textErreur + ("Un prix est attendu \n");
        else {
        	String prix = txtTarif.getText().trim();
        	prod.setTarif(Double.parseDouble(prix));
        }
        if (txtVisuel.getText()==null || txtVisuel.getText().trim().length()==0)
        	textErreur =  textErreur + ("Visuel non rempli \n");
        
        else
        	prod.setVisuel(txtVisuel.getText().trim());
        
        int index = cbxCateg.getSelectionModel().getSelectedIndex()+1;
        if (index >= 1)
        	prod.setCategorie(index);
        else
        	textErreur =  textErreur + ("Pas de categorie selectionnee \n");
        
        textErreur = ExistanceProd(index, textErreur);
        
        if (textErreur == "") {
        	this.lblProd.setTextFill(Color.BLACK);
            this.lblProd.setText(prod.toString2());
            dao.getProduitDAO().create(prod);
            this.data.add(prod);
            this.tblProd.getItems().clear();
            this.tblProd.getItems().addAll(data);
            txtNom.setText("");
            txtDesc.setText("");
            txtTarif.setText("");
            txtVisuel.setText("");
            cbxCateg.setValue(null);
            cbxCateg.setPromptText("");
        }
        else {
        	this.lblProd.setTextFill(Color.RED);
            this.lblProd.setText(textErreur);
        }
    }
    
    public void modifierModele() {
    	String textErreur = "";
    	int index = cbxCateg.getSelectionModel().getSelectedIndex()+1;
    	textErreur = ExistanceProd(index, textErreur);
        
    	if (textErreur == "") {
    		Produit prod = this.tblProd.getSelectionModel().getSelectedItem();
            if (txtNom.getText()==null || txtNom.getText().trim().length()==0)
            	textErreur =  textErreur + ("Nom non rempli \n");
            else
            	prod.setNom(txtNom.getText().trim());
            
            if (txtDesc.getText()==null || txtDesc.getText().trim().length()==0)
            	textErreur =  textErreur + ("Description non remplie \n");
            else
            	prod.setDescription(txtDesc.getText().trim());
            
            if (Pattern.matches("[a-zA-Z]+" , txtTarif.getText())) // cela indique qu'uniquement des lettres sont attendues
            	textErreur =  textErreur + ("Que des chiffres sont attendus pour le tarif \n");
            else if (txtTarif.getText()== null || txtTarif.getText().trim().length() == 0)
            	textErreur = textErreur + ("Un prix est attendu \n");
            else {
            	String prix = txtTarif.getText().trim();
            	prod.setTarif(Double.parseDouble(prix));
            }
            if (txtVisuel.getText()==null || txtVisuel.getText().trim().length()==0)
            	textErreur =  textErreur + ("Visuel non rempli \n");
            
            else
            	prod.setVisuel(txtVisuel.getText().trim());
            
            if (index >= 1)
            	prod.setCategorie(index);
            else
            	textErreur =  textErreur + ("Pas de categorie selectionnee \n");
            
            if (textErreur == "") { // si il n'y a pas d'erreur
            	this.lblProd.setTextFill(Color.BLACK);
                this.lblProd.setText(prod.toString2());
                dao.getProduitDAO().update(prod);
                this.tblProd.getItems().clear();
                this.tblProd.getItems().addAll(data);
                txtNom.setText("");
                txtDesc.setText("");
                txtTarif.setText("");
                txtVisuel.setText("");
                cbxCateg.setValue(null);
                cbxCateg.setPromptText("");
            }
            else {
            	this.lblProd.setTextFill(Color.RED);
                this.lblProd.setText(textErreur);
            }
    	}
        else {
        	this.lblProd.setTextFill(Color.RED);
            this.lblProd.setText(textErreur);
        }
    }
    
    public void supprimerModele(){
    	Produit prod = this.tblProd.getSelectionModel().getSelectedItem();
    	List<LigneCommande> listcde = dao.getLigneCommandeDAO().getAllLigneCommande();
        boolean bool = false;
        int id_prod= 0;
        for (int i=0; i<listcde.size() ; i++) {
             id_prod = listcde.get(i).getIdProd();
            if (id_prod == prod.getId()) {
                bool = true;
                lblErreurSuppr.setVisible(true);
                break;
            }
        }
        if (bool == false) {
            lblErreurSuppr.setVisible(false);
            dao.getProduitDAO().delete(prod);
            this.data.remove(prod);
            this.tblProd.getItems().clear();
            this.tblProd.getItems().addAll(data);
        }
    }
    
    private String ExistanceProd(int index, String textErreur) {
        // on teste si le produit existe déjà
        List<Produit> lsProd = dao.getProduitDAO().getAllProduits();
        for (int i=0; i<lsProd.size() ; i++) {
            // si tout est identique
            if (lsProd.get(i).getNom().toLowerCase().trim().equals(txtNom.getText().trim().toLowerCase()) && 
                    (lsProd.get(i).getDescription().toLowerCase().trim().equals(txtDesc.getText().trim().toLowerCase())) && 
                    (Double.parseDouble(txtTarif.getText().trim()) == lsProd.get(i).getTarif()) &&
                    (lsProd.get(i).getVisuel().toLowerCase().trim().equals(txtVisuel.getText().trim().toLowerCase())) &&
                    (index == lsProd.get(i).getCategorie()))
                textErreur = textErreur + ("Ce produit existe déjà");    
        }
        return textErreur;
    }	
	
	public void triCateg() {
		this.tblProd.getSortOrder().add(colCateg);    
	 }
	
	public void choixCateg() {
		try {
		cbxCateg.setPromptText(cbxCateg.getSelectionModel().getSelectedItem().toString()); 
		}
		catch (Exception e) {
		}
	}
	
	public void rechercheProd() {
		FilteredList<Produit> flProd = new FilteredList<>(data, p -> true); // Envoyer les données dans une liste filtree
    	flProd.setPredicate(prod -> {
    	   					
    	// Comparaisons
    	String lowerCaseFilter = txtRech.getText();
    	if (prod.getNom().toLowerCase().contains(lowerCaseFilter))
    		return true; 
    	else if(Pattern.matches("[0-9]+" , lowerCaseFilter)){
    		if (prod.getTarif() <= Double.parseDouble(lowerCaseFilter))
    			return true;
    	}
    	
    	return false; 
    	});
    			
    	SortedList<Produit> sortedData = new SortedList<>(flProd);
    	sortedData.comparatorProperty().bind(tblProd.comparatorProperty());
    	tblProd.getItems().clear();
    	tblProd.getItems().addAll(sortedData);
	}
	
	public void filtreProd() {
		FilteredList<Produit> flProd = new FilteredList<>(data, p -> true); // Envoyer les données dans une liste filtree
    	flProd.setPredicate(prod -> {
    	// Comparaisons
    	int id = cbxFiltre.getSelectionModel().getSelectedItem().getId();
    	
    	if (prod.getCategorie() == id)
    		return true;
    	return false; 
    	});
		
    	SortedList<Produit> sortedData = new SortedList<>(flProd);
    	sortedData.comparatorProperty().bind(tblProd.comparatorProperty());
    	tblProd.getItems().clear();
    	tblProd.getItems().addAll(sortedData);
	}
	
	@Override
	public void changed(ObservableValue<? extends Produit> observable, Produit oldValue, Produit newValue) {
		this.btnModifier.setDisable(newValue == null);
		this.btnSupprimer.setDisable(newValue == null);
		try {
			txtNom.setText(tblProd.getSelectionModel().getSelectedItem().getNom());;
			txtDesc.setText(tblProd.getSelectionModel().getSelectedItem().getDescription());
			txtVisuel.setText(tblProd.getSelectionModel().getSelectedItem().getVisuel());
			double prix = tblProd.getSelectionModel().getSelectedItem().getTarif();
			txtTarif.setText(String.valueOf(prix));
			List<LigneCommande> listeLCde = dao.getLigneCommandeDAO().getAllLigneCommande();
			int idLCde = 0;
			int idProd = 0;
			int somme = 0;
			for (int i=0; i<listeLCde.size(); i++) {
				idLCde = listeLCde.get(i).getIdProd();
				idProd = tblProd.getSelectionModel().getSelectedItem().getId();
				if (idLCde == idProd)
					somme = somme + listeLCde.get(i).getQuantite();
			}
			lblQuantite.setText("Ce produit a été commandé en " + somme + " exemplaires.");
		}
		catch (Exception e) {
		}
	}
}