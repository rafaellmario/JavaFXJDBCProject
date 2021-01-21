package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentService {
	// attributes
	private DepartmentDao dao = DaoFactory.createDepartmentDao();
	
	// constructors
	
	// methods
	public List<Department> findAll(){
		return dao.findAll();
	}
	
}
