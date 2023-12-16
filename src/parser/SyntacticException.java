package parser;

public class SyntacticException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public SyntacticException(String string) {
		super(string);
	} 

	public SyntacticException(String string,Throwable cause) {
		super(string,cause);
	} 
}
