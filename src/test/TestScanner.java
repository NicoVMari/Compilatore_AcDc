package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import token.Token;
import token.TokenType;
import scanner.*;

class TestScanner {

	private static final String PERCORSO = "src/test/data/testScanner/";
	
	@Test
	void testEOF() throws LexicalException, IOException {
		Scanner scan = new Scanner(PERCORSO+"testEOF.txt");
		assertEquals(scan.nextToken().getTipo(),TokenType.EOF); 
		assertEquals(scan.nextToken().getRiga(),4);
	}
	
	@Test
	void testid() throws LexicalException, IOException { 
		Scanner scan = new Scanner(PERCORSO+"testId.txt");
		Token t1 = scan.nextToken();
		Token t2 = scan.nextToken();
		Token t3 = scan.nextToken();
		Token t4 = scan.nextToken();
		
			
		assertEquals(t1.getTipo(),TokenType.ID);
		assertEquals(t1.getRiga(),1);
		assertEquals(t1.getVal(),"jskjdsfhkjdshkf"); 
		
		assertEquals(t2.getTipo(),TokenType.ID);
		assertEquals(t2.getRiga(),2);
		assertEquals(t2.getVal(),"printl");
		
		assertEquals(t3.getTipo(),TokenType.ID); 
		assertEquals(t3.getRiga(),4);
		assertEquals(t3.getVal(),"ffloat");
		
		assertEquals(t4.getTipo(),TokenType.ID);
		assertEquals(t4.getRiga(),6);
		assertEquals(t4.getVal(),"hhhjj");
	}
	
	@Test
	void testKeyWords() throws LexicalException, IOException {
		Scanner scan = new Scanner(PERCORSO+"testKeywords.txt");

		assertEquals(scan.nextToken().getTipo(),TokenType.PRINT);
		assertEquals(scan.nextToken().getTipo(),TokenType.TYFLOAT);
		assertEquals(scan.nextToken().getTipo(),TokenType.TYINT);
	}

	@Test
	void testOperators() throws LexicalException, IOException {
		Scanner scan = new Scanner(PERCORSO+"testOperators.txt");
		
		Token t1 = scan.nextToken();
		assertEquals(t1.getTipo(),TokenType.PLUS);
		assertEquals(t1.getRiga(),1);
		
		t1 = scan.nextToken();
		assertEquals(t1.getTipo(),TokenType.OP_ASSIGN);
		assertEquals(t1.getRiga(),1);
		assertEquals(t1.getVal(),"/=");
		
		t1 = scan.nextToken();
		assertEquals(t1.getTipo(),TokenType.MINUS);
		assertEquals(t1.getRiga(),2);
		
		t1 = scan.nextToken();
		assertEquals(t1.getTipo(),TokenType.TIMES);
		assertEquals(t1.getRiga(),2);
		
		t1 = scan.nextToken();
		assertEquals(t1.getTipo(),TokenType.DIVIDE);
		assertEquals(t1.getRiga(),3);
		
		t1 = scan.nextToken();
		assertEquals(t1.getTipo(),TokenType.OP_ASSIGN);
		assertEquals(t1.getRiga(),5);
		assertEquals(t1.getVal(),"+=");
		
		t1 = scan.nextToken();
		assertEquals(t1.getTipo(),TokenType.OP_ASSIGN);
		assertEquals(t1.getRiga(),6);
		assertEquals(t1.getVal(),"=");
		
		t1 = scan.nextToken();
		assertEquals(t1.getTipo(),TokenType.OP_ASSIGN);
		assertEquals(t1.getRiga(),6);
		assertEquals(t1.getVal(),"-=");
		
		t1 = scan.nextToken();
		assertEquals(t1.getTipo(),TokenType.MINUS);
		assertEquals(t1.getRiga(),8);
		
		t1 = scan.nextToken();
		assertEquals(t1.getTipo(),TokenType.OP_ASSIGN);
		assertEquals(t1.getRiga(),8);
		assertEquals(t1.getVal(),"=");
		
		t1 = scan.nextToken();
		assertEquals(t1.getTipo(),TokenType.OP_ASSIGN); 
		assertEquals(t1.getRiga(),8);
		assertEquals(t1.getVal(),"*=");
		
		t1 = scan.nextToken();
		assertEquals(t1.getTipo(),TokenType.SEMI);
		assertEquals(t1.getRiga(),10);
		
		t1 = scan.nextToken();
		assertEquals(t1.getTipo(),TokenType.PLUS);
		assertEquals(t1.getRiga(),11);
		
		t1 = scan.nextToken();
		assertEquals(t1.getTipo(),TokenType.PLUS);
		assertEquals(t1.getRiga(),11);
		
		t1 = scan.nextToken();
		assertEquals(t1.getTipo(),TokenType.SEMI);
		assertEquals(t1.getRiga(),13);
		
		t1 = scan.nextToken();
		assertEquals(t1.getTipo(),TokenType.OP_ASSIGN);
		assertEquals(t1.getRiga(),13);
		assertEquals(t1.getVal(),"=");
	}
	
	@Test
	void testErroriTwrows() throws FileNotFoundException {
		Scanner scan = new Scanner(PERCORSO+"erroriTwrows.txt");
		
		LexicalException ex1 = assertThrows(LexicalException.class, () -> {
			scan.nextToken();
		}); 
		assertEquals("NON E' UN CARATTERE LEGALE: Provocato da '^' alla riga 2",ex1.getMessage());
		
		LexicalException ex2 = assertThrows(LexicalException.class, () -> {
			scan.nextToken();
		}); 
		assertEquals("NON E' UN CARATTERE LEGALE: Provocato da '!' alla riga 3",ex2.getMessage());
		
		LexicalException ex3 = assertThrows(LexicalException.class, () -> {
			scan.nextToken();
		}); 
		assertEquals("NON E' POSSIBILE AVERE UN NUMERO IN UN ID/KEYWORDS: Provocato da 'float0' alla riga 5",ex3.getMessage());
	}
	
	@Test
	void testNumbers() throws LexicalException, IOException {
		Scanner scan = new Scanner(PERCORSO+"testINT.txt");

		Token t1 = scan.nextToken();
		assertEquals(scan.peekToken().getTipo(),TokenType.INT);
		assertEquals(t1.getRiga(),2);
		assertEquals(t1.getVal(),"698");
		
		t1 = scan.nextToken();
		assertEquals(t1.getTipo(),TokenType.INT);
		assertEquals(t1.getRiga(),4);
		assertEquals(t1.getVal(),"560099");
		
		t1 = scan.nextToken();
		assertEquals(t1.getTipo(),TokenType.INT);
		assertEquals(t1.getRiga(),5);
		assertEquals(t1.getVal(),"1234");
		
		scan = new Scanner(PERCORSO+"testFLOAT.txt");
		
		t1 = scan.nextToken();
		assertEquals(t1.getTipo(),TokenType.FLOAT);
		assertEquals(t1.getRiga(),1);
		assertEquals(t1.getVal(),"098.8095");
		
		t1 = scan.nextToken();
		assertEquals(t1.getTipo(),TokenType.FLOAT);
		assertEquals(t1.getRiga(),2);
		assertEquals(t1.getVal(),"0.");
		
		t1 = scan.nextToken();
		assertEquals(t1.getTipo(),TokenType.FLOAT);
		assertEquals(t1.getRiga(),3);
		assertEquals(t1.getVal(),"98.");
		
		t1 = scan.nextToken();
		assertEquals(t1.getTipo(),TokenType.FLOAT);
		assertEquals(t1.getRiga(),5);
		assertEquals(t1.getVal(),"89.99999" ); 
	}
	
	@Test
	void testErroriNumbers() throws LexicalException, IOException {
		Scanner scan = new Scanner(PERCORSO+"erroriNumbers.txt");
		
		LexicalException ex1 = assertThrows(LexicalException.class, () -> {
			scan.nextToken();
		}); 
		assertEquals("NON E' POSSIBILE NON AVERE UN PUNTO DOPO CHE IL NUMERO INIZA PER 0: Provocato da '00' alla riga 1",ex1.getMessage());
		
		LexicalException ex2 = assertThrows(LexicalException.class, () -> {
			scan.nextToken();
		}); 
		assertEquals("NON E' POSSIBILE AVERE UNA LETTERA IN UN INT: Provocato da '123a' alla riga 2",ex2.getMessage());
		
		LexicalException ex3 = assertThrows(LexicalException.class, () -> {
			scan.nextToken();
		}); 
		assertEquals("NON E' POSSIBILE AVERE UNA LETTERA IN UN FLOAT: Provocato da '12.a' alla riga 3",ex3.getMessage());
		
		LexicalException ex4 = assertThrows(LexicalException.class, () -> {
			scan.nextToken();
		}); 
		assertEquals("NON E' POSSIBILE AVERE UN NUMERO CON PIU' DI 5 VALORI NELLA PARTE DECIMALE: Provocato da '123.121212' alla riga 4",ex4.getMessage());
		
		Scanner scanEx = new Scanner(PERCORSO+"testFLOAT.txt");
		scanEx.nextToken();scanEx.nextToken(); scanEx.nextToken(); scanEx.nextToken();
		LexicalException ex5 = assertThrows(LexicalException.class, () -> {
			scanEx.nextToken();
		}); 
		assertEquals("NON E' POSSIBILE AVERE UNA LETTERA IN UN INT: Provocato da '3s' alla riga 7",ex5.getMessage());
		
		LexicalException ex6 = assertThrows(LexicalException.class, () -> {
			scanEx.nextToken();
		}); 
		assertEquals("NON E' UN CARATTERE LEGALE: Provocato da '.' alla riga 7",ex6.getMessage());
			
		LexicalException ex8 = assertThrows(LexicalException.class, () -> {
			scanEx.nextToken();
		}); 
		assertEquals("NON E' POSSIBILE NON AVERE UN PUNTO DOPO CHE IL NUMERO INIZA PER 0: Provocato da '0' alla riga 9",ex8.getMessage());
		
		scanEx.nextToken();
		LexicalException ex9 = assertThrows(LexicalException.class, () -> {
			scanEx.nextToken();
		}); 
		assertEquals("NON E' POSSIBILE AVERE UNA LETTERA IN UN FLOAT: Provocato da '0.33d' alla riga 10",ex9.getMessage());
	}
	
	@Test
	void testIdKeyWords() throws LexicalException, IOException {
		Scanner scan = new Scanner(PERCORSO+"testIdKeywords.txt");
		
		assertEquals(scan.nextToken().getTipo(),TokenType.TYINT);
		
		Token t1 = scan.nextToken();
		assertEquals(t1.getTipo(),TokenType.ID);
		assertEquals(t1.getRiga(),1);
		assertEquals(t1.getVal(),"inta"); 
		
		assertEquals(scan.nextToken().getTipo(),TokenType.TYFLOAT);
		assertEquals(scan.nextToken().getTipo(),TokenType.PRINT);

		Token t2 = scan.nextToken();
		assertEquals(t2.getTipo(),TokenType.ID);
		assertEquals(t2.getRiga(),4);
		assertEquals(t2.getVal(),"nome"); 
		
		Token t3 = scan.nextToken();
		assertEquals(t3.getTipo(),TokenType.ID);
		assertEquals(t3.getRiga(),5);
		assertEquals(t3.getVal(),"intnome"); 
		
		assertEquals(scan.nextToken().getTipo(),TokenType.TYINT);
		
		Token t4 = scan.nextToken();
		assertEquals(t4.getTipo(),TokenType.ID);
		assertEquals(t4.getRiga(),6);
		assertEquals(t4.getVal(),"nome"); 
	}
	
	@Test
	void testGenerale() throws LexicalException, IOException {
		Scanner scan = new Scanner(PERCORSO+"testGenerale.txt");
		
		assertEquals(scan.nextToken().getTipo(),TokenType.TYINT);
		
		Token t1 = scan.nextToken();
		assertEquals(t1.getTipo(),TokenType.ID);
		assertEquals(t1.getRiga(),1);
		assertEquals(t1.getVal(),"temp"); 
		
		t1 = scan.nextToken();
		assertEquals(t1.getTipo(),TokenType.SEMI);
		assertEquals(t1.getRiga(),1);
		
		t1 = scan.nextToken();
		assertEquals(t1.getTipo(),TokenType.ID);
		assertEquals(t1.getRiga(),2);
		assertEquals(t1.getVal(),"temp"); 
		
		t1 = scan.nextToken();
		assertEquals(t1.getTipo(),TokenType.OP_ASSIGN);
		assertEquals(t1.getRiga(),2);
		assertEquals(t1.getVal(),"+=");
		
		t1 = scan.nextToken();
		assertEquals(t1.getTipo(),TokenType.FLOAT);
		assertEquals(t1.getRiga(),2);
		assertEquals(t1.getVal(),"5."); 
		
		t1 = scan.nextToken();
		assertEquals(t1.getTipo(),TokenType.SEMI);
		assertEquals(t1.getRiga(),2);
		
		t1 = scan.nextToken();
		assertEquals(t1.getTipo(),TokenType.TYFLOAT);
		assertEquals(t1.getRiga(),4);

		t1 = scan.nextToken();
		assertEquals(t1.getTipo(),TokenType.ID);
		assertEquals(t1.getRiga(),4);
		assertEquals(t1.getVal(),"b"); 
		
		t1 = scan.nextToken();
		assertEquals(t1.getTipo(),TokenType.SEMI);
		assertEquals(t1.getRiga(),4);
		
		t1 = scan.nextToken();
		assertEquals(t1.getTipo(),TokenType.ID);
		assertEquals(t1.getRiga(),5);
		assertEquals(t1.getVal(),"b"); 
		
		t1 = scan.nextToken();
		assertEquals(t1.getTipo(),TokenType.OP_ASSIGN);
		assertEquals(t1.getRiga(),5);
		assertEquals(t1.getVal(),"=");
		
		t1 = scan.nextToken();
		assertEquals(t1.getTipo(),TokenType.ID);
		assertEquals(t1.getRiga(),5);
		assertEquals(t1.getVal(),"temp"); 
		
		t1 = scan.nextToken();
		assertEquals(t1.getTipo(),TokenType.PLUS);
		assertEquals(t1.getRiga(),5);

		t1 = scan.nextToken();
		assertEquals(t1.getTipo(),TokenType.FLOAT);
		assertEquals(t1.getRiga(),5);
		assertEquals(t1.getVal(),"3.2"); 
		
		t1 = scan.nextToken();
		assertEquals(t1.getTipo(),TokenType.SEMI);
		assertEquals(t1.getRiga(),5);
		
		t1 = scan.nextToken();
		assertEquals(t1.getTipo(),TokenType.PRINT);
		assertEquals(t1.getRiga(),6);
		
		t1 = scan.nextToken();
		assertEquals(t1.getTipo(),TokenType.ID);
		assertEquals(t1.getRiga(),6);
		assertEquals(t1.getVal(),"b"); 
		
		t1 = scan.nextToken();
		assertEquals(t1.getTipo(),TokenType.SEMI);
		assertEquals(t1.getRiga(),6);
	}
	
	@Test
	void testExp() throws LexicalException, IOException {
		Scanner scan = new Scanner(PERCORSO+"testExp.txt");
		
		Token t1 = scan.nextToken();
		assertEquals(t1.getTipo(),TokenType.ID);
		assertEquals(t1.getRiga(),2);
		assertEquals(t1.getVal(),"x");
		
		t1 = scan.nextToken();
		assertEquals(t1.getTipo(),TokenType.PLUS);
		assertEquals(t1.getRiga(),2);
		
		t1 = scan.nextToken();
		assertEquals(t1.getTipo(),TokenType.INT);
		assertEquals(t1.getRiga(),2);
		assertEquals(t1.getVal(),"3"); 
	}
	
	@Test
	void peekToken () throws LexicalException, IOException {
		Scanner scan = new Scanner(PERCORSO+"testGenerale.txt");
		
		assertEquals(scan.peekToken().getTipo(), TokenType.TYINT);
		assertEquals(scan.nextToken().getTipo(), TokenType.TYINT );
		assertEquals(scan.peekToken().getTipo(), TokenType.ID );
		assertEquals(scan.peekToken().getTipo(), TokenType.ID );
		
		Token t = scan.nextToken();
		
		assertEquals(t.getTipo(), TokenType.ID);
		assertEquals(t.getRiga(), 1);
		assertEquals(t.getVal(), "temp");	
	}
}

