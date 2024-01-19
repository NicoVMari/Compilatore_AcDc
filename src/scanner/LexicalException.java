package scanner;

/**
 * Eccezione personalizzata per gestire errori legati alla fase di analisi lessicale.
 * Estende la classe Exception e fornisce informazioni aggiuntive sui messaggi di errore.
 */
public class LexicalException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
    /**
     * Costruttore che accetta un messaggio di errore.
     *
     * @param string Il messaggio di errore associato all'eccezione.
     */
	public LexicalException(String string) {
		super(string);
	} 

    /**
     * Costruttore che accetta un messaggio di errore e una causa.
     *
     * @param string Il messaggio di errore associato all'eccezione.
     * @param cause La causa dell'eccezione.
     */
	public LexicalException(String string,Throwable cause) {
		super(string,cause);
	} 
}
