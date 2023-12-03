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

Il linguaggio di programmazione target DC è un calcolatore "stack based," che utilizza la notazione polacca inversa (RPN), ed è cross-platform. DC è una delle prime applicazioni sviluppate per Unix.

Per utilizzare DC, è sufficiente aprire il terminale e digitare `dc`, seguito da un ritorno a capo. A questo punto, è possibile inserire le espressioni da valutare. Per stampare il risultato, si digita `p` (e `q` per uscire).

Gli operatori disponibili includono: `+`, `-`, `*`, `/`, e molti altri......

È possibile specificare la precisione delle operazioni indicando il numero di cifre decimali da considerare. La precisione predefinita è 0, il che significa che, ad esempio, l'espressione `5 4 /` restituirà il valore 0.

<p align="center">
  <img src="https://github.com/NicoVMari/Compilatore_Ac_Dc/assets/96552280/5d3c4b84-e342-4abb-ad18-f3517dac75c8" alt="cattura" width="300" />
</p>
