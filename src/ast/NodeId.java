package ast;

import symbolTable.SymbolTable.Attributes;
import visitor.IVisitor;

/**
 * Classe che rappresenta un nodo dell'albero sintattico astratto (AST) per un identificatore.
 */
public class NodeId extends NodeAST{
	private String name;
	private Attributes definition;
	
    /**
     * Costruttore della classe NodeId.
     * 
     * @param name Nome dell'identificatore.
     */
	public NodeId(String name) {
		this.name = name;
	}
	
    /**
     * Restituisce il nome dell'identificatore.
     * 
     * @return Nome dell'identificatore.
     */
	public String getName() {
		return name;
	}

    /**
     * Restituisce gli attributi associati alla definizione dell'identificatore.
     * 
     * @return Attributi associati alla definizione dell'identificatore.
     */
	public Attributes getDefinition() {
		return definition;
	}

    /**
     * Imposta gli attributi associati alla definizione dell'identificatore.
     * 
     * @param definition Attributi associati alla definizione dell'identificatore.
     */
	public void setDefinition(Attributes definition) {
		this.definition = definition;
	}

	 /**
     * Restituisce una rappresentazione in formato stringa dell'oggetto NodeId.
     * 
     * @return Stringa che rappresenta l'oggetto NodeId.
     */
	@Override
	public String toString() {
		return "NodeId [name=" + name + "]";
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
