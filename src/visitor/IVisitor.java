package visitor;

import ast.*;

public interface IVisitor {

	public abstract void visit(NodeProgram nodeProgram);
	public abstract void visit(NodeId nodeId);
	public abstract void visit(NodeCost nodeCost);
	public abstract void visit(NodeConvert nodeConvert);
	public abstract void visit(NodeDeref nodeDeref);
	public abstract void visit(NodeBinOp nodeBinOp);
	public abstract void visit(NodeDecl nodeDecl);
	public abstract void visit(NodeAssing nodeAssing);
	public abstract void visit(NodePrint nodePrint);
}
