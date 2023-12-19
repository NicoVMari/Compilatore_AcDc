package ast;

import java.util.ArrayList;

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
}
