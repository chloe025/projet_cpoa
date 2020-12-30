package modele;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/*
 * Representation metier d'une 'commande'.
 * Une commande est realisee a une date par un client.
 * Et contient plusieurs produits.
 */
public class Commande {
	DateTimeFormatter formatage = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private int num;
	private LocalDate date;
	private int client;
	
	
	public Commande(int num, LocalDate date, int client) {
		super();
		this.num = num;
		this.date = date;
		this.client = client;
	}

	public Commande() {
		// TODO Auto-generated constructor stub
	}

	//getter et setter
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		if (date==null ) {
			throw new IllegalArgumentException("Date vide !");
		}
		this.date = date;
	}

	public int getClient() {
		return client;
	}

	public void setClient(int client) {
//		if (client) {
//			throw new IllegalArgumentException("Nom de la personne vide !");
//		}
		this.client = client;
	}
	
	@Override
	public String toString() {
		return num + " " + date + ", client = " + client;
	}
	public String toString2() {
		return "\nCommande : no = " + num + ", date = " + date + ", client associe = " + client;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + num;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Commande other = (Commande) obj;
		if (num != other.num)
			return false;
		return true;
	}

}
