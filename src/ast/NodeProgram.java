package ast;

import java.util.ArrayList;

import visitor.IVisitor;

/**
 * Classe che rappresenta un nodo dell'albero sintattico astratto (AST) per il programma.
 * Estende la classe NodeAST.
 */
public class NodeProgram extends NodeAST{
	private ArrayList<NodeDecSt> decSt;

    /**
     * Costruttore della classe NodeProgram.
     * 
     * @param decSt Elenco delle dichiarazioni e statement presenti nel programma.
     */
	public NodeProgram(ArrayList<NodeDecSt> decSt) {
		this.decSt = decSt;
	}

    /**
     * Restituisce l'elenco delle dichiarazioni e statement presenti nel programma.
     * 
     * @return Elenco delle dichiarazioni e statement presenti nel programma.
     */
	public ArrayList<NodeDecSt> getDecSt() {
		return decSt;
	}

    /**
     * Restituisce una rappresentazione in formato stringa dell'oggetto NodeProgram.
     * 
     * @return Stringa che rappresenta l'oggetto NodeProgram.
     */
	@Override
	public String toString() {
		return "NodeProgram [decSt=" + decSt + "]";
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
