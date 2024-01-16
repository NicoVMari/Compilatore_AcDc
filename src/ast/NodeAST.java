package ast;

import visitor.IVisitor;

public abstract class NodeAST {
	private TypeDescriptor typeDescriptor;
	private String codice;
	
	public abstract void accept(IVisitor visitor);
	
	public TypeDescriptor getTypeDescriptor() {
		return typeDescriptor;
	}

	public void setTypeDescriptor(TypeDescriptor typeDescriptor) {
		this.typeDescriptor = typeDescriptor;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}	
}