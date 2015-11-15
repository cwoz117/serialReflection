package model;

public class ArrayOfRef {
	private ReferenceOnly[] myArray;
	
	public ArrayOfRef(){
		myArray = new ReferenceOnly[3];
		for (int i = 0; i < myArray.length; i++){
			myArray[i] = new ReferenceOnly();
		}
	}
	
	public ReferenceOnly[] getValue(){
		return myArray;
	}
	public void setValue(ReferenceOnly[] val){
		myArray = val;
	}
	
	public String toString(){
		String obj = "AryOfRef: \n\t";
		for (int i = 0; i < myArray.length; i++){
			obj += i + ": " + myArray[i] + "\n";
		}
		return obj;
	}
}
