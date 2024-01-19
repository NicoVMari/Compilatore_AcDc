package test;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * Suite di test per il compilatore, include test per il parser, lo scanner,
 * i token, il type checking e la generazione del codice.
 */

@Suite
@SelectClasses({ TestParser.class, TestScanner.class, TestToken.class, TestTypeChecking.class, TestCodeGeneration.class })
public class TestCompilatore {
	
}
