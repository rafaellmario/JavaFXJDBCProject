package model.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.entities.Department;

public class DepartmentService {
	// attributes
	
	
	// constructors
	
	// methods
	public List<Department> findAll(){
		List<Department> myList = new ArrayList<>();
		myList.addAll(Arrays.asList(new Department(1, "Books"),
				      				new Department(2,"Computers"),
				      				new Department(3, "Electronics")));
		return myList;
	}
	
}
