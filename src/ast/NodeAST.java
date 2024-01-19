package ast;

import visitor.IVisitor;

/**
 * Classe astratta che rappresenta un nodo nell'albero sintattico astratto (AST).
 * Fornisce un metodo astratto per supportare la visita del nodo da parte di un oggetto IVisitor.
 */
public abstract class NodeAST {
	
    /**
     * Metodo astratto che permette a un oggetto IVisitor di visitare il nodo.
     *
     * @param visitor L'oggetto IVisitor che sta visitando il nodo.
     */
	public abstract void accept(IVisitor visitor);
}