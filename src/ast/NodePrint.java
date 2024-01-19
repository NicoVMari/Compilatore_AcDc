package ast;

import visitor.IVisitor;

/**
 * Classe che rappresenta un nodo dell'albero sintattico astratto (AST) per l'istruzione di stampa.
 * Estende la classe NodeStm.
 */
public class NodePrint extends NodeStm{
	private NodeId id;

    /**
     * Costruttore della classe NodePrint.
     * 
     * @param id Identificatore oggetto da stampare.
     */
	public NodePrint(NodeId id) {
		this.id = id;
	}

    /**
     * Restituisce l'identificatore oggetto da stampare.
     * 
     * @return Identificatore oggetto da stampare.
     */
	public NodeId getId() {
		return id;
	}

    /**
     * Restituisce una rappresentazione in formato stringa dell'oggetto NodePrint.
     * 
     * @return Stringa che rappresenta l'oggetto NodePrint.
     */
	@Override
	public String toString() {
		return "NodePrint [id=" + id + "]";
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
