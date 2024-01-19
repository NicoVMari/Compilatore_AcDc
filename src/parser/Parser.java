package parser;

import java.io.IOException;
import java.util.ArrayList;

import scanner.LexicalException;
import scanner.Scanner;
import token.Token;
import token.TokenType;
import ast.*;

/**
 * Questa classe implementa un parser per il linguaggio di programmazione.
 */
public class Parser {
	private Scanner scanner;	
	
    /**
     * Costruttore della classe Parser.
     *
     * @param scanner Lo scanner da utilizzare per l'analisi lessicale.
     */
	public Parser(Scanner scanner) {
		this.scanner = scanner;
	}
	
    /**
     * Metodo per confrontare un token con un tipo specificato e consumarlo se corrisponde.
     *
     * @param type Il tipo di token atteso.
     * @return Il token corrispondente.
     * @throws LexicalException Se si verifica un errore lessicale durante la scansione.
     * @throws IOException       Se si verifica un errore di input/output durante la scansione.
     * @throws SyntacticException Se si verifica un errore sintattico durante la scansione.
     */
	private Token match(TokenType type) throws LexicalException, IOException, SyntacticException{
		var tk = scanner.peekToken();
		if (type.equals(tk.getTipo())) return scanner.nextToken();
		throw new SyntacticException("ERRORE: Aspettato token type '" + type + "' alla riga  "+ tk.getRiga());  
	}

    /**
     * Esegue il parsing del programma.
     *
     * @return Il nodo radice dell'albero di parsing.
     * @throws LexicalException   Se si verifica un errore lessicale durante la scansione.
     * @throws IOException       Se si verifica un errore di input/output durante la scansione.
     * @throws SyntacticException Se si verifica un errore sintattico durante il parsing.
     */
	public NodeProgram parse() throws LexicalException, IOException, SyntacticException{
		return this.parsePrg();
	} 

    /**
     * Esegue il parsing della produzione Prg.
     *
     * @return Il nodo Program risultante dal parsing.
     * @throws LexicalException   Se si verifica un errore lessicale durante la scansione.
     * @throws IOException       Se si verifica un errore di input/output durante la scansione.
     * @throws SyntacticException Se si verifica un errore sintattico durante il parsing.
     */
	private NodeProgram parsePrg() throws LexicalException, IOException, SyntacticException {
		Token tk;
		try {
			tk = scanner.peekToken(); 
		}catch(LexicalException e) {
			throw new SyntacticException("ERRORE: LexicalException", e);
		}
		
		switch(tk.getTipo()){
			case TYFLOAT, TYINT, ID, PRINT, EOF:
				var decSt= parseDSs();
				match(TokenType.EOF);
				return new NodeProgram(decSt);
			default:
				throw new SyntacticException("ERRORE: Il token "+ tk.toString() +" alla riga " + tk.getRiga() + " deve essere uno tra questi: TYFLOAT, TYINT, ID, PRINT, EOF");
		}	
	}
	
    /**
     * Esegue il parsing della produzione DSs.
     *
     * @return Una lista di nodi di dichiarazioni e istruzioni risultante dal parsing.
     * @throws SyntacticException Se si verifica un errore sintattico durante il parsing.
     * @throws LexicalException   Se si verifica un errore lessicale durante la scansione.
     * @throws IOException       Se si verifica un errore di input/output durante la scansione.
     */
	private ArrayList<NodeDecSt> parseDSs() throws SyntacticException, LexicalException, IOException {
		Token tk;
		try {
			tk = scanner.peekToken();
		}catch(LexicalException e) {
			throw new SyntacticException("ERRORE: LexicalException", e);
		}
		
		switch(tk.getTipo()) {
			case TYFLOAT, TYINT:
				var decl = parseDcl();
				var decSts1= parseDSs();
				decSts1.add(0,decl);
				return decSts1;
			case ID, PRINT:
				var stm = parseStm();
				var decSts2= parseDSs();
				decSts2.add(0,stm);
				return decSts2;
			case EOF:
				return new ArrayList<NodeDecSt>();
			default:
				throw new SyntacticException("ERRORE: Il token "+ tk.toString() +" alla riga " + tk.getRiga() + " deve essere uno tra questi: TYFLOAT, TYINT, ID, PRINT, EOF"); 
		}
	}
	
    /**
     * Esegue il parsing della produzione Dcl.
     *
     * @return Il nodo di dichiarazione risultante dal parsing.
     * @throws LexicalException   Se si verifica un errore lessicale durante la scansione.
     * @throws IOException       Se si verifica un errore di input/output durante la scansione.
     * @throws SyntacticException Se si verifica un errore sintattico durante il parsing.
     */
	private NodeDecl parseDcl() throws LexicalException, IOException, SyntacticException {
		Token tk = scanner.peekToken();
		
		switch(tk.getTipo()) {
			default: //case TYFLOAT, TYINT:
				var type = parseTy(); 
				var token = match(TokenType.ID);
				var init = parseDclP();
				return new NodeDecl(new NodeId(token.getVal()),type,init);
		}
	}
	
    /**
     * Esegue il parsing della produzione Ty.
     *
     * @return Il tipo risultante dal parsing.
     * @throws LexicalException   Se si verifica un errore lessicale durante la scansione.
     * @throws IOException       Se si verifica un errore di input/output durante la scansione.
     * @throws SyntacticException Se si verifica un errore sintattico durante il parsing.
     */
	private LangType parseTy() throws LexicalException, IOException, SyntacticException {
		Token tk  = scanner.peekToken();
		
		switch(tk.getTipo()) {
			case TYFLOAT:
				match(TokenType.TYFLOAT);
				return LangType.TYFLOAT;
			default: //case TYINT:
				match(TokenType.TYINT);
				return LangType.TYINT;
		}
	}
	
    /**
     * Esegue il parsing della produzione DclP.
     *
     * @return Il nodo di inizializzazione risultante dal parsing.
     * @throws LexicalException   Se si verifica un errore lessicale durante la scansione.
     * @throws IOException       Se si verifica un errore di input/output durante la scansione.
     * @throws SyntacticException Se si verifica un errore sintattico durante il parsing.
     */
	private NodeExpr parseDclP() throws LexicalException, IOException, SyntacticException {
		Token tk;
		try {
			tk = scanner.peekToken();
		}catch(LexicalException e) {
			throw new SyntacticException("ERRORE: LexicalException", e); 
		}
		
		switch(tk.getTipo()) {
			case SEMI:
				match(TokenType.SEMI); 
				return null;
			case OP_ASSIGN:
				match(TokenType.OP_ASSIGN); 
				var expr = parseExp();
				match(TokenType.SEMI);
				return expr;
			default:
				throw new SyntacticException("ERRORE: Il token "+ tk.toString() +" alla riga " + tk.getRiga() + " deve essere uno tra questi: SEMI, OP_ASSIGN");
		}
	}
	
    /**
     * Esegue il parsing della produzione Stm.
     *
     * @return Il nodo di istruzione risultante dal parsing.
     * @throws LexicalException   Se si verifica un errore lessicale durante la scansione.
     * @throws IOException       Se si verifica un errore di input/output durante la scansione.
     * @throws SyntacticException Se si verifica un errore sintattico durante il parsing.
     */
	private NodeStm parseStm() throws LexicalException, IOException, SyntacticException {
		Token tk  = scanner.peekToken();
		
		switch(tk.getTipo()) {
			case ID:
				var tokenId = match(TokenType.ID);
				match(TokenType.OP_ASSIGN);
				var expr = parseExp();
				match(TokenType.SEMI);
				return new NodeAssing(new NodeId(tokenId.getVal()), expr);
			default: //case PRINT
				match(TokenType.PRINT);
				var token = match(TokenType.ID);
				match(TokenType.SEMI);
				return new NodePrint(new NodeId(token.getVal()));
		}
	}
	
    /**
     * Esegue il parsing della produzione Exp.
     *
     * @return Il nodo di espressione risultante dal parsing.
     * @throws LexicalException   Se si verifica un errore lessicale durante la scansione.
     * @throws IOException       Se si verifica un errore di input/output durante la scansione.
     * @throws SyntacticException Se si verifica un errore sintattico durante il parsing.
     */
	private NodeExpr parseExp() throws LexicalException, IOException, SyntacticException { 
		Token tk;
		try {
			tk = scanner.peekToken();
		}catch(LexicalException e) {
			throw new SyntacticException("ERRORE: LexicalException", e);
		}
		
		switch(tk.getTipo()) {
			case ID, FLOAT, INT:
				var left = parseTr();
				return parseExpP(left);
			default:
				throw new SyntacticException("ERRORE: Il token "+ tk.toString() +" alla riga " + tk.getRiga() + " deve essere uno tra questi: ID, FLOAT, INT");
		}
	}
	
    /**
     * Esegue il parsing della produzione ExpP.
     *
     * @param left Il nodo di espressione sinistra.
     * @return Il nodo di espressione risultante dal parsing.
     * @throws LexicalException   Se si verifica un errore lessicale durante la scansione.
     * @throws IOException       Se si verifica un errore di input/output durante la scansione.
     * @throws SyntacticException Se si verifica un errore sintattico durante il parsing.
     */
	private NodeExpr parseExpP(NodeExpr left) throws LexicalException, IOException, SyntacticException {
		Token tk = scanner.peekToken();
		
		switch(tk.getTipo()) {
			case PLUS:
				match(TokenType.PLUS);
				var trPlus = parseTr();
				var binPlus = new NodeBinOp(LangOper.PLUS,left,trPlus); 
				return parseExpP(binPlus);
			case MINUS:
				match(TokenType.MINUS);
				var trMinus = parseTr();
				var binMinus = new NodeBinOp(LangOper.MINUS,left,trMinus);
				return parseExpP(binMinus);
			default: //case SEMI:
				return left;		
		}
	}
	
	/**
     * Esegue il parsing della produzione Tr.
     *
     * @return Il nodo di termine risultante dal parsing.
     * @throws SyntacticException Se si verifica un errore sintattico durante il parsing.
     * @throws LexicalException   Se si verifica un errore lessicale durante la scansione.
     * @throws IOException       Se si verifica un errore di input/output durante la scansione.
     */
	private NodeExpr parseTr() throws SyntacticException, LexicalException, IOException {
		Token tk;
		try {
			tk = scanner.peekToken();
		}catch(LexicalException e) {
			throw new SyntacticException("ERRORE: LexicalException", e);
		}
		
		switch(tk.getTipo()) {
			case ID, FLOAT, INT:
				var expr = parseVal();
				return parseTrP(expr);
			default:
				throw new SyntacticException("ERRORE: Il token "+ tk.toString() +" alla riga " + tk.getRiga() + " deve essere uno tra questi: ID, FLOAT, INT");
		}
	}
	
	/**
     * Esegue il parsing della produzione TrP.
     *
     * @param left Il nodo di termine sinistra.
     * @return Il nodo di termine risultante dal parsing.
     * @throws LexicalException   Se si verifica un errore lessicale durante la scansione.
     * @throws IOException       Se si verifica un errore di input/output durante la scansione.
     * @throws SyntacticException Se si verifica un errore sintattico durante il parsing.
     */
	private NodeExpr parseTrP(NodeExpr left) throws LexicalException, IOException, SyntacticException {
		Token tk;
		try {
			tk = scanner.peekToken();
		}catch(LexicalException e) {
			throw new SyntacticException("ERRORE: LexicalException", e);
		}
		
		switch(tk.getTipo()) {
			case TIMES:
				match(TokenType.TIMES);
				var valTimes = parseVal();
				var binTimes = new NodeBinOp(LangOper.TIMES,left,valTimes);
				return parseTrP(binTimes);
			case DIVIDE:
				match(TokenType.DIVIDE);
				var valDivide = parseVal();
				var binDivide = new NodeBinOp(LangOper.DIVIDE,left,valDivide);
				return parseTrP(binDivide);
			case MINUS, PLUS, SEMI:
				return left;
			default: 
				throw new SyntacticException("ERRORE: Il token "+ tk.toString() +" alla riga " + tk.getRiga() + " deve essere uno tra questi: TIMES, DIVIDE, MINUS, PLUS, SEMI");
		}
	}
	/**
     * Esegue il parsing della produzione Val.
     *
	 * @return Un nodo rappresentante l'espressione corrispondente al valore.
	 * @throws LexicalException   Se si verifica un errore lessicale durante la scansione.
	 * @throws IOException       Se si verifica un errore di input/output durante la scansione.
	 * @throws SyntacticException Se si verifica un errore sintattico durante la scansione.
	 */
	private NodeExpr parseVal() throws LexicalException, IOException, SyntacticException {
		Token tk;
		try {
			tk = scanner.peekToken(); 
		}catch(LexicalException e) {
			throw new SyntacticException("ERRORE: LexicalException", e);
		}
		
		switch(tk.getTipo()) {
			case INT:
				var tokenInt = match(TokenType.INT);
				return new NodeCost(tokenInt.getVal(),LangType.TYINT);
			case FLOAT:
				var tokenFloat = match(TokenType.FLOAT);
				return new NodeCost(tokenFloat.getVal(),LangType.TYFLOAT);
			case ID:
				var tokenId = match(TokenType.ID);
				return new NodeDeref(new NodeId(tokenId.getVal()));
			default:
				throw new SyntacticException("ERRORE: Il token "+ tk.toString() +" alla riga " + tk.getRiga() + " deve essere uno tra questi: INT, FLOAT, ID ");

		}
	}
}
