package parser;

import java.io.IOException;

import scanner.LexicalException;
import scanner.Scanner;
import token.Token;
import token.TokenType;

public class Parser {
	private Scanner scanner;	
	
	public Parser(Scanner scanner) {
		this.scanner = scanner;
	}
	
	private Token match(TokenType type) throws LexicalException, IOException, SyntacticException{
		Token tk = scanner.peekToken();
		if (type.equals(tk.getTipo())) return scanner.nextToken();
		throw new SyntacticException("ERRORE: Aspettato token type '" + type + "' alla riga  "+ tk.getRiga()); 
	}

	
	public void parse() throws LexicalException, IOException, SyntacticException{
		this.parsePrg();
	}

	private void parsePrg() throws LexicalException, IOException, SyntacticException {
		Token tk;
		try {
			tk = scanner.peekToken();
		}catch(LexicalException e) {
			throw new SyntacticException("ERRORE: LexicalException", e);
		}
		
		switch(tk.getTipo()){
			case TYFLOAT, TYINT, ID, PRINT, EOF:
				parseDSs();
				match(TokenType.EOF);
				return;
			default:
				throw new SyntacticException("ERRORE: Il token "+ tk.toString() +" alla riga " + tk.getRiga() + " deve essere uno tra questi: TYFLOAT, TYINT, ID, PRINT, EOF");
		}	
	}
	
	private void parseDSs() throws SyntacticException, LexicalException, IOException {
		Token tk;
		try {
			tk = scanner.peekToken();
		}catch(LexicalException e) {
			throw new SyntacticException("ERRORE: LexicalException", e);
		}
		
		switch(tk.getTipo()) {
			case TYFLOAT, TYINT:
				parseDcl();
				parseDSs();
				return;
			case ID, PRINT:
				parseStm();
				parseDSs();
				return;
			case EOF:
				return;
			default:
				throw new SyntacticException("ERRORE: Il token "+ tk.toString() +" alla riga " + tk.getRiga() + " deve essere uno tra questi: TYFLOAT, TYINT, ID, PRINT, EOF");
		}
	}
	
	private void parseDcl() throws LexicalException, IOException, SyntacticException {
		Token tk = scanner.peekToken();
		
		switch(tk.getTipo()) {
			default: //case TYFLOAT, TYINT:
				parseTy(); 
				match(TokenType.ID);
				parseDclP();
				return;
		}
	}
	
	private void parseTy() throws LexicalException, IOException, SyntacticException {
		Token tk  = scanner.peekToken();
		
		switch(tk.getTipo()) {
			case TYFLOAT:
				match(TokenType.TYFLOAT);
				return;
			default: //case TYINT:
				match(TokenType.TYINT);
				return;
		}
	}
	
	private void parseDclP() throws LexicalException, IOException, SyntacticException {
		Token tk;
		try {
			tk = scanner.peekToken();
		}catch(LexicalException e) {
			throw new SyntacticException("ERRORE: LexicalException", e); 
		}
		
		switch(tk.getTipo()) {
			case SEMI:
				match(TokenType.SEMI);
				return;
			case OP_ASSIGN:
				match(TokenType.OP_ASSIGN); 
				parseExp();
				match(TokenType.SEMI);
				return;
			default:
				throw new SyntacticException("ERRORE: Il token "+ tk.toString() +" alla riga " + tk.getRiga() + " deve essere uno tra questi: SEMI, OP_ASSIGN");
		}
	}
	
	private void parseStm() throws LexicalException, IOException, SyntacticException {
		Token tk  = scanner.peekToken();
		
		switch(tk.getTipo()) {
			case ID:
				match(TokenType.ID);
				match(TokenType.OP_ASSIGN);
				parseExp();
				match(TokenType.SEMI);
				return;
			default: //case PRINT
				match(TokenType.PRINT);
				match(TokenType.ID);
				match(TokenType.SEMI);
				return;
		}
	}
	
	private void parseExp() throws LexicalException, IOException, SyntacticException {
		Token tk;
		try {
			tk = scanner.peekToken();
		}catch(LexicalException e) {
			throw new SyntacticException("ERRORE: LexicalException", e);
		}
		
		switch(tk.getTipo()) {
			case ID, FLOAT, INT:
				parseTr();
				parseExpP();
				return;
			default:
				throw new SyntacticException("ERRORE: Il token "+ tk.toString() +" alla riga " + tk.getRiga() + " deve essere uno tra questi: ID, FLOAT, INT");
		}
	}
	
	private void parseExpP() throws LexicalException, IOException, SyntacticException {
		Token tk = scanner.peekToken();
		
		switch(tk.getTipo()) {
			case PLUS:
				match(TokenType.PLUS);
				parseTr();
				parseExpP();
				return;
			case MINUS:
				match(TokenType.MINUS);
				parseTr();
				parseExpP();
				return;
			default: //case SEMI:
				return;		
		}
	}
	
	private void parseTr() throws SyntacticException, LexicalException, IOException {
		Token tk;
		try {
			tk = scanner.peekToken();
		}catch(LexicalException e) {
			throw new SyntacticException("ERRORE: LexicalException", e);
		}
		
		switch(tk.getTipo()) {
			case ID, FLOAT, INT:
				parseVal();
				parseTrP();
				return;
			default:
				throw new SyntacticException("ERRORE: Il token "+ tk.toString() +" alla riga " + tk.getRiga() + " deve essere uno tra questi: ID, FLOAT, INT");
		}
	}
	
	private void parseTrP() throws LexicalException, IOException, SyntacticException {
		Token tk;
		try {
			tk = scanner.peekToken();
		}catch(LexicalException e) {
			throw new SyntacticException("ERRORE: LexicalException", e);
		}
		
		switch(tk.getTipo()) {
			case TIMES:
				match(TokenType.TIMES);
				parseVal();
				parseTrP();
				return;
			case DIVIDE:
				match(TokenType.DIVIDE);
				parseVal();
				parseTrP();
				return;
			case MINUS, PLUS, SEMI:
				return;
			default:
				throw new SyntacticException("ERRORE: Il token "+ tk.toString() +" alla riga " + tk.getRiga() + " deve essere uno tra questi: TIMES, DIVIDE, MINUS, PLUS, SEMI");
		}
	}
	
	private void parseVal() throws LexicalException, IOException, SyntacticException {
		Token tk;
		try {
			tk = scanner.peekToken();
		}catch(LexicalException e) {
			throw new SyntacticException("ERRORE: LexicalException", e);
		}
		
		switch(tk.getTipo()) {
			case INT:
				match(TokenType.INT);
				return;
			case FLOAT:
				match(TokenType.FLOAT);
				return;
			case ID:
				match(TokenType.ID);
				return;
			default:
				throw new SyntacticException("ERRORE: Il token "+ tk.toString() +" alla riga " + tk.getRiga() + " deve essere uno tra questi: INT, FLOAT, ID ");

		}
	}
}
