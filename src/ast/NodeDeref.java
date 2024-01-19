package ast;

import visitor.IVisitor;

/**
 * Classe che rappresenta un nodo dell'albero sintattico astratto (AST) per l'operazione di dereferenziazione.
 * Estende la classe NodeExpr.
 */
public class NodeDeref extends NodeExpr{
	private NodeId id;

    /**
     * Costruttore della classe NodeDeref.
     * 
     * @param id Identificatore oggetto della dereferenziazione.
     */
	public NodeDeref(NodeId id) {
		this.id = id;
	}

    /**
     * Restituisce l'identificatore oggetto della dereferenziazione.
     * 
     * @return Identificatore oggetto della dereferenziazione.
     */
	public NodeId getId() {
		return id;
	}

    /**
     * Restituisce una rappresentazione in formato stringa dell'oggetto NodeDeref.
     * 
     * @return Stringa che rappresenta l'oggetto NodeDeref.
     */
	@Override
	public String toString() {
		return "NodeDeref [id=" + id + "]";
	}
	
    /**
     * Metodo di accettazione per il visitatore.
     * 
     * @param visitor Oggetto IVisitor che rappresenta il visitatore.
     */
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}
