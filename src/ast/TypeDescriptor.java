package ast;

/**
 * Classe che rappresenta un TypeDescriptor.
 */
public class TypeDescriptor {
    /**
     * Enumerazione che rappresenta i possibili tipi di descrittori di tipo.
     */
	public enum TypeDescriptorType {
		/**
         * Tipo intero.
         */
        INT,
        
        /**
         * Tipo floating-point.
         */
        FLOAT,
        
        /**
         * Descrittore di tipo valido.
         */
        OK,
        
        /**
         * Descrittore di tipo errore.
         */
        ERROR
	}
	
	private TypeDescriptorType tipo;
	private String msg;
	
    /**
     * Costruttore della classe TypeDescriptor.
     * 
     * @param tipo Tipo del descrittore di tipo.
     * @param msg Messaggio associato al descrittore di tipo.
     */
	public TypeDescriptor(TypeDescriptorType tipo, String msg) {
		this.tipo = tipo; 
		this.msg = msg;
	}
	
    /**
     * Costruttore della classe TypeDescriptor.
     * 
     * @param tipo Tipo del descrittore di tipo.
     */
	public TypeDescriptor(TypeDescriptorType tipo) {
		this.tipo = tipo;
		this.msg = null;
	}

    /**
     * Restituisce il tipo del descrittore di tipo.
     * 
     * @return Tipo del descrittore di tipo.
     */
	public TypeDescriptorType getTipo() {
		 return tipo;
	}

    /**
     * Restituisce il messaggio associato al descrittore di tipo.
     * 
     * @return Messaggio associato al descrittore di tipo.
     */
	public String getMsg() {
		return msg;
	}

    /**
     * Verifica la compatibilità tra due tipi di descrittori di tipo.
     * 
     * @param tD Tipo del descrittore di tipo con cui verificare la compatibilità.
     * @return True se i tipi sono compatibili, false altrimenti.
     */
	public boolean compatibile(TypeDescriptorType tD) {
		if(tipo == tD || tipo == TypeDescriptorType.FLOAT && tD == TypeDescriptorType.INT) return true;
		else return false;
	}
}
