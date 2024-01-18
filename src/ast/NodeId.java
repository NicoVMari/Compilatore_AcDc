package ast;

import symbolTable.SymbolTable.Attributes;
import visitor.IVisitor;

public class NodeId extends NodeAST{
	private String name;
	private Attributes definition;
	
	public NodeId(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Attributes getDefinition() {
		return definition;
	}

	public void setDefinition(Attributes definition) {
		this.definition = definition;
	}

	@Override
	public String toString() {
		return "NodeId [name=" + name + "]";
	}
	
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}
