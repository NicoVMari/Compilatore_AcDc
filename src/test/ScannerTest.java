package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import token.Token;
import token.TokenType;
import scanner.*;

class ScannerTest {

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
		Token t2 = scan.nextToken();
		Token t3 = scan.nextToken();
		Token t4 = scan.nextToken(); 
		Token t5 = scan.nextToken();
		Token t6 = scan.nextToken(); 
		Token t7 = scan.nextToken();
		Token t8 = scan.nextToken(); 
		Token t9 = scan.nextToken();
		Token t10 = scan.nextToken();
		Token t11 = scan.nextToken();
		Token t12 = scan.nextToken();
		Token t13 = scan.nextToken();
		Token t14 = scan.nextToken();
		
		assertEquals(t1.getTipo(),TokenType.PLUS);
		assertEquals(t1.getRiga(),1);
		
		assertEquals(t2.getTipo(),TokenType.OP_ASSIGN);
		assertEquals(t2.getRiga(),1);
		assertEquals(t2.getVal(),"/=");
		
		assertEquals(t3.getTipo(),TokenType.MINUS);
		assertEquals(t3.getRiga(),2);
		
		assertEquals(t4.getTipo(),TokenType.TIMES);
		assertEquals(t4.getRiga(),2);
		
		assertEquals(t5.getTipo(),TokenType.DIVIDE);
		assertEquals(t5.getRiga(),3);
		
		assertEquals(t6.getTipo(),TokenType.OP_ASSIGN);
		assertEquals(t6.getRiga(),5);
		assertEquals(t6.getVal(),"+=");
		
		assertEquals(t7.getTipo(),TokenType.OP_ASSIGN);
		assertEquals(t7.getRiga(),6);
		assertEquals(t7.getVal(),"=");
		
		assertEquals(t8.getTipo(),TokenType.OP_ASSIGN);
		assertEquals(t8.getRiga(),6);
		assertEquals(t8.getVal(),"-=");
		
		assertEquals(t9.getTipo(),TokenType.MINUS);
		assertEquals(t9.getRiga(),8);
		
		assertEquals(t10.getTipo(),TokenType.OP_ASSIGN);
		assertEquals(t10.getRiga(),8);
		assertEquals(t10.getVal(),"=");
		
		assertEquals(t11.getTipo(),TokenType.OP_ASSIGN); 
		assertEquals(t11.getRiga(),8);
		assertEquals(t11.getVal(),"*=");
		
		assertEquals(t12.getTipo(),TokenType.SEMI);
		assertEquals(t12.getRiga(),10);
		
		assertEquals(t13.getTipo(),TokenType.PLUS);
		assertEquals(t13.getRiga(),11);
		
		assertEquals(t14.getTipo(),TokenType.PLUS);
		assertEquals(t14.getRiga(),11);
	}
	
	@Test
	void testErroriTwrows() throws FileNotFoundException {
		Scanner scan = new Scanner(PERCORSO+"erroriTwrows.txt");
		
		LexicalException ex1 = assertThrows(LexicalException.class, () -> {
			scan.nextToken();
		}); 
		assertEquals("NON E' UN CARATTERE LEGALE: <^, 2>",ex1.getMessage());
		
		LexicalException ex2 = assertThrows(LexicalException.class, () -> {
			scan.nextToken();
		}); 
		assertEquals("NON E' UN CARATTERE LEGALE: <!, 3>",ex2.getMessage());
		
		LexicalException ex3 = assertThrows(LexicalException.class, () -> {
			scan.nextToken();
		}); 
		assertEquals("NON E' UN CARATTERE LEGALE: <float0, 5>",ex3.getMessage());
	}
	
	@Test
	void testNumbers() throws LexicalException, IOException {
		Scanner scan = new Scanner(PERCORSO+"testINT.txt");
		Token t1 = scan.nextToken();
		Token t2 = scan.nextToken();
		Token t3 = scan.nextToken();
		
		assertEquals(t1.getTipo(),TokenType.INT);
		assertEquals(t1.getRiga(),2);
		assertEquals(t1.getVal(),"698");
		
		assertEquals(t2.getTipo(),TokenType.INT);
		assertEquals(t2.getRiga(),4);
		assertEquals(t2.getVal(),"560099");
		
		assertEquals(t3.getTipo(),TokenType.INT);
		assertEquals(t3.getRiga(),5);
		assertEquals(t3.getVal(),"1234");
		
		scan = new Scanner(PERCORSO+"testFLOAT.txt");
		t1 = scan.nextToken();
		t2 = scan.nextToken();
		t3 = scan.nextToken();
		Token t4 = scan.nextToken();

		
		assertEquals(t1.getTipo(),TokenType.FLOAT);
		assertEquals(t1.getRiga(),1);
		assertEquals(t1.getVal(),"098.8095");
		
		assertEquals(t2.getTipo(),TokenType.FLOAT);
		assertEquals(t2.getRiga(),2);
		assertEquals(t2.getVal(),"0.");
		
		assertEquals(t3.getTipo(),TokenType.FLOAT);
		assertEquals(t3.getRiga(),3);
		assertEquals(t3.getVal(),"98.");
		
		assertEquals(t4.getTipo(),TokenType.FLOAT);
		assertEquals(t4.getRiga(),5);
		assertEquals(t4.getVal(),"89.99999" ); 
	}
	
	@Test
	void testErroriNumbers() throws LexicalException, IOException {
		Scanner scan = new Scanner(PERCORSO+"erroriNumbers.txt");
		
		LexicalException ex1 = assertThrows(LexicalException.class, () -> {
			scan.nextToken();
		}); 
		assertEquals("NON E' UN CARATTERE LEGALE: <00, 1>",ex1.getMessage());
		
		LexicalException ex2 = assertThrows(LexicalException.class, () -> {
			scan.nextToken();
		}); 
		assertEquals("NON E' UN CARATTERE LEGALE: <123a, 2>",ex2.getMessage());
		
		LexicalException ex3 = assertThrows(LexicalException.class, () -> {
			scan.nextToken();
		}); 
		assertEquals("NON E' UN CARATTERE LEGALE: <12.a, 3>",ex3.getMessage());
		
		LexicalException ex4 = assertThrows(LexicalException.class, () -> {
			scan.nextToken();
		}); 
		assertEquals("NON E' UN CARATTERE LEGALE: <123.121212, 4>",ex4.getMessage());
		
		Scanner scanEx = new Scanner(PERCORSO+"testFLOAT.txt");
		scanEx.nextToken(); scanEx.nextToken(); scanEx.nextToken(); scanEx.nextToken();
		LexicalException ex5 = assertThrows(LexicalException.class, () -> {
			scanEx.nextToken();
		}); 
		assertEquals("NON E' UN CARATTERE LEGALE: <3s, 7>",ex5.getMessage());
		
		LexicalException ex6 = assertThrows(LexicalException.class, () -> {
			scanEx.nextToken();
		}); 
		assertEquals("NON E' UN CARATTERE LEGALE: <., 7>",ex6.getMessage());
		
		/*
		LexicalException ex7 = assertThrows(LexicalException.class, () -> {
			scanEx.nextToken();
		}); 
		assertEquals("NON E' UN CARATTERE LEGALE: <0a, 9>",ex7.getMessage());
		*/
			
		scanEx.nextToken(); scanEx.nextToken();
		LexicalException ex8 = assertThrows(LexicalException.class, () -> {
			scanEx.nextToken();
		}); 
		assertEquals("NON E' UN CARATTERE LEGALE: <0.33d, 10>",ex8.getMessage());
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
	
	/*
	@Test
	void testInput() throws LexicalException, IOException {
        Scanner scan = new Scanner(PERCORSO+"testInput.txt");

        Token token = scan.nextToken();
        int rig = token.getRiga();
        while (token.getTipo() != TokenType.EOF) {
        	if(rig != token.getRiga()) { rig = token.getRiga(); System.out.println();}
        	else System.out.print(token.toString());
            token = scan.nextToken();
        }
	}
	*/
}

