package scanner;

public class LexicalException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public LexicalException(String string) {
		super(string);
	} 

	public LexicalException(String string,Throwable cause) {
		super(string,cause);
	} 
}
