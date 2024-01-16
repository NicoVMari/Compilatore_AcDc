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
	public void visit(NodeDecl nodeDecl) {
		NodeId id = nodeDecl.getId();
		LangType type = nodeDecl.getType();
		NodeExpr init = nodeDecl.getInit();
		
		if(SymbolTable.lookup(id.getName()) == null) {
			//Analisi di tipo dell’espressione e inserire l’identificatore nella Symbol Table
			if(init != null) init.accept(this);
			
			SymbolTable.enter(id.getName(), new Attributes(type));
			nodeDecl.setTypeDescriptor(new TypeDescriptor(TypeDescriptorType.OK));
		}
		else 
			//L'identificatore è gia definito
			nodeDecl.setTypeDescriptor(new TypeDescriptor(TypeDescriptorType.ERROR,"ERRORE: la variabile è già stata dichiarata"));
	}
	
	@Override
	public void visit(NodeCost nodeCost) {
		LangType type = nodeCost.getType();
	
		if(type.equals(LangType.TYINT)) 
			nodeCost.setTypeDescriptor(new TypeDescriptor(TypeDescriptorType.INT));
		else  //if(type.equals(LangType.TYFLOAT)){
			nodeCost.setTypeDescriptor(new TypeDescriptor(TypeDescriptorType.FLOAT));
	}

	@Override
	public void visit(NodeConvert nodeConvert) {
		NodeExpr expr = nodeConvert.getExpr();
		
		expr.accept(this);
		
		nodeConvert.setTypeDescriptor(new TypeDescriptor(TypeDescriptorType.FLOAT));
	}

	@Override
	public void visit(NodeDeref nodeDeref) {
		NodeId id = nodeDeref.getId();
		id.accept(this);
		
		if(id.getTypeDescriptor().getMsg() == null) 
			nodeDeref.setTypeDescriptor(new TypeDescriptor(id.getTypeDescriptor().getTipo()));
		else 
			nodeDeref.setTypeDescriptor(new TypeDescriptor(id.getTypeDescriptor().getTipo(),id.getTypeDescriptor().getMsg()));
		
	}

	@Override
	public void visit(NodeBinOp nodeBinOp) {
		NodeExpr left = nodeBinOp.getLeft();
		NodeExpr right = nodeBinOp.getRight();
		
		left.accept(this);
		right.accept(this);
		
		if(left.getTypeDescriptor().compatibile(TypeDescriptorType.ERROR) && right.getTypeDescriptor().compatibile(TypeDescriptorType.ERROR)) {
			nodeBinOp.setTypeDescriptor(new TypeDescriptor(TypeDescriptorType.ERROR,left.getTypeDescriptor().getMsg() + "\n" + right.getTypeDescriptor().getMsg()));
			return;
		}
		else if(left.getTypeDescriptor().compatibile(TypeDescriptorType.ERROR)) {
			nodeBinOp.setTypeDescriptor(new TypeDescriptor(TypeDescriptorType.ERROR,left.getTypeDescriptor().getMsg()));
			return;
		}
		else if(right.getTypeDescriptor().compatibile(TypeDescriptorType.ERROR)){
			nodeBinOp.setTypeDescriptor(new TypeDescriptor(TypeDescriptorType.ERROR,right.getTypeDescriptor().getMsg()));
			return;
		}
		
		if(left.getTypeDescriptor().getTipo().equals(right.getTypeDescriptor().getTipo()))
			nodeBinOp.setTypeDescriptor(new TypeDescriptor(left.getTypeDescriptor().getTipo()));
		else if(left.getTypeDescriptor().compatibile(TypeDescriptorType.INT) && right.getTypeDescriptor().compatibile(TypeDescriptorType.FLOAT)) {
			//Necessario introdurre una conversione automatica da int in float
			NodeConvert expr = this.convertNode(left);
			nodeBinOp.setLeft(expr);
			nodeBinOp.setTypeDescriptor(new TypeDescriptor(TypeDescriptorType.FLOAT));
		}
		else if(left.getTypeDescriptor().compatibile(TypeDescriptorType.FLOAT) && right.getTypeDescriptor().compatibile(TypeDescriptorType.INT)) {
			//Necessario introdurre una conversione automatica da int in float
			NodeConvert expr = this.convertNode(right);
			nodeBinOp.setRight(expr);
			nodeBinOp.setTypeDescriptor(new TypeDescriptor(TypeDescriptorType.FLOAT));
		}
	}
	
	@Override
	public void visit(NodePrint nodePrint) {
		NodeId nodeId = nodePrint.getId();
		nodeId.accept(this); 
		
		if(nodeId.getTypeDescriptor().compatibile(TypeDescriptorType.ERROR))
			//La variabile non è stata dichiarata
			nodePrint.setTypeDescriptor(new TypeDescriptor(TypeDescriptorType.ERROR,"ERRORE: Attributo non presente nella SymbolTable"));
		else 
			//La variabile è stata dichiarata
			nodePrint.setTypeDescriptor(new TypeDescriptor(TypeDescriptorType.OK));
	}
	
	@Override
	public void visit(NodeAssing nodeAssing) {
		NodeId nodeId = nodeAssing.getId();
		NodeExpr nodeExpr = nodeAssing.getExpr();
		
		nodeId.accept(this);
		if(nodeId.getTypeDescriptor().compatibile(TypeDescriptorType.ERROR)) {
			nodeAssing.setTypeDescriptor(new TypeDescriptor(TypeDescriptorType.ERROR,nodeId.getTypeDescriptor().getMsg()));
			return;
		}
		
		nodeExpr.accept(this);
		if(nodeExpr.getTypeDescriptor().compatibile(TypeDescriptorType.ERROR)){
			//Tipo non compatibile nell'espressione
			nodeAssing.setTypeDescriptor(new TypeDescriptor(TypeDescriptorType.ERROR,nodeExpr.getTypeDescriptor().getMsg()));
			return;
		}
		
		if(nodeId.getTypeDescriptor().getTipo().equals(nodeExpr.getTypeDescriptor().getTipo())) 
			//La variabile a sinistra è stata dichiarata e l’espressione a destra ha un tipo che è compatibile con quello della variabile a sinistra
			nodeAssing.setTypeDescriptor(new TypeDescriptor(TypeDescriptorType.OK));
		else if(nodeId.getTypeDescriptor().compatibile(nodeExpr.getTypeDescriptor().getTipo())) {
			//La variabile a sinistra è stata dichiarata e l’espressione a destra ha un tipo che è compatibile con quello della variabile a sinistra
			NodeConvert expr = this.convertNode(nodeExpr);
			nodeAssing.setExpr(expr);
			nodeAssing.setTypeDescriptor(new TypeDescriptor(TypeDescriptorType.OK));
		}
		else 
			//Tipo non compatibile
			nodeAssing.setTypeDescriptor(new TypeDescriptor(TypeDescriptorType.ERROR,"ERRORE: un float non si può usare dove e' richiesto un int"));
	}

	@Override
	public void visit(NodeId nodeId) {
		String name = nodeId.getName();
		Attributes attributes = SymbolTable.lookup(name);
		
		//Controllare che l’identificatore nel NodeId sia definito
		if(attributes != null) {
			//Se è definito, controllare il suo tipo
			if(attributes.getTipo().equals(LangType.TYINT)) 
				nodeId.setTypeDescriptor(new TypeDescriptor(TypeDescriptorType.INT)); 
			else  //if(attributes.getTipo().equals(LangType.TYFLOAT)) {
				nodeId.setTypeDescriptor(new TypeDescriptor(TypeDescriptorType.FLOAT));
			
		}
		else 
			nodeId.setTypeDescriptor(new TypeDescriptor(TypeDescriptorType.ERROR,"ERRORE: Attributo non presente nella SymbolTable"));	
	}
	
	@Override
	public void visit(NodeProgram nodeProgram) {
		TypeDescriptor tD = new TypeDescriptor(TypeDescriptorType.OK);
		
		for(NodeDecSt nodeDecSt :  nodeProgram.getDecSt()) {
			nodeDecSt.accept(this);
			if(nodeDecSt.getTypeDescriptor().compatibile(TypeDescriptorType.ERROR)) {
				 tD.setTipo(TypeDescriptorType.ERROR);
				 tD.setMsg(nodeDecSt.getTypeDescriptor().getMsg());
			}
		}
		
		nodeProgram.setTypeDescriptor(tD);
	}
	
	private NodeConvert convertNode(NodeExpr expr) {
		NodeConvert nodeConvert = new NodeConvert(expr);
		nodeConvert.accept(this);
		return nodeConvert;
	}
}
