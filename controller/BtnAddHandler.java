package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import view.FormUI;
import view.SerializeUI;

public class BtnAddHandler implements EventHandler<ActionEvent> {

	private SerializeUI sui;

	public BtnAddHandler(SerializeUI ui){
		sui = ui;
	}

	@Override
	public void handle(ActionEvent event) {
		FormUI o = new FormUI(sui);
		sui.getStage().hide();
	}

}
