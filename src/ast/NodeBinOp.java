package ast;

import visitor.IVisitor;

/**
 * Classe che rappresenta un nodo di operazione binaria in un albero sintattico.
 * Estende la classe NodeExpr e implementa l'interfaccia IVisitable per supportare la visita del nodo.
 */
public class NodeBinOp extends NodeExpr{
	private LangOper op;
	private NodeExpr left;
	private NodeExpr right;
	
    /**
     * Costruttore che inizializza un nodo di operazione binaria con un operatore, un nodo sinistro e un nodo destro.
     *
     * @param op    L'operatore binario.
     * @param left  Il nodo sinistro dell'operazione binaria.
     * @param right Il nodo destro dell'operazione binaria.
     */
	public NodeBinOp(LangOper op, NodeExpr left, NodeExpr right) {
		this.op = op;
		this.left = left;
		this.right = right;
	}

    /**
     * Restituisce l'operatore binario associato a questo nodo di operazione binaria.
     *
     * @return L'operatore binario.
     */
	public LangOper getOp() {
		return op;
	}

    /**
     * Restituisce il nodo sinistro associato a questo nodo di operazione binaria.
     *
     * @return Il nodo sinistro dell'operazione binaria.
     */
	public NodeExpr getLeft() {
		return left;
	}

    /**
     * Restituisce il nodo destro associato a questo nodo di operazione binaria.
     *
     * @return Il nodo destro dell'operazione binaria.
     */
	public NodeExpr getRight() {
		return right;
	}

    /**
     * Imposta il nodo sinistro per questo nodo di operazione binaria.
     *
     * @param left Il nodo sinistro da impostare.
     */
	public void setLeft(NodeExpr left) {
		this.left = left;
	}

    /**
     * Imposta il nodo destro per questo nodo di operazione binaria.
     *
     * @param right Il nodo destro da impostare.
     */
	public void setRight(NodeExpr right) {
		this.right = right;
	}

    /**
     * Restituisce una rappresentazione in formato stringa di questo nodo di operazione binaria.
     *
     * @return Una stringa che rappresenta il nodo di operazione binaria.
     */
	@Override
	public String toString() {
		return "NodeBinOp [op=" + op + ", left=" + left + ", right=" + right + "]";
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
