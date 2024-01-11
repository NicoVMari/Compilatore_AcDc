package ast;

import visitor.IVisitor;

public abstract class NodeAST {
	private TypeDescriptor typeDescriptor;
	
	public abstract void accept(IVisitor visitor);
	
	public TypeDescriptor getTypeDescriptor() {
		return typeDescriptor;
	}

	public void setTypeDescriptor(TypeDescriptor typeDescriptor) {
		this.typeDescriptor = typeDescriptor;
	}	
}