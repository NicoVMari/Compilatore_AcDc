package ast;

import visitor.IVisitor;

public class NodeBinOp extends NodeExpr{
	private LangOper op; //= null
	private NodeExpr left;
	private NodeExpr right;
	
	public NodeBinOp(LangOper op, NodeExpr left, NodeExpr right) {
		this.op = op;
		this.left = left;
		this.right = right;
	}

	public LangOper getOp() {
		return op;
	}

	public NodeExpr getLeft() {
		return left;
	}

	public NodeExpr getRight() {
		return right;
	}

	public void setLeft(NodeExpr left) {
		this.left = left;
	}

	public void setRight(NodeExpr right) {
		this.right = right;
	}

	@Override
	public String toString() {
		return "NodeBinOp [op=" + op + ", left=" + left + ", right=" + right + "]";
	}
	
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}
