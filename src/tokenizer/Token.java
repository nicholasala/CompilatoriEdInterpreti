package tokenizer;

import java.math.BigDecimal;

public class Token
{
    private Type type;
    private BigDecimal value;
    private String textValue;

    //TODO costruttore passando BigDecimal e non int
    public Token(int value, Type type)
    {
        this.value=new BigDecimal(value);
        this.type=type;
        this.textValue="";
    }
    
    public Token(String textValue, Type type)
    {
        this.value=new BigDecimal(0);
        this.type=type;
        this.textValue=textValue;
    }
    
    public Type getType()
    {
        return type;
    }

    public BigDecimal getValue()
    {
        return value;
    }

    public String getTextValue() {
    	return textValue;
    }
    
    @Override
    public String toString() {
    	return "[ tipo: " + type.toString() + " , valore numerico: " + value.intValue() + " , valore testuale: " + getTextValue() + " ]";
    }
}