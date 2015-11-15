package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import view.SerializeUI;

public class BtnRemoveHandler implements EventHandler<ActionEvent> {

	private SerializeUI sui;
	
	public BtnRemoveHandler(SerializeUI ui){
		sui = ui;
	}
	@Override
	public void handle(ActionEvent event) {
		ObservableList<Object> lst = sui.getSendItems();
		int i = sui.getSendList().getSelectionModel().getSelectedIndex();
		if (i != -1){
			lst.remove(i);
		}
	}

}
