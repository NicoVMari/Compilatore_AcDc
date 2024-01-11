package ast;

import visitor.IVisitor;

public class NodeAssing extends NodeStm{
	private NodeId id; //= null
	private NodeExpr expr; //= null
	
	public NodeAssing(NodeId id, NodeExpr expr) {
		this.id = id;
		this.expr = expr;
	}
	
	public NodeId getId() {
		return id;
	}
	
	public NodeExpr getExpr() {
		return expr;
	}

	public void setExpr(NodeExpr expr) {
		this.expr = expr;
	}
	
	@Override
	public String toString() {
		return "NodeAssing [id=" + id + ", expr=" + expr + "]";
	}
	
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}
