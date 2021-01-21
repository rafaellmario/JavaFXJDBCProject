package model.dao;

import java.util.List;

import model.entities.Department;
import model.entities.Seller;

public interface SellerDao {
	
	public abstract void insert(Seller obj); 
	
	public abstract void update(Seller obj);
	
	public abstract void deleteById(Integer id);
	
	public abstract Seller findById(Integer id); 
	
	public abstract List<Seller> findByDepartment(Department depa);
	
	public abstract List<Seller> findAll();
		
}