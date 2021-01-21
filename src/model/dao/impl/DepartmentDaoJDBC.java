package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DbException;
import db.HandleDatabase;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {

	private Connection conn = null;
	
// Constructors 
	public DepartmentDaoJDBC() {
	}
	
	public DepartmentDaoJDBC(Connection conn) {
		this.conn  = conn;
	}
	
// Methods	
	@Override
	public void insert(Department obj) {
		PreparedStatement state = null;
		
		try{
			state = this.conn.prepareStatement(
					"INSERT INTO department " +
				    "(Name) " +
					"VALUES " +
					"(?)", 
					Statement.RETURN_GENERATED_KEYS);
			state.setString(1, obj.getName());
			
			if(state.executeUpdate() > 0) {
				ResultSet result = state.getGeneratedKeys();
				if(result.next()) 
					obj.setId(result.getInt(1));
				
				HandleDatabase.closeResultSet(result);
			}
			else 
				throw new DbException("Unexpected error! No rows affected!");
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			HandleDatabase.closeStatement(state);
		}
		
		
	}

	@Override
	public void update(Department obj) {
		PreparedStatement state = null;
		
		try {
			state = this.conn.prepareStatement(
					"UPDATE department " +
				    "SET Name = ? " +
				    "WHERE Id = ?");
			
			state.setString(1, obj.getName());
			state.setInt(2, obj.getId());
			
			state.executeUpdate();
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			HandleDatabase.closeStatement(state);
		}
		
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement state = null;
		try {
			state = this.conn.prepareStatement(
					"DELETE FROM department WHERE Id = ?");
			state.setInt(1, id);
						
			if(state.executeUpdate() == 0)
				throw new DbException("Invalid Id");
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			HandleDatabase.closeStatement(state);
		}
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement state = null; 
		ResultSet result = null;
		
		try {
			state = this.conn.prepareStatement(
					"SELECT * FROM department WHERE Id = ?");
			state.setInt(1, id);
			result = state.executeQuery();
			
			if(result.next()) {
				Department dep = new Department();
				dep.setId(result.getInt("Id"));
				dep.setName(result.getString("Name"));
				
				return dep;
			}
			else
				return null;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			HandleDatabase.closeResultSet(result);
			HandleDatabase.closeStatement(state);
		}
	}

	@Override
	public List<Department> findAll() {
		
		PreparedStatement state = null;
		ResultSet result = null;
		
		try {
			state = this.conn.prepareStatement(
					"SELECT * FROM department ORDER BY Name");
			result = state.executeQuery();
			
			List<Department> myList = new ArrayList<>();
			
			while(result.next()){
				Department objDep = new Department();
				objDep.setName(result.getString("Name"));
				objDep.setId(result.getInt("Id"));
				myList.add(objDep);
			}
			
			return myList;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			HandleDatabase.closeResultSet(result);
			HandleDatabase.closeStatement(state);
		}
	}
}
