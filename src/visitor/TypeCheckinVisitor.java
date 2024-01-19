package visitor;

import ast.*;
import ast.TypeDescriptor.TypeDescriptorType;
import symbolTable.SymbolTable;
import symbolTable.SymbolTable.Attributes;

/**
 * Questa classe rappresenta un Visitor utilizzato per eseguire il type checking
 * su un albero AST (Abstract Syntax Tree).
 */
public class TypeCheckinVisitor implements IVisitor{
	private TypeDescriptor resType;
	
    /**
     * Restituisce il tipo risultante dopo il type checking.
     *
     * @return Il tipo risultante dopo il type checking.
     */
	public TypeDescriptor getResType() {
		return resType; 
	}

    /**
     * Costruttore della classe TypeCheckinVisitor. Inizializza la Symbol Table.
     */
	public TypeCheckinVisitor() {
		SymbolTable.init();
	} 
	
    /**
     * Visita un nodo di dichiarazione (NodeDecl) e esegue il type checking.
     *
     * @param nodeDecl Il nodo di dichiarazione da visitare.
     */
	@Override
	public void visit(NodeDecl nodeDecl) { 
		NodeId id = nodeDecl.getId();
		LangType type = nodeDecl.getType();
		NodeExpr init = nodeDecl.getInit();
		
		if(SymbolTable.lookup(id.getName()) == null) {
			//Analisi di tipo dell’espressione e inserire l’identificatore nella Symbol Table
			if(init != null) init.accept(this);
			Attributes attributes = new Attributes(type);
			
			SymbolTable.enter(id.getName(), attributes);
			id.setDefinition(attributes);
			resType = new TypeDescriptor(TypeDescriptorType.OK);
		}
		else 
			//L'identificatore è gia definito
			resType = new TypeDescriptor(TypeDescriptorType.ERROR,"ERRORE: la variabile è già stata dichiarata");
	}
	
    /**
     * Visita un nodo costante (NodeCost) e esegue il type checking.
     *
     * @param nodeCost Il nodo costante da visitare.
     */
	@Override
	public void visit(NodeCost nodeCost) {
		LangType type = nodeCost.getType();
	
		if(type.equals(LangType.TYINT)) 
			resType = new TypeDescriptor(TypeDescriptorType.INT);
		else  //if(type.equals(LangType.TYFLOAT)){
			resType = new TypeDescriptor(TypeDescriptorType.FLOAT);
	}

    /**
     * Visita un nodo di conversione (NodeConvert) e esegue il type checking.
     *
     * @param nodeConvert Il nodo di conversione da visitare.
     */
	@Override
	public void visit(NodeConvert nodeConvert) {
		NodeExpr expr = nodeConvert.getExpr();
		
		expr.accept(this);
		
		resType = new TypeDescriptor(TypeDescriptorType.FLOAT);
	}

    /**
     * Visita un nodo di dereferenziazione (NodeDeref) e esegue il type checking.
     *
     * @param nodeDeref Il nodo di dereferenziazione da visitare.
     */
	@Override
	public void visit(NodeDeref nodeDeref) {
		NodeId id = nodeDeref.getId();
		id.accept(this);
		
		if(resType.getMsg() == null) 
			resType = new TypeDescriptor(resType.getTipo());
		else 
			resType = new TypeDescriptor(resType.getTipo(), resType.getMsg());
		
	}

    /**
     * Visita un nodo di operazione binaria (NodeBinOp) e esegue il type checking.
     *
     * @param nodeBinOp Il nodo di operazione binaria da visitare.
     */
	@Override
	public void visit(NodeBinOp nodeBinOp) {
		NodeExpr left = nodeBinOp.getLeft();
		NodeExpr right = nodeBinOp.getRight();
		
		left.accept(this);
		TypeDescriptor typeDescriptorLeft = resType;
		right.accept(this);
		TypeDescriptor typeDescriptorRight = resType;
		
		if(typeDescriptorLeft.compatibile(TypeDescriptorType.ERROR) && typeDescriptorRight.compatibile(TypeDescriptorType.ERROR)) {
			resType = new TypeDescriptor(TypeDescriptorType.ERROR,typeDescriptorLeft.getMsg() + "\n" + typeDescriptorRight.getMsg());
			return;
		}
		else if(typeDescriptorLeft.compatibile(TypeDescriptorType.ERROR)) {
			resType = new TypeDescriptor(TypeDescriptorType.ERROR,typeDescriptorLeft.getMsg());
			return;
		}
		else if(typeDescriptorRight.compatibile(TypeDescriptorType.ERROR)){
			resType = new TypeDescriptor(TypeDescriptorType.ERROR,typeDescriptorRight.getMsg());
			return;
		}
		
		if(typeDescriptorLeft.getTipo().equals(typeDescriptorRight.getTipo()))
			resType = new TypeDescriptor(typeDescriptorLeft.getTipo());
		else if(typeDescriptorLeft.compatibile(TypeDescriptorType.INT) && typeDescriptorRight.compatibile(TypeDescriptorType.FLOAT)) {
			//Necessario introdurre una conversione automatica da int in float
			NodeConvert expr = this.convertNode(left);
			nodeBinOp.setLeft(expr);
			resType = new TypeDescriptor(TypeDescriptorType.FLOAT);
		}
		else if(typeDescriptorLeft.compatibile(TypeDescriptorType.FLOAT) && typeDescriptorRight.compatibile(TypeDescriptorType.INT)) {
			//Necessario introdurre una conversione automatica da int in float
			NodeConvert expr = this.convertNode(right);
			nodeBinOp.setRight(expr);
			resType = new TypeDescriptor(TypeDescriptorType.FLOAT);
		}
	}
	
    /**
     * Visita un nodo di stampa (NodePrint) e esegue il type checking.
     *
     * @param nodePrint Il nodo di stampa da visitare.
     */
	@Override
	public void visit(NodePrint nodePrint) {
		NodeId nodeId = nodePrint.getId();
		nodeId.accept(this); 
		
		if(resType.compatibile(TypeDescriptorType.ERROR))
			//La variabile non è stata dichiarata
			resType = new TypeDescriptor(TypeDescriptorType.ERROR,"ERRORE: Attributo non presente nella SymbolTable");
		else 
			//La variabile è stata dichiarata
			resType = new TypeDescriptor(TypeDescriptorType.OK);
	}
	
    /**
     * Visita un nodo di assegnamento (NodeAssing) e esegue il type checking.
     *
     * @param nodeAssing Il nodo di assegnamento da visitare.
     */
	@Override
	public void visit(NodeAssing nodeAssing) {
		NodeId nodeId = nodeAssing.getId();
		NodeExpr nodeExpr = nodeAssing.getExpr();
		
		nodeId.accept(this);
		TypeDescriptor typeDescriptorId = resType;
		if(typeDescriptorId.compatibile(TypeDescriptorType.ERROR)) {
			resType = new TypeDescriptor(TypeDescriptorType.ERROR,typeDescriptorId.getMsg());
			return;
		}
		
		nodeExpr.accept(this);
		TypeDescriptor typeDescriptorExpr = resType;
		if(typeDescriptorExpr.compatibile(TypeDescriptorType.ERROR)){
			//Tipo non compatibile nell'espressione
			resType = new TypeDescriptor(TypeDescriptorType.ERROR,typeDescriptorExpr.getMsg());
			return;
		} 
		
		if(typeDescriptorId.getTipo().equals(typeDescriptorExpr.getTipo())) 
			//La variabile a sinistra è stata dichiarata e l’espressione a destra ha un tipo che è compatibile con quello della variabile a sinistra
			resType = new TypeDescriptor(TypeDescriptorType.OK);
		else if(typeDescriptorId.compatibile(typeDescriptorExpr.getTipo())) {
			//La variabile a sinistra è stata dichiarata e l’espressione a destra ha un tipo che è compatibile con quello della variabile a sinistra
			NodeConvert expr = this.convertNode(nodeExpr);
			nodeAssing.setExpr(expr);
			resType = new TypeDescriptor(TypeDescriptorType.OK);
		}
		else 
			//Tipo non compatibile
			resType = new TypeDescriptor(TypeDescriptorType.ERROR,"ERRORE: un float non si può usare dove e' richiesto un int");
	}

    /**
     * Visita un nodo di identificatore (NodeId) e esegue il type checking.
     *
     * @param nodeId Il nodo di identificatore da visitare.
     */
	@Override
	public void visit(NodeId nodeId) {
		String name = nodeId.getName();
		Attributes attributes = SymbolTable.lookup(name);
		
		//Controllare che l’identificatore nel NodeId sia definito
		if(attributes != null) {
			nodeId.setDefinition(attributes);
			//Se è definito, controllare il suo tipo
			if(attributes.getTipo().equals(LangType.TYINT)) 
				resType = new TypeDescriptor(TypeDescriptorType.INT);
			else  //if(attributes.getTipo().equals(LangType.TYFLOAT)) {
				resType = new TypeDescriptor(TypeDescriptorType.FLOAT);
			
		}
		else 
			resType = new TypeDescriptor(TypeDescriptorType.ERROR,"ERRORE: Attributo non presente nella SymbolTable");
	}
	
    /**
     * Visita un nodo di programma (NodeProgram) e esegue il type checking.
     *
     * @param nodeProgram Il nodo di programma da visitare.
     */
	@Override
	public void visit(NodeProgram nodeProgram) { 
		for(NodeDecSt nodeDecSt :  nodeProgram.getDecSt()) {
			nodeDecSt.accept(this);
			if(resType.compatibile(TypeDescriptorType.ERROR)) {
				resType = new TypeDescriptor(TypeDescriptorType.ERROR,resType.getMsg());
				 return;
			}
		} 
		
		resType = new TypeDescriptor(TypeDescriptorType.OK);
	}
	
    /**
     * Converte un nodo di tipo NodeExpr in un nodo di tipo NodeConvert
     * e restituisce il nuovo nodo convertito.
     *
     * @param expr Il nodo di tipo NodeExpr da convertire.
     * @return Il nodo convertito di tipo NodeConvert.
     */
	private NodeConvert convertNode(NodeExpr expr) {
		NodeConvert nodeConvert = new NodeConvert(expr);
		nodeConvert.accept(this);
		return nodeConvert;
	}
}
