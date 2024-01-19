package token;

/**
 * Enumerazione che rappresenta i diversi tipi di token utilizzati nella scansione di un linguaggio.
 * Ogni tipo di token è associato a una determinata categoria.
 */
public enum TokenType {
	/**
     * Tipo di token per rappresentare un intero.
     */
    INT,

    /**
     * Tipo di token per rappresentare un numero in virgola mobile.
     */
    FLOAT,

    /**
     * Tipo di token per rappresentare un identificatore.
     */
    ID,

    /**
     * Tipo di token per rappresentare il tipo di dato intero.
     */
    TYINT,

    /**
     * Tipo di token per rappresentare il tipo di dato in virgola mobile.
     */
    TYFLOAT,

    /**
     * Tipo di token per rappresentare l'istruzione di stampa.
     */
    PRINT,

    /**
     * Tipo di token per rappresentare l'operatore di assegnazione.
     */
    OP_ASSIGN,

    /**
     * Tipo di token per rappresentare l'operatore di addizione.
     */
    PLUS,

    /**
     * Tipo di token per rappresentare l'operatore di sottrazione.
     */
    MINUS,

    /**
     * Tipo di token per rappresentare l'operatore di moltiplicazione.
     */
    TIMES,

    /**
     * Tipo di token per rappresentare l'operatore di divisione.
     */
    DIVIDE,

    /**
     * Tipo di token per rappresentare il punto e virgola.
     */
    SEMI,

    /**
     * Tipo di token per rappresentare la fine del file.
     */
    EOF;
}
