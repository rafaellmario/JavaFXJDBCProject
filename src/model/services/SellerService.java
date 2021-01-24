package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class SellerService {
	// attributes
	private SellerDao dao = DaoFactory.createSellerDao();
	
	// constructors
	
	// methods
	public List<Seller> findAll(){
		return dao.findAll();
	}
	
	public void saveOrUpdate(Seller sel) {
		if(sel.getId() == null)
			this.dao.insert(sel);
		else
			dao.update(sel);
	}
	
	public void remove(Seller sel) {
		this.dao.deleteById(sel.getId());
	}

}
