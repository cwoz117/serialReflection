package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.PrimitivesOnly;
import view.FormUI;
import view.SerializeUI;

public class BtnFormSubmitHandler implements  EventHandler<ActionEvent>{

	private FormUI frm;
	private SerializeUI sui;

	public BtnFormSubmitHandler(FormUI frm, SerializeUI ui){
		this.frm = frm;
		this.sui = ui;
	}

	@Override
	public void handle(ActionEvent event) {
		if (frm.getComboBox().getValue().equals("primitive")){
			PrimitivesOnly o = new PrimitivesOnly();
			try{
				o.setPrimInt(Integer.parseInt(frm.txtInt.getText()));
				o.setPrimDbl(Double.parseDouble(frm.txtDbl.getText()));
				o.setPrimBool(Boolean.parseBoolean(frm.txtBool.getText()));

			} catch(NumberFormatException e){
				
			}
			sui.getSendItems().add(o);
			sui.setSendItems(sui.getSendItems());
			sui.getStage().show();
			frm.getStage().close();
		}
	}
}
