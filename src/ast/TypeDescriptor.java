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

	public TypeDescriptorType getTipo() {
		 return tipo;
	}

	public String getMsg() {
		return msg;
	}

	public void setTipo(TypeDescriptorType tipo) {
		this.tipo = tipo;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean compatibile(TypeDescriptorType tD) {
		if(tipo == tD) return true;
		else if(tipo == TypeDescriptorType.FLOAT && tD == TypeDescriptorType.INT) return true;
		else return false;
	}
}
