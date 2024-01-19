package token;

/**
 * La classe Token rappresenta un elemento di base utilizzato nella scansione di un linguaggio.
 * Ogni token ha un tipo, una riga e un valore associato (opzionale).
 */
public class Token {

	private int riga;
	private TokenType tipo;
	private String val;
	
    /**
     * Costruttore che inizializza un Token con un tipo, una riga e un valore.
     *
     * @param tipo Il tipo del token.
     * @param riga La riga in cui il token è stato trovato.
     * @param val Il valore associato al token.
     */
	public Token(TokenType tipo, int riga, String val) {
		this.riga = riga; 
		this.tipo = tipo;
		this.val = val;
	}
	
    /**
     * Costruttore che inizializza un Token con un tipo e una riga.
     *
     * @param tipo Il tipo del token.
     * @param riga La riga in cui il token è stato trovato.
     */
	public Token(TokenType tipo, int riga) {
		this.riga = riga;
		this.tipo = tipo;
		this.val = null;
	}

    /**
     * Restituisce la riga in cui è stato trovato il token.
     *
     * @return La riga del token.
     */
	public int getRiga() {
		return riga;
	}
	
    /**
     * Restituisce il tipo del token.
     *
     * @return Il tipo del token.
     */
	public TokenType getTipo() {
		return tipo;
	}
	
    /**
     * Restituisce il valore associato al token.
     *
     * @return Il valore del token.
     */
	public String getVal() {
		return val;
	}

    /**
     * Restituisce una rappresentazione testuale del token.
     *
     * @return Una stringa che rappresenta il token.
     */
	@Override
	public String toString() {
	    if (val != null) return String.format("<%s,r:%d,%s>", tipo, riga, val);
	    else return String.format("<%s,r:%d>", tipo, riga);
	} 
}
