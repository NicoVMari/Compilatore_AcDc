# Compilatore da Ac a Dc

Nel linguaggio AC, sono presenti due tipi di dati principali: interi e floating point.

- **Interi**: Un letterale intero è una sequenza di cifre. Se inizia con 0, non può essere seguito da altre cifre.

- **Floating Point**: Un floating point è una sequenza di cifre (non vuota) seguita da un punto "." e al massimo 5 cifre.

Le variabili definite sono stringhe contenenti solo i 26 caratteri dell'alfabeto inglese minuscoli. È obbligatorio dichiarare una variabile prima di utilizzarla in un'espressione.

Le dichiarazioni sono fatte con le seguenti sintassi:

- `float` o `int` seguiti da una variabile e, opzionalmente, da un'inizializzazione.

Le espressioni possono essere:

- Letterali (interi o floating point).
- Variabili.
- Espressioni di tipo "espressione op espressione", dove "op" (operatore binario) può essere +, -, *, o /.

Gli operandi di un'operazione binaria devono avere lo stesso tipo. Un'espressione di tipo int può essere convertita automaticamente a float, se necessario; tuttavia, nessun'altra conversione è possibile.

Le istruzioni di assegnamento e stampa seguono le seguenti sintassi:

- **Assegnamento**: `variabile = espressione` oppure `variabile op= espressione`.
- **Stampa**: `print variabile`.
