package ast;

public class TypeDescriptor {
	public enum TypeDescriptorType {
		INT,
		FLOAT,
		OK,
		ERROR
	}
	
	private TypeDescriptorType tipo;
	private String msg;
	
	public TypeDescriptor(TypeDescriptorType tipo, String msg) {
		this.tipo = tipo; 
		this.msg = msg;
	}
	
	public TypeDescriptor(TypeDescriptorType tipo) {
		this.tipo = tipo;
		this.msg = null;
	}

	public TypeDescriptorType getTipo() {
		 return tipo;
	}

	public String getMsg() {
		return msg;
	}

	public boolean compatibile(TypeDescriptorType tD) {
		if(tipo == tD || tipo == TypeDescriptorType.FLOAT && tD == TypeDescriptorType.INT) return true;
		else return false;
	}
}
