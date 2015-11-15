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

import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import view.SerializeUI;

public class MyTask extends Task<Object>{
	private SerializeUI sui;
	private ServerSocket soc;
	
	public MyTask(SerializeUI ui, ServerSocket soc){
		this.sui = ui;
		this.soc = soc;
	}
	@Override
	protected Object call() {
		Document doc;
		try {
			while(!Thread.interrupted()){
				try{
					soc.setSoTimeout(500);
					Socket s = soc.accept();
					DataInputStream in = new DataInputStream(s.getInputStream());
					String text = in.readUTF();
					SAXBuilder sb = new SAXBuilder();
					doc = sb.build(new StringReader(text));
					Object o = Deserializer.deserialize(doc);
					return o;
					
				} catch (SocketTimeoutException e){
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (JDOMException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

}
