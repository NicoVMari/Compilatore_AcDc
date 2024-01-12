package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import ast.NodeProgram;
import ast.TypeDescriptor;
import ast.TypeDescriptor.TypeDescriptorType;
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
			TypeDescriptor tD = new TypeDescriptor(TypeDescriptorType.ERROR, "ERRORE: la variabile è già stata dichiarata");
			
			assertEquals(tD.getTipo(),typeCheckingVisitor.getResType().getTipo());
			assertEquals(tD.getMsg(),typeCheckingVisitor.getResType().getMsg());
		});
	}

	@Test
	void test2_idNonDec() {
		assertDoesNotThrow(() -> {
			NodeProgram nodeProgram =  new Parser(new Scanner(PERCORSO+"2_idNonDec.txt")).parse();
			
			var typeCheckingVisitor = new TypeCheckinVisitor();
			nodeProgram.accept(typeCheckingVisitor);
			TypeDescriptor tD = new TypeDescriptor(TypeDescriptorType.ERROR, "ERRORE: Attributo non presente nella SymbolTable");
			
			assertEquals(tD.getTipo(),typeCheckingVisitor.getResType().getTipo());
			assertEquals(tD.getMsg(),typeCheckingVisitor.getResType().getMsg());
		});
	}
	
	@Test
	void test3_idNonDec() {
		assertDoesNotThrow(() -> {
			NodeProgram nodeProgram =  new Parser(new Scanner(PERCORSO+"3_idNonDec")).parse();
			
			var typeCheckingVisitor = new TypeCheckinVisitor();
			nodeProgram.accept(typeCheckingVisitor);
			TypeDescriptor tD = new TypeDescriptor(TypeDescriptorType.ERROR, "ERRORE: Attributo non presente nella SymbolTable");
			
			assertEquals(tD.getTipo(),typeCheckingVisitor.getResType().getTipo());
			assertEquals(tD.getMsg(),typeCheckingVisitor.getResType().getMsg());
		});
	}
	
	@Test
	void test4_tipoNonCompatibile() {
		assertDoesNotThrow(() -> {
			NodeProgram nodeProgram =  new Parser(new Scanner(PERCORSO+"4_tipoNonCompatibile.txt")).parse();
			
			var typeCheckingVisitor = new TypeCheckinVisitor();
			nodeProgram.accept(typeCheckingVisitor);
			TypeDescriptor tD = new TypeDescriptor(TypeDescriptorType.ERROR, "ERRORE: un float non si può usare dove e' richiesto un int");
			
			assertEquals(tD.getTipo(),typeCheckingVisitor.getResType().getTipo());
			assertEquals(tD.getMsg(),typeCheckingVisitor.getResType().getMsg());
		});
	}
	
	@Test
	void test5_corretto() {
		assertDoesNotThrow(() -> {
			NodeProgram nodeProgram =  new Parser(new Scanner(PERCORSO+"5_corretto.txt")).parse();
			
			var typeCheckingVisitor = new TypeCheckinVisitor();
			nodeProgram.accept(typeCheckingVisitor);
			
			assertEquals(TypeDescriptorType.OK,typeCheckingVisitor.getResType().getTipo());
		});
	}
	
	@Test
	void test6_corretto() {
		assertDoesNotThrow(() -> {
			NodeProgram nodeProgram =  new Parser(new Scanner(PERCORSO+"6_corretto.txt")).parse();
			
			var typeCheckingVisitor = new TypeCheckinVisitor();
			nodeProgram.accept(typeCheckingVisitor);
			
			assertEquals(TypeDescriptorType.OK,typeCheckingVisitor.getResType().getTipo());
		});
	}
	
	@Test
	void test7_corretto() {
		assertDoesNotThrow(() -> {
			NodeProgram nodeProgram =  new Parser(new Scanner(PERCORSO+"7_corretto.txt")).parse();
			
			var typeCheckingVisitor = new TypeCheckinVisitor();
			nodeProgram.accept(typeCheckingVisitor);
			
			assertEquals(TypeDescriptorType.OK,typeCheckingVisitor.getResType().getTipo());
		});
	}
	
	@Test
	void test8_assignNonCompatibile() {
		assertDoesNotThrow(() -> {
			NodeProgram nodeProgram =  new Parser(new Scanner(PERCORSO+"8_assignNonCompatibile")).parse();
			
			var typeCheckingVisitor = new TypeCheckinVisitor();
			nodeProgram.accept(typeCheckingVisitor);
			TypeDescriptor tD = new TypeDescriptor(TypeDescriptorType.ERROR, "ERRORE: Attributo non presente nella SymbolTable");
			
			assertEquals(tD.getTipo(),typeCheckingVisitor.getResType().getTipo());
			assertEquals(tD.getMsg(),typeCheckingVisitor.getResType().getMsg());
		});
	}	
	
	@Test
	void test9_idNonDec() {
		assertDoesNotThrow(() -> {
			NodeProgram nodeProgram =  new Parser(new Scanner(PERCORSO+"9_idNonDec.txt")).parse();
			
			var typeCheckingVisitor = new TypeCheckinVisitor();
			nodeProgram.accept(typeCheckingVisitor);
			TypeDescriptor tD = new TypeDescriptor(TypeDescriptorType.ERROR, "ERRORE: Attributo non presente nella SymbolTable");
			
			assertEquals(tD.getTipo(),typeCheckingVisitor.getResType().getTipo());
			assertEquals(tD.getMsg(),typeCheckingVisitor.getResType().getMsg());
		});
	}
	
	@Test
	void test10_idNonDec() {
		assertDoesNotThrow(() -> {
			NodeProgram nodeProgram =  new Parser(new Scanner(PERCORSO+"10_idNonDec.txt")).parse();
			
			var typeCheckingVisitor = new TypeCheckinVisitor();
			nodeProgram.accept(typeCheckingVisitor);
			TypeDescriptor tD = new TypeDescriptor(TypeDescriptorType.ERROR, "ERRORE: Attributo non presente nella SymbolTable\nERRORE: Attributo non presente nella SymbolTable");
			
			assertEquals(tD.getTipo(),typeCheckingVisitor.getResType().getTipo());
			assertEquals(tD.getMsg(),typeCheckingVisitor.getResType().getMsg());
		});
	}
	
	@Test
	void test11_corretto() {
		assertDoesNotThrow(() -> {
			NodeProgram nodeProgram =  new Parser(new Scanner(PERCORSO+"11_corretto.txt")).parse();
			
			var typeCheckingVisitor = new TypeCheckinVisitor();
			nodeProgram.accept(typeCheckingVisitor);
			
			assertEquals(TypeDescriptorType.OK,typeCheckingVisitor.getResType().getTipo());
		});
	}
}
