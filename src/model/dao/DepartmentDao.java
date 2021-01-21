package model.dao;

import java.util.List;

import model.entities.Department;

public interface DepartmentDao {
	
	public abstract void insert(Department obj); 
	
	public abstract void update(Department obj);
	
	public abstract void deleteById(Integer id);
	
	public abstract Department findById(Integer id); 
	
	public abstract List<Department> findAll();
	
	
}
