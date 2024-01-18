package test;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import ast.NodeProgram;
import ast.TypeDescriptor.TypeDescriptorType;
import parser.Parser;
import scanner.Scanner;
import visitor.CodeGenerationVisitor;
import visitor.TypeCheckinVisitor;

class TestCodeGeneration {
	
	private static final String PERCORSO = "src/test/data/testCodeGeneration/";

	@Test
	void test1_corretto() {
		assertDoesNotThrow(() -> {
			NodeProgram nodeProgram = new Parser(new Scanner(PERCORSO + "1_corretto.txt")).parse();

			var typeCheckingVisitor = new TypeCheckinVisitor();
			nodeProgram.accept(typeCheckingVisitor);
			assertEquals(typeCheckingVisitor.getResType().getTipo(), TypeDescriptorType.OK);
			
			var codeGenerationVisitor = new CodeGenerationVisitor();
			nodeProgram.accept(codeGenerationVisitor);
			assertEquals(codeGenerationVisitor.getCodice(),"1.0 6 5 k / sa 0 k la p P");
		});
	}
	
	@Test
	void test2_corretto() {
		assertDoesNotThrow(() -> {
			NodeProgram nodeProgram = new Parser(new Scanner(PERCORSO + "2_corretto.txt")).parse();

			var typeCheckingVisitor = new TypeCheckinVisitor();
			nodeProgram.accept(typeCheckingVisitor);
			assertEquals(typeCheckingVisitor.getResType().getTipo(), TypeDescriptorType.OK);
			
			var codeGenerationVisitor = new CodeGenerationVisitor();
			nodeProgram.accept(codeGenerationVisitor);
			assertEquals(codeGenerationVisitor.getCodice(),"8 sa 0 k 10 sb 0 k 10 sb 0 k la lb 7 / + 5 k sc 0 k lb sa 0 k lc p P");
		});
	}
	
	@Test
	void test3_corretto() {
		assertDoesNotThrow(() -> {
			NodeProgram nodeProgram = new Parser(new Scanner(PERCORSO + "3_corretto.txt")).parse();

			var typeCheckingVisitor = new TypeCheckinVisitor();
			nodeProgram.accept(typeCheckingVisitor);
			assertEquals(typeCheckingVisitor.getResType().getTipo(), TypeDescriptorType.OK);
			
			var codeGenerationVisitor = new CodeGenerationVisitor();
			nodeProgram.accept(codeGenerationVisitor);
			assertEquals(codeGenerationVisitor.getCodice(),"1.0 6 5 k / sa 0 k 1.5 2 5 k * sa 0 k 5.3 4 5 k + sa 0 k 3 5 k 3.5 - sa 0 k la p P");
		});
	}
	
	@Test
	void test4_corretto() {
		assertDoesNotThrow(() -> {
			NodeProgram nodeProgram = new Parser(new Scanner(PERCORSO + "4_corretto.txt")).parse();

			var typeCheckingVisitor = new TypeCheckinVisitor();
			nodeProgram.accept(typeCheckingVisitor);
			assertEquals(typeCheckingVisitor.getResType().getTipo(), TypeDescriptorType.OK);
			
			var codeGenerationVisitor = new CodeGenerationVisitor();
			nodeProgram.accept(codeGenerationVisitor);
			assertEquals(codeGenerationVisitor.getCodice(),"3 sa 0 k 0 sb 0 k la 1 5 k + sa 0 k 1 7 3 / + sb 0 k lb 5 k la - sa 0 k lb p P");
		});
	}
	
	@Test
	void test5_corretto() {
		assertDoesNotThrow(() -> {
			NodeProgram nodeProgram = new Parser(new Scanner(PERCORSO + "5_corretto.txt")).parse();

			var typeCheckingVisitor = new TypeCheckinVisitor();
			nodeProgram.accept(typeCheckingVisitor);
			assertEquals(typeCheckingVisitor.getResType().getTipo(), TypeDescriptorType.OK);
			
			var codeGenerationVisitor = new CodeGenerationVisitor();
			nodeProgram.accept(codeGenerationVisitor);
			assertEquals(codeGenerationVisitor.getCodice(),"5 sa 0 k 4 sb 0 k la 5 k 3.2 lb / - sb 0 k lb p P");
		});
	}
	
	@Test
	void test6_corretto() {
		assertDoesNotThrow(() -> {
			NodeProgram nodeProgram = new Parser(new Scanner(PERCORSO + "6_corretto.txt")).parse();

			var typeCheckingVisitor = new TypeCheckinVisitor();
			nodeProgram.accept(typeCheckingVisitor);
			assertEquals(typeCheckingVisitor.getResType().getTipo(), TypeDescriptorType.OK);
			
			var codeGenerationVisitor = new CodeGenerationVisitor();
			nodeProgram.accept(codeGenerationVisitor);
			assertEquals(codeGenerationVisitor.getCodice(),"3 sa 0 k 5.132 la 5 k + sb 0 k ");
		});
	}
	
	@Test
	void test7_corretto() {
		assertDoesNotThrow(() -> {
			NodeProgram nodeProgram = new Parser(new Scanner(PERCORSO + "7_corretto.txt")).parse();

			var typeCheckingVisitor = new TypeCheckinVisitor();
			nodeProgram.accept(typeCheckingVisitor);
			assertEquals(typeCheckingVisitor.getResType().getTipo(), TypeDescriptorType.OK);
			
			var codeGenerationVisitor = new CodeGenerationVisitor();
			nodeProgram.accept(codeGenerationVisitor);
			assertEquals(codeGenerationVisitor.getCodice(),"5 sa 0 k 3 sb 0 k 4.5 sc 0 k 3.5 sd 0 k 3.5 sd 0 k lc la 5 k + se 0 k lc ld + se 0 k la lb + 5 k se 0 k la 5 k ld + se 0 k la lb * 5 k lc / ld + se 0 k le p P");
		});
	}
	
	@Test
	void test4_errore() {
		assertDoesNotThrow(() -> {
			NodeProgram nodeProgram = new Parser(new Scanner(PERCORSO + "8_errore.txt")).parse();

			var typeCheckingVisitor = new TypeCheckinVisitor();
			nodeProgram.accept(typeCheckingVisitor);
			assertEquals(typeCheckingVisitor.getResType().getTipo(), TypeDescriptorType.OK);
			
			var codeGenerationVisitor = new CodeGenerationVisitor();
			nodeProgram.accept(codeGenerationVisitor);
			assertEquals(codeGenerationVisitor.getLog(),"ERRORE: il numero di varibili salvate e' maggiore del numero dei registri disponibili");
		});
	}
}