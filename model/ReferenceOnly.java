package model;

public class ReferenceOnly{
	
	private PrimitivesOnly myVar;
	
	public ReferenceOnly(){
		setValue(new PrimitivesOnly());
	}

	public PrimitivesOnly getValue() {
		return myVar;
	}

	public void setValue(PrimitivesOnly myVar) {
		this.myVar = myVar;
	}
	public String toString(){
		String obj = "RefObj\n";
		obj += "\t" + myVar;
		return obj;
	}
}
