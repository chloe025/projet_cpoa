package test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import dao.factory.DAOFactory;
import modele.Categorie;
import modele.Client;
import modele.Commande;
import modele.LigneCommande;
import modele.Produit;

public class Fonctions_menu {
	
	static void create_produit(DAOFactory daos) {
		Scanner sc = new Scanner(System.in);
		System.out.println("\nVeuillez entrer un nom de produit : ");
		String title = sc.nextLine();
		System.out.println("\nVeuillez entrer une description : ");
		String description =sc.nextLine();
		System.out.println("\nVeuillez entrer un tarif : ");
		double tarif =sc.nextDouble();
		sc.nextLine(); 
		System.out.println("\nVeuillez entrer un visuel : ");
		String visuel =sc.nextLine();
		System.out.println("\nVeuillez entre l'id de la categorie a laquelle le produit "+title+ " appartient : ");
		int id_categ =sc.nextInt();
		
		Produit produit = new Produit();   
		produit.setNom(title);
		produit.setDescription(description);
		produit.setTarif(tarif);
		produit.setVisuel(visuel);
		produit.setCategorie(id_categ);
		try {
			daos.getProduitDAO().create(produit);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	static void modif_produit(DAOFactory daos) {
		System.out.println("Entrez l'id du produit a modifier");
		Scanner sc = new Scanner(System.in);
		int id = sc.nextInt();
		sc.nextLine();
		System.out.println("\nEntrez le nouveau nom : ");
		String title = sc.nextLine();
		System.out.println("\nEntrez la nouvelle description : ");
		String description =sc.nextLine();
		System.out.println("\nEntrez le nouveau tarif : ");
		double tarif =sc.nextDouble();
		sc.nextLine(); 
		System.out.println("\nEntrez le nouveau visuel : ");
		String visuel =sc.nextLine();
		System.out.println("\nEntrez la nouvelle categorie associee a "+title+" : ");
		int id_categ =sc.nextInt();
	
		Produit produit = daos.getProduitDAO().getById(id);   
		produit.setNom(title);
		produit.setDescription(description);
		produit.setTarif(tarif);
		produit.setVisuel(visuel);
		produit.setCategorie(id_categ);
		try {				
			daos.getProduitDAO().update(produit);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	static void suppr_produit(DAOFactory daos) {
		System.out.println("Entrez l'id du produit que vous voulez supprimer");
		Scanner sc = new Scanner(System.in);
		int id = sc.nextInt();
		try {	
			daos.getProduitDAO().delete(daos.getProduitDAO().getById(id));
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	static void affich_produit(DAOFactory daos) {
		List<Produit> liste = daos.getProduitDAO().getAllProduits();
		System.out.println("Liste des produits :\n"+liste+"\n");
	}
	
	static void create_categorie(DAOFactory daos) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\nVeuillez entrer un titre de categorie : ");
		String title = sc.nextLine();
		System.out.println("\nVeuillez entrer un lien d'image : ");
		String link =sc.nextLine();
		Categorie categorie = new Categorie(0, title, link);
		try {
			daos.getCategorieDAO().create(categorie);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	static void modif_categorie(DAOFactory daos) {
		System.out.println("Entrez l'id de la categorie a modifier");
		Scanner sc = new Scanner(System.in);
		int id = sc.nextInt();
		
		sc.nextLine();
		System.out.println("Entrez le nouveau titre : \n");
		String title = sc.nextLine();
		System.out.println("Entrez le nouveau visuel : \n");
		String visuel = sc.nextLine();
		Categorie categ = daos.getCategorieDAO().getById(id);
		categ.setTitre(title);
		categ.setVisuel(visuel);
				
		try {				
			daos.getCategorieDAO().update(categ);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	static void suppr_categorie(DAOFactory daos) {
		System.out.println("Entre l'id de la categorie a supprimer");
		Scanner sc = new Scanner(System.in);
		int id = sc.nextInt();
		
		try {				
			daos.getCategorieDAO().delete(daos.getCategorieDAO().getById(id));
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	static void affich_categorie(DAOFactory daos) {
		List<Categorie> liste = daos.getCategorieDAO().getAllCategories();
		System.out.println("Liste des categories :\n"+liste+"\n");
	}
	
	static void create_client(DAOFactory daos) {
		Scanner sc;
		System.out.println("\nVeuillez entrer un nom : ");
		sc = new Scanner(System.in);
		String nom = sc.nextLine();
		System.out.println("\nVeuillez entre un prenom : ");
		String prenom =sc.nextLine();
				
		Client client = new Client();
		client.setNo(0);
		client.setNom(nom);
		client.setPrenom(prenom);
		
		try {				
			daos.getClientDAO().create(client);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	static void modif_client(DAOFactory daos) {
		Scanner sc;
		sc = new Scanner(System.in);
		//tester si l'id existe
		
		System.out.println("Entrez l'id du client a modifier\n");
		int id = sc.nextInt();
		sc.nextLine();
		System.out.println("Entrez le nouveau nom : \n");
		String nom = sc.nextLine();
		System.out.println("Entrez le nouveau prenom : \n");
		String prenom = sc.nextLine();
		Client client = daos.getClientDAO().getById(id);
		client.setNom(nom);
		client.setPrenom(prenom);
		try {
			daos.getClientDAO().update(client);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	static void suppr_client(DAOFactory daos) {
		System.out.println("Que souhaitez-vous supprimer ? \n"
			+ "Entrez l'id du client a supprimer : \n");
		Scanner sc;
		sc = new Scanner(System.in);
		// tester si l'id existe ou non
		int id = sc.nextInt();
		sc.nextLine();
		try {				
			daos.getClientDAO().delete(daos.getClientDAO().getById(id));
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	static void affich_client(DAOFactory daos) {
		List<Client> liste = daos.getClientDAO().getAllClients();
		System.out.println("Liste des clients :\n"+liste+"\n");
	}

	static void create_commande(DAOFactory daos) {
		Scanner sc;
		System.out.println("\nVeuillez entrer une date  : ");
		sc = new Scanner(System.in);
		String Stringdate = sc.nextLine();
		DateTimeFormatter formatage = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dateDebut = LocalDate.parse(Stringdate, formatage);
		
		System.out.println("\nVeuillez entrer l'id du client associe: ");
		int idCl = sc.nextInt();
				
		Commande cde = new Commande();
		cde.setNum(0);
		cde.setDate(dateDebut);
		cde.setClient(idCl);;
		
		try {				
			daos.getCommandeDAO().create(cde);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	static void modif_commande(DAOFactory daos) {
		Scanner sc;
		System.out.println("Entrez l'id de la commande a modifier\n");
		sc = new Scanner(System.in);
		int idCde = sc.nextInt();
		sc.nextLine();
		System.out.println("\nVeuillez entrer une date  (jour/mois/annee) : ");
		String Stringdate = sc.nextLine();
		DateTimeFormatter formatage = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dateDebut = LocalDate.parse(Stringdate, formatage);
		
		System.out.println("\nVeuillez entrer l'id du client associe: ");
		int idCl = sc.nextInt();
				
		Commande cde = daos.getCommandeDAO().getById(idCde);
		cde.setDate(dateDebut);
		cde.setClient(idCl);
		
		try {				
			daos.getCommandeDAO().update(cde);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	static void suppr_commande(DAOFactory daos) {
		System.out.println("Que souhaitez-vous supprimer ? \n"
				+ "Entrez l'id de la commande a supprimer : \n");
			Scanner sc;
			sc = new Scanner(System.in);
			// tester si l'id existe ou non
			int id = sc.nextInt();
			sc.nextLine();
			try {				
				daos.getCommandeDAO().delete(daos.getCommandeDAO().getById(id));
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
		
	}

	static void affich_commande(DAOFactory daos) {
		List<Commande> liste = daos.getCommandeDAO().getAllCommandes();
		System.out.println("Liste des commandes :\n"+liste+"\n");
	}
	
	static void create_lignecommande(DAOFactory daos) {
		//Création de la liste de produit
		
		Scanner sc;
		System.out.println("\nVeuillez entrer un id de commande : ");
		sc = new Scanner(System.in);
		int idCde = sc.nextInt();
		System.out.println("\nVeuillez entrer un id de produit : ");
		
		int idProd = sc.nextInt();
		
		System.out.println("\nVeuillez entrer une quantite : ");
		int qte = sc.nextInt();
		
		System.out.println("\nVeuillez entrer un tarif unitaire : ");
		int tarif = sc.nextInt();
		
		HashMap<Produit, Integer> LC = new HashMap<Produit, Integer>();
		Produit prod = new Produit();
		prod.setId(idProd);
		LC.put(prod, qte);
				
		LigneCommande lcde = new LigneCommande();
		lcde.setIdCommande(idCde);
		lcde.setIdProd(idProd);
		lcde.setQuantite(qte);
		lcde.setTarif(tarif);;
		
		try {				
			daos.getLigneCommandeDAO().create(lcde);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	static void modif_lignecommande(DAOFactory daos) {
		Scanner sc;
		System.out.println("\nVeuillez entrer l'id de la commande correspondant à la ligne commande à modifier : ");
		sc = new Scanner(System.in);
		int idCde = sc.nextInt();
		System.out.println("\nVeuillez entrer l'id du produit correspondant à la ligne commande à modifier :  ");
		int idProd = sc.nextInt();
		
		System.out.println("\nVeuillez entrer une quantite : ");
		int qte = sc.nextInt();
		
		System.out.println("\nVeuillez entrer un tarif unitaire : ");
		int tarif = sc.nextInt();
				
		LigneCommande lcde = daos.getLigneCommandeDAO().getById(idCde, idProd);
		lcde.setIdCommande(idCde);
		lcde.setIdProd(idProd);
		lcde.setQuantite(qte);
		lcde.setTarif(tarif);;
		
		try {				
			daos.getLigneCommandeDAO().update(lcde);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	static void suppr_lignecommande(DAOFactory daos) {
		System.out.println("Que souhaitez-vous supprimer ? \n"
				+ "Entrez l'id de la commande : \n");
			Scanner sc;
			sc = new Scanner(System.in);
			// tester si l'id existe ou non
			int idCde = sc.nextInt();
			System.out.println("Entrez l'id du produit : \n");
			int idProd = sc.nextInt();
			sc.nextLine();
			try {				
				daos.getLigneCommandeDAO().delete(daos.getLigneCommandeDAO().getById(idCde, idProd));
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
		
	}

	static void affich_lignecommande(DAOFactory daos) {
		List<LigneCommande> liste = daos.getLigneCommandeDAO().getAllLigneCommande();
		System.out.println("Liste des lignes de commande :\n"+liste+"\n");
	}

}
