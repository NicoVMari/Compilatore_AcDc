package visitor;

import ast.LangOper;
import ast.NodeAssing;
import ast.NodeBinOp;
import ast.NodeConvert;
import ast.NodeCost;
import ast.NodeDecSt;
import ast.NodeDecl;
import ast.NodeDeref;
import ast.NodeExpr;
import ast.NodeId;
import ast.NodePrint;
import ast.NodeProgram;
import symbolTable.SymbolTable;
import symbolTable.SymbolTable.Attributes;

/**
 * Questa classe rappresenta un Visitor utilizzato per generare il codice
 * dc a partire da un albero AST (Abstract Syntax Tree).
 */
public class CodeGenerationVisitor implements IVisitor{
	String codice = "";
	String log = "";

    /**
     * Costruttore della classe CodeGenerationVisitor. Inizializza i registri.
     */
	public CodeGenerationVisitor() {
		Registri.init();
	}
	
    /**
     * Restituisce il codice assembly generato.
     *
     * @return Il codice dc generato.
     */
	public String getCodice() {
		return codice;
	}

    /**
     * Restituisce il log contenente eventuali messaggi di errore.
     *
     * @return Il log contenente eventuali messaggi di errore.
     */
	public String getLog() {
		return log;
	}
	
    /**
     * Visita un nodo di dichiarazione (NodeDecl) e esegue il code code generation.
     *
     * @param nodeDecl Il nodo di dichiarazione da visitare.
     */
	@Override
	public void visit(NodeDecl nodeDecl) { 
		NodeId id = nodeDecl.getId();
		NodeExpr init = nodeDecl.getInit();
		
		id.getDefinition().setRegistro(Registri.newRegister()); 
	
		if(init != null) {
			id.accept(this);
			String codiceId = codice;
			init.accept(this);
			String codiceInit = codice;
			codice = codiceInit + " s" + codiceId + " 0 k ";
		}
	}
	
    /**
     * Visita un nodo di dichiarazione (NodeCost) e esegue il code code generation.
     *
     * @param nodeCost Il nodo di dichiarazione da visitare.
     */
	@Override
	//Genera il codice per fare il push sullo stack della costante.
	public void visit(NodeCost nodeCost) {
		codice = nodeCost.getValue();
	}

    /**
     * Visita un nodo di dichiarazione (NodeConvert) e esegue il code code generation.
     *
     * @param nodeConvert Il nodo di dichiarazione da visitare.
     */
	@Override
	//Genera il codice per cambiare la precisione emettendo il codice 5 k.
	public void visit(NodeConvert nodeConvert) {
		NodeExpr expr = nodeConvert.getExpr();
		expr.accept(this);
		codice = codice + " 5 k";
	}

    /**
     * Visita un nodo di dichiarazione (NodeDeref) e esegue il code code generation.
     *
     * @param nodeDeref Il nodo di dichiarazione da visitare.
     */
	@Override
	//Genera il codice per caricare sullo stack del registro associato all’identificatore.
	public void visit(NodeDeref nodeDeref) {
		NodeId id = nodeDeref.getId();
		id.accept(this);
		
		codice = "l" + codice;
	}

    /**
     * Visita un nodo di dichiarazione (NodeBinOp) e esegue il code code generation.
     *
     * @param nodeBinOp Il nodo di dichiarazione da visitare.
     */
	@Override
	//Genera codice per l’espressione a sinistra, poi per quella a destra e poi quello dell’operazione.
	public void visit(NodeBinOp nodeBinOp) {
		NodeExpr left = nodeBinOp.getLeft();
		NodeExpr right = nodeBinOp.getRight();
		LangOper op = nodeBinOp.getOp();
		
		String operator = "";
		
		left.accept(this);
		String codiceLeft = codice;
		right.accept(this);
		String codiceRight = codice;
		
		if(op == LangOper.DIVIDE) operator = "/";
		else if(op == LangOper.MINUS) operator = "-";
		else if(op == LangOper.TIMES) operator = "*";
		else  operator = "+";
		
		codice = codiceLeft + " " + codiceRight + " " + operator;
	}
	
    /**
     * Visita un nodo di dichiarazione (NodePrint) e esegue il code code generation.
     *
     * @param nodePrint Il nodo di dichiarazione da visitare.
     */
	@Override
	//Genera il codice per caricare sullo stack del registro associato al identificatore, seguito da codice per stamparlo e poi rimuoverlo dallo stack.
	public void visit(NodePrint nodePrint) {
		NodeId nodeId = nodePrint.getId();
		nodeId.accept(this); 
		
		codice = "l" + codice + " p P";
	}

    /**
     * Visita un nodo di dichiarazione (NodeAssing) e esegue il code code generation.
     *
     * @param nodeAssing Il nodo di dichiarazione da visitare.
     */
	@Override
	//Genera codice per l’espressione a destra dell’assegnamento. Memorizza il top dello stack nel registro associato all’ identificatore a sinistra. Riporta la precisione a 0 .
	public void visit(NodeAssing nodeAssing) {
		NodeId id = nodeAssing.getId();
		NodeExpr expr = nodeAssing.getExpr();
		
		id.accept(this);
		String codiceId = codice;
		expr.accept(this);
		String codiceExpr = codice;
		
		codice = codiceExpr + " s" + codiceId + " 0 k ";
	}
	
    /**
     * Visita un nodo di dichiarazione (NodeId) e esegue il code code generation.
     *
     * @param nodeId Il nodo di dichiarazione da visitare.
     */
	@Override
	public void visit(NodeId nodeId) {
		Attributes definition = SymbolTable.lookup(nodeId.getName());
		char registro = definition.getRegistro();
		codice = "" + registro;
	}
	
    /**
     * Visita un nodo di dichiarazione (NodeProgram) e esegue il code code generation.
     *
     * @param nodeProgram Il nodo di dichiarazione da visitare.
     */
	@Override
	public void visit(NodeProgram nodeProgram) {
		String codiceProgram = "";
		
		if(SymbolTable.size() > Registri.registri.size()) {
			log = "ERRORE: il numero di varibili salvate e' maggiore del numero dei registri disponibili";
			return;
		}
		
		for(NodeDecSt nodeDecSt :  nodeProgram.getDecSt()) {
			nodeDecSt.accept(this);
			if(codice != "") codiceProgram += codice;
		}
		
		codice = codiceProgram;
	}

}
