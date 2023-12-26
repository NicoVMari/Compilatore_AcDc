package parser;

import java.io.IOException;
import java.util.ArrayList;

import scanner.LexicalException;
import scanner.Scanner;
import token.Token;
import token.TokenType;
import ast.*;

public class Parser {
	private Scanner scanner;	
	
	public Parser(Scanner scanner) {
		this.scanner = scanner;
	}
	
	private Token match(TokenType type) throws LexicalException, IOException, SyntacticException{
		var tk = scanner.peekToken();
		if (type.equals(tk.getTipo())) return scanner.nextToken();
		throw new SyntacticException("ERRORE: Aspettato token type '" + type + "' alla riga  "+ tk.getRiga());  
	}

	
	public NodeProgram parse() throws LexicalException, IOException, SyntacticException{
		return this.parsePrg();
	} 

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
