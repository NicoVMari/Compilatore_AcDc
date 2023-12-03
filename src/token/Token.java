package token;

public class Token {

	private int riga; //riga del codice in cui si trova il token
	private TokenType tipo; //il tipo di token
	private String val; //per identificare e numeri contenenti nella stringa metchata

	public Token(TokenType tipo, int riga, String val) {
		this.riga = riga; 
		this.tipo = tipo;
		this.val = val;
	}
	
	public Token(TokenType tipo, int riga) {
		this.riga = riga;
		this.tipo = tipo;
		this.val = null;
	}

	// Getters per i campi
	public int getRiga() {
		return riga;
	}
	
	public TokenType getTipo() {
		return tipo;
	}
	
	public String getVal() {
		return val;
	}

	@Override
	public String toString() {
	    if (val != null) return String.format("<%s,r:%d,%s>", tipo, riga, val);
	    else return String.format("<%s,r:%d>", tipo, riga);
	} 
    


     

}
