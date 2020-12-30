package application.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import dao.factory.DAOFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import modele.Categorie;
import modele.Produit;

public class CtrlCategories implements Initializable, ChangeListener<Categorie> {
	
    @FXML
    private TableView<Categorie> tblCateg;
    @FXML
    private Label lblCateg;
    @FXML
    private Label lblErreurSuppr;
    @FXML
    private TextField txtTitre;
    @FXML
    private TextField txtVisuel;
    @FXML
    private Button btnCreer;
    @FXML
    private Button btnModifier;
    @FXML
    private Button btnSupprimer;
    
    DAOFactory dao = Controller.getDaos();
    
    private final ObservableList<Categorie> data = FXCollections.observableArrayList();
    
    public void CtrlCateg() {
    	data.addAll(dao.getCategorieDAO().getAllCategories());
    }
    
	@SuppressWarnings("unchecked")
	@Override
    public void initialize(URL location, ResourceBundle resources) {
        TableColumn<Categorie, String> colTitre = new TableColumn<>("Titre");
        colTitre.setCellValueFactory(new PropertyValueFactory<Categorie, String>("titre"));
        TableColumn<Categorie, String> colVisuel = new TableColumn<>("Visuel");
        colVisuel.setCellValueFactory(new PropertyValueFactory<Categorie, String>("visuel"));
        this.tblCateg.getColumns().setAll(colTitre, colVisuel);
        this.tblCateg.getItems().addAll(dao.getCategorieDAO().getAllCategories());
        this.tblCateg.getSelectionModel().selectedItemProperty().addListener(this);
    }
    
    public void creerModele() {
        String textErreur = "";
        textErreur = ExistanceCateg(textErreur);
        Categorie categ = new Categorie();
        if (txtTitre.getText()==null || txtTitre.getText().trim().length()==0)
        	textErreur =  textErreur + ("Titre non rempli \n");
        else
        	categ.setTitre(txtTitre.getText().trim());
        
        if (txtVisuel.getText()==null || txtVisuel.getText().trim().length()==0)
        	textErreur =  textErreur + ("Visuel non rempli \n");
        else
        	categ.setVisuel(txtVisuel.getText().trim());
        
        if (textErreur == "") { // si il n'y a pas d'erreur
        	this.lblCateg.setTextFill(Color.BLACK);
            dao.getCategorieDAO().create(categ);
            this.lblCateg.setText(categ.toString2());
            this.tblCateg.getItems().add(categ);
            txtTitre.setText("");
            txtVisuel.setText("");
        }
        else {
        	this.lblCateg.setTextFill(Color.RED);
            this.lblCateg.setText(textErreur);
        }
    }
    
    public void modifierModele() {
    	String textErreur = "";
    	textErreur = ExistanceCateg(textErreur);
    	if (textErreur == "") {
    		Categorie categ = this.tblCateg.getSelectionModel().getSelectedItem();
    		if (txtTitre.getText()==null || txtTitre.getText().trim().length()==0)
                textErreur =  textErreur + ("Titre non rempli \n");
            else
                categ.setTitre(txtTitre.getText().trim());
            
            if (txtVisuel.getText()==null || txtVisuel.getText().trim().length()==0)
                textErreur =  textErreur + ("Visuel non rempli \n");
            else
                categ.setVisuel(txtVisuel.getText().trim());
            
            if (textErreur == "") { // si il n'y a pas d'erreur
            	this.lblCateg.setTextFill(Color.BLACK);
            	dao.getCategorieDAO().update(categ);
            	this.tblCateg.getItems().clear();
                this.tblCateg.getItems().addAll(dao.getCategorieDAO().getAllCategories());
                this.lblCateg.setText(categ.toString2());
            	txtTitre.setText("");
            	txtVisuel.setText("");
            }
            else {
            	this.lblCateg.setTextFill(Color.RED);
               	this.lblCateg.setText(textErreur);
            }
        }
    	else {
        	this.lblCateg.setTextFill(Color.RED);
        	this.lblCateg.setText(textErreur);
        }
    }
    
    public void supprimerModele(){
        Categorie categ = this.tblCateg.getSelectionModel().getSelectedItem();
        List<Produit> listprod = dao.getProduitDAO().getAllProduits();
        boolean bool = false;
        int cat= 0;
        for (int i=0; i<listprod.size() ; i++) {
            cat = listprod.get(i).getCategorie();
            if (cat == categ.getId()) {
                bool = true;
                lblErreurSuppr.setVisible(true);
                break;
            }
        }
        if (bool == false) {
            lblErreurSuppr.setVisible(false);
            dao.getCategorieDAO().delete(categ);
            this.tblCateg.getItems().remove(categ);
        }
    }
    
    private String ExistanceCateg(String textErreur) {
    	List<Categorie> listCat = dao.getCategorieDAO().getAllCategories();
        for (int i=0; i<listCat.size() ; i++) {
        	// si on a qqch dans la liste d'identique à la saisie
        	if (txtTitre.getText().trim().toLowerCase().equals(listCat.get(i).getTitre().trim().toLowerCase()) && 
            (txtVisuel.getText().trim().toLowerCase().equals(listCat.get(i).getVisuel().trim().toLowerCase())))
                 return textErreur = textErreur + ("Cette catégorie existe déjà");
        }
         return textErreur = "";
    }

	@Override
	public void changed(ObservableValue<? extends Categorie> observable, Categorie oldValue, Categorie newValue) {
		this.btnCreer.setDisable(oldValue == null);
		this.btnModifier.setDisable(newValue == null);
		this.btnSupprimer.setDisable(newValue == null);
		try {
			txtTitre.setText(tblCateg.getSelectionModel().getSelectedItem().getTitre());
			txtVisuel.setText(tblCateg.getSelectionModel().getSelectedItem().getVisuel());
		}
		catch (Exception e) {
		}
	}
}