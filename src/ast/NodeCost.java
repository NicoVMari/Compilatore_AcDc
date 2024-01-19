package ast;

import visitor.IVisitor;

/**
 * Classe che rappresenta un nodo costante in un albero sintattico.
 * Estende la classe NodeExpr e implementa l'interfaccia IVisitable per supportare la visita del nodo.
 */
public class NodeCost extends NodeExpr{
	private String value;
	private LangType type;
	
    /**
     * Costruttore che inizializza un nodo costante con un valore e un tipo.
     *
     * @param value Il valore della costante.
     * @param type  Il tipo della costante.
     */
	public NodeCost(String value, LangType type) {
		this.value = value;
		this.type = type;
	}

    /**
     * Restituisce il valore della costante.
     *
     * @return Il valore della costante.
     */
	public String getValue() {
		return value;
	}

    /**
     * Restituisce il tipo della costante.
     *
     * @return Il tipo della costante.
     */
	public LangType getType() {
		return type;
	}

    /**
     * Restituisce una rappresentazione in formato stringa di questo nodo costante.
     *
     * @return Una stringa che rappresenta il nodo costante.
     */
	@Override
	public String toString() {
		return "NodeCost [value=" + value + ", type=" + type + "]";
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
