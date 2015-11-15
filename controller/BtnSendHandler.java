package controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.jdom2.Document;
import org.jdom2.output.XMLOutputter;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import view.SerializeUI;
import model.*;

public class BtnSendHandler implements EventHandler<ActionEvent>{
	private SerializeUI sui;

	public BtnSendHandler(SerializeUI ui){
		sui = ui;
	}
	@Override
	public void handle(ActionEvent event) {
		Socket s;
		DataOutputStream out;
		InetAddress serverName;

		int serverPort;

		try {
			serverName = InetAddress.getByName(sui.getSendServerTxt().getText());
			serverPort = Integer.parseInt(sui.getSendPortTxt().getText());

			XMLOutputter docToString = new XMLOutputter();
			ObservableList<Object> classes = sui.getSendItems();
			for (int i = 0; i < classes.size(); i++){
				s = new Socket(serverName, serverPort);
				out = new DataOutputStream(s.getOutputStream());
				Document doc = Serializer.serialize(classes.get(i));
				out.writeUTF(docToString.outputString(doc));
				out.flush();
				s.close();
			}
			Alert a = new Alert(AlertType.CONFIRMATION);
			a.setTitle("Objects sent");
			a.showAndWait();
		} catch (UnknownHostException e) {
			Alert a = new Alert(AlertType.ERROR);
			a.setTitle("Bad Host");
			a.setHeaderText("Could not send data, bad servername");
			a.setContentText(e.getMessage());
			a.showAndWait();
			return;
		} catch (NumberFormatException e){
			Alert a = new Alert(AlertType.ERROR);
			a.setTitle("Bad Port");
			a.setHeaderText("Could not send data, bad port number");
			a.setContentText(e.getMessage());
			a.showAndWait();
			return;
		} catch (IllegalArgumentException e) {
			Alert a = new Alert(AlertType.ERROR);
			a.setTitle("Bad arguments");
			a.setHeaderText("Problems with serialization, bad arguments");
			a.setContentText(e.getMessage());
			a.showAndWait();
			return;
		} catch (IllegalAccessException e) {
			Alert a = new Alert(AlertType.ERROR);
			a.setTitle("Illegal Access");
			a.setHeaderText("Could not access a field reflexivly during serialization");
			a.setContentText(e.getMessage());
			a.showAndWait();
			return;
		} catch (IOException e) {
			Alert a = new Alert(AlertType.ERROR);
			a.setTitle("IOException");
			a.setHeaderText("Could not connect to the socket");
			a.setContentText(e.getMessage());
			a.showAndWait();
			return;
		}
	}
}
