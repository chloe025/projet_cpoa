package dao;

import java.util.List;

import modele.Client;

public interface ClientDAO extends DAO<Client>{
	
	List<Client> getAllClients();
}
