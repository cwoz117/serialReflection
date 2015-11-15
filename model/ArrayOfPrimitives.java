package model;

public class ArrayOfPrimitives {
	private int[] myArray;
	
	public ArrayOfPrimitives(){
		setValue(new int[5]);
	}

	public int[] getValue() {
		return (int[])myArray;
	}
	
	public void setValue(int[] val) {
		val = myArray;
	}
	
	public String toString(){
		String obj = "primArry: ";
		for (int i = 0; i < myArray.length; i++){
			obj += myArray[i] + " ";
		}
		return obj;
	}


}
