package visitor;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Questa classe gestisce l'allocazione dei registri utilizzati per la generazione del codice assembly.
 */
public class Registri {
	/** Lista dei registri disponibili. */
	public static ArrayList<Character> registri;

    /**
     * Inizializza la lista dei registri con l'alfabeto minuscolo.
     */
	public static void init() {
		registri = new ArrayList<Character>(Arrays.asList('a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'));
	}
	
    /**
     * Restituisce un nuovo registro dalla lista e lo rimuove.
     *
     * @return Il nuovo registro allocato.
     */
	public static char newRegister() {
		char ris = registri.get(0);
		registri.remove(0);
		return ris;
	}
}
