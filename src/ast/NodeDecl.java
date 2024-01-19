package ast;

import visitor.IVisitor;

/**
 * Classe che rappresenta un nodo di dichiarazione in un albero sintattico.
 * Estende la classe NodeDecSt e implementa l'interfaccia IVisitable per supportare la visita del nodo.
 */
public class NodeDecl extends NodeDecSt{
	private NodeId id;
	private LangType type;
	private NodeExpr init;
	
    /**
     * Costruttore che inizializza un nodo di dichiarazione con un identificatore, un tipo e un'inizializzazione.
     *
     * @param id   Il nodo dell'identificatore associato alla dichiarazione.
     * @param type Il tipo di dato associato alla dichiarazione.
     * @param init Il nodo di espressione che rappresenta l'inizializzazione della dichiarazione.
     */
	public NodeDecl(NodeId id, LangType type, NodeExpr init) {
		this.id = id;
		this.type = type;
		this.init = init;
	}

    /**
     * Restituisce il nodo dell'identificatore associato a questa dichiarazione.
     *
     * @return Il nodo dell'identificatore.
     */
	public NodeId getId() {
		return id;
	}

    /**
     * Restituisce il tipo di dato associato a questa dichiarazione.
     *
     * @return Il tipo di dato.
     */
	public LangType getType() {
		return type;
	}

    /**
     * Restituisce il nodo di espressione associato all'inizializzazione di questa dichiarazione.
     *
     * @return Il nodo di espressione di inizializzazione.
     */
	public NodeExpr getInit() {
		return init;
	}

    /**
     * Restituisce una rappresentazione in formato stringa di questo nodo di dichiarazione.
     *
     * @return Una stringa che rappresenta il nodo di dichiarazione.
     */
	@Override
	public String toString() {
		return "NodeDecl [id=" + id + ", type=" + type + ", init=" + init + "]";
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
