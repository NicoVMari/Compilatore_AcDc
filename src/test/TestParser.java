package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import parser.Parser;
import parser.SyntacticException;
import scanner.LexicalException;
import scanner.Scanner;

class TestParser {

	private static final String PERCORSO = "src/test/data/testParser/";
	
	@Test
	public void testParserCorretto1() throws LexicalException, IOException, SyntacticException {
		Scanner scanner = new Scanner(PERCORSO+"testParserCorretto1.txt");
		Parser parser = new Parser(scanner);
		
		assertDoesNotThrow(() -> parser.parse());
	}
	
	@Test
	public void testParserCorretto2() throws LexicalException, IOException, SyntacticException {
		Scanner scanner = new Scanner(PERCORSO+"testParserCorretto2.txt");
		Parser parser = new Parser(scanner);
		
		assertDoesNotThrow(() -> parser.parse()); 
	}
	
	@Test
	public void testParserEcc_0() throws FileNotFoundException {
		Scanner scanner = new Scanner(PERCORSO+"testParserEcc_0.txt");
		Parser parser = new Parser(scanner);
		
		SyntacticException ex1= assertThrows(SyntacticException.class, () -> {
			parser.parse();
		}); 
		assertEquals("ERRORE: Aspettato token type 'OP_ASSIGN' alla riga  1",ex1.getMessage());
	}
	
	@Test
	public void testParserEcc_1() throws FileNotFoundException {
		Scanner scanner = new Scanner(PERCORSO+"testParserEcc_1.txt");
		Parser parser = new Parser(scanner);
		
		SyntacticException ex1= assertThrows(SyntacticException.class, () -> {
			parser.parse();
		}); 
		assertEquals("ERRORE: Il token <TIMES,r:2> alla riga 2 deve essere uno tra questi: ID, FLOAT, INT",ex1.getMessage());
	}
	
	@Test
	public void testParserEcc_2() throws FileNotFoundException{
		Scanner scanner = new Scanner(PERCORSO+"testParserEcc_2.txt");
		Parser parser = new Parser(scanner);
		
		SyntacticException ex1= assertThrows(SyntacticException.class, () -> {
			parser.parse();
		}); 
		assertEquals("ERRORE: Il token <INT,r:3,1> alla riga 3 deve essere uno tra questi: TYFLOAT, TYINT, ID, PRINT, EOF",ex1.getMessage());
	}
	
	@Test
	public void testParserEcc_3() throws FileNotFoundException{
		Scanner scanner = new Scanner(PERCORSO+"testParserEcc_3.txt");
		Parser parser = new Parser(scanner);
		
		SyntacticException ex1= assertThrows(SyntacticException.class, () -> {
			parser.parse();
		}); 
		assertEquals("ERRORE: Aspettato token type 'OP_ASSIGN' alla riga  2",ex1.getMessage());
	}
	
	@Test
	public void testParserEcc_4() throws FileNotFoundException{
		Scanner scanner = new Scanner(PERCORSO+"testParserEcc_4.txt");
		Parser parser = new Parser(scanner);
		
		SyntacticException ex1= assertThrows(SyntacticException.class, () -> {
			parser.parse();
		}); 
		assertEquals("ERRORE: Aspettato token type 'ID' alla riga  2",ex1.getMessage());
	}
	
	@Test
	public void testParserEcc_5() throws FileNotFoundException{
		Scanner scanner = new Scanner(PERCORSO+"testParserEcc_5.txt");
		Parser parser = new Parser(scanner);
		
		SyntacticException ex1= assertThrows(SyntacticException.class, () -> {
			parser.parse();
		}); 
		assertEquals("ERRORE: Aspettato token type 'ID' alla riga  3",ex1.getMessage());
	}
	
	@Test
	public void testParserEcc_6() throws FileNotFoundException{
		Scanner scanner = new Scanner(PERCORSO+"testParserEcc_6.txt");
		Parser parser = new Parser(scanner);
		
		SyntacticException ex1= assertThrows(SyntacticException.class, () -> {
			parser.parse();
		}); 
		assertEquals("ERRORE: Aspettato token type 'ID' alla riga  4",ex1.getMessage());
	}
	
	@Test
	public void testParserEcc_7() throws FileNotFoundException{
		Scanner scanner = new Scanner(PERCORSO+"testParserEcc_7.txt");
		Parser parser = new Parser(scanner);
		
		SyntacticException ex1= assertThrows(SyntacticException.class, () -> {
			parser.parse();
		}); 
		assertEquals("ERRORE: Aspettato token type 'ID' alla riga  2",ex1.getMessage());
	}
	
	@Test
	public void testParserEcc_8() throws FileNotFoundException{
		Scanner scanner = new Scanner(PERCORSO+"testParserEcc_8.txt");
		Parser parser = new Parser(scanner);
		
		SyntacticException ex1= assertThrows(SyntacticException.class, () -> {
			parser.parse();
		}); 
		assertEquals("ERRORE: Il token <PLUS,r:1> alla riga 1 deve essere uno tra questi: TYFLOAT, TYINT, ID, PRINT, EOF",ex1.getMessage());
	}
	
	@Test
	public void testParserEcc_9() throws FileNotFoundException{
		Scanner scanner = new Scanner(PERCORSO+"testParserEcc_9.txt");
		Parser parser = new Parser(scanner);
		
		SyntacticException ex1= assertThrows(SyntacticException.class, () -> {
			parser.parse();
		}); 
		assertEquals("ERRORE: Il token <INT,r:1,5> alla riga 1 deve essere uno tra questi: SEMI, OP_ASSIGN",ex1.getMessage());
	}
	
	@Test
	public void testParserEcc_10() throws FileNotFoundException{
		Scanner scanner = new Scanner(PERCORSO+"testParserEcc_10.txt");
		Parser parser = new Parser(scanner);
		
		SyntacticException ex1= assertThrows(SyntacticException.class, () -> {
			parser.parse();
		}); 
		assertEquals("ERRORE: Il token <PRINT,r:1> alla riga 1 deve essere uno tra questi: ID, FLOAT, INT",ex1.getMessage());
	}
	
	@Test
	public void testParserEcc_11() throws FileNotFoundException{
		Scanner scanner = new Scanner(PERCORSO+"testParserEcc_11.txt");
		Parser parser = new Parser(scanner);
		
		SyntacticException ex1= assertThrows(SyntacticException.class, () -> {
			parser.parse();
		}); 
		assertEquals("ERRORE: Il token <ID,r:1,y> alla riga 1 deve essere uno tra questi: TIMES, DIVIDE, MINUS, PLUS, SEMI",ex1.getMessage());
	}
	
	@Test
	public void testParserEcc_12() throws FileNotFoundException{
		Scanner scanner = new Scanner(PERCORSO+"testParserEcc_12.txt");
		Parser parser = new Parser(scanner);
		
		SyntacticException ex1= assertThrows(SyntacticException.class, () -> {
			parser.parse();
		}); 
		assertEquals("ERRORE: Il token <PLUS,r:1> alla riga 1 deve essere uno tra questi: INT, FLOAT, ID ",ex1.getMessage());
	}
	
	@Test
	public void testParserEcc_13() throws FileNotFoundException{
		Scanner scanner = new Scanner(PERCORSO+"testParserEcc_13.txt");
		Parser parser = new Parser(scanner);
		
		SyntacticException ex1= assertThrows(SyntacticException.class, () -> {
			parser.parse();
		}); 
		assertEquals("ERRORE: Il token <SEMI,r:1> alla riga 1 deve essere uno tra questi: INT, FLOAT, ID ",ex1.getMessage());
	}
	
	@Test
	public void testSoloDichPrint1() throws FileNotFoundException{
		Scanner scanner = new Scanner(PERCORSO+"testSoloDichPrint1.txt");
		Parser parser = new Parser(scanner);
		
		assertDoesNotThrow(() -> parser.parse());
	}
	
	@Test
	public void testLexicalException0() throws FileNotFoundException {
		Scanner scanner = new Scanner(PERCORSO+"testLex_0.txt");
		Parser parser = new Parser(scanner);
		
		SyntacticException ex1= assertThrows(SyntacticException.class, () -> {
			parser.parse();
		}); 
		assertEquals("ERRORE: LexicalException",ex1.getMessage());
		assertEquals("scanner.LexicalException: NON E' UN CARATTERE LEGALE: Provocato da '^' alla riga 1",ex1.getCause().toString());
	}
	
	@Test
	public void testLexicalException1() throws FileNotFoundException {
		Scanner scanner = new Scanner(PERCORSO+"testLex_1.txt");
		Parser parser = new Parser(scanner);
		
		SyntacticException ex1= assertThrows(SyntacticException.class, () -> {
			parser.parse();
		}); 
		assertEquals("ERRORE: LexicalException",ex1.getMessage());
		assertEquals("scanner.LexicalException: NON E' UN CARATTERE LEGALE: Provocato da '&' alla riga 1",ex1.getCause().toString());
	}
	
	@Test
	public void testLexicalException2() throws FileNotFoundException {
		Scanner scanner = new Scanner(PERCORSO+"testLex_2.txt");
		Parser parser = new Parser(scanner);
		
		SyntacticException ex1= assertThrows(SyntacticException.class, () -> {
			parser.parse();
		}); 
		assertEquals("ERRORE: LexicalException",ex1.getMessage());
		assertEquals("scanner.LexicalException: NON E' UN CARATTERE LEGALE: Provocato da '&' alla riga 1",ex1.getCause().toString());
	}
	@Test
	public void testLexicalException3() throws FileNotFoundException {
		Scanner scanner = new Scanner(PERCORSO+"testLex_3.txt");
		Parser parser = new Parser(scanner);
		
		SyntacticException ex1= assertThrows(SyntacticException.class, () -> {
			parser.parse();
		}); 
		assertEquals("ERRORE: LexicalException",ex1.getMessage());
		assertEquals("scanner.LexicalException: NON E' UN CARATTERE LEGALE: Provocato da '&' alla riga 1",ex1.getCause().toString());
	}
	
	@Test
	public void testLexicalException4() throws FileNotFoundException {
		Scanner scanner = new Scanner(PERCORSO+"testLex_4.txt");
		Parser parser = new Parser(scanner);
		
		SyntacticException ex1= assertThrows(SyntacticException.class, () -> {
			parser.parse();
		}); 
		assertEquals("ERRORE: LexicalException",ex1.getMessage());
		assertEquals("scanner.LexicalException: NON E' UN CARATTERE LEGALE: Provocato da '&' alla riga 1",ex1.getCause().toString());
	}
	
	@Test
	public void testLexicalException5() throws FileNotFoundException {
		Scanner scanner = new Scanner(PERCORSO+"testLex_5.txt");
		Parser parser = new Parser(scanner);
		
		SyntacticException ex1= assertThrows(SyntacticException.class, () -> {
			parser.parse();
		}); 
		assertEquals("ERRORE: LexicalException",ex1.getMessage());
		assertEquals("scanner.LexicalException: NON E' UN CARATTERE LEGALE: Provocato da '&' alla riga 1",ex1.getCause().toString());
	}
	
	@Test
	public void testLexicalException6() throws FileNotFoundException {
		Scanner scanner = new Scanner(PERCORSO+"testLex_6.txt");
		Parser parser = new Parser(scanner);
		
		SyntacticException ex1= assertThrows(SyntacticException.class, () -> {
			parser.parse();
		}); 
		assertEquals("ERRORE: LexicalException",ex1.getMessage());
		assertEquals("scanner.LexicalException: NON E' UN CARATTERE LEGALE: Provocato da '&' alla riga 1",ex1.getCause().toString());
	}
}
