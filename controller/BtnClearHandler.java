package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import view.SerializeUI;

public class BtnClearHandler implements EventHandler<ActionEvent> {

	private SerializeUI sui;
	
	public BtnClearHandler(SerializeUI ui){
		sui = ui;
	}

	@Override
	public void handle(ActionEvent event) {
		for (int i = 0; i <= sui.getRecItems().size(); i++){
			sui.getRecItems().remove(0);
		}
	}
}
