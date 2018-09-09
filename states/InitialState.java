package states;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InitialState implements State {
	
	private final static List<Character> punctuation = Arrays.asList(',', ';','(', ')', '{', '}');
	
	private final static List<Character> arithmeticOperator = Arrays.asList('+', '-', '*', '/', '^');
	private final static List<Character> relationalOperator = Arrays.asList('>', '<', '=');
	private final static List<Character> logicalOperator = Arrays.asList('&', '|', '!');
	
	@Override
	public void nextState(StateContext stateContext, char c) {
		if(c >= 'a' && c <= 'z'){
			stateContext.setState(new Q4(), Character.toString(c));
		}
		else if(c >= '0' && c <= '9'){
			stateContext.setState(new Q1(), Character.toString(c));
		}
		else if(punctuation.contains(c)){
			stateContext.setState(new Q5(), Character.toString(c));
		}
		else if(arithmeticOperator.contains(c) || relationalOperator.contains(c) || logicalOperator.contains(c)){
			stateContext.setState(new Q6(), Character.toString(c));
		}
		else{
			stateContext.setState(new DeadState(), Character.toString(c));
		}

	}

	@Override
	public boolean isValidState() {
		return false;
	}
	
	public String classType(){
		return "INITIAL STATE";		
	}

}
