package ast;

import java.util.ArrayList;

import visitor.IVisitor;

public class NodeProgram extends NodeAST{
	private ArrayList<NodeDecSt> decSt;

	public NodeProgram(ArrayList<NodeDecSt> decSt) {
		this.decSt = decSt;
	}

	public ArrayList<NodeDecSt> getDecSt() {
		return decSt;
	}

	@Override
	public String toString() {
		return "NodeProgram [decSt=" + decSt + "]";
	}

	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}
