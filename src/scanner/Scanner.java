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
	private String log;
	private Token lastToken;

	// skpChars: insieme caratteri di skip (include EOF) e inizializzazione
	final private Set<Character> skpChars = new HashSet<>(Arrays.asList(' ', '\n', '\t', '\r', EOF));

	// letters: insieme lettere e inizializzazione
	final private Set<Character> letters = new HashSet<>(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
			'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'));

	// digits: cifre e inizializzazione
	final private Set<Character> digits = new HashSet<>(
			Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'));

	// char_type_Map: mapping fra caratteri '+', '-', '*', '/', ';', '=', ';' e il
	// TokenType corrispondente
	final private Map<Character, TokenType> charTypeMap = new HashMap<>(
			Map.of('+', TokenType.PLUS, '-', TokenType.MINUS, '*', TokenType.TIMES, '/', TokenType.DIVIDE, ';',
					TokenType.SEMI, '=', TokenType.OP_ASSIGN));
	// , TokenType.OP_ASSIGN, "+=", TokenType.OP_ASSIGN, "-=", TokenType.OP_ASSIGN,
	// "*=", TokenType.OP_ASSIGN, "/=", TokenType.OP_ASSIGN

	// keyWordsMap: mapping fra le stringhe "print", "float", "int" e il TokenType
	// corrispondente
	final private Map<String, TokenType> keywords = new HashMap<>(
			Map.of("int", TokenType.TYINT, "float", TokenType.TYFLOAT, "print", TokenType.PRINT));

	public Scanner(String fileName) throws FileNotFoundException {
		// inizializzare campi che non hanno inizializzazione
		this.buffer = new PushbackReader(new FileReader(fileName));
		riga = 1;
	}

	public Token nextToken() throws LexicalException, IOException {

		// nextChar contiene il prossimo carattere dell'input (non consumato).
		// Catturate l'eccezione IOException e ritornate una LexicalException che la
		// contiene
		char nextChar = peekChar();

		// Avanza nel buffer leggendo i carattere in skpChars
		// incrementando riga se leggi '\n'.
		// Se raggiungi la fine del file ritorna il Token EOF
		while (skpChars.contains(nextChar)) {
			// arco ricorsivo 0 -> 0
			readChar();
			if (nextChar == '\n')
				riga++;
			// arco 0 -> 1
			else if (nextChar == EOF) {
				lastToken = new Token(TokenType.EOF, riga);
				return lastToken;
			}
			nextChar = peekChar();
		}

		// Se nextChar e' in letters
		// return scanId()
		// che legge tutte le lettere minuscole e ritorna un Token ID o
		// il Token associato Parola Chiave (per generare i Token per le
		// parole chiave usate l'HaskMap di corrispondenza
		// arco 0 -> 3
		if (letters.contains(nextChar))
			return scanId();

		// Se nextChar e' o in operators oppure
		// ritorna il Token associato con l'operatore o il delimitatore
		// arco 0 -> 9
		if (charTypeMap.containsKey(nextChar) && (nextChar == ';' || nextChar == '=')) {
			if (this.peekChar() == ';') {
				readChar();
				lastToken = new Token(TokenType.SEMI, riga);
				return lastToken;
			} else {
				char c = this.peekChar();
				readChar();
				lastToken = new Token(TokenType.OP_ASSIGN, riga, String.valueOf(c));
				return lastToken;
			}
		} else if (charTypeMap.containsKey(nextChar)) {
			// arco 0 -> 4
			String valToken = String.valueOf(this.peekChar());
			readChar();
			// arco 4 -> 10
			if (charTypeMap.containsKey(this.peekChar()) && charTypeMap.get(this.peekChar()) == TokenType.OP_ASSIGN) {
				valToken += String.valueOf(this.peekChar());
				readChar();
				lastToken = new Token(TokenType.OP_ASSIGN, riga, valToken);
				return lastToken;
			} else {
				lastToken = new Token(charTypeMap.get(valToken.toCharArray()[0]), riga);
				return lastToken;
			}
		}

		// Se nextChar e' in numbers
		// return scanNumber()
		// che legge sia un intero che un float e ritorna il Token INUM o FNUM
		// i caratteri che leggete devono essere accumulati in una stringa
		// che verra' assegnata al campo valore del Token
		if (digits.contains(nextChar))
			return scanNumber();

		// Altrimenti il carattere NON E' UN CARATTERE LEGALE sollevate una
		// eccezione lessicale dicendo la riga e il carattere che la hanno
		// provocata.
		readChar();
		throw new LexicalException("NON E' UN CARATTERE LEGALE: <" + nextChar + ", " + riga + ">");
	}

	private Token scanNumber() throws IOException, LexicalException {
		// arco 0 -> 7
		if (this.peekChar() == '0') {
			String valToken = String.valueOf(this.peekChar());
			// arco 7 -> 11
			readChar();

			if (this.peekChar() == '0') {
				valToken += String.valueOf(this.readChar());
				throw new LexicalException("NON E' UN CARATTERE LEGALE: <" + valToken + ", " + riga + ">");
			} else {
				while (digits.contains(this.peekChar())) {
					// arco ricorsivo 11 -> 11
					valToken += String.valueOf(this.readChar());
				}

				if (this.peekChar() == '.') {
					valToken = strTokenFloat(valToken);
				}

				// uscita arco
				lastToken = new Token(TokenType.FLOAT, riga, valToken);
				return lastToken;
			}
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
					throw new LexicalException("NON E' UN CARATTERE LEGALE: <" + valToken + ", " + riga + ">");
				}
				// arco 2 -> 5
				else {
					valToken = strTokenFloat(valToken);
					// uscita arco
					lastToken = new Token(TokenType.FLOAT, riga, valToken);
					return lastToken;
				}
			} else {
				lastToken = new Token(TokenType.INT, riga, valToken);
				return lastToken;
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
				throw new LexicalException("NON E' UN CARATTERE LEGALE: <" + valToken + ", " + riga + ">");
			}
			valToken += String.valueOf(this.readChar());
		}

		// errore cont > 5 e arco 6 -> 8
		if (letters.contains(this.peekChar())) {
			valToken += String.valueOf(this.readChar());
			throw new LexicalException("NON E' UN CARATTERE LEGALE: <" + valToken + ", " + riga + ">");
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
				lastToken = new Token(keywords.get(valToken), riga);
				return lastToken;
			} else {
				lastToken = new Token(TokenType.ID, riga, valToken);
				return lastToken;
			}
		} else {
			// arco 3 -> 8
			valToken += this.readChar();
			throw new LexicalException("NON E' UN CARATTERE LEGALE: <" + valToken + ", " + riga + ">");
		}
	}

	public Token peekToken() {
		return lastToken;
	}

	private char readChar() throws IOException, LexicalException {
		try {
			return ((char) this.buffer.read());
		} catch (IOException e) {
			throw new LexicalException("NON E' UN CARATTERE LEGALE: <" + this.buffer.read() + ", " + riga + ">");
		}
	}

	private char peekChar() throws IOException {
		char c = (char) buffer.read();
		buffer.unread(c);
		return c;
	}
}
