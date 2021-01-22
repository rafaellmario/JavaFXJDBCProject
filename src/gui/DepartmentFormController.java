package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentFormController implements Initializable{
	
	// attributes
	private Department entity;
	
	private DepartmentService service;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML 
	private Label labelErrorName;
	
	@FXML 
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	// getters and setters
	public void  setDepartment(Department entity) {
		this.entity = entity;
	}
	
	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}
	
	// Methods
	@FXML 
	public void onBtSaveAction(ActionEvent event) {
		if(this.entity == null)
			throw new IllegalStateException("Entity was null");
		
		if(this.service == null)
			throw new IllegalStateException("Service was null");
		
		try {
			this.entity = getFormData(); 
			this.service.saveOrUpdate(this.entity);
			
			notifyDataChangeListeners();
			
			Utils.currentStage(event).close();
		}
		catch(DbException e) {
			Alerts.showAlert("Error Saving Object", null, e.getMessage(), AlertType.ERROR);
		}
		
			
	}


	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();	
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		this.initializaNodes();
	}
	
	private void initializaNodes() {
		Constraints.setTextFieldInteger(this.txtId);
		Constraints.setTextMaxLength(this.txtName, 30);
	}
	
	public void updateFormData() {
		if(this.entity == null)
			throw new IllegalStateException("Entity was null");
		
		this.txtId.setText(String.valueOf(this.entity.getId()));
		this.txtName.setText(this.entity.getName());
	}
	
	private Department getFormData() {
		Department dep = new Department();
		dep.setId(Utils.tryParsetoInt(this.txtId.getText()));
		dep.setName(this.txtName.getText());
		
		return dep;
	}
	
	public void subscribeDataChangeListener(DataChangeListener list) {
		this.dataChangeListeners.add(list);
	}
	
	private void notifyDataChangeListeners() {
		for(DataChangeListener listener : this.dataChangeListeners)
			listener.onDataChanged();
	}
}
