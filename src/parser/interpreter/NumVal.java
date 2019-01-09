package parser.interpreter;

import java.math.BigDecimal;

import exception.InterpreterException;

public class NumVal extends Val{
	private final BigDecimal value;
	
	NumVal(BigDecimal value){
		this.value = value;
	}
	
	public BigDecimal getValue() {
		return value;
	}
	
	@Override
	protected NumVal checkNum() throws InterpreterException{
		return this;
	}
	
	@Override
	public Val plus(Val rVal) throws InterpreterException {
		return new NumVal(value.add(rVal.checkNum().getValue()));
	}

	@Override
	public Val minus(Val rVal) throws InterpreterException {
		return new NumVal(value.subtract(rVal.checkNum().getValue()));
	}

	@Override
	public Val times(Val rVal) throws InterpreterException {
		return new NumVal(value.multiply(rVal.checkNum().getValue()));
	}

	@Override
	public Val division(Val rVal) throws InterpreterException {
		return new NumVal(value.divide(rVal.checkNum().getValue()));
	}

	@Override
	public Val module(Val rVal) throws InterpreterException {
		return new NumVal(value.remainder(rVal.checkNum().getValue()));
	}

	
	@Override
	public String toString() {
		return String.valueOf(value);
	}
}
