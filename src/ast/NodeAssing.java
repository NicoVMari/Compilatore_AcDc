package ast;

import visitor.IVisitor;

/**
 * Classe che rappresenta un nodo di assegnamento in un albero sintattico.
 * Estende la classe NodeStm e implementa l'interfaccia IVisitable per supportare la visita del nodo.
 */
public class NodeAssing extends NodeStm{
	private NodeId id;
	private NodeExpr expr;
	
    /**
     * Costruttore che inizializza un nodo di assegnamento con un identificatore e un'espressione.
     *
     * @param id   Il nodo dell'identificatore da assegnare.
     * @param expr Il nodo dell'espressione da assegnare.
     */
	public NodeAssing(NodeId id, NodeExpr expr) {
		this.id = id;
		this.expr = expr;
	}
	
    /**
     * Restituisce il nodo dell'identificatore associato a questo nodo di assegnamento.
     *
     * @return Il nodo dell'identificatore.
     */
	public NodeId getId() {
		return id;
	}
	
    /**
     * Restituisce il nodo dell'espressione associato a questo nodo di assegnamento.
     *
     * @return Il nodo dell'espressione.
     */
	public NodeExpr getExpr() {
		return expr;
	}

    /**
     * Imposta il nodo dell'espressione per questo nodo di assegnamento.
     *
     * @param expr Il nodo dell'espressione da impostare.
     */
	public void setExpr(NodeExpr expr) {
		this.expr = expr;
	}
	
    /**
     * Restituisce una rappresentazione in formato stringa di questo nodo di assegnamento.
     *
     * @return Una stringa che rappresenta il nodo di assegnamento.
     */
	@Override
	public String toString() {
		return "NodeAssing [id=" + id + ", expr=" + expr + "]";
	}
	
    /**
     * Implementazione del metodo accept per supportare la visita del nodo da parte di un oggetto IVisitor.
     *
     * @param visitor L'oggetto IVisitor che sta visitando il nodo.
     */
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}
