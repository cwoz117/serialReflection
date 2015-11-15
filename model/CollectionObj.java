package model;

import java.util.ArrayList;
import java.util.List;

public class CollectionObj {
	private List<ReferenceOnly> lst;
	
	public CollectionObj(){
		setValue(new ArrayList<ReferenceOnly>());
		lst.add(new ReferenceOnly());
	}
	
	public String toString(){
		String obj = "Collection \n\t";
		for (ReferenceOnly e: lst){
			obj += e + "\n";
		}
		return obj;
	}

	public List<ReferenceOnly> getValue() {
		return lst;
	}

	public void setValue(List<ReferenceOnly> val) {
		lst = val;
	}
}
