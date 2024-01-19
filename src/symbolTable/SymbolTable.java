package symbolTable;

import java.util.HashMap;

import ast.LangType;

/**
 * Questa classe rappresenta una tabella dei simboli, utilizzata per memorizzare informazioni sugli identificatori
 * all'interno di un programma.
 */
public class SymbolTable {
	
    /**
     * Rappresenta gli attributi associati a un identificatore.
     */
	public static class Attributes {
		private LangType tipo;
		private char registro;

        /**
         * Costruttore della classe Attributes.
         *
         * @param tipo Il tipo dell'identificatore.
         */
		public Attributes(LangType tipo) {
			this.tipo = tipo;
		}

        /**
         * Restituisce il tipo dell'identificatore.
         *
         * @return Il tipo dell'identificatore.
         */
		public LangType getTipo() {
			return tipo;
		}
		
        /**
         * Restituisce il registro associato all'identificatore.
         *
         * @return Il registro associato all'identificatore.
         */
		public char getRegistro() {
			return registro;
		}

        /**
         * Imposta il registro associato all'identificatore.
         *
         * @param registro Il registro da associare all'identificatore.
         */
		public void setRegistro(char registro) {
			this.registro = registro;
		}

        /**
         * Restituisce una rappresentazione in formato stringa degli attributi.
         *
         * @return Una stringa che rappresenta gli attributi.
         */
		@Override
		public String toString() {
			return ""+ tipo + "";
		}
	}
	
	private static HashMap<String, Attributes> table;
	
    /**
     * Inizializza la tabella dei simboli.
     */
	public static void init() {
		table = new HashMap<String,Attributes>();
	}
	
    /**
     * Inserisce un identificatore e i suoi attributi nella tabella dei simboli.
     *
     * @param id     L'identificatore da inserire.
     * @param entry  Gli attributi associati all'identificatore.
     */
	public static void enter(String id, Attributes entry) {
		table.put(id, entry);
	}
	
	/**
     * Restituisce gli attributi associati a un determinato identificatore.
     *
     * @param id L'identificatore di cui cercare gli attributi.
     * @return Gli attributi associati all'identificatore, o null se l'identificatore non è presente nella tabella.
     */
	public static Attributes lookup(String id) {
		return table.get(id);
	}
	
    /**
     * Restituisce una rappresentazione in formato stringa della tabella dei simboli.
     *
     * @return Una stringa che rappresenta la tabella dei simboli.
     */
	public static String toStr() {
		StringBuilder tableString = new StringBuilder("SymbolTable:\n");
		
		table.forEach((key, value) -> {
			tableString.append(key + "\t" + value.toString() + "\n");
		});
		
		return tableString.toString();
	} 
	
    /**
     * Restituisce il numero di elementi presenti nella tabella dei simboli.
     *
     * @return Il numero di elementi nella tabella dei simboli.
     */
	public static int size() {
		return table.size();
	}

}
