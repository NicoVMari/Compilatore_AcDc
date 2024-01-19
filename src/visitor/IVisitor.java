package visitor;

import ast.*;

/**
 * Interfaccia per un Visitor utilizzato per eseguire operazioni su nodi specifici
 * all'interno di un albero AST (Abstract Syntax Tree).
 */
public interface IVisitor {
	
    /**
     * Metodo per visitare un nodo di tipo NodeProgram.
     *
     * @param nodeProgram Il nodo di tipo NodeProgram da visitare.
     */
    public abstract void visit(NodeProgram nodeProgram);

    /**
     * Metodo per visitare un nodo di tipo NodeId.
     *
     * @param nodeId Il nodo di tipo NodeId da visitare.
     */
    public abstract void visit(NodeId nodeId);

    /**
     * Metodo per visitare un nodo di tipo NodeCost.
     *
     * @param nodeCost Il nodo di tipo NodeCost da visitare.
     */
    public abstract void visit(NodeCost nodeCost);

    /**
     * Metodo per visitare un nodo di tipo NodeConvert.
     *
     * @param nodeConvert Il nodo di tipo NodeConvert da visitare.
     */
    public abstract void visit(NodeConvert nodeConvert);

    /**
     * Metodo per visitare un nodo di tipo NodeDeref.
     *
     * @param nodeDeref Il nodo di tipo NodeDeref da visitare.
     */
    public abstract void visit(NodeDeref nodeDeref);

    /**
     * Metodo per visitare un nodo di tipo NodeBinOp.
     *
     * @param nodeBinOp Il nodo di tipo NodeBinOp da visitare.
     */
    public abstract void visit(NodeBinOp nodeBinOp);

    /**
     * Metodo per visitare un nodo di tipo NodeDecl.
     *
     * @param nodeDecl Il nodo di tipo NodeDecl da visitare.
     */
    public abstract void visit(NodeDecl nodeDecl);

    /**
     * Metodo per visitare un nodo di tipo NodeAssing.
     *
     * @param nodeAssing Il nodo di tipo NodeAssing da visitare.
     */
    public abstract void visit(NodeAssing nodeAssing);

    /**
     * Metodo per visitare un nodo di tipo NodePrint.
     *
     * @param nodePrint Il nodo di tipo NodePrint da visitare.
     */
    public abstract void visit(NodePrint nodePrint);
}
