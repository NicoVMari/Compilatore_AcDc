package ast;

import visitor.IVisitor;

public abstract class NodeAST {
	private String codice;
	
	public abstract void accept(IVisitor visitor);
}