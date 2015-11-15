package controller;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import view.SerializeUI;

public class BtnListenHandler implements EventHandler<ActionEvent>{
	private SerializeUI sui;
	private ServerSocket soc;

	public BtnListenHandler(SerializeUI ui){
		sui = ui;
	}

	@Override
	public void handle(ActionEvent event) {

		try {
			int portNumber = Integer.parseInt(sui.getRecPortTxt().getText());

			soc = new ServerSocket(portNumber);

		} catch (NumberFormatException e){
			Alert a = new Alert(AlertType.ERROR);
			a.setTitle("Bad Port");
			a.setHeaderText("Could not bind to requested port, bad port number");
			a.setContentText(e.getMessage());
			a.showAndWait();
			return;
		} catch (IOException e) {
			Alert a = new Alert(AlertType.ERROR);
			a.setTitle("IOException");
			a.setContentText(e.getMessage());
			a.showAndWait();
			return;
		}
		
		final MyService s = new MyService(sui, soc);
		s.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, new EventHandler<WorkerStateEvent>(){
			public void handle(WorkerStateEvent event) {
				s.restart();
			}
		});
		s.start();
		/*
		final Task<Object> t = s.createTask();
		sui.setThread(new Thread(t));
		sui.getThread().setDaemon(true);
		sui.getThread().start(); */
		sui.getRecPortTxt().setDisable(true);
		sui.setRecStart(" Listening...");
	}

}
