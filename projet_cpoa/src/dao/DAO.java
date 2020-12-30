package dao;


public interface DAO<T> {
	
	public abstract boolean create(T object); // Create
	public abstract T getById(int id); // Read
	public abstract boolean update(T object); // Update
	public abstract boolean delete(T object); // Delete
}
