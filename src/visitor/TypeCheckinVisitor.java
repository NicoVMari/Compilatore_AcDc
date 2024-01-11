package visitor;

import ast.*;
import ast.TypeDescriptor.TypeDescriptorType;
import symbolTable.SymbolTable;
import symbolTable.SymbolTable.Attributes;

public class TypeCheckinVisitor implements IVisitor{
	
	public TypeCheckinVisitor() {
		SymbolTable.init();
	}

	@Override
	public void visit(NodeProgram nodeProgram) {
		TypeDescriptor tD = new TypeDescriptor(TypeDescriptorType.OK,"");
		
		for(NodeDecSt nodeDecSt :  nodeProgram.getDecSt()) {
			nodeDecSt.accept(this);
			if(nodeDecSt.getTypeDescriptor().compatibile(TypeDescriptorType.ERROR)) {
				 tD.setTipo(TypeDescriptorType.ERROR);
				 tD.setMsg(nodeDecSt.getTypeDescriptor().getMsg());
			
			}
		}
		
		nodeProgram.setTypeDescriptor(tD);
	}

	@Override
	public void visit(NodeId nodeId) {
		String name = nodeId.getName();
		Attributes attributes = SymbolTable.lookup(name);
		
		if(attributes != null) {
			if(attributes.getTipo() == LangType.TYINT)
				nodeId.setTypeDescriptor(new TypeDescriptor(TypeDescriptorType.INT,""));
			else if(attributes.getTipo() == LangType.TYFLOAT)
				nodeId.setTypeDescriptor(new TypeDescriptor(TypeDescriptorType.FLOAT,"FLOAT"));
		}
		else {
			nodeId.setTypeDescriptor(new TypeDescriptor(TypeDescriptorType.ERROR,"ERRORE: Attributo non presente nella SymbolTable"));
		}	
	}

	@Override
	public void visit(NodeCost nodeCost) {
		LangType type = nodeCost.getType();
	
		if(type.equals(LangType.TYINT)) {
			nodeCost.setTypeDescriptor(new TypeDescriptor(TypeDescriptorType.INT,""));
		}
		else if(type.equals(LangType.TYFLOAT)){
			nodeCost.setTypeDescriptor(new TypeDescriptor(TypeDescriptorType.FLOAT,""));
		}
		else {
			nodeCost.setTypeDescriptor(new TypeDescriptor(TypeDescriptorType.ERROR,"ERRORE: nodeCost"));
		}
	}

	@Override
	public void visit(NodeConvert nodeConvert) {
		NodeExpr expr = nodeConvert.getExpr();
		
		expr.accept(this);
		
		if(expr.getTypeDescriptor().compatibile(TypeDescriptorType.ERROR)) {
			nodeConvert.setTypeDescriptor(new TypeDescriptor(TypeDescriptorType.ERROR,"ERRORE: NodeConvert"));
		}
		else
			nodeConvert.setTypeDescriptor(new TypeDescriptor(TypeDescriptorType.FLOAT,""));
	}

	@Override
	public void visit(NodeDeref nodeDeref) {
		NodeId id = nodeDeref.getId();
		id.accept(this);
		
		nodeDeref.setTypeDescriptor(new TypeDescriptor(id.getTypeDescriptor().getTipo(),id.getTypeDescriptor().getMsg()));
	}

	@Override
	public void visit(NodeBinOp nodeBinOp) {
		NodeExpr left = nodeBinOp.getLeft();
		NodeExpr right = nodeBinOp.getRight();
		
		left.accept(this);
		right.accept(this);
		
		if(left.getTypeDescriptor().compatibile(TypeDescriptorType.ERROR) && !right.getTypeDescriptor().compatibile(TypeDescriptorType.ERROR)) {
			nodeBinOp.setTypeDescriptor(new TypeDescriptor(TypeDescriptorType.ERROR,left.getTypeDescriptor().getMsg()));
		}
		else if(!left.getTypeDescriptor().compatibile(TypeDescriptorType.ERROR) && right.getTypeDescriptor().compatibile(TypeDescriptorType.ERROR)){
			nodeBinOp.setTypeDescriptor(new TypeDescriptor(TypeDescriptorType.ERROR,right.getTypeDescriptor().getMsg()));
		}
		else if(left.getTypeDescriptor().compatibile(TypeDescriptorType.ERROR) && right.getTypeDescriptor().compatibile(TypeDescriptorType.ERROR)) {
			nodeBinOp.setTypeDescriptor(new TypeDescriptor(TypeDescriptorType.ERROR,left.getTypeDescriptor().getMsg() + "\n" + right.getTypeDescriptor().getMsg()));
		}
		else if(left.getTypeDescriptor().compatibile(right.getTypeDescriptor().getTipo())){
			nodeBinOp.setTypeDescriptor(new TypeDescriptor(left.getTypeDescriptor().getTipo(),""));
		}
		else if(left.getTypeDescriptor().compatibile(TypeDescriptorType.INT) && right.getTypeDescriptor().compatibile(TypeDescriptorType.FLOAT)) {
			NodeExpr expr = this.convertNode(left);
			nodeBinOp.setLeft(expr);
			nodeBinOp.setTypeDescriptor(new TypeDescriptor(TypeDescriptorType.FLOAT,""));
		}
		else if(left.getTypeDescriptor().compatibile(TypeDescriptorType.FLOAT) && right.getTypeDescriptor().compatibile(TypeDescriptorType.INT)) {
			NodeExpr expr = this.convertNode(right);
			nodeBinOp.setRight(expr);
			nodeBinOp.setTypeDescriptor(new TypeDescriptor(TypeDescriptorType.FLOAT,""));
		}
	}

	@Override
	public void visit(NodeDecl nodeDecl) {
		NodeId id = nodeDecl.getId();
		LangType type = nodeDecl.getType();
		NodeExpr init = nodeDecl.getInit();
		
		if(SymbolTable.lookup(id.getName()) != null) {
			nodeDecl.setTypeDescriptor(new TypeDescriptor(TypeDescriptorType.ERROR,"ERRORE: la variabile è già stata dichiarata"));
		}
		else {
			if(init != null) init.accept(this);
			
			if(SymbolTable.enter(id.getName(), new Attributes(type))) {
				nodeDecl.setTypeDescriptor(new TypeDescriptor(TypeDescriptorType.OK,"Decl"));
			}
			
		}
	}

	@Override
	public void visit(NodeAssing nodeAssing) {
		NodeId nodeId = nodeAssing.getId();
		NodeExpr nodeExpr = nodeAssing.getExpr();
		
		nodeId.accept(this);
		nodeExpr.accept(this);
		
		if(nodeId.getTypeDescriptor().compatibile(TypeDescriptorType.ERROR) && !nodeExpr.getTypeDescriptor().compatibile(TypeDescriptorType.ERROR)) {
			nodeAssing.setTypeDescriptor(new TypeDescriptor(TypeDescriptorType.ERROR,nodeId.getTypeDescriptor().getMsg()));
		}
		else if(!nodeId.getTypeDescriptor().compatibile(TypeDescriptorType.ERROR) && nodeExpr.getTypeDescriptor().compatibile(TypeDescriptorType.ERROR)){
			nodeAssing.setTypeDescriptor(new TypeDescriptor(TypeDescriptorType.ERROR,nodeExpr.getTypeDescriptor().getMsg()));
		}
		else if(nodeId.getTypeDescriptor().compatibile(TypeDescriptorType.ERROR) && nodeExpr.getTypeDescriptor().compatibile(TypeDescriptorType.ERROR)) {
			nodeAssing.setTypeDescriptor(new TypeDescriptor(TypeDescriptorType.ERROR,nodeId.getTypeDescriptor().getMsg() + "\n" + nodeExpr.getTypeDescriptor().getMsg()));
		}
		else if(nodeId.getTypeDescriptor().getTipo().equals(nodeExpr.getTypeDescriptor().getTipo())) {
			nodeAssing.setTypeDescriptor(new TypeDescriptor(TypeDescriptorType.OK,""));
		}
		else if(nodeId.getTypeDescriptor().compatibile(TypeDescriptorType.INT) && nodeExpr.getTypeDescriptor().compatibile(TypeDescriptorType.FLOAT)) {
			nodeAssing.setTypeDescriptor(new TypeDescriptor(TypeDescriptorType.ERROR,"ERRORE: un float non si può usare dove r' richiesto un float"));
		}
		else if(nodeId.getTypeDescriptor().compatibile(TypeDescriptorType.FLOAT) && nodeExpr.getTypeDescriptor().compatibile(TypeDescriptorType.INT)) {
			nodeAssing.setExpr(this.convertNode(nodeExpr));
			nodeAssing.setTypeDescriptor(new TypeDescriptor(TypeDescriptorType.OK,""));
		}
	}

	@Override
	public void visit(NodePrint nodePrint) {
		NodeId nodeId = nodePrint.getId();
		nodeId.accept(this); 
		
		if(nodeId.getTypeDescriptor().compatibile(TypeDescriptorType.ERROR)) {
			nodeId.setTypeDescriptor(new TypeDescriptor(TypeDescriptorType.ERROR,"ERRORE: NodePrint"));
		}else 
			nodeId.setTypeDescriptor(new TypeDescriptor(TypeDescriptorType.OK,"OK print"));
	}
	
	private NodeExpr convertNode(NodeExpr expr) {
		NodeConvert nodeConvert = new NodeConvert(expr);
		nodeConvert.setTypeDescriptor(new TypeDescriptor(TypeDescriptorType.FLOAT,""));
		return nodeConvert;
	}
}