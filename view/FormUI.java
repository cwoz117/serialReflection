package view;




import controller.BtnFormSubmitHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FormUI implements EventHandler<ActionEvent>{
	

	
	private SerializeUI sui;
	private Stage stage;
	private Label lblClassSelection;

	public TextField txtInt;
	public TextField txtDbl;
	public TextField txtBool;
	
	private ObservableList<String> listClasses;
	private ComboBox<String> cboClassSelection;
	private Button btnSubmit;
	
	private Object obj;
	
	public FormUI(SerializeUI ui){
		this.sui = ui;
		lblClassSelection = new Label("Choose which class you would like to add: ");
		listClasses = FXCollections.observableArrayList(
				"primitive array",
				"reference array",
				"primitive",
				"reference");
		cboClassSelection = new ComboBox<String>(listClasses);
		cboClassSelection.setValue("primitive");
		btnSubmit = new Button("Submit");
		btnSubmit.setOnAction(new BtnFormSubmitHandler(this, sui));

		
		start();
	}
	
	public void start(){
		stage = new Stage();
		stage.setTitle("Add a Class");
		stage.setResizable(false);
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(10));
		HBox control = new HBox(lblClassSelection, cboClassSelection);
		control.setPadding(new Insets(10));
		
		VBox form = new VBox(control, new Separator());
		root.setTop(form);
		root.setCenter(new VBox(primitiveForm(), new Separator()));
		root.setBottom(btnSubmit);
		root.setAlignment(btnSubmit, Pos.BOTTOM_RIGHT);
		stage.setScene(new Scene(root));
		stage.show();

	}
	
	
	public GridPane primitiveForm(){
		txtInt = new TextField();
		txtDbl = new TextField();
		txtBool = new TextField();
		Label lblInt = new Label( "integer value: ");
		Label lblDbl = new Label( "double value: ");
		Label lblBool = new Label("boolean value: ");
		lblInt.setMinWidth(64);
		lblDbl.setMinWidth(64);
		lblBool.setMinWidth(64);
		GridPane form = new GridPane();
		form.setAlignment(Pos.CENTER);
		form.setHgap(5);
		form.setVgap(5);
		form.setPadding(new Insets(5,5,5,5));
		
		form.add(lblInt, 0, 0);
		form.add(txtInt, 1, 0);
		form.add(lblDbl, 0, 1);
		form.add(txtDbl, 1, 1);
		form.add(lblBool, 0, 2);
		form.add(txtBool, 1, 2);
		return form;
	}
	
	public GridPane referenceForm(){
		return null;
		
	}
	public GridPane arrayForm(){
		return null;}
	
	public ComboBox<String> getComboBox() {return cboClassSelection;}
	public Stage getStage(){return stage;}
	public Object getBuilt(){return obj;}
	
	
	public void handle(ActionEvent event) {
		sui.getStage().show();
		sui.getSendList().setItems(sui.getSendItems());
	}
	
	public HBox hGroup(Node... numbers){
		HBox h = new HBox(5);
		h.setPadding(new Insets(1));
		h.getChildren().addAll(numbers);
		return h;
	}
	public VBox vGroup(Node... numbers){
		VBox h = new VBox(5);
		h.setPadding(new Insets(1));
		h.getChildren().addAll(numbers);
		return h;
	}


	
	
}
