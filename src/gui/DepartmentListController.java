package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentListController implements Initializable, DataChangeListener{
	
	// Attributes
	private DepartmentService service;
	
	private ObservableList<Department> obsList;
	
	@FXML
	private TableView<Department> tableViewDepartment;
	
	@FXML
	private TableColumn<Department, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Department, String> tableColumnName; 
	
	@FXML
	private TableColumn<Department, Department> tableColumnEDIT;
	
	@FXML
	private TableColumn<Department, Department> tableColumnREMOVE;
	
	@FXML
	private Button btNew;
	
	
	// getters and setters
	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}
		
	// Methods
	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		
		Department dep = new Department();
		
		this.createDialogForm(dep, "/gui/DepartmentFormView.fxml", parentStage);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		this.tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		Stage stage = (Stage)Main.getMainScene().getWindow();
		this.tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		if(this.service == null)
			throw new IllegalStateException("service was null");
		
		List<Department> list = this.service.findAll();
		this.obsList = FXCollections.observableArrayList(list);
		
		this.tableViewDepartment.setItems(obsList);
		
		initEditButtons();
		initRemoveButtons();
	}
	
	private void createDialogForm(Department dep, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			DepartmentFormController controller = loader.getController();
			controller.setDepartment(dep);
			
			controller.setDepartmentService(new DepartmentService());
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter department data");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		}
		catch(IOException e) {
			Alerts.showAlert("IOException", "Error Load View", e.getMessage(), AlertType.ERROR);
		}
	}
	
	@Override
	public void onDataChanged() {
		this.updateTableView();
	}
	
	private void initEditButtons() {
		this.tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		this.tableColumnEDIT.setCellFactory(param -> new TableCell<Department, Department>() {
		private final Button button = new Button("edit");
			@Override
			protected void updateItem(Department obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(
								obj, "/gui/DepartmentFormView.fxml",Utils.currentStage(event)));
			}
		});
	}
	
	private void initRemoveButtons() {
		this.tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		this.tableColumnREMOVE.setCellFactory(param -> new TableCell<Department, Department>(){
			private final Button button = new Button("remove");
			
			@Override
			protected void updateItem(Department obj, boolean empty) {
				super.updateItem(obj, empty);
				if(obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}
	
	private void removeEntity(Department obj) {
		Optional<ButtonType> result =Alerts.showConfirmation("Confirmation", "Are you shure to delete?");
		
		if(result.get() == ButtonType.OK) {
			if(this.service == null)
				throw new IllegalStateException("Service was null");
			
			try {
				this.service.remove(obj);
				this.updateTableView();
			}
			catch(DbIntegrityException e) {
				Alerts.showAlert("Error removing object", null, e.getMessage(), AlertType.ERROR);
			}
			
		}
	}
	

}
