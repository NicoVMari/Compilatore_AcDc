package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import ast.NodeProgram;
import parser.Parser;
import parser.SyntacticException;
import scanner.LexicalException;
import scanner.Scanner;
import visitor.TypeCheckinVisitor;

class TestTypeChecking {

	private static final String PERCORSO = "src/test/data/testTypeChecking/";
	
	@Test
	void test1_dicRipetute() throws LexicalException, IOException, SyntacticException {
		assertDoesNotThrow(() -> {
			NodeProgram nodeProgram =  new Parser(new Scanner(PERCORSO+"1_dicRipetute.txt")).parse();
			
			var typeCheckingVisitor = new TypeCheckinVisitor();
			nodeProgram.accept(typeCheckingVisitor);
		});
	}
	
	@Test
	void test2_idNonDec() {
		assertDoesNotThrow(() -> {
			NodeProgram nodeProgram =  new Parser(new Scanner(PERCORSO+"2_idNonDec.txt")).parse();
			
			var typeCheckingVisitor = new TypeCheckinVisitor();
			nodeProgram.accept(typeCheckingVisitor);
		});
	}
	
	@Test
	void test3_idNonDec() {
		assertDoesNotThrow(() -> {
			NodeProgram nodeProgram =  new Parser(new Scanner(PERCORSO+"2_idNonDec")).parse();
			
			var typeCheckingVisitor = new TypeCheckinVisitor();
			nodeProgram.accept(typeCheckingVisitor);
		});
	}
	
	@Test
	void test4_tipoNonCompatibile() {
		assertDoesNotThrow(() -> {
			NodeProgram nodeProgram =  new Parser(new Scanner(PERCORSO+"4_tipoNonCompatibile.txt")).parse();
			
			var typeCheckingVisitor = new TypeCheckinVisitor();
			nodeProgram.accept(typeCheckingVisitor);
		});
	}
	
	@Test
	void test5_corretto() {
		assertDoesNotThrow(() -> {
			NodeProgram nodeProgram =  new Parser(new Scanner(PERCORSO+"5_corretto.txt")).parse();
			
			var typeCheckingVisitor = new TypeCheckinVisitor();
			nodeProgram.accept(typeCheckingVisitor);
		});
	}
	
	@Test
	void test6_corretto() {
		assertDoesNotThrow(() -> {
			NodeProgram nodeProgram =  new Parser(new Scanner(PERCORSO+"6_corretto.txt")).parse();
			
			var typeCheckingVisitor = new TypeCheckinVisitor();
			nodeProgram.accept(typeCheckingVisitor);
		});
	}
	
	@Test
	void test7_corretto() {
		assertDoesNotThrow(() -> {
			NodeProgram nodeProgram =  new Parser(new Scanner(PERCORSO+"7_corretto.txt")).parse();
			
			var typeCheckingVisitor = new TypeCheckinVisitor();
			nodeProgram.accept(typeCheckingVisitor);
		});
	}

}
