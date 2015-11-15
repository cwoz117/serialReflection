package controller;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;

public class Deserializer {
	private static Map<Integer, Object> objMap = new HashMap<Integer, Object>();
	
	public static Object deserialize(Document doc) 
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, 
			NoSuchFieldException, SecurityException{
		List<Element> objElements = doc.getRootElement().getChildren();
		createInstances( objElements);
		assignValues( objElements);
		return objMap.get(0);
	}
	private static void createInstances(List<Element> objList) 
			throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		for (Element serObj : objList) {
			Class<?> meta = Class.forName(serObj.getAttributeValue("class"));
			Object instance = null;
			if (!meta.isArray()){
				instance = meta.newInstance();
			} else {
				instance = Array.newInstance(meta.getComponentType(), Integer.parseInt(serObj.getAttributeValue("length")));
			}
			int num = Integer.parseInt(serObj.getAttributeValue("id"));
			objMap.put(num, instance);
		}
	}

	private static void assignValues( List<Element> objList) 
			throws ClassNotFoundException, NoSuchFieldException, SecurityException, 
					IllegalArgumentException, IllegalAccessException{
		for (Element serObj : objList){
			
			int num = Integer.parseInt(serObj.getAttributeValue("id"));
			Object instance = objMap.get(num);
			objMap.remove(num);
			List<Element> allSerializedFields = serObj.getChildren();
			if (!instance.getClass().isArray()){
				for (Element serField : allSerializedFields){
					Class<?> meta = Class.forName(serObj.getAttributeValue("class"));
					Field f = meta.getDeclaredField(serField.getAttributeValue("name"));
					f.setAccessible(true);
					Element value = serField.getChildren().get(0);
					f.set(instance, getValue(value, f.getType()));
				}
			} else {
				Class<?> component = instance.getClass().getComponentType();
				for (int i = 0; i < allSerializedFields.size(); i++){
					Element serField = allSerializedFields.get(i);
					Array.set(instance,  i,  getValue(serField, component));
				}
			}
	
			objMap.put(num, instance);
		}
	}
	private static Object getValue(Element value, Class<?> componentType){
		if (value.getName().equals("null")){
			return null;
		} else if (value.getName().equals("reference")){
			return objMap.get(Integer.parseInt(value.getText()));
		} else {
			if (componentType.equals(boolean.class)){
				return Boolean.valueOf(value.getText());
			} else if (componentType.equals(short.class)){
				return Short.valueOf(value.getText());
			} else if (componentType.equals(int.class)){
				return Integer.valueOf(value.getText());
			} else if (componentType.equals(long.class)){
				return Long.valueOf(value.getText());
			} else if (componentType.equals(float.class)){
				return Float.valueOf(value.getText());
			} else if (componentType.equals(double.class)){
				return Double.valueOf(value.getText());
			} else if (componentType.equals(char.class)){
				return new Character(value.getText().charAt(0));
			} else {
				return value.getText();
			}
		}
	}
}