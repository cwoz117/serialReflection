package controller;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;

public class Serializer{


	public static org.jdom2.Document serialize(Object obj) 
			throws IllegalArgumentException, IllegalAccessException {
		return serializeHelper(obj, new Document(new Element("serialized")), new HashMap<Object, Integer>());
	}

	public static Document serializeHelper(Object obj, Document doc, Map<Object, Integer> objMap) 
			throws IllegalArgumentException, IllegalAccessException{
		int id = objMap.size();
		objMap.put(obj, id);
		Class<?> meta = obj.getClass();
		Element serObj = new Element("object");
		serObj.setAttribute("class", meta.getName());
		serObj.setAttribute("id", Integer.toString(id));
		doc.getRootElement().addContent(serObj);

		if (!meta.isArray()){
			Field[] fields = getInstanceVars(meta);
			for (Field f : fields){
				f.setAccessible(true);
				Element serField = new Element("field");
				serField.setAttribute("name", f.getName());
				serField.setAttribute("declaringclass", f.getDeclaringClass().getName());
				Object child = f.get(obj);
				
				if (Modifier.isTransient(f.getModifiers())){
					child = null;
				}
				serField.addContent(serializeVariable(f.getType(), child, doc, objMap));
				serObj.addContent(serField);
			}
		} else {
			Class<?> component = meta.getComponentType();
			int length = Array.getLength(obj);
			serObj.setAttribute("length", Integer.toString(length));
			for (int i = 0; i < length; i++){
				serObj.addContent(serializeVariable(component, Array.get(objMap, i), doc, objMap));
			}
		}
		return doc;
	}

	public static Field[] getInstanceVars(Class<?> meta){
		List<Field> totalFields = new ArrayList<Field>();
		while (meta != null){
			Field[] fields = meta.getDeclaredFields();
			for (Field f : fields){
				if (!Modifier.isStatic(f.getModifiers())){
					totalFields.add(f);
				}
			}
			meta = meta.getSuperclass();
		}
		Field[] returnable = new Field[totalFields.size()];
		return totalFields.toArray(returnable);
	}
	
	public static Element serializeVariable(Class<?> type, Object value, Document doc, Map<Object, Integer> objMap) 
			throws IllegalArgumentException, IllegalAccessException{
		if (value == null){
			return new Element("null");
		} else if (!type.isPrimitive()){
			Element reference = new Element("reference");
			if (objMap.containsKey(value)){
				reference.setText(objMap.get(value).toString());
			} else{
				reference.setText(Integer.toString(objMap.size()));
				serializeHelper(value, doc, objMap);
			}
			return reference;
		} else {
			Element val = new Element("value");
			val.setText(value.toString());
			return val;
		}
	}
}












