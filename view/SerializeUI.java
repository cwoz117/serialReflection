package view;

import controller.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.*;

/**
 * A JavaFX Hello World
 * @author carldea
 */
public class SerializeUI extends Application {
	
	private Stage stage;
	private Label lblServer;
	private Label lblPort;
	private Label lblTitle;
	private Label lblStudentInfo; 
	private Label lblRecPortNum;
	private Label lblRecStart;
	private TextField txtRecPortNum;
	private TextField txtServer;
	private TextField txtPort;
	private Button btnSend;
	private Button btnAdd;
	private Button btnRemove;
	private Button btnListen;
	private ListView<Object> sendList;
	private ObservableList<Object> sendItems;

	private Label lblRecTitle;
	private Label lblRecStudent;
	private Button btnClear;
	private ListView<Object> recList;
	private ObservableList<Object> recItems;

	private Thread t;

	public SerializeUI(){
		btnSend = new Button("Send");
		btnSend.setOnAction(new BtnSendHandler(this));
		btnAdd = new Button("Add");
		btnAdd.setOnAction(new BtnAddHandler(this));
		btnAdd.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		btnRemove = new Button("Remove");
		btnRemove.setOnAction(new BtnRemoveHandler(this));
		btnRemove.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		lblServer = new Label("Server Name:");
		lblPort = new Label("Port Number:");
		lblTitle = new Label("Serialize objects for network transfer.");
		lblStudentInfo = new Label("CPSC501\tChris Wozniak\t10109820");
		txtServer = new TextField();
		txtPort = new TextField();
		sendList = new ListView<Object>();
		sendItems = FXCollections.observableArrayList();

		lblRecPortNum = new Label("Listen on port: ");
		lblRecStudent = new Label("CPSC501\tChris Wozniak\t10109820");
		lblRecTitle = new Label("Receive sent serialized objects");
		lblRecStart = new Label("");
		txtRecPortNum = new TextField();
		recList = new ListView<Object>();
		recItems = FXCollections.observableArrayList();
		btnClear = new Button("Clear");
		btnClear.setOnAction(new BtnClearHandler(this));
		btnListen = new Button("Listen");
		btnListen.setOnAction(new BtnListenHandler(this));
		btnClear.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		btnListen.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

		t = null;

	}

	public Stage getStage(){return stage;};
	public Thread getThread(){return t;}
	public TextField getSendServerTxt(){return txtServer;}
	public TextField getSendPortTxt(){return txtPort;}
	public TextField getRecPortTxt(){return txtRecPortNum;}
	public ObservableList<Object> getSendItems() {return sendItems;}
	public ObservableList<Object> getRecItems() {return recItems;}
	public ListView<Object> getRecList() {return recList;}
	public ListView<Object> getSendList(){return sendList;}
	
	
	public void setRecStart(String txt){this.lblRecStart.setText(txt); }
	public void setThread(Thread t){this.t = t;}
	public void setRecItems(ObservableList<Object> recList) {this.recItems = recList;}
	public void setSendItems(ObservableList<Object> sendItems) {this.sendItems = sendItems;}
	
	public static void main(String[] args) {
		Platform.setImplicitExit(false);
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;
		primaryStage.setTitle("Serializer");
		primaryStage.setResizable(false);

		TabPane tabs = new TabPane();
		Tab tabSend = new Tab();
		Tab tabRec = new Tab();
		tabRec.setText("Receive");
		tabRec.setContent(recPane());
		tabRec.setClosable(false);
		tabSend.setText("Send");
		tabSend.setContent(sendPane());
		tabSend.setClosable(false);
		tabs.getTabs().addAll(tabSend, tabRec);

		Scene scene = new Scene(tabs);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent event) {
				if (t != null){
					t.interrupt();
				}
				System.exit(1);
			}
		});
	}

	private Pane sendPane(){
		BorderPane rootLayout = new BorderPane();
		rootLayout.setPadding(new Insets(10));

		lblServer.setMinWidth(80);
		lblPort.setMinWidth(80);

		lblTitle.setPadding(new Insets(15,30,15,30));
		lblTitle.setMinWidth(300);

		sendItems.add(new ReferenceOnly());
		//sendItems.add(new ArrayOfPrimitives());

		sendList.setItems(sendItems);
		sendList.setMaxHeight(256);
		sendList.setPrefWidth(192);

		TilePane objBtns = new TilePane(Orientation.VERTICAL);
		objBtns.setVgap(5);
		objBtns.setPadding(new Insets(5,0,5,20));
		objBtns.getChildren().addAll(btnAdd, btnRemove);

		HBox serverGroup = hGroup(lblServer, txtServer);    
		HBox portGroup = hGroup(lblPort, txtPort);
		VBox networkInfo = vGroup(serverGroup, portGroup);
		networkInfo.setMaxSize(192, Double.MAX_VALUE);
		networkInfo.setAlignment(Pos.CENTER_LEFT);
		btnSend.setMinWidth(64);
		HBox networkForm = hGroup(networkInfo, btnSend);

		HBox objectConfigGroup = hGroup(sendList, objBtns);
		objectConfigGroup.setPadding(new Insets(10,0,0,0));

		VBox form = vGroup(objectConfigGroup, new Separator(), networkForm);
		rootLayout.setTop(lblTitle);
		rootLayout.getTop().setStyle("-fx-background-color: #6699CC;");
		rootLayout.setCenter(form);
		rootLayout.setAlignment(form, Pos.CENTER);
		rootLayout.setBottom(new VBox(new Separator(), lblStudentInfo));

		return rootLayout;
	}

	private Pane recPane(){
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(10));

		lblRecTitle.setPadding(new Insets(15,30,15,30));
		lblRecTitle.setMinWidth(300);

		recList.setItems(recItems);
		recList.setMaxHeight(256);
		recList.setPrefWidth(300);

		VBox form = vGroup(new HBox(recList), new Separator(), 
				new HBox(lblRecPortNum, txtRecPortNum, lblRecStart),
				btnListen, btnClear);
		form.setPadding(new Insets(10,0,0,0));
		root.setTop(lblRecTitle);
		root.getTop().setStyle("-fx-background-color: #6699CC;");
		root.setCenter(form);
		root.setBottom(new VBox(new Separator(), lblRecStudent));

		return root;
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