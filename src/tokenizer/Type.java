package tokenizer;

public enum Type
{
    NIL,
    FALSE,
    TRUE,
    IF,
    IFNOT,
    THEN,
    ELSE,
    FI,
    WHILE,
    WHILENOT,
    DO,
    OD,
    PRINT,
    PRINTLN,
    
    ENDOFKEYWORD,
    
    NUM,			// number
    ID,				//variable
    STRING,			//string
    
    SEMICOLON, 		// ;
    COMMA, 			// ,
    INDICATOR, 		// ->
    
    OPAREN, 		// (
    CPAREN, 		// )
    OBRACES, 		// {
    CBRACES, 		// {
    EXCLAMATION, 	// !
    STAR, 			// *
    SLASH, 			// /
    MOD, 			// %
    PLUS, 			// +
    MINUS, 			// -
    LEFTTAG, 		// <
    LEFTEQUALSTAG,  // <=
    RIGHTTAG, 		// >
    RIGHTEQUALSTAG, // >=
    EQEQ, 			// ==
    NOTEQ, 			// !=
    AND, 			// &&
    OR, 			// ||
    EQUALS, 		// =
    PLUSEQUALS, 	// +=
    MINUSEQUALS, 	// -=
    STAREQUALS, 	// *=
    SLASHEQUALS, 	// /=
    MODEQUALS, 		// %=
    
    EOS,
    UNKNOW,
}