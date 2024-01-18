package test;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ TestParser.class, TestScanner.class, TestToken.class, TestTypeChecking.class, TestCodeGeneration.class })
public class TestCompilatore {
	
}
