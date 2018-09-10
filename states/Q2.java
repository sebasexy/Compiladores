package states;

import java.util.Arrays;
import java.util.List;

public class Q2 implements State{
	
	private final static List<Character> punctuation = Arrays.asList(',', ';','(', ')', '{', '}');

	@Override
	public void nextState(StateContext stateContext, char c) {
		if(c >= '0' && c <= '9'){
			stateContext.setState(new Q3(), stateContext.token + c);
		}
		else{
			stateContext.setState(new DeadState(), stateContext.token + c);
		}		
	}

	@Override
	public boolean isValidState() {
		return false;
	}
	
	public String classType(){
		return "CONSTANTE";		
	}
	
	/*
	 * 
	entero func1(logico l1, real r1) { real e1; logico e2; a = ((e1 + e2) - e3) /
	 func1() ^ 3; b = 4 > 4; b = b == c; b = b | c; regresa a; } real func2(logico l1, real r1)
	  { entero e1; real e1; logico e2; entero e2; a = ((e1 + e2) - e3) / 
	  func1() ^ 3; b = ((e1 + e2) - e3) / func1() ^ 3; c1 = a & b; c2 = !c1; si(c1) 
	  { mientras(b) { b = !b; s = a + b / c; } c1 = !c1; } si(c2){} regresa x1; }
	   principal() { entero e1; real e1; logico e2; entero e2; resultado = fun()1 + fun2(); }
	 */

}
