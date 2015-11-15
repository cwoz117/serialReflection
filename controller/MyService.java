package controller;

import java.net.ServerSocket;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import view.SerializeUI;

public class MyService extends Service<Object>{
	private SerializeUI sui;
	private ServerSocket soc;
	
	public MyService(SerializeUI ui, ServerSocket soc){
		this.sui = ui;
		this.soc = soc;
	}
	
	@Override
	protected Task<Object> createTask() {
		final Task<Object> t = new MyTask(sui, soc);
		t.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, new EventHandler<WorkerStateEvent>(){
			@Override
			public void handle(WorkerStateEvent event) {
				sui.getRecItems().add(t.getValue());
				
			}
		});
		return t;
	}

}
