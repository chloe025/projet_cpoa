package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import dao.CategorieDAO;
import dao.ProduitDAO;
import dao.enumeration.Persistance;
import dao.factory.DAOFactory;
import modele.Categorie;
import modele.Client;
import modele.Commande;
import modele.Produit;

public class Main {
	public static void main(String[] args) {
		
		/*
		 * **********************************
		 * 			Menu principal			*
		 * **********************************
		 */
		System.out.println("Bonjour, bienvenue dans l'application");
		DAOFactory daos = TypeEnum();	// on choisit le DAOfactory à utiliser et est stocké dans daos
				
		System.out.println("Que souhaitez-vous faire ?");
		int a = ChoisiMenu(daos);
		
}

	
	// LES FONCTIONS QUI PERMETTENT DE CHOISIR UN MENU
	public static DAOFactory TypeEnum() {
		int a;
		Scanner sc;
		do {
			System.out.println("Choisissez le mode MySQL(1) ou ListeMemoire(2)");
			sc = new Scanner(System.in);
			a = sc.nextInt();
		}while ((a<1)|| (a>2));
		
		DAOFactory daos;
		if (a == 1) daos = DAOFactory.getDAOFactory(Persistance.MYSQL);
		else daos = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE);
	
		return daos;
	}
	
	private static int ChoisiMenu(DAOFactory daos) {
		int a=0;
		Scanner sc;
		do {
			System.out.println("Changer de BDD ? Pressez la touche 1\n"
					+ "Gérer les produits ? Pressez la touche 2 \n"
					+ "Gérer les catégories ?  Pressez la touche 3 \n"
					+ "Gérer les clients ?  Pressez la touche 4\n"
					+ "Gérer les commandes en cours ? Pressez la touche 5\n"
					+ "Gérer les lignes de commandes en cours ? Pressez la touche 6\n"
					+ "Quitter l'application ? Pressez la touche 7");
			sc = new Scanner(System.in);
			a =sc.nextInt();
		}while((a < 1) || (a > 7));
		
		if (a==1) {
			a=0;
			daos = TypeEnum();
		}
		
		else if (a == 2) {	// si on sélectionne le menu 1
			a=0;
			MenuProduit(daos,a);
		}
		
		else if (a == 3) {
			a = 0;
			MenuCategorie(daos, a);
		}
				
		else if (a == 4) {
			a = 0;
			MenuClient(daos, a);			
		}
		else if (a == 5) {
			a = 0;
			MenuCommande(daos,a);
		}
		else if (a == 6) {
			a = 0;
			MenuLigneCommande(daos, a);
		}
		else if (a == 7) a = 1; // on termine
		
		if (a==0) a = ChoisiMenu(daos);
		
		return a;
	}

	
	private static void MenuCommande(DAOFactory daos, int a) {
		int b = ChoisiSousMenuCde();
		switch(b) {
			case 1:{
				Fonctions_menu.create_commande(daos);
				System.out.println("Que souhaitez-vous faire ?");
				break;
			}
			case 2:{
				Fonctions_menu.modif_commande(daos);
				System.out.println("Que souhaitez-vous faire ?");
				break;
			}
			case 3:{
				Fonctions_menu.suppr_commande(daos);
				System.out.println("Que souhaitez-vous faire ?");
				break;
			}
			case 4:{
				Fonctions_menu.affich_commande(daos);
				System.out.println("Que souhaitez-vous faire ?");
				break;
			}
			case 5:{
				System.out.println("Vous quittez le menu Commande");
				a=1;
				break;
			}
				default : System.out.println("Mauvaise touche, veuillez réessayer.");
			}
		if (a==0) MenuCommande(daos, a);
		}
	
	private static void MenuLigneCommande(DAOFactory daos, int a) {
		int b = ChoisiSousMenuLigneCde();
		switch(b) {
			case 1:{
				Fonctions_menu.create_lignecommande(daos);
				System.out.println("Que souhaitez-vous faire ?");
				break;
			}
			case 2:{
				Fonctions_menu.modif_lignecommande(daos);
				System.out.println("Que souhaitez-vous faire ?");
				break;
			}
			case 3:{
				Fonctions_menu.suppr_lignecommande(daos);
				System.out.println("Que souhaitez-vous faire ?");
				break;
			}
			case 4:{
				Fonctions_menu.affich_lignecommande(daos);
				System.out.println("Que souhaitez-vous faire ?");
				break;
			}
			case 5:{
				System.out.println("Vous quittez le menu Ligne Commande");
				a=1;
				break;
			}
				default : System.out.println("Mauvaise touche, veuillez réessayer.");
			}
		if (a==0) MenuLigneCommande(daos, a);
		}


	private static void MenuProduit(DAOFactory daos, int a) {
		int b = ChoisiSousMenuProd();
		
		switch (b) {
			case 1:		// on veut ajouter un produit
			{
				Fonctions_menu.create_produit(daos);
				System.out.println("Que souhaitez-vous faire ?");
				break;
			}
			
			case 2:		// on veut modifier un produit
			{ 
				List<Produit> liste = daos.getProduitDAO().getAllProduits();
				System.out.println("\n" + liste);
				/*boolean existe = false;
				do {		// on vérifie si l'id donné par l'utilisateur existe ou non
					id = sc.nextInt();
					existe = Requetes.id_prodExiste(id);
					if (existe == false) System.out.println("l'id n'existe pas, essayez un autre.");
				} while (!existe);*/
				Fonctions_menu.modif_produit(daos);
				System.out.println("Que souhaitez-vous faire ?");
				break;
			}
		
			case 3: // on supprime un produit
			{
				List<Produit> liste = daos.getProduitDAO().getAllProduits();
				System.out.println("\n" + liste);
				/*boolean existe = false;
				do {		// on vérifie si l'id donné par l'utilisateur existe ou non
					id = sc.nextInt();
					existe = Requetes.id_prodExiste(id);
					if (existe == false) System.out.println("l'id n'existe pas, essayez un autre.");
				} while (!existe);*/
				Fonctions_menu.suppr_produit(daos);
				System.out.println("Que souhaitez-vous faire ?");
				break;
			}
		
		case 4:
		{
			Fonctions_menu.affich_produit(daos);
			System.out.println("Que souhaitez-vous faire ?");
			break;
		}
		
		case 5:
		{
			System.out.println("Vous quittez le menu Produit.");
			a = 1;
			break;
		}
		
		default : System.out.println("Mauvaise touche, veuillez réessayer.");
		}
		if (a==0) MenuProduit(daos,a);
	}

	// on sort avec a =0, si on demande à sortir du menu categorie
	private static void MenuCategorie(DAOFactory daos, int a) {
		int b = ChoisiSousMenuCateg();
		switch (b) {

		case 1:		// on veut ajouter une catégorie
		{
				Fonctions_menu.create_categorie(daos);
				System.out.println("Que souhaitez-vous faire ?");
				break;
		}
			
		case 2:		// on veut modifier une catégorie
		{
			List<Categorie> liste = daos.getCategorieDAO().getAllCategories();
			System.out.println("\n" + liste);
			/*boolean existe = false;
			do {		// on vérifie si l'id donné par l'utilisateur existe ou non
				id = sc.nextInt();
				existe = Requetes.id_CategExiste(id);
				if (existe == false) System.out.println("l'id n'existe pas, essayez un autre.");
			} while (!existe);*/
			Fonctions_menu.modif_categorie(daos);
			System.out.println("Que souhaitez-vous faire ?");
			break;
		}
		
		case 3:
		{	
			List<Categorie> liste = daos.getCategorieDAO().getAllCategories();
			System.out.println("\n" + liste);
			/*boolean existe = false;
			do {		// on vérifie si l'id donné par l'utilisateur existe ou non
				id = sc.nextInt();
				existe = Requetes.id_CategExiste(id);
				if (existe == false) System.out.println("l'id n'existe pas, essayez un autre.");
			} while (!existe);*/
			Fonctions_menu.suppr_categorie(daos);
			System.out.println("Que souhaitez-vous faire ?");
			break;
		}
		
		case 4:
		{
			Fonctions_menu.affich_categorie(daos);
			System.out.println("Que souhaitez-vous faire ?");
			break;
		}
		
		case 5:
		{
			System.out.println("Vous quittez le menu Catégorie.");
			a = 1;
			break;
		}
		default : System.out.println("Mauvaise touche, veuillez réessayer.");
		}
		if (a==0) MenuCategorie(daos,a);
	}

	// on sort avec a =0, si on demande à sortir du menu client
	private static void MenuClient(DAOFactory daos, int a) {
		int b = ChoisiSousMenuCl();
		switch (b) {
			case 1:
			{
				Fonctions_menu.create_client(daos);						
				System.out.println("Que souhaitez-vous faire ?");
				break;
			}				
			case 2:
			{
				List<Client> liste = daos.getClientDAO().getAllClients();
				System.out.println("\n" + liste);
				Fonctions_menu.modif_client(daos);	
				/*boolean existe = false;			
				do {		// on vérifie si l'id donné par l'utilisateur existe ou non
					id = sc.nextInt();
					existe = Requetes.id_ClientExiste(id);
					if (existe == false) System.out.println("l'id n'existe pas, essayez un autre.");
				} while (!existe);*/
					
				System.out.println("Que souhaitez-vous faire ?");
				break;
			}						
			case 3:
			{
				List<Client> liste = daos.getClientDAO().getAllClients();
				System.out.println("\n" + liste);
				Fonctions_menu.suppr_client(daos);
					/*boolean existe = false;
					do {		// on vérifie si l'id donné par l'utilisateur existe ou non
						id = sc.nextInt();
						existe = Requetes.id_ClientExiste(id);
						if (existe == false) System.out.println("l'id n'existe pas, essayez un autre.");
					} while (!existe);*/
				System.out.println("Que souhaitez-vous faire ?");
				break;
			}				
			case 4:
			{
				Fonctions_menu.affich_client(daos);
				System.out.println("Que souhaitez-vous faire ?");
				break;
			}
			case 5:
			{
				System.out.println("Vous quittez le menu Client.");
				a = 1;
				break;
			}	
				default : System.out.println("Mauvaise touche, veuillez réessayer.");
			}
		if (a==0) MenuClient(daos, a);
	}

	private static int ChoisiSousMenuCl() {
		int a=0;
		Scanner sc;
		do {
			System.out.println("Ajouter un client ? Appuyez sur 1\n"
					+ "Modifier un client ? Appuyez sur 2\n"
					+ "Supprimer un client ? Appuyez sur 3\n"
					+ "Afficher la liste des clients ? Appuyez sur 4\n"
					+ "Quitter le menu ? Appuyez sur 5");
			sc = new Scanner(System.in);
			a =sc.nextInt();
		}while(a < 1 || a > 5);
	return a;
	}

	private static int ChoisiSousMenuProd() {
		int a=0;
		Scanner sc;
		do {
			System.out.println("Ajouter un produit ? Appuyez sur 1\n" 
					+ "Modifier un produit ? Appuyez sur 2\n"
					+ "Supprimer un produit ? Appuyez sur 3\n"
					+ "Afficher la liste des produits ? Appuyez sur 4\n"
					+ "Quitter le menu ? Appuyez sur 5");
			sc = new Scanner(System.in);
			a = sc.nextInt();	//on regarde quel sous-menu est choisi
			sc.nextLine();
		}while(a < 1 || a > 5);
		return  a;
	}
	
	private static int ChoisiSousMenuCateg() {
		int a=0;
		Scanner sc;
		do {
			System.out.println("Ajouter une catégorie ? Appuyez sur 1\n" 
					+ "Modifier une catégorie ? Appuyez sur 2\n"
					+ "Supprimer une catégorie ? Appuyez sur 3\n"
					+ "Afficher la liste des catégories ? Appuyez sur 4\n"
					+ "Quitter le menu ? Appuyez sur 5");
			sc = new Scanner(System.in);
			a =sc.nextInt();
		}while ((a<1)||(a>5));
		return a;
	}
	
	private static int ChoisiSousMenuCde() {
		int a =0;
		Scanner sc;
		do {
			System.out.println("Ajouter une commande ? (1)\n"
					+ "Modifier une commande ? (2)\n"
					+ "Supprimer une commande ? (3)\n"
					+ "Afficher les commandes (4)\n"
					+ "Quitter le menu ? (5)");
			sc = new Scanner(System.in);
			a = sc.nextInt();
		}while ((a<1)||(a>5));
		return a;
	}
	
	private static int ChoisiSousMenuLigneCde() {
		int a =0;
		Scanner sc;
		do {
			System.out.println("Ajouter une ligne de commande ? (1)\n"
					+ "Modifier une ligne de commande ? (2)\n"
					+ "Supprimer une ligne de commande ? (3)\n"
					+ "Afficher les lignes de commandes (4)\n"
					+ "Quitter le menu ? (5)");
			sc = new Scanner(System.in);
			a = sc.nextInt();
		}while ((a<1)||(a>5));
		return a;
	}
}

