package model;


public class PrimitivesOnly {
	
	private int primInt;
	private double primDbl;
	private boolean primBool;
	
	public PrimitivesOnly(){
		primInt = 0;
		primDbl = 0.0;
		primBool = false;
	}

	public Integer getPrimInt() {
		return primInt;
	}

	public void setPrimInt(Integer primInt) {
		this.primInt = primInt;
	}

	public double getPrimDbl() {
		return primDbl;
	}

	public void setPrimDbl(double primDbl) {
		this.primDbl = primDbl;
	}

	public boolean isPrimBool() {
		return primBool;
	}

	public void setPrimBool(boolean primBool) {
		this.primBool = primBool;
	}
	public String toString(){
		String obj = "Primitive: ";
		obj += "\t" + primInt;
		obj += "\t" + primDbl;
		obj += "\t" + primBool;
		return obj;
	}
}
