package ast;

import visitor.IVisitor;

/**
 * Classe che rappresenta un nodo di conversione in un albero sintattico.
 * Estende la classe NodeExpr e implementa l'interfaccia IVisitable per supportare la visita del nodo.
 */
public class NodeConvert extends NodeExpr{
	private NodeExpr expr;

    /**
     * Costruttore che inizializza un nodo di conversione con un'espressione da convertire.
     *
     * @param expr Il nodo di espressione da convertire.
     */
	public NodeConvert(NodeExpr expr) {
		this.expr = expr;
	}

    /**
     * Restituisce il nodo di espressione associato a questa conversione.
     *
     * @return Il nodo di espressione da convertire.
     */
	public NodeExpr getExpr() {
		return expr;
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
