package scanner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import token.*;

public class Scanner {
	final char EOF = (char) -1; 
	private int riga;
	private PushbackReader buffer;
	private Token currentToken;

	// skpChars: insieme caratteri di skip (include EOF) e inizializzazione
	final Set<Character> skpChars = new HashSet<>(Arrays.asList(' ', '\n', '\t', '\r', EOF));

	// letters: insieme lettere e inizializzazione
	final Set<Character> letters = new HashSet<>(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j','k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'));

	// digits: cifre e inizializzazione
	final Set<Character> digits = new HashSet<>(Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'));

	// char_type_Map: mapping fra caratteri '+', '-', '*', '/', ';', '=', ';' e il TokenType corrispondente
	final Map<Character, TokenType> charTypeMap = new HashMap<>(Map.of('+', TokenType.PLUS, '-', TokenType.MINUS, '*', TokenType.TIMES, '/', TokenType.DIVIDE, ';',TokenType.SEMI, '=', TokenType.OP_ASSIGN));

	// keyWordsMap: mapping fra le stringhe "print", "float", "int" e il TokenType corrispondente
	final Map<String, TokenType> keywords = new HashMap<>(Map.of("int", TokenType.TYINT, "float", TokenType.TYFLOAT, "print", TokenType.PRINT));

	public Scanner(String fileName) throws FileNotFoundException {
		// inizializzare campi che non hanno inizializzazione
		this.buffer = new PushbackReader(new FileReader(fileName));
		this.riga = 1;
		this.currentToken = null; 
	}

	public Token nextToken() throws LexicalException, IOException {
		try {
			if (this.currentToken != null) {
				Token token = this.currentToken;
				this.currentToken = null;
				return token;
			}

			// nextChar contiene il prossimo carattere dell'input (non consumato). Catturate
			// l'eccezione IOException e ritornate una LexicalException che la contiene
			char nextChar = this.peekChar();

			// Avanza nel buffer leggendo i carattere in skpChars incrementando riga se
			// leggi '\n'. Se raggiungi la fine del file ritorna il Token EOF
			while (skpChars.contains(nextChar)) {
				// arco ricorsivo 0 -> 0
				this.readChar();
				if (nextChar == '\n')
					this.riga++;
				// arco 0 -> 1
				else if (nextChar == EOF) {
					return new Token(TokenType.EOF, this.riga);
				}
				nextChar = this.peekChar();
			}

			// Se nextChar e' in letters return scanId() che legge tutte le lettere
			// minuscole e ritorna un Token ID o il Token associato Parola Chiave (per
			// generare i Token per le parole chiave usate l'HaskMap di corrispondenza
			// arco 0 -> 3
			if (letters.contains(nextChar))
				return this.scanId();

			// Se nextChar e' o in operators oppure ritorna il Token associato con
			// l'operatore o il delimitatore
			// arco 0 -> 9
			if(charTypeMap.containsKey(nextChar)) {
				this.readChar(); 
				
				if(nextChar == '=') return new Token(TokenType.OP_ASSIGN,this.riga, String.valueOf(nextChar));
				else if(this.peekChar() == '=' && nextChar != ';') return new Token(TokenType.OP_ASSIGN,this.riga, String.valueOf(nextChar)+String.valueOf(this.readChar()));
				else if(nextChar == ';') return new Token(TokenType.SEMI, this.riga);
				else return new Token(charTypeMap.get(nextChar),this.riga);
			}

			// Se nextChar e' in numbers return scanNumber() che legge sia un intero che un
			// float e ritorna il Token INUM o FNUM i caratteri che leggete devono essere
			// accumulati in una stringa che verra' assegnata al campo valore del Token
			if (digits.contains(nextChar))
				return this.scanNumber();

			// Altrimenti il carattere NON E' UN CARATTERE LEGALE sollevate una eccezione
			// lessicale dicendo la riga e il carattere che la hanno provocata.
			this.readChar();
			throw new LexicalException("NON E' UN CARATTERE LEGALE: Provocato da '" + nextChar +"' alla riga "+ this.riga);
		} catch (IOException e) {
			throw new LexicalException("NON E' UN CARATTERE LEGALE: Provocato da '" + this.buffer.read() +"' alla riga "+ this.riga, e);
		}
	}

	private Token scanNumber() throws IOException, LexicalException {
		// arco 0 -> 7
		if (this.peekChar() == '0') {
			String valToken = String.valueOf(this.peekChar());
			// arco 7 -> 11
			this.readChar();
			
			while(digits.contains(this.peekChar())) {
				// arco ricorsivo 11 -> 11
				valToken += String.valueOf(this.readChar());
			}
			
			if (this.peekChar() == '.') {
				valToken = this.strTokenFloat(valToken); 
				
				// uscita arco
				return new Token(TokenType.FLOAT, this.riga, valToken);
			}else throw new LexicalException("NON E' POSSIBILE NON AVERE UN PUNTO DOPO CHE IL NUMERO INIZA PER 0: Provocato da '" + valToken +"' alla riga "+ this.riga);
		}
		// arco 0-> 2
		else {
			String valToken = "";
			while (digits.contains(this.peekChar())) {
				// arco ricorsivo 2 -> 2
				valToken += String.valueOf(this.readChar());
			}

			if (letters.contains(this.peekChar()) || this.peekChar() == '.') {
				// arco 2 -> 8
				if (letters.contains(this.peekChar())) {
					valToken += String.valueOf(this.readChar());
					throw new LexicalException("NON E' POSSIBILE AVERE UNA LETTERA IN UN INT: Provocato da '" + valToken +"' alla riga "+ this.riga);
				}
				// arco 2 -> 5
				else {
					valToken = this.strTokenFloat(valToken);
					// uscita arco
					return new Token(TokenType.FLOAT, this.riga, valToken); 
				}
			} else {
				return new Token(TokenType.INT, this.riga, valToken);
			}
		}
	}

	private String strTokenFloat(String valToken) throws IOException, LexicalException {
		valToken += String.valueOf(this.readChar());
		int cont = 0;
		// arco 5 -> 6
		while (digits.contains(this.peekChar())) {
			// arco ricorsivo 6 -> 6
			cont++;
			if (cont > 5) {
				valToken += String.valueOf(this.peekChar());
				throw new LexicalException("NON E' POSSIBILE AVERE UN NUMERO CON PIU' DI 5 VALORI NELLA PARTE DECIMALE: Provocato da '" + valToken +"' alla riga "+ this.riga);
			}
			valToken += String.valueOf(this.readChar());
		}

		// errore cont > 5 e arco 6 -> 8
		if (letters.contains(this.peekChar())) {
			valToken += String.valueOf(this.readChar());
			throw new LexicalException("NON E' POSSIBILE AVERE UNA LETTERA IN UN FLOAT: Provocato da '" + valToken +"' alla riga "+ this.riga);
		}

		return valToken;
	}

	private Token scanId() throws LexicalException, IOException {
		String valToken = "";
		while (letters.contains(this.peekChar())) {
			// arco ricorsivo 3 -> 3
			valToken += String.valueOf(this.readChar());
		}

		if (skpChars.contains(this.peekChar()) || charTypeMap.containsKey(this.peekChar())) {
			if (keywords.containsKey(valToken)) {
				return new Token(keywords.get(valToken), this.riga);
			} else {
				return new Token(TokenType.ID, this.riga, valToken);
			}
		} else {
			// arco 3 -> 8
			valToken += this.readChar();
			throw new LexicalException("NON E' POSSIBILE AVERE UN NUMERO IN UN ID/KEYWORDS: Provocato da '" + valToken +"' alla riga "+ this.riga);
		}
	}

	public Token peekToken() throws LexicalException, IOException {
		if (this.currentToken == null)
			return this.currentToken = this.nextToken();
		return this.currentToken;
	}

	private char readChar() throws IOException, LexicalException {
		return ((char) this.buffer.read());
	}

	private char peekChar() throws IOException, LexicalException {
		char c = (char) buffer.read();
		buffer.unread(c);
		return c;
	}
}
