package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;

public class DepartmentFormController implements Initializable{
	
	// attributes
	private Department entity;
	
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
	
	// Methods
	@FXML 
	public void onBtSaveAction() {
		System.out.println("onBtSaveAction");
	}
	
	@FXML
	public void onBtCancelAction() {
		System.out.println("onBtCancelAction");
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
	
}
