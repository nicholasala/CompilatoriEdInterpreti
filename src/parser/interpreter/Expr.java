package parser.interpreter;

import exception.InterpreterException;

abstract class Expr {
	abstract Val eval(Env env) throws InterpreterException;
}


/*
	Valutare (eseguire, interpretare) un'espressione nel contesto dell'ambiente env
	significa ridurla a un valore.

    La distinzione tra Expr e Val è importante nei richiami. Dapprima gli argomenti, 
    che nell'albero ritornato dal compilatore sono nodi generali, vengono 
    ridotti a valori e solo a questo punto viene fatto il richiamo.
*/