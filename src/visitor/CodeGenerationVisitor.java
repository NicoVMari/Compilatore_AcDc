package visitor;

import ast.NodeAssing;
import ast.NodeBinOp;
import ast.NodeConvert;
import ast.NodeCost;
import ast.NodeDecl;
import ast.NodeDeref;
import ast.NodeId;
import ast.NodePrint;
import ast.NodeProgram;

public class CodeGenerationVisitor implements IVisitor{

	@Override
	public void visit(NodeProgram nodeProgram) {

	}

	@Override
	public void visit(NodeId nodeId) {

	}

	@Override
	public void visit(NodeCost nodeCost) {

	}

	@Override
	public void visit(NodeConvert nodeConvert) {

	}

	@Override
	public void visit(NodeDeref nodeDeref) {

	}

	@Override
	public void visit(NodeBinOp nodeBinOp) {
		
	}

	@Override
	public void visit(NodeDecl nodeDecl) {

	}

	@Override
	public void visit(NodeAssing nodeAssing) {

	}

	@Override
	public void visit(NodePrint nodePrint) {

	}

}
