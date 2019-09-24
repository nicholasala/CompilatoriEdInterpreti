# Compilatori Ed Interpreti
Simple compiler, composed by tokenizer, parser and interpreter for a custom programming language named Funny

Funny code example:

>	   {makeCounter myCounter yourCounter n ->
>
>	        makeCounter = {(balance) -> {(amount) -> balance += amount}};
>
>	        //inizializzo, ogni counter avrà la propria variabile balance
>	        //tutte le volte in cui il codice incontra una graffa aperta, crea una nuova clousure
>	        myCounter = makeCounter(100);
>	        yourCounter = makeCounter(50);
>
>	        println("myCounter: ", myCounter(0));
>	        println("yourCounter: ", yourCounter(0));
>	        println();
>
>	        n = 0;
>	        while n < 10 do
>		        //deposito 50
>		        println("myCounter[", n, "]: ", myCounter(50));
>		        //prelevo 10
>		        println("yourCounter[", n, "]: ", yourCounter(-10));
>		        println();
>		        n += 1
>	        od
>	    }

Compile and run funny code:

>	    BufferedReader reader = new BufferedReader(new FileReader("/funny.txt"));
>	    Parser parser = new Parser(reader);
>	    Expr program = parser.program();
>	    program.eval(null).checkClosure().apply(new ArrayList<>());
